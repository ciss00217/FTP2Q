package tw.com.ftp2q.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.com.heartbeat.factory.RabbitFactory;

public class ProducerMessage {
	private static final Logger logger = LogManager.getLogger(ProducerMessage.class);
	MessageProducer producer = null;
	MessageProducer errorProducer = null;
	TextMessage textMessage = null;

	
	public void send(String mseeage) {
		try {
			logger.debug("send:" + mseeage);
			textMessage.setText(mseeage);
			producer.send(textMessage);
		} catch (Exception e) {
			logger.debug("Error:" + e.getMessage());
			logger.debug("send:" + mseeage);
			try {
				textMessage.setText(mseeage);

				errorProducer.send(textMessage);
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public ProducerMessage(Destination destination,Destination errorDestination, ConnectionFactory connectionFactory) throws JMSException {

		Connection connection = connectionFactory.createConnection();

		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		producer = session.createProducer(destination);
		errorProducer = session.createProducer(errorDestination);
		textMessage = session.createTextMessage();
	}

}
