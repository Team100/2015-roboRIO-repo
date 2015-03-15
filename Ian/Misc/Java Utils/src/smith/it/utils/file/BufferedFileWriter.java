package smith.it.utils.file;

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
	 * @throws IOException
	 */
	BufferedFileWriter(File file) {

		try {
			fWriter = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
		}
		bWriter = new BufferedWriter(fWriter);
		status("Ready!");
	}

	/**
	 * Writes to file.
	 * 
	 * @param str
	 *            Message to be written.
	 * @throws IOException
	 */
	public void write(String str) {
		status("Writing \"" + str + "\" To File");

		try {
			bWriter.write(str);
		} catch (IOException e) {
		}
		status("Done!");
	}

	/**
	 * Writes to file and writes a new line.
	 * 
	 * @param str
	 *            Message to be written.
	 * @throws IOException
	 */
	public void writeLine(String str) {
		status("Writing \"" + str + "\" To File!");

		try {
			bWriter.write(str);
			bWriter.newLine();
		} catch (IOException e) {
		}
		status("Done!");
	}

	/**
	 * Writes a new line.
	 * 
	 * @throws IOException
	 */
	public void newLine() {
		status("Writing Return To File!");
		try {
			bWriter.newLine();
		} catch (IOException e) {
		}
		status("Done!");
	}

	/**
	 * Saves written file.
	 * 
	 * @throws IOException
	 */
	public void flush() {
		status("Flushing!");

		try {
			bWriter.flush();
		} catch (IOException e) {
		}
		status("Done!");
	}

	/**
	 * Closes the writer.
	 * 
	 * @throws IOException
	 */
	public void close() {
		status("Closing!");

		try {
			bWriter.close();
		} catch (IOException e) {
		}
		status("Done!");
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