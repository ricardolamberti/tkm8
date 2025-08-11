package  pss.common.pki;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.CertificateVerification;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.VerificationException;

import pss.JPath;
import pss.common.pki.appletSign.ClientHttpRequest;
import pss.common.pki.ocsp.Ocsp;
import pss.common.pki.users.BizUsuarioFirma;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralNames;
import sun.security.x509.URIName;
import sun.security.x509.X509CertImpl;

public class PkiTools {
	
	String lastError;
	
	public PkiTools() {
	}
	
 public String getLastError() {
		return lastError;
	}

	public void clearLastError() {
		this.lastError = "PKI:";
	}
	public void addLastError(String lastError) {
		this.lastError += lastError;
	}

  public static boolean isSelfSigned(X509Certificate cert)
      throws CertificateException, NoSuchAlgorithmException,
      NoSuchProviderException {
  try {
      // Try to verify certificate signature with its own public key
      PublicKey key = cert.getPublicKey();
      cert.verify(key);
      return true;
  } catch (SignatureException sigEx) {
      // Invalid signature --> not self-signed
      return false;
  } catch (InvalidKeyException keyEx) {
      // Invalid key --> not self-signed
      return false;
  }
}

/**
  * Chequea firma de un pdf
  * @param ksfilename keystore JKS con la cadena de certificados
  * @param pass clave del keystore
  * @param ocspParent Si no se chequea ocsp dejar en null, si se chequea poner la clave del certificado de root
  * @param filename archivo pdf a chequear
  * @return si la firma es valida o no
  * @throws Exception
  */
	public boolean checkPDFFile(String ksfilename, String pass, String root, boolean ocsp, boolean crl, String filename, boolean firmaObligatoria) throws Exception {
	
		clearLastError();
		boolean ok=true;
		// KeyStore kall=PdfPKCS7.loadCacertsKeyStore();

		// CertificateFactory cf=CertificateFactory.getInstance("X509");
		// Collection col=cf.generateCertificates(new FileInputStream("c:\\adminca1.cer"));
		// KeyStore kall=KeyStore.getInstance(KeyStore.getDefaultType());
		// kall.load(null, null);
		// for(Iterator it=col.iterator(); it.hasNext();) {
		// X509Certificate cert=(X509Certificate) it.next();
		// kall.setCertificateEntry(cert.getSerialNumber().toString(Character.MAX_RADIX), cert);
		// }

			KeyStore kall=KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password=pass.toCharArray();
			java.io.FileInputStream fis=new java.io.FileInputStream(ksfilename);
			kall.load(fis, password);
			fis.close();
			boolean firma = false;
			
//			BouncyCastleProvider provider = new BouncyCastleProvider();
//			Security.addProvider(provider);
			
			PdfReader reader=new PdfReader(filename);
		try {
			AcroFields af=reader.getAcroFields();
			ArrayList names=af.getSignatureNames();
			for(int k=0; k<names.size(); ++k) {
				firma=true;
				String name=(String) names.get(k);
				PssLogger.logDebug("Signature name: "+name);
				PssLogger.logDebug("Signature covers whole document: "+af.signatureCoversWholeDocument(name));
				PssLogger.logDebug("Document revision: "+af.getRevision(name)+" of "+af.getTotalRevisions());
	      addLastError("Firma "+name);
	
				// // Start revision extraction
				// FileOutputStream out=new FileOutputStream("revision_"+af.getRevision(name)+".pdf");
				// byte bb[]=new byte[8192];
				// InputStream ip=af.extractRevision(name);
				// int n=0;
				// while ((n=ip.read(bb))>0)
				// out.write(bb, 0, n);
				// out.close();
				// ip.close();
				// // End revision extraction
	
				PdfPKCS7 pk=af.verifySignature(name);
				Calendar cal=pk.getSignDate();
				Certificate pkc[]=pk.getCertificates();
				
				//PssLogger.logDebug("Subject: "+  PdfPKCS7.getSubjectFields(pk.getSigningCertificate()));
				PssLogger.logDebug("Subject: "+ CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
				PssLogger.logDebug("Document modified: "+!pk.verify());
				ok&=pk.verify();
	
				
				List<VerificationException> fails = CertificateVerification.verifyCertificates(pkc, kall, cal);
				ok&=(fails.size()==0);
				if (fails.size() == 0)
					PssLogger.logDebug("Certificates verified against the KeyStore");
				else {
		      addLastError(" "+fails);
		      PssLogger.logDebug("Certificate failed: "+fails);
		      ok=false;
				}
				
				
	      for (Certificate cert : pkc) {
					if (cert instanceof X509Certificate) {
						if (isSelfSigned((X509Certificate)cert)) {
							addLastError(" "+((X509Certificate)cert).getSubjectDN()+" es autofirmado");
							ok&=false;
						}
					} else {
			      addLastError(" certificado desconocido");
		     		ok&=false;;
					}
				}	


				
				if (ocsp) {
		      Certificate certP = kall.getCertificate(root);
		      for (Certificate cert : pkc) {
						if (cert instanceof X509Certificate) {
			      	boolean check = Ocsp.checkOCSP(null, null, null, false, (X509Certificate) certP, (X509Certificate)cert, null); //falta de donde sacar la firma para presentar al ocsp si es necesario
				      if (!check) addLastError(" OCSP Fallo");
			      	ok&=check;
							}
						}
				}
				
				if (crl) {
						Certificate certP = kall.getCertificate(root);
						if (certP instanceof X509Certificate) {
				      List<CRL> l = readCRLsFromCert((X509Certificate)certP);
				      for (Certificate cert : pkc) {
								if (cert instanceof X509Certificate) {
					      	String errorCrl = verifyCertificate((X509Certificate)cert,l,null);
						      if (errorCrl!=null) addLastError(" "+errorCrl);
					     		ok&=errorCrl==null;
								} 
							}	
						}
		     }
			}
			if (firmaObligatoria) {
				if (!firma) {
					ok=false;
					addLastError("No presenta firma");
				}
			}
		} catch (Exception eee) {
			reader.close();
		} finally {
			reader.close();
		}
		return ok;
	}
	
	static long cacheDay = -1;
	static Map<String,List<CRL>> cacheListaCrl;
	public static Map<String,List<CRL>> getCacheListaCrl() throws Exception {
		if (cacheDay!=-1 && cacheDay != JDateTools.getDayOfMonth(new Date())) {
			cacheListaCrl=null;
		}
		cacheDay=JDateTools.getDayOfMonth(new Date());
		if (cacheListaCrl==null) return cacheListaCrl=new TreeMap<String, List<CRL>>();
		return cacheListaCrl;
	}
	public static List<CRL> readCRLsFromCert(X509Certificate cert) throws Exception {
		List<CRL> l = getCacheListaCrl().get(""+cert.getIssuerDN());
		if (l!=null) return l;
		List<CRL> crls = new ArrayList();
		CRLDistributionPointsExtension ext = X509CertImpl.toImpl(cert).getCRLDistributionPointsExtension();
		if (ext == null) return crls;
		for (DistributionPoint o: (List<DistributionPoint>)ext.get(CRLDistributionPointsExtension.POINTS)) {
		  GeneralNames names = o.getFullName();
		  if (names != null) {
		      for (GeneralName name: names.names()) {
		          if (name.getType() == GeneralNameInterface.NAME_URI) {
		              URIName uriName = (URIName)name.getName();
		              Collection<CRL> crlREFLs;
		              try {
		              	crlREFLs=(Collection)Class.forName( "sun.security.tools.KeyTool" ).getDeclaredMethod("loadCRLs", String.class).invoke(null, uriName.getName());
		              	 
		              	} catch( ClassNotFoundException e ) {
		              		crlREFLs=(Collection)Class.forName( "sun.security.tools.KeyTool.Main" ).getDeclaredMethod("loadCRLs", String.class).invoke(null, uriName.getName());
//			              	 crls=Class.forName( "sun.security.tools.keytool.Mainl" ).getMethod("loadCRLs", uriName.getName()z);
		              	}
//		              for (CRL crl: sun.security.tools.keytool.Main.loadCRLs(uriName.getName())) { // java 8
//			            for (CRL crl: sun.security.tools.KeyTool.loadCRLs(uriName.getName())) { // java 7
		              for (CRL crl: crlREFLs) {
		                  if (crl instanceof X509CRL) {
		                  	crlREFLs.add((X509CRL)crl);
		                  }
		              }
		              break;  // Different name should point to same CRL
		          }
		      }
		  }
		}
		getCacheListaCrl().put(""+cert.getIssuerDN(), crls);
		return crls;
	}
	
	/**
	 * Verifies a single certificate.
	 * @param cert the certificate to verify
	 * @param crls the certificate revocation list or <CODE>null</CODE>
	 * @param calendar the date or <CODE>null</CODE> for the current date
	 * @return a <CODE>String</CODE> with the error description or <CODE>null</CODE>
	 * if no error
	 */    
	public static String verifyCertificate(X509Certificate cert, Collection crls, Calendar calendar) {
	    if (calendar == null)
	        calendar = new GregorianCalendar();
	    if (cert.hasUnsupportedCriticalExtension())
	        return "Extension no soportada de CRL";
	    try {
	        cert.checkValidity(calendar.getTime());
	    }
	    catch (Exception e) {
	        return e.getMessage();
	    }
	    if (crls != null) {
	        for (Iterator it = crls.iterator(); it.hasNext();) {
	            if (((CRL)it.next()).isRevoked(cert))
	                return "Certificado revocado";
	        }
	    }
	    return null;
	}
	static public String getSignPDFFile(String filename) throws Exception {
		String signer="";
		PdfReader reader=null;
		try {
			reader = new PdfReader(filename);
			AcroFields af = reader.getAcroFields();
			ArrayList names = af.getSignatureNames();
			for (int k = 0; k < names.size(); ++k) {
				String name = (String) names.get(k);
				PdfPKCS7 pk = af.verifySignature(name);
				Calendar cal = pk.getSignDate();
				Certificate pkc[] = pk.getCertificates();
				//signer += "| "+ PdfPKCS7.getSubjectFields(pk.getSigningCertificate()) + " - " +
				signer += "| " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()) + " - " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
			} 
		} finally {
			if (reader!=null)
				reader.close();
		}
		return signer;
	}
	
