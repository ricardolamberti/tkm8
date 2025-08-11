package pss.common.restJason.apiUser.apiUserDetails;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormApiUserDetail extends JBaseForm {


//-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormApiUserDetail() throws Exception {
  }

  public GuiApiUserDetail GetWin() { return (GuiApiUserDetail) getBaseWin(); }

 


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
	  	JFormControlResponsive c;
		JFormPanelResponsive panel = this.AddItemColumn(6);
  	  	panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
  	  	panel.setSizeLabels(3);		
		panel.AddItemEdit("Empresa", CHAR, OPT, "company").hide();
		panel.AddItemEdit("Usuario", CHAR, OPT, "usuario").hide();

		panel.AddItemCombo( "API", CHAR, OPT, "json_path", new JControlCombo() {
	  		public JWins getRecords(boolean one) throws Exception {
	  			return JWins.createVirtualWinsFromMap(GetWin().GetcDato().getAvailableApiPaths());
	  		}
	  	});
		
  }
  

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
