package  pss.common.security;

import pss.common.customMenu.GuiMenus;
import pss.common.event.mail.GuiMailsSender;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaises;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.regions.nodes.GuiNodos;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.connectivity.messageManager.common.core.JMMBaseForm;
import pss.core.win.GuiVirtual;
import pss.core.win.GuiVirtuals;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormUsuario extends JMMBaseForm {

	public FormUsuario() throws Exception {
	}


	public GuiUsuario GetWin() {
		return (GuiUsuario) getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControlResponsive c;
		AddItemEdit("pais", CHAR, OPT, "pais");
		AddItemEdit("Usuario", CHAR, REQ, "usuario");
		AddItemEdit("Descrip", CHAR, REQ, "descripcion");
		AddItemCheck("Activo", OPT, "activo").SetReadOnly(true);;
		AddItemCombo("Tipo Usuario", CHAR, OPT, "tipo_usuario", new GuiUsuarioTipos());
		AddItemCheck("system user", REQ, "is_system_user").SetReadOnly(true);
		c=AddItemWinLov("company", CHAR, REQ, "company", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getCompanies(zOneRow);
			}
			public void Blanquear() throws Exception {
				getControles().findControl("nodo").clear();
			};
		});
		c.SetReadOnly(true);
		AddItemWinLov("Nodo", CHAR, OPT, "nodo", new JControlWin() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getNodos(zOneRow);
			};
		});
		AddItemEdit("Legajo", CHAR, OPT, "LEGAJO");

		// opcionales; verificados en BizUsuario
		AddItemEdit("LONGITUD_CLAVE", CHAR, OPT, "LONGITUD_CLAVE").setPopupIcon(false);
		AddItemEdit("Reintentos Clave", CHAR, OPT, "retries_clave").setPopupIcon(false);
		AddItemEdit("CADUCIDAD", CHAR, OPT, "INTERVALO_CADUCIDAD").setPopupIcon(false);

		AddItemCombo("eMail", CHAR, OPT, "mail", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getMails(zOneRow);
			};
		});

		AddItemCombo("Fecha Nacimiento", CHAR, REQ, "birth_country", GuiPaises.getPaises());
		// opcionales; verificados en BizUsuario
		AddItemCombo("Lenguaje", CHAR, OPT, "lenguaje", JWins.CreateVirtualWins(JLanguage.ObtenerIdiomas()));
//		AddItem(SKIN, CHAR, OPT, "skin", JWins.CreateVirtualWins(JAvailableSkins.getSkins()));
		AddItemCombo("Menu", CHAR, OPT, "custom_menu", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getMenues(zOneRow);
			};
		});
		
		AddItem("Expira", LONG, OPT, "expire");
		AddItemCombo("Cambia Clave", CHAR, OPT, "has_changekey",  getSioNo());
		AddItemCombo("Help", CHAR, OPT, "has_help",  getSioNo());
		AddItemCombo("Pref", CHAR, OPT, "has_preference",  getSioNo());
		AddItemCombo("Mail", CHAR, OPT, "has_mail",  getSioNo());
		AddItemCombo("Multi", CHAR, OPT, "has_multi", getSioNo());
		AddItemCombo("Barra nav", CHAR, OPT, "has_navigationbar",  getSioNo());
		AddItemCombo("Acceso", CHAR, OPT, "can_outaccess",  getSioNo());
		AddItemCombo("Tiene Movil", CHAR, OPT, "has_mobile",  getSioNo());
		AddItemCombo("Autoriza x LDAP", CHAR, OPT, "has_ldap",  getSioNo());
	
		JFormTabPanelResponsive tab = this.AddItemTabPanel();
		tab.AddItemTab(7);
		tab.AddItemTab(40);
	}

	public JWins getSioNo() throws Exception {
		GuiVirtuals v = new GuiVirtuals();
		v.SetEstatico(true);
		GuiVirtual si=new GuiVirtual();
		si.GetcDato().setValor("S");
		si.GetcDato().setDescripcion("Si");
		v.addRecord(si);
		GuiVirtual no=new GuiVirtual();
		no.GetcDato().setValor("N");
		no.GetcDato().setDescripcion("No");
		v.addRecord(no);		
		
		return v;
	}
	public JWins getNodos(boolean zOneRow) throws Exception {
		GuiNodos nodos = new GuiNodos();
		if (zOneRow) {
			nodos.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
			nodos.getRecords().addFilter("nodo", GetWin().GetcDato().getNodo());
		} else {
			nodos.getRecords().addFilter("company", getControles().findControl("company").getValue());
		}
		return nodos;
	}
	public JWins getMails(boolean zOneRow) throws Exception {
		GuiMailsSender nodos = new GuiMailsSender();
	    nodos.getRecords().addFilter("company", getControles().findControl("company").getValue());
		return nodos;
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
