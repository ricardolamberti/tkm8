package pss.bsp.contrato.wizardBackend;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormWizardFinContrato  extends JBaseForm {


	private static final long serialVersionUID = 1477827540739L;


	  /**
	   * Constructor de la Clase
	   */
	  public FormWizardFinContrato() throws Exception {
	  }

	  public GuiWizardContratoBackend getWin() { return (GuiWizardContratoBackend) getBaseWin(); }

		@Override
		public boolean canApplyAction() throws Exception {
				return false;
		}

	
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "paso" ).setHide(true);
		  AddCardPanel("Detalle", 110).setDisplayOnly(true);
	    
	    
	    AddItemButton( "Anterior", 20 , true).setSizeColumns(6);
	    AddItemButton( "Generar", 40 , true).setResponsiveClass("btn-primary").setSizeColumns(6);

	 	 
	  } 
	  
	

	}  //  @jve:decl-index=0:visual-constraint="14,3"
