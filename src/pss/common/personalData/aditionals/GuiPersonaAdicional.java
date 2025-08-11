package pss.common.personalData.aditionals;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaAdicional extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPersonaAdicional() throws Exception {
  }

  @Override
	public JRecord ObtenerDato() throws Exception { return new BizPersonaAdicional();}
  @Override
	public int GetNroIcono()   throws Exception { return 10023; }
  @Override
	public String GetTitle()   throws Exception { return "Preferencia"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPersonaAdicional.class; }
  @Override
	public String  getKeyField() throws Exception { return "tipo"; }
  @Override
	public String  getDescripField() { return "observacion"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPersonaAdicional GetcDato() throws Exception {
    return (BizPersonaAdicional) this.getRecord();
  }
  
  
}

