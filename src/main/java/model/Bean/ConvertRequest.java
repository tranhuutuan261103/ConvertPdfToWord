package model.Bean;

import java.io.InputStream;

public class ConvertRequest {
	private String fileName;
	private InputStream fileContent;
	
	public ConvertRequest(String fileName, InputStream fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
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
