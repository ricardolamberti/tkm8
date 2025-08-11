package  pss.common.components;

import pss.JPath;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.PssLogger;
import pss.core.tools.JTools;

public class BizCompAlta extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pComp       = new JString();
  JBoolean pRecursivo  = new JBoolean();
  JBoolean pIncludeTables= new JBoolean();
  JBoolean pIncludeSource= new JBoolean();
  JBoolean pIncludeClass= new JBoolean();
  JInteger pDataLevel = new JInteger();

  public void setComponente(String zValue) { pComp.setValue(zValue);}
  public void setIncludeTables(boolean zValue) { pIncludeTables.setValue(zValue);}
  public void setIncludeSource(boolean zValue) { pIncludeSource.setValue(zValue);}
  public void setIncludeClass(boolean zValue) { pIncludeClass.setValue(zValue);}
  public void setDataLevel(int zValue) { pDataLevel.setValue(zValue);}
  public void setRecursivo(boolean zValue) { pRecursivo.setValue(zValue);}

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizCompAlta() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "componente" , pComp );
//    AddItem( "destino" , pDestino );
    addItem( "recursivo" , pRecursivo );
//    AddItem( "tipo_copia" , pTipoCopia );
    addItem( "include_tables" , pIncludeTables );
    addItem( "include_source" , pIncludeSource );
    addItem( "data_level" , pDataLevel );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( FIELD  , "componente" , "Componente", true, true, 100 );
//    AddItemFijo( CAMPO  , "destino" , "Destino", true, true, 100 );
    addFixedItem( FIELD  , "recursivo" , "Recursivo", true, true, 100 );
//    AddItemFijo( CAMPO  , "tipo_copia" , "Tipo Copia", true, true, 100 );
    addFixedItem( FIELD  , "include_tables" , "Tablas", true, true, 100 );
    addFixedItem( FIELD  , "include_source" , "Source", true, true, 100 );
    addFixedItem( FIELD  , "data_level" , "Data Level", true, true, 100 );
  }


  @Override
	public String GetTable() {return "";}
  public boolean isRecursivo() throws Exception { return pRecursivo.getValue(); }

  @Override
	public void processInsert() throws Exception {
    if ( pIncludeTables.getValue() ) {
    	if (JAplicacion.GetApp().hasForzedDatabase()) {
    		processAll(JBDatos.GetBases().getCurrentDatabase());
    	} else {
    		processAll(null);
    	}
    }
  }
    
    
  private void processAll(String base) throws Exception {

  	String destino = JPath.PssPath()+"/pss/core/SetupBD/Tables";
  	if (base!=null) destino+="/"+base;
  	this.verifyDirectoryCreated(destino);
  	this.deleteDirectory(destino);
    
//    this.ProcesarComponente(this.pComp.GetValor(), false);

    if ( this.isRecursivo() )
      this.ProcesarSubModulos(this.pComp.getValue(), destino);
  }

  private void deleteDirectory(String destino) throws Exception {
  	String dir = JPath.PssPath()+"/pss/core/SetupBD/Tables"; // siempre verifico que el raiz este borrado
  	JTools.DeleteFiles(dir);

    JTools.DeleteFiles(destino);
	}
  
	private void verifyDirectoryCreated(String destino) throws Exception {
		JTools.MakeDirectory(JPath.PssPath());
		JTools.MakeDirectory(JPath.PssPath() + "/Pss");
		JTools.MakeDirectory(JPath.PssPath() + "/pss/core");
		JTools.MakeDirectory(JPath.PssPath() + "/pss/core/SetupBD");
		JTools.MakeDirectory(JPath.PssPath() + "/pss/core/SetupBD/Tables");
		JTools.MakeDirectory(destino);		
	}
	private void ProcesarSubModulos(String zSubComp, String zDestino) throws Exception {

    BizCompInstalados oComps = new BizCompInstalados();
    oComps.addFilter("comp_padre", zSubComp);
    oComps.addFilter("dynamic_module", "S");
    oComps.readAll();
    oComps.firstRecord();
    while ( oComps.nextRecord() ) {
      BizCompInstalado oComp = (BizCompInstalado) oComps.getRecord();
//      this.ProcesarComponente(oComp.pComponente.GetValor(), false);
      this.ProcesarSubModulos(oComp.pComponente.getValue(), zDestino);
      PssLogger.logInfo("Componente: " + oComp.pComponente.getValue()+ " ...OK");
    }

  }

