package org.usfirst.frc100.SlideWinder.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraVision extends Subsystem {
	
	private double cycleTime = 0; // Default value
	
	Timer timer = new Timer();
	
	/**
	 * Called once when robot turns on
	 */
	public CameraVision() {
		SmartDashboard.putNumber("CycleTime", cycleTime);
		
		// Initialization code here
	}
	
	@Override
	protected void initDefaultCommand() {		
	}

	/**
	 * Called once in auto init
	 */
	public void initCamera() {
		timer.start();
		cycleTime = SmartDashboard.getNumber("CyleTime");
		
		// More init code here
	}

	/**
	 * Gets the distance in pixels from the line
	 * @return The offset in pixels
	 */
	public int getLineOffset() {
		// Calculate offset from line
		return 0;
	}
	
	/**
	 * Updates the camera image
	 */
	public void updateCamera() {
		if(timer.get()<cycleTime){
			return;
		}
		timer.reset();
		
		// Update camera here
	}
	
	/**
	 * 
	 * @param row
	 */
	public void setScanLine(int row) {
		
	}
	
	/**
	 * 
	 * @param reference
	 */
	public void setLineReference(int reference) {
		
	}
	
	/**
	 * Updates the dashboard
	 */
	public void updateDashboard() {
//		SmartDashboard.putNumber("Key", value);
	}
}
