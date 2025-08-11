package pss.core.data.interfaces.connections;

import pss.core.win.JWin;
import pss.core.winUI.icons.GuiIcon;


public class GuiBDato extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiBDato() throws Exception {
  }

//  @Override
//	public JRecord ObtenerDato() throws Exception { return new JBDato(); }
  @Override
	public String getKeyField() { return "nombre"; }
  @Override
	public String getDescripField() { return "nombre"; }
  @Override
	public String GetTitle() { return "Base de Datos"; }
  @Override
	public int GetNroIcono() { return GuiIcon.DATABASE_ICON; }

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {}

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public JBDato GetcDato() throws Exception {
    return (JBDato)this.getRecord();
  }
  
  
}
