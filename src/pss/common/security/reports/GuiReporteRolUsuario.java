package  pss.common.security.reports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;


public class GuiReporteRolUsuario extends JWin {

  public GuiReporteRolUsuario() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizReporteRolUsuario(); }
  @Override
	public int GetNroIcono()       throws Exception { return 43; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte de Usuarios por Rol"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormReporteRolUsuario.class; }


  @Override
	public void createActionMap() throws Exception {
  }

  public BizReporteRolUsuario GetcDato() throws Exception {
    return (BizReporteRolUsuario)getRecord();
  }


}



