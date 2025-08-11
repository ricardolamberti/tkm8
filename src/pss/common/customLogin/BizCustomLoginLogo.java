package pss.common.customLogin;

import java.io.File;
import java.net.URLDecoder;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;

public class BizCustomLoginLogo  extends JRecord {

  private JString pDirectory = new JString();
  private JString pFile = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setDirectory(String zValue) throws Exception {    pDirectory.setValue(zValue);  }
  public String getDirectory() throws Exception {     return pDirectory.getValue();  }
  public void setFile(String zValue) throws Exception {    pFile.setValue(zValue);  }
  public String getFile() throws Exception {     return pFile.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizCustomLoginLogo() throws Exception {
  }


  @Override
	public void createProperties() throws Exception {
    this.addItem( "directory", pDirectory );
    this.addItem( "file", pFile );

  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "directory", "directory", true, false, 500 );
    this.addFixedItem( FIELD, "file", "file", true, true, 500 );
   

  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void processInsert() throws Exception {
		String filename=getFile();
		if (filename.equals("")) throw new Exception("No se especifico archivo");
		filename=URLDecoder.decode(filename);
		File f = new File(filename);
		if (f==null) throw new Exception("Error al leer archivo");
		JTools.copyFile(filename, getDirectory()+"/"+f.getName());
  }
  
  public void processUpdate() throws Exception {
		String filename=getFile();
		if (filename.equals("")) throw new Exception("No se especifico archivo");
		filename=URLDecoder.decode(filename);
		File f = new File(filename);
		if (f==null) throw new Exception("Error al leer archivo");
		JTools.copyFile(filename, getDirectory()+f.getName());
  }
}