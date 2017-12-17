package tw.com.ftp2q.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/*******************
 * 解析金財通發票回復檔名後所產生的Object
 * 
 * uniformNumbers	:	統一邊號
 * type				:	資料類別
 * fileName			:	檔案名稱(含附檔名)
 * lastResponseDate	:	最後回應時間
 * *****************/
public class InvResponseVO {
	private String uniformNumbers;
	private String type;
	private String fileName;
	private Date lastResponseDate;
	private List<InvResponseVOItemVO> invResponseVOItemVOs;

	public InvResponseVO() {

	}

	public InvResponseVO(String fileName) {
		String[] arr = fileName.split("-");
		if (arr.length > 4) {
			if (arr[1].equals("InvStatus") && arr[2].equals("A")) {
				String dateStr = arr[3];
				String timeStr = arr[4];
				this.type = "InvStatus-A";
				this.uniformNumbers = arr[0];
				this.lastResponseDate = getDateByString(dateStr, timeStr);
				this.fileName = fileName;

			}
		}
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getLastResponseDate() {
		return lastResponseDate;
	}

	public void setLastResponseDate(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
	}

	public List<InvResponseVOItemVO> getInvResponseVOItemVOs() {
		return invResponseVOItemVOs;
	}

	public void setInvResponseVOItemVOs(List<InvResponseVOItemVO> invResponseVOItemVOs) {
		this.invResponseVOItemVOs = invResponseVOItemVOs;
	}

	public static Date getDateByString(String dateStr, String timeStr) {
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

}
