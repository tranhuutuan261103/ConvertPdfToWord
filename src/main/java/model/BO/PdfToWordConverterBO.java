package model.BO;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import model.Bean.ConvertContentRequest;
import model.Bean.ConvertRequest;
import model.DAO.PdfToWordConverterDAO;

public class PdfToWordConverterBO {
	public void convertPdfToWord(ConvertRequest request) {
		try {
			PdfDocument doc = new PdfDocument();
			doc.loadFromStream(request.getFileContent());
			doc.getConvertOptions().setConvertToWordUsingFlow(true);
			String fileName_docx = extractFileNameWithoutExtension(request.getFileName()) + "-" + generateUniqueId() + ".docx";
			
			// Convert PDF file to Word file and save to local
			doc.saveToFile(DownloadManager.getDefaultDownloadPath() + fileName_docx, FileFormat.DOCX);
			System.out.println("Save file to local successfully at " + DownloadManager.getDefaultDownloadPath() + fileName_docx + " (in model.BO.PdfToWordConverterBO)");
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// Save data after convert
			doc.saveToStream(byteArrayOutputStream);
			
			if (request.getUsername() != null && request.getUsername().isEmpty() == false) {
				DriveService driveService = new DriveService();
				String idContent = "-1";
				
				// Upload file to drive
				if (byteArrayOutputStream != null) {
					idContent = driveService.uploadFile(fileName_docx, byteArrayOutputStream);
					byteArrayOutputStream.close();
					System.out.println("Save file to drive successfully (in model.BO.PdfToWordConverterBO)");
				}
				
				// Save id of file on drive to database
				if (idContent.equals("-1") == false) {
					ConvertContentRequest cc_request = new ConvertContentRequest(idContent, request.getUsername(), fileName_docx);
					PdfToWordConverterDAO PTW_dao = new PdfToWordConverterDAO();
					PTW_dao.SaveContent(cc_request);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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