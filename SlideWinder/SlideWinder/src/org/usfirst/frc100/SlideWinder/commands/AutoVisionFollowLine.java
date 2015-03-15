package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.SlideWinder;
import org.usfirst.frc100.SlideWinder.subsystems.CameraVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Autonomously follow the line using vision.
 */
public class  AutoVisionFollowLine extends Command {
	
	private final double distance;

    public AutoVisionFollowLine(double distance) {
        requires(SlideWinder.drivetrain);
        requires(SlideWinder.cameraVision);
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SlideWinder.cameraVision.initCamera();
//    	SlideWinder.cameraVision.setScanLine((int) SmartDashboard.getNumber("Scan Line", 100));
//    	SlideWinder.cameraVision.setLineReference((int) SmartDashboard.getNumber("Line Reference", 160));
//    	SlideWinder.cameraVision.setThreshold((int) SmartDashboard.getNumber("Threshold", 1000));
        SlideWinder.drivetrain.setDistanceTarget(distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//SlideWinder.cameraVision.updateCamera();
    	if(CameraVision.cameraIsOpened){
    	SlideWinder.drivetrain.visionFollowLine(SlideWinder.cameraVision.getLineOffset());
    	}
    	SlideWinder.cameraVision.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return SlideWinder.drivetrain.reachedDistance();
    }

    // Called once after isFinished returns true
    protected void end() {
    	SlideWinder.drivetrain.drive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
