package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeFive_PullBinBack extends CommandGroup {

	public AutoModeFive_PullBinBack(int number) {
		double distance = Preferences.getDouble("AutoPullBin_Distance");
		double distanceToTote = Preferences.getDouble("AutoStackTotes_DistanceToTote");
		double avoidBinDistance = Preferences.getDouble("AutoPullBin_AvoidBinDistance");
		
		addSequential(new AutoCalibrateElevator()); // 0
		addSequential(new CloseClaw()); // 0
		addSequential(new AutoDelay(1.0)); // 1
		addSequential(new SetElevatorPosition(3)); // 1?
 		addSequential(new AutoDrive(distance)); // 2?
		
		if(number >= 2){
			addSequential(new SetElevatorPosition(1)); // 1?
			addSequential(new OpenClaw()); // 0
			addSequential(new AutoDelay(0.5)); // 0.5
			addSequential(new AutoDrive(avoidBinDistance)); // 0
			addSequential(new AutoTurn(90), 2); // 1
			addSequential(new AutoDrive(distanceToTote)); // 2?
			addSequential(new AutoTurn(-90), 2); // 1
			addSequential(new AutoDrive(-distance-avoidBinDistance)); // 2?
			addSequential(new CloseClaw()); // 0
			addSequential(new AutoDelay(1.0)); // 1
			addSequential(new SetElevatorPosition(3)); // 1?
			addSequential(new AutoDrive(distance)); // 2?
		}
	}

}