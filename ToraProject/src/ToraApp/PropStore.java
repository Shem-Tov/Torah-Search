package ToraApp;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropStore {
	private static final String fileName = "./src/config.properties";
	public static Map<String, String> map = new HashMap<String, String>();
	public static final String searchWord = "searchWord";
	public static final String bool_wholeWord = "bool_wholeWord";
	public static final String searchGmt = "searchGmt";
	public static final String bool_gimatriaSofiot = "bool_gimatriaSofiot";
	public static final String bool_countPsukim = "count_psukim";
	public static final String minDilug = "minDilug";
	public static final String maxDilug = "maxDikug";
	public static final String offsetDilug = "offsetDilug";
	
	public static void store() {

		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(fileName);
			// set the properties value
			prop.putAll(map);
			
			// save properties to project root folder
			//prop.putAll(map);
			prop.store(output, null);
		} catch (IOException io) {
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
	
	public static void load(){

    	Properties prop = new Properties();
    	InputStream input = null;

    	try {
        	input = new FileInputStream(fileName);
     		//load a properties file from class path, inside static method
    		prop.load(input);
    		map = prop.entrySet().stream().collect(Collectors.toMap(
    	            e -> String.valueOf(e.getKey()),
    	            e -> String.valueOf(e.getValue())));
                //get the property value and print it out
                //System.out.println(prop.getProperty("database"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
 
    }
}

