package pss.core.services.records;

import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JObject;
import pss.core.tools.JExcepcion;
import pss.core.win.IControl;

public class JProperty {
	public final static String TOT_SUM="SUM";
	public final static String TOT_COUNT="COUNT";
	public final static String TOT_TEXT="TEXT";

	public final static String TIPO_CAMPO="CAMPO";
	public final static String TIPO_CLAVE="CLAVE";
	public final static String TIPO_VIRTUAL="VIRTUAL";
	public final static String TIPO_ARRAY="ARRAY";
	public final static String TIPO_FOREIGN="FOREIGN";
	public final static String TIPO_RECORD="RECORD";
	public final static String TIPO_RECORDS="RECORDS";

	public int bTipo;
	public String sCampo;
	public String sTabla;
	public String sDescr;
	public String sAtributo=null;
	public int iLongitud;
	public int iPrecision;
	public boolean bAutonumerico;
	public boolean bHide=false;
	private String sDependiente;
	private Class clase;
	public boolean bRequerido;
	public boolean bOnlyDisableUpdate;
	public boolean dontCheck;
	public boolean serialize;

	public boolean isOnlyDisableUpdate() {
		return bOnlyDisableUpdate;
	}
	public JProperty setOnlyDisableUpdate(boolean bOnlyDisableUpdate) {
		this.bOnlyDisableUpdate = bOnlyDisableUpdate;
		return this;
	}
	public boolean hasControl(JRecord record) throws Exception {
		return record.getControl(GetCampo())!=null;
	}
	public IControl getControl(JRecord record) throws Exception {
		IControl localControl = record.getControl(GetCampo());
		if (localControl==null) return localControl;
		localControl.setRecord(record);
		return localControl;
	}
	@Deprecated
	public JProperty setControl(IControl control) throws Exception {
		return this;
	}

	public int iOrden;
	public String sDefault;
	/**
	 * @return the bAutonumerico
	 */
	public boolean isAutonumerico() {
		return bAutonumerico == false;
	}
	public boolean isHide() {
		return bHide;
	}

	public Class getClase() {
		return clase;
	}
	/**
	 * @param autonumerico the bAutonumerico to set
	 */
	public void setAutonumerico(boolean autonumerico) {
		bAutonumerico = autonumerico;
	}
	public JProperty setHide(boolean autonumerico) {
		bHide = autonumerico;
		return this;
	}
	public JProperty setCampo(String value) {
		sCampo = value;
		return this;
	}

	public JProperty setTabla(String value) {
		sTabla = value;
		return this;
	}
	public boolean isDontCheck() {
		return dontCheck;
	}

	public JProperty setDontCheck(boolean dontCheck) {
		this.dontCheck = dontCheck;
		return this;
	}

	public JProperty() {
	}

	// Campos JObject
	public JProperty(String zTipo, String zCampo, String zDesc, JObject zDato, String zCampoMje, boolean zAutonumerico, boolean zRequerido, int zLongitud, int zPrecision, String zAtributo, String zDefault, String zTabla) {
		sCampo=zCampo;
		sDescr=zDesc;
		bAutonumerico=zAutonumerico;
		bRequerido=zRequerido;
		iLongitud=zLongitud;
		iPrecision=zPrecision;
		sAtributo=zAtributo;
		sDefault=zDefault;
		sTabla=zTabla;

		if (TIPO_CAMPO.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.FIELD;
		} else if (TIPO_VIRTUAL.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.VIRTUAL;
		} else if (TIPO_CLAVE.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.KEY;
		} else if (TIPO_ARRAY.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.ARRAY;
		} else if (TIPO_RECORD.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.RECORD;
		} else if (TIPO_RECORDS.equalsIgnoreCase(zTipo)) {
			bTipo=JRecord.RECORDS;
		} else {
			bTipo=0x00;
		}
	}

	public JProperty(int bTipoCampo, String zCampo, String zDesc, JObject zDato, String zCampoMje, boolean zAutonumerico, boolean zRequerido, int zLongitud, int zPrecision, String zAtributo, String zDefault, String zTabla) {
		bTipo=bTipoCampo;
		sCampo=zCampo;
		sDescr=zDesc;
		bAutonumerico=zAutonumerico;
		bRequerido=zRequerido;
		iLongitud=zLongitud;
		iPrecision=zPrecision;
		sAtributo=zAtributo;
		sDefault=zDefault;
		sTabla=zTabla;
	}

	public JProperty(String zTipo, String zCampo, String zDesc, JObject zDato, String zCampoMje, boolean zAutonumerico, boolean zRequerido, int zLongitud, int zPrecision, String zAtributo, String zDefault) {
		this(zTipo, zCampo, zDesc, zDato, zCampoMje, zAutonumerico, zRequerido, zLongitud, zPrecision, zAtributo, zDefault, "");
	}


	public String getType() {
		if (this.isField()) return TIPO_CAMPO;
		if (this.isVirtual()) return TIPO_VIRTUAL;
		if (this.isKey()) return TIPO_CLAVE;
		if (this.isArray()) return TIPO_ARRAY;
		if (this.isRecord()) return TIPO_RECORD;
		if (this.isRecords()) return TIPO_RECORDS;
		return "";
	}

	public int getTipo() {
		return bTipo;
	}

