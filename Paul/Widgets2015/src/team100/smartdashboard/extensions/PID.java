package team100.smartdashboard.extensions;

import java.awt.FlowLayout;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
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
    private LinePlot errorPlot = new LinePlot();
    private LinePlot outputPlot = new LinePlot();

    private final TextBox[] boxes = new TextBox[11];
    private final String[] boxNames = {"_kP", "_kI", "_kD", "_kF", "Input", "Error","Target", "Output", "Interval", "Rate", "TotalError"};
    
    private final JPanel p1 = new JPanel();
    private final JPanel p2 = new JPanel();
    
    private String name = "Default";
    public final StringProperty loopName = new StringProperty(this, "LoopName", name);
    private  NetworkTable t = NetworkTable.getTable("SmartDashboard");

    @Override
    public void propertyChanged(Property prop) {
        if (prop == loopName) {
            name = loopName.getValue();
            p1.removeAll();
            for(int i=0; i<boxes.length; i++){
                TextBox box = new TextBox();
                boxes[i] = box;
                addWidget(box, name+boxNames[i], p1);
                if(t.containsKey(name+boxNames[i])){
                    box.setValue(t.getNumber(name+boxNames[i]));
                } else {
                    box.setValue(0);
                }
                if(i>3) box.editable.setValue(false);
            }
            p1.add(reset);
            resetGraphs();
        }
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(850, 400));
        setLayout(new FlowLayout());
        p1.setLayout(new GridLayout(3,4));
        p2.setLayout(new GridLayout(1,2));
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGraphs();
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
    private void addWidget(Widget w, String s, JComponent p) {
        w.setFieldName(s);
        w.setType(DataType.NUMBER);
        System.out.println(w);
        System.out.println(s);
        System.out.println(w.getType());
        System.out.println(w.getFieldName());
        w.init();
        p.add(w);
    }
    
    // Clears the line plots
    private void resetGraphs(){
        p2.removeAll();
        errorPlot = new LinePlot();
        outputPlot = new LinePlot();
        addWidget(errorPlot, "Error", p2);
        addWidget(outputPlot, "Output", p2);
        revalidate();
    }
}