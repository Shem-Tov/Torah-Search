package torahApp;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import ioManagement.Output;


public class WordCounter {

	final Integer ONE = 1;
	Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	
	public WordCounter() {
		//Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	}
	
	public void printWords() throws IOException {
		// Gets keys
		Enumeration<String> e = map.keys();
        // Alphabetizes keys
		List<String> list = Collections.list(e);
        Collections.sort(list);
        for (String key:list) {
			Output.printText(key + " : " + map.get(key));
        }
	}

	public void addWord(String word) {
		String wordTrim = word.trim();
		Object obj = this.map.get(wordTrim);
		if (obj == null) {
			map.put(wordTrim, ONE);
		} else {
			int i = ((Integer) obj).intValue() + 1;
			map.put(wordTrim, i);
		}
	}
}
