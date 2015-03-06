package netTableTest;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class NetTableTest implements ITableListener {

	public static void main(String[] args) {
		new NetTableTest().run();
	}
	
	public void run() {
		NetworkTable.setIPAddress("10.1.0.2");
		NetworkTable.setClientMode();
		NetworkTable myTable = NetworkTable.getTable("SmartDashboard");
		myTable.addTableListener(NetTableTest.this, true);
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex){
			} 
			System.out.println("Tick!Tock!");
		}
	}

	@Override
	public void valueChanged(ITable source, String key, Object value,
			boolean isNew) {
		System.out.println ("Source: " + source + " Key: " + key + " value: " + value + 	" Class: " + value.getClass());	
	}
}