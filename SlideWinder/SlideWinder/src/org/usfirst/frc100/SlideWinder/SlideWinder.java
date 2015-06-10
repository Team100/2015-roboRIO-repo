package org.usfirst.frc100.SlideWinder;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.SlideWinder.commands.*;
import org.usfirst.frc100.SlideWinder.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SlideWinder extends IterativeRobot {
    public static OI oi;
    public static Drivetrain drivetrain;
    public static Elevator elevator;
    public static Arm arm;
    public static Pneumatics pneumatics;
    public static Claw claw;
    public static CameraVision cameraVision;


	public void robotInit() {
    	Preferences.init();
    	RobotMap.init();

        drivetrain = new Drivetrain();
        elevator = new Elevator();
        arm = new Arm();
        pneumatics = new Pneumatics();
        claw = new Claw();
        cameraVision = new CameraVision();
        oi = new OI();

        SmartDashboard.putData(Scheduler.getInstance());
    }

	public void disabledInit(){
		super.disabledInit();
    	Scheduler.getInstance().removeAll();
    	RobotMap.stopAllMotors();
    }

	public void disabledPeriodic() {
		super.disabledPeriodic();
    	RobotMap.stopAllMotors();
    }

	public void autonomousInit() {
    	Scheduler.getInstance().removeAll();
    	elevator.setOverride(false);
    	int modeSelect = oi.selector();
		switch (modeSelect) {
			case 1:
				new AutoDrive(Preferences.getDouble("AutoDriveForward_Distance"), 0.0, false).start();
				break;
			case 2:
				new AutoMode_PullBinBack(1).start();
				break;
			case 3:
				new AutoMode_PullBinBack(0).start();
				break;
			case 4:
				new AutoMode_ThreeTotes(false).start();
				break;
			case 5:
				new AutoMode_ThreeTotes(true).start();
				break;
//			Arm Autonomi
			case 6:
				new AutoMode_TakeRecycling().start();
				break;
//			case 6:
//				new AutoFollowLine(48).start();
//				break;
//			case 7:
//				new AutoVisionFollowLine(48).start();
//				break;
//			case 8:
//				new AutoModeSeven_TakeRecycling().start();
//				break;
//			case 9:
//				new AutoModeEight_TakeRecyclingWithLineReaders().start();
//				break;
//			case 10:
//				new AutoModeNine_TakeRecyclingWithVision().start();
//				break;
			default:
//				new Immobilize().start();
				break;
		}
        new UpdateDashboard().start();
        drivetrain.shift(true);
    }

	public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        pneumatics.run();
    }

	public void teleopInit() {
    	Scheduler.getInstance().removeAll();
    	elevator.setOverride(false);
        new UpdateDashboard().start();
//        new AutoCalibrateElevator().start();
        drivetrain.shift(true);
    }

	public void teleopPeriodic() {
        Scheduler.getInstance().run();
        pneumatics.run();
        oi.updateDPad();
    }

	public void testPeriodic() {
        LiveWindow.run();
    }
}
