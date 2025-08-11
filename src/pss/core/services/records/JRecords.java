package pss.core.services.records;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.JExec;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.winUI.controls.JFormControl;

/**
 * Conjunto de {@link JRecord}. Provee operaciones para manejar colecciones de
 * registros, su lectura paginada y filtros sobre la fuente de datos.
 *
 * @param <TRecord>
 *            tipo de registro gestionado.
 */
public class JRecords<TRecord extends JRecord> extends JBaseRecord {

	// --------------------------------------------------------------------------
	// //
	// Propiedades publicas
	// --------------------------------------------------------------------------
	// //
	protected int curr_row;
	protected JList<TRecord> vItems;
	protected transient JList<TRecord> vItemsRemove;
	protected transient JBaseRegistro pRSet;
	protected JMap<String, JMap<String, TRecord>> hMultiHash;
	protected JMap<String, TRecord> hHash;
	protected Class recordClass;
	protected TRecord recordRef = null;
	protected long rowstoread = -1;
	protected long offset = -1;
	protected long pagesize = -1;
	protected boolean distinct = false;
	protected boolean withUse = false;
	protected long maxNumberRowsPerPage = -1;
	private String sSearchKey = null;
	// private boolean fixed=false;
	boolean fieldBased;
	JObject propAsoc;

	public JObject getPropAsoc() {
		return propAsoc;
	}

	public void setPropAsoc(JObject propAsoc) {
		this.propAsoc = propAsoc;
	}

	public boolean isSQLBased() {
		return true;
	}

	public boolean isFieldBased() {
		return fieldBased;
	}

	public void setFieldBased(boolean fieldBased) {
		this.fieldBased = fieldBased;
	}

	public boolean isWithUse() {
		return withUse;
	}

	public void setWithUse(boolean withUse) {
		this.withUse = withUse;
	}

	/**
	 * @return the offset
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(long offset) {
		this.offset = offset;
	}

	/**
	 * @return the pagesize
	 */
	public long getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize
	 *            the pagesize to set
	 */
	public void setPagesize(long pagesize) {
		this.pagesize = pagesize;
	}

	public int GetCurrRow() throws Exception {
		return curr_row;
	}

	public void setTop(long val) {
		rowstoread = val;
	}

	public long getTop() {
		return rowstoread;
	}

	public void setDistinct(boolean zDist) {
		distinct = zDist;
	}
	public long getMaxNumberRowsPerPage() {
		return maxNumberRowsPerPage;
	}

	public void setMaxNumberRowsPerPage(long maxNumberRowsPerPage) {
		this.maxNumberRowsPerPage = maxNumberRowsPerPage;
	}

	// private boolean bIgnoreForeignFields=true;

	// --------------------------------------------------------------------------
	// //
	// Metodos de Propiedades
	// --------------------------------------------------------------------------
	// //
	public boolean isResultSetOpened() {
		if (pRSet != null)
			return true;

		return false;
	}

	// --------------------------------------------------------------------------
	// //
	// Constructor
	// --------------------------------------------------------------------------
	// //
	public JRecords() {
	}

//	@SuppressWarnings("unchecked")
//	public JRecords(String recordClassName) throws Exception {
//		this((Class<TRecord>) Class.forName(recordClassName));
//	}

	public JRecords(Class<TRecord> clase) throws Exception {
		this.recordClass = clase;
	}


	public void setRecordRef(Class<TRecord> clase) throws Exception {
		this.recordClass = clase;

	}

	public JRecords(TRecord record) throws Exception {
		this.recordRef = record;
		this.recordClass=record.getClass();
	}

	@Override
	public Class<? extends TRecord> getBasedClass() {
		return this.recordClass;
		//		if (this.recordRef == null)
//			return null;
//		return (Class<? extends TRecord>) this.recordRef.getClass();
	}

	@Override
	public String GetTable() throws Exception {
		return this.getRecordRef().GetTable();
	}
	
	@Override
	public String GetTableTemporal() throws Exception {
		return this.getRecordRef().GetTableTemporal();
	}
	
	
	/*
	 * private void initialize(Class zClassBD) throws Exception { try { //
	 * this.setBasedClass(zClassBD); this.recordRef =
	 * (JRecord)zClassBD.newInstance(); //
	 * this.getStructure().setTable(oBD.getStructure().getTable()); //
	 * this.setStatic(oBD.isStatic()); } catch (Exception e) {
	 * JExcepcion.SendError("Error en la creación del JDBs^", e.getMessage()); }
	 * }
	 */

	public boolean readAll() throws Exception {
		if (this.isStatic()|| !isSQLBased()) {
			this.firstRecord();
			return true;
		}
		this.closeRecord();
		this.setTableTemporal(this.GetTableTemporal());
		pRSet = JBaseRegistro.recordsetFactory(this);
		pRSet.setPageSize(this.pagesize);
		pRSet.setOffset(this.offset);
		pRSet.setWithUse(this.withUse);
		pRSet.setTop(this.rowstoread);
		pRSet.setDistinct(this.distinct);
		pRSet.openCursor();
		return true;
	}

