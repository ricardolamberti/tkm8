package  pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiNodoDatabase extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodoDatabase() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizNodoDatabase(); }
  @Override
	public int GetNroIcono()       throws Exception { return GuiIcon.DATABASE_ICON; }
  @Override
	public String GetTitle()       throws Exception { return "Base de datos"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormNodoDatabase.class; }
  @Override
	public String getKeyField()   throws Exception { return "nodo"; }
  @Override
	public String getDescripField()                  { return "nodo"; }



  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizNodoDatabase GetcDato() throws Exception {
    return (BizNodoDatabase) this.getRecord();
  }


}
