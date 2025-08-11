package  pss.common.security;

import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizOperacionRol extends JRecord {

	public static final String VISION_ROL="Rol";
	public static final String VISION_OPER="Oper";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pCompany = new JString();
	JString pOperacion = new JString();
	JInteger pRol = new JInteger();
	JBoolean pClaveSupervisor = new JBoolean();
	JString pDescrOper = new JString() {
		public void preset() throws Exception {
			pDescrOper.setValue(getObjOperacion().GetDescrip());
		}
	};
	JString pDescrRol = new JString() {
		@Override
		public void preset() throws Exception {
			pDescrRol.setValue(ObtenerRol().GetDescrip());
		}
	};

	BizOperacion oOperacion = null;
	BizRol oRol = null;

	public String GetOperacion() throws Exception {
		return pOperacion.getValue();
	}

	public int getRol() throws Exception {
		return pRol.getValue();
	}

	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public void setRol(int zValue) {
		pRol.setValue(zValue);
	}

	public void SetOperacion(String zValue) {
		pOperacion.setValue(zValue);
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizOperacionRol() throws Exception {
		addItem("company", this.pCompany);
		addItem("operacion", this.pOperacion);
		addItem("rol", this.pRol);
		addItem("clave_supervisor", this.pClaveSupervisor);
		addItem("descr_oper", this.pDescrOper);
		addItem("descr_rol", this.pDescrRol);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "OPERACION", "Operación", true, true, 200);
		addFixedItem(KEY, "ROL", "Rol", true, true, 30);
		addFixedItem(FIELD, "clave_supervisor", "Clave Sup.", true, false, 50);
		addFixedItem(VIRTUAL, "descr_oper", "Operacion", true, true, 60);
		addFixedItem(VIRTUAL, "descr_rol", "Rol", true, true, 60);
	}

	@Override
	public String GetTable() {
		return "seg_operacion_rol";
	}


	public boolean ifClaveSupervisor() throws Exception {
		return pClaveSupervisor.getValue();
	}

	@Override
	public void processInsert() throws Exception {
//		if (!hasFather()) {
//			JExcepcion.SendError("Ud. esta intentando asignarle permisos a una Operacion habilitada para Todos los Roles");
//		}
//		if (this.checkRecordExists()) return;
		this.pClaveSupervisor.setValue("N");
		super.processInsert();
	}

	@Override
	public void processDelete() throws Exception {
		// if (!BizUsuario.GetGlobal().ifRolInferior(pRol.GetValor())) {
		// JExcepcion.SendError("Permiso Negado");
		// }
		this.delete();
	}

	public BizOperacion getObjOperacion() throws Exception {
		if (oOperacion != null) return oOperacion;
		BizOperacion record = new BizOperacion();
		record.Read(pCompany.getValue(), pOperacion.getValue());
		return record;
	}

	public BizRol ObtenerRol() throws Exception {
		if (oRol != null) return oRol;
		BizRol record = new BizRol();
		record.Read(pCompany.getValue(), pRol.getValue());
		return (oRol=record);
	}

	public void execEliminarClaveSuperv() throws Exception {
		JExec oExec = new JExec(null, null) {
			public void Do() throws Exception {
				eliminarClaveSupervisor();
			}
		};
		oExec.execute();
	}

	public void eliminarClaveSupervisor() throws Exception {
		BizOperacionRol oOpeRol = (BizOperacionRol) getPreInstance();
		oOpeRol.pClaveSupervisor.setValue("N");
		oOpeRol.update();
	}

	public void execPermitirClaveSuperv() throws Exception {
		JExec oExec = new JExec(null, null) {
			public void Do() throws Exception {
				permitirClaveSupervisor();
			}
		};
		oExec.execute();
	}

	public void permitirClaveSupervisor() throws Exception {
		BizOperacionRol oOpeRol = (BizOperacionRol) getPreInstance();
		oOpeRol.pClaveSupervisor.setValue("S");
		oOpeRol.update();
	} /*
		 * private void checkPermission() throws Exception { if
		 * (!BizUsuario.GetGlobal().ifRolInferior(pRol.GetValor())) {
		 * JExcepcion.SendError("Permiso Negado"); } }
		 */
//
//	private boolean hasFather() throws Exception {
//		try {
//			this.getObjOperacion();
//			return true;
//		}
//		catch (Exception e) {
//			return false;
//		}
//	}

}
