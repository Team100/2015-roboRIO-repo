package org.usfirst.frc100.SlideWinder;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
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
    public static SpeedController elevatorMotor;
    public static Encoder elevatorEncoder;
    public static DigitalInput elevatorUpperLimit;
    public static DigitalInput elevatorLowerLimit;
    public static SpeedController armRaiseMotor;
    public static DoubleSolenoid armStabPiston;
    public static DigitalInput armContainerSensor;
    public static AnalogPotentiometer armPotentiometer;
    public static DigitalInput armForwardLimit;
    public static DigitalInput armBackLimit;
    public static DoubleSolenoid armDeployPiston;
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
//        drivetrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        drivetrainRightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", drivetrainRightEncoder);
        drivetrainRightEncoder.setDistancePerPulse(1.0);
//        drivetrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        drivetrainSlideEncoder = new Encoder(4, 5, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "SlideEncoder", drivetrainSlideEncoder);
        drivetrainSlideEncoder.setDistancePerPulse(1.0);
//        drivetrainSlideEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        drivetrainGyro = new Gyro(0);
        LiveWindow.addSensor("Drivetrain", "Gyro", drivetrainGyro);
        drivetrainGyro.setSensitivity(0.007);
        drivetrainLeftLineReader = new AnalogInput(2);
        LiveWindow.addSensor("Drivetrain", "LeftLineReader", drivetrainLeftLineReader);
        
        drivetrainRightLineReader = new AnalogInput(3);
        LiveWindow.addSensor("Drivetrain", "RightLineReader", drivetrainRightLineReader);
        
        elevatorMotor = new VictorSP(3);
        LiveWindow.addActuator("Elevator", "Motor", (VictorSP) elevatorMotor);
        
        elevatorEncoder = new Encoder(6, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("Elevator", "Encoder", elevatorEncoder);
        elevatorEncoder.setDistancePerPulse(1.0);
//        elevatorEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);

        elevatorUpperLimit = new DigitalInput(8);
        LiveWindow.addSensor("Elevator", "UpperLimit", elevatorUpperLimit);
        
        elevatorLowerLimit = new DigitalInput(9);
        LiveWindow.addSensor("Elevator", "LowerLimit", elevatorLowerLimit);
        
        armRaiseMotor = new VictorSP(5);
        LiveWindow.addActuator("Arm", "RaiseMotor", (VictorSP) armRaiseMotor);
        
        armStabPiston = new DoubleSolenoid(0, 4, 5);      
        LiveWindow.addActuator("Arm", "StabPiston", armStabPiston);
        
        armContainerSensor = new DigitalInput(10);
        LiveWindow.addSensor("Arm", "ContainerSensor", armContainerSensor);
        
        armPotentiometer = new AnalogPotentiometer(1, 1.0, 0.0);
        LiveWindow.addSensor("Arm", "Potentiometer", armPotentiometer);
        
        armForwardLimit = new DigitalInput(11);
        LiveWindow.addSensor("Arm", "ForwardLimit", armForwardLimit);
        
        armBackLimit = new DigitalInput(12);
        LiveWindow.addSensor("Arm", "BackLimit", armBackLimit);
        
        armDeployPiston = new DoubleSolenoid(0, 6, 7);
		LiveWindow.addActuator("Arm", "DeployPiston", armDeployPiston);
        
        pneumaticsCompressor = new Compressor(0);
        
        
        clawPiston = new DoubleSolenoid(0, 2, 3);      
        LiveWindow.addActuator("Claw", "Piston", clawPiston);

		drivetrainLeftLineReadTrigger = new AnalogTrigger(
				drivetrainLeftLineReader);
		drivetrainRightLineReadTrigger = new AnalogTrigger(
				drivetrainRightLineReader);
		drivetrainLeftLineReadTrigger.setLimitsRaw(0, 0);
		drivetrainRightLineReadTrigger.setLimitsRaw(0, 0);
	}
}
