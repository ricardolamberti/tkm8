package pss.common.paymentManagement;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormEditResponsive;

public class FormPenalizeDebt extends JBaseForm {


private static final long serialVersionUID = 1545312788135L;



  /**
   * Constructor de la Clase
   */
  public FormPenalizeDebt() throws Exception {
  }

  public GuiPenalizeDebt getWin() { return (GuiPenalizeDebt) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		JFormEditResponsive edit;
		edit = AddItemEdit( "Id",  UINT, OPT, "ID" );
		edit.setSizeColumns(6);
		edit = AddItemEdit( "Empresa",  CHAR, OPT, "COMPANY" );
		edit.setSizeColumns(6);
		edit = AddItemEdit( "PENALIZE_TIME",  UINT, OPT, "PENALIZE_TIME" );
		edit.setSizeColumns(6);

  } 
} 
