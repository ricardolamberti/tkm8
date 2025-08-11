package pss.common.event.manager;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizEventCode extends JRecord {

	
	public static final String EVT_MAIL_RECIVE="EVT_MAIL_RECIVE";

	/********************************************
	 * Objeto de las acciones que se ejecutaran
	 ********************************************/
	private JString pEventCode = new JString(); 
	private JString pModulo = new JString();
	private JString pDescripcion = new JString();
	
	private JString pDescrModulo = new JString() {
		public void preset() throws Exception {
			setValue(getModuleDescription());
		}
	};
	private JString pDescrInterfaz = new JString();

	public static String MODULE_HO = "HO";
	public static String MODULE_FLEET = "FLEET";
	public static String MODULE_FC = "FC";
	public static String MODULE_TANK = "TANK";
	public static String MODULE_LEX = "LEX";
	public static String MODULE_CORE = "CORE";
	public static String MODULE_GDS = "GDS";
	public static String MODULE_RETAIL = "RETAIL";
	public static String MODULE_TOURISM = "TOURISM";
	public static String MODULE_STOCK = "STOCK";
	public static String MODULE_PRODUCT = "PRODUCT";



//	public static String[] getModuleClasses() {
//		return aModules;
//	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public String getInterfaz() throws Exception {
		return pDescrInterfaz.getValue();
	}

	@Override
	public void createProperties() throws Exception {
		addItem("event_code", pEventCode);
		addItem("modulo", pModulo);
		addItem("descripcion", pDescripcion);
		addItem("descr_modulo", pDescrModulo);
		addItem("descr_interfaz", pDescrInterfaz);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "event_code", "Código", false, false, 5);
		addFixedItem(FIELD, "modulo", "Módulo", true, true, 50);
		addFixedItem(FIELD, "descripcion", "Descripción", true, false, 150);
		addFixedItem(VIRTUAL, "descr_modulo", "Módulo", true, false, 150);
		addFixedItem(FIELD, "descr_interfaz", "Interfaz", true, false, 150);
	}

	public BizEventCode() throws Exception {
	}

	@Override
	public String GetTable() {
		return "";
	}

	public String getModulo() throws Exception {
		return pModulo.getValue();
	}

	public void setModulo(String sModulo) throws Exception {
		pModulo.setValue(sModulo);
	}

	public void setEventCode(String v) throws Exception {
		pEventCode.setValue(v);
	}

	public String getEventCode() throws Exception {
		return this.pEventCode.getValue();
	}

	public void setDescripcion(String v) throws Exception {
		pDescripcion.setValue(v);
	}

	public void setDescrInterfaz(String v) throws Exception {
		pDescrInterfaz.setValue(v);
	}

	public static BizEventCode findEventCode(String code) throws Exception {
		return BizEventCode.getEventsMap().getElement(code);
	}

	
//	private static String[] aModules = { 
//		"pss.ho.event.BizHOEvent", 
//		"pss.ho.fleet.event.BizFleetEvent", 
//		"pss.ho.fc.event.BizFcEvent", 
//		"pss.ho.tank.event.BizTankEvent", 
//		"pss.lex.event.BizLexEvent" , 
//		"pss.common.event.sql.BizSqlEventCode",
//		"pss.erp.event.BizRetailEvent",
//		"pss.tourim.event.BizTourismEvent"
//		};

	private static JMap<String, String> moduleMap;
	public static JMap<String, String> getModuleMap() throws Exception {
		if (moduleMap!=null) return moduleMap;
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(BizEventCode.MODULE_HO, "pss.ho.event.BizHOEvent");
		map.addElement(BizEventCode.MODULE_FLEET, "pss.ho.fleet.event.BizFleetEvent"); 
		map.addElement(BizEventCode.MODULE_FC, "pss.ho.fc.event.BizFcEvent");
		map.addElement(BizEventCode.MODULE_TANK, "pss.ho.tank.event.BizTankEvent"); 
		map.addElement(BizEventCode.MODULE_LEX, "pss.lex.event.BizLexEvent");
		map.addElement(BizEventCode.MODULE_CORE, "pss.common.event.BizCoreEvent");
		map.addElement(BizEventCode.MODULE_RETAIL, "pss.erp.event.BizRetailEvent");
		map.addElement(BizEventCode.MODULE_TOURISM, "pss.tourim.event.BizTourismEvent");
		map.addElement(BizEventCode.MODULE_STOCK, "pss.erp.stock.event.BizStockEvent");
		map.addElement(BizEventCode.MODULE_PRODUCT, "pss.erp.products.event.BizProductEvent");
		return (moduleMap=map);
	}

//	public static BizEventCode virtualCreate(int iOffset) throws Exception {
//		BizEventCode oEvent = (BizEventCode) Class.forName(aModules[iOffset]).newInstance();
//		return oEvent;
//	}

//	public static int getOffsetByModule(String sModulo) throws Exception {
//		int iOffset = -1;
//		if (sModulo.equalsIgnoreCase(MODULE_HO)) {
//			iOffset = 0;
//		} else if (sModulo.equalsIgnoreCase(MODULE_FLEET)) {
//			iOffset = 1;
//		} else if (sModulo.equalsIgnoreCase(MODULE_LEX)) {
//			iOffset = 4;
//		}  else if (sModulo.equalsIgnoreCase(MODULE_CORE)) {
//			iOffset = 5;
//		}else if (iOffset == -1) {
//			JExcepcion.SendError("Tipo de clase [" + sModulo + "] no encontrada");
//		}
//		return iOffset;
//	}

