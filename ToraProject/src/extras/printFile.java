package extras;

import java.io.BufferedReader;
import java.io.IOException;
import ioManagement.ManageIO;
import ioManagement.Output;
import torahApp.ToraApp;

public class printFile {

	public static void printTorah() {
		BufferedReader br = ManageIO.getBufferedReader(ManageIO.fileMode.Line);
		if (br == null) {
			Output.printText("לא הצליח לפתוח קובץ תורה", 1);
			return;
		}
		try {
			for (String line; (line = br.readLine()) != null;) {
				Output.printText(line);
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק הדפסה באמצע", 1);
					break;
				}
				//System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
