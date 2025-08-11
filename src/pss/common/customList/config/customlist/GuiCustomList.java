	package pss.common.customList.config.customlist;

import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.common.customList.config.field.campo.GuiCampos;
import pss.common.customList.config.informe.GuiInformes;
import pss.common.customList.config.owner.GuiCustomListOwners;
import pss.common.customList.config.relation.GuiCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.company.GuiCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActQueryActive;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomList extends JWin implements IContenidoCarpeta{


	private GuiCustomList customListPadre;
	public final static long ONLY_TABLE =1;
	public final static long EDIT_MODE =2;
	public final static long TABLE_GRAPH =3;
	public final static long EMBEDDED =4; // dm por email
	public final static long INFOEMBEDDED =5; // informes por email
	public final static long INFOONLY_TABLE =6;
	private long mode;
	public void setMode(long v) {
		this.mode=v;
	}
	
	public long getMode() {
		return mode;
	}
	public boolean showFilter() {
		return !(mode==EMBEDDED ||mode==INFOEMBEDDED ||mode==ONLY_TABLE||mode==INFOONLY_TABLE || mode==EDIT_MODE);
	}
	public boolean showZoom() {
		return !(mode==INFOEMBEDDED ||mode==INFOONLY_TABLE);
	}

	public boolean acceptDragMode() {
		return mode==EDIT_MODE;
	}

  /**
   * Constructor de la Clase
   */
  public GuiCustomList() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCustomList(); }
  
  public int GetNroIcono()   throws Exception {
  	if (this.GetcDato().isFavorito())
  		return 15050;
  	if (GetcDato().isSystemProtect()) 
  		return 912;
  	if (this.GetcDato().isFavorito())
  		return 15050;
  	if (this.GetcDato().isMatriz())
  		return 10045;
  	if (this.GetcDato().isLista())
  		return 10049;
  	return 10003; 
  }
  public String GetTitle()   throws Exception { 
  	return GetcDato().getDescripcion().equals("")?"Lista DiN°mica":GetcDato().getDescripcion(); 
  	
  }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
