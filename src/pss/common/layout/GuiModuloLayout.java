package pss.common.layout;

import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;


public class GuiModuloLayout extends GuiModulo {

  public GuiModuloLayout() throws Exception {
    super();
    SetModuleName( "Layout" );
    SetNroIcono  ( 204 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction( 1, "Layout", null , 204 , true, false, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
    this.addAction( 20, "Layouts" , null , 204 , true, false, true, "Group");
//    this.addAction( 22, "Campos Layouts" , null , 204 , true, false);    
//    this.addAction( 23, "Impresoras" , null , 5 , true, false, true, "Group");    
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1 ) return new JActQuery(this);
  	if ( a.getId()==20) return new JActWins(this.ObtenerLayouts());
//  	if ( a.getId()==22) return new JActWins(new GuiLayoutCampos());  	
//  	if ( a.GetId()==23) return new JActWins(ObtenerPrinters());  	
  	return null;
  }
    

  public JWins ObtenerLayouts() throws Exception {
    GuiLayouts oLayouts = new GuiLayouts();
    oLayouts.getRecords().addFilter("pais", BizUsuario.getUsr().getCountry());
    oLayouts.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
    oLayouts.getRecords().addOrderBy("descripcion");
    return oLayouts;
  }

//  public JWins ObtenerPrinters() throws Exception {
//  	GuiPrinters oWins = new GuiPrinters();
//    oWins.getRecords().addFilter("company", BizUsuario.getUsr().getCountry());
//    return oWins;
//  }
}

