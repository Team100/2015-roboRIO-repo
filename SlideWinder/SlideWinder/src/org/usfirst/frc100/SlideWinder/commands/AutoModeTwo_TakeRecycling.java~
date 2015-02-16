package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the
 * field.
 */
public class AutoModeTwo_TakeRecycling extends CommandGroup {

	private final double SLIDE_DISTANCE;
	private final double DRIVE_LENGTH;
	private final double DRIVE_LENGTH2;

	public AutoModeTwo_TakeRecycling() {
		DRIVE_LENGTH = Preferences.getDouble("AutoTakeRecycling_DriveLength");
		SLIDE_DISTANCE = Preferences
				.getDouble("AutoTakeRecycling_SlideDistance");
		DRIVE_LENGTH2 = Preferences.getDouble("AutoTakeRecycling_DriveLength2");

		addSequential(new AutoSlideToLine());
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoFollowLine(DRIVE_LENGTH));
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoFollowLine(DRIVE_LENGTH2));
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoFollowLine(DRIVE_LENGTH));
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoDrive(0, SLIDE_DISTANCE));
	}
}
