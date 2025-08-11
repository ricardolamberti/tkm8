package pss.common.restJason.apiRestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pss.JPath;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.restJason.JServiceApi;
import pss.common.security.BizLoginTrace;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;



public class apiWebServiceTools {

	public static String	APPROVED								= "00";
	public static String	MORE_RECORDS						= "98";
	public static String	ERR_INVALID_USER				= "01";
	public static String	ERR_INVALID_PASSWD			= "02";
	public static String	ERR_NO_INFORMATION			= "40";
	public static String	ERR_INVALID_EXPEDIENTE	= "41";
	public static String	ERR_UNEXPECTED_ERROR		= "99";
	
	private static String SECRET_KEY = "Nhrm7167";
	private static long TOKEN_EXPIRATION_HOURS = 2;

	@SuppressWarnings("unchecked")
	public BizUsuario logIn(String usr, String passwd) throws Exception {
		if (usr==null)  JExcepcion.SendError("Usuario invalido");
		BizUsuario user = new BizUsuario();
		user.dontThrowException(true);
		user.addFilter("usuario", usr.toUpperCase());
		if (!user.read())
			JExcepcion.SendError("Usuario Invalido: " + usr);

//		try {
//
//			JRecords<BizLogTrace> t = new JRecords<BizLogTrace>(BizLogTrace.class);
//			t.addFilter("usuario", usr.toUpperCase());
//			t.addFilter("out_access", true);
//			t.addFilter("fecha", JDateTools.addMinutes(new Date(), -10), ">=", true);
//			t.addFilter("fecha", JDateTools.addMinutes(new Date(), 1), "<=", true);
//			t.setTop(10);
//			t.readAll();
//			t.firstRecord();
//			if (t.nextRecord()) {
//				user.validatePassword(passwd);
//			} else {
		BizLoginTrace login = new BizLoginTrace();
		login.SetLogin(usr.toUpperCase());
		login.SetPassword(passwd);
		login.SetIdApp(JAplicacion.AppTipoMovil());
		login.SetTipoApp(JAplicacion.AppTipoMovil());
		login.setIpAddress("200.0.0.0"); // Con esto me aseguro que ponga que es outaccess
		login.setOutAccess("M");
		login.setPersistent(false);
		login.SetValidarUsuario(true);

		login.execProcessInsert();
//			}
//		} catch (Exception eee) {
//			PssLogger.logError(eee);
//			throw new PssException(ERR_INVALID_PASSWD, "Clave Invalida");
//		}
		return user;
	}
	
	
	public static String generateToken(String user, String pw, String company) {
    return generateToken(user,pw,company, TOKEN_EXPIRATION_HOURS);
  }
	
	public static String generateToken(String user, String pw, String company, long tokenExpirationHours) {
    Claims claims = Jwts.claims().setSubject(user);
    claims.put("userId", user);
    claims.put("password", pw);
		claims.put("company", company);

		return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setExpiration(JDateTools.addHours(new Date(), tokenExpirationHours))
                .compact();
    }
   
   public static apiTokenData parseToken(String token) throws Exception {
	   apiTokenData ret = new apiTokenData();
       try {
           Claims body = Jwts.parser()
                   .setSigningKey(SECRET_KEY)
                   .parseClaimsJws(token)
                   .getBody();

           ret.setUserData(body.getSubject(), (String)body.get("password"));
           ret.setExpirationDate(body.getExpiration());
           ret.setCompany((String)body.get("company"));


       } catch (Exception e) {
    	   if (e instanceof ExpiredJwtException)
    		   JExcepcion.SendError("Token Vencido. Debe renovar el Token");
    		   //ret.setErrorMsg("Token Vencido. Debe renovar el Token");
    	   else
    		   JExcepcion.SendError(e.getMessage());
    		   //ret.setErrorMsg(e.getMessage());
       }
       
       return ret;
   }
   
   
   public static boolean validateTokenDate(apiTokenData token) {
	   Date expDate = token.getExpirationDate();
	   return expDate.after(new Date());
   }
   
   
   public static final List<Class<?>> getClassesInPackage(String packageName) {
	    String path = packageName.replaceAll("//", File.separator);
	    List<Class<?>> classes = new ArrayList<>();
	    String[] classPathEntries = System.getProperty("java.class.path").split(
	            System.getProperty("path.separator")
	    );

	    String name;
	    for (String classpathEntry : classPathEntries) {
	        if (classpathEntry.endsWith(".jar")) {
	            File jar = new File(classpathEntry);
	            try {
	                JarInputStream is = new JarInputStream(new FileInputStream(jar));
	                JarEntry entry;
	                while((entry = is.getNextJarEntry()) != null) {
	                    name = entry.getName();
	                    if (name.endsWith(".class")) {
	                        if (name.contains(path) && name.endsWith(".class")) {
	                            String classPath = name.substring(0, entry.getName().length() - 6);
	                            classPath = classPath.replaceAll("[\\|/]", ".");
	                            classes.add(Class.forName(classPath));
	                        }
	                    }
	                }
	            } catch (Exception ex) {
	                // Silence is gold
	            }
	        } else {
	            try {
	                File base = new File(classpathEntry + File.separatorChar + path);
	                for (File file : base.listFiles()) {
	                    name = file.getName();
	                    if (name.endsWith(".class")) {
	                        name = name.substring(0, name.length() - 6);
	                        classes.add(Class.forName(packageName + "." + name));
	                    }
	                }
	            } catch (Exception ex) {
	                // Silence is gold
	            }
	        }
	    }

	    return classes;
	}
   
   
   public static ArrayList<String>getClassNamesFromPackage(String packageName) throws Exception{
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL packageURL;
	    ArrayList<String> names = new ArrayList<String>();;

	    packageName = packageName.replace(".", "/");
	    packageURL = classLoader.getResource(packageName);

	    if(packageURL.getProtocol().equals("jar")){
	        String jarFileName;
	        JarFile jf ;
	        Enumeration<JarEntry> jarEntries;
	        String entryName;

	        // build jar file name, then loop through zipped entries
	        jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
	        jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
	        System.out.println(">"+jarFileName);
	        jf = new JarFile(jarFileName);
	        jarEntries = jf.entries();
	        while(jarEntries.hasMoreElements()){
	            entryName = jarEntries.nextElement().getName();
	            if(entryName.startsWith(packageName) && entryName.length()>packageName.length()+5){
	                entryName = entryName.substring(packageName.length(),entryName.lastIndexOf('.'));
	                names.add(entryName);
	            }
	        }

	    // loop through files in classpath
	    }else{
	    URI uri = new URI(packageURL.toString());
	    File folder = new File(uri.getPath());
	        // won't work with path which contains blank (%20)
	        // File folder = new File(packageURL.getFile()); 
	        File[] contenuti = folder.listFiles();
	        String entryName;
	        for(File actual: contenuti){
	            entryName = actual.getName();
	            if (entryName.lastIndexOf('.')>=0)
	            	entryName = entryName.substring(0, entryName.lastIndexOf('.'));
	            names.add(entryName);
	        }
	    }
	    return names;
	}
   
