package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Triggers the auto arm when a container is detected.
 */
public class AutoGrabRecycling extends Command {
    private final int containers;
    private final int time;
    private int containersTaken = 0;
    private boolean takeRecyclingThisTime = false;
    
    /**
     * @param containers - The number of containers to take before exit
     * @param time - The time to wait before starting
     */
    public  AutoGrabRecycling(int containers, int time) {
    	this.containers = containers;
    	this.time = time;
    }

	public void initialize() {
		Robot.arm.setDeploy(true);
		Robot.arm.setStab(true);
		Robot.arm.setDeploy(false);
		Robot.arm.setStab(false);
		Robot.arm.setStab(true);
	}
    
    public void execute() {
    	if (Robot.arm.getContainer()) {
    		if (takeRecyclingThisTime) {
    			Robot.arm.setStab(true);
    			Robot.arm.setDeploy(false);
    			Robot.arm.setStab(false);
    			Robot.arm.setDeploy(true);
    			containersTaken++;
    		}
    		takeRecyclingThisTime = !takeRecyclingThisTime;
    	}
    }
    
    public void end() {
    	
    }
    
    public boolean isFinished() {
    	return containers == containersTaken;
    }

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}
