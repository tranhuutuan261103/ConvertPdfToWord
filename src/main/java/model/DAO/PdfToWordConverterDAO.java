package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseConnection;

public class PdfToWordConverterDAO {
	public boolean SaveContent(String idUser, String idContent) {
		Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        con = DatabaseConnection.getConnection();
	        if (con == null) {
	            throw new SQLException("Khong the ket noi");
	        }
	        String query = "INSERT INTO emailcontents (email, content) VALUES(?, ?)";
	        ps = con.prepareStatement(query);
	        ps.setString(1, idUser);
	        ps.setString(2, idContent);
	        rs = ps.executeQuery();
	        return true;
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
		return false;
	}
}
