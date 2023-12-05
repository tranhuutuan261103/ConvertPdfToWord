package model.BO;

import java.util.UUID;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import model.Bean.ConvertRequest;

public class PdfToWordConverterBO {
	private String pathRoot = "D:\\ServerStoreWord\\";
	
	public boolean convertPdfToWord(ConvertRequest request) {
		PdfDocument doc = new PdfDocument();
		doc.loadFromStream(request.getFileContent());
		doc.getConvertOptions().setConvertToWordUsingFlow(true);
		String fileName_docx = extractFileNameWithoutExtension(request.getFileName()) + "-" + generateUniqueId() + ".docx";
		doc.saveToFile(pathRoot + fileName_docx, FileFormat.DOCX);
		return true;
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