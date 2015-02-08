package team100.smartdashboard.extensions;

import java.awt.FlowLayout;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.gui.elements.BooleanBox;
import edu.wpi.first.smartdashboard.gui.elements.Command;
import edu.wpi.first.smartdashboard.gui.elements.LinePlot;
import edu.wpi.first.smartdashboard.gui.elements.TextBox;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PID extends StaticWidget {

    public static final String NAME = "PID Tuner";

    private final JButton reset = new JButton("Reset Graphs");
    private BooleanBox reachedTarget;
    private final JButton testPID = new JButton("Start PID");
    private LinePlot errorPlot = new LinePlot();
    private LinePlot outputPlot = new LinePlot();

    private final TextBox[] boxes = new TextBox[13];
    private final String[] boxNames = {"kP", "kI", "kD", "kF", "Input", "Error","Target", "Output", "Interval", "Rate", "TotalError", "SensorValue", "TestTarget"};
    
    private final JPanel p1 = new JPanel();
    private final JPanel p2 = new JPanel();
    
    private String name = "Default ";
    public final StringProperty loopName = new StringProperty(this, "LoopName", name);
    private final  NetworkTable t = NetworkTable.getTable("SmartDashboard");

    @Override
    public void propertyChanged(Property prop) {
        if (prop == loopName) {
            name = loopName.getValue()+" ";
            p1.removeAll();
            
            for(int i=0; i<boxes.length; i++){
                TextBox box = new TextBox();
                boxes[i] = box;
                addWidget(box, name+boxNames[i], p1, DataType.NUMBER);
                if(t.containsKey(name+boxNames[i])){
                    box.setValue(t.getNumber(name+boxNames[i]));
                } else {
                    box.setValue(0);
                }
                if(i>3&&i<12) box.editable.setValue(false);
            }
            
            p1.add(testPID);
            p1.add(reset);            

            reachedTarget = new BooleanBox();
            addWidget(reachedTarget, name+"ReachedTarget", p1, DataType.BOOLEAN);
            if(t.containsKey(name+"ReachedTarget")){
                reachedTarget.setValue(t.getBoolean(name+"ReachedTarget"));
            } else {
                reachedTarget.setValue(false);
            }
            
            resetGraphs();
        }
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(850, 475));
        setLayout(new FlowLayout());
        GridLayout g = new GridLayout(4,4);
        g.setHgap(5);
        g.setVgap(5);
        p1.setLayout(g);
        p1.setBackground(Color.orange);
        p2.setLayout(new GridLayout(1,2));
        p2.setBackground(Color.orange);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGraphs();
            }
        });
        testPID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(t.containsSubTable("Test "+name+"PID")) {
                    ITable t2 = t.getSubTable("Test "+name+"PID");
                    t2.putBoolean("running", !t2.getBoolean("running"));
                    if(t2.getBoolean("running")){
                        testPID.setText("Cancel PID");
                    } else {
                        testPID.setText("Start PID");
                    }
                }
            }
        });
        add(p1);
        add(p2);
        propertyChanged(loopName);
        t.addTableListener(new ITableListener(){
            @Override
            public void valueChanged(ITable itable, String string, Object o, boolean bln) {
                for(int i=0; i<boxes.length; i++){
                    if(string.equals(name+boxNames[i])){
                        boxes[i].setValue(o);
                    }
                }
                if(string.equals(name+"Error")){
                    errorPlot.setValue(o);
                } else if(string.equals(name+"Output")){
                    outputPlot.setValue(o);
                } else if(string.equals(name+"ReachedTarget")){
                    reachedTarget.setValue(o);
                }
            }
        });
    }

    @Override
    // Orange background!!!
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // Adds widget to dashboard
    private void addWidget(Widget w, String s, JComponent p, DataType d) {
        w.setFieldName(s);
        w.setType(d);
        w.init();
        p.add(w);
    }
    
    // Clears the line plots
    private void resetGraphs(){
        p2.removeAll();
        errorPlot = new LinePlot();
        outputPlot = new LinePlot();
        addWidget(errorPlot, "Error", p2, DataType.NUMBER);
        addWidget(outputPlot, "Output", p2, DataType.NUMBER);
        revalidate();
    }
}