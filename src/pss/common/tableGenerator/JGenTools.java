package pss.common.tableGenerator;

import pss.core.data.files.JStreamFile;


public class JGenTools {

  public JStreamFile oStream = new JStreamFile();
  private String pPath;


  /**
   * Constructor
   */
  public JGenTools() {}

  public JGenTools(String zPath) {
    this.pPath = zPath;
  }
  /**
   * Creo una nueva clase con los parametros
   */
  public void createFile( String zFile ) throws Exception {
    if ( this.pPath != null ) {
      this.oStream.CreateNewFile( this.pPath + "/" + zFile );
    } else {
      this.oStream.CreateNewFile(zFile);
    }
  }
  /**
   * Creo una nueva clase con los parametros
   */ 
  public void createAndWritePackageAndImports( String zFile, String zPkg ) throws Exception {
    this.oStream.createDirectory(this.pPath);
    if ( this.pPath != null ) {
      this.oStream.CreateNewFile( this.pPath + "/" + zFile + ".java" );
    } else {
      this.oStream.CreateNewFile(zFile + ".java");
    }
    this.write( "package "+ zPkg +";\n"   );
  }
  /**
   * Escribo la clase
   */
  public void writeClass( String zClass, String zExtend ) throws Exception {
    this.write( "public class " + zClass + " extends "+zExtend+" {\n" );
  }
  /**
   * Escribo el Constructor
   */
  public void writeConstructor( String zClass ) throws Exception {
    this.write( "\n" );
    this.writeComment( "Constructor de la Clase" );
    this.write( "  public "+ zClass + "() throws Exception {"  );
  }
  /**
   * Escribo comentario de las Properties
   */
  public void writeComment( String zComment ) throws Exception  {
    this.write( "  /**" );
    this.write( "   * " + zComment );
    this.write( "   */" );
  }
  /**
   * Writes a methods separator
   */
  public void writeSeparator(String zMethodsDescription) throws Exception {
    this.write("\n");
    this.write("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
    this.write("// " + zMethodsDescription);
    this.write("//////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
    this.write("\n");
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Escribo comentario de las Properties
   */
  public void writeEndOfConstructorAndClass() throws Exception  {
    this.write( "\n  } " );
    this.write( "} "     );
    this.close();
  }
  /**
   * Escribo comentario de las Properties
   */
  public void writeEndMethod() throws Exception  {
    this.write( "\n  } " );
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
