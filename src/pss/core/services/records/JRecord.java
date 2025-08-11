package pss.core.services.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.script.Bindings;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelations;
import pss.common.dbManagement.JBaseDBManagement;
import pss.common.dbManagement.depuration.BizDepuration;
import pss.common.dbManagement.depuration.JPurgeInterface;
import pss.common.dbManagement.synchro.JDBClassIndex;
import pss.common.dbManagement.synchro.JDBClassIndexes;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.regions.propagation.BizPropagarMje;
import pss.common.regions.propagation.JSetupPropagate;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.JExec;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JObjectFactory;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.IControl;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JControlTree;
import pss.core.winUI.controls.JFormRow;

/**
 * Representa un registro de datos con sus propiedades y comportamiento
 * persistente. Gestiona campos, relaciones y operaciones de clonación o
 * actualización sobre la fuente de datos.
 */
public class JRecord extends JBaseRecord implements Comparable<Object>,JPurgeInterface {

	private static JMap<String, JMap<String, JProperty>> aPropClass;
	private static JMap<String, String> mIdentityFields = JCollectionFactory.createMap(5);
	private static final byte FLAG_HAS_TO_CHECK_FIXED_PROPERTIES = (byte) 1;
	private static final byte FLAG_FIXED_PROPERTIES_ARE_DEFINED = (byte) 2;
	private static final byte FLAG_DATOS_LEIDOS = (byte) 4;

	public final static int FIELD = 0x01;
	public final static int KEY = 0x02;
	public final static int VIRTUAL = 0x03;
	public final static int ARRAY = 0x04;
	public final static int FOREIGN = 0x05;
	public final static int RECORD = 0x06;
	public final static int RECORDS = 0x07;

	protected JMap<String, JObject<?>> hProperties;
	protected transient JMap<String, JProperty> hFixedProperties;
	protected transient JMap<String, IControl> hControlProperties;
	private JMap<String,String> extraData;
	// protected JMap<String, JIndex> hIndexes = null;
	protected transient JDBClassIndexes hIndexes = null;
	protected transient JRelations relations = null;
	private Boolean bTryNotCommit = false;
	public Boolean getTryNotCommit() {
		return bTryNotCommit;
	}

	public void setTryNotCommit(Boolean bTryNotCommit) {
		this.bTryNotCommit = bTryNotCommit;
	}
	public String szSortCriteria;
	private Boolean bForzeAllRecordsUpdate = false;
	private byte flags = FLAG_HAS_TO_CHECK_FIXED_PROPERTIES | FLAG_FIXED_PROPERTIES_ARE_DEFINED;

	public JRecord() throws Exception {
	}

	private String rowId;
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public void doBeginTransaction() throws Exception {

	}

	public void doCommitTransaction() throws Exception {

	}

	public void doRollBackTransaction() throws Exception {

	}
	
	public String getMessageUpdateOk() throws Exception {
		return null;

	}
	
	public String getMessageInsertOk() throws Exception {
		return null;
	}
	
	 
	  

	public void cloneTable(Class<?> table, String oldCompany, String newcompany)
			throws Exception {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JRecords<?> records = new JRecords(table);
		records.addFilter("company", oldCompany);
		records.toStatic();
		while (records.nextRecord()) {
			JRecord rec = records.getRecord();
			rec.getProp("company").setValue(newcompany);
			rec.processUpdateOrInsertWithCheck();
		}

	}

	public void cloneTable(Class<?> table,JMap<String,Object> keyClone, JMap<String,Object> keyParent, boolean onlyNew)	throws Exception {
		if (!onlyNew ){
			if (!getRecordRef().hasAutonumerico()) { // si no tiene autonumerico, la recreacion tendra claves coincidente, con autonumericos esto no sucederia
				JRecords<?> recordsToDelete = new JRecords(table);
				
				recordsToDelete.applyFiltersFromMapObject(keyClone);
				recordsToDelete.deleteAll();
			} else {
				JRecords<?> recordsToFind= new JRecords(table);
				
				recordsToFind.applyFiltersFromMapObject(keyClone);
				if (recordsToFind.exists())
					return;// si tiene autonumerico, por ahora solo si la tabla esta vacia
	
			}
		}
		JRecords<?> records = new JRecords(table);
		
		records.applyFiltersFromMapObject(keyParent);
		records.toStatic();
		while (records.nextRecord()) {
			JRecord rec = records.getRecord();
			JMap<String,Object> keyClone2 = JCollectionFactory.createMap();
			JMap<String,Object> keyParent2 = JCollectionFactory.createMap();
			keyClone2.addElements(keyClone);
			keyParent2.addElements(keyParent);
			rec.cloneRecord(keyClone2,keyParent2, onlyNew);
		}
	}

