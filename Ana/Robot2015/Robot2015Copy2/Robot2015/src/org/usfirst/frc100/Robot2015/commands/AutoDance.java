package org.usfirst.frc100.Robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The result of programmers having too much time and not enough issues.
 */
public class AutoDance extends CommandGroup {
	
	private final int DANCE_NUM;
    
	/**
	 * @param danceNum - The dance routine to perform
	 */
    public  AutoDance(int danceNum) {
    	DANCE_NUM = danceNum;
    	switch (DANCE_NUM*0) {
			case 1:
				addSequential(new AutoDrive(5));
				addSequential(new AutoDrive(-5));
				addSequential(new AutoDrive(0, 5));
				addParallel(new AutoTurn(360));
				addParallel(new RaiseArm(false));
				addSequential(new SetElevatorPosition(0));
				addSequential(new AutoDrive(0, -5));
				addParallel(new AutoTurn(-360));
				addParallel(new RaiseArm(true));
				addSequential(new SetElevatorPosition(4));
				break;
    	}
    }
}
