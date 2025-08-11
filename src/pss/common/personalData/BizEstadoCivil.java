package  pss.common.personalData;

import pss.common.regions.nodes.BizNodo;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class BizEstadoCivil extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  private JString pIdEstadocivil = new JString();
  private JString pIdPais = new JString();
  private JString pDescripcion = new JString();
  private JString pRequiereconyuge = new JString();
  private JString pDescripMasc = new JString();
  private JString pDescripFem = new JString();

  public void   setIdPais(String zVal ) throws Exception { pIdPais.setValue( zVal ); }
  public String getIdPais() throws Exception { return pIdPais.getValue(); }
  public void   setIdEstadocivil(String zVal ) throws Exception { pIdEstadocivil.setValue( zVal ); }
  public String getIdEstadocivil() throws Exception { return pIdEstadocivil.getValue(); }
  public void   setDescripcion(String zVal ) throws Exception { pDescripcion.setValue( zVal ); }
  public String getDescripcion() throws Exception { return pDescripcion.getValue(); }
  public String getDescripFem() throws Exception { return pDescripFem.isRawNull()?getDescripcion():pDescripFem.getValue(); }
  public String getDescripMasc() throws Exception { return pDescripMasc.isRawNull()?getDescripcion():pDescripMasc.getValue(); }
  public void   setRequiereconyuge(String zVal ) throws Exception { pRequiereconyuge.setValue( zVal ); }
  public String getRequiereconyuge() throws Exception { return pRequiereconyuge.getValue(); }




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizEstadoCivil() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "id_pais", pIdPais );
    addItem( "id_estadoCivil", pIdEstadocivil );
    addItem( "descripcion", pDescripcion );
    addItem( "descrip_masc", pDescripMasc );
    addItem( "descrip_fem", pDescripFem );
    addItem( "requiereConyuge", pRequiereconyuge );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "id_pais", "Id pais", true, true, 15 );
    addFixedItem( KEY, "id_estadoCivil", "Código", true, true, 15 );
    addFixedItem( FIELD, "descripcion", "Descripción", true, true, 50 );
    addFixedItem( FIELD, "descrip_masc", "Descripción Masc", true, false, 50 );
    addFixedItem( FIELD, "descrip_fem", "Descripción Fem", true, false, 50 );
    addFixedItem( FIELD, "requiereConyuge", "Requiere Conyuge", true, false, 1 );
  }

  @Override
	public String GetTable() { return "PER_ESTADOCIVIL"; }
  public boolean Read( String zPais,String zIdestadocivil ) throws Exception {
    addFilter( "id_estadoCivil",  zIdestadocivil );
    addFilter( "id_pais",  zPais );
    return read();
  }
  public boolean Read( String zIdestadocivil ) throws Exception {
    addFilter( "id_estadoCivil",  zIdestadocivil );
    BizNodo oNodo = BizUsuario.getUsr().getObjNodo();
    addFilter( "id_pais",  oNodo.GetPais() );
    return read();
  }
  @Override
	public void processDelete() throws Exception {
    if (JRecords.existsComplete(BizPersona.class,"estado_civil",pIdEstadocivil.getValue()))
      JExcepcion.SendError("No se puede eliminar un estado civil que está en uso");
    super.processDelete();
  }
  
  @Override
  public void processInsert() throws Exception {
  	if (pIdEstadocivil.isNull()) {
  		pIdEstadocivil.setValue(JTools.subStr(pDescripcion.getValue(),0,15));
  	}
  	super.processInsert();
  }
}


