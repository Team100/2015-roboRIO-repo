package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDance extends CommandGroup {
	private final int DANCE_NUM;
    
    public  AutoDance() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

    	DANCE_NUM = (int)Preferences.getDouble("Dance_Num");
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	switch (DANCE_NUM) {
			case 1:
				
				break;

			default: {
				addSequential(new AutoDrive(5));
				addSequential(new AutoDrive(5));
				addParallel(new AutoTurn(360));
				addParallel(new AutoDrive(0, 5));
				addParallel(new RaiseArm(false));
				addSequential(new SetElevatorPosition(0));
				addParallel(new AutoTurn(-360));
				addParallel(new AutoDrive(0, -5));
				addParallel(new RaiseArm(true));
				addSequential(new SetElevatorPosition(4));
				break;
			}
    	}
    }
}
