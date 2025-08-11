package pss.common.customList.config.customlist;

import pss.common.customList.config.field.ICampo;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.relation.JRelation;
import pss.common.event.sql.BizSqlEvent;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.data.interfaces.structure.ROrderBy;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JLogicaCustomListMatriz extends JLogicaCustomListAgrupado {
	
	public boolean isCategoria(BizCampo c) throws Exception {
		return isEje(c);
	}
	public boolean isDataset(BizCampo c) throws Exception {
		if (c.isColumna()) return true;
		if (c.getCampo().equals("")) return false;
		if (c.getTipoCampo().equalsIgnoreCase("JDATE")) return false;
		return isEje(c);
	}
	public boolean isValor(BizCampo c) throws Exception {
		return !isEje(c);
	}
	
	public boolean isCampo(BizCampo c) throws Exception {
		if (c.isGroup()) return false;
		if (!c.getVisible()) return false;
		if (isEje(c)) return false;
		if (c.isOnlyFiltro()) return false;
		return true;
	}
	public boolean isEje(BizCampo c) throws Exception {
		if (c.isGroup()) return false;
		if (!c.getVisible()) return false;
		if (c.isCampo()) return false;
		if (c.isLinea()) return false;
		if (c.isColumna()) return true;
		if (c.isFila()) return true;
		if (c.isOnlyFiltro()) return false;
		if (c.hasOperador()) return true;
		if (c.isCampoCantidad()) return false;
		if (c.isCampoFormula() && c.isCampoFormulaConFuncion()) return false;
		if (c.isCampoFormula() && !c.isCampoFormulaConFuncion()) return true;
  	if (c.getTipoCampo().equalsIgnoreCase("JSTRING")) return true;
		if (c.getTipoCampo().equalsIgnoreCase("JBOOLEAN")) return true;
		if (c.getTipoCampo().equalsIgnoreCase("JDATE")) return true;
		return false;
	}
	public boolean isColumna(BizCampo c) throws Exception {
		if (c.isGroup()) return false;
		if (!isEje(c)) return false;
		if (c.isOnlyFiltro()) return false;
		if (c.isColumna()) return true;
		if (c.isCampoCantidad()) return false;
		if (c.getTipoCampo().equalsIgnoreCase("JDATE")) return true;
		return false;
	}
	public boolean isFila(BizCampo c) throws Exception {
		if (!isEje(c)) return false;
		if (c.isColumna()) return false;
		if (c.isOnlyFiltro()) return false;
		if (c.isFila()) return true;
		if (!c.getTipoCampo().equalsIgnoreCase("JDATE")) return true;
		return false;
	}
  public String getSqlTotalizeRow(BizCampo c) throws Exception {
  	if (customList.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(customList.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	BizCustomList biz = customList.getClon();
  	biz.clean();
//  	biz.setObjFiltros(customList.getObjFiltros());
  	biz.setFiltrosDinamicos(customList.getFiltrosDinamicos());
  	String name=biz.getLogica().prepareOneFields(c,wins.getRecords(),null,BizCampo.FUNTION_SUM);
  	if (name!=null && name.startsWith("VIRTUAL:")) return name;
  	biz.getLogica().setWithgeo(false);
  	biz.getLogica().setCustomList(customList);
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoins(wins.getRecords(), true);
  	biz.getLogica().prepareFilters(wins.getRecords(), true);
  	biz.getLogica().prepareFiltersDinamico(wins.getRecords(), true);
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
  //	reg.setDistinct(true);
  	return reg.ArmarSelect();
  }
  public String getSqlTotalize(BizCampo c) throws Exception {
  	if (customList.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()==null) throw new Exception("No se puede visualizar detalles de consultas basadas en SQL");
   	JWins wins = (JWins)Class.forName(customList.getObjRelation().getObjRecordTarget().getRelationMap().getSourceWinsClass()).newInstance();
//  	wins.setRecords(this.customList.GetcDato().getAllRecords());
  	BizCustomList biz = customList.getClon();
  	biz.clean();
//  	biz.setObjFiltros(customList.getObjFiltros());
  	biz.setFiltrosDinamicos(customList.getFiltrosDinamicos());
  	String name=biz.getLogica().prepareOneFields(c,wins.getRecords(),null,BizCampo.FUNTION_SUM);
  	if (name!=null && name.startsWith("VIRTUAL:")) return name;
  	biz.getLogica().setCustomList(customList);
  	biz.getLogica().prepareTables(wins.getRecords());
  	biz.getLogica().prepareJoins(wins.getRecords(), true);
  	biz.getLogica().prepareFilters(wins.getRecords(), true);
  	biz.getLogica().prepareFiltersDinamico(wins.getRecords(), true);
  	biz.getLogica().prepareLiteralFilters(wins.getRecords());
  	JBaseRegistro reg=JBaseRegistro.recordsetFactory(wins.getRecords());
  //	reg.setDistinct(true);
  	return reg.ArmarSelect();
  }

  public void prepareFields(JBaseRecord r) throws Exception {
  	r.clearFields();
  	
  	JIterator<BizCampo> iterE = customList.getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		f.prepareEje(r);
//  		f.prepareRanking(r);
 			f.prepareDiferencia(r);

  	}  
  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!f.isRolCampo()) continue;
  		if (f.isPorcentaje()) {
  			f.prepareField(r,getSqlTotalize(f),false);
  		}else 
  			f.prepareField(r);
  	}
  	if (isWithgeo()) {
      iterE = customList.getObjEjes().getStaticIterator();
    	while (iterE.hasMoreElements()) {
    		ICampo f = iterE.nextElement();
    		if (!f.hasGeoCampo()) continue;
    		f.prepareGeoField(r);
    	}  
    	iter = customList.getObjCampos().getStaticIterator();
    	while (iter.hasMoreElements()) {
    		BizCampo f = iter.nextElement();
    		if (!f.hasGeoCampo()) continue;
    		f.prepareGeoField(r);
    	}
  	}

  }
  public void extractField(BizSqlEvent event,JWin owner,int field) throws Exception{
   	int i=0;//customList.getObjCampos().getStaticItems().size()==1?1:0;//parche porque el campo select no viene del todo bien
   	JIterator<BizCampo> iter2 = customList.getObjEjes().getStaticIterator();
  	while (iter2.hasMoreElements()) {
  		BizCampo f = iter2.nextElement();
  		if (!f.isRolFila()) continue;
  		i++;
  	}
  	if (i==0) i=1;//esto hay que arreglarlo, el sistema me manda la posicion pero falla en las filas que tienen colspan!
  	int j=0;
   	JIterator<BizCampo> iter3 = customList.getObjCampos().getStaticIterator();
  	while (iter3.hasMoreElements()) {
  		BizCampo f = iter3.nextElement();
  		if (!f.isRolCampo()) continue;
  		j++;
  	}
  	int pos = (field-i)%j;
  	i =0;
//  	JIterator<BizCampo> iter = customList.getCamposAlfabetico().getStaticIterator();
  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!isCampo(f)) continue;
      if ((i++)!=pos) continue;
      if (!f.hasFunction() && f.getFixedProp().isVirtual()) {
        	JProperty prop = f.getFixedProp();
        	String filters ="";
  				JStringTokenizer t = JCollectionFactory.createStringTokenizer(prop.getDependiente(), ';');
  				while (t.hasMoreTokens()) {
  					String filtro = t.nextToken();
  					String valor = owner.getRecord().getProp(f.getNameField(filtro)).toString();
  					filters+=filtro+"="+valor+":";
 				  }
  				event.setCampoVirtual("VIRTUAL:"+f.getObjRecordTarget().getClass().getName()+"|"+filters+"|"+f.getCampo());
      }
    	event.setIdCampo(f.getSecuencia()); 
    	event.setCampo(f.getNameField()); 
			break;
  	}
  	
	}

  public String prepareOneFields(BizCampo f,JRecords<?> r,String SQLtotalize,String force) throws Exception {

  	if (force!=null) {
    	f.setOver(true);
    	f.prepareField(r,SQLtotalize,true);
    }
    else if (f.isPorcentaje()) {
    	f.setOver(false);
    	if (SQLtotalize!=null)
      	f.prepareField(r,SQLtotalize,false);
    	else
    		f.prepareField(r);
    } else if (f.isCampoFormulaConFuncion()) {
    	return f.getNameField();
    } else
    	f.prepareField(r);
		return f.getNameField();
  	
  }
  public void prepareJoins(JRecords<?> r, boolean totalizer) throws Exception {
  	super.prepareJoins(r, totalizer);
  	JIterator<BizCampo> it3 = customList.getObjEjes().getStaticIterator();
		while (it3.hasMoreElements()) {
			BizCampo e = it3.nextElement();
			this.attachJoin(r, e);
			if (!isWithgeo()) continue;
  		if (!e.hasGeoCampo()) continue;
			this.attachGeoJoin(r, e);
		}

  }
  
   public void prepareJoinsDetail(JRecords<?> r, boolean totalizer) throws Exception {
		super.prepareJoinsDetail(r, totalizer);
		JIterator<BizCampo> it3 = customList.getObjEjes().getStaticIterator();
		while (it3.hasMoreElements()) {
			this.attachJoinDetail(r, it3.nextElement());
		}
  }
  public void attachJoinDetail(JRecords<?> r, BizCampo f) throws Exception {
		if (r.GetTable().toLowerCase().equals(f.getTargetAlias().toLowerCase())) {
			r.addFixedFilter(f.getObjRelation().getSerializeJoin());
			return; 
		}
		this.attachJoin(r, f);
//	if (r.hasJoin(f.getTargetTableForFrom())) return;
//		JRelation rel = f.getObjRelation();
//		r.addJoin(rel.getTypeJoin(),f.getTargetTableForFrom(),rel.getAlias(), rel.getSerializeJoin());
  }
  
  @Override
  public void prepareFilters(JRecords<?> r, boolean totalizer) throws Exception {
  	super.prepareFilters(r, totalizer);
//		JIterator<BizCampo> it3 = customList.getObjEjes().getStaticIterator();
//		while (it3.hasMoreElements()) {
//			BizCampo c=it3.nextElement();
//			this.attachFilter(r, c.getObjRelation());
//			if (!isWithgeo()) continue;
//			if (!c.hasGeoCampo()) continue;
//			this.attachFilter(r, c.getObjGeoRelation());
//		}
	
  }
  public void prepareFiltersDetail(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception {
  	super.prepareFiltersDetail(r, ejes, filters, totalizer);
  	JIterator<BizCampo> iterE = customList.getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		
			r.addFilter(f.hasFunction()||f.hasOperador()?null:f.getTargetAlias(), f.getCampoFromFiltro(), customList.extract(ejes,f.getSecuencia()), "=");
  	} 
  }
  public void prepareFiltersDetail(JWin owner,JRecords<?> r) throws Exception {
  	super.prepareFiltersDetail(owner, r);
  	JIterator<BizCampo> iterE = customList.getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		
			r.addFilter(f.hasFunction()||f.hasOperador()?null:f.getTargetAlias(), f.getCampoFromFiltro(), owner.getRecord().getProp(f.getNameField()).toString(), "=");
  	} 
  }
  public  void prepareFiltersDetailFila(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
//			if (f.isDinamico()) {
//				String value = customList.extract(filters, f.getOrden());
//				if (value==null||value.equals("")||value.equals(",")) continue;
//				f.setValor(value);
//			}
			if (f.isGroup()) continue;
			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
				RJoins j = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
				j.addCondicion(f.getFixedFilter(true,false));
			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
				RJoins j = r.getStructure().getFirstJoin();
				if (j==null)
					r.addFixedFilter(f.getFixedFilter(false,false));
				else
					j.addCondicion(f.getFixedFilter(true,false));
			} else 
				r.addFixedFilter(f.getFixedFilter(false,false));
		}
  	JIterator<BizCampo> iterE = customList.getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		//if (f.isColumna()) continue;
			r.addFilter(f.hasFunction()||f.hasOperador()?null:f.getTargetAlias(), f.getCampoFromFiltro(),customList.extract(ejes, f.getSecuencia()), "=");
  	} 
  }
  public  void prepareFiltersDetailFila(JWin owner,JRecords<?> r) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
