package  pss.common.components;

import pss.core.data.BizPssConfig;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JSetupParameters {

	public static final int NIVEL_SIN_DATOS = 0;
	public static final int NIVEL_DATOS_GLOBALES = 1;
	public static final int NIVEL_DATOS_PAIS = 2;
	public static final int NIVEL_DATOS_BUSINESS = 3;
	public static final int NIVEL_DATOS_NODO = 4;
	public static final int NIVEL_DATOS_TRANSACCION = 5;

	private int iDataLevel = 0;
	private boolean bTruncateData = false;
	private boolean bExportData = false;
	private String sExportSQL = "";
	private String sPurgeCondition = null;
	private JMap<String, String> oFunctions;
	private JMap<String, String> oDropFunctions;
	private JMap<String, String> oPreCookFunctions;
	private String sNewTableFunction = "";
	private JMap<String, String> oPostModifyColumn;
	private String sClearDataChangePrimaryKey = "";
	private boolean bHasPurgeCondition = false;
	private String dataModel;

	/**
	 * Constructor
	 */
	public JSetupParameters(int zDataLevel) throws Exception {
		this.iDataLevel = zDataLevel;
		this.oFunctions = JCollectionFactory.createMap();
		this.oDropFunctions = JCollectionFactory.createMap();
		this.oPreCookFunctions = JCollectionFactory.createMap();
		this.oPostModifyColumn = JCollectionFactory.createMap();
		this.dataModel = BizPssConfig.getPssConfig().getInitializedData();
	}

	public boolean hasPurgeCondition() {
		return bHasPurgeCondition;
	}

	public String getPurgeCondition() {
		return sPurgeCondition;
	}

	public String getSQLExported() {
		return sExportSQL;
	}

	public boolean isDataExported() {
		return bExportData;
	}

	public boolean isDataTruncated() {
		return bTruncateData;
	}

	public int getDataLevel() {
		return iDataLevel;
	}

	public boolean isExportData() {
		return bExportData;
	}

	public String getExportSQL() {
		return sExportSQL;
	}

	public JMap<String, String> getHDropFunctions() {
		return oDropFunctions;
	}

	/**
	 * Retorna mapa de sentencias sql que deben ejecutarse despues de modificar o borrar un campo determinado
	 * 
	 * @param No
	 * @return JMap<String, String> Donde el primer String es el nombre del campo, 
	 * y el segundo String es la sentencia SQL a ejecutar despues de modificar o borrar el campo indicado. 
	 */
	public JMap<String, String> getHPostModifyColumn() {
		return oPostModifyColumn;
	}


	/**
	 * Retorna mapa de sentencias sql que deben ejecutarse antes de modificar o borrar un campo determinado
	 * 
	 * @param No
	 * @return JMap<String, String> Donde el primer String es el nombre del campo, 
	 * y el segundo String es la sentencia SQL a ejecutar antes de modificar o borrar el campo indicado. 
	 */
	public JMap<String, String> getHPreCookFunctions() {
		return oPreCookFunctions;
	}

	public JMap<String, String> getHFunctions() {
		return oFunctions;
	}

	public boolean isTruncateData() {
		return bTruncateData;
	}

	public void setTruncateData(boolean zValue) {
		bTruncateData = zValue;
	}

	public void setExportData(boolean zValue) {
		bExportData = zValue;
	}

	public void setExportSQL(String zValue) {
		sExportSQL = zValue;
	}

	public void setPurgeCondition(String zValue) {
		sPurgeCondition = zValue;
	}

	/**
	 * Guarda las sentencias SQL que deben ejecutarse si se crea la tabla
	 * 
	 * @param zValue String - Sentencias SQL a ejecutarse despues de crearse la tabla
	 * @return No
	 */
	public void setNewTableFunction(String zValue) {
		sNewTableFunction = zValue;
	}

	
	/**
	 * Retorna las sentencias sql a ejecutar despues de crear una tabla
	 * 
	 * @param No
	 * @return String sentencias sql a ejecutar despues de crear una tabla
	 */
	public String getNewTableFunction() {
		return sNewTableFunction;
	}

	// Esto es para correr antes de cambiar una clave primaria.
	public void setClearDataChangePrimaryKey(String zValue) {
		sClearDataChangePrimaryKey = zValue;
	}

	public String getClearDataChangePrimaryKey() {
		return sClearDataChangePrimaryKey;
	}

	public void reset() throws Exception {
		bTruncateData = false;
		bExportData = false;
		sExportSQL = "";
		oFunctions.removeAllElements();
	}

	public void addDefaultFunction(String key, String sql) {
		oFunctions.addElement(key, sql);
	}

	public void addDropFunction(String key, String sql) {
		oDropFunctions.addElement(key.toLowerCase(), sql);
	}

	public void addPreCookFunction(String key, String sql) {
		oDropFunctions.addElement(key.toLowerCase(), sql);
	}

	public void addPostModifyColumn(String key, String sql) {
		oPostModifyColumn.addElement(key.toLowerCase(), sql);
	}

	public boolean hasFunction(String zCampo) {
		return oFunctions.containsKey(zCampo.toLowerCase());
	}

	public boolean isIncluirDatos() {
		return iDataLevel > NIVEL_SIN_DATOS;
	}

	public boolean isLevelGlobal() {
		return iDataLevel >= NIVEL_DATOS_GLOBALES;
	}

	public boolean isLevelCountry() {
		return iDataLevel >= NIVEL_DATOS_PAIS;
	}

	public boolean isLevelBusiness() {
		return iDataLevel >= NIVEL_DATOS_BUSINESS;
	}

	public boolean isLevelNode() {
		return iDataLevel >= NIVEL_DATOS_NODO;
	}

	public boolean isLevelTransaction() {
		return iDataLevel >= NIVEL_DATOS_TRANSACCION;
	}

	public static JRecords<BizVirtual> getLevels() throws Exception {
		JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_SIN_DATOS), "Sin Datos", 1));
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_DATOS_GLOBALES), "Nivel Global", 1));
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_DATOS_PAIS), "Nivel Pais", 1));
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_DATOS_BUSINESS), "Nivel Negocio", 1));
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_DATOS_NODO), "Nivel Sucursal", 1));
		oBDs.addItem(JRecord.virtualBD(String.valueOf(NIVEL_DATOS_TRANSACCION), "Nivel Transaccion", 1));
		return oBDs;
	}

	public void setHasPurgeCondition(boolean zValue) {
		bHasPurgeCondition = zValue;
	}

	
	public String getDataModel() {
		return dataModel;
	}

	
	public void setDataModel(String dataModel) {
		this.dataModel=dataModel;
	}

}
