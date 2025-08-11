package pss.core.win.submits;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;

public class JActReport extends JActFileGenerate {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2199579210846916239L;

	public JActReport(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
		
	@Override
	public String generate() throws Exception {
  	JBDReportes report = this.getReport();
  	if (this.isWeb()) {
//  		this.checkFile();
    	String fileName = BizUsuario.getUsr().getCompany()+"/"+report.toString()+".pdf";
    	fileName = fileName.replace("[]null", "");
    	JTools.createDirectories(JPath.PssPathTempFiles(), fileName);
  		report.setType(JReport.REPORTES_PDF);
  		report.setOutputFile(JPath.PssPathTempFiles()+"/"+fileName);
    	report.execProcessInsert();
    	return fileName;
  	} else {
    	report.execProcessInsert();
    	return null;
  	}
	}
	
	  
	  
  private JBDReportes getReport() throws Exception {
  	return (JBDReportes) this.getWinResult().getRecord();
  }

  @Override
	public boolean isOnlySubmit() { return true;}
  
//  @Override
//	public String getExtension() throws Exception {
//  	return ".pdf";
//  }

}
