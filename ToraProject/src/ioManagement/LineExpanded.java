package ioManagement;

public class LineExpanded {
	LineHtmlReport lineHtmlReport;

	LineReport lineReport;
	
	LineExpanded(LineReport lineReport, LineHtmlReport lineHtmlReport){
		this.lineReport = lineReport;
		this.lineHtmlReport = lineHtmlReport;
	}
	
	public LineHtmlReport getLineHtmlReport() {
		return lineHtmlReport;
	}

	public LineReport getLineReport() {
		return lineReport;
	}
}
