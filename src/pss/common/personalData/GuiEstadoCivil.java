package  pss.common.personalData;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEstadoCivil extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiEstadoCivil() throws Exception {
  }


  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizEstadoCivil(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 987; }
  @Override
	public String  GetTitle()    throws Exception  { return "Estados Civil"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormEstadoCivil.class; }
  @Override
	public String  getKeyField() throws Exception { return "id_estadocivil"; }
  @Override
	public String  getDescripField()                { return "descripcion"; }

	@Override
	public Class<? extends JBaseForm> getFormEmbedded() throws Exception {		return FormEstadoCivilEmbedded.class;}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizEstadoCivil GetcDato() throws Exception {
    return (BizEstadoCivil) this.getRecord();
  }

}
