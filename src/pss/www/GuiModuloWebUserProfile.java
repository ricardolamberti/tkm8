package pss.www;

import pss.common.restJason.apiReqHistory.GuiApiRequestHistorys;
import pss.common.security.BizUsuario;
import pss.common.security.GuiWebUserProfile;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.users.history.BizUserHistory;
import pss.www.platform.users.history.GuiUserHistories;
import pss.www.platform.users.online.BizOnlineUser;
import pss.www.platform.users.online.GuiOnlineUsers;
import pss.www.platform.users.online.JStadistics;
import pss.www.platform.users.stadistics.GuiStadistics;




	public class GuiModuloWebUserProfile extends GuiModulo {

		public GuiModuloWebUserProfile() throws Exception {
			super();
			SetModuleName("Preferencias Web");
			SetNroIcono(953);
		}
		@Override
		public BizAction createDynamicAction() throws Exception {
			return addAction(1, "Web", null, 1, true, true, true, "Group");
		}


		@Override
		public void createActionMap() throws Exception {
			this.addAction(10, "Cambiar Preferencias", null, 953, true, false, true, "Group");
			this.addAction(20, "Usuarios Online", null, 718, true, false, true, "Group");
			this.addAction(30, "Estadistica online", null, 718, true, false, true, "Group");
			this.addAction(40, "Acciones Online", null, 718, true, false, true, "Group");
			this.addAction(42, "Acciones En proceso", null, 718, true, false, true, "Group");
			this.addAction(44, "Acciones Error", null, 718, true, false, true, "Group");
			this.addAction(50, "WebService History", null, 718, true, false, true, "Group");
		}

		@Override
		public JAct getSubmitFor(BizAction a) throws Exception {
			if (a.getId() == 10) return new JActUpdate(this.getUsuario(),2);
			if (a.getId() == 20) return new JActWins(this.getUsuarioOnline());
			if (a.getId() == 30) return new JActQuery(GuiStadistics.getStat());
			if (a.getId() == 40) return new JActWins(this.getHistorico());
			if (a.getId() == 42) return new JActWins(this.getInProgress());
			if (a.getId() == 44) return new JActWins(this.getInError());
			if (a.getId() == 50) return new JActWins(this.getWebServiceHistory());
			return null;
		}

		private JWin getUsuario() throws Exception {
			GuiWebUserProfile user = new GuiWebUserProfile();
			user.GetcDato().read(BizUsuario.getUsr().GetUsuario());
			return user;
		}
		private JWins getUsuarioOnline() throws Exception {
			GuiOnlineUsers users = new GuiOnlineUsers();
			users.setPreviewFlag(JWins.PREVIEW_MAX);
			return users;
		}
		
	  public JWins getHistorico() throws Exception {
	  	GuiUserHistories g = new GuiUserHistories();
	  	g.setRecords(this.getAllUserHistories(0));
	  	g.SetVision(this.GetVision());
	  	return g; 
	  }
	  
	  public JWins getInProgress() throws Exception {
	  	GuiUserHistories g = new GuiUserHistories();
	  	g.setRecords(this.getAllUserHistories(1));
	  	g.SetVision(this.GetVision());
	  	return g; 
	  }

	  public JWins getInError() throws Exception {
	  	GuiUserHistories g = new GuiUserHistories();
	  	g.setRecords(this.getAllUserHistories(2));
	  	g.SetVision(this.GetVision());
	  	return g; 
	  }

	  public JRecords<BizUserHistory> getAllUserHistories(int tipo) throws Exception {
	  	JStadistics stadistics =  JWebServer.getInstance().getWebApplication(null).getStadistics();
	  	BizOnlineUser user = (BizOnlineUser) stadistics.getUsers().findInHash(BizUsuario.getUsr().GetUsuario());
	  	JRecords<BizUserHistory> recs = user.getStadistics();
	  	recs.Ordenar("fecha_hora");
	  	recs.invertirOrden();
	  	return recs;
	  }
	  
	  
	  public JWins getWebServiceHistory() throws Exception {
	  	GuiApiRequestHistorys g = new GuiApiRequestHistorys();
	  	g.getRecords().addOrderBy("creation_date", "desc");
	  	g.setPreviewFlag(JWins.PREVIEW_NO);
	  	return g; 
	  }


	}