/*  private void ProcesarTabla(String zTabla) throws Exception {
    BizTablaAlta oTablaAlta = new BizTablaAlta();
    oTablaAlta.pTabla.SetValor(zTabla);
    oTablaAlta.pTipoCopia.SetValor(this.pTipoCopia.GetValor());
    oTablaAlta.pDestino.SetValor(this.pDestino.GetValor());
    oTablaAlta.processInsert();
  }
*/
/*  private void ProcesarComponente(String zComp, boolean bCopyRecursive) throws Exception {
    if ( pTipoCopia.GetValor().equals("") ) {
      return;
    }
    if ( ! bCopyRecursive ) {
      BizDirectorios oDirectorios = new BizDirectorios();
      oDirectorios.SetFiltros("directorio", zComp);
      oDirectorios.ReadAll();
      oDirectorios.FirstRecord();
      while ( oDirectorios.NextRecord() ) {
        BizDirectorio oDirectorio = (BizDirectorio) oDirectorios.GetRecord();
        this.ProcesarComponente(oDirectorio.pDirectorio.GetValor(), true);
      }
    }
    if ( pTipoCopia.GetValor().equals("PC") ) {
      //      String sPss = JTools.PssPath();
      //      String sCom = sPss+"/"+zComp+" "+pDestino.GetValor()+"/"+zComp;
      //      sCom = "xcopy /I "+ (bCopyRecursive?"/S ":" ")+ JTools.ReplaceToken(sCom, '/', (char) 92);
      //      Runtime.getRuntime().exec(sCom);
      JStringTokenizer oToken = JCollectionFactory.createStringTokenizer(zComp, '/');
      String[] tokens = new String[oToken.countTokens()];
      String token = "";
      for(int i=0; oToken.hasMoreTokens(); i++) {
        tokens[i] = oToken.nextToken();
      }
      int k = bCopyRecursive ? 1 : 0;
      for(int i=0; i<tokens.length-k; i++) {
        token += "/" + tokens[i];
      }
      if ( pIncludeSource.GetValor() ) {
        String source  = JTools.PssPath()+"/../" + zComp;
        File oFile = new File(source);
        if ( oFile != null  && oFile.isDirectory() ) {
          String destiny = pDestino.GetValor() + token;
          xcopy(source, destiny, bCopyRecursive);
        }
      }
      if ( pIncludeClass.GetValor() ) {
        String source  = JTools.PssPath()   + "/" + zComp;
        String destiny = pDestino.GetValor() + (pIncludeSource.GetValor() ? "/Pss/":"") +token;
        xcopy(source, destiny, bCopyRecursive);
      }
    }
    BizClases oClases = new BizClases();
    oClases.SetFiltros("directorio", zComp);
    oClases.ReadAll();
    oClases.FirstRecord();
    while ( oClases.NextRecord() ) {
      BizClase oClase = (BizClase) oClases.GetRecord();
      BizClaseAlta oClaseAlta = new BizClaseAlta();
      oClaseAlta.pComponente.SetValor(oClase.pComponente.GetValor());
      oClaseAlta.pArchivo.SetValor(oClase.pArchivo.GetValor());
      oClaseAlta.pTipoCopia.SetValor(this.pTipoCopia.GetValor());
      oClaseAlta.pDestino.SetValor(this.pDestino.GetValor());
      oClaseAlta.processInsert();
    }
  }
*/

}
