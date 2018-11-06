package ioManagement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import extras.Console;
import extras.extraFunctions;
import torahApp.Dilugim;
import torahApp.Gimatria;
import torahApp.LetterSearch;
import torahApp.ToraSearch;

public class Methods {
	public interface MethodRunner {
		public void run(Object... args) throws IOException;
	}

	public static final int id_searchWords=1;
	public static final int id_searchGimatria=2;
	public static final int id_calculateGimatria=3;
	public static final int id_searchDilugim=4;
	public static final int id_searchLetters=5;
	public static final int id_settings=7;
	
	// Add method references here
	public static List<MethodRunner> arrayMethods = new ArrayList<>();
	private static List<String> arrayMethodTitle = new ArrayList<>();
	private static List<Integer> arrayMethodID = new ArrayList<>();
	public static void arrayMethodCreator() throws IOException {
		getArrayMethodID().add(id_searchWords);
		getArrayMethodTitle().add("Search Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				ToraSearch tora = ToraSearch.getInstance();
				tora.searchWords(args);
			}
		});
		getArrayMethodID().add(id_searchGimatria);
		getArrayMethodTitle().add("Search Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria gim = Gimatria.getInstance();
				gim.searchGimatria(args);
			}
		});
		getArrayMethodID().add(id_calculateGimatria);
		getArrayMethodTitle().add("Calculate Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria.callCalculateGimatria(args);
			}
		});
		getArrayMethodID().add(id_searchDilugim);
		getArrayMethodTitle().add("Search Dilugim");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Dilugim dilug = Dilugim.getInstance();
				dilug.searchWordsDilugim(args);
			}
		});
		getArrayMethodID().add(id_searchLetters);
		getArrayMethodTitle().add("Search Letters");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				LetterSearch letter = LetterSearch.getInstance();
				letter.searchForLetters(args);
			}
		});
		getArrayMethodID().add(8);
		getArrayMethodTitle().add("Find Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findWords();
			}
		});
		getArrayMethodID().add(9);
		getArrayMethodTitle().add("Find First Letters");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findFirstLetters();
			}
		});
		getArrayMethodID().add(id_settings);
		getArrayMethodTitle().add("Settings");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Console.menuSettings();
			}
		});
		
	}
	public static List<String> getArrayMethodTitle() {
		return arrayMethodTitle;
	}
	public static void setArrayMethodTitle(List<String> arrayMethodTitle) {
		Methods.arrayMethodTitle = arrayMethodTitle;
	}
	public static List<Integer> getArrayMethodID() {
		return arrayMethodID;
	}
	public static void setArrayMethodID(List<Integer> arrayMethodID) {
		Methods.arrayMethodID = arrayMethodID;
	}

}
