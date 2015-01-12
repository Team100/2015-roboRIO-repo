package org.usfirst.frc.team100.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
    Jaguar leftMotor = new Jaguar(1);
    Jaguar rightMotor = new Jaguar(2);
    RobotDrive drive = new RobotDrive(leftMotor, rightMotor);
    Encoder leftEncoder = new Encoder(1,2);
    Encoder rightEncoder = new Encoder(3,4);
    Gyro gyro = new Gyro(0);
    PID drivePID;
    PID driveAnglePID;
    Joystick joystick = new Joystick(1);
    
    
    AnalogInput p = new AnalogInput(1);
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        SmartDashboard.putNumber("TestDistance", 0.0);
        SmartDashboard.putNumber("TestAngle", 0.0);
        SmartDashboard.putNumber("Reset?", 0.0);
        Preferences.read();
        drivePID = new PID("drive");
        driveAnglePID = new PID("driveAngle");
    }

    public void autonomousInit(){
        drivePID.setTarget(SmartDashboard.getNumber("TestDistance"));
        driveAnglePID.setTarget(SmartDashboard.getNumber("TestAngle"));
//    	drivePID.update(leftEncoder.get()/2+rightEncoder.get()/2);
    	drivePID.update(p.getValue());
        driveAnglePID.update(gyro.getAngle());
    	Preferences.write();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//        drivePID.update(leftEncoder.get()/2+rightEncoder.get()/2);
    	drivePID.update(p.getValue());
        driveAnglePID.update(gyro.getAngle());
        drive.arcadeDrive(drivePID.getOutput(),driveAnglePID.getOutput());
        if(SmartDashboard.getNumber("Reset?") == 1.0){
        	drivePID.reset();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drive.tankDrive(joystick.getY(), joystick.getThrottle());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
}
