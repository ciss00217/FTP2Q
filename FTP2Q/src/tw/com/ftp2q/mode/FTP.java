package tw.com.ftp2q.mode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import tw.com.ftp2q.factory.ConfigFactory;
import tw.com.ftp2q.producer.ProducerMessage;
import tw.com.ftp2q.service.FTP2QServiceMethod;
import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.FTPConnectionFactoryVO;
import tw.com.ftp2q.vo.InvResponseVO;
import tw.com.ftp2q.vo.InvResponseVOItemVO;
import tw.com.ftp2q.vo.OrderResponseItemVO;
import tw.com.ftp2q.vo.OrderResponseVO;
import tw.com.heartbeat.clinet.serivce.HeartBeatService;
import tw.com.heartbeat.clinet.vo.HeartBeatClientVO;

public class FTP {
	private static final Logger LOG = LoggerFactory.getLogger(FTP.class);

	private String host;
	private int port;
	private String username;
	private String password;
	private String dir;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public FTP(String host, int port, String username, final String password, String dir) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.dir = dir;
	}

	/******************
	 * 功能: 取得在lastInv 之後的所有資料並包成物件
	 * 
	 ********/
	public List<InvResponseVO> getInvResponseVOs(String configFilePath) {

		ConfigFactory configFactory = new ConfigFactory(configFilePath);

		FTP2QVO ftp2QVO = configFactory.getFTP2QVO();

		// 統編從設定檔來
		String uniform_numbers = ftp2QVO.getHandleVO().getUniformNumbers();

		// lastInv從設定檔來
		String lastInv = ftp2QVO.getHandleVO().getLastInv();

		// 取得全部的檔案名稱
		List<String> allFileNames = listFileNames();

		List<InvResponseVO> invResponseVOs = getAllInvResponseVOsByUniformNumbers(allFileNames, uniform_numbers);

		if (lastInv != null && !"".equals(lastInv)) {

			InvResponseVO lastInvResponseVO = new InvResponseVO(lastInv);

			invResponseVOs.removeIf((InvResponseVO invResponseVO) -> (invResponseVO.getLastResponseDate()
					.getTime() <= lastInvResponseVO.getLastResponseDate().getTime()));
		}

		for (InvResponseVO invResponseVO : invResponseVOs) {
			this.setInvResponseVOItemVO(invResponseVO);
		}

		return invResponseVOs;
	}

	/**************
	 * 功能: 取得在lastOrder 之後的所有資料並且包成物件
	 *
	 ******/
	public List<OrderResponseVO> getOrderResponseVOs(String configFilePath) {

		ConfigFactory configFactory = new ConfigFactory(configFilePath);

		FTP2QVO ftp2QVO = configFactory.getFTP2QVO();

		// 統編從設定檔來
		String uniform_numbers = ftp2QVO.getHandleVO().getUniformNumbers();

		// lastOrder從設定檔來
		String lastOrder = ftp2QVO.getHandleVO().getLastOrder();

		// 取得全部的檔案名稱
		List<String> allFileNames = listFileNames();

		List<OrderResponseVO> orderResponseVOs = getAllOrderResponseVOsByUniformNumbers(allFileNames, uniform_numbers);

		if (lastOrder != null && !"".equals(lastOrder)) {

			OrderResponseVO lastOrderResponseVO = new OrderResponseVO(lastOrder);

			orderResponseVOs.removeIf((OrderResponseVO orderResponsevo) -> (orderResponsevo.getLastResponseDate()
					.getTime() <= lastOrderResponseVO.getLastResponseDate().getTime()));
		}

		for (OrderResponseVO orderResponseVO : orderResponseVOs) {
			this.setOrderResponseItemVO(orderResponseVO);
		}

		return orderResponseVOs;
	}

	/************************
	 * 功能: 抓取金財通之訂單到Queue
	 * 
	 * 邏輯: 1. 抓取統編一樣的資料 2. 排除最後時間lastOrder之前的 3. 將資料丟到Queue 4.
	 * 紀錄時間最大的資料到lastOrder,lastInvstatus到xml
	 ***/
	public boolean downloanOrder(String configFilePath, String outPutPath) {
		boolean isSucess = false;
		try {
			ConfigFactory configFactory = new ConfigFactory(configFilePath);

			FTP2QVO ftp2QVO = configFactory.getFTP2QVO();

			// 統編從設定檔來
			String uniform_numbers = ftp2QVO.getHandleVO().getUniformNumbers();

			// lastOrder從設定檔來
			String lastOrder = ftp2QVO.getHandleVO().getLastOrder();

			// 取得全部的檔案名稱
			List<String> allFileNames = listFileNames();

			List<OrderResponseVO> orderResponseVOs = getAllOrderResponseVOsByUniformNumbers(allFileNames,
					uniform_numbers);

			if (lastOrder != null && !"".equals(lastOrder)) {

				OrderResponseVO lastOrderResponseVO = new OrderResponseVO(lastOrder);

				orderResponseVOs.removeIf((OrderResponseVO orderResponsevo) -> (orderResponsevo.getLastResponseDate()
						.getTime() <= lastOrderResponseVO.getLastResponseDate().getTime()));
			}

			OrderResponseVO lastOrderResponseVO = null;
			for (OrderResponseVO orderResponseVO : orderResponseVOs) {
				String fileName = orderResponseVO.getFileName();
				File file = new File(outPutPath + "\\" + fileName);
				boolean isSuccess = download(fileName, file);

				LOG.debug("fileName:" + fileName);
				LOG.debug("isSuccess:" + isSuccess);

				lastOrderResponseVO = getLastOrderResponseVO(lastOrderResponseVO, orderResponseVO);
			}

			if (lastOrderResponseVO != null) {
				// 紀錄 lastOrderResponseVO 到xml
				String fileName=lastOrderResponseVO.getFileName();
				fileName=fileName.replace(".txt", "");
				ftp2QVO.getHandleVO().setLastOrder(fileName);

				FTP2QServiceMethod.editConfig(configFilePath, ftp2QVO);
			}
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isSucess;
	}

	/****************************
	 * 紀錄 回應時間最後的invResponseVO 在config裡
	 * 
	 * configFilePath: config 檔案路徑位置
	 * 
	 * invResponseVOs: 比較的List
	 * 
	 * ************************/
	public boolean recordLastInvResponseVO(String configFilePath, List<InvResponseVO> invResponseVOs) {
		boolean isSucess = false;
		try {
			ConfigFactory configFactory = new ConfigFactory(configFilePath);

			FTP2QVO ftp2QVO = configFactory.getFTP2QVO();

			InvResponseVO lastInvResponseVO = getLastInvResponseVO(invResponseVOs);

			if (lastInvResponseVO != null) {
				// 紀錄 lastOrderResponseVO 到xml
				String fileName=lastInvResponseVO.getFileName();
				
				fileName=fileName.replace(".txt", "");
				
				ftp2QVO.getHandleVO().setLastInv(fileName);

				FTP2QServiceMethod.editConfig(configFilePath, ftp2QVO);
			}
			isSucess = true;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSucess;
	}
	
	
	/****************************
	 * 紀錄 回應時間最後的OrderResponseVO 在config裡
	 * 
	 * configFilePath: config 檔案路徑位置
	 * 
	 * orderResponseVOs: 比較的List
	 * 
	 * ************************/
	public boolean recordLastOrderResponseVO(String configFilePath, List<OrderResponseVO> orderResponseVOs) {
		boolean isSucess = false;
		try {
			ConfigFactory configFactory = new ConfigFactory(configFilePath);

			FTP2QVO ftp2QVO = configFactory.getFTP2QVO();

			OrderResponseVO lastOrderResponseVO = getLastOrderResponseVO(orderResponseVOs);

			if (lastOrderResponseVO != null) {
				// 紀錄 lastOrderResponseVO 到xml
				
				String fileName=lastOrderResponseVO.getFileName();
				fileName=fileName.replace(".txt", "");
				ftp2QVO.getHandleVO().setLastOrder(fileName);

				FTP2QServiceMethod.editConfig(configFilePath, ftp2QVO);
			}
			isSucess = true;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSucess;
	}

	/************
	 * 功能: 回傳比對時間較大的
	 *
	 * 回應時間最大的即是是最後的回應的
	 * 
	 *********************/
	public OrderResponseVO getLastOrderResponseVO(OrderResponseVO lastOrderResponseVO,
			OrderResponseVO orderResponseVO) {
		if (lastOrderResponseVO == null) {
			lastOrderResponseVO = orderResponseVO;
		} else {
			Long lateTime = lastOrderResponseVO.getLastResponseDate().getTime();

			Long orderTime = orderResponseVO.getLastResponseDate().getTime();

			if (orderTime > lateTime) {
				lastOrderResponseVO = orderResponseVO;
			}
		}
		return lastOrderResponseVO;
	}
	
	/************
	 * 功能: 回傳比對時間較大的
	 *
	 * 回應時間最大的即是是最後的回應的
	 * 
	 *********************/
	public OrderResponseVO getLastOrderResponseVO(List<OrderResponseVO> orderResponseVOs) {
		OrderResponseVO lastOrderResponseVO = null;
		for (OrderResponseVO orderResponseVO : orderResponseVOs) {
			if (lastOrderResponseVO == null) {
				lastOrderResponseVO = orderResponseVO;
			} else {
				Long lateTime = lastOrderResponseVO.getLastResponseDate().getTime();

				Long orderTime = orderResponseVO.getLastResponseDate().getTime();

				if (orderTime > lateTime) {
					lastOrderResponseVO = orderResponseVO;
				}
			}
		}

		return lastOrderResponseVO;
	}

	/************
	 * 功能: 回傳比對時間較大的
	 *
	 * 回應時間最大的即是是最後的回應的
	 * 
	 *********************/
	public InvResponseVO getLastInvResponseVO(List<InvResponseVO> invResponseVOs) {
		InvResponseVO lastInvResponseVO = null;
		for (InvResponseVO invResponseVO : invResponseVOs) {
			if (lastInvResponseVO == null) {
				lastInvResponseVO = invResponseVO;
			} else {
				Long lateTime = lastInvResponseVO.getLastResponseDate().getTime();

				Long orderTime = invResponseVO.getLastResponseDate().getTime();

				if (orderTime > lateTime) {
					lastInvResponseVO = invResponseVO;
				}
			}
		}
		return lastInvResponseVO;
	}

	public boolean downloanInv() {
		boolean isSucess = false;

		return isSucess;
	}

	/******
	 * 取到合乎統一編號的發票資料
	 * 
	 ******/
	public List<InvResponseVO> getAllInvResponseVOsByUniformNumbers(List<String> allFileNames, String uniformNumbers) {
		List<InvResponseVO> list = new ArrayList<InvResponseVO>();

		for (String fileName : allFileNames) {
			String[] uniformNumbersArr = fileName.split("-");
			String mUniformNumbers = uniformNumbersArr[0];
			if (uniformNumbers.equals(mUniformNumbers)) {
				try {
					if (FTP2QServiceMethod.isInvResponseVO(fileName)) {
						list.add(new InvResponseVO(fileName));
					}
				} catch (Exception e) {
					LOG.error("something wrong by fileName:" + fileName, e);
				}

			}
		}
		return list;
	}

	/******
	 * 取到合乎統一編號的訂單資料
	 * 
	 ******/
	public List<OrderResponseVO> getAllOrderResponseVOsByUniformNumbers(List<String> allFileNames,
			String uniformNumbers) {
		List<OrderResponseVO> list = new ArrayList<OrderResponseVO>();

		for (String fileName : allFileNames) {
			String[] uniformNumbersArr = fileName.split("-");
			String mUniformNumbers = uniformNumbersArr[0];
			if (uniformNumbers.equals(mUniformNumbers)) {
				try {
					if (FTP2QServiceMethod.isOrderResponseVO(fileName)) {
						list.add(new OrderResponseVO(fileName));
					}
				} catch (Exception e) {
					LOG.error("something wrong by fileName:" + fileName, e);
				}

			}
		}
		return list;
	}

	/*******************
	 * 功能:從FTP上抓取內容放入orderResponseVO裡
	 * 
	 * 再將內容物件化並放入OrderResponseItemVO裡
	 ********************/
	public void setOrderResponseItemVO(OrderResponseVO orderResponseVO) {

		boolean isSucess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		String readFileName = orderResponseVO.getFileName();

		List<OrderResponseItemVO> orderResponseItemVOs = new ArrayList<OrderResponseItemVO>();

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(dir);

			InputStream stream = channelSftp.get(readFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				OrderResponseItemVO item = new OrderResponseItemVO(line);
				orderResponseItemVOs.add(item);
			}
			orderResponseVO.setOrderResponseItemVOs(orderResponseItemVOs);
			isSucess = true;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeChannel(channelSftp);
			closeChannel(channel);
			closeSession(session);
		}

	}

	/*******************
	 * 功能:從FTP上抓取內容放入InvResponseVO裡
	 * 
	 * 再將內容物件化並放入InvResponseVOItemVO裡
	 ********************/
	public void setInvResponseVOItemVO(InvResponseVO invResponseVO) {

		boolean isSucess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		String readFileName = invResponseVO.getFileName();

		List<InvResponseVOItemVO> invResponseVOItemVOs = new ArrayList<InvResponseVOItemVO>();

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(dir);

			InputStream stream = channelSftp.get(readFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				InvResponseVOItemVO item = new InvResponseVOItemVO(line);
				invResponseVOItemVOs.add(item);
			}
			invResponseVO.setInvResponseVOItemVOs(invResponseVOItemVOs);
			isSucess = true;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeChannel(channelSftp);
			closeChannel(channel);
			closeSession(session);
		}

	}

	public boolean download(String downloadFileName, File outPutFile) {
		boolean isSucess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(dir);
			byte[] buffer = new byte[1024];
			BufferedInputStream bis = new BufferedInputStream(channelSftp.get(downloadFileName));

			OutputStream os = new FileOutputStream(outPutFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			isSucess = true;
			bis.close();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeChannel(channelSftp);
			closeChannel(channel);
			closeSession(session);
		}
		return isSucess;

	}

	public boolean upload(File uploadFile) {
		boolean isSucess = false;
		ChannelSftp sftp = null;
		Channel channel = null;
		Session sshSession = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			sftp.cd(dir);
			sftp.put(new FileInputStream(uploadFile), uploadFile.getName());
			isSucess = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeChannel(sftp);
			closeChannel(channel);
			closeSession(sshSession);
		}
		return isSucess;

	}

	public List<String> listFileNames() {
		List<String> list = new ArrayList<String>();
		ChannelSftp sftp = null;
		Channel channel = null;
		Session sshSession = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			Vector<?> vector = sftp.ls(dir);

			for (Object item : vector) {
				LsEntry entry = (LsEntry) item;
				list.add(entry.getFilename());
				//System.out.println(entry.getFilename());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeChannel(sftp);
			closeChannel(channel);
			closeSession(sshSession);
		}
		return list;
	}

	private static void closeChannel(Channel channel) {
		if (channel != null) {
			if (channel.isConnected()) {
				channel.disconnect();
			}
		}
	}

	private static void closeSession(Session session) {
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			}
		}
	}

	
	public static void main(String[] args) throws InterruptedException {
		ConfigFactory configFactory = new ConfigFactory("D:\\jarManager\\jarXml\\ftp2q-ftp2q-config.xml");
		FTP2QVO ftp2QVO = configFactory.getFTP2QVO();
		FTPConnectionFactoryVO ftpConnectionFactoryVO = ftp2QVO.getFtpConnectionFactoryVO();
		
		int Port =ftp2QVO.getFtpConnectionFactoryVO().getPort();
		String Host =ftp2QVO.getFtpConnectionFactoryVO().getHost();
		String FileDirectory =ftp2QVO.getFtpConnectionFactoryVO().getFileDirectory();
		String Password =ftp2QVO.getFtpConnectionFactoryVO().getPassword();
		String Username =ftp2QVO.getFtpConnectionFactoryVO().getUsername();
		
		System.out.println("Port :"+Port);
		System.out.println("Host :"+Host);
		System.out.println("FileDirectory :"+FileDirectory);
		System.out.println("Password :"+Password);
		System.out.println("Username :"+ Username);

		FTP ftp = new FTP(Host,Port,
				Password, Username,
				FileDirectory);
		
		List<String> aaa=ftp.listFileNames();
		
		for(String bbb:aaa){
			System.out.println(bbb);
		}
		
	}

}
