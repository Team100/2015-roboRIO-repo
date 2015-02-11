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
 *
 * @author Paul
 */
public class Preferences extends StaticWidget {

    public static final String NAME = "Preference Viewer";
    private final JComboBox keyBox = new JComboBox();
    private final JTextField valueField = new JTextField();
    private final NetworkTable sd = NetworkTable.getTable("SmartDashboard");
    private final ITable prefs = NetworkTable.getTable("Preferences");

    @Override
    public void init() {
        setPreferredSize(new Dimension(415, 40));
        keyBox.setPreferredSize(new Dimension(200, 25));
        valueField.setPreferredSize(new Dimension(200, 25));

        /*
         * If the user changes the JComboBox, start a read command
         */
        keyBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valueField.setText(""+prefs.getValue(keyBox.getSelectedItem()+""));
                sd.putString("Preference Name", keyBox.getSelectedItem() + "");
                if (sd.containsSubTable("Read Preference")) {
                    ITable command = sd.getSubTable("Read Preference");
                    command.putBoolean("running", true);
                }
            }
        });

        /*
         * If the user changes the JTextField, start a write command and update
         * the subtable
         */
        valueField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                sd.putString("Preference Name", keyBox.getSelectedItem() + "");
                prefs.putString(keyBox.getSelectedItem() + "", valueField.getText());
                if (sd.containsSubTable("Write Preference")) {
                    ITable command = sd.getSubTable("Write Preference");
                    command.putBoolean("running", true);
                }
            }
        });

        /* 
         * If a key is added to the subtable, it is added to the JComboBox
         * If the value of the selected preference is changed, the JTextArea 
         * will be updated
         */
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
