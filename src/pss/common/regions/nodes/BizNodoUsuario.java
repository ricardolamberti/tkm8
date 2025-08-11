package  pss.common.regions.nodes;

import pss.common.components.JSetupParameters;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

public class BizNodoUsuario extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany = new JString();
	// JString pCompanyDescripcion = new JString() { public void preset() throws
	// Exception {pCompanyDescripcion.setValue(
	// ObtenerCompany().getDescription());}};
	JString pNodo = new JString();
	JString pNodoDescripcion = new JString() {
		public void preset() throws Exception {
			setValue(getObjNodo().GetDescrip());
		}
	};
	JString pUsuario = new JString();
	JString pDescrUsuario = new JString() {
		public void preset() throws Exception {
			setValue(ObtenerUsuario().GetDescrip());
		}
	};
	JString pDescrNodo = new JString() {
		public void preset() throws Exception {
			setValue(getDescrNodo());
		}
	};

	public String getNode() throws Exception {
		return pNodo.getValue();
	}

	public String getUser() throws Exception {
		return pUsuario.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	
	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public void SetNode(String zValue) {
		pNodo.setValue(zValue);
	}

	public void SetUser(String zValue) {
		pUsuario.setValue(zValue);
	}

	BizUsuario oUsuario = null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizNodoUsuario() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("nodo", pNodo);
		this.addItem("usuario", pUsuario);
		this.addItem("descr_usuario", pDescrUsuario);
		this.addItem("descr_nodo", pDescrNodo);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 50);
		this.addFixedItem(KEY, "nodo", "Sucursal", true, true, 15);
		this.addFixedItem(KEY, "usuario", "Usuario", true, true, 15);
		this.addFixedItem(VIRTUAL, "descr_usuario", "Usuario", true, true, 100);
		this.addFixedItem(VIRTUAL, "descr_nodo", "Sucursal", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "nod_nodo_usuario";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelNode());
	}

	public BizUsuario ObtenerUsuario() throws Exception {
		if (oUsuario != null) return oUsuario;
		BizUsuario oUsuario = new BizUsuario();
		oUsuario.Read(pUsuario.getValue());
		this.oUsuario = oUsuario;
		return oUsuario;
	}

//	public BizNodo ObtenerNodo() throws Exception {
//		if (this.oNodo != null) return this.oNodo;
//		BizNodo oNodo = new BizNodo();
//		oNodo.Read(this.getCompany(), this.pNodo.getValue());
//		this.oNodo = oNodo;
//		return this.oNodo;
//	}

	public boolean Read(String zCompany, String zNodo, String user) throws Exception {
		this.addFilter("company", zCompany);
		this.addFilter("nodo", zNodo);
		this.addFilter("usuario", user);
		return this.read();
	}

	// -------------------------------------------------------------------------//
	// Procesar Alta
	// -------------------------------------------------------------------------//
	@Override
	public void processInsert() throws Exception {

		BizNodoUsuario oNodo = new BizNodoUsuario();
		oNodo.dontThrowException(true);
		if (oNodo.Read(this.pCompany.getValue(), this.pNodo.getValue(), this.pUsuario.getValue())) {
			JExcepcion.SendError("El usuario ya esta asociado a la Sucursal");
		}
		insertRecord();

	}

	public static String joinCompany(String zNodoUsuario, String zCompany) {
		String sWhere = "";
		sWhere += " and " + zCompany + ".company = " + zNodoUsuario + ".pais ";
		return sWhere;
	}

	public static String joinUsuario(String zNodoUsuario, String zUsuario) {
		String sWhere = "";
		sWhere += " and " + zUsuario + ".usuario = " + zNodoUsuario + ".usuario ";
		return sWhere;
	}
	
	public String getDescrNodo() throws Exception {
		return this.getObjNodo().GetDescrip();
	}
	
	BizNodo nodo = null;
	public BizNodo getObjNodo() throws Exception {
		if (this.nodo!=null) return this.nodo;
		return (this.nodo=this.getObjCompany().findNodo(this.getNode()));
	}

	BizCompany company = null;
	public BizCompany getObjCompany() throws Exception {
		if (this.company!=null) return this.company;
		return BizCompany.getCompany(this.getCompany());
	}

}
