package  pss.common.personalData;

import pss.common.components.JSetupParameters;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizTipoDoc extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pPais = new JString();
  JString pTipo_doc = new JString();
  JString pDescripcion = new JString();

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public String getTipoDoc() throws Exception { return pTipo_doc.getValue();}
  public String getDescripcion() throws Exception { return pDescripcion.getValue();}
  public BizTipoDoc() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "pais", pPais);
    addItem( "tipo_doc", pTipo_doc);
    addItem( "descripcion", pDescripcion);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "pais", "País", true, true, 15 );
    addFixedItem( KEY, "tipo_doc", "Tipo de Documento", true, true, 15 );
    addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 20 );
  }

  public String getPais() throws Exception { return pPais.getValue(); }
  @Override
	public String GetTable() { return "PER_TIPO_DOC";}


  @Override
	public void setupConfig(JSetupParameters zParams ) throws Exception {
    zParams.setExportData(zParams.isLevelCountry());
    zParams.setTruncateData(zParams.isLevelCountry());
  }

  public boolean Read( String zCountry, String zTipoDoc ) throws Exception {
  	addFilter("pais", zCountry);
    addFilter("tipo_doc", zTipoDoc);
    return read();
  }

  @Override
	public void processDelete() throws Exception {
    if ( JRecords.existsComplete(BizPersona.class, "nacionalidad", pPais.getValue(),"tipo_doc",pTipo_doc.getValue()) )
       JExcepcion.SendError("Este Tipo de Documento fue utilizado, no se podrá eliminar.");
    super.processDelete();
  }

  public static boolean doesExist(boolean zWantException, String zCountry, String zTipoDoc) throws Exception {
    BizTipoDoc oTDoc = new BizTipoDoc();
    oTDoc.dontThrowException(true);
    boolean bEncontro = oTDoc.Read(zCountry, zTipoDoc);
    if (!bEncontro && zWantException ) JExcepcion.SendError("Tipo de documento Inexistente");
    return bEncontro;
  }

	static JMap<String, BizTipoDoc> hCacheLocal = null; 
  public static JMap<String, BizTipoDoc> getTipos() throws Exception {
  	if (hCacheLocal==null) {
  		JRecords<BizTipoDoc> recs = new JRecords<BizTipoDoc>(BizTipoDoc.class);
  		recs.addFilter("pais", BizUsuario.getUsr().getCountry()); // hardcoded, ojo
  		recs.readAll();
  		hCacheLocal=recs.convertToHash("tipo_doc");
  	}
  	return hCacheLocal;
  }
  public static JMap<String, String> getTiposMap() throws Exception {
  	JMap<String, String> map = JCollectionFactory.createMap();
  	JIterator<BizTipoDoc> iter = BizTipoDoc.getTipos().getValueIterator();
  	while (iter.hasMoreElements()) {
  		BizTipoDoc t = iter.nextElement();
  		map.addElement(t.getTipoDoc(), t.getDescripcion());
  	}
  	return map;
  }  
  public static String getDescrTipo(String tipo) throws Exception {
  BizTipoDoc t = BizTipoDoc.getTipos().getElement(tipo);
  if (t==null) return "";
  	return t.getDescripcion();
  }

  public static BizTipoDoc getTipo(String tipo) throws Exception {
  	return BizTipoDoc.getTipos().getElement(tipo);
  }

}
