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

/**
 * A basic NetworkTables reading/file-writing client.
 * 
 * Make sure to look through the code and configure it. If the
 * client is only reading variables as "false" and "0.0", it's
 * probably not connected to the robot. This Program may take a
 * few seconds to start up.
 * 
 * @Author Ian Smith
 */
public class Client {

	// Sets the place where the data file will be stored.//The default file name
	// is "NetTables Data.txt".
	public static final String FILE_PATH = "C:/Users/Public/Documents/";
	// The default file name is "NetworkTables Data.txt".
	public static final String TXT_FILE_NAME = "NetTables Txt Log";
	// The default file name is "NetworkTables Data.csv".
	public static final String CSV_FILE_NAME = "NetTables Csv Log";

	// The time between refreshes in milliseconds.
	public static final long REFRESH_RATE = 1000;
	// Put the roboRIO IP here.
	public static final String IP = "10.1.0.2";
	// Debug mode.
	public static final boolean DEBUG_MODE = false;
	
	// GUI constants.
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	// Data refresh cycle.
	int refreshCycle = 1;
	// Current time in millis.
	long currentTimeMillis;
	// Current time in secs.
	float currentTimeSecs;

	// Constructs the table.
	NetworkTable table;

	// Creates a header array for the .csv file.
	List<String> csvHeader = new ArrayList<String>();
	// Converts header array to string.
	String headerStr;
	// Creates a data array for the .csv file
	List<String> csvData = new ArrayList<String>();
	// Converts data array to string.
	String dataStr;

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
	}

	// Sets up the files.
	public void setupFiles() {
		// Creates the .txt file.
		txtFile = new File(FILE_PATH + TXT_FILE_NAME + " 1.txt");
		// Creates the .csv file.
		csvFile = new File(FILE_PATH + CSV_FILE_NAME + " 1.csv");

		// If the files don't exist set their names, else change and repeat.
		for (int copyNum = 1; txtFile.exists() && csvFile.exists(); copyNum++) {
			txtFile = new File(FILE_PATH + TXT_FILE_NAME + " " + copyNum + ".txt");
			csvFile = new File(FILE_PATH + CSV_FILE_NAME + " " + copyNum + ".csv");
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

	// Prints the cycle and time.
	public void cycleTime() {
		if (refreshCycle == 1) {
			csvHeader.add("Cycle:");
			csvHeader.add("Time (Secs):");
		}
		csvData.add(0, Integer.toString(refreshCycle));
		csvData.add(1, Float.toString(currentTimeSecs));
		try {
			// Writes the cycle to file.
			bTxtWriter.write("Cycle: " + refreshCycle);
			bTxtWriter.newLine();
			// Writes secs since start to file.
			bTxtWriter.write("Secs since start: " + currentTimeSecs);
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Print cycles.
		System.out.println("Cycle: " + refreshCycle);
		// Print secs since start.
		System.out.println("Time (Secs): " + currentTimeSecs);
	}

	// Prints Current Subsystem.
	public void subsystem(String subsystem) {
		// Adds subsystem to .csv file.
		if (refreshCycle == 1) {
			csvHeader.add(subsystem.toUpperCase() + " SUBSYSTEM");
		}
		try {
			bTxtWriter.newLine();
			bTxtWriter.write(subsystem + " Subsytstem:");
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		csvData.add("<" + subsystem.toUpperCase() + ">");
		debug(subsystem.toUpperCase() + " SUBSYSTEM");
		System.out.println();
		System.out.println(subsystem + " Subsystem:");
	}

	// Gets Dashboard booleans.
	public void getBool(String key) {
		if (refreshCycle == 1) {
			csvHeader.add(key + ":");
		}
		boolean bool = table.getBoolean(key, false);
		String strBool = Boolean.toString(bool);
		csvData.add(strBool);
		try {
			// Write data to .txt file.
			bTxtWriter.write(key + ": " + bool);
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Prints data.
		System.out.println(key + ": " + bool);
	}

	// Gets Dashboard doubles.
	public void getNum(String key) {
		if (refreshCycle == 1) {
			csvHeader.add(key + ":");
		}
		double num = table.getNumber(key, 0.0);
		String strNum = Double.toString(num);
		csvData.add(strNum);
		try {
			// Write data to .txt file.
			bTxtWriter.write(key + ": " + num);
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Prints data.
		System.out.println(key + ": " + num);
	}

	// Writes data to .csv file.
	public void writeCsv() {
		try {
			if (refreshCycle == 1) {
				headerStr = Arrays.toString(csvHeader.toArray());
				bCsvWriter.write(headerStr.substring(1, headerStr.length() - 1));
				bCsvWriter.newLine();
			}
			dataStr = Arrays.toString(csvData.toArray());
			bCsvWriter.write(dataStr.substring(1, dataStr.length() - 1));
			// Resets csvData.
			csvData.clear();
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

		// Refresh loop.
		while (true) {

			// Wait for "REFRESH_RATE" milliseconds.
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException ex) {
				Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
			}

			// Sets long currentTimeMillis.
			currentTimeMillis = (System.currentTimeMillis() - START_TIME);

			// Converts float currenTimeSecs.
			currentTimeSecs = (float) (currentTimeMillis / 1000.0);

			debug("LOOP");

			// Print cycles and time.
			cycleTime();

			debug("CYCLE");

			// Gets data from the Arm subsystem.
			subsystem("Arm");
			getBool("Arm Grabbing");
			getBool("Arm Container Sensor");
			getBool("Arm Forward Limit");
			getBool("Arm Back Limit");
			getNum("Arm Potentiometer");

			// Gets data from the Claw subsystem.
			subsystem("Claw");
			getBool("Claw Closed");

			// Gets data from the DriveTrain subsystem.
			subsystem("DriveTrain");
			getBool("DriveTrain High Gear");
	        getNum("DriveTrain LeftEncoder");
	        getNum("DriveTrain RightEncoder");
	        getNum("DriveTrain Gyro");
	        getNum("Left LineReader Value");
	    	getNum("Right LineReader Value");        
	        getBool("Left LineReader OnWhite");
	    	getBool("Right LineReader OnWhite");
	    	
	    	// Acceleration code.
			getNum("DriveTrain Acceleration Limit");
			getNum("DriveTrain Interval");
			getNum("DriveTrain Velocity"); // only applies to non-slide
			getNum("DriveTrain Acceleration");

			// Gets data from the Elevator subsystem.
			subsystem("Elevator");
			getBool("Elevator Upper Limit");
			getBool("Elevator Lower Limit");
			getBool("ELevator Brake");
			getNum("Elevator Encoder");

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