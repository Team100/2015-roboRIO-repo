package org.usfirst.frc100.Robot2015;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Creates a PID loop, has methods to set the target value and get the output.
 * Automatically generates new SmartDashboard values and preferences.
 */
public class PID {

    private final String name; // The loop's unique identifier
    private double kP = 0.0, kI = 0.0, kD = 0.0; // PID constants
    private double input = 0.0; // New value from sensor
    private double target = 0.0; // Target for the PID loop
    private double offset = 0.0; // Offset from input value to the zero value
    private double error = 0.0; // Distance from target value
    private double lastError = 0.0, totalError = 0.0; // Stores previous error
    private double output = 0.0; // Loop output value
    private double interval = 0.0; // Time between loops
    private double rate = 0.0; // Change in error per second
    private Timer timer = new Timer(); // Keeps track of loop frequency
    private boolean enabled = true;

    // Instantiates a PID loop, requires a unique name for creating preferences
    public PID(String name) {
        if (Preferences.contains(name + "_kP")) {
            kP = Preferences.getDouble(name + "_kP");
        } else {
            Preferences.set(name + "_kP", 0.0);
        }
        if (Preferences.contains(name + "_kI")) {
            kI = Preferences.getDouble(name + "_kI");
        } else {
            Preferences.set(name + "_kI", 0.0);
        }
        if (Preferences.contains(name + "_kD")) {
            kD = Preferences.getDouble(name + "_kD");
        } else {
            Preferences.set(name + "_kD", 0.0);
        }
        if (Preferences.contains(name + "InitialOffset")) {
            offset = Preferences.getDouble(name + "InitialOffset");
        } else {
            Preferences.set(name + "InitialOffset", 0.0);
        }
        if (!Preferences.contains(name + "SensorRatio")) {
            Preferences.set(name + "SensorRatio", 1.0);
        }
        if (!Preferences.contains(name + "ErrorTolerance")) {
            Preferences.set(name + "ErrorTolerance", 0.0);
        }
        if (!Preferences.contains(name + "RateTolerance")) {
            Preferences.set(name + "RateTolerance", Double.MAX_VALUE);
        }
        this.name = name;
        timer.start();
        SmartDashboard.putNumber(name + " kP", kP);
        SmartDashboard.putNumber(name + " kI", kI);
        SmartDashboard.putNumber(name + " kD", kD);
        displayData();
    }

    // Updates the PID loop using new input data
    public void update(double newValue) {
        kP = SmartDashboard.getNumber(name + " kP");
        kI = SmartDashboard.getNumber(name + " kI");
        kD = SmartDashboard.getNumber(name + " kD");
        Preferences.set(name + "_kP", kP);
        Preferences.set(name + "_kI", kI);
        Preferences.set(name + "_kD", kD);
        interval = timer.get();
        input = newValue * Preferences.getDouble(name + "SensorRatio") - offset;
        lastError = error;
        error = target - input;
        totalError += error * interval;
        rate = (error - lastError) / interval;
        if(interval>0.0 && interval<1.0){
        	output = kP * error + kI * totalError + kD * rate;
        	timer.reset();
        	displayData();
        } else {
        	output = kP * error;
        	resetError();
        }
    }

    // The current error of the loop
    public double getError(){
    	return error;
    }
    
    // Returns the current output value
    public double getOutput() {
        if (enabled) {
            return output;
        } else {
            return 0.0;
        }
    }

    // Sets the target value of the PID loop
    public void setTarget(double newTarget) {
        target = newTarget;
        error = target;
        resetError();
    }

    // Returns the current target for the PID loop
    public double getTarget() {
        return target;
    }

    // Clears any built up error
    public void resetError() {
        totalError = 0.0;
        lastError = error;
        timer.reset();
        displayData();
    }

    // Sets the current location to be a specified value, but does NOT reset the target
    public void setRelativeLocation(double value) {
    	offset += input - value;
    	error = target - value;
    	input = value;
    	resetError();
    }

    // Returns whether the PID loop is close enough to the target value
    public boolean reachedTarget() {
		return Math.abs(error) < Preferences.getDouble(name + "ErrorTolerance")
				&& Math.abs(rate) < Preferences.getDouble(name + "RateTolerance");
    }

    // Allows PID loop to output
    public void enable() {
        enabled = true;
    }

    // Sets PID output to zero
    public void disable() {
        enabled = false;
    }
    
    // Displays data on the SmartDashboard
    private void displayData(){
        SmartDashboard.putNumber(name + " Error", error);
        SmartDashboard.putNumber(name + " Target", target);
        SmartDashboard.putNumber(name + " Input", input);
        SmartDashboard.putNumber(name + " Output", output);
        SmartDashboard.putNumber(name + " Interval", interval);
        SmartDashboard.putNumber(name + " Rate", rate);
        SmartDashboard.putBoolean(name + " Reached Target", reachedTarget());
    }
}