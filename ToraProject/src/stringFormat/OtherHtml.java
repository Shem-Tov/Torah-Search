package stringFormat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ioManagement.Output;

public class OtherHtml {
	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

	public static void printHtmlTable(String table, int padding) {
		// try {
		// FileWriter writer = new FileWriter("csv.txt");

		Document doc = Jsoup.parseBodyFragment(table);
		Elements rows = doc.getElementsByTag("tr");
		Set<Integer> set = new LinkedHashSet<Integer>();
		for (Element row : rows) {
			int cellColumn=-1;
			Elements cells2 = row.getElementsByTag("td");
			for (Element cell : cells2) {
				cellColumn++;
				if (cell.text().length() > padding * 2) {
					set.add(cellColumn);
				}
			}
		}
		for (Element row : rows) {
			Elements cells = row.getElementsByTag("th");
			Elements cells2 = row.getElementsByTag("td");
			for (Element cell : cells) {
				String str = cell.text().concat(" ");
				// writer.write(str);
				System.out.print(Output.r2l() + StringAlignUtils.padLeft(str, padding));
			}
			int cellCounter = -1;
			for (Element cell : cells2) {
				cellCounter++;
				String str = cell.text().concat(" ");
				// writer.write(str);
				if (set.contains(cellCounter)) {
					System.out.println();
					Boolean loop = true;
					int oldIndex = 0;
					do {
						int index = str.indexOf(' ', padding * 2 + oldIndex);
						if ((index > -1) && ((str.length() - index) > (padding))) {
							System.out.println(Output.r2l()
									+ StringAlignUtils.padLeft(str.substring(oldIndex, index), padding * 2));
							oldIndex = index;
						} else {
							System.out.println(
									Output.r2l() + StringAlignUtils.padLeft(str.substring(oldIndex), padding * 2));
							loop = false;
						}
					} while (loop);
				} else {
					System.out.print(Output.r2l() + StringAlignUtils.padLeft(str, padding));
				}
			}
			// writer.write("\n");
			System.out.println("\n");
		}
		// writer.close();
		// } catch (IOException e) {
		// e.getStackTrace();
		// }
	}
}
