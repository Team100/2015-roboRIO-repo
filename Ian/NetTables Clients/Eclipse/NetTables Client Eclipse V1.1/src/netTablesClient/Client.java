package netTablesClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * A basic NetworkTables reading/file-writing client.
 * 
 * Make sure to look through the code and configure it. If the client is only
 * reading variables as "false" and "0.0", it's probably not connected to the
 * robot. This Program may take a few seconds to start up.
 * 
 * @Author Ian Smith
 */
public class Client {

	/*
	 * CONSTANTS HERE:
	 */

	// Sets the place where the data file will be stored.
	// The default file name is "NetTables Data.txt".
	public static final String DIR_PATH = System.getProperty("user.dir")
			.replace('\\', '/') + "/";

	// The parameers file name.
	public static final String PARAMS_FILE_NAME = "run-params.txt";
	// The init file name.
	public static final String INIT_FILE_NAME = "init-params.txt";
	// The command seperator.
	public static final String CMD_SEPERATOR = " ";

	// The default file name is "NetworkTables Data.txt".
	public static final String TXT_FILE_NAME = "NetTables Txt Log";
	// The default file name is "NetworkTables Data.csv".
	public static final String CSV_FILE_NAME = "NetTables Csv Log";

	// GUI constants.
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	/*
	 * NON-CONSTANT VARIABLES HERE:
	 */

	// Data refresh cycle.
	int refreshCycle = 1;
	// Current time in millis.
	long currentTimeMillis;
	// Current time in secs.
	float currentTimeSecs;
	// Is running boolean.
	boolean isRunning = true;
	// Debug mode.
	boolean debugMode = false;
	// A boolean to turn off or on the GUI.
	boolean useGui = true;
	// The roboRIO IP here.
	String ip = "10.1.0.2";
	// The time between refreshes in milliseconds.
	long refreshRate = 1000;

	// Constructs a header array for the .csv file.
	List<String> csvHeader = new ArrayList<String>();
	// Converts header array to string.
	String headerStr;
	// Creates a data array for the .csv file
	List<String> csvData = new ArrayList<String>();
	// Converts data array to string.
	String dataStr;

	List<String> initList;
	List<String> paramsList;

	/*
	 * CONSTRUCTORS HERE:
	 */

	// Instanciates the table.
	NetworkTable table;

	// Instanciates the params file.
	File paramsFile;
	// Instanciates the init file.
	File initFile;

	// Instanciates the .txt file.
	File txtFile;
	// Instanciates the .csv file.
	File csvFile;
	// Instanciates the .txt file writer.
	FileWriter fTxtWriter;
	BufferedWriter bTxtWriter;
	// Instanciates the .txt file writer.
	FileWriter fCsvWriter;
	BufferedWriter bCsvWriter;

	// Instanciates PrintStream.
	PrintStream printStream;

