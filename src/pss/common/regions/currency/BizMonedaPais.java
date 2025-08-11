package pss.common.regions.currency;

import pss.common.components.JSetupParameters;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.common.regions.divitions.BizPais;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizMonedaPais extends JRecord {
	
//	public static final String VISION_WITH_CTZ = "1";
//	public static final String VISION_VIEW = "2";
	
	static JRecords<BizMonedaPais> oMonedasPais;
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany=new JString();
	JString pMoneda=new JString();
	JString pPais=new JString();
//	JFloat pCotizVenta=new JFloat() {
//		@Override
//		public void preset() throws Exception {
//			pCotizVenta.setValue(getCotizacionVigente().getCotizVenta());
//		}
//		public int getCustomPrecision() throws Exception {return 3;}
//	};
//	JFloat pCotizCompra=new JFloat() {
//		public void preset() throws Exception {
//			pCotizCompra.setValue(getCotizacionVigente().getCotizCompra());
//		}
//		public int getCustomPrecision() throws Exception {return 3;}
//	};
//	JFloat pCotizContab=new JFloat() {
//		public void preset() throws Exception {
//			pCotizContab.setValue(getCotizacionVigente().getCotizContab());
//		}
//		public int getCustomPrecision() throws Exception {return 3;}
//	};
	JString pDescrPais=new JString() {
		public void preset() throws Exception {
			pDescrPais.setValue(ObtenerPais().getDescrPaisLista());
		}
		public String getRel() {
			return "pais";
		}
	};
	JString pDescrMoneda=new JString() {
		@Override
		public void preset() throws Exception {
			pDescrMoneda.setValue(ObtenerMoneda().GetDescrip());
		}
	};
//	JString pDescrMonedaCtz=new JString() {
//		@Override
//		public void preset() throws Exception {
//			pDescrMonedaCtz.setValue(getDescrMonedaCtz());
//		}
//	};
	JString pCodMoneda=new JString() {
		@Override
		public void preset() throws Exception {
			pCodMoneda.setValue(ObtenerMoneda().GetDescrip());
		}
	};
	BizPais oPais=null;
	BizMoneda oMoneda=null;
//	private BizMonedaCotizacion cotiz=null;
	
	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}

	public String getMoneda() throws Exception {
		return pMoneda.getValue();
	}
	// public double GetCoeficiente() throws Exception {
	// return this.pCoeficiente.getValue();
	// }
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizMonedaPais() throws Exception {
		addItem("company", pCompany);
		addItem("moneda", pMoneda);
		addItem("pais", pPais);
//		addItem("cotiz_venta", pCotizVenta);
//		addItem("cotiz_compra", pCotizCompra);
//		addItem("cotiz_contab", pCotizContab);
		addItem("descr_moneda", pDescrMoneda);
//		addItem("descr_moneda_ctz", pDescrMonedaCtz);
		addItem("descr_pais", pDescrPais);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "moneda", "Moneda", true, true, 15);
		addFixedItem(KEY, "pais", "Pais", true, true, 2);
//		addFixedItem(VIRTUAL, "cotiz_venta", "Cotización Venta", true, true, 11, 4);
//		addFixedItem(VIRTUAL, "cotiz_compra", "Cotización Compra", true, true, 11, 4);
//		addFixedItem(VIRTUAL, "cotiz_contab", "Cotización Contable", true, true, 11, 4);
		addFixedItem(VIRTUAL, "descr_moneda", "Moneda", true, true, 18);
