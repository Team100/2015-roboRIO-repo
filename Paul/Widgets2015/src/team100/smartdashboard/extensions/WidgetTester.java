
package team100.smartdashboard.extensions;

import javax.swing.JFrame;

/**
 *
 * @author Student
 */
public class WidgetTester {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setSize(1000,1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PID pid = new PID();
        pid.init();
        f.add(pid);
        f.setVisible(true);
    }
}
