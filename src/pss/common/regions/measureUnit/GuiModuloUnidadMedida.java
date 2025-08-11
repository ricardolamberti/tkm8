package pss.common.regions.measureUnit;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloUnidadMedida extends GuiModulo {

  public GuiModuloUnidadMedida() throws Exception {
    super();
    SetModuleName( "Medidas" );
    SetNroIcono  ( 100 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction(1, "Medidas", null, 100 , true, true, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
    addAction(2, "Tipos", null, 899 , true, false, true, "Group" );
    addAction(3, "Unidades", null, 100 , true, false, true, "Group" );
  }

  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId() == 1 ) return new JActQuery(this); 
  	if ( a.getId() == 2 ) return new JActWins(new GuiTipoMedidas());
  	if ( a.getId() == 3 ) return new JActWins(new GuiUnidadMedidas());  	
    return null;
  }    

}



