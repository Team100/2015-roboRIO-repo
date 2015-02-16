package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.smartdashboard.types.NamedDataType;
import java.awt.GridLayout;
import javax.swing.JLabel;

/**
 * Measures power from the PDP.
 * @author Me
 */
public class PDP_Widget extends AbstractTableWidget {
    
    public static final String NAME = "PDP Table";
    public static final DataType[] TYPES = {PDP_Type.get()};
        
    AbstractTableWidget.NumberTableField[] fields = new AbstractTableWidget.NumberTableField[16];
    JLabel[] labels = new JLabel[16];
    AbstractTableWidget.NumberTableField voltField;
    JLabel voltLabel;
    
    @Override
    public void init() {
        setLayout(new GridLayout(17,2));
        voltLabel = new JLabel("PDP Voltage");
        voltField = new AbstractTableWidget.NumberTableField("PDP Voltage");
        add(voltLabel);
        add(voltField);
        for (int a = 0; a < 16; a++){
            labels[a] = new JLabel("PDP Port " +a);
            fields[a] = new AbstractTableWidget.NumberTableField("PDP Port " +a);
            add(labels[a]);
            add(fields[a]);
        }
    }

    @Override
    public void propertyChanged(Property prprt) {
       
    }

    private static class PDP_Type extends NamedDataType{

        public static final String LABEL = "PDP Current";
        
        public static NamedDataType get(){
            if(NamedDataType.get(LABEL) != null){
                return NamedDataType.get(LABEL);
            } else {
                return new PDP_Type();
            }
        }
        
        private PDP_Type() {
            super(LABEL, PDP_Widget.class);
        }
    }
}
