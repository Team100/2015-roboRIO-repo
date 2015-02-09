package smith.it.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A buffered csv file writer class.
 * 
 * @author Ian Smith
 *
 */
public class BufferedCsvWriter {

	BufferedFileWriter bFWriter;
	List<String> csvHeader;
	List<String> csvData;
	private int cycle = 1;
	private String arrStr;

	/**
	 * The Writer's Constructor.
	 * 
	 * @param file
	 *            The file to be written to.
	 */
	BufferedCsvWriter(File file) {

		bFWriter = new BufferedFileWriter(file);
		csvHeader = new ArrayList<String>();
		csvData = new ArrayList<String>();
		status("Ready!");
	}

	/**
	 * Puts data in the arrays.
	 * 
	 * @param key
	 *            The "key" to access the data.
	 * @param data
	 *            The value being stored.
	 */
	public void putData(String key, String data) {
		status("Key: " + key + " Equals: " + data);
		if (cycle == 1) {
			csvHeader.add(key);
		}
		csvData.add(data);
	}

	/**
	 * Gets data from the arrays.
	 * 
	 * @param key
	 *            The key to access the data.
	 * @return Return the key's data.
	 */
	public String getData(String key) {
		return csvData.get(csvHeader.indexOf(key));
	}

	/**
	 * Writes and flushes data to file.
	 */
	public void writeFlush() {
		if (cycle == 1) {
			status("Writing Header!");
			arrStr = Arrays.toString(csvHeader.toArray());
			bFWriter.writeLine(arrStr.substring(1, arrStr.length() - 1));
			status("Header Written!");
		}
		status("Writing Data!");
		arrStr = Arrays.toString(csvData.toArray());
		bFWriter.writeLine(arrStr.substring(1, arrStr.length() - 1));
		bFWriter.flush();
		status("Data Written!");
		// Resets csvData.
		csvData.clear();
		cycle++;
	}

	/**
	 * Closes the writer.
	 */
	public void close() {
		status("Closing!");
		bFWriter.close();
		status("Done!");
	}

	/**
	 * A private status command.
	 * 
	 * @param status
	 *            Status message.
	 */
	private void status(String status) {
		System.out.println("BufferedCsvWriter: " + status);
	}
}