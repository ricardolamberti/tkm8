package pss.bsp.contrato.wizardObjetivo;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.common.event.sql.BizSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.core.ui.components.JPssButton;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssHtmlTextArea;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormWizardFinContrato  extends JBaseForm {


	private static final long serialVersionUID = 1477827540739L;


	  /**
	   * Constructor de la Clase
	   */
	  public FormWizardFinContrato() throws Exception {
	  }

	  public GuiWizardContratoObjetivo getWin() { return (GuiWizardContratoObjetivo) getBaseWin(); }

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
		  AddCardPanel("Detalle", 100).setDisplayOnly(true);
	    
	    
	    AddItemButton( "Anterior", 20 , true).setSizeColumns(6);
	    AddItemButton( "Generar", 40 , true).setResponsiveClass("btn-primary").setSizeColumns(6);

	 	 
	  } 
	  
	

	}  //  @jve:decl-index=0:visual-constraint="14,3"
