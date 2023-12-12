package controller.fileStorage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.services.drive.Drive;

import model.BO.DriveService;
import model.BO.FileStorageBO;
import model.BO.PdfToWordConverter;
import model.Bean.FileStorageVM;

/**
 * Servlet implementation class DownloadFile
 */
@WebServlet("/fileStorage/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFile() {
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
        if (email == null || email.isEmpty()) {
            response.sendRedirect("../home/index.jsp");
            return;
        }

        String fileId = request.getParameter("downloadId");
        System.out.println(fileId);

        FileStorageBO bo = new FileStorageBO();
        FileStorageVM fileStorageVM = bo.getFileById(fileId, email);

        if (fileStorageVM != null) {
            try {
                DriveService driveService = new DriveService();
                Drive service = driveService.getDriveService();

                // Set response headers
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileStorageVM.getFileName() + "\"");

                // Download file from Google Drive and stream it to the response output stream
                service.files().get(fileStorageVM.getId())
                        .executeMediaAndDownloadTo(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading file: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("File not found or user not authorized.");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found or user not authorized.");
            return;
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

class DownloadFileRunnable implements Runnable {
	 private FileStorageVM fileStorageVM;

	 public DownloadFileRunnable(FileStorageVM fileStorageVM) {
	     this.fileStorageVM = fileStorageVM;
	 }

	 @Override
	 public void run() {
	     PdfToWordConverter PTW_bo = new PdfToWordConverter();
	     PTW_bo.downloadFile(fileStorageVM);
	 }
}
