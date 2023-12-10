package model.BO;

import java.util.ArrayList;

import model.Bean.ConvertContentRequest;
import model.Bean.FileStorageVM;
import model.DAO.FileStorageDAO;

public class FileStorageBO {
	public ArrayList<FileStorageVM> getAllFile(String email){
		FileStorageDAO dao = new FileStorageDAO();
		return dao.getAllFile(email);
	}
	
	public FileStorageVM getFileById(String id, String email) {
		FileStorageDAO dao = new FileStorageDAO();
		return dao.getFileById(id, email);
	}
	
	public boolean deleteFileById(String id, String email) {
		FileStorageDAO dao = new FileStorageDAO();
		return dao.deleteFileById(id, email);
	}

	public boolean SaveContent(ConvertContentRequest cc_request) {
		FileStorageDAO dao = new FileStorageDAO();
		return dao.SaveContent(cc_request);
	}
	
	public int deleteAllFilesById(String[] listId, String email) {
		FileStorageDAO dao = new FileStorageDAO();
		return dao.deleteAllFilesById(listId, email);
	}
}
