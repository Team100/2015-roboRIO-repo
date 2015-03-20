package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the
 * field.
 */
public class AutoMode_TakeRecycling extends CommandGroup {

	private final double DRIVE_DISTANCE;

	public AutoMode_TakeRecycling() {
		DRIVE_DISTANCE = Preferences.getDouble("AutoTakeRecycling_DriveDistance");
		addSequential(new DeployArm(true), 1);
		addSequential(new AutoDelay(0.5));
		addSequential(new AutoDrive(DRIVE_DISTANCE), 5);
	}
}
