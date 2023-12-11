package model.Bean;

import java.sql.Timestamp;

public class FileStorageVM {
	private String id;
	private String fileName;
	private Timestamp creationDate;
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
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public FileStorageVM(String id, String fileName, Timestamp creationDate) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.creationDate = creationDate;
	}
}
