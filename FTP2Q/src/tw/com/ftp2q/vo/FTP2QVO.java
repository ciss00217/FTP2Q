package tw.com.ftp2q.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import tw.com.heartbeat.clinet.vo.HeartBeatClientVO;

@XmlRootElement(name = "class")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "heartBeatConnectionFactoryVO", "ftpConnectionFactoryVO", "orderDestination", "invDestination",
		"orderErrorDestination", "invErrorDestination", "heartBeatClientVO", "handleVO" })
public class FTP2QVO {

	@XmlElement(name = "jmsConnectionFactory")
	private HeartBeatConnectionFactoryVO heartBeatConnectionFactoryVO;

	@XmlElement(name = "ftpConnectionFactoryVO")
	private FTPConnectionFactoryVO ftpConnectionFactoryVO;

	@XmlElement(name = "orderDestination")
	private HeartBeatDestinationVO orderDestination;

	@XmlElement(name = "invDestination")
	private HeartBeatDestinationVO invDestination;

	@XmlElement(name = "orderErrorDestination")
	private HeartBeatDestinationVO orderErrorDestination;

	@XmlElement(name = "invErrorDestination")
	private HeartBeatDestinationVO invErrorDestination;

	// 心跳協議
	@XmlElement(name = "heartBeatClientVO")
	private HeartBeatClientVO heartBeatClientVO;

	@XmlElement(name = "handleVO")
	private HandleVO handleVO;

	public HeartBeatConnectionFactoryVO getHeartBeatConnectionFactoryVO() {
		return heartBeatConnectionFactoryVO;
	}

	public void setHeartBeatConnectionFactoryVO(HeartBeatConnectionFactoryVO heartBeatConnectionFactoryVO) {
		this.heartBeatConnectionFactoryVO = heartBeatConnectionFactoryVO;
	}

	public FTPConnectionFactoryVO getFtpConnectionFactoryVO() {
		return ftpConnectionFactoryVO;
	}

	public void setFtpConnectionFactoryVO(FTPConnectionFactoryVO ftpConnectionFactoryVO) {
		this.ftpConnectionFactoryVO = ftpConnectionFactoryVO;
	}

	public HeartBeatDestinationVO getOrderDestination() {
		return orderDestination;
	}

	public void setOrderDestination(HeartBeatDestinationVO orderDestination) {
		this.orderDestination = orderDestination;
	}

	public HeartBeatDestinationVO getInvDestination() {
		return invDestination;
	}

	public void setInvDestination(HeartBeatDestinationVO invDestination) {
		this.invDestination = invDestination;
	}

	public HeartBeatDestinationVO getOrderErrorDestination() {
		return orderErrorDestination;
	}

	public void setOrderErrorDestination(HeartBeatDestinationVO orderErrorDestination) {
		this.orderErrorDestination = orderErrorDestination;
	}

	public HeartBeatDestinationVO getInvErrorDestination() {
		return invErrorDestination;
	}

	public void setInvErrorDestination(HeartBeatDestinationVO invErrorDestination) {
		this.invErrorDestination = invErrorDestination;
	}

	public HeartBeatClientVO getHeartBeatClientVO() {
		return heartBeatClientVO;
	}

	public void setHeartBeatClientVO(HeartBeatClientVO heartBeatClientVO) {
		this.heartBeatClientVO = heartBeatClientVO;
	}

	public HandleVO getHandleVO() {
		return handleVO;
	}

	public void setHandleVO(HandleVO handleVO) {
		this.handleVO = handleVO;
	}

	

}