//	public static JRecords<BizVirtual> getModules() throws Exception {
//		JRecords<BizVirtual> oBDs = JRecords.createVirtualFormMap(getInstalledModules());
//		return oBDs;
//	}

//	public static JMap<String, String> getModuleEvents() throws Exception {
//		return getInstalledEvents();
//	}
	
//	protected static JMap<String, String> mapModuleEvents = null;
//	public static JMap<String, String> getModuleEvents(String module) throws Exception {
//		JIterator<String> it= mapModuleEventsDesc.getKeyIterator();
//		mapModuleEvents = JCollectionFactory.createOrderedMap();
//		while (it.hasMoreElements()) {
//			String code = it.nextElement();
//			String code1 = code.substring(0,code.indexOf("|"));
//			if (code1.equals(module)==false) continue;
//			String code2 = code.substring(code.indexOf("|")+1);
//		  String value = mapModuleEventsDesc.getElement(code);	
//		  mapModuleEvents.addElement(code2, value);
//		}
//		
//		return mapModuleEvents;
//	}

	protected static JMap<String, BizEventCode> mapEvents = null;
	public static JMap<String, BizEventCode> getEventsMap() throws Exception {
		if (mapEvents != null) return mapEvents;
		BizEventCode.createEvents();
		return mapEvents;
	}
	
	public static JRecords<BizEventCode> getAllEvents() throws Exception {
		JRecords<BizEventCode> recs = new JRecords<BizEventCode>(BizEventCode.class);
		recs.setStatic(true);
		JIterator<BizEventCode> iter = BizEventCode.getEventsMap().getValueIterator();
		while (iter.hasMoreElements()) {
			recs.addItem(iter.nextElement());
		}
		return recs;
	}


	private static void createEvents() throws Exception {
		JIterator<String> iter = BizEventCode.getModuleMap().getValueIterator();
		while (iter.hasMoreElements()) {
			String m = iter.nextElement();
			if (!JTools.isInstalled(m)) continue;
			BizEventCode bec = (BizEventCode) Class.forName(m).newInstance();
			bec.generateModuleEventCodeList();
		}
	}

//	private static JMap<String, String> createModules() throws Exception {
//		JMap<String, String> map = JCollectionFactory.createOrderedMap();
//		if (JTools.isInstalled(aModules[0])) map.addElement(MODULE_HO, "General");
//		if (JTools.isInstalled(aModules[1])) map.addElement(MODULE_FLEET, "Flota HeadOffice");
//		if (JTools.isInstalled(aModules[2])) map.addElement(MODULE_FC, "Playa HeadOffice");
//		if (JTools.isInstalled(aModules[3])) map.addElement(MODULE_TANK, "Tank HeadOffice");
//		if (JTools.isInstalled(aModules[4])) map.addElement(MODULE_LEX, "Lex");
//		if (JTools.isInstalled(aModules[5])) map.addElement(MODULE_CORE, "Core");
//		if (JTools.isInstalled(aModules[6])) map.addElement(MODULE_SITI, "SITI");
//		if (JTools.isInstalled(aModules[7])) map.addElement(MODULE_TOURISM, "Turismo");
//		return map;
//	}


	
	public String getModuleDescription() throws Exception {
		return null;
	}
	public String getEventModule() throws Exception {
		return null;
	}
	public void generateModuleEventCodeList() throws Exception {
	}


//	private static JMap<String, String> mapModules = null;
//	public static JMap<String, String> getInstalledModules() throws Exception {
//		if (mapModules != null) return mapModules;
//		return (mapModules=BizEventCode.createModules());
//	}
	
	public String getDescrCompleta() throws Exception { 
		return this.getDescripcion() + " ("+this.getEventModule()+")";
	}

	public void create(String codigo, String descrip) throws Exception {
		if (mapEvents==null) mapEvents=JCollectionFactory.createOrderedMap();
		BizEventCode ec = this.getClass().newInstance();
		ec.setModulo(this.getEventModule());
		ec.setEventCode(codigo);
		ec.setDescripcion(descrip);
		BizEventCode.getEventsMap().addElement(ec.getEventCode(), ec);
	}

	public final static long ACT_NOTIFY = 1L;
	public final static long ACT_MAIL = 2L;
	public final static long ACT_CHANNEL = 3L;
	public final static long ACT_JSON = 4L;
	
	protected static JMap<String, String> createEventActions() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizEventCode.ACT_NOTIFY+"", "Notificarme");
		map.addElement(BizEventCode.ACT_MAIL+"", "Enviarme Mail");
		map.addElement(BizEventCode.ACT_JSON+"", "Enviarme JSon");
		map.addElement(BizEventCode.ACT_CHANNEL+"", "Enviar a dispositivos");
		return map;
	}

	public static String getDescrAction(long action) throws Exception {
		return getEventActions().getElement(action+"");
	}

	protected static JMap<String, String> mapActions = null;
	public static JMap<String, String> getEventActions() throws Exception {
		if (mapActions != null) return mapActions; 
		return (mapActions=BizEventCode.createEventActions());
	}
	
	public JMap<String, String> getTransformers() throws Exception {
		return null;
	}
//	
//	public JMap<String, String> getSourceFilters() throws Exception {
//		return null;
//	}
//
//	public String transform(BizRegister register, BizEvent e) throws Exception {
//		return e.getInfo();
//	}
//	
//	public boolean checkFilters(BizRegister register, BizEvent e) throws Exception {
//		return true;
//	}
//
	public void processEvent(BizRegister r, BizEvent e) throws Exception {
		r.processAction(e);
	}
	
}
