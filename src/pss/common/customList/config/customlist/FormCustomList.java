package pss.common.customList.config.customlist;

import java.awt.Color;
import java.awt.Dimension;

import pss.common.customList.config.field.campo.GuiCampos;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.www.ui.JWebIcon;

public class FormCustomList extends JBaseForm {


private static final long serialVersionUID = 1226426806993L;

/**
   * Constructor de la Clase
   */
  public FormCustomList() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCustomList getWin() { return (GuiCustomList) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    this.setSize(new Dimension(1160, 603));
    this.setLayout(null);

    
  }
  
  JFormPanelResponsive conf;
  JFormPanelResponsive cm;
  JFormPanelResponsive selTipo;
  JFormPanelResponsive ia;
  JFormImageCardResponsive ic1 = null;
  JFormImageCardResponsive ic2 = null;
  JFormImageCardResponsive ic3 = null;
  JFormImageCardResponsive ic4 = null;
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( null, UINT, OPT, "list_id" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "record_owner" ).setVisible(false);
    AddItemEdit( null, CHAR, OPT, "invisible" ).setVisible(false);

    if (isAlta()) {
      conf=AddItemFieldset("Configuración");
      
      conf.AddItemEdit( "Descripción", CHAR, REQ, "description" ).setSizeColumns(3).SetValorDefault("Reporte");
      conf.AddItemCombo( "Origen datos", CHAR, REQ, "rel_id", new JControlCombo() {
      	public JWins getRecords(boolean one) throws Exception {
      		return getRelationGalery(one);
      	}
      }).setSizeColumns(2).SetValorDefault(4).setRefreshForm(true);
    }
		JFormPanelResponsive row1=AddItemRow();
    JFormImageCardResponsive ic = null;
    ic = row1.AddImageCard( "Agregar campos" , JWebIcon.getResponsiveIcon("fa fa-plus-circle fa-3x"), "Agregar más campos o filtros", 530);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
    ic = row1.AddImageCard( "Agregar Filtros O" , JWebIcon.getResponsiveIcon("fa fa-folder-plus fa-3x"), "Agregar condiciones 'O' ", 531);
    if (ic!=null) ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
    ic = row1.AddImageCard( "Refrescar la vista previa" , JWebIcon.getResponsiveIcon("fa fa-sync fa-3x"), "Refrescar ", -1);
    ic.setResponsiveClass("panel-primary").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
    ic.setIdControl("refresh_refresh");
    ic1 = row1.AddImageCard( "Elegir entre diferentes tipos de gráficos" , JWebIcon.getResponsiveIcon("fa fa-chart-bar fa-3x"), "Cambiar tipos de gráficos ", -1);
    ic1.setResponsiveClass(getWin().GetcDato().getShowTipo()?"panel-primary":"panel-danger").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
    ic1.setIdControl("refresh_tipo");
    if (!isAlta()) {
	    ic2 = row1.AddImageCard( "Cambiar información principal" , JWebIcon.getResponsiveIcon("fa fa-database fa-3x"), "Datos principales ", -1);
	    ic2.setResponsiveClass(getWin().GetcDato().getShowModelo()?"panel-primary":"panel-danger").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
	    ic2.setIdControl("refresh_modelo");
    }
    ic4 = row1.AddImageCard( "Chat IA" , JWebIcon.getResponsiveIcon("fa fa-user-circle fa-3x"), "IA", -1);
    ic4.setResponsiveClass(getWin().GetcDato().getShowIA()?"panel-primary":"panel-danger").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
    ic4.setIdControl("refresh_ia");    
    
//    ic3 = row1.AddImageCard( "Cambiar diversos parametros" , JWebIcon.getResponsiveIcon("fa fa-cogs fa-3x"), "Configurar", -1);
//    ic3.setResponsiveClass(getWin().GetcDato().getShowConf()?"panel-primary":"panel-danger").setComplexColumnClass("clearfix col-xl-2 col-lg-3 col-md-4 col-sm-6 col-xs-12 col-12");
//    ic3.setIdControl("refresh_conf");


