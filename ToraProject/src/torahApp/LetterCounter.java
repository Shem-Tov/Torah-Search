package torahApp;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import ioManagement.Output;

public class LetterCounter {

	final Integer ONE = 1;
	Hashtable<Character, Integer> map = new Hashtable<Character, Integer>();

	public LetterCounter() {
		// Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	}

	public void printWords() throws IOException {
		// Gets keys
		Enumeration<Character> e = map.keys();
		// Alphabetizes keys
		List<Character> list = Collections.list(e);
		Collections.sort(list);
		for (Character key : list) {
			Output.printText(key + " : " + map.get(key));
		}
	}

	public void addChar(Character ch) {
		Object obj = this.map.get(ch);
		if (obj == null) {
			map.put(ch, ONE);
		} else {
			int i = ((Integer) obj).intValue() + 1;
			map.put(ch, i);
		}
	}

	public Boolean findAndRemoveChar(Character ch) {
		return removeChar(ch);
	}

	public Boolean removeChar(Character ch) {
		Object obj = this.map.get(ch);
		if (obj != null) {
			int i = ((Integer) obj).intValue();
			if (i == ONE) {
				map.remove(ch);
			} else {
				map.put(ch, i - 1);
			}
			return true;
		} else {
			return false;
		}
	}

}
