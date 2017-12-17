package tw.com.ftp2q.service;

import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.ftp2q.factory.ConfigFactory;
import tw.com.ftp2q.mode.FTP;
import tw.com.ftp2q.producer.ProducerMessage;
import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.FTPConnectionFactoryVO;
import tw.com.heartbeat.clinet.serivce.HeartBeatService;
import tw.com.heartbeat.clinet.vo.HeartBeatClientVO;

/************************************************************************
 * 功能: 抓取金財通之訂單及發票回復資料 到分別的Queue
 * 
 * 邏輯: 
 * 1. 抓取統編一樣的資料 
 * 2. 排除最後時間lastOrder,lastInvstatus之前的 
 * 3. 將資料丟到Queue 
 * 4. 紀錄時間最大的資料到lastOrder,lastInvstatus到xml 
 * 5. 休眠30分
 ************************************************************************/

public class FTP2QService {
	private static final Logger LOG = LoggerFactory.getLogger(FTP2QService.class);

	public static void main(String[] args) throws InterruptedException {

		if (args.length >= 2) {

			String configXml = args[0];
			String heartBeatXml = args[1];

			ConfigFactory factory = new ConfigFactory(configXml);

			HeartBeatService heartBeatService = new HeartBeatService(heartBeatXml);

			HeartBeatClientVO heartBeatClientVO = heartBeatService.getHeartBeatClientVO();

			ProducerMessage invpProducerMessage = null;

			ProducerMessage orderProducerMessage = null;

			while (true) {

				heartBeatService.beat();

				String invJson = FTP2QServiceMethod.getInvResponseVOsAndRecored(configXml);

				String orderJson = FTP2QServiceMethod.getOrderResponseVOsAndRecored(configXml);

				LOG.debug("invJson: " + invJson);

				LOG.debug("orderJson: " + orderJson);

				try {
					invpProducerMessage = factory.createInvProducer();

					orderProducerMessage = factory.createOrderProducer();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (invJson != null && !"[]".equals(invJson.trim())) {
					invpProducerMessage.send(invJson);
				}

				if (orderJson != null && !"[]".equals(orderJson.trim())) {
					orderProducerMessage.send(orderJson);
				}

				Thread.sleep(heartBeatClientVO.getTimeSeries());
			}

		}

	}
}
