package model.BO;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DriveService {
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private final NetHttpTransport HTTP_TRANSPORT;

    public DriveService() throws GeneralSecurityException, IOException {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    }

    private Credential getCredentials() throws Exception {
        InputStream in = getClass().getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Drive getDriveService() throws Exception {
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void listFiles() throws IOException {
        Drive service = null;
		try {
			service = getDriveService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }

    public void uploadFile(String fileName, String filePath) throws IOException {
        Drive service = null;
		try {
			service = getDriveService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList("1kgkdGMr4koast-x2b4qis33QVLoxofnM"));

        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);
        ByteArrayContent content = new ByteArrayContent("application/octet-stream", fileContent);

        File uploadedFile = service.files().create(fileMetadata, content)
                .setFields("id")
                .execute();

        System.out.println("File ID: " + uploadedFile.getId());
    }
    
    public String uploadFile(String fileName, ByteArrayOutputStream outputStream) throws IOException {
    	Drive service = null;
		try {
			service = getDriveService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList("1kgkdGMr4koast-x2b4qis33QVLoxofnM"));

        // Convert DataOutputStream to ByteArrayContent
        ByteArrayContent content = new ByteArrayContent("application/octet-stream", outputStream.toByteArray());

		// Create the file on Google Drive
        File uploadedFile = service.files().create(fileMetadata, content)
                .setFields("id")
                .execute();

        // Print the ID of the newly created file
        return uploadedFile.getId();
    }
    
    public void downloadFile(String fileId, String destinationPath) throws IOException {
        Drive service = null;
        try {
            service = getDriveService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream outputStream = new FileOutputStream(destinationPath);

        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        
        System.out.println("File downloaded successfully to: " + destinationPath);
    }
    
    public static String getDefaultDownloadPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return System.getProperty("user.home") + "\\Downloads\\";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return System.getProperty("user.home") + "/Downloads/";
        } else {
            return System.getProperty("user.home") + "/";
        }
    }
}
