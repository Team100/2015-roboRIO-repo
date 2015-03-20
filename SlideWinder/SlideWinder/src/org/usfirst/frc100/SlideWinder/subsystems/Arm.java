package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The autonomous arm used to grab recycling containers from the step.
 */
public class Arm extends Subsystem {
	
//	DoubleSolenoid x_Piston = RobotMap.arm_X_Piston;
	DoubleSolenoid y_Piston = RobotMap.arm_Y_Piston;
	
	public void initDefaultCommand() {
	}

	/**
	 * Returns the current state of the arm
	 * 
	 * @return Whether the arm is attempting to grab a recycling container
	 */
	public boolean isGrabbing() {
		return y_Piston.get() == DoubleSolenoid.Value.kForward;
	}

	/**
	 * Sets the arm to grab or pull back
	 *
	 * @param extend - whether the grabber should be extended or retracted
	 */
	public void deploy(boolean extend) {
		if (extend) {
//			x_Piston.set(DoubleSolenoid.Value.kForward);
			y_Piston.set(DoubleSolenoid.Value.kForward);
		} else {
//			x_Piston.set(DoubleSolenoid.Value.kReverse);
			y_Piston.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * Updates the SmartDashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putBoolean("Arm/Grabbing", isGrabbing());
	}
}
