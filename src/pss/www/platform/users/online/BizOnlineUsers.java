package pss.www.platform.users.online;

import pss.core.services.records.JRecords;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebServer;

public class BizOnlineUsers extends JRecords<BizOnlineUser> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizOnlineUsers() throws Exception {
	}

	@Override
	public Class<BizOnlineUser> getBasedClass() {
		return BizOnlineUser.class;
	}

	@Override
	public String GetTable() {
		return "";
	}


	@Override
	public boolean readAll() throws Exception {
		this.clearStaticItems();
		JWebApplication app =JWebServer.getInstance().getWebApplication(null);
		getStaticItems().addElements(app.getStadistics().getUsers().getStaticItems());
		setStatic(true);
		this.convertToHash("user");
	
//		JApplicationSessionManager sessionManager =  JWebServer.getInstance().getWebApplication(null).getSessionManager();
//		Map<String, JSessionController> sessions = sessionManager.getSessions();
//		boolean agrupado = this.getFilterValue("agrupado")!=null && this.getFilterValue("agrupado").equals("S") ;
//		String tipo_usuario = this.getFilterValue("tipo_usuario") ;
//		if (tipo_usuario==null)
//			tipo_usuario = "";
//		Runtime.getRuntime().gc();
//		Iterator<JSessionController> it = sessions.values().iterator();
//		setStatic(true);
//		this.convertToHash("user");
//		while (it.hasNext()) {
//			JSessionController ss = it.next();
//			boolean add=false;
//			BizOnlineUser user=null;
//			BizOnlineUser userTmp = new BizOnlineUser();
//			userTmp.fill(agrupado?"ALL":"MAIN",ss.getSession());
//			if (agrupado) {
//				user = (BizOnlineUser)findInHash(userTmp.getUser());
//				if (user!=null)
//					user.add(userTmp);
//			} 
//			
//			if (user==null) {
//				user= userTmp;
//				add=true;
//			}
//			
//			boolean includeItem = true ;
//				if (!tipo_usuario.equals("")){ 
//				if (!user.getUsuarioTipo().equals(tipo_usuario))
//					includeItem = false;
//			}
//			if (includeItem) {
//				if (add) {
//					this.addItem(user);
//					this.addInHash(user.getUser(), user);
//				}
//				Iterator<JWebApplicationSession> it2 = ss.getSubsessiones().values().iterator();
//				while (it2.hasNext()) {
//					JWebApplicationSession session = it2.next();
//					BizOnlineUser user2=null;
//					BizOnlineUser userTmp2 = new BizOnlineUser();
//					userTmp2.fill(agrupado?"ALL":"SUBSESSION",session);
//					if (agrupado) {
//						user2 = (BizOnlineUser)findInHash(userTmp.getUser());
//						if (user2!=null)
//							user2.add(userTmp2);
//					} 
//					if (user2==null) {
//						user2= userTmp2;
//						this.addItem(user2);
//						this.addInHash(user2.getUser(), user2);
//					}
//				}
//			}
//		}
//		firstRecord();
		return true;
	}
	


}