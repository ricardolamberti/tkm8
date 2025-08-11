package pss.common.restJason.apiUser;

import java.util.Date;

import pss.common.restJason.apiRestBase.apiWebServiceTools;
import pss.common.restJason.apiUser.apiUserDetails.BizApiUserDetail;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizApiUser extends JRecord {

	
	//-------------------------------------------------------------------------//
	// Propiedades de la Clase
	//-------------------------------------------------------------------------//
	private JString pCompany = new JString();
	private JString pUsuario = new JString();
	private JString pUsuarioDescr = new JString(){
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getObjUsuario().getDescrUsuario());
		}
	};
	private JBoolean pActive = new JBoolean();	
	private JBoolean pUserActive = new JBoolean(){
		public void preset() throws Exception {
			pUserActive.setValue(obtenerUsuarioActivo());
		}
	};
	private JString pToken = new JString();
	private JDateTime pTokenDueDate = new JDateTime(true);
	
	public String getCompany() throws Exception {return pCompany.getValue();	}
	public String getUsuario() throws Exception {return pUsuario.getValue();	}
	public boolean isActive() throws Exception { return pActive.getValue(); }
	public boolean isUserActive() throws Exception { return pUserActive.getValue(); }
	public String getToken() throws Exception {return pToken.getValue();	}
	public Date getTokenDueDate() throws Exception {return pTokenDueDate.getValue();	}

	
	public void setCompany(String value) {this.pCompany.setValue(value);}
	public void setActive(boolean value) {this.pActive.setValue(value);}
	public void setToken(String value) {this.pToken.setValue(value);}
	public void setTokenDueDate(Date value) {this.pTokenDueDate.setValue(value);}


	private BizUsuario usuario;
	
	//-------------------------------------------------------------------------//
	// Constructor de la Clase
	//-------------------------------------------------------------------------//
	public BizApiUser() throws Exception {}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("usuario", pUsuario);
		addItem("active", pActive);
		addItem("token", pToken);
		addItem("token_duedate", pTokenDueDate);
		addItem("descr_usuario", pUsuarioDescr);
		addItem("user_active", pUserActive);
		
	}
	
	
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "usuario", "Usuario", true, true, 50);
		addFixedItem( FIELD, "company", "Empresa", true, true, 15);
		addFixedItem( FIELD, "active", "Acceso Habilitado", true, true, 1);
		addFixedItem( FIELD, "token", "Token", true, false, 4000);
		addFixedItem( FIELD, "token_duedate", "Vto. Token", true, false, 20);
		addFixedItem( VIRTUAL, "descr_usuario", "Mercado", true, false, 200);
		addFixedItem( VIRTUAL, "user_active", "Activo SITI", true, false, 1);

	}

	@Override
	public String GetTable() {
		return "API_JSON_USER";
	}

	public boolean Read(String zUsuario) throws Exception {
		addFilter("usuario", zUsuario);
		return this.read();
	}
	
	public void processInsert() throws Exception {
		this.setToken(this.generateToken());
		super.insert();
	}
		
	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario rec = new BizUsuario();
		rec.Read(this.getUsuario());
		return this.usuario=rec;
	}

	private boolean obtenerUsuarioActivo() throws Exception {
		if (this.getObjUsuario()==null) return false;
		return this.getObjUsuario().isActive();
	}
	
	public boolean isActiveToCallApi() throws Exception {
		return this.isActive() && this.isUserActive();
	}

	
	public void marcarActivo(boolean activa) throws Exception {
		BizApiUser p = (BizApiUser) this.getPreInstance();
		p.pActive.setValue(activa);
		p.update();
	
	}
	

	
	public String generateToken() throws Exception {
		return apiWebServiceTools.generateToken(this.getUsuario(), this.getObjUsuario().getPasswordDecrypt(), this.getCompany(), 87600);
		// lo pido para 10 años. Luego, la validez del tiempo la controlo consultando la tabla
    }
	
	
	public JRecords<BizApiUserDetail> getApiUBizApiUserDetailows() throws Exception {
		JRecords<BizApiUserDetail> recs = new JRecords<BizApiUserDetail>(BizApiUserDetail.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("usuario", this.getUsuario());
		recs.toStatic();
		return recs;
	}
	
	public static void checkToken(String usuario, String token) throws Exception {
		BizApiUser rec = new BizApiUser();
		rec.dontThrowException(true);
		if (!rec.Read(usuario))
			JExcepcion.SendError(" Usuario no existe en la configuración de API - JSON ");
		if (!rec.isActive())
			JExcepcion.SendError(" Usuario no habilitado configuración de API - JSON ");
		if (!rec.isActiveToCallApi())
			JExcepcion.SendError(" Usuario no habilitado en el sistema ");
		if (rec.getTokenDueDate().before(new Date()))
			JExcepcion.SendError(" Token Vencido ");
		return;
	}

	
	
}

