package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc100.Robot2015.Preferences;
import org.usfirst.frc100.Robot2015.Robot;

/**
 * Read or writes the Preferences file on the RoboRIO
 */
public class  SyncPreferences extends Command {

	private final boolean read;
	
    public SyncPreferences(boolean read) {
    	this.read = read;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(read) {
    		Preferences.read();
    	} else {
    		Preferences.write();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
