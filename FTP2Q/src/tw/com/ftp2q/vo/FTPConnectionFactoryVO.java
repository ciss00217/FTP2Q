package tw.com.ftp2q.vo;

public class FTPConnectionFactoryVO {
	
	private String username;
	private String password;
	private String host;
	private int port;
	private String fileDirectory;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getFileDirectory() {
		return fileDirectory;
	}
	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

}
