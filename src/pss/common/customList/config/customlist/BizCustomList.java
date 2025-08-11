package pss.common.customList.config.customlist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.CharEncoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.StringTokenizer;

import pss.JPath;
import pss.common.customList.advanced.BizAdvancedSQL;
import pss.common.customList.config.customlist.importJSON.Command;
import pss.common.customList.config.customlist.importJSON.JSONParser;
import pss.common.customList.config.customlist.importJSON.Report;
import pss.common.customList.config.dynamic.BizDynamic;
import pss.common.customList.config.dynamic.GuiDynamic;
import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.BizCampos;
import pss.common.customList.config.field.filtro.BizFiltroSQL;
import pss.common.customList.config.field.filtro.BizFiltroSQLs;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.graph.BizGraph;
import pss.common.customList.config.informe.BizInforme;
import pss.common.customList.config.informe.BizInformes;
import pss.common.customList.config.owner.BizCustomListOwner;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.sql.BizSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.layoutWysiwyg.parametros.BizDocListadoParam;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphMatrix;
import pss.core.graph.implementations.GraphMatrixAndLine;
import pss.core.graph.implementations.GraphVector;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.ChatGPT;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.IResizableView;

public class BizCustomList extends JRecord implements IActionData {
	 
//	public static final String SQL_BASED="SQL";

  private JString pCompany = new JString();
  private JLong pListId = new JLong();
  private JString pDescription = new JString();
  private JString pRecordOwner = new JString();
  private JString pRelId = new JString();
  private JDate pHoy = new JDate();
  private JString pAgrupado = new JString();
  private JString pInfoDistribution = new JString();
  private JString pSQL = new JString();
  private JString pChatBot = new JString();
  private JBoolean pClearBot = new JBoolean() {
  	public void init() {} {
  		this.setValue(true);
  	}
  };
  private JHtml pHistoryBot = new JHtml();
  private JString pModelo = new JString();
  private JString pOwner = new JString();
  private JLong pSystemProtect = new JLong();
  private JBoolean pInvisible = new JBoolean();
  private JBoolean pWithCC = new JBoolean();
  private JBoolean pTotalizar = new JBoolean();
  private JBoolean pSubTotalizar = new JBoolean();
  private JBoolean pShowTodoEnEdicion = new JBoolean();
  private JBoolean pShowTipo = new JBoolean() {
  	public void preset() throws Exception {
  		if (pShowTipo.isRawNull())
  			pShowTipo.setValue(false);
  	};
  };
  private JBoolean pShowModelo = new JBoolean(){
  	public void preset() throws Exception {
  		if (pShowTipo.isRawNull())
  			pShowTipo.setValue(false);
  	};
  };
  private JBoolean pShowConf = new JBoolean(){
  	public void preset() throws Exception {
  		if (pShowTipo.isRawNull())
  			pShowTipo.setValue(false);
  	};
  };
  private JBoolean pShowIA = new JBoolean(){
  	public void preset() throws Exception {
  		if (pShowIA.isRawNull())
  			pShowIA.setValue(false);
  	};
  };
  private JLong pLimite = new JLong();
  
  // info visiaulizacion
//  private JLong pPosX = new JLong();
//  private JLong pPosY = new JLong();
//  private JLong pHeight = new JLong();
//  private JLong pWidth = new JLong();
  private JLong pGraph = new JLong();

  private JString pDato  = new JString() {
  	public void preset() throws Exception {
  		pDato.setValue(getDato());
  	};
  };
  private JString pViewTable = new JString();
  private JString pImage  = new JString() {
  	public void preset() throws Exception {
  		pImage.setValue(getImage());
  	};
  };

  
  private JString pPreview = new JString() {
  	public void preset() throws Exception {
  		pPreview.setValue(getPreview());
  	};
  };
  private JBoolean pFav = new JBoolean() {
  	public void preset() throws Exception {
  		pFav.setValue(isFavorito());
  	};
  };
	private JObjBDs pSecciones = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjSecciones());
		}
	};
	private JObjBDs pListadoPorSecc = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjListadoPorSeccion(this.getFilter()));
		}
	};

	private JObjBDs pListado = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjListado(this.getFilter()));
		}
	};

	private JObjBDs pCampos = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjAllCampos());
		}
	};
	private JObjBDs pFiltros = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjFiltros());
		}
	};
	private JObjBDs pFiltrosReporte = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjFiltrosReporte());
		}
	};
	private JObjBDs pCamposVisibles = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getCamposVisibles());
		}
	};
	private JObjBDs pEjesCC = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjEjesControl());
		}
	};
	private JObjBDs pEjesSCC = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjEjesSinControl());
		}
	};	
	private JObjBDs pEjes = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjEjes());
		}
	};	
	private JObjBDs pColumnas = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjColumnas());
		}
	};
	private JObjBDs pFilas = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjFilas());
		}
	};
	private JObjBDs pParametros = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjParametros());
		}
	};
	private JObjBDs pInformes = new JObjBDs() {
		public void preset() throws Exception {
			setValue(getObjInformes());
		}
	};	

//  private Object wRecordSet;
  private JRecord recordSource;
  private JRecord recordOwner;
//  private JRecord recordTarget;
  private transient JRelation relation;
  private String idrelation;
  private BizCustomList customListParentSystemProtect;
  private BizCustomList customListParentInforme;
//  private BizCampos ejes;
//  private BizCampos ejesCC;
//  private BizCampos ejesSCC;
//  private BizCampos columnas;
//  private BizCampos filas;
//  private BizCampos campos;
  private BizCampos allCampos;
  private BizCampos filtrosCampos;
  private BizCampos extraFiltros;
//  private BizCampos camposVisibles;
//  private BizCampos filtros;
  private BizFiltroSQLs filtroSQLs;
  private BizInformes objInformes;
  
  private boolean withGeo = false;
//	private JRelations sourceRelations;


//  private JMap<String, String> camposGallery;
//  private JMap<String, String> filterGallery;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public boolean isWithGeo() {
		return withGeo;
	}
	public void setWithGeo(boolean withGeo) {
		this.withGeo = withGeo;
	}
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setModelo(String zValue) throws Exception {    pModelo.setValue(zValue);  }
  public String getModelo() throws Exception {     return pModelo.getValue();  }
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
 public void setSystemProtect(long zValue) throws Exception {    pSystemProtect.setValue(zValue);  }
  public void setNullSystemProtect() throws Exception {    pSystemProtect.setNull();  }
  public long getSystemProtect() throws Exception {     return pSystemProtect.getValue();  }
  public boolean isSystemProtect() throws Exception {     return !(pSystemProtect.isNull() || pSystemProtect.getValue()==0);  }
  public void setListId(long zValue) throws Exception {    pListId.setValue(zValue);  }
  public void setNullListId() throws Exception {    pListId.setNull();  }
  public boolean isNullListId() throws Exception {    return pListId.isNull();  }
  public long getListId() throws Exception {     return pListId.getValue();  }
  public String getRecordOwner() throws Exception {     return pRecordOwner.getValue();  }
  public void setRecordOwner(String zValue) throws Exception {    pRecordOwner.setValue(zValue);  }
  public String getRelId() throws Exception {     return pRelId.getValue();  }
//  public String getWinOwner() throws Exception {     return pWinOwner.getValue();  }
//  public String getRecordSet() throws Exception {     return pRecordSet.getValue();  }
//  public String getIdAction() throws Exception {     return pIdAction.getValue();  }
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
//  public void setRecordSet(String zValue) throws Exception {    pRecordSet.setValue(zValue);  }
//  public String getRecordSet() throws Exception {     return pRecordSet.getValue();  }
  public void setLimite(long zValue) throws Exception {    pLimite.setValue(zValue);  }
  public void setNullLimite() throws Exception {    pLimite.setNull();  }
  public long getLimite() throws Exception {     return pLimite.getValue();  }
  public boolean hasLimite() throws Exception {     return !(pLimite.isNull() || pLimite.getValue()==0);  }

  public void setHoy(Date zValue) throws Exception {    pHoy.setValue(zValue);  }

	public Date getHoy() throws Exception {
		if (pHoy.isNull())
			return BizUsuario.getUsr().todayGMT();
		return pHoy.getValue();
	}
	 public boolean isNullInvisible() throws Exception {
			return pInvisible.isNull();
		}
  public boolean isInvisible() throws Exception {
		return pInvisible.getValue();
	}
	public void setInvisible(boolean zValue) throws Exception {
		pInvisible.setValue(zValue);
	}
  public boolean isWithCC() throws Exception {
		return pWithCC.getValue();
	}
	public void setWithCC(boolean zValue) throws Exception {
		pWithCC.setValue(zValue);
	}

  public boolean isTotalizar() throws Exception {
		return pTotalizar.getValue();
	}
	public void setTotalizar(boolean zValue) throws Exception {
		pTotalizar.setValue(zValue);
	}
  public boolean isSubTotalizar() throws Exception {
		return pSubTotalizar.getValue();
	}
	public void setSubTotalizar(boolean zValue) throws Exception {
		pSubTotalizar.setValue(zValue);
	}
  public boolean isShowTodosLosDatosEnEdicion() throws Exception {
		return pShowTodoEnEdicion.getValue();
	}
	public void setShowTodosLosDatosEdicion(boolean zValue) throws Exception {
		pShowTodoEnEdicion.setValue(zValue);
	}
  public boolean isViewTableInvertido() throws Exception {
		return pViewTable.getValue().equals("I");
	}
  public boolean isViewTable() throws Exception {
		return !pViewTable.getValue().equals("N")||pViewTable.isNull();
	}
	public void setViewTable(boolean zValue) throws Exception {
		pViewTable.setValue(zValue?"S":"N");
	}
//  public void setPosX(long zValue) throws Exception {    pPosX.setValue(zValue);  }
//  public void setNullPosX() throws Exception {    pPosX.setNull();  }
//  public long getPosX() throws Exception {     return pPosX.getValue();  }
//  public boolean isNullPosX() throws Exception {    return pPosX.isNull();  }
//
//  public void setPosY(long zValue) throws Exception {    pPosY.setValue(zValue);  }
//  public void setNullPosY() throws Exception {    pPosY.setNull();  }
//  public long getPosY() throws Exception {     return pPosY.getValue();  }
//  public boolean isNullPosY() throws Exception {    return pPosY.isNull();  }
//
//  public void setWidth(long zValue) throws Exception {    pWidth.setValue(zValue);  }
//  public void setNullWidth() throws Exception {    pWidth.setNull();  }
//  public long getWidth() throws Exception {     return pWidth.getValue();  }
//  public boolean isNullWidth() throws Exception {    return pWidth.isNull();  }
//
//  public void setHeight(long zValue) throws Exception {    pHeight.setValue(zValue);  }
//  public void setNullHeight() throws Exception {    pHeight.setNull();  }
//  public long getHeight() throws Exception {     return pHeight.getValue();  }
//  public boolean isNullHeight() throws Exception {    return pHeight.isNull();  }

  public void setGraph(long zValue) throws Exception {    pGraph.setValue(zValue);  }
  public void setNullGraph() throws Exception {    pGraph.setNull();  }
  public long getGraph() throws Exception {     return pGraph.getValue();  }
  public boolean isNullGraph() throws Exception {    return pGraph.isNull();  }

  public boolean hasLogica() throws Exception {    return !(pAgrupado.isNull() || pAgrupado.getValue().equals(""));  }
  public boolean isOrigenDatos(BizCampo campo) throws Exception {    return campo.isNullOrigenDatos() || (campo.getOrigenDatos().equals(getRelId()));  }

  public void checkLogica() throws Exception {
    if (!hasLogica()) 
  		pAgrupado.setValue(PRESENTATION_TYPE_MATRIX);
  	if (isLista()) return;
  	JIterator<BizCampo> it = getObjAllCampos().getStaticIterator();
  	while(it.hasMoreElements()) {
  		BizCampo campo = it.nextElement();
  		if (!isOrigenDatos(campo)) continue;
  		if (campo.isOnlyLista()) {
  			setAgrupado(BizCustomList.PRESENTATION_TYPE_LIST);
  			return;
  		}
  	}
  }  
  transient JLogicaCustomList logica;
  
  public JLogicaCustomList getLogica() throws Exception {
  	checkLogica();
  	JLogicaCustomList newLogica = logica;
  	if (isDato() && (newLogica==null || !(newLogica instanceof JLogicaCustomListDato)))  newLogica=new JLogicaCustomListDato();
  	else if (isGraphRequiredAgrupado() && (newLogica==null || !(newLogica.isAgrupado())))  newLogica=new JLogicaCustomListAgrupado();
  	else if (isAgrupado() && (newLogica==null || !(newLogica.isAgrupado())))  newLogica=new JLogicaCustomListAgrupado();
  	else if (isMatriz() && (newLogica==null || !(newLogica.isMatriz())))  newLogica=new JLogicaCustomListMatriz();
  	else if (isGrafico() && (newLogica==null || !(newLogica.isMatriz())))  newLogica=new JLogicaCustomListMatriz();
  	else if (isLista() && (newLogica==null || !(newLogica.isRelacional())))  newLogica=new JLogicaCustomListRelacional();
  	else if (newLogica==null)  newLogica=new JLogicaCustomListRelacional();
  	if (logica==null || !newLogica.getClass().equals(logica.getClass())) {
  		logica=newLogica;
//  		allCampos=null;
  		cacheImage=null;
  		analizeRol(logica);
  	}
  	logica.setCustomList(this);
  	return logica;
  }
  public boolean hasRelId() throws Exception { return pRelId.isNotNull();  }
  /**
   * Coornstructor de la Clase
   */
  public BizCustomList() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "list_id", pListId );
    this.addItem( "system_protect", pSystemProtect );
    this.addItem( "invisible", pInvisible );
    this.addItem( "totalizar", pTotalizar );
    this.addItem( "subtotalizar", pSubTotalizar );
    this.addItem( "showfull", pShowTodoEnEdicion );
    this.addItem( "withcc", pWithCC  );
    this.addItem( "description", pDescription );
    this.addItem( "record_owner", pRecordOwner);
    this.addItem( "rel_id", pRelId);
    this.addItem( "agrupado", pAgrupado);
    this.addItem( "distribution", pInfoDistribution);
    this.addItem( "sql", pSQL);
    this.addItem( "chat_bot", pChatBot);
    this.addItem( "clear_bot", pClearBot);
    this.addItem( "hist_bot", pHistoryBot);
    this.addItem( "modelo", pModelo);
    this.addItem( "repo_owner", pOwner);
    this.addItem( "fav", pFav);
  	this.addItem( "listado", pListado);
  	this.addItem( "secciones", pSecciones );
  	this.addItem( "listado_secc", pListadoPorSecc);
  	this.addItem( "filtros", pFiltros);
  	this.addItem( "filtrosrep", pFiltrosReporte);
  	this.addItem( "campos", pCampos);
  	this.addItem( "campos_visibles", pCamposVisibles);
  	this.addItem( "ejes", pEjes);
  	this.addItem( "ejes_cc", pEjesCC);
  	this.addItem( "ejes_scc", pEjesSCC);
  	this.addItem( "columnas", pColumnas);
  	this.addItem( "filas", pFilas);
  	this.addItem( "limite", pLimite);

//  	this.addItem( "pos_x", pPosX);
//  	this.addItem( "pos_y", pPosY);
//  	this.addItem( "height", pWidth);
//  	this.addItem( "width", pHeight);
  	this.addItem( "view_table", pViewTable);
  	this.addItem( "graph", pGraph);
  	this.addItem( "dato", pDato);
  	this.addItem( "image", pImage);

  	
  	this.addItem( "preview", pPreview);
  	this.addItem( "informes", pInformes);
  	this.addItem( "hoy", pHoy);
  	
  	this.addItem( "show_conf", pShowConf);
  	this.addItem( "show_modelo", pShowModelo);
  	this.addItem( "show_tipo", pShowTipo);
  	this.addItem( "show_ia", pShowIA);

 //  	this.addItem( "parametros", pParametros);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "list_id", "List id", false, false, 64 );
    this.addFixedItem( FIELD, "system_protect", "De sistema", true, false, 64); 
    this.addFixedItem( FIELD, "invisible", "Invisible", true, false, 1); 
    this.addFixedItem( FIELD, "description", "Descripción", true, true, 1000); 
    this.addFixedItem( FIELD, "record_owner", "Record Owner", true, false, 250 );
    this.addFixedItem( FIELD, "rel_id", "Fuente de Datos", true, false, 5 );
    this.addFixedItem( FIELD, "agrupado", "Agrupado", true, false, 50 );
    this.addFixedItem( FIELD, "distribution", "Distirbucion", true, false, 50 );
    this.addFixedItem( FIELD, "sql", "sql", true, false, 2000 );
    this.addFixedItem( FIELD, "repo_owner", "Dueño", true, false, 50 );
     this.addFixedItem( VIRTUAL, "show_tipo", "show_tipo", true, false, 1 );
    this.addFixedItem( VIRTUAL, "show_modelo", "show_modelo", true, false, 1 );
    this.addFixedItem( VIRTUAL, "show_tipo", "show_tipo", true, false, 1 );
    this.addFixedItem( VIRTUAL, "show_ia", "show_ia", true, false, 1 );

