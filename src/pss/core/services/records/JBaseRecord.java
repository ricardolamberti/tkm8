package pss.core.services.records;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import pss.common.customList.config.relation.JRelations;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.IOrderByControl;
import pss.core.data.interfaces.structure.RCatalog;
import pss.core.data.interfaces.structure.RField;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.data.interfaces.structure.RFixedFilter;
import pss.core.data.interfaces.structure.RFixedHaving;
import pss.core.data.interfaces.structure.RFixedOrderBy;
import pss.core.data.interfaces.structure.RGroupBy;
import pss.core.data.interfaces.structure.RHaving;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.data.interfaces.structure.RStructure;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JObject;
import pss.core.tools.GeoPosition;
import pss.core.tools.JDateTools;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

/**
 * Base del acceso a los datos.
 * 
 * Contiene la estructura de los datos. El modelo de acceso a los datos estático
 * o dinámico.
 * 
 * SubClases: JRecord, JRecords
 * 
 */
public class JBaseRecord implements Serializable {

	/**
	 * Estructura de los datos.
	 */
	private  RStructure rStructure = null;
	private String uniqueID = UUID.randomUUID().toString();
  
	public String getUniqueId() {
		return uniqueID;
	}

	public void setUniqueId(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	private String sVision = "";
	/**
	 * Mientras este flag este en true, no son controladas las reglas de
	 * integridad, usado para la sincronizacion de la base de datos por medio de
	 * uan base remotaet
	 */
	private boolean bViolateIntegrity = false;
	private boolean bLoadForeignFields = false;
	// private Class oBasedClass = null;
	/**
	 * Hay dos modelos de acceso a los registros, el estatico donde los datos
	 * estan en memoria y son recorridos Y el "dinamico" donde son levantados de
	 * la base de datos.
	 * 
	 * bStatic = true: indica que los datos son estaticos
	 */
	private boolean bStatic = false;
	/**
	 * Establece un relacion composicion fuerte entre dos JBaseRecord, del estilo
	 * pais -> Provincia -> Ciudad
	 */
	private transient JBaseRecord parent = null;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Implementada en JRecords para establecer su relacion con un JRecord
	 */
	public Class getBasedClass() {
		// return oBasedClass;
		return null;
	}

	public boolean isSQLBased() {
		return true;
	}

	/**
	 * Retorna si el metodo de acceso a los datos es estatico o dinamico
	 */
	public boolean isStatic() throws Exception {
		return bStatic;
	}

	/**
	 * setea el modo de acceso a los datos
	 * 
	 * @param zValue
	 *          : true es estatico
	 */
	public void setStatic(boolean zValue) {
		bStatic = zValue;
	}

	/**
	 * Obtiene la entidad fuerte de la relacion
	 * 
	 * @return retorna un JBaseRecord o null
	 * @throws Exception
	 */
	public JBaseRecord getParent() throws Exception {
		return this.parent;
	}

	/**
	 * Obtiene la entidad fuerte de la relacion, del padre
	 * 
	 * @return retorna un JBaseRecord o null
	 * @throws Exception
	 */
	public JBaseRecord getGrandParent() throws Exception {
		if (this.parent == null)
			return null;
		return this.parent.parent;
	}

	/**
	 * Establece la entidad de relacion Fuerte
	 * 
	 * @param zValue
	 *          Entidad fuerte
	 * @throws Exception
	 */
	public void setParent(JBaseRecord zValue) throws Exception {
		this.parent = zValue;
	}

	/**
	 * Devuelve al estructura de los datos
	 * 
	 * @return la estructura de los datos
	 * @throws Exception
	 */
	public RStructure getStructure() throws Exception {
		if (rStructure == null) {
			rStructure = new RStructure();
			rStructure.setTable(this.GetTable());
//			rStructure.setTableTemporal(this.GetTableTemporal()); // lo pongo en el readAll para q pueda ser dinamica ya uqe los filtros condicionan los joins
		}
		return rStructure;
	}

	/**
	 * constructor
	 */
	public JBaseRecord() {
	}

	// --------------------------------------------------------------------------
	// Metodos de manejo de Tablas
	// --------------------------------------------------------------------------

	public void SetVision(String zValue) {
		sVision = zValue;
	}

	public boolean hasVision() {
		return !this.GetVision().equals("");
	}

	public void setStructure(RStructure zValue) {
		rStructure = zValue;
	}

	public String GetVision() {
		return sVision;
	}

	public void setViolateIntegrity(boolean zValue) {
		bViolateIntegrity = zValue;
	}

	public boolean isViolateIntegrity() throws Exception {
		return bViolateIntegrity;
	}

	public boolean isTableIncludedInGeneration() {
		return true;
	}

	public String GetTable() throws Exception {
		return this.getStructure().getTable();
	}
	public String GetTableTemporal() throws Exception {
		return this.getStructure().getTableTemporal();
	}
	public boolean canSynchro() throws Exception {
		return true;
	}

	public void setTableTemporal(String t) throws Exception {
		this.getStructure().setTableTemporal(t);
	}

	@SuppressWarnings("unchecked")
	public void SetClassInterface(Class zValue) throws Exception {
		getStructure().setInterfase(zValue);
	}

	@Deprecated
	public void SetNoExcSelect(boolean zValor) throws Exception {
		getStructure().setWithoutException(zValor);
	}

	public void dontThrowException(boolean zValor) throws Exception {
		getStructure().setWithoutException(zValor);
	}

	public void SetSQL(String zValue) throws Exception {
		getStructure().setSQL(zValue);
	}

	public void lock(boolean zValue) throws Exception {
		getStructure().setWithLock(zValue);
	}

	@Deprecated
	public void SetLock(boolean zValue) throws Exception {
		this.lock(zValue);
	}

	public void SetNoLock(boolean zValue) throws Exception {
		getStructure().setNoLock(zValue);
	}

	public void SetIgnoreLocks(boolean zValue) throws Exception {
		getStructure().setWithIgnoreLocks(zValue);
	}

	public void SetDirtyRead(boolean zValue) throws Exception {
		getStructure().setWithDirtyRead(zValue);
	}

	public void SetIndexHint(String zValue) throws Exception {
		getStructure().SetIndexHint(zValue);
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Getters de rBase
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/

	public boolean ifLock() throws Exception {
		return getStructure().withLock();
	}

	public boolean ifIgnoreLocks() throws Exception {
		return getStructure().withIgnoreLocks();
	}

	public boolean ifDirtyRead() throws Exception {
		return getStructure().withDirtyRead();
	}

	public boolean hasIndexHint() throws Exception {
		return getStructure().hasIndexHint();
	}

	public String getIndexHint() throws Exception {
		return getStructure().getIndexHint();
	}

	public boolean ifNoLock() throws Exception {
		return getStructure().ifNoLock();
	}

	public boolean withoutException() throws Exception {
		return getStructure().withoutException();
	}

	public String getLastSQL() throws Exception {
		return getStructure().getLastSQL();
	}

	public String GetLastSQL() throws Exception {
		return getStructure().getLastSQL();
	}

	public Class GetClassInterface() throws Exception {
		return getStructure().getInterfase();
	}

	public JList<RField> getFields() throws Exception {
		return getStructure().getFields();
	}

	public JList<RJoins> getJoins() throws Exception {
		return getStructure().getJoins();
	}

	public JList<RGroupBy> getGroupBy() throws Exception {
		return getStructure().getGroupBy();
	}

	public JList<ROrderBy> getOrderBy() throws Exception {
		return getStructure().getOrderBy();
	}
	
	public JList<ROrderBy> getCorteControl() throws Exception {
		return getStructure().getCorteControl();
	}

	public JList<RFilter> getFilters() throws Exception {
		return getStructure().getFilters();
	}

	public JList<RFixedFilter> getFixedFilters() throws Exception {
		return getStructure().getFixedFilters();
	}

	public JList<RHaving> getHaving() throws Exception {
		return getStructure().getHaving();
	}

	public JList<RFixedHaving> getFixedHaving() throws Exception {
		return getStructure().getFixedHaving();
	}

	public JList<RFixedOrderBy> getFixedOrderBy() throws Exception {
		return getStructure().getFixedOrderBy();
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Wrappers de rBase
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public void clearFields() throws Exception {
		getStructure().clearFields();
	}

	public void clearInnerJoins() throws Exception {
		getStructure().clearInnerJoins();
	}

	public void clearBasicFilters() throws Exception {
		getStructure().clearBasicFilters();
	}

	public void clearFilters() throws Exception {
		getStructure().clearFilters();
	}

	public void clearFixedFilters() throws Exception {
		getStructure().clearFixedFilters();
	}

	public void clearJoins() throws Exception {
		this.getStructure().clearJoins();
	}

	public void clearStructure() throws Exception {
		this.getStructure().clearAll();
	}

	public void clearGroupBy() throws Exception {
		this.getStructure().clearGroupBy();
	}

	public void clearOrderBy() throws Exception {
		this.getStructure().clearOrderBy();
	}

	public void clearDynamicFilters() throws Exception {
		this.getStructure().clearDynamicFilters();
	}
	public boolean hasDynamicFilters(boolean withDefault) throws Exception {
	  return	this.getStructure().hasDynamicFilters(withDefault);
	}

	public RField addField(String zCampo) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, "", "", false, false, null);
	}

	public RField addField(String table, String zCampo, String value) throws Exception {
		return this.addField(table, zCampo, value, "", false, false, null);
	}

	public RField addField(String zCampo, String zValor) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, zValor, JObject.JSTRING, false, false, null);
	}

	public RField addField(String zCampo, boolean zValor) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, zValor ? "S" : "N", JObject.JBOOLEAN, false, false, null);
	}

	public RField addField(String zCampo, double zValor) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, String.valueOf(zValor), JObject.JFLOAT, false, false, null);
	}

	public RField addField(String zCampo, long zValor) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, String.valueOf(zValor), JObject.JLONG, false, false, null);
	}

	public RField addField(String zCampo, Date zValor) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, JDateTools.DateToString(zValor), JObject.JDATE, false, false, null);
	}

	public RField addField(String zCampo, Date zValor, boolean hasTime) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, JDateTools.DateToString(zValor, "dd-MM-yyyy HH:mm:ss"), JObject.JDATETIME, false, false, null);
	}

	public RField addField(String zCampo, JMultiple zValor, boolean zAuto, boolean zOnlySelects) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, zValor.toString(), zValor.getObjectType(), zAuto, zOnlySelects, null);
	}
	public RField addField(String zCampo, JObject zValor, boolean zAuto, boolean zOnlySelects) throws Exception {
		return this.addField(getStructure().getTable(), zCampo, zValor.toString(), zValor.getObjectType(), zAuto, zOnlySelects, null);
	}

	public RField addField(String zTabla, String zCampo, String zValor, String zTipo, boolean zAuto, boolean zOnlySelects, String zRename) throws Exception {
		return this.getStructure().addField(zTabla, zCampo, zValor, zTipo, zAuto, zOnlySelects, zRename);
	}

	public void addFuntion(String zCampo) throws Exception {
		this.getStructure().addField("", zCampo, "", "", false, false);
	}

	public void addGroupBy(String zTabla, String zCampo) throws Exception {
		this.getStructure().addGroupBy(zTabla, zCampo);
	}

	public void addGroupBy(String zCampo) throws Exception {
		this.getStructure().addGroupBy(this.getTableByFilter(zCampo), zCampo);
	}

	public RJoins addJoin(String zTablaJoin) throws Exception {
		return this.addJoin(zTablaJoin, false);
	}

	public RJoins addJoin(String zTablaJoin, boolean bInnerJoin) throws Exception {
		return this.getStructure().addJoin(zTablaJoin, bInnerJoin);
	}
	public RJoins addJoin(String zTablaJoin, String alias, boolean bInnerJoin) throws Exception {
		return this.getStructure().addJoin(zTablaJoin, alias,bInnerJoin);
	}
	// JRelations.JOIN tipicamente
	public RJoins addJoin(String  typeJoin,String zTablaJoin, String condicion) throws Exception {
		return this.getStructure().addJoin(typeJoin,zTablaJoin, null,condicion!=null,condicion);
	}
	public RJoins addJoin(String  typeJoin,String zTablaJoin, String alias, boolean bInnerJoin, String condicion) throws Exception {
		return this.getStructure().addJoin(typeJoin,zTablaJoin, alias,bInnerJoin,condicion);
	}
	public RJoins addJoin(String  typeJoin,String zTablaJoin, String alias, String condicion) throws Exception {
		return this.getStructure().addJoin(typeJoin,zTablaJoin, alias,condicion!=null,condicion);
	}
	public void addJoins(JList<RJoins> joins) throws Exception {
		JIterator<RJoins> it = joins.getIterator();
		while (it.hasMoreElements()) {
			this.getStructure().addJoin(it.nextElement());
		}
	}
	public boolean hasJoin(String table) throws Exception {
		return this.getStructure().hasJoin(table);
	}
	public boolean hasJoin(String table, String alias) throws Exception {
		return this.getStructure().hasJoin(table, alias);
	}
	public boolean hasJoin(String table, String alias,boolean recursivo) throws Exception {
		return this.getStructure().hasJoin(table, alias,recursivo);
	}
	public boolean hasJoinOrTable(String table, String alias) throws Exception {
		return hasJoinOrTable(table, alias,true);
	}
	public boolean hasJoinOrTable(String table, String alias,boolean recursivo) throws Exception {
		if (table==null) return true;
		if (this.getStructure().hasJoin(table, alias,recursivo)) return true;
		if (alias==null && this.getStructure().hasJoin(table,null,recursivo)) return true;
		if (this.getStructure().getTable().toLowerCase().equals(table.toLowerCase())) return true;
		if (this.getStructure().getTableTemporal()!=null && this.getStructure().getTableTemporal().toLowerCase().equals(table.toLowerCase())) return true;
		return false;
	}



	public ROrderBy addOrderBy(String zCampo) throws Exception {
		return this.getStructure().addOrderBy(this.getTableByFilter(zCampo), zCampo, "ASC");
	}

	public ROrderBy addOrderBy(String zCampo, String zOrden) throws Exception {
		return this.getStructure().addOrderBy(this.getTableByFilter(zCampo), zCampo, zOrden);
	}

	public ROrderBy addOrderBy(String zTabla, String zCampo, String zOrden) throws Exception {
		return this.getStructure().addOrderBy(zTabla, zCampo, zOrden);
	}
	public void clearOrderBy(ROrderBy orderby) throws Exception {
		this.getStructure().removeOrderBy(orderby);
	}
	public void clearOrderBy(String zTabla, String zCampo, String zOrden) throws Exception {
		this.getStructure().removeOrderBy(zTabla, zCampo, zOrden);
	}
	public ROrderBy addCorteControl(String zCampo,IOrderByControl zControl) throws Exception {
		return this.getStructure().addCorteControl(this.getTableByFilter(zCampo), zCampo, "ASC",zControl);
	}

	public ROrderBy addCorteControl(String zCampo, String zOrden,IOrderByControl zControl) throws Exception {
		return this.getStructure().addCorteControl(this.getTableByFilter(zCampo), zCampo, zOrden,zControl);
	}

	public ROrderBy addCorteControl(String zTabla, String zCampo, String zOrden,IOrderByControl zControl) throws Exception {
		return this.getStructure().addCorteControl(zTabla, zCampo, zOrden,zControl);
	}
	public ROrderBy addCorteControl(ROrderBy corte) throws Exception {
		return this.getStructure().addCorteControl(corte);
	}

	public RFixedFilter addFixedFilter(String zFiltro) throws Exception {
		return this.getStructure().addFixedFilter(zFiltro);
	}

	public RFixedFilter addFixedFilter(String zFiltro,String tablaAsoc) throws Exception {
		return this.getStructure().addFixedFilter(zFiltro, tablaAsoc);
	}


	public RFixedHaving addFixedHaving(String zFiltro) throws Exception {
		return this.getStructure().addFixedHaving(zFiltro);
	}

	public void addFixedOrderBy(String zOrder) throws Exception {
		this.getStructure().addFixedOrderBy(zOrder);
	}

	public RFilter addFilter(String zFiltro, JObject zValor) throws Exception {
		if (zValor.isRecord())
			return this.addFilter(zFiltro, zValor, JObject.JBD, "=", "AND", "");
		if (zValor.isIntervalDateTime() || zValor.isIntervalDate())
			return this.addFilter(zFiltro, zValor, zValor.getObjectType(), "=", "AND", "");
		return this.addFilter(zFiltro, zValor.toString(), zValor.getObjectType(), "=", "AND", "");
	}

	// public void addFilter(String zFiltro, String zValor, String zOper, String
	// zRel) throws Exception {
	// this.addFilter(zFiltro, zValor, JObject.JSTRING, zOper, zRel, "");
	// }

	public RFilter addFilter(String zFiltro, String zValor, String zOper) throws Exception {
		return this.addFilter(zFiltro, zValor, JObject.JSTRING, (zOper.equals("") ? "=" : zOper), "AND", "");
	}

	public RFilter addFilter(String zFiltro, Serializable zValor, String zOper) throws Exception {
		return this.addFilter(zFiltro, zValor, JObject.JBD, (zOper.equals("") ? "=" : zOper), "AND", "");
	}

	public RFilter addFilter(String zFiltro, Serializable zValor) throws Exception {
		return this.addFilter(zFiltro, zValor, JObject.JBD, "=", "AND", "");
	}
	public RFilter addFilter(String zFiltro, String zValor) throws Exception {
		return this.addFilter(zFiltro, zValor, JObject.JSTRING, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, int zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JINTEGER, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Integer zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JINTEGER, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, long zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JLONG, "=", "AND", "");
	}
	
	public RFilter addFilter(String zFiltro, Long zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JLONG, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, double zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JFLOAT, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Double zValor) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JFLOAT, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, boolean zValor) throws Exception {
		return this.addFilter(zFiltro, zValor ? "S" : "N", JObject.JBOOLEAN, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Boolean zValor) throws Exception {
		return this.addFilter(zFiltro, zValor ? "S" : "N", JObject.JBOOLEAN, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, GeoPosition zValor) throws Exception {
		return this.addFilter(zFiltro, new JGeoPosition(zValor).toRawString(), JObject.JGEOPOSITION, "=", "AND", "");
	}

	public RFilter ddFilter(String zFiltro, Date zValor, boolean withTime) throws Exception {
		return this.addFilter(zFiltro, JDateTools.DateToString(zValor, JDateTime.DATETIME_FORMAT), JObject.JDATE, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Date zValor) throws Exception {
		return this.addFilter(zFiltro, JDateTools.DateToString(zValor, JDateTools.DEFAULT_DATE_FORMAT), JObject.JDATE, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Date zValor, String zOper) throws Exception {
		return this.addFilter(zFiltro, JDateTools.DateToString(zValor, JDateTools.DEFAULT_DATE_FORMAT), JObject.JDATE, zOper, "AND", "");
	}

	public RFilter addFilter(String zFiltro, long zValor, String zOper) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JLONG, zOper, "AND", "");
	}

	public RFilter addFilter(String zFiltro, Long zValor, String zOper) throws Exception {
		return this.addFilter(zFiltro, String.valueOf(zValor), JObject.JLONG, zOper, "AND", "");
	}
	
	public RFilter addFilter(String zFiltro, String zValor, String zTipo, String zOper, String zRel, String zAgrup) throws Exception {
		RFilter f= this.addFilter(this.getTableByFilter(zFiltro), zFiltro, zOper, zValor, zTipo, zRel, zAgrup);
		JProperty p = this.getRecordRef().getFixedProp(zFiltro, false);
		if (p!=null) f.setVirtual(this.getRecordRef().getFixedProp(zFiltro).isVirtual());
		return f;
	}

	public RFilter addFilter(String zFiltro, Serializable zValor, String zTipo, String zOper, String zRel, String zAgrup) throws Exception {
		return this.addFilter(this.getTableByFilter(zFiltro), zFiltro, zOper, zValor, zTipo, zRel, zAgrup);
	}
	public RFilter addFilter(String table, String zFiltro, Serializable zValor, String zTipo, String zOper, String zRel, String zAgrup) throws Exception {
		return this.addFilter(table, zFiltro, zOper, zValor, zTipo, zRel, zAgrup);
	}
	public RFilter addFilter(String zTable, String zCampo, String zValor, String zOper) throws Exception {
		return this.addFilter(zTable, zCampo, zOper, zValor, JObject.JSTRING, "AND", "");
	}
	public RFilter addFilter(String zTable, String zCampo, long zValor, String zOper) throws Exception {
		return this.addFilter(zTable, zCampo, zOper, zValor, JObject.JLONG, "AND", "");
	}

	public RFilter addFilter(String zTable, String zCampo, String zOper, String zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		return this.getStructure().addFilter(zTable, zCampo, zOper, zValor, zTipo, zRel, zAgrup);
	}
	public RFilter addFilter(String zTable, String zCampo, String zOper, Serializable zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		return this.getStructure().addFilter(zTable, zCampo, zOper, zValor, zTipo, zRel, zAgrup);
	}

	public RFilter addFilter(String zTable, String zFiltro, Date zValor, String zOper,boolean withTime) throws Exception {
		return this.addFilter(zTable,zFiltro, JDateTools.DateToString(zValor, JDateTime.DATETIME_FORMAT), JObject.JDATETIME, zOper, "AND", "");
	}

	public RFilter addFilter(String zFiltro, Date zValor, boolean withTime) throws Exception {
		return this.addFilter(zFiltro, JDateTools.DateToString(zValor, JDateTime.DATETIME_FORMAT), JObject.JDATETIME, "=", "AND", "");
	}

	public RFilter addFilter(String zFiltro, Date zValor, String zOper, boolean withTime) throws Exception {
		return this.addFilter(zFiltro, JDateTools.DateToString(zValor,  JDateTime.DATETIME_FORMAT), JObject.JDATETIME, zOper, "AND", "");
	}
	public RFilter addFilter(String table,String zFiltro, JObject zValor) throws Exception {
		if (zValor.isRecord())
			return this.addFilter(table,zFiltro, zValor, JObject.JBD, "=", "AND", "");
		if (zValor.isIntervalDateTime() || zValor.isIntervalDate())
			return this.addFilter(table,zFiltro, zValor, zValor.getObjectType(), "=", "AND", "");
		return this.addFilter(table,zFiltro, zValor.toString(), zValor.getObjectType(), "=", "AND", "");
	}

	public RHaving addHaving(String zTable, String zCampo, String zOper, String zValor, String zTipo, String zRel, String zAgrup) throws Exception {
		return this.getStructure().addHaving(zTable, zCampo, zOper, zValor, zTipo, zRel, zAgrup);
	}
	
	
	public RCatalog getCatalog() throws Exception {
		return this.getStructure().getCatalog();
	}

	public void assignCatalog(String catalog) throws Exception {
		this.getStructure().setCatalog(new RCatalog(catalog));
	}

	public boolean hasCatalog() throws Exception {
		return this.getStructure().getCatalog() != null;
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Fin Wrappers de rBase
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public void fieldsToFilters(JRecord rec) throws Exception {
		fieldsToFilters(rec,true);
	}
	public void fieldsToFilters(JRecord rec, boolean includeRecords) throws Exception {
		Iterator<JProperty> oEnum = rec.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!includeRecords && oProp.isRecord()) continue;
			if (!includeRecords && oProp.isRecords()) continue;
			if (!rec.getProp(oProp.GetCampo()).hasValue()) continue;
			JObject obj =  rec.getProp(oProp.GetCampo());
			this.addFilter(oProp.GetCampo(), (Serializable) obj.getObjectValue());
		}
	}
	public boolean filterHasValue(String zFiltro) throws Exception {
		return this.getStructure().getFilterValue(zFiltro) != null;
	}

	public boolean filterHasFixedValue(String zFiltro) throws Exception {
		RFilter f = this.getStructure().getFiltro(zFiltro);
		if (f == null)
			return false;
		if (f.isDynamic())
			return false;
		return f.getValue() != null && !f.getValue().equals("");
	}
	public boolean filterHasDynamicValue(String zFiltro) throws Exception {
		RFilter f = this.getStructure().getFiltro(zFiltro);
		if (f == null)
			return false;
		if (!f.isDynamic())
			return false;
		return f.getValue() != null && !f.getValue().equals("");
	}

	public String getFilterValue(String zFiltro, String def) throws Exception {
		String v = this.getStructure().getFilterValue(zFiltro);
		if (v == null)
			return def;
		return v;
	}
	
	public String getFilterValue(String zFiltro, String def, String operator) throws Exception {
		String v = this.getStructure().getFilterValue(zFiltro, operator);
		if (v==null) return def;
		return v;
	}
	
	public String getFilterOperValue(String zFiltro,String op) throws Exception {
		return this.getStructure().getFilterValue(zFiltro,op);
	}
	public String getFilterValue(String zFiltro) throws Exception {
		return this.getStructure().getFilterValue(zFiltro);
	}
	public Object getFilterOjValue(String zFiltro) throws Exception {
		return this.getStructure().getFilterObjValue(zFiltro);
	}
	public JIntervalDate getFilterIntervalValue(String zFiltro) throws Exception {
		Object obj = this.getStructure().getFilterObjValue(zFiltro);
		if (obj==null) return null;
		if (obj instanceof JIntervalDate)
			return (JIntervalDate) obj;
		return null;
	}
	public RFilter getFilter(String zFiltro) throws Exception {
		return this.getStructure().getFiltro(zFiltro);
	}

	public void clearFilter(String zFiltro, String operator) throws Exception {
		this.getStructure().clearFilter(zFiltro, operator);
	}

	public void clearFilter(String zFiltro) throws Exception {
		this.getStructure().clearFilter(zFiltro);
	}

	public void setFilter(RFilter filter) throws Exception {
		this.getStructure().setFilter(filter);
	}
	public RFilter setFilterValue(String zFilter, String zValue) throws Exception {
		return this.getStructure().setFilterValue(this.getTableByFilter(zFilter), zFilter, zValue,null);
	}

	public RFilter setFilterValue(String zFilter, String zValue,String type) throws Exception {
		return this.getStructure().setFilterValue(this.getTableByFilter(zFilter), zFilter, zValue,type);
	}
	public RFilter setFilterObjValue(String zFilter, Serializable zValue,String type) throws Exception {
		return this.getStructure().setFilterObjValue(this.getTableByFilter(zFilter), zFilter, zValue,type);
	}

	public void clearJoin(String zJoin) throws Exception {
		this.getStructure().clearJoin(zJoin);
	}

	public boolean compareFilters(RFilter zFiltro, JBaseRegistro zSet) throws Exception {
		boolean bRc = false;
		String sTipo = zFiltro.getType();

		// Comparaciones de strings
		if (sTipo.equals(JObject.JSTRING) || sTipo.equals(JObject.JPASSWORD) || sTipo.equals(JObject.JDATE)) {
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.CampoAsStr(zFiltro.getField()).equals(zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.CampoAsStr(zFiltro.getField()).equals(zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.CampoAsStr(zFiltro.getField()).compareToIgnoreCase(zFiltro.getValue()) <= 0);
			// Comparaciones de integers
		} else if (sTipo.equals(JObject.JINTEGER)) {
			// return zSet.CampoAsInt( zFiltro.sCampo ).equals( Integer.valueOf(
			// zFiltro.sValor ) );
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.CampoAsInt(zFiltro.getField()).equals(Integer.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.CampoAsInt(zFiltro.getField()).equals(Integer.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.CampoAsInt(zFiltro.getField()).compareTo(Integer.valueOf(zFiltro.getValue())) <= 0);

			// Comparaciones de longs
		} else if (sTipo.equals(JObject.JLONG)) {
			// return zSet.CampoAsLong( zFiltro.sCampo ).equals( Long.valueOf(
			// zFiltro.sValor ) );
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.CampoAsLong(zFiltro.getField()).equals(Long.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.CampoAsLong(zFiltro.getField()).equals(Long.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.CampoAsLong(zFiltro.getField()).compareTo(Long.valueOf(zFiltro.getValue())) <= 0);

			// Comparaciones de floats
		} else if (sTipo.equals(JObject.JFLOAT)) {
			// return zSet.CampoAsFloat( zFiltro.sCampo ).equals( Double.valueOf(
			// zFiltro.sValor ) );
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.CampoAsFloat(zFiltro.getField()).equals(Double.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.CampoAsFloat(zFiltro.getField()).equals(Double.valueOf(zFiltro.getValue()));
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.CampoAsFloat(zFiltro.getField()).compareTo(Double.valueOf(zFiltro.getValue())) <= 0);

		}
		return bRc;
	}

	public void copyStructureFrom(RStructure zStructure) throws Exception {
		this.getStructure().copyStructureFrom(zStructure);
	}

	public RStructure cloneStructure() throws Exception {
		return this.getStructure().cloneStructure();
	}

	public int getFieldIndex(String field) throws Exception {
		return this.getStructure().getFieldIndex(field);
	}

	public boolean isTableBased() throws Exception {
		if (getStructure().getTable() == null)
			return false;
		return !getStructure().getTable().trim().equals("");
	}

	public String serialize(String zRootName) throws Exception {
		JXMLElementFactory oFactory = JXMLElementFactory.getInstance();
		if (zRootName == null)
			zRootName = "root";
		Element oRoot = oFactory.createElement(zRootName);
		oRoot.appendChild(this.serialize(oRoot));
		Document document = oRoot.getOwnerDocument();

		DOMImplementationLS domImplementation = (DOMImplementationLS) document.getImplementation();
	  LSSerializer lsSerializer = domImplementation.createLSSerializer();
	  return lsSerializer.writeToString(oRoot);   

//		return oRoot.toString();
	}

	public String serialize() throws Exception {
		return serialize((String) null);
	}

	protected Element serialize(Element zRoot) throws Exception {
		return null;
	}

	public void unSerialize(String zData) throws Exception {
		Element oRoot = JXMLElementFactory.getInstance().createElementFromString(zData);
		this.unSerializeRoot(oRoot);
	}

	public void unSerializeRoot(Element zRoot) throws Exception {
	}

	public boolean exists() throws Exception {
		JBaseRegistro set = JBaseRegistro.recordsetFactory(this);
		return set.readExist();
	}

	public void notifyEvent(String zEvent) throws Exception {
		if (!JBDatos.GetBases().isNotifyEvents())
			return;
		JRecordEvent oEvent = new JRecordEvent(this);
		oEvent.SetId(zEvent);
		oEvent.SetArgs(this);
		JBDatos.notifyJBDEvent(oEvent);
	}

	public void notifyDropOK() throws Exception {
	}

	public String getTableByFilter(String filter) throws Exception {
		return null;
	}

	public void setLoadForeignFields(boolean value) {
		this.bLoadForeignFields = value;
	}

	protected boolean loadForeignFields() throws Exception {
		return bLoadForeignFields;
	}

	/**
	 * Determina si la tabla representada es una tabla del sistema de la base de
	 * datos o es una tabla de usuario. Por defecto retorna siempre false. Si se
	 * necesita representar una tabla de sistema de la base de datos hay que
	 * sobreescribir este metodo retornando true
	 * 
	 * @return "false" - Indica que la clase representa un tabla de usuario.
	 *         "true" - Si la clase representa una tabla de sistema
	 */
	public boolean isSystemTable() {
		return false;
	}

	public void forceFilterToData() throws Exception {
	}

	public void applyFiltersFromMap(JMap<String, String> map) throws Exception {
		if (map == null)
			return;
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			this.setFilterValue(key, map.getElement(key),null);
		}
	}
	
	public void applyFiltersFromMapObject(JMap<String, Object> map) throws Exception {
		if (map == null)
			return;
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			Object obj = map.getElement(key);
			if (obj instanceof String)
				this.addFilter(key,(String)obj);
			else if (obj instanceof Integer)
				this.addFilter(key,(int)obj);
			else if (obj instanceof Long)
				this.addFilter(key,(long)obj);
			else if (obj instanceof Double)
				this.addFilter(key,(double)obj);
			else if (obj instanceof Float)
				this.addFilter(key,(float)obj);
			else if (obj instanceof Boolean)
				this.addFilter(key,(boolean)obj);
			else if (obj instanceof GeoPosition)
				this.addFilter(key,(GeoPosition)obj);
			else if (obj instanceof Date)
				this.addFilter(key,(Date)obj);
			else if (obj instanceof Serializable)
				this.addFilter(key,(Serializable)obj);
		}
	}
	public void applyFiltersFromList(JList<RFilter> filters) throws Exception {
		if (filters == null)
			return;
		JIterator<RFilter> iter = filters.getIterator();
		while (iter.hasMoreElements()) {
			RFilter f = iter.nextElement();
			this.setFilter(f);
		}
	}
	public void applyFixedFiltersFromList(JList<RFixedFilter> filters) throws Exception {
		if (filters == null)
			return;
		JIterator<RFixedFilter> iter = filters.getIterator();
		while (iter.hasMoreElements()) {
			RFixedFilter f = iter.nextElement();
			this.addFixedFilter(f.getFiltro());
		}
	}
	public void applyJoinsFromList(JList<RJoins> joins) throws Exception {
		if (joins == null)
			return;
		JIterator<RJoins> iter = joins.getIterator();
		while (iter.hasMoreElements()) {
			RJoins f = iter.nextElement();
			this.addJoin(f.GetTablaJoin());
		}
	}
	public void applyGroupsFromList(JList<RGroupBy> groups) throws Exception {
		if (groups == null)
			return;
		JIterator<RGroupBy> iter = groups.getIterator();
		while (iter.hasMoreElements()) {
			RGroupBy f = iter.nextElement();
			this.addGroupBy(f.GetTabla(),f.GetCampo());
		}
	}
	public boolean hasFiexdSQL() throws Exception {
		return this.getStructure().getSQL() != null;
	}

	public String getLayoutIdent() throws Exception {
		return this.getClass().getName();
	}
	public String getLayoutSubIdent() throws Exception {
		return null;
	}
	
	public boolean hidePreasignedFilters() throws Exception {
		return true;
	}
	
	public boolean hasStaticFilter(String field) throws Exception {
		RFilter f = this.getStructure().getFiltro(field);
		if (f==null) return false;
		if (!f.hasValue()) return false;
		if (f.isDynamic()) return false;
		return true;
	}

	public JRecord getRecordRef() throws Exception {
		return (JRecord) this;
	}
	
	public boolean hasChecked(String fld) throws Exception {
		return this.getFilterValue(fld, "N").equals("S");
	}

}