package pss.common.customList.config.relation;

import java.io.Serializable;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.regions.multilanguage.JLanguage;
import pss.core.data.interfaces.sentences.JRegSQL;
import pss.core.services.fields.JObject;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JRelation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175826804673607180L;
	private String id;
	private String oldid;
	private String description;
	private Class<JRecord> classSource;
	private Class<JRecord> classTarget;
	//private String classTargetWins;
	private JMap<String, String> mapTarget;

	private JList<JPair> filters;
	private JList<JPair> joins;
	private JList<JPair> geos;
	private int type;
	private String myKeyField;
	private String relKeyField;
	private String typeJoin;
	private String alias;	
	public String getTypeJoin() {
		if (typeJoin==null) return "JOIN";
		return typeJoin;
	}
	private JRelation relParent;

	public void setTypeJoin(String typeJoin) {
		this.typeJoin = typeJoin;
	}

	public void setAlias(String value) {
		this.alias = value;
	}
	
	public String getParentAlias() {
		if (relParent==null) return "";
		return (relParent.alias==null?"":relParent.alias)+relParent.getParentAlias();
	}
	public String getAlias() {
		//return alias;
		if (this.alias==null) return null;
		return this.alias+getParentAlias();
	}
	public String getSimpleAlias() {
		return this.alias;
	}
	public boolean hasAlias() {
		return this.alias!=null;
	}


	private transient JRecord recordSource;
  private transient JRecord recordTarget;
  	
	public JRelation() {
	}
	
	public String getId() {
		return this.id;
	}
	public String geOldId() {
		return this.oldid;
	}
		
	public String getDescription() {
		return JLanguage.translate(this.description);
	}

	public String getMyKeyField() {
		return this.myKeyField;
	}
	
	public String getRelKeyField() {
		return this.relKeyField;
	}
	
	public Class<JRecord> getClassTarget() {
		return this.classTarget;
	}

	public Class<JRecord> getClassSource() {
		return this.classSource;
	}
	public boolean hasFilters() {
		return this.filters!=null;
	}
	public boolean hasJoin() {
		return this.joins!=null;
	}
	public boolean hasGeos() {
		return this.geos!=null;
	}

	public boolean hasClassTarget() {
		return this.classTarget!=null;
	}

	public boolean hasClassSource() {
		return this.classSource!=null;
	}

	public void setId(String value) {
		this.id = value;
	}
	public void setOldId(String value) {
		this.oldid = value;
	}
	public void setDescription(String value) {
		this.description = value;
	}

	public void setMapTarget(JMap<String, String> value) {
		this.mapTarget = value;
	}

	
	public void setObjRelParent(JRelation value) {
		this.relParent = value;
	}

	public void setClassSource(Class<JRecord> value) {
		this.classSource=value;
	}

	public void setClassTarget(Class<JRecord> value) {
		this.classTarget=value;
	}
	
	public JList<JPair> getFilters()  {
		if (filters==null) this.filters=JCollectionFactory.createList();
		return this.filters;
	}
	public JList<JPair> getJoins() throws Exception {
		return this.joins;
	}
	public JList<JPair> getGeos() throws Exception {
		return this.geos;
	}

	public void addJoin(String myField, String relField) {
		if (joins==null) this.joins=JCollectionFactory.createList();
		JPair p = new JPair(myField, relField);
		joins.addElement(p);
	}
	public void addFilter(String filter) {
		getFilters().addElement(new JPair(filter,""));
	}
	public void addFilter(String filter, String tableAsoc) {
		getFilters().addElement(new JPair(filter,tableAsoc));
	}

	public void addGeo(String field, String geofield) {
		if (geos==null) this.geos=JCollectionFactory.createList();
		JPair p = new JPair(field, geofield);
		geos.addElement(p);
	}

	public void setType(int value) {
		this.type = value;
	}
	
	public void setMyKeyField(String value) {
		this.myKeyField = value;
	}

	public void setRelKeyField(String value) {
		this.relKeyField = value;
	}
	public boolean isRelationParent() {
		return this.type==1;
	}
	
	public boolean isRelationChild() {
		return this.type==2;
	}
	
	public boolean isRelationFixed() {
		return this.type==3;
	}
	
	public String getSerializeJoin() throws Exception {
		return this.getSerializeJoin(null);
	}
	
	public String getSerializeJoin(JRecord source) throws Exception {
		if (!this.hasJoin()) return null;
		if (this.classTarget==null) return null;
//		String tableSource = classSource.newInstance().GetTable();
//		String tableTarget = classTarget.newInstance().GetTable();
		boolean first=true;
		StringBuffer s = new StringBuffer();
		JIterator<JPair> iter = this.joins.getIterator();
		while (iter.hasMoreElements()) {
			JPair p = iter.nextElement();
			if (!first) s.append(" and ");
			s.append(p.firstObject()).append("=");
			if (source==null)
				//s.append(tableSource).append(".").append(p.secondObject());
				s.append(p.secondObject());
			else {
				JObject obj = source.getProp((String)p.secondObject()); 
				s.append(new JRegSQL().ArmarDato(null, obj.getObjectType(), obj.toString()));
			}
			first=false;
		}
		return " "+s.toString()+" ";
	}
	
	public String getSerializeFilter() throws Exception {
		if (!this.hasFilters()) return null;
		StringBuffer s = new StringBuffer();
		JIterator<JPair> iter = this.filters.getIterator();
		while (iter.hasMoreElements()) {
			JPair f = iter.nextElement();
			if (s.length()!=0) s.append(" and ");
			if (!((String)f.firstObject()).trim().equals("")) s.append(f.firstObject());
		}
		return " "+s.toString()+" ";
	}
	

	public JMap<String, String> getMapTarget() throws Exception {
		return this.mapTarget;
	}
	
	public JRelations getTargetRelationMap() throws Exception {
		JRelations rels =  this.getObjRecordTarget().getRelationMap();
//		rels.assignRecordName(this.findDescr());
		return rels;
	}
	
	public JRecord getObjRecordTarget() throws Exception {
		if (this.recordTarget!=null) return this.recordTarget;
		if (this.classTarget==null) return new BizVirtual(); 
		JRecord r = (JRecord)this.getClassTarget().newInstance();
		return (this.recordTarget=r);
	}
  
	
	public JRecord getObjRecordSource() throws Exception {
		if (this.recordSource!=null) return this.recordSource;
		JRecord r = (JRecord)this.getClassSource().newInstance();
		return (this.recordSource=r);
	}
	
	public boolean isRelationRaiz() throws Exception {
		return this.getId().equals("0");
	}
	
	public boolean hasRelParent() {
		return this.relParent!=null;
	}
	public JRelation getObjRelParent(){
		return this.relParent;
	}
	
	public String findDescrParent() throws Exception {
//		if (this.relParent==null) return "";
		if (!this.hasRelParent()) return this.getDescription();
//		return this.getObjRelParent().findDescrParent();
		//+(this.isRelationRaiz()?"":"->"+this.getDescription());
		return this.getObjRelParent().findDescrParent()+"->"+this.getDescription();
	}
	


	public String serializeDeep() throws Exception {
		if (!this.hasRelParent()) return this.serialize();
//		if (!this.getObjRelParent().hasRelParent()) return this.serialize(); // ignoro el utlimo
		String s = this.getObjRelParent().serializeDeep();
		return (s!=null?s+"|":"")+this.serialize();
	}
	
	private String serialize() throws Exception {
//		String s = this.serializeDeepParent();
		return this.getClassSource().getName()+"_"+this.getId();
	}
	public String getTargetTableForFrom(BizCampo campo) throws Exception {
//		if (!campo.getRelId().equals(getId())) return null;
		return getTargetTableForFrom(campo.getCampo());
	}
	public String getTargetGeoTableForFrom(BizCampo campo) throws Exception {
//		if (campo.getRelId()!=getId()) return null;
		return getTargetTableForFrom(campo.getGeoCampo());
	}
	public String getTargetTableForFrom(String campo) throws Exception {
		if (this.getObjRecordTarget().GetTable()==null)
			return this.getObjRecordTarget().getTableByFilter(campo);
		return this.getObjRecordTarget().GetTable();
//		if (this.getObjRecordTarget().GetTableTemporal()==null)
//			return this.getObjRecordTarget().getTableByFilter(campo);
//		return this.getObjRecordTarget().GetTableTemporal();
		}

	public String getTargetAlias(String campo) throws Exception {
		String t = null;
		if (this.hasRelParent())
			t = this.getObjRelParent().getAlias();
		if (this.hasAlias()) return this.getAlias();
		if (t!=null) return t;
		return this.getObjRecordTarget().getTableByFilter(campo);
	}

	public String getBeautyDescrCampo() throws Exception {
		if (!this.hasRelParent()) return this.getDescription();
		if (this.getDescription().trim().equals("")) return this.getObjRelParent().getBeautyDescrCampo();
		return (this.getObjRelParent().getBeautyDescrCampo().trim().equals("")?"":this.getObjRelParent().getBeautyDescrCampo().trim()+", ")+this.getDescription().replace("*", "");
	}

	static long uniqueId=1;
	public BizCampoGallery createCampoGallery() throws Exception {
		BizCampoGallery g = new BizCampoGallery();
		g.setId(uniqueId++);
		g.setRecordOwner(this.getObjRecordTarget().getClass().getName());
		g.setRecordSource(this.getObjRecordSource().getClass().getName());
		g.setRelId(this.getId());
		g.setRelIdDescr(this.findDescrParent());
		g.setSerialDeep(this.serializeDeep());
		g.setObjRelacion(this);
		g.setFolder(false);
		return g;
	}
	
	static JMap<String,	JRecords<BizCampoGallery>> cacheRelation;
	static JMap<String,	JRecords<BizCampoGallery>> cacheEjesRelation;
	static JMap<String,	JRecords<BizCampoGallery>> cacheFilterRelation;
	

	static public JMap<String,	JRecords<BizCampoGallery>> getCacheRelation() throws Exception {
		if (cacheRelation!=null) return cacheRelation;
		cacheRelation = JCollectionFactory.createMap();
		return cacheRelation;
	}
	
	static public JMap<String,	JRecords<BizCampoGallery>> getCacheEjesRelation() throws Exception {
		if (cacheEjesRelation!=null) return cacheEjesRelation;
		cacheEjesRelation = JCollectionFactory.createMap();
		return cacheEjesRelation;
	}
	
	static public JMap<String,	JRecords<BizCampoGallery>> getCacheFilterRelation() throws Exception {
		if (cacheFilterRelation!=null) return cacheFilterRelation;
		cacheFilterRelation = JCollectionFactory.createMap();
		return cacheFilterRelation;
	}
	
  public JRecords<BizCampoGallery> getCamposGallery() throws Exception {
  	JRecords<BizCampoGallery> recs;
//  	recs = getCacheRelation().getElement(getDescription()+"_"+this.getObjRecordTarget().getClass().getCanonicalName());
//  	if (recs!=null) return recs;
  	JRecord rt = this.getObjRecordTarget();
  	recs = new JRecords<BizCampoGallery>(BizCampoGallery.class);
  	recs.setStatic(true);
  	JMap<String,String> folders = getTargetRelationMap().getFolderGroup();
		JIterator<String> it =folders.getKeyIterator();
		while(it.hasMoreElements()) {
			String hijo=it.nextElement();
			String padre=folders.getElement(hijo);
			
  		BizCampoGallery g = this.createCampoGallery();
  		g.setCampo("");
  		g.setGrupo(padre);
  		g.setFunction("");
  		g.setDescripcion(hijo);
  		g.setDescripcionSinFuncion(hijo);
  		recs.addItem(g);
			
		}

  	JMap<String, String> map = rt.getAllFixedProperties(false,false,false);
  	JIterator<String> iter = map.getKeyIterator();
  	while (iter.hasMoreElements()) {
  		String field =  iter.nextElement();

  		if (rt.getRelationMap().isFieldHided(field)) continue;
  		

  		JObject prop = rt.getProp(field,false);
  		if (prop==null)
  			continue;
  		BizCampoGallery g = this.createCampoGallery();
  		g.setCampo(field);
   		g.setDescripcion(JLanguage.translate(map.getElement(field)));
   		g.setDescripcionSinFuncion(JLanguage.translate(map.getElement(field)));
  		recs.addItem(g);
   		JMap<String, String> func = BizCampo.getFunctionMap(prop.getObjectType());
  		JIterator<String> itF = func.getKeyIterator();
  		while (itF.hasMoreElements()) {
  			String key = itF.nextElement();
  			String value = func.getElement(key);
    		g = this.createCampoGallery();
    		g.setCampo(field);
    		if (!key.equals("")) g.setFunction(key);
    		g.setDescripcion(JLanguage.translate(map.getElement(field))+", "+JLanguage.translate(""+JLanguage.translate(value)));
     		g.setDescripcionSinFuncion(JLanguage.translate(map.getElement(field)));
     	 	recs.addItem(g);
  			
  		}
  	}
  //	recs.Ordenar("descripcion");
//  	recs.convertToHash("campo_function");
//  	getCacheRelation().addElement(getDescription()+"_"+this.getObjRecordTarget().getClass().getCanonicalName(), recs);
  	return recs;
  }
  public JRecords<BizCampoGallery> getOnlyCamposGallery(BizCustomList list) throws Exception {
  	JRecords<BizCampoGallery> recs;
  	JRecord rt = this.getObjRecordTarget();
  	recs = new JRecords<BizCampoGallery>(BizCampoGallery.class);
  	recs.setStatic(true);
  	
  	JMap<String, String> map = rt.getAllFixedProperties(false,false,false);
  	JIterator<String> iter = map.getKeyIterator();
  	while (iter.hasMoreElements()) {
  		String field =  iter.nextElement();

  		if (rt.getRelationMap().isFieldHided(field)) continue;
  		
  		BizCampoGallery g = this.createCampoGallery();
  		g.setCampo(field);
   		g.setDescripcion(JLanguage.translate(map.getElement(field)));
   		g.setDescrCompleta(JLanguage.translate(map.getElement(field)));
   		g.setDescripcionSinFuncion(JLanguage.translate(map.getElement(field)));
   		g.setOrden(g.getDescrCompleta());
  		recs.addItem(g);
  	}
  	return recs;
  }
 

  public String findDescrCompleta(String campo) throws Exception {
	  if (this.getCamposGallery().findInHash(campo)==null) return "";
  	return ((BizCampoGallery)this.getCamposGallery().findInHash(campo)).getDescrCompleta();
  }

  
  public BizCampoGallery findCampoGallery(String campo) throws Exception {
  	return (BizCampoGallery)this.getCamposGallery().findInHash(campo);
  }

  

}
