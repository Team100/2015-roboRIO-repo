package org.usfirst.frc100.SlideWinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * Stores all changeable or arbitrary values in a file on the cRIO.
 */
public class Preferences {

    private static ArrayList<String> keys = new ArrayList<String>(); // The preference identifiers
    private static NetworkTable table = NetworkTable.getTable("Preferences");
    private static final String DIVIDER = " ";

	/**
	 * Sets a preference value, creating the preference if it does not exist
	 * 
	 * @param name - The name of the preference
	 * @param value - The desired value of the preference
	 */
	public static void set(String name, Object value) {
		table.putString(name, value+"");
		if (!keys.contains(name)) keys.add(name);
	}

	/**
	 * Casts double to an object for regular set method
	 * 
	 * @param name - The name of the preference
	 * @param value - The desired value of the preference
	 */
	public static void set(String name, double value) {
		set(name, Double.valueOf(value));
	}

	/**
	 * Casts boolean to an object for regular set method
	 * 
	 * @param name - The name of the preference
	 * @param value - The desired value of the preference
	 */
	public static void set(String name, boolean value) {
		set(name, Boolean.valueOf(value));
	}

	/**
	 * Returns value of a preference as a double
	 * 
	 * @param name - The name of the preference
	 * @return The value of the preference
	 */
	public static double getDouble(String name) {
		String value = getString(name);
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			System.err.println("Preference is not a double: " + name + DIVIDER + value);
			return 0;
		}
	}

	/**
	 * Returns value of a preference as a boolean
	 * 
	 * @param name - The name of the preference
	 * @return The value of the preference
	 */
	public static boolean getBoolean(String name) {
		String value = getString(name);
		if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
			System.err.println("Preference is not a boolean: " + name + DIVIDER + value);
		}
		return value.equalsIgnoreCase("true");
	}

	/**
	 * Returns value of a preference as a String
	 * 
	 * @param name - The name of the preference
	 * @return The value of the preference
	 */
	public static String getString(String name) {
		if (table.containsKey(name)) {
			return table.getString(name);
		} else {
			System.err.println("Preference not found: " + name);
			set(name, "0");
			return "0";
		}
	}

	/**
	 * Returns whether a preference exists
	 * 
	 * @param name - The name of the preference
	 * @return The existence of the given preference
	 */
	public static boolean contains(String name) {
		return table.containsKey(name);
	}

	/**
	 * Pulls preferences from the file, overwrites any existing preferences
	 */
	public static void read() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("/home/lvuser/Preferences.txt"));
			keys.clear();
			for (int i = 0; i < 10000; i++) { // End of file
				String line = in.readLine();
				if (line == null) {
					break;
				} else if(line.length() == 0) { // Newline
					keys.add("");
				} else if (line.contains(DIVIDER)) { // Key and value
					int dividerIndex = line.indexOf(DIVIDER);
					String key = line.substring(0, dividerIndex);
					String value = line.substring(dividerIndex + DIVIDER.length(), line.length());
					set(key, value);
				} else { // Key without value
					set(line, "0");
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overwrites the file with current preferences
	 */
	public static void write() {
		BufferedWriter out;
		if (keys.isEmpty()) { // So we don't accidentally delete the preferences file
			return;
		}
		try {
			out = new BufferedWriter(new FileWriter("/home/lvuser/Preferences.txt"));
			for (int i = 0; i < keys.size(); i++) {
				if(){
					String line = keys.get(i) + DIVIDER + table.getString(keys.get(i));
				out.write(line);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the preferences file and starts listening to the network table
	 */
	public static void init(){
		read();
		table.addTableListener(new ITableListener() {
			@Override
			public void valueChanged(ITable source, String key, Object value, boolean isNew) {
				if(!keys.contains(key)) keys.add(key);
			}
		}, true);
	}
}