	public void firstRecord() throws Exception {
		if (this.isStatic() || !isSQLBased()) {
			curr_row = -1;
		}
	}

	public boolean nextRecord() throws Exception {

		if (this.isStatic() || !isSQLBased()) {
			curr_row++;
			return !this.isEOF();
		}

		if (pRSet == null)
			return false;
		// JExcepcion.SendError("No se ha creado el Record Set");

		if (pRSet.next())
			return true;

		this.closeRecord();
		return false;
	}

	public TRecord getRecord() throws Exception {
		TRecord record;
		if (!isSQLBased()) {
			record = vItems.getElementAt(curr_row);
			record.SetVision(GetVision());
			record.setParent(this);
		}else if (this.isStatic() ) {
			record = vItems.getElementAt(curr_row);
			record.SetVision(GetVision());
		} else {
			record = this.createItem(pRSet);
			record.setParent(this);
			record.SetVision(GetVision());
			record.Read(pRSet, this.loadForeignFields() || getRecordRef().loadForeignFields());
		}
		return record;
	}

	public boolean hasCorteControl() throws Exception {
		return this.getStructure().hasCorteControl();
	}

	public void joinData(JRecord record) throws Exception {
		 record.setParent(this); // RJL, porque comentaron que el parent no se setea siempre?
		// record.joinWithRecords(this);
		if (this.getStructure().hasFilters()) {
			JIterator<RFilter> oIter = this.getFilters().getIterator();
			while (oIter.hasMoreElements()) {
				RFilter filter = (RFilter) oIter.nextElement();
				if (filter.isVirtual()) { // sirve para pasar datos como la vision
					JObject p = record.getProp(filter.getField(), false);
					if (p != null && !filter.getValue().equals("null")) {
						p.setValue(filter.getValue());
						record.setFilterValue(filter.getField(), filter.getValue(), filter.getType());
					}
				}
				if (record.isDiscardFilter(filter))
					continue;
				RFilter newFilter;
				if (filter.getObjValue() instanceof JRecord)
					newFilter = record.setFilterObjValue(filter.getField(), filter.getObjValue(), filter.getType());
				else 	if (filter.getObjValue() instanceof JRecords)
					newFilter = record.setFilterObjValue(filter.getField(), filter.getObjValue(), filter.getType());
				else
					newFilter = record.setFilterValue(filter.getField(), filter.getValue(), filter.getType());
				newFilter.setVirtual(filter.isVirtual());
			}
		}
		if (this.getStructure().hasCorteControl()) {
			JIterator<ROrderBy> oIter = this.getCorteControl().getIterator();
			while (oIter.hasMoreElements()) {
				ROrderBy corte = oIter.nextElement();
				record.addCorteControl(corte);
			}
		}
	}
	public void joinWithOthers(JRecord zBD,String fieldException) throws Exception {

	}

	public void joinWithOthersIfEquals(JRecord zBD,String fieldException) throws Exception {
		Iterator<JProperty> oEnum = zBD.getFixedProperties().valueIterator();
		JRecord record = this.createItem(pRSet);
		while (oEnum.hasNext()) {
			JProperty oProp = oEnum.next();
			if (fieldException!=null && fieldException.equals(oProp.GetCampo()))
				continue;
			if (!record.existProperty(oProp.GetCampo()))
				continue;
			JObject<?> oPropOrig = zBD.getProp(oProp.GetCampo());
	//		JObject<?> oPropDest = record.getProp(oProp.GetCampo());
	
			if (oPropOrig != null && !oPropOrig.toString().equals("")) {
				this.addFilter(oProp.GetCampo(),oPropOrig.toString());
			}
		}
}
	public void closeRecord() throws Exception {
		if (pRSet == null)
			return;
		pRSet.close();
		pRSet = null;
	}

	public boolean isEOF() throws Exception {
		if (this.isStatic()|| !isSQLBased()) {
			if (vItems == null)
				return true;
			return curr_row >= this.vItems.size();
		}
		if (pRSet == null)
			return true;
		return pRSet.EOF();
	}

	public JRecords<TRecord> toStatic() throws Exception {
		if (this.isStatic())
			return this;
		if (!this.isResultSetOpened())
			this.readAll();
		this.filledItems();
		this.setStatic(true);
		this.firstRecord();
		return this;
	}

