package pss.common.restJason.apiRestBase;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="apiJasonUserData")
public class apiUserData {
	private String	usuario;
	private String	password;

	public apiUserData() {
	}

	public apiUserData( String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String userId) {
		this.usuario = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	  @Override
	  public String toString() {
	        return " userData [usuario=" + usuario + ", clave=" + password + "]";
	  }
}