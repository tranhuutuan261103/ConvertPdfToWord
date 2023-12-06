package model.BO;

public class DownloadManager {
	public static String getDefaultDownloadPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
        	// He dieu hanh Windows
            return System.getProperty("user.home") + "\\Downloads\\";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
        	// He dieu hanh Unix/Linux hoac macOS
            return System.getProperty("user.home") + "/Downloads/";
        } else {
        	// Truong hop khong xac dinh, tra ve thu muc nguoi dung
            return System.getProperty("user.home") + "/";
        }
    }
}
