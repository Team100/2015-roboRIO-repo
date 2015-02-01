package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTakeRecyclingWhileDriving extends CommandGroup {
    private final int containers;
    private final int time;
    private int containersTaken = 0;
    
    /**
     * 
     * @param containers the number of containers to take before exit
     * @param time the time to wait before start
     */
    public  AutoTakeRecyclingWhileDriving(int containers, int time) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

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
    	this.containers = containers;
    	this.time = time;
    }

	public void initialize() {
    	try {
			wait(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void execute() {
    	if (Robot.arm.getContainer()) {
    		Robot.arm.setStab(true);
    		Robot.arm.deployArm(false);
    		Robot.arm.setStab(false);
    		Robot.arm.deployArm(true);
    		try {
				wait(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		containersTaken++;
    	}
    }
    
    public void end() {
    	
    }
    
    public boolean isFinished() {
    	return containers == containersTaken;
    }
}
