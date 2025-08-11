package  pss.common.security;

import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JBoolean;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizListOpers extends JRecords<BizListOper> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizListOpers() throws Exception {
	}

	@Override
	public Class<BizListOper> getBasedClass() {
		return BizListOper.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public boolean readAll() throws Exception {
		this.setStatic(true);
		BizCompany company = new BizCompany();
		company.Read(this.getFilterValue("company"));
		
		JRecords<BizRol> cargos=new JRecords<BizRol>(BizRol.class);
		cargos.addFilter("company", company.getCompany());
		cargos.addFilter("tipo", company.getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL : BizRol.JERARQUICO);
		cargos.readAll();
		cargos.toStatic();


		JRecords<BizOperacion> operaciones=new JRecords<BizOperacion>(BizOperacion.class);
		operaciones.addFilter("company", company.getCompany());
		operaciones.addOrderBy("descripcion", "desc");
		operaciones.readAll();
		operaciones.firstRecord();
		while (operaciones.nextRecord()) {
			BizOperacion oper=operaciones.getRecord();
			BizListOper list=new BizListOper();
			list.createProperties();
			list.createFixedProperties();
			list.setCompany(oper.getCompany());
			list.SetOperacion(oper.GetOperacion());
			list.SetDescrip(oper.GetDescrip());
			list.setNroIcono(oper.getNroIcono());
//			list.setObjOperacion(oper);
			JIterator<BizRol> iter=cargos.getStaticItems().getIterator();
			while (iter.hasMoreElements()) {
				BizRol rol=iter.nextElement();
				list.addItem(String.valueOf(rol.getRol()), new JBoolean());
//				list.addItem("sup_"+String.valueOf(rol.getRol()), new JBoolean());
				list.addFixedItem(BizListOper.FIELD, String.valueOf(rol.getRol()), rol.GetDescrip(), true, true, 60);
//				list.addFixedItem(BizListOper.FIELD, "sup_"+String.valueOf(rol.getRol()), "Sup", true, true, 60);

				BizOperacionRol opeRol=rol.getAllOperations().getElement(oper.GetOperacion());
				boolean permisoOk=opeRol!=null;
//				boolean claveSup=(opeRol==null) ? false : opeRol.ifClaveSupervisor();
				list.getProp(String.valueOf(rol.getRol())).setValue(permisoOk);
//				list.getProp("sup_"+String.valueOf(rol.getRol())).setValue(claveSup);
			}
			this.addItem(list);
		}
		this.firstRecord();
		return true;
	}

	@Override
	public void filledItems() throws Exception {
		// TODO Auto-generated method stub
	//	super.filledItems();
	}
}
