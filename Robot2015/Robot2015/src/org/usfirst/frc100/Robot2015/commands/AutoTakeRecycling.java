package org.usfirst.frc100.Robot2015.commands;
import org.usfirst.frc100.Robot2015.Preferences;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode that moves all of the recycling bins onto our side of the field.
 */
public class AutoTakeRecycling extends CommandGroup {
    
	private final double DRIVE_1LENGTH;
	private final double DRIVE_2LENGTH;
	private final double SLIDE_DISTANCE;
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

    	DRIVE_1LENGTH = Preferences.getDouble("AutoTakeRecycling_Drive1Length");
    	DRIVE_2LENGTH = Preferences.getDouble("AutoTakeRecycling_Drive2Length");
    	SLIDE_DISTANCE = Preferences.getDouble("AutoTakeRecycling_SlideDistance");
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new DeployArm(true));
    	addSequential(new GrabRecycling());
    	addParallel(new DeployArm(false));
    	addParallel(new DropRecycling());
    	addParallel(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_1LENGTH));
    	addSequential(new GrabRecycling());
    	addParallel(new DeployArm(false));
    	addParallel(new DropRecycling());
    	addParallel(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_2LENGTH));
    	addSequential(new GrabRecycling());
    	addParallel(new DeployArm(false));
    	addParallel(new DropRecycling());
    	addParallel(new DeployArm(true));
    	addSequential(new AutoDrive(DRIVE_1LENGTH));
    	addSequential(new GrabRecycling());
    	addSequential(new DeployArm(false));
    	addSequential(new DropRecycling());
    	addSequential(new AutoDrive(0, SLIDE_DISTANCE));
    }
}
