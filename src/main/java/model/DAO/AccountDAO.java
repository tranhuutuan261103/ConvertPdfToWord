package model.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Bean.Account;

import model.*;

public class AccountDAO {
	
	public boolean Authentication(Account request) {
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
	
	public boolean Register(Account request) {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }

	        System.out.println(request.getEmail() + " - " + request.getPassword());
	        String checkQuery = "SELECT * FROM users WHERE email = ?";
	        ps = con.prepareStatement(checkQuery);
	        ps.setString(1, request.getEmail());
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            return false;
	        }

	        rs.close();
	        ps.close();

	        String insertQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
	        ps = con.prepareStatement(insertQuery);
	        ps.setString(1, request.getEmail());
	        ps.setString(2, request.getPassword()); 
	        int affectedRows = ps.executeUpdate();

	        return affectedRows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

}
