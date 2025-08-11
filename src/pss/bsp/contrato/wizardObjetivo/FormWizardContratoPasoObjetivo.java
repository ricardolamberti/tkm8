package pss.bsp.contrato.wizardObjetivo;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormWizardContratoPasoObjetivo extends JBaseForm {


	private static final long serialVersionUID = 1477827540739L;

	  /**
	   * Propiedades de la Clase
	   */

		@Override
		public boolean canApplyAction() throws Exception {
				return false;
		}

	  /**
	   * Constructor de la Clase
	   */
	  public FormWizardContratoPasoObjetivo() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiWizardContratoObjetivo getWin() { return (GuiWizardContratoObjetivo) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {

	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "paso" ).setHide(true);
		  AddCardPanel("Detalle", 110 );
	    
	    
	    AddItemButton( "Anterior", 20 , true).setSizeColumns(6);
	    AddItemButton( "Siguiente", 10 , true).setResponsiveClass("btn-primary").setSizeColumns(6);
	  } 
	  



	}  //  @jve:decl-index=0:visual-constraint="14,3"
