package  pss.common.pki.ejbca;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.ejbca.core.protocol.ws.client.gen.AuthorizationDeniedException_Exception;
import org.ejbca.core.protocol.ws.client.gen.EjbcaException_Exception;
import org.ejbca.core.protocol.ws.client.gen.EjbcaWS;
import org.ejbca.core.protocol.ws.client.gen.EjbcaWSService;
import org.ejbca.core.protocol.ws.client.gen.IllegalQueryException_Exception;
import org.ejbca.core.protocol.ws.client.gen.UserDataVOWS;
import org.ejbca.core.protocol.ws.client.gen.UserMatch;
import org.ejbca.util.CertTools;


public class EjbcaTools {

	public static void createNewCertificate(String CN) {
		 
	}

	public static void revoqueCertificate(String CN,String reazon) {
		 
	}

	public static List<UserDataVOWS> findCertificate(String CN) throws AuthorizationDeniedException_Exception, EjbcaException_Exception, IllegalQueryException_Exception, MalformedURLException {
		 CertTools.installBCProvider();	
		  String urlstr = "https://localhost:8443/ejbca/ejbcaws/ejbcaws?wsdl";
			
		  System.setProperty("javax.net.ssl.trustStore","/pki/ejb/p12/truststore.jks");
		  System.setProperty("javax.net.ssl.trustStorePassword","ejbca");  
			
		  System.setProperty("javax.net.ssl.keyStore","/pki/ejb/p12/truststore.jks");
		  System.setProperty("javax.net.ssl.keyStorePassword","ejbca");      
		                             
		  QName qname = new QName("http://ws.protocol.core.ejbca.org/", "EjbcaWSService");
		  EjbcaWSService service = new EjbcaWSService(new URL(urlstr),qname);
		  EjbcaWS ejbcaraws = service.getEjbcaWSPort();  
		  
		  UserMatch usermatch = new UserMatch();
		  usermatch.setMatchwith(org.ejbca.util.query.UserMatch.MATCH_WITH_DN);
		  usermatch.setMatchtype(org.ejbca.util.query.UserMatch.MATCH_TYPE_CONTAINS);
		  usermatch.setMatchvalue("CN");
		  List<UserDataVOWS> result= ejbcaraws.findUser(usermatch);
		  
		  
		 return result;
	}
	
	public static void main(String[] args) {
		try {
			 List<UserDataVOWS> result = findCertificate("USUARIO1");
			 for (UserDataVOWS u : result) {
				 System.out.println("USUARIO "+u.getUsername());
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
	}
}
