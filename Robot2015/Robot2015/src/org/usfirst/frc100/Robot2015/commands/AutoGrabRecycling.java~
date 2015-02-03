package org.usfirst.frc100.Robot2015.commands;

import org.usfirst.frc100.Robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrabRecycling extends Command {
    private final int containers;
    private final int time;
    private int containersTaken = 0;
    
    /**
     * 
     * @param containers the number of containers to take before exit
     * @param time the time to wait before start
     */
    public  AutoGrabRecycling(int containers, int time) {
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

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}
}
