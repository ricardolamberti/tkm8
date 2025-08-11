package  pss.common.layoutWysiwyg;

import pss.common.layout.GuiLayouts;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloWysiwyg extends GuiModulo {

  public GuiModuloWysiwyg() throws Exception {
    super();
    SetModuleName( "Layout Wysiwyg" );
    SetNroIcono  ( 204 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction( 1, "Layout Wysiwyg", null , 204 , true, false, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
    this.addAction( 20, "Layouts Wysiwyg" , null , 204 , true, false, true, "Group");
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1 ) return new JActQuery(this);
  	if ( a.getId()==20) return new JActWins(this.ObtenerLayouts());
  	return null;
  }
    

  public JWins ObtenerLayouts() throws Exception {
  	GuiPlantillas oLayouts = new GuiPlantillas();
 //   oLayouts.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
  	if (!BizUsuario.getUsr().isAdminUser())
  		oLayouts.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
    oLayouts.getRecords().addOrderBy("descripcion");
    return oLayouts;
  }

}