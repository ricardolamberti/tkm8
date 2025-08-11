package  pss.common.personalData;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTelefono extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTelefono() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizTelefono(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 762; }
  @Override
	public String  GetTitle()    throws Exception  { return "Teléfono"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormTelefono.class; }
  @Override
  public Class<? extends JBaseForm> getFormEmbedded() throws Exception { return FormTelefonoEmbbeded.class;}
  @Override
	public String  getKeyField() throws Exception { return "persona"; }
  @Override
	public String  getDescripField()                { return "numero"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPersona GetcDato() throws Exception {
    return (BizPersona) this.getRecord();
  }

  public BizTelefono GetccDato() throws Exception {
    return (BizTelefono) this.getRecord();
  }
 

}
