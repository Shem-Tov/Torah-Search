package frame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import console.Methods;
import hebrewLetters.HebrewLetters;
import ioManagement.LastSearchClass;
import ioManagement.ManageIO;
import ioManagement.ManageIO.fileMode;
import ioManagement.Output;
import torahApp.ToraApp;

public class SwingActivity extends SwingWorker<Void, Integer> {

	private static SwingActivity instance;
	private int currentProgress;
	private static final int finalProgress_hardCoded = 5846;
	private static int finalProgress = finalProgress_hardCoded;

	public static void setFinalProgress(int[] range) {
		if (range[1] == 0) {
			switch (Frame.getComboBox_DifferentSearch(fileMode.Line)) {
			case Line:
			case NoTevot:
				finalProgress = finalProgress_hardCoded;
				break;
			case Different:
				finalProgress = ManageIO.countLinesOfFile(ManageIO.fileMode.Different);
				break;
			case LastSearch:
				finalProgress = LastSearchClass.getStoredSize();
				break;
			}
		} else {
			finalProgress = range[1] - range[0];
		}

	}

	public static SwingActivity getInstance() {
		if (instance == null) {
			instance = new SwingActivity();
		}
		return instance;
	}

	public void callProcess(int num) {
		callProcess(num, 1, 1, 1);
	}

	public void callProcess(int num, int thisDilug, int minDilug, int maxDilug) {
		currentProgress = num;
		int factor = (int) (100 * (((float) currentProgress / finalProgress) + (thisDilug - minDilug))
				/ (maxDilug - minDilug + 1));
		publish(factor);
	}

