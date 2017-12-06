package tw.com.ftp2q.vo;

public class OrderResponseItemVO {
	private String orderNo;
	private String orderStatus;
	private String memo;

	public OrderResponseItemVO() {

	}

	public OrderResponseItemVO(String arrStr) {
		String lastStr = arrStr.substring(arrStr.length() - 1);
		if ("|".equals(lastStr)) {
			arrStr = arrStr + "*";
			String[] arr = arrStr.split("\\|");
			if (arr.length > 2) {
				orderNo = arr[0];
				orderStatus = arr[1];
				memo = "";
			}
		} else {
			String[] arr = arrStr.split("\\|");
			if (arr.length > 2) {
				orderNo = arr[0];
				orderStatus = arr[1];
				memo = arr[2];
			}
		}

	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
