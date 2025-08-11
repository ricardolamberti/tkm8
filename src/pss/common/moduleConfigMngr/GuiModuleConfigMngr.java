package  pss.common.moduleConfigMngr;

import pss.common.moduleConfigMngr.actions.GuiModuleConfigurationActions;
import pss.common.moduleConfigMngr.transactions.GuiModuleConfigTransactions;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiModuleConfigMngr extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiModuleConfigMngr() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizModuleConfigMngr();
	}

	public int GetNroIcono() throws Exception {
		return 1;
	}

	public String GetTitle() throws Exception {
		return "Configuracion Modulo";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormModuleConfigMngr.class;
	}

	public String getKeyField() throws Exception {
		return "module_id";
	}

	public String getDescripField() {
		return "module_id";
	}

	public BizModuleConfigMngr GetcDato() throws Exception {
		return (BizModuleConfigMngr) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		// super.createActionMap();
		createActionQuery();
		this.addAction(99, "Transacciones", null, 924, true, true, false);
		this.addAction(98, "Acciones", null, 410, true, true, false);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		switch (a.getId()) {
		case 99: return new JActWins(getModuleConfigTransaction());
		case 98: return new JActWins(getModuleConfigActions());
		}
	  return super.getSubmitFor(a);
	}
	
 protected GuiModuleConfigTransactions getModuleConfigTransaction() throws Exception {
	 GuiModuleConfigTransactions oGMCT = new GuiModuleConfigTransactions();
	 oGMCT.GetBaseDato().addFilter( "company",  this.GetcDato().getCompany() ); 
	 oGMCT.GetBaseDato().addFilter( "country",  this.GetcDato().getCountry() ); 
	 oGMCT.GetBaseDato().addFilter( "node",  this.GetcDato().getNode() ); 
	 oGMCT.GetBaseDato().addFilter( "module_id",  this.GetcDato().getModuleId() ); 
	 oGMCT.GetBaseDato().addFilter( "config_type",  this.GetcDato().getConfigType() ); 
	 oGMCT.GetBaseDato().addFilter( "conf_id",  this.GetcDato().getConfigId() ); 	 
	 return oGMCT;
 }
 
 protected GuiModuleConfigurationActions getModuleConfigActions() throws Exception {
	GuiModuleConfigurationActions oGMCA = new GuiModuleConfigurationActions();
	oGMCA.GetBaseDato().addFilter("company", this.GetcDato().getCompany());
	oGMCA.GetBaseDato().addFilter("country", this.GetcDato().getCountry());
	oGMCA.GetBaseDato().addFilter("node", this.GetcDato().getNode());
	oGMCA.GetBaseDato().addFilter("module_id", this.GetcDato().getModuleId());
	oGMCA.GetBaseDato().addFilter("config_type", this.GetcDato().getConfigType());
	oGMCA.GetBaseDato().addFilter("action_data", "%conf_id=" + this.GetcDato().getConfigId() + "%", "like");
	return oGMCA;
 }
}