	public JRecord cloneRecord(JMap<String,Object> keyClone, JMap<String,Object> keyParent, boolean onlyNew) throws Exception {
		JRecord clone = this.getClass().newInstance();

		clone.copyNotAutonumProperties(this);
	
		JIterator<String> iter = keyClone.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			clone.getProp(key).setValue(keyClone.getElement(key));
		}
		if (clone.hasKeyValue()  && clone.checkRecordExists())
			clone.update();
		else {
			clone.insert();
			clone.assignIdentityField();
			
		}
		return clone;
	}

	
	/**
	 * Answers whether this object has to clear the maps and lists it uses when
	 * finalized. The default is <code>false</code>. This means that the maps
	 * won't be cleared but the references to them will be set to
	 * <code>null</code>.
	 */
	public boolean clearAllOnFinalize() {
		return false;
	}

	public boolean isValidateConstraints() {
		return false;
	}

	public boolean read() throws Exception {
//		if(this instanceof BizCustomList)
//			PssLogger.logInfo("vvv");
		if (!this.isTableBased() || this.isStatic() || !this.isSQLBased())
			return true;
		else if (this.getFilters().isEmpty())
			JExcepcion.SendError("Debe especificar algun filtro");
		
		this.setTableTemporal(this.GetTableTemporal());
		JBaseRegistro oSet = createRecordset();

		boolean bOk = oSet.Select();
		if (bOk)
			this.Read(oSet);
		oSet.close();
		return bOk;
	}

	@Deprecated
	public boolean Read() throws Exception {
		return this.read();
	}

	public void setLogicalParent(JRecord zValue) throws Exception {
  }
	public void setDatosLeidos(boolean zValue) {
		if (zValue)
			flags = (byte) (flags | FLAG_DATOS_LEIDOS);
		else
			flags = (byte) (flags & ~FLAG_DATOS_LEIDOS);
	}

	public boolean wasRead() throws Exception {
		if (this.isStatic())
			return true;
		return (flags & FLAG_DATOS_LEIDOS) != 0;
	}
	
	public boolean wasDbRead() throws Exception {
		return (flags & FLAG_DATOS_LEIDOS) != 0;
	}

	public void Read(JBaseRegistro zSet) throws Exception {
		this.Read(zSet, this.loadForeignFields());
		this.clean();
	}
	// reimplementar para limpiar caches luego de una lectura
	public void clean() throws Exception {
	}
	protected boolean hasQuotes() {
		return false;
	}

	
	public void Read(JBaseRegistro zSet, boolean withForeign) throws Exception {
		String sFieldName = null;
		JProperty prop = null;
		JObject<?> pObject = null;
		JMap<String, JProperty> oFProp = this.getFixedProperties();
		if (oFProp == null)
			return;
		Iterator<JProperty> oEnum = oFProp.valueIterator();
		while (oEnum.hasNext()) {
			prop = oEnum.next();

			if (!withForeign && prop.ifForeign())
				continue;
			if (!prop.isTableBased())
				continue;
			sFieldName = prop.GetCampo();
			pObject = this.getProp(sFieldName);
			if (pObject == null) {
				JExcepcion.SendError("No puedo encontrar la propiedad^ '" + prop.GetCampo() + "' - " + this.getClass().getName());
			}


			this.loadFieldFromDataSet(prop.GetCampo(), pObject, zSet);
//			if (this instanceof BizCustomList)
//				PssLogger.logInfo("zxxx");
			this.setDatosLeidos(true);
		}
	}
	
	public JRecord getPropValue(String fieldName,JObject prop, JRecord value) throws Exception {
		return value;
	}
	public String getPropValue(String fieldName,JObject prop, String value) throws Exception {
		return value;
	}
	public void loadFieldFromDataSet(String field, JObject obj,JBaseRegistro rSet) throws Exception {
		String sObjectType = obj.getObjectType();

		if (hasQuotes()) {
			field = field.replaceAll("\"", "");
		}
		if (rSet==null) return;
		try {
			if (sObjectType.equals(JObject.JSTRING)) {
				obj.setValue(rSet.CampoAsStr(field));
			}
			if (sObjectType.equals(JObject.JCOLOUR)) {
				obj.setValue(rSet.CampoAsStr(field));
			} if (sObjectType.equals(JObject.JIMAGE)) {
					obj.setValue(rSet.CampoAsStr(field));
			} else if (sObjectType.equals(JObject.JINTEGER)) {
				obj.setValue(rSet.CampoAsInt(field));
			} else if (sObjectType.equals(JObject.JLONG)) {
				obj.setValue(rSet.CampoAsLong(field));
			} else if (sObjectType.equals(JObject.JFLOAT)|| sObjectType.equals(JObject.JCURRENCY)) {
				obj.setValue(rSet.CampoAsFloat(field));
			} else if (sObjectType.equals(JObject.JDATE)) {
				assignDateValue(rSet, field, obj);
			} else if (sObjectType.equals(JObject.JDATETIME)) {
				assignDateTimeValue(rSet, field, obj);
			} else if (sObjectType.equals(JObject.JPASSWORD)) {
				obj.setValue(JTools.PasswordToString(rSet.CampoAsStr(field)));
			} else if (sObjectType.equals(JObject.JHOUR)) {
				obj.setValue(JTools.StringToHour(rSet.CampoAsStr(field)));
			} else if (sObjectType.equals(JObject.JBOOLEAN)) {
				obj.setValue(rSet.CampoAsStr(field));
			} else if (sObjectType.equals(JObject.JHTML)) {
				obj.setValue(JTools.decodeIso(rSet.CampoAsStr(field)));
			} else if (sObjectType.equals(JObject.JMULTIPLE)) {
				obj.setValue(rSet.CampoAsStr(field));
			} else if (sObjectType.equals(JObject.JGEOPOSITION)) {
				obj.setValue(rSet.CampoAsStr(field));
			} else if (sObjectType.equals(JObject.JBLOB)) {
				// obj.setValue(rSet.CampoAsStr(field));
				obj.setValue(rSet.CampoAsBlob(field));
			}
			obj.unsetEstablish();
		} catch (Exception e) {
			PssLogger.logDebug("Error en campo: " + field);
			throw e;
		}
	}

	// private void assignFloatValue(JBaseRegistro zSet, String sFieldName,
	// JProperty property, JFloat pObject) throws Exception {
	//
	// }

	private void assignDateValue(JBaseRegistro zSet, String sFieldName,
			JObject<?> pObject) throws Exception {
		JDate obj = (JDate) pObject;
		if (obj.savedAsString()) {
			String sdate = zSet.CampoAsStr(sFieldName).trim();
			obj.setValue(JDateTools.StringToDate(sdate, obj.getDbFormat()));
		} else {
			obj.setValue(zSet.CampoAsDate(sFieldName));
		}
	}

	private void assignDateTimeValue(JBaseRegistro zSet, String sFieldName,
			JObject<?> pObject) throws Exception {
		JDateTime dateTime = (JDateTime) pObject;

		if (dateTime.savedAsString()) {
			String sdate = zSet.CampoAsStr(sFieldName).trim();
			dateTime.setValue(JDateTools.StringToDate(sdate,
					dateTime.getDbFormat()));
			return;
		}

		Date oDate = zSet.CampoAsDate(sFieldName);
		java.sql.Time oTime = zSet.CampoAsTime(sFieldName);

		if (oDate == null)
			return;
		if (oTime == null)
			return;

		Calendar cdate = Calendar.getInstance();
		cdate.setTimeInMillis(oDate.getTime());

		Calendar ctime = Calendar.getInstance();
		ctime.setTimeInMillis(oTime.getTime());

		cdate.set(Calendar.HOUR_OF_DAY, ctime.get(Calendar.HOUR_OF_DAY));
		cdate.set(Calendar.MINUTE, ctime.get(Calendar.MINUTE));
		cdate.set(Calendar.SECOND, ctime.get(Calendar.SECOND));
		cdate.set(Calendar.MILLISECOND, ctime.get(Calendar.MILLISECOND));

		pObject.setValue(cdate.getTime());
	}

	public void deleteMultiple() throws Exception {
		delete(false);
	}

	public void execDeleteAll() throws Exception {
		JExec oExec = new JExec(null, "deleteAll") {

			@Override
			public void Do() throws Exception {
				deleteMultiple();
			}
		};
		oExec.execute();
	}

	public void delete() throws Exception {
		delete(true);
	}

	private void delete(boolean usekeys) throws Exception {
		if (!this.isTableBased()) {
			notifyDeleteOK();
			return;
		}
		JBaseRegistro oSet = createRecordset();

		if (usekeys) {
			this.clearFilters();
			this.keysToFilters();
		}
		oSet.delete();
		oSet.close();

		this.notifyDeleteOK();
	}


	public String getSqlInsert() throws Exception {
		if (!this.isTableBased()) {
			return "";
		}

		JBaseRegistro oSet = JBaseRegistro.recordsetFactory(this);

		clearFields();
		this.fieldsToStructure();
		String sql = oSet.ArmarInsert();
		oSet.close();
		return sql;
	}

	public void insert() throws Exception {
		if (!this.isTableBased()) {
			notifyInsertOK();
			return;
		}
		JBaseRegistro oSet = createRecordset();

		clearFields();
		this.fieldsToStructure();
		oSet.insert();
		oSet.close();
		this.clearFields();

		notifyInsertOK();
	}

	public void updateMultiple() throws Exception {
		update(false);
	}

	public void update() throws Exception {
		update(true);
	}

	private void update(boolean usekeys) throws Exception {

		if (!this.isTableBased()) {
			notifyUpdateOK();
			return;
		}
		JBaseRegistro oSet = createRecordset();

		this.clearFields();
		if (usekeys)
			this.clearFilters();
		this.fieldsToStructure(true);
		if (usekeys)
			this.keysToFilters();
		oSet.update();
		oSet.close();
		this.clearFields();

		notifyUpdateOK();
	}

	public void notifyDeleteOK() throws Exception {
		this.notifyEvent("DeleteOk");
	}

	public void notifyInsertOK() throws Exception {
		this.notifyEvent("InsertOk");
	}

	public void notifyUpdateOK() throws Exception {
		this.notifyEvent("UpdateOk");
	}

	@Override
	public void notifyDropOK() throws Exception {
		this.notifyEvent("DropOk");
	}

	// public void UpdateEstablish() throws Exception {
	// update(true);
	// }

	public void fieldsToStructure() throws Exception {
		fieldsToStructure(false);
	}

	public void fieldsToStructure(boolean zbEstablished) throws Exception {
		JMap<String, JProperty> map = this.getFixedProperties();
		if (map == null)
			return;
		JIterator<JProperty> iter = map.getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty prop = iter.nextElement();
			if (!prop.isTableBased())
				continue;
			if (prop.ifForeign())
				continue;
			JObject<?> obj = this.getProp(prop.GetCampo());
			if (obj == null)
				JExcepcion.SendError("No existe el campo variable:"
						+ prop.GetCampo());
			if (zbEstablished && !obj.isEstablecida())
				continue;
//			PssLogger.logInfo("CAMPO: ---->"+prop.GetCampo());
			this.addField(prop.GetCampo(), obj, prop.ifAutonumerico(),
					prop.ifForeign());
		}
	}

	public boolean hasAutonumerico() throws Exception {
		JMap<String, JProperty> map = this.getFixedProperties();
		if (map == null)
			return false;
		JIterator<JProperty> iter = map.getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty prop = iter.nextElement();
			if (prop.ifAutonumerico())
				return true;
		}
		return false;
	}

	private String getIdentityField() throws Exception {
		return mIdentityFields.getElement(this.getClass().getName());
	}

	private void assignIdentityField() throws Exception {
		String fld;
		if ((fld = getIdentityField()) == null)
			return;
		JObject<?> obj = this.getProp(fld);
		obj.setValue(this.getIdentity(fld));
	}

	public JList<String> GetDesignFields() throws Exception {
		JList<String> v = JCollectionFactory.createList();
		JMap<String, JProperty> oFProp = this.getFixedProperties();
		Iterator<JProperty> oEnum = oFProp.valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			v.addElement(oProp.GetCampo() + " = " + "getReportProp("+ oProp.GetCampo().trim() + ")");
		}
		return v;
	}

	public String getPropAsString(String zCampo) throws Exception {
		return getProp(zCampo).toString();
	}
	public String getPropAsStringNoExec(String zCampo) throws Exception {
		JObject obj = getProp(zCampo,false);
		if (obj==null) return "";
		return obj.toString();
	}
	public Object getReportProp(Object zCampo) throws Exception {
		return getProp(zCampo.toString()).asObject();
	}

	public JProperty getFixedProp(String campo, boolean exc) throws Exception {
		return JRecord.getFixedProp(this.getClass(), campo, exc);
	}
	public JProperty getFixedProp(String campo) throws Exception {
		return JRecord.getFixedProp(this.getClass(), campo);
	}
	public JProperty getFixedPropNoExeption(String campo) throws Exception {
		return JRecord.getFixedProp(this.getClass(), campo,false);
	}


	public static JProperty getFixedProp(Class clase, String campo) throws Exception {
		return getFixedProp(clase,campo,true);
	}
	public static JProperty getFixedProp(Class clase, String campo, boolean exception)	throws Exception {
		int dot = campo.indexOf('.');
		if (dot != -1)
			return JRecord.getFixedProp(clase, campo.substring(0, dot),exception);
		JProperty prop = JRecord.getFixedPropertyMap(clase).getElement(extractFieldName(campo.toLowerCase()));
		if (prop == null) {
			if (exception)
				JExcepcion.SendError("Campo inexistente: ^" + campo + " - "					+ clase.getName());
			else
				return null;
		}
		return prop;
		}
	public JProperty findFirstFixedPropByClass(Class clase,boolean exception)	throws Exception {
		JIterator<JProperty> it =  JRecord.getFixedPropertyMap(this.getClass()).getValueIterator();
		while (it.hasMoreElements()) {
			JProperty prop = it.nextElement(); 
			if (prop.getClase()==null) continue;
			if (prop.getClase().equals(clase)) return prop;
		}
		if (exception)
			JExcepcion.SendError("Campo inexistente con clase: ^" + clase + " - "					+ this.getClass());
		
		return null;
	}

	public JObject<?> getProp(String campo) throws Exception {
		return getProp(campo, true);
	}
	public JObject<?> getProp(String campo, boolean exception) throws Exception {
		return getProp(campo, exception, true);
	}
	
	public JObject<?> getPropRaw(String campo) throws Exception {
		return this.getProp(campo, true, false);
	}

	public JObject<?> getProp(String campo, boolean exception, boolean soloRaiz) throws Exception {
		if (soloRaiz) {
			int dot = campo.indexOf('.');
			if (dot != -1)	return this.getProp(campo.substring(0, dot), exception);
		}
		if (!this.hasProperties())
			return null;
		JObject obj = this.getProperties().getElement(extractFieldName(campo.toLowerCase()));
		if (obj == null && exception)
			JExcepcion.SendError("No puedo encontrar la propiedad '" + campo +"' en '" + this.getClass().getName() + "'");
		if (obj != null)
			obj.setFilter(extractFilterName(campo));
		return obj;
	}

	public JProperty getFixedPropDeep(String campo) throws Exception {
		return getFixedPropDeep(campo,true);
	}
	public JProperty getFixedPropDeep(String campo, boolean exception) throws Exception {
		JProperty prop = !exception?this.getFixedPropNoExeption(campo):this.getFixedProp(campo);
		if (prop==null) return null;
		if (!prop.isBaseRecord())
			return prop;
		int dot = campo.indexOf('.');
		if (dot == -1)
			return prop;
		JRecord record = (JRecord) prop.getClase().newInstance();
		JProperty deep = record.getFixedPropDeep(campo.substring(dot + 1),exception);
		if (deep==null) return null;
		JProperty clone = deep.createClone();
		clone.setCampo(campo);
		return clone;
	}

	public JObject<?> getPropDeep(String campo, boolean exception) throws Exception {
		JObject obj = this.getProp(campo, exception);
		if (obj == null)
			return null;
		if (obj.isRecords()) {
			if (campo.indexOf('.') ==-1) return obj;
			JRecords recs =((JObjBDs)obj).getValue();
			if (recs==null) return null;
			String id = ((JObjBDs)obj).getFilter();
			long iid = id!=null||JTools.isNumber(id)?JTools.getLongNumberEmbedded(id):0;
			JIterator<JRecord> it = recs.getStaticIterator();
			while (it.hasMoreElements()) {
				JRecord r = it.nextElement();
				if (r == null)	return null;
				if (iid>0) {
					iid--;
					continue;
				}
				return r.getPropDeep(campo.substring(campo.indexOf('.') + 1),exception);
			}
			return null;
		}
		else if (!obj.isRecord())
			return obj;
		if (campo.indexOf('.') == -1)
			return obj;
		JRecord record = (JRecord) ((JObjBD) obj).getValue();
		if (record == null)
			return null;
		return record.getPropDeep(campo.substring(campo.indexOf('.') + 1),exception);
	}
	public JObject<?> getPropDeepOnlyRef(String campo) throws Exception {
		return getPropDeepOnlyRef(campo,true);
	}
	public JObject<?> getPropDeepOnlyRef(String campo, boolean exception) throws Exception {
		int dot = campo.lastIndexOf('.');
		if (dot == -1)	{
			JObject prop = this.getProp(campo, exception);
			return JObjectFactory.virtualCreate(prop.getObjectType());
		}
		String path = campo.substring(0,dot);
		String field = campo.substring(dot+1);
		JProperty fprop= this.getFixedPropDeep(path,exception);
		if (fprop==null) return null;
		JRecord record = (JRecord) fprop.getClase().newInstance();
		return record.getProp(field,exception);
	}
	public JObject<?> getPropDeep(String campo) throws Exception {
		return getPropDeep(campo, true);
	}


	public Class getPropType(String campo) throws Exception {
		JObject obj = this.getProp(campo);
		if (obj.isRecord()) {
			JRecord rec = (JRecord) this.getFixedProp(campo).getClase().newInstance();
			return rec.getPropType(campo.substring(campo.indexOf('.') + 1));
		}
		return obj.getObjectClass();
	}

	static private String extractFieldName(String campo) throws Exception {
		int posI = campo.indexOf("[");
		if (posI == -1)
			return campo;
		return campo.substring(0, posI);
	}

	static private String extractFilterName(String campo) throws Exception {
		int posI = campo.indexOf("[");
		if (posI == -1)
			return null;
		int posF = campo.indexOf("]", posI + 1);
		return campo.substring(posI + 1, posF);
	}

	public boolean existProperty(String zCampo) throws Exception {
		if (this.getProperties() == null)
			return false;
		return this.getProperties().getElement(
				extractFieldName(zCampo.toLowerCase().trim())) != null;
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc,
			boolean zNoAutonum, boolean zRequerido, int zLongitud,
			Bindings bind, String formula) throws Exception {
		return addFixedItem(zTipo, zCampo, zDesc, zNoAutonum, zRequerido,
				zLongitud, 0, "", "");
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc,
			boolean zNoAutonum, boolean zRequerido, int zLongitud)
			throws Exception {
		return addFixedItem(zTipo, zCampo, zDesc, zNoAutonum, zRequerido,
				zLongitud, 0, "", "");
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc,
			boolean zNoAutonum, boolean zRequerido, int zLongitud,
			int zPrecision) throws Exception {
		return addFixedItem(zTipo, zCampo, zDesc, zNoAutonum, zRequerido,
				zLongitud, zPrecision, "", "");
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc,
			boolean zNoAutonum, boolean zRequerido, int zLongitud,
			int zPrecision, String zFormat) throws Exception {
		return addFixedItem(zTipo, zCampo, zDesc, zNoAutonum, zRequerido,
				zLongitud, zPrecision, zFormat, "");
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc,
			boolean zNoAutonum, boolean zRequerido, int zLongitud,
			int zPrecision, String zFormat, String zDefault) throws Exception {
		return addFixedItem(zTipo, zCampo, zDesc, zNoAutonum, zRequerido,
				zLongitud, zPrecision, zFormat, zDefault, "");
	}

	private void verifyAutonumeric(boolean noautonum, int type, String field)
			throws Exception {
		if (noautonum)
			return;
		if (type != KEY)
			return;
		// Autonumerico
		if (mIdentityFields.containsKey(this.getClass().getName()))
			return;
		mIdentityFields.addElement(this.getClass().getName(), field);
	}

	public JProperty addFixedItem(int zTipo, String zCampo, String zDesc, boolean noautonum, boolean zRequerido, int zLongitud, int zPrecision, String zFormat, String zDefault, String zTable) throws Exception {
		JProperty prop = new JProperty(zTipo, zCampo, zDesc, null, "", noautonum, zRequerido, zLongitud, zPrecision, zFormat, zDefault, zTable);
		return this.addFixedItem(prop);
	}	
	
	public JProperty addFixedItem(JProperty prop) throws Exception {
		this.verifyAutonumeric(!prop.isAutonumerico(), prop.getTipo(), prop.GetCampo());
		if (this.hasToCachedFixedProperties())
			JRecord.addFixedPropertyMap(this.getClass(), prop.GetCampo().toLowerCase(), prop);
		// if (hFixedProperties == null) hFixedProperties =
		// JCollectionFactory.createOrderedMap(32);
		// hFixedProperties.addElement(zCampo.toLowerCase(), oProp);
		return prop;
	}

	public JObject<?> addItem(String zCampo, JObject<?> zDato) throws Exception {
		if (hProperties == null)
			hProperties = JCollectionFactory.createOrderedMap(15);
		hProperties.addElement(zCampo.toLowerCase(), zDato);
		return zDato;
	}

	public void execProcessUpdate() throws Exception {
		JExec oExec = new JExec(this, "processUpdate") {

			@Override
			public void Do() throws Exception {
				processUpdate();
			}
		};
		oExec.execute();
	}

	public void execProcessInsert() throws Exception {
		JExec oExec = new JExec(this, "processInsert") {

			@Override
			public void Do() throws Exception {
				processInsert();
			}
		};
		oExec.execute();
	}

	public void execIfNeededprocessInsert() throws Exception {
		if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress()) {
			this.processInsert();
		} else {
			JExec oExec = new JExec(this, "processInsert") {

				@Override
				public void Do() throws Exception {
					processInsert();
				}
			};
			oExec.execute();
		}
	}

	public void execProcessUpdateOrInsertWithCheck() throws Exception {
		JExec oExec = new JExec(this, "processUpdateOrInsertWithCheck") {

			@Override
			public void Do() throws Exception {
				processUpdateOrInsertWithCheck();
			}
		};
		oExec.execute();
	}

	public void execProcessDelete() throws Exception {
		JExec oExec = new JExec(this, "processDelete") {

			@Override
			public void Do() throws Exception {
				processDelete();
			}
		};
		oExec.execute();
	}

	public void execProcessUpdateOrInsert() throws Exception {
		JExec oExec = new JExec(this, null) {
			public void Do() throws Exception {
				processUpdateOrInsert();
			}
		};
		oExec.execute();
	}

	public void processUpdateOrInsert() throws Exception {
		if (this.wasRead())
			this.processUpdate();
		else
			this.processInsert();

	}

	
	public void processUpdateOrInsertWithCheck() throws Exception {
		if (this.checkRecordExists())
			this.processUpdate();
		else
			this.processInsert();

	}

	public void processInsert() throws Exception {
		insertRecord();
		this.cleanModifiedOnServer();
	}

	public void processUpdate() throws Exception {
		JRecord oBD = getPreInstance();
		if (oBD.copyUpdatedProperties(this)) {
			oBD.updateRecord();
		} else {
			PssLogger.logDebug("nothing to update in " + oBD.GetTable());
		}
		this.clearPersistantObjects();
		this.cleanModifiedOnServer();
	}

	public void clearPersistantObjects()throws Exception {
	}

	public void processDelete() throws Exception {
		deleteRecord();
	}
	
	public void processUpdateOrInsertWithCheckTable() throws Exception {
		if (this.checkRecordExists())
			this.processUpdateTable();
		else
			this.processInsertTable();
	}
	
	public void processDeleteTable() throws Exception {
		deleteRecord();
	}
	public void processInsertTable() throws Exception {
		insertRecord();
	}

	public void processUpdateTable() throws Exception {
		JRecord oBD = getPreInstance();
		if (oBD.copyUpdatedProperties(this)) {
			oBD.updateRecord();
		} else {
			PssLogger.logDebug("nothing to update in " + oBD.GetTable());
		}
		this.clearPersistantObjects();
	}
		
	public void insertRecord() throws Exception {
		validateRecord();
		if (this.getIdentityField() == null) {
			if (checkRecordExists())
				JDuplicatedException.RaiseError();
		}
		this.insert();
		this.assignIdentityField();
		this.onPostGrabarRegistro();
	}

	public void validateRecord() throws Exception {
		validateSizeConstraints();
		validateConstraints();
		validateNullsConstraints();
	}

	public void updateRecord() throws Exception {
		validateRecord();
		this.update();
		this.onPostGrabarRegistro();
		// if(staticTables.getElement(getClass()) != null)initStaticTable( true,getClass() );
	}

	public boolean deleteRecord() throws Exception {
		if (isTableBased() && !checkRecordExists()) {
			if (this.withoutException())
				return false;
			JExcepcion.SendError(this.GetMensajeDelete());
		}

		if (!this.isViolateIntegrity())
			validateParentConstraints();

		this.delete();
		this.onPostBorrarRegistro();
		// if(staticTables.getElement(getClass()) != null)initStaticTable( true,getClass() );
		return true;
	}

	protected String GetMensajeDelete() throws Exception {
		return "Registro Inexistente en la tabla^: " + this.GetTable();
	}

	public void validateParentConstraints() throws Exception {
	}

	public boolean checkRecordExists() throws Exception {
		if (!isTableBased()) {
			return false;
		}
		JRecord oBD = this.getClass().newInstance();
		oBD.keysToFilters(this);
		oBD.dontThrowException(true);
		return oBD.exists();
	}

	public void validateNullsConstraints() throws Exception {
		JProperty prop = null;
		if (this.getFixedProperties() == null)
			return;
		JIterator<JProperty> iter = this.getFixedProperties().getValueIterator();
		while (iter.hasMoreElements()) {
			prop = iter.nextElement();
			if (prop.ifForeign())
				continue;
			if (!prop.isRequire())
				continue;
			if (prop.isDontCheck())
				continue;
			if (!prop.isTableBased())
				continue;
			if (!this.getProp(prop.GetCampo()).hasValue())
				JExcepcion.SendError(JLanguage.translate("Campo Requerido")+":"	+ JLanguage.translate(prop.GetDescripcion()));
		}
	}
	public void cleanModifiedOnServer() throws Exception {
		JProperty prop = null; 
		JProperty fixed;
		if (this.getFixedProperties() == null)
			return;
		JIterator<JProperty> iter = this.getFixedProperties().getValueIterator();
		while (iter.hasMoreElements()) {
			fixed = iter.nextElement();
			JObject obj = this.getProp(fixed.GetCampo(), false);
			if (obj==null) continue;
			obj.setModifiedOnServer(false);
		}
	}
	private void validateSizeConstraints() throws Exception {
		if (!this.isValidateConstraints())
			return;
		JMap<String, JProperty> oFProp = this.getFixedProperties();
		Iterator<JProperty> oEnum = oFProp.valueIterator();
		while (oEnum.hasNext()) {
			JProperty prop = oEnum.next();
			if (!prop.isTableBased())
				continue;
			if (prop.ifForeign())
				continue;
			prop.validateSize(getProp(prop.GetCampo()));
		}
	}

	public void validateConstraints() throws Exception {
	}

	public void onPostGrabarRegistro() throws Exception {
	}

	public void onPostBorrarRegistro() throws Exception {
	}

	private JBaseRegistro createRecordset() throws Exception {
		JBaseRegistro oSet = JBaseRegistro.recordsetFactory(this);
		return oSet;
	}

	public long getIdentity(String zCampo) throws Exception {
		JBaseRegistro oSet = createRecordset();
		long id = oSet.GetIdentity(zCampo);
		// PssLogger.logDebug("ID=" + id);
		return id;
	}

	public long selectCount() throws Exception {
		JBaseRegistro oSet = createRecordset();
		long lCount = oSet.selectCount();
		oSet.close();
		return lCount;
	}

	public String selectSum(String zCampo) throws Exception {
		JBaseRegistro oSet = createRecordset();
		String sSum = oSet.selectSum(zCampo);
		if (sSum == null)
			sSum = "0";
		oSet.close();
		return sSum;
	}

	public long SelectSumLong(String zCampo) throws Exception {
		return new Long(this.selectSum(zCampo)).longValue();
	}

	public double SelectSumDouble(String zCampo) throws Exception {
		return new Double(this.selectSum(zCampo)).doubleValue();
	}

	public Object selectObjectAvg(String zCampo, boolean isNumber)
			throws Exception {
		JBaseRegistro oSet = createRecordset();
		Object sMax = oSet.selectObjectAvg(zCampo, isNumber);
		oSet.close();
		return sMax;
	}

	public Object selectObjectMax(String zCampo, boolean isNumber)
			throws Exception {
		JBaseRegistro oSet = createRecordset();
		Object sMax = oSet.selectObjectMax(zCampo, isNumber);
		oSet.close();
		return sMax;
	}

	public Object selectObjectMin(String zCampo) throws Exception {
		JBaseRegistro oSet = createRecordset();
		Object sMax = oSet.selectObjectMin(zCampo);
		oSet.close();
		return sMax;
	}

	public String SelectMax(String zCampo, boolean isNumber) throws Exception {
		return (String) selectObjectMax(zCampo, isNumber);
	}

	public String SelectMin(String zCampo) throws Exception {
		return (String) selectObjectMin(zCampo);
	}

	public long SelectMaxLong(String zCampo) throws Exception {
		Object obj = selectObjectMax(zCampo, true);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).longValue();
		return ((Long) obj).longValue();
	}

	public long SelectMaxLongFromString(String zCampo) throws Exception {
		BigDecimal oVal2 = (BigDecimal) selectObjectMax(zCampo, false);
		if (oVal2 == null)
			return 0;
		return oVal2.longValue();
	}

	public int SelectMaxIntFromString(String zCampo) throws Exception {
		BigDecimal oVal2 = (BigDecimal) selectObjectMax(zCampo, false);
		if (oVal2 == null)
			return 0;
		return oVal2.intValue();
	}

	public long SelectMinLong(String zCampo) throws Exception {
		Object obj = selectObjectMin(zCampo);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).longValue();
		return ((Long) obj).longValue();
	}

	public int SelectMinInt(String zCampo) throws Exception {
		Object obj = selectObjectMin(zCampo);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).intValue();
		return ((Long) obj).intValue();
	}

	public int SelectMaxInt(String zCampo) throws Exception {
		Object obj = selectObjectMax(zCampo, true);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).intValue();
		return ((Integer) obj).intValue();
	}

	public double SelectMaxDouble(String zCampo) throws Exception {
		Object obj = selectObjectMax(zCampo, true);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).doubleValue();
		return ((Double) obj).doubleValue();
	}

	public double SelectAvgDouble(String zCampo) throws Exception {
		Object obj = selectObjectAvg(zCampo, true);
		if (obj == null)
			return 0;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).doubleValue();
		return ((Double) obj).doubleValue();
	}

	public Date SelectMaxDate(String zCampo) throws Exception {
		return (Date) selectObjectMax(zCampo, true);
	}

	public Date SelectMinDate(String zCampo) throws Exception {
		return (Date) selectObjectMin(zCampo);
	}

	public Date SelectMaxDateTime(String zCampo) throws Exception {
		return (Date) selectObjectMax(zCampo, true);
	}

	public JRecord getPreInstance() throws Exception {
		return this.getPreInstance(true);
	}

	public JRecord getPreInstance(boolean bConException) throws Exception {
		JRecord oBD = this.getClass().newInstance();
		if (!oBD.isTableBased())
			return this;
		oBD.keysToFilters(this);
		oBD.dontThrowException(true);
		oBD.lock(true);
		boolean bOk = oBD.read();
		oBD.lock(false);
		if (!bOk)
			if (bConException)
				JExcepcion.SendError("Registro Inexistente en la tabla^: "
						+ this.GetTable());
			else
				return null;
		return oBD;
	}

	public boolean copyUpdatedProperties(JRecord zBD) throws Exception {
		boolean status = false;
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isTableBased())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			if (zBD.getForzeAllRecordsToUpdate()) {

				if (!oPropOrig.toString().equals(oPropDest.toString())) {
					oPropDest.setEstablecida(true);
					status = true;
				}
				if (oPropOrig.isNull())
					oPropDest.setNull();
				else
					oPropDest.setValue(oPropOrig.asRawObject());

			} else {
				if (oPropOrig.isEstablecida()) {
					boolean undo = false;
					if (oPropOrig.toString().equals(oPropDest.toString()))
						undo = true;
					if (oPropOrig.isNull())
						oPropDest.setNull();
					else
						oPropDest.setValue(oPropOrig.asRawObject());

					oPropDest.setEstablecida(oPropOrig.isEstablecida());

					if (undo)
						oPropDest.unsetEstablish();
					else
						status = true;
				} else {
					oPropDest.unsetEstablish();
				}
			}
		}
		this.setDatosLeidos(zBD.wasRead());
		this.setStatic(zBD.isStatic());
		return status;
	}

	// public void copyProperties(JRecord zBD, boolean onlydifferences) throws
	// Exception {
	// copyProperties(zBD, onlydifferences, false);
	// }

	// public void copyProperties(JRecord zBD, boolean onlydifferences, boolean
	// log) throws Exception {
	// Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
	// if (log)
	// PssLogger.logDebug("------------------------------------------------------------------------------"
	// );
	// while (oEnum.hasNext()) {
	// JProperty oProp = oEnum.next();
	// if (!oProp.isTableBased())
	// continue;
	// JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
	// JObject<?> oPropDest = this.getProp(oProp.GetCampo());
	//
	// if (oPropDest != null) {
	// if (oPropOrig.isNull()) {
	// oPropDest.setNull();
	// } else {
	// if (onlydifferences) {
	// if (log)
	// PssLogger.logDebug("orig (" + oPropOrig + ") = dest (" + oPropDest + ")"
	// );
	// if (oPropDest.equals(oPropOrig) == false) {
	// oPropDest.setValue(oPropOrig.asRawObject());
	// }
	// } else {
	// oPropDest.setValue(oPropOrig.asRawObject());
	// oPropDest.setEstablecida(oPropOrig.isEstablecida());
	// }
	// }
	// }
	// }
	// if (log)
	// PssLogger.logDebug("------------------------------------------------------------------------------"
	// );
	// this.setDatosLeidos(zBD.wasRead());
	// this.setStatic(zBD.isStatic());
	// }

	public void copyProperties(JRecord zBD) throws Exception {
		this.copyProperties(zBD, true);
	}

	public void copyCommonsProperties(JRecord zBD, boolean tableBased)
			throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (tableBased && !oProp.isTableBased())
				continue;
			if (!this.existProperty(oProp.GetCampo()))
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			if (oPropDest != null) {
				if (oPropOrig.isNull())
					oPropDest.setNull();
				else
					oPropDest.setValue(oPropOrig.asRawObject());

				oPropDest.setEstablecida(oPropOrig.isEstablecida());
			}
		}