//   	if (GetcDato().isInforme())
//  		return FormCustomListInforme.class;
   	return FormCustomListQuery.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormNew() throws Exception {
   	if (GetcDato().isInforme())
  		return FormCustomListInforme.class;
   	return FormCustomList.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormUpdate() throws Exception {
   	if (GetcDato().isInforme())
  		return FormCustomListInforme.class;
   	return FormCustomList.class; 
  }
  
  public String  getKeyField() throws Exception { return "list_id"; }
  public String  getDescripField() { return "description"; }
  public BizCustomList GetcDato() throws Exception { return (BizCustomList) this.getRecord(); }
  
//  public void setObjWinOwner(JWin value) {
//  	this.winOwner=value;
//  }

	public void createActionMap() throws Exception {
		addAction(2, "Editar", null, 10076, true, true).setOnlyInForm(true);
		createActionDelete().setMulti(true);
		BizAction a=addAction(10, "Edicion Avanzada", null, 41, false, false);
  	a.setRefreshAction(false);
  	a=addAction(25, "Edicion", null, 41, false, false);
  	a.setRefreshAction(false);
//		addAction(19, "Filtros SQL", null, 10063, false, false).setRefreshAction(false);
		addAction(21,  "Informes", null, 10063, false, false).setRefreshAction(false);
	//	addAction(30, "SQL", null, 10062, true, true);
		addAction(91,  "Ver formato", null, 10062, true, true).setOnlyInForm(true);
		addAction(80,  "Permisos", null, 26, false, false).setOnlyInForm(true);
		addAction(81,  "Hacer público", null, 26, true, true).setOnlyInForm(true);
		addAction(20,  "Ejecutar", null, 10059, true, true);
		addAction(22,  "Result Preview", null, 10059, false, false);
		addAction(24,  "Result Embedded", null, 10059, false, false).setFilterAction(false);
		addAction(26,  "Result informe embbeded", null, 10059, true, true);
		addAction(60,  "Dashboard On", null, 15022, true, true).setMulti(true);
		addAction(70,  "Dashboard Off", null, 15023, true, true).setMulti(true);
		addAction(50,  "Compartir", null, 10078, true, true);
		addAction(55,  "Imprimir", null, 10050, true, true).setImprimir(true);;
		addAction(500, "Clonar" ,null, 84, true, true);
		addAction(501, "A otra empresa" ,null, 84, true, true).setGroup(true);
		addAction(505, "Agr/act hijos" ,null, 84, true, true).setMulti(true);
		addAction(506, "Borrar en hijos" ,null, 84, true, true).setMulti(true);
		addAction(507, "Hijos" ,null, 84, false, false);
		addAction(508, "Agregar al padre" ,null, 84, true, true).setMulti(true);
		addAction(520, "Companias", null, 10013, false, false);
		addAction(521, "Ver padre", null, 84, true, true);

		addAction(513, "Desproteger" ,null, 84, true, true).setMulti(true);
		addAction(514, "Proteger" ,null, 84, true, true).setMulti(true);
	
		addAction(516, "Recalcule" ,null, 84, true, true);
		addAction(517, "Remigrar" ,null, 84, true, true);
		addAction(540, "Chat import" ,null, 84, false, false);
		addAction(541, "Limpiar Chat" ,null, 84, false, false);

		addAction(530, "Agregar Campos", null, 206, true, true).setModal(true);
		addAction(531, "Agregar Filtro OR", null, 206, true, true).setModal(true);
	
//		addAction(600	, "Convertir a Listado" ,null, 84, true, true).setMulti(true);
		addAction(20000, "Agregar consulta" ,null, 84, false, false).setMulti(true);

	}
	
  

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==2) return !this.GetcDato().isSystemProtect();//  && (BizUsuario.getUsr().isAnyAdminUser() || GetcDato().getOwner().equals(BizUsuario.getUsr().GetUsuario()));
		if (a.getId()==BizAction.REMOVE) return !this.GetcDato().isSystemProtect()   && (BizUsuario.getUsr().isAnyAdminUser() || GetcDato().getOwner().equals(BizUsuario.getUsr().GetUsuario()));

		if (a.getId()==8) return this.GetcDato().isMatriz() ;
		if (a.getId()==10) return !this.GetcDato().isInforme() && !this.GetcDato().isSystemProtect();
		if (a.getId()==12) return !this.GetcDato().isSqlAdvanced()&&!this.GetcDato().isInforme() ;
		if (a.getId()==15) return this.GetcDato().isLista();
