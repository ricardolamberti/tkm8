package  pss.common.security.reports;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;

public class GuiModuloSeguridadReportes extends GuiModulo {

  public GuiModuloSeguridadReportes() throws Exception {
  super();
  SetModuleName( "Reportes" );
  SetNroIcono  ( 5 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction(1, "Reportes", null, 5 , true, false, true, "Group");
  }


  @Override
	public void createActionMap() throws Exception {
    this.addAction( 2, "Usuario" , null , 5 , true, false, true, "Group");
    this.addAction( 4, "Roles de Usuario" , null, 5 , true, false, true, "Group");
    this.addAction( 6, "Rol" , null, 5 , true, false, true, "Group");
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1 ) return new JActQuery(this);
  	if ( a.getId()==2 ) return new JActNew(new GuiReporteUsuario(), 5);
  	if ( a.getId()==4 ) return new JActNew(new GuiReporteRolUsuario(), 5);  	
  	if ( a.getId()==6 ) return new JActNew(new GuiReporteRol(), 5);  	
  	return null;
  }  

}
