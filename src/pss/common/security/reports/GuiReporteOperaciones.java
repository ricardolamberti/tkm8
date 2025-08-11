package  pss.common.security.reports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiReporteOperaciones extends JWin {

  public GuiReporteOperaciones() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReporteOperaciones(); }
  @Override
	public int GetNroIcono()       throws Exception { return 43; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte de Operaciones"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormReporteOperaciones.class; }


  @Override
	public void createActionMap() throws Exception {
  }

//  @Override
//	public JBaseForm showNewForm(boolean zShow) throws Exception {
//    JBaseForm oForm = super.showNewForm(zShow);
//    oForm.SetReportMode();
//    return oForm;
//  }
}



