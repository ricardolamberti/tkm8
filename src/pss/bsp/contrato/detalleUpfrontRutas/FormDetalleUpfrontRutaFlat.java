package pss.bsp.contrato.detalleUpfrontRutas;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDetalleUpfrontRutaFlat   extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleUpfrontRutaFlat() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleUpfrontRuta getWin() { return (GuiDetalleUpfrontRuta) getBaseWin(); }

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
//    column1.AddItemLabel("Conclusión", 10);
//    column1.AddItemLabel("Nivel", 2);
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
    
    JFormColumnResponsive column1 = AddItemColumn(12);
  //  JFormColumnResponsive column2 = AddItemColumn(3);

    column1.setLabelLeft();
    column1.AddItemLabelData( CHAR, "descripcion" ).size(15).bold().setSizeColumns(8).SetReadOnly(true);
      column1.AddItemLabelData( FLOAT,  "ganancia_estimada" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#004400").setSizeColumns(4).SetReadOnly(true);
    column1.AddItemLabelData( "Base comisionable", FLOAT, "valor_totalcontrato" ,12).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#770077").setVisible(true).SetReadOnly(true);
    column1.AddItemRow().AddItemLabelData( "Origen", CHAR, "origen_consolidado" ).setSizeLabels(2).size(15).bold().setSizeColumns(8).SetReadOnly(true);
    column1.AddItemRow().AddItemLabelData( "Destino", CHAR, "destino_consolidado" ).setSizeLabels(2).size(15).bold().setSizeColumns(8).SetReadOnly(true);
    JFormPanelResponsive row = column1.AddItemRow();
    row.AddItemLabelData( "Moneda", CHAR, "moneda_consolidada" ).setSizeLabels(6).bold().setSizeColumns(3).SetReadOnly(true);
    row.AddItemLabelData( "Premio", CHAR, "premio_upfront" ).setSizeLabels(6).bold().setSizeColumns(3).SetReadOnly(true);
 //   column1.AddItemLabelData( CHAR,  "conclusion" ).setSizeColumns(9).SetReadOnly(true);
//    column1.AddItemLabelData( CHAR,  "nivel_alcanzado_estimada" ).italic().color("#000077").setSizeColumns(3).SetReadOnly(true);
//    JFormPanelResponsive line = column1.AddInLinePanel();
//    line.addButton(5133, 100, 0).setSizeColumns(2);
//    line.addButton(5012, 600, 0).setSizeColumns(2);
//    line.addButton(GuiIcon.CONSULTAR_ICON , 1, 0).setSizeColumns(2);
//    line.addButton(GuiIcon.MENOS_ICON, 3, 0).setSizeColumns(2);

//    JFormImageResponsive i=column2.AddItemImage( "", "imagen2" );
//    i.setSource(JPssImage.SOURCE_SCRIPT);
  }

	@Override
	public void checkControls(String sControlId) throws Exception {
		if (getWin().GetcDato().getOrigenConsolidado().equals(""))
			findControl("origen_consolidado").hide();
		if (getWin().GetcDato().getDestinoConsolidado().equals(""))
			findControl("destino_consolidado").hide();
		super.checkControls(sControlId);
	}

}  //  @jve:decl-index=0:visual-constraint="7,4"
