package pss.bsp.contrato.detalleCopa;

import pss.bsp.contrato.detalleDatamining.BizDetalleDatamining;
import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDetalleCopa extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

/**
   * Constructor de la Clase
   */
  public FormDetalleCopa() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDetalleCopa getWin() { return (GuiDetalleCopa) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

 
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
    
    JFormControl c=AddItemCheck( null, OPT,"acumulativo").setHide(true).SetValorDefault(false);

    
    JFormImageCardResponsive ic = null;
    ic = AddItemRow().AddImageCard("Extra Info?"    , JWebIcon.getResponsiveIcon("fa fa-info-circle fa-3x"), "Mas información asociada?", 100);
    if (ic!=null) ic.setResponsiveClass("panel-danger").setSizeColumns(2);

    JFormColumnResponsive column1 = AddItemColumn(6);
    JFormColumnResponsive column2 = AddItemColumn(6);

    JFormFieldsetResponsive panelR=column1.AddItemFieldset("Resultados",12);
    JFormImageResponsive i=panelR.AddItemImage( "", "imagen2" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    panelR.AddItemEdit( "Evaluación al fin del contrato", CHAR, OPT, "conclusion" ).SetReadOnly(true);
    panelR.AddItemEdit( "Valor del indicador objetivo", FLOAT, OPT, "valor_fcontrato" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Valor Base comisionable", FLOAT, OPT, "valor_totalcontrato" ).setSizeColumns(6).setVisible(true).SetReadOnly(true);
    panelR.AddItemEdit( "Nivel alcanzado", CHAR, OPT, "nivel_alcanzado_estimada" ).setSizeColumns(6).SetReadOnly(true);
    panelR.AddItemEdit( "Ganancia/Comisión", FLOAT, OPT, "ganancia_estimada" ).setSizeColumns(6).SetReadOnly(true);

    JFormFieldsetResponsive panelG1=column2.AddItemFieldset("Gráficas",12);
    panelG1.setBackground("#e6e6fa");
    i=panelG1.AddItemImage("", "imagen1" );
    i.setHeight(680);
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setSizeColumns(10);
    
    JFormFieldsetResponsive panelI=AddItemFieldset("Contrato",6);
	  panelI.AddItemEdit( "período", CHAR, OPT, "periodo_detalle" ).setSizeColumns(8).SetReadOnly(true);
	
	  panelI.AddItemCombo( null, CHAR, OPT, "periodo" , JWins.createVirtualWinsFromMap(BizDetalleDatamining.getPeriodos())).setHide(true);
	  panelI.AddItemCheck("Extra/Subobjetivo",OPT,"kicker").setSizeColumns(4).SetReadOnly(true);

//	  panelI.AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Valor ref. global", FLOAT, OPT, "valor_global" ).setSizeColumns(3).SetReadOnly(true);
//	  panelI.AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "FMS Max", FLOAT, OPT, "fms_max" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "FMS Min", FLOAT, OPT, "fms_min" ).setSizeColumns(3).SetReadOnly(true);
	  panelI.AddItemEdit( "Min.Cantidad", FLOAT, OPT, "Limite" ).setSizeColumns(3).SetReadOnly(true);


	  panelI.AddItemDateTime( "Fecha desde cálculo", DATE, OPT, "fecha_desde_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaDesde()).setRefreshForm(true);
	  panelI.AddItemDateTime( "Fecha hasta cálculo", DATE, OPT, "fecha_calculo" ).SetValorDefault(getWin().GetcDato().getObjContrato().getFechaHasta()).setRefreshForm(true);


    
 



    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.setSizeColumns(6);
    
    JFormLista l= tabs.AddItemList(31);
    l= tabs.AddItemList(35);

   }
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls("");
  		super.OnPostShow();
  	}

  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (sControlId.equals("variable")) {
  			getWin().GetcDato().clean();
  		}
//  		if (GetControles().findControl("acumulativo").getValue().equals("S")) {
//  			GetControles().findControl("periodo").edit(GetModo());
//  			//GetControles().findControl("valor").edit(GetModo());
//  		} else {
//  			GetControles().findControl("periodo").disable();
//  			//GetControles().findControl("valor").disable();
//  			GetControles().findControl("periodo").setValue("");
//  			//GetControles().findControl("valor").setValue(""+getWin().GetcDato().getValor());
//  		}
//  		if (sControlId.equals("")) {
//  			getWin().GetcDato().calcule();
//  			this.GetControles().findControl("valor").setValue(""+getWin().GetcDato().getObjEvent().getValor());
//  		}
  		super.checkControls(sControlId);
  	}

}  //  @jve:decl-index=0:visual-constraint="7,4"
