package org.usfirst.frc100.SlideWinder.subsystems;

import org.usfirst.frc100.SlideWinder.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The compressor.
 */
public class Pneumatics extends Subsystem {
	Compressor compressor = RobotMap.pneumaticsCompressor;

	PowerDistributionPanel pdp = new PowerDistributionPanel();

	public void initDefaultCommand() {
	}

	/**
	 * Starts the compressor if enough current
	 * 
	 * @return Whether the compressor started successfully
	 */
	public boolean run() {
		if (availableCompressorCurrent()) {
			compressor.start();
			return true;
		} else {
			compressor.stop();
			return false;
		}
	}

	/**
	 * Stops the compressor
	 */
	public void stop() {
		compressor.stop();
	}

	/**
	 * Determines if available current is enough to run the compressor
	 * 
	 * @return - Whether there is enough current to run the compressor
	 */
	public boolean availableCompressorCurrent() {
		return true;
		//return (120.0 - pdp.getTotalCurrent() > compressor
			//.getCompressorCurrent());
	}
}
