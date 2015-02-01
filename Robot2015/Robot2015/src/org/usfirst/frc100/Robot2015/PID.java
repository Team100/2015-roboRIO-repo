package org.usfirst.frc100.Robot2015;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Creates a PID loop, has methods to set the target value and get the output.
 * Automatically generates new SmartDashboard values and preferences.
 */
public class PID {

    private final String name; // The loop's unique identifier
    private double kP = 0.0, kI = 0.0, kD = 0.0, kF = 0.0; // PID constants
    private double input = 0.0; // New value from sensor
    private double target = 0.0; // Target for the PID loop
    private double offset = 0.0; // Offset from input value to the zero value
    private double error = 0.0; // Distance from target value
    private double lastError = 0.0, totalError = 0.0; // Stores previous error
    private double output = 0.0; // Loop output value
    private double interval = 0.0; // Time between loops
    private double rate = 0.0; // Change in error per second
    private Timer timer = new Timer(); // Keeps track of loop frequency

    // Instantiates a PID loop, requires a unique name for creating preferences
    public PID(String name) {
    	kP = Preferences.getDouble(name + "_kP");
    	kI = Preferences.getDouble(name + "_kI");
    	kD = Preferences.getDouble(name + "_kD");
    	kF = Preferences.getDouble(name + "_kF");
        SmartDashboard.putNumber(name + " kP", kP);
        SmartDashboard.putNumber(name + " kI", kI);
        SmartDashboard.putNumber(name + " kD", kD);
        SmartDashboard.putNumber(name + " kF", kF);
        this.name = name;
        timer.start();
        displayData();
    }

    // Updates the PID loop using new input data
    public void update(double newValue) {
        kP = SmartDashboard.getNumber(name + " kP");
        kI = SmartDashboard.getNumber(name + " kI");
        kD = SmartDashboard.getNumber(name + " kD");
        kF = SmartDashboard.getNumber(name + " kF");
        Preferences.set(name + "_kP", kP);
        Preferences.set(name + "_kI", kI);
        Preferences.set(name + "_kD", kD);
        Preferences.set(name + "_kF", kF);
        interval = timer.get();
        input = newValue * Preferences.getDouble(name + "SensorRatio") - offset;
        lastError = error;
        error = target - input;
        if(Math.abs(output)<Preferences.getDouble(name + "IntegralLimit") && interval<1.0) totalError += error * interval;
        if(interval!=0) rate = (error - lastError) / interval;
        output = kP * error + kI * totalError + kD * rate;        
        timer.reset();
        displayData();
    }

    // The current error of the loop
    public double getError(){
    	return error;
    }
    
    // Returns the current output value
    public double getOutput() {
    	return output;
    }

    // Sets the target value of the PID loop
    public void setTarget(double newTarget) {
        target = newTarget;
        error = target - input;
        totalError = error*kF;
        displayData();
    }

    // Returns the current target for the PID loop
    public double getTarget() {
        return target;
    }

    // Sets the current location to be a specified value, but does NOT reset the target
    public void setRelativeLocation(double value) {
    	offset += input - value;
    	input = value;
    	setTarget(target);
    }

    // Returns whether the PID loop is close enough to the target value
    public boolean reachedTarget() {
		return Math.abs(error) < Preferences.getDouble(name + "ErrorTolerance")
				&& Math.abs(rate) < Preferences.getDouble(name + "RateTolerance");
    }
    
    // Displays data on the SmartDashboard
    private void displayData(){
        SmartDashboard.putNumber(name + " Error", error);
        SmartDashboard.putNumber(name + " Target", target);
        SmartDashboard.putNumber(name + " Input", input);
        SmartDashboard.putNumber(name + " Output", output);
        SmartDashboard.putNumber(name + " Interval", interval);
        SmartDashboard.putNumber(name + " Rate", rate);
        SmartDashboard.putNumber(name + " TotalError", totalError);
        SmartDashboard.putBoolean(name + " Reached Target", reachedTarget());
    }
}