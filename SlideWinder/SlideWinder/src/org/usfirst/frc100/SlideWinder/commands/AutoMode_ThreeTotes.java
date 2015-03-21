package org.usfirst.frc100.SlideWinder.commands;

import org.usfirst.frc100.SlideWinder.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMode_ThreeTotes extends CommandGroup {

	private final double DISTANCE_TO_TOTE;
	private final double DISTANCE_FORWARD;
	private final double DISTANCE_TO_SCORING;
	private final double ANGLE;
	
	public AutoMode_ThreeTotes(boolean three) {
		DISTANCE_TO_TOTE = Preferences.getDouble("AutoStackTotes_DistanceToTote");
		DISTANCE_FORWARD = Preferences.getDouble("AutoStackTotes_DistanceForward");
		DISTANCE_TO_SCORING = Preferences.getDouble("AutoStackTotes_DistanceToScoring");
		ANGLE = 45;
		
		System.out.println("blah");
		addSequential(new AutoCalibrateElevator(), 2.0);
		addSequential(new CloseClaw());
		addSequential(new AutoDelay(0.8));
		addSequential(new SetElevatorPosition(3));
		addSequential(new AutoDrive(DISTANCE_FORWARD), 3);
		addSequential(new AutoTurn(90), 2);
		addSequential(new AutoDrive(DISTANCE_TO_TOTE));
		addSequential(new QuickStackOne());
		addSequential(new AutoTurn(-ANGLE), 2);
		addSequential(new AutoDrive(DISTANCE_FORWARD/2), 3);
		if(three) {
			addSequential(new AutoTurn(ANGLE*2), 2);
			addSequential(new AutoDrive(DISTANCE_FORWARD/2), 3);
			addSequential(new AutoTurn(-ANGLE), 2);
			addSequential(new AutoDrive(DISTANCE_TO_TOTE+21-14*Math.sqrt(2)), 3);
			addSequential(new QuickStackOne());
			addSequential(new AutoTurn(90), 2);
			addSequential(new AutoDrive(DISTANCE_TO_SCORING), 3);
		} else {
			addSequential(new AutoTurn(ANGLE + 90), 2);
			addSequential(new AutoDrive(DISTANCE_TO_SCORING+7*Math.sqrt(2)), 3);
		}
		
	}
}
