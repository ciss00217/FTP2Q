package tw.com.ftp2q.test;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.io.IOUtils; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException;
import java.net.SocketException;
import java.io.FileOutputStream; 
public class FtpTest {

    public static void main(String[] args) throws SocketException, IOException { 
    	test();
       // testDownload(); 
    	/*String [] a = new String[10];
    	
    	System.out.println(a[9]);;*/
    	
    } 
    public static void test() throws SocketException, IOException {
    	 String server = "192.168.112.164"; 
       
         FTPSClient ftp = new FTPSClient(); 
         ftp.connect(server,22); 
        // int reply = ftp.getReplyCode(); 
       //  System.out.println(FTPReply.isPositiveCompletion(reply)); 
         System.out.println(ftp.login("mysqlmove", "admin123")); 
         ftp.disconnect();
    } 
    
    
    /** 
     * FTP上傳單個檔測試 
     */ 
    public static void testUpload() { 
    	FTPSClient  ftpClient = new FTPSClient(); 
        FileInputStream fis = null; 
        try { 
            ftpClient.connect("192.168.112.164"); 
            ftpClient.login("mysqlmove", "admin123"); 
            File srcFile = new File("D:\\test.xml"); 
            fis = new FileInputStream(srcFile); 
            //設置上傳目錄 
            ftpClient.changeWorkingDirectory("/home/mysqlmove"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //設置檔案類型（二進位） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("test.xml", fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP用戶端出錯！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("關閉FTP連接發生異常！", e); 
            } 
        } 
    } 
    /** 
     * FTP下載單個文件測試 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 
        try { 
            ftpClient.connect("192.168.14.117"); 
            ftpClient.login("admin", "123"); 
            String remoteFileName = "/admin/pic/3.gif"; 
            fos = new FileOutputStream("c:/down.gif"); 
            ftpClient.setBufferSize(1024); 
            //設置檔案類型（二進位） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.retrieveFile(remoteFileName, fos); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP用戶端出錯！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("關閉FTP連接發生異常！", e); 
            } 
        } 
    } 
}
