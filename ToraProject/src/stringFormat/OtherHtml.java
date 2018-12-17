package stringFormat;

import org.jsoup.Jsoup;

public class OtherHtml {
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
}
