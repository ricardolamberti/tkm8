package  pss.common.pki.ocsp;

import java.net.URI;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import pss.core.tools.PssLogger;

/**
 * Interfaz con servicios de OCSP
 * 
 * @author RJL
 *
 */
public class Ocsp {
	/*
	 * public static boolean checkOCSP(String ksfilename, String kspwd, String
	 * ocspurl, boolean signRequest, Certificate certP, Certificate certH) {
	 * boolean result = false; try { CertTools.installBCProvider();
	 * 
	 * if (ocspurl==null) ocspurl =
	 * CertTools.getAuthorityInformationAccessOcspUrl(certH);
	 * 
	 * OCSPUnidClient ocsp = OCSPUnidClient.getOCSPUnidClient(ksfilename, kspwd,
	 * ocspurl, signRequest, true); OCSPUnidResponse response = ocsp.lookup(certH,
	 * certP); if (response.getErrorCode() != OCSPUnidResponse.ERROR_NO_ERROR) {
	 * PssLogger.logError("Error querying OCSP server.");
	 * PssLogger.logError("Error code is: "+response.getErrorCode()); } if
	 * (response.getHttpReturnCode() != 200) {
	 * PssLogger.logDebug("Http return code is: "+response.getHttpReturnCode()); }
	 * if (response.getResponseStatus() == 0) { String logline =
	 * "OCSP return value is: "+response.getStatus()+" ("; switch
	 * (response.getStatus()) { case OCSPUnidResponse.OCSP_GOOD: result = true;
	 * logline += "good)"; break; case OCSPUnidResponse.OCSP_REVOKED:
	 * logline+="revoked)"; break; case OCSPUnidResponse.OCSP_UNKNOWN:
	 * logline+="unknown)"; break; } PssLogger.logDebug(logline); if
	 * (response.getFnr() != null) {
	 * PssLogger.logDebug("Returned Fnr is: "+response.getFnr()); } } else {
	 * String logline =
	 * "OCSP response status is: "+response.getResponseStatus()+" ("; switch
	 * (response.getResponseStatus()) { case OCSPRespGenerator.MALFORMED_REQUEST:
	 * logline+="malformed request)"; break; case
	 * OCSPRespGenerator.INTERNAL_ERROR: logline+="internal error"; break; case
	 * OCSPRespGenerator.TRY_LATER: logline+="try later)"; break; case
	 * OCSPRespGenerator.SIG_REQUIRED: logline+="signature required)"; break; case
	 * OCSPRespGenerator.UNAUTHORIZED: logline+="unauthorized)"; break; }
	 * PssLogger.logError(logline); }
	 * 
	 * } catch (Exception e) { PssLogger.logError(e); }
	 * 
	 * return result; }
	 */

	public static boolean checkOCSP(String ksfilename, String kspwd, String ocspurl, boolean signRequest, X509Certificate certP, X509Certificate certH, X509Certificate certOCSP) {
		try {
			CertPath cp = null;
			Vector certs = new Vector();
			URI ocspServer = null;

			// load the cert to be checked
			certs.add(certP);

			// handle location of OCSP server
			if (ocspurl != null) {
				ocspServer = new URI(ocspurl);
			}

			// init cert path
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			cp = (CertPath) cf.generateCertPath(certs);

			// load the root CA cert for the OCSP server cert
			X509Certificate rootCACert = certH;

			// init trusted certs
			TrustAnchor ta = new TrustAnchor(rootCACert, null);
			Set trustedCertsSet = new HashSet();
			trustedCertsSet.add(ta);

			// init cert store
			Set certSet = new HashSet();
			if (certOCSP!=null) {
					certSet.add(certOCSP);
			}
			CertStoreParameters storeParams = new CollectionCertStoreParameters(certSet);
			CertStore store = CertStore.getInstance("Collection", storeParams);

			// init PKIX parameters
			PKIXParameters params = null;
			params = new PKIXParameters(trustedCertsSet);
			params.addCertStore(store);

			// enable OCSP
			Security.setProperty("ocsp.enable", "true");
			if (ocspServer != null) {
				Security.setProperty("ocsp.responderURL", ocspurl);
				if (certOCSP!=null)
					Security.setProperty("ocsp.responderCertSubjectName", certOCSP.getSubjectX500Principal().getName());
			}

			// perform validation
			CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
			PKIXCertPathValidatorResult cpv_result = (PKIXCertPathValidatorResult) cpv.validate(cp, params);
			X509Certificate trustedCert = (X509Certificate) cpv_result.getTrustAnchor().getTrustedCert();

			if (trustedCert == null) {
				PssLogger.logDebug("Trsuted Cert = NULL");
				return  false;
			} else {
				PssLogger.logDebug("Trusted CA DN = " + trustedCert.getSubjectDN());
				return  true;
			}

		} catch (CertPathValidatorException e) {
			PssLogger.logError(e);
			return false;

		} catch (Exception e) {
			PssLogger.logError(e);
			return false;
		}
//		return true;
	}

}
