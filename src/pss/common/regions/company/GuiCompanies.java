package  pss.common.regions.company;

import pss.core.connectivity.messageManager.common.core.JMMWins;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiCompanies extends JMMWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCompanies() throws Exception {
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5000;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Empresas";
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiCompany.class;
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		this.addActionNew(1, "Nueva Empresa");
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewCompany(), 0);
		return this.getSubmitFor(a);
	}

	public GuiCompanyAlta getNewCompany() throws Exception {
		GuiCompanyAlta newCompany=new GuiCompanyAlta();
		this.joinData(newCompany);
		return newCompany;
	}

	@Override
	public JRecords<BizCompany> ObtenerDatos() throws Exception {
		return new BizCompanies();
	}

	@Override
	public JWin createWin(JRecord zBD) throws Exception {
		if (zBD==null) return super.createWin(zBD);
		JWin oWin=GuiCompany.VirtualCreateType(((BizCompany) zBD).getBusiness());
		oWin.setRecord(zBD);
		return oWin;
	}

	// public void readAll() throws Exception {
	// if (!BizUsuario.getUsr().isAdminUser()) {
	// getRecords().addFixedFilter("where (company is null or company='"+BizUsuario.getUsr().getCompany()+"') ");
	// }
	// super.readAll();
	// }

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("description");
	}
	
	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Empresa", "description").setOperator("like");
	}

}
