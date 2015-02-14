package netTablesClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * A basic NetworkTables reading/file-writing client.
 * 
 * Make sure to look through the code and configure it. If the client is only
 * reading variables as "false" and "0.0", it's probably not connected to the
 * robot. This Program may take a few seconds to start up.
 * 
 * @Author Ian Smith
 */
public class Client implements ITableListener {

	/*
	 * CONSTANTS HERE:
	 */

	// Sets the place where the data file will be stored.//The default file name
	// is "NetTables Data.txt".
	public static final String FILE_PATH = "C:/Users/Public/Documents/";
	// The default file name is "NetworkTables Data.txt".
	public static final String TXT_FILE_NAME = "NetTables Txt Log";
	// The default file name is "NetworkTables Data.csv".
	public static final String CSV_FILE_NAME = "NetTables Csv Log";
	// A boolean to turn off or on the GUI.
	public static final boolean USE_GUI = true;

	// The time between refreshes in milliseconds.
	public static final long REFRESH_RATE = 1000;
	// Put the roboRIO IP here.
	public static final String IP = "roboRIO-100.local";
	// Debug mode.
	public static final boolean DEBUG_MODE = false;

	// GUI constants.
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;

	/*
	 * CHANGING VARIABLES HERE:
	 */

	// Data refresh cycle.
	int refreshCycle = 1;
	// Current time in millis.
	long currentTimeMillis;
	// Current time in secs.
	float currentTimeSecs;
	// set To True When running.
	boolean isRunning = true;

	// Creates a header array for the .csv file.
	List<String> keyArr = new ArrayList<String>();
	// Converts header array to string.
	String keyStr;
	// Creates a data array for the .csv file
	List<String> dataArr = new ArrayList<String>();
	// Converts data array to string.
	String dataStr;

	/*
	 * CONSTRUCTORS HERE:
	 */

	// Constructs the table.
	NetworkTable table;

	// Constructs the .txt file.
	File txtFile;
	// Constructs the .csv file.
	File csvFile;
	// Constructs the .txt file writer.
	FileWriter fTxtWriter;
	BufferedWriter bTxtWriter;
	// Constructs the .txt file writer.
	FileWriter fCsvWriter;
	BufferedWriter bCsvWriter;

	// Constructs PrintStream.
	PrintStream printStream;

	// Constructs GUI components.
	JFrame frame;
	JTextArea textArea;

	/*
	 * > PROGRAM METHODS HERE <
	 */

	// Runs the program.
	public static void main(String[] args) {
		// Starts the desktop client.
		new Client().run();
	}

	// Prints debug messages.
	public void debug(String msg) {
		if (DEBUG_MODE) {
			System.out.println("DEBUG: " + msg.toUpperCase());
		}
	}

	// Sets up the NetworkTable.
	public void setupTable() {
		// Sets NetworkTables to client mode.
		NetworkTable.setClientMode();
		// Sets the IP address.
		NetworkTable.setIPAddress(IP);
		// Constructs a NetworkTable called "table".
		table = NetworkTable.getTable("SmartDashboard");
		// Adds a table listener.
		table.addTableListener(Client.this, true);
	}

