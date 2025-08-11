package pss.common.security.friends;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizUserFriend extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pCompany=new JString();
	private JString pUser=new JString();
	private JString pUserFriend=new JString();
	private JString pDescrUser=new JString() {
		public void preset() throws Exception {
			pDescrUser.setValue(getDescrUsuario());
		};
	};
	private JString pDescrFriend=new JString() {
		public void preset() throws Exception {
			pDescrFriend.setValue(getDescrFriend());
		};
	};
	
	public String getUser() throws Exception {
		return this.pUser.getValue();
	}

	public String getFriend() throws Exception {
		return this.pUserFriend.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizUserFriend() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("usuario", pUser);
		addItem("usuario_friend", pUserFriend);
		addItem("descr_user", pDescrUser);
		addItem("descr_friend", pDescrFriend);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Company", true, true, 15);
		addFixedItem(KEY, "usuario", "Usuario", true, true, 15);
		addFixedItem(KEY, "usuario_friend", "Usuario Amigo", true, true, 15);
		addFixedItem(VIRTUAL, "descr_user", "Descr Usuario", true, true, 100);
		addFixedItem(VIRTUAL, "descr_friend", "Vinculado", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "SEG_USER_FRIEND";
	}

//	public boolean Read(String user, String friend) throws Exception {
//		addFilter("user", user);
//		addFilter("user_friend", friend);
//		return this.read();
//	}
	
	private BizUsuario user;
	public BizUsuario getObjUsuario() throws Exception {
		if (this.user!=null) return this.user;
		BizUsuario record=new BizUsuario();
		record.Read(pUser.getValue());
		return (this.user=record);
	}

	private BizUsuario friend;
	public BizUsuario getObjFriend() throws Exception {
		if (this.friend!=null) return this.friend;
		BizUsuario record=new BizUsuario();
		record.Read(pUserFriend.getValue());
		return (this.friend=record);
	}

	public String getDescrFriend() throws Exception {
		return this.getObjFriend().GetDescrip();
	}

	public String getDescrUsuario() throws Exception {
		return this.getObjUsuario().GetDescrip();
	}
}
