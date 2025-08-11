package pss.common.restJason.apiUser;

import pss.common.security.GuiUsuarios;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormApiUser extends JBaseForm {


//-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormApiUser() throws Exception {
  }

  public GuiApiUser GetWin() { return (GuiApiUser) getBaseWin(); }

 


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
		panel.AddItemWinLov("Usuario", CHAR, REQ, "usuario", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getUsuarios(zOneRow);
			}
		});	
		panel.AddItemCheck( "Habilitado", REQ, "active").setAlign(JFormCheckResponsive.LABEL_RIGHT).SetValorDefault(true).SetReadOnly(true);
		panel.AddItemDateTime("Vto. Token", DATETIME, OPT, "token_duedate",6).setSizeLabels(6);
		
		JFormPanelResponsive panelArea = panel.AddItemColumn();
		c = panelArea.AddItemArea("Token", CHAR, OPT, "token" ).setReadOnly(true);
		c.setHeight(100);		
		
		JFormPanelResponsive row = panel.AddItemColumn();
		row.setHeight(20);
		
		JFormPanelResponsive paneltab = panel.AddItemColumn(12);
		JFormTabPanelResponsive tab = paneltab.AddItemTabPanel();
		tab.AddItemListOnDemand(100);
	
	
  }
  
	private JWins getUsuarios(boolean zOneRow) throws Exception {
		GuiUsuarios wins=new GuiUsuarios();
		if (zOneRow) {
			wins.getRecords().addFilter("company", this.GetWin().GetcDato().getCompany());
			wins.getRecords().addFilter("usuario", this.GetWin().GetcDato().getUsuario());
		} else {
			wins.getRecords().addFilter("company", this.findControl("company").getValue());
		}
		return wins;
	}
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
