
package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	AnalogInput ir = new AnalogInput(0);
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

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
    	SmartDashboard.putNumber("PDPVoltage:", pdp.getVoltage());
        /**SmartDashboard.putNumber("Port1Current", pdp.getCurrent(1));
        SmartDashboard.putNumber("PDPTemp", pdp.getTemperature());
        System.out.println("PDPVoltage" + pdp.getVoltage());
        */
    	
    	double irVolt = ir.getVoltage();
    	SmartDashboard.putNumber("rawVoltage", irVolt);
    	double distance = -12.902*Math.pow(irVolt, 3) + 71.388*Math.pow(irVolt, 2) - 138.05*irVolt + 108.3;
    	SmartDashboard.putNumber("distance", distance);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
