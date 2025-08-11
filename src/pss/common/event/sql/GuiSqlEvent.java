package pss.common.event.sql;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.customlist.GuiCustomListResult;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.event.sql.datos.GuiSqlEventDatos;
import pss.common.security.BizUsuario;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiSqlEvent extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEvent() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizSqlEvent(); }
  public int GetNroIcono()   throws Exception { 
  	if (GetcDato().isREPROCESANDO()) return 10015;
  	if (GetcDato().isREPROCESAR()) return 55;
  	if (GetcDato().isInvisible()) return 15018;
  	if (GetcDato().isSystemProtect()) return 912;
  	return 15019; 
  }
  public String GetTitle()   throws Exception { return "Indicador"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetVision()!=null&&GetVision().equals("M")) return FormNewSqlEvent.class;
  	if (GetVision()!=null&&GetVision().equals("Q")) return FormQuickSqlEvent.class;
  	if (GetVision()!=null&&GetVision().equals("E")) return FormSqlEventDetalle.class;
  	return FormSqlEvent.class; 
  }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizSqlEvent GetcDato() throws Exception { return (BizSqlEvent) this.getRecord(); }

  
  @Override
	public void createActionMap() throws Exception {
   	this.addAction(30, "Historico", null, 406, false, false, false, "Group" );
   	this.addAction(10, "Acciones", null, 5060, false, false, false, "Group" );
   	this.addAction(20, "Repoblar", null, 15018, true, true, true, "Group" ).setMulti(true);;
   	this.addAction(40, "Detalle", null, 10020, false, false, false, "Group" );
   	this.addAction(45, "Hijos", null, 5060, false, false, false, "Group" );
		this.addAction(50, "Compartir", null, 10078, true, true);
   	this.addAction(60, "Ver Data mining", null, 10027, true, true, true, "Group" ).setOnlyInForm(true);
   	this.addAction(100, "Vista rapida", null, 10027, true, false, true, "Group" );
   	this.addAction(110, "Ver variables", null, 10027, true, true, true, "Group" ).setOnlyInForm(true);
   	this.addAction(120, "Explicacion", null, 10027, false, false, false, "Group" );
   	this.addAction(130, "Migrar tkm6", null, 10027, true, true, true, "Group" );
  	addAction(502	, "Actualizar hijos" ,null, 84, true, true).setMulti(true);
		addAction(505	, "Agregar a hijos" ,null, 84, true, true).setMulti(true);;
		addAction(506	, "Borrar en hijos" ,null, 84, true, true).setMulti(true);
		addAction(507	, "Agregar a padre" ,null, 84, true, true).setMulti(true);;
		addAction(513	, "Desproteger" ,null, 84, true, true).setMulti(true);
		addAction(514	, "Proteger" ,null, 84, true, true).setMulti(true);
		addAction(515	, "Invisibilizar" ,null, 84, true, true).setMulti(true);
		addAction(516	, "Visibilizar" ,null, 84, true, true).setMulti(true);
		addAction(517	, "Ver padre" ,null, 84, true, true);

   	super.createActionMap();
	}

  @Override
  public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==BizAction.UPDATE) return !this.GetcDato().isSystemProtect() ;
  	if (a.getId()==BizAction.REMOVE) return !this.GetcDato().isSystemProtect();
  	if (a.getId()==40) return !GetcDato().getConsultaDetalle().equals("");
  	if (a.getId()==45) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==60) return GetcDato().getObjCustomList()!=null;
  	if (a.getId()==110) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==130) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==502) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==513) return BizUsuario.IsAdminCompanyUser() && this.GetcDato().isSystemProtect();
		if (a.getId()==514) return BizUsuario.IsAdminCompanyUser() && !this.GetcDato().isSystemProtect();
		if (a.getId()==505) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==506) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==507) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==515) return BizUsuario.IsAdminCompanyUser() && !GetcDato().isInvisible();
		if (a.getId()==516) return BizUsuario.IsAdminCompanyUser() && GetcDato().isInvisible();
		if (a.getId()==517) return BizUsuario.IsAdminCompanyUser() && GetcDato().isSystemProtect();
