package pss.common.customList.config.dynamic;



import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.maps.GeoPositionable;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.GeoPosition;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDynamic extends JWin implements GeoPositionable{

	private GuiCustomList customList;
	private JFilterMap filterDynamicMap;

  public JFilterMap getFilterDynamicMap() {
		return filterDynamicMap;
	}

	public void setFilterDynamicMap(JFilterMap filterMap) {
		this.filterDynamicMap = filterMap;
	}

	public void setCustomList(GuiCustomList customList) throws Exception {
		this.customList = customList;
		((BizDynamic)this.getRecord()).setCustomList(customList.GetcDato());
	}

	/**
   * Constructor de la Clase
   */
  public GuiDynamic(GuiCustomList list) throws Exception {
  	this.customList=list;
//  	this.sourceWin=s;
  }
  
  public GuiDynamic() throws Exception {
  }

  public boolean canConvertToURL() throws Exception {
  	return false;
  }
  
  public GuiCustomList getCustomList() {
  	return customList;
  }

//  public JRecord ObtenerDato()   throws Exception { return (JRecord)this.sourceWin.GetBaseDato(); }
  public JRecord ObtenerDato()   throws Exception { return new BizDynamic(); }
  public int GetNroIcono()   throws Exception { return 10027; }
  public String  GetTitle()    throws Exception  { return ( this.customList!=null?this.customList.GetcDato().getDescription():"Lista DiN°mica"); }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDynamic.class; }

  public void createActionMap() throws Exception {
  	this.createActionQuery();
  	this.addAction(20, "Agregar Indicador", null, 15019, true, true);
  	this.addAction(10, "Detalle", null, 10047, true, true);
  	this.addAction(15, "Detalle", null, 10047, true, true);
  	this.addAction(17, "Detalle", null, 10047, true, true);
  	this.addAction(16, "Detalle Fila", null, 10046, true, true);
//  	if (this.customList==null) return;
//  	GuiCustomLists s = this.customList.getSecciones();
//  	JIterator iter = s.toStatic().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		GuiCustomList dl = (GuiCustomList)iter.nextElement();
//  		BizAction a = this.addAction(100+(int)dl.GetcDato().getListId(), dl.GetcDato().getDescription(), null, 1, false, false, true, "Group");
//  	}
  }
  @Override
  public int GetDobleClick(String columna, boolean isEje) throws Exception {
  	if (isEje) return 16;
  	return GetDobleClick();
  }  
  @Override
  public int GetDobleClick() throws Exception {
  	return this.customList.GetcDato().isMatriz()?15:this.customList.GetcDato().isAgrupado()?10:1;
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	if (a.getId()==1) return false;//!this.customList.GetcDato().isAgrupado() && this.customList.GetcDato().hasSecciones();
  	if (a.getId()==20) return BizUsuario.getUsr().IsAdminCompanyUser();
  	if (a.getId()==10) return this.customList.GetcDato().isAgrupado();
  	if (a.getId()==15) return this.customList.GetcDato().isMatriz();
  	if (a.getId()==16) return this.customList.GetcDato().isMatriz();
  	if (a.getId()==17) return this.customList.GetcDato().isLista();
  	//if (a.getId()==20) return this.customList.GetcDato().isMatriz();
//  	if (a.getId()>100) return !this.customList.GetcDato().isAgrupado();
  	return true;
  }
  
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return actDetail(this.getDetalles());
  	if (a.getId()==15) return actDetail(this.getDetalleMatriz());
  	if (a.getId()==16) return actDetail(this.getDetalleFila());
  	if (a.getId()==17) return actDetail(this.getDetallesLista());
  	if (a.getId()==20) return new JActNew(this.getNewEvento(a),0);
