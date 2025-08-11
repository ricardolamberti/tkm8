package pss.tourism.airports;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiAirport extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiAirport() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizAirport(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1105; }
  @Override
	public String GetTitle()       throws Exception { return "Aeropuerto"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormAirport.class; }
  @Override
	public String getKeyField()   throws Exception { return "code"; }
  @Override
	public String getDescripField()                  { return "description"; }


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
  public BizAirport GetcDato() throws Exception {
    return (BizAirport) this.getRecord();
  }


}
