package extras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CopyFiles {
	private static void copyFileBytes(String src, String dest) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		int counter=0;
		try {
			inputStream = new FileInputStream(new File(src));
			outputStream = new FileOutputStream(new File(dest));

			// the size of the buffer doesn't have to be exactly 1024 bytes, try playing
			// around with this number and see what effect it will have on the performance
			// byte[] buffer = new byte[1024];
			int c = 0;
			@SuppressWarnings("unused")
			int lastC = 0;
			while ((c = inputStream.read()) != -1) {
				// outputStream.write((c==13)?10:c);
				/*if (((c == 32) || (c == 10)) && (lastC == c)) {

				} else {
					outputStream.write(c);
				}
				*/
				if ((c!=32) && (c!=10)) {
					counter++;
					outputStream.write(c);
				}

			}
		} finally {
			System.out.println("Number of Letters: "+counter);
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private static void copyFileLines(String src, String dest) throws IOException {

		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new FileReader(src));
			pw = new PrintWriter(new FileWriter(dest));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length()>1) {
					pw.println(line.trim());
					pw.flush();
				}
				//Thread.sleep(1000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			br.close();
			pw.close();
		}
	}

	public static void main(String[] args) {
		try {
			copyFileBytes("Lines_3.txt", "Letters_3.txt");
			//copyFileLines("Lines_3.txt", "Lines_4.txt");
			System.out.println("Finished Copying");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
