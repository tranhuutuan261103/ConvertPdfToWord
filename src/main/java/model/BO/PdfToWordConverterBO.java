package model.BO;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import model.Bean.ConvertRequest;
import model.DAO.PdfToWordConverterDAO;

public class PdfToWordConverterBO {
	private String pathRoot = "D:\\ServerStoreWord\\";
	
	public void convertPdfToWord(ConvertRequest request) {
		try {
			PdfDocument doc = new PdfDocument();
			doc.loadFromStream(request.getFileContent());
			doc.getConvertOptions().setConvertToWordUsingFlow(true);
			String fileName_docx = extractFileNameWithoutExtension(request.getFileName()) + "-" + generateUniqueId() + ".docx";
			doc.saveToFile(pathRoot + fileName_docx, FileFormat.DOCX);
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			doc.saveToStream(byteArrayOutputStream);
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));

			DriveService driveService = new DriveService();
			String idContent = "-1";
			if (dos != null) {
				idContent = driveService.uploadFile(dos);
			    dos.close();
			}
			
			if (idContent.equals("-1") == false) {
				PdfToWordConverterDAO PTW_dao = new PdfToWordConverterDAO();
				PTW_dao.SaveContent("example1@emailify.shop", idContent);
				System.out.println("Oke");
			}
			System.out.println("Not oke");
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