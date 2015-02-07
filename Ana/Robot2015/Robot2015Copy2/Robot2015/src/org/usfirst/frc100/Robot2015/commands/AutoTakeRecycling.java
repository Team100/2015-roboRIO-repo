package org.usfirst.frc100.Robot2015.commands;
import org.usfirst.frc100.Robot2015.Preferences;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the field.
 */
public class AutoTakeRecycling extends CommandGroup {
    
	private final double SLIDE_DISTANCE;
	private final double DRIVE_LENGTH;
	
    public  AutoTakeRecycling() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will1 run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

    	DRIVE_LENGTH = Preferences.getDouble("AutoTakeRecycling_DriveLength");
    	SLIDE_DISTANCE = Preferences.getDouble("AutoTakeRecycling_SlideDistance");
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new AutoFollowLine(DRIVE_LENGTH));
    	addSequential(new AutoGrabRecycling(3));
    	addSequential(new AutoDrive(0, SLIDE_DISTANCE));
    }
}
