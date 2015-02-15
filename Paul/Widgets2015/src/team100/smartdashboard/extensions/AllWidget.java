package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * One widget to rule them all.
 * @author Gefen
 */
public class AllWidget extends AbstractTableWidget{
    
    public static final DataType[] TYPES = {SubsystemType.get()};
    GridLayout layout;
    
    private ITable prefs;
    private final ArrayList<JLabel> labelArrayList = new ArrayList<>();
    private final ArrayList<AbstractTableWidget.NumberTableField> fieldArrayList = new ArrayList<>();

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