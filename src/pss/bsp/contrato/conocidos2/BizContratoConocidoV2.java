package pss.bsp.contrato.conocidos2;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.biblioteca.BizOldBiblioteca;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizContratoConocidoV2 extends JRecord {

  private JString pPais = new JString();
  private JString pAerolineas = new JString();
  private JString pTipoContrato = new JString();
  private JString pTipoContratoDescr = new JString();
  private JString pPeriodo = new JString();
  private JString pTipoPremio = new JString();
  private JString pTipoObjetivo = new JString();
  private JBoolean pObjetivoExtra = new JBoolean();
  private JBoolean pDolares = new JBoolean();
  private JLong pId = new JLong();
  private JLong pIdGenerador = new JLong();
  private JString pDescripcion = new JString();
  private JString pDetalle = new JString();
  private JLong pIconoAerolinea = new JLong();
  private JLong pVariable = new JLong();
  private JLong pVariableGanancia = new JLong();
  private JLong pVariableAuxiliar = new JLong();

  private JImage pImagenMenu = new JImage();
  private JImage pImagen = new JImage();
  private JLong pImagenMenuId = new JLong();
  private JLong pImagenId = new JLong();
  private JString pSerializeDetalle = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setAerolineas(String zValue) throws Exception {    pAerolineas.setValue(zValue);  }
  public String getAerolineas() throws Exception {     return pAerolineas.getValue();  }
  public boolean isNullAerolineas() throws Exception { return  pAerolineas.isNull(); } 
  public void setNullToAerolineas() throws Exception {  pAerolineas.setNull(); } 

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
  public void setDolares(boolean zValue) throws Exception {    pDolares.setValue(zValue);  }
  public boolean getDolares() throws Exception {     return pDolares.getValue();  }
  public boolean isNullDolares() throws Exception { return  pDolares.isNull(); } 
  public void setNullToDolares() throws Exception {  pDolares.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdGenerador(long zValue) throws Exception {    pIdGenerador.setValue(zValue);  }
  public long getIdGenerador() throws Exception {     return pIdGenerador.getValue();  }
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
  public void setVariable(long zValue) throws Exception {    pVariable.setValue(zValue);  }
  public long getVariable() throws Exception {     return pVariable.getValue();  }
  public boolean isNullVariable() throws Exception { return  pVariable.isNull(); } 
  public void setNullToVariable() throws Exception {  pVariable.setNull(); } 
  public void setVariableAuxiliar(long zValue) throws Exception {    pVariableAuxiliar.setValue(zValue);  }
  public long getVariableAuxiliar() throws Exception {     return pVariableAuxiliar.getValue();  }
  public boolean isNullVariableAuxiliar() throws Exception { return  pVariableAuxiliar.isNull(); } 
  public void setNullToVariableAuxiliar() throws Exception {  pVariableAuxiliar.setNull(); } 
  public void setVariableGanancia(long zValue) throws Exception {    pVariableGanancia.setValue(zValue);  }
  public long getVariableGanancia() throws Exception {   	return pVariableGanancia.getValue();   }

  
  BizDetalle objDetalle;
  public void cleanObjDetalle() {
  	objDetalle=null;
  }
  public void setObjDetalle(BizDetalle detalle) throws Exception {
  	objDetalle=detalle;
  }
  public BizDetalle getObjDetalle() throws Exception {
  	if (objDetalle==null) {
  		BizDetalle det =new  BizDetalle();
  		BizContrato contrato = new BizContrato();
  		det.setLogicaContrato(getTipoContrato());
  		BizDetalle detFinal =det.getObjLogicaInstance().getBiz();
  		detFinal.setObjContrato(contrato);
   	  detFinal.setLogicaContrato(getTipoContrato());
  		objDetalle=detFinal;
    	if (pSerializeDetalle.isNotNull()) {
    		objDetalle=getSerializeDetalle(objDetalle);
    	} 
  	}
  	
		return objDetalle;
	}
	
	public BizDetalle getSerializeDetalle(BizDetalle det) throws Exception {  
  	BizDetalle detalle = det;
  	detalle.unSerialize(pSerializeDetalle.getValue());
  	return detalle;
  } 

  public void fillSerializeDetalle(BizDetalle detalle) throws Exception {  
  	pSerializeDetalle.setValue(detalle.serialize());
  } 

  /**
   * Constructor de la Clase
   */
  public BizContratoConocidoV2() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "pais", pPais );
    this.addItem( "aerolineas", pAerolineas );
    this.addItem( "variable", pVariable );
    this.addItem( "variable_aux", pVariableAuxiliar );
    this.addItem( "variable_ganancia", pVariableGanancia );
    this.addItem( "tipo_premio", pTipoPremio );
    this.addItem( "tipo_objetivo", pTipoObjetivo );
    this.addItem( "objetivo_extra", pObjetivoExtra );
    this.addItem( "id", pId );
    this.addItem( "id_generador", pIdGenerador );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "detalle", pDetalle );
    this.addItem( "dolares", pDolares );
    this.addItem( "periodo", pPeriodo );
    this.addItem( "tipo_contrato", pTipoContrato );
    this.addItem( "tipo_contrato_descr", pTipoContratoDescr );
    this.addItem( "icono", pIconoAerolinea );
    this.addItem( "image_menu_filename", pImagenMenu );
    this.addItem( "image_filename", pImagen );
    this.addItem( "image_menu", pImagenMenuId );
    this.addItem( "image", pImagenId );
    this.addItem( "serialize_detalle", pSerializeDetalle );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 32 );
    this.addFixedItem( FIELD, "id_generador", "id_generador", true, false, 32 );
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "aerolineas", "Aerolineas", true, false, 2000 );
    this.addFixedItem( FIELD, "tipo_premio", "Tipo premio", true, false, 300 );
    this.addFixedItem( FIELD, "tipo_objetivo", "Tipo objetivo", true, false, 300 );
    this.addFixedItem( FIELD, "objetivo_extra", "Objetivo extra", true, false, 1 );
    this.addFixedItem( FIELD, "dolares", "Dolares", true, false, 1 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 500 );
    this.addFixedItem( FIELD, "detalle", "Detalle", true, false, 4000 );
    this.addFixedItem( FIELD, "periodo", "Periodo", true, false, 18 );
    this.addFixedItem( FIELD, "tipo_contrato", "Tipo contrato", true, false, 500 );
    this.addFixedItem( FIELD, "tipo_contrato_descr", "Tipo contrato descr", true, false, 500 );
    this.addFixedItem( FIELD, "variable", "Variable", true, false, 18 );
    this.addFixedItem( FIELD, "variable_aux", "Variable auxiliar", true, false, 18 );
    this.addFixedItem( FIELD, "variable_ganancia", "variable_ganancia", true, false, 18 );
    this.addFixedItem( FIELD, "icono", "Icono aerolinea", true, false, 18 );
    this.addFixedItem( VIRTUAL, "image_menu_filename", "Imagen menu", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "image_filename", "Imagen", true, false, 4000 );
    this.addFixedItem( FIELD, "image_menu", "Imagen menu", true, false, 18 );
    this.addFixedItem( FIELD, "image", "Imagen", true, false, 18 );
    this.addFixedItem( FIELD, "serialize_detalle", "Serialize detalle", true, false, 4000 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_contratosconocidosv2"; }


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
  public boolean readByGenerador( long zId ) throws Exception { 
    addFilter( "id_generador",  zId ); 
    return read(); 
  } 
  public boolean readRepetido( String pais,String periodo,String tipo,String aerolinea,String tipo_prem, String tipo_obj,long var, long vargan, long varaux) throws Exception { 
    addFilter( "pais",  pais ); 
    addFilter( "periodo",  periodo ); 
    addFilter( "aerolineas",  aerolinea ); 
    addFilter( "tipo_contrato",  tipo ); 
    addFilter( "tipo_premio",  tipo_prem ); 
    addFilter( "tipo_objetivo",  tipo_obj ); 
    if (var!=0) addFilter( "variable",  var ); 
    if (vargan!=0) addFilter( "variable_ganancia",  vargan ); 
    if (varaux!=0) addFilter( "variable_aux",  varaux ); 

    return read(); 
  } 
  
  public boolean read( String pais,String aerolinea,String tipo,long var, long vargan, long varaux) throws Exception { 
    addFilter( "pais",  pais ); 
    addFilter( "aerolineas",  aerolinea ); 
    addFilter( "tipo_contrato",  tipo ); 
    if (var!=0) addFilter( "variable",  var ); 
    if (vargan!=0) addFilter( "variable_ganancia",  vargan ); 
    if (varaux!=0) addFilter( "variable_aux",  varaux ); 
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
  	if (pImagenId.isNotNull()) {
   		BizOldBiblioteca biblo = new BizOldBiblioteca();
   		biblo.dontThrowException(true);
   		if (biblo.read(pImagenId.getValue())) {
   			biblo.processDelete();
   		}
  	}
  	if (pImagenMenuId.isNotNull()) {
   		BizOldBiblioteca biblo = new BizOldBiblioteca();
   		biblo.dontThrowException(true);
   		if (biblo.read(pImagenMenuId.getValue())) {
   			biblo.processDelete();
   		}
  	}
  	if (pImagen.isNotNull()) {
  		String filename = pImagen.getValue();
  		String source=filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename("DEFAULT",source, type);
	  	biblo.processInsert();
	  	this.pImagenId.setValue(biblo.getId());
 	  } 	
  	if (pImagenMenu.isNotNull()) {
  		String filename = pImagenMenu.getValue();
  		String source=filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename("DEFAULT",source, type);
	  	biblo.processInsert();
	  	this.pImagenMenuId.setValue(biblo.getId());
 	  } 	

  	pTipoContratoDescr.setValue(BizDetalle.getLogicasContrato().getElement(pTipoContrato.getValue()));
  	fillSerializeDetalle(getObjDetalle());
  	super.processUpdate();
  }
  
  @Override
  public void processInsert() throws Exception {
  	if (pImagen.isNotNull()) {
  		String filename = pImagen.getValue();
  		String source=filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename("DEFAULT",source, type);
	  	biblo.processInsert();
	  	this.pImagenId.setValue(biblo.getId());
 	  }
  	if (pImagenMenu.isNotNull()) {
  		String filename = pImagenMenu.getValue();
  		String source=filename;
  		int lastPunto = source.lastIndexOf('.')+1;
  		String type = source.substring(lastPunto);				
  		BizOldBiblioteca biblo = new BizOldBiblioteca();
	  	biblo.setContentByFilename("DEFAULT",source, type);
	  	biblo.processInsert();
	  	this.pImagenMenuId.setValue(biblo.getId());
 	  } 
  	pTipoContratoDescr.setValue(BizDetalle.getLogicasContrato().getElement(pTipoContrato.getValue()));
  	fillSerializeDetalle(getObjDetalle());
  	super.processInsert();
  }
  
  public void execFillContratosConocidos() throws Exception {
		JExec oExec = new JExec(this, "fillContratosConocidos") {

			@Override
			public void Do() throws Exception {
				fillContratosConocidos();
			}
		};
		oExec.execute();
	}
  public void fillContratosConocidos() throws Exception {
	  JRecords<BizContrato> contratos = new JRecords<BizContrato>(BizContrato.class);
	  JIterator<BizContrato> it = contratos.getStaticIterator();
	  while (it.hasMoreElements()) {
	  	BizContrato con = it.nextElement();
		  JRecords<BizDetalle> detalles = new JRecords<BizDetalle>(BizDetalle.class);
		  JIterator<BizDetalle> detit = detalles.getStaticIterator();
		  while (detit.hasMoreElements()) {
		  	BizDetalle detalle = detit.nextElement();
		  	String aerol = detalle.getRawAerolineas().equals("")?detalle.getIdAerolinea():detalle.getRawAerolineas();
		  	String pais = BizBSPCompany.getObjBSPCompany(detalle.getCompany()).getPais();
		  	pais = pais.equals("1")?"AR":pais;
		  	String tipoObjetivo = detalle.getFirstNivel()!=null?detalle.getFirstNivel().getTipoObjetivo():null;
		  	String tipoPremio= detalle.getFirstNivel()!=null?detalle.getFirstNivel().getTipoPremio():null;
		  	String periodo = con.getPeriodo();
		  	long var = detalle.getVariable();
		  	long vargan = detalle.getVariableGanancia();
		  	long varaux = detalle.getVariableAuxiliar();
		  	boolean dolares = detalle.isDolares();
		  	BizContratoConocidoV2 conocido = new BizContratoConocidoV2();
		  	conocido.dontThrowException(true);
		  	if (!conocido.readByGenerador(detalle.getId())) {
		  		conocido.readRepetido(pais,con.getPeriodo(),detalle.getLogicaContrato(),aerol,tipoObjetivo,tipoPremio,var,vargan,varaux);
		  	};
		  	conocido.setPais(pais);
		  	conocido.setPeriodo(periodo);
		  	conocido.setTipoContrato(detalle.getLogicaContrato());
		  	conocido.setAerolineas(aerol);
		  	conocido.setIdGenerador(detalle.getId());
		  	conocido.setDescripcion(aerol+" - "+periodo+" - "+detalle.getTipoContrato());
		  	
		  	conocido.setVariable(var);
		  	conocido.setVariableGanancia(vargan);
		  	conocido.setVariableAuxiliar(varaux);
		  	
		  	conocido.setDolares(dolares);
		  	conocido.setTipoObjetivo(tipoObjetivo);
			 	conocido.setTipoPremio(tipoPremio);
				fillSerializeDetalle(detalle);
		  	conocido.processUpdateOrInsertWithCheck();
		  }	  	
	  }
  }
  

  BizOldBiblioteca biblio = null;
  BizOldBiblioteca biblioMenu = null;
  public String getImagen() throws Exception {
  	if (this.pImagenId.isNull()) return null;
  	return "tempfiles/"+this.getObjBiblioteca().getContentAsFilename("DEFAULT");

  }
  public BizOldBiblioteca getObjBiblioteca() throws Exception {
  	if (this.pImagenId.isNull()) return null;
  	if (this.biblio!=null) return this.biblio;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pImagenId.getValue());
  	return (this.biblio=b);
  }
  public String getImagenMenu() throws Exception {
  	if (this.pImagenMenuId.isNull()) return null;
  	return "tempfiles/"+this.getObjBibliotecaMenu().getContentAsFilename("DEFAULT");

  }
  public BizOldBiblioteca getObjBibliotecaMenu() throws Exception {
  	if (this.pImagenMenuId.isNull()) return null;
  	if (this.biblioMenu!=null) return this.biblioMenu;
  	BizOldBiblioteca b = new BizOldBiblioteca();
  	b.read(this.pImagenMenuId.getValue());
  	return (this.biblioMenu=b);
  }
}
