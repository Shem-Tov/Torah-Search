package frame;

import java.awt.ComponentOrientation;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Table getInstance(Boolean isDilug) {
		if (instance == null) {
			instance = new Table(isDilug);
		}
		return instance;
	}
	
	public void newTable(Boolean isDilug) {
		rowLines = new ArrayList<String>();
		Vector<String> headers = new Vector<String>();
		if (isDilug) {
			headers.addElement("דילוג");
		}
		headers.addElement("חיפוש");
		headers.addElement("מיקום");
		headers.addElement("פסוק");
		Vector<Vector<String>> dataVect = new Vector<Vector<String>>();
		this.setModel(new DefaultTableModel(dataVect, headers));
        model = (DefaultTableModel) this.getModel();
 	}

	private static Table instance;
	
	private String packHtml(String str) {
		return "<html>"+str+"</html>";
	}
	
	//SUMS 100% - in Percentage
	double[] cWidthDilug = {0.05, 0.12, 0.15, 0.68};
	double[] cWidthNoDilug = {0.1, 0.1, 0.8};
	int baseRowHeight = 70;
	int lettersPerRow = 40;
	
	public void resizeColumns(Boolean isDilug, int tW) {
	    TableColumn column;
	    TableColumnModel jTableColumnModel = this.getColumnModel();
	    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

	    int cantCols = jTableColumnModel.getColumnCount();
	    for (int i = 0; i < cantCols; i++) {
	        column = jTableColumnModel.getColumn(i);
	        int pWidth = (int) Math.round((isDilug?cWidthDilug[i]:cWidthNoDilug[i]) * tW);
	        column.setPreferredWidth(pWidth);
	        // right align
	        column.setCellRenderer(rightRenderer);
	    }
	}

	public void updateTableDimensions(Boolean isDilug, int panelWidth)
	{
	   /*
		int lineColumn = (isDilug)? 3:2;
		for (int row = 0; row < this.getRowCount(); row++)
	    {
	       int rowHeight = this.getRowHeight();
           Component comp = this.prepareRenderer(this.getCellRenderer(row, lineColumn), row, lineColumn);
	       rowHeight = Math.max(rowHeight, (int)(comp.getPreferredSize().height*2.2));
	       this.setRowHeight(row, rowHeight);
	    }
	    */
	    resizeColumns(isDilug, panelWidth);
	}
	
	public void addToTable(String word, String place, String line, String expandedLine,String... dilug) {
		if (dilug.length > 0) {
			model.addRow(new Object[] { packHtml(dilug[0]), packHtml(word)
					, packHtml(place), packHtml(line) });
		} else {
			model.addRow(new Object[] { packHtml(word)
					, packHtml(place), packHtml(line) });
		}
		rowLines.add(expandedLine);
	}
	
    @Override
	public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        //int colIndex = columnAtPoint(p);

        try {
              //tip = getValueAt(rowIndex, colIndex).toString();
        	tip = rowLines.get(rowIndex); 	
        } catch (RuntimeException e1) {
            //catch null pointer exception if mouse is over an empty line
        }

        return tip;
    }


	DefaultTableModel model;
	static ArrayList<String> rowLines;
	//JFrame f;

	Table(Boolean isDilug) {
		//f = new JFrame();

			//Implement table cell tool tips.           
		newTable(isDilug);
		this.setRowHeight(Frame.getFontSizeBig()*6);
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.setVisible(true);
		//this.setBounds(30, 40, 200, 300);
	}

	/*
	public static void main(String[] args) {
		new Table(true);
	}
	*/

}
