package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 * Parameter zero means dead reckoning
 */
public class AutoMode_PullBinBack extends CommandGroup {

	public AutoMode_PullBinBack(int number) {
		double distance = Preferences.getDouble("AutoPullBin_Distance");
		double distanceToTote = Preferences.getDouble("AutoStackTotes_DistanceToTote");
		double avoidBinDistance = Preferences.getDouble("AutoPullBin_AvoidBinDistance");
		
		addSequential(new AutoCalibrateElevator(), 3.0); // 0
		addSequential(new CloseClaw()); // 0
		addSequential(new AutoDelay(0.75)); // 1
		if(number==0){
			addSequential(new SetElevatorPosition(3), 0.5); // 1?
		} else {
			addSequential(new SetElevatorPosition(3), 2.0); // 1?
		}
 		addSequential(new AutoDrive(distance), 2.0); // 2?

		if(number >= 2){
			addSequential(new SetElevatorPosition(1), 3.0); // 1?
			addSequential(new OpenClaw()); // 0
			addSequential(new AutoDelay(0.5)); // 0.5
			addSequential(new AutoDrive(avoidBinDistance), 1.5); // 1
			addSequential(new AutoTurn(90), 1.0); // 1
			addSequential(new AutoDrive(distanceToTote), 2.0); // 2
			addSequential(new AutoTurn(-90), 1.0); // 1
			addSequential(new AutoDrive(-distance-avoidBinDistance), 2.0); // 2
			addSequential(new CloseClaw()); // 0
			addSequential(new AutoDelay(.75)); // 0.75
			addSequential(new SetElevatorPosition(3), 3.0); // 1?
			addSequential(new AutoDrive(distance), 2.0); // 2?
		}
	}

}