   public static File[] getPackageContent(String packageName) throws IOException{
	    ArrayList<File> list = new ArrayList<File>();
	    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
	                            .getResources(packageName);
	    while (urls.hasMoreElements()) {
	        URL url = urls.nextElement();
	        File dir = new File(url.getFile());
	        for (File f : dir.listFiles()) {
	            list.add(f);
	        }
	    }
	    return list.toArray(new File[]{});
	}
   
   
   public static JOrderedMap<String, String> getAnnotationsFrom(String dirName) throws Exception {
		File oFile = new File(dirName);
		File aFile[] = oFile.listFiles();

		if (aFile == null)
			JExcepcion.SendError("El directorio^ '" + dirName + JLanguage.translate("' no existe, pero esta configurado en el producto. Se deberá eliminarlo de la confiuración del producto."));

		JOrderedMap<String, String> tableClassesInfo = JCollectionFactory.createOrderedMap();
		for (int i = 0; i < aFile.length; i++) {
			if (!aFile[i].isDirectory()) { // Si el archivo no es un directorio
				String directoryName = oFile.getPath();
				String fileName =  aFile[i].getName();
				
				if (fileName.lastIndexOf(".class") == -1)
					continue;

				// Descarto las inner classes
				if (fileName.indexOf('$') != -1)
					continue;

				// Formateo y guardo el nombre del package  y de la clase.
				String packageName = directoryName.substring(JPath.PssPath().length() + 1).replace(File.separatorChar, '.');
				String className = fileName.substring(0, fileName.length() - 6);
				try {
					try {
						Class.forName(packageName + "." + className);
					} catch (Throwable a) {
						continue;
					}
					Class classElement = (Class) Class.forName(packageName + "." + className);
					Class anotationType = javax.ws.rs.Path.class;
					Annotation[] anotta = classElement.getAnnotationsByType(anotationType);
					if (anotta.length>0)
						tableClassesInfo.addElement(packageName + "." + className,((javax.ws.rs.Path)anotta[0]).value());
				} catch (ExceptionInInitializerError e) {
					continue;
				}
			} else { 
				//	Si la el archivo leido es un directorio lo proceso y guardo el resultado siempre y cuando
				// haya encontrado alguna clase valida.
				// Excepto sea core ó www
				String completeFileName = oFile.getPath() + "/" + aFile[i].getName();
				if (completeFileName.contains("pss/core") || completeFileName.contains("pss/www") ) {
					continue;
				}
				JMap<String, String> classestmp = apiWebServiceTools.getAnnotationsFrom(completeFileName);
				if (classestmp.size() > 0) {
					tableClassesInfo.addElements(classestmp);
				}
			} // end if
		} // end for
		
		return tableClassesInfo;
	}

   
	public static String checkJavaDirectory() throws Exception {
		return JPath.PssPath() + "/" + "pss";
	}

   
//	public static JList<Object> getListJsonItems(Object object, String key) throws Exception {
//		JList<Object> retList = JCollectionFactory.createList();
//		JSONArray jsonArray = apiWebServiceTools.getJSonArray(object, key);
//		for (int i=0; i<jsonArray.length();i++) {
//			retList.addElement(jsonArray.get(i));
//		}
//		return retList;
//	}
	
//	public static JSONArray getJSonArray(Object object, String key){
//		JSONArray jsonArray = new JSONArray();
//		if (object instanceof JSONObject) {
//			JSONObject jsonObject = (JSONObject) object;
//			if (jsonObject.has(key)) {
//				if (jsonObject.get(key) instanceof JSONArray) {
//					jsonArray = (JSONArray) jsonObject.get(key);
//				}
//			}
//		}
//		return jsonArray;
//	}
	

//	
//	public static Object getValueFromKey(Object object, String key){
//    if (object instanceof JSONObject) {
//        JSONObject jsonObject = (JSONObject) object;
//
//        if (jsonObject.has(key)) {
//        	   return jsonObject.get(key);
//        }
//
//        return jsonObject.keySet().stream()
//                .map(childKey -> getValueFromKey(jsonObject.get(childKey), key))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .orElse(null);
//    } else if (object instanceof JSONArray) {
//        JSONArray jsonArray = (JSONArray) object;
//
//        return IntStream.range(0, jsonArray.length())
//                .mapToObj(jsonArray::get)
//                .map(o -> getValueFromKey(o , key))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .orElse(null);
//    } else {
//        return null;
//    }
// }
		

}
