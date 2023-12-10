package model.BO;

import model.DAO.AccountDAO;
import model.Bean.Account;


public class AccountBO {
	
	public boolean Authentication(Account request) {
		AccountDAO dao = new AccountDAO();
		return dao.Authentication(request);
	}
	
	public boolean Register(Account request) {
		// TODO Handler login here.
		
		AccountDAO dao = new AccountDAO();
		return dao.Register(request);
	}
}
