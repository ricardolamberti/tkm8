package pss.bsp.contrato;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.conocidos2.GuiContratoConocidosV2;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalleBackendDobleRutas.BizDetalleBackendDobleRuta;
import pss.bsp.contrato.detalleBackendRutas.BizDetalleBackendRuta;
import pss.bsp.contrato.detalleUpfrontRutas.BizDetalleUpfrontRuta;
import pss.bsp.contrato.series.variaciones.GuiVariaciones;
import pss.bsp.contrato.wizardBackend.GuiWizardContratoBackend;
import pss.bsp.contrato.wizardObjetivo.GuiWizardContratoObjetivo;
import pss.bsp.contrato.wizardUpfront.GuiWizardContratoUpfront;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.common.event.action.GuiSqlEventAction;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.action.history.GuiSqlEventHistory;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecords;
import pss.core.tools.JConcatenar;
import pss.core.tools.JTools;
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
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;

public class GuiContratos extends JWins implements IContenidoCarpeta {



  /**
   * Constructor de la Clase
   */
  public GuiContratos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15014; } 
  public String  GetTitle()    throws Exception  { return GetVision().equals(BizContrato.CONTRATOS)?"Contratos":"Objetivos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiContrato.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Contrato Backend" );
    addActionNew( 10, "Nuevo Contrato Upfront" );
    addActionNew( 11, "Nuevo Objetivo" );
  	addAction(20, "Contratos conocidos", null, 10078, true, true).setInToolbarMore(true);
  	addAction(50, "Compartir Todos", null, 10078, true, true).setInToolbarMore(true);
  	addAction(55, "Imprimir Todos", null, 10050, true, true).setImprimir(true).setInToolbarMore(true);
  	addAction(56, "autogenerar contratos", null, 10050, true, true).setInToolbarMore(true);