//		this.setDatosLeidos(zBD.wasRead());
		this.setStatic(zBD.isStatic());
	}
	
	public boolean copyProperties2(JRecord zBD, boolean onlydifferences) throws Exception {
		return copyProperties2(zBD, onlydifferences, false);
	}
	
	public boolean copyProperties2(JRecord zBD, boolean onlydifferences, boolean log) throws Exception {
		boolean ret = false;
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		if (log) 
			  PssLogger.logDebug("------------------------------------------------------------------------------" );
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isTableBased())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			// if (oPropOrig.isEstablecida()) {
			// boolean undo = false;
			// if ( oPropOrig.toString().equals(oPropDest.toString()) )
			// undo = true;
			if (oPropDest != null) {
				if (oPropOrig.isNull()) {
					oPropDest.setNull();
				} else {
					if (onlydifferences) {
						if (log) 
						  PssLogger.logDebug("orig (" + oPropOrig + ") = dest (" + oPropDest + ")" );
						if (oPropDest.equals(oPropOrig) == false) {
							oPropDest.setValue(oPropOrig.asRawObject());
							ret = true;
						}
					} else {
						oPropDest.setValue(oPropOrig.asRawObject());
						oPropDest.setEstablecida(oPropOrig.isEstablecida());
					}
				}
			}
			// if ( undo )
			// oPropDest.unsetEstablish();
			// else
			// status=true;
			// } else {
			// oPropDest.unsetEstablish();
			// }
		}
		if (log) 
			  PssLogger.logDebug("------------------------------------------------------------------------------" );
		this.setDatosLeidos(zBD.wasRead());
		this.setStatic(zBD.isStatic());
		return ret;
		// return status;
	}
	
	public void copyProperties(JRecord zBD, boolean tableBased) throws Exception {
		copyProperties(zBD, tableBased, true);
  }
	public void copyProperties(JRecord zBD, boolean tableBased, boolean autonum) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (tableBased && !oProp.isTableBased() && !oProp.isRecords() && !oProp.isRecord())
				continue;
			if (!autonum && oProp.isAutonumerico())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			if (oProp.isRecords() || oProp.isRecord()) {
				if (oPropDest != null) {
					oPropDest.setValue(oPropOrig.asRawObject());
					oPropDest.setEstablecida(oPropOrig.isEstablecida());
				}
			
			} else {
				if (oPropDest != null) {
					if (oPropOrig.isNull())
						oPropDest.setNull();
					else
						oPropDest.setValue(oPropOrig.asRawObject());

					oPropDest.setEstablecida(oPropOrig.isEstablecida());
				}
			}
		}
