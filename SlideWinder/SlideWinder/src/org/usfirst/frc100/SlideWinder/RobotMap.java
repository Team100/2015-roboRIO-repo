package org.usfirst.frc100.SlideWinder;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static SpeedController drivetrainLeftMotor;
    public static SpeedController drivetrainRightMotor;
    public static RobotDrive drivetrainRobotDrive;
    public static SpeedController drivetrainSlideMotor;
    public static DoubleSolenoid drivetrainShifter;
    public static Encoder drivetrainLeftEncoder;
    public static Encoder drivetrainRightEncoder;
    public static Encoder drivetrainSlideEncoder;
    public static Gyro drivetrainGyro;
    public static AnalogInput drivetrainLeftLineReader;
    public static AnalogInput drivetrainRightLineReader;
    public static SpeedController elevatorMotor1;
    public static SpeedController elevatorMotor2;
    public static Encoder elevatorEncoder;
    public static DigitalInput elevatorUpperLimit;
    public static DigitalInput elevatorLowerLimit;
    public static DoubleSolenoid arm_Y_Piston;
//    public static DoubleSolenoid arm_X_Piston;
    public static Compressor pneumaticsCompressor;
    public static DoubleSolenoid clawPiston;

	public static AnalogTrigger drivetrainLeftLineReadTrigger;
	public static AnalogTrigger drivetrainRightLineReadTrigger;

	/**
	 * Initializes actuators/sensors and adds them to LiveWindow
	 */
	public static void init() {
        drivetrainLeftMotor = new VictorSP(0);
        LiveWindow.addActuator("Drivetrain", "LeftMotor", (VictorSP) drivetrainLeftMotor);

        drivetrainRightMotor = new VictorSP(1);
        LiveWindow.addActuator("Drivetrain", "RightMotor", (VictorSP) drivetrainRightMotor);

        drivetrainRobotDrive = new RobotDrive(drivetrainLeftMotor, drivetrainRightMotor);

        drivetrainRobotDrive.setSafetyEnabled(true);
        drivetrainRobotDrive.setExpiration(0.1);
        drivetrainRobotDrive.setSensitivity(0.5);
        drivetrainRobotDrive.setMaxOutput(1.0);


        drivetrainSlideMotor = new VictorSP(2);
        LiveWindow.addActuator("Drivetrain", "SlideMotor", (VictorSP) drivetrainSlideMotor);

        drivetrainShifter = new DoubleSolenoid(0, 0, 1);
        LiveWindow.addActuator("Drivetrain", "Shifter", drivetrainShifter);

        drivetrainLeftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "LeftEncoder", drivetrainLeftEncoder);
        drivetrainLeftEncoder.setDistancePerPulse(1.0);
        drivetrainRightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", drivetrainRightEncoder);
        drivetrainRightEncoder.setDistancePerPulse(1.0);
        drivetrainSlideEncoder = new Encoder(4, 5, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "SlideEncoder", drivetrainSlideEncoder);
        drivetrainSlideEncoder.setDistancePerPulse(1.0);
        drivetrainGyro = new Gyro(0);
        LiveWindow.addSensor("Drivetrain", "Gyro", drivetrainGyro);
        drivetrainGyro.setSensitivity(0.007);
        drivetrainLeftLineReader = new AnalogInput(2);
        LiveWindow.addSensor("Drivetrain", "LeftLineReader", drivetrainLeftLineReader);

        drivetrainRightLineReader = new AnalogInput(3);
        LiveWindow.addSensor("Drivetrain", "RightLineReader", drivetrainRightLineReader);

        elevatorMotor1 = new VictorSP(3);
        LiveWindow.addActuator("Elevator", "Motor", (VictorSP) elevatorMotor1);
//        elevatorMotor2 = new VictorSP(6);
//        LiveWindow.addActuator("Elevator", "Motor", (VictorSP) elevatorMotor2);

        elevatorEncoder = new Encoder(6, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("Elevator", "Encoder", elevatorEncoder);
        elevatorEncoder.setDistancePerPulse(1.0);

        elevatorUpperLimit = new DigitalInput(8);
        LiveWindow.addSensor("Elevator", "UpperLimit", elevatorUpperLimit);

        elevatorLowerLimit = new DigitalInput(9);
        LiveWindow.addSensor("Elevator", "LowerLimit", elevatorLowerLimit);

        arm_Y_Piston = new DoubleSolenoid(0, 6, 7);
        LiveWindow.addActuator("Arm", "Y_Piston", arm_Y_Piston);
 
//        arm_X_Piston = new DoubleSolenoid(0, 6, 7);
//		LiveWindow.addActuator("Arm", "X_Piston", arm_X_Piston);

        pneumaticsCompressor = new Compressor(0);

        clawPiston = new DoubleSolenoid(0, 4, 5);
        LiveWindow.addActuator("Claw", "Piston", clawPiston);

		drivetrainLeftLineReadTrigger = new AnalogTrigger(
				drivetrainLeftLineReader);
		drivetrainRightLineReadTrigger = new AnalogTrigger(
				drivetrainRightLineReader);
		drivetrainLeftLineReadTrigger.setLimitsRaw(0, 0);
		drivetrainRightLineReadTrigger.setLimitsRaw(0, 0);
	}
	
	public static void stopAllMotors() {
		drivetrainLeftMotor.set(0);
		drivetrainRightMotor.set(0);
		drivetrainSlideMotor.set(0);
		elevatorMotor1.set(0);
	}
}
