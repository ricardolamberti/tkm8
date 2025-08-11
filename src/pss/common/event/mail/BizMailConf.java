package pss.common.event.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import com.sun.mail.util.MailSSLSocketFactory;

import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizMailConf extends JRecord {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pDescription = new JString();
  private JString pSmtpServer = new JString();
  private JInteger pSmtpPort = new JInteger();
  private JString pSmtpTransport = new JString();
  private JString pPop3Server = new JString();
  private JInteger pPop3Port = new JInteger();
  private JString pPop3Transport = new JString();

  public void   setId(long zVal ) throws Exception { pId.setValue( zVal ); }
  public long getId() throws Exception { return pId.getValue(); }
  public void   setDescription(String zVal ) throws Exception { pDescription.setValue( zVal ); }
  public String getDescription() throws Exception { return pDescription.getValue(); }
  public void   setCompany(String zVal ) throws Exception { pCompany.setValue( zVal ); }
  public String getCompany() throws Exception { return pCompany.getValue(); }
  public void   setSmtpTransport(String zVal ) throws Exception { pSmtpTransport.setValue( zVal ); }
  public String getSmtpTransport() throws Exception { return pSmtpTransport.getValue(); }
  public void   setSmtpServer(String zVal ) throws Exception { pSmtpServer.setValue( zVal ); }
  public String getSmtpServer() throws Exception { return pSmtpServer.getValue(); }
  public void   setSmtpPort(long zVal ) throws Exception { pSmtpPort.setValue( zVal ); }
  public int getSmtpPort() throws Exception { return pSmtpPort.getValue(); }
  public String getPop3Server() throws Exception { return pPop3Server.getValue(); }
  public int getPop3Port() throws Exception { return pPop3Port.getValue(); }
  public String getPop3Transport() throws Exception { return pPop3Transport.getValue(); }

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizMailConf() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "id", pId );
    addItem( "company", pCompany );
    addItem( "description", pDescription );
    addItem( "smtp_server", pSmtpServer );
    addItem( "smtp_port", pSmtpPort );
    addItem( "transport", pSmtpTransport );
    addItem( "pop3_server", pPop3Server );
    addItem( "pop3_port", pPop3Port );
    addItem( "pop3_transport", pPop3Transport );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "id", "Id", false, false, 18, 0 );
    addFixedItem( FIELD, "company", "Empresa", true, false, 50 );
    addFixedItem( FIELD, "description", "Descripción", true, true, 50 );
    addFixedItem( FIELD, "smtp_server", "Smtp Servidor", true, true, 50 );
    addFixedItem( FIELD, "smtp_port", "Smtp Puerto", true, true, 6 );
    addFixedItem( FIELD, "transport", "Transporte", true, true, 50 );
    addFixedItem( FIELD, "pop3_server", "Pop3 Servidor", true, false, 50 );
    addFixedItem( FIELD, "pop3_port", "Pop3 Puerto", true, false, 6 );
    addFixedItem( FIELD, "pop3_transport", "Pop3 Transporte", true, false, 50 );
  }

  @Override
	public String GetTable() {  return "MAIL_CONF";  }

  public boolean read ( int iID ) throws Exception {
    clearFilters();
    addFilter("ID", iID);//iID=3
    return read();
  }
  
	public void validateConstraints() throws Exception {
		if ( this.pCompany.isNull() ) {
			if (BizUsuario.getUsr()==null)
				this.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
			else
				this.pCompany.setValue(BizUsuario.getUsr().getCompany());
		}
	}

