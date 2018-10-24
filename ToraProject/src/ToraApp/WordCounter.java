package ToraApp;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;


public class WordCounter {

	final Integer ONE = 1;
	Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	
	public WordCounter() {
		//Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	}
	public void printWords() throws IOException {
		Enumeration<String> e = map.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Output.printText(key + " : " + map.get(key));
		}
	}

	public void addWord(String word) {
		Object obj = this.map.get(word);
		if (obj == null) {
			map.put(word, ONE);
		} else {
			int i = ((Integer) obj).intValue() + 1;
			map.put(word, i);
		}
	}
}
