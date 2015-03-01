package org.usfirst.frc100.SlideWinder;

import org.usfirst.frc100.SlideWinder.commands.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public Joystick leftJoystick;
        public JoystickButton lowerElevatorButton;
        public JoystickButton gradualDriveButton;
        
    public Joystick rightJoystick;
    	public JoystickButton raiseElevatorButton;
    	public JoystickButton quickTurnButton;
    	public JoystickButton openClawButton2;
    	public JoystickButton closeClawButton2;

    public Joystick manipulatorJoystick;
    	public JoystickButton liftToteButton1;
	    public JoystickButton liftToteButton2;
	    public JoystickButton liftToteButton3;
	    public JoystickButton liftToteButton4;
	    public JoystickButton manualControlButton;
	    public JoystickButton closeClawButton;
	    public JoystickButton calibrateElevatorButton;
	    public JoystickButton openClawButton;
	    public JoystickButton coopertitionButton;
	    public JoystickButton scoringButton;
	    public InternalButton rightDPadButton;	// Right D-Pad
	    public InternalButton feedFirstToteButton;	// Up D-Pad 
	    public InternalButton leftDPadButton;	// Left D-Pad
	    public InternalButton feedNextToteButton;	// Down D-Pad
	    public JoystickButton quickStackButton1;
	    public JoystickButton quickStackButton2;
	    public JoystickButton shiftButton;
    
    public Joystick autoModeSelect;
		public JoystickButton binary1;
		public JoystickButton binary2;
		public JoystickButton binary3;
		public JoystickButton binary4;
    
	public OI() {
        leftJoystick = new Joystick(0); {
	        lowerElevatorButton = new JoystickButton(leftJoystick, 1);
	        lowerElevatorButton.whileHeld(new ManualMoveElevator(false));

	        gradualDriveButton = new JoystickButton(leftJoystick, 2);
	        gradualDriveButton.whileHeld(new GradualDrive());
	        
	        shiftButton = new JoystickButton(leftJoystick, 3);
	        shiftButton.whileHeld(new Shift());
	    }

        rightJoystick = new Joystick(1); {
	        raiseElevatorButton = new JoystickButton(rightJoystick, 1);
	        raiseElevatorButton.whileHeld(new ManualMoveElevator(true));

	        quickTurnButton = new JoystickButton(rightJoystick, 2);
	        quickTurnButton.whileHeld(new AutoTurn(180));

	        openClawButton2 = new JoystickButton(rightJoystick, 4);
	        openClawButton2.whenPressed(new OpenClaw());
	        
	        closeClawButton2 = new JoystickButton(rightJoystick, 5);
	        closeClawButton2.whenPressed(new CloseClaw());
        }

        manipulatorJoystick = new Joystick(2); {
	        liftToteButton1 = new JoystickButton(manipulatorJoystick, 1);
	        liftToteButton1.whenPressed(new SetElevatorPosition(1));

	        liftToteButton2 = new JoystickButton(manipulatorJoystick, 2);
	        liftToteButton2.whenPressed(new SetElevatorPosition(2));

	        liftToteButton3 = new JoystickButton(manipulatorJoystick, 3);
	        liftToteButton3.whenPressed(new SetElevatorPosition(3));

	        liftToteButton4 = new JoystickButton(manipulatorJoystick, 4);
	        liftToteButton4.whenPressed(new SetElevatorPosition(4));

	        manualControlButton = new JoystickButton(manipulatorJoystick, 5);
	        manualControlButton.whileHeld(new ManipulatorManualControl());

	        closeClawButton = new JoystickButton(manipulatorJoystick, 6);
	        closeClawButton.whenPressed(new CloseClaw());

	        calibrateElevatorButton = new JoystickButton(manipulatorJoystick, 7);
	        calibrateElevatorButton.whenPressed(new AutoCalibrateElevator());

	        openClawButton = new JoystickButton(manipulatorJoystick, 8);
	        openClawButton.whenPressed(new OpenClaw());

	        coopertitionButton = new JoystickButton(manipulatorJoystick, 9);

	        scoringButton = new JoystickButton(manipulatorJoystick, 10);

	        quickStackButton1 = new JoystickButton(manipulatorJoystick, 11);
	        quickStackButton1.whenPressed(new QuickStackOne());

	        quickStackButton2 = new JoystickButton(manipulatorJoystick, 12);
	        quickStackButton2.whenPressed(new QuickStackTwo());

	        rightDPadButton = new InternalButton();

	        feedFirstToteButton = new InternalButton();
	        feedFirstToteButton.whenPressed(new SetElevatorPosition(10.5, false));

	        leftDPadButton = new InternalButton();

	        feedNextToteButton = new InternalButton();
	        feedNextToteButton.whenPressed(new FeedFromStation());
        }

        autoModeSelect = new Joystick(3); {
			binary1 = new JoystickButton(autoModeSelect, 1);
	        binary2 = new JoystickButton(autoModeSelect, 2);
	        binary3 = new JoystickButton(autoModeSelect, 3);
	        binary4 = new JoystickButton(autoModeSelect, 4);
        }

	    /*
        // SmartDashboard Buttons
        SmartDashboard.putData("SetElevatorPosition", new SetElevatorPosition());

        SmartDashboard.putData("Drive", new Drive());

        SmartDashboard.putData("DeployArm", new DeployArm());

        SmartDashboard.putData("RaiseArm", new RaiseArm());

        SmartDashboard.putData("Shift", new Shift());

        SmartDashboard.putData("AutoDrive", new AutoDrive());

        SmartDashboard.putData("AutoTurn", new AutoTurn());

        SmartDashboard.putData("AutoModeTwo_TakeRecycling", new AutoModeTwo_TakeRecycling());

        SmartDashboard.putData("AutoModeOne_StackTotes", new AutoModeOne_StackTotes());

        SmartDashboard.putData("AutoDelay", new AutoDelay());

        SmartDashboard.putData("OpenClaw", new OpenClaw());

        SmartDashboard.putData("CloseClaw", new CloseClaw());

        SmartDashboard.putData("ManipulatorManualControl", new ManipulatorManualControl());

        SmartDashboard.putData("GradualDrive", new GradualDrive());

        SmartDashboard.putData("AutoCalibrateElevator", new AutoCalibrateElevator());

        SmartDashboard.putData("UpdateDashboard", new UpdateDashboard());

        SmartDashboard.putData("SlideStraight", new SlideStraight());

        SmartDashboard.putData("AutoFollowLine", new AutoFollowLine());

        SmartDashboard.putData("SyncPreferences", new SyncPreferences());

        SmartDashboard.putData("AutoSlideToLine", new AutoSlideToLine());

        SmartDashboard.putData("TestPID", new TestPID());

        SmartDashboard.putData("AutoGrabRecycling", new AutoGrabRecycling());

        SmartDashboard.putData("AutoVisionFollowLine", new AutoVisionFollowLine());

        SmartDashboard.putData("Immobilize", new Immobilize());
		 */

		SmartDashboard.putData("Load Preferences", new SyncPreferences(true));
		SmartDashboard.putData("Save Preferences", new SyncPreferences(false));
		SmartDashboard.putData("PID/Test DriveDistance PID", new TestPID(
				TestPID.System.DRIVEDISTANCE));
		SmartDashboard.putData("PID/Test DriveSlide PID", new TestPID(
				TestPID.System.DRIVESLIDE));
		SmartDashboard.putData("PID/Test DriveAngle PID", new TestPID(
				TestPID.System.DRIVEANGLE));
		SmartDashboard.putData("PID/Test Elevator PID", new TestPID(
				TestPID.System.ELEVATOR));
		SmartDashboard.putData("PID/Test Arm PID", new TestPID(TestPID.System.ARM));
	}

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }

    public Joystick getManipulatorJoystick() {
        return manipulatorJoystick;
    }
    
    private int getDPad(Joystick stick) {
    	if(stick.getPOV() < 0) {
    		return -1;
    	} else {
    		return stick.getPOV() / 45;
    	}
    }
    
    public void updateDPad() {
    	int value = getDPad(manipulatorJoystick);
    	
    	rightDPadButton.setPressed(value == 0);
    	feedFirstToteButton.setPressed(value == 2);
    	leftDPadButton.setPressed(value == 4);
    	feedNextToteButton.setPressed(value == 6);
    }
    
    public int selector() {
		boolean bin1Val = binary1.get();
    	boolean bin2Val = binary2.get();
    	boolean bin3Val = binary3.get();
    	boolean bin4Val = binary4.get();
    	int total = 0;
    	
    	if(bin1Val){
    		total += 1;
    	}if(bin2Val){
    		total += 2;
    	}if(bin3Val){
    		total += 4;
    	}if(bin4Val){
    		total += 8;
    	}
    	return total;
	}
}
