package model.Bean;

import java.io.InputStream;

public class ConvertRequest {
	private String username;
	private String fileName;
	private InputStream fileContent;
	
	public ConvertRequest(String username, String fileName, InputStream fileContent) {
		super();
		this.username = username;
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getFileContent() {
		return fileContent;
	}
	public void setFileContent(InputStream fileContent) {
		this.fileContent = fileContent;
	}
}
