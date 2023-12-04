package model.BO;

import model.DAO.AccountDAO;
import model.DTO.LoginRequest;
import model.DTO.RegisterRequest;

public class AccountBO {
	
	public boolean Authentication(LoginRequest request) {
		AccountDAO dao = new AccountDAO();
		return dao.Authentication(request);
	}
	
	public boolean Register(RegisterRequest request) {
		// TODO Handler login here.
		
		return new AccountDAO().Register(request);
	}
}
