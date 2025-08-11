package  pss.common.pki.ejbca;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JOSCommand;

public class BizPKIConfiguracion extends JRecord {

  private JString pCompany = new JString();
  private JString pPkiCmd = new JString();
  private JString pPkiAddress = new JString();
  private JString pPkiCertificateprofile = new JString();
  private JString pPkiUserprofile = new JString();
  private JString pPkiCA = new JString();
  private JString pPkiKeystore = new JString();
  private JString pPkiKeystorePsw = new JString();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setPkiCmd(String zValue) throws Exception {    pPkiCmd.setValue(zValue);  }
  public String getPkiCmd() throws Exception {     return pPkiCmd.getValue();  }
  public void setPkiCA(String zValue) throws Exception {    pPkiCA.setValue(zValue);  }
  public String getPkiCA() throws Exception {     return pPkiCA.getValue();  }
  public void setPkiAddress(String zValue) throws Exception {    pPkiAddress.setValue(zValue);  }
  public String getPkiAddress() throws Exception {     return pPkiAddress.getValue();  }
  public void setPkiCertificateprofile(String zValue) throws Exception {    pPkiCertificateprofile.setValue(zValue);  }
  public String getPkiCertificateprofile() throws Exception {     return pPkiCertificateprofile.getValue();  }
  public void setPkiUserprofile(String zValue) throws Exception {    pPkiUserprofile.setValue(zValue);  }
  public String getPkiUserprofile() throws Exception {     return pPkiUserprofile.getValue();  }
  public void setPkiKeystore(String zValue) throws Exception {    pPkiKeystore.setValue(zValue);  }
  public String getPkiKeystore() throws Exception {     return pPkiKeystore.getValue();  }
  public void setPkiKeystorePsw(String zValue) throws Exception {    pPkiKeystorePsw.setValue(zValue);  }
  public String getPkiKeystorePsw() throws Exception {     return pPkiKeystorePsw.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPKIConfiguracion() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "pki_address", pPkiAddress );
    this.addItem( "pki_cmd", pPkiCmd );
    this.addItem( "pki_ca", pPkiCA );
    this.addItem( "pki_certificateprofile", pPkiCertificateprofile );
    this.addItem( "pki_userprofile", pPkiUserprofile );
    this.addItem( "pki_keystore", pPkiKeystore );
    this.addItem( "pki_keystorePsw", pPkiKeystorePsw );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Configuracion", true, true, 15 );
    this.addFixedItem( FIELD, "pki_address", "Pki address", true, false, 300 );
    this.addFixedItem( FIELD, "pki_cmd", "Pki cmd", true, false, 300 );
    this.addFixedItem( FIELD, "pki_ca", "Pki ca", true, false, 300 );
    this.addFixedItem( FIELD, "pki_certificateprofile", "Pki certificateprofile", true, false, 300 );
    this.addFixedItem( FIELD, "pki_userprofile", "Pki userprofile", true, false, 300 );
    this.addFixedItem( FIELD, "pki_keystore", "Pki keystore", true, false, 300 );
    this.addFixedItem( FIELD, "pki_keystorePsw", "Pki keystore pass", true, false, 300 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "pki_configurator"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zcompany  ) throws Exception { 
    addFilter( "company",  zcompany ); 
    return read(); 
  } 

  public static boolean revokeUser(String usuario) throws Exception {
		BizPKIConfiguracion conf = new BizPKIConfiguracion();
		conf.read(BizUsuario.getUsr().getCompany());
		return  conf.iRevokeUser(usuario);
		
	} 
  public static boolean statusUser(BizPKICertificado cert, String usuario) throws Exception {
		BizPKIConfiguracion conf = new BizPKIConfiguracion();
		conf.read(BizUsuario.getUsr().getCompany());
		return  conf.iStatusUser(cert,usuario);
		
	}
  public static boolean addUser(String usuario, String psw, String email) throws Exception {
		BizPKIConfiguracion conf = new BizPKIConfiguracion();
		conf.read(BizUsuario.getUsr().getCompany());
		return  conf.iAddUser(usuario,psw,email);
	}
	private boolean iRevokeUser(String usuario) throws Exception {
		String command; 
		File arch = new File(getPkiCmd());
		command=getPkiCmd()+" ra revokeuser ";
		command+="\""+usuario+"\" 5";
		ByteArrayOutputStream bufStr = new ByteArrayOutputStream();
		PrintStream output = new PrintStream(bufStr);
		JOSCommand oCommand = new JOSCommand(command, output, output);
		oCommand.setWorkingDirectory(arch.getParentFile());
		oCommand.executeWaiting(60000, 1);
		if (!oCommand.hasTerminatedNormally()) return false;
		String strout = new String(bufStr.toByteArray());
		return strout.indexOf("New status=50")!=-1;
	}	
	private boolean iStatusUser(BizPKICertificado cert,String usuario) throws Exception {
		String command; 
		File arch = new File(getPkiCmd());
		command=getPkiCmd()+" ra finduser ";
		command+="\""+usuario+"\"";
		ByteArrayOutputStream bufStr = new ByteArrayOutputStream();
		PrintStream output = new PrintStream(bufStr);
		JOSCommand oCommand = new JOSCommand(command, output, output);
		oCommand.setWorkingDirectory(arch.getParentFile());
		oCommand.executeWaiting(60000, 1);
		if (!oCommand.hasTerminatedNormally()) return false;
		String strout = new String(bufStr.toByteArray());
		if (strout.indexOf("User '"+usuario+"' does not exist.")!=-1) return false;
		if (strout.indexOf("status=50")!=-1) cert.setEstado("REVOCADO");
		if (strout.indexOf("status=40")!=-1) cert.setEstado("VIGENTE");
		if (strout.indexOf("status=30")!=-1) cert.setEstado("EN PROCESO");
		if (strout.indexOf("status=20")!=-1) cert.setEstado("INICIALIZADO");
		if (strout.indexOf("status=10")!=-1) cert.setEstado("PENDIENTE");
		if (strout.indexOf("modified=")!=-1) 
			cert.setFecha(strout.substring(strout.indexOf("modified=")+9));
		else if (strout.indexOf("created=")!=-1) 
			cert.setFecha(strout.substring(strout.indexOf("created=")+8,strout.indexOf("modified=")));
		

		cert.setUsuario(usuario);
		return true;
	}
	 
	private boolean iAddUser(String usuario, String psw, String email) throws Exception {
		String command; 
		File arch = new File(getPkiCmd());
		command=getPkiCmd()+" ra adduser ";
		command+="\""+usuario+"\" \""+psw+"\" \""+getDN(usuario)+"\" null \""+getPkiCA()+"\" \""+email+"\" 1 P12 \""+getPkiCertificateprofile()+"\" \""+getPkiUserprofile()+"\"";
		JOSCommand oCommand = new JOSCommand(command, System.out, System.out);
		oCommand.setWorkingDirectory(arch.getParentFile());
  	oCommand.executeWaiting(60000, 1);
		return oCommand.hasTerminatedNormally();
	}
	
	private String getDN(String user) {
		return "CN="+user;
	}

}
