package pss.bsp.contrato.wizardBackend;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.GuiContratos;
import pss.bsp.contrato.conocidos.BizContratoConocido;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormWizardContratoPasoElegirContrato extends JBaseForm {


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
	  public FormWizardContratoPasoElegirContrato() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiWizardContratoBackend getWin() { return (GuiWizardContratoBackend) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {

	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( "", CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( "", CHAR, OPT, "paso" ).setHide(true).SetValorDefault(1);
	    
	    
	    JFormFieldsetResponsive p1 = AddItemFieldset("Organización de contratos");
	    p1.AddItemCombo( "Aerolinea principal", CHAR, REQ, "id_aerolineas" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean bOneRow) throws Exception {
	    		return getAerolineas(bOneRow);
	    	}
	    }).setShowKey(true).setRefreshForm(true);
	    p1.AddItemCombo( "Periodo", CHAR, REQ, "periodo" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean bOneRow) throws Exception {
	    		return JWins.createVirtualWinsFromMap(BizContratoConocido.getTipoPeriodos());
	    	}
	    }).setRefreshForm(true);
	    p1.AddItemDateTime("Desde que fecha?", DATE, OPT, "fecha_desde").setRefreshForm(true);
		   
	    JFormFieldsetResponsive p2 = AddItemFieldset("Contratos donde se aplicará");
	    p2.AddItemListBox( "Contratos" , CHAR, OPT, "id_contrato" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		
	    		return getContratos(one);
	    	}
	    }).setResponsiveClass("gallery_cubo").setRefreshForm(true);
	    
	    AddItemButton( "Siguiente", 10 , true).setResponsiveClass("btn-primary");
	  } 
	  JWins getContratos(boolean one) throws Exception {
	  	
	  	GuiContratos events = new GuiContratos();
	  	events.setRecords(getWin().GetcDato().getContratosCandidatos());
	  
	  	return events;
	  }
 

	  JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers events = new GuiCarriers();
	  	if (one) events.getRecords().addFilter("carrier",getWin().GetcDato().getAerolineas());
	  	events.addOrderAdHoc("carrier", "ASC");
	  	return events;
	  }

	}  //  @jve:decl-index=0:visual-constraint="14,3"
