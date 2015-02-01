package org.usfirst.frc100.Robot2015;

import org.usfirst.frc100.Robot2015.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton gradualDriveButton;
    public JoystickButton slideButton;
    public Joystick leftJoystick;
    public JoystickButton shiftButton;
    public Joystick rightJoystick;
    public JoystickButton nonScoringButton;
    public JoystickButton coopertitionButton;
    public JoystickButton openClawButton;
    public JoystickButton calibrateElevatorButton;
    public JoystickButton closeClawButton;
    public JoystickButton manualControlButton;
    public JoystickButton liftToteButton4;
    public JoystickButton liftToteButton3;
    public JoystickButton liftToteButton2;
    public JoystickButton liftToteButton1;
    public Joystick manipulatorJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        manipulatorJoystick = new Joystick(2);
        
        liftToteButton1 = new JoystickButton(manipulatorJoystick, 1);
        liftToteButton1.whenPressed(new SetElevatorPosition(1));
        liftToteButton2 = new JoystickButton(manipulatorJoystick, 2);
        liftToteButton2.whenPressed(new SetElevatorPosition(2));
        liftToteButton3 = new JoystickButton(manipulatorJoystick, 3);
        liftToteButton3.whenPressed(new SetElevatorPosition(3));
        liftToteButton4 = new JoystickButton(manipulatorJoystick, 4);
        liftToteButton4.whenPressed(new SetElevatorPosition(4));
        manualControlButton = new JoystickButton(manipulatorJoystick, 5);
        manualControlButton.whileHeld(new ManualControl());
        closeClawButton = new JoystickButton(manipulatorJoystick, 6);
        closeClawButton.whenPressed(new CloseClaw());
        calibrateElevatorButton = new JoystickButton(manipulatorJoystick, 7);
        calibrateElevatorButton.whenPressed(new AutoCalibrateElevator());
        openClawButton = new JoystickButton(manipulatorJoystick, 8);
        openClawButton.whenPressed(new OpenClaw());
        coopertitionButton = new JoystickButton(manipulatorJoystick, 9);
        nonScoringButton = new JoystickButton(manipulatorJoystick, 10);
        rightJoystick = new Joystick(1);
        
        shiftButton = new JoystickButton(rightJoystick, 1);
        shiftButton.whileHeld(new Shift());
        leftJoystick = new Joystick(0);
        
        slideButton = new JoystickButton(leftJoystick, 1);
        slideButton.whileHeld(new SlideStraight());
        gradualDriveButton = new JoystickButton(leftJoystick, 2);
        gradualDriveButton.whileHeld(new GradualDrive());

	    /*
        // SmartDashboard Buttons
        SmartDashboard.putData("SetElevatorPosition", new SetElevatorPosition());

        SmartDashboard.putData("Drive", new Drive());

        SmartDashboard.putData("DeployArm", new DeployArm());

        SmartDashboard.putData("RaiseArm", new RaiseArm());

        SmartDashboard.putData("Shift", new Shift());

        SmartDashboard.putData("AutoDrive", new AutoDrive());

        SmartDashboard.putData("AutoTurn", new AutoTurn());

        SmartDashboard.putData("AutoTakeRecycling", new AutoTakeRecycling());

        SmartDashboard.putData("AutoStackTotes", new AutoStackTotes());

        SmartDashboard.putData("AutoDelay", new AutoDelay());

        SmartDashboard.putData("OpenClaw", new OpenClaw());

        SmartDashboard.putData("CloseClaw", new CloseClaw());

        SmartDashboard.putData("ManualControl", new ManualControl());

        SmartDashboard.putData("GradualDrive", new GradualDrive());

        SmartDashboard.putData("AutoCalibrateElevator", new AutoCalibrateElevator());

        SmartDashboard.putData("UpdateDashboard", new UpdateDashboard());

        SmartDashboard.putData("SlideStraight", new SlideStraight());

        SmartDashboard.putData("AutoFollowLine", new AutoFollowLine());

        SmartDashboard.putData("SyncPreferences", new SyncPreferences());

        SmartDashboard.putData("AutoSlideToLine", new AutoSlideToLine());

        SmartDashboard.putData("TestPID", new TestPID());

        SmartDashboard.putData("AutoGrabRecycling", new AutoGrabRecycling());


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        */
        
        SmartDashboard.putData("LoadPreferences", new SyncPreferences(true, true));
        SmartDashboard.putData("SavePreferences", new SyncPreferences(false, true));        
        SmartDashboard.putData("ReadPreference", new SyncPreferences(true, false));
        SmartDashboard.putData("WritePreference", new SyncPreferences(false, false));
        
    }
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }

    public Joystick getManipulatorJoystick() {
        return manipulatorJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

