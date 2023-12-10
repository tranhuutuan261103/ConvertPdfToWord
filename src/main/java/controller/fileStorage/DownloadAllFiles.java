package controller.fileStorage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.BO.FileStorageBO;
import model.Bean.FileStorageVM;

/**
 * Servlet implementation class DownloadAllFiles
 */
@WebServlet("/fileStorage/DownloadAllFiles")
public class DownloadAllFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadAllFiles() {
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
		
		String par = (String) request.getParameter("downloadIdAll");
		
		String[] listId = par.split(",");
		
		FileStorageBO bo = new FileStorageBO();
		for (String id : listId) {
			FileStorageVM fileStorageVM = bo.getFileById(id, email);
			
			if (fileStorageVM != null)
			{
				// Create thread to download file
				Thread thread = new Thread(new DownloadFileRunnable(fileStorageVM));
				thread.start();
			}
			else {
				System.out.println("Can't download file having id: " + id);
			}
		}
		
		response.sendRedirect("../home/managefile.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
