package pss.bsp.contrato.wizardBackend;

import pss.bsp.contrato.conocidos2.BizContratoConocidoV2;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;
import pss.www.ui.JWebIcon;

public class FormWizardContratoTipo extends JBaseForm {


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
	  public FormWizardContratoTipo() throws Exception {
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
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "paso" ).setHide(true);

//	    
//		  	  public static JRecords<BizVirtual> getTiposContratosBackend() throws Exception {
//		  			JRecords<BizVirtual> records =JRecords.createVirtualBDs();
//		  			records.addItem(JRecord.virtualBD(GuiDetalleCopa.class.getCanonicalName(), "Contrato Copa por rutas", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleCopaWS.class.getCanonicalName(), "Contrato Copa Wallet Share", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleAvianca.class.getCanonicalName(), "Contrato Avianca por puntos", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleRuta.class.getCanonicalName(), "Contrato por rutas", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleObjetivo.class.getCanonicalName(), "Contrato Backend", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleBackendDobleRuta.class.getCanonicalName(), "Contrato Backend dos objetivos", 15200));
//		  			records.addItem(JRecord.virtualBD(GuiDetalleDatamining.class.getCanonicalName(), "Contrato basado en Datamining", 15200));
//		  			return records;
//		  		}
//		  		
	    JIterator<BizContratoConocidoV2> it = getWin().GetcDato().getModelosContratosCandidatos().getStaticIterator();
	    
	    while (it.hasMoreElements()) {
	    	BizContratoConocidoV2 c = it.nextElement();
	      AddImageCard(null, JWebIcon.getPssDataResource(c.getImagenMenu()), c.getDescripcion(), (int)c.getId()+1000).setMaximizeIcon( JWebIcon.getPssDataResource(c.getImagen())).setResponsiveClass("panel-primary").setSizeColumns(3);
	    	
	    }

	    JFormImageCardResponsive ic = null;
	    JFormPanelResponsive row = AddItemRow();
	    ic=row.AddImageCard( "Contrato Backend" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Un objetivo configurable", 11);
	    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato Backend dos objetivos" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Dos objetivo configurables", 12);
	    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato por rutas" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "multiples objetivos por ruta", 13);
	    if (ic!=null) ic.setResponsiveClass("panel-yellow").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato Copa por Interfaz" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Copa por interfaz", 14);
	    if (ic!=null) ic.setResponsiveClass("panel-info").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato Copa por rutas" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Copa por rutas", 19);
	    if (ic!=null) ic.setResponsiveClass("panel-info").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato Copa Wallet Share" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Copa basado en wallet share", 15);
	    if (ic!=null) ic.setResponsiveClass("panel-info").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
	    ic=row.AddImageCard( "Contrato Avianca por puntos" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Avianca por puntos", 16);
	    if (ic!=null) ic.setResponsiveClass("panel-danget").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
		  ic= row.AddImageCard( "Contrato basado en Datamining" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Basado en indicadores de ticketminig", 17);
	    if (ic!=null) ic.setResponsiveClass("panel-default").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
		  ic= row.AddImageCard( "Contrato Vacio" , JWebIcon.getResponsiveIcon("fa fa-edit fa-3x"), "Basado en indicadores de ticketminig", 18);
	    if (ic!=null) ic.setResponsiveClass("panel-danger").setComplexColumnClass("clearfix col-2 col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12");
		  AddItemButton( "Anterior", 20 , true).setSizeColumns(6);
		  AddItemButton( "Siguiente", 10 , true).setResponsiveClass("btn-primary").setSizeColumns(6);
	  } 
	  

	  JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers events = new GuiCarriers();
	  	if (one) events.getRecords().addFilter("carrier",getWin().GetcDato().getAerolineas());
	  	return events;
	  }

	}  //  @jve:decl-index=0:visual-constraint="14,3"
