package controller.fileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.services.drive.Drive;

import model.BO.DriveService;
import model.BO.FileStorageBO;
import model.Bean.FileStorageVM;

/**
 * Servlet implementation class DownloadAllFiles
 */
@WebServlet("/fileStorage/DownloadAllFiles")
public class DownloadAllFiles extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiem tra login hay chua
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("username");
        if (email == null || email.isEmpty()) {
            response.sendRedirect("../home/index.jsp");
            return;
        }

        String par = (String) request.getParameter("downloadIdAll");

        String[] listId = par.split(",");

        FileStorageBO bo = new FileStorageBO();

        // Create a temporary directory within the servlet context
        ServletContext context = getServletContext();
        File tempDirectory = new File(context.getRealPath("/WEB-INF/tempDownloads"));
        if (!tempDirectory.exists()) {
            tempDirectory.mkdirs();
        }

        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (String id : listId) {
                FileStorageVM fileStorageVM = bo.getFileById(id, email);

                if (fileStorageVM != null) {
                    try {
                    	DriveService driveService = new DriveService(); // Assuming you have a DriveService class
                        Drive service = driveService.getDriveService();

                        // Create a temporary file for each downloaded file
                        File tempFile = new File(tempDirectory, fileStorageVM.getFileName());

                        // Download file from Google Drive and write it to the temporary file
                        try (FileOutputStream fileOut = new FileOutputStream(tempFile)) {
                            service.files().get(fileStorageVM.getId()).executeMediaAndDownloadTo(fileOut);
                        }

                        // Add the file to the zip archive
                        zipOut.putNextEntry(new ZipEntry(fileStorageVM.getFileName()));
                        
                        // Write the file contents to the zip stream
                        try (InputStream fileIn = new FileInputStream(tempFile)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = fileIn.read(buffer)) != -1) {
                                zipOut.write(buffer, 0, bytesRead);
                            }
                        }

                        // Close the temporary file
                        tempFile.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error downloading file with ID: " + fileStorageVM.getId() + ", Error: " + e.getMessage());
                        // Log or handle the error as needed
                    }
                } else {
                    System.out.println("Can't download file having id: " + id);
                }
            }
        }

        // Set response headers for the zip file
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"downloaded_files.zip\"");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
