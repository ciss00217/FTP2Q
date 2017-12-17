package tw.com.ftp2q.service;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tw.com.ftp2q.factory.ConfigFactory;
import tw.com.ftp2q.mode.FTP;
import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.FTPConnectionFactoryVO;
import tw.com.ftp2q.vo.InvResponseVO;
import tw.com.ftp2q.vo.OrderResponseVO;

public class FTP2QServiceMethod {

	public static boolean editConfig(String configFilePath, FTP2QVO ftp2QVO) throws JAXBException {
		boolean isSucess = false;
		File file = new File(configFilePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(ftp2QVO, file);
		jaxbMarshaller.marshal(ftp2QVO, System.out);

		isSucess = true;
		return isSucess;
	}

	public static boolean isOrderResponseVO(String fileName) {
		String[] arr = fileName.split("-");
		if (arr.length > 4 && arr[1].equals("O")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInvResponseVO(String fileName) {
		String[] arr = fileName.split("-");
		if (arr.length > 4 && arr[1].equals("InvStatus")) {
			return true;
		} else {
			return false;
		}
	}

	public static Date strTodate(String dateStr, String pattern) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getInvResponseVOsAndRecored(String configFilePath) {

		ConfigFactory configFactory = new ConfigFactory(configFilePath);
		FTP2QVO ftp2QVO = configFactory.getFTP2QVO();
		FTPConnectionFactoryVO ftpConnectionFactoryVO = ftp2QVO.getFtpConnectionFactoryVO();

		int Port = ftpConnectionFactoryVO.getPort();
		String Host = ftpConnectionFactoryVO.getHost();
		String FileDirectory = ftpConnectionFactoryVO.getFileDirectory();
		String Password = ftpConnectionFactoryVO.getPassword();
		String Username = ftpConnectionFactoryVO.getUsername();

		FTP ftp = new FTP(Host, Port, Password, Username, FileDirectory);

		List<InvResponseVO> invResponseVOs = ftp.getInvResponseVOs(configFilePath);

		ftp.recordLastInvResponseVO(configFilePath, invResponseVOs);

		Type listType = new TypeToken<List<InvResponseVO>>() {
		}.getType();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		String json = gson.toJson(invResponseVOs, listType);
		System.out.println("getInvResponseVOsAndRecoredTest:" + json);

		return json;
	}

	public static String getOrderResponseVOsAndRecored(String configFilePath) {

		ConfigFactory configFactory = new ConfigFactory(configFilePath);
		FTP2QVO ftp2QVO = configFactory.getFTP2QVO();
		FTPConnectionFactoryVO ftpConnectionFactoryVO = ftp2QVO.getFtpConnectionFactoryVO();

		int Port = ftpConnectionFactoryVO.getPort();
		String Host = ftpConnectionFactoryVO.getHost();
		String FileDirectory = ftpConnectionFactoryVO.getFileDirectory();
		String Password = ftpConnectionFactoryVO.getPassword();
		String Username = ftpConnectionFactoryVO.getUsername();

		FTP ftp = new FTP(Host, Port, Password, Username, FileDirectory);

		List<OrderResponseVO> orderResponseVOs = ftp.getOrderResponseVOs(configFilePath);

		ftp.recordLastOrderResponseVO(configFilePath, orderResponseVOs);

		Type listType = new TypeToken<List<OrderResponseVO>>() {
		}.getType();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		String json = gson.toJson(orderResponseVOs, listType);
		
		return json;
	}
}
