package pss.bsp.contrato.detalleUpfrontRutas;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleUpfrontRuta extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;

	  /**
	   * Propiedades de la Clase
	   */


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleUpfrontRuta() throws Exception {
	    try { jbInit(); }
	    catch (Exception e) { e.printStackTrace(); } 
	  }

	  public GuiDetalleUpfrontRuta getWin() { return (GuiDetalleUpfrontRuta) getBaseWin(); }

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
	    AddItemEdit( "Descripción", CHAR, OPT, "descripcion" ).setSizeColumns(7);
	    AddItemCheck("Obj.Extra",OPT, "kicker" ).setSizeColumns(1);
	    AddItemEdit( "Grupo", CHAR, OPT, "grupo" ).setSizeColumns(2);
	    AddItemEdit( "Prioridad", LONG, OPT, "prioridad" ).setSizeColumns(2);
		  JFormControl c = AddItemCombo( "Aerolinea", CHAR, REQ, "id_aerolinea" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getAerolineas(one);
	    	}
	    }).setShowKey(true).setSizeColumns(3);
		  AddItemEdit( "Aerolineas Placa", CHAR, OPT, "aerolineas_placa" ).setSizeColumns(5).setToolTip("Separados por ,").setPlaceHolder("Para multiples aerolineas separadas por , (si se desea solo campo Aerolinea dejar vacio)");
		  AddItemEdit( "Aerolineas Operadoras", CHAR, OPT, "aerolineas" ).setSizeColumns(6).setToolTip("Separados por ,").setPlaceHolder("Para multiples aerolineas separadas por , (se puede especificar clase AM:ABC)(si se desea solo campo Aerolinea dejar vacio)");
		  AddItemEdit( "Aerolineas Op.excluidas", CHAR, OPT, "no_aerolineas" ).setSizeColumns(6);
			 c.setPlaceHolder("Aerolinea del contrato");
		  AddItemCheck( "Dolares", CHAR, "dolares" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
		  AddItemCheck( "Con YQ", CHAR, "yq" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
	    AddItemEdit( "GDSs", CHAR, OPT, "gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    AddItemEdit( "Excluir GDSs", CHAR, OPT, "no_gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");

	    AddItemEdit( "Iatas", CHAR, OPT, "iatas" ).setSizeColumns(12).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (vacio es todos)");
	  	AddItemEdit( "Clases incluidas", CHAR, OPT, "class_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (Acepta Clase:Aerolinea ejemplo A:DL,B:AM) (vacio es todos)");
	    AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Separados por , (Acepta Clase:Aerolinea ejemplo A:DL,B:AM)(vacio es todos)");
	    AddItemEdit( "Tourcode incluidos", CHAR, OPT, "tourcode_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Fam.Tarifaria incluidos", CHAR, OPT, "farebasic_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin, 7:XAB es que en pos 7 haya X,A o B)").setPlaceHolder("Separados por , (vacio es todos,% es comodin, 7:XAB es que en pos 7 haya X,A o B)");
	    AddItemEdit( "Fam.Tarifaria excluidos", CHAR, OPT, "farebasic_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin, 7:XAB es que en pos 7 haya X,A o B)").setPlaceHolder("Separados por , (vacio es todos,%X es comidin, 7:XAB es que en pos 7 haya X,A o B)");
	    AddItemEdit( "Tipo pasajero incluidos", CHAR, OPT, "tipopasajero_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Tipo pasajero excluidos", CHAR, OPT, "tipopasajero_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    AddItemEdit( "Mercados incluidos", CHAR, OPT, "mercados" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    AddItemEdit( "Mercados excluidos", CHAR, OPT, "no_mercados" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)").setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    AddItemEdit( "Vuelos incluidos", CHAR, OPT, "vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    AddItemEdit( "Vuelos excluidos", CHAR, OPT, "no_vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    AddItemEdit( "Rutas incluidas", CHAR, OPT, "rutas" ).setSizeColumns(6).setPlaceHolder("Los rutas son de aeropuertos separados con -, Ej:EZE-COR-EZE");
	    AddItemEdit( "Rutas excluidas", CHAR, OPT, "no_rutas" ).setSizeColumns(6).setPlaceHolder("Los rutas son de aeropuertos separados con -, Ej:EZE-COR-EZE");
		  AddItemRow().AddItemThreeCheck( "Operación", CHAR, OPT, "andor" , "S",JFormRadioResponsive.NO_FILTER,"N","Origen O destino","Automatico","Origen Y Destino").setSizeColumns(3);
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
		  JFormPanelResponsive row = AddItemRow();
		  row.AddItemWinLov("Meses emisión",CHAR,OPT,"meses", new JControlWin() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		return getMeses(one);
	    	}
		  }).setMultiple(true);
		  row = AddItemRow();
	    row.AddItemDateTime( "Fecha emision desde", DATE, OPT, "fecha_emision_desde" ).setSizeColumns(6);
	    row.AddItemDateTime( "Fecha emision hasta", DATE, OPT, "fecha_emision_hasta").setSizeColumns(6);
	    row = AddItemRow();
		  row.AddItemDateTime( "Fecha vuelo desde", DATE, OPT, "fecha_vuelo_desde" ).setSizeColumns(4);
	    row.AddItemDateTime( "Fecha vuelo hasta", DATE, OPT, "fecha_vuelo_hasta").setSizeColumns(4);
	    row.AddItemEdit("Sep.Fechas", LONG,OPT, "sep_fecha").setSizeColumns(4);;
	    row = AddItemRow();
		  row.AddItemDateTime( "Fecha Blockout desde", DATE, OPT, "fecha_blockout_desde" ).setSizeColumns(6);
	    row.AddItemDateTime( "Fecha Blockout hasta", DATE, OPT, "fecha_blockout_hasta").setSizeColumns(6);
	    row = AddItemRow();
		  row.AddItemCheck( "Viceversa", CHAR, "viceversa" ).setSizeColumns(3);
	    row.AddItemCheck( "Bookings", CHAR, "pax" ).setSizeColumns(3).setRefreshForm(true);
	    row = AddItemRow();
		  row.AddItemThreeCheck( "Tipo Viaje", CHAR, OPT, "tipoviaje" , "S",JFormRadioResponsive.NO_FILTER,"N","Open Jaw","-","Round trip").setSizeColumns(3);
	    row.AddItemThreeCheck( "Multidestino", CHAR, OPT, "multidestino" , "S",JFormRadioResponsive.NO_FILTER,"N","Mas de 4 destinos","-","Menos de 4 destinos").setSizeColumns(3);
	    row.AddItemThreeCheck( "Primera edicion", CHAR, OPT, "Primera" , "S",JFormRadioResponsive.NO_FILTER,"N","Primera edicion","-","Reeditado").setSizeColumns(3);
	    row.AddItemThreeCheck( "Tipo", CHAR, OPT, "domestic" , "S",JFormRadioResponsive.NO_FILTER,"N","Domestico","-","Internacional").setSizeColumns(3);
	    row.AddItemThreeCheck( "Interlineal", CHAR, OPT,"interlineal" , "S",JFormRadioResponsive.NO_FILTER,"N","Solo Multi","Interlineal","Solo una").setSizeColumns(3);
	    row.AddItemThreeCheck( "StopOver", CHAR, OPT,"stopover" , "S",JFormRadioResponsive.NO_FILTER,"N","Sin StopOver", "-","Con Stopover").setSizeColumns(3);
	    row.AddItemThreeCheck( "Reemisión", CHAR, OPT,"cambio" , "S",JFormRadioResponsive.NO_FILTER,"N","Posterior despegue", "-","Antes Despegue").setSizeColumns(3);
	    row.AddItemThreeCheck( "Vuelta al mundo",CHAR, OPT,"vuelta_mundo" , "S",JFormRadioResponsive.NO_FILTER,"N","Si", "-","No").setSizeColumns(3);
					  //		  AddItemCheck( "Omitir exchange", CHAR, "exchange" ).setSizeColumns(3);
	    row = AddItemRow();
		  row.AddItemEdit( "Premio",FLOAT,REQ, "porc_upfront").setSizeColumns(3).setToolTip("Premio");
	    
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
	  private JWins getMeses(boolean one) throws Exception {
	  	return JWins.createVirtualWinsFromMap(getMeses());
	  }
	  
	
	  public static JMap<String, String> getMeses() throws Exception {
	  	JMap<String, String> map = JCollectionFactory.createMap();
	    
    
      DateTimeFormatter claveFormatter = DateTimeFormatter.ofPattern("uuuuMM"); // e.g., ENE2024
      DateTimeFormatter valorFormatter = DateTimeFormatter.ofPattern("MMM-uuuu"); // e.g., ENE-2024

      YearMonth inicio = YearMonth.now().withMonth(1); // Primer mes del año actual
      YearMonth fin = inicio.plusYears(2); // Dos años adelante

      while (!inicio.isAfter(fin)) {
          String clave = inicio.format(claveFormatter).toUpperCase(); // Clave en MAYÚSCULAS
          String valor = inicio.format(valorFormatter).toUpperCase(); // Valor en MAYÚSCULAS
        	map.addElement(clave, valor );
      	
          inicio = inicio.plusMonths(1);
      }

      return  map;
  }

	  @Override
	  	public void checkControls(String sControlId) throws Exception {
//	  		if (getControles().findControl("pax").getValue().equals("S")) {
//	  			JFormEditResponsive form1 =(JFormEditResponsive)getControles().findControl("aerolineas_placa");
//	  			JFormEditResponsive form2 =(JFormEditResponsive)getControles().findControl("aerolineas");
//	  			form1.setVisible(true);
//	  			form2.setSizeColumns(4);
//	  		} else {
//	  			JFormEditResponsive form1 =(JFormEditResponsive)getControles().findControl("aerolineas_placa");
//	  			JFormEditResponsive form2 =(JFormEditResponsive)getControles().findControl("aerolineas");
//	  			form1.setVisible(false);
//	  			form2.setSizeColumns(9);
//	  		}
	  		super.checkControls(sControlId);
	  	}

	}  //  @jve:decl-index=0:visual-constraint="24,55"
