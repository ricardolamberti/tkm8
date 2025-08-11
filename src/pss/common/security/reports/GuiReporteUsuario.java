package  pss.common.security.reports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiReporteUsuario extends JWin {

  public GuiReporteUsuario() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReporteUsuario(); }
  @Override
	public int GetNroIcono()       throws Exception { return 43; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte de Usuarios"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormReporteUsuario.class; }


  @Override
	public void createActionMap() throws Exception {
  }

}