//    this.addFixedItem( FIELD, "pos_x", "X", true, false, 18 );
//    this.addFixedItem( FIELD, "pos_y", "Y", true, false, 18 );
//    this.addFixedItem( FIELD, "width", "W", true, false, 18 );
//    this.addFixedItem( FIELD, "height", "H", true, false, 18 );
    this.addFixedItem( FIELD, "graph", "presntacion", true, false, 18 );
    this.addFixedItem( FIELD, "totalizar", "totalizar?", true, false, 1 );
    this.addFixedItem( FIELD, "subtotalizar", "sub-totalizar?", true, false, 1 );
    this.addFixedItem( VIRTUAL, "showfull", "show full?", true, false, 1 );
    this.addFixedItem( FIELD, "view_table", "ver datos?", true, false, 1 );
    
    this.addFixedItem( FIELD, "limite", "Limite", true, false, 18 );
    this.addFixedItem( FIELD, "modelo", "Modelo impresion", true, false, 50 );
    this.addFixedItem( VIRTUAL, "fav", "Favorito", true, false, 1 );
    this.addFixedItem( VIRTUAL, "withcc", "Corte control", true, false, 1 );
    this.addFixedItem( RECORDS, "listado", "Listado", true, false, 0 ).setClase(BizDynamic.class);
    this.addFixedItem( RECORDS, "secciones", "Secciones", true, false, 0 ).setClase(BizVirtual.class);
    this.addFixedItem( RECORDS, "listado_secc", "Listado por seccion", true, false, 0 ).setClase(BizDynamic.class);
    this.addFixedItem( RECORDS, "campos", "Campos", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "filtros", "Filtros", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "filtrosrep", "Filtros reportes", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "campos_visibles", "Campos Visibles", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "ejes", "Ejes", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "ejes_cc", "Ejes con cortes control", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "ejes_scc", "Ejes sin cortes control", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "columnas", "Columnas", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "filas", "Filas", true, false, 0 ).setClase(BizCampo.class);
    this.addFixedItem( RECORDS, "informes", "informes", true, false, 0 ).setClase(BizInforme.class);
    this.addFixedItem( VIRTUAL, "preview", "preview", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "dato", "dato", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "image", "imagen", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "chat_bot", "chat_bot", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "clear_bot", "clear_bot", true, false, 1 );
    this.addFixedItem( VIRTUAL, "hist_bot", "Historia", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "hoy", "hoy", true, false, 18 );
    //    this.addFixedItem( RECORDS, "parametros", "Parametros", true, false, 0 ).setClase(BizDocListadoParam.class);
 }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "LST_CUSTOM_LISTV2"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public BizInformes getInformes() throws Exception {
		BizInformes g=new BizInformes();
  	g.addFilter("company", this.getCompany());
  	g.addFilter("list_id", this.getListId());
  	g.setObjParentCustomList(this);
		return g;
	}

  public boolean getShowTipo() throws Exception { 
  	return pShowTipo.getValue(); 
  } 	
  public boolean getShowModelo() throws Exception { 
  	return pShowModelo.getValue(); 
  } 	
  public String getChatBot() throws Exception { 
  	return pChatBot.getValue(); 
  } 
  public void setChatBot(String value) throws Exception { 
  	pChatBot.setValue(value); 
 }
  public void setClearBot(boolean value) throws Exception { 
  	pClearBot.setValue(value); 
 } 
  public void setHistoryBot(String value) throws Exception { 
  	pHistoryBot.setValue(value); 
 } 
  
  public String getHistoryBot() throws Exception { 
  	return pHistoryBot.getValue(); 
  } 
  public boolean getClearBot() throws Exception { 
  	return pClearBot.getValue(); 
  } 
 
  public boolean getShowConf() throws Exception { 
  	return pShowConf.getValue(); 
  } 
  public boolean getShowIA() throws Exception { 
  	return pShowIA.getValue(); 
  } 
  public void setShowConf(boolean value) throws Exception { 
  	 pShowConf.setValue(value); 
  } 
  public void setShowModelo(boolean value) throws Exception { 
 	 pShowModelo.setValue(value); 
  }
  public void setShowTipo(boolean value) throws Exception { 
 	 pShowTipo.setValue(value); 
  }
  public void setShowIA(boolean value) throws Exception { 
 	 pShowIA.setValue(value); 
 } 
  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zListId ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "list_id",  zListId ); 
    return read(); 
  } 
  public boolean readRecords() throws Exception { 
  	pCampos.getValue();
  	return read(); 
  } 	
  public boolean read( String zCompany, String zListId, JFilterMap param ) throws Exception { 
  	dontThrowException(true);
    addFilter( "company",  zCompany ); 
    addFilter( "list_id",  Long.parseLong(zListId) ); 
    return read(); 
  } 
  public boolean read( long zListId ) throws Exception { 
  	dontThrowException(true);
    addFilter( "list_id",  zListId); 
    return read(); 
  } 
  public BizCampos getObjEjes() throws Exception {
  	BizCampos r = this.getEjes();
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!c.isRolEje())
  			it.remove();
  	}
  	return r;
  } 


  Boolean hasControl;
  public boolean hasEjesControl() throws Exception {
  	if (this.hasControl!=null) return hasControl;
  	JRecords<BizCampo> r = this.getEjesConCorteControl();
  	r.toStatic();
   	this.hasControl=r.getStaticItems().size()==0;
  	return hasControl;
  } 
  public BizCampos getObjEjesControl() throws Exception {
  	BizCampos r = this.getEjesConCorteControl();
  	r.toStatic();
  	if (r.getStaticItems().size()==0)
  		r=getObjColumnas();
  	return r;
  } 
  
  public BizCampos getObjEjesSinControl() throws Exception {
  	BizCampos r;
  	if (hasEjesControl())
  		r=getObjFilas();
  	else {
  		r = this.getEjesSinCorteControl();
    	r.toStatic();
  	}
  	return r;
  } 
 
  	
  public BizCampos getObjColumnas() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos columnas = new BizCampos();
  	columnas.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.isRolColumna())
  			columnas.getStaticItems().addElement(c);
  	}
  	return columnas;
  }
  
  public BizCampos getObjFilas() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos filas = new BizCampos();
  	filas.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.isRolFila())
  			filas.getStaticItems().addElement(c);
  	}
  	return filas;
  } 
  public boolean sinCategoria() throws Exception {
  	return getGraphCategoria()==null;
  } 
  public BizCampo getGraphCategoria() throws Exception {
  	return getFirstFila();
  } 
  public BizCampo getGraphDataset() throws Exception {
  	return getFirstColumna();
  } 
  public BizCampos getGraphValor() throws Exception {
  	return getObjCampos();
  } 
  public BizCampo getFirstColumna() throws Exception {
  	JRecords<BizCampo> r = this.getObjColumnas();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
  } 
  public BizCampo getFirstFilaNoDate() throws Exception {
  	JRecords<BizCampo> r = this.getObjFilas();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo campo = it.nextElement();
  		if (campo.getTipoCampo().equalsIgnoreCase("JDATE")) continue;
  		return campo;
 	}
  	return null;
  } 
  public BizCampo getFirstFila() throws Exception {
  	JRecords<BizCampo> r = this.getObjFilas();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
  } 
  public BizCampo getLastFila() throws Exception {
  	JRecords<BizCampo> r = this.getObjFilas();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	BizCampo last = null;
  	while (it.hasMoreElements()) {
    	BizCampo f = it.nextElement();
   // 	if (f.hasOrdenAscDesc()) continue;
    	last=f;
  	}
		return last;
  } 
  public BizCampo getCampoDataset() throws Exception {
  	JRecords<BizCampo> r = this.getObjAllCampos();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (c.isOnlyFiltro()) continue;
  		if (!isOrigenDatos(c)) continue;
  		if (!(c.isDateInput() || c.isDateTimeInput())) continue;
  		return c;
  	}
  	return null;
  } 
  public BizCampo getFirstCampo() throws Exception {
  	JRecords<BizCampo> r = this.getObjCampos();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
  } 
  public BizCampo getFirstCampoNumerico() throws Exception {
  	JRecords<BizCampo> r = this.getObjCampos();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	while (it.hasMoreElements()){
    	BizCampo c=it.nextElement();
    	if (c.isNumeric())
    		return c;
  	}
		return null;
  } 
  public BizCampos getObjAllExtraFiltros() throws Exception {
  	if (this.extraFiltros!=null) return this.extraFiltros;
  	BizCampos r = new BizCampos();
  	r.setObjCustomList(this);
  	return (this.extraFiltros=r);
  }
  public BizCampos getObjFiltrosReporte() throws Exception {
  	if (this.filtrosCampos!=null) return this.filtrosCampos;
  	JMap<String,BizCampo> camposAdded = JCollectionFactory.createMap();
  	
  	BizCampos r =new BizCampos();
  	r.setStatic(true);
  	r.setObjCustomList(this);
  	
  	JIterator<BizCampo> itc = getObjAllCampos().getStaticIterator();
  	while (itc.hasMoreElements()){
  		BizCampo campo = itc.nextElement();
  		if (!isOrigenDatos(campo)) continue;
			r.getStaticItems().addElement(campo);
			camposAdded.addElement(campo.getCampo(), campo);
  	}

  	JIterator<BizInforme> it = getObjInformes().getStaticIterator();
  	
  	while (it.hasMoreElements()){
  		BizInforme inf = it.nextElement();
  	  itc = inf.getObjCustomList().getObjFiltrosHijos().getStaticIterator();
  		while (itc.hasMoreElements()) {
  			BizCampo campo = itc.nextElement();
  			BizCampo otherCampo = camposAdded.getElement(campo.getCampo());
  			if (otherCampo==null) {
    			r.getStaticItems().addElement(campo);
    			camposAdded.addElement(campo.getCampo(), campo);
  			} else if (!otherCampo.getOperador().equals(campo.getOperador())){
  				if (otherCampo.isDateInput()) {
  					otherCampo.setOperador(BizFuncion.FUNTION_INTERVALO);
  				} else if (otherCampo.isCheckInput()) {
  					
  				} else {
  					otherCampo.setOperador("in");
  				}
  			}
  		}
  	}

  	
  	
  	return (this.filtrosCampos=r);
  }
  
  public BizCampos getObjAllCampos() throws Exception {
  	if (this.allCampos!=null) return this.allCampos;

  	BizCampos r =new BizCampos();
  	r.setStatic(true);
  	r.setObjCustomList(this);
  	
  	JIterator<BizCampo> itc = getCampos().getStaticIterator();
  	while (itc.hasMoreElements()){
  		BizCampo campo = itc.nextElement();
  		if (!isOrigenDatos(campo)) continue;
  		if (campo.isDependiente())
  			continue;
  		if (!campo.hasFiltro())
  			campo.setNullValues();
  		if (campo.isNullOrigenDatos())
  			campo.setOrigenDatos(getRelId()); // por migracion
			r.getStaticItems().addElement(campo);
  	}
  	return (this.allCampos=r);
  }
  public BizCampo findCampo(String campo) throws Exception {
  	JRecords<BizCampo> r = this.getObjAllCampos();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (c.getNameField().equals(campo)) return c;
  	}
  	return null;
  } 
  public BizCampo findCampoByField(String campo) throws Exception {
  	JRecords<BizCampo> r = this.getObjAllCampos();
  	JIterator<BizCampo> it=r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (c.getField().equals(campo)) return c;
  	}
  	return null;
  } 
  public void removeInforme(BizInforme informe) throws Exception {
  	JRecords<BizInforme> r = this.getObjInformes();
  	if (r!=null) r.removeStaticItem(informe);

  }
  public void removeCampo(BizCampo campo) throws Exception {
  	JRecords<BizCampo> r = this.getObjAllCampos();
  	r.removeStaticItem(campo);
  } 
  
  public void deleteCampo(BizCampo campo) throws Exception {
    this.getObjAllCampos().getStaticItems().removeElement(campo);
  } 


  public BizCampos getObjCampos() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos campos = new BizCampos();
  	campos.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;

  		if (c.isRolCampo())
  			campos.getStaticItems().addElement(c);
  	}
  	return campos;
  }
  public JRecords<BizVirtual> getObjCamposAutoRef() throws Exception {
  	JRecords<BizVirtual> lista = new JRecords<BizVirtual>(BizVirtual.class);
  	lista.setStatic(true);
  	BizCampos r = this.getObjAllCampos();
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;

  		if (c.isRolCampo())
  			lista.getStaticItems().addElement(JRecord.virtualBD(""+c.getNameField(),c.getDescrCampo(),5060));
  	}
  	return lista;
  }
  public BizCampos getObjOrders() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos campos = new BizCampos();
  	campos.setStatic(true);
  	TreeMap<Long,BizCampo> orden= new TreeMap<Long, BizCampo>();
  	
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
 // 		if (!c.isRolCampo()) continue;
  		if (c.hasOrdenAscDesc())
  			orden.put(c.getOrdenOrden(),c);
  	}
  	
  	for(BizCampo o:orden.values()) {
  		campos.getStaticItems().addElement(o);
  	}
  	return campos;
  }
  public BizCampos getEjesConCorteControl() throws Exception {
   	BizCampos r = this.getObjAllCampos();
  	BizCampos campos = new BizCampos();
  	campos.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.isCorteControl())
  			campos.getStaticItems().addElement(c);
  	}
  	return campos;
  }
  public BizCampos getEjesSinCorteControl() throws Exception {
   	BizCampos r = this.getObjAllCampos();
  	BizCampos campos = new BizCampos();
  	campos.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (!c.isCorteControl())
  			campos.getStaticItems().addElement(c);
  	}
  	return campos;
  }

  
  public BizCampos getCampos() throws Exception {
  	BizCampos r = new BizCampos();
  	r.setObjCustomList(this);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("list_id", this.getListId());
  	r.addOrderBy("orden");
//  	r.readAll();
  	return r;
  }
  public BizCampos getEjes() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos ejes = new BizCampos();
  	ejes.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.isRolEje())
  			ejes.getStaticItems().addElement(c);
  	}
  	return ejes;
  }
  public BizCampos getCamposVisibles() throws Exception {
  	BizCampos r = this.getObjCampos();
  	BizCampos visibles = new BizCampos();
  	visibles.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.getVisible())
  			visibles.getStaticItems().addElement(c);
  	}
  	return visibles;
  }
  public BizCampos getCamposAlfabetico() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos visibles = new BizCampos();
  	visibles.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
			visibles.getStaticItems().addElement(c);
  	}
  	visibles.Ordenar("campo");
  	return visibles;
  }
  public BizCampos getGeoCampos() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos visibles = new BizCampos();
  	visibles.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.hasGeoCampo())
  			visibles.getStaticItems().addElement(c);
  	}
  	return visibles;
  }
  public BizCampos getGeoEjes() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos visibles = new BizCampos();
  	visibles.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (!c.isRolEje())
  			continue;
  		if (c.hasGeoCampo())
  			visibles.getStaticItems().addElement(c);
  	}
  	return visibles;
  }

  public BizCampo getCampo(int orden) throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (c.getOrden()==orden)
  			return c;
  	}
  	return null;
  }

  public JRecords<BizCampo> getCamposHijos(BizCampo parent) throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos filtros = new BizCampos();
  	filtros.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
//  		if (!c.hasFiltro())
//  			continue;
  		if (!isOrigenDatos(c)) continue;
  		if (c.getObjFiltroParent()!=parent) {
  			if (!c.isDependiente()) continue;
	  		if (!c.getOrdenPadre().equals(""+parent.getOrden()))
	    			continue;
  		}
 			filtros.getStaticItems().addElement(c);
  	}
  	return filtros;
	}
  public BizCampos getObjFiltrosHijos() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	BizCampos filtros = new BizCampos();
  	filtros.setStatic(true);
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.isOcultarReporte()) continue;
  //		if (!c.isOnlyFiltro() && !c.hasFiltro()) continue;
  		
  		filtros.getStaticItems().addElement(c);
  	}
  	return filtros;
  }
  public BizCampos getObjFiltros() throws Exception {
   	Map<String,BizCampo> fields= new TreeMap<String,BizCampo>();
    	BizCampos r = this.getObjAllCampos();
  	BizCampos filtros = new BizCampos();
  	filtros.setStatic(true);
   	if (extraFiltros!=null) {// estos filtros vienen del informe padre
    	JIterator<BizCampo> itEF = extraFiltros.getStaticIterator();
    	while (itEF.hasMoreElements()) {
    		BizCampo c = itEF.nextElement();
    		if (!c.isFiltroActivo()) continue;
    		if (!c.getRecordSource().equals(this.getObjRelation().getClassTarget().getCanonicalName()))
    			continue;
     		fields.put(c.getCampo(),c);
    		filtros.getStaticItems().addElement(c);
    	}
  	}
  	
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
//  		if (!c.hasFiltro())
//  			continue;
//  		if (c.getOperador()==null)
//  			continue;
  		if (!isOrigenDatos(c)) continue;
  		if (c.isDependiente()) continue;
  		if (!c.isFiltroActivo()) continue;
  		if (fields.get(c.getCampo())!=null)
    		break;
  		filtros.getStaticItems().addElement(c);
  	}
 
  	return filtros;

  }
  public BizFiltroSQLs getObjFiltroSQLs() throws Exception {
  	if (this.filtroSQLs!=null) return this.filtroSQLs;
  	BizFiltroSQLs r = this.getFiltroSQLs();
   	r.setObjCustomList(this);
    r.toStatic();
  	return (this.filtroSQLs=r);
  }


  public BizFiltroSQLs getFiltroSQLs() throws Exception {
  	BizFiltroSQLs r = new BizFiltroSQLs();
  	r.setObjCustomList(this);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("list_id", this.getListId());
  	r.addOrderBy("orden");
  	return r;
  }




