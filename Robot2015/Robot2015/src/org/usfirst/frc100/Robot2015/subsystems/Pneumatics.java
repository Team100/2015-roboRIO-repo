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
    PowerDistributionPanel pdp = new PowerDistributionPanel();
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    }
    
    // Starts the compressor if enough current - boolean signifies if the compressor was started sucessfully
    public boolean start(){
    	if(availableCompressorCurrent()){
    	compressor.start();
    	return true;
    	} else {
    		compressor.stop();
    		return false;
    	}
    }
    
    // Stops the compressor
    public void  stop(){
    	compressor.stop();
    }
    
    // Returns if there is enough current to run the compressor
    public boolean availableCompressorCurrent(){
    	return (120.0 - pdp.getTotalCurrent() > compressor.getCompressorCurrent());
    }
}