	@Override
	protected Void doInBackground() {
		// TODO Auto-generated method stub
		String tempStr = Frame.getTextField_Search();
		String mainComboString = Frame.getComboBox_main();
		Boolean differentSearch = !Frame.isTorahSearch();
		if (mainComboString != Frame.combo_strTorahPrint) {
			if ((tempStr == null) || (tempStr.length() == 0)) {
				Output.printText("חסר מילת חיפוש", 1);
				return null;
			}
			if ((!differentSearch) && (!mainComboString.equals(Frame.combo_strGimatriaSearch))
					&& !HebrewLetters.checkHebrew(tempStr)) {
				Output.printText("ניתן להקליד בתיבת החיפוש רק אותיות עבריות ורווחים", 1);
				return null;
			}
			if ((!differentSearch) && (mainComboString.equals(Frame.combo_strDilugim)) && (tempStr.length() == 1)) {
				Output.printText("נא להקליד יותר מאות אחת בתיבת החיפוש עבור החיפוש בדילוגים", 1);
				return null;
			}
			if ((!mainComboString.equals(Frame.combo_strGimatriaCalculate)) && (differentSearch)) {
				Output.printText("החיפוש לא נעשה בספר תורה", 1);
				Output.printText("חיפוש נעשה מקובץ - " + ToraApp.differentSearchFile, 1);
				Output.printText("אם ברצונך לחפש בתורה -> תשנה \"קובץ משתמש\" לתורה ",1);
			}
			if (Frame.getComboBox_DifferentSearch(null)==fileMode.LastSearch) {
				Output.printText("החיפוש לא נעשה בספר תורה", 1);
				Output.printText("חיפוש נעשה מתוצאות חיפוש קודם ", 1);
				Output.printText("אם ברצונך לחפש בתורה -> תשנה \"חיפוש קודם\" לתורה ",1);
			}			
		}
		// args are used  at the end
		// approximately line 197
		// in Method
		// Methods.arrayMethods.get
		Object[] args = { null };
		int selection = 0;
		try {
			switch (mainComboString) {
			case Frame.combo_strSearch:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 7);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_wholeWord();
				args[2] = Frame.getCheckBox_gimatriaSofiot();
				args[3] = Frame.get_searchRange();
				args[4] = Frame.getCheckbox_searchMultiple();
				if ((Frame.getCheckbox_searchMultiple()) && (Frame.getTextField_padding().length() > 0)) {
					args[5] = Frame.getTextField_padding();
					if (!HebrewLetters.checkHebrew((String)args[5]) || (differentSearch)) {
						Output.printText("ניתן להקליד בתיבת החיפוש רק אותיות עבריות ורווחים", 1);
						return null;
					}
					args[6] = (Frame.getComboBox_sub_Index() == 0) ? true : false;
					Frame.setSaveMode_search(Frame.getComboBox_sub_Index());
					Frame.setString_searchSTR2((String) args[5]);
				}
				Frame.setBool_searchMultiple(Frame.getCheckbox_searchMultiple());
				Frame.showProgressBar(true, 0b01);
				selection = Methods.id_searchWords;
				break;
			case Frame.combo_strGimatriaSearch:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 5);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_wholeWord();
				args[2] = Frame.getCheckBox_gimatriaSofiot();
				args[3] = Frame.get_searchRange();
				//multiple whole words
				args[4] = Frame.getCheckbox_searchMultiple();
				Frame.setBool_gimatriaMultiple(Frame.getCheckbox_searchMultiple());
				Frame.showProgressBar(true, 0b01);
				selection = Methods.id_searchGimatria;
				break;
			case Frame.combo_strGimatriaCalculate:
				args = Arrays.copyOf(args, 2);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_gimatriaSofiot();
				selection = Methods.id_calculateGimatria;
				break;
			case Frame.combo_strDilugim:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 8);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_gimatriaSofiot();
				Boolean exitCode = false;
				Frame.setBool_reverseDilug(Frame.getCheckbox_searchMultiple());
				Output.printText("");
				if (!StringUtils.isNumeric(Frame.getTextField_dilugMin().trim())) {
					Output.printText("שדה 'דילוג מינימים' צריך להיות מספר", 1);
					exitCode = true;
				}
				if (!StringUtils.isNumeric(Frame.getTextField_dilugMax().trim())) {
					Output.printText("שדה 'דילוג מקסימים' צריך להיות מספר", 1);
					exitCode = true;
				}
				if (!StringUtils.isNumeric(Frame.getTextField_padding().trim())) {
					Output.printText("שדה 'מספר אותיות' צריך להיות מספר", 1);
					exitCode = true;
				}
				if (exitCode)
					return null;
				args[2] = Frame.getTextField_dilugMin().trim();
				args[3] = Frame.getTextField_dilugMax().trim();
				args[4] = Frame.getTextField_padding().trim();
				Frame.setString_padding_Dilug((String) args[4]);
				args[5] = Frame.get_searchRange();
				args[6] = Frame.getCheckbox_searchMultiple();
				Frame.showProgressBar(true, 0b11);
				if (Frame.getComboBox_sub_Index() == 0) {
					selection = Methods.id_searchDilugim;
				} else {
					args[7] = Frame.getComboBox_sub_Index();
					selection = Methods.id_searchDilugWordPasuk;
				}
				Frame.setSaveMode_dilugim(Frame.getComboBox_sub_Index());
				break;
			case Frame.combo_strLetterSearch:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 13);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_gimatriaSofiot();
				args[2] = Frame.get_searchRange();
				args[3] = Frame.getComboBox_sub_Index();
				Frame.setSaveMode_letter(Frame.getComboBox_sub_Index());
				// args[3] mode 0 = Words-Single
				//         mode 1 = Words-Multiple
				//         mode 2 = Psukim
				args[4] = Frame.getCheckbox_letterOrder1();
				args[5] = Frame.getCheckbox_first1();
				args[6] = Frame.getCheckbox_last1();
				// 7 = exact spaces
				args[7] = Frame.getCheckBox_wholeWord();
				switch ((int)args[3]) {
				case 2:
				case 3:
					Frame.setBool_letter_exactSpaces((Boolean)args[7]);
					break;
				}
				switch ((int)args[3]) {
				case 1:
				case 3:
					//8 = searchSTR2
					args[8] = Frame.getTextField_padding();
					if (!HebrewLetters.checkHebrew((String)args[8]) || (differentSearch)) {
						Output.printText("ניתן להקליד בתיבת החיפוש רק אותיות עבריות", 1);
						return null;
					}
					Frame.setString_searchSTR2((String) args[8]);
					args[9] = Frame.getCheckbox_letterOrder2();
					args[10] = Frame.getCheckbox_first2();
					//11 = last2
					args[11] = Frame.getCheckBox_countPsukim();
					//12 = sofiot
					args[12] = Frame.getCheckbox_searchMultiple();
					Frame.setBool_letter_last2((Boolean)args[11]);
					break;
				}
				Frame.showProgressBar(true, 0b01);
				selection = Methods.id_searchLetters;
				break;
			case Frame.combo_strCountSearch:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 5);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_wholeWord();
				args[2] = Frame.getCheckBox_gimatriaSofiot();
				args[3] = Frame.get_searchRange();
				args[4] = Frame.getTextField_padding().trim();
				Frame.setString_countIndex((String) args[4]);
				Frame.showProgressBar(true, 0b01);
				selection = Methods.id_searchCount;
				break;
			case Frame.combo_strTorahPrint:
				args = Arrays.copyOf(args, 2);
				args[0] = Frame.get_searchRange();
				args[1] = Frame.getCheckbox_searchMultiple();
				Frame.setBool_placeInfo(Frame.getCheckbox_searchMultiple());
				selection = Methods.id_printTorah;
				break;
			case Frame.combo_strTorahRangeReport:
				DialogSearchRangeFrame dFrame = DialogSearchRangeFrame.getInstance(false);
				dFrame.setVisible(true);
				selection = -1;
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		try {
			if (selection >= 0) {
				Methods.arrayMethods.get(selection).run(args);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	protected void process(List<Integer> chunks) {
		for (int chunk : chunks) {
			// System.out.println(chunk+"%");
			Frame.setProgressBar(chunk);
		}
	}

	protected void done() {
		currentProgress = 0;
		Frame.resetButton_storeSearch();
		Frame.showProgressBar(false, 0b11);
		// frame.setButtonEnabled();
		Frame.setMethodRunning(false);
		instance = null;
	}
}