//		this.setDatosLeidos(zBD.wasRead());
		this.setStatic(zBD.isStatic());
	}
	public void copyKeysProperties(JRecord zBD) throws Exception {
		copyKeysProperties(zBD,true);
	}
	
	public void copyKeysProperties(JRecord zBD,boolean excepcion) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())
				continue;
			JObject<?> oPropOrig = zBD.getPropDeep(oProp.GetCampo(),excepcion);
			if (oPropOrig==null)
				continue;
			JObject<?> oPropDest = getProp(oProp.GetCampo(),excepcion);
			if (oPropDest==null)
				continue;
			oPropDest.setValue(oPropOrig.asRawObject());
		}
	}

	public void copyNotKeysProperties(JRecord zBD) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (oProp.isKey())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = getProp(oProp.GetCampo());
			oPropDest.setValue(oPropOrig.asRawObject());
		}
	}
	public void copyNotAutonumProperties(JRecord zBD) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (oProp.isAutonumerico())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = getProp(oProp.GetCampo());
			oPropDest.setValue(oPropOrig.asRawObject());
		}
	}
	public void copyCommonsNotKeysProperties(JRecord zBD, boolean tableBased)
			throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (oProp.isKey())
				continue;
			if (tableBased && !oProp.isTableBased())
				continue;
			if (!this.existProperty(oProp.GetCampo()))
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			if (oPropDest != null) {
				if (oPropOrig.isNull())
					oPropDest.setNull();
				else
					oPropDest.setValue(oPropOrig.asRawObject());

			//	oPropDest.setEstablecida(oPropOrig.isEstablecida());
			}
		}
		this.setDatosLeidos(zBD.wasRead());
		this.setStatic(zBD.isStatic());
	}


	public void keysToFilters(JRecord zBD) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())
				continue;
			this.addFilter(oProp.GetCampo(), zBD.getProp(oProp.GetCampo()));
		}
	}
	public String  getKeyList() throws Exception {
		String out="";
		Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())	continue;
			out += (out.equals("")?"":";")+oProp.GetCampo();
		}
		return out;
	}

	public String  getKeyListValue() throws Exception {
		String out="";
		Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())	continue;
			out += (out.equals("")?"":";")+getPropAsString(oProp.GetCampo());
		}
		return out;
	}

		
	public boolean  hasKeyValue() throws Exception {
		Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())	continue;
			if (getPropAsString(oProp.GetCampo()).equals(""))
				return false;
		}
		return true;
	}
	// public String getFirstKey() throws Exception {
	// JIterator<JProperty> iter = this.getFixedProperties().getKeyIterator();
	// while (iter.hasMoreElements()) {
	// JProperty key = iter.nextElement();
	// if (key.isKey())
	// return key;
	// }
	// return null;
	// }
	//
	public void keysToFilters() throws Exception {
		Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isKey())
				continue;
			this.setFilterValue(oProp.GetCampo(), getProp(oProp.GetCampo()).toString(),oProp.getType());
		}
	}

	public boolean isEqual(JRecord zBD) throws Exception {
		if (!this.getClass().equals(zBD.getClass()))
			return false;
		Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp1 = oEnum.next();
			JProperty oProp2 = zBD.getFixedProp(oProp1.GetCampo());
			if (oProp1.isKey()) {
				if (oProp2 == null)
					return false;
				if (oProp2.isKey() == false)
					return false;
				if (!getProp(oProp1.GetCampo()).equals(
						zBD.getProp(oProp1.GetCampo())))
					return false;
			}

		}
		return true;
	}

	@Override
	public Element serialize(Element zRoot) throws Exception {
		return serializeContent("row", zRoot);
	}

	public Element serializeContent(String tag, Element zRoot, boolean all)
			throws Exception {
		Element row = zRoot.getOwnerDocument().createElement(tag);
		JMap<String, JProperty> oFProp = this.getFixedProperties();
		Iterator<JProperty> oEnum = oFProp.valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!all && !oProp.serializeSupported())
				continue;
			row.setAttribute(oProp.GetCampo(), getProp(oProp.GetCampo()).toString());
		}
		return row;
	}

	public Element serializeContent(String tag, Element zRoot) throws Exception {
		return serializeContent(tag, zRoot, false);
	}

	@Override
	public void unSerializeRoot(Element zRoot) throws Exception {
		Element eRow = (Element) zRoot.getElementsByTagName("row").item(0);
		this.DeserializarElement(eRow);
	}

	public void DeserializarElement(Element zRow) throws Exception {
		// String Valor, Campo;

		NamedNodeMap oProps = zRow.getAttributes();
		int len = oProps.getLength();
		for (int i = 0; i < len; i++) {
			String sProp = oProps.item(i).getNodeName();
			String sValor = zRow.getAttribute(sProp);
			this.getProp(sProp).setValue(sValor);
	//		PssLogger.logInfo("----->"+sProp+"="+sValor);
		}
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Object other) {
		int iCompRc;
		try {
			if (other == null) {
				return 0;
			}

			JRecord OtherJBD = (JRecord) other;
			String szCriteria = this.OnSort();

			if (szCriteria.compareTo(OtherJBD.OnSort()) != 0)
				return 0;
			boolean asc =true;
			StringTokenizer st = new StringTokenizer(szCriteria, ";");

			while (st.hasMoreTokens()) {
				asc=true;
				String szPropiedad = st.nextToken();
				if (szPropiedad.startsWith("-")) {
					asc=false;
					szPropiedad=szPropiedad.substring(1);
				}
				Object a = this.getPropDeep(szPropiedad).asObject();
				Object b = OtherJBD.getPropDeep(szPropiedad).asObject();
				if (a==null && b==null) return 0;
				if (a==null && b!=null) return -1;
				if (a!=null && b==null) return 1;
				if (a instanceof Comparable) {
					if (a instanceof String)
						iCompRc = ((String) a).compareToIgnoreCase((String) b);
					else
						iCompRc = ((Comparable<Object>) a).compareTo(b);
					if (iCompRc != 0) {
						if (asc)
							return iCompRc;
						else
							return iCompRc*-1;
					}
				} else
					return 0;// index - otherRow.index;
			}
		} catch (Exception E) {
			return 0;
		}
		return 0;
	}

	public String OnSort() throws Exception {
		String szOnSortRc = "";

		if (this.szSortCriteria != null) {
			szOnSortRc = szSortCriteria;
		} else {
			JProperty oProp;
			Iterator<JProperty> oEnum = this.getFixedProperties().valueIterator();
			while (oEnum.hasNext()) {
				oProp = oEnum.next();
				if (oProp.isKey()) {
					szOnSortRc = szOnSortRc + oProp.GetCampo() + ";";
				}
			}
		}
		return szOnSortRc;
	}

	public void EnviarMjeAlta(String zNodo) throws Exception {
		JExcepcion.SendError("Metodo sin Implementar");
	}

	public boolean ifTodosTienenValor() throws Exception {
		JMap<String, JProperty> oFProp = this.getFixedProperties();
		Iterator<JProperty> oEnum = oFProp.valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isTableBased())
				continue;
			if (!getProp(oProp.GetCampo()).hasValue())
				return false;
		}
		return true;
	}

	public String TipoMje() {
		return "";
	}

	public void ProcessEvent(JRecord zBD, String zMetodo) throws Exception {
	}

	public void PropagarEjecucion(String zMetodo, JSetupPropagate zSetup)
			throws Exception {
		if (this.getSetupPropagate(zSetup)==null || !this.getSetupPropagate(zSetup).isPropagate())
			return;
		BizPropagarMje oPropagarMje = new BizPropagarMje();
		oPropagarMje.SetOrigen(BizUsuario.getUsr().getNodo());
		oPropagarMje.SetObjBD(this);
		oPropagarMje.SetMetodo(zMetodo);
		oPropagarMje.SetPropagateRule(zSetup);
		oPropagarMje.processInsert();
	}

	public JSetupPropagate doSetupPropagate(JSetupPropagate zSetup)
			throws Exception {
		return zSetup;
	}

	public JSetupPropagate createSetupPropagate() throws Exception {
		if (BizUsuario.getUsr()==null) return null;
		if (BizUsuario.getUsr().getObjNodo()==null) return null;
		return BizUsuario.getUsr().getObjNodo().getPropagateModel(GetTable());
	}

	public JSetupPropagate getSetupPropagate(JSetupPropagate zSetup)
			throws Exception {
		if (zSetup == null)
			return doSetupPropagate(createSetupPropagate());
		return zSetup;
	}

	public boolean hasProperties() throws Exception {
		return this.getProperties() != null;
	}
	public void cleanProperties() throws Exception {
		hProperties = null;
		createProperties();
	}

	public JMap<String, JObject<?>> getProperties() throws Exception {
		if (hProperties != null)
			return hProperties;
		this.createProperties();
		return hProperties;
	}

	public void createProperties() throws Exception {
	}

	/**
	 * <p>
	 * Crea los indices secundarios logicos.
	 * </p>
	 * <p>
	 * La mecanica de creacion de indices secundarios es la siguiente
	 * sobreescribir este metodo en la clase que hereda de JRecord e implementar
	 * de la siguiente forma:
	 * </p>
	 * <p>
	 * Por cada indice que se quiera crear hay que llamar a uno de los
	 * siguientes metodos:
	 * <li>{@link #addUniqueIndex(String) addUniqueIndex}</li>
	 * <li>{@link #addIndex(String) addIndex}</li>
	 * <br>
	 * que retorna un indice logico representado con la clase
	 * {@link pss.common.dbManagement.synchro.JDBClassIndex JVirtualIndex}.
	 * Luego, usando el objeto retornado agregar los campos que conforman el
	 * indice usando el metodo
	 * {@link pss.common.dbManagement.synchro.JDBClassIndex#addField addField}
	 * </p>
	 * 
	 * @throws Exception
	 */
	// public void createIndexes() throws Exception {
	// }

	/**
	 * Crea un indice secundario logico con la particularidad de ser unico.
	 * 
	 * @param name
	 *            Nombre del indice
	 * @return Objeto {@link pss.common.dbManagement.synchro.JDBClassIndex
	 *         JVirtualIndex} que representa un indice logico.
	 * @throws Exception
	 *             Ante un error en la creacion del indice
	 * 
	 */
	protected JDBClassIndex addUniqueIndex(String name) throws Exception {
		return addIndexInternal(name, true);
	}

	/**
	 * Crea un indice secundario logico.
	 * 
	 * @param name
	 *            Nombre del indice
	 * @return Objeto {@link pss.common.dbManagement.synchro.JDBClassIndex
	 *         JVirtualIndex} que representa un indice logico.
	 * @throws Exception
	 * 
	 */
	protected JDBClassIndex addIndex(String name) throws Exception {
		return addIndexInternal(name, false);
	}

	/**
	 * Crea un indice logico y lo almacena
	 * 
	 * @param name
	 *            Nombre del indice
	 * @param unique
	 *            Indica si el indice es unico o no
	 * @return Objeto {@link pss.common.dbManagement.synchro.JDBClassIndex
	 *         JVirtualIndex} que representa un indice logico.
	 * @throws Exception
	 *             Error durante la creacion del indice
	 */
	protected JDBClassIndex addIndexInternal(String name, boolean unique)
			throws Exception {
		checkForIndexesMap();

		JDBClassIndex i = new JDBClassIndex();
		i.setIndexName(name);
		i.setIndexIsUnique(unique);
		i.setIndexIsPrimaryKey(false);
		i.setIndexIsClustered(false);
		this.hIndexes.addVirtualIndex(i);
		return i;
	}

	/**
	 * Retorna instancia de la clase que maneja los indices relacionados con la
	 * tabla
	 * 
	 * @return Instancia de la clase
	 *         {@link pss.common.dbManagement.synchro.JDBClassIndexes
	 *         JVirtualIndexes}
	 */
	public JDBClassIndexes getMyIndexes() {
		checkForIndexesMap();
		return hIndexes;
	}

	/**
	 * Chequea que se haya creado la clase que maneja los indices relacionados
	 * con la tabla
	 */
	private void checkForIndexesMap() {
		if (hIndexes == null) {
			hIndexes = new JDBClassIndexes();
		}
	}

	public static JMap<String, JProperty> getFixedPropertyMap(Class clase)
			throws Exception {
		if (aPropClass == null)
			aPropClass = JCollectionFactory.createOrderedMap();
		JMap<String, JProperty> map = aPropClass.getElement(clase.getName());
		if (map != null)
			return map ;
		return createFixedPropertyMap(clase);
	}

	public static synchronized JMap<String, JProperty> createFixedPropertyMap(Class clase) throws Exception {
		if (aPropClass == null)
			aPropClass = JCollectionFactory.createOrderedMap(32);
		JMap<String, JProperty> map = aPropClass.getElement(clase.getName());
		if (map != null)
			return map;
		map = JCollectionFactory.createOrderedMap(20);
		aPropClass.addElement(clase.getName(), map);
		JRecord record = (JRecord) clase.newInstance();
		record.createFixedProperties();
		return map;
	}

	// public static JMap<String, JProperty> addFixedPropertyMap(String clase)
	// throws Exception {
	// if (aPropClass == null) aPropClass = JCollectionFactory.createMap(32);
	// return aPropClass.getElement(clase);
	// }
	public JMap<String,String> getExtraData() {
		if (extraData!=null) return extraData;
		return extraData= JCollectionFactory.createOrderedMap();
	}
	public void addExtraData(String key,String value) {
		if (this.getExtraData().getElement(key)!=null)
			getExtraData().removeElement(key);
		getExtraData().addElement(key, value);
	}

	public String getExtraData(String key) {
		return getExtraData().getElement(key);
	}
	public boolean hasExtraData() {
		return extraData!=null && getExtraData().size()>0;
	}
	
	
	public static void addFixedPropertyMap(Class clase, String campo,
			JProperty prop) throws Exception {
		JMap<String, JProperty> map = JRecord.getFixedPropertyMap(clase);
		map.addElement(campo, prop);
		// return JRecord.addFixedPropertyMap(clase, prop);
	}

	public JMap<String, JProperty> getFixedProperties() throws Exception {
		return JRecord.getFixedPropertyMap(this.getClass());
		// if (map != null) return map;
		// this.createFixedProperties();
		// // if (hFixedProperties == null)
		// // return null;
		// // this.createIndexes();
		// if (this.hasToCachedFixedProperties())
		// aPropClass.addElement(sNewClass, hFixedProperties);
		// return this.getFixedProperties();
	}

	protected boolean hasToCachedFixedProperties() throws Exception {
		return true;
	}

	public static void removeFixedProp(String zClassName) {
		aPropClass.removeElement(zClassName);
	}

	public void createFixedProperties() throws Exception {
		flags = (byte) (flags ^ ~FLAG_FIXED_PROPERTIES_ARE_DEFINED);
	}
	public void createControlProperties() throws Exception {
//	flags = (byte) (flags ^ ~FLAG_CONTROL_PROPERTIES_ARE_DEFINED);
	}
	public boolean hasControlsProperties() {
		return getControlsProperties().size()>0;
	}

	public synchronized void getSynchoControlsProperties() {
		if (hControlProperties != null)
			return;
		try {
			hControlProperties = JCollectionFactory.createMap();
			createControlProperties();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	public JMap<String, IControl> getControlsProperties() {
		if (hControlProperties == null)
			getSynchoControlsProperties();

		return hControlProperties;
	}

	public void setControlsProperties(JMap<String, IControl> controls) {
		this.hControlProperties = controls;
	}
	public IControl getControl(String field)  throws Exception {
		if (!hasControlsProperties()) return null;
		return this.getControlsProperties().getElement(field);
	}
	public void addControlsProperty(String field ,IControl control)  throws Exception {
		control.setProperty(getFixedProp(field));
		this.getControlsProperties().addElement(field, control);
	}
	
	public static BizVirtual virtualBD(String zValor, String zDescrip, int zIcono) throws Exception {
		BizVirtual oVirtual = new BizVirtual();
		oVirtual.setValor(zValor);
		oVirtual.setDescripcion(JLanguage.translate(zDescrip));
		oVirtual.setIcono(zIcono);
		return oVirtual;
	}
	public static BizVirtual virtualBD(String zValor, String zDescrip,
			String zIcono) throws Exception {
		BizVirtual oVirtual = new BizVirtual();
		oVirtual.setValor(zValor);
		oVirtual.setDescripcion(JLanguage.translate(zDescrip));
		oVirtual.setIconoString(zIcono);
		return oVirtual;
	}
	public static boolean compareFilters(RFilter zFiltro, JRecord zSet)
			throws Exception {
		boolean bRc = false;
		String sTipo = zFiltro.getType();

		// Comparaciones de strings
		if (sTipo.equals(JObject.JSTRING) || sTipo.equals(JObject.JPASSWORD)
				|| sTipo.equals(JObject.JDATE)
				|| sTipo.equals(JObject.JMULTIPLE)
				|| sTipo.equals(JObject.JBOOLEAN)
				|| sTipo.equals(JObject.JGEOPOSITION)) {
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.getPropAsString(zFiltro.getField()).equals(
						zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.getPropAsString(zFiltro.getField()).equals(
						zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.getPropAsString(zFiltro.getField())
						.compareToIgnoreCase(zFiltro.getValue()) <= 0);
		} else if (sTipo.equals(JObject.JINTEGER)
				|| sTipo.equals(JObject.JLONG) || sTipo.equals(JObject.JFLOAT)) {
			if (zFiltro.getOperator().equalsIgnoreCase("="))
				bRc = zSet.getProp(zFiltro.getField()).equals(
						zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase("<>"))
				bRc = !zSet.getProp(zFiltro.getField()).equals(
						zFiltro.getValue());
			else if (zFiltro.getOperator().equalsIgnoreCase(">"))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) > 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<"))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) < 0);
			else if (zFiltro.getOperator().equalsIgnoreCase(">="))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("<="))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) <= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=>"))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) >= 0);
			else if (zFiltro.getOperator().equalsIgnoreCase("=<"))
				bRc = (zSet.getProp(zFiltro.getField()).compareTo(
						new JString(zFiltro.getValue())) <= 0);
		}

		return bRc;

	}

	@SuppressWarnings("unchecked")
	public JRecords<? extends JRecord> getPreConfiguredData() throws Exception {
		if (this.getClass().getName()
				.equals("pss.sj.documentos.tipos.BizDocFisicoTipo"))
			PssLogger.logDebug("punto de log");
		URL data = JBaseDBManagement.class.getResource("data/"
				+ BizPssConfig.getPssConfig().getInitializedData() + "/"
				+ this.getClass().getName() + ".xml");
		if (data == null)
			return null;
		String file = data.getFile();
		if (file == null)
			return null;
		String contents = JTools.readFileAsString(file);
		if (contents == null || contents.equals(""))
			return null;
		JRecords<JRecord> oBDs = new JRecords(this.getClass());
		oBDs.unSerialize(contents);
		return oBDs;
	}

	public void setupConfiguration(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(false);
		zParams.setExportData(false);
		zParams.setExportSQL("select * from " + GetTable());
		zParams.setPurgeCondition(null);
		zParams.setHasPurgeCondition(false);
		zParams.getHFunctions().removeAllElements();
		zParams.getHDropFunctions().removeAllElements();
		zParams.getHPreCookFunctions().removeAllElements();
		zParams.getHPostModifyColumn().removeAllElements();
		setupConfig(zParams);
	}

	protected void setupConfig(JSetupParameters zParams) throws Exception {
	}

	@SuppressWarnings("unchecked")
	public void doPurgetion(BizDepuration oDep) throws Exception {
		while (true) {
			if (Thread.currentThread().isInterrupted())
				return;
			JRecords<JRecord> oBDs = new JRecords(this.getClass());
			this.addFilterForPurge(oBDs, oDep.maxDate());
			oBDs.readAll();
			oBDs.firstRecord();
			int iRowsToCommit = 0;
			while (oBDs.nextRecord()) {
				if (Thread.currentThread().isInterrupted())
					return;
				JRecord oBD = oBDs.getRecord();
				oBD.setViolateIntegrity(this.isViolateIntegrity());
				oBD.processDelete();
				Thread.sleep(50);
				iRowsToCommit++;
				if (iRowsToCommit >= 1000) {
					break;
				}
			}
			if (iRowsToCommit < 1000) {
				break;
			}
			JBDatos.GetBases().commit();
			JBDatos.GetBases().beginTransaction();
			Thread.sleep(500);
		}
	}

	protected void addFilterForPurge(JRecords<? extends JRecord> oBDs, Date date)
			throws Exception {
		oBDs.addFilter(this.getDateFieldForPurge(), date, "<=");
	}

	public String getDateFieldForPurge() throws Exception {
		JExcepcion.SendError("Debe configurar campo fecha para depuraciï¿½n");
		return null;
	}

	public boolean readByKeys() throws Exception {
		this.clearFilters();
		this.keysToFilters();
		return this.read();
	}

	public boolean hasReferenceTo(JRecord zBD) throws Exception {
		return false;
	}

	protected double getSqlValue(String sQuery, String sCampo) throws Exception {
  	if (sQuery.indexOf("VIRTUAL:")!=-1) {
  		int posI= sQuery.indexOf("VIRTUAL:");
  		String contents = sQuery.substring(posI+"VIRTUAL:".length());
  		int pos=contents.indexOf("|");
  		int pos2=contents.indexOf("|",pos+1);
  		String clase = contents.substring(0,pos);
  		String filtros = contents.substring(pos+1,pos2);
  		String campo = contents.substring(pos2+1);
  		
  		JRecord rec=(JRecord)Class.forName(clase).newInstance();
			JStringTokenizer t = JCollectionFactory.createStringTokenizer(filtros, ':');
			while (t.hasMoreTokens()) {
				String c = t.nextToken();
				int pos3 = c.indexOf("=");
				String var = c.substring(0,pos3);
				String val = c.substring(pos3+1);
				rec.addFilter(var, val);
			}
			rec.dontThrowException(true);
  		if (!rec.read())return 0;
  		if (rec.getProp(campo).toString().equals("")) return 0;
   	  if (!JTools.isNumber(rec.getProp(campo).toString(), true)) return 0;
  		return Double.parseDouble(rec.getProp(campo).toString());
  	}
  	JList<String> vFields = JCollectionFactory.createList();
		vFields.addElement(sCampo);
		JMap<String, ?> oFields = createRecordset().ExecuteQueryOneRow(vFields,sQuery);
		if ( oFields==null || oFields.getElement(sCampo)==null) return 0;
		return Double.valueOf((String) oFields.getElement(sCampo)).doubleValue();
	}

	protected boolean ifExistsSql(String sQuery) throws Exception {
		return createRecordset().ExecuteQueryLookForExistence(sQuery);
	}

	public String getObjectDescription() throws Exception {
		return "";
	}

	@SuppressWarnings("unchecked")
	public JRecords<? extends JRecord> getCollectionClass() throws Exception {
		return new JRecords(this.getClass());
	}

	// public void setIgnoreForeignFields(boolean zValue) {
	// bIgnoreForeignFields=zValue;
	// }
	@SuppressWarnings("unchecked")
	public void procesarDeleteTablaRelacionada(String zClase, String zCampo,
			String zFiltro, boolean zTruncate) throws Exception {
		JRecords<?> oJBDs = null;
		try {
			oJBDs = new JRecords(Class.forName(zClase));
		} catch (ClassNotFoundException e) {
			return;
		}
		oJBDs.addFilter(zCampo, zFiltro);
		oJBDs.readAll();
		if (zTruncate)
			oJBDs.delete();
		else
			oJBDs.processDeleteAll();
	}

	public String getTableForReport() throws Exception {
		return this.getStructure().getTable();
	}

	public void AddFixedPropertiesBase() throws Exception {
	}

	// public boolean hasKeyFilters() throws Exception {
	// JIterator iter = this.getFixedProperties().getKeyIterator();
	// while (iter.hasMoreElements()) {
	// JProperty prop = (JProperty) iter.nextElement();
	// // if (!prop.ifPassword()) continue;
	// if (this.getFilterValue(prop.GetCampo())==null) return false;
	// }
	// return true;
	// }

	public void filtersToProp() throws Exception {
		JList<?> filters = this.getFilters();
		if (filters == null)
			return;
		JIterator<?> oIter = filters.getIterator();
		while (oIter.hasMoreElements()) {
			RFilter filter = (RFilter) oIter.nextElement();
			JObject<?> obj = this.getProp(filter.getField());
			if (obj == null)
				JExcepcion.SendError("Campo inexistente en el join: " + filter.getField());
			obj.setValue(filter.getValue());
		}
	}

	public JRecord createDefaultClone() throws Exception {
		JRecord clone = this.getClass().newInstance();
		clone.copyProperties(this);
		return clone;
	}
	public JRecord createCopyClone() throws Exception {
		JRecord clone = this.getClass().newInstance();
		clone.copyProperties(this,true,false);
		return clone;
	}

	@Override
	public String getTableByFilter(String filter) throws Exception {
		if (filter.equals("")) 
			return null;
		if (filter.equals("COUNT")) 
			return this.GetTable();
		if (filter.equals("EXTERN")) 
			return this.GetTable();
		JProperty fixedProp = this.getFixedPropDeep(filter.toLowerCase(),false);
		if (fixedProp == null)
			return null;
		String table = fixedProp.GetTabla();
		if (table != null && !table.equals(""))
			return table;
//		if (filter.indexOf(".") != -1 && !fixedProp.isVirtual())
//			return null; // campo con la tabla incluida
		return this.GetTable();
	}

	public boolean wasInvokeBy(Class<?> clase) throws Exception {
		return this.wasInvokeBy(this.getParent(), clase);
	}

	private boolean wasInvokeBy(JBaseRecord parent, Class<?> clase)
			throws Exception {
		if (parent == null)
			return false;
		if (parent.getClass().isAssignableFrom(clase))
			return true;
		return this.wasInvokeBy(parent.getParent(), clase);
	}

	public boolean isDiscardFilter(RFilter filter) throws Exception {
		if (!this.existProperty(filter.getField()))
			return true;
		if (!filter.getOperator().equals("="))
			return true;
		if (filter.isDynamic())
			return true;
		JProperty p = this.getFixedProp(filter.getField());
		if (p.isVirtual())
			return true;
		if (p.ifForeign())
			return true;
		return false;
	}

	public void forceFilterToData() throws Exception {
		if (!this.getStructure().hasFilters())
			return;
		JIterator<RFilter> iter = this.getFilters().getIterator();
		while (iter.hasMoreElements()) {
			RFilter filter = iter.nextElement();
			if (filter.isDynamic())
				continue;
			if (filter.getValue().equals("null"))
				continue;
			JObject prop = this.getProp(filter.getField());
			if (prop == null)
				continue;// JExcepcion.SendError("no existe la propiedad: ^"+filter.
							// getField());
			if (prop.isRecord())
				prop.setValue(filter.getObjValue());
			else
				prop.setValue(filter.getValue());

		}
	}

	public String getRecordName() throws Exception {
		return this.GetTable();
	}

	public String getDescripField() throws Exception {
		return null;
	}
	
	public String getKeyField() throws Exception {
		return null;
	}


	public String getDescripFieldValue() throws Exception {
		if (this.getDescripField() == null)
			return null;
		JObject<?> p = this.getProp(this.getDescripField());
		if (p == null) {
			JExcepcion.SendError("No puedo encontrara la propiedad '"
					+ this.getDescripField() + "' en "
					+ this.getClass().getName());
		}
		return p.toFormattedString();
	}

	public JRelations getRelationMap() throws Exception {
		if (this.relations != null)
			return this.relations;
		JRelations rels = new JRelations(this.getClass());
		rels.addRelationParent(0, "",/*this.getRecordName(),*/ this.getClass(), null);
		this.attachRelationMap(rels);
		return (this.relations = rels);
	}

	public void attachRelationMap(JRelations rels) throws Exception {
	}

	
	
	public void setForzeAllRecordsToUpdate(Boolean zValue) {
		this.bForzeAllRecordsUpdate = zValue;
	}

	public Boolean getForzeAllRecordsToUpdate() {
		return this.bForzeAllRecordsUpdate;
	}

	public void refreshStaticData() throws Exception {
	}

	// @Override
	// public String toString() {
	// String out= this.getClass().getCanonicalName()+"| ";
	// try {
	// JMap<String, JProperty> oFProp = this.getFixedProperties();
	// Iterator<JProperty> oEnum = oFProp.valueIterator();
	// while (oEnum.hasNext()) {
	// JProperty oProp = oEnum.next();
	// if (!oProp.isKey()) continue;
	// out += oProp.GetCampo() + "=" + getProp(oProp.GetCampo()).toString() +
	// "|";
	// }
	// } catch (Exception e) {
	//
	// }
	// return out;
	// }

	public void execProcessInsertWithNewConnection() throws Exception {
		JExec oExec = new JExec(this, "processInsert") {

			@Override
			public void Do() throws Exception {
				processInsert();
			}
		};
		oExec.executeWithNewConnection();
	}

	public String getClassTitle() throws Exception {
		return this.getClass().getSimpleName();
	}

	public String getObjectType(String campo) throws Exception {
		return getObjectType(campo,true);
	}
	
	public String getObjectType(String campo,boolean excep) throws Exception {
		JObject<?> obj = this.getProp(campo,excep);
		if (obj == null && excep)
			JExcepcion.SendError("Property not found: " + campo);
		if (obj == null && !excep)
			return null;
		if (!obj.isRecord())
			return obj.getObjectType();
		if (campo.indexOf('.')==-1)
			return obj.getObjectType();
		JRecord r = (JRecord) this.getFixedProp(campo).getClase().newInstance();
		return r.getObjectType(campo.substring(campo.indexOf('.') + 1),excep);
	}


	public List<JProperty> calculeDiff(JRecord zBD) throws Exception {
		List<JProperty> lista = new ArrayList<JProperty>();
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (!oProp.isTableBased())
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
			JObject<?> oPropDest = this.getProp(oProp.GetCampo());

			if (!oPropOrig.toString().equals(oPropDest.toString())) {
				lista.add(oProp);
			}
		}
		return lista;
	}

	public boolean isSameRecord(JRecord otro) throws Exception {
		if (!this.getClass().getName().equals(otro.getClass().getName()))
			return false;
		JIterator<JProperty> iter = this.getFixedProperties()
				.getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty prop = iter.nextElement();
			if (!prop.isKey())
				continue;
			if (!this.getProp(prop.GetCampo()).toString()
					.equals(otro.getProp(prop.GetCampo()).toString()))
				return false;
		}
		return true;
	}

	public String visualizaInLayout(String campo) throws Exception {
		return "";
	}
	
  public String visualizaInEditor(String campo) throws Exception {
  	return visualizaInLayout(campo);
  }

	public String getGrupo(String idgrupo,String campo) throws Exception {
		return "";
	}

	public boolean isSelectedInGrid() throws Exception {
		if (JFormRow.isSelected(getExtraData().getElement("internal_select_fila"))) return true;
		return false;
	}
	public boolean isBorradoInGrid() throws Exception {
		if (JFormRow.isDelete(getExtraData().getElement("internal_estado_fila"))) return true;
		if (!detectIsComplete()) return true;
		return false;
	}
	public boolean detectIsComplete() throws Exception {
		 try {
			validateRecord();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return false;
		
	}
	public boolean isNewInGrid() throws Exception {
		if (JFormRow.isNew(getExtraData().getElement("internal_estado_fila")) || JFormRow.isLast(getExtraData().getElement("internal_estado_fila"))) {
			return !detectIsComplete();
		} 
		return false;
	}
  public JMap<String, String> getAllFixedProperties(boolean incudeRecords) throws Exception {
 		return getAllFixedProperties( incudeRecords,false,true);
  }
  public JMap<String, String> getAllFixedProperties(boolean incudeRecords, boolean includeHide, boolean includeRecord) throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
 		this.appendFields(map, this, "", "", incudeRecords,includeHide,includeRecord);
 		return map;
  }


  private void appendFields(JMap<String, String> map, JRecord record, String pref, String dpref, boolean includeRecords, boolean includeHide,boolean includeRecord) throws Exception {
	  JIterator<JProperty> iter = record.getFixedProperties().getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty p = iter.nextElement();
			if (p.isRecords() && includeRecords) {
				continue;
			}
			if (p.isHide()&&!includeHide)
				continue;
			if (p.isRecord()) {
				continue;
			}
//			map.addElement((fixProp==null?"":fixProp.GetCampo()+".")+p.GetCampo(), (fixProp==null?"":fixProp.GetDescripcion()+".")+p.GetDescripcion());
			String descr =record.visualizaInLayout(p.GetCampo());
			if (descr!=null) {
		//		PssLogger.logInfo(pref+p.GetCampo());
				if (descr.equals("")) map.addElement(pref+p.GetCampo(), dpref+p.GetDescripcion());
				else map.addElement(pref+p.GetCampo(), dpref+descr);
			}
		}
	  iter = record.getFixedProperties().getValueIterator();
		while (iter.hasMoreElements()) {
			JProperty p = iter.nextElement();
			if (p.isHide()&&!includeHide)
				continue;
			if (p.isRecords() && includeRecords) {
				if (map.containsKey(p.GetCampo()))
					continue;
				String descr =record.visualizaInLayout(p.GetCampo());
				if (descr!=null) 
					if (descr.equals("")) map.addElement( pref+p.GetCampo(),  "Zona "+dpref+p.GetDescripcion());
					else map.addElement( pref+p.GetCampo(), "Zona "+dpref+descr);
				continue;
			}
			if (p.isRecord()) {
//				if (map.containsKey(p.GetCampo())) return;
				Class clase = p.getClase();
				if (clase==null) JExcepcion.SendError("Debe deifinir la clase para el record: "+p.GetCampo());
	  		JRecord subrec = (JRecord)clase.newInstance();
				String descr =record.visualizaInLayout(p.GetCampo());
				if (descr==null) continue;
				if (descr.equals("")) descr =  p.GetDescripcion();
				if (includeRecord)
					map.addElement(p.GetCampo(), descr);
				this.appendFields(map, subrec, pref+p.GetCampo()+".", dpref+descr+".", includeRecords,includeHide,includeRecord);
				continue;
			}
		}
	}

	@Override
	public String toString() {
		try {
			return getClassTitle()+"["+getKeyListValue() +"]"+getDescripFieldValue();
		} catch (Exception e) {
			return "Error";
		}
	}

  
  
  public IControl createControlWin( final JWins zWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlWin() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = zWins.getRecords().isStatic()?zWins:zWins.createClone();//zWins.getRecords().isStatic()
     		wins.getRecords().clearFilters(); 
     		wins.getRecords().fieldsToFilters(wins.getExtraFilter());
     		if (extraFilter!=null) 
     			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
     		
     		if (getRecord()!=null) {
//       		wins.getRecords().joinWithOthers(getRecord(),field);
       		wins.getRecords().joinWithOthers(getRecord(),field);
       		wins.SetEstatico(false);//wins.getRecords().GetTable().equals(""));
        	if (bOneRow && field!=null) {
        		JObject obj =  getRecord().getProp(getProperty().GetCampo());
        		if ( obj instanceof JObjBD ) {
        			wins.getRecords().addFilter(field, ((JObjBD) obj).getValue());
        		}else {
        			wins.getRecords().addFilter(field, obj.toString());
        			
        		}
        	}
      	}
      	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return zWins.getWinRef().getClass().newInstance();
      }

     };
    
  }
  public IControl createControlCombo(final  JWins zWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlCombo() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = zWins.getRecords().isStatic()?zWins:zWins.createClone();//zWins.getRecords().isStatic()
     		if (getRecord()!=null) {
       		if (extraFilter!=null) 
       			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
       		wins.getRecords().joinWithOthers(getRecord(),field);
       		if (bOneRow && field!=null) {
        		wins.getRecords().addFilter(field, getRecord().getProp(getProperty().GetCampo()).toString());
        	}    			
     		}
 //     	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return zWins.getWinRef().getClass().newInstance();
      }
     };
    
  }
  public IControl createControlTree( final JWins zWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlTree() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = zWins.getRecords().isStatic()?zWins:zWins.createClone();//zWins.getRecords().isStatic()
    		if (getRecord()!=null) {
       		if (extraFilter!=null) 
       			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
	     		wins.getRecords().joinWithOthers( getRecord(),field);
	     		if (bOneRow) {
	      		wins.getRecords().addFilter(field, getRecord().getProp(getProperty().GetCampo()).toString());
	      	}
    		}
      	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return zWins.getWinRef().getClass().newInstance();
      }
     };
    
  }
  public IControl createControlWin( final Class zClassWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlWin() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = (JWins)zClassWins.newInstance();
     		wins.getRecords().clearFilters(); 
     		wins.getRecords().fieldsToFilters(wins.getExtraFilter());
     		if (extraFilter!=null) 
     			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
     		
     		if (getRecord()!=null) {
//       		wins.getRecords().joinWithOthers(getRecord(),field);
       		wins.getRecords().joinWithOthers(getRecord(),field);
       		wins.SetEstatico(false);//wins.getRecords().GetTable().equals(""));
        	if (bOneRow && field!=null) {
        		JObject obj =  getRecord().getProp(getProperty().GetCampo());
        		if ( obj instanceof JObjBD ) {
        			wins.getRecords().addFilter(field, ((JObjBD) obj).getValue());
        		}else {
        			wins.getRecords().addFilter(field, obj.toString());
        			
        		}
        	}
      	}
      	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return ((JWins)zClassWins.newInstance()).getWinRef().getClass().newInstance();
      }

     };
    
  }
  public IControl createControlCombo(final  Class zClassWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlCombo() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = (JWins)zClassWins.newInstance();
     		if (getRecord()!=null) {
       		if (extraFilter!=null) 
       			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
       		wins.getRecords().joinWithOthers(getRecord(),field);
       		if (bOneRow && field!=null) {
        		wins.getRecords().addFilter(field, getRecord().getProp(getProperty().GetCampo()).toString());
        	}    			
     		}
 //     	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return ((JWins)zClassWins.newInstance()).getWinRef().getClass().newInstance();
      }
     };
    
  }
  public IControl createControlTree( final Class zClassWins,final String field, final JPair<String,String> extraFilter) throws Exception {
  	return new JControlTree() {
     	@Override
     	public JWins getRecords(boolean bOneRow) throws Exception {
     		JWins wins = (JWins)zClassWins.newInstance();
    		if (getRecord()!=null) {
       		if (extraFilter!=null) 
       			addFilters(wins,getRecord(),extraFilter.firstObject(),extraFilter.secondObject());
       		wins.getRecords().joinWithOthers( getRecord(),field);
	     		if (bOneRow) {
	      		wins.getRecords().addFilter(field, getRecord().getProp(getProperty().GetCampo()).toString());
	      	}
    		}
      	wins.setModeWinLov(true);
      	return wins;
     	}
      public JWin buildWin() throws Exception { 
      	return ((JWins)zClassWins.newInstance()).getWinRef().getClass().newInstance();
      }
     };
    
  }
  public void addFilters(JWins wins,JRecord record,String first,String second) throws Exception {
		StringTokenizer fstToks = new StringTokenizer(first, ";,");
		StringTokenizer sndToks = new StringTokenizer(second, ";,");
		while (fstToks.hasMoreElements()) {
			String fsttok = fstToks.nextToken();
			String sndtok = sndToks.nextToken();
			wins.getRecords().addFilter(fsttok, record.getProp(sndtok).toString());
		}
  }
  public boolean checkFilter(BizAction a) throws Exception {
  	if (a==null) return true;
  	if (!a.hasFilterMap()) return true;
  	String value; JObject prop;
  	JIterator<String> iter = a.getFilterMap().getMap().getKeyIterator();
  	while (iter.hasMoreElements()) {
  		String key = iter.nextElement();
  		prop = this.getProp(key, false);
  		if (prop==null) continue;
  		value=a.getFilterMapValue(key, "");
  		// filter like
  		if (prop.toString().toLowerCase().contains(value.toLowerCase())) 
  			return true;
  	}
  	return false;
  }
  
  JRecord attach;
	public JRecord getAttach() {
		return attach;
	}

	public void setAttach(JRecord attach) {
		this.attach = attach;
	}
  

}
