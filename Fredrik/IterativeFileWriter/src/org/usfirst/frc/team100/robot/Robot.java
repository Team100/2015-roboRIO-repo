
package org.usfirst.frc.team100.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	public static final String message = "Testing!";
	File homeDir = new File(System.getProperty("user.home"));
	File testFile;
	Charset charset = Charset.forName("US-ASCII");
	//FileWriter fileWriter;
    public void robotInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void teleopInit() {
    	/**
    	 * @param writer the object used to write to the file
    	 */
    	try (BufferedWriter writer = Files.newBufferedWriter(testFile.toPath(), charset, StandardOpenOption.WRITE)) {
        	testFile = new File(homeDir.getAbsoluteFile() + "testFile");
        	Files.createFile(testFile.toPath());
        	System.out.println(homeDir.toPath());
    		writer.write(message);
    		writer.flush();
    	} catch (IOException | NullPointerException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