	// Instanciates GUI components.
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
		if (debugMode) {
			System.out.println("DEBUG: " + msg.toUpperCase());
		}
	}

	// Sets up the NetworkTable.
	public void setupTable() {

		// Sets NetworkTables to client mode.
		NetworkTable.setClientMode();
		// Sets the IP address.
		NetworkTable.setIPAddress(ip);
		// Constructs a NetworkTable called "table".
		table = NetworkTable.getTable("SmartDashboard");
	}

	// Sets up the files.
	public void setupFiles() {
		System.out.println("Creating files");

		// Constructs the params file.
		paramsFile = new File(DIR_PATH + PARAMS_FILE_NAME);

		if (!paramsFile.exists()) {
			isRunning = false;
		}

		// Constructs the .txt file.
		txtFile = new File(DIR_PATH + TXT_FILE_NAME + " 1.txt");
		// Constructs the .csv file.
		csvFile = new File(DIR_PATH + CSV_FILE_NAME + " 1.csv");

		// Assigns paramsList and initList.
		try {
			paramsList = BufferedFileReader.fileToArrList(paramsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// If the files don't exist set their names, else change and repeat.
		for (int copyNum = 1; txtFile.exists() && csvFile.exists(); copyNum++) {
			txtFile = new File(DIR_PATH + TXT_FILE_NAME + " " + copyNum
					+ ".txt");
			csvFile = new File(DIR_PATH + CSV_FILE_NAME + " " + copyNum
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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Sets up the GUI and redirects the input streams.
	public void setupGUI() {
		if (useGui) {
			frame = new JFrame("NetTables Client");
			textArea = new JTextArea();

			frame.add(new JScrollPane(textArea));
			frame.setSize(WIDTH, HEIGHT);
			textArea.setSize(WIDTH, HEIGHT);
			textArea.setEditable(false);
			textArea.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			printStream = new PrintStream(new OutputStreamToTextArea(textArea));
			System.setOut(printStream);
			System.setErr(printStream);
		}
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
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Print cycles.
		System.out.println("Cycle: " + refreshCycle);
		// Print secs since start.
		System.out.println("Time (Secs): " + currentTimeSecs);
		System.out.println();
	}

	// Prints Current Subsystem.
	public void subsystem(String subsystem) {
		// Adds subsystem to .csv file.
		if (refreshCycle == 1) {
			csvHeader.add(subsystem.toUpperCase() + " SUBSYSTEM");
		}
		try {
			bTxtWriter.write(subsystem + " Subsytstem:");
			bTxtWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		csvData.add("<" + subsystem.toUpperCase() + ">");
		debug(subsystem.toUpperCase() + " SUBSYSTEM");
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

	// Parses the params file.
	public void parseParams(List<String> fileArr) {
		String line;
		String cmd;
		String params;

		for (int i = 0; i < fileArr.size(); i++) {
			line = fileArr.get(i);
			if (line.indexOf(CMD_SEPERATOR) != -1) {
				cmd = line.substring(0, line.indexOf(CMD_SEPERATOR));
				params = line.substring(line.indexOf(CMD_SEPERATOR) + 1,
						line.length());
				switch (cmd) {
				// Lists the subsystem.
					case "SUBSYSTEM" :
						subsystem(params);
						break;

					// Gets number data.
					case "GETNUM" :
						getNum(params);
						break;

					// Gets boolean data.
					case "GETBOOL" :
						getBool(params);
						break;

					// Prints a message to the console.
					case "STATUS" :
						System.out.println("STATUS: " + params);
						break;

					// Only used when MODE is set to DEBUG;
					case "DEBUG" :
						debug(params);
						break;

					// Switches the mode
					case "MODE" :
						if (params.equals("DEBUG")) {
							debugMode = true;
						} else if (params.equals("NORMAL")) {
							debugMode = false;
						}
						break;

					// sets the GUI mode only works in init-params.txt.
					case "GUIMODE" :
						if (params.equals("GUI")) {
							useGui = true;
						} else if (params.equals("IDE")) {
							useGui = false;
						}
						break;

					// Sets the refresh rate.
					case "REFRESH" :
						refreshRate = Long.parseLong(params, 10);
						break;

					// Sets the IP.
					case "SETIP" :
						ip = params;
						break;

					// Ignores the line if command is unknown.
					default :

						break;
				}
			} else if (!line.equals("")) {
				cmd = line;
				switch (cmd) {
					
					// Goes to next line.
					case "NEWLINE" :
						System.out.println("");
						try {
							bTxtWriter.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;

					// Ignores the line if command is unknown.
					default :

						break;
				}
			}
		}
	}

	// Writes data to .csv file.
	public void writeCsv() {
		try {
			if (refreshCycle == 1) {
				headerStr = Arrays.toString(csvHeader.toArray());
				bCsvWriter
						.write(headerStr.substring(1, headerStr.length() - 1));
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

		// Creates the init-params.txt file and list.
		initFile = new File(DIR_PATH + INIT_FILE_NAME);
		try {
			initList = BufferedFileReader.fileToArrList(initFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// Parses the init-params.txt file.
		parseParams(initList);

		// Sets up the GUI.
		setupGUI();

		System.out.println("Initializing, please wait...");

		// Sets up NetworkTable.
		setupTable();

		// Sets up .txt and .csv files.
		setupFiles();

		// Start time.
		final long START_TIME = System.currentTimeMillis();

		// Refresh loop.
		while (isRunning) {
			// Wait for "REFRESH_RATE" milliseconds.
			try {
				Thread.sleep(refreshRate);
			} catch (InterruptedException e) {
			}

			// Sets long currentTimeMillis.
			currentTimeMillis = (System.currentTimeMillis() - START_TIME);

			// Converts float currenTimeSecs.
			currentTimeSecs = (float) (currentTimeMillis / 1000.0);

			// Print cycles and time.
			cycleTime();

			// Parses the run-params.txt file.
			parseParams(paramsList);

			// Writes .csv arrays to the file.
			writeCsv();

			// Makes sure that the .txt file is being written to
			flush();

			// add 1 to "refreshCycles".
			refreshCycle = refreshCycle + 1;
		}
		try {
			bTxtWriter.close();
			bCsvWriter.close();
		} catch (IOException e) {
		}
		System.out
				.println(PARAMS_FILE_NAME
						+ " does not exist! Please create it before trying to run this program.");
	}
}