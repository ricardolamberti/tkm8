package pss.common.security.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import pss.core.data.BizPssConfig;
import pss.core.tools.PssLogger;

public class CheckUser {

	public static boolean validate(String user, String password) throws Exception {

		String ldapAdServer = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "LDAP_SERVER", "");
		String ldapSearchBase = BizPssConfig.getPssConfig().getCachedValue("GENERAL", "LDAP_SEARCHBASE","");
		String ldapDomain = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "LDAP_DOMAIN", "");
		String isSSL = BizPssConfig.getPssConfig().getCachedValue("GENERAL", "LDAP_SSL","");
		
		PssLogger.logInfo("SERVER: "+ldapAdServer);
		
		PssLogger.logInfo("SEARCHBASE: "+ldapSearchBase);
		
		PssLogger.logInfo("DOMAIN: "+ldapDomain);
		
		//String ldapUsername = user + "@" + ldapDomain;
		String ldapUsername = "cn=" + user + "," +  ldapDomain;
		String ldapPassword = password;

		Hashtable<String, Object> env = new Hashtable<String, Object>();
		
		//ldapAdServer = "LDAP://www.zflexldap.com:389";
		//ldapUsername =  "cn=ro_admin,ou=sysadmins,dc=zflexsoftware,dc=com";
		//ldapPassword = "zflexpass";
		
		
		if (isSSL!=null) {
			if (isSSL.equalsIgnoreCase("Y") || isSSL.equalsIgnoreCase("S"))
			env.put(Context.SECURITY_PROTOCOL, "ssl");
		}
		
		//env.put(Context.SECURITY_AUTHENTICATION, "simple");
		if (ldapUsername != null) {
			env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
		}
		if (ldapPassword != null) {
			env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);

		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		env.put("java.naming.ldap.attributes.binary", "objectSID");

		// the following is helpful in debugging errors
		// env.put("com.sun.jndi.ldap.trace.ber", System.err);
		try {
			PssLogger.logInfo("testing password with AD ...");

			DirContext dirContext = new InitialDirContext(env);
			
			if (dirContext!=null)
				PssLogger.logInfo("result ok with AD :" + dirContext.getNameInNamespace());
			
		} catch (Exception eee) {
			PssLogger.logInfo("error testing password with AD : " + eee.getMessage());
			return false;
		}

		return true;
	}

}
