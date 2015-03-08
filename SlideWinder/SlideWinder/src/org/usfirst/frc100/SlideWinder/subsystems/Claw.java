package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The claw attached to the elevator used to pick up totes and recycling
 * containers.
 */
public class Claw extends Subsystem {
	DoubleSolenoid piston = RobotMap.clawPiston;


	public void initDefaultCommand() {
	}

	/**
	 * Getter method for the claw piston
	 * 
	 * @return Whether the claw is closed
	 */
	public boolean isClosed() {
		return piston.get() == DoubleSolenoid.Value.kForward;
	}

	/**
	 * Setter method for the claw piston
	 * 
	 * @param closed - The desired claw position
	 */
	public void setPiston(boolean closed) {
		if (!closed) {
			piston.set(DoubleSolenoid.Value.kReverse);
		} else {
			piston.set(DoubleSolenoid.Value.kForward);
		}
	}

	/**
	 * Updates the SmartDashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putBoolean("Claw/Closed", isClosed());
	}
}
