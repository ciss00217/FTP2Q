package tw.com.ftp2q.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="handleVO")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "lastOrder", "lastInv","uniformNumbers"})
public class HandleVO {
	//最後的訂單紀錄
	@XmlElement(name = "lastOrder")
	private String lastOrder;
	
	//最後的發票紀錄
	@XmlElement(name = "lastInv")
	private String lastInv;
	
	//統一編號
	@XmlElement(name = "uniformNumbers")
	private String uniformNumbers;

	public String getUniformNumbers() {
		return uniformNumbers;
	}
	public void setUniformNumbers(String uniformNumbers) {
		this.uniformNumbers = uniformNumbers;
	}
	public String getLastOrder() {
		return lastOrder;
	}
	public void setLastOrder(String lastOrder) {
		this.lastOrder = lastOrder;
	}
	public String getLastInv() {
		return lastInv;
	}
	public void setLastInv(String lastInv) {
		this.lastInv = lastInv;
	}

	
}
