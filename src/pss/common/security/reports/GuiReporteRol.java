package  pss.common.security.reports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiReporteRol extends JWin {

  public GuiReporteRol() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReporteRol(); }
  @Override
	public int GetNroIcono()       throws Exception { return 43; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte de Roles"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormReporteRol.class; }

  @Override
	public void createActionMap() throws Exception {
  }

}