	public static void signUsuarioPDF(String pdfIn, String pdfOut,String pass, String reason, String location, boolean visible) throws Exception {
		signPdf(BizUsuarioFirma.obtainSignFile(BizUsuario.getUsr().GetUsuario()),pass,pdfIn,pdfOut,reason,location,visible);
	}
	/**
	 * Firmar un pdf
	 * @param pfx archivo de la firma en formato pfx
	 * @param pass clave del pfx
	 * @param pdf archivo pdf
	 * @param pdfSigner salida del archivo pdf firmado
	 * @param reason una razon para la firma, se adjunta al certificado
	 * @param location localizacion de la firma, se adjunta al certificado
	 * @throws Exception
	 */
	public static void signPdf(String pfx, String pass, String pdf, String pdfSigner, String reason, String location) throws Exception {
		signPdf(pfx, pass, pdf, pdfSigner, reason, location,true);
	}
	public static void signPdf(String pfx, String pass, String pdf, String pdfSigner, String reason, String location,boolean visible) throws Exception {
		KeyStore ks=KeyStore.getInstance("pkcs12");
		ks.load(new FileInputStream(pfx), pass.toCharArray());
		String alias=(String) ks.aliases().nextElement();
		PrivateKey key=(PrivateKey) ks.getKey(alias, pass.toCharArray());
		Certificate[] chain=ks.getCertificateChain(alias);
		PdfReader reader=new PdfReader(pdf);
		//FileOutputStream fout=new FileOutputStream(pdfSigner);
		FileOutputStream fout=new FileOutputStream(pdfSigner);
		PdfStamper stp=PdfStamper.createSignature(reader, fout, '\0');
		PdfSignatureAppearance sap=stp.getSignatureAppearance();
		
		//sap.setCrypto(key, chain, null, PdfSignatureAppearance.CERTIFIED_FORM_FILLING);
		sap.setReason(reason);
		sap.setLocation(location);
		// comment next line to have an invisible signature
		if (visible)
			sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
		
		
		 ExternalSignature es = new PrivateKeySignature(key, "SHA-256", "BC");
     ExternalDigest digest = new BouncyCastleDigest();
     MakeSignature.signDetached(sap, digest, es, chain, null, null, null, 0,MakeSignature.CryptoStandard.CMS);
		
 		stp.close();
		
	}

