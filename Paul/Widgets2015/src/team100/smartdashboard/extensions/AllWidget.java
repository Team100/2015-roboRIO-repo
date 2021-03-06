package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.elements.bindings.AbstractTableWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;

/**
 * One widget to rule them all.
 * @author Team100
 */
public class AllWidget extends AbstractTableWidget{
    
    public static final DataType[] TYPES = {SubsystemType.get()};
    public static final String NAME = "OmniWidget";
    GridLayout layout;
    
    private ITable prefs;
    private ArrayList<Object> labels = new ArrayList<>();
    private ArrayList<EditorTextField> fields = new ArrayList<>();

    public void setValueBetter(Object o) {
        prefs = (ITable)o;
        prefs.addTableListener(new ITableListener() {
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean isNew) {
                if(isNew&&!key.equals("~TYPE~")) {
                    removeAll();
                    labels.add(key);
                    if(Double.class.equals(value.getClass())){
                        fields.add(new NumberTableField(key));
                    } else if(String.class.equals(value.getClass())){
                        fields.add(new StringTableField(key));
                    } else if(Boolean.class.equals(value.getClass())){
                        fields.add(new BooleanTableField(key));
                    } else {
                        labels.remove(key);
                    }
                    fields.get(fields.size()-1).setText(value+"");
                    sort();
                    add(new JLabel("Subsystem "));
                    add(new JLabel(getFieldName()));
                    for(int i=0; i<labels.size();i++){
                        add(new JLabel(labels.get(i)+""));
                        add(fields.get(i));
                    }
                    revalidate();
                    repaint();
                }
            }
        }, true);
    }
    
    @Override
    public void init() {
        layout = new GridLayout(0,2);
        this.setLayout(layout);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setValueBetter(NetworkTable.getTable("SmartDashboard/"+getFieldName()));
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
    
    public void sort() {
        Object[] temp = labels.toArray();
        ArrayList<EditorTextField> tempfields = new ArrayList(fields);
        Arrays.sort(temp);
        for(int a = 0 ; a < labels.size(); a++){
            for(int b = 0 ; b < labels.size(); b++) {
                if((temp[b]+"").equals(labels.get(a)+"")) {
                    tempfields.set(b, fields.get(a));
                }
            }
        }
        fields = tempfields;
        labels = new ArrayList<>(Arrays.asList(temp));
        
    }
}