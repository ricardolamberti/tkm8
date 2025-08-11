package  pss.common.components;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;

import pss.JPath;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.win.modules.GuiModulo;

public class BizCompInstalado extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pComponente = new JString();
  JString pCompPadre  = new JString();
  JString pClase  = new JString();
  JString pDescrip    = new JString() { @Override
	public void preset() throws Exception {ObtenerDescrip();}};

  boolean bAlreadyChequed = false;
  GuiModulo oModulo = null;
  BizCompInstalados oSubComps = null;
  BizCompInstalados oSubModulos = null;

  public String GetComponente() throws Exception { return pComponente.getValue(); }
  public String GetCompPadre()  throws Exception { return pCompPadre.getValue(); }
  public String GetClase()  throws Exception { return pClase.getValue(); }
  public String GetDescription() throws Exception { return pDescrip.getValue(); }
  public void setComponente(String zValue) throws Exception { pComponente.setValue(zValue); }
//  public JList getDirectories() { return aDirectories; }


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizCompInstalado() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "componente" , pComponente );
    addItem( "comp_padre" , pCompPadre );
    addItem( "clase"      , pClase );
    addItem( "descripcion", pDescrip );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY  , "componente" , "Componente", true, true, 100 );
    addFixedItem( FIELD  , "comp_padre" , "Componente Padre", true, true, 100 );
    addFixedItem( FIELD  , "clase"      , "Clase"       , true, true, 100 );
    addFixedItem( VIRTUAL, "descripcion", "Descripcion", true, true, 50 );
  }


  @Override
	public String GetTable() {return "";}

/*  public boolean Read(String zComp) throws Exception {
    pComponente.SetValor(zComp);
    int idx = zComp.lastIndexOf("/");
    pCompPadre.SetValor(zComp.substring(0, idx) );
    pClase.SetValor(ObtenerModulo());
    return true;
  }
*/
  private void ObtenerDescrip() throws Exception {
    StringTokenizer st = new StringTokenizer( pComponente.getValue(), "/" );
    int cant = st.countTokens();
    for ( int i=0; st.hasMoreTokens(); i++ ) {
      String param = st.nextToken();
      if ( i == cant-1  ) {
        pDescrip.setValue(param);
        return;
      }
    }
  }

  public boolean isModule() throws Exception {
    return this.getModulo() != null;
  }
  public boolean isDynamicModule() throws Exception {
     if (this.getModulo() == null) return false;
     return this.getModulo().isModuleComponent();
  }

  public GuiModulo getModulo() throws Exception {
    if ( !bAlreadyChequed ) this.checkFiles();
    return this.oModulo;
  }
