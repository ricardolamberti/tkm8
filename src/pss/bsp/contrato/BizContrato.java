package pss.bsp.contrato;

import java.net.URLEncoder;
import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalles;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.contrato.logica.JContratoRuta;
import pss.bsp.monitor.log.BizBspLog;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.IActionData;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptPie;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JConcatenar;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.BizPNRTicket;

public class BizContrato extends JRecord  implements IActionData {

	public static String OBJETIVOS = "OBJ";
	public static String CONTRATOS = "CONT";
	public static String SLIM = "SLIM";

	public static String ACTIVE = "S";
	public static String INACTIVE = "N";
	public static String NEW = "O";
	
  private JString pDescripcion = new JString();
  private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pObservaciones = new JString();
  private JString pActive = new JString();
  private JString pAtencion = new JString();
  private JString pTipoContrato = new JString();
  private JDate pFechaDesde = new JDate();
  private JDate pFechaHasta = new JDate();
  private JDate pFechaHastaReplicacion = new JDate();
  private JDate pFechaDesdeCalculo = new JDate();
  private JDate pFechaCalculo = new JDate();
  private JDateTime pFechaVersionCambio = new JDateTime();
  private JDateTime pFechaVersionCalculo = new JDateTime();
  private JLong pReservas = new JLong() {
  	public void preset() throws Exception {
  		pReservas.setValue(getReservas());
  	};
  };
  private JCurrency pGanancia = new JCurrency() {
  	public void preset() throws Exception {
  		pGanancia.setSimbolo(true);
  		pGanancia.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
  		pGanancia.setValue(getGanancia());
  	};
  };
  private JCurrency pGananciaDolares = new JCurrency() {
  	public void preset() throws Exception {
  		pGananciaDolares.setSimbolo(true);
			pGananciaDolares.setMoneda("USD");
			pGananciaDolares.setValue(getGananciaDolares());
  	};
  };
  private JCurrency pBaseComisionable = new JCurrency() {
  	public void preset() throws Exception {
  		pBaseComisionable.setSimbolo(true);
  		pGanancia.setMoneda(BizUsuario.getUsr().getObjCountry().GetMonedaLocal());
    	pBaseComisionable.setValue(getBaseComisionable());
  	};
  };
  private JCurrency pBaseComisionableDolares = new JCurrency() {
  	public void preset() throws Exception {
  		pBaseComisionableDolares.setSimbolo(true);
  		pBaseComisionableDolares.setMoneda("USD");
  		pBaseComisionableDolares.setValue(getBaseComisionableDolares());
  	};
  };
  private JLong pCantDetalle = new JLong() {
  	public void preset() throws Exception {
  		pCantDetalle.setValue(getCantDetalles());
  	};
  };
  private JString pResultado = new JString() {
  	public void preset() throws Exception {
  		pResultado.setValue(getResultado());
  	};
  };
  private JBoolean pHasAtencion = new JBoolean() {
  	public void preset() throws Exception {
  		pHasAtencion.setValue(hasAtencion());
  	};
  };
  private JFloat pNivelCumplimiento = new JFloat() {
  	public void preset() throws Exception {
  		pNivelCumplimiento.setValue(getNivelCumplimiento());
  	};
  	public int getCustomPrecision() throws Exception {return 2;};
  };
  private JString pImagen1 = new JString() {
  	public void preset() throws Exception {pImagen1.setValue(getImagen1(false));}
  };
  private JString pImagenPrint = new JString() {
  	public void preset() throws Exception {pImagenPrint.setValue(getImagen1(true));}
  };
	protected JObjBDs<BizDetalle> pDetalle= new JObjBDs<BizDetalle>() {
		public void preset() throws Exception {
			pDetalle.setValue(getObjDetalles());
		}
	};
	
