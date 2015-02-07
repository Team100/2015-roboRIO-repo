package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Preferences;
import org.usfirst.frc100.Robot2015.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Triggers the auto arm when a container is detected.
 */
public class AutoGrabRecycling extends Command {
    private final int containers;
    private final double TIME_AFTER_INITIAL_DEPLOY;
    private final double TIME_AFTER_STAB;
    private final double TIME_AFTER_DEPLOY;
    private int containersTaken = 0;
    private boolean takeRecyclingThisTime = false;
    
    /**
     * @param containers - The number of containers to take before exit
     * @param time - The time to wait before starting
     */
    public  AutoGrabRecycling(int containers) {
    	TIME_AFTER_INITIAL_DEPLOY = Preferences.getDouble("AutoGrabRecycling_TIME_AFTER_1ST_DEPLOY");
    	TIME_AFTER_STAB = Preferences.getDouble("AutoGrabRecycling_TIME_AFTER_STAB");
    	TIME_AFTER_DEPLOY = Preferences.getDouble("AutoGrabRecycling_TIME_AFTER_2ND_DEPLOY");
    	this.containers = containers;
    }

	public void initialize() {
		Robot.arm.setDeploy(true);
		Timer.delay(TIME_AFTER_INITIAL_DEPLOY);
		Robot.arm.setStab(true);
		Timer.delay(TIME_AFTER_STAB);
		Robot.arm.setDeploy(false);
		Timer.delay(TIME_AFTER_DEPLOY);
		Robot.arm.setStab(false);
		Robot.arm.setDeploy(true);
	}
    
    public void execute() {
    	if (Robot.arm.getContainer()) {
    		if (takeRecyclingThisTime) {
    			Robot.arm.setStab(true);
    			Timer.delay(TIME_AFTER_STAB);
    			Robot.arm.setDeploy(false);
    			Timer.delay(TIME_AFTER_DEPLOY);
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
