package  pss.common.regions.company;

import pss.common.event.mail.GuiMailConfs;
import pss.common.regions.nodes.GuiNodos;
import pss.common.security.BizUsuario;
import pss.common.security.GuiSegConfiguracion;
import pss.common.security.license.typeLicense.GuiTypeLicenses;
import pss.core.connectivity.messageManager.common.core.JMMWin;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiCompany extends JMMWin {

	boolean logoForm = false;  String title = "Empresa";	/**	 * @return the logoForm	 */	
	private boolean isLogoForm() {		return logoForm;	}	/**	 * @param logoForm the logoForm to set	 */	
	private void setLogoForm(boolean logoForm) {		this.logoForm = logoForm;	}		
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiCompany() throws Exception {}

	@Override
	public JRecord ObtenerDato() throws Exception {
		return new BizCompany();
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10072;
	}
	
	@Override
	public String GetTitle() throws Exception {
		return title;
	}

	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		if ( logoForm ) return FormCompanyLogo.class;
		return FormCompany.class;
	}

  @Override
	public String getKeyField() { return "company"; }
  @Override
	public String getDescripField() { return "description"; }

	// -------------------------------------------------------------------------//
	// Devuelvo el dato ya casteado
	// -------------------------------------------------------------------------//
	public BizCompany GetcDato() throws Exception {
		return (BizCompany) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		super.createActionMap();
		//this.addAction(10, "Sucursales", null, 77, true, false, true, "Group" );
		this.addAction(12, "Cierres", null, 77, true, false);
  	this.addAction(15, "Config. Seguridad", null, GuiIcon.SECURITY_ICON, true, true, true);
  	this.addAction(18, "Cambiar Logo", null, 5060, true, true, true, "Group" );
  	//this.addAction(19, "Modulos instalados", null, 5060, false, false, false, "Group" );
  	this.addAction(280, "Configurar Mail", null, 10026, true, true, true, "Group" );
//   	this.addAction(500, "Modelos Licencia", null, 912, true, true, true, "Group" );
  }

	@Override
	public boolean OkAction(BizAction act) throws Exception {
		if (act.getId()==15) return BizUsuario.getUsr().isAnyAdminUser();
		return true;
	}
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

		//if (id==10) return new JActWins(this.getNodos());
		if (a.getId()==12) return new JActWins(this.getGruposCierres());
		if (a.getId()==15 ) return new JActNew(this.getSecurityConfig(), 0);
  	if ( a.getId()==18 ) return new JActUpdate(this.getLogo(), JWin.ACTION_UPDATE);
  	if (a.getId()==19) return new JActWins(this.getBusinessModules());
  	if ( a.getId()==500 ) return new JActWins(getTypeLicenses());
  	if (a.getId()==280 ) return new JActWins(this.getMailServers());
		return null;
	}
	
  public JWin getLogo() throws Exception {
  	GuiCompany c = new GuiCompany();
  	c.getRecord().addFilter("company", this.GetcDato().getCompany());
  	c.setLogoForm(true);
  	title="Logo Empresa";
  	c.GetcDato().read();
  	return c;
  }

	public JWins getBusinessModules() throws Exception {
		GuiCompanyBusinessModules bModules = new GuiCompanyBusinessModules();
		bModules.getRecords().addFilter("company", this.GetcDato().getCompany());
		bModules.getRecords().addFilter("business", this.GetcDato().getBusiness());
		return bModules;
	}

	
  public JWins getMailServers() throws Exception {
  	GuiMailConfs c = new GuiMailConfs();
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
  	return c;
  }
  
  
  public JWin getSecurityConfig() throws Exception {
  	GuiSegConfiguracion win = new GuiSegConfiguracion();
  	win.GetcDato().dontThrowException(true);
  	win.GetcDato().Read(this.GetcDato().getCompany());
  	return win;
  }

	
//  public JWin getSecurityPolicies() throws Exception {
//  	GuiSegConfiguracion win = new GuiSegConfiguracion();
//  	win.GetcDato().Read(this.GetcDato().getCompany());
//  	return win;
//  }
	
	public JWins getNodos() throws Exception {
		GuiNodos nodes = new GuiNodos();
		nodes.getRecords().addFilter("company", this.GetcDato().getCompany());
		return nodes;
	}
	
	
	
	public JWins getTypeLicenses() throws Exception {
		GuiTypeLicenses lic = new GuiTypeLicenses();
		lic.getRecords().addFilter("company", this.GetcDato().getCompany());
		return lic;
	}
	
	public JWins getGruposCierres() throws Exception {
		/*GuiGrupoCierres cierres = new GuiGrupoCierres();
		cierres.getRecords().addFilter("company", this.GetcDato().getCompany());
		return cierres;*/
		return null;
	}

	public static GuiCompany VirtualCreateType(String zTipo) throws Exception {
  	return JCompanyBusinessModules.getInstanceFor(zTipo).getWinInstance();
  }

  @Override
	public Class getActionOwnerClass() {
  	return GuiCompany.class; 
  }


  
}
