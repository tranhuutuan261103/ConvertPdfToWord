package controller.fileStorage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BO.FileStorageBO;
import model.BO.PdfToWordConverter;
import model.Bean.FileStorageVM;

/**
 * Servlet implementation class DeleteFile
 */
@WebServlet("/fileStorage/DeleteFile")
public class DeleteFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Kiem tra login hay chua
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("username");
		if (email == null || email.isEmpty() == true) {
			response.sendRedirect("../home/index.jsp");
			return;
		}
		
		String id = (String) request.getParameter("id");
		
		FileStorageBO bo = new FileStorageBO();
		FileStorageVM fileStorageVM = bo.getFileById(id, email);
		
		if (fileStorageVM != null)
		{
			// Create thread to convert file
			Thread thread = new Thread(new DeleteFileRunnable(id, email));
			thread.start();
		}
		else {
			System.out.println("Not oke");
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

}

class DeleteFileRunnable implements Runnable {
	 private String idFile;
	 private String email;

	 public DeleteFileRunnable(String idFile, String email) {
	     this.idFile = idFile;
	     this.email = email;
	 }

	 @Override
	 public void run() {
	     PdfToWordConverter PTW_bo = new PdfToWordConverter();
	     PTW_bo.deleteFile(idFile, email);
	 }
}
