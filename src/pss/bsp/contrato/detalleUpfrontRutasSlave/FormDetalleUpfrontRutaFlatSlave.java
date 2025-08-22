package pss.bsp.contrato.detalleUpfrontRutasSlave;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDetalleUpfrontRutaFlatSlave   extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleUpfrontRutaFlatSlave() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleUpfrontRutaSlave getWin() { return (GuiDetalleUpfrontRutaSlave) getBaseWin(); }

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

    column1.setLabelLeft();
    column1.AddItemLabelData( CHAR, "descripcion" ).size(15).bold().setSizeColumns(8).SetReadOnly(true);
    column1.AddItemLabelData( FLOAT,  "ganancia_estimada_calc" ).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#004400").setSizeColumns(4).SetReadOnly(true);
    column1.AddItemLabelData( "Base comisionable", FLOAT, "valor_totalcontrato_calc" ,12).setTextHAlignment(JFormEditResponsive.HALIGN_RIGHT).size(15).italic().color("#770077").setVisible(true).SetReadOnly(true);
    column1.AddItemRow().AddItemLabelData( "Origen", CHAR, "origen_consolidado" ).setSizeLabels(2).size(15).bold().setSizeColumns(8).SetReadOnly(true);
    column1.AddItemRow().AddItemLabelData( "Destino", CHAR, "destino_consolidado" ).setSizeLabels(2).size(15).bold().setSizeColumns(8).SetReadOnly(true);
    JFormPanelResponsive row = column1.AddItemRow();
    row.AddItemLabelData( "Moneda", CHAR, "moneda_consolidada" ).setSizeLabels(6).bold().setSizeColumns(3).SetReadOnly(true);
    row.AddItemLabelData( "Premio", CHAR, "premio_upfront" ).setSizeLabels(6).bold().setSizeColumns(3).SetReadOnly(true);

  }

	@Override
	public void checkControls(String sControlId) throws Exception {
		if (getWin().GetcDato().getOrigenConsolidado().equals("") && findControl("origen_consolidado")!=null)
			findControl("origen_consolidado").hide();
		if (getWin().GetcDato().getDestinoConsolidado().equals("") && findControl("origen_consolidado")!=null)
			findControl("destino_consolidado").hide();
		super.checkControls(sControlId);
	}

}  //  @jve:decl-index=0:visual-constraint="7,4"
