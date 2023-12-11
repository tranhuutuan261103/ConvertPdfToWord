package model.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Bean.Account;

import model.*;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	        String query = "SELECT password FROM users WHERE email = ?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, request.getEmail());
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            String storedPassword = rs.getString("password");
	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            exists = passwordEncoder.matches(request.getPassword(), storedPassword);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
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

	        String checkQuery = "SELECT * FROM users WHERE email = ?";
	        ps = con.prepareStatement(checkQuery);
	        ps.setString(1, request.getEmail());
	        rs = ps.executeQuery();
	        if (rs.next()) {
	            return false;
	        }

	        rs.close();
	        ps.close();

	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String hashedPassword = passwordEncoder.encode(request.getPassword());

	        String insertQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
	        ps = con.prepareStatement(insertQuery);
	        ps.setString(1, request.getEmail());
	        ps.setString(2, hashedPassword);
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
	
	public boolean changePassword(Account request) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DatabaseConnection.getConnection();
            if (con == null) {
                throw new SQLException("Khong the ket noi");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(request.getPassword());

            String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, hashedPassword);
            ps.setString(2, request.getEmail());
            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
