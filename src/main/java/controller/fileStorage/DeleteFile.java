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
		
		String id = (String) request.getParameter("deleteId");
		
		FileStorageBO bo = new FileStorageBO();
		FileStorageVM fileStorageVM = bo.getFileById(id, email);
		
		if (fileStorageVM != null)
		{
			// Delete file
			PdfToWordConverter PTW_bo = new PdfToWordConverter();
		    PTW_bo.deleteFile(id, email);
		}
		else {
			System.out.println("Not oke");
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