//  	GuiCustomLists s = this.customList.getResult(a).getSecciones();
//  	JIterator iter = s.toStatic().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		GuiCustomListResult dl = (GuiCustomListResult)iter.nextElement();
//  		if (100+(int)dl.GetcDato().getListId()!=a.getId())
//  			continue;
//  		dl.GetcDato().setObjRecordSource(this.getRecord());
//  		return new JActWins(dl.getDynamicWins(a));
//  	}
  	return null;
  }
  public JAct actDetail(JWins wins) throws Exception {
  		wins.toStatic(); 
  		if (wins.getRecords().getStaticItems().size()==1) {
  			JAct action = actWinDetail(wins.getFirstRecord());
  			if (action != null)
  				return action;
  		}
  		return new JActWins(wins);
  } 
 
  public JAct actWinDetail(JWin w) throws Exception {
		BizAction action = null;
		String uniqueId = w.getSimpleClickByUniqueId();
		if (uniqueId!=null) {
			action = w.findActionByUniqueId(uniqueId);
		}
		if (action == null) {
			uniqueId = w.getDobleClickByUniqueId();
			if (uniqueId!=null) {
				action = w.findActionByUniqueId(uniqueId);
			}
		}
		if (action == null)
			w.findAction(w.GetSimpleClick());
		if (action == null)
			action = w.findAction(w.GetDobleClick());
		if (action == null)
			action = w.findAction(JWin.ACTION_DROP);
		if (action == null)
			action = w.findAction(JWin.ACTION_QUERY);
		if (action != null)
			return w.getSubmit(action);
		return null;
  }  

  public JWin getNewEvento(BizAction a) throws Exception {
   	GuiSqlEvent sqlEvent = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance();
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
  	String select =this.getSelectedCell();
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	BizSqlEvent event = sqlEvent.GetcDato();
    event.setCompany(biz.getCompany());
  	event.setDescripcion(biz.getDescription());
   	event.setCustomList(this.getCustomList().GetcDato().getListId());
  	event.setClassDetalle(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
  	biz.getLogica().extractField(event,this,Integer.parseInt(select));
  	String ejesDetalle="";
  	String filtrosDetalle="";
  	String descrEjesDetalle="";
  	String descrFiltrosDetalle="";
		JRecords<BizCampo> ejes = this.customList.GetcDato().getEjes();
		ejes.clearOrderBy();
		ejes.addOrderBy("secuencia");
  	JIterator<BizCampo> iterE = ejes.getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		ejesDetalle+= (ejesDetalle.equals("")?"":"|")+f.getSecuencia()+"="+this.getRecord().getProp(f.getNameField()).toString();
  		descrEjesDetalle+= (descrEjesDetalle.equals("")?"":", ")+f.getBeautyDescrCampo()+" es "+this.getRecord().getProp(f.getNameField()).toString();
  	} 
  	if (ejesDetalle.equals("")) {// no hay ejes
    	JIterator<BizCampo> iterC = this.customList.GetcDato().getCampos().getStaticIterator();
    	while (iterC.hasMoreElements()) {
    		BizCampo f = iterC.nextElement();
    		if (!f.isRolCampo()) continue;
    		if (!f.getVisible()) continue;
    		if (f.getCampo().equals(event.getCampo())) continue;
    		if (f.hasFunction()) continue;
    		ejesDetalle+= (ejesDetalle.equals("")?"":"|")+f.getSecuencia()+"="+this.getRecord().getProp(f.getNameField()).toString();
    		descrEjesDetalle+= (descrEjesDetalle.equals("")?"":", ")+f.getDescrCampo()+" es "+this.getRecord().getProp(f.getNameField()).toString();
    	} 
  		
  	}
  	JIterator<BizCampo> iterF = this.customList.GetcDato().getObjFiltros().getStaticIterator();
  	while (iterF.hasMoreElements()) {
  		BizCampo f = iterF.nextElement();
  		if (f.isDinamico()) 
  			filtrosDetalle+= (filtrosDetalle.equals("")?"":"|")+f.getOrden()+"="+a.getFilterMapValue("filtro"+f.getOrden(), "");
  		descrFiltrosDetalle+= (descrFiltrosDetalle.equals("")?"":", ")+f.getDescrFiltro(true,a);
  	} 
  	event.setEjesValor(ejesDetalle);
  	event.setFiltrosValor(filtrosDetalle);
  	event.setObservacion("Particularidades:\n"+descrEjesDetalle+"\nFiltros:\n"+descrFiltrosDetalle);
  	event.fillSqlFromCustomList();
		event.fillSqlDetalleFromCustomList();
		return sqlEvent;
  	
  }  
  /*
  public JWin getNewEventoOld(BizAction a) throws Exception {
   	GuiSqlEvent sqlEvent = new GuiSqlEvent();
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	String select =this.getSelectedCell();
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	String name=biz.getLogica().prepareOneFields(this,Integer.parseInt(select),wins.getRecords(),getSqlTotalize(a),null);
  	if (name!=null && !name.startsWith("VIRTUAL:")){
	  	biz.getLogica().prepareTables(wins.getRecords());
	  	biz.getLogica().prepareJoinsDetail(wins.getRecords());
	  	biz.getLogica().prepareFiltersDetail(this,wins.getRecords(),a);
	  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
	  	biz.getLogica().prepareOrders(wins.getRecords());
	  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjCampos().getStaticIterator();
	  	while (iter.hasMoreElements()) {
				BizCampo c = iter.nextElement();
				if (c.hasFunction() && !c.getFuncion().equals(BizCampo.FUNTION_LIT)) continue;
				if (c.getNameField().equals(name)) continue;
				JProperty p = c.getFixedProp();
				if (!p.hasDependencias()) {
					if (c.hasFunction())
						this.attachFilterLiteral(wins.getRecords(), c, c.getTargetAlias(),c.getCampo(),c.getFormatParam());
					else
					  this.attachFilter(wins.getRecords(), c, c.getTargetAlias(),c.getCampo());
				} else {
					JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
					while (t.hasMoreTokens()) {
						this.attachFilter(wins.getRecords(), c, c.getTargetAlias(), t.nextToken());
					}
				}
	
	  	}
  	}
  	String ejesDetalle="";
  	String filtrosDetalle="";
  	JIterator<BizEje> iterE = this.customList.GetcDato().getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizEje f = iterE.nextElement();
  		ejesDetalle+= (ejesDetalle.equals("")?" ":", ")+f.getBeautyDescrCampo()+" es "+this.getRecord().getProp(f.getNameField()).toString();
  	} 
  	JIterator<BizFiltro> iterF = this.customList.GetcDato().getObjFiltros().getStaticIterator();
  	while (iterF.hasMoreElements()) {
  		BizFiltro f = iterF.nextElement();
  		if (f.isDinamico()) continue;
  		filtrosDetalle+= (filtrosDetalle.equals("")?" ":", ")+f.getDescrFiltro();
  	} 
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
   	reg.setDistinct(true);
    
  	String sql = name!=null&&name.startsWith("VIRTUAL:")?name: reg.ArmarSelect();
  	String sqlCampo=name!=null&&name.startsWith("VIRTUAL:")?"VIRTUAL":name;
   	String sqlDetalle = getSqlDetalle(a);
    sqlEvent.GetcDato().setCompany(biz.getCompany());
  	sqlEvent.GetcDato().setDescripcion(biz.getDescription());
  	sqlEvent.GetcDato().setObservacion("Particularidades:\n"+ejesDetalle+"\nFiltros:\n"+filtrosDetalle);
  	sqlEvent.GetcDato().setCampo(sqlCampo);
  	sqlEvent.GetcDato().setConsulta(sql);
  	sqlEvent.GetcDato().setCustomList(this.getCustomList().GetcDato().getListId());
  	sqlEvent.GetcDato().setClassDetalle(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass());
  	sqlEvent.GetcDato().setConsultaDetalle(sqlDetalle);
  	sqlEvent.GetcDato().addFilter("company", biz.getCompany());
  	sqlEvent.GetcDato().addFilter("campo", sqlCampo);
  	sqlEvent.GetcDato().addFilter("consulta", sql);
  	return sqlEvent;
  }  
  */