//  public JRecords<BizGraph> getGraficos() throws Exception {
//  	JRecords<BizGraph> r = new JRecords<BizGraph>(BizGraph.class);
//  	r.addFilter("company", this.getCompany());
//  	r.addFilter("list_id", this.getListId());
//  	r.readAll();
//  	return r;
//  }
  public JRecords<BizCustomListOwner> getPermisos() throws Exception {
  	JRecords<BizCustomListOwner> r = new JRecords<BizCustomListOwner>(BizCustomListOwner.class);
  	r.addFilter("company", this.getCompany());
  	r.addFilter("list_id", this.getListId());
  	r.readAll();
  	return r;
  }
  public BizGraph getGrafico(int number) throws Exception {
//  	JRecords<BizGraph> grs = getGraficos();
//  	grs.toStatic();
//  	grs.firstRecord();
//  	int i=1;
//  	while (grs.nextRecord()) {
//  		BizGraph g = grs.getRecord();
//  		if (i==number) return g;
//  		i++;
//  	}
//  	
  	return buildGraph();
  }
	public String imprimirResumen(JFilterMap a, boolean html) throws Exception {
			String tempfile ;
			tempfile =JTools.getUniqueFileName(JPath.PssPathTempFiles(),html?"html":"pdf");
			JTools.MakeDirectory(JPath.PssPathTempFiles());
			File f = new File(tempfile);
			GuiCustomList l = new GuiCustomListInforme();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			if (html) 
				JTools.writeStringToFile(l.getHtmlView(26,"htmlfull",a,false,true,false,false),tempfile);
			else
				JTools.convertHtml2JPGorPDF(new ByteArrayInputStream(l.getHtmlView(26,"htmlfull",a,true,false,false,false).getBytes()), tempfile);
			return f.getName();
//		if (isInforme()||html) {
	//  	JIterator<BizInforme> it = getObjInformes().getStaticIterator();
	//		String[] files = new String[(int)( getObjInformes().sizeStaticElements()*2)+1];
	//  	int i=0;
	//  	while (it.hasMoreElements()){
	//  		BizInforme inf = it.nextElement();
	//  		String f = inf.getObjCustomList().imprimirResumen(a, html);
	//			if (f==null) continue;
	//			files[i++] = JPath.PssPathTempFiles() + "/" +f;
	//  	
	//  	}
	//		if (i==0) return null;
	//		if (html) {
	//			String tempfile = getCompany() + "/concat" + this.hashCode() + ".html";
	//			JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +  BizUsuario.getUsr().getCompany());
	//			JConcatenar.concatenarHTML(files, JPath.PssPathTempFiles() + "/" + tempfile);
	//			return tempfile;
	//			
	//		} else {
	//			String tempfile = getCompany() + "/concat" + this.hashCode() + ".pdf";
	//			JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +  BizUsuario.getUsr().getCompany());
	//			JConcatenar.concatenarPDF(files, JPath.PssPathTempFiles() + "/" + tempfile);
	//			return tempfile;
	//		}

//		}
//		String tempfile ;
//		tempfile =BizUsuario.getUsr().getObjBusiness().getModuloBizLayout().generateListadoTemp(true,this,a,getCompany(), getModelo(),html);
//		return tempfile;
	}
	 

  public BizInformes getObjInformes() throws Exception {
  	if (this.objInformes!=null) return this.objInformes;
  	BizInformes r =new BizInformes();
  	r.addFilter("company", getCompany());
  	r.addFilter("list_id", getListId());
  	r.setParent(this);
  	r.setObjParentCustomList(this);
//  	r.toStatic();
  	return (this.objInformes=r);
  }
  public BizInformes getObjInformesPropios() throws Exception {
  	BizInformes r =new BizInformes();
  	r.addFilter("company", getCompany());
  	r.addFilter("list_id", getListId());
  	r.setParent(this);
  	r.setObjParentCustomList(this);
  	return r;
  }


  public void processDelete() throws Exception {
  	this.getCampos().processDeleteAll();
  	this.getObjInformesPropios().processDeleteAll();
  	this.getPermisos().processDeleteAll();
  	super.processDelete();
  	
//		BizCompany.getCompany(getCompany()).getObjBusiness().getCarpeta().GetcDato().processCompletarCarpetas();

  }
  
  public void fillDeletedCampos() throws Exception {
  	JRecords<BizCampo> campos = new JRecords<BizCampo>(BizCampo.class);
  	campos.addFilter("list_id", getListId());
  	campos.addFilter("company", getCompany());
  	JIterator<BizCampo> it = campos.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo campo = it.nextElement();
  		JIterator<BizCampo> itC=this.getObjAllCampos().getStaticIterator();
  		boolean find = false;
  		while(itC.hasMoreElements()) {
  			BizCampo det = itC.nextElement();
  			if (det.getSecuencia()==campo.getSecuencia()) {
  				find = true;
  				break;
  			};
  		}
  		if (find) continue;
  		removeCampo(campo);
  		
  	}

  }
  public void addDetailToCampos() throws Exception {
  	JList <BizCampo> ors = JCollectionFactory.createList();
		JIterator<BizCampo> it=this.getObjAllCampos().getStaticIterator();
		while(it.hasMoreElements()) {
			BizCampo det = it.nextElement();
			if (!det.isDependiente() && det.isGroup())
				ors.addElement(det);
		}
		
		JIterator<BizCampo> itc=ors.getIterator();
		while (itc.hasMoreElements()) {
			BizCampo det = itc.nextElement();
			det.addDetailToCampos(this);
		}
  }
  
  public void processInsert() throws Exception {
  	if (this.pAgrupado.isNull())
  		this.pAgrupado.setValue("M");
  	if (pInvisible.isNull())
  		pInvisible.setValue(false);
  	if (pOwner.isNull())
  		pOwner.setValue(BizUsuario.getUsr().GetUsuario());
  
		if (this.pModelo.isNull()) {
			BizPlantilla pl = BizCompany.getObjPlantilla(getCompany(), "sys_v2_reporte");
			if (pl!=null) 
				this.pModelo.setValue(""+pl.getId());
		}
		if (!isGrafico())
			setViewTable(true);
		addDetailToCampos();
		super.processInsert();
  	setListId(getIdentity("list_id"));

  	if (isInforme()) {
     	JRecords<BizInforme> detallesI = this.pInformes.getRawValue();
     	if (detallesI!=null)
     		detallesI.processProcessTable(this,true);
  	} 
  	JRecords<BizCampo> detalles = this.pCampos.getRawValue();
   	if (detalles!=null)
   		detalles.processProcessTable(this,true);
  	
   	if (!BizUsuario.getUsr().GetUsuario().equals("") && !BizUsuario.getUsr().GetUsuario().equals("ADMIN")) {
			BizCustomListOwner owner = new BizCustomListOwner();
			owner.setCompany(getCompany());
			owner.setListId(getListId());
			owner.setUsuario(BizUsuario.getUsr().GetUsuario());
			owner.processInsert();
   	}

	
  }

  
  public void addCampoCantidad() throws Exception {
  	BizCampo campo = new BizCampo(); 
  	campo.setCompany(getCompany());
  	campo.setListId(getListId());
  	campo.setObjCustomList(this);
  	String id =  getObjRelation().serializeDeep()+ "|COUNT|COUNT";
  	BizCampoGallery campoGallery = (BizCampoGallery)getAllCampos("", true).findInHash("campo_function",id);
  	
  	campo.setCampo(BizField.FUNTION_COUNT);
  	campo.setFuncion(BizCampo.FUNTION_COUNT);
  	campo.setIdGallery(campoGallery.getId());
  	campo.processInsert();
  }
  @Override
	public void processUpdate() throws Exception {
		if (this.pModelo.isNull()) {
			BizPlantilla pl = BizCompany.getObjPlantilla(getCompany(), "sys_v2_reporte");
			if (pl!=null) 
				this.pModelo.setValue(""+pl.getId());
		}
  	if (pOwner.isNull())
  		pOwner.setValue(BizUsuario.getUsr().GetUsuario());
  
  	if (pInvisible.isNull())
  		pInvisible.setValue(false);
		if (!isGrafico())
			setViewTable(true);
		addDetailToCampos();
		fillDeletedCampos();

		super.processUpdate();
		
		if (isInforme()) {
	   	JRecords<BizInforme> detallesI = this.pInformes.getRawValue();
	   	if (detallesI!=null)
	   		detallesI.processProcessTable(this,true);
			
		} 
  	JRecords<BizCampo> detalles = this.pCampos.getRawValue();
   	if (detalles!=null)
   		detalles.processProcessTable(this,true);
  	
		
//			BizCompany.getCompany(getCompany()).getObjBusiness().getCarpeta().GetcDato().processCompletarCarpetas();

	}

	public void cleanFiltrossCampos() throws Exception {
		filtrosCampos=null;
	}
	
	public void clean() throws Exception {
		customListParentSystemProtect=null;
		customListParentInforme=null;
		recordOwner=null;
		relation=null;
		idrelation=null;
		objOrden=null;
//		allCampos=null;
//		campos=null;
//		ejes=null;
//		filtros=null;
//		orders=null;
//		ejesCC=null;
//		ejesSCC=null;
//		orders=null;
//		columnas=null;
//		camposVisibles=null;
		filtroSQLs=null;
		logica=null;
//		objInformes=null;
	} 

  public BizCustomList getObjCustomListParentSystemProtect() throws Exception {
  	if (this.customListParentSystemProtect!=null) return this.customListParentSystemProtect;
  	BizCustomList r = new BizCustomList();
  	r.read( this.pSystemProtect.getValue());
  	return (this.customListParentSystemProtect=r);
  }

  public boolean isFav() throws Exception {
  	BizCustomListFav f = new BizCustomListFav();
  	f.dontThrowException(true);
  	f.addFilter("company", getCompany());
  	f.addFilter("list_id", getListId());
  	f.addFilter("usuario", BizUsuario.getUsr().GetUsuario());
  	return f.exists();
  }

  public boolean isPublico() throws Exception {
  	BizCustomListOwner f = new BizCustomListOwner();
  	f.dontThrowException(true);
  	f.addFilter("company", getCompany());
  	f.addFilter("list_id", getListId());
  	return !f.exists();
  }
	public void processHacerPublico() throws Exception {
  	BizCustomListOwner f = new BizCustomListOwner();
  	f.dontThrowException(true);
  	f.addFilter("company", getCompany());
  	f.addFilter("list_id", getListId());
  	f.deleteMultiple();
	}
	public void execHacerPublico() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processHacerPublico();
			}
		};
		oExec.execute();
	}
	public void execMarcarComoFavorito() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processMarcarComoFavorito();
			}
		};
		oExec.execute();
	}
	
	public void execProcessRecalcule() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processRecalcule();
			}
		};
		oExec.execute();
	}

	public void execProcessRemigrarOR() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processRemigrarOR();
			}
		};
		oExec.execute();
	}
