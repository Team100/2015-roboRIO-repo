package org.usfirst.frc100.Robot2015.subsystems;

import org.usfirst.frc100.Robot2015.PID;
import org.usfirst.frc100.Robot2015.Preferences;
import org.usfirst.frc100.Robot2015.RobotMap;
import org.usfirst.frc100.Robot2015.commands.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The "butterslide" drivetrain of the robot.
 */
public class Drivetrain extends Subsystem {
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController leftMotor = RobotMap.drivetrainLeftMotor;
    SpeedController rightMotor = RobotMap.drivetrainRightMotor;
    RobotDrive robotDrive = RobotMap.drivetrainRobotDrive;
    SpeedController slideMotor = RobotMap.drivetrainSlideMotor;
    DoubleSolenoid shifter = RobotMap.drivetrainShifter;
    Encoder leftEncoder = RobotMap.drivetrainLeftEncoder;
    Encoder rightEncoder = RobotMap.drivetrainRightEncoder;
    Encoder slideEncoder = RobotMap.drivetrainSlideEncoder;
    Gyro gyro = RobotMap.drivetrainGyro;
    AnalogInput leftLineReader = RobotMap.drivetrainLeftLineReader;
    AnalogInput rightLineReader = RobotMap.drivetrainRightLineReader;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    AnalogTrigger leftLineReadTrigger = RobotMap.drivetrainLeftLineReadTrigger;
    AnalogTrigger rightLineReadTrigger = RobotMap.drivetrainRightLineReadTrigger;
    private double velocity = 0;
    private double driveLimit = 0;
    private double accelerationLoopInterval = 20;
    private double turnLimit = 0;
    private double turnVelocity = 0;
    private double turnAcceleration = 0;
    private double previousTurnVelocity = 0;
    private double slideLimit = 0;
    private double slideVelocity = 0;
    private double trueVelocity = 0;
    private double previousTrueVelocity = 0;
    private double trueAcceleration = 0;
    private Timer timer = new Timer();
    private PID distancePID = new PID("DriveDistance");
    private PID anglePID = new PID("DriveAngle");
    private PID slidePID = new PID("DriveSlide");