	public static void main(String[] args) {
		try {
			new PkiTools().checkPDFFile("c:\\dev\\default.jks", "pentapass", "adminca1",true,false, "c:\\dev\\remito2.pdf", false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static KeyPair generateKeys(String seed) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		random.setSeed(seed.getBytes());
		keyGen.initialize(1024, random);
		return keyGen.genKeyPair();
	}

	public static String generateSign(KeyPair key,byte[] data) throws Exception {
		PrivateKey priv = key.getPrivate();
		return generateSign(priv, data);
	}	
	
	public static boolean verifySign(KeyPair key,byte[] data,String sign) throws Exception {
		PublicKey pub = key.getPublic();
		return verifySign(pub,data,sign);
	}	
	
	public static String generateSign(byte[] data) throws Exception {
		return generateSign(readPrivateKey(JPath.PssPathData()),data);
	}
	
	public static String generateSign(PrivateKey priv,byte[] data) throws Exception {
		Signature dsa = Signature.getInstance("SHA1withDSA"); 
		
		dsa.initSign(priv);
		dsa.update(data);
		
		byte[] sig = dsa.sign();
		return JTools.BinaryToNibble(sig);
	}
	
	public static boolean verifySign(byte[] data,String sign) throws Exception {
		return verifySign(readPublicKey(JPath.PssPathData()),data,sign);
	}
	
	public static boolean verifySign(PublicKey pub,byte[] data,String sign) throws Exception {
		byte[] sig = JTools.NibbleToBinary(sign);
		Signature dsa = Signature.getInstance("SHA1withDSA"); 
		dsa.initVerify(pub);

		dsa.update(data);
		boolean verifies = dsa.verify(sig);
		return verifies;
	}
	
	static PublicKey cachePublic;
	public static PublicKey readPublicKey(String path) throws Exception {
		if (cachePublic!=null) return cachePublic;
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");

		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		return  keyFactory.generatePublic(publicKeySpec);
	}	

	public static void SaveKeyFromData(String path, KeyPair keyPair) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
 
		// Graba Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(path + "/public.key");
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
 
		// Graba Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		fos = new FileOutputStream(path + "/private.key");
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
	}

	static PrivateKey cachePrivate;
	public static PrivateKey readPrivateKey(String path) throws Exception {
		if (cachePrivate!=null) return cachePrivate;
		// Read Private Key.
		File filePrivateKey = new File(path + "/private.key");
		FileInputStream fis = new FileInputStream(path + "/private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		return  keyFactory.generatePrivate(privateKeySpec);
	}	

	public static KeyPair LoadKeyPair(String path, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(path + "/public.key");
		FileInputStream fis = new FileInputStream(path + "/public.key");

		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		// Read Private Key.
		File filePrivateKey = new File(path + "/private.key");
		fis = new FileInputStream(path + "/private.key");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();

		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}
	
	public static void SaveKeyPair(KeyPair keyPair) throws Exception {
		SaveKeyPair(JPath.PssPathData(),keyPair);
	}
	
	public static void SaveKeyPair(String path, KeyPair keyPair) throws IOException {
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
 
		// Graba Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(path + "/public.key");
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
 
		// Graba Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		fos = new FileOutputStream(path + "/private.key");
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
	}
	
  public static void sendFile(String filename,String url) throws Exception {
		ClientHttpRequest http=new ClientHttpRequest(url) {
			@Override
			public void step(long i) {
				PssLogger.logDebug("Uploading... "+i+" bytes");
			}
		};
		http.setParameter("uploaded_file", filename,  new ByteArrayInputStream(JTools.readFileAsArrayByte(filename)));
		java.io.InputStream response=http.post();
		GZIPInputStream  zin = new GZIPInputStream (response);
		String output = "";
		while (zin.available()!=0) output+=(char) (zin.read()&0xFF);
		zin.close();
	}
  
 
}
