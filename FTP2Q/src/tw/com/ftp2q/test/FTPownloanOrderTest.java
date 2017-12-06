package tw.com.ftp2q.test;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tw.com.ftp2q.factory.ConfigFactory;
import tw.com.ftp2q.mode.FTP;
import tw.com.ftp2q.vo.FTP2QVO;
import tw.com.ftp2q.vo.InvResponseVO;
import tw.com.ftp2q.vo.OrderResponseItemVO;
import tw.com.ftp2q.vo.OrderResponseVO;

public class FTPownloanOrderTest {
	public static void main(String[] args) {
		getInvResponseVOsAndRecoredTest();
		//getOrderResponseVOsAndRecoredTest();
	}

	public static void getOrderResponseVOsTest() {
		FTP FTP = new FTP("192.168.112.164", 22, "mysqlmove", "admin123", "/home/mysqlmove/kevin_order_test");

		List<OrderResponseVO> list = FTP.getOrderResponseVOs("D:\\jarManager\\jarXml\\ftp2q-ftp2q-config.xml");

		Type listType = new TypeToken<List<OrderResponseVO>>() {
		}.getType();
		
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		
		String json = gson.toJson(list, listType);

		System.out.println("okokokok:" + json);
	}

	public static void getInvResponseVOsTest() {
		FTP FTP = new FTP("192.168.112.164", 22, "mysqlmove", "admin123", "/home/mysqlmove/kevin_order_test");

		List<InvResponseVO> list = FTP.getInvResponseVOs("D:\\test1.xml");

		Type listType = new TypeToken<List<InvResponseVO>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		String json = gson.toJson(list, listType);
		System.out.println("okokokok:" + json);
	}
	
	public static void getInvResponseVOsAndRecoredTest() {
		String configFilePath="D:\\test1.xml";
		FTP FTP = new FTP("192.168.112.164", 22, "mysqlmove", "admin123", "/home/mysqlmove/kevin_order_test");

		List<InvResponseVO> invResponseVOs = FTP.getInvResponseVOs(configFilePath);

		 FTP.recordLastInvResponseVO(configFilePath, invResponseVOs);
		
		Type listType = new TypeToken<List<InvResponseVO>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		String json = gson.toJson(invResponseVOs, listType);
		System.out.println("getInvResponseVOsAndRecoredTest:" + json);
	}
	
	public static void getOrderResponseVOsAndRecoredTest() {
		String configFilePath = "D:\\test1.xml";
		
		FTP FTP = new FTP("192.168.112.164", 22, "mysqlmove", "admin123", "/home/mysqlmove/kevin_order_test");

		List<OrderResponseVO> orderResponseVOs = FTP.getOrderResponseVOs(configFilePath);

		FTP.recordLastOrderResponseVO(configFilePath, orderResponseVOs);

		Type listType = new TypeToken<List<OrderResponseVO>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd HH:mm:ss").create();
		String json = gson.toJson(orderResponseVOs, listType);
		System.out.println("getOrderResponseVOsAndRecoredTest:" + json);
	}
}