//		if (a.getId()==17) return !this.GetcDato().isLista()&&!this.GetcDato().isInforme()&& !this.GetcDato().isSystemProtect();
		if (a.getId()==19) return this.GetcDato().isSqlAdvanced();
		if (a.getId()==21) return this.GetcDato().isInforme();
		if (a.getId()==20) return true;//!this.GetcDato().isDependant();
		if (a.getId()==22) return true;
		if (a.getId()==24) return true;
		if (a.getId()==26) return true;
		if (a.getId()==25) return !this.GetcDato().isInforme() && !this.GetcDato().isSystemProtect();
		if (a.getId()==30) return this.GetcDato().isSqlAdvanced();
		if (a.getId()==60) return !this.GetcDato().isFav();
		if (a.getId()==70) return this.GetcDato().isFav();
		if (a.getId()==81) return !this.GetcDato().isPublico();
		if (a.getId()==85) return true;
		if (a.getId()==55) return true;
		if (a.getId()==18) return true;//!this.GetcDato().isInforme();//GetVision().equals("RESULT");
		if (a.getId()==90) return !this.GetcDato().isSystemProtect() &&this.GetcDato().isInforme();//GetVision().equals("RESULT");
		if (a.getId()==100) return !GetcDato().isInforme();
		if (a.getId()==501) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==513) return BizUsuario.IsAdminCompanyUser() && this.GetcDato().isSystemProtect();
		if (a.getId()==514) return BizUsuario.IsAdminCompanyUser() && !this.GetcDato().isSystemProtect();
		if (a.getId()==505) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==506) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==507) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==508) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==540) return true;
		if (a.getId()==541) return true;
		if (a.getId()==600) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==521) 
			return BizUsuario.IsAdminCompanyUser() && this.GetcDato().isSystemProtect();
  	if (a.getId()==516) return BizUsuario.IsAdminCompanyUser() && GetcDato().isInvisible();
  	if (a.getId()==517) return BizUsuario.IsAdminCompanyUser();
		if (a.getId()==20000) return this.GetcDato().isInforme() && !this.GetcDato().isSystemProtect();

		return true;
	}
	
	@Override
	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		if (a.getId()==530) return true;
		if (a.getId()==531) return true;
		if (a.getId()==532) return true;
		return super.checkActionOnDrop(a);
	}
	
	public boolean checkActionComponentType(BizAction action, boolean isForm) throws Exception {
		if (this.GetVision().equals("RESULT")) return true;
		return super.checkActionComponentType(action, isForm);
	}

	public JWin getClon() throws Exception {
		GuiCustomList newDoc = new GuiCustomList();
		newDoc.setRecord(GetcDato().getClon());
		newDoc.setDropListener(this);
		newDoc.GetcDato().SetVision("CLON");
		return newDoc;
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		//if (a.getId()==2) return new JActUpdate(getUpdate(),BizAction.UPDATE);
		if (a.getId()==18) return new JActQuery(this); 
		if (a.getId()==21) return new JActWins(this.getInformes());
		if (a.getId()==25) return new JActWins(this.getOnlyCampos(a));
		if (a.getId()==20) return new JActQueryActive(this.getResult(a,false,false));
		if (a.getId()==22) return new JActQueryActive(this.getResultPreview(a,false));
		if (a.getId()==24) return new JActQueryActive(this.getResult(a,false,true));
		if (a.getId()==26) return new JActQueryActive(this.getResult(a,true,true));
		if (a.getId()==91) return new JActQuery(this.getModelo());
		if (a.getId()==80) return new JActWins(this.getPermisos());
		if (a.getId()==30) return new JActUpdate(this.getSQLConfig(), 2);
		if (a.getId()==50) return new JActNew(this.getCompartir(a), BizAction.DROP);
		if (a.getId()==60) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execMarcarComoFavorito();
			}
		};
	
		
		if (a.getId()==55) return new JActFileGenerate(this, a.getId()) {
				public String generate() throws Exception {
					return imprimir(this.getActionSource(),true);
				}
		};
		if (a.getId()==70) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execDesmarcarComoFavorito();
			}
		};
		if (a.getId()==71) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execProcessRenumerar();
			}
		};
		if (a.getId()==81) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execHacerPublico();
			}
		};
		if (a.getId()==500) return new JActNew(getClon(),4,true,false,true);
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
		if (a.getId()==507) return new JActWins(getCustomListHijos());
		if (a.getId()==508) return new JActSubmit(this){
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

		if (a.getId()==514) return new JActWinsSelect(getCustomListParent(),this);

		//if (a.getId()==501) return new JActWinsSelect(new GuiCompanies(),this);
		if (a.getId()==501)			
			return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				target=(JWins)this.getResult();
				target.setRecords(JRecords.createRecords(this.getResult().GetBaseDato()));
			}
			@Override
			public JAct nextAction() throws Exception {
				 clearActions();
				 return findAction(520).getObjSubmit();
			}
		};		
