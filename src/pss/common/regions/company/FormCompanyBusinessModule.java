package  pss.common.regions.company;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormComboResponsive;

public class FormCompanyBusinessModule extends JBaseForm {

	public FormCompanyBusinessModule() throws Exception {
	}

	public GuiCompany GetWin() {
		return (GuiCompany) getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormComboResponsive c;
		AddItemEdit("company", CHAR, REQ, "company");
		AddItemEdit("negocio", CHAR, REQ, "business");
    c=AddItemCombo( "module"   , CHAR, REQ, "module" , BizUsuario.getUsr().getObjBusiness().getBusinessModules());
    c.SetValorDefault("");		
    c.setFirstOcur(true);
	}
	
} 