//	public void processMarcarDesmarcarComoFavorito() throws Exception {
//		if (this.isFav())
//			this.processDesmarcarComoFavorito();
//		else 
//			this.processMarcarComoFavorito();
//	}
//
	public void processMarcarComoFavorito() throws Exception {
		BizCustomListFav f = new BizCustomListFav();
		f.setCompany(getCompany());
		f.setListId(getListId());
		f.setUsuario(BizUsuario.getUsr().GetUsuario());
		f.processInsert();
	}
	
	public void execDesmarcarComoFavorito() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processDesmarcarComoFavorito();
			}
		};
		oExec.execute();
	}
	public void processDesmarcarComoFavorito() throws Exception {
		BizCustomListFav f = new BizCustomListFav();
		f.dontThrowException(true);
		if (!f.read(getCompany(), getListId(), BizUsuario.getUsr().GetUsuario()))
			return;
		f.processDelete();
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
	public void execConvertirAListado() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processConvertidorAListado();
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

	public void processRecalcule() throws Exception {
		JIterator<BizCampo> itC = getObjAllCampos().getStaticIterator();
		while (itC.hasMoreElements()) {
			BizCampo c = itC.nextElement();
  		if (!isOrigenDatos(c)) continue;
  		if (c.getCampo().equals("")) continue;
			c.processCalculeStadistic();
		}
	}

	public void clearChat() throws Exception {
		setClearBot(true);
		setHistoryBot("");
	}
	public void processChat() throws Exception {
		Report report = new Report();
		report.setType(isMatriz()?"matrix":isAgrupado()?"agrupado":isDato()?"dato":"lista");
		List<Command> listCommand = new ArrayList<Command>();
		report.setListCommand(listCommand);
		BizCampos campos = this.getObjAllCampos();
		JIterator<BizCampo> it = campos.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo campo = it.nextElement();
			Command c = new Command();
			c.setAccion("agregar");
			c.setField(campo.getDescrCampo());
			c.setFunction(campo.getFuncion());
			c.setPorcentaje(campo.isPorcentaje()?"si":"no");
			c.setObject(campo.isColumna()?"columna":campo.isFila()?"fila":campo.isFilter()?"filter":"campo");
			c.setOperator(campo.getOperador());
			if (campo.hasValor2()) {
				c.setValueFrom(campo.getValor());
				c.setValueTo(campo.getValor2());
			} else {
				c.setValue(campo.getValor());
			}
			listCommand.add(c);
		}
		
    ObjectMapper objectMapper = new ObjectMapper();
    String entorno = "";

    if (campos.getStaticItems().size()==0) {
    	entorno = 							"            La Salida es un json como por ejemplo dada la pregunta: para la grilla de totalizar los vuelos por aeropuesrtos de origen y destino en 2014, las sigueintes instrucciones:" + 
					"             report=[ " + 
					"                type=grilla," + 
					"                list_commnad=[" + 
					"                    [accion=agregar, object=columna, field='Boleto, Aeropuertos, A.Destino, Cod.Aeropuerto Destino'] (agrega columna con aeropuerto destino)" + 
					"                    [accion=agregar, object=fila, field='Boleto, Aeropuertos, A.Origen, Cod.Aeropuerto Origen'] (agrega fila con aeropuerto origen)" + 
					"                    [accion=agregar, object=campo, orden=1, sentido_orden='ascendente',field='Boleto, Tarifas, Tarifa (Moneda local), Tarifa facturada (local)', function=SUM, porcentaje=no] (agrega campo acumulado de tarifa)" + 
					"                    [accion=agregar, object=filtro, field='Boleto, Fechas, Fecha Salida',operator=in,value_to=01/01/2014,value_from=31/12/2014] (agrega filtro en el año 2014)" + 
					"                ]" + 
					"            ]";

    } else {
    	entorno = " El estado actual del reporte es : " +  objectMapper.writeValueAsString(report);
	
    }

		JRecords<BizCampoGallery> recs = getOnlyCampos(this, null, true, true);
		String scampos = "";
		JIterator<BizCampoGallery> itc = recs.getStaticIterator();
		while (itc.hasMoreElements()) {
			BizCampoGallery campo = itc.nextElement();
			scampos+= " '"+campo.getDescrCompleta()+"';";
		}
		String chatSpec =  getObjRelation().getObjRecordTarget().getRelationMap().getChatSpec();
		
		processChat( ChatGPT.chatListCustom(getCompany(),BizUsuario.getUsr().GetUsuario(), getChatBot(),scampos, entorno, chatSpec,getClearBot()));
		setClearBot(false);
	}
	
	public void processRemigrarOR() throws Exception {
		JMap<Long,Long> equivCampos = JCollectionFactory.createMap();
		JMap<Long,Long> equivCamposV2 = JCollectionFactory.createMap();

//		BizCustomListLegacy leg = new BizCustomListLegacy();
//		leg.dontThrowException(true);
//		if (!leg.readByDescr(this.getCompany(), this.getDescripcion())) return;

		BizCampos ampls = this.getObjAllCampos();
		JIterator<BizCampo> it = ampls.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo ampl= it.nextElement();
			if (ampl.getSecuenciaOld()==0) continue;
			
			equivCampos.addElement(ampl.getSecuencia(), ampl.getOrden());
			}
		JIterator<BizCampo> it2 = this.getObjAllCampos().getStaticIterator();
		while (it2.hasMoreElements()) {
			BizCampo ampl= it2.nextElement();
			if (!ampl.isDependiente()) continue;
		
				if (equivCampos.getElement(Long.parseLong(ampl.getOrdenPadre()))==null)
				continue;
				if (equivCampos.getElement(Long.parseLong(ampl.getOrdenPadre()))==0)
				continue;
				ampl.setOrdenPadre(""+equivCampos.getElement(Long.parseLong(ampl.getOrdenPadre())));
		
			ampl.update();
		}
	}
	public void processReordenar(BizCampo moveCampo,long toPos,boolean ascendente) throws Exception {
	 	BizCampos newOrden = null;
	  if (toPos!=-1) {
			newOrden = processReordenarToPos(moveCampo, toPos, ascendente);
	  } else {
	  	if (ascendente) {
				newOrden = processReordenarAscendente(moveCampo);
			} else {
				newOrden = processReordenarDescendente(moveCampo);
			}
	  }
	  setObjAllCampos(newOrden,false);
	}
	
	public void execProcessEliminarRepetidos() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processEliminarRepetidos();
			}
		};
		oExec.execute();
	}


	public void processEliminarRepetidos() throws Exception {
		JRecords<BizCampo> campos = getCampos();
		JMap<String,BizCampo> backupCampos = JCollectionFactory.createMap();
		JIterator<BizCampo> it = campos.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo c = it.nextElement();
			if (backupCampos.getElement(c.getDescrCampo()+"_"+c.getOperador()+"_"+c.getFuncion()+"_"+c.getOperacion()+"_"+c.getValor())!=null) {
				c.delete();
			}
			backupCampos.addElement(c.getDescrCampo()+"_"+c.getOperador()+"_"+c.getFuncion()+"_"+c.getOperacion()+"_"+c.getValor(),c);
			
		}
	}
	
	public void setObjAllCampos(BizCampos origCampos,boolean cleanKeys) throws Exception {
		this.allCampos=new BizCampos();
		pCampos.setValue(this.allCampos);
		JIterator<BizCampo> it = origCampos.getStaticIterator();
		this.allCampos.setStatic(true);
		while (it.hasMoreElements()) {
			BizCampo origCampo = it.nextElement();
			BizCampo campo = new BizCampo();
			campo.copyProperties(origCampo);
			
			campo.setObjCustomList(this);
			if (cleanKeys) {
				campo.setCompany(getCompany());
				if (getListId()==0)
					campo.setNullToListId();
				else	
					campo.setListId(getListId());
				campo.setNullToSecuencia();
			}
			this.allCampos.addItem(campo);
		}

		
	}
	public void updateOrderToTop(BizCampo moveCampo) throws Exception {
	  long orden =1;
	  if (moveCampo==null) {
			JIterator<BizCampo> itC = this.getObjAllCampos().getStaticIterator();
			while (itC.hasMoreElements()) {
					BizCampo c = itC.nextElement();
		  		if (!isOrigenDatos(c)) continue;
					if (!c.hasOrdenAscDesc()) {
						c.setOrdenOrden(0);
						continue;
					}
					if (c.getOrdenOrden()!=0) continue;
					moveCampo=c;
					break;
			}
	  }
	  if (moveCampo!=null) {
	  	orden++;
	  }
		JIterator<BizCampo> itC = this.getObjAllCampos().getStaticIterator();
		while (itC.hasMoreElements()) {
				BizCampo c = itC.nextElement();
	  		if (!isOrigenDatos(c)) continue;
				if (moveCampo!=null) {
					if (c.hasSecuencia() && moveCampo.hasSecuencia()) {
						if (c.getSecuencia()==moveCampo.getSecuencia()) {
					  	c.setOrdenOrden(1);
							continue;
						}
					} else {
						if (c.isSimilar(moveCampo))  {
					  	c.setOrdenOrden(1);
							continue;
						}
					}
				}
				if (!c.hasOrdenAscDesc()) {
					c.setOrdenOrden(0);
					continue;
				}
				c.setOrdenOrden(orden++);
		}
	}
	public BizCampos processReordenarToPos(BizCampo moveCampo,long toPos,boolean ascendente) throws Exception {
	 	BizCampos newOrden = new BizCampos();
	 	newOrden.setStatic(true);
	  long orden =1;
	  boolean insert = false;
			JIterator<BizCampo> itC = getObjAllCampos().getStaticIterator();
			while (itC.hasMoreElements()) {
				BizCampo c = itC.nextElement();
	  		if (!isOrigenDatos(c)) continue;
				if (c.getOrden()==toPos && ascendente && !insert) {
					moveCampo.setOrden(orden++);
					moveCampo.setearOrden(this);
					newOrden.getStaticItems().addElement(moveCampo);
					insert=true;
				}
				if ((c == moveCampo) || (c.hasSecuencia() && c.getSecuencia()==moveCampo.getSecuencia())) {
					continue;
				}

				c.setOrden(orden++);
				c.setearOrden(this);
				newOrden.getStaticItems().addElement(c);

				if (c.getOrden()==toPos && !ascendente && !insert)  {
					moveCampo.setOrden(orden++);
					moveCampo.setearOrden(this);
					newOrden.getStaticItems().addElement(moveCampo);
					insert=true;
				}

			} 
			if (!insert) {
				moveCampo.setOrden(orden++);
				moveCampo.setearOrden(this);
				newOrden.getStaticItems().addElement(moveCampo);
			}
	  	
		return newOrden;
	}
	public BizCampos processReordenarDescendente(BizCampo moveCampo) throws Exception {
	 	BizCampos newOrden = new BizCampos();
	 	newOrden.setStatic(true);
	  long orden =1;
  	JIterator<BizCampo> itC = getObjAllCampos().getStaticIterator();
  	BizCampo last =null;
		while (itC.hasMoreElements()) {
			BizCampo c = itC.nextElement();
  		if (!isOrigenDatos(c)) continue;
			if ((c == moveCampo) || (c.hasSecuencia() && c.getSecuencia()==moveCampo.getSecuencia())) {
				last=c;
				continue;
			}

			c.setOrden(orden++);
			c.setearOrden(this);
			newOrden.getStaticItems().addElement(c);
			if (last!=null) {
				last.setOrden(orden++);
				newOrden.getStaticItems().addElement(last);
				last=null;
			}
			
		}
		if (last!=null) {
			last.setOrden(orden++);
			last.setearOrden(this);
			newOrden.getStaticItems().addElement(last);
		}
		return newOrden;
	}
	public BizCampos processReordenarAscendente(BizCampo moveCampo) throws Exception {
	 	BizCampos newOrden = new BizCampos();
	 	newOrden.setStatic(true);
	 	long orden =1;
		boolean find = false;

		JIterator<BizCampo> itC = getObjAllCampos().getStaticIterator();
		BizCampo last = null;
		while (itC.hasMoreElements()) {
			BizCampo c = itC.nextElement();
  		if (!isOrigenDatos(c)) continue;
			if ((c == moveCampo) || (c.hasSecuencia() && c.getSecuencia() == moveCampo.getSecuencia())) {
				moveCampo.setOrden(orden++);
				moveCampo.setearOrden(this);
				newOrden.getStaticItems().addElement(moveCampo);
				last.setOrden(orden++);
				newOrden.getStaticItems().addElement(last);
				find = true;
				last = null;
				continue;
			}

			if (find) {
				c.setOrden(orden++);
				newOrden.getStaticItems().addElement(c);
				continue;
			}
			if (last != null) {
				last.setOrden(orden++);
				last.setearOrden(this);
				newOrden.getStaticItems().addElement(last);
			}
			last = c;

		}

		return newOrden;
	}

	
	public void processConvertidorAListado() throws Exception {
		

		BizCampos ejes = getEjes();
		BizCampos campos = getCampos();
		JIterator<BizCampo> it = ejes.getStaticIterator();
		JIterator<BizCampo> itC = campos.getStaticIterator();
		int order =ejes.getStaticItems().size()+1;
		//ejes como campos
		while (itC.hasMoreElements()) {
			BizCampo c = itC.nextElement();
		  if (c.isCampoCantidad()) {
		  	c.processDelete();
		  	continue;
		  }
			c.setObjCustomList(null);
		  c.setFuncion("");
		  c.setOrden(order++);
			c.update();
		}
		order =1;
		while (it.hasMoreElements()) {
			BizCampo eje = it.nextElement();
			//if (eje.isColumna()) continue;
			BizCampo c = new BizCampo();
		  if (c.isCampoCantidad()) continue;
		  if (c.isPorcentaje()) continue;
			c.setCompany(eje.getCompany());
			c.setListId(eje.getListId());
			c.setRecordOwner(eje.getRecordOwner());
			c.setRecordSource(eje.getRecordSource());
			c.setObjCustomList(null);
			c.setCampo(eje.getCampo());
			c.setSerialDeep(eje.getSerialDeep());
			c.setOrden(order++);
		  c.setRelId(eje.getRelId());
		  c.setOrigenDatos(eje.getOrigenDatos());
			c.processInsert();
		}
		
		ejes.processDeleteAll();
		
		//marca como listado
		setAgrupado("N");
		processUpdate();
		clean();
		
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
		//agregar
		JRecords<BizCompany> lista = new JRecords<BizCompany>(BizCompany.class);
		lista.addFilter("parent_company", getCompany());
		JIterator<BizCompany> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCompany comp = it.nextElement();
			BizCustomList oldEvent = new BizCustomList();
			oldEvent.dontThrowException(true);
			oldEvent.addFilter("company", comp.getCompany());
			oldEvent.addFilter("system_protect", this.getListId());
			if (oldEvent.read()) {
				continue;
			}

			BizCustomList oNewCustomList=new BizCustomList();
			oNewCustomList.copyProperties(this);
			oNewCustomList.setCompany(comp.getCompany());
			oNewCustomList.setSystemProtect(this.getListId());
			oNewCustomList.pListId.setNull();
			this.processClon(oNewCustomList);
		}
		//actualizar
		JRecords<BizCustomList> listaAct = new JRecords<BizCustomList>(BizCustomList.class);
		listaAct.addFilter("system_protect", this.getListId());
		JIterator<BizCustomList> itAct = listaAct.getStaticIterator();
		while (itAct.hasMoreElements()) {
			BizCustomList oldEvent = itAct.nextElement();
			processCambiarUnHijos(oldEvent);
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
		JRecords<BizCustomList> lista = new JRecords<BizCustomList>(BizCustomList.class);
		lista.addFilter("system_protect", getListId());
		JIterator<BizCustomList> it = lista.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList list = it.nextElement();
			list.processDelete();
		}
		
	}


	public void processCambiarUnHijos(BizCustomList hijo) throws Exception {
		hijo.copyNotKeysProperties(this);
		hijo.setSystemProtect(getListId());
		this.processUpdateClon(hijo);
		
	}
	public BizCustomList findCustomList(String company,String descrip) throws Exception {
		BizCustomList l =new BizCustomList();
		l.dontThrowException(true);
		l.addFilter("company", company);
		l.addFilter("description", descrip);
		if (!l.read()) return null;
		return l;
	}
	
	public void execProcessReparar() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processReparar();
			}
		};
		oExec.execute();
	}
	
  public  void processReparar() throws Exception {
		JRecords<BizCustomList> recs = new JRecords<BizCustomList>(BizCustomList.class);
		JIterator<BizCustomList> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCustomList ev = it.nextElement();
			BizCompany company = BizCompany.getCompany(ev.getCompany());
			if (company.getParentCompany()==null) continue;
			if (company.getParentCompany().equals("")) continue;
			BizCompany companyParent = BizCompany.getCompany(company.getParentCompany());
			if (companyParent==null) continue;
			String descr = ev.getDescripcion();
			if (descr.startsWith("Indicador:")) 
				descr = descr.substring(10);
			
			BizCustomList listEnPadre =findCustomList(companyParent.getCompany(),descr);
			if (listEnPadre==null) continue;

			
			
			if (ev.isInvisible()) {
				if (!ev.isSystemProtect()) {
					ev.setSystemProtect(listEnPadre.getListId());
					ev.update();
					continue;
				}
				
			} else {
				if (!ev.isSystemProtect()) {
					ev.setSystemProtect(listEnPadre.getListId());
					ev.update();
					continue;
				}
			}
			
		}

  
		JRecords<BizSqlEvent> recsE = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		JIterator<BizSqlEvent> itE = recsE.getStaticIterator();
		while (itE.hasMoreElements()) {
			BizSqlEvent ev = itE.nextElement();
			String ejesValor = ev.getEjesValor();
			if (ev.getCustomList()==2472) {
				PssLogger.logDebug("-");
			}

			if (ev.getObjCustomList()==null) continue;
			
			String descr = ev.getObjCustomList().getDescripcion();
			if (!descr.startsWith("Indicador:")) continue;
			descr = descr.substring(10);

			BizCustomList original =null;

			BizCampo campo =new BizCampo();
			campo.dontThrowException(true);
			if(!campo.read(ev.getIdCampo()))
					continue;
			original = campo.getObjCustomList();
			
			BizCustomList clon = ev.getObjCustomList();
			BizCampos ejes = original.getEjes();
			ejes.clearOrderBy();
			ejes.addOrderBy("secuencia");
			
			BizCampos ejesClon = clon.getEjes();
			ejesClon.clearOrderBy();
			ejesClon.addOrderBy("secuencia");
			
			JIterator<BizCampo> itEv =ejes.getStaticIterator();
			while (itEv.hasMoreElements()) {
				BizCampo eje = itEv.nextElement();
					JIterator<BizCampo> itC =ejesClon.getStaticIterator();
				while (itC.hasMoreElements()) {
					BizCampo ejeC = itC.nextElement();
					if (ejeC.isSimilar(eje)) {
						ejesValor=JTools.replace(ejesValor, eje.getSecuencia()+"=", ejeC.getSecuencia()+"=");
						break;
					}
				}			
			}
			
			ev.setEjesValor(ejesValor);
		
			BizCampos camposClon = clon.getCampos();
			camposClon.clearOrderBy();
			camposClon.addOrderBy("secuencia");
			
			JIterator<BizCampo> itCamposC = camposClon.getStaticIterator();
			while (itCamposC.hasMoreElements()) {
				BizCampo campoC = itCamposC.nextElement();
				if (campoC.isSimilar(campo)) {
					ev.setIdCampo(campoC.getSecuencia());
					break;
				}
			}	
			ev.update();
		
		}
  }
	public boolean isCampoClave(GuiCampoGallery campo) throws Exception {
		return campo.getCampo().GetcDato().getCampo().toLowerCase().equals("numeroboleto");
	}
	public JWins getOpcionesCampoClave() throws Exception {
			JMap<String, String> map = JCollectionFactory.createOrderedMap();
			map.addElement("Convertir", JLanguage.translate("Es más optimo convertir el reporte matricial en listado"));
			map.addElement("Mantener", JLanguage.translate("Mantener el reporte matricial"));
			map.addElement("Descartar", JLanguage.translate("Descartar cambio"));
			return JWins.createVirtualWinsFromMap(map);
		}
  
	public void execProcessRenumerar() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processRenumerar();
			}
		};
		oExec.execute();
	}
	public void processRenumerar() throws Exception {
		long newOrden=1;
		
		JRecords<BizCampoGallery> camposgallery = getAllCampos((BizAction)null, true);
//		JRecords<BizCampoGallery> ejesgallery = getAllEjes((BizAction)null, true);
//		JRecords<BizCampoGallery> filtrosgallery = getAllFilters((BizAction)null, true);
		
		
		BizCampos campos = getCampos();
		JIterator<BizCampo> itC = campos.getStaticIterator();
		while (itC.hasMoreElements()) {
			BizCampo elem = itC.nextElement();
			if (elem.getSerialDeep().equals("")) {
				JIterator<BizCampoGallery> it =camposgallery.getStaticIterator();
				while (it.hasMoreElements()) {
					BizCampoGallery cg = it.nextElement();
					if (cg.getCampo().equals(elem.getCampo()) && cg.getFunction().equals(elem.getFuncion())) {
						elem.setSerialDeep(cg.getSerialDeep());
						break;
					}
				}
			}
			elem.setOrden(newOrden++);
			elem.update();
		}
	


	}
	public void execProcessMigrate2() throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processMigrate2();
			}
		};
		oExec.execute();
	}
  public void processMigrate2() throws Exception {

  
  	BizCampos filtros = getObjAllCampos();
  	
  	filtros.toStatic();
  	JIterator<BizCampo> itf = filtros.getStaticIterator();
  	while (itf.hasMoreElements()) {
  		BizCampo campo = itf.nextElement();
  		if(campo.getValor().equals("")) continue;
			campo.setValor(campo.getValor());
  		campo.update();
  	}

  }
	public void execProcessAgregarFiltroInforme(final BizCampo added) throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarFiltroInforme(added);
			}
		};
		oExec.execute();
	}
	public void processAgregarFiltroInforme(BizCampo added) throws Exception {


		


		this.addCampo(added);
		
		
	}

	public void execProcessAgregarInforme(final BizCustomList added) throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarInforme(added);
			}
		};
		oExec.execute();
	}
	public void processAgregarInforme(BizCustomList added) throws Exception {

		BizInforme informe = new BizInforme();
		informe.setCompany(added.getCompany());
		informe.setListId(this.getListId());
		informe.setObjCustomList(added);
		informe.setObjParentCustomList(this);
		informe.processInsert();
	//	added.processInsert();
		added.cleanFiltrossCampos();
		fillNewPosition(informe);
		this.getObjInformes().getStaticItems().addElement(informe);
		
		
	}
	public void execProcessAgregarInformeDB(final BizCustomList added) throws Exception {
		JExec oExec = new JExec(null, null) {
			@Override
			public void Do() throws Exception {
				processAgregarInformeDB(added);
			}
		};
		oExec.execute();
	}
	public void processAgregarInformeDB(BizCustomList added) throws Exception {


		BizInforme informe = new BizInforme();
		informe.setCompany(added.getCompany());
		informe.setListId(this.getListId());
		informe.setObjCustomList(added);
		fillNewPosition(informe);
		informe.processInsert();		
		informe.insert();
	}
	JList<BizInforme> mapaPositions;
	public void clearMapaPosition() throws Exception {
		mapaPositions=null;
	}
	private JList<BizInforme> getMapaPositions() throws Exception {
		if (mapaPositions==null) mapaPositions = JCollectionFactory.createList();
		return mapaPositions;
	}
	public void savePosition(BizInforme view) throws Exception {
		getMapaPositions().addElement(view);
	
	}
	public void fillNewPosition(IResizableView view) throws Exception {

		clearMapaPosition();
		JIterator<BizInforme> it = this.getObjInformes().getStaticIterator();
		while (it.hasMoreElements()) {
			BizInforme inf = it.nextElement();
			savePosition(inf);
		}
				
		view.setPosY(getMapaPositions().size()*300);
		view.setPosX(10);
		view.setWidth(500);
		view.setHeight(300);
		
	}



	public boolean isFavorito() throws Exception {
		BizCustomListFav fav = new BizCustomListFav();
		fav.dontThrowException(true);
		return fav.read(getCompany(), getListId(), BizUsuario.getUsr().GetUsuario());
	}
	

  public String getTitleInformes(int id) throws Exception {
  	BizInforme informe = getInformes(id);
  	if (informe!=null) return informe.getDescripcion();
  	return null;
  }
  public BizInforme getInformes(int id) throws Exception {
  	int i=0;
  	JIterator<BizInforme> inf = getObjInformes().getStaticIterator();
  	while (inf.hasMoreElements()) {
  		BizInforme informe=inf.nextElement();
  		i++;
  		if (i==id) return informe;
  		
  	}
  	return null;
  }
  public boolean hasFiltrosManuales() throws Exception {
//  	JIterator<BizCampo> iter = this.getObjFiltros().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizCampo f = iter.nextElement();
//  		if (f.isDinamico()) {
//  			if (getFilterValue(""+f.getNameField())!=null) return true;
//  		}
//  	}
  	return false;
  }
  public void setInformeDistribution(String type) throws Exception {
 	 this.pInfoDistribution.setValue(type) ;
 }
 public String getInformeDistribution() throws Exception {
	 return this.pInfoDistribution.getValue() ;
}
  public void setAgrupado(String type) throws Exception {
  	 this.pAgrupado.setValue(type) ;
  }
  public boolean isDato() throws Exception {
  	return this.pAgrupado.getValue().equals(PRESENTATION_TYPE_DATO);
  }
  public boolean isGraphRequiredAgrupado() throws Exception {
  	if (pAgrupado.getValue().equals(Graph.GRAPH_TYPE_SCRIPT_SPIRAL)) return true;
  	if (pAgrupado.getValue().equals(Graph.GRAPH_TYPE_SCRIPT_SERIETEMP)) return true;
  	return false;
  }
  public boolean isAgrupado() throws Exception {
  	return this.pAgrupado.getValue().equals("S") || this.pAgrupado.getValue().equals(PRESENTATION_TYPE_AGRUPADO);
  }
  public boolean isLista() throws Exception {
  	return this.pAgrupado.getValue().equals("N") || this.pAgrupado.getValue().equals(PRESENTATION_TYPE_LIST);
  }
  public boolean isMatriz() throws Exception {
  	return this.pAgrupado.getValue().equals("M") || this.pAgrupado.getValue().equals(PRESENTATION_TYPE_MATRIX);
  }
  public boolean isGrafico() throws Exception {
  	return /*this.pAgrupado.getValue().equals("M") ||*/ this.pAgrupado.getValue().startsWith("G");
  }
  public boolean isGeo() throws Exception {
  	if (!isGrafico()) return false;
  	BizGraph gr =new BizGraph();
    gr.setType(pAgrupado.getValue());
    Graph g = gr.getObjGraph();
    return g.getGeoRequest()>0;
  }
  public boolean isInforme() throws Exception {
  	return this.pAgrupado.getValue().equals("I");
  }
  public boolean isSqlAdvanced() throws Exception {
  	if (this.getObjRelation()==null) return false;
  	return this.getObjRelation().getClassTarget().isAssignableFrom(BizAdvancedSQL.class);
  }

  //  public boolean isSQLBased() throws Exception {
