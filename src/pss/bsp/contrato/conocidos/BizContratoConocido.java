package pss.bsp.contrato.conocidos;

import java.util.Date;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.JTipoPremioAmericanAirlines;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajeDelTotal;
import pss.bsp.contrato.detalle.nivel.JTipoPremioPorcentajePorNivel;
import pss.core.services.records.JRecord;
import pss.core.services.fields.*;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizContratoConocido extends JRecord {

  private JString pCompany = new JString();
  private JString pPais = new JString();
  private JString pAerolineas = new JString();
  private JLong pIdvariableganancia = new JLong();
  private JLong pIdvariable = new JLong();
  private JLong pIdvariableAux = new JLong();
  private JString pTipoContrato = new JString();
  private JString pTipoContratoDescr = new JString();
  private JString pPeriodo = new JString();
  private JString pTipoPremio = new JString();
  private JString pTipoObjetivo = new JString();
  private JBoolean pObjetivoExtra = new JBoolean();
  private JLong pId = new JLong();
  private JString pDescripcion = new JString();
  private JString pDetalle = new JString();
  private JLong pIconoAerolinea = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompania(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompania() throws Exception {     return pCompany.getValue();  }
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setAerolineas(String zValue) throws Exception {    pAerolineas.setValue(zValue);  }
  public String getAerolineas() throws Exception {     return pAerolineas.getValue();  }
  public boolean isNullAerolineas() throws Exception { return  pAerolineas.isNull(); } 
  public void setNullToAerolineas() throws Exception {  pAerolineas.setNull(); } 
  public void setIdvariableganancia(long zValue) throws Exception {    pIdvariableganancia.setValue(zValue);  }
  public long getIdvariableganancia() throws Exception {     return pIdvariableganancia.getValue();  }
  public boolean isNullIdvariableganancia() throws Exception { return  pIdvariableganancia.isNull(); } 
  public void setNullToIdvariableganancia() throws Exception {  pIdvariableganancia.setNull(); } 
  public void setIdvariable(long zValue) throws Exception {    pIdvariable.setValue(zValue);  }
  public long getIdvariable() throws Exception {     return pIdvariable.getValue();  }
  public boolean isNullIdvariable() throws Exception { return  pIdvariable.isNull(); } 
  public void setNullToIdvariable() throws Exception {  pIdvariable.setNull(); } 
  public void setIdvariableAux(long zValue) throws Exception {    pIdvariableAux.setValue(zValue);  }
  public long getIdvariableAux() throws Exception {     return pIdvariableAux.getValue();  }
  public boolean isNullIdvariableAux() throws Exception { return  pIdvariableAux.isNull(); } 
  public void setNullToIdvariableAux() throws Exception {  pIdvariableAux.setNull(); } 
  public void setTipoPremio(String zValue) throws Exception {    pTipoPremio.setValue(zValue);  }
  public String getTipoPremio() throws Exception {     return pTipoPremio.getValue();  }
  public boolean isNullTipoPremio() throws Exception { return  pTipoPremio.isNull(); } 
  public void setNullToTipoPremio() throws Exception {  pTipoPremio.setNull(); } 
  public void setTipoObjetivo(String zValue) throws Exception {    pTipoObjetivo.setValue(zValue);  }
  public String getTipoObjetivo() throws Exception {     return pTipoObjetivo.getValue();  }
  public boolean isNullTipoObjetivo() throws Exception { return  pTipoObjetivo.isNull(); } 
  public void setNullToTipoObjetivo() throws Exception {  pTipoObjetivo.setNull(); } 
  public void setObjetivoExtra(boolean zValue) throws Exception {    pObjetivoExtra.setValue(zValue);  }
  public boolean getObjetivoExtra() throws Exception {     return pObjetivoExtra.getValue();  }
  public boolean isNullObjetivoExtra() throws Exception { return  pObjetivoExtra.isNull(); } 
  public void setNullToObjetivoExtra() throws Exception {  pObjetivoExtra.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setDetalle(String zValue) throws Exception {    pDetalle.setValue(zValue);  }
  public String getDetalle() throws Exception {     return pDetalle.getValue();  }
  public boolean isNullDetalle() throws Exception { return  pDetalle.isNull(); } 
  public void setNullToDetalle() throws Exception {  pDetalle.setNull(); } 
  public void setPeriodo(String zValue) throws Exception {    pPeriodo.setValue(zValue);  }
  public String getPeriodo() throws Exception {     return pPeriodo.getValue();  }
  public boolean isNullPeriodo() throws Exception { return  pPeriodo.isNull(); } 
  public void setNullToPeriodo() throws Exception {  pPeriodo.setNull(); } 
  public void setTipoContrato(String zValue) throws Exception {    pTipoContrato.setValue(zValue);  }
  public void setTipoContratoDescr(String zValue) throws Exception {    pTipoContratoDescr.setValue(zValue);  }
  public String getTipoContrato() throws Exception {     return pTipoContrato.getValue();  }
  public boolean isNullTipoContrato() throws Exception { return  pTipoContrato.isNull(); } 
  public void setNullToTipoContrato() throws Exception {  pTipoContrato.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizContratoConocido() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "pais", pPais );
    this.addItem( "aerolineas", pAerolineas );
    this.addItem( "id_variableganancia", pIdvariableganancia );
    this.addItem( "id_variable", pIdvariable );
    this.addItem( "id_variableaux", pIdvariableAux );
    this.addItem( "tipo_premio", pTipoPremio );
    this.addItem( "tipo_objetivo", pTipoObjetivo );
    this.addItem( "objetivo_extra", pObjetivoExtra );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "detalle", pDetalle );
    this.addItem( "periodo", pPeriodo );
    this.addItem( "tipo_contrato", pTipoContrato );
    this.addItem( "tipo_contrato_descr", pTipoContratoDescr );
    this.addItem( "icono", pIconoAerolinea );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "company", "Compania", true, true, 15 );
      this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "aerolineas", "Aerolineas", true, true, 15 );
    this.addFixedItem( FIELD, "id_variableganancia", "Id variableganancia", true, false, 18 );
    this.addFixedItem( FIELD, "id_variable", "Id variable", true, false, 18 );
    this.addFixedItem( FIELD, "id_variableaux", "Id variable auxiliar", true, false, 18 );
    this.addFixedItem( FIELD, "tipo_premio", "Tipo premio", true, true, 300 );
    this.addFixedItem( FIELD, "tipo_objetivo", "Tipo objetivo", true, true, 300 );
    this.addFixedItem( FIELD, "objetivo_extra", "Objetivo extra", true, true, 1 );
    this.addFixedItem( KEY, "id", "Id", false, false, 32 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 500 );
    this.addFixedItem( FIELD, "detalle", "Detalle", true, false, 4000 );
    this.addFixedItem( FIELD, "periodo", "Periodo", true, true, 18 );
    this.addFixedItem( FIELD, "tipo_contrato", "Tipo contrato", true, true, 500 );
    this.addFixedItem( FIELD, "tipo_contrato_descr", "Tipo contrato descr", true, true, 500 );
    this.addFixedItem( FIELD, "icono", "Icono aerolinea", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_contratosconocidos"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( String pais,String aerolinea,String tipo,long var, long vargan) throws Exception { 
    addFilter( "pais",  pais ); 
    addFilter( "aerolineas",  aerolinea ); 
    addFilter( "tipo_contrato",  tipo ); 
    if (var!=0) addFilter( "id_variable",  var ); 
    if (vargan!=0) addFilter( "id_variableganancia",  vargan ); 
    return read(); 
  } 
  
  public static final String MENSUAL = "MENSUAL";
  public static final String BIMESTRAL = "BIMESTRAL";
  public static final String TRIMESTRAL = "TRIMESTRAL";
  public static final String CUATRIMESTRAL = "CUATRIMESTRAL";
  public static final String SEMESTRAL = "SEMESTRAL";
  public static final String ANUAL = "ANUAL";
  public static final String MARZOAMARZO = "MARZOAMARZO";
  
	static JMap<String,String> gMapPeriodos;
  public static JMap<String,String> getTipoPeriodos() throws Exception {
  	if (gMapPeriodos!=null) return gMapPeriodos;
  	gMapPeriodos = JCollectionFactory.createOrderedMap();
  	gMapPeriodos.addElement(MENSUAL, "Mensual");
  	gMapPeriodos.addElement(BIMESTRAL, "Bimestral");
  	gMapPeriodos.addElement(TRIMESTRAL, "Trimestral");
  	gMapPeriodos.addElement(CUATRIMESTRAL, "Cuatrimestral");
  	gMapPeriodos.addElement(SEMESTRAL, "Semestral");
  	gMapPeriodos.addElement(ANUAL, "Anual");
  	gMapPeriodos.addElement(MARZOAMARZO, "Marzo a Marzo");
  	return gMapPeriodos;
  }
  
  @Override
  public void processUpdate() throws Exception {
  	pTipoContratoDescr.setValue(BizDetalle.getLogicasContrato().getElement(pTipoContrato.getValue()));
  	super.processUpdate();
  }
  
  @Override
  public void processInsert() throws Exception {
  	pTipoContratoDescr.setValue(BizDetalle.getLogicasContrato().getElement(pTipoContrato.getValue()));
  	super.processInsert();
  }
}
