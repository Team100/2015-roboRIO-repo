
package team100.smartdashboard.extensions;

import javax.swing.JFrame;

/**
 *
 * @author Me
 */
public class WidgetTester {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PDP_Widget pref = new PDP_Widget();
        pref.init();
        f.add(pref);
        f.pack();
        f.setVisible(true);
    }
}
