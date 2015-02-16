package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the
 * field.
 */
public class AutoModeThree_TakeRecyclingWithVision extends CommandGroup {

	private final double SLIDE_DISTANCE;
	private final double DRIVE_DISTANCE;
	private final double DRIVE_DISTANCE2;

	public AutoModeThree_TakeRecyclingWithVision() {
		DRIVE_DISTANCE = Preferences.getDouble("AutoTakeRecycling_DriveDistance1");
		SLIDE_DISTANCE = Preferences
				.getDouble("AutoTakeRecycling_SlideDistance");
		DRIVE_DISTANCE2 = Preferences.getDouble("AutoTakeRecycling_DriveDistance2");
		addParallel(new RaiseArm(false));
		addSequential(new AutoSlideToLine());
		addParallel(new Immobilize());
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoVisionFollowLine(DRIVE_DISTANCE));
		addParallel(new Immobilize());
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoVisionFollowLine(DRIVE_DISTANCE2));
		addParallel(new Immobilize());
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoVisionFollowLine(DRIVE_DISTANCE));
		addParallel(new Immobilize());
		addSequential(new AutoGrabRecycling());
		addSequential(new AutoDrive(0, SLIDE_DISTANCE));
	}
}
