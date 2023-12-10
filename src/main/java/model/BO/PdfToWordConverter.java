package model.BO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.UUID;

import com.aspose.pdf.Document;

import com.aspose.pdf.DocSaveOptions;
import model.Bean.ConvertContentRequest;
import model.Bean.ConvertRequest;
import model.Bean.FileStorageVM;

public class PdfToWordConverter {
	public void convertPdfToWord(ConvertRequest request) {
		try {
			Path tempPdfFile = Files.createTempFile("temp", ".pdf");
			Files.copy(request.getFileContent(), tempPdfFile, StandardCopyOption.REPLACE_EXISTING);
			
			// Open PDF source file
			Document pdfDocument = new Document(tempPdfFile.toString());
			
			// Convert PDF to DOCX
			DocSaveOptions saveOptions = new DocSaveOptions();
			saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);

            String fileName_docx = extractFileNameWithoutExtension(request.getFileName()) + "-" + generateUniqueId() + ".docx";

            // Save data after convert
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfDocument.save(byteArrayOutputStream, saveOptions);
            
            // Save the ByteArrayOutputStream content to Word file
            Files.write(Paths.get(DownloadManager.getDefaultDownloadPath(), fileName_docx), byteArrayOutputStream.toByteArray());
            System.out.println("Save file to local successfully at " + DownloadManager.getDefaultDownloadPath()
                    + fileName_docx + " (in model.BO.PdfToWordConverter)");
            
			if (request.getUsername() != null && request.getUsername().isEmpty() == false) {
				String idContent = "-1";
				
				// Upload file to drive
				if (byteArrayOutputStream != null) {
					DriveService driveService = new DriveService();
					idContent = driveService.uploadFile(fileName_docx, byteArrayOutputStream);
					byteArrayOutputStream.close();
					System.out.println("Save file to drive successfully (in model.BO.PdfToWordConverter)");
				}
				
				// Save id of file on drive to database
				if (idContent.equals("-1") == false) {
					ConvertContentRequest cc_request = new ConvertContentRequest(idContent, request.getUsername(), fileName_docx);
					FileStorageBO fileStorageBO = new FileStorageBO();
					fileStorageBO.SaveContent(cc_request);
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

	public void downloadFile(FileStorageVM fileStorageVM) {
		try {
			DriveService driveService = new DriveService();
			driveService.downloadFile(fileStorageVM.getId(), fileStorageVM.getFileName());
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteFile(String idFile, String email) {
		try {
			DriveService driveService = new DriveService();
			driveService.deleteFile(idFile);
			FileStorageBO bo = new FileStorageBO();
			bo.deleteFileById(idFile, email);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}