package org.usfirst.frc100.Robot2015;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.Robot2015.commands.*;
import org.usfirst.frc100.Robot2015.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	private static final boolean stealRecyclingDuringAuto = true;
	
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drivetrain drivetrain;
    public static Elevator elevator;
    public static Arm arm;
    public static Pneumatics pneumatics;
    public static Claw claw;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public void robotInit() {
    	Preferences.read();
    	RobotMap.init();
    	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrain = new Drivetrain();
        elevator = new Elevator();
        arm = new Arm();
        pneumatics = new Pneumatics();
        claw = new Claw();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        oi = new OI();
        
        SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData(Robot.drivetrain);
        SmartDashboard.putData(Robot.elevator);
        SmartDashboard.putData(Robot.arm);
        SmartDashboard.putData(Robot.claw);
    }

    public void disabledInit(){
    	Scheduler.getInstance().removeAll();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
    	Scheduler.getInstance().removeAll();
        if(stealRecyclingDuringAuto){
        	new AutoTakeRecycling().start();
        } else {
        	new AutoStackTotes().start();
        }
        new UpdateDashboard().start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        pneumatics.run();
    }

    public void teleopInit() {
    	Scheduler.getInstance().removeAll();
        new UpdateDashboard().start();
        new AutoCalibrateElevator().start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        pneumatics.run();
    }

    public void testPeriodic() {
        LiveWindow.run();
    }
}