    if (!isAlta()) {
    	  conf=AddItemFieldset("Configuración");
		    
		    conf.AddItemEdit( "Descripción", CHAR, REQ, "description" ).setSizeColumns(3).SetValorDefault("Reporte");
		    conf.AddItemCombo( "Origen datos", CHAR, REQ, "rel_id", new JControlCombo() {
		    	public JWins getRecords(boolean one) throws Exception {
		    		return getRelationGalery(one);
		    	}
		    }).setSizeColumns(2).SetValorDefault(4).setRefreshForm(true);
    }
    conf.setBackground(new Color(176,216,230));
    JFormPanelResponsive rowConf=conf.AddItemRow();
    rowConf.setComplexColumnClass("col-xl-7 col-lg-12 col-md-12 col-sm-12 col-xs-12 col-12");
    rowConf.AddItemCheck("Totalizar?", OPT, "totalizar").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(2).setRefreshForm(true).SetValorDefault(true);
    rowConf.AddItemCheck("Sub-Totalizar?", OPT, "subtotalizar").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(2).setRefreshForm(true).SetValorDefault(false);
    rowConf.AddItemThreeCheck( "Mostrar Datos?", CHAR, OPT, "view_table" , "S","N","I","Ver datos","No ver datos","Ver invertido").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4).setRefreshForm(true).SetValorDefault(false);    
    rowConf.AddItemCheck("Ver todo?", OPT, "showfull").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(2).setRefreshForm(true).SetValorDefault(false);    
    rowConf.AddItemEdit( "Agrupar en otros", UINT, OPT, "limite" ).setSizeColumns(2).setRefreshForm(true).SetValorDefault(4);

//    cm=AddItemFieldset("Parametrizaci�n");   
    ia=AddItemFieldset("IA");   
    selTipo=AddItemFieldset("Tipos de gráficos");
    selTipo.setBackground(new Color(248,248,255));

    ia.AddItemArea(null, JBaseForm.CHAR, JBaseForm.OPT, "chat_bot").setHeight(40).setSizeColumns(10).setRefreshForm(true);
    ia.AddItemButton("Enviar",540,true).setSizeColumns(1);
    ia.AddItemButton("Limpiar",541,true).setSizeColumns(1);
    ia.AddItemHtml(null, JBaseForm.CHAR, JBaseForm.OPT, "hist_bot",true,80,10).setHeight(240).setSizeColumns(12).setReadOnly(true);
    ia.setBackground(new Color(102,205,170));