//	public boolean isSender() throws Exception{
//		return this.getTransport().toLowerCase().indexOf("smtp")!=-1;
//	}
//	public boolean isReceiver() throws Exception{
//		return !this.isSender();
//	}
	public boolean isSalienteExchange() throws Exception{
		return this.getSmtpTransport().startsWith("e");
	}	
	public boolean isEntranteExchange() throws Exception {
		return this.getPop3Transport().startsWith("e");
	}
	
	public JRecords<BizMailCasilla> getCasillas() throws Exception { 
		JRecords<BizMailCasilla> recs = new JRecords<BizMailCasilla>(BizMailCasilla.class);
		recs.addFilter("id_mail_conf", this.pId.getValue());
		recs.readAll();
		return recs;
	}
	
	@Override
	public void processDelete() throws Exception {
		this.getCasillas().processDeleteAll();
		super.processDelete();
	}
	
	@Override
	public void processInsert() throws Exception {
		this.insert();
		this.pId.setValue(this.getIdentity("id"));
	}


	public boolean isPop3() throws Exception {
		return this.getPop3Transport().equals("pop3");
	}
	
	public boolean isEntranteSSL() throws Exception  {
		return this.getPop3Transport().endsWith("s");
	}
	public boolean isEntranteTLS() throws Exception  {
		return this.getPop3Transport().endsWith("t");
	}
	public boolean isSalienteSSL() throws Exception  {
		return this.getSmtpTransport().endsWith("s");
	}
	public boolean isSalienteTLS() throws Exception  {
		return this.getSmtpTransport().endsWith("t");
	}

	public String getEntranteTransport() throws Exception {
		String t = this.getPop3Transport();
		if (this.isEntranteExchange()) t = t.substring(1); // saco la e
		if (this.isEntranteTLS()) t=t.substring(0, t.length()-1);  // saco la t
		return t; 
	}
	
	public String getSalienteTransport() throws Exception {
		String t = this.getSmtpTransport();
		if (this.isSalienteExchange()) t = t.substring(1);  // saco la e
		if (this.isSalienteTLS()) t=t.substring(0, t.length()-1); // saco la t
		return t;
	}

	
	public boolean hasPop3() throws Exception { 
		return !this.pPop3Server.isEmpty();
	}
	

	public Properties makeProperties(String user, boolean saliente) throws Exception {
		// HGK todo una truchada porque toda la info esta en el campo transport, hacerlo bien (transport, cifrado, tipo servidor, etc)
		Properties properties = new Properties();
		if (saliente) {
			if (this.isSalienteExchange()) {
				properties.setProperty("mail.smtp.submitter", user);
				properties.setProperty("mail.smtp.auth", "true");
				properties.setProperty("mail.smtp.host", this.getSmtpServer());
				properties.setProperty("mail.smtp.port", ""+this.getSmtpPort());
				properties.setProperty("mail.debug", "true");
//				properties.setProperty("mail.smtp.socketFactory.port", ""+this.getSmtpPort());
//		    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		    properties.setProperty("mail.smtp.socketFactory.fallback", "false");
			} 
		
			if (this.isSalienteTLS()) {
				properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
				properties.setProperty("mail.smtp.starttls.enable", "true");
			}
			
			if (this.isSalienteSSL()) {
			  properties.put("mail.smtp.ssl.enable", "true");
			}
		} else {
			String transport=this.getEntranteTransport();
	
			properties.setProperty("mail."+transport+".host", this.getPop3Server());
			properties.setProperty("mail."+transport+".port", ""+this.getPop3Port());

			if (this.isPop3()) {
				properties.put("mail.pop3.disabletop", true);
				properties.put("mail.pop3.forgettopheaders", true);
			}
			
			if (this.isEntranteTLS()) {
				properties.put("mail.imap.starttls.enable", "true");
			}
			if (this.isEntranteSSL()) {
				properties.put("mail.imap.ssl.enable", true);
				if (this.isEntranteExchange()) {
					MailSSLSocketFactory sf = new MailSSLSocketFactory();
					sf.setTrustAllHosts(true);
					properties.put("mail.imap.ssl.trust", "*");
					properties.put("mail.imaps.ssl.socketFactory", sf);
				} 
			}
			properties.put("mail."+transport+".socketFactory", this.getPop3Port());
			properties.put("mail."+transport+".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}
		return properties;
	}
	
	
	public Authenticator makeAuth(String user, String pass) throws Exception {
		Authenticator auth = new Authenticator() {
	    protected PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(user, pass);
	    };
		};
		return auth;
		
	}
	
}
