package pss.common.customList.config.relation;

import pss.common.customList.config.field.campo.BizCampo;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JRelations {
	public static final String  JOIN_LEFT = "LEFT JOIN";
	public static final String  JOIN_RIGHT = "RIGHT JOIN";
	public static final String  JOIN_FULL = "FULL JOIN";
	public static final String  JOIN_ONE = "ONE JOIN";
	public static final String  JOIN_TEN = "TEN JOIN";
	public static final String  JOIN = "JOIN";

	private JList<JRelation> list;
	private JList<String> hideFields;
	private JList<String> onlyLista;
	private JMap<String,JPair> geos;
	//private JList<String> hideFilters;
	private Class sourceClass;
	private String sourceWinsClass;
	private JRecord record;
	private JMap<String, BizCampo> camposMailing;
	private JMap<String, JPair> filtros;
	private String chatSpec;	
	public String getChatSpec() {
		return chatSpec;
	}

	public void setChatSpec(String chatSpec) {
		this.chatSpec = chatSpec;
	}
	public JRelations(Class c) {
		this.sourceClass=c;
	}
	
	public JRecord getRecRef() throws Exception { 
		if (record!=null) return this.record;
		return (this.record=(JRecord)this.sourceClass.newInstance());
	}
	
	public JList<JRelation> getList() throws Exception {
		return this.list;
	}
	
	public JRelation findFixedRelation() throws Exception {
		JIterator<JRelation> iter = this.getList().getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
			if (r.isRelationFixed()) 
				return r;
		}
		return null;
	}
	public JMap<String, JPair> getFiltros() throws Exception {
		if (filtros!=null) return filtros;
		return filtros=JCollectionFactory.createMap();
	}
	public JPair getFiltro(String campo) throws Exception {
		return getFiltros().getElement(campo);
	}
	public JMap<String, BizCampo> getCamposMailing() throws Exception {
		if (camposMailing!=null) return camposMailing;
		return camposMailing=JCollectionFactory.createMap();
	}
	public BizCampo getCamposMailing(String tipo) throws Exception {
		return getCamposMailing().getElement(tipo);
	}
	
	public BizCampo addCamposMailing(String tipo,String campo,String record,String relId,String operator) throws Exception {
		BizCampo f = new BizCampo();
		f.setRelId(relId);
		f.setRecordOwner(record);
		f.setRecordSource(record);
		f.setCampo(campo);
		f.setOperador(operator);
		f.setDinamico(false);
		f.setHasFiltro(true);
		f.setOperacion(BizCampo.OPER_AND);
		return getCamposMailing().addElement(tipo,f);
	}

	public void addFilter(String campo,String operator,String value) throws Exception {
		 getFiltros().addElement(campo,new JPair(operator,value));
	}

	public JRelation addRelationParent(int id, String descr, JMap<String, String> map, String fieldKey,String typeJoin, String fieldKeyChild) throws Exception {
		JRelation r =this.addRelation(1, id, (id!=0?" ":"")+descr, null, fieldKey,typeJoin,fieldKeyChild);
		r.setMapTarget(map);
		return r;
	}
	
	public JRelation addRelationParent(int id, String descr, JMap<String, String> map, String fieldKey,String typeJoin) throws Exception {
		JRelation r =this.addRelation(1, id, (id!=0?" ":"")+descr, null, fieldKey,typeJoin,fieldKey);
		r.setMapTarget(map);
		return r;
	}
	public JRelation addRelationParent(int id, String descr, JMap<String, String> map, String fieldKey) throws Exception {
		JRelation r =this.addRelation(1, id, (id!=0?" ":"")+descr, null, fieldKey,null,fieldKey);
		r.setMapTarget(map);
		return r;
	}
	public JRelation addRelationParent(int id, String descr, Class relation, String myKeyField, String relKeyField) throws Exception {
		return this.addRelation(1, id, descr, relation, myKeyField,null,relKeyField);
	}
	public JRelation addRelationParent(int id, String descr, Class relation, String myKeyField) throws Exception {
		return this.addRelation(1, id, descr, relation, myKeyField,null,myKeyField);
	}
	public JRelation addRelationChild(int id, String descr, Class relation) throws Exception {
		return this.addRelation(2, id, descr, relation, null,null,null);
	}
	public JRelation addRelationForce(int id, String descr) throws Exception {
		return this.addRelation(3, id, descr, null, null,null,null);
	}
	public JRelation addRelationForce(int id, String descr, Class relation, String myKeyField, String relKeyField) throws Exception {
		return this.addRelation(3, id, descr, relation, myKeyField,null,relKeyField);
	}
	public JRelation addRelationParent(int id, JRelation r) throws Exception {
		r.setId(String.valueOf(id));		
		return this.addRelation(r);
	}
	private JRelation addRelation(int type, int id, String descr, Class relation, String myKeyField,String typeJoin, String relKeyField) throws Exception {
		JRelation r = new JRelation();
		r.setId(String.valueOf(id));
		r.setDescription(descr);
		r.setClassSource(sourceClass);
		r.setClassTarget(relation);
		r.setType(type);
		r.setTypeJoin(typeJoin);
		r.setMyKeyField(myKeyField);
		r.setRelKeyField(relKeyField);
		return this.addRelation(r);
	}
	
	public void setSourceWinsClass(String value) throws Exception {
		this.sourceWinsClass=value;
	}

	public String getSourceWinsClass() throws Exception {
		return this.sourceWinsClass;
	}
	
	public JRelation addRelation(JRelation r) throws Exception {
		if (list==null) this.list = JCollectionFactory.createList();
		list.addElement(r);
		return r;
	}
	
	public JRelation findRel(String id) throws Exception {
		JIterator<JRelation> iter = list.getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
			if (r.getId().equals(id) || (r.geOldId()!=null && r.geOldId().equals(id))) 
				return r;
		}
		return null;
	}
	
	public JRelation findRelByFieldKey(String field) throws Exception {
		JIterator<JRelation> iter = list.getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
			if (!r.isRelationParent()) continue;
			if (r.getMyKeyField()==null) continue;
			if (r.getMyKeyField().toLowerCase().equals(field.toLowerCase())) 
				return r;
		}
		return null;
	}
	public JRelation findRelTargetByField(String field) throws Exception {
		JIterator<JRelation> iter = list.getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
			if (!r.isRelationParent()) continue;
			if (r.getRelKeyField()==null) continue;
			if (r.getRelKeyField().equalsIgnoreCase(field)) 
				return r;
		}
		return null;
	}
	public void hideField(String f) throws Exception {
		if (this.hideFields==null) this.hideFields=JCollectionFactory.createList();
		this.hideFields.addElement(f);
	}
	
	// marca campos que son unicos y por eso si se usan convierte a modo lista
	public void onlyLista(String f) throws Exception {
		if (this.onlyLista==null) this.onlyLista=JCollectionFactory.createList();
		this.onlyLista.addElement(f);
	}

	public void addGeoPostions(String field, JPair geofield) throws Exception {
		if (this.geos==null) this.geos=JCollectionFactory.createMap();
		this.geos.addElement(field.toLowerCase(), geofield);
	}
	public String getGeoField(String f) throws Exception {
		if (this.geos==null) return getFirstGeoField();
		JPair geoField= this.geos.getElement(f.toLowerCase());
		if ( geoField==null) geoField=this.geos.getElement("*");
		return geoField!=null?((JString)(geoField.secondObject())).getValue():getFirstGeoField(); 
	}
	
	public long getRelationGeoField(String f) throws Exception {
		if (this.geos==null) return 0;
		JPair geoField= this.geos.getElement(f.toLowerCase());
		if ( geoField==null) geoField=this.geos.getElement("*");
		return geoField!=null && (geoField.firstObject() instanceof JLong)?((JLong)geoField.firstObject()).getValue():0; 
	}
	private JMap<String, String> fieldGroup;
	private JMap<String, String> folderGroup;
	private JMap<String, String> allFolderGroup;

	public void addFolderGroup(String folderChild, String folderParent) {
		String parent = folderParent;
		if (folderParent==null) parent="root_";
		getFolderGroup().addElement(folderChild,parent);
		getAllFolderGroup().addElement(folderChild,folderChild);
	}
	
	public void addFieldGroup(String relid,String field,String function, String group) {
		if (fieldGroup==null) this.fieldGroup=JCollectionFactory.createOrderedMap();
		fieldGroup.addElement(relid+"_"+field.toLowerCase()+"_"+function,group);
	}
	public String getFieldGroup(String relid,String field,String function) {
		field=field.toLowerCase();
		if (fieldGroup==null) this.fieldGroup=JCollectionFactory.createOrderedMap();
		String res= fieldGroup.getElement(relid+"_"+field+"_"+function);
		if (res!=null) return res; 
		res = fieldGroup.getElement(relid+"_"+field+"_*");
		if (res!=null) return res; 
		res = fieldGroup.getElement(relid+"_*_*");
		if (res!=null) return res; 
		res = fieldGroup.getElement("*_"+field+"_"+function);
		if (res!=null) return res; 
		res = fieldGroup.getElement("*_"+field+"_*");
		if (res!=null) return res; 
		return fieldGroup.getElement("*_*_*");
	}
	public boolean getFieldGroupExact(String relid,String field,String function) {
		field=field.toLowerCase();
		if (fieldGroup==null) this.fieldGroup=JCollectionFactory.createOrderedMap();
		String res= fieldGroup.getElement(relid+"_"+field+"_"+function);
		if (res!=null) return true; 
		res = fieldGroup.getElement(relid+"_"+field+"_*");
		if (res!=null) return true; 
		res = fieldGroup.getElement("*_"+field+"_"+function);
		if (res!=null) return true; 
		res = fieldGroup.getElement("*_"+field+"_*");
		if (res!=null) return true; 
		return false;
	}
	
	public String getFullNameGroup(String group) {
		if (getParentFolderGroup(group)==null) return group;
		if (getParentFolderGroup(group).equals("root_")) return  group;
		String parentName=getFullNameGroup(getParentFolderGroup(group));
		if (parentName.equals(",root_")) return  group;
		return  getFullNameGroup(getParentFolderGroup(group))+", "+ group;
	}
	public String getParentFolderGroup(String child) {
		return getFolderGroup().getElement(child);
	}	
	public JList<String> getAllFieldGroup() {
		JList<String> list = JCollectionFactory.createList();
		if (fieldGroup==null) return list;
		for (Object s: fieldGroup.getElements()) {
			if (!list.containsElement((String)s))
				list.addElement((String)s);
		}
		return list;
	}
	public JMap<String,String> getFolderGroup() {
		if (folderGroup==null) this.folderGroup=JCollectionFactory.createOrderedMap();
		return folderGroup;
	}
	public JMap<String,String> getAllFolderGroup() {
		if (allFolderGroup==null) this.allFolderGroup=JCollectionFactory.createOrderedMap();
		return allFolderGroup;
	}
	

  
	public String getFirstGeoField() throws Exception {
	
//		JRecord r = (JRecord)sourceClass.newInstance();
		JIterator<JProperty> it = this.getRecRef().getFixedProperties().getValueIterator();
		while (it.hasMoreElements()) {
			JProperty prop = it.nextElement();
			
			if (!getRecRef().getObjectType(prop.GetCampo()).equals(JObject.JGEOPOSITION)) continue;
			return prop.GetCampo();
		}
		
		
		return null;
	}
	/*
	public void hideFilter(String f) throws Exception {
		if (this.hideFilters==null) this.hideFilters=JCollectionFactory.createList();
		this.hideFilters.addElement(f);
	}
*/
	public boolean isFieldHided(String f) throws Exception {
		if (this.hideFields==null) return false;
		String field = f.toLowerCase();
		JIterator<String> iter = this.hideFields.getIterator();
		while (iter.hasMoreElements()) {
			String hided=iter.nextElement().toLowerCase();
			if (hided.startsWith("*")) {
				if (field.endsWith(JTools.replace(hided,"*", "")))
					return true;			
			} else if (hided.endsWith("*")) {
				if (field.startsWith(JTools.replace(hided,"*", "")))
					return true;			
			} else if (field.equals(hided))
				return true;
		}
		return false; 
	}
	
	public boolean isOnlyList(String f) throws Exception {
		if (this.onlyLista==null) return false;
		JIterator<String> iter = this.onlyLista.getIterator();
		while (iter.hasMoreElements()) {
			if (f.toLowerCase().equals(iter.nextElement().toLowerCase()))
				return true;
		}
		return false; 
	}
	/*
	public boolean isFilterHided(String f) throws Exception {
		if (this.hideFilters==null) return false;
		JIterator<String> iter = this.hideFilters.getIterator();
		while (iter.hasMoreElements()) {
			if (f.equals(iter.nextElement()))
				return true;
		}
		return false; 
	}*/
	
	public boolean hasParentRecord(String field) throws Exception {
		if (this.list==null) return false;
		JIterator<JRelation> iter = this.list.getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
//			if (r.getFieldKeyChild()!=null && r.getFieldKeyChild().equalsIgnoreCase(field))
				if (r.getMyKeyField()!=null && r.getMyKeyField().equalsIgnoreCase(field))
				return true;
		}
		return false;
	}
	
	public void assignRecordName(String name) throws Exception {
		JRelation r = this.getList().getElementAt(0);
		r.setDescription(name);
	}
	
}
