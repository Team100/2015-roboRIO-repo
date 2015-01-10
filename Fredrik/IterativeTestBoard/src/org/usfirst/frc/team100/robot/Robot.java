
package org.usfirst.frc.team100.robot;

import java.awt.EventQueue;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
	Joystick joystick;
	Compressor compressor;
	JoystickButton stopButton;
	DoubleSolenoid sol;
	boolean stop = false;
	SmartDashboard dashboard;
	PowerDistributionPanel pdp = new PowerDistributionPanel();
    public void robotInit() {
    	dashboard = new SmartDashboard();
		joystick = new Joystick(0);
		compressor = new Compressor();
		sol = new DoubleSolenoid(0, 1);
		stopButton = new JoystickButton(joystick, 2);
		compressor.start();
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
        double y = joystick.getY();
    	SmartDashboard.putNumber("Y", y);
    	for (int i = 0; i < 16; i++) {
    		SmartDashboard.putNumber("Channel" + i, pdp.getCurrent(i));
    	}
        if (stopButton.get()) stop ^= true;
        if (!stop) {
        	if (y > 0) {
        		sol.set(Value.kForward);
    		} else if (y < 0) {
    			sol.set(Value.kReverse);
    		} else sol.set(Value.kOff);
        } else {
        	sol.set(Value.kOff);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
