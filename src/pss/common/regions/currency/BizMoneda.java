package pss.common.regions.currency;

import java.text.DecimalFormat;

import pss.common.components.JSetupParameters;
import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.tools.formatters.JRegionalDataFormatter;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class BizMoneda extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private static JRecords<BizMoneda> oMonedas=null;

	JRecords<BizMonedaPais> oConversiones=null;
	JString pCodigo=new JString();
	JString pDescripcion=new JString();
	JString pDescripcionView=new JString() {
		public void preset() throws Exception {
			pDescripcionView.setValue(GetDescription()+(getOptionalMultiplicatorValue()==1?"":" ("+getOptionalMultiplicatorDescription()+")"));
		};
	};
	JString pDefault=new JString();
	JString pISOCode=new JString();
	JString pCRINDCode=new JString();
	JString pSimbolo=new JString();
	JString pCurrencyFormat=new JString();
	JInteger pFractionDigits=new JInteger();
	JInteger pOptionalDecimals=new JInteger();
	JString pIcono=new JString();
	JInteger pNroIcono=new JInteger();
	JString pOptionalMultiplicator=new JString();



	public static String ISO_ARG="ARS";
	public static String ISO_DOLAR="USD";
	public static String ISO_EURO="EUR";
	public static String ISO_REAL="BRL";
	
	
	public static final String OD_X= "X";
	public static final String OD_0= "0";
	public static final String OD_00= "00";
	public static final String OD_000= "000";
	public static final String OD_0000= "0000";
	public static final String OD_00000= "00000";
	public static final String OD_000000= "0000000";

	
	private static JMap<String, String> ojbOpcionDecimal;
	public static JMap<String, String> getAllOpcionesDecimales() throws Exception {
		if (ojbOpcionDecimal != null)
			return ojbOpcionDecimal;
		JMap<String, String> maps = JCollectionFactory.createMap();
		maps.addElement(OD_X, "Sin Opcion");
		maps.addElement(OD_0, "0");
		maps.addElement(OD_00, "00");
		maps.addElement(OD_000, "000");
		maps.addElement(OD_0000, "0000");
		maps.addElement(OD_00000, "000000");
		maps.addElement(OD_000000, "000000");
		return ojbOpcionDecimal = maps;
	}
	
	private static JMap<String, Long> ojbOpcionDecimalMultiplicador;
	public static JMap<String, Long> getAllOpcionesDecimalesMultiplicador() throws Exception {
		if (ojbOpcionDecimalMultiplicador != null)
			return ojbOpcionDecimalMultiplicador;
		JMap<String, Long> maps = JCollectionFactory.createMap();
		maps.addElement(OD_X, 1l);
		maps.addElement(OD_0, 10l);
		maps.addElement(OD_00, 100l);
		maps.addElement(OD_000, 1000l);
		maps.addElement(OD_0000, 10000l);
		maps.addElement(OD_00000, 100000l);
		maps.addElement(OD_000000, 1000000l);
		return ojbOpcionDecimalMultiplicador = maps;
	}
	
	

	public static String PESOS_ARG="PESOS_ARG";
	public static String DOLAR="DOLAR";

	public String GetDescrip() throws Exception {
		return pDescripcionView.getValue();
	}

	public String getIcono() throws Exception {
		return pIcono.getValue();
	}

	public int getNroIcono() throws Exception {
		return pNroIcono.getValue();
	}

	public boolean hasIcono() throws Exception {
		return !this.pIcono.isEmpty();
	}

	public String GetDescription() throws Exception {
		return pDescripcion.getValue();
	}

	public String GetCode() throws Exception {
		return pCodigo.getValue();
	}

	public String GetSimbolo() throws Exception {
		return pSimbolo.isNull() ? "$" : pSimbolo.getValue();
	}

	public String GetISOCode() throws Exception {
		return pISOCode.getValue();
	}

	public String GetCRINDCode() throws Exception {
		return pCRINDCode.getValue();
	}

	public boolean IsDefault() throws Exception {
		return pDefault.getValue().equalsIgnoreCase("S");
	}

	public String getCurrencyFormat() throws Exception {
		return pCurrencyFormat.getValue();
	}

	public int getOptionalDeciamls() throws Exception {
		return pOptionalDecimals.getValue();
	}
	
	public String getOptionalMultiplicator()throws Exception  {
		return pOptionalMultiplicator.getValue();
	}

	public void setOptionalMultiplicator(String pOptionalMultiplicator) throws Exception {
		this.pOptionalMultiplicator.setValue(pOptionalMultiplicator);
	}
	
	public boolean isNullOptionalMultiplicator()throws Exception  {
		return pOptionalMultiplicator.isNull();
	}

	
	public String getOptionalMultiplicatorDescription()  throws Exception {
		if (isNullOptionalMultiplicator()) return "";
		return "X"+getAllOpcionesDecimalesMultiplicador().getElement(getOptionalMultiplicator());
	}
	public long getOptionalMultiplicatorValue() throws Exception {
		if (isNullOptionalMultiplicator()) return 1;
		return getAllOpcionesDecimalesMultiplicador().getElement(getOptionalMultiplicator());
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizMoneda() throws Exception {
		addItem("codigo", pCodigo);
		addItem("descripcion", pDescripcion);
		addItem("descripcion_view", pDescripcionView);
		addItem("conv_iso", pISOCode);
		addItem("conv_crind", pCRINDCode);
		addItem("simbolo", pSimbolo);
		addItem("currency_format", pCurrencyFormat);
		addItem("fraction_digits", pFractionDigits);
		addItem("optional_decimals", pOptionalDecimals);
		addItem("optional_multiplicator", pOptionalMultiplicator);
		addItem("nro_icono", pNroIcono);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "CODIGO", "Código", true, true, 3); // máximo 3 para usar codificación ISO. EP
		addFixedItem(FIELD, "DESCRIPCION", "Descripción", true, true, 30);
		addFixedItem(VIRTUAL, "descripcion_view", "Descripción", true, true, 250);
		addFixedItem(FIELD, "CONV_ISO", "Código ISO", true, false, 4);
		addFixedItem(FIELD, "CONV_CRIND", "Código CRIND", true, false, 4);
		addFixedItem(FIELD, "SIMBOLO", "Símbolo", true, true, 3);
		addFixedItem(FIELD, "currency_format", "Formato Moneda", true, true, 40, 0, "", "###,##0.00");
		addFixedItem(FIELD, "fraction_digits", "fraction_digits", true, true, 2);
		addFixedItem(FIELD, "optional_decimals", "Decimales adicionales", true, true, 2, 0, null, "1");
		addFixedItem(FIELD, "optional_multiplicator", "Multiplicador", true, false, 10, 0, null, OD_X);
		addFixedItem(FIELD, "nro_icono", "Nro Icono", true, false, 8);
	}

	@Override
	public String GetTable() {
		return "MON_MONEDA";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

	public boolean Read(String zMoneda) throws Exception {
		this.addFilter("codigo", zMoneda);
		return this.read();
	}

	public boolean ReadISO(String zMoneda) throws Exception {
		this.addFilter("CONV_ISO", zMoneda);
		// this.SetNoExcSelect(true);
		return this.read();
	}

	public boolean ReadCRIND(String zMoneda) throws Exception {
		this.addFilter("CONV_CRIND", zMoneda);
		// this.SetNoExcSelect(true);
		return this.read();
	}

//	public BizMonedaPais ObtenerMonedaPaisLocal() throws Exception {
//		return BizMonedaPais.findMonedaPais(pCodigo.getValue(), BizUsuario.getUsr().getCountry(), true);
//	}
	
	public static String getDescription(String moneda) throws Exception {
		return BizMoneda.obtenerMoneda(moneda, true).GetDescrip();
	}

	public static String getSimbolo(String moneda) throws Exception {
		return BizMoneda.obtenerMoneda(moneda, true).GetSimbolo();
	}

	@Override
	public void processDelete() throws Exception {
//		if (JRecords.existsComplete("pss.erp.ctacte.BizCuentaMovDetalle", "moneda", pCodigo.getValue())) JExcepcion.SendError("No se puede eliminar una moneda que fue usado en movimientos de cuenta corriente");
//		if (JRecords.existsComplete("pss.erp.cashDrawer.BizCajaValor", "moneda", pCodigo.getValue())) JExcepcion.SendError("No se puede eliminar una moneda que está vinculada a un valor de caja");
		if (JRecords.existsComplete(BizMonedaPais.class, "moneda", pCodigo.getValue())) 
			JExcepcion.SendError("No se puede eliminar una moneda que está vinculada con un pais");
		if (JRecords.existsComplete(BizPais.class, "moneda_local", pCodigo.getValue())) 
			JExcepcion.SendError("No se puede eliminar una moneda que es la moneda local de un pais");

//		this.ObtenerConversiones().processDeleteAll();
		super.processDelete();
	}

//	public JRecords<BizMonedaPais> ObtenerConversiones() throws Exception {
//		oConversiones=new JRecords<BizMonedaPais>(BizMonedaPais.class);
//		oConversiones.addFilter("moneda", this.pCodigo.getValue());
//		oConversiones.readAll();
//		oConversiones.toStatic();
//		return oConversiones;
//	}
	public static BizMoneda getMoneda(String sMoneda) throws Exception {
		return BizMoneda.obtenerMoneda(sMoneda, false);
	}
	public static synchronized BizMoneda obtenerMoneda(String sMoneda, boolean bThrowExc) throws Exception {
		if (oMonedas==null || !oMonedas.hasHash()) {
			JRecords<BizMoneda> mons =new JRecords<BizMoneda>(BizMoneda.class);
			mons.readAll();
			mons.convertToHash("codigo");
			oMonedas=mons;
		}
		BizMoneda oMoneda=(BizMoneda) oMonedas.findInHash(sMoneda);
		if (oMoneda==null&&bThrowExc) {
			JExcepcion.SendError("No existe la moneda: "+sMoneda);
		}
		return oMoneda;
	}
	
//	public static BizMonedaPais getMonedaPaisLocal(String moneda) throws Exception {
//		return BizUsuario.getUsr().getObjCountryUser().findMonedaPais(BizUsuario.getUsr().getCompany(), moneda);
//	}

//	public static String getMonedaLocal() throws Exception {
//		return BizUsuario.getUsr().getObjCountry().GetMonedaLocal();
//	}
//	
//	public static String getMonedaLocal(String pais) throws Exception {
//		if (pais==null) return BizMoneda.getMonedaLocal();
//		return BizPais.findPais(pais).GetMonedaLocal();
//	}

//	public static double getConverRate(String target) throws Exception {
//		return BizMoneda.getConverRate(BizUsuario.getUsr().getObjCountry().GetMonedaLocal(), target);
//	}
//	public static double getConverRate(String source, String target) throws Exception {
//		BizMonedaConver c=BizMoneda.getConver(source, target);
//		if (c==null) return 0d;
//		return c.getConverRate(source, target);
//	}
//
//	public static BizMonedaConver getConver(String target) throws Exception {
//		return BizMoneda.getConver(BizUsuario.getUsr().getObjCountry().GetMonedaLocal(), target);
//	}
//	
//	public static BizMonedaConver getConver(String source, String target) throws Exception {
//		return BizMonedaConver.findConver(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getCountry(), source, target);
//	}	
	
//	public static double getCotizVenta(String pais, String source, String target) throws Exception {
//		BizMonedaConver c=BizMonedaConver.findConver(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getCountry(), source, target);
//		if (c==null) return 0d;
//		return c.getCotizVenta();
//	}
//	public static double getCotizCompra(String pais, String source, target) throws Exception {
//		BizMonedaConver c=BizMonedaConver.findConver(BizUsuario.getUsr().getCompany(), moneda, pais, false);
//		if (c==null) return 0d;
//		return c.getCotizacionVigente().getCotizCompra();
//	}
//	public static double getCotizContab(String pais, String moneda) throws Exception {
//		BizMonedaPais monedaPais=BizMonedaPais.findMonedaPais(BizUsuario.getUsr().getCompany(), moneda, pais, false);
//		if (monedaPais==null) return 0d;
//		return monedaPais.getCotizacionVigente().getCotizContab();
//	}

	
//	public static double getCotizVenta(String moneda) throws Exception {
//		return getCotizVenta(BizUsuario.getUsr().getCountryUser(), moneda);
//	}
//
//	public static double getCotizCompra(String moneda) throws Exception {
//		return getCotizCompra(BizUsuario.getUsr().getCountryUser(), moneda);
//	}
//
//	public static double getCotizContab(String moneda) throws Exception {
//		return getCotizContab(BizUsuario.getUsr().getCountryUser(), moneda);
//	}

//	public static double convert(String pais, double amount, String monedaSource, String monedaTarget) throws Exception {
//		if (monedaSource.equals(monedaTarget)) return amount;
//		return JTools.rd(amount*convertRate(pais, monedaSource, monedaTarget));
//	}
	
//	public static double convertRate(String pais, String monedaSource, String monedaTarget) throws Exception {
//		if (monedaSource.equals(monedaTarget)) return 1d;
//		double sourceCurrencyRate=BizMoneda.getCotizVenta(pais, monedaSource);
//		if (sourceCurrencyRate==0d) JExcepcion.SendError("No existe cotización para la moneda: ^"+monedaSource);
//		double targetCurrencyRate=BizMoneda.getCotizVenta(pais, monedaTarget);
//		if (targetCurrencyRate==0d) JExcepcion.SendError("No existe cotización para la moneda: ^"+monedaTarget);
//		return  sourceCurrencyRate/targetCurrencyRate;
//	}

//	public static double convert(String pais, double amount, String monedaSource, String monedaTarget) throws Exception {
//		if (monedaSource.equals(monedaTarget)) return amount;
//		return JTools.rd(amount*getConverRate(monedaSource, monedaTarget));
//	}

	public String getCurrencyFormatExactDecimals() throws Exception {
		DecimalFormat CurrencyFormatter=new DecimalFormat(pCurrencyFormat.getValue());
		CurrencyFormatter.setMinimumFractionDigits(4);
		return CurrencyFormatter.toPattern();
	}

	@Override
	public void validateConstraints() throws Exception {
		DecimalFormat oFormat=null;
		try {
			oFormat=new DecimalFormat(this.getCurrencyFormat());
		} catch (Exception ex) {
			JExcepcion.SendError("Formato de moneda erroneo");
		}
		pFractionDigits.setValue(oFormat.getMinimumFractionDigits());
		if (pFractionDigits.getValue()>4) JExcepcion.SendError("No se puede ingresar un formato con más de 4 dígitos decimales");
	}

	public static String getCotizacionAsString(double ctz1, double ctz2) throws Exception {
    JRegionalDataFormatter f = JRegionalFormatterFactory.getRegionalFormatter();
    if (ctz1==ctz2 && ctz1==1d) return "";//f.formatNumber(ctz1,3);
    if (ctz1==ctz2) return f.formatNumber(ctz1,4);
    if (ctz1==0d || ctz2==0d) return "";//f.formatNumber(ctz1,3);
    if (ctz1==1d) return "1/"+f.formatNumber(ctz2,4);
    if (ctz2==1d) return f.formatNumber(ctz1,4);
    return f.formatNumber(ctz1,3)+"/"+f.formatNumber(ctz2,4);

	}

	public static String getCotizRateAsString(double ctz1, double ctz2) throws Exception {
    JRegionalDataFormatter f = JRegionalFormatterFactory.getRegionalFormatter();
    if (ctz1==ctz2) return f.formatNumber(ctz2,3);
    if (ctz1==0d || ctz2==0d) return "";//f.formatNumber(ctz1,3);
    if (ctz1==1d) return "1/"+f.formatNumber(ctz2,3);
    if (ctz2==1d) return f.formatNumber(ctz1,3);
    return f.formatNumber(ctz1,3)+"/"+f.formatNumber(ctz2,3);

	}

	public static int getPresicion(String moneda) throws Exception {
		return JRegionalFormatterFactory.getAbsoluteBusinessFormatter().getCurrencyFractionDigits(moneda);
	}

}
