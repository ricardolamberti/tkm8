package  pss.common.security;

import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JBoolean;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiListOpers extends JWins {

	public GuiListOpers() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiListOper.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 44;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Lista Operaciones";
	}

	@Override
	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		return new BizListOpers();
	}

	@Override
	public void createActionMap() throws Exception {
	}
	
	@Override
	public void ConfigurarColumnasLista(JWinList lista) throws Exception {
		BizCompany company = new BizCompany();
		company.Read(this.getRecords().getFilterValue("company"));
		
		this.getWinRef().getRecord().createProperties();
		this.getWinRef().getRecord().createFixedProperties();
		lista.AddIcono("");
		lista.AddColumnaLista("descripcion").setActionOnClick(1);
//		lista.AddColumnaLista("has_security").setActionOnClick(15);
		JRecords<BizRol> cargos=new JRecords<BizRol>(BizRol.class);
		cargos.addFilter("company", company.getCompany());
		cargos.addFilter("tipo", company.getObjBusiness().hasRolesAplicacion() ? BizRol.NORMAL : BizRol.JERARQUICO);
		cargos.readAll();
		JIterator<BizRol> iter=cargos.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizRol rol = iter.nextElement();
			this.getWinRef().getRecord().addItem(String.valueOf(rol.getRol()), new JBoolean());
			this.getWinRef().getRecord().addFixedItem(BizListOper.FIELD, String.valueOf(rol.getRol()), rol.GetDescrip(), true, true, 60);
			lista.AddColumnaLista(rol.GetDescrip(), String.valueOf(rol.getRol())).setActionOnClick("acc_"+rol.getRol());
		}

	}

}
