package pss.bsp.bspBusiness;

import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormNewBSPCompany extends JBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -824914234250493196L;


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewBSPCompany() throws Exception {

	}

	public GuiNewBSPCompany GetWin() {
		return (GuiNewBSPCompany) GetBaseWin();
	}

	

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemRow().AddItemEdit("Sigla", CHAR, REQ, "company").setSizeColumns(2);
		AddItemRow().AddItemEdit("Description", CHAR, REQ, "description").setSizeColumns(6);
		AddItemRow().AddItemEdit("Negocio", CHAR, OPT, "business").setSizeColumns(6).SetValorDefault(GetWin().GetcDato().getBusiness());
		AddItemRow().AddItemCombo("Modelo", CHAR, OPT, "modelo", new JControlCombo() {@Override
		public JWins getRecords(boolean one) throws Exception {
			return getModelos(one);
		} }).setSizeColumns(6);
		AddItemRow().AddItemCombo("Pais", CHAR, REQ, "pais", new GuiPaisesLista().addOrderAdHoc("descripcion", "ASC")).setSizeColumns(6);
		AddItemRow().AddItemEdit("Email", CHAR, REQ, "email").setSizeColumns(6);
		AddItemRow().AddItemEdit( "Licencias", UINT, OPT, "licencias" ).setSizeColumns(2);
		JFormPanelResponsive row=AddItemRow();
		row.AddItemDateTime( "Fecha incio", DATE, OPT, "fecha_incio" ).setSizeColumns(6);
		row.AddItemDateTime( "Fecha hasta", DATE, OPT, "fecha_hasta" ).setSizeColumns(6);
		AddItemRow().AddItemEdit( "Renovaciones", UINT, OPT, "renovaciones" ).setSizeColumns(2).SetValorDefault(0);
		AddItemRow().AddItemCombo( "Tipo licencia", CHAR, REQ, "tipo_licencia", new GuiTypeLicenses() ).setFirstOcur(true).setSizeColumns(6);

	}
	
	public JWins getModelos(boolean one) throws Exception {
		GuiCompanies comp = new GuiCompanies();
		if (one) {
			comp.getRecords().addFilter("company", GetWin().GetcDato().getModelo());
		}
		comp.getRecords().addFilter("company", "_","like");
		return comp;
	}
	

	


}  //  @jve:decl-index=0:visual-constraint="40,6"