	// Si el dato es campo
	public final boolean isKey() {
		return bTipo==JRecord.KEY;
	}

	public final boolean ifForeign() {
		return bTipo==JRecord.FOREIGN;
	}
	
	public final boolean isBaseRecord() {
		return this.isRecord() || this.isRecords();
	}
	
	public final boolean isRecord() {
		return bTipo==JRecord.RECORD;
	}
	public final boolean isRecords() {
		return bTipo==JRecord.RECORDS;
	}

	public final boolean isField() {
		return bTipo==JRecord.FIELD;
	}

	public final boolean isVirtual() {
		return bTipo==JRecord.VIRTUAL;
	}

	public final boolean isArray() {
		return bTipo==JRecord.ARRAY;
	}

	public final boolean isTableBased() {
		return bTipo==JRecord.FIELD||bTipo==JRecord.KEY||bTipo==JRecord.FOREIGN;
	}
	public final boolean isTableBasedExtended() {
		return bTipo==JRecord.FIELD||bTipo==JRecord.KEY||bTipo==JRecord.FOREIGN||bTipo==JRecord.RECORD||bTipo==JRecord.RECORDS;
	}

	public final boolean ifConverString() {
		return bTipo==JRecord.FIELD||bTipo==JRecord.KEY||bTipo==JRecord.VIRTUAL||bTipo==JRecord.FOREIGN;
	}

	public final boolean serializeSupported() {
		return bTipo==JRecord.FIELD||bTipo==JRecord.KEY||bTipo==JRecord.ARRAY||bTipo==JRecord.FOREIGN||isSerialize();
	}

	public final boolean isRequire() {
		return bRequerido;
	}

	public boolean isSerialize() {
		return serialize;
	}
	public JProperty setSerialize(boolean serialize) {
		this.serialize = serialize;
		return this;
		
	}
	public JProperty setDescription(String zDescription) throws Exception {
		sDescr=zDescription;
		return this;
	}

	public JProperty setClase(Class value) throws Exception {
		clase=value;
		return this;
	}

	public int getSize() {
		return iLongitud;
	}

	public String GetAtributo() {
		return sAtributo;
	}

	public String getDefault() {
		return sDefault;
	}

	public String GetCampo() {
		return sCampo;
	}

	public String GetTabla() {
		return sTabla;
	}

	public int GetPrecision() {
		// if(oDato instanceof JCurrency){
		// JCurrency oMoneda=(JCurrency) oDato;
		// return oMoneda.GetDecimales();
		// }
		return iPrecision;
	}

	public String GetDescripcion() {
		return sDescr;
	}

	public boolean ifAutonumerico() {
		return !bAutonumerico;
	}
	
	public JProperty setOrden(int zValue) {
		iOrden=zValue;
		return this;
	}

	public int getOrden() {
		return iOrden;
	}

	public JProperty setDependiente(String zValue) {
		sDependiente=zValue;
		return this;
	}

	public String getDependiente() {
		return sDependiente;
	}

	public boolean hasDependencias() {
		return sDependiente!=null;
	}

	public JProperty SetLongitud(int zValue) throws Exception {
		iLongitud=zValue;
		return this;
	}

	public JProperty SetPrecision(int zValue) throws Exception {
		iPrecision=zValue;
		return this;
	}
	

	public void validateSize(JObject zObjeto) throws Exception {

		/**
		 * 
		 * la idea es que se revise el dato antes de grabarlo en la db de manera que esta no genere excepciones para hacer esto se usa el valor del size y la longitud en el caso de los tipos comunes y un calculo del maximo valor permitido para el caso de los tipos numericos que * permiten punto decimal
		 * 
		 */
		if (zObjeto==null) return;
		if (getSize() <= 0) return;
			
		if (zObjeto instanceof JFloat||zObjeto instanceof JCurrency) {
			if (((JFloat) zObjeto).getValue()>=Math.pow(10, getSize()-GetPrecision())) JExcepcion.SendError("El valor ingresado participa de una operacion que resulta en valores que exeden los limites monetarios previstos para el sistema");
		} else {
			if (zObjeto.toString().length()>getSize()) JExcepcion.SendError("El valor ingresado participa de una operacion que resulta en valores que exceden los limites previstos para el sistema");
		}
		return;
	}

	public JProperty createClone() throws Exception {
		JProperty p = new JProperty();
		p.bTipo=this.bTipo;
		p.sCampo=sCampo;
		p.sTabla=sTabla;
		p.sDescr=sDescr;
		p.sAtributo=sAtributo;
		p.iLongitud=iLongitud;
//		p.control=control;
		p.bHide=bHide;
		p.iPrecision=iPrecision;
		p.bAutonumerico=bAutonumerico;
		p.sDependiente=sDependiente;
		p.clase=clase;
		p.bRequerido=bRequerido;
		p.iOrden=iOrden;
		p.sDefault=sDefault;
		p.bOnlyDisableUpdate=bOnlyDisableUpdate;
		p.serialize=serialize;
		p.dontCheck=dontCheck;
		return p;
	}
	
	public String getCampoRaiz() throws Exception {
		int dot = this.GetCampo().indexOf(".");
		if (dot==-1) return this.GetCampo();
		return this.GetCampo().substring(0, dot);
	}
	
}
