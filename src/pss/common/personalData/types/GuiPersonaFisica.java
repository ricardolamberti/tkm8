package  pss.common.personalData.types;

import pss.common.personalData.GuiPersona;
import pss.core.services.records.JRecord;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaFisica extends GuiPersona {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPersonaFisica() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizPersonaFisica(); }
  @Override
	public String  GetTitle()    throws Exception  { return "P.Física "+GetcDato().getNombreCompleto(); }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormPersonaFisica.class; }
  @Override
	public String  getDescripField()                { return "nombre_completo"; }
//	@Override
//	public boolean canConvertToURL() throws Exception {
//		return false;
//	}

}
