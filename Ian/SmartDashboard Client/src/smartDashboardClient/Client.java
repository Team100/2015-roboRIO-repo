package smartDashboardClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * A basic NetworkTables reading/ file-writing client.
 * For this client to work the robot code MUST have NetworkTables Integration.
 * Make sure to rename the file after each use. It will be overwritten if you don't.
 * This Program takes a while to start up.
 * 
 * Code By Ian Smith
 */
public class Client{
	
	
	//The time between refreshes in milliseconds.
	public static final long REFRESH_RATE = 1000;
	
	/*
	 * Sets the place where the data file will be stored.
	 * The default file name is "NetworkTables Data.txt".
	 */
	public static final String FILE_PATH = "C:/Users/Public/Documents/SmartDashboard Data.txt";
	//Data refresh cycles.
	public int refreshCycle = 1;
	
	public static void main(String[] args) {
		//Starts the desktop client.
		new Client().run();
	}

	//Method that runs the client.
	public void run() {
		
		
		try {
			
			//Sets up the file.
			File file = new File(FILE_PATH);
			
			//If the file doesn't exist then create the file.
			if (!file.exists()) {
				file.createNewFile();
			}
			//If the file exists then it will be OVERWRITTEN!
			else {
				System.out.println("File already exists!");
				System.out.println();
			}
			//Sets up the file writer.
			FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
			//Sets up the buffered writer.
			@SuppressWarnings("resource")
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			System.out.println("File setup complete!");
			System.out.println();
		
			//Refresh loop.
			while (true) {
				
				//Wait for "REFRESH_RATE" milliseconds.
				try {
				Thread.sleep(REFRESH_RATE);
				}
				catch (InterruptedException ex) {
					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				//Print cycles.
				System.out.println("Cycle: " + refreshCycle);
				//Writes the cycle to file.
				bWriter.write("Cycle: " + refreshCycle);
				bWriter.newLine();
				
				//Some placeholder values. CHANGE THESE!!!
				
				//Gets the Drive Train Mode.
				boolean inSlideMode = SmartDashboard.getBoolean("inSlideMode", false);
				//Prints the data.
				System.out.println("In Slide Mode: " + inSlideMode);
				//Writes data to file.
				bWriter.write("In Slide Mode: " + inSlideMode);
				bWriter.newLine();
				
				//Gets the left motor controller speed.
				double leftControllerSpeed = SmartDashboard.getNumber("leftControllerSpeed", 0.0);
				//Prints the data.
				System.out.println("Left Controller Speed: " + leftControllerSpeed);
				//Writes data to file.
				bWriter.write("Left Controller Speed: " + leftControllerSpeed);
				bWriter.newLine();
				
				//Gets the right motor controller speed.
				double rightControllerSpeed = SmartDashboard.getNumber("rightControllerSpeed", 0.0);
				//Prints the data.
				System.out.println("Right Controller Speed: " + rightControllerSpeed);
				//Writes data to file.
				bWriter.write("Right Controller Speed: " + rightControllerSpeed);
				bWriter.newLine();
				
				//WhiteSpace
				System.out.println();
				//Writes whitespace to file.
				bWriter.newLine();
				
				//Flushes bWriter.
				bWriter.flush();
				
				//add 1 to "refreshCycles".
				refreshCycle = refreshCycle + 1;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}