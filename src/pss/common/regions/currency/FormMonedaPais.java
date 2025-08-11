package pss.common.regions.currency;

import pss.common.regions.divitions.GuiPaises;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormMonedaPais extends JBaseForm {


 //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormMonedaPais() throws Exception {

  }


 

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
	  
		JFormPanelResponsive panel = this.AddItemColumn(6);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);

		panel.AddItemEdit( "Empresa", CHAR, REQ, "company" ).hide();

		panel.AddItemCombo("País", CHAR, REQ, "pais", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return  new GuiPaises();
			}
		});

		panel.AddItemCombo("Moneda", CHAR, REQ, "moneda", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return  new GuiMonedas();
			}
		});

  }

  
  

}  //  @jve:decl-index=0:visual-constraint="12,10"





