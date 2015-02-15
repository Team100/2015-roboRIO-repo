
import edu.wpi.first.smartdashboard.types.NamedDataType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class SubsystemType extends NamedDataType{
    public static final String LABEL = "Point";
    private SubsystemType() {
        super(LABEL, PDPWidget.class);
    }
    public static NamedDataType get() {
        if(NamedDataType.get(LABEL) != null){
            return NamedDataType.get(LABEL);
        }else{
            return new SubsystemType();
        }
    }
}
