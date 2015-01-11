package org.usfirst.frc.team100.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Stores all changeable or arbitrary values in a file on the cRIO.
 */
public class Preferences {

    private static ArrayList<String> keys = new ArrayList<String>();
    private static ArrayList<String> values = new ArrayList<String>();

    // Creates or sets a preference, but does not modify cRIO file
    public static void set(String name, Object value) {
        if(keys.contains(name)){
        	int index = keys.indexOf(name);
        	values.set(index, value+"");
        } else {
        	keys.add(name);
        	values.add(value+"");
        }
    }

    // Casts double to an object for regular set method
    public static void set(String name, double value) {
    	set(name, Double.valueOf(value));
    }
    
    // Casts boolean to an object for regular set method
    public static void set(String name, boolean value) {
    	set(name, Boolean.valueOf(value));
    }

    // Returns value of the double preference with the given name
    public static double getDouble(String name) {
        try {
        	return Double.parseDouble(getString(name));
        } catch(NumberFormatException e){
        	System.out.println("Preference is not a double: " + name);
        }
        return 0;
    }

    // Returns value of the boolean preference with the given name
    public static boolean getBoolean(String name) {
        String value = getString(name);
        if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
            System.out.println("ERROR: no boolean found: " + name + " " + value);
        }
        return "true".equals(value);
    }

    // Returns value of the String preference with the given name
    public static String getString(String name) {
        if(keys.contains(name)){
        	int index = keys.indexOf(name);
        	return values.get(index);
        } else {
        	return "0";
        }
    }

    // Returns whether a preference is present
    public static boolean contains(String name) {
        return keys.contains(name);
    }

    // Pulls preferences from the file, overwrites any existing preferences
    public static void read() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("/Preferences.txt"));
			keys.clear();
			values.clear();
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
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
            out = new BufferedWriter(new FileWriter("/Preferences.txt"));
            for (int i = 0; i < keys.size(); i++) {
                if(values.get(i).equals("")){
                	out.write(keys.get(i));
                } else {
                	out.write(keys.get(i)+" "+values.get(i));
                }
                out.newLine();
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}