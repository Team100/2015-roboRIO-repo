package team100.smartdashboard.extensions;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * A widget that provides an easy way to view and modify preferences.
 * @author Paul
 */
public class Preferences extends StaticWidget {

    public static final String NAME = "Preference Viewer";
    private final JComboBox keyBox = new JComboBox();
    private final JTextField valueField = new JTextField();
    private final ITable prefs = NetworkTable.getTable("Preferences");

    @Override
    public void init() {
        setPreferredSize(new Dimension(415, 40));
        keyBox.setPreferredSize(new Dimension(200, 25));
        valueField.setPreferredSize(new Dimension(200, 25));

        // If the user changes the JComboBox, update the value field
        keyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prefs.containsKey(keyBox.getSelectedItem()+"")){
                    valueField.setText(prefs.getValue(keyBox.getSelectedItem()+"")+"");
                }
            }
        });

        // If the user changes the JTextField, update the subtable
        valueField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                prefs.putString(keyBox.getSelectedItem() + "", valueField.getText());
            }
        });

        // If a key is added to the subtable, it is added to the JComboBox
        // If the value of the selected preference is changed, the JTextArea will be updated
        prefs.addTableListener(new ITableListener() {
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean isNew) {
                if (isNew) {
                    keyBox.addItem(key);
                }
                if (key.equals(keyBox.getSelectedItem())) {
                    valueField.setText(value + "");
                }
            }
        }, true);

        add(keyBox);
        add(valueField);
    }

    @Override
    public void propertyChanged(Property prprt) {
    }
}