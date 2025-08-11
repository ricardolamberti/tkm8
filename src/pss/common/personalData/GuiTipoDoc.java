package  pss.common.personalData;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTipoDoc extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiTipoDoc() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizTipoDoc(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 10036; }
  @Override
	public String  GetTitle()    throws Exception  { return "Tipo de Documento"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormTipoDoc.class; }
  @Override
	public String  getKeyField() throws Exception { return "tipo_doc"; }
  @Override
	public String  getDescripField()                { return "descripcion"; }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizTipoDoc GetcDato() throws Exception {
    return (BizTipoDoc) this.getRecord();
  }


}
