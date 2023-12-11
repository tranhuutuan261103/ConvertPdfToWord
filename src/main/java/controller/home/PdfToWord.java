package controller.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
		try {
			Collection<Part> fileParts = request.getParts();
			System.out.println("Called PdfToWord controller");
			
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("username");
			
			if (fileParts != null && !fileParts.isEmpty()) {
		        List<ConvertRequest> convertRequests = new ArrayList<>();
	
		        // Loop through each file part and create ConvertRequest
		        for (Part filePart : fileParts) {
		            // Extract file name and extension from Content-Disposition header
		            String contentDispositionHeader = filePart.getHeader("Content-Disposition");
		            String fileName = extractFileName(contentDispositionHeader);
		            
		            String uniqueId = generateUniqueId();
		            String generatedFileName = extractFileNameWithoutExtension(fileName) + "-" + uniqueId;
	
		            // Get data of file
		            InputStream fileContent = filePart.getInputStream();
	
		            // Create ConvertRequest and add to the list
		            if (fileContent != null) {
			            ConvertRequest convertRequest = new ConvertRequest(username, generatedFileName, fileContent);
			            convertRequests.add(convertRequest);
		            }
		            else {
		            	System.out.println("InputStream of " + generatedFileName + " is null");
		            }
		        }
	
		        // Create threads to convert files
		        List<Thread> conversionThreads = new ArrayList<>();
	
		        for (ConvertRequest convertRequest : convertRequests) {
		            Thread conversionThread = new Thread(new ConversionRunnable(convertRequest));
		            conversionThreads.add(conversionThread);
		            conversionThread.start();
		        }
	
		        for (Thread conversionThread : conversionThreads) {
		            try {
		                conversionThread.join();
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		        
		        response.sendRedirect("../home/index-login.jsp");
	
		    } else {
		        response.sendRedirect("../home/index.jsp");
		    }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
	
	private String generateUniqueId() {
		UUID id = UUID.randomUUID();
		return id.toString();
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