//  	return this.pRelId.equals(SQL_BASED);
//  }

  public boolean hasRecordOwner() throws Exception {
  	return this.pRecordOwner.isNotNull();
  }
  
  public JMap<String, String> getRelationGallery(boolean source) throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  	map.addElements(this.getRelationGallery(true, source));
  	map.addElements(this.getRelationGallery(false, source));
  	return map;
  }
  
  public JMap<String, String> getRelationGallery(boolean parent, boolean source) throws Exception {
  	JMap<String, String> map = JCollectionFactory.createOrderedMap();
  	JRelations rels = source?this.getObjRecordOwner().getRelationMap(): this.getObjRelation().getObjRecordTarget().getRelationMap();
  	if (rels==null) return null;
  	JIterator<JRelation> iter = rels.getList().getIterator();
  	while (iter.hasMoreElements()) {
  		JRelation r = iter.nextElement();
  		if (r.isRelationFixed()) continue;
  		if (parent && !r.isRelationParent()) continue;
  		if (!parent && r.isRelationParent()) continue;
  		map.addElement(r.getId(), r.getDescription());
  	}
  	return map;
  }
  
  public void setObjRecordSource(JRecord value) throws Exception {
  	this.recordSource=value;
  }
  public JRecord getObjRecordSource() throws Exception {
  	return this.recordSource;
  }
	public JRecord getObjRecordOwner() throws Exception {
		if (this.recordOwner!=null) return this.recordOwner;
		JRecord r = (JRecord)Class.forName(this.getRecordOwner()).newInstance();
		return (this.recordOwner=r);
	}

  public JRelation getObjRelation() throws Exception {
  
  	if (this.relation!=null && this.pRelId.getValue().equals(idrelation)) return this.relation;
  	this.idrelation= this.pRelId.getValue();
  	return (this.relation=this.getObjRecordOwner().getRelationMap().findRel(this.pRelId.getValue()));
  }
  
