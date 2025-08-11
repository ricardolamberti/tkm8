package pss.bsp.contrato.detalleObjetivo;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.tourism.airports.GuiAirports;

public class FormEditDetalleObjetivo extends JBaseForm {


	private static final long serialVersionUID = 1446860278358L;

	  /**
	   * Propiedades de la Clase
	   */


	/**
	   * Constructor de la Clase
	   */
	  public FormEditDetalleObjetivo() throws Exception {
	   }

	  public GuiDetalleObjetivo getWin() { return (GuiDetalleObjetivo) getBaseWin(); }

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
//	    JFormControl c = AddItemCombo( "Aerolinea", CHAR, OPT, "id_aerolinea" , new JControlCombo() {
//	    	@Override
//	    	public JWins getRecords(boolean one) throws Exception {
//	    		return getAerolineas(one);
//	    	}
//	    }).setShowKey(true).setSizeColumns(4);
//	    c.setPlaceHolder("Aerolinea del contrato");
		  AddItemEdit( "Aerolineas incluidas", CHAR, OPT, "aerolineas_placa" ).setSizeColumns(6);
		  AddItemEdit( "Aerolineas excluidas", CHAR, OPT, "no_aerolineas_placa" ).setSizeColumns(6);
		  AddItemEdit( "Aerolineas Operadoras", CHAR, OPT, "aerolineas" ).setSizeColumns(6).setToolTip("Separados por ,").setPlaceHolder("Para multiples aerolineas separadas por , (se puede especificar clase AM:ABC)(si se desea solo campo Aerolinea dejar vacio)");
		  AddItemEdit( "Aerolineas Op.excluidas", CHAR, OPT, "no_aerolineas" ).setSizeColumns(6);
		  AddItemCheck( "Dolares", CHAR, "dolares" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
		  AddItemCheck( "Con YQ", CHAR, "yq" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4);
		  //if (!isAlta())
		  // AddItemCheck( "Modificar otros períodos", CHAR, "idem_anterior" ).setAlign(JFormCheckResponsive.LABEL_TOP).setSizeColumns(4).SetValorDefault(true);

		  JFormTabPanelResponsive tabs = getInternalTabPanel();
		  JFormLocalForm a=tabs.AddItemLocalForm("Meta");
		
	    JFormPanelResponsive objetivo = a.getRootPanel();
	   // AddItemRow().AddItemButton("Copiar condiciones desde Meta a Base comisionable", 520, false).setResponsiveClass("btn-primary").setSizeColumns(6);
	    AddItemEdit( "Iatas", CHAR, OPT, "iatas" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    AddItemEdit( "Excluir Iatas", CHAR, OPT, "no_iatas" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    objetivo.AddItemEdit( "GDSs", CHAR, OPT, "gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    objetivo.AddItemEdit( "Excluir GDSs", CHAR, OPT, "no_gds" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos)");
	    objetivo.AddItemEdit( "Clases incluidas", CHAR, OPT, "class_included" ).setSizeColumns(5);
	    objetivo.AddItemEdit( "Clases excluidas", CHAR, OPT, "class_excluded" ).setSizeColumns(5);
	    objetivo.AddItemEdit( "Tourcode incluidos", CHAR, OPT, "tourcode_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)");
	    objetivo.AddItemEdit( "Tourcode excluidos", CHAR, OPT, "tourcode_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)");
	    objetivo.AddItemEdit( "Fam.Tarifaria incluidos", CHAR, OPT, "farebasic_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    objetivo.AddItemEdit( "Fam.Tarifaria excluidos", CHAR, OPT, "farebasic_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)").setPlaceHolder("Separados por , (vacio es todos)");
	    objetivo.AddItemEdit( "Tipo Pasajero incluidos", CHAR, OPT, "tipopasajero_included" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)");
	    objetivo.AddItemEdit( "Tipo Pasajero excluidos", CHAR, OPT, "tipopasajero_excluded" ).setSizeColumns(6).setToolTip("Separados por , (vacio es todos,% es comodin)");
	    objetivo.AddItemEdit( "Mercados incluidos", CHAR, OPT, "mercados" ).setSizeColumns(6).setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    objetivo.AddItemEdit( "Mercados excluidos", CHAR, OPT, "no_mercados" ).setSizeColumns(6).setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US");
	    objetivo.AddItemEdit( "Vuelos incluidos", CHAR, OPT, "vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    objetivo.AddItemEdit( "Vuelos excluidos", CHAR, OPT, "no_vuelos" ).setSizeColumns(6).setPlaceHolder("Los vuelos se dividen con coma, y - para rangos, Ej:1000-1330,1456");
	    objetivo.AddItemEdit( "Rutas", CHAR, OPT, "rutas" ).setSizeColumns(12).setPlaceHolder("Los rutas son de aeropuertos separados con -, Ej:EZE-COR-EZE");
	    objetivo.AddItemRow().AddItemThreeCheck( "Operación", CHAR, OPT, "andor" , "S",JFormRadioResponsive.NO_FILTER,"N","Origen O destino","Automatico","Origen Y Destino").setSizeColumns(3);
	    JFormFieldsetResponsive col1 = (JFormFieldsetResponsive) objetivo.AddItemFieldset("Origen").setSizeColumns(6);
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
	    JFormFieldsetResponsive col2 = (JFormFieldsetResponsive) objetivo.AddItemFieldset("Destino").setSizeColumns(6);
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
		  
		  objetivo.AddItemCombo( "Cálculo", CHAR, REQ, "ms" , new JControlCombo() {
		  	public JWins getRecords(boolean one) throws Exception {
		  		return getCalculos(one);
		  	};
		  }).setSizeColumns(12);
		  objetivo.AddItemThreeCheck( "Tipo", CHAR, OPT, "domestic" , "S",JFormRadioResponsive.NO_FILTER,"N","Domestico","-","Internacional").setSizeColumns(4);
		  objetivo.AddItemCheck( "Viceversa", CHAR, "viceversa" ).setSizeColumns(4);
		  objetivo.AddItemThreeCheck( "Primera edicion", CHAR, OPT, "Primera" , "S",JFormRadioResponsive.NO_FILTER,"N","Primera edicion","-","Reeditado").setSizeColumns(3);
	    objetivo.AddItemCheck( "Bookings", CHAR, "pax" ).setSizeColumns(4);
		  objetivo.AddItemThreeCheck( "Fecha", CHAR, OPT, "volado" , "S",JFormRadioResponsive.NO_FILTER,"N","Volado","Volado y emitido","Emitido").setSizeColumns(4);
		  objetivo.AddItemThreeCheck( "Interlineal", CHAR, OPT, "interlineal" , "S",JFormRadioResponsive.NO_FILTER,"N","Solo Multi","Interlineal","Solo una").setSizeColumns(4);
//		  objetivo.AddItemCheck( "Omitir exchange", CHAR, "exchange" ).setSizeColumns(4);
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
	    
	    AddItemEdit( "FMS global/Pax expected", FLOAT, OPT, "fms_global" ).setSizeColumns(4);
	    AddItemEdit( "Target Share gap", FLOAT, OPT, "sharegap_global" ).setSizeColumns(3);
	    AddItemEdit( "Valor global", FLOAT, OPT, "valor_global" ).setSizeColumns(3);
	  //  AddItemEdit( "Reembolso", FLOAT, OPT, "valor_reembolso" ).setSizeColumns(3);
	
	    AddItemTable("Niveles", "niveles", GuiNiveles.class).setVision(BizNivel.NIVEL_ONLYMETA);

	  }
	  @Override
	  	public void OnPostShow() throws Exception {
	  		checkControls("");
	  		super.OnPostShow();
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
	  private JWins getCalculos(boolean one) throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizDetalle.getCalculos());
	  }
	  private JWins getCalculosBase(boolean one) throws Exception {
	  	return JWins.createVirtualWinsFromMap(BizDetalle.getCalculosBase());
	  }
	  private JWins getAeropuertos(boolean one) throws Exception {
	  	GuiAirports g = new GuiAirports();
	 		return g;
	  }


	  @Override
  	public void checkControls(String sControlId) throws Exception {
	  	if (sControlId.startsWith("Copiar condiciones")) {
		  	getControles().BaseToControl(GetModo(), true);
	  	}
  		if (sControlId.equals("variable")||sControlId.equals("variable_ganancia")) {
  			getWin().GetcDato().clean();
  			if (getWin().GetcDato().getObjEvent()!=null)
  				getControles().findControl("descripcion").setValue(getWin().GetcDato().getObjEvent().getDescripcion());
  		}

  		
  		
  		if (getWin().GetccDato().needFMSGLobal()) {
  			getControles().findControl("fms_global").edit();
  		} else {
  			getControles().findControl("fms_global").hide();
  		}
   		if (getWin().GetccDato().needShareGapGLobal()) {
  			getControles().findControl("sharegap_global").edit();
  		} else {
  			getControles().findControl("sharegap_global").hide();
  		}
   		if (getWin().GetccDato().needValorRefGLobal()) {
  			getControles().findControl("valor_global").edit();
  		} else {
  			getControles().findControl("valor_global").hide();
  		}
  		super.checkControls(sControlId);
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
	}  //  @jve:decl-index=0:visual-constraint="24,55"
