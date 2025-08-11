package  pss.common.security;

import pss.core.services.fields.JBoolean;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizListOper extends BizOperacion {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JBoolean pHasSecurity=new JBoolean() {
		public void preset() throws Exception {
			pHasSecurity.setValue(hasSecurity());
		}
	};



//	public void setObjOperacion(BizOperacion value) throws Exception {
//		oOperacion = value;
//	}
//
//	private BizOperacion oOperacion=null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizListOper() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		super.createProperties();
		addItem("has_security", this.pHasSecurity);
	}

	@Override
	public void createFixedProperties() throws Exception {
		super.createFixedProperties();
		addFixedItem(FIELD, "has_security", "Seguridad?", true, true, 30);
	}

	@Override
	public String GetTable() {
		return "";
	}

	public synchronized JRecords<BizRol> getRolesJerarquicos() throws Exception {
		JRecords<BizRol> records=new JRecords<BizRol>(BizRol.class);
		records.addFilter("company", pCompany.getValue());
		records.addFilter("tipo", BizRol.JERARQUICO);
		records.readAll();
		return records;
	}

//	public BizOperacion getObjOperacion() throws Exception {
//		if (oOperacion!=null) return oOperacion;
////		BizOperacion record=new BizOperacion();
////		record.dontThrowException(true);
////		if (!record.Read(pCompany.getValue(), pOperacion.getValue()))
////			return null;
//		return (oOperacion=BizOperacion.getHash(pCompany.getValue()).getElement(pOperacion.getValue()));
//	}
	
	public boolean hasSecurity() throws Exception {
		return BizOperacion.getHash(pCompany.getValue()).getElement(pOperacion.getValue())!=null;
	}

//	@Override
//	public void processDelete() throws Exception {
//		getObjOperacion().processDelete();
//	}
	
//	public void execDeshabilitarPermisos() throws Exception {
//		JExec oExec = new JExec(null, null) {
//			public void Do() throws Exception {
//				deshabilitarPermisos();
//			}
//		};
//		oExec.execute();
//	}
	
//	public void execHabilitarDeshabilitarPermisos() throws Exception {
//		JExec oExec = new JExec(null, null) {
//			public void Do() throws Exception {
//				deshabilitarHabilitarPermisos();
//			}
//		};
//		oExec.execute();
//	}
//	public void deshabilitarHabilitarPermisos() throws Exception {
//		BizOperacion oOpe = new BizOperacion();
//		oOpe.dontThrowException(true);
//		if (oOpe.Read(this.pCompany.getValue(), this.pOperacion.getValue()))
//			deshabilitarPermisos();
//		else
//			habilitarPermisos();
//	
//	}
//	public void deshabilitarPermisos() throws Exception {
//		BizOperacion oOpe = new BizOperacion();
//		oOpe.Read(this.pCompany.getValue(), this.pOperacion.getValue());
//		oOpe.processDelete();
//	}
	public void cambiarPermisos(BizRol rol) throws Exception {
		BizOperacion oper = new BizOperacion();
		oper.dontThrowException(true);
		if (!oper.Read(this.pCompany.getValue(), this.pOperacion.getValue()))
					oper = this.habilitarPermisos();
		BizOperacionRol opRol = new BizOperacionRol();
		opRol.dontThrowException(true);
		opRol.addFilter("company", getCompany());
		opRol.addFilter("operacion", GetOperacion());
		opRol.addFilter("rol", rol.getRol());
		if (opRol.read()) {
			opRol.processDelete();
		} else {
			opRol.setCompany(getCompany());
			opRol.SetOperacion(GetOperacion());
			opRol.setRol(rol.getRol());
			opRol.processInsert();
			// si todos los roles tienen seguridad borro la operacion
			BizRol rec=new BizRol();
			rec.addJoin(" left outer join seg_operacion_rol on seg_operacion_rol.company=seg_rol.company and seg_operacion_rol.rol=seg_rol.rol and seg_operacion_rol.operacion='"+this.GetOperacion()+"'", true);
			rec.addFixedFilter(" where seg_operacion_rol.operacion is null");
			rec.addFilter("company",  this.getCompany());
			rec.dontThrowException(true);
			if (!rec.read()) 
				oper.processDelete();
		}
		
	}

//	public void execHabilitarPermisos() throws Exception {
//		JExec oExec = new JExec(null, null) {
//			public void Do() throws Exception {
//				habilitarPermisos();
//			}
//		};
//		oExec.execute();
//	}

	public BizOperacion habilitarPermisos() throws Exception {
		BizOperacion oper = new BizOperacion();
		oper.setCompany(this.pCompany.getValue());
		oper.SetOperacion(this.pOperacion.getValue());
		oper.SetDescrip(this.pDescrip.getValue());
		oper.setNroIcono(this.pNroIcono.getValue());
		oper.processInsert();
		JRecords<BizRol> roles=new JRecords<BizRol>(BizRol.class);
		roles.addFilter("company", this.getCompany());
		roles.readAll();
		JIterator<BizRol> iter = roles.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizRol rol = iter.nextElement();
			BizOperacionRol opRol = new BizOperacionRol();
			opRol.setCompany(this.getCompany());
			opRol.SetOperacion(this.GetOperacion());
			opRol.setRol(rol.getRol());
			opRol.processInsert();
		}
		return oper;
	}

}
