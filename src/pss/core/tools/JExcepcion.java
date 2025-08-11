
//Title:        Project Benetton
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       Sgalli
//Company:
//Description:  Manejo de Excepciones ( UException en Delphi )

package pss.core.tools;

import java.util.Vector;

public class JExcepcion extends Exception {

  //-------------------------------------------------------------------------- //
  // Propiedades publicas y privadas de la clase
  //-------------------------------------------------------------------------- //
  public static final int C_EXCEPTION_FATAL_ERROR = 0;
  public static final int C_EXCEPTION_HANDLED_ERROR = 1;
  public static final int C_ESCAPE = 2;
  public static final String C_SYSTEM_ERROR = "9999";
  private Vector<String> vFuncPath;
  private int iSeverity;
  private String sErrorCode;

  public String GetErrorCode() {
    return sErrorCode;
  }
  int GetSeverity() {
    return iSeverity;
  }

  public void SetErrorCode(String zVal) {
    sErrorCode = zVal;
  };
  public void SetSeverity(int zVal) {
    iSeverity = zVal;
  };

  //========================================================================== //
  // Metodos de la clase
  //========================================================================== //

  public boolean ifSevEscape() {
    return iSeverity == C_ESCAPE;
  }

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
  public JExcepcion(String zErrCode, String zMje, String zFuncOrigen) {
    this("", zErrCode, zMje, zFuncOrigen);
  } // JExcepcion

  /**
   * Bugui constructor
   * @param errCode
   * @param msg
   */
  public JExcepcion(String errCode, String msg) {
    this(errCode, msg, "");
  }

  public JExcepcion(String zMsg) {
    this("", zMsg, "");
  }

  public JExcepcion(String zCode, String zMessage, Throwable cause) {
    super(zMessage, cause);
    vFuncPath = new Vector<String>();
    vFuncPath.addElement("");
    iSeverity = C_EXCEPTION_HANDLED_ERROR;
    sErrorCode = zCode;
  }

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
  public JExcepcion(String zOrigen, String zErrCode, String zMje, String zFuncOrigen) {
    super(zMje);
    vFuncPath = new Vector<String>();
    vFuncPath.addElement(zFuncOrigen);
    iSeverity = C_EXCEPTION_HANDLED_ERROR;
    sErrorCode = zErrCode;
  } // JExcepcion

  //-------------------------------------------------------------------------- //
  // AddFunction()
  // Agrega una funcion al camino de funciones
  //-------------------------------------------------------------------------- //
  public void AddFunction(String zFuncOrigen) {
    vFuncPath.addElement(zFuncOrigen);
  } // AddFunction

  //-------------------------------------------------------------------------- //
  // GetCantOfFunctions()
  // Retorna la cantidad de funciones en el path de funciones
  //-------------------------------------------------------------------------- //
  public int GetCantOfFunctions() {
    return vFuncPath.size();
  } // GetCantOfFunctions

  //-------------------------------------------------------------------------- //
  // GetAllFunctionPath()
  // Retorna el path completo de funciones que recorrio
  //-------------------------------------------------------------------------- //
  public String GetAllFunctionPath() {
    int iIndex;
    int iCantFunc;
    String sAux = new String();

    iCantFunc = vFuncPath.size();
    for (iIndex = 0; iIndex < iCantFunc; iIndex++) {
      if (iIndex == 0)
        sAux = vFuncPath.elementAt(iIndex).toString();
      else
        sAux = sAux + " <- " + vFuncPath.elementAt(iIndex).toString();
    }
    return sAux;

  } // GetAllFunctionPath

  //-------------------------------------------------------------------------- //
  // GetFunctionInPath(int zIndex)
  // Retorna una funcion del path segun su indice
  //-------------------------------------------------------------------------- //
  public String GetFunctionInPath(int zIndex) {
    return vFuncPath.elementAt(zIndex).toString();
  } // GetFunctPath

