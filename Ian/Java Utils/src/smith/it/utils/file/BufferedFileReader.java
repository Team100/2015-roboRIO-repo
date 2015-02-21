package smith.it.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BufferedFileReader {

	static FileInputStream inStream;
	static BufferedReader bReader;
	static String line;
	static List<String> fileArr;

	public static List<String> fileToArrList(File file) throws IOException {

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