package  pss.common.personalData.tipoCui;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTipoCui extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTipoCui() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizTipoCui(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 10036; }
  @Override
	public String  GetTitle()    throws Exception  { return "Tipo de Cuit"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormTipoCui.class; }
  @Override
	public String  getKeyField() throws Exception { return "tipo_cui"; }
  @Override
	public String  getDescripField()                { return "descripcion"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizTipoCui GetcDato() throws Exception {
    return (BizTipoCui) this.getRecord();
  }


}
