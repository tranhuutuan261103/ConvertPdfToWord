package controller.home;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.BO.PdfToWordConverter;
import model.Bean.ConvertRequest;

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
		System.out.println("Called PdfToWord controller");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		if (filePart != null) {
			// Extract file name and extension from Content-Disposition header
		    String contentDispositionHeader = filePart.getHeader("Content-Disposition");
		    String fileName = extractFileName(contentDispositionHeader);
			
		    // Get data of file
		    InputStream fileContent = filePart.getInputStream();
			
		    // Create thread to convert file
			ConvertRequest convertRequest = new ConvertRequest(username, fileName, fileContent);
			Thread conversionThread = new Thread(new ConversionRunnable(convertRequest));
	        conversionThread.start();
		}
		
		response.sendRedirect("../home/index-login.jsp");
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
}

class ConversionRunnable implements Runnable {
	private ConvertRequest convertRequest;
	
	public ConversionRunnable(ConvertRequest convertRequest) {
	    this.convertRequest = convertRequest;
	}
	
	@Override
	public void run() {
	    PdfToWordConverter PTW_bo = new PdfToWordConverter();
	    PTW_bo.convertPdfToWord(convertRequest);
	}
}