//		this.addAction(20, "Config.Estimacion", null, 10020, true, true, true, "Group");
  }
	@Override
	public boolean OkAction(BizAction a) throws Exception {		
		
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			
			return false;
		}
		if (a.getId()==1) return this.GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==10) return this.GetVision().equals(BizContrato.CONTRATOS);
		if (a.getId()==11) return this.GetVision().equals(BizContrato.OBJETIVOS);
				
		if (a.getId()==50) return true;
		if (a.getId()==55) return true;
		if (a.getId()==56) return BizUsuario.getUsr().IsAdminCompanyUser();
		if (a.getId()==20) return BizUsuario.getUsr().IsAdminCompanyUser();
		return super.OkAction(a);
	}
  
  
  @Override
	public JAct getSubmitFor(final BizAction a) throws Exception {
		if (a.getId() == 18)	return new JActWins(this.getEstimadores());
		if (a.getId() == 20)	return new JActWins(this.getEstimadores());
  	if (a.getId()==20) return new JActWins(new GuiContratoConocidosV2());
  	if (a.getId()==50) return new JActNew(this.getCompartir(a), 4);
  	if (a.getId()==55) return new JActFileGenerate(this) { 
			public String generate() throws Exception {
				return imprimir(a);
			};
		};
	 	if (a.getId()==56) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				execProcessCambiar();
				super.submit();
			}
		};
	 	return null;
	}


	public JWins getEstimadores() throws Exception {
		GuiVariaciones var = new GuiVariaciones();
		if (!BizUsuario.IsAdminUser())
			var.getRecords().addFixedFilter("(company is null or company='"+BizUsuario.getUsr().getCompany()+"')");
		return var;
	}
	
	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.createMyWin(), 0);
		if (a.getId()==10) return new JActNew(this.createMyWinUpfront(), 0);
		if (a.getId()==11) return new JActNew(this.createMyWinObjetivo(), 0);
		return super.getSubmit(a);
	}
	public JWin createMyWinUpfront() throws Exception {
		JWin oWin=new GuiWizardContratoUpfront();
		this.joinData(oWin);
		oWin.getRecord().addFilter("company",BizBSPUser.getUsrBSP().getCompany());
		return oWin;
	}	
	public JWin createMyWinObjetivo() throws Exception {
		JWin oWin=new GuiWizardContratoObjetivo();
		this.joinData(oWin);
		oWin.getRecord().addFilter("company",BizBSPUser.getUsrBSP().getCompany());
		return oWin;
	}	
	public JWin createMyWin() throws Exception {
		JWin oWin=new GuiWizardContratoBackend();
		this.joinData(oWin);
//		oWin.getRecord().addFilter("pais", BizBSPCompany.getObjBSPCompany(BizBSPUser.getUsrBSP().getCompany()).getCountry());
		oWin.getRecord().addFilter("company",BizBSPUser.getUsrBSP().getCompany());
		return oWin;
	}	
	public void execProcessCambiar() throws Exception {
		JRecords<BizContrato> ctrs = new JRecords<BizContrato>(BizContrato.class);
		if (!BizUsuario.getUsr().isAdminUser())
			ctrs.addFilter("company", BizUsuario.getUsr().getCompany());
		JIterator<BizContrato> it = ctrs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizContrato c = it.nextElement();
			JIterator<BizDetalle> itd = c.getObjDetalles().getStaticIterator();
			while (itd.hasMoreElements()) {
				BizDetalle detalle = itd.nextElement();
				BizDetalle newdet = detalle.getObjLogicaInstance().getBiz();
				newdet.copyProperties(detalle);

				if (newdet instanceof BizDetalleBackendDobleRuta || newdet instanceof BizDetalleBackendRuta|| newdet instanceof BizDetalleUpfrontRuta) {
					newdet.execAutogenerar();
					
				}
				
			}			
		}
	}

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());
		zFiltros.addEditResponsive("Descripcion", "descripcion").setOperator("ilike");
  	zFiltros.addCheckResponsive("Activos", "active").SetValorDefault(BizContrato.ACTIVE);
  	zFiltros.addCDateResponsive("Vigentes en fecha", "fecha_desde");
 // 	zFiltros.NuevoCDate("Fecha hasta", "fecha_hasta");
  	if (this.GetVision().equals(BizContrato.CONTRATOS))
  		zFiltros.addCheckThreeResponsive("","resultado","Upfront",JFormRadioResponsive.NO_FILTER,"Backend","Upfront","-","Backend").SetValorDefault("").setRefreshForm(true);
  	super.ConfigurarFiltros(zFiltros);
  }

	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("active") && control.hasValue()) {
			if (control.getValue().equals("S") &&	!control.getControls().findControl("fecha_desde").hasValue()) {
				this.getRecords().addFilter("active","N","<>").setDynamic(true);
				return;
			} else {
				return;
			}
		}
		if (control.getIdControl().equals("fecha_desde") && control.hasValue()) {
			//this.getRecords().addFilter("fecha_hasta",control.getValue(),">=").setDynamic(true);
			this.getRecords().addFilter("fecha_hasta",control.getValue(),">=").setDynamic(true);
			this.getRecords().addFilter("fecha_desde",control.getValue(),"<=").setDynamic(true);
			return;
		}
		if (control.getIdControl().equals("resultado") && control.hasValue()) {
			this.getRecords().addFixedFilter(" where EXISTS ( select 1 from bsp_contrato_detalle where bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id and logica like '%"+control.getValue()+"%'  )").setDynamic(true);
			return;
		}
		super.asignFilterByControl(control);
	}
	
	
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isConcentrador() || BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("company");
  	if (GetVision().equals("SLIM")) {
      zLista.AddColumnaLista("Contrato","descripcion");
      zLista.AddColumnaLista("#Items","cant_detalles");
      zLista.AddColumnaLista("Base Com. "+(BizUsuario.getUsr().getObjCountry()==null?"":BizUsuario.getUsr().getObjCountry().GetMonedaLocal()),"base_comisionable");
      zLista.AddColumnaLista("base_comisionable_dolares");
      zLista.AddColumnaLista("% Cumpl.","nivel");
      zLista.AddColumnaLista("Ganancia "+(BizUsuario.getUsr().getObjCountry()==null?"":BizUsuario.getUsr().getObjCountry().GetMonedaLocal()),"ganancia");
      zLista.AddColumnaLista("ganancia_dolares");
      
      return;
  	}
    zLista.AddColumnaLista("has_atencion");
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
  	if (!BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
 		 zLista.AddColumnaLista("nivel");
 		 zLista.AddColumnaLista("ganancia");
 		 zLista.AddColumnaLista("ganancia_dolares");		
  	}

    zLista.AddColumnaLista("observaciones");
  }
 
  public String imprimir(BizAction a) throws Exception {
  	  String company =getFilterValue("company");
  	  if (company.equals("")) return null;
  		int i = 0;
  	 
  		JRecords<BizContrato> contratos = new JRecords<BizContrato>(BizContrato.class);
  		contratos.addFilter("company", getFilterValue("company"));
  		contratos.addFilter("active", BizContrato.ACTIVE);
  		JIterator<BizContrato> it = contratos.getStaticIterator();
  		String[] files = new String[contratos.getStaticItems().size()*2];
  		while (it.hasMoreElements()) {
  			BizContrato contrato = it.nextElement();
  			String f = contrato.imprimirResumen();
  			if (f==null) continue;
  			files[i++] = JPath.PssPathTempFiles() + "/" +f;
  			
  		}
  		
  	
  		String tempfile = company+ "/concat" + this.hashCode() + ".html";
  		JTools.MakeDirectory(JPath.PssPathTempFiles() + "/" +  company);
  		JConcatenar.concatenarHTML(files, JPath.PssPathTempFiles() + "/" + tempfile);
  	
  		
  		return tempfile;
  	 	
  }

	public JWin getCompartir(BizAction a) throws Exception {
		GuiSqlEventAction sql = new GuiSqlEventAction();
		sql.getRecord().addFilter("company", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("class_evento", BizBSPCompany.class.getCanonicalName());
		sql.getRecord().addFilter("id_evento", BizBSPUser.getUsrBSP().getCompany());
		sql.getRecord().addFilter("descripcion", "Informe contratos");
  	BizPlantilla pl = BizCompany.getObjPlantilla(BizBSPUser.getUsrBSP().getCompany(), "sys_informe");
		if (pl!=null) sql.getRecord().addFilter("id_plantilla",pl.getId());
		sql.setDropListener(this);
		this.setFilterMap(a.getFilterMap());
		return sql;
	}
	
  JFilterMap filter;


  public JFilterMap getFilterMap() {
		return filter;
	}


	public void setFilterMap(JFilterMap action) {
		this.filter = action;
	}
	
  @Override
  public JAct Drop(JBaseWin zBaseWin) throws Exception {
   	if (zBaseWin instanceof GuiSqlEventAction) {
  		GuiSqlEventAction action = (GuiSqlEventAction)zBaseWin;
  		if (action.GetcDato().isAccionDOWNLOAD()) {
  			return new JActFileGenerate(action) { 
  				public String generate() throws Exception {
  		  		BizSqlEventHistory hist=((GuiSqlEventAction)getResult()).GetcDato().getObjSqlEvent().generarAviso(getFilterMap(),((GuiSqlEventAction)getResult()).GetcDato(),false);
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


	@Override
	public boolean read(String company, String id) throws Exception {
		this.addFilterAdHoc("company", company);
		return true;
	}


	@Override
	public JAct getActResultPreview(BizAction a, boolean inform) throws Exception {
		return new JActWins(this);
	}
	
	 public boolean isSystemProtect() throws Exception {
	  	return false;
	  }

}
