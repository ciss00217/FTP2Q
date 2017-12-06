package tw.com.ftp2q.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/********************************************************************************
*	order_no: 					訂單編號 char 40 Y 0 新單/1 修單/2 刪單
*	order_ship_date: 			預計出貨日期 char 10 Y
*	order_status	:			訂單狀態 nvarchar 2 Y 新單;修單
*	invoice_number:				發票號碼 char 10 Y
*	invoice_date:				發票日期 char 10 Y
*	invoice_status:				發票狀態 nvarchar 4 Y 開立;作廢;待作廢;待折讓;折讓;異常
*	invoice_type:				二聯或三聯 nvarchar 2 Y 三聯;二聯
*	invoice_processing_method:	電子/捐贈/紙本 nvarchar 2 Y 紙本;捐贈;電子
*	vat_type:					稅率別 nvarchar 3 Y 應稅;零稅率;免稅;混和稅率
*	invoice_without_vat_price:	發票未稅金額 Number 14 Y 或折讓單的未稅金額
*	invoice_vat_price:			發票稅額 Number 14 Y 或折讓單的稅額金額
*	invoice_include_vat_price:	發票含稅金額 Number 14 Y 或折讓單的含稅金額
*	buyer_ein_code:				買方統一編號 char 10 Y 二聯發票帶空值
*	transaction_date:			異動日期 char 20 Y
*	payment_method:				付款方式 CHAR 100 OPTION
*	related_number_1:			相關號碼 1(出貨單號) CHAR 20 OPTION
*	related_number_2:			相關號碼 2 CHAR 20 OPTION 退貨單號(退款單號)=折讓單號
* 
**********************************************************************************/
public class InvResponseVOItemVO {
	private String order_no;
	private String order_ship_date;
	private String order_status;
	private String invoice_number;
	private String invoice_date;
	private String invoice_status;
	private String invoice_type;
	private String invoice_processing_method;
	private String vat_type;
	private String invoice_without_vat_price;
	private String invoice_vat_price;
	private String invoice_include_vat_price;
	private String buyer_ein_code;
	private String transaction_date;
	private String payment_method;
	private String related_number_1;
	private String related_number_2;
	
	public InvResponseVOItemVO(String response) {
		String lastStr = response.substring(response.length() - 1);
		if ("|".equals(lastStr)) {
			response=response+"*";
			
			String[] arr = response.split("\\|");
			if (arr.length > 16) {
				order_no = arr[0];
				order_ship_date = arr[1];
				order_status = arr[2];
				invoice_number = arr[3];
				invoice_date = arr[4];
				invoice_status = arr[5];
				invoice_type = arr[6];
				invoice_processing_method = arr[7];
				vat_type = arr[8];
				invoice_without_vat_price = arr[9];
				invoice_vat_price = arr[10];
				invoice_include_vat_price = arr[11];
				buyer_ein_code = arr[12];
				transaction_date = arr[13];
				payment_method = arr[14];
				related_number_1 = arr[15];
				related_number_2 = "";

			}
		} else {
			String[] arr = response.split("\\|");
			if (arr.length > 16) {
				order_no = arr[0];
				order_ship_date = arr[1];
				order_status = arr[2];
				invoice_number = arr[3];
				invoice_date = arr[4];
				invoice_status = arr[5];
				invoice_type = arr[6];
				invoice_processing_method = arr[7];
				vat_type = arr[8];
				invoice_without_vat_price = arr[9];
				invoice_vat_price = arr[10];
				invoice_include_vat_price = arr[11];
				buyer_ein_code = arr[12];
				transaction_date = arr[13];
				payment_method = arr[14];
				related_number_1 = arr[15];
				related_number_2 = arr[16];
			}
		}
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getOrder_ship_date() {
		return order_ship_date;
	}

	public void setOrder_ship_date(String order_ship_date) {
		this.order_ship_date = order_ship_date;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoice_processing_method() {
		return invoice_processing_method;
	}

	public void setInvoice_processing_method(String invoice_processing_method) {
		this.invoice_processing_method = invoice_processing_method;
	}

	public String getVat_type() {
		return vat_type;
	}

	public void setVat_type(String vat_type) {
		this.vat_type = vat_type;
	}

	public String getInvoice_without_vat_price() {
		return invoice_without_vat_price;
	}

	public void setInvoice_without_vat_price(String invoice_without_vat_price) {
		this.invoice_without_vat_price = invoice_without_vat_price;
	}

	public String getInvoice_vat_price() {
		return invoice_vat_price;
	}

	public void setInvoice_vat_price(String invoice_vat_price) {
		this.invoice_vat_price = invoice_vat_price;
	}

	public String getInvoice_include_vat_price() {
		return invoice_include_vat_price;
	}

	public void setInvoice_include_vat_price(String invoice_include_vat_price) {
		this.invoice_include_vat_price = invoice_include_vat_price;
	}

	public String getBuyer_ein_code() {
		return buyer_ein_code;
	}

	public void setBuyer_ein_code(String buyer_ein_code) {
		this.buyer_ein_code = buyer_ein_code;
	}

	public String getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getRelated_number_1() {
		return related_number_1;
	}

	public void setRelated_number_1(String related_number_1) {
		this.related_number_1 = related_number_1;
	}

	public String getRelated_number_2() {
		return related_number_2;
	}

	public void setRelated_number_2(String related_number_2) {
		this.related_number_2 = related_number_2;
	}

	
}
