package frame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

import ioManagement.Methods;

public class SwingActivity extends SwingWorker<Void,Integer>{

	private static SwingActivity instance;
	private static int currentProgress;
	private static int finalProgress = 5846;
	
	public static void setFinalProgress(int num) {
		finalProgress = num;
	}

	public static SwingActivity getInstance() {
		if (instance == null) {
			instance = new SwingActivity();
		}
		return instance;
	}
	
	public void callProcess(int num) {
		callProcess(num,1,1,1);
	}
	
	public void callProcess(int num, int thisDilug, int minDilug, int maxDilug) 
	{
		currentProgress=num;
		int factor=(int)(100*(((float)currentProgress/finalProgress)+(thisDilug-minDilug))/(maxDilug-minDilug+1));
		publish(factor);
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		Object[] args = { null };
		int selection = 0;
		switch (Frame.getComboBox_main()) {
		case Frame.combo_strSearch:
			Frame.showProgressBar(true,0b01);
			args = Arrays.copyOf(args, 3);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_wholeWord();
			args[2] = Frame.getCheckBox_gimatriaSofiot();
			selection = Methods.id_searchWords;
			break;
		case Frame.combo_strGimatriaSearch:
			Frame.showProgressBar(true,0b01);
			args = Arrays.copyOf(args, 4);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_wholeWord();
			args[2] = Frame.getCheckBox_gimatriaSofiot();
			args[3] = Frame.getCheckBox_countPsukim();
			selection = Methods.id_searchGimatria;
			break;
		case Frame.combo_strGimatriaCalculate:
			args = Arrays.copyOf(args, 2);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_gimatriaSofiot();
			selection = Methods.id_calculateGimatria;
			break;
		case Frame.combo_strDilugim:
			Frame.showProgressBar(true,0b11);
			args = Arrays.copyOf(args, 6);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_gimatriaSofiot();
			args[2] = Frame.getTextField_dilugMin();
			args[3] = Frame.getTextField_dilugMax();
			args[4] = Frame.getTextField_padding();
			String offset1 = Frame.getTextField_offset();
			args[5] = ((offset1==null)||(offset1.length()==0)) ? "0":offset1;
			selection = Methods.id_searchDilugim;
			break;
		case Frame.combo_strLetterSearch:
			Frame.showProgressBar(true,0b01);
			args = Arrays.copyOf(args, 3);
			args[0] = Frame.getTextField_Search();
			args[1] = Frame.getCheckBox_gimatriaSofiot();
			selection = Methods.id_searchLetters;
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
	
	protected void process(List<Integer> chunks)
	{
		for (int chunk:chunks) {
			//System.out.println(chunk+"%");
			Frame.setProgressBar(chunk);
		}
	}
	
	protected void done()
	{
		currentProgress=0;
		Frame.showProgressBar(false,0b11);
		//frame.setButtonEnabled();
		Frame.setMethodRunning(false);
		instance=null;
	}
}
