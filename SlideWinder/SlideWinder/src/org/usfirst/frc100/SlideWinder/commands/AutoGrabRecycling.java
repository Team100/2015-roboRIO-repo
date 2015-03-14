//package org.usfirst.frc100.SlideWinder.commands;
//
//import org.usfirst.frc100.SlideWinder.Preferences;
//import org.usfirst.frc100.SlideWinder.SlideWinder;
//
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.command.Command;
//
///**
// * Triggers the auto arm when a container is detected.
// */
//public class AutoGrabRecycling extends Command {
//	private final double TIME_AFTER_RETRACT;
//	private final double TIME_AFTER_STAB;
//	private final double TIME_AFTER_DEPLOY;
//
//	/**
//	 * @param containers - The number of containers to take before exit
//	 * @param time - The time to wait before starting
//	 */
//	public AutoGrabRecycling() {
//		TIME_AFTER_DEPLOY = Preferences
//				.getDouble("AutoGrabRecycling_TIME_AFTER_DEPLOY");
//		TIME_AFTER_STAB = Preferences
//				.getDouble("AutoGrabRecycling_TIME_AFTER_GRAB");
//		TIME_AFTER_RETRACT = Preferences
//				.getDouble("AutoGrabRecycling_TIME_AFTER_RETRACT");
//	}
//
//	public void initialize() {
//		SlideWinder.arm.setDeploy(true);
//		Timer.delay(TIME_AFTER_DEPLOY);
//		SlideWinder.arm.setStab(true);
//		Timer.delay(TIME_AFTER_STAB);
//		SlideWinder.arm.setDeploy(false);
//		Timer.delay(TIME_AFTER_RETRACT);
//		SlideWinder.arm.setStab(false);
//	}
//
//	public void execute() {
//
//	}
//
//	public void end() {
//
//	}
//
//	public boolean isFinished() {
//		return true;
//	}
//
//	@Override
//	protected void interrupted() {
//		// TODO Auto-generated method stub
//	}
//}
