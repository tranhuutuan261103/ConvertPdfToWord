package model.DAO;

import model.DTO.LoginRequest;
import model.DTO.RegisterRequest;

public class AccountDAO {
	
	public boolean Authentication(LoginRequest request) {
		if (request.getEmail().equals("admin") && request.getPassword().equals("admin"))
			return true;
		
		return false;
	}
	
	public boolean Register(RegisterRequest request) {
		// TODO Handler login here.
		
		return true;
	}
}
