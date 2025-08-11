package pss.bsp.contrato.detalle.nivel;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.contrato.logica.JContratoAvianca;
import pss.bsp.contrato.logica.JContratoBackendDobleRuta;
import pss.bsp.contrato.logica.JContratoLatamFamilia;
import pss.bsp.contrato.logica.JContratoTriple;
import pss.bsp.contrato.logica.JContratoUpfront;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JColour;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizNivel extends JRecord {
	
	public static final String NIVEL_ONLYMETA = "OM";

  private JLong pId = new JLong();
  private JLong pLinea = new JLong();
  private JLong pIdNivel = new JLong();
  private JFloat pValor = new JFloat();
  private JFloat pParam1 = new JFloat();
  private JFloat pParam2 = new JFloat();
  private JFloat pParam3 = new JFloat();
  private JFloat pParam4 = new JFloat();
  private JFloat pParam5 = new JFloat();
  private JFloat pParam6 = new JFloat();
  private JString pTipoObjetivo = new JString();
  private JString pTipoPremio = new JString();
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescripcion());
  	};
  };
  private JFloat pPremio = new JFloat();
  private JString pCompany = new JString();
  private JString pTipoPremioDescr = new JString() {
  	public void preset() throws Exception {
  		pTipoPremioDescr.setValue(getTiposPremios().getElement(getTipoPremio()));
  	};
  };
  private JString pTipoObjetivoDescr = new JString() {
  	public void preset() throws Exception {
  		pTipoObjetivoDescr.setValue(getTiposObjetivos().getElement(getTipoObjetivo()));
  	};
  };
  private JBoolean pElegido = new JBoolean() {
  	public void preset() throws Exception {
  		pElegido.setValue(getElegido());
  	};
  };
  private JFloat pEstimado = new JFloat() {
  	public void preset() throws Exception {
  		pEstimado.setValue(getEstimado());
  	};
  };
  private JColour pColor = new JColour();
  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setValor(double zValue) throws Exception {    pValor.setValue(zValue);  }
  public boolean isNullValor() throws Exception { return  pValor.isNull(); } 
  public void setNullToValor() throws Exception {  pValor.setNull(); } 
  public void setPremio(double zValue) throws Exception {    pPremio.setValue(zValue);  }
  public double getPremio() throws Exception {     return pPremio.getValue();  }
  public boolean isNullPremio() throws Exception { return  pPremio.isNull(); } 
  public void setNullToPremio() throws Exception {  pPremio.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setIdNivel(long zValue) throws Exception {    pIdNivel.setValue(zValue);  }
  public long getIdNivel() throws Exception {     return pIdNivel.getValue();  }
  public boolean isNullIdNivel() throws Exception { return  pIdNivel.isNull(); } 
  public void setNullToIdNivel() throws Exception {  pIdNivel.setNull(); } 
  public void setTipoPremio(String zValue) throws Exception {    pTipoPremio.setValue(zValue);  }
  public String getTipoPremio() throws Exception {     return pTipoPremio.getValue();  }
  public boolean isNullTipoPremio() throws Exception { return  pTipoPremio.isNull(); } 
  public void setNullToTipoPremio() throws Exception {  pTipoPremio.setNull(); } 
  public void setTipoObjetivo(String zValue) throws Exception {    pTipoObjetivo.setValue(zValue);  }
  public String getTipoObjetivo() throws Exception {     return pTipoObjetivo.getValue();  }
  public boolean isNullTipoObjetivo() throws Exception { return  pTipoObjetivo.isNull(); } 
  public void setNullToTipoObjetivo() throws Exception {  pTipoObjetivo.setNull(); } 
  public void setParam1(double zValue) throws Exception {    pParam1.setValue(zValue);  }
  public double getParam1() throws Exception {     return pParam1.getValue();  }
  public boolean isNullParam1() throws Exception { return  pParam1.isNull(); } 
  public void setNullToParam1() throws Exception {  pParam1.setNull(); } 
  public void setParam2(double zValue) throws Exception {    pParam2.setValue(zValue);  }
  public double getParam2() throws Exception {     return pParam2.getValue();  }
  public boolean isNullParam2() throws Exception { return  pParam2.isNull(); } 
  public void setNullToParam2() throws Exception {  pParam2.setNull(); } 
  public void setParam3(double zValue) throws Exception {    pParam3.setValue(zValue);  }
  public double getParam3() throws Exception {     return pParam3.getValue();  }
  public boolean isNullParam3() throws Exception { return  pParam3.isNull(); } 
  public void setNullToParam3() throws Exception {  pParam3.setNull(); } 
  public void setParam4(double zValue) throws Exception {    pParam4.setValue(zValue);  }
  public double getParam4() throws Exception {     return pParam4.getValue();  }
  public boolean isNullParam4() throws Exception { return  pParam4.isNull(); } 
  public void setNullToParam4() throws Exception {  pParam4.setNull(); } 
  public void setParam5(double zValue) throws Exception {    pParam5.setValue(zValue);  }
  public double getParam5() throws Exception {     return pParam5.getValue();  }
  public boolean isNullParam5() throws Exception { return  pParam5.isNull(); } 
  public void setNullToParam5() throws Exception {  pParam5.setNull(); } 
  public void setParam6(double zValue) throws Exception {    pParam6.setValue(zValue);  }
  public double getParam6() throws Exception {     return pParam6.getValue();  }
  public boolean isNullParam6() throws Exception { return  pParam6.isNull(); } 
  public void setNullToParam6() throws Exception {  pParam6.setNull(); } 

  public void setColor(String zValue) throws Exception {    pColor.setValue(zValue);  }
  public String getColor() throws Exception {     return pColor.getValue();  }
  public String getDescripcion() throws Exception {     
  	return getTipoPremioInstance().display(this);  
  }


  /**
   * Constructor de la Clase
   */
  public BizNivel() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "id_nivel", pIdNivel );
    this.addItem( "valor", pValor );
    this.addItem( "premio", pPremio );
    this.addItem( "tipo_premio", pTipoPremio );
    this.addItem( "tipo_nivel", pTipoObjetivo );
    this.addItem( "param1", pParam1 );
    this.addItem( "param2", pParam2 );
    this.addItem( "param3", pParam3 );
    this.addItem( "param4", pParam4 );
    this.addItem( "param5", pParam5 );
    this.addItem( "param6", pParam6 );
    this.addItem( "color", pColor );
    this.addItem( "tipo_premio_descr", pTipoPremioDescr );
    this.addItem( "tipo_nivel_descr", pTipoObjetivoDescr );
    this.addItem( "elegido", pElegido );
    this.addItem( "estimado", pEstimado );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, true, 64 );
    this.addFixedItem( KEY, "linea", "Id linea", true, true, 64 );
    this.addFixedItem( KEY, "id_nivel", "Id nivel", false, false, 64 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "valor", "Valor objetivo", true, false, 18,2 );
    this.addFixedItem( FIELD, "premio", "Premio", true, false, 18,4 );
    this.addFixedItem( FIELD, "tipo_premio", "Id Tipo Premio", true, false, 400 );
    this.addFixedItem( FIELD, "tipo_nivel", "Id Tipo Objetivo", true, false, 400 );
    this.addFixedItem( FIELD, "color", "Color", true, false, 16 );
    this.addFixedItem( FIELD, "param1", "Param.Nivel 1", true, false, 18,2 );
    this.addFixedItem( FIELD, "param2", "Param.Nivel 2", true, false, 18,2 );
    this.addFixedItem( FIELD, "param3", "Param.Nivel 3", true, false, 18,2 );
    this.addFixedItem( FIELD, "param4", "Param.Premio 1", true, false, 18,2 );
    this.addFixedItem( FIELD, "param5", "Param.Premio 2", true, false, 18,2 );
    this.addFixedItem( FIELD, "param6", "Param.Premio 3", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "tipo_premio_descr", "Tipo Premio", true, false, 400 );
    this.addFixedItem( VIRTUAL, "tipo_nivel_descr", "Tipo Objetivo", true, false, 400 );
    this.addFixedItem( VIRTUAL, "elegido", "Fav.", true, false, 400 );
    this.addFixedItem( VIRTUAL, "estimado", "Premio a hoy", true, false, 18,2 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_nivel"; }

	public boolean detectIsComplete() throws Exception {
		if (pParam1.isNotNull()) return true; 
		if (pParam2.isNotNull()) return true; 
		if (pParam3.isNotNull()) return true; 
		if (pParam4.isNotNull()) return true; 
		if (pParam5.isNotNull()) return true; 
		if (pParam6.isNotNull()) return true; 
		if (pValor.isNotNull() ) return true; 
		if (pPremio.isNotNull()) return true; 
		return false;
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	transient BizDetalle objDetalle;
  transient ITipoPremio objTipoPremio;
  public ITipoPremio getTipoPremioInstance() throws Exception {
  	if (objTipoPremio!=null && objTipoPremio.getClass().getName().equals(pTipoPremio.getValue())) return objTipoPremio;
  	if (pTipoPremio.isNull()) pTipoPremio.setValue(JTipoPremioPorcentajeDelTotal.class.getName());
  	ITipoPremio pr = (ITipoPremio)Class.forName(getTipoPremio()).newInstance();
  	return objTipoPremio=pr;
  	
  }
  transient ITipoNivel objTipoNivel;
  public ITipoNivel getTipoObjetivoInstance() throws Exception {
  	if (objTipoNivel!=null && objTipoNivel.getClass().getName().equals(pTipoObjetivo.getValue())) return objTipoNivel;
  	if (pTipoObjetivo.isNull()) pTipoObjetivo.setValue(JTipoNivelNormal.class.getName());
  	ITipoNivel pr = (ITipoNivel)Class.forName(getTipoObjetivo()).newInstance();
  	return objTipoNivel=pr;
  	
  }

  @Override
  public void clean() throws Exception {
  	objTipoPremio=null;
  	objDetalle=null;
  	super.clean();
  }
  @Override
  public void setLogicalParent(JRecord zValue) throws Exception {
  	setObjDetalle((BizDetalle)zValue);
  }
  public void setObjDetalle(BizDetalle det) throws Exception {
  	objDetalle=det;
  }
  public BizDetalle getObjDetalle() throws Exception {
  	if (objDetalle!=null) return objDetalle;
  	if (pLinea.isNull()) return null;
  	BizDetalle det = new BizDetalle();
  	det.dontThrowException(true);
  	if (!det.read(getLinea())) return null;
  	ILogicaContrato logica = det.getObjLogicaInstance();
  	BizDetalle biz = logica.getBiz();
  	biz.copyProperties(det);
  	return objDetalle=biz;
  }
  
  public String getError() throws Exception {
		ITipoNivel obj = getTipoObjetivoInstance();
		String error = obj.getError(this);
		if (error!=null) return error;
		ITipoPremio premio = getTipoPremioInstance();
		error = premio.getError(this);
		return error;
	}
    public double getValorPremio(double val,double level) throws Exception {
    ITipoNivel nivel=getTipoObjetivoInstance();
    double valor =nivel.calculeForPremio(this, val);
  	ITipoPremio premio = getTipoPremioInstance();
  	return premio.calcule(valor,level,this, getObjDetalle());
  }
  public String getValorPerc(double val,double level) throws Exception {
  	ITipoPremio premio = getTipoPremioInstance();
  	return premio.display(""+getValorPercDouble(val,level));
  }
  public double getValorPercDouble(double val,double level) throws Exception {
    ITipoNivel nivel=getTipoObjetivoInstance();
    double valor =nivel.calculeForPremio(this, val);
  	ITipoPremio premio = getTipoPremioInstance();
  	return JTools.rd(premio.calculePerc(valor,level,this),2);
  }
  public double getEstimado() throws Exception {
  	ITipoPremio premio = getTipoPremioInstance();
  	return JTools.rd(premio.estimar(getObjDetalle().getValorTotal(),this,getObjDetalle()),2);
  }

  public boolean getElegido() throws Exception {
  	return getObjDetalle().getValor()==getIdNivel();
  }
  public double getValor() throws Exception {    
  	ITipoNivel nivel = getTipoObjetivoInstance();
    if (nivel==null) 	return pValor.getValue();
    return nivel.calcule(this);
 	}
  public double getRealValor() throws Exception {    
  	return pValor.getValue();
 	}

	public String getDefaultTipoObjetivo() throws Exception {
		BizDetalle det = getObjDetalle();
		if (det==null) return JTipoNivelNormal.class.getName();
		String tipo = det.getDefaultTipoObjetivo();
		if (tipo==null) return JTipoNivelNormal.class.getName();
		return tipo;
	}
	
	public String getDefaultTipoPremio() throws Exception {
		BizDetalle det = getObjDetalle();
		if (det==null) return JTipoPremioPorcentajeDelTotal.class.getName();
		String tipo = det.getDefaultTipoPremio();
		if (tipo==null) return JTipoPremioPorcentajeDelTotal.class.getName();
		return tipo;
	}


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id_nivel",  zId ); 
    return read(); 
  } 
  public boolean read( String company,long contrato,long zId ) throws Exception { 
    addFilter( "company",  company ); 
	  addFilter( "id", contrato);

    addFilter( "id_nivel",  zId ); 
    return read(); 
  } 
    
	static JMap<String,String> gMapTipoPremios;
  public static JMap<String,String> getTiposPremios() throws Exception {
  	if (gMapTipoPremios!=null) return gMapTipoPremios;
  	gMapTipoPremios = JCollectionFactory.createOrderedMap();
  	gMapTipoPremios.addElement(JTipoPremioPorcentajeDelTotal.class.getName(), "% sobre total");
  	gMapTipoPremios.addElement(JTipoPremioPorcentajeDelPremio.class.getName(), "% del % sobre total");
  	gMapTipoPremios.addElement(JTipoPremioImporteFijo.class.getName(), "Importe fijo");
   	gMapTipoPremios.addElement(JTipoPremioImportePorValor.class.getName(), "Importe fijo * cantidad");
  	gMapTipoPremios.addElement(JTipoPremioAmericanAirlines.class.getName(), "Fx American Airlines");
  	gMapTipoPremios.addElement(JTipoPremioNivelSobreTotal.class.getName(), "% por nivel/total");
  	gMapTipoPremios.addElement(JTipoPremioNivelSobreTotalPorFamilia.class.getName(), "% por nivel/total ponderado");
  	gMapTipoPremios.addElement(JTipoPremioPorcentajePorNivel.class.getName(), "% por nivel");
  	gMapTipoPremios.addElement(JTipoPremioEscalaLineal.class.getName(), "% por escala lineal");
  	gMapTipoPremios.addElement(JTipoPremioPorcentajePorNivelDePorcentajes.class.getName(), "% por nivel para porcentajes");
    gMapTipoPremios.addElement(JTipoPremioCrecimiento.class.getName(), "% ((crecimiento-indiceMin)/(indiceMaximo-indiceMinimo))^CFx(Max%-min%)+min%");
  	gMapTipoPremios.addElement(JTipoPremioGlobalMasRutas.class.getName(), "(global + rutas) x %Bono");
  	gMapTipoPremios.addElement(JTipoPremioPuntos.class.getName(), "% y puntos");
  	return gMapTipoPremios;
  }
	static JMap<String,String> gMapTipoObjetivos;
  public static JMap<String,String> getTiposObjetivos() throws Exception {
  	if (gMapTipoObjetivos!=null) return gMapTipoObjetivos;
  	gMapTipoObjetivos = JCollectionFactory.createOrderedMap();
  	gMapTipoObjetivos.addElement(JTipoNivelNormal.class.getName(), "Alcanzar objetivo");
  	gMapTipoObjetivos.addElement(JTipoNivelFlotante.class.getName(), "Alcanzar objetivo flotante");
  	gMapTipoObjetivos.addElement(JTipoNivelNormalConLimite.class.getName(), "Alcanzar objetivo, y nivel minimo");
   	gMapTipoObjetivos.addElement(JTipoNivelNormalConLimiteExterno.class.getName(), "Alcanzar objetivo, y nivel minimo auxiliar");
  	gMapTipoObjetivos.addElement(JTipoNivelMixto.class.getName(), "Alcanzar objetivo, y ganancia minimo");
  	gMapTipoObjetivos.addElement(JTipoNivelMonedaExtranjera.class.getName(), "Alcanzar obj.moneda extranjera");
  	gMapTipoObjetivos.addElement(JTipoNivelPorcAnterior.class.getName(), "% Periodo anterior");
  	gMapTipoObjetivos.addElement(JTipoNivelAA.class.getName(), "Fair share market");
  	gMapTipoObjetivos.addElement(JTipoNivelPSP.class.getName(), "Alcanzar PSP");
  	gMapTipoObjetivos.addElement(JTipoNivelCrecimiento.class.getName(), "Crecimiento nivel anterior");
  	gMapTipoObjetivos.addElement(JTipoNivelIntervalo.class.getName(), "Crecimiento nivel intervalo");
  	gMapTipoObjetivos.addElement(JTipoNivelPorcentaje.class.getName(), "Alcanzar porcentaje del objetivo");
  	gMapTipoObjetivos.addElement(JTipoNivelPorcToPorc.class.getName(), "Alcanzar porcentaje del porcentaje");
  	gMapTipoObjetivos.addElement(JTipoNivelDifFMS.class.getName(), "Alcanzar GAP FMS");
   	gMapTipoObjetivos.addElement(JTipoNivelPuntos.class.getName(), "Alcanzar puntos");
   	gMapTipoObjetivos.addElement(JTipoNivelUpfront.class.getName(), "Upfront");
    	return gMapTipoObjetivos;
  }

  public JMap<String,String> getTiposPremiosPorTipoDetalle() throws Exception {
  	JMap<String,String> mapTipoPremios = JCollectionFactory.createOrderedMap();
  	if (getObjDetalle().getLogicaContrato().equals(JContratoAvianca.class.getName())) {
	  	mapTipoPremios.addElement(JTipoPremioPuntos.class.getName(), "% y puntos");
  	} else if (getObjDetalle().getLogicaContrato().equals(JContratoLatamFamilia.class.getName())) {
	  	mapTipoPremios.addElement(JTipoPremioNivelSobreTotalPorFamilia.class.getName(), "% por nivel/total ponderado");
  	} else {
  		mapTipoPremios.addElement(JTipoPremioPorcentajeDelTotal.class.getName(), "% sobre total");
  		mapTipoPremios.addElement(JTipoPremioPorcentajeDelPremio.class.getName(), "% del % sobre total");
	  	mapTipoPremios.addElement(JTipoPremioImporteFijo.class.getName(), "Importe fijo");
	  	mapTipoPremios.addElement(JTipoPremioImportePorValor.class.getName(), "Importe fijo x cantidad");
	  	mapTipoPremios.addElement(JTipoPremioAmericanAirlines.class.getName(), "Fx American Airlines");
	  	mapTipoPremios.addElement(JTipoPremioNivelSobreTotal.class.getName(), "% por nivel/total");
	  	mapTipoPremios.addElement(JTipoPremioPorcentajePorNivel.class.getName(), "% por nivel");
	  	mapTipoPremios.addElement(JTipoPremioEscalaLineal.class.getName(), "% por escala lineal");
	  	mapTipoPremios.addElement(JTipoPremioPorcentajePorNivelDePorcentajes.class.getName(), "% por nivel para porcentajes");
	    mapTipoPremios.addElement(JTipoPremioCrecimiento.class.getName(), "% ((crecimiento-indiceMin)/(indiceMaximo-indiceMinimo))^CFx(Max%-min%)+min%");
	  	mapTipoPremios.addElement(JTipoPremioGlobalMasRutas.class.getName(), "(global + rutas) x %Bono");
  	}
  	return mapTipoPremios;
  }

  public JMap<String,String> getTiposObjetivosPorTipoDetalle() throws Exception {
  	JMap<String,String> mapTipoObjetivos = JCollectionFactory.createOrderedMap();
  	if (getObjDetalle().getLogicaContrato().equals(JContratoUpfront.class.getName())) {
  		mapTipoObjetivos.addElement(JTipoNivelUpfront.class.getName(), "Upfront");
  	} else if (getObjDetalle().getLogicaContrato().equals(JContratoAvianca.class.getName())) {
  		mapTipoObjetivos.addElement(JTipoNivelPuntos.class.getName(), "Alcanzar puntos");
  	} else if (getObjDetalle().getLogicaContrato().equals(JContratoTriple.class.getName())) {
  		mapTipoObjetivos.addElement(JTipoNivelFlotante.class.getName(), "Alcanzar objetivo flotante");
  		mapTipoObjetivos.addElement(JTipoNivelNormalConLimiteExterno.class.getName(), "Alcanzar objetivo, y nivel minimo");
  	} else if (getObjDetalle().getLogicaContrato().equals(JContratoBackendDobleRuta.class.getName())) {
  		mapTipoObjetivos.addElement(JTipoNivelFlotante.class.getName(), "Alcanzar objetivo flotante");
  		mapTipoObjetivos.addElement(JTipoNivelNormalConLimiteExterno.class.getName(), "Alcanzar objetivo, y nivel minimo");
  	} else if (getObjDetalle().getLogicaContrato().equals(JContratoLatamFamilia.class.getName())) {
  		mapTipoObjetivos.addElement(JTipoNivelFlotante.class.getName(), "Alcanzar objetivo flotante");
  		mapTipoObjetivos.addElement(JTipoNivelNormalConLimiteExterno.class.getName(), "Alcanzar objetivo, y nivel minimo");
  	} else {
  		mapTipoObjetivos.addElement(JTipoNivelNormal.class.getName(), "Alcanzar objetivo");
  		mapTipoObjetivos.addElement(JTipoNivelPorcAnterior.class.getName(), "% Periodo anterior");
  		mapTipoObjetivos.addElement(JTipoNivelAA.class.getName(), "Fair share market");
  		mapTipoObjetivos.addElement(JTipoNivelPSP.class.getName(), "Alcanzar PSP");
  		mapTipoObjetivos.addElement(JTipoNivelCrecimiento.class.getName(), "Crecimiento nivel anterior");
    	mapTipoObjetivos.addElement(JTipoNivelPorcentaje.class.getName(), "Alcanzar porcentaje del objetivo");
    	mapTipoObjetivos.addElement(JTipoNivelPorcToPorc.class.getName(), "Alcanzar porcentaje del porcentaje");
    	mapTipoObjetivos.addElement(JTipoNivelNormalConLimite.class.getName(), "Alcanzar objetivo, y nivel minimo");
    	mapTipoObjetivos.addElement(JTipoNivelMixto.class.getName(), "Alcanzar objetivo, y ganancia minimo");
    	mapTipoObjetivos.addElement(JTipoNivelMonedaExtranjera.class.getName(), "Alcanzar obj.moneda extranjera");
    	mapTipoObjetivos.addElement(JTipoNivelDifFMS.class.getName(), "Alcanzar GAP FMS");
  	}
   	return mapTipoObjetivos;
  }

	public String getColor(int i) throws Exception {
		String Colors[]= { "32fd03", "28c504", "0b7902", "053a01", "fcf904", "cbc903", "929000", "515001", "fb0012", "c30714", "86030c", "721a21" };
		if (i>=Colors.length) return "ff0000";
		return Colors[i];
	}
	@Override
	public void processUpdateOrInsertWithCheckTable() throws Exception {
		if (this.checkRecordExists())
			this.processUpdate();
		else
			this.processInsertTable();
	}
	
	@Override
	public void processInsertTable() throws Exception {
		if (getObjDetalle().getObjContrato().isObjetivo()) {
			setTipoPremio(JTipoPremioObjetivo.class.getName());
		}
		if (pColor.isNull()) setColor(getColor(1));
		setValor(getValor());
		super.processInsertTable();
		
		recalculeColor();
		getObjDetalle().processPopulate(null, false);
	}
	@Override
	public void processInsert() throws Exception {
		if (getObjDetalle().getObjContrato().isObjetivo()) {
			setTipoPremio(JTipoPremioObjetivo.class.getName());
		}
		if (pColor.isNull()) setColor(getColor(1));
		setValor(getValor());
		super.processInsert();
		
		recalculeColor();
		getObjDetalle().processPopulate(null, false);
	}
	
	public void processInsertClon() throws Exception {
		if (getObjDetalle().getObjContrato().isObjetivo()) {
			setTipoPremio(JTipoPremioObjetivo.class.getName());
		}
		if (pColor.isNull()) setColor(getColor(1));
		setValor(getValor());
		super.processInsert();
		
		recalculeColor();
	}

	@Override
	public void processUpdate() throws Exception {
		if (getObjDetalle().getObjContrato().isObjetivo()) {
			setTipoPremio(JTipoPremioObjetivo.class.getName());
		}
		setValor(getValor());
		super.processUpdate();
		recalculeColor();
//		getObjDetalle().processPopulate(null, false);
	}
	public void processUpdateSimple() throws Exception {
		if (getObjDetalle().getObjContrato().isObjetivo()) {
			setTipoPremio(JTipoPremioObjetivo.class.getName());
		}
		setValor(getValor());
		super.processUpdate();
		recalculeColor();
	}
	@Override
	public void processDelete() throws Exception {
		super.processDelete();
		recalculeColor();
	}
	public void recalculeColor() throws Exception {
		JRecords<BizNivel> niveles = new JRecords<BizNivel>(BizNivel.class);
		niveles.addFilter("company", getCompany());
		niveles.addFilter("id", getId());
		niveles.addFilter("linea", getLinea());
		niveles.addOrderBy("valor","ASC");
		int c= 0;
		JIterator<BizNivel> it = niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel nivel = it.nextElement();
			nivel.setColor(getColor(c++));
			nivel.update();
		}

		
	}
	public void execProcessMarcarComoFavorito() throws Exception {
		JExec oExec = new JExec(this, "processMarcarComoFavorito") {

			@Override
			public void Do() throws Exception {
				processMarcarComoFavorito();
			}
		};
		oExec.execute();
	}

	public void processMarcarComoFavorito() throws Exception {
		BizDetalle det = getObjDetalle();
		det.setValor(getIdNivel());
		det.processUpdate();
		
	}
}
