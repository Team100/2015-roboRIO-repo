
package team100.smartdashboard.extensions;

import javax.swing.JFrame;

/**
 *
 * @author Student
 */
public class WidgetTester {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setSize(450,100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PID pref = new PID();
        pref.init();
        f.add(pref);
        f.setVisible(true);
    }
}
