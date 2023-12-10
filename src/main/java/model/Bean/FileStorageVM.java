package model.Bean;

import java.sql.Date;

public class FileStorageVM {
	private String id;
	private String fileName;
	private Date creationDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public FileStorageVM(String id, String fileName, Date creationDate) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.creationDate = creationDate;
	}
}
