package pss.common.personalData.aditionals.types;

import pss.common.personalData.aditionals.BizPersonaAdicional;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTipoAdicional extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTipoAdicional() throws Exception {
  }

  @Override
	public JRecord ObtenerDato() throws Exception { return new BizTipoAdicional();}
  @Override
	public int GetNroIcono()   throws Exception { return 10023; }
  @Override
	public String GetTitle()   throws Exception { return "Campo Adicional"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTipoAdicional.class; }
  @Override
	public String  getKeyField() throws Exception { return "tipo"; }
  @Override
	public String  getDescripField() { return "descripcion"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPersonaAdicional GetcDato() throws Exception {
    return (BizPersonaAdicional) this.getRecord();
  }
  
  
}

