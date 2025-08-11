package  pss.common.security.type;

import pss.common.customMenu.GuiMenus;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.common.security.BizWebUserProfile;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormUsuarioTipo extends JBaseForm {

	public FormUsuarioTipo() throws Exception {
  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive col = this.AddItemColumn(12);
    JFormPanelResponsive row = col.AddItemRow();
    row.AddItemEdit("company", CHAR, OPT, "company").hide();
    JFormPanelResponsive row2 = row.AddItemRow();
    row2.AddItemEdit( "Tipo", CHAR, REQ, "tipo_usuario", 2 );
    row2.AddItemEdit( "Descripción", CHAR, REQ, "descripcion", 10);

    JFormPanelResponsive row3 = col.AddItemRow();
    row3.AddItemCombo("Nacionalidad", CHAR, OPT, "birth_country", new JControlCombo() {
    	public JWins getRecords() throws Exception {
    		return new GuiPaises();
    	};
    }, 3);
    row3.AddItemCombo("Lenguaje", CHAR, OPT, "lenguaje", new JControlCombo() {
    	public JWins getRecords() throws Exception {
    		return JWins.CreateVirtualWins(JLanguage.ObtenerIdiomas());
    	};
    }, 3);
    		
//    col.AddItemCombo("Skin", CHAR, OPT, "skin", (JWins)null);//JWins.CreateVirtualWins(JAvailableSkins.getSkins()));
    row3.AddItemCombo("Skin web", CHAR, OPT, "skin_web", new JControlCombo() {
    	public JWins getRecords() throws Exception {
    		return BizWebUserProfile.getSkins();
    	};
    }, 3);
    JFormPanelResponsive row4 = col.AddItemRow();
       		
    row4.AddItemCombo("Home", CHAR, OPT, "home_web", new JControlCombo() {
    	public JWins getRecords() throws Exception {
    		return getHomePages();
    	};
    }, 3);
    row4.AddItemEdit("Page Web", LONG, OPT, "page_web", 3);
    JFormPanelResponsive row7 = col.AddItemRow();
    row7.AddItemEdit("Menu", LONG, OPT, "matrix_web", 3).SetValorDefault(5000);
    row7.AddItemCombo("Menu", CHAR, OPT, "custom_menu", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getMenues(zOneRow);
			};
		}, 3);
    JFormPanelResponsive row5 = col.AddItemRow();
    row5.AddItemEdit("Expira", LONG, OPT, "expire", 3);
    row5.AddItemEdit("Retries", LONG, OPT, "retries_clave", 3);
    
    JFormPanelResponsive row6 = col.AddItemRow();
    row6.AddItemCheck("Loguear", OPT, "loguear", 2);
    row6.AddItemCheck("Cambio clave", OPT, "has_changekey", 2);
    row6.AddItemCheck("Ayuda", OPT, "has_help", 2);
    row6.AddItemCheck("Preferencias", OPT, "has_preference", 2);
    row6.AddItemCheck("Mail", OPT, "has_mail", 2);
    row6.AddItemCheck("Multi-ventanas", OPT, "has_multi", 2);
    row6.AddItemCheck("Barra navegador", OPT, "has_navigationbar", 2);
    row6.AddItemCheck("Seguridad indefinida", OPT, "view_inseg", 2);
    row6.AddItemCheck("Acceso remoto", OPT, "can_outaccess", 2);
    row6.AddItemCheck("Clave Fuerte", OPT, "strong_pass", 2);
				
		
  }
	private JWins getHomePages() throws Exception {
		if (BizUsuario.getUsr().getObjCompany() == null)
			return null;
		JCompanyBusiness bus = JCompanyBusinessModules.getInstanceFor(BizUsuario.getUsr().getObjCompany().getBusiness());
		return JWins.createVirtualWinsFromMap(bus.getBusinessHomePages());
	}
	public GuiUsuarioTipo GetWin() {
		return (GuiUsuarioTipo) getBaseWin();
	}

  
	public JWins getMenues(boolean one) throws Exception {
		GuiMenus m = new GuiMenus();
		if (one) {
			m.getRecords().addFilter("id_menu", GetWin().GetcDato().getCustomMenu());
		} else {
			m.getRecords().addFilter("business", BizUsuario.getUsr().getObjCompany().getBusiness());
		}
		return m;
	}

	public JWins getCompanies(boolean zOneRow) throws Exception {
		GuiCompanies nodos = new GuiCompanies();
		if (zOneRow) {
			nodos.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
		}
		return nodos;
	}


}
