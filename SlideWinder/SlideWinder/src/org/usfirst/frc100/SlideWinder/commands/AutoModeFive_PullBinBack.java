package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import org.usfirst.frc100.SlideWinder.SlideWinder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Places a stack on one tote and picks up the whole stack
 */
public class AutoModeFive_PullBinBack extends CommandGroup {

	public AutoModeFive_PullBinBack(int number) {
		addSequential(new AutoCalibrateElevator());
		if( SlideWinder.claw.isClosed() ){
			addSequential(new OpenClaw());
			addSequential(new AutoDrive(12));
		} 
		addSequential(new CloseClaw());
		addSequential(new SetElevatorPosition(2));
		addSequential(new AutoDrive(-108));
		addSequential(new SetElevatorPosition(1));
		addSequential(new OpenClaw());
		
		if(number >= 2){
			addSequential(new AutoDrive(-18));
			addSequential(new AutoTurn(90));
			addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToTote")));
			addSequential(new AutoTurn(-90));
			addSequential(new AutoDrive(126));
			addSequential(new CloseClaw());
			addSequential(new SetElevatorPosition(2));
			addSequential(new AutoDrive(-108));
			addSequential(new SetElevatorPosition(1));
			addSequential(new OpenClaw());
			
			if(number == 3){
				addSequential(new AutoDrive(-18));
				addSequential(new AutoTurn(90));
				addSequential(new AutoDrive(Preferences.getDouble("AutoStackTotes_DistanceToTote")));
				addSequential(new AutoTurn(-90));
				addSequential(new AutoDrive(126));
				addSequential(new CloseClaw());
				addSequential(new SetElevatorPosition(2));
				addSequential(new AutoDrive(-108));
				addSequential(new SetElevatorPosition(1));
				addSequential(new OpenClaw());
			}
		}
	}

}