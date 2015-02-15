
import static com.oracle.jrockit.jfr.DataType.INTEGER;
import static com.sun.javafx.font.FontConstants.nameTag;
import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
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
    
    public static final DataType[] TYPES = {SubsystemType.get()};
    
    private ITable prefs;

    
    public PDPWidget() {
        setLayout(new GridLayout(0,2));

        ArrayList<JLabel> labelArrayList = new ArrayList<JLabel>();
        ArrayList<AbstractTableWidget.NumberTableField> fieldArrayList = new ArrayList<AbstractTableWidget.NumberTableField>();

        
        
        
       
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        
        revalidate();
        repaint();
        
        prefs.addTableListener(new ITableListener() {
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean isNew) {
                
                labelArrayList.add(new JLabel(key));
                fieldArrayList.add(new AbstractTableWidget.NumberTableField(key));
            }
        }, true);
    }

    @Override
    public void init() {
        nameTag.setText(getFieldName());
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
   
    @Override
    public void setValue(Object o) {
        prefs = (ITable)o;
        super.setValue(o);
    }
}
