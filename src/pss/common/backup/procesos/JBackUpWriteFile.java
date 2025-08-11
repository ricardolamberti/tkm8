package pss.common.backup.procesos;

import pss.core.data.files.JStreamFile;


public class JBackUpWriteFile {

  public JStreamFile oStream = null;
  private String pPath;
  private boolean pFileIsNew = true;


  /**
   * Constructor
   */
  public JBackUpWriteFile() {}

  public JBackUpWriteFile(String zPath) {
    this.pPath = zPath;
  }
  
  public boolean isNewFile() {
  	return pFileIsNew;
  }
  
 
  /**
   * Creo una nueva clase con los parametros
   */
  public void createFile( String zFile) throws Exception {
    if ( this.pPath != null ) {
      this.create( this.pPath + "/" + zFile  );
    } else {
    	this.create(zFile );
    }
  }
  
  private void create(String zFile) throws Exception {
  	zFile += ".backup";
  	this.oStream = new JStreamFile(zFile);
  	this.oStream.createDirectory(this.pPath);
  	if (this.oStream.doesFileExist())
  		this.pFileIsNew = false;
  	this.oStream.CreateNewFile(zFile,false);
  	/*if (this.oStream.doesFileExist()) {
  		this.oStream.Open(zFile);
  	}else {
  		this.oStream.CreateNewFile(zFile);
  	}*/
  }
  
  private void DeleteFile(String zFile) throws Exception {
  	this.oStream.Delete(zFile);
  }
  /**
   * Escribo el Constructor
   */
  public void writeConstructor( String zClass ) throws Exception {
    this.write( "\n" );
    this.write( "  public "+ zClass + "() throws Exception {"  );
  }
  /**
   * write method
   */
  public void write( String zBuf ) throws Exception {
    this.oStream.Write( zBuf + "\n" );
    this.oStream.Flush();
  }
  /**
   * Closes the stream
   */
  public void close() throws Exception {
    try {
      this.oStream.Close();
    } catch ( Exception e ) {}
  }

}
