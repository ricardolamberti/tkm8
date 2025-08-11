package  pss.common.security;

import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizUsuarioRol extends JRecord {

	
	public static final String VISION_ROL = "1";
	public static final String VISION_USER = "2";
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany = new JString();
	JString pUsuario = new JString();
	JInteger pRol = new JInteger();
	JString pDescrRol = new JString() {
		public void preset() throws Exception {
			pDescrRol.setValue(getObjRol().GetDescrip());
		}
	};
	JString pDescrUsuario = new JString() {
		public void preset() throws Exception {
			pDescrUsuario.setValue(getObjUsuario().GetDescrip());
		}
	};
	JString pRolJerarquico = new JString();

	public String getDescRol() throws Exception {
		
		return pDescrRol.getValue();
	}

	
	private BizRol oRol = null;
	private BizUsuario user = null;

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	
	public boolean isRolNull() throws Exception {
		return pRol.isNull();
	}
	
	public int getRol() throws Exception {
		return pRol.getValue();
	}

	public void SetUsuario(String zValue) {
		pUsuario.setValue(zValue);
	};

	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	};
	
	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}

	public void setRol(int zValue) {
		pRol.setValue(zValue);
	};
	
	public void setRol(long zValue) {
		pRol.setValue(zValue);
	};

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizUsuarioRol() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("usuario", pUsuario);
		addItem("rol", pRol);
		addItem("descr_rol", pDescrRol);
		addItem("descr_usuario", pDescrUsuario);
		addItem("rol_jerarquico", pRolJerarquico);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "usuario", "Usuario", true, true, 15);
		addFixedItem(KEY, "rol", "Rol", true, true, 15);
		addFixedItem(VIRTUAL, "descr_rol", "Rol", true, true, 60);
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario", true, true, 60);
		addFixedItem(VIRTUAL, "rol_jerarquico", "Rol Jerárquico", true, true, 60);
	}

	@Override
	public String GetTable() {
		return "seg_usuario_rol";
	}

	public BizRol getObjRol() throws Exception {
		if (oRol != null) return oRol;
		BizRol record = new BizRol();
		record.Read(this.pCompany.getValue(), this.pRol.getValue());
		return (oRol = record);
	}
	
	public BizUsuario getObjUsuario() throws Exception {
		if (user != null) return user;
		BizUsuario record = new BizUsuario();
		record.Read(this.pUsuario.getValue());
		return (user = record);
	}

}
