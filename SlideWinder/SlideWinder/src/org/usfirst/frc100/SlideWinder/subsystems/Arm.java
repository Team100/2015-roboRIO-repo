package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.PID;
import org.usfirst.frc100.SlideWinder.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The autonomous arm used to grab recycling containers from the step.
 */
public class Arm extends Subsystem {
	SpeedController raiseMotor = RobotMap.armRaiseMotor;
	DoubleSolenoid stabPiston = RobotMap.armStabPiston;
	DigitalInput containerSensor = RobotMap.armContainerSensor;
	AnalogPotentiometer potentiometer = RobotMap.armPotentiometer;
	DigitalInput forwardLimit = RobotMap.armForwardLimit;
	DigitalInput backLimit = RobotMap.armBackLimit;
	DoubleSolenoid deployPiston = RobotMap.armDeployPiston;

	PID armPID = new PID("Arm");

	public void initDefaultCommand() {
	}

	public boolean getContainer() {
		return containerSensor.get();
	}

	/**
	 * @param raise - Value for height motor
	 */
	public void manualControl(double raise) {
		raiseMotor.set(raise);
	}

	// Returns whether the arm's grabbing mechanism is closed
	public boolean isGrabbing() {
		return stabPiston.get() == DoubleSolenoid.Value.kForward;
	}

	/**
	 * Sets the arm to open or close the grabbing mechanism
	 * 
	 * @param grab - whether the grabber should grab or release a bin
	 */
	public void setStab(boolean stab) {
		if (stab) {
			stabPiston.set(DoubleSolenoid.Value.kForward);
		} else {
			stabPiston.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void toggleStab() {
		if (stabPiston.get() == DoubleSolenoid.Value.kForward) {
			stabPiston.set(DoubleSolenoid.Value.kReverse);
		} else {
			stabPiston.set(DoubleSolenoid.Value.kForward);
		}
	}

	/**
	 * Sets the arm to extend or retract the grabbing mechanism
	 * 
	 * @param extend - whether the grabber should be extended or retracted
	 */
	public void setDeploy(boolean extend) {
		if (extend) {
			deployPiston.set(DoubleSolenoid.Value.kForward);
		} else {
			deployPiston.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * Toggles the deploy piston
	 */
	public void toggleDeploy() {
		if (deployPiston.get() == DoubleSolenoid.Value.kForward) {
			deployPiston.set(DoubleSolenoid.Value.kReverse);
		} else {
			deployPiston.set(DoubleSolenoid.Value.kForward);
		}
	}

	/**
	 * sets the arm to a particular height
	 * 
	 * @param height - the value of the height between HEIGHT_MIN and HEIGHT_MAX 
	 * TODO add height limits
	 */
	public void setArmHeight(double height) {
		armPID.setTarget(height);
	}

	public boolean updateArm() {
		armPID.update(potentiometer.get());
		raiseMotor.set(armPID.getOutput());
		return armPID.reachedTarget();
	}

	// Updates the SmartDashboard
	public void updateDashboard() {
		SmartDashboard.putBoolean("Arm/Grabbing", isGrabbing());
		SmartDashboard.putBoolean("Arm/Container Sensor", containerSensor.get());
		SmartDashboard.putBoolean("Arm/Forward Limit", forwardLimit.get());
		SmartDashboard.putBoolean("Arm/Back Limit", backLimit.get());
		SmartDashboard.putNumber("Arm/Potentiometer", potentiometer.get());
	}
}
