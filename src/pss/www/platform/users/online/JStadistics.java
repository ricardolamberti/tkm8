package pss.www.platform.users.online;

import pss.common.security.BizUsuario;
import pss.www.platform.applications.JWebApplicationSession;

public class JStadistics {
	BizOnlineUsers users;
	
	public JStadistics() {
	}
	
	public void initialize() throws Exception{
		users= new BizOnlineUsers();
		users.setStatic(true);
		users.convertToHash("user");
	}

	public void addInfoSession(JWebApplicationSession session) throws Exception {
		BizOnlineUser user = (BizOnlineUser) users.findInHash(BizUsuario.getUsr().GetUsuario());
		if (user == null) {
			user = new BizOnlineUser();
			user.setUser(BizUsuario.getUsr().GetUsuario());
			users.addInHash(BizUsuario.getUsr().GetUsuario(), user);
			users.getStaticItems().addElement(user);
			
		}
		user.fill("", session);
		
	}
	public void removeInfoSession(JWebApplicationSession session) throws Exception {
		BizOnlineUser user = (BizOnlineUser) users.findInHash(BizUsuario.getUsr().GetUsuario());
		if (user == null) return;
		users.getStaticItems().removeElement(user);
		users.removeInHash(BizUsuario.getUsr().GetUsuario());
		
	}

	public BizOnlineUsers getUsers() {
		return users;
	}

	public void setUsers(BizOnlineUsers users) {
		this.users = users;
	}
	
	
}
