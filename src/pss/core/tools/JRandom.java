package pss.core.tools;

import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.tools.jnidisp.CFunc;
import pss.core.tools.jnidisp.CMalloc;

public class JRandom {

// Funciones en librería de C
// int getHandle(char * host); // 127.0.0.1:9999
// int getLicencia(int handle,char * licencia,char * seccion, char * clave, char * valor,int size,char * respuesta, int sizeResp);
// int detectHK(int handle,char * respuesta, int sizeResp);
// int Eco(int handle,char * respuesta, int sizeResp);
// int FreeHandle(int handle);

  public static final String LIC_PLATINO = "PLATINO";
  public static final String LIC_PLATINO_MODULOS = "MODULOS";
  public static final String LIC_PLATINO_MODULOS_HABILITADO = "HABILITADO";

  // Singleton Design Pattern
  private static JRandom oRandom;

  private int iHandle;

  static private String RandomController = "";

  final private CFunc getHandle;
  final private CFunc getLicencia;
  final private CFunc detectHK;
  final private CFunc doEco;
  @SuppressWarnings("unused")
	final private CFunc freeHandle;

  private String JRandomGetDll2() throws Exception {
    return "int";
  }

  private JRandom() throws Exception {
    RandomController = "Pr";
    RandomController += JRandomGetDll2();
    RandomController += JRandomGetDll3();
    RandomController += JRandomGetDll4();
    getHandle = new CFunc( RandomController, "getHandle" );
    getLicencia = new CFunc( RandomController, "getLicencia" );
    detectHK = new CFunc( RandomController, "detectHK" );
    doEco = new CFunc( RandomController, "Eco" );
    freeHandle = new CFunc( RandomController, "FreeHandle" );
  }

  private static synchronized JRandom getRandom() throws Exception {
    if( oRandom != null ) return oRandom;
    JRandom oRandomAux = new JRandom();
    if( !oRandomAux.openHandle( BizUsuario.getUsr().getObjNodo().getServidorLicencia() ) ) {
      JExcepcion.SendError( "No se puede establecer conexión con servidor de licencias" );
    }
    oRandom = oRandomAux;
    return oRandom;
  }

  public synchronized boolean openHandle( String sHost ) throws Exception {
    Object params[] = new Object[1];
    params[0] = sHost;
    int iRet = getHandle.callInt( params );
    switch( iRet ) {
      case 0:
        JExcepcion.SendError( "Error en conexión con servidor de autorizaciones" );
      case 1:
        return true;
      case 2:
      default:
        return false;
    }
  }

  public synchronized String[] get( String sA, String sB, String sC ) throws Exception {
// int getLicencia(int handle,char * licencia,char * seccion, char * clave, char * valor, int size, char * respuesta, int sizeResp);
    CMalloc cBuffer = new CMalloc(1024);
    CMalloc cValue = new CMalloc(1024);
    Object params[] = new Object[8];
    params[0] = new Integer( iHandle );
    params[1] = sA;
    params[2] = sB;
    params[3] = sC;
    params[4] = cValue;
    params[5] = new Integer( 1024 );
    params[6] = cBuffer;
    params[7] = new Integer( 1024 );
    getLicencia.callInt( params );
    String sRet[] = new String[2];
    sRet[0] = cValue.getString(0);
    sRet[1] = cBuffer.getString(0);
    cValue.free();
    cBuffer.free();
    return sRet;
  }

  public synchronized boolean detect() throws Exception {
// int detectHK(int handle,char * respuesta, int sizeResp);
    CMalloc cBuffer  = new CMalloc(1024);
    Object params[] = new Object[3];
    params[0] = new Integer( iHandle );
    params[1] = cBuffer;
    params[2] = new Integer( 1024 );
    boolean bRet = detectHK.callInt( params ) == 1;
    cBuffer.free();
    return bRet;
  }

  public synchronized boolean eco() throws Exception {
// int Eco(int handle,char * respuesta, int sizeResp);
    CMalloc cBuffer  = new CMalloc(1024);
    Object params[] = new Object[3];
    params[0] = new Integer( iHandle );
    params[1] = cBuffer;
    params[2] = new Integer( 1024 );
    boolean bRet = doEco.callInt( params ) == 1;
    cBuffer.free();
    return bRet;
  }
  private String JRandomGetDll3() throws Exception {
    return "erAdm.d";
  }

  private String JRandomGetDll4() throws Exception {
    return "ll";
  }
  public static void getRandom(String zA, String zB, String zC) throws Exception {
    String time = String.valueOf((new Date()).getTime()).trim();
    int i = Integer.parseInt(time.substring(time.length()-1));
    @SuppressWarnings("unused")
		String[] sRandom = null;
    if (i<=3) JRandom.getRandom().eco();
    if (i<=6 && i>3) JRandom.getRandom().detect();
    if (i>6) sRandom = JRandom.getRandom().get(zA,zB,zC);
  }


}
