package pss.core.data.files;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import pss.core.services.fields.JString;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JIniFile {

  //-------------------------------------------------------------------------- //
  // Propiedades publicas y privadas de la clase
  //-------------------------------------------------------------------------- //
  private String              sFileName = null;              // Nombre del archivo
  private JMap<String, Params>                oSections = null;              // Secciones
  private boolean             bIfModified = false;           // Flag, se activa al modificar algun parametros
  private boolean             bEOF = false;

  private JMap<String, String>               oSectionsOrder = JCollectionFactory.createMap();

  //-------------------------------------------------------------------------- //
  // Properties
  //-------------------------------------------------------------------------- //
  // public String GetPath()     { return sPath;}
  public String GetFileName()   { return sFileName;}
  public boolean IfModified ()  { return bIfModified;}

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
  public JIniFile(String zFileName) throws Exception {
    sFileName = zFileName;
    oSections = JCollectionFactory.createMap();
    oSections.addElement(new String("[]"), new Params());
    oSectionsOrder.addElement(String.valueOf(0), new String("[]"));
    loadSections();
  }

  public static BufferedInputStream OpenFileToRead(String zFileName) throws Exception {
    Object obj;
    if(zFileName.indexOf("://") == -1) {
      obj = new FileInputStream(zFileName);
    }
    else {
      URL           url           = new URL(zFileName);
      URLConnection urlConnection = url.openConnection();
      obj                         = urlConnection.getInputStream();
    }
    return new BufferedInputStream(((java.io.InputStream) (obj)));
  }



  public String ReadLine(BufferedInputStream pInput) throws Exception {
    int          iAux;
    char         cAux = 0;
    StringBuffer sBuffer = new StringBuffer();

    while (true) {
      iAux = pInput.read();
      cAux = (char) iAux;
      if (cAux == '\n' || iAux == -1) break;
      if (cAux != '\r') sBuffer.append(cAux);
    }

    if (iAux == -1) // If EOF found
      bEOF = true;

    return sBuffer.toString().trim();
  }


  private void loadSections() throws Exception {
    String              sLine       = new String("");
    String              sSector     = null;
    String              sParametro;
    String              sValor;
    int                 iLineNr     = 0;
    int                 iIndexOf;
    BufferedInputStream pInput      = null;
    // JHash               oParameters = null;            // Parametros por cada seccion
    Params              oParameters = null;             // Parametros por cada seccion

    try {
      pInput = OpenFileToRead(this.sFileName);

      while (!this.IfEOF()) {
        iLineNr++;
        sLine = ReadLine(pInput);

        if (sLine == null || sLine.length() == 0) continue;

        if (sLine.charAt(0) == '[') { // Comienza un sector
          if (sLine.charAt(sLine.length() - 1) != ']')
            JExcepcion.SendError("Error de formato en archivo - linea nro^ " + this.sFileName +  " - " + iLineNr );
          sSector = sLine.substring(1, sLine.length()-1);
          if (oSections.getElement(sSector) == null) {
            oParameters = new Params();
            oSections.addElement(sSector, oParameters);
            oSectionsOrder.addElement(String.valueOf(oSectionsOrder.size()), sSector);
          }
          continue;
        }

        // es un parametro o una linea de comentario
        if (oParameters == null) { // Copia textual el encabezado del ini
          JIterator<Params> iter = oSections.getValueIterator();
          oParameters =  iter.nextElement();
        }

        iIndexOf = sLine.indexOf("=");
        if (iIndexOf == -1 || sLine.charAt(0) == ';' || sLine.charAt(0) == '#') {
          oParameters.AddItem(sLine, new JString(""));
        }
        else {
          sParametro  = sLine.substring(0, iIndexOf).trim();
          sValor      = sLine.substring(iIndexOf+1).trim();
          oParameters.AddItem(sParametro, new JString(sValor));
        }
      } // end while
    } // try
    catch( Exception E ) {
      JExcepcion.ProcesarError( E, this.getClass().getName() + ".ReadAll" );
    } // catch
    finally {
      if (pInput != null) {
        pInput.close();
      }
    }
  } // ReadAll

  

  public String GetParamValue( String zSection, String zParam ) throws Exception {
    return GetParamValue(zSection, zParam, null);
  }

  public String GetParamValue( String zSection, String zParam, String zDefault ) throws Exception {
    Params oParams;
    try {
      if (!this.IfSectionExist(zSection)) return zDefault;
      oParams = oSections.getElement(zSection);
      if (oParams == null) return zDefault;
      if (!oParams.oParams.containsKey(zParam)) return zDefault;
      return ((JString) oParams.oParams.getElement(zParam)).getValue();
    } // try
    catch( Exception E ) {
      JExcepcion.ProcesarError( E, this.getClass().getName() + "GetParamValue" );
      return null;
    } // catch
  } // GetParamValue


  public boolean IfSectionExist(String zSection) throws Exception {
    return (this.oSections.containsKey(zSection));
  }

  public boolean IfParamExist (String zSeccion, String zParam) throws Exception {
     Params oParam;

    if (!this.IfSectionExist(zSeccion)) return false;
    oParam = oSections.getElement(zSeccion);
    return oParam.oParams.containsKey(zParam);
  }


  public String[] GetSectionNames() {
    String      aSections[] = new String[oSections.size()];
    JIterator<String> iter = oSections.getKeyIterator();
    int         iIndex = 0;

    while (iter.hasMoreElements())
      aSections[iIndex++] = iter.nextElement();

    return aSections;
  }


  public boolean IfEOF() throws Exception { return this.bEOF;}

}

class Params {
  JMap<String, Object> oParamOrder;
  JMap<Object, Object> oParams;

  Params () {
    oParamOrder = JCollectionFactory.createMap();
    oParams     = JCollectionFactory.createMap();
  }

  public void AddItem( Object zKey, Object zElem ) {
    oParams.addElement( zKey, zElem );
    oParamOrder.addElement(String.valueOf(oParamOrder.size()), zKey);
  }

}
