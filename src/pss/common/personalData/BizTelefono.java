package  pss.common.personalData;

import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizTelefono extends JRecord {

	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	JLong pPersona = new JLong();
	JLong pTipo_tel = new JLong();
	JString pNumero = new JString();
	JString pObservacion = new JString();
	JString pDescrTipoTel = new JString() {
		@Override
		public void preset() throws Exception {
			ObtenerDescrTipoTel();
		}
	};

	private static JRecords<BizVirtual> oTiposTelefonos = null;

	public static final long TIPO_PARTICULAR = 1;
	public static final long TIPO_COMERCIAL = 2;
	public static final long TIPO_FAX = 3;
	public static final long TIPO_CELULAR = 4;
	public static final long TIPO_ALTERNATIVO1 = 5;
	public static final long TIPO_ALTERNATIVO2 = 6;
	public Long GetTipoNumero() throws Exception {
		return pTipo_tel.getValue();
	}
	public String getDescripcionTipo() throws Exception {
		return ((BizVirtual)getTiposTelefonos().findInHash(""+GetTipoNumero())).getDescrip();
	}
	public String GetNumero() throws Exception {
		return pNumero.getValue();
	}
	public String getObservacion() throws Exception {
		return pObservacion.getValue();
	}

	// SETTERs
	public void SetPersona(long zValor) throws Exception {
		pPersona.setValue(zValor);
	}
	public void SetTipoTel(long zValor) throws Exception {
		pTipo_tel.setValue(zValor);
	}
	public void SetNumero(String zValor) throws Exception {
		pNumero.setValue(zValor);
	}
	public void setObservacion(long zValor) throws Exception {
		pObservacion.setValue(zValor);
	}

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizTelefono() throws Exception {}

	@Override
	public void createProperties() throws Exception {
		addItem("persona", pPersona);
		addItem("tipo_tel", pTipo_tel);
		addItem("numero", pNumero);
		addItem("observacion", pObservacion);
		addItem("descr_tipo_tel", pDescrTipoTel);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "persona", "Persona", true, true, 10);
		addFixedItem( KEY, "tipo_tel", "Tipo Teléfono", true, true, 2);
		addFixedItem( FIELD, "numero", "Número Teléfono", true, true, 50);
		addFixedItem( FIELD, "observacion", "Observación", true, false, 200);
		addFixedItem( VIRTUAL, "descr_tipo_tel", "Tipo Tel", true, true, 5);
	}

	@Override
	public String GetTable() {
		return "PER_telefono";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
    zParams.setExportData(zParams.isLevelBusiness());
	}

	public boolean Read(long zPersona, long zTipoTel) throws Exception {
		addFilter("persona", zPersona);
		addFilter("tipo_tel", zTipoTel);
		return Read();
	}

	private void ObtenerDescrTipoTel() throws Exception {
		pDescrTipoTel.setValue((getTiposTelefonos().findVirtualKey(String.valueOf(pTipo_tel.getValue())).getDescrip()));
	}

	public synchronized static JRecords<BizVirtual> getTiposTelefonos() throws Exception {
		if (oTiposTelefonos != null)
			return oTiposTelefonos;
		oTiposTelefonos = JRecords.createVirtualBDs();
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_PARTICULAR), "Particular", 1));
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_COMERCIAL), "Comercial", 1));
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_FAX), "Fax", 1));
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_CELULAR), "Celular", 1));
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_ALTERNATIVO1), "Alternativo 1", 1));
		oTiposTelefonos.addItem(JRecord.virtualBD(String.valueOf(TIPO_ALTERNATIVO2), "Alternativo 2", 1));
		oTiposTelefonos.convertToHash("valor");
		return oTiposTelefonos;
	}
	
	public String getDescription() throws Exception {
		return pNumero.getValue(); 
	}
	
	public void processUpdate() throws Exception {
		if (this.pNumero.isEmpty()) {
			this.processDelete();
			return;
		}
		super.processUpdate();
	}

	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass(GuiDomicilio.class.getCanonicalName());

	
		rels.hideField("secuencia");
		rels.hideField("persona");
		rels.hideField("tipo_tel");
		rels.hideField("observacion");

	}
}
