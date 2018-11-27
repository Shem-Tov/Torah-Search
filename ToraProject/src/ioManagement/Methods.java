package ioManagement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import extras.Console;
import extras.extraFunctions;
import torahApp.CountSearch;
import torahApp.DilugWordPasuk;
import torahApp.Dilugim;
import torahApp.Gimatria;
import torahApp.LetterSearch;
import torahApp.ReportTorahCount;
import torahApp.ToraSearch;

public class Methods {
	public interface MethodRunner {
		public void run(Object... args) throws IOException;
	}

	public static final int id_searchWords=0;
	public static final int id_searchGimatria=1;
	public static final int id_calculateGimatria=2;
	public static final int id_searchDilugim=3;
	public static final int id_searchDilugWordPasuk=4;
	public static final int id_searchLetters=5;
	public static final int id_searchCount=6;
	public static final int id_settings=7;
	public static final int id_printTorah=8;
	public static final int id_ReportTorahCount=9;
	
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
		getArrayMethodID().add(id_searchDilugWordPasuk);
		getArrayMethodTitle().add("Search Dilugim");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				DilugWordPasuk dilug = DilugWordPasuk.getInstance();
				dilug.searchDilugWordPasuk(args);
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
		getArrayMethodID().add(id_searchCount);
		getArrayMethodTitle().add("Search Count");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				CountSearch cSearch = CountSearch.getInstance();
				cSearch.searchByCount(args);
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
		getArrayMethodID().add(id_printTorah);
		getArrayMethodTitle().add("Print Torah");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extras.printFile.printTorah();
			}
		});
		getArrayMethodID().add(id_ReportTorahCount);
		getArrayMethodTitle().add("Report Torah Count");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				ReportTorahCount tCount = ReportTorahCount.getInstance();
				tCount.createReport(args);
			}
		});
		getArrayMethodID().add(11);
		getArrayMethodTitle().add("Find Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findWords();
			}
		});
		getArrayMethodID().add(12);
		getArrayMethodTitle().add("Find First Letters");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findFirstLetters();
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
