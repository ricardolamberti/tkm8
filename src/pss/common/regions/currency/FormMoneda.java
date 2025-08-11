package pss.common.regions.currency;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormMoneda extends JBaseForm {



  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public FormMoneda() throws Exception {

  }

  public GuiMoneda GetWin() {
    return (GuiMoneda) GetBaseWin();
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
		JFormPanelResponsive panel = this.AddItemColumn(6);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);

		panel.AddItemEdit( "Código", CHAR, REQ, "codigo" );
		panel.AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
		panel.AddItemEdit( "Símbolo", CHAR, REQ, "simbolo" );
		panel.AddItemEdit( "Formato Moneda", CHAR, REQ, "currency_format" );
		panel.AddItemEdit( "Decimales Adicionales", INT, REQ, "optional_decimals" );

  }



}  //  @jve:decl-index=0:visual-constraint="10,10"
