
import static com.sun.javafx.font.FontConstants.nameTag;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import static sun.font.TrueTypeFont.nameTag;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student
 */
public class PDPWidget extends AbstractTableWidget{
    
    public static final DataType TYPES = {PDP_Type.get()};
    
    public AbstractTableWidget.NumberTableField xField;
    public AbstractTableWidget.NumberTableField yField;
    
    private JLabel xLabel;
    private JLabel yLabel;
    
    public PDPWidget() {
        setLayout(new GridBagLayout());
        xLabel = new JLabel("X:");
        yLabel = new JLabel("Y:");
        xLabel.setHorizontalAlignment(JLabel.RIGHT);
        yLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        xField = new AbstractTableWidget.NumberTableField("x");
        yField = new AbstractTableWidget.NumberTableField("y");
        
        
            int columns = 10;
        xField.setColumns(columns);
        yField.setColumns(columns);
        
        
        GridBagConstraints c = new GridBagConstraints();
        
        
        c.gridy = 1;
        add(xLabel, c);
        c.gridy = 2;
        add(yLabel, c);
        
        
        c.gridx = 1;
        c.weightx = 1.0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
            add(nameTag = new nameTag(""), c);
        nameTag.setHorizontalAlignment(JLabel.LEFT);
        c.gridy = 1;
        add(xField, c);
        c.gridy = 2;
        add(yField, c);
        
        setMaximumSize(new Dimension());
                
    }

    @Override
    public void init() {
        
    }

    @Override
    public void propertyChanged(Property prprt) {
        
    }
    
}