//		if (a.getId()==515) return new JActWins(this.getFiltrosAutomaticos());
		if (a.getId()==516) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execProcessRecalcule();
			}
		};
		if (a.getId()==517) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execProcessRemigrarOR();
			}
		};
		
		if (a.getId()==540) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().processChat();
			}
		};
		if (a.getId()==541) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().clearChat();
			}
		};
		if (a.getId()==520) return new JActWinsSelect(new GuiCompanies(), target, false);			
		if (a.getId()==521) return new JActQuery(this.getCustomListPadreSystemProtect());
		
		if (a.getId()==530) return new JActFieldSwapWins(getCampos(),getGallery(),this,"campo_serial","campo_serial");
		if (a.getId()==531) return new JActNew(getFiltroOR(),4);
	
		if (a.getId()==600) return new JActSubmit(this){
			public void submit() throws Exception {
				GetcDato().execConvertirAListado();
			}
		};
		if (a.getId()==20000) return new JActWinsSelect(getConsultas(),this,true);
		
		return super.getSubmitFor(a);
	}
	public GuiCamposGallery getGallery() throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 	  g.setRecords(this.GetcDato().getOnlyCampos(this.GetcDato(), null, true,true));
  	g.setObjCustomList(this);
		g.setPreviewFlag(JWins.PREVIEW_SI);
		g.setForceActionPreview(100);
		g.setPreviewSplitPos(150);
		g.setModeView(JBaseWin.MODE_TREE);
		g.SetVision("SWAP");
	//	g.setToolbarForced(JWins.TOOLBAR_LEFT);
		g.toStatic();
  	return g;
	}
	public GuiCampos getCampos() throws Exception {
		GuiCampos campos = new GuiCampos();
		campos.setRecords(this.GetcDato().getObjAllCampos());
		return campos;
	}
	public GuiCampo getFiltroOR() throws Exception {
		GuiCampo c = new GuiCampo();
		c.GetcDato().addFilter("company", this.GetcDato().getCompany());
		c.GetcDato().addFilter("list_id", this.GetcDato().getListId());
		c.GetcDato().addFilter("rel_id", this.GetcDato().getRelId());
		c.GetcDato().addFilter("record_owner", this.GetcDato().getRecordOwner());
		c.GetcDato().addFilter("operacion", BizCampo.OPER_OR);
    c.GetcDato().addFilter("orden_padre", "0");
    c.GetcDato().setRelId(this.GetcDato().getRelId());
    c.GetcDato().setOrigenDatos(this.GetcDato().getRelId());
    
		c.GetcDato().setOperacion(BizCampo.OPER_OR);
  	c.GetcDato().setObjFiltroParent(null);
   	c.GetcDato().setOrdenPadre("0");
		c.GetcDato().setObjCustomList(this.GetcDato());
		c.setDropListener(this);
		c.setParent(this);
		return c;
	}
	public GuiCustomLists getConsultas() throws Exception {
		GuiCustomLists lists = new GuiCustomLists();
		lists.getRecords().addFilter("company", GetcDato().getCompany());
		lists.getRecords().addFilter("invisible", false);
		return lists;
	}
	JWins target = new GuiCustomLists();
  public GuiCustomList getCustomListPadreSystemProtect() throws Exception {
  	GuiCustomList win = new GuiCustomList();
  	win.setRecord(GetcDato().getObjCustomListParentSystemProtect());
  	return win;
  
  }
  public GuiCustomLists getCustomListParent() throws Exception {
  	GuiCustomLists wins = new GuiCustomLists();
  	wins.getRecords().addFilter("company",BizUsuario.getUsr().getObjCompany().getParentCompany());
  	wins.addVisionAdHoc("PROTEGER");
  	return wins;
  
  }

  public GuiCustomLists getCustomListHijos() throws Exception {
  	GuiCustomLists wins = new GuiCustomLists();
  	wins.getRecords().addFilter("system_protect",GetcDato().getListId());
  	wins.SetVision("SYSTEM");
  	return wins;
  
  }


  public String imprimir(BizAction a, boolean html) throws Exception {
  	return this.GetcDato().imprimirResumen(a.getFilterMap(),html );
  }
  
	public String imprimir(BizAction a) throws Exception {
  	return this.GetcDato().imprimirResumen(null,true );
	}

	public int GetDobleClick() {
		return 20;
	}


	public static boolean isCampoClave(GuiCampoGallery campo) throws Exception {
		return campo.GetcDato().getCampo().toLowerCase().equals("numeroboleto");
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {

		if (zBaseWin instanceof GuiCustomLists) {
			GuiCustomLists ls = (GuiCustomLists) zBaseWin;
			JIterator<JWin> it=ls.getStaticIterator();
			while (it.hasMoreElements()) {
				GetcDato().execProcessAgregarInformeDB((BizCustomList)it.nextElement().getRecord());
			}
			return null;
		}
		if (zBaseWin instanceof GuiCustomList) {
			GuiCustomList l = (GuiCustomList) zBaseWin;
			if (GetcDato().isInforme()) {
				GetcDato().execProcessAgregarInformeDB(l.GetcDato());
			} else{
				l.GetcDato().execProcessInsert();
				BizCompany.getCompany(GetcDato().getCompany()).getObjBusiness().getCarpeta().GetcDato().execProcessCompletarCarpetas();

			}
			return null;
		}
//		if (zBaseWin instanceof GuiCampo) {
//			GuiCampo filtro = (GuiCampo)zBaseWin;
//			filtro.GetcDato().processDrop(this.GetcDato());
//			filtro.setDropListener(null);
//
//				return null;
//		}
		if (zBaseWin instanceof GuiCamposGallery) {
			GuiCamposGallery campos = (GuiCamposGallery)zBaseWin;
			GetcDato().getObjAllCampos().execProcessFillRecords(this.GetcDato(),campos.getRecords(),"campo_serial","campo_serial");
			return null;
		}
		if (zBaseWin instanceof GuiCampoGallery) {
			GuiCampoGallery campo = (GuiCampoGallery)zBaseWin;
				campo.GetcDato().execProcessAddCampo(this.GetcDato());
			return null;
		}

		if (zBaseWin instanceof GuiCompany) {
			GuiCompany c = (GuiCompany) zBaseWin;
			GuiCustomList lt = new GuiCustomList();
			lt.GetcDato().copyProperties(this.GetcDato());
			lt.GetcDato().setCompany(c.GetcDato().getCompany());
			lt.GetcDato().setNullListId();
			this.GetcDato().execProcessClon(lt.GetcDato());
			return null;
		}
		if (zBaseWin instanceof GuiCustomList) {
			GuiCustomList lt = (GuiCustomList) zBaseWin;
			if (lt.GetVision().equals("PROTEGER")) {
				this.GetcDato().setSystemProtect(lt.GetcDato().getListId());
				this.GetcDato().execProcessUpdate();
		  	return null;
			}
			this.GetcDato().execProcessClon(lt.GetcDato());
			GuiCustomList nlt = new GuiCustomList();
			nlt.GetcDato().read(lt.GetcDato().getCompany(),lt.GetcDato().getListId());
			return new JActQuery(nlt);
		}

  	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) {
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(((GuiSqlEventAction)getResult()).GetcDato().getFilterMap(),((GuiSqlEventAction)getResult()).GetcDato(),false);
  					return hist.getArchivoAsociado();
  				};
  			};
  		}
  		BizSqlEventHistory hist = action.GetcDato().execProcessExecute(this.getFilterMap());
   		if (hist==null) return new JActBack(this);
   	    		
  		GuiSqlEventHistory ghist = new GuiSqlEventHistory();
  		ghist.setRecord(hist);
  		return new JActQuery(ghist);
  	}
		return super.Drop(zBaseWin);
	}

	public JWin getModelo() throws Exception {
		JWin win = BizUsuario.getUsr().getObjBusiness().getModuloLayout();
		win.getRecord().addFilter("id", GetcDato().getModelo());
		win.getRecord().read();
		return win;
	}

	@Override
	public JWin getRelativeWin() throws Exception {
		if (GetcDato().isInforme()) {
			GuiCustomListInforme inf= new GuiCustomListInforme();
			inf.setRecord(GetcDato());
			inf.SetVision(GetVision());
			return inf;
		}
		return super.getRelativeWin();
	}
	public JWin getUpdate() throws Exception {
		return this;
	}

	private GuiCustomListOwners getPermisos() throws Exception {
		GuiCustomListOwners g=new GuiCustomListOwners();
  	g.getRecords().addFilter("company", this.GetcDato().getCompany());
  	g.getRecords().addFilter("list_id", this.GetcDato().getListId());
  	return g;
	}
	

	private GuiCamposGallery getOnlyCampos(BizAction a) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 	  g.setRecords(GetcDato().getOnlyCampos(this.GetcDato(), a, true,true));
  	g.setObjCustomList(this);
		g.setPreviewFlag(JWins.PREVIEW_SI);
		g.setForceActionPreview(100);
		g.setPreviewSplitPos(150);
		g.setModeView(JBaseWin.MODE_TREE);
	//	g.setToolbarForced(JWins.TOOLBAR_LEFT);
		g.toStatic();
  	return g;

	}


	public GuiInformes getInformes() throws Exception {
		GuiInformes g=new GuiInformes();
		g.setRecords(GetcDato().getInformes());
  	g.SetVision("INFORME");
		return g;
	}
	public GuiCustomListResult getResult(BizAction a) throws Exception {
		return getResult(a, false,false);
	}
	public GuiCustomListResult getResult(BizAction a,boolean onlyview, boolean embedded) throws Exception {
		GuiCustomListResult c = new GuiCustomListResult();
		c.setRecord(this.GetBaseDato());
		c.setFilterMap(a.getFilterMap());
		c.SetVision("");
		if (embedded && onlyview)  {
			c.setToolbarForced(JBaseWin.TOOLBAR_NONE);
			c.setMode(GuiCustomList.INFOEMBEDDED);
			
		}
		else if (embedded)  {
			c.setToolbarForced(JBaseWin.TOOLBAR_NONE);
			c.setMode(GuiCustomList.EMBEDDED);
		}
		else if (onlyview)  {
			c.setToolbarForced(JBaseWin.TOOLBAR_NONE);
			c.setMode(GuiCustomList.ONLY_TABLE);
		} 
		return c;
	}
	
	public GuiCustomListResult getResultPreview(BizAction a,boolean onlyview) throws Exception {
		GuiCustomListResult c = new GuiCustomListResult();
		c.setRecord(this.GetBaseDato());
		c.setFilterMap(a.getFilterMap());
		c.setToolbarForced(JBaseWin.TOOLBAR_NONE);
//		c.setInForm(onlyview);
		if (!c.GetcDato().isShowTodosLosDatosEnEdicion())
			c.SetVision("PREVIEW");
		if (!onlyview) 
			c.setMode(GuiCustomList.ONLY_TABLE);
		else
			c.setMode(GuiCustomList.EDIT_MODE);

		
		return c;
	}
	public GuiCustomList getEditInforme(BizAction a) throws Exception {
		GuiCustomListInforme c = new GuiCustomListInforme();
		c.setMode(GuiCustomList.EDIT_MODE);
		c.setRecord(this.GetBaseDato());

		
		return c;
	}

	public JAct getActResultPreview(BizAction a,boolean inform) throws Exception {
		return new JActQueryActive(this.getResultPreview(a, inform));
	}
	
