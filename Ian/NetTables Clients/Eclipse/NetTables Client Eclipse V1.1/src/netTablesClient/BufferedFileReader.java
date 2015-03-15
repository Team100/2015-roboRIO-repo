package netTablesClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BufferedFileReader {

	private static FileInputStream inStream;
	private static BufferedReader bReader;
	private static String line;
	private static List<String> fileArr;
	private static File file;

	public static List<String> fileToArrList(File inFile) throws IOException {

		if (inFile.exists()) {
			line = null;
			fileArr = new ArrayList<String>();

			inStream = new FileInputStream(inFile);
			bReader = new BufferedReader(new InputStreamReader(inStream));

			while ((line = bReader.readLine()) != null) {
				fileArr.add(line);
			}

			bReader.close();

			return fileArr;
		} else {
			System.out.println("BufferedFileReader: File does not exist!");
			return null;
		}
	}

	public static List<String> fileToArrList(String filePath) throws IOException {

		file = new File(filePath);
		
		if (file.exists()) {
			line = null;
			fileArr = new ArrayList<String>();

			inStream = new FileInputStream(file);
			bReader = new BufferedReader(new InputStreamReader(inStream));

			while ((line = bReader.readLine()) != null) {
				fileArr.add(line);
			}

			bReader.close();

			return fileArr;
		} else {
			System.out.println("BufferedFileReader: File does not exist!");
			return null;
		}
	}

	public static void printFileArr(List<String> fileArrList) {

		for (int i = 0; i < fileArrList.size() - 1; i++) {
			System.out.println(fileArrList.get(i));
		}
	}
}