//		addFixedItem(VIRTUAL, "descr_moneda_ctz", "Moneda", true, true, 18);
		addFixedItem(VIRTUAL, "descr_pais", "Pais", true, true, 18, 4);
	}

	@Override
	public String GetTable() {
		return "MON_MONEDA_PAIS";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

	public boolean Read(String company, String zMoneda, String zPais) throws Exception {
		addFilter("company", company);
		addFilter("moneda", zMoneda);
		addFilter("pais", zPais);
		return this.read();
	}

	public BizPais ObtenerPais() throws Exception {
		if (oPais!=null) return oPais;
		oPais=new BizPais();
		oPais.Read(pPais.getValue());
		return oPais;
	}

	public BizMoneda ObtenerMoneda() throws Exception {
		if (oMoneda!=null) return oMoneda;
		oMoneda=BizMoneda.obtenerMoneda(pMoneda.getValue(), true);
		return oMoneda;
	}

//	public BizMoneda ObtenerCodMoneda() throws Exception {
//		if (oMoneda!=null) return oMoneda;
//		oMoneda=new BizMoneda();
//		oMoneda.Read(pMoneda.getValue());
//		oMoneda.GetCode();
//		return oMoneda;
//	}

	@Override
	public void processDelete() throws Exception {
		if (JRecords.existsComplete("pss.erp.cashDrawer.BizCajaValor", "company", pCompany.getValue(), "country", pPais.getValue(), "moneda", pMoneda.getValue())) 
			JExcepcion.SendError("No se puede eliminar una relación Moneda-País que está asociado a un Tipo de Valor en el módulo de Cajas");
		if (JRecords.existsComplete("pss.erp.MedioPago.BizFormaPago", "company", pCompany.getValue(), "pais", pPais.getValue(), "sistema_pago", "MONEDA", "vinc1", pMoneda.getValue())) 
			JExcepcion.SendError("No se puede eliminar una relación Moneda-País que participa en la configuracion de una forma de pago");
		if (JRecords.existsComplete("pss.erp.MedioPago.BizFormaPago", "company", pCompany.getValue(), "pais", pPais.getValue(), "sistema_pago", "TARJETA", "vinc2", pMoneda.getValue())) 
			JExcepcion.SendError("No se puede eliminar una relación Moneda-País que participa en la configuracion de una forma de pago");
		if (JRecords.existsComplete("pss.erp.MedioPago.BizFormaPago", "company", pCompany.getValue(), "pais", pPais.getValue(), "sistema_pago", "CHEQUE", "vinc1", pMoneda.getValue())) 
			JExcepcion.SendError("No se puede eliminar una relación Moneda-País que participa en la configuracion de una forma de pago");
		JRecords<BizMonedaCotizacion> oMonedaCotizaciones=new JRecords<BizMonedaCotizacion>(BizMonedaCotizacion.class);
		oMonedaCotizaciones.addFilter("company", this.pCompany.getValue());
		oMonedaCotizaciones.addFilter("moneda", this.pMoneda.getValue());
		oMonedaCotizaciones.addFilter("pais", this.pPais.getValue());
		oMonedaCotizaciones.readAll();
		oMonedaCotizaciones.processDeleteAll();
		super.processDelete();
//		refreshLoadedDependentData();
	}

//	@Override
//	public void processInsert() throws Exception {
//		BizMonedaCotizacion ctz = new BizMonedaCotizacion();
//		ctz.setCompany(this.pCompany.getValue());
//		ctz.setPais(this.pPais.getValue());
//		ctz.setMoneda(this.pMoneda.getValue());
//		ctz.setCotizVenta(1d);
//		ctz.setCotizCompra(1d);
//		ctz.setCotizContab(1d);
//		ctz.processInsert();
//		super.processInsert();
//	}

//	private void refreshLoadedDependentData() throws Exception {
//		BizPais oNodeCountry=BizUsuario.getUsr().getObjNodo().ObtenerPais();
//		if (oNodeCountry.GetPais().equals(pPais.getValue())&&oNodeCountry.GetMonedaLocal().equals(pMoneda.getValue())) {
//			oNodeCountry.ObtenerMonedaPaisLocal().Read(pMoneda.getValue(), pPais.getValue());
//		}
//		JRegionalFormatterFactory.refreshForCountry(pPais.getValue());
//	}

//	public BizMonedaCotizacion getCotizacionVigente() throws Exception {
//		if (this.cotiz!=null) return this.cotiz;
//		BizMonedaCotizacion record=BizMonedaCotizacion.readCotizacionCorriente(pCompany.getValue(), pPais.getValue(), pMoneda.getValue());
//		if (record==null) JExcepcion.SendError("No exite cotización");
//		return (this.cotiz=record);
//	}

	public static synchronized BizMonedaPais findMonedaPais(String company, String zMoneda, String zPais, boolean bThrowExc) throws Exception {
		if (oMonedasPais==null) {
			oMonedasPais=new JRecords<BizMonedaPais>(BizMonedaPais.class);
			oMonedasPais.readAll();
			oMonedasPais.convertToHash("company;pais;moneda");
		}
		BizMonedaPais oMonedaPais=(BizMonedaPais) oMonedasPais.findInHash(company+zPais+zMoneda);
		if (oMonedaPais==null&&bThrowExc) {
			JExcepcion.SendError("No existe la moneda "+zMoneda+" para el pais "+zPais);
		}
		return oMonedaPais;
	}
	
//	public void clearCotizacion() throws Exception {
//		this.cotiz=null;
//	}
	
//	public String getDescrMonedaCtz() throws Exception {
//		//return ObtenerMoneda().GetDescrip()+" - ("+this.getCotizacionVigente().getCotizVenta()+"/"+this.getCotizacionVigente().getCotizCompra()+")";
//		return ObtenerMoneda().GetDescrip()+" - ("+this.getCotizacionVigente().getCotizVenta()+")";
//	}
}
