package frame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import hebrewLetters.HebrewLetters;
import ioManagement.Methods;
import ioManagement.Output;

public class SwingActivity extends SwingWorker<Void, Integer> {

	private static SwingActivity instance;
	private static int currentProgress;
	private static final int finalProgress_hardCoded = 5846;
	private static int finalProgress = finalProgress_hardCoded;

	public static void setFinalProgress(int[] range) {
		if (range[1] == 0) {
			finalProgress = finalProgress_hardCoded;
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
		if ((tempStr == null) || (tempStr.length() == 0)) {
			Output.printText("חסר מילת חיפוש", 1);
			return null;
		}
		if ((Frame.getComboBox_main() != Frame.combo_strGimatriaSearch) && !HebrewLetters.checkHebrew(tempStr)) {
			Output.printText("ניתן להקליד בתיבת החיפוש רק אותיות עבריות ורווחים", 1);
			return null;
		}
		if ((Frame.getComboBox_main() == Frame.combo_strDilugim) && (tempStr.length()==1)) {
			Output.printText("נא להקליד יותר מאות אחת בתיבת החיפוש עבור החיפוש בדילוגים", 1);
			return null;
		}
		Object[] args = { null };
		int selection = 0;
		switch (Frame.getComboBox_main()) {
		case Frame.combo_strSearch:
			Tree.getInstance().clearTree();
			args = Arrays.copyOf(args, 4);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_wholeWord();
			args[2] = Frame.getCheckBox_gimatriaSofiot();
			args[3] = Frame.get_searchRange();
			Frame.showProgressBar(true, 0b01);
			selection = Methods.id_searchWords;
			break;
		case Frame.combo_strGimatriaSearch:
			Tree.getInstance().clearTree();
			args = Arrays.copyOf(args, 4);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_wholeWord();
			args[2] = Frame.getCheckBox_gimatriaSofiot();
			args[3] = Frame.get_searchRange();
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
			args = Arrays.copyOf(args, 7);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_gimatriaSofiot();
			Boolean exitCode = false;
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
			Frame.showProgressBar(true, 0b11);
			selection = Methods.id_searchDilugim;
			break;
		case Frame.combo_strLetterSearch:
			Tree.getInstance().clearTree();
			args = Arrays.copyOf(args, 4);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_gimatriaSofiot();
			args[2] = Frame.get_searchRange();
			if (Frame.getComboBox_sub() == Frame.comboBox_sub_Strings[0]) {
				args[3] = false;
			} else {
				args[3] = true;
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
			Frame.showProgressBar(true, 0b01);
			selection = Methods.id_searchCount;
			break;
		case Frame.combo_strTorahPrint:
			selection = Methods.id_printTorah;
			break;
		}
		try {
			if (selection > 0) {
				Methods.arrayMethods.get(selection - 1).run(args);
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
