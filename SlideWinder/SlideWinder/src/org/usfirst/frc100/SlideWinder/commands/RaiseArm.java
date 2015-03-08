//package org.usfirst.frc100.SlideWinder.commands;
//
//import edu.wpi.first.wpilibj.command.Command;
//
//import org.usfirst.frc100.SlideWinder.Preferences;
//import org.usfirst.frc100.SlideWinder.SlideWinder;
//
///**
// * Raises or lowers the autonomous arm, depending on the boolean value
// * specified in the constructor.
// */
//public class RaiseArm extends Command {
//
//	private boolean isFinished = false;
//	private final boolean raise;
//
//	/**
//	 * @param raise - Raise or lower the arm
//	 */
//    public RaiseArm(boolean raise) {
//        requires(SlideWinder.arm);
//        this.raise = raise;
//    }
//
//    // Called just before this Command runs the first time
//    protected void initialize() {
//    	if(raise){
//    		SlideWinder.arm.setArmHeight(Preferences.getDouble("ElevatorMaxHeight"));
//    	} else {
//    		SlideWinder.arm.setArmHeight(Preferences.getDouble("ElevatorMinHeight"));
//    	}
//    	isFinished = false;
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    protected void execute() {
//    	isFinished = SlideWinder.arm.updateArm();
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    protected boolean isFinished() {
//        return isFinished;
//    }
//
//    // Called once after isFinished returns true
//    protected void end() {
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    protected void interrupted() {
//    	end();
//    }
//}
