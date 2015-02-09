package smith.it.utils;

import java.io.File;
import java.io.IOException;

/**
 * A simple file creator.
 * 
 * @author Ian Smith
 *
 */
public class FileMaker {

	// A list of common directories.
	public static final String C_DIR = "C:/";
	public static final String PUBLIC_DIR = "C:/Users/Public/";
	public static final String PUBLIC_DOC_DIR = "C:/Users/Public/Documents/";
	public static final String USER_DIR = System.getProperty("user.home")
			.replace("\\", "/") + "/";
	public static final String USER_DOC_DIR = System.getProperty("user.home")
			.replace("\\", "/") + "/Documents/";

	// A list of common file types.
	public static final String TXT_FILE = ".txt";
	public static final String ODF_FILE = ".odt";
	public static final String CSV_FILE = ".csv";

	private File file;

	/**
	 * Constructs the file maker.
	 * 
	 * @param path
	 *            The path to the file.
	 * @param name
	 *            The name of the file.
	 * @param extension
	 *            The file extension.
	 */
	FileMaker(String path, String name, String extension) {
		file = new File(path + name + extension);
		status("Ready!");
	}

	/**
	 * Constructs the file maker.
	 * 
	 * @param pathNameAndExtension
	 *            The path, name, and extension.
	 */
	FileMaker(String pathNameAndExtension) {
		file = new File(pathNameAndExtension);
		status("Ready!");
	}

	/**
	 * Checks if the file exists.
	 * 
	 * @return
	 */
	public boolean fileExists() {
		status("Checking Existance!");
		if (file.exists()) {
			status("File Exists!");
			return true;
		} else {
			status("File Doesn't Exist!");
			return false;
		}
	}

	/**
	 * Makes the File.
	 * 
	 * @param overwrite
	 *            Tells whether the file can overwrite.
	 */
	public void makeFile(boolean overwrite) {
		status("Creating File " + file.toString() + "!");
		if (overwrite) {
			try {
				file.createNewFile();
				status("Created File!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (!file.exists()) {
			try {
				file.createNewFile();
				status("Created File!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			status("Creation Failed!");
		}
	}
	/**
	 * Makes a folder.
	 */
	public void makeFolder() {
		status("Creating Folder " + file.toString() + "!");
		file.mkdir();
		status("Created Folder!");
	}

	/**
	 * Makes all the folders in a path.
	 */
	public void makeFoldersInPath() {
		status("Creating All Folders In Path " + file.toString() + "!");
		file.mkdirs();
		status("Created Folder!");
	}

	/**
	 * Gets the file.
	 * 
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Gets the absolute file.
	 * 
	 * @return Returns the absolute file.
	 */
	public File getAbsFile() {
		return file.getAbsoluteFile();
	}

	/**
	 * A private status command.
	 * 
	 * @param status
	 *            Status message.
	 */
	private void status(String status) {
		System.out.println("FileMaker: " + status);
	}
}