package org.usfirst.frc100.Robot2015.subsystems;

import org.usfirst.frc100.Robot2015.RobotMap;
import org.usfirst.frc100.Robot2015.commands.*;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The compressor.
 */
public class Pneumatics extends Subsystem {
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    Compressor compressor = RobotMap.pneumaticsCompressor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    }
    
    // Starts the compressor
    public void start(){
    	compressor.start();
    }
    
    // Stops the compressor
    public void  stop(){
    	compressor.stop();
    }
}