//	public JRecord getObjRecordTarget() throws Exception {
//		if (this.recordTarget!=null) return this.recordTarget;
//		JRecord r = (JRecord)this.getObjRelation().getClassTarget().newInstance();
//		return (this.recordTarget=r);
//	}
  public String remplaceParametros(String sql) throws Exception {
  	String out = sql;
  

  	JIterator<BizFiltroSQL> it = getObjFiltroSQLs().getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizFiltroSQL fsql = it.nextElement();
  		String value =fsql.getValor();
  		if (value==null) {
    	  if (fsql.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_MES)) value=JDateTools.DateToString(JDateTools.getFirstDayOfMonth(BizUsuario.getUsr().todayGMT()));
    		else if (fsql.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_MES)) value=JDateTools.DateToString(JDateTools.getLastDayOfMonth(BizUsuario.getUsr().todayGMT()));
    		else if (fsql.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_INICIAL_ANO)) value=JDateTools.DateToString(JDateTools.getFirstDayOfYear(BizUsuario.getUsr().todayGMT()));
    		else if (fsql.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_FINAL_ANO)) value=JDateTools.DateToString(JDateTools.getLastDayOfYear(BizUsuario.getUsr().todayGMT()));
    		else if (fsql.getCampoDefecto().equalsIgnoreCase(JCompanyBusiness.FECHA_ACTUAL)) value=JDateTools.DateToString(BizUsuario.getUsr().todayGMT());
    		else value ="";
  		}
  		out= JTools.replace(out, "@"+fsql.getFiltro()+"@", value );
  	}
  	return out;
  }
  public void analizeRol(JLogicaCustomList zlogica) throws Exception {
   	clearRolTable();
    	
		if (isGrafico()) {
			analizeRolGraph(zlogica);
		} else {
			analizeRolTable(zlogica);
		}
	}
  public void clearRolTable() throws Exception {
  	JIterator<BizCampo> iter = this.getObjAllCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			f.setNullRol();
		}
	}
  public void analizeRolTable(JLogicaCustomList zlogica) throws Exception {
  	JIterator<BizCampo> iter = this.getObjAllCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
  		if (!isOrigenDatos(f)) continue;
			if (zlogica.isFila(f))
				f.setRol(BizCampo.FILA);
			else if (zlogica.isColumna(f))
				f.setRol(BizCampo.COLUMNA);
			else if (zlogica.isCampo(f))
				f.setRol(BizCampo.CAMPO);
			
		}
	}
  public void analizeRolGraph(JLogicaCustomList zlogica) throws Exception {
  	BizGraph gr =new BizGraph();
    gr.setType(pAgrupado.getValue());
    Graph g = gr.getObjGraph();
    boolean geo=g.getGeoRequest()>0;
    boolean MS=g.isMultiSerie();
    String Time=g.isTimeLine();
    if (g instanceof GraphVector) {
      analizeRolGraphVector(geo,MS,Time,zlogica);
    } else if (g instanceof GraphMatrix) {
      analizeRolGraphMatrix(geo,MS,Time,zlogica);
    } else if (g instanceof GraphMatrixAndLine) {
      analizeRolGraphMatrixAndLine(geo,MS,Time,zlogica);
    	
    }
  }
  public void analizeRolGraphVector(boolean geo,boolean MS,String time,JLogicaCustomList zlogica) throws Exception {
  	JIterator<BizCampo> iter = null;
  	boolean onlyOneValor=true;
  	boolean onlyOneDataset=true;
  	// contienda 1: lo que el usuario diga
  	iter = this.getObjAllCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
  		if (!isOrigenDatos(f)) continue;
			if (f.isColumna() && onlyOneDataset && !f.isNoUsar() ) {
				f.setRol(BizCampo.DATASET);
				onlyOneDataset=false;
			}
			else if (f.isCampo() && onlyOneValor) {
				f.setRol(BizCampo.VALOR);
				onlyOneValor=MS?true:false;
			}
		}
	 	// contienda 2: priorizo el capo del tipo adecuado para graficas con pos geografica
			if (geo) {
		  	iter = this.getObjAllCampos().getStaticIterator();
				while (iter.hasMoreElements()) {
					BizCampo f = iter.nextElement();
		  		if (!isOrigenDatos(f)) continue;
					if (f.hasRol()) continue;
					if (onlyOneDataset && (f.hasGeoCampo()) && !f.isNoUsar() ) {
						f.setRol(BizCampo.DATASET);
						onlyOneDataset=false;
						break;
					}
					
				}			
			}
  	// contienda 3: priorizo el capo del tipo adecuado para graficas con fecha
		if (time.equals(Graph.TIME_IN_DATASET)) {
	  	iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.hasRol()) continue;
				if (onlyOneDataset && ( f.isDate()) && !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
					break;
				}
				
			}
		}
	
		// contienda 4: lo que analisis de la logica diga
		if (onlyOneValor || onlyOneDataset) {
			iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.hasRol()) continue;
				if (zlogica.isDataset(f) && onlyOneDataset&& !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
				}
				else if (zlogica.isCampo(f) && onlyOneValor) {
					f.setRol(BizCampo.VALOR);
					onlyOneValor=MS?true:false;
				}
			}
		}
		// contienda 5: lo que quede
		if (onlyOneValor || onlyOneDataset) {
			iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.hasRol()) continue;
				if (zlogica.isEje(f) && onlyOneDataset&& !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
				}
				else if (zlogica.isCampo(f) && onlyOneValor) {
					f.setRol(BizCampo.VALOR);
					onlyOneValor=MS?true:false;
				}
			}
		}
  }


  public void analizeRolGraphMatrix(boolean geo,boolean MS,String time,JLogicaCustomList zlogica) throws Exception {
  	JIterator<BizCampo> iter = this.getObjAllCampos().getStaticIterator();
  	boolean onlyOneCategory=true;
  	boolean onlyOneDataset=true;
  	boolean onlyOneValor=true;
   	// contienda 1: lo que el usuario diga
 		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
  		if (!isOrigenDatos(f)) continue;
			if (f.isGroup()) continue;
			if (f.isFila() && onlyOneCategory  && !f.isNoUsar() ) {
				f.setRol(BizCampo.CATEGORIA);
				onlyOneCategory = false;
			} else if (f.isColumna()&& onlyOneDataset  && !f.isNoUsar() ) {
				f.setRol(BizCampo.DATASET);
				onlyOneDataset=false;
			}
			else if (f.isCampo() && onlyOneValor) {
				f.setRol(BizCampo.VALOR);
				onlyOneValor=MS?true:false;
			}
		}

	 	// contienda 2: priorizo el campo del tipo adecuado para graficas con pos geografica
		if (geo) {
	  	iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				if (onlyOneCategory && (f.hasGeoCampo()) && !f.isNoUsar() ) {
					f.setRol(BizCampo.CATEGORIA);
					onlyOneCategory=false;
					continue;
				}
				if (onlyOneDataset && (f.hasGeoCampo()) && !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
					continue;
				}
				
			}			
		}
  	// contienda 3.a: priorizo el campo del tipo adecuado para graficas con fecha
		if (time.equals(Graph.TIME_IN_DATASET)) {
	  	iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				if (f.hasFunction()) continue;
				if (onlyOneDataset && (f.isDate()) && !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
					break;
				}
				
			}
		}
  	// contienda 3.b: priorizo el campo del tipo adecuado para graficas con fecha
		if (time.equals(Graph.TIME_IN_CATEGORY)) {
	  	iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				if (f.hasFunction()) continue;
				if (onlyOneCategory &&(f.isDate()) && !f.isNoUsar() ) {
					f.setRol(BizCampo.CATEGORIA);
					onlyOneCategory=false;
					break;
				}
				
			}
	  	iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				if (onlyOneDataset && !f.isDate() && !f.isNumeric() && !f.isCheckInput() && !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
					break;
				}
				
			}
		}
		// contienda 4: lo que analisis de la logica diga
		if (onlyOneCategory||onlyOneDataset||onlyOneValor) {
			iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
	  		if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				if (zlogica.isDataset(f)&& onlyOneDataset  && !f.isNoUsar() ) {
					f.setRol(BizCampo.DATASET);
					onlyOneDataset=false;
				}else if (zlogica.isCategoria(f)&& onlyOneCategory && !f.isNoUsar() ) {
					f.setRol(BizCampo.CATEGORIA);
					onlyOneCategory = false;
				} 
				else if (zlogica.isCampo(f) && onlyOneValor) {
					f.setRol(BizCampo.VALOR);
					onlyOneValor=MS?true:false;
				}
			}
		}
		// contienda 5: lo que queda
		if (onlyOneCategory||onlyOneDataset||onlyOneValor) {
			iter = this.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo f = iter.nextElement();
	  		if (!isOrigenDatos(f)) continue;
				if (f.isGroup()) continue;
				if (f.hasRol()) continue;
				 if (zlogica.isEje(f)&& onlyOneDataset && !f.isNoUsar() ) {
						f.setRol(BizCampo.DATASET);
						onlyOneDataset=false;
				} else if (zlogica.isEje(f)&& onlyOneCategory  && !f.isNoUsar() ) {
					f.setRol(BizCampo.CATEGORIA);
					onlyOneCategory = false;
				} 
				else if (zlogica.isCampo(f) && onlyOneValor) {
					f.setRol(BizCampo.VALOR);
					onlyOneValor=MS?true:false;
				}
			}
		}
  }

  public void analizeRolGraphMatrixAndLine(boolean geo,boolean MS,String time,JLogicaCustomList zlogica) throws Exception {
  	analizeRolGraphMatrix(geo,MS,time,zlogica);
  	boolean hasLinea=false;
  	JIterator<BizCampo> iter = this.getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
  		if (!isOrigenDatos(f)) continue;
			if (f.isGroup()) continue;
			if (f.hasRol()) continue;
			if (f.isLinea()  && !f.isNoUsar() ) {
				f.setRol(BizCampo.LINEA);
				hasLinea=true;
				break;
			}
		}
  	if (!hasLinea) {
    	iter = this.getObjAllCampos().getStaticIterator();
    	while (iter.hasMoreElements()) {
  			BizCampo f = iter.nextElement();
    		if (!isOrigenDatos(f)) continue;
  			if (f.isGroup()) continue;
  			if (f.hasRol()) continue;
  			if (f.isCampo()  && !f.isNoUsar() ) {
  				f.setRol(BizCampo.LINEA);
  				hasLinea=true;
  				break;
  			}
  		}  		
  	}

  }
  
  public boolean isEdit() throws Exception {
  	return true;
  }

  
  public JRecords<?> getAllRecords() throws Exception {
		JRecords<?> r = new JRecords<BizDynamic>(BizDynamic.class);
		this.clearOrderBy();
		r.SetVision(this.GetVision());
		if (r.GetVision().equals("PREVIEW")) {
			r.setPagesize(10);
			r.setWithUse(true);
		}
		getLogica().setWithgeo(isWithGeo());
		getLogica().prepareTables(r);
	  if (this.isSqlAdvanced()) {
	  	r.SetSQL(remplaceParametros(this.pSQL.getValue()));
	  	return r;
	  }
	  getLogica().prepareJoins(r, false);
  	getLogica().prepareFields(r);
  	getLogica().prepareLiteralFilters(r);
  	getLogica().prepareFilters(r, false);
  	getLogica().prepareFiltersDinamico(r, false);
  	getLogica().prepareOrders(r);

  	fillFiltersPreview(r);

	  return r;
  }


	 
	
  public BizCampo findDynamicFilter(String filtro, String operator) throws Exception {
  	JIterator<BizCampo> iter = this.getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!isOrigenDatos(f)) continue;
  		if (!f.isDinamico()) continue;
  		if (filtro.startsWith("filtro")) {
  			if (!("filtro"+f.getOrden()).equals(filtro)) continue;
  		} else {
	  		if (!f.getNameField().toLowerCase().equals(filtro.toLowerCase())) continue;
	  		if (!f.getFuncion().endsWith(BizFuncion.FUNTION_HOY) && !f.getFuncion().endsWith(BizFuncion.FUNTION_AYER) &&
	  				!f.getFuncion().endsWith(BizFuncion.FUNTION_PROXIMOS) && !f.getFuncion().endsWith(BizFuncion.FUNTION_ULTIMOS) &&
	  				!f.getFuncion().endsWith(BizFuncion.FUNTION_MANIANA) &&  !f.getFuncion().endsWith("ACTUAL") && operator!=null && 
	  				!f.getOperador().toLowerCase().equals(operator.toLowerCase())) continue;	  			
  		}
  		return f;
  	}
   	return null;
  }
	
  public BizFiltroSQL findDynamicFilterSQL(String filtro, String operator) throws Exception {
  	JIterator<BizFiltroSQL> iter2 = this.getObjFiltroSQLs().getStaticIterator();
  	while (iter2.hasMoreElements()) {
  		BizFiltroSQL f = iter2.nextElement();
  		if (!f.getFilterName().equals(filtro) && !f.getFiltro().equals(filtro)) continue;
  		return f;
  	}
  	return null;
  }
  public void fillFilters(JRecords recs) throws Exception {
		JRecords<BizDocListadoParam> filtros =getObjParametros();
		if (filtros==null) return;
		filtros.toStatic();
		JIterator<BizDocListadoParam> it=filtros.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDocListadoParam f = it.nextElement();
			if (f.getValue().equals("")) continue;
			if (f.getOrden()>1000) {
				RFilter filter=recs.addFilter(""+f.getObjFiltroSQL().getFilterName(), f.getValue(), "=");
				filter.setVirtual(true);
			}	else {
				if (f.getObjFiltro()==null) 
					continue;
				if (f.getObjFiltro().getOperador().equals(BizFuncion.FUNTION_INTERVALO)) 
					continue;
				RFilter filter=recs.addFilter(""+f.getObjFiltro().getNameField(), f.getValue(), f.getObjFiltro().getOperador());
				filter.setDynamic(true);
				filter.setVirtual(true);
			}
		}
	}

  public void fillFiltersPreview(JRecords recs) throws Exception {
		JRecords<BizDocListadoParam> filtros =getObjParametros();
		if (filtros==null) return;
		filtros.toStatic();
		JIterator<BizDocListadoParam> it=filtros.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDocListadoParam f = it.nextElement();
			if (f.getValue().equals("")) continue;
			if (f.getOrden()>1000) {
				RFilter filter=recs.addFilter(""+f.getObjFiltroSQL().getFilterName(), f.getValue(), "=");
				filter.setVirtual(false);
			}	else {
//				if (f.getObjFiltro()==null) 
//					continue;
//				if (f.getObjFiltro().getOperador().equals(BizFuncion.FUNTION_INTERVALO)) 
//					continue;
//				RFilter filter=recs.addFilter(""+f.getObjFiltro().getFiltro(), f.getValue(), f.getObjFiltro().getOperador());
//				filter.setDynamic(true);
//				filter.setVirtual(false);
				//RFixedFilter filter=recs.addFixedFilter(getExpresionWithOperator(f.getObjFiltro().getOperador(),""+f.getObjFiltro().getFiltro(),f.getObjFiltro().getObjectType(), f.getValue() ));
//				filter.setDynamic(true);
			}
		}
	}
	JList<RFilter> filtrosDinamicos;


	public JList<RFilter> getFiltrosDinamicos() {
		return filtrosDinamicos;
	}

	public void setFiltrosDinamicos(JList<RFilter> filtrosDinamicos) {
		this.filtrosDinamicos = filtrosDinamicos;
	}
  
  JRecords<BizDocListadoParam> objParams;
	public void setObjParams(JRecords<BizDocListadoParam> objParams) {
		this.objParams = objParams;
	}
	public JRecords<BizDocListadoParam> getObjParametros() throws Exception {
		return objParams;
	}
	JCorteControl cc;
	JRecords<BizVirtual> seccs;
	public JRecords<BizVirtual> getObjSecciones() throws Exception {
		if (seccs==null) 
			getObjListado(null);
		return seccs=cc.getObjSecciones();
		
	}
	
	public JRecords<BizDynamic> getObjListadoPorSeccion(String filter) throws Exception {
		if (cc==null) getObjListado(null);
		return cc.getObjListadoPorSeccion(filter);
	
	}
	
	public JRecords<BizDynamic> getObjListado(String filter) throws Exception {
		GuiDynamics d = new GuiDynamics();
		cc=new JCorteControl();
		GuiCustomList custom = new GuiCustomList();
		custom.setRecord(this);
		custom.GetcDato().setWithCC(true);
		d.setCustomList(custom);
		fillFilters(d.getRecords());
		if (filter!=null) d.getRecords().addFixedFilter(filter);
		d.readAll();
		d.firstRecord();
		BizDynamic antDyn=null;
		while (d.nextRecord()) {
			GuiDynamic din = (GuiDynamic)d.getRecord();
			din.setCustomList(custom);
			cc.process((BizDynamic)din.getRecord(),antDyn);
			antDyn=(BizDynamic)din.getRecord();
			d.addRecord(din);
		}
		if (antDyn!=null) 			
			cc.process(null,antDyn);

		d.SetEstatico(true);
		return d.getRecords();
  }

	public BizCustomList execProcessClon(final BizCustomList clon) throws Exception {
  	JExec oExec = new JExec(this, "processClon") {
 		@Override
			public void Do() throws Exception {
				 processClon(clon);
			}
		};
		oExec.execute();
		return clon;
	}
	public BizCustomList getClon() throws Exception {
    final BizCustomList clon = new BizCustomList();
    clon.copyProperties(this);
    clon.pListId.setNull();
    clon.pDescription.setValue(this.getDescription()+" (bis)");
    clon.pSystemProtect.setNull();
    clon.setOwner(BizUsuario.getUsr().GetUsuario());
    clon.setObjAllCampos(this.getObjAllCampos(),true);
   	clon.setHoy(this.getHoy());
    
		return clon;
	}
	
	public BizCustomList processClon(String company, boolean alhijo) throws Exception {
		BizCustomList oNewCustomList=new BizCustomList();
		oNewCustomList.copyProperties(this);
		oNewCustomList.setCompany(company);
		oNewCustomList.setOwner(BizUsuario.getUsr().GetUsuario());
		oNewCustomList.setNullListId();

		if (alhijo) oNewCustomList.setSystemProtect(this.getListId());
		return this.processClon(oNewCustomList);
	}


	public BizCustomList processClon(BizCustomList newDoc) throws Exception {
//		newDoc.copyProperties(this);
	 // newDoc.setObjAllCampos(this.getObjAllCampos(),true);
		PssLogger.logInfo("Cloning "+this.getListId()+" "+this.getDescripcion());
		if (!newDoc.isInvisible()&&getCompany().equals(newDoc.getCompany()))
				newDoc.setNullSystemProtect();
//		JMap<Long,Long> equivCampos = JCollectionFactory.createMap();
		newDoc.allCampos=new BizCampos();
		newDoc.allCampos.setStatic(true);
		BizCampos ampls = this.getObjAllCampos();
		JIterator<BizCampo> it = ampls.getStaticIterator();
		while (it.hasMoreElements()) {
			BizCampo ampl= it.nextElement();
			BizCampo na = new BizCampo();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setListId(newDoc.getListId());
			na.setNullToSecuencia();
			newDoc.addCampo(na);
		}
		newDoc.processInsert();
	

//		JIterator<BizCampo> it2 = newDoc.getObjAllCampos().getStaticIterator();
//		while (it2.hasMoreElements()) {
//			BizCampo ampl= it2.nextElement();
//			if (!ampl.isDependiente()) continue;
//			ampl.setOrdenPadre(""+equivCampos.getElement(Long.parseLong(ampl.getOrdenPadre())));
//			ampl.update();
//		}
//		
		return newDoc;
	}
	
	public BizCustomList processUpdateClon(BizCustomList newDoc) throws Exception {
//	newDoc.copyProperties(this);
//	JMap<String,String> equivCampos = JCollectionFactory.createMap();
//	JMap<Long,Long> equivEjes = JCollectionFactory.createMap();
//	JMap<Long,Long> equivOrden = JCollectionFactory.createMap();
//	
	newDoc.processUpdate();

	
	JRecords<BizCampo> ampls = new JRecords<BizCampo>(BizCampo.class);
	ampls.addFilter("company", getCompany());
	ampls.addFilter("list_id", getListId());
	ampls.toStatic();
	String updated = ""; 
	while (ampls.nextRecord()) {
		BizCampo ampl= ampls.getRecord();
		BizCampo na = new BizCampo();
		na.dontThrowException(true);
	  ampl.setObjCustomList(this);
	  na.setObjCustomList(newDoc);
	  
		boolean find=na.read(newDoc.getCompany(), newDoc.getListId(), ampl.getSerialDeep(),ampl.getCampo(),ampl.getFuncion(),ampl.isPorcentaje(),ampl.getOrden());
		na.copyNotKeysProperties(ampl);
		na.setCompany(newDoc.getCompany());
		na.setListId(newDoc.getListId());
		if (find) na.update();
		else 	{
			na.insert();
			na.setSecuencia(na.getIdentity("secuencia"));
		}
		
//		if (ampl.isDependiente()) 
//			equivCampos.addElement(ampl.getOrdenPadre(), na.getOrdenPadre());
		updated += (updated.equals("")?"":", ")+na.getSecuencia();
	}
	if (!updated.equals("")) {
		JRecords<BizCampo> amplsD = new JRecords<BizCampo>(BizCampo.class);
		amplsD.addFilter("company", newDoc.getCompany());
		amplsD.addFilter("list_id", newDoc.getListId());
		amplsD.addFilter("secuencia", "("+updated+")","not in");
		amplsD.delete();
	}
//	JIterator<BizCampo> it2 = newDoc.getObjAllCampos().getStaticIterator();
//	while (it2.hasMoreElements()) {
//		BizCampo ampl= it2.nextElement();
//		if (!ampl.isDependiente()) continue;
//		ampl.setOrdenPadre(""+equivCampos.getElement(Long.parseLong(ampl.getOrdenPadre())));
//		ampl.update();
//	}
//	updated = ""; 
//	JRecords<BizOrder> ampls5 = new JRecords<BizOrder>(BizOrder.class);
//	ampls5.addFilter("company", getCompany());
//	ampls5.addFilter("list_id", getListId());
//	ampls5.toStatic();
//	while (ampls5.nextRecord()) {
//		BizOrder ampl= ampls5.getRecord();
//		BizOrder na = new BizOrder();
//		na.dontThrowException(true);
//		boolean find=na.read(newDoc.getCompany(), newDoc.getListId(), ampl.getSerialDeep(),ampl.getCampo());
//		na.copyNotKeysProperties(ampl);
//		na.setCompany(newDoc.getCompany());
//		na.setListId(newDoc.getListId());
//		if (find) na.update();
//		else na.processInsert();
//		equivOrden.addElement(ampl.getSecuencia(), na.getSecuencia());
//		updated += (updated.equals("")?"":", ")+na.getSecuencia();
//	}
//	if (!updated.equals("")) {
//		JRecords<BizOrder> amplsD = new JRecords<BizOrder>(BizOrder.class);
//		amplsD.addFilter("company", newDoc.getCompany());
//		amplsD.addFilter("list_id", newDoc.getListId());
//		amplsD.addFilter("secuencia", "("+updated+")","not in");
//		amplsD.delete();
//	}	
	/*
	updated = ""; 
 	BizEjes ampls2 = new BizEjes();
 	ampls2.setObjCustomList(this);
	ampls2.addFilter("company", getCompany());
	ampls2.addFilter("list_id", getListId());
	ampls2.toStatic();
	while (ampls2.nextRecord()) {
		BizEje ampl= ampls2.getRecord();
		BizEje na = new BizEje();
		na.dontThrowException(true);
		boolean find=na.read(newDoc.getCompany(), newDoc.getListId(), ampl.getSerialDeep(),ampl.getCampo(),ampl.getFuncion());
		na.copyNotKeysProperties(ampl);
		na.setCompany(newDoc.getCompany());
		na.setListId(newDoc.getListId());
		if (ampl.hasRanking()) 
			na.setRanking(equivOrden.getElement(ampl.getRanking()));

		if (ampl.hasDiferencia())
			na.setCampoDiferencia(equivCampos.getElement(ampl.getCampoDiferencia()));
		if (find) na.update();
		else na.processInsert();
		equivEjes.addElement(ampl.getSecuencia(), na.getSecuencia());
		updated += (updated.equals("")?"":", ")+na.getSecuencia();
	}
	if (!updated.equals("")) {
		JRecords<BizEje> amplsD = new JRecords<BizEje>(BizEje.class);
		amplsD.addFilter("company", newDoc.getCompany());
		amplsD.addFilter("list_id", newDoc.getListId());
		amplsD.addFilter("secuencia", "("+updated+")","not in");
		amplsD.delete();
	}
	*/
//	updated = ""; 
//	JRecords<BizFiltro> ampls3 = new JRecords<BizFiltro>(BizFiltro.class);
//	ampls3.addFilter("company", getCompany());
//	ampls3.addFilter("list_id", getListId());
//	ampls3.toStatic();
//	while (ampls3.nextRecord()) {
//		BizFiltro ampl= ampls3.getRecord();
//		BizFiltro na = new BizFiltro();
//		na.dontThrowException(true);
//		boolean find;
//		find=na.read(newDoc.getCompany(), newDoc.getListId(), ampl.getOrden());
//		na.copyNotKeysProperties(ampl);
//		na.setCompany(newDoc.getCompany());
//		na.setListId(newDoc.getListId());
//		if (find) na.update();
//		else {
//			na.insert();
//			na.setSecuencia(na.getIdentity("secuencia"));
//		}
//
//		updated += (updated.equals("")?"":", ")+na.getSecuencia();
//	}
//	if (!updated.equals("")) {
//		JRecords<BizFiltro> amplsD = new JRecords<BizFiltro>(BizFiltro.class);
//		amplsD.addFilter("company", newDoc.getCompany());
//		amplsD.addFilter("list_id", newDoc.getListId());
//		amplsD.addFilter("secuencia", "("+updated+")","not in");
//		amplsD.delete();
//	}
//	updated = ""; 
//	JRecords<BizFiltroSQL> ampls4 = new JRecords<BizFiltroSQL>(BizFiltroSQL.class);
//	ampls4.addFilter("company", getCompany());
//	ampls4.addFilter("list_id", getListId());
//	ampls4.toStatic();
//	while (ampls4.nextRecord()) {
//		BizFiltroSQL ampl= ampls4.getRecord();
//		BizFiltroSQL na = new BizFiltroSQL();
//		na.dontThrowException(true);
//		boolean find=na.read(newDoc.getCompany(), newDoc.getListId(),ampl.getOrden());
//		na.copyNotKeysProperties(ampl);
//		if (find) na.update();
//		else na.processInsert();
//		
//		na.setCompany(newDoc.getCompany());
//		na.setListId(newDoc.getListId());
//		updated += (updated.equals("")?"":", ")+na.getSecuencia();
//	}
//	if (!updated.equals("")) {
//		JRecords<BizFiltroSQL> amplsD = new JRecords<BizFiltroSQL>(BizFiltroSQL.class);
//		amplsD.addFilter("company", newDoc.getCompany());
//		amplsD.addFilter("list_id", newDoc.getListId());
//		amplsD.addFilter("secuencia", "("+updated+")","not in");
//		amplsD.delete();
//	}
//
//	updated = ""; 
//	JRecords<BizGraph> ampls6 = new JRecords<BizGraph>(BizGraph.class);
//	ampls6.addFilter("company", getCompany());
//	ampls6.addFilter("list_id", getListId());
//	ampls6.toStatic();
//	while (ampls6.nextRecord()) {
//		BizGraph ampl= ampls6.getRecord();
//		BizGraph na = new BizGraph();
//		na.dontThrowException(true);
//		boolean find=na.read(newDoc.getCompany(), newDoc.getListId(), ampl.getSecuencia());
//		na.copyNotKeysProperties(ampl);
//		na.setCompany(newDoc.getCompany());
//		na.setListId(newDoc.getListId());
//		na.setNullToSecuencia();
//		Long val = null;
//		if (isMatriz()) {
//			val = ampl.getColCategoria().equals("")?null:equivEjes.getElement(Long.parseLong(ampl.getColCategoria()));
//			if (val!=null) na.setColCategoria(""+val.longValue());
//			val = ampl.getColDataset().equals("")?null:equivEjes.getElement(Long.parseLong(ampl.getColDataset()));
//			if (val!=null) na.setColDataset(""+val.longValue());
//		} else {
//			val = ampl.getColCategoria().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColCategoria()));
//			if (val!=null) na.setColCategoria(""+val.longValue());
//			val = ampl.getColDataset().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColDataset()));
//			if (val!=null) na.setColDataset(""+val.longValue());
//		}
//		val = ampl.getColValor().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColValor()));
//		if (val!=null) na.setColValor(""+val.longValue());
//		val = ampl.getColValor2().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColValor2()));
//		if (val!=null) na.setColValor2(""+val.longValue());
//		val = ampl.getColValor3().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColValor3()));
//		if (val!=null) na.setColValor3(""+val.longValue());
//		val = ampl.getColValor4().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColValor4()));
//		if (val!=null) na.setColValor4(""+val.longValue());
//		val = ampl.getColValor5().equals("")?null:equivCampos.getElement(Long.parseLong(ampl.getColValor5()));
//		if (val!=null) na.setColValor5(""+val.longValue());
//		if (find) na.update();
//		else na.processInsert();
//		updated += (updated.equals("")?"":", ")+"'"+na.getSecuencia()+"'";
//	}
//	if (!updated.equals("")) {
//		JRecords<BizGraph> amplsD = new JRecords<BizGraph>(BizGraph.class);
//		amplsD.addFilter("company", newDoc.getCompany());
//		amplsD.addFilter("list_id", newDoc.getListId());
//		amplsD.addFilter("secuencia", "("+updated+")","not in");
//		amplsD.delete();
//	}
		return newDoc;
}
	public String getPreview() throws Exception {
		return this.getPreview("pss.common.customList.config.GuiCustomList_24");
	}
	public String getPreview(String action) throws Exception {
    if (isMatriz()) {
    	if (getEjes().selectCount()==0 || getCampos().selectCount()==0)
    		return "<img style=\"position:absolute;top:0;left:0px;width:731px;height:590px\" src=\"pss_icon/matriz.jpg\" /> ";
    } else if (getCampos().selectCount()==0) {
    		return "<img style=\"position:absolute;top:0;left:0px;width:731px;height:590px\" src=\"pss_icon/lista.jpg\" /> ";
    }
    return "<SCRIPT TYPE=\"POSTSCRIPT\">goTo(this,'do-WinListRefreshAction', false, null, null, 'preview', 'cls_op1=pss.common.customList.config.customlist.GuiCustomList,vision_op1=,fltr_company_op1="+this.getCompany()+", fltr_list_id_op1="+this.getListId()+"', 'act_op1="+action+",');  </SCRIPT> ";
	}
	
	public String getSQL(BizCampo c) throws Exception {
		JBaseWin basewin = (JBaseWin)Class.forName(this.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
		if (!(basewin instanceof JWins)) return null;
		JWins wins = (JWins)basewin;
  	this.clean();
		this.getLogica().setWithgeo(false);
		String name=this.getLogica().prepareOneFields(c,wins.getRecords(),null,!c.hasFunction()?BizCampo.FUNTION_SUM:c.getFuncion());
		if (name!=null && name.startsWith("VIRTUAL:")) return name;
		this.getLogica().prepareTables(wins.getRecords());
		this.getLogica().prepareJoins(wins.getRecords(), true);
		this.getLogica().prepareFilters(wins.getRecords(), true);
		this.getLogica().prepareLiteralFilters(wins.getRecords());
		JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
		reg.setDistinct(true);
		return reg.ArmarSelect();
	}
	
	public String getSqlDetalle(long limit) throws Exception {
  	BizCustomList biz = this;
   	JWins wins = (JWins)Class.forName(biz.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
  	biz.clean();
  	JRecords r = wins.getRecords();
  	biz.getLogica().setWithgeo(false);
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
  	biz.getLogica().prepareFilters(wins.getRecords(), false);
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
   
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(r);
  	if (limit>0) reg.setTop(limit);
  	return reg.ArmarSelect();
  }  

	public String getDato() throws Exception {
		if (!isDato()) return "0";
  	BizCampo c = getFirstCampoNumerico();
  	if (c==null) return "0";
  	String sql = getSQL(c);
  	if (sql==null) return "0";
  	
  	return ""+getSqlValue(sql, c.getNameField());
  }  
	public String getImage() throws Exception {
		try {
			return getImageInterno();
		} catch (Exception e) {
			PssLogger.logError(e);
			return "script:"+e.getMessage();
		}
	}
	public BizGraph buildGraph() throws Exception {
		BizGraph gr =new BizGraph();
  	gr.setObjCustomList(this);
 		BizCampo categoria = getGraphCategoria();
		BizCampo dataset = getGraphDataset();
		BizCampos valores = getGraphValor();
		if (categoria != null)
			gr.setColCategoria(categoria.getNameField());

		if (dataset != null)
			gr.setColDataset(dataset.getNameField());

		int id = 1;
		JIterator<BizCampo> itV = valores.getStaticIterator();
		while (itV.hasMoreElements()) {
			BizCampo valor = itV.nextElement();
			if (id == 1)
				gr.setColValor(valor.getNameField());
			else if (id == 2)
				gr.setColValor2(valor.getNameField());
			else if (id == 3)
				gr.setColValor3(valor.getNameField());
			else if (id == 4)
				gr.setColValor4(valor.getNameField());
			else if (id == 5)
				gr.setColValor5(valor.getNameField());
			id++;
		}

    gr.setType(pAgrupado.getValue());
  	return gr;
	}
	
	public void clearCacheImage() throws Exception {
		cacheImage=null;
	}
	String cacheImage = null;
	public String getImageInterno() throws Exception {
		if (!isGrafico()) return null;
		if (cacheImage!=null) return cacheImage;
  	GuiCustomListResult l=new GuiCustomListResult();
  	l.SetVision(GetVision());
		l.setRecord(this);
		JWins w = l.getReporte();
		JWinList wl = new JWinList(w);
    w.ConfigurarColumnasLista(wl);
    w.ConfigurarFiltrosLista(wl);
  	Graph g = buildGraph().getGraph();
  	if (g==null) return null;
		g.localFill(wl,null,null);
		return cacheImage=g.getImage(1500, 600);
	}  

	private BizCampoGallery createCantidad(JRelation rel) throws Exception {
		BizCampoGallery vacio = rel.createCampoGallery();
		vacio.setCampo(BizField.FUNTION_COUNT);
		vacio.setFunction(BizField.FUNTION_COUNT);
		vacio.setRecordOwner(rel.getObjRecordTarget().getClass().getName());
		vacio.setRecordSource(rel.getObjRecordSource().getClass().getName());
		vacio.setRelId(rel.getId());
		vacio.setRelIdDescr(rel.findDescrParent());
		vacio.setSerialDeep(rel.serializeDeep());
		vacio.setObjRelacion(rel);
		vacio.setDescripcion(JLanguage.translate("#Cantidad de")+" "+rel.getDescription());
		vacio.setDescrCompleta(JLanguage.translate("#Cantidad de")+" "+rel.getDescription());
		vacio.setOrden(JTools.LPad("!",10,"0")+"_"+rel.getDescription());
		vacio.setDescripcionSinFuncion(JLanguage.translate("#Cantidad de")+" "+rel.getDescription());
		vacio.setSimple(true);
		return vacio;
	}
	private BizCampoGallery createFormula(JRelation rel) throws Exception {
		BizCampoGallery vacio = rel.createCampoGallery();
		vacio.setCampo(BizField.FUNTION_FORMULA);
		vacio.setFunction(BizField.FUNTION_FORMULA);
		vacio.setRecordOwner(rel.getObjRecordTarget().getClass().getName());
		vacio.setRecordSource(rel.getObjRecordSource().getClass().getName());
		vacio.setRelId(rel.getId());
		vacio.setRelIdDescr(rel.findDescrParent());
		vacio.setSerialDeep(rel.serializeDeep());
		vacio.setObjRelacion(rel);
		vacio.setDescripcion(JLanguage.translate("#Formula")+" "+rel.getDescription());
		vacio.setDescrCompleta(JLanguage.translate("#Formula")+" "+rel.getDescription());
		vacio.setDescripcionSinFuncion(JLanguage.translate("#Formula")+" "+rel.getDescription());
		vacio.setOrden(JTools.LPad("!",10,"0")+"_"+rel.getDescription());
		vacio.setSimple(false);
		return vacio;
	}
	public JRecords<BizCampoGallery> getAllCampos(BizAction a, boolean fields) throws Exception {
		String fuente = (a!=null)? a.getFilterMapValue("rel_id", ""): "";
  	return getAllCampos(fuente,fields);
	}
	public JRecords<BizCampoGallery> getAllCampos( String filter,boolean fields) throws Exception {
  	JRelation rel = this.getObjRelation();
		
  	JRecords<BizCampoGallery> recs =new JRecords<BizCampoGallery>(BizCampoGallery.class);
  	recs.setStatic(true);
  	if (rel==null) return recs;
  	recs = rel.getCacheRelation().getElement(rel.getClassTarget().getCanonicalName());
  	if (recs!=null) return recs;
  	recs = new JRecords<BizCampoGallery>(BizCampoGallery.class);
  	recs.setStatic(true);
		this.appendAllCampos(filter,  rel,null, recs, fields, "");
		recs.convertToHash("id");
		recs.convertToHash("campo_function");
		rel.getCacheRelation().addElement(rel.getClassTarget().getCanonicalName(), recs);
  	return recs;
	}
	
	public void addExtraCampos(BizCampos campos) throws Exception {
		this.extraFiltros=campos;
	}

	public JRecords<BizCampoGallery> getOnlyCampos(BizCustomList list, BizAction a, boolean fields, boolean addfolders) throws Exception {
		String fuente = (a!=null)? a.getFilterMapValue("rel_id", ""): "";//list.getRelId();
		String grupo = (a!=null)? a.getFilterMapValue("grupo", ""): "";
		String descr = (a!=null)? a.getFilterMapValue("descripcion", ""): "";
		JMap<String,BizCampoGallery> folders = JCollectionFactory.createMap();
   	return getOnlyCampos(list,fuente,grupo,descr,fields,addfolders,folders);
	}
	public JRecords<BizCampoGallery> getOnlyCampos(BizCustomList list, String filter,String fGrupo,String descr,boolean fields, boolean addfolders,JMap<String,BizCampoGallery> folders) throws Exception {
  	JRelation rel = this.getObjRelation();
  	
  	JRecords<BizCampoGallery> recs;
//  	recs = rel.getCacheOnlyCamposRelation().getElement(rel.getClassTarget().getCanonicalName());
//  	if (recs!=null) return recs;

  	recs = new JRecords<BizCampoGallery>(BizCampoGallery.class);
  	recs.setStatic(true);
		this.appendOnlyCampos(list,filter, fGrupo, descr, rel,null, recs, fields,addfolders, "", folders);
		recs.Ordenar("orden");
		
		
//		JIterator<BizCampoGallery> it = recs.getStaticIterator();
//		while (it.hasMoreElements()){
//			BizCampoGallery campo = it.nextElement();
//			PssLogger.logInfo("------------------------------> "+campo.getOrden());
//		}

		markUsed(recs);
  	return recs;
	}


	public void appendAllCampos(String fuente, JRelation relTarget, JRelations relations,JRecords<BizCampoGallery> recs, boolean fields, String includeds) throws Exception {

		if (relTarget.getClassTarget()==null) return;
		if (includeds.indexOf(relTarget.getClassTarget().getCanonicalName())!=-1) return;
		JRelations rels = relations;
		if (rels==null) 
			rels = relTarget.getTargetRelationMap();
		
		
		if (relations == null && fields) {
			recs.addItem(this.createCantidad(relTarget));
			recs.addItem(this.createFormula(relTarget));
		}

		JIterator<JRelation> iter = relTarget.getObjRecordTarget().getRelationMap().getList().getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();
			

			if (r.isRelationFixed() ) continue;

			r.setObjRelParent(relTarget);

			if (r.isRelationRaiz()) {
				// campos de la relacion
				JIterator<BizCampoGallery> iter2 = r.getCamposGallery().getStaticIterator();
				while (iter2.hasMoreElements()) {
					String grupo="";
					BizCampoGallery c2 = iter2.nextElement(); 
					if (c2.getCampo().equals(""))
						grupo = c2.getGrupo();
					else
						grupo = rels.getFieldGroup(fuente+relTarget.getId(),c2.getCampo(),c2.getFunction());
					if (grupo!=null && grupo.equals("")) continue;
					if (!c2.hasDescription()) continue;
					c2.setGrupo(grupo);
					recs.addItem(c2);
				}
			}	else
				this.appendAllCampos(fuente+relTarget.getId()+"_", r, rels, recs, fields,includeds+","+relTarget.getClassTarget().getCanonicalName());
			
		}

	}

	public void appendOnlyCampos(BizCustomList list,String fuente, String fGrupo,String descr,JRelation relTarget, JRelations relations,JRecords<BizCampoGallery> recs, boolean fields,boolean addfolders, String includeds,JMap<String, BizCampoGallery> folders) throws Exception {

		if (relTarget==null) return;
		if (relTarget.getClassTarget()==null) return;
		if (includeds.indexOf(relTarget.getClassTarget().getCanonicalName())!=-1) return;
		JRelations rels = relations;
		if (rels==null) 
			rels = relTarget.getTargetRelationMap();
		
		
		if (relations == null && fields) {
			recs.addItem(this.createCantidad(relTarget));
			recs.addItem(this.createFormula(relTarget));
		}
//		PssLogger.logInfo("Fuente: "+fuente);
		
		JIterator<JRelation> iter = relTarget.getObjRecordTarget().getRelationMap().getList().getIterator();
		while (iter.hasMoreElements()) {
			JRelation r = iter.nextElement();

			if (r.isRelationFixed() ) continue;

			r.setObjRelParent(relTarget);

			if (r.isRelationRaiz()) {
				// campos de la relacion
				JIterator<BizCampoGallery> iter2 = r.getOnlyCamposGallery(list).getStaticIterator();
				while (iter2.hasMoreElements()) {
					String grupo="";
					BizCampoGallery c2 = iter2.nextElement();
	
					if (!descr.equals("") && c2.getDescrCompleta().toLowerCase().indexOf(descr.toLowerCase())==-1) continue;
					boolean s = rels.getFieldGroupExact(fuente+relTarget.getId(),c2.getCampo(),c2.getFunction());
					grupo = rels.getFieldGroup(fuente+relTarget.getId(),c2.getCampo(),c2.getFunction());
					if (grupo!=null && grupo.equals("")) continue;
					if (!c2.hasDescription()) continue;
					if (addfolders && descr.equals("") && fGrupo.equals("")) {
						c2.setGrupo(grupo);
					} 
					if (!fGrupo.equals("") && grupo==null) continue;
					if (!fGrupo.equals("") && !grupo.equals(fGrupo)) continue;
					
					c2.setDescrCompleta(rels.getFullNameGroup(c2.getGrupo())+", "+c2.getDescripcion());//+"("+r.getBeautyDescrCampo()+")");
					c2.setDescripcionSinFuncion(r.getBeautyDescrCampo());
					c2.setOrden(c2.getDescrCompleta());
					if (!c2.getDescrCompleta().trim().startsWith(","))
						recs.addItem(c2);
					else if (fuente.equals("")) {
						c2.setDescrCompleta(c2.getDescripcion());
						recs.addItem(c2);
					} else			
						PssLogger.logInfo("Descartado: "+c2.getDescrCompleta()+"("+fuente+")");
					
				}
			}	else
				this.appendOnlyCampos(list,fuente+relTarget.getId()+"_",fGrupo,descr, r, rels, recs, fields,addfolders,includeds+","+relTarget.getClassTarget().getCanonicalName(),folders);
			
		}

	}
	public String addFolder(JRelation rel,JRecords<BizCampoGallery> recs, String grupo,JMap<String,BizCampoGallery> folders) throws Exception {
		BizCampoGallery vacio = folders.getElement(grupo);
		if (vacio!=null) return vacio.getOrden();
		vacio = rel.createCampoGallery();
		vacio.setCampo("");
		vacio.setFunction("");
		vacio.setFolder(true);
		vacio.setOrden(JTools.LPad(""+folders.size(),10,"0"));
		vacio.setRecordOwner(rel.getObjRecordTarget().getClass().getName());
		vacio.setRecordSource(rel.getObjRecordSource().getClass().getName());
		vacio.setRelId(rel.getId());
		String pg=rel.getTargetRelationMap().getFolderGroup().getElement(grupo);
		if (pg!=null&&!pg.equals("")&&!pg.equals("root_")) {
			String parentOrden= addFolder(rel, recs, pg, folders);
			vacio.setOrden(JTools.LPad(parentOrden,10,"0")+"_"+JTools.LPad(""+folders.size(),10,"0"));
			vacio.setGrupo(pg);
		} else
		 vacio.setGrupo("0");
		vacio.setRelIdDescr(rel.findDescrParent());
		vacio.setSerialDeep(rel.serializeDeep());
		vacio.setObjRelacion(rel);
		vacio.setDescripcion(grupo);
		vacio.setDescrCompleta(grupo);
		vacio.setDescripcionSinFuncion(grupo);
		vacio.setSimple(true);
		recs.addItem(vacio);
		folders.addElement(grupo, vacio);
		return vacio.getOrden();
	}
	
	public void markUsed(JRecords<BizCampoGallery> recs) throws Exception {
		recs.convertToHash("campo_serial");
		BizCampos campos = getObjAllCampos();
		JIterator<BizCampo> itC = campos.getStaticIterator();
		while (itC.hasMoreElements()) {
			BizCampo elem = itC.nextElement();
			String key = elem.getSerialDeep()+"|"+ elem.getCampo();
			BizCampoGallery cg = (BizCampoGallery)recs.findInHash("campo_serial", key);
			if (cg!=null) {
				if (elem.isRolFila()) 
					cg.setUse(cg.getUse()+"FILA ");
				else if (elem.isRolColumna()) 
					cg.setUse(cg.getUse()+"COLUMNA ");
				else if (elem.isRolCampo()) 
					cg.setUse(cg.getUse()+"CAMPO ");
				else if (elem.hasOrdenAscDesc()) 
					cg.setUse(cg.getUse()+"ORDEN ");
			}
		}
		
	}

	
	public static String extract(String dicc, long id) {
		StringTokenizer toks = new StringTokenizer(dicc,"|");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			if (!tok.trim().startsWith(id+"=")) 
				continue;
			return tok.trim().substring((id+"=").length());
		}
		return null;
	}
