
package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	SmartDashboard dash = new SmartDashboard();
    public void robotInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit() {
    	
    }
    /**
     * This function is called periodically during operator control
     */
	
    @SuppressWarnings("static-access")
	public void teleopPeriodic() {
    	dash.putNumber("X", accel.getX());
    	dash.putNumber("Y", accel.getY());
    	dash.putNumber("Z", accel.getZ());
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
		
    }
    
}