//	public GuiCustomList getPreview() throws Exception {
//		GuiCustomList c = new GuiCustomList();
//		c.setRecord(this.GetBaseDato());
//		c.SetVision("PREVIEW");
//		return c;
//	}
//	public JWin getTargetWin() throws Exception {
//		return this.getTargetWins().getWinRef();
//	}

//  public JMap<String, String> getCamposGalery() throws Exception {
//  	JRecords rs = ((JWins)this.GetcDato().getObjRecordSet()).getRecords();
//  	JRecord r = rs.getRecordRef();
//  	JMap<String, String> map = JCollectionFactory.createMap();
//  	JIterator<JProperty> iter = r.getFixedProperties().getValueIterator();
//  	while (iter.hasMoreElements()) {
//  		JProperty p = iter.nextElement();
//  		map.addElement(p.GetCampo(), p.GetDescripcion());
//  	}
//  	return map;
//  }

	
  
//  public GuiActions getActionLists() throws Exception {
//  	GuiActions actionList = new GuiActions();
//  	JWin w = this.getObjWinOwner();
//  	BizActions actions = w.getActionMapWithoutFilters();
//  	JIterator<BizAction> iter= actions.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizAction a = iter.nextElement();
//  		this.appendActions(actionList, a);
//  	}
//  	return actionList;
//  }
	
