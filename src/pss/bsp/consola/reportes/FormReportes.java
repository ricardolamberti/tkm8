package  pss.bsp.consola.reportes;

import javax.swing.JTabbedPane;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormReportes extends JBaseForm {


private static final long serialVersionUID = 1245260233503L;

  /**
   * Propiedades de la Clase
   */
//JPssLabel lusuario = new JPssLabel();
//JPssEdit usuario = new JPssEdit  ();
//JPssLabel lusos = new JPssLabel();
//JPssEdit licencia = new JPssEdit  ();

private JTabbedPane jTabbedPane = null;


  /**
   * Constructor de la Clase
   */
  public FormReportes() throws Exception {
   }

  public GuiReportes getWin() { return (GuiReportes) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive row=AddItemRow();
  	row.AddInfoCard("Reportes programados", CHAR, "repprog", JWebIcon.getResponsiveIcon("far fa-clock fa-5x"),"Reportes programados", 110).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row=AddItemRow();
  	row.AddInfoCard("Reporte DQB", CHAR, "dqb", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes DQB", 37).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row.AddInfoCard("MIT vs PNR", CHAR, "mit", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Concilición MIT vs PNR", 45).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row.AddInfoCard("Reportes CUF", CHAR, "cuf", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes CUF", 50).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row.AddInfoCard("Reportes RFND", CHAR, "rfnd", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes RFND", 210).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
   	row.AddInfoCard("Reportes x Dks", CHAR, "dks", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes DKs", 160).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
   	row.AddInfoCard("Reportes x Aerolinea", CHAR, "carriers", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes x Aerolineas", 200).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row.AddInfoCard("Reportes CTR917", CHAR, "ctr", JWebIcon.getResponsiveIcon("fa fa-print fa-5x"),"Reportes CTR917", 190).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
  	row=AddItemRow();
  	row.AddImageCard("Abiertos", JWebIcon.getResponsiveIcon("fa fa-pencil-alt fa-5x"), "Boletos Abiertos", 39).setResponsiveClass("border-left-primary shadow ").setSizeColumns(3);
  	row.AddImageCard("Ticket Upfront", JWebIcon.getResponsiveIcon("fa fa-dollar-sign fa-5x"), "Ticket Upfront", 175).setResponsiveClass("border-left-primary shadow ").setSizeColumns(3);
  	row.AddImageCard("Ticket Over", JWebIcon.getResponsiveIcon("fa fa-dollar-sign fa-5x"), "Ticket Over", 170).setResponsiveClass("border-left-primary shadow ").setSizeColumns(3);
  	row.AddImageCard("Cadenas", JWebIcon.getResponsiveIcon("fa fa-link fa-5x"), "Cadenas de tickets", 130).setResponsiveClass("border-left-primary shadow ").setSizeColumns(3);
      
	} 

  


}  //  @jve:decl-index=0:visual-constraint="10,10" 
