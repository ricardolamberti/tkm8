package pss.common.personalData.types;

import pss.common.personalData.GuiPersona;
import pss.core.services.records.JRecord;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiPersonaJuridica extends GuiPersona {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPersonaJuridica() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizPersonaJuridica(); }
  @Override
	public String  GetTitle()    throws Exception  { return "P.Jurídica "+GetcDato().getNombreCompleto(); }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormPersonaJuridica.class; }
  @Override
	public String  getDescripField()                { return "nombre_completo"; }

  @Override
	public boolean OkAction(BizAction zAct) throws Exception {
    if ( zAct.getId() == 14) return false;
    return super.OkAction(zAct);
  }
//
//	@Override
//	public boolean canConvertToURL() throws Exception {
//		return false;
//	}

}
