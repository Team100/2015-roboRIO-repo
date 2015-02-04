package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Robot2015.Preferences;

/**
 * Read or writes the Preferences on either the SmartDashboard or the RoboRIO
 * file.
 */
public class SyncPreferences extends Command {

	private final boolean read;
	private final boolean fromFile;
	
	/**
	 * @param read - Reading or writing preferences
	 * @param fromFile - RoboRIO file or SmartDashboard
	 */
    public SyncPreferences(boolean read, boolean fromFile) {
    	this.read = read;
    	this.fromFile = fromFile;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (fromFile) {
			if (read) {
				Preferences.read();
			} else {
				Preferences.write();
			}
		} else {
			String name = SmartDashboard.getString("Preference Name");
			if (read) {
				SmartDashboard.putString("Preference Value", Preferences.getString(name));
			} else {
				Preferences.set(name, SmartDashboard.getString("Preference Value"));
			}
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
