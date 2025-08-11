package  pss.common.regions;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloRegiones extends GuiModulo {

  public GuiModuloRegiones() throws Exception {
    super();
    SetModuleName( "Generales" );
    SetNroIcono  ( 1 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
     return addAction(1, "Generales", null, 1 , true, false, true, "Group" );
     
  }

  @Override
	public void createActionMap() throws Exception {
    addAction(11, "Lista Paises", null, 1 , true, false, true, "Group" );
//    addAction(12, "Paises Operativos", null, 1 , true, false, true, "Group" );
//    addAction(13, "Empresas", null, 1102 , true, false, true, "Group" );
    this.loadDynamicActions(null);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId() == 1 ) return new JActQuery(this); 
  	if ( a.getId() == 11 ) return new JActWins(new GuiPaisesLista());
//  	if ( a.GetId() == 12 ) return new JActWins(new GuiPaises());
//  	if ( a.GetId() == 13 ) return new JActWins(new GuiCompanies()); 
    return null;
  }

 
  @Override
	public boolean isModuleComponent() throws Exception { return false; }

}