/*
  public String getSqlTotalize(BizAction a) throws Exception {
  	GuiSqlEvent sqlEvent = new GuiSqlEvent();
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	String select =this.getSelectedCell();
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	String name=biz.getLogica().prepareOneFields(this,Integer.parseInt(select),wins.getRecords(),null,BizCampo.FUNTION_SUM);
  	if (name!=null && name.startsWith("VIRTUAL:")) return name;
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoins(wins.getRecords());
  	biz.getLogica().prepareFilters(wins.getRecords());
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
  	biz.getLogica().prepareOrders(wins.getRecords());
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
			BizCampo c = iter.nextElement();
			if (c.hasFunction() && !c.getFuncion().equals(BizCampo.FUNTION_LIT)) continue;
			
			JProperty p = c.getFixedProp();
			if (!p.hasDependencias()) {
				if (c.hasFunction())
					this.attachFilterLiteral(wins.getRecords(), c, c.getTargetAlias(),c.getCampo(),c.getFormatParam());
				else
				  this.attachFilter(wins.getRecords(), c, c.getAlias(),c.getCampo());
			} else {
				JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
				while (t.hasMoreTokens()) {
					this.attachFilter(wins.getRecords(), c, c.getTargetAlias(), t.nextToken());
				}
			}

  	}
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
  	reg.setDistinct(true);
  	return reg.ArmarSelect();
  } 
  */ 
  
  public JWins getDetalleMatriz() throws Exception {
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	biz.getLogica().setWithgeo(false);
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
  	biz.getLogica().prepareFiltersDetail(this,wins.getRecords());
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
//  	biz.getLogica().prepareOrders(wins.getRecords());

  	String select =this.getSelectedCell();
		
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
			BizCampo c = iter.nextElement();
			if (!c.isRolCampo()) continue;
			if (c.hasFunction() && !c.getFuncion().equals(BizCampo.FUNTION_LIT)) continue;
				JProperty p = c.getFixedProp();
			if (!p.hasDependencias()) {
				if (c.hasFunction())
					this.attachFilterLiteral(wins.getRecords(), c, c.getTargetAlias(),c.getCampo(),c.getFormatParam());
				else
				  this.attachFilter(wins.getRecords(), c, c.getTargetAlias(),c.getCampo());
			} else {
				JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
				while (t.hasMoreTokens()) {
					this.attachFilter(wins.getRecords(), c, c.getTargetAlias(), t.nextToken());
				}
			}

  	}
  	wins.setShowFilters(false);
  	wins.getRecords().setDistinct(true);
  	return wins;
  }  
  public String getSqlDetalle() throws Exception {
  	JWins wins = getDetalleMatriz();
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
//  	reg.setDistinct(true);
  	return reg.ArmarSelect();
  }
  public JWins getDetalleFila() throws Exception {
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	biz.getLogica().setWithgeo(false);
  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareFiltersDetailFila(this,wins.getRecords());
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
//  	biz.getLogica().prepareOrders(wins.getRecords());


  	String select =this.getSelectedCell();
		
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
			BizCampo c = iter.nextElement();
			if (!c.isRolCampo()) continue;
			if (c.hasFunction() && !c.getFuncion().equals(BizCampo.FUNTION_LIT)) continue;
			JProperty p = c.getFixedProp();
			if (!p.hasDependencias()) {
				if (c.hasFunction())
					this.attachFilterLiteral(wins.getRecords(), c, c.getTargetAlias(),c.getCampo(),c.getFormatParam());
				else
				  this.attachFilter(wins.getRecords(), c, c.getTargetAlias(),c.getCampo());
			} else {
				JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
				while (t.hasMoreTokens()) {
					this.attachFilter(wins.getRecords(), c, c.getTargetAlias(), t.nextToken());
				}
			}

  	}
  	wins.setShowFilters(false);
  	wins.getRecords().setDistinct(true);
  	return wins;
  }  
  public JWins getDetalles() throws Exception {
//		GuiDynamics d = new GuiDynamics();
//		d.setCustomList(this.customList);
//		return d;
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	BizCustomList biz = this.customList.GetcDato();
  	biz.clean();
  	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
  	biz.getLogica().prepareFiltersDetail(this,wins.getRecords());
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
 // 	biz.getLogica().prepareOrders(wins.getRecords());

//		JIterator<BizFiltro> iter2 = biz.getFiltros().getStaticIterator();
//		while (iter2.hasMoreElements()) {
//			BizFiltro f = iter2.nextElement();
//			if (!f.isDinamico()) continue;
//			biz.addFixedFilter(f.getFixedFilter());
//		}
		
  	JIterator<BizCampo> iter = this.customList.GetcDato().getObjAllCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
			BizCampo c = iter.nextElement();
			if (!c.isRolCampo()) continue;
			if (c.hasFunction() && !c.getFuncion().equals(BizCampo.FUNTION_LIT)) continue;
			if (!c.hasCampo()) continue;
			
			if (c.isDependiente()) continue;
			if (!c.isFilterInLista()) continue;
			JProperty p = c.getFixedProp();
			if (!p.hasDependencias()) {
				if (c.hasFunction())
					this.attachFilterLiteral(wins.getRecords(), c, c.getTargetAlias(),c.getCampo(),c.getFormatParam());
				else
				  this.attachFilter(wins.getRecords(), c, c.getTargetAlias(),c.getCampo());
			} else {
				JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
				while (t.hasMoreTokens()) {
					this.attachFilter(wins.getRecords(), c, c.getTargetAlias(), t.nextToken());
				}
			}

  	}
  	wins.setShowFilters(false);
  	wins.getRecords().setDistinct(true);
  	return wins;
  }
  public JWins getDetallesLista() throws Exception {
//	GuiDynamics d = new GuiDynamics();
//	d.setCustomList(this.customList);
//	return d;
	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
 	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();

	JIterator<JProperty> iter = customList.GetcDato().getObjRelation().getObjRecordTarget().getFixedProperties().getValueIterator();
	while (iter.hasMoreElements()) {
		JProperty p = iter.nextElement();
		if (!p.isKey()) continue;
		wins.getRecords().addFilter(p.GetCampo(), this.getRecord().getProp(p.GetCampo()));
	}

	

	wins.setShowFilters(false);
	return wins;
}
//  public JWins getDetallesLista() throws Exception {
////	GuiDynamics d = new GuiDynamics();
////	d.setCustomList(this.customList);
////	return d;
//	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
// 	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
////	wins.setRecords(this.customList.GetcDato().getAllRecords());
//	BizCustomList biz = this.customList.GetcDato();
//	biz.clean();
//	biz.getLogica().prepareJoinsDetail(wins.getRecords(), false);
//	biz.getLogica().prepareFiltersDetail(this,wins.getRecords());
//	biz.getLogica().prepareLiteralFilters(wins.getRecords());
//
//	JIterator<JProperty> iter = customList.GetcDato().getObjRelation().getObjRecordTarget().getFixedProperties().getValueIterator();
//	while (iter.hasMoreElements()) {
//		JProperty p = iter.nextElement();
//		if (!p.isKey()) continue;
//		wins.getRecords().addFilter(p.GetCampo(), this.getRecord().getProp(p.GetCampo()));
//	}

	
//
//	wins.setShowFilters(false);
//	return wins;
//}
  private void attachFilter(JRecords<?> r, BizCampo c, String table, String field) throws Exception { 
		String filtro = c.getFieldWithFormat(table, field);
		String valor = this.getRecord().getProp(c.getNameField(field)).toString();
		r.addFilter(filtro, valor).setTable(null);
  }
  private void attachFilterLiteral(JRecords<?> r, BizCampo c, String table, String field, String literal) throws Exception { 
		String valor = this.getRecord().getProp(c.getNameField(field)).toString();
		r.addFixedFilter(literal+" = '"+ valor+"'");
  }
	public String getFieldForeground(String zColName) throws Exception {
//		if (zColName.toLowerCase().indexOf("dur")!=-1) return "FF0000";
		return null;
	}
	public String getFieldBackground(String zColName) throws Exception {
		return null;
	}
	public String getFieldTooltip(String zColName) throws Exception {
		return null;
	}

	public int getHeightRow() throws Exception {
		return 15;
	}

	@Override
	public GeoPosition getGeoPosition(String field) throws Exception {
  	if (this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(this.customList.GetcDato().getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
   	// esto esta mal, solo anda si getgeoposicion no usa el jrecord
   	JWin w = wins.getWinRef();
   	if (!(w instanceof GeoPositionable)) return null;
   	return ((GeoPositionable)w).getGeoPosition(field);
	}
	
	@Override
	public JBaseWin getListenerForDragAndDropWeb(String zone,JBaseWin all) throws Exception {
		return ((GuiDynamics)all).customList;
	}
	public String getZoneDrop(String zone) throws Exception {
		if (zone.startsWith("data")) return "filtro";
		return zone;
	}
	
	public boolean acceptDrop(String zone) throws Exception {
		return false;
	}

 }