    // Sets the default command to Drive
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new Drive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    }
    
    // Drives the robot using the given the speed, strafe, and turn values
    public void drive(double speed, double strafe, double turn) {
    	robotDrive.arcadeDrive(speed, turn);
    	slideMotor.set(strafe);
    }
    
    // Shifts to high or low gear
    public void shift(boolean highgear) {
    	if(highgear) {
    		shifter.set(DoubleSolenoid.Value.kForward);
    	} 
    	else {
    		shifter.set(DoubleSolenoid.Value.kReverse);
    	}
    }
    
    // Limits the acceleration.
    public void gradualDrive(double drive, double slide, double turn) {
        timer.stop();
        accelerationLoopInterval = timer.get();
        velocity = leftEncoder.getRate();
        slideVelocity = slideEncoder.getRate();
        trueVelocity = Math.hypot(velocity, slideVelocity);
        trueAcceleration = (trueVelocity-previousTrueVelocity) / accelerationLoopInterval;
        turnVelocity = gyro.getRate();
        turnAcceleration = (turnVelocity - previousTurnVelocity)/accelerationLoopInterval;
        // see if the velocity limits work
        if (trueAcceleration > Preferences.getDouble("UpperAccelerationLimit") || turnAcceleration > Preferences.getDouble("UpperTurnAccelerationLimit") ) {
             drive(driveLimit, slideLimit, turnLimit);

        } else if (trueAcceleration < Preferences.getDouble("LowerAccelerationLimit") || turnAcceleration < Preferences.getDouble("LowerTurnAccelerationLimit") ) {
            drive(driveLimit, slideLimit, turnLimit);

        } else {
            if(drive > driveLimit) {
                driveLimit += Preferences.getDouble("LimitStep");
            } else if (drive < driveLimit){
               driveLimit -= Preferences.getDouble("LimitStep");
            }
            if(slide > slideLimit){
            	slideLimit += Preferences.getDouble("LimitStep");
            } else if(slide < slideLimit){
            	slideLimit -= Preferences.getDouble("LimitStep");
            }
            if(turn > turnLimit){
            	turnLimit += Preferences.getDouble("LimitStep");
            }else if(turn < turnLimit){
            	turnLimit -= Preferences.getDouble("LimitStep");
            }
            
            drive(driveLimit, slideLimit, turnLimit);
        }
        
        previousTrueVelocity = trueVelocity;
        previousVelocity = velocity;
        previousSlideVelocity = slideVelocity;
        previousTurnVelocity = turnVelocity;
        timer.start();
    }
    
    // Sets the target for the auto PID
    public void setAutoTarget(double targetDistance, double targetSlide, double targetAngle ){
    	distancePID.setTarget(targetDistance);
    	slidePID.setTarget(targetSlide);
    	anglePID.setTarget(targetAngle);
    	distancePID.setRelativeLocation(0);
    	slidePID.setRelativeLocation(0);
    	anglePID.setRelativeLocation(0);
    }
    
    // Updates the auto PID
    public void updateAuto() {
    	distancePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) /2);
    	slidePID.update(slideEncoder.getDistance());
    	anglePID.update(gyro.getAngle());
    	drive(distancePID.getOutput(), slidePID.getOutput(), anglePID.getOutput());
    }
    
    // Returns whether the auto PID has finished
    public boolean autoReachedTarget(){
    	return distancePID.reachedTarget() && anglePID.reachedTarget() && slidePID.reachedTarget();
    }
    
    // Updates the SmartDashboard
    public void updateDashboard() {
        SmartDashboard.putBoolean("DriveTrain High Gear", shifter.get() == DoubleSolenoid.Value.kForward);
        SmartDashboard.putNumber("DriveTrain LeftEncoder", leftEncoder.getDistance());
        SmartDashboard.putNumber("DriveTrain RightEncoder", rightEncoder.getDistance());
        SmartDashboard.putNumber("DriveTrain Gyro", gyro.getAngle());
        SmartDashboard.putNumber("Left LineReader Value", leftLineReader.getValue());
    	SmartDashboard.putNumber("Right LineReader Value", rightLineReader.getValue());        
        SmartDashboard.putBoolean("Left LineReader OnWhite", !leftLineReadTrigger.getTriggerState());
    	SmartDashboard.putBoolean("Right LineReader OnWhite", !rightLineReadTrigger.getTriggerState());
        
        // Acceleration code
    	SmartDashboard.putNumber("DriveTrain Acceleration Limit", driveLimit);
        SmartDashboard.putNumber("DriveTrain Interval", accelerationLoopInterval);
        SmartDashboard.putNumber("DriveTrain Velocity", velocity); // only applies to non-slide
        SmartDashboard.putNumber("DriveTrain Acceleration", trueAcceleration );
    }
    
    //anglePID methods
    public double updateAngle() {
    	anglePID.update(gyro.getAngle());
    	return anglePID.getOutput();
    }
    
    public void setAngleTarget(double targetAngle) {
    	anglePID.setTarget(targetAngle);
    	anglePID.setRelativeLocation(0);    
	}
    
    //distancePID methods
    public void setDistanceTarget(double targetDistance){
    	distancePID.setTarget(targetDistance);
    	distancePID.setRelativeLocation(0);
    }
    
    public double updateDistance() {
    	distancePID.update((leftEncoder.getDistance() + rightEncoder.getDistance()) /2);
    	return distancePID.getOutput();
    }
    
    //returns turn value to turn towards line
    public double followLine() {
        double turnTrack = 0;
        
        if(rightLineReadTrigger.getTriggerState() && !leftLineReadTrigger.getTriggerState()){
    		turnTrack = -.5;
    	}
    	else if(leftLineReadTrigger.getTriggerState() && !rightLineReadTrigger.getTriggerState()){
    		turnTrack = .5;
    	}
    	else if(!rightLineReadTrigger.getTriggerState() && !leftLineReadTrigger.getTriggerState()){
    		turnTrack = 0;
    	}
        
    	return turnTrack;     
    }
    
    public void setLineTrackLimits(){
    	int limit;
    	int diff = rightLineReader.getValue() - leftLineReader.getValue();
    	if( diff < 50 ){
    		limit = (int)Preferences.getDouble("LineTracker Limit");
    	}
    	else{
    		limit = leftLineReader.getValue() + diff/2;
    		SmartDashboard.putNumber("LineTracker Limit", limit);
    		Preferences.set("LineTracker Limit", limit);
    	}
    	leftLineReadTrigger.setLimitsRaw(limit, limit);
    	rightLineReadTrigger.setLimitsRaw(limit, limit);
    }
}
