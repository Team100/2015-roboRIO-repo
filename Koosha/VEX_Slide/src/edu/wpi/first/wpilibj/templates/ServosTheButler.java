/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ServosTheButler extends IterativeRobot
{
    Joystick controller;
    Servo slider;
    Timer clock;
    double servoVal;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        controller = new Joystick(1);
        slider = new Servo(6);
        clock = new Timer();
        
    }

    public void disabledInit()
    {
        clock.stop();
    }

    public void autonomousInit()
    {
        clock.reset();
        clock.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        servoVal = (1.0 / 2.0) * (Math.sin(15 * clock.get()) + 1); // 15 degrees / second
        slider.set(servoVal);
        SmartDashboard.putNumber("Servo Value", servoVal);
    }

    public void teleopInit()
    {
        clock.stop();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        servoVal = (1.0 / 2.0) * (controller.getY() + 1);
        SmartDashboard.putNumber("Servo Value", servoVal);
        slider.set(servoVal);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