	 private JHtml pDescripcionLarga = new JHtml() {
		 public void preset() throws Exception {
			 if (getFechaDesde()==null) {
				 pDescripcionLarga.setValue(getDescripcion()+ "<br/>");
				 
			 } else
				 pDescripcionLarga.setValue(getDescripcion()+ "<br/>"+JDateTools.DateToString(getFechaDesde())+" - "+JDateTools.DateToString(getFechaHasta()));
		 };
	 };
		public String getPeriodo() throws Exception {
			
			int dias = JDateTools.getDaysBetween(getFechaDesde(), getFechaHasta());
			if (dias>12*28) return "ANUAL";
			else if (dias>6*28) return "SEMESTRAL";
			else if (dias>4*28) return "TRIMESTRAL";
			else if (dias>3*28) return "CUATRIMESTRAL";
			else if (dias>2*28) return "BIMESTRAL";
			else if (dias>=1*28) return "MENSUAL";
			
			return "";
		}
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setObservaciones(String zValue) throws Exception {    pObservaciones.setValue(zValue);  }
  public String getObservaciones() throws Exception {     return pObservaciones.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setFechaDesde(Date zValue) throws Exception {    pFechaDesde.setValue(zValue);  }
  public Date getFechaDesde() throws Exception {     return pFechaDesde.getValue();  }
  public void setActive(String zValue) throws Exception {    pActive.setValue(zValue);  }
  public String getActive() throws Exception {     return pActive.getValue();  }
  public void setAtencion(String zValue) throws Exception {    pAtencion.setValue(zValue);  }
  public String getAtencion() throws Exception {     return pAtencion.getValue();  }
  public boolean hasAtencion() throws Exception {     return pAtencion.isNotNull() && !pActive.getValue().equals("");  }
  public void setTipoContrato(String zValue) throws Exception {    pTipoContrato.setValue(zValue);  }
  public String getTipoContrato() throws Exception {     return pTipoContrato.getValue();  }
  public boolean isObjetivo() throws Exception {     return pTipoContrato.isNotNull() && pTipoContrato.getValue().equals("O");  }
  public boolean isContrato() throws Exception {     return pTipoContrato.isNull() || pTipoContrato.getValue().equals("C");  }
  

  public void setNullAtencion() throws Exception {    pAtencion.setNull();  }
  public boolean isNullAtencion() throws Exception { return  pAtencion.isNull(); } 
  public boolean isNullFechaDesde() throws Exception { return  pFechaDesde.isNull(); } 
  public void setNullToFechaDesde() throws Exception {  pFechaDesde.setNull(); } 
  public void setFechaHasta(Date zValue) throws Exception {    pFechaHasta.setValue(zValue);  }
  public Date getFechaHasta() throws Exception {     return pFechaHasta.getValue();  }
  public boolean isNullFechaHasta() throws Exception { return  pFechaHasta.isNull(); } 
  public void setNullToFechaHasta() throws Exception {  pFechaHasta.setNull(); } 

  public void setFechaVersionCambio(Date zValue) throws Exception {    pFechaVersionCambio.setValue(zValue);  }
  public Date getFechaVersionCambio() throws Exception {     return pFechaVersionCambio.getValue();  }

  public void setFechaVersionCalculo(Date zValue) throws Exception {    pFechaVersionCalculo.setValue(zValue);  }
  public Date getFechaVersionCalculo() throws Exception {     return pFechaVersionCalculo.getValue();  }

  public void setFechaHastaReplicacion(Date zValue) throws Exception {    pFechaHastaReplicacion.setValue(zValue);  }
  public Date getFechaHastaReplicacion() throws Exception {     return pFechaHastaReplicacion.getValue();  }
  public boolean isNullFechaHastaReplicacion() throws Exception { return  pFechaHastaReplicacion.isNull(); } 
  public void setNullToFechaHastaReplicacion() throws Exception {  pFechaHastaReplicacion.setNull(); } 

  public boolean isActive() throws Exception {     return pActive.getValue().equals(ACTIVE);  }
  public boolean isInactive() throws Exception {     return pActive.getValue().equals(INACTIVE);  }
  public boolean isNew() throws Exception {     return pActive.getValue().equals(NEW);  }

  String resultado;
  double nivel=0;
  double ganancia=0;
  double gananciaDolares=0;
  double baseCom=0;
  double baseComDolares=0;
  long cantDetalles=0;
  public String getResultado() throws Exception { 
  	if (resultado!=null && !resultado.equals("")) return resultado;
  	String res="";
  	JRecords<BizDetalle> detalles = getDetalles();
  	JIterator<BizDetalle> it = detalles.getStaticIterator();
  	long cantDet=0;
  	double acumNiveles=0;
  	baseComDolares=0;
  	baseCom=0;
  	while (it.hasMoreElements()) {
  		BizDetalle det = it.nextElement();
  		if (det.getKicker()) continue;
  		acumNiveles+=det.getNivelCumplimiento();
  		cantDet++;
  		res+= (res.equals("")?"":", ")+det.getDescrVariable()+" al "+det.getNivelCumplimiento(); 
  		
  		if (det.isDolares())
  			gananciaDolares+=det.getGanancia();
  		else
  			ganancia+=det.getGanancia();
  		
   		if (det.isDolares())
   		 	baseComDolares+=det.getValorTotal();
   		else
   			baseCom+=det.getValorTotal();
  	}
  	nivel = cantDet==0?0:acumNiveles /cantDet;
  	cantDetalles=cantDet;
  	return resultado=res;  
  }
  public double getNivelCumplimiento() throws Exception { getResultado(); return nivel;}
  public double getGanancia() throws Exception { getResultado(); return ganancia;}
  public double getGananciaDolares() throws Exception { getResultado(); return gananciaDolares;}
  public long getCantDetalles() throws Exception { getResultado(); return cantDetalles;}
  public double getBaseComisionable() throws Exception { getResultado(); return baseCom;}
  public double getBaseComisionableDolares() throws Exception { getResultado(); return baseComDolares;}

  @Override
  public void clean() throws Exception {
  	resultado="";
  	nivel=0;
  	super.clean();
  }
  
  public JRecords<BizDetalle> getDetalles() throws Exception {
  	JRecords<BizDetalle> detalles = new JRecords<BizDetalle>(BizDetalle.class);
  	detalles.addFilter("id", getId());
  	detalles.addOrderBy("prioridad","desc");
  	return detalles;
  	
  }
  public JRecords<BizDetalle> getAllDetalles() throws Exception {
  	JRecords<BizDetalle> detalles = new JRecords<BizDetalle>(BizDetalle.class);
  	return detalles;
  	
  }

  /**
   * Constructor de la Clase
   */
  public BizContrato() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "nivel", pNivelCumplimiento);
    this.addItem( "ganancia", pGanancia);
    this.addItem( "ganancia_dolares", pGananciaDolares);
    this.addItem( "cant_detalles", pCantDetalle);
    this.addItem( "base_comisionable", pBaseComisionable);
    this.addItem( "base_comisionable_dolares", pBaseComisionableDolares);
    this.addItem( "resultado", pResultado);
    this.addItem( "observaciones", pObservaciones);
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "fecha_desde", pFechaDesde );
    this.addItem( "fecha_hasta_rep", pFechaHastaReplicacion );
    this.addItem( "fecha_desde_calculo", pFechaDesdeCalculo );
    this.addItem( "fecha_calculo", pFechaCalculo );
    this.addItem( "fecha_version_cambio", pFechaVersionCambio );
    this.addItem( "fecha_version_calculo", pFechaVersionCalculo );
     this.addItem( "descripcion", pDescripcion );
    this.addItem( "descripcion_larga", pDescripcionLarga );
    this.addItem( "id", pId );
    this.addItem( "active", pActive );
    this.addItem( "atencion", pAtencion );
    this.addItem( "tipo_contrato", pTipoContrato );
    this.addItem( "has_atencion", pHasAtencion );
    this.addItem( "company", pCompany );
    this.addItem( "imagen1", pImagen1 );
    this.addItem( "imagen_print", pImagenPrint );
    this.addItem( "detalles", pDetalle );
    this.addItem( "reservas", pReservas );
 }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( VIRTUAL, "nivel", "Nivel cumplimiento", true, false, 18 ,2);
    this.addFixedItem( VIRTUAL, "ganancia", "Ganancia", true, false, 18 ,2);
    this.addFixedItem( VIRTUAL, "ganancia_dolares", "Ganancia USD", true, false, 18 ,2);
    this.addFixedItem( VIRTUAL, "cant_detalles", "#Items", true, false, 18 );
    this.addFixedItem( VIRTUAL, "base_comisionable", "Base Com.", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "base_comisionable_dolares", "Base Com. USD", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "resultado", "Resultado", true, false, 250 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha Hasta", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_hasta_rep", "Fecha Hasta Replicacion", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha Desde", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_desde_calculo", "Fecha Desde Calculo", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_calculo", "Fecha Hasta Calculo", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_version_cambio", "Fecha version cambio", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_version_calculo", "Fecha version Calculo", true, false, 18 );
     this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "observaciones", "Observaciones", true, false, 4000 );
    this.addFixedItem( FIELD, "active", "Activo", true, false, 1 );
    this.addFixedItem( FIELD, "tipo_contrato", "Tipo contrato", true, false, 1 );
     this.addFixedItem( FIELD, "atencion", "Atencion", true, false, 500 );
    this.addFixedItem( KEY, "id", "Id", false, false, 32 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( VIRTUAL, "imagen1", "imagen1", true, false, 64 );
    this.addFixedItem( VIRTUAL, "imagen_print", "imagenPrint", true, false, 64 );
    this.addFixedItem( VIRTUAL, "has_atencion", "!", true, false, 1 );
  	this.addFixedItem( RECORDS, "detalles", "Objetivos", true, false, 18).setClase(BizDetalle.class);
  	this.addFixedItem( VIRTUAL, "descripcion_larga", "Descripcion", true, true, 250 );
   	this.addFixedItem( VIRTUAL, "reservas", "Reservas", true, true, 18 );
     }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_contrato"; }

  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(GuiContratos.class.getName());
  	rels.hideField("id");
  	
  	JRelation rel; 
  	rel = rels.addRelationForce(10, "restriccion usuario");
   	rel.addFilter(" (bsp_contrato.company in (COMPANY_CUSTOMLIST)) ");