	// Sets up the files.
	public void setupFiles() {
		// Creates the .txt file.
		txtFile = new File(FILE_PATH + TXT_FILE_NAME + " 1.txt");
		// Creates the .csv file.
		csvFile = new File(FILE_PATH + CSV_FILE_NAME + " 1.csv");

		// If the files don't exist set their names, else change and repeat.
		for (int copyNum = 1; txtFile.exists() && csvFile.exists(); copyNum++) {
			txtFile = new File(FILE_PATH + TXT_FILE_NAME + " " + copyNum
					+ ".txt");
			csvFile = new File(FILE_PATH + CSV_FILE_NAME + " " + copyNum
					+ ".csv");
		}

		try {
			// Create the .txt file.
			txtFile.createNewFile();
			System.out.println("Created .txt File: " + txtFile);
			// Create the .csv file.
			csvFile.createNewFile();
			System.out.println("Created .csv File: " + csvFile);

			// Sets up the .txt file writer.
			fTxtWriter = new FileWriter(txtFile.getAbsoluteFile());
			// Sets up the .csv file writer.
			fCsvWriter = new FileWriter(csvFile.getAbsoluteFile());

			// Sets up the .txt buffered writer.
			bTxtWriter = new BufferedWriter(fTxtWriter);
			System.out.println(".txt file setup complete!");

			// Sets up the .csv buffered writer.
			bCsvWriter = new BufferedWriter(fCsvWriter);
			System.out.println(".csv file setup complete!");

			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Sets up the GUI and redirects the input streams.
	public void setupGUI() {
		if (USE_GUI) {
			frame = new JFrame("NetTables Client");
			textArea = new JTextArea();

			frame.add(new JScrollPane(textArea));
			frame.setSize(WIDTH, HEIGHT);
			textArea.setSize(WIDTH, HEIGHT);
			textArea.setEditable(false);
			textArea.setVisible(true);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			printStream = new PrintStream(new CustomOutputStream(textArea));
			System.setOut(printStream);
			System.setErr(printStream);
		}
	}

	// Prints the cycle and time.
	public void cycleTime() {
		if (refreshCycle == 1) {
			keyArr.add("Cycle:");
			keyArr.add("Time (Secs):");
		}
		dataArr.add(0, Integer.toString(refreshCycle));
		dataArr.add(1, Float.toString(currentTimeSecs));
	}

	// Prints Current Subsystem.
	public void subsystem(String subsystem) {
		// Adds subsystem to .csv file.
		if (refreshCycle == 1) {
			keyArr.add(subsystem.toUpperCase() + " SUBSYSTEM");
		}
		try {
			bTxtWriter.newLine();
			bTxtWriter.write(subsystem + " Subsytstem:");
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataArr.add("<" + subsystem.toUpperCase() + ">");
		debug(subsystem.toUpperCase() + " SUBSYSTEM");
		System.out.println();
		System.out.println(subsystem + " Subsystem:");
	}

	// Updates the files and console.
	public void update() {
		for (int i = 0; i < keyArr.size(); i++) {
			try {
				// Write data to .txt file.
				bTxtWriter.write(keyArr.get(i) + ": " + dataArr.get(i));
				bTxtWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Prints data.
			System.out.println(keyArr.get(i) + ": " + dataArr.get(i));
		}
	}

	// Writes data to .csv file.
	public void writeCsv() {
		try {
			if (refreshCycle == 1) {
				keyStr = Arrays.toString(keyArr.toArray());
				bCsvWriter.write(keyStr.substring(1, keyStr.length() - 1));
				bCsvWriter.newLine();
			}
			dataStr = Arrays.toString(dataArr.toArray());
			bCsvWriter.write(dataStr.substring(1, dataStr.length() - 1));
			// Resets dataArr.
			dataArr.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Makes sure that the files are updated.
	public void flush() {
		try {
			// Writes whitespace to .csv file.
			bCsvWriter.newLine();
			// Flushes bCsvWriter.
			bCsvWriter.flush();
			// Writes whitespace to .txt file.
			bTxtWriter.newLine();
			// Flushes bTxtWriter.
			bTxtWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// WhiteSpace
		System.out.println();
	}

	// Automatically updates Dashboard values.
	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		if (isRunning && refreshCycle == 1) {
			keyArr.add(key);
		}

		dataArr.set(keyArr.indexOf(key), (String) value);
	}

	// Method that runs the client.
	public void run() {

		// Debug message.
		debug("SETUP");

		// Sets up the GUI.
		setupGUI();

		// Sets up NetworkTable.
		setupTable();

		// Sets up .txt and .csv files.
		setupFiles();

		// Start time.
		final long START_TIME = System.currentTimeMillis();

		// Sets is running to true.
		isRunning = true;

		// Refresh loop.
		while (true) {

			// Wait for "REFRESH_RATE" milliseconds.
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException ex) {
				Logger.getLogger(Client.class.getName()).log(Level.SEVERE,
						null, ex);
			}

			// Sets long currentTimeMillis.
			currentTimeMillis = (System.currentTimeMillis() - START_TIME);

			// Converts float currenTimeSecs.
			currentTimeSecs = (float) (currentTimeMillis / 1000.0);

			debug("LOOP");

			// Print cycles and time.
			cycleTime();

			debug("CYCLE");
			
			update();

			// Writes .csv arrays to the file.
			writeCsv();

			// Makes sure that the .txt file is being written to
			flush();

			debug("LOOP END");

			// add 1 to "refreshCycles".
			refreshCycle = refreshCycle + 1;
		}
	}
}