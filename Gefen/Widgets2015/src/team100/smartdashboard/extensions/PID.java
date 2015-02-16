package team100.smartdashboard.extensions;

import java.awt.FlowLayout;
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.gui.elements.BooleanBox;
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
    public final StringProperty loopName = new StringProperty(this, "LoopName", "Default");
    private final  NetworkTable sd = NetworkTable.getTable("SmartDashboard/PID");
//    private final  NetworkTable prefs = NetworkTable.getTable("Preferences");
    
    private final Color team100orange = new Color(0xF4, 0x92, 0x07);

    @Override
    public void propertyChanged(Property prop) {
        if (prop == loopName) {
            name = loopName.getValue()+" ";
            p1.removeAll();
            
            for(int i=0; i<boxes.length; i++){
                TextBox box = new TextBox();
                boxes[i] = box;
                    addWidget(box, name+boxNames[i], p1, DataType.NUMBER);
                if(sd.containsKey(name+boxNames[i])){
                    box.setValue(sd.getNumber(name+boxNames[i]));
                } else {
                    box.setValue(0);
                }
//                String prefKey = (name + boxNames[i]).replace(" ", "_");
//                if(prefs.containsKey(prefKey)&&i < 4) boxes[i].setValue(prefs.getValue(prefKey));
                if (i>3&&i<12) box.editable.setValue(false);
            }
            
            p1.add(testPID);
            p1.add(reset);            

            reachedTarget = new BooleanBox();
            addWidget(reachedTarget, name+"ReachedTarget", p1, DataType.BOOLEAN);
            if(sd.containsKey(name+"ReachedTarget")){
                reachedTarget.setValue(sd.getBoolean(name+"ReachedTarget"));
            } else {
                reachedTarget.setValue(false);
            }
            
            resetGraphs();
        }
    }

    @Override
    public void init() {
        setPreferredSize(new Dimension(825, 425));
        setLayout(new FlowLayout());
        GridLayout g = new GridLayout(4,4);
        g.setHgap(5);
        g.setVgap(5);
        p1.setLayout(g);
        p1.setBackground(team100orange);
        p2.setLayout(new GridLayout(1,2));
        p2.setBackground(team100orange);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGraphs();
            }
        });
        testPID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sd.containsSubTable("Test "+name+"PID")) {
                    ITable t2 = sd.getSubTable("Test "+name+"PID");
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
        // Listen for new SD data
        sd.addTableListener(new ITableListener(){
            @Override
            public void valueChanged(ITable itable, String key, Object value, boolean bln) {
                for(int i=0; i<boxes.length; i++){
                    if(key.equals(name+boxNames[i])){
//                        System.out.println("SD changed: "+key+" "+value);
                        boxes[i].setValue(value);
//                        String prefKey = key.replace(" ", "_");
//                        if(i<4&&prefs.containsKey(prefKey)&&!value.equals(prefs.getValue(prefKey))){
//                            try {
//                                prefs.putNumber(prefKey, (double) value);
//                            } catch (Exception e){
//                                prefs.putNumber(prefKey, 0);
//                            }
//                        }
                    }
                }
                if(key.equals(name+"Error")){
                    errorPlot.setValue(value);
                } else if(key.equals(name+"Output")){
                    outputPlot.setValue(value);
                } else if(key.equals(name+"ReachedTarget")){
                    reachedTarget.setValue(value);
                }
            }
        });
//        //Listen for external preference modification
//        prefs.addTableListener(new ITableListener() {
//            @Override
//            public void valueChanged(ITable itable, String string, Object o, boolean bln) {
//                for(int i=0; i<4; i++){
//                    System.out.println("Pref changed: "+string+" "+o);
//                    System.out.println("prefKey: "+(name+boxNames[i]).replace(" ", "_"));
//                    if(string.equals((name+boxNames[i]).replace(" ", "_"))&&!o.equals(sd.getValue(name+boxNames[i]))){
//                        System.out.println("Made it!");
//                        boxes[i].setValue(o);
//                        sd.putNumber((name+boxNames[i]), Double.parseDouble(o+""));
//                        System.out.println(sd.getNumber(name+boxNames[i]));
//                    }
//                }
//            }
//        });
    }

    @Override
    // Orange background!!!
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(team100orange);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    // Adds widget to dashboard
    private void addWidget(Widget w, String s, JComponent p, DataType d) {
        w.setFieldName("PID/"+s);
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