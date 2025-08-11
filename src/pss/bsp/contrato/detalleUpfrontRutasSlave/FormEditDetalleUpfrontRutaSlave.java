package pss.bsp.contrato.detalleUpfrontRutasSlave;

import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleUpfrontRutaSlave extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;

	  /**
	   * Propiedades de la Clase
	   */


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleUpfrontRutaSlave() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleUpfrontRutaSlave getWin() { return (GuiDetalleUpfrontRutaSlave) getBaseWin(); }

	  /**
	   * Inicializacion Grafica
	   */
	  protected void jbInit() throws Exception {
	 
	  }
	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
	    AddItemEdit( "Descripción", CHAR, OPT, "descripcion" ).setSizeColumns(12);
	    JFormControl c = AddItemCombo( "Aerolinea", CHAR, REQ, "id_aerolinea" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    }).setShowKey(true).setSizeColumns(3);;
		  AddItemEdit( "Aerolineas Placa", CHAR, OPT, "aerolineas_placa" ).setSizeColumns(5).setToolTip("Separados por ,").setPlaceHolder("Para multiples aerolineas separadas por , (si se desea solo campo Aerolinea dejar vacio)");
		  AddItemEdit( "Aerolineas", CHAR, OPT, "aerolineas" ).setSizeColumns(4).setToolTip("Separados por ,").setPlaceHolder("Para multiples aerolineas separadas por , (si se desea solo campo Aerolinea dejar vacio)");
	    c.setPlaceHolder("Aerolinea del contrato");
		  AddItemCheck( "Dolares", CHAR, "dolares" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
		  AddItemCheck( "Con YQ", CHAR, "yq" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
	    AddItemEdit( "GDSs", CHAR, OPT, "gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    AddItemEdit( "Excluir GDSs", CHAR, OPT, "no_gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");

	    AddItemEdit( "Iatas", CHAR, OPT, "iatas" ).setSizeColumns(12).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (vacio es todos)");
	  	AddItemEdit( "Clases incluidas", CHAR, OPT, "class_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tourcode incluidos", CHAR, OPT, "tourcode_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Fam.Tarifaria incluidos", CHAR, OPT, "farebasic_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Fam.Tarifaria excluidos", CHAR, OPT, "farebasic_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tipo pasajero incluidos", CHAR, OPT, "tipopasajero_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tipo pasajero excluidos", CHAR, OPT, "tipopasajero_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Mercados incluidos", CHAR, OPT, "mercados" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    AddItemEdit( "Mercados excluidos", CHAR, OPT, "no_mercados" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    AddItemEdit( "Vuelos incluidos", CHAR, OPT, "vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    AddItemEdit( "Vuelos excluidos", CHAR, OPT, "no_vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    AddItemEdit( "Rutas", CHAR, OPT, "rutas" ).setSizeColumns(12).setPlaceHolder("Los rutas son de aeropuertos separados con -, Ej:EZE-COR-EZE");
	    JFormFieldsetResponsive col1 = (JFormFieldsetResponsive) AddItemFieldset("Origen").setSizeColumns(6);
		  col1.AddItemCheck( null, CHAR, "origen_negado" ).setDescripcionValorCheck("Excluir").setDescripcionValorUnCheck("Incluir").setSizeColumns(3);
			col1.AddItemWinLov("Continente",CHAR,OPT,"origen_continente", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getContinentes(one);
	    	}
		  }).setMultiple(true);
		  col1.AddItemWinLov("Zona",CHAR,OPT,"origen_zona", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getZonas(one);
	    	}
		  }).setMultiple(true);
		  col1.AddItemWinLov("Pais",CHAR,OPT,"origen_pais", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getPaises(one);
	    	}
		  }).setShowKey(true).setMultiple(true);
		  col1.AddItemWinLov("Aeropuertos",CHAR,OPT,"origen_aeropuerto", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAeropuertos(one);
	    	}
		  }).setShowKey(true).setMultiple(true);
	    JFormFieldsetResponsive col2 = (JFormFieldsetResponsive) AddItemFieldset("Destino").setSizeColumns(6);
	    col2.AddItemCheck( null, CHAR, "destino_negado" ).setDescripcionValorCheck("Excluir").setDescripcionValorUnCheck("Incluir").setSizeColumns(3);
		  col2.AddItemWinLov("Continente",CHAR,OPT,"destino_continente", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getContinentes(one);
	    	}
		  }).setMultiple(true);
		  col2.AddItemWinLov("Zona",CHAR,OPT,"destino_zona", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getZonas(one);
	    	}
		  }).setMultiple(true);
		  col2.AddItemWinLov("Pais",CHAR,OPT,"destino_pais", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getPaises(one);
	    	}
		  }).setShowKey(true).setMultiple(true);
		  col2.AddItemWinLov("Aeropuertos",CHAR,OPT,"destino_aeropuerto", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAeropuertos(one);
	    	}
		  }).setShowKey(true).setMultiple(true);
	
	    AddItemDateTime( "Fecha emision desde", DATE, OPT, "fecha_emision_desde" ).setSizeColumns(6);
	    AddItemDateTime( "Fecha emision hasta", DATE, OPT, "fecha_emision_hasta").setSizeColumns(6);
	    AddItemDateTime( "Fecha vuelo desde", DATE, OPT, "fecha_vuelo_desde" ).setSizeColumns(4);
	    AddItemDateTime( "Fecha vuelo hasta", DATE, OPT, "fecha_vuelo_hasta").setSizeColumns(4);
	  	AddItemEdit("Sep.Fechas", LONG,OPT, "sep_fecha").setSizeColumns(4);;
	    AddItemDateTime( "Fecha Blockout desde", DATE, OPT, "fecha_blockout_desde" ).setSizeColumns(6);
	    AddItemDateTime( "Fecha Blockout hasta", DATE, OPT, "fecha_blockout_hasta").setSizeColumns(6);
	    AddItemCheck( "Viceversa", CHAR, "viceversa" ).setSizeColumns(3);
	    AddItemCheck( "Bookings", CHAR, "pax" ).setSizeColumns(3).setRefreshForm(true);
		  AddItemThreeCheck( "Tipo", CHAR, OPT, "domestic" , "S",JFormRadioResponsive.NO_FILTER,"N","Domestico","-","Internacional").setSizeColumns(3);
		  AddItemThreeCheck( "Interlineal", CHAR, OPT,"interlineal" , "S",JFormRadioResponsive.NO_FILTER,"N","Solo Multi","Interlineal","Solo una").setSizeColumns(3);
