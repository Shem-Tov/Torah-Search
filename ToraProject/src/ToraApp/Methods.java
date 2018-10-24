package ToraApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Methods {
	public interface MethodRunner {
		public void run(Object... args) throws IOException;
	}

	// Add method references here
	public static List<MethodRunner> arrayMethods = new ArrayList<>();
	static List<String> arrayMethodTitle = new ArrayList<>();
	static List<Integer> arrayMethodID = new ArrayList<>();
	public static void arrayMethodCreator() throws IOException {
		arrayMethodID.add(1);
		arrayMethodTitle.add("Search Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				ToraSearch.searchWords(args);
			}
		});
		arrayMethodID.add(2);
		arrayMethodTitle.add("Search Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria.searchGimatria(args);
			}
		});
		arrayMethodID.add(3);
		arrayMethodTitle.add("Calculate Gimatria");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Gimatria.callCalculateGimatria((String)args[0]);
			}
		});
		arrayMethodID.add(4);
		arrayMethodTitle.add("Find Words");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findWords();
			}
		});
		arrayMethodID.add(5);
		arrayMethodTitle.add("Find First Letters");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				extraFunctions.findFirstLetters();
			}
		});
		arrayMethodID.add(6);
		arrayMethodTitle.add("Settings");
		arrayMethods.add(new MethodRunner() {
			@Override
			public void run(Object... args) throws IOException {
				Console.menuSettings();
			}
		});
		
	}

}