	public void createList() {
		this.vItems = JCollectionFactory.createList();
		this.vItemsRemove = JCollectionFactory.createList();
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
	

	public void fieldsToFilters(Serializable zBD) throws Exception {

	}


	public void filledItems() throws Exception {
		this.createList();
		this.firstRecord();
		while (this.nextRecord()) {
			JRecord r = this.addItem(this.getRecord());
			Thread.yield();
		}
	}

	public JBaseRegistro RecordSet() throws Exception {
		return pRSet;
	}

	public void modified() throws Exception {
		if (getPropAsoc()==null) return;
		getPropAsoc().setModifiedOnServer(true);
	}
	// public final void OnReadAll() throws Exception {}

	public final void removeStaticItem(TRecord zBD) throws Exception {
		if (vItems == null)
			return;
		modified();
		vItems.removeElement(zBD);
		getStaticRemoveItems().addElement(zBD);
	}

	public TRecord createItem(JBaseRegistro zSet) throws Exception {
		TRecord rec = getBasedClass().newInstance();
		rec.getProperties();
		return rec;
	}

	public void deleteAll() throws Exception {
		modified();
		this.firstRecord();
		while (this.nextRecord()) {
			JRecord record = this.getRecord();
			record.delete();
		}
	}

	public void insertAll() throws Exception {
		modified();
		this.firstRecord();
		while (this.nextRecord()) {
			JRecord record = this.getRecord();
			record.insert();
		}
	}

	public void endStatic() throws Exception {
		this.setStatic(false);
		this.clearStaticItems();
	}

	public void clearStaticItems() {
		vItems = null;
		vItemsRemove = null;
		curr_row = -1;
	}

	public boolean ifRecordFound() throws Exception {
		if (this.isStatic()|| !isSQLBased()) {
			if (vItems == null)
				return false;
			return this.vItems.size() > 0;
		}
		return !pRSet.EOF();
	}

	public String selectMax(String zCampo) throws Exception {
		if (isStatic() == false && isSQLBased()) {
			JBaseRegistro RSet = JBaseRegistro.recordsetFactory(this);
			String sMax = RSet.SelectMax(zCampo);
			RSet.close();
			return sMax;
		}
		JObject max = null;
		firstRecord();
		while (nextRecord()) {
			JRecord rec = getRecord();
			JObject fl = rec.getProp(zCampo);
			if (max == null)
				max = fl;
			else if (max.compareTo(fl) < 0)
				max = fl;
		}
		return "" + max;
	}

	@SuppressWarnings("unchecked")
	public double selectSum(String zCampo) throws Exception {
		if (isStatic() == false && isSQLBased() && GetTable()!=null) {
			JBaseRegistro RSet = JBaseRegistro.recordsetFactory(this);
			String sSum = RSet.selectSum(zCampo);
			RSet.close();
			return Double.parseDouble(sSum);
		}

		double tot = 0.0F;
		firstRecord();
		while (nextRecord()) {
			JRecord rec = getRecord();
			JObject fl = rec.getProp(zCampo);
			if (fl instanceof JFloat)
				tot += ((JFloat) fl).getValue();
			else if (fl instanceof JLong)
				tot += ((JLong) fl).getValue();
		}
		return tot;
	}

	public long selectSupraCount() throws Exception {
		if (this.isStatic() && isSQLBased())
			return this.sizeStaticElements();
		JBaseRegistro base = JBaseRegistro.recordsetFactory(this);
		long lCount = base.selectSupraCount(this.getStructure().getLastSQL());
		base.close();
		return lCount;
	}

	public long selectCount() throws Exception {
		if (this.isStatic()) {
			return this.sizeStaticElements();
		}
		JBaseRegistro base = JBaseRegistro.recordsetFactory(this);
		long lCount = base.selectCount();
		base.close();
		return lCount;
	}

	public TRecord addItem(TRecord record) throws Exception {
		modified();
		return this.addItem(record, -1);
	}

	public TRecord addItem(TRecord record, int pos) throws Exception {
		modified();
		if (record == null)
			return null;
		if (this.isStatic() && !record.wasRead()) {
			record.setStatic(true);
		}
		record.setParent(this);
		if (pos == -1)
			this.getStaticItems().addElement(record);
		else
			this.getStaticItems().addElementAt(pos, record);
		
		return record;
	}

	// public void SuperReadAll() throws Exception {}

	@Override
	public Element serialize(Element zRoot) throws Exception {
		Element eTabla = zRoot.getOwnerDocument().createElement("tabla");
		eTabla.setAttribute("clase_base", this.getBasedClass().getName());
		eTabla.setAttribute("static", this.isStatic()?"S":"N");

		this.firstRecord();
		while (this.nextRecord()) {
			JRecord oBD = this.getRecord();
			eTabla.appendChild(oBD.serialize(eTabla));
		}
		return eTabla;
	}

	@Override
	public void unSerializeRoot(Element zRoot) throws Exception {
		Element eTabla = (Element) zRoot.getElementsByTagName("tabla").item(0);
		this.DeserializarElement(eTabla);
	}

	@SuppressWarnings("unchecked")
	public void DeserializarElement(Element zTabla) throws Exception {
		String sClase = zTabla.getAttribute("clase_base");
		String sStatic = zTabla.getAttribute("static");
		this.setRecordRef((Class<TRecord>)Class.forName(sClase));
		this.setStatic(sStatic.equals("S")?true:false);
		NodeList oRows = zTabla.getElementsByTagName("row");
		int len = oRows.getLength();
		for (int i = 0; i < len; i++) {
			Element oRow = (Element) oRows.item(i);
			TRecord oBD = (TRecord) Class.forName(sClase).newInstance();
			oBD.DeserializarElement(oRow);
			this.addItem(oBD);
		}
	}

	// Sintaxis criteria ([-]field;)* "campo1;-campo2" campo1 ascendente y luego
	// campo2 descendente
	public void pushCriteria(String sCriteria) {
		if (vItems == null) return;
		JIterator<TRecord> oJBD = vItems.getIterator();
		while (oJBD.hasMoreElements()) {
			oJBD.nextElement().szSortCriteria = sCriteria;
		}
	}

	public void Ordenar(String sCriteria) {
		if (sCriteria==null || sCriteria.equals("")) return;
		this.pushCriteria(sCriteria);
		if (vItems == null) return;
		vItems.sort();
	}

	public JRecords appendRecords(JRecords<TRecord> records) throws Exception {
		if (records == null)
			return this;
		records.firstRecord();
		while (records.nextRecord())
			this.addItem(records.getRecord());
		return this;
	}

	@Override
	public String toString() {
		try {
			return serialize();
		} catch (Exception e) {
			return "";
		}
	}

	public void truncate() throws Exception {
		JBaseRegistro oSet = JBaseRegistro.VirtualCreate(this);
		oSet.delete();
	}

	public void processDeleteAll() throws Exception {
		JIterator it = this.getStaticIterator();
		while (it.hasMoreElements()) {
			JRecord oBD = (JRecord) it.nextElement();
			oBD.setViolateIntegrity(this.isViolateIntegrity());
			oBD.dontThrowException(true);
			oBD.processDelete();
		}
	}
	
	// construye un objeto, para fillRecord, que es llamado desde el swapList, es para relaciones muchos a muchos,donde el parent es un lado, y origen el otro, las key son las que deben coindidir, el metodo debe construir el registro con los id a ambos lados 
	public TRecord buildNewRecord(JRecord parent,JRecord origen,String keyField,String keyFieldFuente,String keyFieldParent,String keyFieldFuenteParent) throws Exception {
		return buildNewRecord(parent, origen, keyField, keyFieldFuente, keyFieldParent, keyFieldFuenteParent,false);
	}
	public TRecord buildNewRecord(JRecord parent,JRecord origen,String keyField,String keyFieldFuente,String keyFieldParent,String keyFieldFuenteParent, boolean table) throws Exception {
		TRecord destino = getRecordRef();
		if (destino.getClass().isAssignableFrom((origen.getClass()))) {
			addItem((TRecord) origen);
			
			return (TRecord) origen;
		}
		destino.copyKeysProperties(parent,false);
		destino.getProp(keyField).setValue(origen.getPropDeep(keyFieldFuente).getObjectValue());
		if (keyFieldParent!=null) destino.getProp(keyFieldParent).setValue(origen.getPropDeep(keyFieldFuenteParent).getObjectValue());

		
		if (!table)
			addItem(destino);
		else
			destino.processUpdateOrInsertWithCheck();
		return destino;
	}
	public void processFillRecords(JRecord parent,JRecords fuentes, String keyFieldFuente, String keyField, String keyFieldFuenteParent, String keyFieldParent, String parentField) throws Exception {
		processFillRecords(parent, fuentes, keyFieldFuente, keyField, keyFieldFuenteParent, keyFieldParent, parentField, false);
	}
	public void processFillRecords(JRecord parent,JRecords fuentes, String keyFieldFuente, String keyField, String keyFieldFuenteParent, String keyFieldParent, String parentField,boolean tableBased) throws Exception {
	 	JMap<String,JRecord> map = fuentes.convertToHash(keyFieldFuente);
		
	 	JIterator<TRecord> it = this.getStaticIterator();
  	int fila=0;
  	while (it.hasMoreElements()) {
  		try {
				fila++;
				TRecord destino = it.nextElement();
				
				String key = destino.getPropDeep(keyField).toString();
				if (map.containsKey(key)) {
					map.removeElement(key);
					continue; // ya existe
				}
				//remover 
				if (tableBased)
					destino.processDelete();
				it.remove();
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA "+fila+": "+e.getMessage());
			}
  	}
  	// faltantes
  	JIterator<JRecord> itr = map.getValueIterator();
  	while (itr.hasMoreElements()) {
  		try {
				JRecord origen = (JRecord)itr.nextElement();
				TRecord destino = this.buildNewRecord(parent,origen,keyField,keyFieldFuente,keyFieldParent,keyFieldFuenteParent,tableBased);
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA "+fila+": "+e.getMessage());
			}
  	}
//		if (parent!=null)
//			parent.getProp(parentField).setValue(this);

	}

	public void processProcessTable(JRecord parent,boolean forceParentKeys) throws Exception {
  	JIterator<?> it = getStaticIterator();
  	int fila=0;
  	while (it.hasMoreElements()) {
  		try {
  			fila++;
				JRecord destino = (JRecord)it.nextElement();
				if (destino.isNewInGrid()) continue;
				if (forceParentKeys) destino.copyKeysProperties(parent);
				if (destino.isBorradoInGrid()) {
					destino.keysToFilters();
					if (destino.exists())
						destino.processDeleteTable();
				} else
					destino.processUpdateOrInsertWithCheckTable();
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA " + fila + ": " + e.getMessage());
			}
		}
		JIterator<?> itr = getStaticRemoveItems().getIterator();
		while (itr.hasMoreElements()) {
			try {
				JRecord destino = (JRecord) itr.nextElement();
				destino.keysToFilters();
				if (destino.exists())
					destino.processDeleteTable();
			} catch (Exception e) {
				PssLogger.logError(e);
				throw new Exception("FILA " + fila + ": " + e.getMessage());
			}
		}
		vItemsRemove = null;
	}

	public void processNewAll() throws Exception {
		this.firstRecord();
		while (this.nextRecord()) {
			JRecord oBD = this.getRecord();
			oBD.processInsert();
		}
	}

	public void delete() throws Exception {
		JBaseRegistro oSet = JBaseRegistro.VirtualCreate(this);
		oSet.delete();
		oSet.close();
	}

	public void update() throws Exception {
		JBaseRegistro oSet = JBaseRegistro.VirtualCreate(this);
		oSet.update();
		oSet.close();
	}

	public boolean hasOnlyOneElement() throws Exception {
		return this.sizeStaticElements() == 1;
	}

	public boolean hasAny() throws Exception {
		return this.sizeStaticElements() >= 0;
	}

	public int size() throws Exception {
		return (int) this.sizeStaticElements();
	}
	public long sizeStaticElements() throws Exception {
		if (!this.isStatic())
			this.toStatic();
		// JExcepcion.SendError("Funcion no implementada para Conjuntos NO
		// estaticos");
		if (vItems == null)
			return 0l;
		return getStaticItems().size();
	}

	public TRecord getLastStaticElement() throws Exception {
		if (this.sizeStaticElements() <= 0)
			return null;
		return this.getStaticElement((int) this.sizeStaticElements() - 1);
	}

	public TRecord getStaticElement(int idx) throws Exception {
		// if (!this.isStatic())
		// JExcepcion.SendError("Funcion no implementada para Conjuntos NO
		// estaticos");
		// if (vItems == null)
		// return null;
		this.toStatic();
		if (idx + 1 > this.sizeStaticElements())
			return null;
		return getStaticItems().getElementAt(idx);
	}

	public JList<TRecord> getStaticItems() {
		if (vItems == null) {
			vItems = JCollectionFactory.createList();
			// this.setStatic(true);
		}
		return vItems;
	}

	public JList<TRecord> getStaticRemoveItems() {
		if (vItemsRemove == null) {
			vItemsRemove = JCollectionFactory.createList();
		}
		return vItemsRemove;
	}

	@SuppressWarnings("unchecked")
	public Collection getCollection() {
		return new JRecordCollection(this);
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro, long zValor) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro, zValor);
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro, String zValor) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro, zValor.trim());
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro1, String zValor1, String zFiltro2, String zValor2) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1, String zFiltro2, long zValor2) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2);
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, long zValor3)
			throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>(sClase);
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3);
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, String zValor3,
			String zFiltro4, String zValor4) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>(sClase);
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3.trim());
		oJBDs.addFilter(zFiltro4, zValor4.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, long zValor3) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3);
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, String zValor3) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, String zValor3, String zFiltro4,
			String zValor4) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3.trim());
		oJBDs.addFilter(zFiltro4, zValor4.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, String zValor1) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		return oJBDs.exists();
	}

	@SuppressWarnings("unchecked")
	public static <TRecord extends JRecord> boolean existsComplete(String sClase, String zFiltro1, long zValor1) throws Exception {
		JRecords<TRecord> oJBDs = null;
		try {
			oJBDs = new JRecords<TRecord>((Class<TRecord>) Class.forName(sClase));
		} catch (ClassNotFoundException e) {
			return false;
		}
		oJBDs.addFilter(zFiltro1, zValor1);
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro1, String zValor1, String zFiltro2, long zValor2) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2);
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro1, long zValor1, String zFiltro2, String zValor2) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro1, zValor1);
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro1, String zValor1, String zFiltro2, String zValor2, String zFiltro3, String zValor3)
			throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2.trim());
		oJBDs.addFilter(zFiltro3, zValor3.trim());
		return oJBDs.exists();
	}

	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, String zFiltro1, String zValor1, String zFiltro2, long zValor2, String zFiltro3, String zValor3)
			throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		oJBDs.addFilter(zFiltro1, zValor1.trim());
		oJBDs.addFilter(zFiltro2, zValor2);
		oJBDs.addFilter(zFiltro3, zValor3.trim());
		return oJBDs.exists();
	}

	/**
	 * Check if the jbd exists into the database. WARNING!!!! USE IT JUST IN
	 * STRING FILTERS
	 */
	public static <TRecord extends JRecord> boolean existsComplete(Class<TRecord> zClass, JMap<String, String> zKeyValues) throws Exception {
		JRecords<TRecord> oJBDs = new JRecords<TRecord>(zClass);
		JIterator<String> oIterator = zKeyValues.getKeyIterator();
		while (oIterator.hasMoreElements()) {
			String sKey = oIterator.nextElement();
			String sValue = zKeyValues.getElement(sKey);
			oJBDs.addFilter(sKey, sValue.trim());
		}
		return oJBDs.exists();
	}

	public void appendRecords(JMap<?, TRecord> map) throws Exception {
		JIterator<TRecord> iter = map.getValueIterator();
		while (iter.hasMoreElements()) {
			this.addItem(iter.nextElement());
		}
		this.setStatic(true);
	}

	public static JRecords<BizVirtual> createVirtualFormMap(JMap<String, String> map) throws Exception {
		return createVirtualFormMap(map, 157);
	}

	public static JRecords<BizVirtual> createVirtualFormMap(JMap<String, String> map, int icono) throws Exception {
		if (map == null)
			return null;
		JRecords<BizVirtual> records = JRecords.createVirtualBDs();
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			records.addItem(JRecord.virtualBD(key, map.getElement(key), icono));
		}
		return records;
	}

	public static JRecords<BizVirtual> createVirtualFormList(JList<String> list) throws Exception {
		if (list == null)
			return null;
		JRecords<BizVirtual> records = JRecords.createVirtualBDs();
		JIterator<String> iter = list.getIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			records.addItem(JRecord.virtualBD(key, key, 1));
		}
		return records;
	}

	public static JRecords<BizVirtual> createVirtualBDs() throws Exception {
		JRecords<BizVirtual> oBDs = new JRecords<BizVirtual>(BizVirtual.class);
		oBDs.setStatic(true);
		// oBDs.getStructure().setTable("");
		// oBDs.setBasedClass(BizVirtual.class);
		return oBDs;
	}

	public BizVirtual findVirtualKey(String zKey) throws Exception {
		this.firstRecord();
		while (this.nextRecord()) {
			BizVirtual oVirtual = (BizVirtual) this.getRecord();
			if (oVirtual.getValor().equals(zKey))
				return oVirtual;
		}
		return null;
	}

	public JRecord findKey(String zValor) throws Exception {
		if (zValor==null) return null;

		this.firstRecord();
		while (this.nextRecord()) {
			JRecord oJBD = this.getRecord();
			if (oJBD==null) continue;
			if (oJBD.getProp(GetSearchKey(),false)==null) continue;
			if (oJBD.getProp(GetSearchKey()).toString().equals(zValor))
				return oJBD;
		}
		return null;
	}

