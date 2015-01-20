package networkTableClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/*
 * A basic NetworkTables reading/ file-writing client.
 * For this client to work the robot code MUST have NetworkTables Integration.
 * Make sure to rename the file after each use. It will be overwritten if you don't.
 * This Program takes a while to start up.
 * 
 * Code By Ian Smith
 */
public class Client {

	// Put the robot IP here.
	public static final String IP = "10.1.0.2";

	// The time between refreshes in milliseconds.
	public static final long REFRESH_RATE = 1000;

	/*
	 * Sets the place where the data file will be stored. The default file name
	 * is "NetworkTables Data.txt".
	 */
	public static final String FILE_PATH = "C:/Users/Public/Documents/NetworkTables Data.txt";
	// Data refresh cycles.
	public int refreshCycle = 1;

	public static void main(String[] args) {
		// Starts the desktop client.
		new Client().run();
	}

	// Method that runs the client.
	public void run() {

		try {
			// Sets NetworkTables to client mode.
			NetworkTable.setClientMode();
			// Sets the IP address.
			NetworkTable.setIPAddress(IP);
			// Constructs a NetworkTable variable called "table" that's
			// associated with the "datatable" NetworkTable
			NetworkTable table = NetworkTable.getTable("SmartDashboard");

			// Sets up the file.
			File file = new File(FILE_PATH);

			// If the file doesn't exist then create the file.
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("File has been created!");
				System.out.println();
			}
			// If the file exists then it will be OVERWRITTEN!
			else {
				System.out.println("File already exists!");
				System.out.println();
			}
			// Sets up the file writer.
			FileWriter fWriter = new FileWriter(file.getAbsoluteFile());
			// Sets up the buffered writer.
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			System.out.println("File setup complete!");
			System.out.println();

			// Refresh loop.
			while (true) {

				// Wait for "REFRESH_RATE" milliseconds.
				try {
					Thread.sleep(REFRESH_RATE);
				} catch (InterruptedException ex) {
					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
				}

				// Print cycles.
				System.out.println("Cycle: " + refreshCycle);
				// Writes the cycle to file.
				bWriter.write("Cycle: " + refreshCycle);
				bWriter.newLine();
				
				
				
				// Gets data from the arm subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Arm Subsystem:");
				bWriter.write("Arm Subsytstem:");
				bWriter.newLine();

				boolean armGrabbing = table.getBoolean("Arm Grabbing", false);
				bWriter.write("Arm Grabbing: " + armGrabbing);
				bWriter.newLine();
				System.out.println("Arm Grabbing: " + armGrabbing);
				
				boolean armContainerSensor = table.getBoolean("Arm Container Sensor", false);
				bWriter.write("Arm Container Sensor: " + armContainerSensor);
				bWriter.newLine();
				System.out.println("Arm Container Sensor: " + armContainerSensor);
				
				double armPotentiometer = table.getNumber("Arm Potentiometer", 0.0);
				bWriter.write("Arm Potentionmeter: " + armPotentiometer);
				bWriter.newLine();
				System.out.println("Arm Potentionmeter: " + armPotentiometer);
				
				
				
				// Gets data from the claw subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Claw Subsystem:");
				bWriter.write("Claw Subsytstem:");
				bWriter.newLine();
				
				boolean clawClosed = table.getBoolean("Claw Closed", false);
				bWriter.write("Claw Closed: " + clawClosed);
				bWriter.newLine();
				System.out.println("Claw Closed: " + clawClosed);
				
				
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
				bWriter.write("DriveTrain Interval: " + drivetrainInterval);
				bWriter.newLine();
				System.out.println("DriveTrain Interval: " + drivetrainInterval);
				
				double drivetrainVelocity = table.getNumber("DriveTrain Velocity", 0.0); // only applies to non-slide
				bWriter.write("DriveTrain Velocity: " + drivetrainVelocity);
				bWriter.newLine();
				System.out.println("DriveTrain Velocity: " + drivetrainVelocity);
				
				double drivetrainAcceleration = table.getNumber("DriveTrain Acceleration", 0.0);
				bWriter.write("DriveTrain Acceleration: " + drivetrainAcceleration);
				bWriter.newLine();
				System.out.println("DriveTrain Acceleration: " + drivetrainAcceleration);
				
				boolean drivetrainHighGear = table.getBoolean("DriveTrain High Gear", false);
				bWriter.write("DriveTrain High Gear: " + drivetrainHighGear);
				bWriter.newLine();
				System.out.println("DriveTrain High Gear: " + drivetrainHighGear);
				
				boolean drivetrainSlideMode = table.getBoolean("DriveTrain Slide Mode", false);
				bWriter.write("DriveTrain Slide Mode: " + drivetrainSlideMode);
				bWriter.newLine();
				System.out.println("DriveTrain Slide Mode: " + drivetrainSlideMode);
				
				
				
				// Gets data from the elevator subsystem.
				System.out.println();
				bWriter.newLine();
				System.out.println("Elevator Subsystem:");
				bWriter.write("Elevator Subsytstem:");
				bWriter.newLine();
				
				boolean elevatorUpperLimit = table.getBoolean("Elevator Upper Limit", false);
				bWriter.write("Elevator Upper Limit: " + elevatorUpperLimit);
				bWriter.newLine();
				System.out.println("Elevator Upper Limit: " + elevatorUpperLimit);
				
				boolean elevatorLowerLimit = table.getBoolean("Elevator Lower Limit", false);
				bWriter.write("Elevator Lower Limit: " + elevatorLowerLimit);
				bWriter.newLine();
				System.out.println("Elevator Lower Limit: " + elevatorLowerLimit);
				
				boolean elevatorBrake = table.getBoolean("ELevator Brake", false);
				bWriter.write("ELevator Brake: " + elevatorBrake);
				bWriter.newLine();
				System.out.println("ELevator Brake: " + elevatorBrake);
				
				boolean elevatorInPosition = table.getBoolean("Elevator In Position", false);
				bWriter.write("Elevator In Position: " + elevatorInPosition);
				bWriter.newLine();
				System.out.println("Elevator In Position: " + elevatorInPosition);
				
				
				
				// WhiteSpace
				System.out.println();
				// Writes whitespace to file.
				bWriter.newLine();
				// Flushes bWriter.
				bWriter.flush();

				// add 1 to "refreshCycles".
				refreshCycle = refreshCycle + 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}