//    cm.AddItemCheck("Totalizar?", OPT, "totalizar").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(1).setRefreshForm(true).SetValorDefault(true);
//    cm.AddItemCheck("Sub-Totalizar?", OPT, "subtotalizar").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(1).setRefreshForm(true).SetValorDefault(false);
//    cm.AddItemCheck("Mostrar Datos?", OPT, "view_table").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(1).setRefreshForm(true).SetValorDefault(false);    
//    cm.AddItemCheck("Ver todo?", OPT, "showfull").setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(1).setRefreshForm(true).SetValorDefault(false);    
//    cm.AddItemEdit( "Agrupar en otros", UINT, OPT, "limite" ).setSizeColumns(2).setRefreshForm(true).SetValorDefault(3);
	  selTipo.AddItemMultiple( "", CHAR, REQ, "agrupado", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getTypes(one);
    	}
    }).setSizeColumns(12).setRefreshForm(true);
 
		 AddItemCombo("modelo", CHAR, OPT, "modelo" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getModelos(one);
    	}
    }).setSizeColumns(2).setVisible(false);


    JFormPanelResponsive row=AddItemRow();
    JFormColumnResponsive col3=row.AddItemColumn("col-sm-3 col-12");
    col3.setBackground(new Color(176,216,230));
    col3.setPadding(3, 3,3, 3);
    JFormColumnResponsive col4=row.AddItemColumn("col-sm-9 col-12 gutter-col");

    col3.AddItemTableExpand(null, "campos", GuiCampos.class).addDropManager("campos").setRowToolbarPos(JBaseWin.TOOLBAR_IN).setSizeRow("col-sm-12").setMinHeight(400);
   // col3.AddItemButton("Aplicar Cambios", -1, true).setRefreshForm(true);
    col4.AddCardPanel(22).setDiferido(true).setWithHeader(false);

  }
  private JWins getTypes(boolean one) throws Exception {
  	return JWins.CreateVirtualWins(BizCustomList.getPresentationTypes());
  }
  @Override
  public boolean isFullSize() throws Exception {
  	return true;
  }
  private JWins getModelos(boolean one) throws Exception {
  	JWins wins=BizUsuario.getUsr().getObjBusiness().getModuloLayouts();
  	//no ands
  	
  	if (wins.getWinRef().getRecord().getProperties().getElement("company")!=null) {
  		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
    	if (one) {
    		wins.getRecords().addFilter("id", getWin().GetcDato().getModelo());
    	} else {
    		wins.getRecords().addFilter("origen", "L");
    	}
  	} else {//por compatibilidad
    	if (one) {
    		wins.getRecords().addFilter("id", getWin().GetcDato().getModelo());
    	} else {
    		wins.getRecords().addFilter("origen", "D");
    	}
  		
  	}
  	return wins;
  }
  
  private JWins getRelationGalery(boolean one) throws Exception {
  	if (one) {
  		return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getRelationGallery(false, true));
  	} else {
			return JWins.createVirtualWinsFromMap(this.getWin().GetcDato().getRelationGallery(false, true));
  	}
  }
  
  @Override
  	public void OnShow() throws Exception {
  		checkControls("");
  		super.OnShow();
  	}
  @Override
  	public void checkControls(String sControlId) throws Exception {

			if (sControlId.equals("agrupado")) {
				getWin().GetcDato().clean();
			}
		if (sControlId.equals("refresh_tipo")) {
  			getWin().GetcDato().setShowTipo(!getWin().GetcDato().getShowTipo());
  			getWin().GetcDato().setShowModelo(false);
  			getWin().GetcDato().setShowIA(false);
  			getWin().GetcDato().setShowConf(false);
  		}
  		if (sControlId.equals("refresh_modelo")) {
  			getWin().GetcDato().setShowModelo(!getWin().GetcDato().getShowModelo());
  			getWin().GetcDato().setShowTipo(false);
  			getWin().GetcDato().setShowIA(false);
  			getWin().GetcDato().setShowConf(false);
  			
  		}
  		if (sControlId.equals("refresh_conf")) {
  			getWin().GetcDato().setShowConf(!getWin().GetcDato().getShowConf());
  			getWin().GetcDato().setShowModelo(false);
  			getWin().GetcDato().setShowIA(false);
  			getWin().GetcDato().setShowTipo(false);
  			
  		}
  		if (sControlId.equals("refresh_ia")) {
  			getWin().GetcDato().setShowIA(!getWin().GetcDato().getShowIA());
  			getWin().GetcDato().setShowModelo(false);
  			getWin().GetcDato().setShowConf(false);
  			getWin().GetcDato().setShowTipo(false);
  			
  		}
 // 		cm.setVisible(getWin().GetcDato().getShowConf());
  		if (!isAlta()) conf.setVisible(getWin().GetcDato().getShowModelo());
  		selTipo.setVisible(getWin().GetcDato().getShowTipo());
  		ia.setVisible(getWin().GetcDato().getShowIA());
  		
  		ic1.setResponsiveClass(getWin().GetcDato().getShowTipo()?"panel-primary border-left-success":"panel-primary border-left-danger");
  		if (ic2!=null) ic2.setResponsiveClass(getWin().GetcDato().getShowModelo()?"panel-primary border-left-success":"panel-primary border-left-danger");
  		if (ic4!=null) ic4.setResponsiveClass(getWin().GetcDato().getShowIA()?"panel-primary border-left-success":"panel-primary border-left-danger");
  //		ic3.setResponsiveClass(getWin().GetcDato().getShowConf()?"panel-primary border-left-success":"panel-primary border-left-danger");
  		
  		getControles().findControl("view_table").SetReadOnly(!getWin().GetcDato().isGrafico());
//			getWin().GetcDato().updateOrderToTop(null);
  		super.checkControls(sControlId);
  	}


	
}  //  @jve:decl-index=0:visual-constraint="4,-13"
