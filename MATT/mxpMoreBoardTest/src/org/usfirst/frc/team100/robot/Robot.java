
package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
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
     * 
     * 
     */
	
	DigitalInput lim1;
	DigitalInput lim2;
	AnalogInput ultra1;
	Victor vic1;
	
    public void robotInit() {

    	lim1 = new DigitalInput(10);
    	lim2 = new DigitalInput(0);
    	ultra1 = new AnalogInput(4);
    	vic1 = new Victor(11);
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	System.out.println("lim10 "+ lim1.get());
    	System.out.println("lim0 "+ lim2.get());
    	SmartDashboard.putBoolean("lim10", lim1.get());
    	SmartDashboard.putBoolean("lim0", lim2.get());
    	System.out.println("ultra1 "+ ultra1.getVoltage()*512/5);
    	SmartDashboard.putNumber("ultra1", ultra1.getVoltage()*512/5);
    	if(lim1.get())
    	{
    		vic1.set(1);
    	}
    	else
    		vic1.set(0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
