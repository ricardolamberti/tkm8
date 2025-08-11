package  pss.common.security;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizOperacion extends JRecord {

	private static BizOperacion OPERATION_CONFIGURE_SECURITY;

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany=new JString();
	JString pOperacion=new JString();
	JString pDescrip=new JString();
	JInteger pNroIcono=new JInteger();
	
	private static JMap<String, JMap<String, BizOperacion>> aHash=null;

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String GetOperacion() throws Exception {
		return pOperacion.getValue();
	}

	public String GetDescrip() throws Exception {
		return pDescrip.getValue();
	}

	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public void SetOperacion(String zValue) {
		pOperacion.setValue(zValue);
	}

	public void SetDescrip(String zValue) {
		pDescrip.setValue(zValue);
	}

	public void setNroIcono(int value) {
		pNroIcono.setValue(value);
	}

	public int getNroIcono() throws Exception {
		if (pNroIcono.isNull()) return 44;
		return pNroIcono.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizOperacion() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("operacion", pOperacion);
		this.addItem("descripcion", pDescrip);
		this.addItem("nro_icono", pNroIcono);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 15);
		this.addFixedItem(KEY, "operacion", "Operación", true, true, 200);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 80);
		this.addFixedItem(FIELD, "nro_icono", "Nro Icono", true, false, 10);
	}

	@Override
	public String GetTable() {
		return "seg_operacion";
	}

	public static BizOperacion getConfigureSecurityOperation() throws Exception {
		if (OPERATION_CONFIGURE_SECURITY==null) {
			OPERATION_CONFIGURE_SECURITY=new BizOperacion();
			OPERATION_CONFIGURE_SECURITY.pCompany.setValue(BizUsuario.getUsr().getCompany());
			OPERATION_CONFIGURE_SECURITY.pOperacion.setValue("configure_security");
			OPERATION_CONFIGURE_SECURITY.pDescrip.setValue("Seguridad");
		}
		return OPERATION_CONFIGURE_SECURITY;
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(zParams.isLevelBusiness());
	}

	public boolean Read(String company, String zOperacion) throws Exception {
		addFilter("company", company);
		addFilter("operacion", zOperacion);
		return this.read();
	}

//	public void Delete(String zOperacion) throws Exception {
//		this.addFilter("operacion", zOperacion);
//		this.delete();
//	}

	public static void RemoveHash(BizOperacion zOper) throws Exception {
		JMap<String, BizOperacion> map = getHash(zOper.getCompany());
		map.removeElement(zOper.GetOperacion());
	}

	public static void InsertHash(BizOperacion zOper) throws Exception {
		JMap<String, BizOperacion> map = getHash(zOper.getCompany());
		map.addElement(zOper.GetOperacion(), zOper);
	}

	public static void UpdateHash(BizOperacion zOper) throws Exception {
		BizOperacion.RemoveHash(zOper);
		BizOperacion.InsertHash(zOper);
	}

	public static synchronized JMap<String, BizOperacion> getHash(String company) throws Exception {
		if (aHash==null) aHash = JCollectionFactory.createMap();
		JMap<String, BizOperacion> map = aHash.getElement(company);
		if (map!=null) return map;
		map = JCollectionFactory.createMap();
		JRecords<BizOperacion> oOpers=new JRecords<BizOperacion>(BizOperacion.class);
		oOpers.addFilter("company", company);
		oOpers.readAll();
		while (oOpers.nextRecord()) {
			BizOperacion oOpe=oOpers.getRecord();
			map.addElement(oOpe.GetOperacion(), oOpe);
		}
		aHash.addElement(company, map);
		return map;
	}

	// Poderosa línea de código
	public static synchronized void clearCache() {
		aHash=null;
	}

	@Override
	public void processDelete() throws Exception {
		this.getOperacionRoles().processDeleteAll();
		super.processDelete();
		BizOperacion.RemoveHash(this);
	}

	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		BizOperacion.InsertHash(this);

//		JRecords<BizUsuarioRol> oUsuRoles=new JRecords<BizUsuarioRol>(BizUsuarioRol.class);
//		oUsuRoles.addFilter("usuario", BizUsuario.getUsr().GetUsuario());
//		oUsuRoles.readAll();
//		while (oUsuRoles.nextRecord()) {
//			BizUsuarioRol oUsuRol=oUsuRoles.getRecord();
//			if (!oUsuRol.ObtenerRol().ifRolJerarquico()) continue;
//			BizOperacionRol oOpeRol=new BizOperacionRol();
//			oOpeRol.SetOperacion(pOperacion.getValue());
//			oOpeRol.SetRol(oUsuRol.GetRol());
//			oOpeRol.processInsert();
//		}
	}
	
	public JRecords<BizOperacionRol> getOperacionRoles() throws Exception {
		JRecords<BizOperacionRol> records=new JRecords<BizOperacionRol>(BizOperacionRol.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("operacion", this.GetOperacion());
		records.readAll();
		return records;
	}
	
}
