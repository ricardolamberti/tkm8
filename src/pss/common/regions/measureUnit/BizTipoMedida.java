package pss.common.regions.measureUnit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.formatters.JRegionalFormatterFactory;

public class BizTipoMedida extends JRecord {

	private static JRecords<BizTipoMedida> oTiposMedidas;

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	protected JString pTipo=new JString();
	protected JString pDescrip=new JString();
	protected JInteger pDecimales=new JInteger();

	// -------------------------------------------------------------------------//
	// Getters
	// -------------------------------------------------------------------------//
	public String getTipo() throws Exception {
		return pTipo.getValue();
	}

	public String getDescrip() throws Exception {
		return pDescrip.getValue();
	}

	public int getDecimales() throws Exception {
		return pDecimales.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizTipoMedida() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("Tipo", pTipo);
		addItem("descripcion", pDescrip);
		addItem("decimales", pDecimales);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "Tipo", "Tipo", true, true, 20);
		addFixedItem(FIELD, "descripcion", "Tipo Medida", true, true, 50);
		addFixedItem(FIELD, "decimales", "Decimales", true, true, 1, 0, null, "3");
	}

	@Override
	public String GetTable() {
		return "UNI_TIPO_UNIDADMEDIDA";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(true);
	}

	public boolean Read(String zCodigo) throws Exception {
		this.addFilter("Tipo", zCodigo);
		return this.Read();
	}

	@Override
	public void processDelete() throws Exception {
		if (JRecords.existsComplete(BizUnidadMedida.class, "tipo", pTipo.getValue())) JExcepcion.SendError("No se puede eliminar este Tipo de Unidad porque está asociado a una Unidad de Medida");
		super.processDelete();
	}

	public String format(double zValue) throws Exception {
		String sFormat="0";
		if (this.pDecimales.getValue()>0) {
			sFormat+="."+JTools.stringRepetition("0", this.pDecimales.getValue());
		}
		DecimalFormatSymbols oSymbols=new DecimalFormatSymbols();
		oSymbols.setDecimalSeparator(JRegionalFormatterFactory.getRegionalFormatter().getNumberFormat().getDecimalFormatSymbols().getDecimalSeparator());
		DecimalFormat oMeasureFormat=new DecimalFormat(sFormat);
		oMeasureFormat.setDecimalFormatSymbols(oSymbols);
		double iRounded=JTools.rd(zValue, pDecimales.getValue());
		return oMeasureFormat.format(iRounded);
	}

	public static synchronized BizTipoMedida obtenerTipoMedida(String sTipoMedida, boolean bThrowExc) throws Exception {
		if (oTiposMedidas==null) {
			oTiposMedidas=new JRecords<BizTipoMedida>(BizTipoMedida.class);
			oTiposMedidas.readAll();
			oTiposMedidas.convertToHash("tipo");
		}
		BizTipoMedida oTipo=(BizTipoMedida) oTiposMedidas.findInHash(sTipoMedida);
		if (oTipo==null&&bThrowExc) {
			JExcepcion.SendError("No existe el tipo de unidad de medida: "+sTipoMedida);
		}
		return oTipo;
	}

}
