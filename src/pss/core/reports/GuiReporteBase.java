package pss.core.reports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;

public class GuiReporteBase extends JWin {

  public GuiReporteBase() throws Exception {
  	super() ;
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReporteBase(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return ""; }

  @Override
	public void createActionMap() throws Exception {
  }

  public BizReporteBase GetcDato() throws Exception {
    return (BizReporteBase) this.getRecord();
  }

//  @Override
//	public JBaseForm showNewForm() throws Exception {
//    JBaseForm oForm = super.showNewForm();
//    oForm.SetReportMode();
//    return oForm;
//  }

}



