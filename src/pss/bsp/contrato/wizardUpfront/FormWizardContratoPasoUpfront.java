package pss.bsp.contrato.wizardUpfront;

import pss.bsp.contrato.detalleUpfrontRutas.GuiDetalleUpfrontRuta;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormWizardContratoPasoUpfront extends JBaseForm {


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
	  public FormWizardContratoPasoUpfront() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiWizardContratoUpfront getWin() { return (GuiWizardContratoUpfront) getBaseWin(); }

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
	  

	  JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers events = new GuiCarriers();
	  	if (one) events.getRecords().addFilter("carrier",getWin().GetcDato().getAerolineas());
	  	return events;
	  }

	}  //  @jve:decl-index=0:visual-constraint="14,3"
