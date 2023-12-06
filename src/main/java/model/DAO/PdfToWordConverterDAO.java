package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DatabaseConnection;
import model.Bean.ConvertContentRequest;

public class PdfToWordConverterDAO {
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
}
