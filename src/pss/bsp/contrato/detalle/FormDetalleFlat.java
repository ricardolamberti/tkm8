package pss.bsp.contrato.detalle;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalleBackendRutas.GuiDetalleBackendRuta;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;

public class FormDetalleFlat  extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleFlat() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalle getWin() { return (GuiDetalle) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  
	@Override
	public void InicializarPanelHeader(JWin zBase) throws Exception {
	  JFormColumnResponsive column1 = AddItemColumn(9);
    JFormColumnResponsive column2 = AddItemColumn(3);
    column1.AddItemLabel("Descripción", 10);
    column1.AddItemLabel("Ganancia", 2);
    column1.AddItemLabel("Conclusión", 10);
    column1.AddItemLabel("Nivel", 2);
	}
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		setAutoRefresh(true, 10000, "DASHBOARD");
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
   
    AddItemEdit( null, CHAR, REQ, "variable" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "variable_ganancia").setHide(true);
    
    JFormColumnResponsive column1 = AddItemColumn();
    column1.setComplexColumnClass("col-lg-9 col-md-12");
    JFormColumnResponsive column2 = AddItemColumn();
    column2.setComplexColumnClass("col-lg-3 hidden-sm hidden-md hidden-xs");
    column1.setLabelLeft();

    if (getWin().GetcDato().getObjContrato().isObjetivo()) {
      column1.AddItemLabelData( CHAR, "descripcion" ).size(15).bold().setSizeColumns(8).SetReadOnly(true);
      column1.AddItemLabelData( CHAR,  "conclusion" ).setSizeColumns(9).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "nivel_alcanzado_estimada" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).italic().color("#000077").setSizeColumns(3).SetReadOnly(true);
      column1.AddItemLabelData( "Total alcanzado", FLOAT, "valor_totalcontrato" ,12).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#770077").setVisible(true).SetReadOnly(true);
   	
	 
    } else if (BizBSPCompany.getObjBSPCompany(getWin().GetcDato().getCompany()).isDependant()) {
      column1.AddItemLabelData( CHAR, "descripcion" ).size(15).bold().setSizeColumns(8).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "ganancia_estimada_calc" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#004400").setSizeColumns(4).SetReadOnly(true);
      column1.AddItemLabelData( CHAR,  "conclusion" ).setSizeColumns(9).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "nivel_alcanzado_estimada_calc" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).italic().color("#000077").setSizeColumns(3).SetReadOnly(true);
      column1.AddItemLabelData( "Base comisionable", FLOAT, "valor_totalcontrato_calc" ,12).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#770077").setVisible(true).SetReadOnly(true);

    } else {
      column1.AddItemLabelData( CHAR, "descripcion" ).size(15).bold().setSizeColumns(8).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "ganancia_estimada" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#004400").setSizeColumns(4).SetReadOnly(true);
      column1.AddItemLabelData( CHAR,  "conclusion" ).setSizeColumns(9).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "nivel_alcanzado_estimada" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).italic().color("#000077").setSizeColumns(3).SetReadOnly(true);
      column1.AddItemLabelData( "Base comisionable", FLOAT, "valor_totalcontrato" ,12).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#770077").setVisible(true).SetReadOnly(true);
   	
    }
    

    JFormImageResponsive i=column2.AddItemImage( "", "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
  }



}  //  @jve:decl-index=0:visual-constraint="7,4"
