
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
import javax.swing.JTextArea;
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
public class AllWidget extends AbstractTableWidget{
    
    public static final DataType[] TYPES = {SubsystemType.get()};
    GridLayout layout;
    
    private ITable prefs;
    private ArrayList<JLabel> labelArrayList = new ArrayList<JLabel>();
    private ArrayList<AbstractTableWidget.NumberTableField> fieldArrayList = new ArrayList<AbstractTableWidget.NumberTableField>();

    @Override
    public void setValue(Object o) {
        add(new JLabel("Subsystem: "));
        add(new JLabel(this.getFieldName()));
        prefs = (ITable)o;
        prefs.addTableListener(new ITableListener() {
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean isNew) {
                if(isNew&&!key.equals("~TYPE~")) {
                    labelArrayList.add(new JLabel(key));
                    fieldArrayList.add(new AbstractTableWidget.NumberTableField(key));
                    add(labelArrayList.get(labelArrayList.size()-1));
                    add(fieldArrayList.get(labelArrayList.size()-1));
                    fieldArrayList.get(labelArrayList.size()-1).setText(value+"");
                    revalidate();
                    repaint();
                }
            }
        }, true);
        super.setValue(o);
    }
    
    @Override
    public void init() {
           
        layout = new GridLayout(0,2);
        this.setLayout(layout);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        
        
        
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
   
    
}
