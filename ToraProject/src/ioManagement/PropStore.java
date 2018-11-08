package ioManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import torahApp.ToraApp;

public class PropStore {
	private static final String fileName = ToraApp.resourceFolder + "config.properties";
	public static Map<String, String> map = new HashMap<String, String>();
	public static final String searchWord = "searchWord";
	public static final String bool_wholeWord = "bool_wholeWord";
	public static final String searchGmt = "searchGmt";
	public static final String bool_gimatriaSofiot = "bool_gimatriaSofiot";
	public static final String bool_countPsukim = "count_psukim";
	public static final String minDilug = "minDilug";
	public static final String maxDilug = "maxDikug";
	public static final String offsetDilug = "offsetDilug";
	public static final String paddingDilug = "paddingDilug";
	public static final String subTorahTablesFile = "subTorahTables";
	public static final String subTorahLineFile = "subTorahLineFile";
	public static final String subTorahLettersFile = "subTorahLettersFile";
	public static final String fontSize = "fontSize";
	public static final String bgColor = "bgColor";

	public static void addNotNull(String key, String value) {
		if (value != null) {
			map.put(key, value);
		}
	}

	public static void store() {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			File outputFile;
			try {
				outputFile = new File(fileName);
				outputFile.createNewFile(); // if file already exists will do nothing
				output = new FileOutputStream(outputFile);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// set the properties value
			prop.putAll(map);

			// save properties to project root folder
			// prop.putAll(map);
			prop.store(output, null);
		} catch (IOException | NullPointerException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void load() {

		Properties prop = new Properties();
		InputStreamReader input = null;
		for (int i = 1; i < 2; i++) {
			try {
				if (i == 0) {
					//file = new File(ClassLoader.getSystemResource(fileName).toURI());
					input = new InputStreamReader(PropStore.class.getClassLoader().getResourceAsStream(fileName));
				} else {
					input = new InputStreamReader(new FileInputStream(fileName));
				}
				// load a properties file from class path, inside static method
				prop.load(input);
				map = prop.entrySet().stream()
						.collect(Collectors.toMap(e -> String.valueOf(e.getKey()), e -> String.valueOf(e.getValue())));
				// get the property value and print it out
				// System.out.println(prop.getProperty("database"));
				break;
			} catch (IOException | IllegalArgumentException | NullPointerException ex) {
				
				if (i == 1) {
					Output.printText("Could not open config file", 1);
					// ex.printStackTrace();
				}
			}
		}
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
