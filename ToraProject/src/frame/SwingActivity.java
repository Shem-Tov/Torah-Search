package frame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import console.Methods;
import hebrewLetters.HebrewLetters;
import ioManagement.ManageIO;
import ioManagement.Output;
import torahApp.ToraApp;

public class SwingActivity extends SwingWorker<Void, Integer> {

	private static SwingActivity instance;
	private int currentProgress;
	private static final int finalProgress_hardCoded = 5846;
	private static int finalProgress = finalProgress_hardCoded;

	public static void setFinalProgress(int[] range) {
		if (range[1] == 0) {
			if (Frame.getCheckBox_DifferentSearch()) {
				finalProgress = ManageIO.countLinesOfFile(ManageIO.fileMode.Different);
			} else {
				finalProgress = finalProgress_hardCoded;
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
		Boolean differentSearch = Frame.getCheckBox_DifferentSearch();
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
				Output.printText("אם ברצונך לחפש בתורה -> הגדרות -> חיפוש בקובץ של המשתמש -> להוריד סימון ",1);
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
				if ((Frame.getCheckbox_searchMultiple()) && (Frame.getTextField_padding().length() > 0)
						&& ((HebrewLetters.checkHebrew(Frame.getTextField_padding())) || (differentSearch))) {
					args[5] = Frame.getTextField_padding();
					args[6] = (Frame.getComboBox_sub_Index() == 0) ? true : false;
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
				args[5] = Frame.get_searchRange();
				args[6] = Frame.getCheckbox_searchMultiple();
				Frame.showProgressBar(true, 0b11);
				if (Frame.getComboBox_sub_Index() == 0) {
					selection = Methods.id_searchDilugim;
				} else {
					args[7] = Frame.getComboBox_sub_Index();
					selection = Methods.id_searchDilugWordPasuk;
				}
				break;
			case Frame.combo_strLetterSearch:
				Tree.getInstance().clearTree();
				args = Arrays.copyOf(args, 6);
				args[0] = Frame.getTextField_Search();
				args[1] = Frame.getCheckBox_gimatriaSofiot();
				args[2] = Frame.get_searchRange();
				if (Frame.getComboBox_sub().equals(Frame.comboBox_sub_Strings_Letters[0])) {
					args[3] = false;
				} else {
					args[3] = true;
				}
				args[4] = Frame.getCheckbox_letterOrder();
				args[5] = Frame.getCheckBox_wholeWord();

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
		Frame.showProgressBar(false, 0b11);
		// frame.setButtonEnabled();
		Frame.setMethodRunning(false);
		instance = null;
	}
}
