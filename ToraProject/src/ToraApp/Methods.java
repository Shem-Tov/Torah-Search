package ToraApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Methods {
	public interface MethodRunner {
		public void run(Object... args) throws IOException;
	}

	public static final int id_searchWords=1;
	public static final int id_searchGimatria=2;
	public static final int id_calculateGimatria=3;
	public static final int id_searchDilugim=4;
	public static final int id_settings=7;
	
	// Add method references here
	public static List<MethodRunner> arrayMethods = new ArrayList<>();
	static List<String> arrayMethodTitle = new ArrayList<>();
	static List<Integer> arrayMethodID = new ArrayList<>();
	public static void arrayMethodCreator() throws IOException {
		arrayMethodID.add(id_searchWords);
		arrayMethodTitle.add("Search Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				ToraSearch tora = ToraSearch.getInstance();
				tora.searchWords(args);
			}
		});
		arrayMethodID.add(id_searchGimatria);
		arrayMethodTitle.add("Search Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria gim = Gimatria.getInstance();
				gim.searchGimatria(args);
			}
		});
		arrayMethodID.add(id_calculateGimatria);
		arrayMethodTitle.add("Calculate Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria.callCalculateGimatria(args);
			}
		});
		arrayMethodID.add(id_searchDilugim);
		arrayMethodTitle.add("Search Dilugim");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Dilugim dilug = Dilugim.getInstance();
				dilug.searchWordsDilugim(args);
			}
		});
		arrayMethodID.add(5);
		arrayMethodTitle.add("Find Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findWords();
			}
		});
		arrayMethodID.add(6);
		arrayMethodTitle.add("Find First Letters");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findFirstLetters();
			}
		});
		arrayMethodID.add(id_settings);
		arrayMethodTitle.add("Settings");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Console.menuSettings();
			}
		});
		
	}

}
