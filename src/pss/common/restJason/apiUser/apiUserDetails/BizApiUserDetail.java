package pss.common.restJason.apiUser.apiUserDetails;

import pss.common.restJason.apiRestBase.apiWebServiceTools;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;

public class BizApiUserDetail extends JRecord {

	
	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	private JString pCompany = new JString();
	private JString pUsuario = new JString();
	private JString pJSonPath = new JString();
	
	public String getCompany() throws Exception {return pCompany.getValue();	}
	public String getUsuario() throws Exception {return pUsuario.getValue();	}

	
	public void setCompany(String value) {this.pCompany.setValue(value);}
	public void setUsuario(String value) {this.pUsuario.setValue(value);}


	private JMap<String, String> availableApiPaths;
	
	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizApiUserDetail() throws Exception {}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("usuario", pUsuario);
		addItem("json_path", pJSonPath);
	}
	
	
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "usuario", "Usuario", true, true, 50);
		addFixedItem( KEY, "json_path", "json_path", true, true, 200);
		addFixedItem( FIELD, "company", "Empresa", true, true, 15);

	}

	@Override
	public String GetTable() {
		return "API_JSON_USER_DETAIL";
	}

	public boolean Read(String zUsuario, String zJsonpath) throws Exception {
		addFilter("usuario", zUsuario);
		addFilter("json_path", zJsonpath);
		return this.read();
	}
	
	public void processInsert() throws Exception {
		super.insert();
	}
		
	public JMap<String, String> getAvailableApiPaths() throws Exception {
		if (this.availableApiPaths!=null) return this.availableApiPaths;
		JOrderedMap<String, String> map = JCollectionFactory.createOrderedMap();
		JOrderedMap<String, String> maptmp = apiWebServiceTools.getAnnotationsFrom(apiWebServiceTools.checkJavaDirectory());
		JIterator<String> iter = maptmp.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String keyandValue = key + maptmp.getElement(key);
			map.addElement(keyandValue, keyandValue);
			
		}
		
	  	return this.availableApiPaths= map;
	}	
	
	
	
	public static void checkTokenService(String usuario, String jsonPath) throws Exception {
		BizApiUserDetail rec = new BizApiUserDetail();
		rec.dontThrowException(true);
		if (!rec.Read(usuario,jsonPath))
			JExcepcion.SendError(" No existe habilitación para el servicio " + jsonPath);
		return;
	}

	
	
	/*
	//ArrayList<String> listClass = apiWebServiceTools.getClassNamesFromPackage("pss.tourism.tarifario");
	//File[] files = apiWebServiceTools.getPackageContent("pss/tourism/tarifario");
	JMap<String, String> maptmp = apiWebServiceTools.getAnnotationsFrom(apiWebServiceTools.checkJavaDirectory());
	
	@SuppressWarnings("rawtypes")
	Class classElement = apiJasonTarifario.class;
	Class anotationType = javax.ws.rs.Path.class;
	Annotation[] anotta = classElement.getAnnotationsByType(anotationType);
	
	String a = "1";
	//Parameter parameter = classElement.getAnnotation("Path");
	 * */
	 
	
}

