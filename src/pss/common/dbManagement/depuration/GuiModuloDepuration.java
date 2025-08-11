package  pss.common.dbManagement.depuration;

import pss.common.scheduler.GuiProcess;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloDepuration extends GuiModulo {

  public GuiModuloDepuration() throws Exception {
    super();
    SetModuleName( "Depuración" );
    SetNroIcono  ( 913 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction(1, "Depuración" , null,913 , true, false, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
    this.addAction(3, "Depuraciones", null ,913 , true, false, true, "Group");
  }

  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1) return new JActQuery(this);
  	if ( a.getId()==3) return new JActWins(this.ObtenerDepuraciones());
  	return null;
  }
  

  public GuiProcess ObtenerConfigProceso() throws Exception {
//    if ( ! UITools.showConfirmation("Confirmación", "Esta Ud. Seguro?") ) return;
    return  null;
  }

  public JWins ObtenerDepuraciones() throws Exception {
    GuiDepurations oDeps = new GuiDepurations();
    oDeps.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
    return oDeps;
  }

}