//  	if (a.getId()==100) return false;
  	return super.OkAction(a);
  }
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==30 ) return new JActWins(this.getHistorico());
  	if (a.getId()==10 ) return new JActWins(this.getAcciones());
  	if (a.getId()==40 ) return new JActWins(this.getDetalles());
  	if (a.getId()==45 ) return new JActWins(this.getHijos());
		if (a.getId()==50) return new JActNew(this.getCompartir(a), BizAction.DROP);
  	if (a.getId()==60 ) return new JActQuery(this.getCustomList());
  	if (a.getId()==20 ) return new JActSubmit(this){
  		@Override
  		public void submit() throws Exception {
  			GetcDato().execProcessPopulate(null,null);
  		}
  	};
  	if (a.getId()==100 ) return new JActQuery(this.getQuickView());
  	if (a.getId()==110 ) return new JActQuery(this.getView());
  	if (a.getId()==517 ) return new JActQuery(this.getSQLEventParent());
  	if (a.getId()==120 ) return new JActQuery(this.getExplicacion());

		if (a.getId()==502) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execCambiarTodosLosHijos();
			}
		};
		if (a.getId()==505) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execAgregarTodosLosHijos();
			}
		};
		if (a.getId()==506) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execBorrarTodosLosHijos();
			}
		};
		if (a.getId()==507) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execAgregarAlPadre();
			}
		};
		if (a.getId()==513) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().setSystemProtect(0);
				GetcDato().execProcessUpdate();
			}
		};
		if (a.getId()==514) return new JActWinsSelect(getSQLEventsParent(),this);
		if (a.getId()==515) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().setInvisible(true);
				GetcDato().execProcessUpdate();
			}
		};		
		if (a.getId()==516) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().setInvisible(false);
				GetcDato().execProcessUpdate();
			}
		};
		return super.getSubmitFor(a);
	}
  public GuiSqlEvent getSQLEventParent() throws Exception {
  	GuiSqlEvent win = new GuiSqlEvent();
  	win.GetcDato().read(GetcDato().getSystemProtect());
  	return win;
  } 
  public GuiSqlEvents getSQLEventsParent() throws Exception {
  	GuiSqlEvents wins = new GuiSqlEvents();
  	wins.getRecords().addFilter("company",BizUsuario.getUsr().getObjCompany().getParentCompany());
  	wins.addVisionAdHoc("PROTEGER");
  	return wins;
  
  }
	public GuiSqlEvents getHijos() throws Exception {
		GuiSqlEvents wins = new GuiSqlEvents();
		wins.getRecords().addFilter("system_protect",GetcDato().getId());
		wins.SetVision("SYSTEM");
		return wins;
	
	}


	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (zColName.equals("valor")) {
			return (GetcDato().getColor());
		}
		else if (zColName.equals("var_porc")) {
			return GetcDato().getVarPorc()<0?"c30707":GetcDato().getVarPorc()>0?"1aa807":"524cdb";
		}
		else if (zColName.equals("var_valor")) {
			return GetcDato().getVarPorc()<0?"c30707":GetcDato().getVarPorc()>0?"1aa807":"524cdb";
		}
		else if (zColName.equals("tendencia")) {
			return GetcDato().getVarPorc()<0?"c30707":GetcDato().getVarPorc()>0?"1aa807":"524cdb";
		}
		return super.getFieldForeground(zColName);
	}
	
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (zColName.equals("valor_emergencia")) {
			return "b5d9d8";
		}
		else if (zColName.equals("valor_minimo")) {
			return "b5d9d8";
		}
		else if (zColName.equals("valor_aviso")) {
			return "b5d9d8";
		}
		return super.getFieldBackground(zColName);
	}
	
	@Override
	public int GetSimpleClick() throws Exception {
		return 100;
	}
	@Override
	public int GetDobleClick() throws Exception {
		return 1;
	}
	
  public GuiCustomList getCustomList() throws Exception {
  	GuiCustomListResult	c = new GuiCustomListResult();
  	c.setRecord(GetcDato().getObjCustomList());
  	if (GetcDato().getFHasta()!=null)
  		c.GetcDato().setHoy(GetcDato().getFHasta());
  	return c;
  }
  public GuiSqlEvent getQuickView() throws Exception {
  	GuiSqlEvent	c = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
  	c.setRecord(this.GetcDato());
  	c.SetVision("Q");
  	return c;
  }
  public GuiSqlEvent getView() throws Exception {
  	GuiSqlEvent	c = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
  	c.setRecord(this.GetcDato());
  	c.GetcDato().fillVirtuals();
  	c.SetVision("M");
  	return c;
  }
  public GuiSqlEvent getExplicacion() throws Exception {
  	GuiSqlEvent	c = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
  	c.setRecord(this.GetcDato());
  	c.GetcDato().fillVirtuals();
  	c.SetVision("E");
  	return c;
  }
  public JWins getAcciones() throws Exception {
  	GuiSqlEventActions	c = new GuiSqlEventActions();
  	c.getRecords().addFilter("company", this.GetcDato().getCompany());
  	c.getRecords().addFilter("class_evento", BizSqlEvent.class.getCanonicalName());
  	c.getRecords().addFilter("id_evento", ""+this.GetcDato().getId());
  	c.setPreviewFlag(JWins.PREVIEW_NO);
  	return c;
  }
  public JWins getDetalles() throws Exception {
  	return this.GetcDato().getDetalles(null,null);
  }
  public JWins getHistorico() throws Exception {
  	GuiSqlEventDatos	c = new GuiSqlEventDatos();
  	c.getRecords().addFilter("id_evento", this.GetcDato().getId());
  	if ( this.GetcDato().getFDesde()!=null)
  		c.getRecords().addFilter("fecha", this.GetcDato().getFDesde(),">=");
  	if ( this.GetcDato().getFHasta()!=null)
  		c.getRecords().addFilter("fecha", this.GetcDato().getFHasta(),"<=");
  	c.getRecords().addOrderBy("fecha","DESC");
  	c.getRecords().setParent(this.GetcDato());
  	c.setPreviewFlag(JWins.PREVIEW_NO);
  	return c;
  	
  }
  @Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", this.GetcDato().getCompany());
		sql.getRecord().addFilter("class_evento", BizSqlEvent.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", this.GetcDato().getId());
		sql.getRecord().addFilter("descripcion", this.GetcDato().getDescripcion());
		sql.getRecord().addFilter("parametros",a.getFilterMap().serialize());
		sql.setDropListener(this);
//		sql.GetcDato().setFilterMap(a.getFilterMap()); 
		this.setFilterMap(a.getFilterMap());
		return sql;
	}
	JFilterMap filters;


  public JFilterMap getFilterMap() {
		return filters;
	}


	public void setFilterMap(JFilterMap action) {
		this.filters = action;
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
	  	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) {
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(null,((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}

  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(null);
   		if (hist==null) return new JActBack(this);
   	    		
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
		if (zBaseWin instanceof GuiSqlEvent) {
			GuiSqlEvent lt = (GuiSqlEvent) zBaseWin;
			if (lt.GetVision().equals("PROTEGER")) {
				this.GetcDato().setSystemProtect(lt.GetcDato().getId());
				this.GetcDato().execProcessUpdate();
				return null;
			}
		}
		return super.Drop(zBaseWin);
	}

	public void ExecCheckForEventsNewFiles(String company) throws Exception {

				JRecords<BizSqlEvent> events = new JRecords<BizSqlEvent>(BizSqlEvent.class);
				events.addFilter("company", company);
				events.addFilter("estado", BizSqlEvent.REPROCESAR,"<>");
				events.addFilter("estado", BizSqlEvent.REPROCESANDO,"<>");
				events.addFilter("activo", true);
				JIterator<BizSqlEvent> it =events.getStaticIterator();
				while (it.hasMoreElements()) {
					BizSqlEvent sqlEventOrig = it.nextElement();
					BizSqlEvent sqlEvent = sqlEventOrig.getObjWithoutRead();
					if (BizUsuario.getUsr().getObjBusiness().isSqlEventProcessInService()) {
						if (sqlEvent.isREPROCESANDO()) continue;
						if (sqlEvent.isREPROCESAR())continue;
					}
					if (!sqlEvent.isActivo()) continue;
					sqlEvent.execProcessUpdateDatos();
				//	sqlEvent.processPopulate(null, null);
				}
			
		
  }
	public void ExecCheckForEvents(String company) throws Exception {
//
		JRecords<BizSqlEvent> events = new JRecords<BizSqlEvent>(BizSqlEvent.class);
		events.addFilter("company", company);
		events.addFilter("estado", BizSqlEvent.REPROCESAR);
		events.addFilter("activo", true);
		JIterator<BizSqlEvent> it =events.getStaticIterator();
		while (it.hasMoreElements()) {
			BizSqlEvent sqlEventOrig = it.nextElement();
	  	try {
				BizSqlEvent sqlEvent = sqlEventOrig.getObjWithoutRead();
//				if (sqlEvent.getConsulta().indexOf("continente")!=-1) continue;
//				if (sqlEvent.getConsulta().indexOf("region")!=-1) continue;
				if (BizUsuario.getUsr().getObjBusiness().isSqlEventProcessInService()) {
					if (sqlEvent.isREPROCESANDO()) continue;
					if (sqlEvent.isREPROCESAR()) {
						sqlEvent.execSetServicePopulate(null,null,BizSqlEvent.REPROCESANDO);
						sqlEvent.execServiceProcessPopulate();
						continue;
					}
				}
				if (!sqlEvent.isActivo()) continue;
			} catch (Exception e) {
				PssLogger.logError(e);
				sqlEventOrig.setEstado(BizSqlEvent.ERROR);
				sqlEventOrig.execProcessUpdate();;
			}
		}
		
  }
 }
