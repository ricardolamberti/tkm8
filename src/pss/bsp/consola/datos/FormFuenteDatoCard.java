package pss.bsp.consola.datos;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.www.ui.JWebIcon;

public class FormFuenteDatoCard extends JBaseForm {


private static final long serialVersionUID = 1245260233503L;

 
  /**
   * Constructor de la Clase
   */
  public FormFuenteDatoCard() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFuenteDato getWin() { return (GuiFuenteDato) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
//  	 AddItemImage()
//  	AddInfoCard("Cantidad de tickets", LONG, "total", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-yellow").setSizeColumns(3);
//  	AddInfoCard("Boletos emitidos hoy", LONG, "hoy", JWebIcon.getResponsiveIcon("fa fa-plane fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-primary").setSizeColumns(3);
//  	AddInfoCard("Cliente", LONG, "cliente_ok", JWebIcon.getResponsiveIcon("fa fa-desktop fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-green").setSizeColumns(3);
//  	AddInfoCard("Cliente en error", LONG, "cliente_error", JWebIcon.getResponsiveIcon("fa fa-unlink fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-red").setSizeColumns(3);

//	AddInfoCard("Cantidad de tickets", LONG, "total", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-warning").setSizeColumns(3);
//	AddInfoCard("Boletos emitidos hoy", LONG, "hoy", JWebIcon.getResponsiveIcon("fa fa-plane fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-info").setSizeColumns(3);
//	AddInfoCard("Cliente", LONG, "cliente_ok", JWebIcon.getResponsiveIcon("fa fa-desktop fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-success").setSizeColumns(3);
//	AddInfoCard("Cliente en error", LONG, "cliente_error", JWebIcon.getResponsiveIcon("fa fa-unlink fa-5x"),"Fuente de datos", 1).setResponsiveClass("panel-danger").setSizeColumns(3);

		if (!BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).isDependant()) {
			AddInfoCard("Cantidad de tickets", CHAR, "total", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"), "Fuente de datos", 1).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
			AddInfoCard("Boletos emitidos hoy", CHAR, "hoy", JWebIcon.getResponsiveIcon("fa fa-plane fa-5x"), "Fuente de datos", 11).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
			AddInfoCard("Robots", LONG, "cliente_ok", JWebIcon.getResponsiveIcon("fa fa-desktop fa-5x"), "Robots", 43).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
			AddInfoCard("Robots en error", LONG, "cliente_error", JWebIcon.getResponsiveIcon("fa fa-unlink fa-5x"), "Robots offline", 44).setResponsiveClass("border-left-danger shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
		} else {
			AddInfoCard("Cantidad de tickets", CHAR, "total", JWebIcon.getResponsiveIcon("fa fa-database fa-5x"), "Fuente de datos", 11).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
			AddInfoCard("Boletos emitidos hoy", CHAR, "hoy", JWebIcon.getResponsiveIcon("fa fa-plane fa-5x"), "Fuente de datos", 11).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
	   	AddInfoCard("Último PNR", LONG, "last_pnr", JWebIcon.getResponsiveIcon("fas fa-calendar-alt fa-5x"),"Fuente de datos", 11).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	AddInfoCard("Contratos activos", LONG, "contratos_activos", JWebIcon.getResponsiveIcon("fas fa-hand-holding-usd fa-5x"),"Contratos", 100).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
 
    	AddInfoCardDouble("Ultima Liquidación", FLOAT, "ultima_total_facturado", "ultima_total_comision", JWebIcon.getResponsiveIcon("fas fa-chart-line fa-5x"),"Última Liquidación", 160).setResponsiveClass("border-left-success shadow").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	AddInfoCardDouble("Liquidacion Anual", FLOAT, "total_facturado", "total_comision", JWebIcon.getResponsiveIcon("fas fa-chart-bar fa-5x"),"Liquidaciones", 150).setResponsiveClass("border-left-success shadow").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
	}
  	
   	if (!BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
    	AddInfoCard("Reservas Vigentes", CHAR, "booktotal", JWebIcon.getResponsiveIcon("fa fa-bookmark fa-5x"),"Fuente de datos", 120).setResponsiveClass("border-left-success shadow ").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	AddInfoCard("Reportes", CHAR, "repprog", JWebIcon.getResponsiveIcon("far fa-clock fa-5x"),"Reportes", 110).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	AddInfoCard("Último PNR", LONG, "last_pnr", JWebIcon.getResponsiveIcon("fas fa-calendar-alt fa-5x"),"Fuente de datos", 1).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	AddInfoCard("Contratos activos", LONG, "contratos_activos", JWebIcon.getResponsiveIcon("fas fa-hand-holding-usd fa-5x"),"Contratos", 100).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
    	
//    	AddInfoCardDouble("Ganancia Anual", FLOAT, "ganancia_anual", "ganancia_anual_usa", JWebIcon.getResponsiveIcon("fas fa-chart-line fa-5x"),"Contratos", 100).setResponsiveClass("border-left-success shadow").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
//    	AddInfoCardDouble("Ganancia Mensual ", FLOAT, "ganancia_mensual", "ganancia_mensual_usa", JWebIcon.getResponsiveIcon("fas fa-chart-bar fa-5x"),"Contratos", 100).setResponsiveClass("border-left-success shadow").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-xs-12");
   		
		} 
 
  	//  	 JFormPanelResponsive panel = AddItemPanel("Fuente de datos");
//  	 panel.AddItemEdit("Cantidad de boletos", LONG, OPT,"total");
//  	 panel.AddItemEdit("Boletos emitidos hoy", LONG, OPT,"hoy");
//  	 panel.AddItemEdit("Cliente", LONG, OPT,"cliente_ok");
//  	 panel.AddItemEdit("Cliente en error", LONG, OPT,"cliente_error");
  } 

  




}  //  @jve:decl-index=0:visual-constraint="10,10" 
