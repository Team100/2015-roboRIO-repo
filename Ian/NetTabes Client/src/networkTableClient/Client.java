package networkTableClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/*
 * A basic NetworkTables reading/file-writing client.
 * 
 * Make sure to look through the code and configure it. If the
 * client is only reading variables as "false" and "0.0", it's
 * probably not connected to the robot. This Program may take a
 * few seconds to start up.
 * 
 * Code By Ian Smith
 */
public class Client {

	// Sets the place where the data file will be stored.//The default file name is "NetTables Data.txt".
	public static final String FILE_PATH = "C:/Users/Public/Documents/";
	//The default file name is "NetworkTables Data.txt".
	public static final String FILE_NAME = "NetTables Data";
	
	
	
	// The time between refreshes in milliseconds.
	public static final long REFRESH_RATE = 1000;
	// Put the robot IP here.
	public static final String IP = "10.1.0.2";
	//Debug mode.
	public static final boolean DEBUG_MODE = false;
	
	
	public static void main(String[] args) {
		// Starts the desktop client.
		new Client().run();
	}

	// Method that runs the client.
	public void run() {
		
		if (DEBUG_MODE) {
			System.out.println("DEBUG RUN");
		}
		
		try {
			// Sets NetworkTables to client mode.
			NetworkTable.setClientMode();
			// Sets the IP address.
			NetworkTable.setIPAddress(IP);
			// Constructs a NetworkTable variable called "table" that's
			// associated with the "datatable" NetworkTable
			NetworkTable table = NetworkTable.getTable("SmartDashboard");

			// Constructs the file.
			File file = new File(FILE_PATH + FILE_NAME + " 1.txt");

			// If the file doesn't exist then create it, else change the name and try again.
			for (int copyNum = 1; file.exists(); copyNum++) {
				file = new File(FILE_PATH + FILE_NAME + " " + copyNum + ".txt");
			}
			
			file.createNewFile();
			System.out.println("Created file: " + file);
			
			// Sets up the file writer.
			FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
			// Sets up the buffered writer.
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			System.out.println("File setup complete!");
			System.out.println();

			if (DEBUG_MODE) {
				System.out.println("DEBUG SETUP FILE");
			}
			
			// Data refresh cycles.
			int refreshCycle = 1;
			
			// Refresh loop.
			while (true) {

				// Wait for "REFRESH_RATE" milliseconds.
				try {
					Thread.sleep(REFRESH_RATE);
				} catch (InterruptedException ex) {
					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
				}

				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP");
				}
				
				// Print cycles.
				System.out.println("Cycle: " + refreshCycle);
				// Writes the cycle to file.
				bWriter.write("Cycle: " + refreshCycle);
				bWriter.newLine();
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP CYCLE");
				}
				
				// Gets data from the arm subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Arm Subsystem:");
				bWriter.write("Arm Subsytstem:");
				bWriter.newLine();

				boolean armGrabbing = table.getBoolean("Arm Grabbing", false);
				bWriter.write("Arm Is Grabbing: " + armGrabbing);
				bWriter.newLine();
				System.out.println("Arm is Grabbing: " + armGrabbing);
				
				boolean armContainerSensor = table.getBoolean("Arm Container Sensor", false);
				bWriter.write("Arm Senses Container: " + armContainerSensor);
				bWriter.newLine();
				System.out.println("Arm Senses Container: " + armContainerSensor);
				
				double armPotentiometer = table.getNumber("Arm Potentiometer", 0.0);
				bWriter.write("Arm Potentiometer Output: " + armPotentiometer);
				bWriter.newLine();
				System.out.println("Arm Potentiometer Output: " + armPotentiometer);
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP ARM");
				}
				
				// Gets data from the claw subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Claw Subsystem:");
				bWriter.write("Claw Subsytstem:");
				bWriter.newLine();
				
				boolean clawClosed = table.getBoolean("Claw Closed", false);
				bWriter.write("Claw Is Closed: " + clawClosed);
				bWriter.newLine();
				System.out.println("Claw Is Closed: " + clawClosed);
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP CLAW");
				}
				
				// Gets data from the drivetrain subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Drivetrain Subsystem:");
				bWriter.write("Drivetrain Subsytstem:");
				bWriter.newLine();
				
				double drivetrainAccelerationLimit = table.getNumber("DriveTrain Acceleration Limit", 0.0);
				bWriter.write("DriveTrain Acceleration Limit: " + drivetrainAccelerationLimit);
				bWriter.newLine();
				System.out.println("DriveTrain Acceleration Limit: " + drivetrainAccelerationLimit);
				
				double drivetrainInterval = table.getNumber("DriveTrain Interval", 0.0);
				bWriter.write("DriveTrain Acceleration Interval: " + drivetrainInterval);
				bWriter.newLine();
				System.out.println("DriveTrain Acceleration Interval: " + drivetrainInterval);
				
				double drivetrainVelocity = table.getNumber("DriveTrain Velocity", 0.0); // only applies to non-slide
				bWriter.write("DriveTrain Non-Slide Velocity: " + drivetrainVelocity);
				bWriter.newLine();
				System.out.println("DriveTrain Non-Slide Velocity: " + drivetrainVelocity);
				
				double drivetrainAcceleration = table.getNumber("DriveTrain Acceleration", 0.0);
				bWriter.write("DriveTrain Acceleration: " + drivetrainAcceleration);
				bWriter.newLine();
				System.out.println("DriveTrain Acceleration: " + drivetrainAcceleration);
				
				boolean drivetrainHighGear = table.getBoolean("DriveTrain High Gear", false);
				bWriter.write("DriveTrain Is In High Gear: " + drivetrainHighGear);
				bWriter.newLine();
				System.out.println("DriveTrain Is In High Gear: " + drivetrainHighGear);
				
				boolean drivetrainSlideMode = table.getBoolean("DriveTrain Slide Mode", false);
				bWriter.write("DriveTrain Is In Slide Mode: " + drivetrainSlideMode);
				bWriter.newLine();
				System.out.println("DriveTrain Is In Slide Mode: " + drivetrainSlideMode);
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP DRIVETRAIN");
				}
				
				// Gets data from the elevator subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Elevator Subsystem:");
				bWriter.write("Elevator Subsytstem:");
				bWriter.newLine();
				
				boolean elevatorUpperLimit = table.getBoolean("Elevator Upper Limit", false);
				bWriter.write("Elevator Is At Upper Limit: " + elevatorUpperLimit);
				bWriter.newLine();
				System.out.println("Elevator Is At Upper Limit: " + elevatorUpperLimit);
				
				boolean elevatorLowerLimit = table.getBoolean("Elevator Lower Limit", false);
				bWriter.write("Elevator Is At Lower Limit: " + elevatorLowerLimit);
				bWriter.newLine();
				System.out.println("Elevator Is At Lower Limit: " + elevatorLowerLimit);
				
				boolean elevatorBrake = table.getBoolean("ELevator Brake", false);
				bWriter.write("ELevator Is Brakeing: " + elevatorBrake);
				bWriter.newLine();
				System.out.println("ELevator Is Brakeing: " + elevatorBrake);
				
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP ELEVATOR");
				}
				
				// WhiteSpace
				System.out.println();
				// Writes whitespace to file.
				bWriter.newLine();
				// Flushes bWriter.
				bWriter.flush();

				// add 1 to "refreshCycles".
				refreshCycle = refreshCycle + 1;
				
				if (DEBUG_MODE) {
					System.out.println("DEBUG LOOP END");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}