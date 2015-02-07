package org.usfirst.frc100.Robot2015;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Stores all changeable or arbitrary values in a file on the cRIO.
 */
public class Preferences {
	
	private static final boolean DEBUG_MODE = false;

    private static ArrayList<String> keys = new ArrayList<String>();
    private static ArrayList<String> values = new ArrayList<String>();

    /**
     * Creates or sets a preference, but does not modify cRIO file
     * @param name - Name of preference
     * @param value - Desired preference value
     */
    public static void set(String name, Object value) {
        if(keys.contains(name)){
        	if(DEBUG_MODE) System.out.println("Preference modified: " + name + " " + value);
        	int index = keys.indexOf(name);
        	values.set(index, value+"");
        } else {
        	if(DEBUG_MODE) System.out.println("Preference added: " + name + " " + value);
        	keys.add(name);
        	values.add(value+"");
        }
    }

    /**
     * Casts double to an object for regular set method
     * @param name - Name of preference
     * @param value - Desired preference value
     */
    public static void set(String name, double value) {
    	set(name, Double.valueOf(value));
    }
    
    /**
     * Casts boolean to an object for regular set method
     * @param name - Name of preference 
     * @param value - Desired preference value
     */
    public static void set(String name, boolean value) {
    	set(name, Boolean.valueOf(value));
    }

    /**
     * Returns value of the double preference with the given name
     * @param name -  Name of preference
     * @return double value parsed from string name
     */
    public static double getDouble(String name) {
    	String value = getString(name);
        try {
        	return Double.parseDouble(value);
        } catch(NumberFormatException e){
        	if(DEBUG_MODE) System.out.println("Preference is not a double: " + name + " " + value);
        }
        return 0;
    }

    /**
     * Returns value of the boolean preference with the given name
     * @param name - Name of preference
     * @return boolean value of a boolean preference
     */
    public static boolean getBoolean(String name) {
        String value = getString(name);
        if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
        	if(DEBUG_MODE) System.out.println("Preference is not a boolean: " + name + " " + value);
        }
        return "true".equals(value);
    }

    /**
     * Returns value of the String preference with the given name
     * @param name - Name of preference
     * @return String of preference
     */
    public static String getString(String name) {
        if(keys.contains(name)){
        	int index = keys.indexOf(name);
        	return values.get(index);
        } else {
        	if(DEBUG_MODE) System.out.println("Preference not found: " + name);
        	set(name, 0);
        	return "0";
        }
    }

    /**
     * Returns whether a preference is present
     * @param name - Name of preference
     * @return boolean if present
     */
    public static boolean contains(String name) {
        return keys.contains(name);
    }

    // Pulls preferences from the file, overwrites any existing preferences
    public static void read() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("/home/lvuser/Preferences.txt"));
			keys.clear();
			values.clear();
			if(DEBUG_MODE) System.out.println("READING PREFERENCES");
			for (int i=0; i<10000; i++){
				String line = in.readLine();
				if (line == null) {
					break;
				}
				if(line.contains(" ")){
					int spaceIndex = line.indexOf(" ");
					keys.add(line.substring(0, spaceIndex));
					values.add(line.substring(spaceIndex+1, line.length()));
				} else {
					keys.add(line);
					values.add("");
				}
				if(DEBUG_MODE) System.out.println(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // Overwrites the file with current preferences
    public static void write() {
        BufferedWriter out;
        if(keys.isEmpty()){
        	return;
        }
        try {
            out = new BufferedWriter(new FileWriter("/home/lvuser/Preferences.txt"));
            if(DEBUG_MODE) System.out.println("WRITING PREFERENCES");
            for (int i = 0; i < keys.size(); i++) {
                if(values.get(i).equals("")){
                	if(DEBUG_MODE) System.out.println(keys.get(i));
                	out.write(keys.get(i));
                } else {
                	if(DEBUG_MODE) System.out.println(keys.get(i)+" "+values.get(i));
                	out.write(keys.get(i)+" "+values.get(i));
                }
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}