//		  AddItemCheck( "Omitir exchange", CHAR, "exchange" ).setSizeColumns(3);
	    AddItemEdit( "Premio",FLOAT,REQ, "porc_upfront").setSizeColumns(3).setToolTip("Premio");
	    
	  }
	  @Override
	  	public void OnPostShow() throws Exception {
	  		checkControls("");
	  		super.OnPostShow();
	  	}
	  
	 

	  private JWins getAerolineas(boolean one) throws Exception {
	  	GuiCarriers g = new GuiCarriers();
	  	if (one) {
	  		g.getRecords().addFilter("carrier", getWin().GetcDato().getIdAerolinea());
	  	} 
	  	g.addOrderAdHoc("carrier", "ASC");
	  	return g;
	  }
	  private JWins getPaises(boolean one) throws Exception {
	  	GuiPaisesLista g = new GuiPaisesLista();
	  	return g;
	  }
	  private JWins getContinentes(boolean one) throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizPaisLista.getContinentes());
	  }
	  private JWins getZonas(boolean one) throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizPaisLista.getRegiones());
	  }
	  private JWins getAeropuertos(boolean one) throws Exception {
	  	GuiAirports g = new GuiAirports();
	 		return g;
	  }


	  @Override
	  	public void checkControls(String sControlId) throws Exception {
	  		if (getControles().findControl("pax").getValue().equals("S")) {
	  			JFormEditResponsive form1 =(JFormEditResponsive)getControles().findControl("aerolineas_placa");
	  			JFormEditResponsive form2 =(JFormEditResponsive)getControles().findControl("aerolineas");
	  			form1.setVisible(true);
	  			form2.setSizeColumns(4);
	  		} else {
	  			JFormEditResponsive form1 =(JFormEditResponsive)getControles().findControl("aerolineas_placa");
	  			JFormEditResponsive form2 =(JFormEditResponsive)getControles().findControl("aerolineas");
	  			form1.setVisible(false);
	  			form2.setSizeColumns(9);
	  		}
	  		super.checkControls(sControlId);
	  	}

	}  //  @jve:decl-index=0:visual-constraint="24,55"
