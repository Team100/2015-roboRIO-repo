package smith.it.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A buffered file writer class.
 * 
 * @author Ian Smith
 *
 */
public class BufferedFileWriter {

	private FileWriter fWriter;
	private BufferedWriter bWriter;

	/**
	 * The writer's constructor.
	 * 
	 * @param file
	 *            The file that will be written to.
	 */
	BufferedFileWriter(File file) {
		try {
			fWriter = new FileWriter(file.getAbsoluteFile());
			bWriter = new BufferedWriter(fWriter);
			status("Ready!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes to file.
	 * 
	 * @param str
	 *            Message to be written.
	 */
	public void write(String str) {
		status("Writing \"" + str + "\" To File");
		try {
			bWriter.write(str);
			status("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes to file and writes a new line.
	 * 
	 * @param str
	 *            Message to be written.
	 */
	public void writeLine(String str) {
		status("Writing \"" + str + "\" To File!");
		try {
			bWriter.write(str);
			bWriter.newLine();
			status("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes a new line.
	 */
	public void newLine() {
		status("Writing Return To File!");
		try {
			bWriter.newLine();
			status("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves written file.
	 */
	public void flush() {
		status("Flushing!");
		try {
			bWriter.flush();
			status("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the writer.
	 */
	public void close() {
		status("Closing!");
		try {
			bWriter.close();
			status("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A private status command.
	 * 
	 * @param status
	 *            Status message.
	 */
	private void status(String status) {
		System.out.println("BufferedFileWriter: " + status);
	}
}