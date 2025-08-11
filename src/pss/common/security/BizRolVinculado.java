package  pss.common.security;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

public class BizRolVinculado extends JRecord {

	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	private JString pCompany = new JString();
	private JInteger pRol = new JInteger();
	private JInteger pRolVinculado = new JInteger();
	JString pDescrRolVinc = new JString() {
		@Override
		public void preset() throws Exception {
			pDescrRolVinc.setValue(getObjRolVinc().GetDescrip());
		}
	};

	BizRol oRolVinc = null;
	BizRol oRol = null;

	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}
	
	public int getRolVinculado() throws Exception {
		return this.pRolVinculado.getValue();
	}

	public void setCompany(String value) {
		this.pCompany.setValue(value);
	}

	
	public void setRol(int value) {
		this.pRol.setValue(value);
	}
	
	public void setRolVinculado(int value) {
		this.pRolVinculado.setValue(value);
	}

	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizRolVinculado() throws Exception {}
	
	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("rol", pRol);
		addItem("rol_vinculado", pRolVinculado);
		addItem("descr_rolvinc", pDescrRolVinc);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "rol", "Rol", true, true, 30);
		addFixedItem(KEY, "rol_vinculado", "Rol Vinculado", true, true, 30);
		addFixedItem(VIRTUAL, "descr_rolvinc", "Rol Vinculado", true, true, 60);
	}

	@Override
	public String GetTable() {
		return "seg_rol_vinculado";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
    zParams.setExportData(true);
    zParams.setTruncateData(zParams.isLevelBusiness());
	}

	@Override
	public void processInsert() throws Exception {
		if (this.pRol.getValue()==this.pRolVinculado.getValue())
			JExcepcion.SendError("No se puede vincular un rol a si mismo");
		super.processInsert();
	}

	public BizRol getObjRolVinc() throws Exception {
		if (oRolVinc != null) return oRolVinc;
		BizRol record = new BizRol();
		record.Read(pCompany.getValue(), pRolVinculado.getValue());
		return (oRolVinc = record );
	}

	public BizRol getObjRol() throws Exception {
		if (oRol != null)	return oRol;
		BizRol record = new BizRol();
		record.Read(pCompany.getValue(), pRol.getValue());
		return (oRol=record);
	}

}
