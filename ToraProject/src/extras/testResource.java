package extras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testResource {
	public static void main(String[] args) {
		BufferedReader paramReader = new BufferedReader(
				new InputStreamReader(testResource.class.getClassLoader().getResourceAsStream("Lines.txt")));
		try {
			System.out.println(paramReader.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
