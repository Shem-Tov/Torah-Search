package extras;

import java.io.BufferedReader;
import java.io.IOException;
import frame.ColorClass;
import ioManagement.ManageIO;
import ioManagement.Output;
import torahApp.ToraApp;
import torahApp.TorahPlaceClass;

public class printFile {

	public static void printTorah(Object[] args) {
		int[] searchRange;
		boolean bool_addInfo;
		searchRange = (args[0] != null) ? (int[]) (args[0]) : (new int[] { 0, 0 });
		bool_addInfo = (args[1] != null) ? (Boolean) args[1] : true;
		int countLines = 0;
		BufferedReader br = ManageIO.getBufferedReader(ManageIO.fileMode.Line);
		if (br == null) {
			Output.printText("לא הצליח לפתוח קובץ תורה", 1);
			return;
		}
		try {
			Output.printLine(2);
			// For some strange reason the Text
			// is being printed between these two
			// Lines. printLine should be added to
			// the end of the function, but it
			// works better here.
			if (!bool_addInfo) Output.printLine(2);
			for (String line; (line = br.readLine()) != null;) {
				countLines++;
				if ((searchRange[1] != 0) && ((countLines <= searchRange[0]) || (countLines > searchRange[1]))) {
					continue;
				}
				if (bool_addInfo) {
					TorahPlaceClass TorahPlace = ToraApp.checkStartBookParashaFromLineNum(countLines);
					if (TorahPlace.getName() != null) {
						Output.printLine(2);
						Output.printText(TorahPlace.getName());
					}
					if (TorahPlace.getIsStartPerek()) {
						Output.printLine(2);
					}
				}
				Output.printText(
						((bool_addInfo) ? ToraApp.lookupTorahPositionFromLineNumber(countLines) + " ":"")
						+ Output.markText(line, ColorClass.tooltipStyleHTML));
				if ((ToraApp.getGuiMode() == ToraApp.id_guiMode_Frame) && (frame.Frame.getMethodCancelRequest())) {
					Output.printText("\u202B" + "המשתמש הפסיק הדפסה באמצע", 1);
					break;
				}
				// System.out.println(line);
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