// 	rel.addJoin("bsp_contrato.company", BizUsuario.getUsr().getCompany());

  	JRelation r=rels.addRelationChild(1, "Objetivos", BizDetalle.class);
  	r.addJoin("bsp_contrato.id", "bsp_contrato_detalle.id");

		rels.addFolderGroup("Contrato", null);
		rels.addFolderGroup("Objetivos", "Contrato");
		rels.addFieldGroup("3", "*", "*", "Contrato");
		rels.addFieldGroup("3_1", "*", "*", "Objetivos");

  }
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
  public boolean readByDescription( String zCompany,String zDescr ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "active",  "S" ); 
    addFilter( "descripcion",  zDescr ); 
    return read(); 
  } 
  
  public void markForChange() throws Exception {
  	setFechaVersionCambio(new Date());
  	update();
  }
  public void changeDoIt() throws Exception {
  	setFechaVersionCalculo(getFechaVersionCambio());
  	update();
  }
	public String getImagen1(boolean print) throws Exception {
		GuiDetalles w = new GuiDetalles();

		w.getRecords().addFilter("id", getId());
		w.getRecords().addFilter("kicker", "S","<>");
		w.getRecords().addFilter("logica", JContratoRuta.class.getName(),"<>");
		w.getRecords().addOrderBy("grupo");
		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		Graph gr = new GraphScriptPie();
		gr.setPrint(print);
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Anual");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getDescripcion());
		gr.setTitle(getDescripcion());
		ModelMatrix mg=new ModelMatrix();
		
		mg.addColumn("grupo", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn("descr_variable", ModelMatrix.GRAPH_FUNCTION_DATASET);
		mg.addColumn("valor_totalcontrato", ModelMatrix.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    gr.setWithDownload(true);
    gr.setWithZoom(true);
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(800, 800).replace("script:", "");
		}
		return null;
	}
	public boolean isActiveCompanyAndContract() throws Exception {
		if (!isActive()) return false;
		BizBSPCompany bsp = BizBSPCompany.getObjBSPCompany(getCompany());
		return !bsp.getObjExtraData().getInactiva() && (bsp.getObjExtraData().getVersion()==6||bsp.getObjExtraData().getVersion()==4);
	}
	public String imprimirResumen() throws Exception {
		JRecord[]recs = new JRecord[3];
		recs[0]=BizBSPCompany.getObjBSPCompany(this.getCompany());
		recs[1]=this;
		recs[2]=null;
		
		int i = 0;
		String[] files = new String[((int)getObjDetalles().sizeStaticElements()*2)+1];
		files[i++] = JPath.PssPathTempFiles() +"/"+ BizPlantilla.generateListadoTemporario(this.getCompany(),true,recs,"sys_contrato");
		JIterator<BizDetalle> it =getObjDetalles().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle detalle = it.nextElement();
			BizDetalle newdet = detalle.getObjLogicaInstance().getBiz();
			newdet.copyProperties(detalle);
			String f = newdet.imprimirResumen();
			files[i++] = JPath.PssPathTempFiles() + "/" +f;
		}
		String tempfile = getCompany() + "/concat" + this.hashCode() + ".html";
		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +  getCompany());
		JConcatenar.concatenarHTML(files, JPath.PssPathTempFiles() + "/" + tempfile);
	
		
		return tempfile;
	}
	 
	public JRecords<BizDetalle> getObjDetalles() throws Exception {
	  JRecords<BizDetalle> records = new JRecords<BizDetalle>(BizDetalle.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("id", this.getId());
		records.addOrderBy("kicker","ASC");
		records.addOrderBy("descripcion","DESC");
		return records;
	}

	public void execProcessFinAtencion() throws Exception {
		JExec oExec = new JExec(this, "processFinAtencion") {

			@Override
			public void Do() throws Exception {
				processFinAtencion();
			}
		};
		oExec.execute();
	}
	public void processFinAtencion() throws Exception {
		setNullAtencion();
		update();
	}
	public void execProcessSigPeriodo() throws Exception {
		JExec oExec = new JExec(this, "processSigPeriodo") {

			@Override
			public void Do() throws Exception {
				processSigPeriodo();
			}
		};
		oExec.execute();
	}
	
	public void execProcessCopiarOtraEmpresa(final BizCompany compania) throws Exception {
		JExec oExec = new JExec(this, "processCopiarOtraEmpresa") {

			@Override
			public void Do() throws Exception {
				processCopiarOtraEmpresa(compania);
			}
		};
		oExec.execute();
	}
	
	public void execProcessActive() throws Exception {
		JExec oExec = new JExec(this, "processActive") {

			@Override
			public void Do() throws Exception {
				processActive();
			}
		};
		oExec.execute();
	}
	public void execProcessRepoblar() throws Exception {
		JExec oExec = new JExec(this, "processRepoblar") {

			@Override
			public void Do() throws Exception {
				processRepoblar();
			}
		};
		oExec.execute();
	}
	public void execProcessRecalcular() throws Exception {
		JExec oExec = new JExec(this, "processRepoblar") {

			@Override
			public void Do() throws Exception {
				processRecalcular();
			}
		};
		oExec.execute();
	}
	public void execProcessRepoblar2() throws Exception {
		JExec oExec = new JExec(this, "processRepoblar2") {

			@Override
			public void Do() throws Exception {
				processRepoblar2();
			}
		};
		oExec.execute();
	}
	
	
	public void processRepoblar2() throws Exception {
		JIterator<BizDetalle> it=getAllDetalles().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			
			BizDetalle d=det.getObjLogicaInstance().getBiz();
			d.copyProperties(det);
			if (d.getObjEvent()!=null&&d.getObjEvent().getObjCustomList()!=null){
				d.getObjEvent().cloneCustomListExpress();
//				d.getObjEvent().processUpdate();
			}
			if (d.getObjEventGanancia()!=null&&d.getObjEventGanancia().getObjCustomList()!=null) {
				d.getObjEventGanancia().cloneCustomListExpress();
//				d.getObjEventGanancia().processUpdate();
			}
		}
	}
	

	public void execProcessCheckForCalculo() throws Exception {
		JExec oExec = new JExec(this, "processCheckForCalculo") {

			@Override
			public void Do() throws Exception {
				processCheckForCalculo();
			}
		};
		oExec.execute();
	}
	public void processCheckForCalculo() throws Exception {
		if (pFechaCalculo.isNotNull()) {
			Calendar calc = Calendar.getInstance();
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(new Date());
			calc.setTime(pFechaCalculo.getValue());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			if (calc.after(ayer)) return;
		}
		JIterator<BizDetalle> it=getDetalles().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			BizDetalle d=det.getObjLogicaInstance().getBiz();
			d.copyProperties(det);
			d.processCheckForCalculo();
		}
		pFechaCalculo.setValue(new Date());
		update();
	}
	
	public void processRecalcular() throws Exception {
		try {
			Date fecha = new Date();
			JIterator<BizDetalle> it=getDetalles().getStaticIterator();
			while (it.hasMoreElements()) {
				BizDetalle det = it.nextElement();
				BizDetalle d=det.getObjLogicaInstance().getBiz();
				d.copyProperties(det);
				d.processPopulate(null,true);
				if (isContrato())
					new BizPNRTicket().procCalculeOverDetalle(det,null,null,fecha);
			   
			}
			changeDoIt();
		} catch (Exception e) {
			BizBspLog.log("DEFAULT", "PRINCIPAL", BizBspLog.BSPLOG_MODULO_GENERAL, BizBspLog.BSPLOG_ERROR , e.getMessage(), null, null, null, 0, 0, 0);

			throw e;
		}
	}
	public void processRepoblar() throws Exception {
		JIterator<BizDetalle> it=getDetalles().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			BizDetalle d=det.getObjLogicaInstance().getBiz();
			d.copyProperties(det);

			if (d.getObjEvent()!=null)
				d.getObjEvent().processPopulate(null, null);
			if (d.getObjEventGanancia()!=null)
				d.getObjEventGanancia().processPopulate(null, null);
			if (d.getObjEventAuxiliar()!=null)
				d.getObjEventAuxiliar().processPopulate(null, null);
		}
	}
	
	public void processActive() throws Exception {
		if (isNew()) setActive(ACTIVE);
		else if (isActive()) setActive(INACTIVE);
		else if (!isNullAtencion())setActive(ACTIVE);
		else if (isInactive()) setActive(ACTIVE);
		setNullAtencion();
		update();
	}
	
	@Override
	public void processDelete() throws Exception {
		JRecords<BizDetalle> det = getObjDetalles();
		det.processDeleteAll();
		super.processDelete();
	}
	
	public synchronized void execProcessBuildNextContrato(final boolean news) throws Exception {
		JExec oExec = new JExec(this, "buildNextContrato") {

			@Override
			public void Do() throws Exception {
				buildNextContrato(news);
			}
		};
		oExec.execute();
	}

	public  BizContrato buildNextContrato(boolean news) throws Exception {
		Calendar fechaIni = Calendar.getInstance();
		Calendar fechaFin = Calendar.getInstance();
		Calendar fechaAct = Calendar.getInstance();
		Calendar ahora = Calendar.getInstance();
		fechaIni.setTime(JDateTools.getDateStartDay(this.getFechaDesde()));
		fechaFin.setTime(JDateTools.getDateEndDay(this.getFechaHasta()));
		ahora.setTime(JDateTools.getDateStartDay(new Date()));
		fechaAct.setTime(new Date());
		fechaAct.add(Calendar.YEAR, -1);
		if (news && fechaAct.before(this.getFechaDesde())) {
			JIterator<BizDetalle> it = getObjDetalles().getStaticIterator();
			while (it.hasMoreElements()) {
				BizDetalle det = it.nextElement();
				BizDetalle d=det.getObjLogicaInstance().getBiz();
				d.copyProperties(det);
				
				d.processUpdate();
			}
		}
		if (!isActive()) return null;

		if (fechaFin.after(ahora)) return null;
		setActive(INACTIVE);
		update();
		
		Calendar fechaIniNC = Calendar.getInstance();
		Calendar fechaFinNC = Calendar.getInstance();
		fechaIniNC.setTime(fechaFin.getTime());
		fechaIniNC.add(Calendar.DAY_OF_MONTH, 1);
		long difInMonths = (long)((float)(fechaFin.getTime().getTime() - fechaIni.getTime().getTime()) / ((long)29 * 24 * 60 * 60 * 1000));
		if (difInMonths==0) difInMonths=1;
		fechaFinNC.setTime(fechaIniNC.getTime());
		fechaFinNC.add(Calendar.MONTH, (int)difInMonths);
		fechaFinNC.add(Calendar.DAY_OF_MONTH, -1);

		BizContrato na = new BizContrato();
		
		JRecords<BizContrato> candidatos = new JRecords<BizContrato>(BizContrato.class);
		candidatos.addFilter("company", getCompany());
		candidatos.addFilter("descripcion", getDescripcion());
		candidatos.addFilter("fecha_desde", fechaIniNC.getTime(), ">=");
		candidatos.addFilter("fecha_hasta_rep", fechaIniNC.getTime(), "<");
		candidatos.addOrderBy("fecha_desde", "ASC");
		candidatos.setTop(1);
		JIterator<BizContrato> it = candidatos.getStaticIterator();
		if (it.hasMoreElements()) {
			na=it.nextElement();
			na.setActive(ACTIVE);
			na.update();
			return na;
		}
		
		na.copyProperties(this);
		na.setActive(ACTIVE);
		na.setAtencion("Se renovó el contrato reajustar niveles");
		na.pId.setNull();
		na.setFechaDesde(fechaIniNC.getTime());
		na.setFechaHasta(fechaFinNC.getTime());
		this.processClon(na);
		return na;
	}
	
	public BizContrato processSigPeriodo() throws Exception {
		Calendar fechaIniNC = Calendar.getInstance();
		Calendar fechaFinNC = Calendar.getInstance();
		fechaIniNC.setTime(getFechaHasta());
		fechaIniNC.add(Calendar.DAY_OF_MONTH, 1);
		long difInMonths = (long)((float)(getFechaHasta().getTime() -getFechaDesde().getTime()) / ((long)29 * 24 * 60 * 60 * 1000));
		if (difInMonths==0) difInMonths=1;
		fechaFinNC.setTime(fechaIniNC.getTime());
		fechaFinNC.add(Calendar.MONTH, (int)difInMonths);
		fechaFinNC.add(Calendar.DAY_OF_MONTH, -1);

		BizContrato na = new BizContrato();
		
		JRecords<BizContrato> candidatos = new JRecords<BizContrato>(BizContrato.class);
		candidatos.addFilter("company", getCompany());
		candidatos.addFilter("descripcion", getDescripcion());
		candidatos.addFilter("fecha_desde", fechaIniNC.getTime(), ">=");
		candidatos.addFilter("fecha_desde", fechaFinNC.getTime(), "<");
		candidatos.addOrderBy("fecha_desde", "ASC");
		candidatos.setTop(1);
		JIterator<BizContrato> it = candidatos.getStaticIterator();
		if (it.hasMoreElements()) {
			return na; // ya existe
		}
		
		na.copyProperties(this);
		na.pId.setNull();
		na.setFechaDesde(fechaIniNC.getTime());
		na.setFechaHasta(fechaFinNC.getTime());
		this.processClon(na);
		return na;
	}
	
	public BizContrato processCopiarOtraEmpresa( BizCompany compania) throws Exception {
	
		BizContrato na = new BizContrato();
			
		na.copyProperties(this);
		na.pId.setNull();
		na.pCompany.setValue(compania.getCompany());
		this.processClon(na);
		return na;
	}
	
	
	
	@Override
	public void processInsert() throws Exception {
		pActive.setValue(NEW);
		if (pTipoContrato.isNull())
			pTipoContrato.setValue("C");
		setAtencion("Nuevo contrato, se requiere activar, para que comience a funcionar");
		super.processInsert();
	}
	
	public BizContrato processClon(BizContrato newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizDetalle> ampls = new JRecords<BizDetalle>(BizDetalle.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id", getId());
		JIterator<BizDetalle> it = ampls.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle ampl = it.nextElement();
			//if (ampl.isAutogenerado()) continue;
			BizDetalle na = null;
			ILogicaContrato l = ampl.getObjLogicaInstance();
			na=l.getBiz();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setId(newDoc.getId());
			na.setNullToLinea();;
			na.setFechaPrediccion(null);
			ampl.processClon(na);
		}

		it = newDoc.getObjDetalles().getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle ampl = it.nextElement();
			BizDetalle na = null;
			ILogicaContrato l = ampl.getObjLogicaInstance();
			na=l.getBiz();
			na.copyProperties(ampl);
			na.autogenerar();
			na.calcule(true);
			na.update();
		}

