package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Bean.LoginRequest;
import model.Bean.RegisterRequest;
import model.*;

public class AccountDAO {
	
	public boolean Authentication(LoginRequest request) {
		Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    boolean exists = false;

	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }
	        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, request.getEmail());
	        ps.setString(2, request.getPassword());
	        rs = ps.executeQuery();
	        exists = rs.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return exists;
	}
	
	public boolean Register(RegisterRequest request) {
		// TODO Handler login here.
		
		return true;
	}
}
