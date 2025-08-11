package  pss.common.security;

import java.util.Iterator;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizRol extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany=new JString();
	JInteger pRol=new JInteger();
	JString pDescrip=new JString();
	JString pTipo=new JString();
	private JMap<String, BizRol> rolesVinculados=null;
	private JMap<String, BizOperacionRol> aOperationsMap=null;

	public static final String JERARQUICO="J";
	public static final String NORMAL="N";

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public int getRol() throws Exception {
		return pRol.getValue();
	}

	public String GetDescrip() throws Exception {
		return pDescrip.getValue();
	}

	public String GetTipo() throws Exception {
		return pTipo.getValue();
	}
	
	public void setCompany(String value) {
		this.pCompany.setValue(value);
	}

	public void setRol(int value) {
		if (value==-1) {
			this.pRol.setNull();
			return;
		}
		this.pRol.setValue(value);
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRol() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("rol", pRol);
		this.addItem("descripcion", pDescrip);
		this.addItem("tipo", pTipo);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, false, 15);
		addFixedItem(KEY, "rol", "Rol", true, true, 5);
		addFixedItem(FIELD, "descripcion", "Descripción", true, true, 100);
		addFixedItem(FIELD, "tipo", "Tipo", true, true, 1);
	}

	@Override
	public String GetTable() {
		return "SEG_ROL";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(true);
		zParams.setTruncateData(zParams.isLevelBusiness());
	}
	
	public boolean Read(long zRol) throws Exception {
		this.addFilter("rol", zRol);
		return this.read();
	}

	public boolean Read(String company, int zRol) throws Exception {
		this.addFilter("company", company);
		this.addFilter("rol", zRol);
		return this.read();
	}

	@Override
	public void processInsert() throws Exception {
		if (pTipo.isNull()) pTipo.setValue(BizRol.NORMAL);
		if (pRol.isNull()) {
			BizRol max = new BizRol();
			max.addFilter("company", this.pCompany.getValue());
			pRol.setValue(max.SelectMaxInt("rol")+1);
		}
		super.insert();
	}

	@Override
	public void processDelete() throws Exception {

		this.getOperacionRoles().processDeleteAll();
		this.getUsuarioRoles().processDeleteAll();
		this.getRolesVinculados1().processDeleteAll();
		this.getRolesVinculados2().processDeleteAll();

		super.delete();
	}

	public boolean ifRolJerarquico() throws Exception {
		return pTipo.getValue().equals(BizRol.JERARQUICO);
	}

	public boolean ifRolNormal() throws Exception {
		return pTipo.getValue().equals(BizRol.NORMAL);
	}