//		newDoc.processRepoblar();
		return newDoc;
	}
	 public static void ExecCheckForEvents(boolean news,boolean calcular) throws Exception {
		 if (!news&&!calcular) return;
	  	try {
				JRecords<BizContrato> events = new JRecords<BizContrato>(BizContrato.class);
				JIterator<BizContrato> it =events.getStaticIterator();
				while (it.hasMoreElements()) {
					BizContrato contract = it.nextElement();
					if (!contract.isActiveCompanyAndContract()) continue;
					contract.execProcessCheckForCalculo();
					contract.execProcessBuildNextContrato(news);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 
	  
	  }
	 public static void ExecCheckForEvents(String company, boolean news) throws Exception {

	  	try {
				JRecords<BizContrato> events = new JRecords<BizContrato>(BizContrato.class);
				events.addFilter("company", company);
				events.addFilter("active", true);
				JIterator<BizContrato> it =events.getStaticIterator();
				while (it.hasMoreElements()) {
					BizContrato contract = it.nextElement();
					if (!contract.isActiveCompanyAndContract()) continue;
					contract.execProcessCheckForCalculo();;
					contract.execProcessBuildNextContrato(news);
					Thread.yield();
				}
			} catch (Exception e) {
				PssLogger.logError(e);
				e.printStackTrace();
			}

	 
	  
	  }
	 public static void ExecCalculeALL() throws Exception {

	  	try {
				JRecords<BizContrato> events = new JRecords<BizContrato>(BizContrato.class);
				JIterator<BizContrato> it =events.getStaticIterator();
				while (it.hasMoreElements()) {
					BizContrato contract = it.nextElement();
					contract.execProcessRepoblar2();
					Thread.yield();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 
	  
	  }

	 
	  public BizSqlEventHistory  generarAviso(JFilterMap a,BizSqlEventAction action,boolean vistaPrevia) throws Exception {
			BizSqlEventHistory hist= new BizSqlEventHistory();
	  	hist.setFecha(new Date());
	  	hist.setIdevento(""+action.getIdevento());
	  	hist.setCompany(getCompany());
	  	hist.setFundamento(action.getDescripcion());
	  	hist.setMensajeUsuario(action.getMensajeUsuario());
	  	hist.setIdaction(action.getIdaction());
	  	hist.setAccion(action.getAction());
	  	hist.setDestinatario(action.getDestinatario());
	 		hist.setMensaje(action.getMensajeAviso(a,hist));
	  	if (!vistaPrevia) hist.setArchivoAsociado(action.getArchivoAsociado(a,hist));
	  	return hist;
	  }
	  
		public String getNotificacionAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
	  	return "";
	  }
		public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
			if (!action.isAdjunto() && !action.isAccionURL()) {
				if (action.isOutEXCEL()) {
					GuiContrato l = new GuiContrato();
					l.setRecord(this);
					String content =l.getHtmlView(10,"xls",a);
					return URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20");
				}
				if (action.isOutCSV()) {
					GuiContrato l = new GuiContrato();
					l.setRecord(this);
					String content =l.getHtmlView(10,"csv",a);
					return URLEncoder.encode(content,"ISO-8859-1").replace("+", "%20");
				}
				if (action.isOutPDF()) {
					GuiContrato l = new GuiContrato();
					l.setRecord(this);
					String file = l.GetcDato().imprimirResumen();
					return action.getMensajeUsuario()+"<br/>"+ URLEncoder.encode(JTools.readFileAsString(JPath.PssPathTempFiles()+"/"+file),"ISO-8859-1").replace("+", "%20");
				}
				GuiContrato l = new GuiContrato();
				l.setRecord(this);
				String file = l.GetcDato().imprimirResumen();
				return action.getMensajeUsuario()+"<br/>"+ URLEncoder.encode(JTools.readFileAsString(JPath.PssPathTempFiles()+"/"+file),"ISO-8859-1").replace("+", "%20");
			}
			// mando un correo
			return action.getMensajeUsuario()+"<br/>"+ action.getObjPlantilla().generateDocSimple(hist,this);
	  }
		
		public String getCorreoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
			return getCorreoAviso(a,action, hist);
		}
		public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist,String campo,String valor) throws Exception {
			return getCorreoAviso(a,action, hist,campo,valor);
		}	
		public String getArchivoAsociadoAviso(JFilterMap a,BizSqlEventAction action,BizSqlEventHistory hist) throws Exception {
			if (action.isOutPantalla()) {
				GuiContrato l = new GuiContrato();
				l.setRecord(this);
				String file = l.GetcDato().imprimirResumen();
				return file;
			}
			if (action.isOutEXCEL()) {
				GuiContrato l = new GuiContrato();
				l.setRecord(this);
				String file = l.hashCode()+".xlsx";////.xls
				String content =l.getHtmlView(10,"excel",a);
				JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
				return file;
			}
			if (action.isOutCSV()) {
				GuiContrato l = new GuiContrato();
				l.setRecord(this);
				String file = l.hashCode()+".csv";
				String content =l.getHtmlView(10,"csv",a);
				JTools.writeStringToFile(content,JPath.PssPathTempFiles()+"/"+file);
				return file;
			}
			if (action.isOutPDF()) {
				GuiContrato l = new GuiContrato();
				l.setRecord(this);
				String file = l.GetcDato().imprimirResumen();
				
				return file;
			}
			
			return null;
	  }
		@Override
		public String hasGenerateAviso(BizSqlEventAction action) throws Exception {
			return null;
		}


		@Override
		public void mensajeEnviado(String marca) throws Exception {
		}
		
		@Override
		public boolean read(String company, String zId, JFilterMap param) throws Exception {
	  	dontThrowException(true);
	  	addFilter("company",company);
	    return read(Long.parseLong(zId) ); 
		}

		public long getReservas() throws Exception {
			return getReservasDetail().selectSupraCount();
		}
		public JRecords<BizPNRReserva> getReservasDetail() throws Exception {
			JRecords<BizPNRReserva> reservas = new JRecords<BizPNRReserva>(BizPNRReserva.class);
			reservas.setStatic(true);
			JIterator<BizDetalle> it = this.getDetalles().getStaticIterator();
			while (it.hasMoreElements()) {
				BizDetalle det = it.nextElement();
				if (!det.hasConsultaReservaMeta()) continue;
				JRecords<BizPNRReserva> reservatDet = det.getListaReservasMeta();
				reservas.getStaticItems().addElements(reservatDet.getStaticItems());
			}
			
			return reservas;
		}
		
	  public static void ExecCalcule(String company) throws Exception {

	  	try {
			 	JRecords<BizContrato> pnrs = new JRecords<BizContrato>(BizContrato.class);
			 	pnrs.addFilter("company", company);
			 	pnrs.addFixedFilter("where  (fecha_version_cambio IS DISTINCT FROM fecha_version_calculo) ");
				JIterator<BizContrato> it =pnrs.getStaticIterator();
				while (it.hasMoreElements()) {
					BizContrato pnr = it.nextElement();
					pnr.execProcessRecalcular();
					Thread.yield();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
		
}
