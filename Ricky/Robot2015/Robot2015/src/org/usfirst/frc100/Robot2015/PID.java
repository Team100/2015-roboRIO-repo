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
        this.name = name;
        timer.start();
        SmartDashboard.putNumber(name + "_kP", kP);
        SmartDashboard.putNumber(name + "_kI", kI);
        SmartDashboard.putNumber(name + "_kD", kD);
        displayData();
    }

    // Updates the PID loop using new input data
    public void update(double newValue) {
        kP = SmartDashboard.getNumber(name + "_kP");
        kI = SmartDashboard.getNumber(name + "_kI");
        kD = SmartDashboard.getNumber(name + "_kD");
        Preferences.set(name + "_kP", kP);
        Preferences.set(name + "_kI", kI);
        Preferences.set(name + "_kD", kD);
        interval = timer.get();
        input = newValue * Preferences.getDouble(name + "SensorRatio") - offset;
        lastError = error;
        error = target - input;
        totalError += error * interval;
        if(interval>0.0 && interval<1.0){
        	output = kP * error + kI * totalError + kD * (error - lastError) / interval;
        	timer.reset();
        	displayData();
        } else {
        	output = kP * error;
        	reset();
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
        reset();
    }

    // Returns the current target for the PID loop
    public double getTarget() {
        return target;
    }

    // Clears any built up error
    public void reset() {
        totalError = 0.0;
        lastError = error;
        timer.reset();
        displayData();
    }

    // Sets the current location to be a specified value, but does NOT reset the target
    public void setRelativeLocation(double value) {
    	offset += target - error - value;
    	error = target - value;
    	reset();
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
        SmartDashboard.putNumber(name + "Error", error);
        SmartDashboard.putNumber(name + "Target", target);
        SmartDashboard.putNumber(name + "Input", input);
        SmartDashboard.putNumber(name + "Output", output);
        SmartDashboard.putNumber(name + "Interval", interval);
    }
}