package tw.com.ftp2q.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderResponseVO {
	private String uniformNumbers;
	private String type;
	private String fileName;
	private Date lastUploadDate;
	private Date lastResponseDate;
	private List<OrderResponseItemVO> orderResponseItemVOs;
	
	public OrderResponseVO(String fileName) {
		String[] arr = fileName.split("-");
		if (arr.length > 4) {
			if (arr[1].equals("O") && arr[2].equals("M")) {
				String dateStr = arr[3];
				String timeStr = arr[4];
				this.type = "O-M";
				this.uniformNumbers = arr[0];
				this.lastUploadDate=getDateByString(dateStr,timeStr);
				dateStr = arr[5];
				timeStr = arr[6];
				lastResponseDate=getDateByString(dateStr,timeStr);
				this.fileName=fileName;

			} else if (arr[1].equals("O") && arr[2].equals("MD")) {
				String dateStr = arr[3];
				String timeStr = arr[4];
				this.type = "O-MD";
				this.uniformNumbers = arr[0];
				this.lastUploadDate=getDateByString(dateStr,timeStr);
				dateStr = arr[5];
				timeStr = arr[6];
				this.lastResponseDate=getDateByString(dateStr,timeStr);
				this.fileName=fileName;

			} else if (arr[1].equals("O") && (!arr[2].equals("MD")) && (!arr[2].equals("M"))) {
				String dateStr = arr[2];
				String timeStr = arr[3];
				this.type = "O";
				this.uniformNumbers = arr[0];
				this.lastUploadDate=getDateByString(dateStr,timeStr);
				dateStr = arr[4];
				timeStr = arr[5];
				this.lastResponseDate=getDateByString(dateStr,timeStr);
				this.fileName=fileName;
			}

		}
	}
	
	public static Date getDateByString(String dateStr,String timeStr){
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			date = df.parse(String.valueOf(dateStr + timeStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUniformNumbers() {
		return uniformNumbers;
	}


	public void setUniformNumbers(String uniformNumbers) {
		this.uniformNumbers = uniformNumbers;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Date getLastUploadDate() {
		return lastUploadDate;
	}


	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}


	public Date getLastResponseDate() {
		return lastResponseDate;
	}


	public void setLastResponseDate(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
	}


	public List<OrderResponseItemVO> getOrderResponseItemVOs() {
		return orderResponseItemVOs;
	}


	public void setOrderResponseItemVOs(List<OrderResponseItemVO> orderResponseItemVOs) {
		this.orderResponseItemVOs = orderResponseItemVOs;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dateStr = "20171129";
		String timeStr = "145859";
		Date date=getDateByString(dateStr,timeStr);
		
		System.out.println(date);
	}

}
