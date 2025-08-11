package pss.bsp.consola;

import java.awt.Dimension;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.common.customList.config.carpetas.GuiCarpeta;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormBSPConsola extends JBaseForm {


	private static final long serialVersionUID = 1245260233503L;

	  /**
	   * Propiedades de la Clase
	   */
	JPssLabel lusuario = new JPssLabel();
	JPssEdit usuario = new JPssEdit  ();
	//JPssLabel lusos = new JPssLabel();

	@Override
	public boolean isWithHeader() {
		return false;
	}
	  /**
	   * Constructor de la Clase
	   */
	  public FormBSPConsola() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiBSPConsola getWin() { return (GuiBSPConsola) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	    setSize(new Dimension(1070, 665));

	    GuiCarpeta.setNav("");//trucho
//	    String background=BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getFondo();
//	    this.setBackground(background);
	   
	  }
	  @Override
	  public Dimension getSize() {
	  	// TODO Auto-generated method stub
	  	return super.getSize();
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	  	  JFormPanelResponsive rowA =  (JFormPanelResponsive)AddItemRow().setGutter(true).setMargin(15, 0, 0, 0);
	  	  JFormPanelResponsive rowB =  (JFormPanelResponsive)AddItemRow().setGutter(true);
	  	  JFormColumnResponsive col1 = (JFormColumnResponsive)rowB.AddItemColumn().setSizeColumns(4);
	  	  JFormColumnResponsive col2 = (JFormColumnResponsive)rowB.AddItemColumn().setSizeColumns(8);
	  	  JFormPanelResponsive row1 =  (JFormPanelResponsive)rowA.AddItemRow().setSizeColumns(12).setResponsiveClass("gutter-col col-sm-12 col-xs-12");
	  	  JFormPanelResponsive row21 = (JFormPanelResponsive)col1.AddItemRow().setSizeColumns(12);
	  	  JFormPanelResponsive row22 = (JFormPanelResponsive)col2.AddItemRow().setSizeColumns(12);
	  	  JFormPanelResponsive row31 = (JFormPanelResponsive)col1.AddItemRow().setSizeColumns(12);
	  	  JFormPanelResponsive row32 = (JFormPanelResponsive)col2.AddItemRow().setSizeColumns(12);
	  		
	  	  row1.AddCardPanel(25);
	  		

//	  		  rowA.AddItemFieldset("Contratos").AddItemList(100);
	  		
	  	  
      	row21.AddImageCard(null, JWebIcon.getPssIcon(15104), "Data Mining", 50).setResponsiveClass("border-left-primary shadow ").setSizeColumns(12);
      	row22.AddImageCard("Reportes", JWebIcon.getResponsiveIcon("fa fa-envelope fa-5x"), "Reportes", 57).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-4 col-lg-6 col-md-6 col-sm-12 col-xs-12");
      	row22.AddImageCard("Evolución de las variables", JWebIcon.getResponsiveIcon("fa fa-chart-line fa-5x"), "Indicadores", 65).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-4 col-lg-6 col-md-6 col-sm-12 col-xs-12");
      	row22.AddImageCard(null, JWebIcon.getResponsiveIcon("fa fa-cogs fa-5x"), "Configuracion", 60).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-4 col-lg-12 col-md-12 col-sm-12 col-xs-12");
      	row31.AddImageCard("Contratos", JWebIcon.getResponsiveIcon("fa fa-pencil-alt fa-5x"), "Contratos", 55).setResponsiveClass("border-left-primary shadow ").setSizeColumns(12);
      	row32.AddImageCard("Principales variables", JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-5x"), "Dashboard", 30).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-12 col-md-12 col-sm-12 col-xs-12");
       	row32.AddImageCard("Admin.Liquidaciones", JWebIcon.getResponsiveIcon("fas fa-balance-scale fa-5x"), "Liquidaciones", 110).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-12 col-md-12 col-sm-12 col-xs-12");
        row32.AddImageCard(null, JWebIcon.getPssIcon(15105), "Consolidado", 70).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-12 col-md-12 col-sm-12 col-xs-12");
        if (BizUsuario.IsAdminCompanyUser())
        	row32.AddImageCard(null, JWebIcon.getPssIcon(15106), "Chat IA", 90).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-12 col-md-12 col-sm-12 col-xs-12");
        if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
    			  	row32.AddImageCard(null, JWebIcon.getPssIcon(15105), "Liquidaciones", 120).setResponsiveClass("border-left-primary shadow ").setComplexColumnClass("clearfix col-12 col-xl-6 col-lg-12 col-md-12 col-sm-12 col-xs-12");
        }
//	  	  
//	  	  JRecords<BizObjetivo> recs = new JRecords<BizObjetivo>(BizObjetivo.class);
//	  	  recs.addFilter("active", true);
//	  	  recs.addFilter("company", BizUsuario.getUsr().getCompany());
//	  	  JIterator<BizObjetivo> itc = recs.getStaticIterator();
//	  	  while (itc.hasMoreElements()) {
//	  	  	BizObjetivo contr = itc.nextElement();
//		  	  JRecords<BizDetalle> recsDet = new JRecords<BizDetalle>(BizDetalle.class);
//		  	  recs.addFilter("id", contr.getId());
//		  	  JIterator<BizDetalle> itd = recsDet.getStaticIterator();
//		  	  while (itd.hasMoreElements()) {
//		  	  	BizDetalle detalle = itd.nextElement();
//		  	    row32.AddInfoCard( detalle.getDescripcion(), LONG, null, JWebIcon.getResponsiveIcon("fas fa-hand-holding-usd fa-5x"),"Contratos", 100).setResponsiveClass("border-left-primary shadow").setComplexColumnClass("clearfix col-12 col-xl-3 col-lg-6 col-md-6 col-sm-12 col-xs-12");
//
//		  	  }	  	  	
//	  	  }
	  		  	 
	  } 

	  
	@Override
	public boolean isFullSize() throws Exception {
		return true;
	}
	
	public boolean isFixHight() throws Exception {
		return false;
	}

}