  //-------------------------------------------------------------------------- //
  // ProcesarError( Exception zExcep, String zFunc )
  // Proceso un error
  //-------------------------------------------------------------------------- //
  public static void ProcesarError(Exception zExcep, String zFunc) throws JExcepcion {
    JExcepcion oException;

    if (zExcep.getClass().getName().equals("pss.core.JExcepcion")) {
      oException = (JExcepcion)zExcep;
      if (zFunc.length() > 0) {
        oException.AddFunction(zFunc);
      }
    } else {
      oException = new JExcepcion("INTERNAL", "9999", zExcep.getMessage(), zFunc);

      oException.iSeverity = C_EXCEPTION_FATAL_ERROR;
    }
    throw oException;
  } // ProcesarError

  //-------------------------------------------------------------------------- //
  // SendError( String zErrCode, String zMje, int zSeverity )
  // Genero una excepcion propia
  //-------------------------------------------------------------------------- //
  public static void SendError(String zMje) throws JExcepcion {
	
    SendError("1500", zMje, C_EXCEPTION_FATAL_ERROR);
  } // SendError

  public static void SendError(String zErr, String zMje) throws JExcepcion {
    SendError(zErr, zMje, C_EXCEPTION_FATAL_ERROR);
  } // SendError

  
  public static void SendError(String zMje, int zSeverity) throws JExcepcion {
    SendError("1500", zMje, zSeverity);
  }

  public static void SendError(String zErrCode, String zMje, int zSeverity) throws JExcepcion {
    JExcepcion oException;
    oException = new JExcepcion("INTERNAL", zErrCode, zMje, "");
    oException.iSeverity = zSeverity;
    throw oException;

  } // SendError
  
  
  public static String getFullExceptionMessage(Throwable e) {
    String msg;
    if (e instanceof Error) {
      msg = getErrorMessage((Error) e);
    } else if (e instanceof RuntimeException) {
      msg = getRuntimeExceptionMessage((RuntimeException) e);
    } else if (e instanceof JExcepcion) {
      msg = e.getMessage();
    } else { // es Exception
      msg = e.getMessage();
    }
    if (msg == null || msg.trim().length() < 1) {
      msg = "Error desconocido";
    }
    if (e.getCause() != null) {
      msg += ":\n" + getFullExceptionMessage(e.getCause());
    }
    //    JDebugPrint.logError(e);
    return msg;
  }

  private static String getErrorMessage(Error err) {
    String errDescr;
    if (err instanceof UnsatisfiedLinkError) {
      errDescr = "No se puede encontrar una función nativa";
    } else if (err instanceof StackOverflowError) {
      errDescr = "Error de desbordamiento de pila";
    } else if (err instanceof VirtualMachineError) {
      errDescr = "Error de la Máquina Virtual Java";
    } else if (err instanceof NoSuchMethodError) {
      errDescr = "No se puede encontrar un método";
    } else if (err instanceof NoClassDefFoundError) {
      errDescr = "No se puede encontrar una clase";
    } else {
      errDescr = "";
    }
    return getAppendedMessage(errDescr, err);
  }

  private static String getRuntimeExceptionMessage(RuntimeException exc) {
    String excDescr;
    if (exc instanceof IndexOutOfBoundsException) {
      excDescr = "Indice fuera de rango";
    } else if (exc instanceof ArithmeticException) {
      excDescr = "Error de aritmética";
    } else if (exc instanceof NullPointerException) {
      excDescr = "Referencia a objeto nulo";
    } else {
      excDescr = "";
    }
    return getAppendedMessage(excDescr, exc);
  }
  
  private static String getAppendedMessage(String zBaseMessage, Throwable zException) {
    String msg = zException.getMessage();
    String result;
    if (zBaseMessage != null) {
      if (msg != null) {
        result = zBaseMessage + ":\n" + msg;
      } else {
        result = zBaseMessage;
      }
    } else if (msg != null) {
      result = msg;
    } else {
      result = "";
    }
    return result;
  }
  
}