//  private void appendActions(GuiActions actions, BizAction a) throws Exception {
//  	if (a.hasSubActions()) {
//  		JIterator<BizAction> iter = a.GetSubAcciones().getStaticIterator();
//  		while (iter.hasMoreElements()) {
//  			BizAction sa = iter.nextElement();
//  			this.appendActions(actions, sa);
//  		}
//  	}
//		if (!a.isForCustomList()) return;
//		GuiAction wa = new GuiAction();
//		wa.setRecord(a);
//		actions.addRecord(wa);
//  }

//	public JWins getTargetWins() throws Exception {
//		if (this.targetWins!=null) return this.targetWins;
//		JRelation r = this.getSourceRelations().findRel(this.GetcDato().getRelId());
//		JWins w = (JWins)r.getRelationClass().newInstance();
//		return (this.targetWins=w);
//	}
//
//	public JWin getObjWinOwner() throws Exception {
//		if (this.winOwner!=null) return this.winOwner;
//		JWin r = (JWin)Class.forName(this.GetcDato().getWinOwner()).newInstance();
//		return (this.winOwner=r);
//	}
	
//
//	public BizAction getAction() throws Exception {
//		return this.getObjWinOwner().findActionByUniqueId(this.GetcDato().getIdAction());
//	}
//
//	public JWins getFinalWins() throws Exception {
//		return this.getAction().getObjSubmit().getWinsResult();
//	}
//
//	public JWin getFinalWin() throws Exception {
//		return this.getFinalWins().GetClassWin().newInstance();
//	}
	

	public JWin getSQLConfig() throws Exception {
		GuiSQL sql = new GuiSQL();
		sql.GetcDato().read(this.GetcDato().getCompany(), this.GetcDato().getListId());
		return sql;
	}

	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", this.GetcDato().getCompany());
		sql.getRecord().addFilter("class_evento", GetcDato().getClass().getCanonicalName());
		sql.getRecord().addFilter("id_evento", this.GetcDato().getListId());
		sql.getRecord().addFilter("descripcion", this.GetcDato().getDescription());
		sql.getRecord().addFilter("id_plantilla", this.GetcDato().getModelo());
  	if (a!=null && a.getFilterMap()!=null) sql.getRecord().addFilter("parametros", a.getFilterMap().serialize());
  	sql.getRecord().addFilter("extradata", this.GetcDato().getObjAllCampos().serialize());
		sql.setDropListener(this);
//		sql.GetcDato().setFilterMap(a.getFilterMap());
		this.setFilterMap(a.getFilterMap());
		return sql;
	}
	JFilterMap filters;


  public JFilterMap getFilterMap() {
		return filters;
	}

@Override
public void setRecord(JBaseRecord zDato) throws Exception {
	super.setRecord(zDato);
	GetcDato().analizeRol(GetcDato().getLogica());
}
	public void setFilterMap(JFilterMap action) {
		this.filters = action;
	}
	 public boolean isSystemProtect() throws Exception {
	  	return GetcDato().isSystemProtect();
	  }

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	@Override
	public int GetSimpleClick() {
		return 18;
	}

	@Override
	public boolean read(String company, String id) throws Exception {
		return GetcDato().read(company,id, getFilterMap());
	}

}
