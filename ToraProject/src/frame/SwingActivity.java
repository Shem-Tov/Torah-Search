package frame;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingWorker;

import ToraApp.Methods;

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
	
	public void callProcess(int num) 
	{
		currentProgress=num;
		int factor=(int)(100*currentProgress/finalProgress);
		publish(factor);
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		Object[] args = { null };
		int selection = 0;
		switch (frame.getComboBox_main()) {
		case 0:
			args = Arrays.copyOf(args, 2);
			args[0] = frame.getTextField_Search();
			args[1] = frame.getCheckBox_wholeWord();
			selection = Methods.id_searchWords;
			break;
		case 1:
			args = Arrays.copyOf(args, 4);
			args[0] = frame.getTextField_Search();
			args[1] = frame.getCheckBox_wholeWord();
			args[2] = frame.getCheckBox_gimatriaSofiot();
			args[3] = frame.getCheckBox_countPsukim();
			selection = Methods.id_searchGimatria;
			break;
		case 2:
			args = Arrays.copyOf(args, 2);
			args[0] = frame.getTextField_Search();
			args[1] = frame.getCheckBox_gimatriaSofiot();
			selection = Methods.id_calculateGimatria;
			break;
		case 3:
			args = Arrays.copyOf(args, 6);
			args[0] = frame.getTextField_Search();
			args[1] = frame.getCheckBox_gimatriaSofiot();
			args[2] = frame.getTextField_dilugMin();
			args[3] = frame.getTextField_dilugMax();
			args[4] = frame.getTextField_padding();
			String offset1 = frame.getTextField_offset();
			args[5] = ((offset1==null)||(offset1.length()==0)) ? "0":offset1;
			selection = Methods.id_searchDilugim;
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
			frame.setProgressBar(chunk);
		}
	}
	
	protected void done()
	{
		currentProgress=0;
		frame.showProgressBar(false);
		frame.setButtonEnabled();
		instance=null;
	}
}
