package tw.com.ftp2q.test;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.FTPConnectionFactoryVO;
import tw.com.ftp2q.vo.HandleVO;
import tw.com.ftp2q.vo.HeartBeatConnectionFactoryVO;
import tw.com.ftp2q.vo.HeartBeatDestinationVO;
import tw.com.heartbeat.clinet.vo.HeartBeatClientVO;

public class CreateFTP2QVOXmlTest {

	public static void main(String[] args) {
		FTP2QVO FTP2QVO = new FTP2QVO();

		HeartBeatConnectionFactoryVO heartBeatConnectionFactory = new HeartBeatConnectionFactoryVO();
		heartBeatConnectionFactory.setPassword("password");
		heartBeatConnectionFactory.setHost("192.168.112.199");
		heartBeatConnectionFactory.setPort(5672);
		heartBeatConnectionFactory.setVirtualHost("/");
		heartBeatConnectionFactory.setUsername("admin");

		HeartBeatDestinationVO orderDestination = new HeartBeatDestinationVO();
		orderDestination.setAmqp(true);
		orderDestination.setAmqpExchangeName("jms.durable.queues");
		orderDestination.setAmqpQueueName("order");
		orderDestination.setAmqpRoutingKey("order");
		orderDestination.setDestinationName("order");

		HeartBeatDestinationVO invDestination = new HeartBeatDestinationVO();
		invDestination.setAmqp(true);
		invDestination.setAmqpExchangeName("jms.durable.queues");
		invDestination.setAmqpQueueName("inv");
		invDestination.setAmqpRoutingKey("inv");
		invDestination.setDestinationName("inv");
		
		HeartBeatDestinationVO invErrorDestination = new HeartBeatDestinationVO();
		invErrorDestination.setAmqp(true);
		invErrorDestination.setAmqpExchangeName("jms.durable.queues");
		invErrorDestination.setAmqpQueueName("invError");
		invErrorDestination.setAmqpRoutingKey("invError");
		invErrorDestination.setDestinationName("invError");
		
		HeartBeatDestinationVO orderErrorDestination = new HeartBeatDestinationVO();
		orderErrorDestination.setAmqp(true);
		orderErrorDestination.setAmqpExchangeName("jms.durable.queues");
		orderErrorDestination.setAmqpQueueName("orderError");
		orderErrorDestination.setAmqpRoutingKey("orderError");
		orderErrorDestination.setDestinationName("orderError");
		
		HeartBeatClientVO heartBeatClientVO = new HeartBeatClientVO();
		heartBeatClientVO.setBeatID("ftp2q");
		heartBeatClientVO.setFileName("ftp2q");
		heartBeatClientVO.setTimeSeries(1800000);
		
		HandleVO handleVO=new HandleVO();
		handleVO.setLastInv("12656355-InvStatus-A-20110430-210004");
		handleVO.setLastOrder("12656355-O-20110430-201927-20110430-210002");
		handleVO.setUniformNumbers("12656355");
		
		FTPConnectionFactoryVO ftpConnectionFactoryVO = new FTPConnectionFactoryVO();
		ftpConnectionFactoryVO.setFileDirectory("/home/mysqlmove/kevin_order_test");
		ftpConnectionFactoryVO.setHost("192.168.112.164");
		ftpConnectionFactoryVO.setPassword("admin123");
		ftpConnectionFactoryVO.setPort(22);
		ftpConnectionFactoryVO.setUsername("mysqlmove");
		
		FTP2QVO.setHandleVO(handleVO);
		FTP2QVO.setHeartBeatClientVO(heartBeatClientVO);
		FTP2QVO.setHeartBeatConnectionFactoryVO(heartBeatConnectionFactory);
		FTP2QVO.setInvDestination(invDestination);
		FTP2QVO.setOrderDestination(orderDestination);
		FTP2QVO.setFtpConnectionFactoryVO(ftpConnectionFactoryVO);
		FTP2QVO.setInvErrorDestination(invErrorDestination);
		FTP2QVO.setOrderErrorDestination(orderErrorDestination);
		
		try {

			File file = new File("D:\\test1.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(FTP2QVO, file);
			jaxbMarshaller.marshal(FTP2QVO, System.out);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