//	public static String GetDescripcionReporte(String zValor) throws Exception {
//		String sDesc="";
//		sDesc="Rol: ";
//		if (zValor.equals("")) sDesc+="< Todos >";
//		else {
//			BizRol oRol=new BizRol();
//			oRol.Read(zValor);
//			sDesc+=zValor+"-"+oRol.GetDescrip();
//		}
//		return sDesc;
//	}

	private JMap<String, BizRol> allVinculedRoles() throws Exception {
		if (rolesVinculados!=null) return rolesVinculados;
		JMap<String, BizRol> map=JCollectionFactory.createMap();
		this.appendRoles(map);
		return (rolesVinculados =map);
	}

	private void appendRoles(JMap<String, BizRol> map) throws Exception {
		JRecords<BizRolVinculado> records=new JRecords<BizRolVinculado>(BizRolVinculado.class);
		records.addFilter("company", this.pCompany.getValue());
		records.addFilter("rol", this.pRol.getValue());
		records.readAll();
		JIterator<BizRolVinculado> iter = records.getStaticIterator(); 
		while (iter.hasMoreElements()) {
			BizRolVinculado vinc=iter.nextElement();
			if (map.containsKey(String.valueOf(vinc.getRolVinculado()))) continue;
			map.addElement(String.valueOf(vinc.getRolVinculado()), vinc.getObjRolVinc());
			vinc.getObjRolVinc().appendRoles(map);
		}

	}

	public JRecords<BizOperacionRol> getOperations() throws Exception {
		JRecords<BizOperacionRol> opers=new JRecords<BizOperacionRol>(BizOperacionRol.class);
		opers.addFilter("company", this.pCompany.getValue());
		opers.addFilter("rol", this.pRol.getValue());
		opers.readAll();
		return opers;
	}

	public static void appendOperations(JMap<String, BizOperacionRol> target, JMap<String, BizOperacionRol> source) throws Exception {
		Iterator<BizOperacionRol> iter=source.valueIterator();
		while (iter.hasNext()) {
			BizOperacionRol opeRol=iter.next();
			addToHash(target, opeRol);
		}
	}

	private void appendOperations(JMap<String, BizOperacionRol> map, JRecords<BizOperacionRol> opers) throws Exception {
		JIterator<BizOperacionRol> iter = opers.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizOperacionRol opeRol=iter.nextElement();
			addToHash(map, opeRol);
		}
	}

	private static void addToHash(JMap<String, BizOperacionRol> map, BizOperacionRol opeRol) throws Exception {
		BizOperacionRol oper=map.getElement(opeRol.GetOperacion());
		if (oper==null) 
			map.addElement(opeRol.GetOperacion(), opeRol);
		else {
			oper.pClaveSupervisor.setValue(oper.pClaveSupervisor.getValue()&&opeRol.ifClaveSupervisor());
		}
	}

	public JMap<String, BizOperacionRol> getAllOperations() throws Exception {
		if (aOperationsMap!=null) return aOperationsMap;
		JMap<String, BizOperacionRol> map =JCollectionFactory.createMap();
		this.appendOperations(map, this.getOperations());

		JIterator<BizRol> iter=this.allVinculedRoles().getValueIterator();
		while (iter.hasMoreElements()) {
			BizRol rol=iter.nextElement();
			rol.appendOperations(map, rol.getOperations());
		}
		return (aOperationsMap=map);
	}
	
	public JRecords<BizOperacionRol> getOperacionRoles() throws Exception { 
		JRecords<BizOperacionRol> records=new JRecords<BizOperacionRol>(BizOperacionRol.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("rol", pRol.getValue());
		records.readAll();
		return records;
	}

	public JRecords<BizUsuarioRol> getUsuarioRoles() throws Exception { 
		JRecords<BizUsuarioRol> records=new JRecords<BizUsuarioRol>(BizUsuarioRol.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("rol", pRol.getValue());
		records.readAll();
		return records;
	}

	public JRecords<BizRolVinculado> getRolesVinculados1() throws Exception { 
		JRecords<BizRolVinculado> records=new JRecords<BizRolVinculado>(BizRolVinculado.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("rol", pRol.getValue());
		records.readAll();
		return records;
	}

	public JRecords<BizRolVinculado> getRolesVinculados2() throws Exception { 
		JRecords<BizRolVinculado> records=new JRecords<BizRolVinculado>(BizRolVinculado.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("rol_vinculado", pRol.getValue());
		records.readAll();
		return records;
	}



	/*
	 * public JBD AddItem(BizRol zRol) throws Exception { this.FirstRecord(); while ( this.NextRecord() ) { BizRol oRol = (BizRol) this.GetRecord(); if ( oRol.pRol.GetValor().equals(zRol.pRol.GetValor())) return zRol; } return super.AddItem(zRol); }
	 * 
	 * private void AgregarRol(String zRol) throws Exception { BizRol oRol = new BizRol(); oRol.SetNoExcSelect(true); if ( !oRol.Read(zRol) ) JExcepcion.SendError("Rol Vinculado Inexistente^: "+zRol); if ( isJerarquia() && !oRol.ifRolJerarquico() ) return; if ( isNormal() && !oRol.ifRolNormal() ) return; AddItem(oRol); }
	 * 
	 * private boolean isJerarquia() throws Exception { return GetVision().equals("Jerarquia"); } private boolean isNormal() throws Exception { return GetVision().equals("Normal"); }
	 */
}
