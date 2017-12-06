package tw.com.ftp2q.factory;

import java.io.File;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import tw.com.ftp2q.producer.ProducerMessage;
import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.FTPConnectionFactoryVO;
import tw.com.ftp2q.vo.HeartBeatConnectionFactoryVO;
import tw.com.ftp2q.vo.HeartBeatDestinationVO;
import tw.com.heartbeat.clinet.vo.HeartBeatClientVO;

public class ConfigFactory {
	String filePath;

	public ConfigFactory(String filePath) {
		this.filePath = filePath;
	}
	
	public FTP2QVO getFTP2QVO() {

		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);

			return ftp2QVO;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}
	

	public RMQDestination getFTP2QInvDestination() {
		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);
			
			//因功能需求一樣拿來當模板
			tw.com.ftp2q.vo.HeartBeatDestinationVO destinationVO=ftp2QVO.getInvDestination();

			RMQDestination rMQDestination = new RMQDestination();

			rMQDestination.setDestinationName(destinationVO.getDestinationName());
			rMQDestination.setAmqp(destinationVO.isAmqp());
			rMQDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
			rMQDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
			rMQDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

			return rMQDestination;


		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public RMQDestination getFTP2QInvErrorDestination() {

		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);
			tw.com.ftp2q.vo.HeartBeatDestinationVO destinationVO =ftp2QVO.getInvErrorDestination();

			RMQDestination rMQDestination = new RMQDestination();
			rMQDestination.setDestinationName(destinationVO.getDestinationName());
			rMQDestination.setAmqp(destinationVO.isAmqp());
			rMQDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
			rMQDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
			rMQDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

			return rMQDestination;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public RMQDestination getFTP2QOrderErrorDestination() {

		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);
			tw.com.ftp2q.vo.HeartBeatDestinationVO destinationVO =ftp2QVO.getOrderErrorDestination();

			RMQDestination rMQDestination = new RMQDestination();
			rMQDestination.setDestinationName(destinationVO.getDestinationName());
			rMQDestination.setAmqp(destinationVO.isAmqp());
			rMQDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
			rMQDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
			rMQDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

			return rMQDestination;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}

	public RMQConnectionFactory createRabbitConnectionFactory() {
		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);

			tw.com.ftp2q.vo.HeartBeatConnectionFactoryVO heartBeatConnectionFactoryVO = ftp2QVO.getHeartBeatConnectionFactoryVO();

			RMQConnectionFactory rMQConnectionFactory = new RMQConnectionFactory();
			rMQConnectionFactory.setPassword(heartBeatConnectionFactoryVO.getPassword());
			rMQConnectionFactory.setHost(heartBeatConnectionFactoryVO.getHost());
			rMQConnectionFactory.setUsername(heartBeatConnectionFactoryVO.getUsername());
			rMQConnectionFactory.setVirtualHost(heartBeatConnectionFactoryVO.getVirtualHost());

			return rMQConnectionFactory;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public HeartBeatClientVO createHeartBeatClientVO() {
		try {
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);

			HeartBeatClientVO heartBeatClientVO = ftp2QVO.getHeartBeatClientVO();

			return heartBeatClientVO;

		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}

	}
	

	
	public ProducerMessage createOrderProducer() throws JMSException, JAXBException {
		
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);

			HeartBeatDestinationVO destinationVO = ftp2QVO.getOrderDestination();

			RMQDestination orderDestination = new RMQDestination();

			orderDestination.setDestinationName(destinationVO.getDestinationName());
			orderDestination.setAmqp(destinationVO.isAmqp());
			orderDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
			orderDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
			orderDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

			destinationVO = ftp2QVO.getOrderErrorDestination();

			RMQDestination errorDestination = new RMQDestination();

			errorDestination.setDestinationName(destinationVO.getDestinationName());
			errorDestination.setAmqp(destinationVO.isAmqp());
			errorDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
			errorDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
			errorDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

			HeartBeatConnectionFactoryVO connectionFactoryVO = ftp2QVO.getHeartBeatConnectionFactoryVO();

			RMQConnectionFactory rMQConnectionFactory = new RMQConnectionFactory();
			rMQConnectionFactory.setPassword(connectionFactoryVO.getPassword());
			rMQConnectionFactory.setHost(connectionFactoryVO.getHost());
			rMQConnectionFactory.setUsername(connectionFactoryVO.getUsername());
			rMQConnectionFactory.setVirtualHost(connectionFactoryVO.getVirtualHost());

			ProducerMessage producrerMessage = new ProducerMessage(orderDestination, errorDestination,
					rMQConnectionFactory);

		
		return producrerMessage;

	}
	
	public ProducerMessage createInvProducer() throws JAXBException, JMSException{
		
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(FTP2QVO.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		FTP2QVO ftp2QVO = (FTP2QVO) jaxbUnmarshaller.unmarshal(file);

		HeartBeatDestinationVO destinationVO = ftp2QVO.getInvDestination();

		RMQDestination invDestination = new RMQDestination();

		invDestination.setDestinationName(destinationVO.getDestinationName());
		invDestination.setAmqp(destinationVO.isAmqp());
		invDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
		invDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
		invDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

		destinationVO = ftp2QVO.getInvErrorDestination();

		RMQDestination errorDestination = new RMQDestination();

		errorDestination.setDestinationName(destinationVO.getDestinationName());
		errorDestination.setAmqp(destinationVO.isAmqp());
		errorDestination.setAmqpExchangeName(destinationVO.getAmqpExchangeName());
		errorDestination.setAmqpQueueName(destinationVO.getAmqpQueueName());
		errorDestination.setAmqpRoutingKey(destinationVO.getAmqpRoutingKey());

		HeartBeatConnectionFactoryVO connectionFactoryVO = ftp2QVO.getHeartBeatConnectionFactoryVO();

		RMQConnectionFactory rMQConnectionFactory = new RMQConnectionFactory();
		rMQConnectionFactory.setPassword(connectionFactoryVO.getPassword());
		rMQConnectionFactory.setHost(connectionFactoryVO.getHost());
		rMQConnectionFactory.setUsername(connectionFactoryVO.getUsername());
		rMQConnectionFactory.setVirtualHost(connectionFactoryVO.getVirtualHost());

		ProducerMessage producrerMessage = new ProducerMessage(invDestination, errorDestination,
				rMQConnectionFactory);

	
	return producrerMessage;

}
	
}
