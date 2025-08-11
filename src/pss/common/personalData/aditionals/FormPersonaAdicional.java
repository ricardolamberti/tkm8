package pss.common.personalData.aditionals;

import pss.common.personalData.aditionals.types.GuiTipoAdicionales;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormPersonaAdicional extends JBaseForm {

	
	private GuiPersonaAdicional getWin() { return (GuiPersonaAdicional) this.getBaseWin(); }
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormPersonaAdicional() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(3);
    col.AddItemEdit("company", CHAR, REQ, "company" ).hide();
    col.AddItemEdit("idPersona", ULONG, OPT, "id_persona").hide();
    col.AddItemCombo( "Tipo", UINT, REQ, "tipo", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getTipos(one);
    	}
    });
    col.AddItemArea("Descripción", CHAR, REQ, "observacion");
  }
  
  private JWins getTipos(boolean one) throws Exception {
  	GuiTipoAdicionales v = new GuiTipoAdicionales();
  	if (one) {
  		v.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
  		v.getRecords().addFilter("tipo", this.getWin().GetcDato().getTipo());
  	} else {
  		v.getRecords().addFilter("company", this.findControl("company").getValue());
  	}
  	return v;
  }

  
}