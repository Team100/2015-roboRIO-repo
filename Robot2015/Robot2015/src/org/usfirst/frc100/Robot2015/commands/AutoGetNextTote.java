package org.usfirst.frc100.Robot2015.commands;

import java.security.spec.EncodedKeySpec;

import org.usfirst.frc100.Robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGetNextTote extends Command {

	final double DISTANCE_TO_TOTE = 9001.0;
	final double DISTANCE_FORWARD = 10;
	int totesDone = 0;
	
    public AutoGetNextTote() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	Robot.drivetrain.setAutoTarget(DISTANCE_FORWARD, 0, 0);
    	Robot.elevator.setPosition(1);
    	while (!Robot.elevator.isInPosition()) {
    		Robot.elevator.updatePID();
    	}
    	Robot.elevator.activateBrake();
    	Robot.claw.setPiston(true);
    	totesDone++;
    }
    
    protected void execute() {
    	Robot.elevator.releaseBrake();
    	Robot.elevator.setPosition(totesDone);
    	while (!Robot.elevator.isInPosition()) {
    		Robot.elevator.updatePID();
    	}
    	Robot.claw.setPiston(false);
    	Robot.elevator.setPosition(1);
    	while (!Robot.elevator.isInPosition()) {
    		Robot.elevator.updatePID();
    	}
    	Robot.elevator.activateBrake();
    	Robot.claw.setPiston(true);
    	totesDone++;
    	
    }
    
    protected boolean isFinished() {
    	return totesDone == 4;
    }
    
    protected void end() {
    	Robot.elevator.releaseBrake();
    }

	protected void interrupted() {
		end();
	}
}