/*
  public boolean isModule(boolean bDynamicModule ) throws Exception {
    checkFiles();
    if ( !isModule() ) return false;
    if ( bDynamicModule ) return true;
    return this.getModulo().isModuleComponent();
  }
*/
  public GuiModulo createModulo(String zFile) throws Exception {
    String clase = FileToClass(pComponente.getValue(), zFile);
    GuiModulo oModuloAsociado = (GuiModulo)Class.forName(clase).newInstance();
    oModuloAsociado.setCompAsociado(this);
    return oModuloAsociado;
  }
 
  private void checkFiles() throws Exception {
    if ( bAlreadyChequed ) return;
  	if(JPath.isJar())
  		checkFilesInJar();
  	else
  		checkFilesInDirectory();
  }  
  
  private void checkFilesInDirectory() throws Exception {
//    if ( this.oModulo != null ) return this.oModulo;
    oSubComps = new BizCompInstalados();
    oSubComps.setStatic(true);
    
    File aFile[] = new File(JPath.PssPath() + "/" + pComponente.getValue()).listFiles() ;
    if (aFile==null)
    	return;
    for ( int i=0; i < aFile.length ; i++ ) {
      if ( BizCompInstalado.isModulo(aFile[i]) ) {
        this.oModulo = this.createModulo(aFile[i].getName());
      }
      if (BizCompInstalado.isDirectory(aFile[i])) {
        BizCompInstalado oComp = new BizCompInstalado();
        oComp.pCompPadre.setValue(pComponente.getValue());
        oComp.pComponente.setValue(pComponente.getValue()+"/"+aFile[i].getName());
        this.addSubComp(oComp);
      }
    }
    bAlreadyChequed= true;
  }
  
  private void checkFilesInJar() throws Exception {
    oSubComps = new BizCompInstalados();
    oSubComps.setStatic(true);
  //  PssLogger.logInfo(pComponente.getValue());
  	String directory = pComponente.getValue();

  	File aFile[] = new File( JPath.PssPathJAR() ).listFiles() ;
    if (aFile==null) return;
    for ( int i=0; i < aFile.length ; i++ ) {
    	if (!aFile[i].getName().startsWith("astor-")) continue;
    	if (!aFile[i].getName().endsWith(".jar")) continue;
		  List<JarEntry> list =JTools.getJarContent(aFile[i].getAbsolutePath(), directory);
		  for ( JarEntry entry:list) {
		  	String onlyName = entry.getName().substring(entry.getName().lastIndexOf("/")+1);
		  	String fullName = entry.getName();
		  	if (onlyName.endsWith("/")) onlyName=onlyName.substring(0,onlyName.length()-1);
		  	if (fullName.endsWith("/")) fullName=fullName.substring(0,fullName.length()-1);
		  	if ( BizCompInstalado.isModulo(onlyName) ) {
		      this.oModulo = this.createModulo(onlyName);
		    }
		    if (BizCompInstalado.isDirectory(entry)) {
		      BizCompInstalado oComp = new BizCompInstalado();
		      oComp.pCompPadre.setValue(pComponente.getValue());
		      oComp.pComponente.setValue(fullName);
		      this.addSubComp(oComp);
		    }
		  }
    }
	  bAlreadyChequed= true;

  
  }



  private void addSubComp(BizCompInstalado zComp) throws Exception {
    oSubComps.addItem(zComp);
  }

  public BizCompInstalados getSubComponentesModulos(boolean bDynamicModule) throws Exception {
    checkFiles();  // barrido de files para buscar directorios
    if (oSubComps == null) return null;
    if (oSubModulos != null) return oSubModulos;
    oSubModulos = new BizCompInstalados();
    oSubModulos.setStatic(true);
    oSubComps.firstRecord();
    while (oSubComps.nextRecord()) {
      BizCompInstalado oSubComp = (BizCompInstalado)oSubComps.getRecord();
      oSubComp.checkFiles(); // barrida de files para buscar que directorio es modulo
      if ( oSubComp.isModule() ) {
        if ( !bDynamicModule && !oSubComp.getModulo().isModuleComponent()) continue;
        this.addSubModulo(oSubComp);
      }
    }
    return oSubModulos;
  }

  private void addSubModulo(BizCompInstalado zSubModulo) throws Exception {
    oSubModulos.addItem(zSubModulo);
  }

  private static boolean isDirectory(File zFile) {
    String zFileName = zFile.getName();
    boolean bRet = // Si no es alguno de estos files
    !( zFileName.endsWith(".class") || zFileName.endsWith(".gif") ||
       zFileName.endsWith(".jpg")   || zFileName.endsWith(".wav") ||
       zFileName.endsWith(".zip")   || zFileName.endsWith(".jar") ||
       zFileName.endsWith(".ini")   || zFileName.endsWith(".dat") );

    if ( bRet == false ) return bRet;
    return zFile.isDirectory();
  }
  private static boolean isDirectory(JarEntry zFile) {
    String zFileName = zFile.getName();
    boolean bRet = // Si no es alguno de estos files
    !( zFileName.endsWith(".class") || zFileName.endsWith(".gif") ||
       zFileName.endsWith(".jpg")   || zFileName.endsWith(".wav") ||
       zFileName.endsWith(".zip")   || zFileName.endsWith(".jar") ||
       zFileName.endsWith(".ini")   || zFileName.endsWith(".dat") );

    if ( bRet == false ) return bRet;
    return zFile.isDirectory();
  }
  private static boolean isModulo(String zFile)  {
    String sName;
    return //!zFile.isDirectory() &&
        zFile.startsWith("GuiModulo") &&
        !zFile.equals("GuiModulo.class") &&
        !zFile.equals("GuiModulos.class") &&
        zFile.endsWith(".class") &&
        zFile.indexOf("$") < 0;
  }

  private static boolean isModulo(File zFile)  {
    String sName;
    return //!zFile.isDirectory() &&
        (sName = zFile.getName()).startsWith("GuiModulo") &&
        !sName.equals("GuiModulo.class") &&
        !sName.equals("GuiModulos.class") &&
        sName.endsWith(".class") &&
        sName.indexOf("$") < 0;
  }

  public static String FileToClass(String zDir, String zFile) throws Exception {
    return zDir.replace('/', '.') + "." + zFile.substring(0, zFile.length()-6);
  }

  @Override
	public void processDelete() throws Exception {
    Borrar(pComponente.getValue());
  }

  private void Borrar(String zFile) throws Exception {
    File        oFile = new File(zFile);
    File        aFile[] = oFile.listFiles() ;
    for ( int i=0; i < aFile.length ; i++ ) {
      if ( aFile[i].isDirectory() ) {
        Borrar(zFile+"/"+aFile[i].getName());
        continue;
      }
      aFile[i].delete();
    }
    oFile.delete();
  }

}
