package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DatabaseConnection;
import model.Bean.ConvertContentRequest;
import model.Bean.FileStorageVM;

public class FileStorageDAO {
	public ArrayList<FileStorageVM> getAllFile(String email){
		ArrayList<FileStorageVM> list = new ArrayList<>();
		Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }
	        String query = "SELECT id, name FROM convertcontents WHERE email = ?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, email);
	        rs = ps.executeQuery();
	        
	        if (rs == null) {
	        	return list;
	        }
	        
	        while (rs.next()) {
	        	FileStorageVM fileStorageVM = new FileStorageVM(rs.getString(1), rs.getString(2));
	        	list.add(fileStorageVM);
	        }
	        return list;
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
	    
		return list;
	}
	
	public FileStorageVM getFileById(String id, String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }
	        String query = "SELECT id, name FROM convertcontents WHERE id = ? and email = ?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, id);
	        ps.setString(2, email);
	        rs = ps.executeQuery();
	        
	        if (rs.next() == false) {
	        	return null;
	        }
	        
	        return new FileStorageVM(rs.getString(1), rs.getString(2));

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
		
		return null;
	}
	
	public boolean SaveContent(ConvertContentRequest request) {
		Connection con = null;
	    PreparedStatement ps = null;
	    boolean rs = false;

	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }
	        String query = "INSERT INTO convertcontents (id, email, name) VALUES(?, ?, ?)";
	        ps = con.prepareStatement(query);
	        ps.setString(1, request.getId());
	        ps.setString(2, request.getEmail());
	        ps.setString(3, request.getFileName());
	        rs = ps.execute();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
		return rs;
	}
	
	public boolean deleteFileById(String id, String email) {
	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }

	        String query = "DELETE FROM convertcontents WHERE id = ? AND email = ?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, id);
	        ps.setString(2, email);

	        // Use executeUpdate() for DELETE queries
	        int rowsAffected = ps.executeUpdate();

	        // Check if any rows were affected
	        if (rowsAffected == 0) {
	            return false; // No rows were deleted
	        }

	        return true; // Successfully deleted

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }

	    return false; // Return false in case of an exception
	}
}