//			if (f.isDinamico()) {
//				String value = ((GuiDynamic)owner).getFilterDynamicMap().getFilterValue("filtro"+f.getOrden());
//				if (value==null||value.equals("")||value.equals(",")) continue;
//				f.setValor(value);
//			}
			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
				RJoins j = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
				j.addCondicion(f.getFixedFilter(true,false));
			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
				RJoins j = r.getStructure().getFirstJoin();
				if (j==null)
					r.addFixedFilter(f.getFixedFilter(false,false));
				else
					j.addCondicion(f.getFixedFilter(true,false));
			} else 
				r.addFixedFilter(f.getFixedFilter(false,false));
		}
  	JIterator<BizCampo> iterE = customList.getObjEjes().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		if (f.isRolColumna()) continue;
			r.addFilter(f.getTargetAlias(), f.getCampoFromFiltro(),owner.getRecord().getProp(f.getNameField()).toString(), "=");
  	} 
  }

  public void attachJoin(JRecords<?> r, BizCampo f) throws Exception {
		this.attachJoinDeep(r, f, f.getObjRelation());
  }

  public void attachGeoJoin(JRecords<?> r, BizCampo f) throws Exception {
		this.attachGeoJoinDeep(r, f, f.getObjGeoRelation());
  }  


  public void attachGeoJoinDeep(JRecords<?> r, BizCampo f, JRelation rel) throws Exception {
  	if (rel==null) return;
		if (rel.hasRelParent()) {
			this.attachGeoJoinDeep(r, f, rel.getObjRelParent());
		}
		if (rel.hasJoin()) {
			if (r.hasJoin(rel.getTargetTableForFrom(f.getGeoCampo()), rel.getAlias(),false)) return;
			String condicion = rel.getSerializeJoin();
			if ( rel.getObjRelParent()!=null && rel.getObjRelParent().getAlias()!=null && !rel.getObjRelParent().getAlias().equals(""))
				condicion = JTools.replace(condicion, rel.getObjRelParent().getObjRecordTarget().GetTable(), rel.getObjRelParent().getAlias());
			if ( rel.getSimpleAlias()!=null && rel.getAlias()!=null)
				condicion = JTools.replace(condicion, rel.getSimpleAlias(), rel.getAlias());
			r.addJoin(rel.getTypeJoin(), rel.getTargetTableForFrom(f.getGeoCampo()), rel.getAlias(), condicion);
		}
  }
  
  public void prepareOrders(JRecords<?> r) throws Exception {
  	boolean clear=false;
  	ROrderBy lastOrderBy=null;
  	boolean usedRnaking=false;

  	if (customList.isWithCC()) {
  		JRecords<BizCampo> ejes = customList.getObjEjesControl();
  		ejes.toStatic();
  		JIterator<BizCampo> it = ejes.getStaticIterator();
  		while (it.hasMoreElements()) {
  			BizCampo f = it.nextElement();
  			if (f.getFixedProp()!=null && f.getFixedProp().isVirtual()) continue;
  			if (!f.isCampoCantidad() && f.getFixedProp()!=null && f.getFixedProp().hasDependencias()) continue;
  			
  			if (!clear) {
  				r.clearOrderBy();
  				clear = true;
  			}
  			lastOrderBy=r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
  			BizCampo o = f.getObjCampoOrden();
  			if (o!=null){
  				usedRnaking=true;
  	  		if (!o.getCampo().equals(BizCampo.FUNTION_COUNT) && !o.hasFunction()) {
  	    		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
  	    		if (p.hasDependencias()) {
  	    			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
  	    			while (t.hasMoreTokens()) {
  	    				String c = t.nextToken();
  	    				r.addGroupBy(o.getTargetAlias(), c);
  	    				r.addOrderBy(o.getTargetAlias(), c, o.getOrdenAscDesc());
  	    			}
  	    			continue;
  	    		}
  	  			r.addGroupBy(o.getTargetAlias(), o.getCampo());
  	  		}
  	  		
  				r.addOrderBy(null, o.getNameField(), o.getOrdenAscDescForce("ASC"));
  				r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
  				
  				
  			}
  		}
  		
  	}
  	
  	BizCampo lastEje=null;
		JRecords<BizCampo> ejesL = customList.getObjEjes();
		ejesL.toStatic();
		JIterator<BizCampo> itL = ejesL.getStaticIterator();
		while (itL.hasMoreElements()) {
			BizCampo f = itL.nextElement();
			if (f.isRolColumna()&&!customList.sinCategoria()) continue;
			if (f.isDependiente()) continue;
			if (!f.isCampoCantidad() && f.getFixedProp()!=null && f.getFixedProp().hasDependencias()) continue;
			if (!clear) {
				r.clearOrderBy();
				clear = true;
			}
			lastEje=f;
			BizCampo o = f.getObjCampoOrden();
			lastOrderBy=r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
			if (!usedRnaking && o!=null){
				usedRnaking=true;
	  		if (!o.getCampo().equals(BizCampo.FUNTION_COUNT) && !o.hasFunction()) {
	    		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
	    		if (p.hasDependencias()) {
	    			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
	    			while (t.hasMoreTokens()) {
	    				String c = t.nextToken();
	    				r.addGroupBy(o.getTargetAlias(), c);
	    				r.addOrderBy(o.getTargetAlias(), c, o.getOrdenAscDesc());
	    			}
	    			continue;
	    		}
	  			r.addGroupBy(o.getTargetAlias(), o.getCampo());
	  		}
	  		
				r.addOrderBy(null, o.getNameField(), f.getOrdenAscDescForce("ASC"));
				r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
					
				if (customList.sinCategoria()&&f.getOrdenLimite()>0) {
					r.setTop(f.getOrdenLimite());
				}
			}
		}
		JRecords<BizCampo> ejes2 = customList.getObjEjes();
		ejes2.toStatic();
		JIterator<BizCampo> it2 = ejes2.getStaticIterator();
		while (it2.hasMoreElements()) {
			BizCampo f = it2.nextElement();
			if (f.getFixedProp()!=null && f.getFixedProp().isVirtual()) continue;
			if (!f.isCampoCantidad() && f.getFixedProp()!=null && f.getFixedProp().hasDependencias()) continue;
			if (f.isColumna()) continue;
			if (f.isDependiente()) continue;
			if (!clear) {
				r.clearOrderBy();
				clear = true;
			}
			BizCampo o = f.getObjCampoOrden();
			lastOrderBy=r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
			if (!usedRnaking && o!=null) {
				usedRnaking=true;
				if (!o.getCampo().equals(BizCampo.FUNTION_COUNT) && !o.hasFunction()) {
	    		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
	    		if (p.hasDependencias()) {
	    			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
	    			while (t.hasMoreTokens()) {
	    				String c = t.nextToken();
	    				r.addGroupBy(o.getTargetAlias(), c);
	    				r.addOrderBy(o.getTargetAlias(), c, o.getOrdenAscDesc());
	    			}
	    			continue;
	    		}
	  			r.addGroupBy(o.getTargetAlias(), o.getCampo());
	  		}
	  		
				r.addOrderBy(null, o.getNameField(), o.getOrdenAscDescForce("ASC"));
				r.addOrderBy(f.getNameField(), f.getOrdenAscDescForce("ASC"));
			
			}
		}
		if (lastOrderBy!=null) {
			r.clearOrderBy(lastOrderBy);
		}
		JIterator<BizCampo> iter;
		if (customList.isGraphRequiredAgrupado()) {
			iter = customList.getObjAllCampos().getStaticIterator();
			while (iter.hasMoreElements()) {
				BizCampo o = iter.nextElement();
				if (o.isOnlyFiltro()) continue;
				if (o.isDependiente()) continue;
				if (!o.getVisible()) continue;
				if (!o.isCampoCantidad() && o.getFixedProp()!=null && o.getFixedProp().hasDependencias()) continue;
				if (!o.isDateInput()&&!o.isDateTimeInput()) continue;
				if (!clear) {
					r.clearOrderBy();
					clear=true;
				}
				r.addOrderBy(null, o.getNameField(), o.getOrdenAscDescForce("ASC"));
			}
		}

//		JIterator<BizCampo> iterO = customList.getObjOrders().getStaticIterator();
//		while (iterO.hasMoreElements()) {
//			BizCampo o = iterO.nextElement();
//			if (!o.isTableBased()) continue;
//			if (!o.isCampoCantidad() && o.getFixedProp()!=null && o.getFixedProp().hasDependencias()) continue;
//			if (!clear) {
//				r.clearOrderBy();
//				clear=true;
//			}
//			r.addOrderBy(null, o.getNameField(), o.getOrdenAscDescForce("DESC"));
//		}


  }
	public boolean isMatriz() { return true;}
	public boolean isAgrupado() { return false;}

}
