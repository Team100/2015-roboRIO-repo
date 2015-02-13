package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the
 * field.
 */
public class AutoModeTwo_TakeRecycling extends CommandGroup {

	private final double SLIDE_DISTANCE;
	private final double DRIVE_LENGTH;

	public AutoModeTwo_TakeRecycling() {
		DRIVE_LENGTH = Preferences.getDouble("AutoTakeRecycling_DriveLength");
		SLIDE_DISTANCE = Preferences
				.getDouble("AutoTakeRecycling_SlideDistance");

		addSequential(new AutoSlideToLine());
		addParallel(new AutoFollowLine(DRIVE_LENGTH));
		addSequential(new AutoGrabRecycling(3));
		addSequential(new AutoDrive(0, SLIDE_DISTANCE));
	}
}