//	public boolean hasInfoRow() throws Exception {
//		this.firstRecord();
//		while (this.nextRecord()) {
//			JRecord oJBD = this.getRecord();
//			if (oJBD.getRowId()!=null) {
//				return true;
//			}
//
//		}
//		return false;
//	}
	public JRecord findRowId(long zValor) throws Exception {
		this.firstRecord();
		while (this.nextRecord()) {
			JRecord oJBD = this.getRecord();
			if (oJBD.getRowId()==null) {
				continue;
			}
			if (JTools.getLongFirstNumberEmbedded(oJBD.getRowId())==zValor)
				return oJBD;
		}
		return null;
	}

	public void SetSearchKey(String zValor) {
		sSearchKey = zValor;
	}

	public String GetSearchKey() {
		return sSearchKey;
	}

	private JMap<String, JMap<String, TRecord>> getMultiHash() throws Exception {
		if (hMultiHash == null)
			hMultiHash = JCollectionFactory.createMap();
		return hMultiHash;
	}

	public JMap<String, TRecord> convertToHash(String zClave) throws Exception {
		JMap<String, TRecord> map = JCollectionFactory.createOrderedMap();
		JStringTokenizer oToken = JCollectionFactory.createStringTokenizer(zClave, ';');
		JIterator<TRecord> it = this.getStaticIterator();
		while (it.hasMoreElements()) {
			TRecord oBD = it.nextElement();
			String sAllKeys = "";
			oToken.reset();
			while (oToken.hasMoreTokens())
				sAllKeys += (sAllKeys.equals("")?"":"_")+oBD.getPropDeep(oToken.nextToken()).toString();
			map.addElement(sAllKeys, oBD);
		}
		getMultiHash().addElement(zClave, map);
		return hHash = map;
	}

	public boolean hasHash() throws Exception {
		return hHash!=null;
	}
	public Object findInHash(String zClave) throws Exception {
		if (hHash == null)
			JExcepcion.SendError("No existe Hash Table generada");
		return hHash.getElement(zClave);
	}

	public Object findInHash(String key, String zClave) throws Exception {
		if (getMultiHash().getElement(key) == null)
			return null;
		// JExcepcion.SendError("No existe Hash Table generada");
		return getMultiHash().getElement(key).getElement(zClave);
	}

	public Object removeInHash(String zClave) throws Exception {
		if (hHash == null)
			JExcepcion.SendError("No existe Hash Table generada");
		return hHash.removeElement(zClave);
	}

	public Object removeInHash(String key, String zClave) throws Exception {
		if (getMultiHash().getElement(key) == null)
			JExcepcion.SendError("No existe Hash Table generada");
		return getMultiHash().getElement(key).removeElement(zClave);
	}

	public Object addInHash(String zClave, TRecord record) throws Exception {
		if (hHash == null)
			JExcepcion.SendError("No existe Hash Table generada");
		return hHash.addElement(zClave, record);
	}

	public Object addInHash(String key, String zClave, TRecord record) throws Exception {
		if (getMultiHash().getElement(key) == null)
			JExcepcion.SendError("No existe Hash Table generada");
		return getMultiHash().getElement(key).addElement(zClave, record);
	}

	public JList<TRecord> convertToList() throws Exception {
		JList<TRecord> vJBDs = JCollectionFactory.createList();
		firstRecord();
		while (nextRecord()) {
			vJBDs.addElement(getRecord());
		}
		return vJBDs;
	}

	public JList<String> convertToList(String sCampo) throws Exception {
		JList<String> vJBDs = JCollectionFactory.createList();
		firstRecord();
		while (nextRecord()) {
			vJBDs.addElement(getRecord().getProp(sCampo).toString());
		}
		return vJBDs;
	}

	// public void setIgnoreForeignFields(boolean zValue) {
	// bIgnoreForeignFields=zValue;
	// }

	public void ExecprocessDeleteAll() throws Exception {
		JExec oExec = new JExec(null, "processDeleteAll()") {

			@Override
			public void Do() throws Exception {
				processDeleteAll();
			}
		};
		oExec.execute();
	}
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes) throws Exception {
		String keyOptions = fuentes.getRecordRef().getKeyField();
		String keySource = findId(this,fuentes.getRecordRef().getClass());
		String keyToFill = findIdRecords(parent,this.getRecordRef().getClass());
		String keyOptionsParent = parent.getKeyField();
		String keySourceParent = findParent(this,parent.getClass());
		execProcessFillRecords(parent, fuentes, keyOptions, keySource,keyOptionsParent, keySourceParent, keyToFill);
	}
	
	private static String findId(JRecords zSource, Class classToFind) throws Exception {
		JProperty prop = zSource.getRecordRef().findFirstFixedPropByClass(classToFind,true);
		String out = prop.GetCampo()+".";
		if (!prop.isRecord()) return null;
		JRecord  winChild = (JRecord)prop.getClase().newInstance();
		out+=winChild.getKeyField();
		return out;
	}
	private static String findParent(JRecords zSource, Class classToFind) throws Exception {
		JProperty prop = zSource.getRecordRef().findFirstFixedPropByClass(classToFind,false);
//		if (prop==null)
//			return zSource.find
		String out = prop.GetCampo()+".";
		if (!prop.isRecord()) return null;
		JRecord  winChild = (JRecord)prop.getClase().newInstance();
		out+=winChild.getKeyField();
		return out; 
	}
	private static String findIdRecords(JRecord zSource, Class classToFind) throws Exception {
		JProperty prop = zSource.findFirstFixedPropByClass(classToFind,true);
		String out = prop.GetCampo();
		return out;
	}
	
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes, final String keyFieldFuente, final String keyField) throws Exception {
		execProcessFillRecords(parent, fuentes,keyFieldFuente,keyField,null,null);
	}
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes, final String keyFieldFuente, final String keyField, final String parentField) throws Exception {
		execProcessFillRecords(parent, fuentes, keyFieldFuente, keyField, null, null, parentField);
	}
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes, final String keyFieldFuente, final String keyField, final String keyFieldFuenteParent, final String keyFieldParent) throws Exception {
		String keyToFill = findIdRecords(parent,this.getRecordRef().getClass());
		execProcessFillRecords(parent, fuentes, keyFieldFuente, keyField, keyFieldFuenteParent, keyFieldParent, keyToFill);
	}
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes, final String keyFieldFuente, final String keyField, final String keyFieldFuenteParent, final String keyFieldParent, final String parentField) throws Exception {
		execProcessFillRecords(parent, fuentes, keyFieldFuente, keyField, keyFieldFuenteParent, keyFieldParent, parentField, false);
	}	 
	public void execProcessFillRecords(final JRecord parent,final JRecords fuentes, final String keyFieldFuente, final String keyField, final String keyFieldFuenteParent, final String keyFieldParent, final String parentField, final boolean tableBased) throws Exception {
		JExec oExec = new JExec(null, "processFillRecords()") {

			@Override
			public void Do() throws Exception {
				processFillRecords(parent, fuentes, keyFieldFuente,keyField, keyFieldFuenteParent,keyFieldParent, parentField,tableBased);
				
			}
		};
		oExec.execute();
	}


	public JIterator<TRecord> getStaticIterator() throws Exception {
		this.toStatic();
		// if (!this.isStatic()) JExcepcion.SendError("Registros no estaticos");
		return this.getStaticItems().getIterator();
	}

	@Override
	public void notifyDropOK() throws Exception {
		if (this.sizeStaticElements() <= 0)
			return;
		JRecord firstRecord = this.getStaticElement(0);
		firstRecord.notifyDropOK();
	}

	public void notifyUpdateAllOK() throws Exception {
		this.notifyEvent("UpdateAllOk");
	}

	public static JRecords<JRecord> createRecords(JBaseRecord baseRecord) throws Exception {
		JRecords<JRecord> records;
		if (baseRecord instanceof JRecords) {
			records = (JRecords) baseRecord;
		} else {
			records = new JRecords<JRecord>((Class<JRecord>) baseRecord.getClass());
			records.addItem((JRecord) baseRecord);
		}
		records.setStatic(true);
		return records;
	}

	// public boolean hasToJoinFilter(RFilter filter) throws Exception {
	// return filter.getOperator().equals("=");
	// }

	@Override
	public String getTableByFilter(String filter) throws Exception {
		return this.getRecordRef().getTableByFilter(filter);
	}
	
	public boolean analizeFilter(RFilter filter,JFormControl c) throws Exception {
		if (filter.isDynamic()) return true;
		if (filter.isVirtual()) {
			c.setVisible(false);
			return true;
		}
		if (hidePreasignedFilters()) 
			c.setVisible(false); 
		else
			c.SetReadOnly(true);
		return true;
	}

	public TRecord getRecordRef() throws Exception {
		if (this.recordRef == null)
			this.recordRef = this.getBasedClass().newInstance();

		this.recordRef.SetVision(this.GetVision());
		this.recordRef.setParent(this);
//		this.recordRef.setStructure(getStructure()); //HGK
		return this.recordRef;
	}

	public void setStatic(boolean value) {
		
		super.setStatic(value);
		if (vItems == null)
			return;
		JIterator<TRecord> iter = this.vItems.getIterator();
		while (iter.hasMoreElements()) {
			iter.nextElement().setStatic(value);
		}
	}

	public boolean isDatabaseSupportsOffset() {
		if (pRSet == null)
			return false;
		return this.pRSet.isDatabaseSupportsOffset();
	}

	// public boolean isFixed() throws Exception {
	// return this.fixed;
	// }
	//
	// public void setFixed(boolean value) {
	// this.fixed=value;
	// }

	// public void assignFilters(JFilterMap filterMap) throws Exception {
	// if (filterMap==null) return;
	// if (!filterMap.hasFilters()) return;
	// String filter, value;
	// JIterator<String> iter = filterMap.getMap().getKeyIterator();
	// while (iter.hasMoreElements()) {
	// filter = iter.nextElement();
	// value = filterMap.getMap().getElement(filter);
	// if (value.equals("")) continue;
	// RFilter f = this.getFilter(filter);
	// if (f!=null) {
	// if (value.equals(f.getValue())) continue;
	// JExcepcion.SendError("Error en filtros");
	// }
	// this.addFilter(filter, value).setDynamic(true);
	// }
	// }

	public JFilterMap createFilterMap() throws Exception {
		JFilterMap map = new JFilterMap();
		JIterator<RFilter> iter = this.getFilters().getIterator();
		while (iter.hasMoreElements()) {
			RFilter f = iter.nextElement();
			map.addFilterMap(f.getField(), f.getValue());
		}
		return map;
	}

	public TRecord getFirstRecord() throws Exception {
		if (!this.ifRecordFound())
			return null;
		return this.getStaticElement(0);
	}

	public void invertirOrden() throws Exception {
		JList<TRecord> list = JCollectionFactory.createList();
		JIterator<TRecord> iter = this.getStaticIterator();
		while (iter.hasMoreElements()) {
			list.addElement(iter.nextElement());
		}
		this.clearStaticItems();
		int len = list.size();
		for (int i = len - 1; i >= 0; i--) {
			this.addItem(list.getElementAt(i));
		}
	}

	@SuppressWarnings("unchecked")
	public double selectAvg(String zCampo) throws Exception {
		if (isStatic() == false && isSQLBased()) {
			JBaseRegistro RSet = JBaseRegistro.recordsetFactory(this);
			String sSum = RSet.selectAvg(zCampo);
			RSet.close();
			return Double.parseDouble(sSum);
		}

		double tot = 0.0F;
		firstRecord();
		while (nextRecord()) {
			JRecord rec = getRecord();
			JObject<?> fl = rec.getProp(zCampo);
			if (fl instanceof JFloat)
				tot += ((JFloat) fl).getValue();
			else if (fl instanceof JLong)
				tot += ((JLong) fl).getValue();
		}
		return (tot / this.sizeStaticElements());
	}



}
