package pss.bsp.consola.datos.cadenas;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.GuiBSPCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.tourism.carrier.GuiCarriers;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiCadenas extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCadenas() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCadena.class;
	}

  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }


	@Override
	public String GetTitle() throws Exception {
		return "Boletos encadenados";
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()|| (BizUsuario.getUsr().isAdminUser()))
			
  	zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("CodigoPNR");
		zLista.AddColumnaLista("creation_date");
		zLista.AddColumnaLista("departure_date");
		
		zLista.AddColumnaLista("void");
		zLista.AddColumnaLista("Internacional");
		zLista.AddColumnaLista("CodigoAerolinea");
		zLista.AddColumnaLista("NumeroBoleto").setActionOnClick(20);
		zLista.AddColumnaLista("numeroboleto_1").setActionOnClick(21);
		zLista.AddColumnaLista("numeroboleto_2").setActionOnClick(22);
		zLista.AddColumnaLista("numeroboleto_3").setActionOnClick(23);
		zLista.AddColumnaLista("numeroboleto_4").setActionOnClick(24);
		zLista.AddColumnaLista("numeroboleto_5").setActionOnClick(25);
		zLista.AddColumnaLista("numeroboleto_6").setActionOnClick(26);
		zLista.AddColumnaLista("numeroboleto_7").setActionOnClick(27);
		zLista.AddColumnaLista("numeroboleto_8").setActionOnClick(28);
		zLista.AddColumnaLista("numeroboleto_9").setActionOnClick(29);
		zLista.AddColumnaLista("numeroboleto_10").setActionOnClick(30);
		
	}
	

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;
	//	zFiltros.addGenericResponsive("Busqueda","").setFilterNeverHide(true);
		if (BizUsuario.getUsr().isAdminUser() )
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		else if ( BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador()) {
			GuiBSPCompany c= new GuiBSPCompany();
			c.setRecord(BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()));
			zFiltros.addComboResponsive("Agencia", "company", c.getHijos());
		}

		zFiltros.addComboResponsive("Tipo documento", "tipo_operacion", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getTiposOperacion();
			}
		}, false).SetValorDefault("ETR");

		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("F.emisión", "creation_date");
			d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
		
		JFormIntervalCDatetimeResponsive dv= zFiltros.addIntervalCDateResponsive("F.despegue", "departure_date");
		zFiltros.addWinLovResponsive("LA",  "CodigoAerolinea",new GuiCarriers().addOrderAdHoc("description", "asc")).setShowKey(true);
		zFiltros.addEditResponsive("Boleto", JObject.JSTRING, "NumeroBoleto");
		zFiltros.addEditResponsive("Pasajero", JObject.JSTRING, "nombre_pasajero", "ilike");
		zFiltros.addEditResponsive("PNR", JObject.JSTRING, "codigopnr").setOperator("=");
		zFiltros.addComboResponsive("GDS ", "gds", new JControlCombo() {
			@Override
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return getGDS();
			}
		}, true);
		zFiltros.addEditResponsive("Origen", JObject.JSTRING, "aeropuerto_origen").setOperator("ilike");
		zFiltros.addEditResponsive("Destino", JObject.JSTRING, "aeropuerto_destino").setOperator("ilike");
		zFiltros.addCheckThreeResponsive(null, "Internacional","Internacional","Todos","Domestico");
		zFiltros.addCheckThreeResponsive("Anulados", "void","Si","-","No");
		zFiltros.addCheckThreeResponsive("Con reemisiones", "con_reemisiones","Si","-","No");
		
	}
	
	@Override
	public void asignFiltersFromFilterBar(JFormFiltro filterBar) throws Exception {
		JFormControl control=filterBar.GetControles().findControl("codigopnr");
		if (control!=null) {
			if (control.hasValue()) {
				this.asignFilterByControl(control);
				this.AddValueToWinsMapFilter(control);
				return;
			} else {
				control=filterBar.GetControles().findControl("numeroboleto");
				if (control.hasValue()) {
					this.asignFilterByControl(control);
					this.AddValueToWinsMapFilter(control);
					return;
				} 
			}
		}
		super.asignFiltersFromFilterBar(filterBar);
	}
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("tipo_operacion")) {
			if (!control.hasValue()) {
				return;
			}
		}
		if (control.getIdControl().equals("NumeroBoleto")) {
			if (control.hasValue()) {
				this.getRecords().addFixedFilter("( NumeroBoleto='"+control.getValue()+"' "
				+" or numeroboleto_1='"+control.getValue()+"' "
				+" or numeroboleto_2='"+control.getValue()+"' "
				+" or numeroboleto_3='"+control.getValue()+"' "
				+" or numeroboleto_4='"+control.getValue()+"' "
				+" or numeroboleto_5='"+control.getValue()+"' "
				+" or numeroboleto_6='"+control.getValue()+"' "
				+" or numeroboleto_7='"+control.getValue()+"' "
				+" or numeroboleto_8='"+control.getValue()+"' "
				+" or numeroboleto_9='"+control.getValue()+"' "
				+" or numeroboleto_10='"+control.getValue()+"') "
				);
				
				return;
			}
		}
		if (control.getIdControl().equals("CodigoPNR")) {
			if (control.hasValue()) {
				this.getRecords().addFilter("CodigoPNR", control.getValue().toUpperCase(), "=");
							return;
			}
		}
		if (control.getIdControl().equals("con_reemisiones")) {
			if (!control.hasValue()) {
				return;
			}
			else if (control.getValue().equals("S")) {
				this.getRecords().addFilter("ref_original_1", "null", "<>");
				return;
			}
			else if (control.getValue().equals("N")){
				this.getRecords().addFilter("ref_original_1", "null");
				return;
			}
		}

		super.asignFilterByControl(control);
	}
	protected JWins getGDS() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("AMADEUS", JLanguage.translate("Amadeus"));
		map.addElement("SABRE", JLanguage.translate("Sabre"));
		map.addElement("NDC", JLanguage.translate("Ndc"));
		map.addElement("TRAVELPORT", JLanguage.translate("Worldspan"));
		map.addElement("GALILEO", JLanguage.translate("Galileo"));
		return JWins.createVirtualWinsFromMap(map);
	}
	protected JWins getOrderOptions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("Fecha", JLanguage.translate("Fecha(Descendente)"));
		map.addElement("Fecha_asc", JLanguage.translate("Fecha(Ascendente)"));
		map.addElement("Boleto", JLanguage.translate("Boleto"));
		map.addElement("Pasajero", JLanguage.translate("Pasajero"));
		return JWins.createVirtualWinsFromMap(map);
	}
	protected JWins getTiposOperacion() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement("ETR", JLanguage.translate("Ticket electronico"));
		map.addElement("EMD", JLanguage.translate("Documento electronico"));
		return JWins.createVirtualWinsFromMap(map);
	}


}
