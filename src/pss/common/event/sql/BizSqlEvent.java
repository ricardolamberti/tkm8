package pss.common.event.sql;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import pss.JPath;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.BizCampos;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.event.sql.serie.GuiVirtualSeries;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.implementations.GraphXYLine;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.GuiVirtuals;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class BizSqlEvent extends JRecord implements IActionData {

	public static final String OK = "OK";
	public static final String ERROR = "ERROR";
	public static final String REPROCESAR = "REPROCESAR";
	public static final String REPROCESANDO= "REPROCESANDO";
	
  private JLong pId = new JLong();
  private JLong pSystemProtect = new JLong();
  protected JString pCompany = new JString();

  private JString pDescripcion = new JString();
  private JString pObservacion = new JString();
  protected JString pEstado = new JString();
  private JDate pFechaIniRepro = new JDate();
  private JDate pFechaFinRepro = new JDate();
  
  private JDate pFechaEmergencia = new JDate();
  private JDate pFechaAviso = new JDate();
  private JDate pFechaMinimo = new JDate();
  private JDateTime pFechaUpdate = new JDateTime();
   
  private JFloat pValorEmergencia = new JFloat();
  private JFloat pValorAviso = new JFloat();
  private JFloat pValorMinimo = new JFloat() ;
  
  private JString pClass = new JString();
  private JString pConsulta = new JString();
  private JString pConsultaDetalle = new JString();

  private JLong pCustomList = new JLong();
  private JBoolean   pInvisible = new JBoolean();

  private JString pCampo = new JString();
  private JLong   pIdCampo = new JLong();
  private JString pCampoVirtual = new JString();
  private JString pEjesValor = new JString();
  private JString pFiltrosValor = new JString();
  private JString pValor01 = new JString();
  private JString pValor02 = new JString();
  private JString pValor03 = new JString();
  private JString pValor04 = new JString();
  private JString pValor05 = new JString();
  private JString pValor06 = new JString();
  private JString pValor07 = new JString();
  private JString pValor08 = new JString();
  private JString pValor09 = new JString();
  private JString pValor10 = new JString();
  private JString pFiltro01 = new JString();
  private JString pFiltro02 = new JString();
  private JString pFiltro03 = new JString();
  private JString pFiltro04 = new JString();
  private JString pFiltro05 = new JString();
  private JString pFiltro06 = new JString();
  private JString pFiltro07 = new JString();
  private JString pFiltro08 = new JString();
  private JString pFiltro09 = new JString();
  private JString pFiltro10 = new JString();
  private JString pMapa = new JString();
  
  private JString pCampoDescr = new JString() {
  	public void preset() throws Exception {
  		pCampoDescr.setValue(getCampoDescr());
  	};
  };

  private JBoolean pActivo = new JBoolean();
  private JFloat pVarValor = new JFloat() {
  	public void preset() throws Exception {
  		pVarValor.setValue(getVarValor());
  	};
  };
  private JFloat pVarPorc = new JFloat() {
  	public void preset() throws Exception {
  		pVarPorc.setValue(getVarPorc());
  	};
  };
  private JFloat pTendencia = new JFloat() {
  	public void preset() throws Exception {
  		pTendencia.setValue(getTendencia());
  	};
  };
  private JImage pTendenciaView = new JImage() {
  	public void preset() throws Exception {
  		pTendenciaView.setValue(getTendenciaView());
  	};
  };
  
  
  
  private JDate pFDesde = new JDate();
  private JDate pFHasta = new JDate();
  private JFloat pValor = new JFloat() {
  	public void preset() throws Exception {
  		pValor.setValue(getValor());
  	};
  	public int getCustomPrecision() throws Exception {return 2;};
  };
  private JString pImagen = new JString() {
  	public void preset() throws Exception { 
  		pImagen.setValue(generateGraph1());
  	};
  };
  private JString pImagenAviso = new JString() {
  	public void preset() throws Exception { 
  		pImagenAviso.setValue(generateGraphAviso());
  	};
  };
  
	protected JObjBDs<BizVirtual> pVariables= new JObjBDs<BizVirtual>() {
		public void preset() throws Exception {
			pVariables.setValue(getObjVariables());
		}
	};
	protected JObjBDs<BizCampo> pFiltros= new JObjBDs<BizCampo>() {
		public void preset() throws Exception {
			pFiltros.setValue(getObjFiltros());
		}
	};
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setObservacion(String zValue) throws Exception {    pObservacion.setValue(zValue);  }
  public String getObservacion() throws Exception {     return pObservacion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setFechaIniRepro(Date zValue) throws Exception {    pFechaIniRepro.setValue(zValue);  }
  public Date getFechaIniRepro() throws Exception {     return pFechaIniRepro.getValue();  }
  public void setFechaFinRepro(Date zValue) throws Exception {    pFechaFinRepro.setValue(zValue);  }
  public Date getFechaFinRepro() throws Exception {     return pFechaFinRepro.getValue();  }
  public void setFechaEmergencia(Date zValue) throws Exception {    pFechaEmergencia.setValue(zValue);  }
  public Date getFechaEmergencia() throws Exception {     return pFechaEmergencia.getValue();  }
  public boolean isNullFechaEmergencia() throws Exception { return  pFechaEmergencia.isNull(); } 
  public void setNullToFechaEmergencia() throws Exception {  pFechaEmergencia.setNull(); } 
  public void setFechaAviso(Date zValue) throws Exception {    pFechaAviso.setValue(zValue);  }
  public Date getFechaAviso() throws Exception {     return pFechaAviso.getValue();  }
  public boolean isNullFechaAviso() throws Exception { return  pFechaAviso.isNull(); } 
  public void setNullToFechaAviso() throws Exception {  pFechaAviso.setNull(); } 
  public void setFechaMinimo(Date zValue) throws Exception {    pFechaMinimo.setValue(zValue);  }
  public Date getFechaMinimo() throws Exception {     return pFechaMinimo.getValue();  }
  public boolean isNullFechaMinimo() throws Exception { return  pFechaMinimo.isNull(); } 
  public void setNullToFechaMinimo() throws Exception {  pFechaMinimo.setNull(); } 
  public void setFechaUpdate(Date zValue) throws Exception {    pFechaUpdate.setValue(zValue);  }
  public Date getFechaUpdate() throws Exception {     return pFechaUpdate.getValue();  }
  public void setValorEmergencia(double zValue) throws Exception {    pValorEmergencia.setValue(zValue);  }
  public double getValorEmergencia() throws Exception {     return pValorEmergencia.getValue();  }
  public boolean isNullValorEmergencia() throws Exception { return  pValorEmergencia.isNull(); } 
  public void setNullToValorEmergencia() throws Exception {  pValorEmergencia.setNull(); } 
  public void setValorAviso(double zValue) throws Exception {    pValorAviso.setValue(zValue);  }
  public double getValorAviso() throws Exception {     return pValorAviso.getValue();  }
  public boolean isNullValorAviso() throws Exception { return  pValorAviso.isNull(); } 
  public void setNullToValorAviso() throws Exception {  pValorAviso.setNull(); } 
  public void setValorMinimo(double zValue) throws Exception {    pValorMinimo.setValue(zValue);  }
  public double getValorMinimo() throws Exception {     return pValorMinimo.getValue();  }
  public boolean isNullValorMinimo() throws Exception { return  pValorMinimo.isNull(); } 
  public void setNullToValorMinimo() throws Exception {  pValorMinimo.setNull(); } 
  public void setConsulta(String zValue) throws Exception {    pConsulta.setValue(zValue);  }
  public String getConsulta() throws Exception {     return pConsulta.getValue();  }
  public void setClassDetalle(String zValue) throws Exception {    pClass.setValue(zValue);  }
  public String getClassDetalle() throws Exception {     return pClass.getValue();  }
  public boolean isNullConsulta() throws Exception { return  pConsulta.isNull(); } 
  public void setNullToConsulta() throws Exception {  pConsulta.setNull(); } 
  public void setConsultaDetalle(String zValue) throws Exception {    pConsultaDetalle.setValue(zValue);  }
  public String getConsultaDetalle() throws Exception {     return pConsultaDetalle.getValue();  }
  public boolean isNullConsultaDetalle() throws Exception { return  pConsultaDetalle.isNull(); } 
  public void setNullToConsultaDetalle() throws Exception {  pConsultaDetalle.setNull(); } 
  public void setCampo(String zValue) throws Exception {    pCampo.setValue(zValue);  }
  public String getCampo() throws Exception {     return pCampo.getValue();  }
  public boolean isNullCampo() throws Exception { return  pCampo.isNull(); } 
  public void setNullToCampo() throws Exception {  pCampo.setNull(); } 
  public void setIdCampo(long zValue) throws Exception {    pIdCampo.setValue(zValue);  }
  public long getIdCampo() throws Exception {     return pIdCampo.getValue();  }
  public boolean isNullIdCampo() throws Exception { return  pIdCampo.isNull(); } 
  public void setNullToIdCampo() throws Exception {  pIdCampo.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setSystemProtect(long zValue) throws Exception {    pSystemProtect.setValue(zValue);  }
  public long getSystemProtect() throws Exception {     return pSystemProtect.getValue();  }
  public boolean isSystemProtect() throws Exception {     return !(pSystemProtect.isNull() || pSystemProtect.getValue()==0);  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setCustomList(long zValue) throws Exception {    pCustomList.setValue(zValue);  }
  public long getCustomList() throws Exception {     return pCustomList.getValue();  }
  public boolean isNullCustomList() throws Exception { return  pCustomList.isNull(); } 
  public void setNullToCustomList() throws Exception {  pCustomList.setNull(); } 

  public boolean isOK() throws Exception { return  pEstado.equals(OK); } 
  public boolean isERROR() throws Exception { return  pEstado.equals(ERROR); } 
  public boolean isREPROCESANDO() throws Exception { return  pEstado.equals(REPROCESANDO); } 
  public boolean isREPROCESAR() throws Exception { return  pEstado.isNull() ||  pEstado.equals(REPROCESAR); } 
  public void setInvisible(boolean zValue) throws Exception {    pInvisible.setValue(zValue);  }
  public boolean isInvisible() throws Exception {     return pInvisible.getValue();  }

  public boolean isActivo() throws Exception { return  pActivo.getValue(); } 
  public void setActivo(boolean value) throws Exception {  pActivo.setValue(value); } 

  public Date getFDesde() throws Exception { return  pFDesde.getValue(); } 
  public void setFDesde(Date value) throws Exception {  pFDesde.setValue(value); } 
  public Date getFHasta() throws Exception { return  pFHasta.getValue(); } 
  public void setFHasta(Date value) throws Exception {  pFHasta.setValue(value); } 

  public void setFiltrosValor(String zValue) throws Exception {    pFiltrosValor.setValue(zValue);  }
  public String getFiltrosValor() throws Exception {     return pFiltrosValor.getValue();  }
  public boolean isNullFiltrosValor() throws Exception { return  pFiltrosValor.isNull(); } 
  public void setNullToFiltrosValor() throws Exception {  pFiltrosValor.setNull(); } 

  public void setEjesValor(String zValue) throws Exception {    pEjesValor.setValue(zValue);  }
  public String getEjesValor() throws Exception {     return pEjesValor.getValue();  }
  public boolean isNullEjesValor() throws Exception { return  pEjesValor.isNull(); } 
  public void setNullToEjesValor() throws Exception {  pEjesValor.setNull(); } 

  public void setCampoVirtual(String zValue) throws Exception {    pCampoVirtual.setValue(zValue);  }
  public String getCampoVirtual() throws Exception {     return pCampoVirtual.getValue();  }
  public boolean isNullCampoVirtual() throws Exception { return  pCampoVirtual.isNull(); } 
  public void setNullToCampoVirtual() throws Exception {  pCampoVirtual.setNull(); } 

  public void setMapa(String zValue) throws Exception {    pMapa.setValue(zValue);  }
  public String getMapa() throws Exception {     return pMapa.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizSqlEvent() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "system_protect", pSystemProtect );
    this.addItem( "custom_list", pCustomList );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "observacion", pObservacion );
    this.addItem( "estado", pEstado );
    this.addItem( "invisible", pInvisible );
    this.addItem( "fecha_inirepro", pFechaIniRepro );
    this.addItem( "fecha_finrepro", pFechaFinRepro );
    this.addItem( "fecha_update", pFechaUpdate );
    this.addItem( "fecha_emergencia", pFechaEmergencia );
    this.addItem( "fecha_aviso", pFechaAviso );
    this.addItem( "fecha_minimo", pFechaMinimo );
    this.addItem( "valor_emergencia", pValorEmergencia );
    this.addItem( "valor_aviso", pValorAviso );
    this.addItem( "valor_minimo", pValorMinimo );
    this.addItem( "consulta", pConsulta );
    this.addItem( "consulta_detalle", pConsultaDetalle );
    this.addItem( "class_detalle", pClass );
    this.addItem( "campo", pCampo );
    this.addItem( "id_campo", pIdCampo );
    this.addItem( "activo", pActivo );
    this.addItem( "valor", pValor );
    this.addItem( "imagen", pImagen );
    this.addItem( "imagen_aviso", pImagenAviso );
    this.addItem( "tendencia", pTendencia );
    this.addItem( "tendenciaview", pTendenciaView);
    this.addItem( "var_valor", pVarValor );
    this.addItem( "var_porc", pVarPorc );
    this.addItem( "fdesde", pFDesde );
    this.addItem( "fhasta", pFHasta );
    this.addItem( "campo_virtual", pCampoVirtual );
    this.addItem( "ejes_valor", pEjesValor );
    this.addItem( "filtros_valor", pFiltrosValor );
    this.addItem( "valor01", pValor01 );
    this.addItem( "valor02", pValor02 );
    this.addItem( "valor03", pValor03 );
    this.addItem( "valor04", pValor04 );
    this.addItem( "valor05", pValor05 );
    this.addItem( "valor06", pValor06 );
    this.addItem( "valor07", pValor07 );
    this.addItem( "valor08", pValor08 );
    this.addItem( "valor09", pValor09 );
    this.addItem( "valor10", pValor10 );
    this.addItem( "filtro01", pFiltro01 );
    this.addItem( "filtro02", pFiltro02 );
    this.addItem( "filtro03", pFiltro03 );
    this.addItem( "filtro04", pFiltro04 );
    this.addItem( "filtro05", pFiltro05 );
    this.addItem( "filtro06", pFiltro06 );
    this.addItem( "filtro07", pFiltro07 );
    this.addItem( "filtro08", pFiltro08 );
    this.addItem( "filtro09", pFiltro09 );
    this.addItem( "filtro10", pFiltro10 );
    this.addItem( "campo_descr", pCampoDescr );
    this.addItem( "mapa", pMapa );
    this.addItem( "obj_variables", pVariables );
    this.addItem( "obj_filtros", pFiltros );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 400 );
    this.addFixedItem( FIELD, "observacion", "Observaciones", true, false, 4000 );
    this.addFixedItem( FIELD, "estado", "Estado", true, false, 50 );
    this.addFixedItem( FIELD, "invisible", "Invisible", true, false, 1 );
    this.addFixedItem( FIELD, "fecha_inirepro", "Fecha ini repro", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_finrepro", "Fecha fin repro", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_update", "Fecha update", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_emergencia", "Fecha emergencia", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_aviso", "Fecha aviso", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_minimo", "Fecha mínimo", true, false, 18 );
    this.addFixedItem( FIELD, "valor_emergencia", "Valor máximo", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_aviso", "Valor aviso(%)", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_minimo", "Valor mínimo", true, false, 18,2 );
    this.addFixedItem( FIELD, "consulta", "Consulta", true, true, 20000 );
    this.addFixedItem( FIELD, "consulta_detalle", "Consulta Detalle", true, false, 20000 );
    this.addFixedItem( FIELD, "class_detalle", "Clase Detalle", true, false, 400 );
    this.addFixedItem( FIELD, "ejes_valor", "Ejes valores", true, false, 4000 );
    this.addFixedItem( FIELD, "campo_virtual", "Campo virtual", true, false, 4000 );
    this.addFixedItem( FIELD, "filtros_valor", "Ejes valores", true, false, 4000 );
    this.addFixedItem( FIELD, "campo", "Campo", true, false, 400 );
    this.addFixedItem( FIELD, "id_campo", "Id Campo", true, false, 18 );
    this.addFixedItem( FIELD, "system_protect", "Protegido", true, false, 18 );
    this.addFixedItem( FIELD, "custom_list", "Generador consultas", true, false, 18 );
    this.addFixedItem( FIELD, "activo", "Activo", true, false, 1 );
    this.addFixedItem( VIRTUAL, "valor", "Valor", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "var_valor", "Variación", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "var_porc", "Variación(%)", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "tendencia", "Tendencia", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "tendenciaview", "T.", true, false, 1 );
    this.addFixedItem( VIRTUAL, "imagen", "imagen", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "imagen_aviso", "imagen_aviso", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "fdesde", "Fecha desde", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fhasta", "Fecha hasta", true, false, 18 );
    this.addFixedItem( VIRTUAL, "valor01", "valor01", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor02", "valor02", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor03", "valor03", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor04", "valor04", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor05", "valor05", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor06", "valor06", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor07", "valor07", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor08", "valor08", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor09", "valor09", true, false, 50 );
    this.addFixedItem( VIRTUAL, "valor10", "valor10", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro01", "filtro01", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro02", "filtro02", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro03", "filtro03", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro04", "filtro04", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro05", "filtro05", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro06", "filtro06", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro07", "filtro07", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro08", "filtro08", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro09", "filtro09", true, false, 50 );
    this.addFixedItem( VIRTUAL, "filtro10", "filtro10", true, false, 50 );
    this.addFixedItem( VIRTUAL, "mapa", "mapa", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "campo_descr", "Descripcion campo", true, false, 250 );
    this.addFixedItem( RECORDS, "obj_variables", "Variables", true, false, 18 ).setClase(BizVirtual.class);
    this.addFixedItem( RECORDS, "obj_filtros", "Filtros", true, false, 18 ).setClase(BizCampo.class);
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return "evt_sqleventos"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  BizCustomList objCustomList;
  
  public BizCustomList getObjCustomList() throws Exception {
  	if (pCustomList.isNull()) return null;
  	if (objCustomList!=null)  return objCustomList;
  	BizCustomList c = new BizCustomList();
  	c.dontThrowException(true);
  	if (!c.read(getCompany(),""+ getCustomList(), null)) return null;
  	return objCustomList=c;
  }

  public BizCustomList getObjCustomListAnyWhere() throws Exception {
  	if (pCustomList.isNull()) return null;
  	if (objCustomList!=null)  return objCustomList;
  	BizCustomList c = new BizCustomList();
  	c.dontThrowException(true);
  	if (!c.read(getCustomList())) return null;
  	return objCustomList=c;
  }

  BizCampo objCampo;
  public BizCampo getObjCampo() throws Exception {
  	if (pIdCampo.isNull() || pCustomList.isNull()) return null;
  	if (objCampo!=null)  return objCampo;
  	BizCampo c = new BizCampo();
  	c.dontThrowException(true);
  	if (!c.read(getCompany(), getCustomList(), getIdCampo())) return null;
  	return objCampo=c;
  }
  
  JRecords<BizVirtual> objVariables;
  public JRecords<BizVirtual> getObjVariables() throws Exception {
  	if (objVariables!=null)  return objVariables;
  	JRecords<BizVirtual> c = new JRecords<BizVirtual>(BizVirtual.class);
  	c.setStatic(true);
  	if (getObjCustomList()!=null) {
  		BizCampos ejes= getObjCustomList().getEjes();
    	JIterator<BizCampo> it = ejes.getStaticIterator();
    	while (it.hasMoreElements()) {
    		BizCampo eje = it.nextElement();
    		BizVirtual v = new BizVirtual();
    		v.setDescripcion(eje.getDescripcion());
    		String value = getObjCustomList().extract(getEjesValor(), eje.getSecuencia());
    		if (value==null || value.equals("")) value = getObjCustomList().extract(getEjesValor(), eje.getSecuenciaOld());
    		v.setValor(value);
    		c.getStaticItems().addElement(v);
    	}
  		
  	}  	
  	return objVariables=c;
  }
  
  BizCampos objFiltros;
  public BizCampos getObjFiltros() throws Exception {
  	BizCampos filtros= new BizCampos();
  	if (getObjCustomList()!=null) {
  		filtros=getObjCustomList().getObjFiltros();
  	}
  	return objFiltros=filtros;
  }
  BizSqlEventDato objLastDato;
  public BizSqlEventDato getObjLastDato(Date fechaDesde,Date fechaHasta) throws Exception {
  	if (fechaHasta ==null && objLastDato!=null) return objLastDato;
  	if (fechaHasta==null) {
    	BizSqlEventDato l = getValorAnterior(null,null);
    	return objLastDato=l;
  	}
  	Calendar c = Calendar.getInstance();
  	c.setTime(fechaDesde);
  	Calendar ch = Calendar.getInstance();
  	ch.setTime(fechaHasta);
  	BizSqlEventDato l = getValorAnterior(c,ch);
  	return l;
  }

  BizSqlEvent objParent;
  public BizSqlEvent getObjParent() throws Exception {
  	if (pSystemProtect.isNull()) return null;
  	if (objParent!=null)  return objParent;
  	BizSqlEvent c = new BizSqlEvent();
  	c.dontThrowException(true);
  	if (!c.read( pSystemProtect.getValue())) return null;
  	return objParent=c;
  }

  public String getCampoDescr() throws Exception {     
  	BizCampo campo = getObjCampo();
  	if (campo==null) return "";
  	if (campo.getObjCustomList()==null) 
  		return "";
  	return campo.getDescrCampo();  
  }


//  Double valor =null;
  public double getValor() throws Exception {
  	BizSqlEventDato d =getObjLastDato(getFDesde(),getFHasta());
 	if ( d==null) 
 		return 0;
//  		return this.getSqlValue(getConsulta(),getCampo());
  	return d.getValue();
//  	if (valor!=null) return valor;
//  	return valor=this.getSqlValue(getConsulta(),getCampo());
  }
  
  public double getVarValor() throws Exception {
  	BizSqlEventDato d =getObjLastDato(getFDesde(),getFHasta());
  	if ( d==null) return 0;
  	return d.getVarValue();
  }
  public double getVarPorc() throws Exception {
  	BizSqlEventDato d =getObjLastDato(getFDesde(),getFHasta());
  	if ( d==null) return 0;
  	return d.getVarPorc();
  }
  public double getTendencia() throws Exception {
  	BizSqlEventDato d =getObjLastDato(getFDesde(),getFHasta());
  	if ( d==null) return 0;
  	return d.getTendencia();
  }
  public String getTendenciaView() throws Exception {
  	if (getTendencia()>0) return "/images/fsube2.gif";
  	if (getTendencia()<0) return "/images/fbaja2.gif";
  	return "/images/figual2.gif";
  	
  	
  }
  @Override
  public void clean() throws Exception {
  	objLastDato=null;
  	objCustomList=null;
  	objCampo=null;
    objVariables=null;
    objFiltros=null;
  	super.clean();
  }
  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  }
  public boolean read(String company, String zId, JFilterMap param ) throws Exception { 
  	dontThrowException(true);
  	addFilter("company",company);
    return read(Long.parseLong(zId) ); 
  } 
  public boolean readByDescription(String company, String zDescr ) throws Exception { 
  	addFilter("company",company);
  	addFilter("descripcion", zDescr);
    return read( ); 
  } 
  public BizSqlEvent getObjWithoutRead() throws Exception { 
  	BizSqlEvent sql = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
  	sql.copyProperties(this);
    return sql; 
  }
  public String extractMapa(long id) throws Exception {
  	return BizCustomList.extract(pMapa.getValue(),id);
  }
  @Override
  public void processInsert() throws Exception {
		
   	if (GetVision().equals("M")) {
   		int i=1;
   		String s = "";
   		if (pValor01.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor01.getValue());
   		if (pValor02.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor02.getValue());
   		if (pValor03.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor03.getValue());
   		if (pValor04.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor04.getValue());
   		if (pValor05.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor05.getValue());
   		if (pValor06.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor06.getValue());
   		if (pValor07.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor07.getValue());
   		if (pValor08.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor08.getValue());
   		if (pValor09.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor09.getValue());
   		if (pValor10.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor10.getValue());
   		pEjesValor.setValue(s);
   		s="";
   		if (pFiltro01.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro01.getValue());
   		if (pFiltro02.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro02.getValue());
   		if (pFiltro03.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro03.getValue());
   		if (pFiltro04.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro04.getValue());
   		if (pFiltro05.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro05.getValue());
   		if (pFiltro06.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro06.getValue());
   		if (pFiltro07.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro07.getValue());
   		if (pFiltro08.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro08.getValue());
   		if (pFiltro09.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro09.getValue());
   		if (pFiltro10.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro10.getValue());
   		pFiltrosValor.setValue(s);
     	pCampo.setValue(getObjCampo().getNameField());
   		
     	fillSqlFromCustomList();
			fillSqlDetalleFromCustomList();
   	}
   	cloneCustomList();
    this.setActivo(true);
   	this.SetVision(null);
  	super.processInsert();
  	processPopulate(null,null);
  	crearAvisosBasicos();
  }

  public void crearAvisosBasicos() throws Exception {
  	BizSqlEventAction action = new BizSqlEventAction();
  	action.setCompany(getCompany());
  	action.setIdevento(""+getId());
  	action.setClassevento(BizSqlEvent.class.getCanonicalName());
  	action.setTipoPeriodicidad(BizSqlEventAction.LIMITE);
  	action.setAction(BizSqlEventAction.NOTIF);
  	action.setUsuario(BizUsuario.getUsr().GetUsuario());
   	BizPlantilla pl = BizCompany.getObjPlantilla(getCompany(), "sys_email");
 	  if (pl!=null) action.setIdplantilla(pl.getId());
  	action.processInsert();
  }

  
  @Override
  public void processUpdate() throws Exception {
   	if (GetVision()!=null && GetVision().equals("M")&& getObjCampo()!=null) {
   		int i=1;
   		String s = "";
  		if (pValor01.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor01.getValue());
   		if (pValor02.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor02.getValue());
   		if (pValor03.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor03.getValue());
   		if (pValor04.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor04.getValue());
   		if (pValor05.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor05.getValue());
   		if (pValor06.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor06.getValue());
   		if (pValor07.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor07.getValue());
   		if (pValor08.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor08.getValue());
   		if (pValor09.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor09.getValue());
   		if (pValor10.hasValue()) s+=(s.equals("")?"":"|")+((extractMapa(i++))+"="+pValor10.getValue());
   		pEjesValor.setValue(s);
   		s="";
   		if (pFiltro01.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro01.getValue());
   		if (pFiltro02.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro02.getValue());
   		if (pFiltro03.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro03.getValue());
   		if (pFiltro04.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro04.getValue());
   		if (pFiltro05.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro05.getValue());
   		if (pFiltro06.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro06.getValue());
   		if (pFiltro07.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro07.getValue());
   		if (pFiltro08.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro08.getValue());
   		if (pFiltro09.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro09.getValue());
   		if (pFiltro10.hasValue()) s+=(s.equals("")?"":"|")+((i++)+"="+pFiltro10.getValue());
   		pFiltrosValor.setValue(s);
     	pCampo.setValue(getObjCampo().getNameField());
   		
     	fillSqlFromCustomList();
			fillSqlDetalleFromCustomList();
   	}
   	cloneCustomList();
  	this.SetVision(null);
   	super.processUpdate();
  }
  public void fillVirtuals() throws Exception {
  	if (getObjCustomList()==null) return;
		JRecords<BizCampo> ejes = getObjCustomList().getEjes();
		ejes.clearOrderBy();
		ejes.addOrderBy("secuencia");
		JIterator<BizCampo> it = ejes.getStaticIterator();
		int i=1;
		String smapa="";
		while (it.hasMoreElements()) {
			BizCampo eje = it.nextElement();
	    smapa+=i+"="+eje.getSecuencia()+"|";
	    String value = BizCustomList.extract(getEjesValor(), eje.getSecuencia());
	    if (value==null) continue;
	    if (value.equals("")) value = BizCustomList.extract(getEjesValor(), eje.getSecuenciaOld());
	    getProp("valor"+JTools.lpad(""+i, "0", 2)).setValue(value);
			i++;
		}
  	pMapa.setValue(smapa);
  	
  	pFiltro01.setValue(BizCustomList.extract(getFiltrosValor(), 1));
  	pFiltro02.setValue(BizCustomList.extract(getFiltrosValor(), 2));
  	pFiltro03.setValue(BizCustomList.extract(getFiltrosValor(), 3));
  	pFiltro04.setValue(BizCustomList.extract(getFiltrosValor(), 4));
  	pFiltro05.setValue(BizCustomList.extract(getFiltrosValor(), 5));
  	pFiltro06.setValue(BizCustomList.extract(getFiltrosValor(), 6));
  	pFiltro07.setValue(BizCustomList.extract(getFiltrosValor(), 7));
  	pFiltro08.setValue(BizCustomList.extract(getFiltrosValor(), 8));
  	pFiltro09.setValue(BizCustomList.extract(getFiltrosValor(), 9));
  	pFiltro10.setValue(BizCustomList.extract(getFiltrosValor(), 10));
  }
  


	public void execProcessUpdateDatos() throws Exception {
		JExec oExec = new JExec(this, "processUpdateDatos") {

			@Override
			public void Do() throws Exception {
				processUpdateDatos();
			}
		};
		oExec.execute();
	}

	public void processUpdateDatos() throws Exception {
		boolean updated =false;
		BizSqlEventDato datoUltimo = new BizSqlEventDato();
		datoUltimo.addFilter("id_evento",getId());
		Date fechaUltimoDato = datoUltimo.SelectMaxDate("fecha");
		

		Calendar fdesde = Calendar.getInstance();
		fdesde.setTime(fechaUltimoDato != null ? fechaUltimoDato : JDateTools.getFirstDayOfYear(new Date()));
		Calendar fhasta = Calendar.getInstance();
		fhasta.setTime(new Date());
		for (int day = 1; fdesde.before(fhasta); day++) {
			updated |= agregarASerie(true, fdesde, false,null)!=null;
			fdesde.add(Calendar.DAY_OF_YEAR, 1);
		}
		updated |= agregarASerie(true, fhasta, true,null)!=null;// siempre que se ejecute una vez, para actualizar el dia actual
		setFechaUpdate(new Date());
		update();
		
  }
  
 
  public String getColor() throws Exception {
  	
		double valor = getValor();
		
		if (!isNullValorMinimo() && valor<=getValorMinimo()) {
				return "1704a1";
		}
		if (!isNullValorEmergencia() && valor>=getValorEmergencia()) {
			return "891a04";
		} else {
  		if (!isNullValorAviso()) {
  		  double valorMax=0;
  		  double valorMin=0;
  			if (!isNullValorEmergencia()) valorMax=getValorEmergencia()-((getValorEmergencia()*getValorAviso())/100); 
  			if (!isNullValorMinimo()) valorMin=getValorMinimo()+((getValorMinimo()*getValorAviso())/100); 
	  		if (valorMax!=0 && valor>=valorMax) {
	  			return "bcbe2d";
	  		}
	  		else if (valorMin!=0 && valor<=valorMin) {
	  			return "bcbe2d";
	  		}
  		}
  	}
		return "000000";

  }
  
  
  
	public String generateGraph1() throws Exception {
		GuiVirtualSeries w = new GuiVirtualSeries();
	  String sSql;
	  Date fdesde = getFDesde();
	  Date fhasta = getFHasta();
	  if (fdesde==null) fdesde=JDateTools.getFirstDayOfYear(new Date());
	  if (fhasta==null) fhasta=JDateTools.getDateEndDay(new Date());
	  
	 	sSql ="SELECT '"+getDescripcion()+"' as descripcion,evt_sqleventos_datos.value as valor, fecha  ";
	 	sSql+=" FROM evt_sqleventos_datos where id_evento= "+getId()+ " and ";
		sSql+=" fecha between '"+fdesde+"' and '"+fhasta+"' ";
   	sSql+=" order by fecha ";
	  w.getRecords().SetSQL(sSql);
	  
		JWinList wl = new JWinList(w);

		wl.AddColumnaLista("descripcion");
		wl.AddColumnaLista("valor");
		wl.AddColumnaLista("fecha");

		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	  gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Variable");
		gr.setImage("html/images/fondochart.png");

		gr.setTitle(getDescripcion());
		
		gr.getModel().addColumn("fecha", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);
		if (!isNullValorEmergencia()) gr.setMax(getValorEmergencia());
		if (!isNullValorMinimo()) gr.setMin(getValorMinimo());
		
  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(900, 900).replace("script:", "");
		}
		return null;
	}
	
	public String generateGraphAviso() throws Exception {
		GuiVirtuals w = new GuiVirtuals();
	  String sSql;
	  Date  fdesde=JDateTools.getFirstDayOfYear(new Date());
	  Date fhasta=JDateTools.getDateEndDay(new Date());
	  
	 	sSql ="SELECT '"+getDescripcion()+"' as descripcion,evt_sqleventos_datos.value as valor, extract(doy from fecha) as icono ";
	 	sSql+=" FROM evt_sqleventos_datos where id_evento= "+getId()+ " and ";
		sSql+=" fecha between '"+fdesde+"' and '"+fhasta+"' ";
   	sSql+=" order by fecha ";
	  w.getRecords().SetSQL(sSql);
	  
		JWinList wl = new JWinList(w);

		wl.AddColumnaLista("descripcion");
		wl.AddColumnaLista("valor");
		wl.AddColumnaLista("icono");

		Graph gr = new GraphXYLine();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	  gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Variable");
		
		gr.setTitle(getDescripcion());
		
		gr.getModel().addColumn("icono", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(500, 500);
		}
		return null;
	}

	public void execProcessPopulate(final Date fdesde, final Date fhasta) throws Exception {
		JExec oExec = new JExec(this, "processPopulate") {

			@Override
			public void Do() throws Exception {
				processPopulate(fdesde,fhasta);
			}
		};
		oExec.execute();
	}
	
	private void deleteRecords(long id) throws Exception {
		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete  from evt_sqleventos_datos where id_evento=" + id + "");
		reg.close();
	}

	public void processPopulate(Date fechaDesde, Date fechaHasta) throws Exception {
		if (!BizUsuario.getUsr().getObjBusiness().isSqlEventProcessInService()) {
			internalServiceProcessPopulate(fechaDesde, fechaHasta);
			return;
		}
		setServicePopulate(fechaDesde, fechaHasta,null);
	}
	
	public synchronized void setServicePopulate(Date fechaDesde, Date fechaHasta, String forceMark) throws Exception {
		if (forceMark!=null) {
			if (forceMark.equals(OK) && isREPROCESANDO()) setEstado(forceMark);
			else if (forceMark.equals(REPROCESANDO))	setEstado(forceMark);
			this.setFechaUpdate(new Date());
			update();
			return;
		}
		
		if (isREPROCESANDO()) {
			boolean esMasAmplia= false;
			Date fi = getFechaIniRepro();
			Date ff = getFechaFinRepro();
			esMasAmplia |= (fechaDesde==null && fi!=null);
			esMasAmplia |= (fechaHasta==null && ff!=null);
			esMasAmplia |= (fechaDesde!=null && fi!=null && JDateTools.dateEqualOrAfter(fi, fechaDesde));
			esMasAmplia |= (fechaHasta!=null && ff!=null && JDateTools.dateEqualOrBefore(ff, fechaHasta));
			if (!esMasAmplia) return; // lo ignoro porque se esta recalculando algo mas grande.
			// lo dejo poner reprocesar
		}
		
		boolean ajustFechas = isREPROCESAR();
		setEstado(REPROCESAR);
		
		if (ajustFechas) { // tomo la mas amplia
			Date fi = getFechaIniRepro();
			Date ff = getFechaFinRepro();
			if (fechaDesde==null) setFechaIniRepro(null);
			else if (fi==null) setFechaIniRepro(null);
			else if (JDateTools.dateEqualOrAfter(fi, fechaDesde))  setFechaIniRepro(fechaDesde);
			if (fechaHasta==null) setFechaFinRepro(null);
			else if (ff==null) setFechaIniRepro(null);
			else if (JDateTools.dateEqualOrBefore(ff, fechaHasta))  setFechaFinRepro(fechaHasta);
		} else {
			setFechaIniRepro(fechaDesde);
			setFechaFinRepro(fechaHasta);
		}

		update();
		
	}
	
	public void execServiceProcessPopulate() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				serviceProcessPopulate();
			}
		};
		oExec.execute();
	}

	public void execSetServicePopulate(final Date fechaDesde, final Date fechaHasta, final String forceMark) throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				setServicePopulate( fechaDesde,  fechaHasta,  forceMark);
			}
		};
		oExec.execute();
	}

	private void serviceProcessPopulate() throws Exception {
			internalServiceProcessPopulate(getFechaIniRepro(),getFechaFinRepro());
			setServicePopulate(null,null,OK);

	}		
	
	protected void internalServiceProcessPopulate(Date fechaDesde, Date fechaHasta) throws Exception {
		Calendar fechaInicial = Calendar.getInstance();
		if (fechaHasta==null) fechaHasta=new Date();
		//this
		Calendar fdesde = Calendar.getInstance();
		Calendar fhasta = Calendar.getInstance();
		fhasta.setTime(fechaHasta);
		if (fechaDesde==null) {
			Date fechaI = new Date();
			Calendar unoAtras = Calendar.getInstance();
			unoAtras.setTime(new Date());
			unoAtras.add(Calendar.YEAR, -1);
			
			fechaInicial.setTime(fechaI);

			if (fechaInicial.before(unoAtras))
				fechaInicial = unoAtras;			
			
			fdesde = fechaInicial;
		} else 
			fdesde.setTime(JDateTools.getFirstDayOfYear(fechaDesde));

		if (getConsulta().startsWith("VIRTUAL:")) {
			deleteRecords(getId());
			agregarASerie(false,fhasta,false,null);
			return;
		}
		if (fechaDesde==null) {
//			deleteRecords(getId());
			BizSqlEventDato last=null;
			for (int day = 1; fdesde.before(fhasta); day++) {
				last=agregarASerie(true,fdesde,false,last);
				fdesde.add(Calendar.DAY_OF_YEAR, 1);
				Thread.yield();
			}
			agregarASerie(true,fhasta,true,last);
		} else {
	//		deleteRecords(getId());//bug, se generaron registros de mas estap ara eliminar, pero deberia sacarse a futuro
			int dias=1;
			BizSqlEventDato last=null;
			while( fdesde.before(fhasta)) {//fdesde.getTime() fhasta.getTime()
				last=agregarASerie(true,fdesde,false,last);
				fdesde.add(Calendar.DAY_OF_YEAR, dias);
				Thread.sleep(10);
				Thread.yield();
			}
			agregarASerie(true,fhasta,true,last);
//			int dias=1;
//			BizSqlEventDato last=null;
//			while( fdesde.before(fhasta)) {//fdesde.getTime() fhasta.getTime()
//				last=agregarASerie(true,fdesde,false,last);
//				if (fhasta.get(Calendar.YEAR)!=fdesde.get(Calendar.YEAR)) {
//					fdesde.add(Calendar.MONTH, 1);
//				} else {
//					fdesde.add(Calendar.DAY_OF_YEAR, dias);
//				}
//				Thread.yield();
//			}
//			agregarASerie(true,fhasta,true,last);
		}
		setFechaUpdate(new Date());
		JRecords<BizSqlEventDato> sobrante =new JRecords<BizSqlEventDato>(BizSqlEventDato.class);
		sobrante.addFilter("id_evento", getId());
		sobrante.addFilter("fecha", fhasta.getTime(),">");
		sobrante.delete();
	}
	
	public BizSqlEventDato agregarASerie(boolean checkExistencia,Calendar fecha,boolean actual,BizSqlEventDato anterior) throws Exception{
		BizSqlEventDato serie = new BizSqlEventDato();
		double oldValue=0;
		boolean insert=true;
		if (checkExistencia) {
			serie.dontThrowException(true);
			if (serie.read(getId(),fecha.getTime())) {
				oldValue=serie.getValue();
				insert=false;
			}

		}
		BizSqlEventDato last;
		double valor;
		valor=getValor(fecha);
		if (anterior!=null) {
			Calendar fant = Calendar.getInstance();
			fant.setTime(anterior.getFecha());
			last = agregarASerieWithValue(true,fant,anterior.getValue(),fecha,valor,anterior);//fecha.getTime()
		}
		else 
			last=(anterior!=null?anterior:getValorAnterior(null,fecha));
		if (!insert && Math.abs(oldValue-valor)<0.01) 
			return serie;
		
		double dif = last==null?0:valor-last.getValue();
		double porc = last==null?0:valor==0?0:(dif*100)/valor;
		double tendencia = last==null?0:dif>0?1:(dif<0?-1:0);
		serie.setIdevento(getId());
		serie.setFecha(fecha.getTime());
		serie.setValue(valor);
		serie.setVarValue(dif);
		serie.setVarPorc(porc);
		serie.setTendencia(tendencia);
		if (insert) 
			serie.processInsert();
		else
			serie.processUpdate();
		Thread.yield();
		return serie;
		
	}
	public BizSqlEventDato agregarASerieWithValue(boolean checkExistencia,Calendar fechaIni,double valorIni,Calendar fechaEnd,double valorEnd,BizSqlEventDato anterior) throws Exception{
		Calendar fecha = Calendar.getInstance();
		fecha=fechaIni;
		int dias=0;
		double oldValue=valorIni;
		fecha.add(Calendar.DAY_OF_YEAR, 1);
		BizSqlEventDato last=anterior;
		int intervalo = JDateTools.getDaysBetween(fechaIni.getTime(), fechaEnd.getTime());
		if (intervalo==0) return anterior;
		double paso = (valorEnd-valorIni)/intervalo;
		while (fecha.before(fechaEnd)) {
			BizSqlEventDato serie = new BizSqlEventDato();
			dias++;
			boolean insert=true;
			if (checkExistencia) {
				serie.dontThrowException(true);
				if (serie.read(getId(),fecha.getTime())) {
					oldValue=serie.getValue();
					insert=false;
				}
			}
			double valor=valorIni+(dias*paso);
			if (!insert && oldValue==valor) {
				last=serie;
				fecha.add(Calendar.DAY_OF_YEAR, 1);

				continue;
			}

			double dif = valor-oldValue;
			double porc = valor==0?0:(dif*100)/valor;
			double tendencia = dif>0?1:(dif<0?-1:0);
			serie.setIdevento(getId());
			serie.setFecha(fecha.getTime());
			serie.setValue(valor);
			serie.setVarValue(dif);
			serie.setVarPorc(porc);
			serie.setTendencia(tendencia);
			if (insert) 
				serie.processInsert();
			else
				serie.processUpdate();
			last=serie;
			fecha.add(Calendar.DAY_OF_YEAR, 1);

		}
		Thread.yield();
		return last;
	
	}
	public BizSqlEventDato getValorAnterior(Calendar fechaDesde,Calendar fechaHasta) throws Exception {
		if (pId.isNull()) return null;
		JRecords<BizSqlEventDato> datos = new JRecords<BizSqlEventDato>(BizSqlEventDato.class);
		datos.addFilter("id_evento", getId());
		if (fechaDesde!=null) datos.addFilter("fecha", fechaDesde.getTime(),">=");
		if (fechaHasta!=null) datos.addFilter("fecha", fechaHasta.getTime(),"<=");
		 datos.addFilter("fecha", new Date(),"<=");
		datos.addOrderBy("fecha","DESC");
		datos.setTop(1);
		JIterator<BizSqlEventDato> it = datos.getStaticIterator();
		if (!it.hasMoreElements()) return null;
		BizSqlEventDato dato = it.nextElement();
		return dato;
	}	  
	public double getValor(Calendar fecha) throws Exception {
		return getValor(fecha,null);
	}
	
	public double getValor(Calendar fecha, String extraCondicion) throws Exception {
		if (getId()==587)
			PssLogger.logDebug("error");
		
		String sql = getConsulta();
		sql = JTools.replace(sql, "::FECHA::", "");
		if (sql.equals("")) return 0;
		if (fecha==null || JDateTools.getDateStartDay(fecha.getTime()).equals(JDateTools.getDateStartDay(new Date()))) {
			return this.getSqlValue(sql,getCampo());
		}
		sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
		
		Calendar ayer = Calendar.getInstance();
		ayer.setTime(fecha.getTime());
		ayer.add(Calendar.DAY_OF_MONTH, -1);
		sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
		
		Calendar maniana = Calendar.getInstance();
		maniana.setTime(fecha.getTime());
		maniana.add(Calendar.DAY_OF_MONTH, 1);
		sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
	
		double valor = this.getSqlValue(sql, getCampo());
		 
		return valor;
	}
	public double getAcumulado(Calendar fecha, String campo) throws Exception {
		String sql = getConsultaDetalle();
		sql = JTools.replace(sql, "::FECHA::", "");
		sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
		Calendar ayer = Calendar.getInstance();
		ayer.setTime(fecha.getTime());
		ayer.add(Calendar.DAY_OF_MONTH, -1);
		sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
		Calendar maniana = Calendar.getInstance();
		maniana.setTime(fecha.getTime());
		maniana.add(Calendar.DAY_OF_MONTH, 1);
		sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
		JWins wins= (JWins) Class.forName(getClassDetalle()).newInstance();
		
		JRecords s = new JRecords(wins.getWinRef().getRecord().getClass());
		s.SetSQL(sql);
		return s.selectSum(campo);

	}
	

	@Override
	public void processDelete() throws Exception {
		deleteRecords(getId());
		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete  from evt_sqleventos_history where id_evento='" + getId() + "'");
		reg.close();
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete  from evt_sqleventos_action where id_evento='" + getId() + "'");
		reg.close();
		super.processDelete();
	}
	public BizSqlEvent processClon(String company,boolean alHijo) throws Exception {
		BizSqlEvent newEvent=BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		newEvent.copyProperties(this);
		newEvent.setNullToId();
		newEvent.setCompany(company);
		if (alHijo) newEvent.setSystemProtect(this.getId());
		String sql = newEvent.getConsulta();
		sql = JTools.replace(sql, "'"+this.getCompany()+"'", "'"+company+"'");
		sql = JTools.replace(sql, "company="+this.getCompany(), "company="+company);
			
		newEvent.setConsulta(sql);
		
		sql = newEvent.getConsultaDetalle();
		sql = JTools.replace(sql, "'"+this.getCompany()+"'", "'"+company+"'");
		sql = JTools.replace(sql, "company="+this.getCompany(), "company="+company);
			
		newEvent.setConsultaDetalle(sql);

		newEvent.cloneCustomList();
//			newCustomList= newEvent.getObjCustomListProtect(company,getObjCustomList()==null?null:getObjCustomList().getDescripcion());
//		if (newCustomList==null)
//			newCustomList= newEvent.repairCustomListIntegrity(getObjCustomList());
//		if (newCustomList==null) 
//			throw new Exception("No se puede actualizar "+company);
//		newEvent.setCustomList(newCustomList.getListId());
		repairVariables(newEvent);
		newEvent.setInvisible(true);

		newEvent.setEstado(BizSqlEvent.REPROCESAR);
		newEvent= this.processClon(newEvent);
//		newEvent.processPopulate(null, null);
		return newEvent;
	}	
	
	public BizSqlEvent processUpdateClon(BizSqlEvent newEvent) throws Exception {
		String oldSql=newEvent.getConsulta();
		String company = newEvent.getCompany();
		BizCustomList newCustomList= newEvent.getObjCustomList();

		newEvent.copyNotKeysProperties(this);
		newEvent.setSystemProtect(this.getId());
		String sql = newEvent.getConsulta();
		sql = JTools.replace(sql, "'"+this.getCompany()+"'", "'"+company+"'");
		sql = JTools.replace(sql, "company="+this.getCompany(), "company="+company);
			
		newEvent.setConsulta(sql);
		boolean change = oldSql==null || !oldSql.equals(sql);
		sql = newEvent.getConsultaDetalle();
		sql = JTools.replace(sql, "'"+this.getCompany()+"'", "'"+company+"'");
		sql = JTools.replace(sql, "company="+this.getCompany(), "company="+company);
			
		newEvent.setConsultaDetalle(sql);

		if (newCustomList==null)
			newCustomList= newEvent.getObjCustomListProtect(getCustomList());
		if (newCustomList==null)
			newCustomList= newEvent.getObjCustomListProtect(company,getObjCustomList()==null?null:getObjCustomList().getDescripcion());
		if (newCustomList==null) 
			newCustomList= newEvent.repairCustomListIntegrity(getObjCustomList());
		
		if (newCustomList==null)
			throw new Exception("No se puede actualizar "+company);
		newCustomList.copyNotKeysProperties(getObjCustomList());
		newCustomList.setSystemProtect(getObjCustomList().getListId());
		getObjCustomList().processUpdateClon(newCustomList);
		newEvent.setCustomList(newCustomList.getListId());
		newEvent.setInvisible(true);
		repairVariables(newEvent);
		if (change) newEvent.setEstado(BizSqlEvent.REPROCESAR);
		newEvent.update();
	//	if (change) newEvent.processPopulate(null,null);
		return newEvent;
	}	
	
	
	public void repairVariables(BizSqlEvent newEvent) throws Exception {
		String ejesValor = this.getEjesValor();
		BizCustomList original = this.getObjCustomList();
		BizCustomList clon = newEvent.getObjCustomList();
		if (original==null || clon==null)
			return;
		BizCampos ejes = original.getEjes();
		ejes.clearOrderBy();
		ejes.addOrderBy("secuencia");
		BizCampos ejesClon = clon.getEjes();
		ejesClon.clearOrderBy();
		ejesClon.addOrderBy("secuencia");
		JIterator<BizCampo> it =ejes.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo eje = it.nextElement();
				JIterator<BizCampo> itC =ejesClon.getStaticIterator();
			while (itC.hasMoreElements()) {
				BizCampo ejeC = itC.nextElement();
				if (ejeC.isSimilar(eje)) {
					ejesValor=JTools.replace(ejesValor, eje.getSecuencia()+"=", ejeC.getSecuencia()+"=");
					break;
				}
			}			
		}
		
		newEvent.setEjesValor(ejesValor);
	
		BizCampos camposClon = clon.getCampos();
		camposClon.clearOrderBy();
		camposClon.addOrderBy("secuencia");
		BizCampo campo =new BizCampo();
		campo.dontThrowException(true);
		if(!campo.read( getIdCampo())) {
//			if(!campo.read( getCompany(),getIdCampo())) 
						throw new Exception("No se encuentra el campo "+getIdCampo()+" en "+getCompany());
			
		}
		JIterator<BizCampo> itCamposC = camposClon.getStaticIterator();
		while (itCamposC.hasMoreElements()) {
			BizCampo campoC = itCamposC.nextElement();
			if (campoC.isSimilar(campo)) {
				newEvent.setIdCampo(campoC.getSecuencia());
				break;
			}
		}	
		

	}
	
	
	
  public BizCustomList getObjCustomListProtect(long fatherCustom) throws Exception {
  	BizCustomList c = new BizCustomList();
  	c.dontThrowException(true);
    c.addFilter( "company",  getCompany() ); 
    c.addFilter( "system_protect", fatherCustom ); 
  	if (!c.read())
  		return null;
  	return c;
  }
  public BizCustomList getObjCustomListProtect(String company, String descripcion) throws Exception {
    	BizCustomList c1 = new BizCustomList();
    	c1.dontThrowException(true);
      c1.addFilter( "company",  company ); 
      c1.addFilter( "description", descripcion ); 
    	if (!c1.read())
    		return null;
      return c1;
  }
	public BizCustomList repairCustomListIntegrity(BizCustomList customList) throws Exception {
		if (customList==null) return null;
		
		BizCustomList newCustomList = customList.processClon(getCompany(),true);
		pCustomList.setValue(newCustomList.getListId());
		return newCustomList;
	}
	public BizSqlEvent processClon(BizSqlEvent newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizSqlEventAction> ampls = new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_evento", ""+getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizSqlEventAction ampl = ampls.getRecord();
			BizSqlEventAction na = new BizSqlEventAction();
			na.copyProperties(ampl);
			na.setNullToCorreo();
			na.setNullToUsuario();
			na.setNullToTelefono();
			na.setCompany(newDoc.getCompany());
			na.setIdevento(""+newDoc.getId());
			na.processInsert();
		}

		return newDoc;
	}
  public JWins getDetalles(Calendar fecha,String columnaDestacada) throws Exception {
  	String clase = getClassDetalle();
  	if (clase.equals("")) return null;
	 	JWins wins = (JWins)Class.forName(clase).newInstance();
	 	wins.getRecords().SetSQL(getSQLDetalles(fecha));
		if (columnaDestacada!=null) wins.SetVision(columnaDestacada);
		wins.setShowFilters(false);
  	return wins;
	}

  public double getDetallesSinFuncion(Calendar fecha,String columnaDestacada) throws Exception {
	 	JWins wins = (JWins)Class.forName(getClassDetalle()).newInstance();
	 	wins.getRecords().SetSQL(getSQLDetalles(fecha));
		if (columnaDestacada!=null) wins.SetVision(columnaDestacada);
		wins.setShowFilters(false);
		if (getObjCampo().getCampo().equals("COUNT"))
		  	return wins.getRecords().selectCount();
  	return wins.getRecords().selectSum(getObjCampo().getCampo());
	}

  public String getSQLDetalles(Calendar fecha) throws Exception {
		if (getClassDetalle()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
	 	String sql = this.getConsultaDetalle();
	 	sql = JTools.replace(sql, "::FECHA::", "");
		if (fecha!=null) {
			sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(fecha.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(fecha.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
		
		}
	 	return sql;
	}
  public void fillSqlDetalleFromCustomList() throws Exception {
  	BizCustomList biz = this.getObjCustomList();
   	JWins wins = (JWins)Class.forName(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
  	biz.clean();
  	JRecords r = wins.getRecords();
  	//RJL Corregir
  	String name = getCampoVirtual();
  //	biz.getLogica().prepareOneFields(getObjCampo(), r, null, null);
   	if (name.startsWith("VIRTUAL:")) {
    	setConsultaDetalle(null);
   		return ;
   	}
   	
  	biz.getLogica().setWithgeo(false);
  	biz.getLogica().prepareTables(wins.getRecords());
	  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
	  	biz.getLogica().prepareFilters(wins.getRecords(), false);
	  	biz.getLogica().prepareFiltersDetailFila(wins.getRecords(),getEjesValor(),getFiltrosValor(), false);
//	  	biz.getLogica().prepareFilters(wins.getRecords(), false);
	  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
	  //	biz.getLogica().prepareOrders(wins.getRecords());
  	
   //	biz.addFixedFilter(pEjesValor.getValue());
   
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(r);
   	reg.setDistinct(true);
    
  	String sql = reg.ArmarSelect();
  	setConsultaDetalle(sql);
  	setClassDetalle(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
  }  

  public void fillSqlFromCustomList() throws Exception {
  	BizCustomList biz = this.getObjCustomList();

  	JWins wins = (JWins)Class.forName(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
  	biz.clean();
  	JRecords r = wins.getRecords();
  	//RJL Corregir
  	String name = getCampoVirtual();
   	if (name.startsWith("VIRTUAL:")) {
    	setConsulta(name);
   		return;
   	}
    	name=biz.getLogica().prepareOneFields(getObjCampo(), r, getSqlTotalize(getObjCampo(), getEjesValor(), getFiltrosValor()), null);
    	biz.getLogica().setWithgeo(false);
    	biz.getLogica().prepareTables(wins.getRecords());
	  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
	  	biz.getLogica().prepareFilters(wins.getRecords(), false);
	  	biz.getLogica().prepareFiltersDetail(wins.getRecords(),getEjesValor(),getFiltrosValor(), false);
	//  	biz.getLogica().prepareFilters(wins.getRecords(), false);
	  	biz.getLogica().prepareLiteralFilters(wins.getRecords());

   	biz.addFixedFilter(pEjesValor.getValue());
  	
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(r);
   	reg.setDistinct(true);
    
  	String sql = reg.ArmarSelect();
  	setConsulta(sql);

  	setCustomList(biz.getListId());
  }  
  public void cloneCustomList() throws Exception {
 	
  	BizCustomList biz = this.getObjCustomListAnyWhere();
  	if (biz==null) return;
  	if (biz.getCompany().equals(getCompany())) {
  		if (biz.getDescripcion().startsWith("Indicador:")) return; //ya fue creado
  	} 
  	BizCustomList oNewCustomList=biz;
  	oNewCustomList=new BizCustomList();
  	oNewCustomList.copyProperties(biz);
  	oNewCustomList.setCompany(getCompany());
  	oNewCustomList.setNullListId();
  	oNewCustomList.setInvisible(true);
 // 	if( !oNewCustomList.isSystemProtect())
  		oNewCustomList.setSystemProtect(biz.getListId());
  	oNewCustomList.setDescription("Indicador:"+getDescripcion());
  	biz.processClon(oNewCustomList);
  	clean();
  	setCustomList(oNewCustomList.getListId());
  }  
  public void cloneCustomListExpress() throws Exception {
   	
  	BizCustomList biz = this.getObjCustomList();
   	if (biz.isInvisible()&&!biz.isNullInvisible()) return;
    biz.setInvisible(true);
  	biz.setDescription("Indicador:"+getDescripcion());
  	clean();
  	setCustomList(biz.getListId());
  	update();
  } 
  public String getSqlTotalize(BizCampo c,String ejes,String filters) throws Exception {
  	if (getObjCustomList().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) 
  		throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(getObjCustomList().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	BizCustomList biz = getObjCustomList();
  	biz.clean();
  	biz.getLogica().setWithgeo(false);
  	String name=biz.getLogica().prepareOneFields(c,wins.getRecords(),null,BizCampo.FUNTION_SUM);
  	if (name!=null && name.startsWith("VIRTUAL:")) return name;
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoins(wins.getRecords(), true);
  	biz.getLogica().prepareFilters(wins.getRecords(), true);
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
 // 	biz.getLogica().prepareOrders(wins.getRecords());
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
  	reg.setDistinct(true);
  	return reg.ArmarSelect();
  }
	@Override
	public BizSqlEventHistory generarAviso(JFilterMap a,BizSqlEventAction action, boolean vistaPrevia) throws Exception {
    	BizSqlEventHistory hist= new BizSqlEventHistory();
    	hist.setFecha(new Date());
    	hist.setIdevento(""+getId());
    	hist.setCompany(getCompany());
    	hist.setFundamento(action.getDescripcionData()+" "+action.getFundamento());
    	hist.setIdaction(action.getIdaction());
    	hist.setAccion(action.getAction());
   		hist.setDestinatario(action.getDestinatario());
    	hist.setMensaje(action.getMensajeAviso(a,hist));
    	if (!vistaPrevia) hist.setArchivoAsociado(action.getArchivoAsociado(a,hist));
    	return hist;
  }
	
	public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
  	return hist.getFundamento();
  }
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
			if (action.isAccionURL()||action.isAdjunto()) // envio correo de aviso
		  	return action.getObjPlantilla().generateDocSimple(hist,this);
//			if (action.isOutPantalla()) {
//				GuiSqlEvent l = new GuiSqlEvent();
//				l.setRecord(this);
//				String name = l.hashCode() + ".html";
//				return URLEncoder.encode(l.getHtmlView(100,null),"ISO-8859-1").replace("+", "%20");
//			}
			if (action.isOutEXCEL()) {
				GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
				l.setRecord(this);
				String name = l.hashCode() + ".xls";
				return URLEncoder.encode(l.getHtmlView(40,"excel",a),"ISO-8859-1").replace("+", "%20");
			}
			if (action.isOutCSV()) {
				GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
				l.setRecord(this);
				String name = l.hashCode() + ".csv";
				return URLEncoder.encode(l.getHtmlView(40,"csv",a),"ISO-8859-1").replace("+", "%20");
			}
		return action.getObjPlantilla().generateDocSimple(hist,this);
  }
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist);
	}
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		return getCorreoAviso(a,action, hist,campo,valor);
	}	

	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
			if (action.isOutPantalla()) {
				GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
				l.setRecord(this);
				JTools.MakeDirectory(JPath.PssPathTempFiles() + "/"+l.GetcDato().getCompany());
				String name = l.GetcDato().getCompany()+"/"+ l.hashCode() + ".html";
				String content = l.getHtmlView(40, "html",a);
//				if (action.isAccionDOWNLOAD()) // el encode es porque se por alguna razon no puedo hacer que el reques baje en 8859, entonces lo encodeo y zafo
//					JTools.writeStringToFile(URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20"),JPath.PssPathTempFiles()+"/"+name);
//				else
					JTools.writeStringToFile(content, JPath.PssPathTempFiles() + "/" + name);
				return name;
			}
			if (action.isOutEXCEL()) {
				GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
				l.setRecord(this);
				JTools.MakeDirectory(JPath.PssPathTempFiles() + "/"+l.GetcDato().getCompany());
				String name = l.GetcDato().getCompany()+"/"+ l.hashCode() + ".xls";
				String content = l.getHtmlView(40, "excel",a);
//				if (action.isAccionDOWNLOAD()) // el encode es porque se por alguna razon no puedo hacer que el reques baje en 8859, entonces lo encodeo y zafo
//					JTools.writeStringToFile(URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20"),JPath.PssPathTempFiles()+"/"+name);
//				else
					JTools.writeStringToFile(content, JPath.PssPathTempFiles() + "/" + name);
				return name;
			}
			if (action.isOutCSV()) {
				GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
				l.setRecord(this);
				JTools.MakeDirectory(JPath.PssPathTempFiles() + "/"+l.GetcDato().getCompany());
				String name = l.GetcDato().getCompany()+"/"+ l.hashCode() + ".csv";
				String content = l.getHtmlView(40, "csv",a);
//				if (action.isAccionDOWNLOAD()) // el encode es porque se por alguna razon no puedo hacer que el reques baje en 8859, entonces lo encodeo y zafo
//					JTools.writeStringToFile(URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20"),JPath.PssPathTempFiles()+"/"+name);
//				else
					JTools.writeStringToFile(content, JPath.PssPathTempFiles() + "/" + name);
				return name;
			}
			GuiSqlEvent l = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
			l.setRecord(this);
			JTools.MakeDirectory(JPath.PssPathTempFiles() + "/"+l.GetcDato().getCompany());
			String name = l.GetcDato().getCompany()+"/"+ l.hashCode() + ".html";
			String content = action.getObjPlantilla().generateDocSimple(hist,this);
			JTools.writeStringToFile(URLDecoder.decode(content), JPath.PssPathTempFiles() + "/" + name);
		  return name;
	}
	public String getArchivoExcelAviso(JFilterMap a, BizSqlEventAction action, BizSqlEventHistory hist) throws Exception {
		GuiCustomList l = new GuiCustomList();
		l.setRecord(this);
		String name = l.hashCode() + ".xls";
		JTools.writeStringToFile(l.getHtmlView(100, "excel",a), JPath.PssPathTempFiles() + "/" + name);
		return name;
	}
	@Override
	public void mensajeEnviado(String marca) throws Exception {
  	if (marca.equals("MINIMO")) setFechaMinimo(new Date());
  	if (marca.equals("MAXIMO")) setFechaEmergencia(new Date());
  	if (marca.equals("AVISO")) setFechaAviso(new Date());
  	processUpdate();
	}
	@Override
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
		String resp = null;
		if (!action.getTipoPeriodicidad().equals(BizSqlEventAction.LIMITE))
			return null;
		double valor = getValor(null);
		if (!isNullValorMinimo() && valor <= getValorMinimo()) {
			if (isNullFechaMinimo()) {
				resp = "MINIMO";
				action.setFundamento("caído por debajo de " + getValorMinimo());
//				setFechaMinimo(new Date());
			}
		} else {
			setFechaMinimo(null);
			if (!isNullValorEmergencia() && valor >= getValorEmergencia()) {
				if (isNullFechaEmergencia()) {
					resp = "MAXIMO";
					action.setFundamento("sobrepasado por encima de " + getValorEmergencia());
//					setFechaEmergencia(new Date());
				}
			} else {
				setFechaEmergencia(null);
				if (!isNullValorAviso()) {
					double valorMax = 0;
					double valorMin = 0;
	
					if (!isNullValorEmergencia())
						
						valorMax = getValorEmergencia() - ((getValorEmergencia() * getValorAviso()) / 100);
					if (!isNullValorMinimo())
						valorMin = getValorMinimo() + ((getValorMinimo() * getValorAviso()) / 100);
	
					if (valorMax != 0 && valor >= valorMax) {
						if (isNullFechaAviso()) {
							resp = "AVISO";
							action.setFundamento(" aproximandose en un " + getValorAviso() + "% del valor maximo " + getValorEmergencia());
//							setFechaAviso(new Date());
						}
					} else if (valorMin != 0 && valor <= valorMin) {
						if (isNullFechaAviso()) {
							resp = "AVISO";
							action.setFundamento(" aproximandose en un " + getValorAviso() + "% del valor minimo " + getValorMinimo());
//							setFechaAviso(new Date());
						}
					} else {
						setFechaAviso(null);
					}
				}
			}
		}
		processUpdate();
		return resp;
	}
	public void execAgregarAlPadre() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarAlPadre();
			}
		};
		oExec.execute();
	}
	public void processAgregarAlPadre() throws Exception {
		if (pSystemProtect.isNotNull()) throw new Exception("Ya tiene padre");	
		BizCompany company = BizCompany.getCompany(getCompany());
		String companyPadre = company.getParentCompany();
		
		this.processClon(companyPadre,false);
		
	}
	public void execAgregarTodosLosHijos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarTodosLosHijos();
			}
		};
		oExec.execute();
	}
	public void processAgregarTodosLosHijos() throws Exception {
		JRecords<BizCompany> lista = new JRecords<BizCompany>(BizCompany.class);
		lista.addFilter("parent_company", getCompany());
		JIterator<BizCompany> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCompany comp = it.nextElement();
			BizSqlEvent oldEvent = new BizSqlEvent();
			oldEvent.dontThrowException(true);
			oldEvent.addFilter("company", comp.getCompany());
			oldEvent.addFilter("descripcion", this.getDescripcion());
			if (oldEvent.exists()) continue;
			this.processClon(comp.getCompany(),true);
		}
		
	}

	public void execBorrarTodosLosHijos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processBorrarTodosLosHijos();
			}
		};
		oExec.execute();
	}

	public void processBorrarTodosLosHijos() throws Exception {
		JRecords<BizSqlEvent> lista = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		lista.addFilter("system_protect", getId());
		JIterator<BizSqlEvent> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEvent list = it.nextElement();
			list.processDelete();
		}
		
	}
	public void execCambiarTodosLosHijos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processCambiarTodosLosHijos();
			}
		};
		oExec.execute();
	}
	public void processCambiarTodosLosHijos() throws Exception {
		JRecords<BizSqlEvent> lista = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		lista.addFilter("system_protect", getId());
		JIterator<BizSqlEvent> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEvent list = it.nextElement();
			this.processUpdateClon(list);
		}
		
	}

}
