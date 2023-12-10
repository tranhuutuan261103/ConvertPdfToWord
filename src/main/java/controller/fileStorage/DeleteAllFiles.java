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
 * Servlet implementation class DeleteAllFiles
 */
@WebServlet("/fileStorage/DeleteAllFiles")
public class DeleteAllFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAllFiles() {
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
		
		String par = (String) request.getParameter("deleteIdAll");
		
		String[] listId = par.split(",");
		if (listId.length == 0)
		{
			response.sendRedirect("../home/managefile.jsp");
			return;
		}
		
		FileStorageBO bo = new FileStorageBO();
		for (String id : listId) {
			FileStorageVM fileStorageVM = bo.getFileById(id, email);
			
			if (fileStorageVM != null)
			{
				// Delete file
				PdfToWordConverter PTW_bo = new PdfToWordConverter();
			    PTW_bo.deleteFile(id, email);
			}
			else {
				System.out.println("Can't delete file having id: " + id);
			}
		}
		
		response.sendRedirect("../fileStorage/GetAllFile");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
