package pss.core.data.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pss.core.tools.XML.JXMLSerializer;

public class JStreamFile extends JBaseFile {

  //-------------------------------------------------------------------------- //
  // Propiedades publicas y privadas de la clase
  //-------------------------------------------------------------------------- //
  private BufferedReader oRBuffer; // Manejo del archivo para su lectura
  private BufferedWriter oWBuffer; // Manejo del archivo para su escritura
  private JXMLSerializer oXMLSerializer;
  //  URL                          uURL;

  private File pFile;

  //-------------------------------------------------------------------------- //
  // Creo un nuevo archivo de texto
  //-------------------------------------------------------------------------- //
  @Override
	public void CreateNewFile(String zFile) throws Exception {
    sFileName = zFile;
    pFile = new File( sFileName );
    pOutput = new FileOutputStream(pFile, bAppend);
    oWBuffer = new BufferedWriter(new OutputStreamWriter(pOutput));
  }

  public JStreamFile(String FileName) throws Exception {
    sFileName = FileName;
  }

  public JStreamFile() {
    super();
  }
  /**
   * Creates a directory
   * @author bugui!!!
   */
  public void createDirectory(String zPath) throws Exception {
    File oFile = new File(zPath);
    if (oFile.isDirectory() && oFile.exists())
      return;
    oFile.mkdir();
  }

  //-------------------------------------------------------------------------- //
  // Creo un nuevo archivo de texto.  Se puede destruir si existe              //
  //-------------------------------------------------------------------------- //
  public void CreateNewFile(String zFile, boolean DestroyIfExist) throws Exception {
    sFileName = zFile;
    pFile = new File(zFile);
    if (DestroyIfExist) {
      pFile.delete();
    } else {
      SetAppend(true);
    }
    pOutput = new FileOutputStream(pFile, bAppend);
    oWBuffer = new BufferedWriter(new OutputStreamWriter(pOutput));
  }

  public long size() {
    return 10;
  }

  //-------------------------------------------------------------------------- //
  // Open de un archivo
  //-------------------------------------------------------------------------- //
  @Override
	public void Open(InputStream zFile) throws Exception {
    pInput = zFile;
    oRBuffer = new BufferedReader(new InputStreamReader(pInput));
    bIsOpen = true;
	}
  @Override
	public void Open(String zFile) throws Exception {
    pFile = new File( zFile );
    pInput = new FileInputStream(pFile);
    oRBuffer = new BufferedReader(new InputStreamReader(pInput));
    bIsOpen = true;
  }

  public boolean ifFileExist() throws Exception {
    return this.pInput != null;
  }

  // --------------------------------------------------------------------- //
  // FP hice esta, porque la anterior no me funciona y no quiero cambiarla //
  // --------------------------------------------------------------------- //
  public boolean doesFileExist() throws Exception {
    File oFile = new File(sFileName);
    return oFile.exists();
  }

  //-------------------------------------------------------------------------- //
  // NewLine en una linea
  //-------------------------------------------------------------------------- //
  @Override
	public void NewLine(OutputStream zOut) throws Exception {
    // No funciona por eso se comenta para que no imprima lineas en blanco en el log
    //oWBuffer.newLine();
  } // NewLine

  //
  //  escribe XML
  //
  public void writeXML(Node zXMLNode) throws Exception {
    this.getXMLSerializer().serialize(this.oWBuffer, (Element)zXMLNode);
  }

  /**
   * Returns an instance of xmlserializer
   * @return
   * @throws Exception
   */
  private JXMLSerializer getXMLSerializer() throws Exception {
    if (this.oXMLSerializer == null) {
      this.oXMLSerializer = new JXMLSerializer();
    }
    return this.oXMLSerializer;
  }

  //
  //  cierra el archivo
  //
  @Override
	public void Close() throws Exception {
    if (this.oWBuffer != null) {
      this.oWBuffer.close();
    }
    if (this.oRBuffer != null) {
      this.oRBuffer.close();
    }
    super.Close();
    this.oXMLSerializer = null;
  }

  //-------------------------------------------------------------------------- //
  // Leo una linea
  //-------------------------------------------------------------------------- //
  @Override
	public String ReadLine(InputStream zIn) throws Exception {
    return oRBuffer.readLine();
  }
  // ---------------------------------------------------------------------------- //
  public String ReadLine() throws Exception {
    return oRBuffer.readLine();
  }
  // ---------------------------------------------------------------------------- //
  public void altaln(String Registro) throws Exception {
    SetAutoFlushOn();
    if (sFileName == "") {
      return;
    }
    //CreateNewFile(sFileName, false);
    if (!isFileOpen()) {
      Open(sFileName);
    }
    WriteLn(Registro);
    //Close();
  }
  // ---------------------------------------------------------------------------- //
  public static void altaln(String zFileName, String Registro) throws Exception {
    JStreamFile oFile = new JStreamFile(zFileName);
    if (!oFile.doesFileExist()) {
      oFile.CreateNewFile(zFileName);
    }
    oFile.altaln(Registro);
  }
  // ---------------------------------------------------------------------------- //
  public static void alta(String zFileName, String Registro) throws Exception {
    JStreamFile oFile = new JStreamFile(zFileName);
    if (!oFile.doesFileExist()) {
      oFile.CreateNewFile(zFileName);
    }
    oFile.alta(Registro);
  }
  // ---------------------------------------------------------------------------- //
  public void alta(String Registro) throws Exception {
    if (sFileName == "") {
      return;
    }
    if (isFileOpen()) {
      Open(sFileName);
    }
    Write(Registro);
    Close();
  }

  // MLudeiro optimizando (asi no tengo que abrir el archivo para saber el tamaño
  // por CADA LINEA DE LOG, aouch!!!)
  public long getLenght() throws Exception {
    return pFile.length();
  }

// ---------------------------------------------------------------------------- //
}