//  public void unserializeCampos(JRecords<BizCampo> campos) throws Exception {
//  	JIterator<BizCampo> iter = campos.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizCampo f = iter.nextElement();
//  		f.getObjRelation().setObjRelParent(this.createRelParent(f.getSerialDeep()));
//  	}
//  }
//  
//  public void unserializeEjes(JRecords<BizEje> ejes) throws Exception {
//  	JIterator<BizEje> iter = ejes.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizEje f = iter.nextElement();
//  		f.getObjRelation().setObjRelParent(this.createRelParent(f.getSerialDeep()));
//  	}
//  }

	@Override
	public String getDescripcion() throws Exception {
		return getDescription();
	}


//  public JRelation createRelParent(String deep) throws Exception {
//		if (deep==null) return null;
//		if (deep.equals("")) return null;
//		JRelation p = null;
//		JRelation anterior = null;
//		JStringTokenizer tk = JCollectionFactory.createStringTokenizer(deep, '|');
//		while (tk.hasMoreTokens()) {
//			String c = tk.nextToken();
//			int idx = c.indexOf("_");
//			JRecord rec = (JRecord)Class.forName(c.substring(0, idx)).newInstance();
//			p= rec.getRelationMap().findRel(c.substring(idx+1));
//			p.setObjRelParent(anterior);
//			anterior=p;
//  	}
//		return p;
//  }

  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action,boolean vistaPrevia) throws Exception {

  	BizCampos campos = new BizCampos();
		if (!action.isNullExtraDatos()) {
			campos.unSerialize(action.getParamExtraDatos());
			setObjAllCampos(campos,false);
		}
  	BizSqlEventHistory hist= new BizSqlEventHistory();
  	hist.setFecha(new Date());
  	hist.setIdevento(""+action.getIdevento());
  	hist.setCompany(getCompany());
  	hist.setFundamento(getDescripcion());
  	hist.setMensajeUsuario(action.getMensajeUsuario());
  	hist.setIdaction(action.getIdaction());
  	hist.setAccion(action.getAction());
  	hist.setDestinatario(action.getDestinatario());
  	if (!vistaPrevia) hist.setArchivoAsociado(action.getArchivoAsociado(a,hist));
  	
  	fillFilters(a);
  	if (this.isInforme()) {
  		hist.setMensaje(action.getMensajeAviso(a,hist));
  		return hist;
  	}
  	JRecords recs= getAllRecords();
  	long size=recs.selectCount();
  	if (size>1000 && vistaPrevia)
   		hist.setMensaje("Reporte de "+size+" registros. No se genera vista previa,puede demorar varios minutos la generacion.");
  	else
  		hist.setMensaje(action.getMensajeAviso(a,hist));
  	return hist;
  }
  
	public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
  	return "Hay un nuevo reporte disponible "+getDescripcion()+" "+JDateTools.DateToString(new Date()) ;
  }

	public void fillFilters(JFilterMap a ) throws Exception {
		fillFilters(a,0);
	}
	public void fillFilters(JFilterMap a ,int add) throws Exception {
    JRecords<BizDocListadoParam> recs = new JRecords<BizDocListadoParam>(BizDocListadoParam.class);
		if (a!=null) {
	    recs.setStatic(true);
			if (a!=null) {
	      JIterator<String> it = a.getMap().getKeyIterator();
	      while (it.hasMoreElements()) {
	      	String key=it.nextElement();
	      	if (key.startsWith("filtro") && !JTools.getNumberEmbedded(key).equals(""))  {
		      	String value=(String)a.getMap().getElement(key);
		      	BizDocListadoParam param = new BizDocListadoParam();
		      	param.setCompany(this.getCompany());
		      	param.setListId(this.getListId());
	      		param.setOrden(Long.parseLong(JTools.getNumberEmbedded(key))+add);
		       	param.setValue(URLDecoder.decode(value, CharEncoding.ISO_8859_1));
		      	recs.getStaticItems().addElement(param);
	      	}
	      }
			}
		}
    this.setObjParams(recs);
	}
	
	public String getExtraParamsInternalRequest() throws Exception {
  	BizCampos r = this.getObjAllCampos();
  	String args="";
  	int row=0;
  	JIterator<BizCampo> it = r.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizCampo c = it.nextElement();
  		JMap<String,String> cp = c.getAllFixedProperties(false);
  	  JIterator<JProperty> iter = c.getFixedProperties().getValueIterator();
  		while (iter.hasMoreElements()) {
  			JProperty p = iter.nextElement();
  			if (p.isRecords() || p.isHide() || p.isRecord()) 
  				continue;

  			String field = p.GetCampo();
  			String value = c.getProp(field).toString();
    		args+="&dgf_campos_row_"+row+"_fd-"+field+"="+value;
  		}
  	}
  	return args;
  }
	
	public String getCorreoAviso(JFilterMap a, BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (action.isAccionURL()||action.isAdjuntoOnly()) // envio correo de aviso
	  	return action.getObjPlantilla().generateDocSimple(hist,this);
		
		if (isInforme()) {
			GuiCustomList l = new GuiCustomListInforme();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			return action.getMensajeUsuario()+"<br/>"+ URLEncoder.encode("<html><body><img src=\""+JTools.convertHtml2JPG(new ByteArrayInputStream(l.getHtmlView(26,"htmlfull",a,true,false,false,false).getBytes()),1000,4000)+"\"></body></html>","ISO-8859-1").replace("+", "%20");
			
		}
		// el mail va con los datos
		if (action.isOutPantalla()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			return action.getMensajeUsuario()+"<br/>"+ URLEncoder.encode(l.getHtmlView(24,"html",a),"ISO-8859-1").replace("+", "%20");
		}
		if (action.isOutEXCEL()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			return URLEncoder.encode(l.getHtmlView(24,"excel",a),"ISO-8859-1").replace("+", "%20");
		}
		if (action.isOutCSV()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			return URLEncoder.encode(l.getHtmlView(24,"csv",a),"ISO-8859-1").replace("+", "%20");
		}

		return action.getMensajeUsuario()+"<br/>"+ getObjPlantilla().generateDoc(this.getCompany(),this,a);
  }
	public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		BizCampo f = getObjRelation().getObjRecordTarget().getRelationMap().getCamposMailing(campo);
		if (f==null && !campo.equals("")) throw new Exception("No se puede dividir el texto por el criterio "+campo);
		if (f!=null) {
			f.setCompany(this.getCompany());
			f.setListId(this.getListId());
			f.setDinamico(false);
			f.setOperacion(BizCampo.OPER_AND);
			f.setVisible(false);
			f.setHasFiltro(true);
			f.setValor(valor);
			this.addCampo(f);
		}
		String msg = getObjPlantilla().generateDoc(this.getCompany(),this,a);
		if (f!=null) {
//			this.getObjAllCampos().getStaticItems().removeElement(f);
			this.deleteCampo(f);
		}
		return msg;
  }
	public void addCampo(BizCampo f) throws Exception {
		this.getObjAllCampos().getStaticItems().addElement(f);
	
	}
	
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
		BizCampo f = getObjRelation().getObjRecordTarget().getRelationMap().getCamposMailing(campo);
		if (f==null && !campo.equals("")) throw new Exception("No se puede dividir el texto por el clriterio "+campo);
		if (f!=null) {
			f.setCompany(this.getCompany());
			f.setListId(this.getListId());
			f.setDinamico(false);
			f.setOperacion(BizCampo.OPER_AND);
			f.setValor(valor);
			f.setVisible(false);
			f.setHasFiltro(true);
			this.addCampo(f);
		}
		String msg;

		msg= imprimirResumen(a, false);

		if (f!=null) {
//			this.getObjAllCampos().getStaticItems().removeElement(f);
			this.deleteCampo(f);
		}
		return msg;
  }
	public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
		if (action.isOutPantalla()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			String file = l.hashCode()+".html";
			String content = l.getHtmlView(24,"html",a);
			JTools.MakeDirectory(JPath.PssPathTempFiles());
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutEXCEL()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			String file = l.hashCode()+".xlsx";//.xls
			String content = l.getHtmlView(24,"excel",a);
			JTools.MakeDirectory(JPath.PssPathTempFiles());
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isOutCSV()) {
			GuiCustomList l = new GuiCustomList();
			l.setRecord(this);
			l.GetcDato().fillFilters(a);
			String file = l.hashCode()+".csv";
			String content = l.getHtmlView(24,"csv",a);
			JTools.MakeDirectory(JPath.PssPathTempFiles());
			JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
			return file;
		}
		if (action.isAdjuntoOnly()) 
			return imprimirResumen(a, false);
  	return imprimirResumen(a, true);
  }

	@Override
	public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
		return null;
	}
	
	public void mensajeEnviado(String marca) throws Exception {
		
	};
	
  BizPlantilla objPlantilla;
  public BizPlantilla getObjPlantilla() throws Exception {
  	if (objPlantilla!=null) return objPlantilla;
  	BizPlantilla p = new BizPlantilla();
  	p.dontThrowException(true);
  	if (!p.Read(getModelo())) return BizCompany.getObjPlantilla(getCompany(), "sys_reporte");
  	return objPlantilla = p;
  }
  
  public void ExecEliminarErroneos() throws Exception {
		JExec oExec = new JExec(this, "processEliminarErroneos") {

			@Override
			public void Do() throws Exception {
				processEliminarErroneos();
			}
		};
		oExec.execute();
	}
  public static void processEliminarErroneos() throws Exception {

  	try {
			JRecords<BizCustomList> events = new JRecords<BizCustomList>(BizCustomList.class);
			JIterator<BizCustomList> it =events.getStaticIterator();
			while (it.hasMoreElements()) {
				BizCustomList custom = it.nextElement();
				if (!custom.isInvisible()) continue;
				BizSqlEvent event = new BizSqlEvent();
				event.addFilter("company" , custom.getCompany());
				event.addFilter("custom_list", custom.getListId());
				if (event.exists()) continue;
				custom.processDelete();
				Thread.yield();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 
  
  }
  BizCampo objOrden;
  public BizCampo getObjRanking() throws Exception {
  	if (objOrden!=null) return objOrden;
  	BizCampos orden = getObjOrders();
  	if (orden.getStaticItems().size()==0) return null;
  	return (this.objOrden=orden.getFirstRecord());
  }

  
	public static final String PRESENTATION_TYPE_DATO = "PresentationDato";
	public static final String PRESENTATION_TYPE_LIST = "PresentationList";
	public static final String PRESENTATION_TYPE_AGRUPADO = "PresentationGroup";
	public static final String PRESENTATION_TYPE_MATRIX = "PresentationMatrix";

  public static JRecords<BizVirtual> getPresentationTypes() throws Exception {
		JRecords<BizVirtual> records =JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(PRESENTATION_TYPE_DATO, "Dato", 15200));
		records.addItem(JRecord.virtualBD(PRESENTATION_TYPE_LIST, "Lista", 15203));
		records.addItem(JRecord.virtualBD(PRESENTATION_TYPE_AGRUPADO, "Agrupado", 15204));
		records.addItem(JRecord.virtualBD(PRESENTATION_TYPE_MATRIX, "Matriz", 15205));
		records.appendRecords(Graph.getGraphTypes());
		return records;
	}
	

	public static final String PRESENTATION_INFO_GRID1ROW = JWinGridExpand.GRID1ROW;
	public static final String PRESENTATION_INFO_GRIDROW = JWinGridExpand.GRIDROW;
	public static final String PRESENTATION_INFO_GRIDCOLUMN = JWinGridExpand.GRIDCOLUMN;
	public static final String PRESENTATION_INFO_GRID2PAR1IMPAR = JWinGridExpand.GRID2PAR1IMPAR;
	public static final String PRESENTATION_INFO_GRIDFREE = JWinGridExpand.GRIDFREE;

  public static JRecords<BizVirtual> getPresentationInformeTypes() throws Exception {
		JRecords<BizVirtual> records =JRecords.createVirtualBDs();
		records.addItem(JRecord.virtualBD(PRESENTATION_INFO_GRID1ROW, "1 por Fila", 15210));
		records.addItem(JRecord.virtualBD(PRESENTATION_INFO_GRIDROW, "Filas", 15210));
		records.addItem(JRecord.virtualBD(PRESENTATION_INFO_GRIDCOLUMN, "Columnas", 15211));
		records.addItem(JRecord.virtualBD(PRESENTATION_INFO_GRID2PAR1IMPAR, "2 columns y 1 fila", 15212));
		records.addItem(JRecord.virtualBD(PRESENTATION_INFO_GRIDFREE, "Libre", 15213));
		return records;
	}
  
  public void processChat(String chat) throws Exception {
  	PssLogger.logInfo("Respuesta: "+chat);
  	Report  report = JSONParser.parseJson(chat.trim());
		String out = "";
		boolean porFechaEmision=false;
		boolean porFechaSalida=false;
		BizCampo order = null;
		String ascDesc=null;
		int limite=0;
		boolean refAnulado=false;

		JRecords<BizCampoGallery> recs = getOnlyCampos(this, null, true, true);
		recs.convertToHash("descr_completa");
		recs.convertToHash("campo");

		if (report.getType().equalsIgnoreCase("grilla"))
			this.setAgrupado(PRESENTATION_TYPE_MATRIX);
		else if (report.getType().equalsIgnoreCase("agrupado"))
			this.setAgrupado(PRESENTATION_TYPE_AGRUPADO);
		else if (report.getType().equalsIgnoreCase("lista"))
			this.setAgrupado(PRESENTATION_TYPE_LIST);
		else if (report.getType().equalsIgnoreCase("dato"))
			this.setAgrupado(PRESENTATION_TYPE_DATO);
		
		
		
		
		for (Command comm : report.getListCommand()) {
			refAnulado= (comm.getField().toLowerCase().indexOf("anulado")!=-1);
			if (comm.getAccion()!=null && comm.getAccion().equalsIgnoreCase("quitar")) {
				JIterator<BizCampo> it=  this.getObjAllCampos().getStaticIterator();
				while (it.hasMoreElements()) {
					BizCampo campo= it.nextElement();
					if (campo.getDescrCampo().equalsIgnoreCase(comm.getField())) {
						this.removeCampo(campo);
						break;
					}
				}
				continue;
			}
			String campoName = comm.getField();
			if (comm.getField().toLowerCase().indexOf("#cantidad")!=-1) {
				campoName = recs.getStaticItems().getElementAt(0).getDescrCompleta();
			}
			
			BizCampoGallery campoGallery = (BizCampoGallery)recs.findInHash("descr_completa",campoName);
  		if (campoGallery==null) {
  			campoGallery = (BizCampoGallery)recs.findInHash("campo",campoName);
    		if (campoGallery==null) {
    			out+="Ignora "+comm.getField()+"\r\n";
      		continue;
    		} 
  		}
  		out+="Agregar "+comm.getField()+"\r\n";
  			
  		BizCampo campo = campoGallery.buildCampo(this);
  		if (comm.getObject().equalsIgnoreCase("columna"))
    		campo.setOrientacion(BizCampo.COLUMNA);
  		if (comm.getObject().equalsIgnoreCase("fila"))
    		campo.setOrientacion(BizCampo.FILA);
  		if (comm.getObject().equalsIgnoreCase("campo"))
    		campo.setOrientacion("N");
  		if (comm.getObject().equalsIgnoreCase("filtro")) {
    		campo.setOrientacion("N");
    		campo.setHasFiltro(true);
    		campo.setVisible(false);
    		campo.setFuncion(null);
  		} else
  			campo.setVisible(true);
  	  		
  		porFechaEmision |= comm.getField().equalsIgnoreCase("Boleto, Fechas, Fecha emisión");
  	  porFechaSalida |= comm.getField().equalsIgnoreCase("Boleto, Fechas, Fecha Salida");

  	  if (comm.getOperator()!=null) {
  			if (comm.getOperator().equalsIgnoreCase("in"))
    			campo.setOperador(BizFuncion.FUNTION_INTERVALO);
  			else
  				campo.setOperador(comm.getOperator());
  		}
			if (comm.getField().toLowerCase().indexOf("#cantidad")!=-1) {
				campo.setFuncion(BizCampo.FUNTION_COUNT);
			}
			else if (comm.getFunction()!=null && !comm.getFunction().equals("INTERVAL")) {
  				campo.setFuncion(comm.getFunction());
  		}
  		if (this.isMatriz() && campo.isCampo() && !campo.hasFiltro() && !campo.hasFunction()) {
  			campo.setFuncion(BizCampo.FUNTION_SUM);
  		}
  		
  		if (campo.isCampo()) {
  			if (comm.getPorcentaje()!=null && comm.getPorcentaje().equalsIgnoreCase("si"))
    			campo.setPorcentaje(true);
    	
  		}
  		
  		if (comm.getValue()!=null)
  			campo.setValor(comm.getValue());
  		if (comm.getValueFrom()!=null)
  			campo.setValor(comm.getValueFrom().compareTo(comm.getValueTo())>=0?comm.getValueTo():comm.getValueFrom());  			
  		if (comm.getValueTo()!=null)
  			campo.setValor2(comm.getValueFrom().compareTo(comm.getValueTo())>=0?comm.getValueFrom():comm.getValueTo());

  		if (comm.getOrden()!=0 ) {
  			if (!isMatriz()) {
	  			campo.setOrden(comm.getOrden());
	    		if (comm.getSentidoOrden()!=null)
	    			campo.setOrdenAscDesc(comm.getSentidoOrden().toLowerCase().indexOf("asc")!=-1?BizCampo.ORDER_ASC:BizCampo.ORDER_DESC);  		
	    		if (comm.getLimite()!=0) {
	    			campo.setOrdenLimite(comm.getLimite());
	    		}
	    	} else {
	    		order=campo;
	    		if (comm.getSentidoOrden()!=null) ascDesc=comm.getSentidoOrden().toLowerCase().indexOf("asc")!=-1?BizCampo.ORDER_ASC:BizCampo.ORDER_DESC;
	    		limite=comm.getLimite();
	    	}
  		}
  		BizCampo oldCampo = this.findCampo(campo.getNameField());
  		if (oldCampo==null || oldCampo.getFuncion()!=campo.getFuncion()) {
    		this.addCampo(campo);
  		} 
  		else {
  			oldCampo.copyCommonsNotKeysProperties(campo, false);
  		}
  	}
		
		if (isMatriz()&&order!=null) {
			JIterator<BizCampo> it=  this.getObjAllCampos().getStaticIterator();
			while (it.hasMoreElements()) {
				BizCampo campo= it.nextElement();
				if (campo.isColumna()) {
					campo.setCampoOrden(order.getNameField());
					if (limite!=0) campo.setOrdenLimite(limite);
					campo.setOrdenAscDesc(ascDesc);
					break;
				}
			}
			
		}
		
		if (porFechaSalida) {
			if (!refAnulado) {
		 		BizCampo oldCampo = this.findCampoByField("void");
	  		if (oldCampo==null) {
					BizCampoGallery campoGallery = (BizCampoGallery)recs.findInHash("descr_completa","Boleto, Anulado?");
					BizCampo campo = campoGallery.buildCampo(this);
					this.addCampo(campo);
	  		}				
			}

		
	 		BizCampo oldCampo2 = this.findCampoByField("reemitted");
  		if (oldCampo2==null) {
				BizCampoGallery campoGalleryRem = (BizCampoGallery)recs.findInHash("descr_completa","Boleto, Revisado?");
				BizCampo campoRem = campoGalleryRem.buildCampo(this);
				this.addCampo(campoRem);
  		}
  	} 
		else if (porFechaEmision) {
			if (!refAnulado) {
		 		BizCampo oldCampo = this.findCampoByField("void");
	  		if (oldCampo==null) {
					BizCampoGallery campoGallery = (BizCampoGallery)recs.findInHash("descr_completa","Boleto, Anulado?");
					BizCampo campo = campoGallery.buildCampo(this);
					this.addCampo(campo);
	  		}		
			}
		} 
		
	

		
		
  	setHistoryBot("<div style=\"color: white;padding:10px;margin:10px;width:70%;border-radius: 5px;background-color: grey;\">"+getChatBot()+
  			    "</div><div style=\"color: white;padding:10px;margin-top:10px;margin-left:10px;margin-bottom:10px;margin-left:90px;width:80%;border-radius: 5px;background-color: darkgray;\">"+convertToHTML(chat)+"</div>"+getHistoryBot());
  	setChatBot("");
  }
	
  
  String convertToHTML(String in) throws Exception {
  	String out=in;
  	out = JTools.replace(out, "```json", "<code>");
  	out = JTools.replace(out, "```", "</code>");
  	out = JTools.replace(out, "\n", "<br>");

  	return out;
  }

}
