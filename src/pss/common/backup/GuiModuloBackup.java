package  pss.common.backup;

import pss.common.backup.settings.GuiBackUpMainSettings;
import pss.common.event.mail.GuiMailConfs;
import pss.common.event.mail.GuiMailsSender;
import pss.common.event.manager.GuiEvents;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloBackup extends GuiModulo {

	public GuiModuloBackup() throws Exception {
		super();
		SetModuleName("BACKUP");
		SetNroIcono(5050);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "BACKUP", null, 5050, true, true, true,
				"Group");
	}

	
	 @Override
		public void createActionMap() throws Exception {
	  	this.addAction( 5, "Configuración BackUp" , null , 941 , true, true, true, "Group");
	  	this.addAction( 10, "Configuración Eventos" , null , 157 , true, true, true, "Group");
	  	this.addAction( 15, "Configuración Mail" , null , 10 , true, true, true, "Group");
	  	this.addAction( 20, "Configuración Envío" , null , 10 , true, true, true, "Group");
	  	this.loadDynamicActions(null);
	  }

		public JAct getSubmitFor(BizAction a) throws Exception {
			if ( a.getId()==5 ) return new JActWins(getBackUpSettings());
			if ( a.getId()==10 ) return new JActWins(getEventSettings());
			if ( a.getId()==15 ) return new JActWins(getMailSettings());
			if ( a.getId()==20 ) return new JActWins(getMailSenders());
	  	return null;
	  }
	  
		public JWins getBackUpSettings() throws Exception {
			GuiBackUpMainSettings g = new GuiBackUpMainSettings();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }
		
		public JWins getEventSettings() throws Exception {
			GuiEvents g = new GuiEvents();
	    g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }

		public JWins getMailSettings() throws Exception {
			GuiMailConfs g = new GuiMailConfs();
	    return g;
	  }
		
		public JWins getMailSenders() throws Exception {
			GuiMailsSender g = new GuiMailsSender();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }

}
