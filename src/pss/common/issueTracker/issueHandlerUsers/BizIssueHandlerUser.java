package pss.common.issueTracker.issueHandlerUsers;

import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizIssueHandlerUser extends JRecord {
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private JString pUsuario = new JString();
	private JString pUsuarioDescr = new JString() {
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getDescrUsuario());
		}
	};
	private JString pUsuarioMapCompany = new JString();
	private JString pUsuarioMapSistema = new JString();
	
	private JString pEmail = new JString();
	//private JInteger pIdCasilla = new JInteger();
	private JBoolean pActive = new JBoolean();
	
	public String getUsuarioMapCompany() throws Exception{	return pUsuarioMapCompany.getValue();}
	public String getUsuario() throws Exception{	return pUsuario.getValue();}
	public String getUsuarioMapSistema() throws Exception{	return pUsuarioMapSistema.getValue();}
	public String getEmail() throws Exception{	return pEmail.getValue();}
	public Boolean isActive() throws Exception{	return pActive.getValue();}
	//public int getIdCasilla() throws Exception{	return pIdCasilla.getValue();}
	
	public void setUsuarioMapCompany(String value) throws Exception{	this.pUsuarioMapCompany.setValue(value);}
	public void setUsuario(String value) throws Exception{	this.pUsuario.setValue(value);}
	public void setUsuarioMapSistema(String value) throws Exception{	this.pUsuarioMapSistema.setValue(value);}
	public void setEmail(String value) throws Exception{	this.pEmail.setValue(value);}
	public void setActive(Boolean value) throws Exception{	this.pActive.setValue(value);}
	//public void setIdCasilla(int value) throws Exception{	this.pIdCasilla.setValue(value);}
	
	private BizUsuario usuario;
//	private BizMailCasilla casilla;
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueHandlerUser() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
    addItem( "usuario", pUsuario);
    addItem( "usuario_map_company", pUsuarioMapCompany);
    addItem( "usuario_map_sistema", pUsuarioMapSistema);
    addItem( "email", pEmail);
    addItem( "active", pActive);
    //addItem( "id_casilla", pIdCasilla);
    addItem( "descr_usuario", pUsuarioDescr);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "usuario", "Usuario", true, true, 200 );
		addFixedItem(FIELD, "usuario_map_company", "Empresa Usuario Real", true, true, 15 );
		addFixedItem(FIELD, "usuario_map_sistema", "Usuario Real", true, true, 40 );
		addFixedItem(FIELD, "email", "email", true, false, 500 );
		addFixedItem(FIELD, "active", "Activo", true, false, 1 );
		//addFixedItem(FIELD, "id_casilla", "Casilla", true, true, 18 );
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario", true, false, 200 );
	}

	@Override
	public String GetTable() {
		return "ISSUE_TRACK_HANDLE_USER";
	}

	public boolean Read(String usuario) throws Exception {
		addFilter("usuario", usuario);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
		this.setActive(true);
  	super.processInsert();
	}
	
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
	}
	
	
	
	public String getDescrUsuario() throws Exception {
		return this.getObjUsuario().GetDescrip();
	}

	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario t = new BizUsuario();
		t.Read(this.pUsuarioMapSistema.getValue());
		return (this.usuario=t);
	}
	
	public static JRecords<BizIssueHandlerUser> getIssueHandlerUsers() throws Exception {
		JRecords<BizIssueHandlerUser> recs = new JRecords<BizIssueHandlerUser>(BizIssueHandlerUser.class);
		recs.toStatic();
		return recs;
	}
	
	public static boolean isLoginedUserAHandlerUser() throws Exception {
		JRecords<BizIssueHandlerUser> recs = new JRecords<BizIssueHandlerUser>(BizIssueHandlerUser.class);
		recs.addFilter("usuario_map_sistema", BizUsuario.getUsr().GetUsuario());
		recs.toStatic();
		if (recs.sizeStaticElements()>0)
			return true;
		return false;
	}	
	
	public static boolean isThisUserAHandlerUser(String user) throws Exception {
		JRecords<BizIssueHandlerUser> recs = new JRecords<BizIssueHandlerUser>(BizIssueHandlerUser.class);
		recs.addFilter("usuario_map_sistema", user);
		recs.toStatic();
		if (recs.sizeStaticElements()>0)
			return true;
		return false;
	}	
	
	public void activar() throws Exception {
		JExec exec = new JExec() {
			public void Do() throws Exception {
				marcar_activa(true);
			}
		};
		exec.execute();
	}
	
	public void desactivar() throws Exception {
		JExec exec = new JExec() {
			public void Do() throws Exception {
				marcar_activa(false);
			}
		};
		exec.execute();
	}
	
	
	public void marcar_activa(boolean activa) throws Exception {
		BizIssueHandlerUser p = (BizIssueHandlerUser) this.getPreInstance();
		p.pActive.setValue(activa);
		p.update();
	}

	
	
	
	
}
