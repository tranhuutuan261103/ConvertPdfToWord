package controller.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

/**
 * Servlet implementation class PdfToWord
 */
@MultipartConfig(
    location = "",
    maxFileSize = 20848820L,  // Set the maximum file size
    maxRequestSize = 418018841L,  // Set the maximum request size
    fileSizeThreshold = 1048576  // Set the size threshold for storing items in memory
)
@WebServlet("/home/PdfToWord")
public class PdfToWord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String pathRoot = "D:\\ServerStoreWord\\";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdfToWord() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("myfile");
		
		// Extract file name and extension from Content-Disposition header
	    String contentDispositionHeader = filePart.getHeader("Content-Disposition");
	    String fileName = extractFileName(contentDispositionHeader);
		
		InputStream fileContent = filePart.getInputStream();
		
		PdfDocument doc = new PdfDocument();
		doc.loadFromStream(fileContent);
		doc.getConvertOptions().setConvertToWordUsingFlow(true);
		String fileName_docx = extractFileNameWithoutExtension(fileName) + "-" + generateUniqueId() + ".docx";
		doc.saveToFile(pathRoot + fileName_docx, FileFormat.DOCX);
		
		response.sendRedirect("../home/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String extractFileName(String contentDispositionHeader) {
	    String[] elements = contentDispositionHeader.split(";");
	    for (String element : elements) {
	        if (element.trim().startsWith("filename")) {
	            // Extract and return the file name with extension
	            return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    // If no filename information found in the header, generate a default name or handle accordingly
	    return "defaultFileName"; 
	}
	
	private String extractFileNameWithoutExtension(String filePath) {
	    // Remove the file extension
	    int dotIndex = filePath.lastIndexOf('.');
	    String fileName = "default";
	    if (dotIndex > 0) {
	    	fileName = filePath.substring(0, dotIndex);
	    }

	    return fileName;
	}
	
	private String generateUniqueId() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}

}
