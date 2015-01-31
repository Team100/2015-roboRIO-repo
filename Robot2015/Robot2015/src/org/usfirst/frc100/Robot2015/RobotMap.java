package org.usfirst.frc100.Robot2015;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController drivetrainLeftMotor;
    public static SpeedController drivetrainRightMotor;
    public static RobotDrive drivetrainRobotDrive;
    public static SpeedController drivetrainSlideMotor;
    public static DoubleSolenoid drivetrainLeftShifter;
    public static DoubleSolenoid drivetrainRightShifter;
    public static DoubleSolenoid drivetrainButterflyPiston;
    public static Encoder drivetrainLeftEncoder;
    public static Encoder drivetrainRightEncoder;
    public static Encoder drivetrainSlideEncoder;
    public static Gyro drivetrainGyro;
    public static AnalogInput drivetrainLeftLineReader;
    public static AnalogInput drivetrainRightLineReader;
    public static SpeedController elevatorMotor;
    public static Encoder elevatorEncoder;
    public static DoubleSolenoid elevatorBrake;
    public static DigitalInput elevatorUpperLimit;
    public static DigitalInput elevatorLowerLimit;
    public static SpeedController armDeployMotor;
    public static SpeedController armRaiseMotor;
    public static DoubleSolenoid armPiston;
    public static DigitalInput armContainerSensor;
    public static AnalogPotentiometer armPotentiometer;
    public static DigitalInput armForwardLimit;
    public static DigitalInput armBackLimit;
    public static Compressor pneumaticsCompressor;
    public static DoubleSolenoid clawPiston;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static AnalogTrigger drivetrainLeftLineReadTrigger;
    public static AnalogTrigger drivetrainRightLineReadTrigger;

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
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
        
        drivetrainLeftShifter = new DoubleSolenoid(0, 0, 1);      
        LiveWindow.addActuator("Drivetrain", "LeftShifter", drivetrainLeftShifter);
        
        drivetrainRightShifter = new DoubleSolenoid(0, 2, 3);      
        LiveWindow.addActuator("Drivetrain", "RightShifter", drivetrainRightShifter);
        
        drivetrainButterflyPiston = new DoubleSolenoid(0, 4, 5);      
        LiveWindow.addActuator("Drivetrain", "ButterflyPiston", drivetrainButterflyPiston);
        
        drivetrainLeftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "LeftEncoder", drivetrainLeftEncoder);
        drivetrainLeftEncoder.setDistancePerPulse(1.0);
        drivetrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        drivetrainRightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", drivetrainRightEncoder);
        drivetrainRightEncoder.setDistancePerPulse(1.0);
        drivetrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        drivetrainSlideEncoder = new Encoder(4, 5, false, EncodingType.k4X);
        LiveWindow.addSensor("Drivetrain", "SlideEncoder", drivetrainSlideEncoder);
        drivetrainSlideEncoder.setDistancePerPulse(1.0);
        drivetrainSlideEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
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
        elevatorEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        elevatorBrake = new DoubleSolenoid(1, 2, 3);      
        LiveWindow.addActuator("Elevator", "Brake", elevatorBrake);
        
        elevatorUpperLimit = new DigitalInput(8);
        LiveWindow.addSensor("Elevator", "UpperLimit", elevatorUpperLimit);
        
        elevatorLowerLimit = new DigitalInput(9);
        LiveWindow.addSensor("Elevator", "LowerLimit", elevatorLowerLimit);
        
        armDeployMotor = new VictorSP(4);
        LiveWindow.addActuator("Arm", "DeployMotor", (VictorSP) armDeployMotor);
        
        armRaiseMotor = new VictorSP(5);
        LiveWindow.addActuator("Arm", "RaiseMotor", (VictorSP) armRaiseMotor);
        
        armPiston = new DoubleSolenoid(0, 6, 7);      
        LiveWindow.addActuator("Arm", "Piston", armPiston);
        
        armContainerSensor = new DigitalInput(10);
        LiveWindow.addSensor("Arm", "ContainerSensor", armContainerSensor);
        
        armPotentiometer = new AnalogPotentiometer(1, 1.0, 0.0);
        LiveWindow.addSensor("Arm", "Potentiometer", armPotentiometer);
        
        armForwardLimit = new DigitalInput(11);
        LiveWindow.addSensor("Arm", "ForwardLimit", armForwardLimit);
        
        armBackLimit = new DigitalInput(12);
        LiveWindow.addSensor("Arm", "BackLimit", armBackLimit);
        
        pneumaticsCompressor = new Compressor(0);
        
        
        clawPiston = new DoubleSolenoid(1, 0, 1);      
        LiveWindow.addActuator("Claw", "Piston", clawPiston);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrainLeftLineReadTrigger.setLimitsRaw(0, 0);
        drivetrainRightLineReadTrigger.setLimitsRaw(0, 0);
        
    }
}
