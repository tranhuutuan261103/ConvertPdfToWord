package model.Bean;

public class ConvertContentRequest {
	private String id;
	private String email;
	private String fileName;
	public ConvertContentRequest(String id, String email, String fileName) {
		super();
		this.id = id;
		this.email = email;
		this.fileName = fileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
