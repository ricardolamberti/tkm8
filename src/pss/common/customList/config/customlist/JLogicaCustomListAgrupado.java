package pss.common.customList.config.customlist;

import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.sql.BizSqlEvent;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompany;
import pss.core.data.interfaces.structure.RJoins;
import pss.core.services.fields.JObject;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecords;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JWin;

public class JLogicaCustomListAgrupado extends JLogicaCustomList {
	BizCustomList customList;
	public void setCustomList(BizCustomList zCustomList) {
		customList=zCustomList;
	}
	public boolean isCampo(BizCampo c) throws Exception {
		if (c.isGroup()) return false;
		if (!c.getVisible()) return false;
		return true;
	}

	
  public  void prepareTables(JRecords<?> r) throws Exception {
  	if (customList.getObjRelation()==null) return;
  	r.getStructure().setTable(customList.getObjRelation().getObjRecordTarget().GetTable());
  	r.getStructure().setTableTemporal(customList.getObjRelation().getObjRecordTarget().GetTableTemporal());
	}

	public void prepareLiteralFilters(JRecords<?> r) throws Exception {
  	JRelations relations = customList.getObjRelation().getObjRecordTarget().getRelationMap();
  	JIterator<JRelation> iter = relations.getList().getIterator();
  	while (iter.hasMoreElements()) {
  		JRelation p = iter.nextElement();
  		if (!p.isRelationFixed()) continue;
    	JList<JPair> filters = p.getFilters();
    	JIterator<JPair> itFilter = filters.getIterator();
    	while (itFilter.hasMoreElements()) {
    		JPair filter = itFilter.nextElement();
    		String sFilter= JTools.replace((String)filter.firstObject(), "COMPANY_CUSTOMLIST",  BizCompany.getObjCompany(customList.getCompany()).getCustomListCompany());
    		sFilter= JTools.replace(sFilter, "COMPANY_TICKET",  BizCompany.getObjCompany(customList.getCompany()).getTicketCompany());
        	r.addFixedFilter(sFilter,(String)filter.secondObject());
    	}
  		if (p.hasJoin()) {
  			String table = p.getObjRecordTarget().GetTable();
  			if (r.hasJoin(table, p.getAlias())) return;
  			String condicion = p.getSerializeJoin();
  			if ( p.getObjRelParent()!=null && p.getObjRelParent().getAlias()!=null && !p.getObjRelParent().getAlias().equals(""))
  				condicion = JTools.replace(condicion, p.getObjRelParent().getObjRecordTarget().GetTable(), p.getObjRelParent().getAlias());
  			if ( p.getSimpleAlias()!=null && p.getAlias()!=null)
  				condicion = JTools.replace(condicion, p.getSimpleAlias(), p.getAlias());
  			r.addJoin(p.getTypeJoin(), table, p.getAlias(), condicion);
  		}
  	}
  }

  
  public void prepareFields(JBaseRecord r) throws Exception {
  	r.clearFields();
   	this.attachKeysToFields(r);

  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!f.isRolCampo()) continue;
  		f.prepareField(r);
  	}
  	if (!isWithgeo()) return;

  	iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!f.hasGeoCampo()) continue;
  		f.prepareGeoField(r);
  	}


  }
  
  public void extractField(BizSqlEvent event,JWin owner,int field) throws Exception{
	 	int i=0;
		JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
  		if (!f.isRolCampo()) continue;

			if ((i++)!=field) continue;
    	event.setIdCampo(f.getSecuencia()); 
    	event.setCampo(f.getNameField()); 
		}
	}
  public String prepareOneFields(JWin owner,int field,JRecords<?> r,String SQLtotalize,String force) throws Exception {
  	r.clearFields();
   	this.attachKeysToFields(r);
   	int i=1;
  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!f.isRolCampo()) continue;

  		if ((i++)!=field) continue;
      if (!f.hasFunction() && f.getFixedProp().isVirtual()) {
      	JProperty prop = f.getFixedProp();
      	String filters ="";
				JStringTokenizer t = JCollectionFactory.createStringTokenizer(prop.getDependiente(), ';');
				while (t.hasMoreTokens()) {
					String filtro = t.nextToken();
					String valor = owner.getRecord().getProp(f.getNameField(filtro)).toString();
					filters+=filtro+"="+valor+":";
				}
      	return "VIRTUAL:"+f.getObjRecordTarget().getClass().getName()+"|"+filters+"|"+f.getCampo();
      }
      return prepareOneFields(f, r, SQLtotalize, force);
  	}
  	return null;
  }
  public String prepareOneFields(BizCampo f,JRecords<?> r,String SQLtotalize,String force) throws Exception {
		if (force!=null) {
//    	f.setPorcentaje(false);
    	f.setOver(true);
    	f.prepareField(r,SQLtotalize,true);
    }
    else if (f.isPorcentaje()) {
    	if (SQLtotalize!=null)
      	f.prepareField(r,SQLtotalize,false);
    	else
    		f.prepareField(r);
    }else
    	f.prepareField(r);

		return f.getNameField();
  	
  }
  public void prepareOrders(JRecords<?> r) throws Exception {
  	
  	boolean clear=false;

//  	JIterator<BizOrder> iter = customList.getObjOrders().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizOrder o = iter.nextElement();
//  		if (!o.getCampo().equals(BizCampo.FUNTION_COUNT) && !o.hasFunction()) {
//    		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
//    		if (p.hasDependencias()) {
//    			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
//    			while (t.hasMoreTokens()) {
//    				String c = t.nextToken();
//    				r.addGroupBy(o.getTargetAlias(), c);
//    				r.addOrderBy(o.getTargetAlias(), c, o.getTipo());
//
//    			}
//    			continue;
//    		}
//
//    		r.addGroupBy(o.getTargetAlias(), o.getCampo());
//  		}
//  		if (!clear) {
//  			r.clearOrderBy();
//  			clear = true;
//  		}
//			r.addOrderBy(null, o.findFunction(o.getTargetAlias(),o.getCampo()), o.getTipo());
//			long limite = o.getLimite();
//			if (limite!=0) {
//				long top = r.getTop();
//				if (top==-1 || top>limite) {
//					r.setTop(limite);
//				}
//			}
//
//  	}
  	JIterator<BizCampo> iter = customList.getObjOrders().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo o = iter.nextElement();
  		if (!o.hasOrdenAscDesc()) continue;
  		if (!o.getCampo().equals(BizCampo.FUNTION_COUNT) && !o.hasFunction()) {
    		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
    		if (p.hasDependencias()) {
    			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
    			while (t.hasMoreTokens()) {
    				String c = t.nextToken();
    				r.addGroupBy(o.getTargetAlias(), c);
    				r.addOrderBy(o.getTargetAlias(), c,o.getOrdenAscDescForce("DESC"));

    			}
    			continue;
    		}

    		r.addGroupBy(o.getTargetAlias(), o.getCampo());
  		}
  		if (!clear) {
  			r.clearOrderBy();
  			clear = true;
  		}
			r.addOrderBy(null, o.findFunction(o.getTargetAlias(),o.getCampo()), o.getOrdenAscDescForce("ASC"));
//			long limite = o.getMaxSize();
//			if (limite!=0) {
//				long top = r.getTop();
//				if (top==-1 || top>limite) {
//					r.setTop(limite);
//				}
//			}

  	}
  }

  public void prepareJoins(JRecords<?> r,BizCampo f) throws Exception {
		if (f.isGroup()) {
			JIterator<BizCampo> it2 = f.getObjCustomList().getCamposHijos(f).getStaticIterator();
			while (it2.hasMoreElements()) {
				prepareJoins(r, it2.nextElement());
			}
		} else {
			this.attachJoin(r, f);
		}
  }
  
  public void prepareJoins(JRecords<?> r, boolean totalizer) throws Exception {
		r.clearJoins();
		JRelations relations = customList.getObjRelation().getObjRecordTarget().getRelationMap();
		JIterator<JRelation> iter = relations.getList().getIterator();
		while (iter.hasMoreElements()) {
			JRelation p = iter.nextElement();
			if (!p.isRelationFixed())
				continue;
			if (!p.hasJoin())
				continue;
			attachJoinDeep(r, p.getRelKeyField(), p);

		}
		JIterator<BizCampo> it1 = customList.getObjFiltros().getStaticIterator();
		while (it1.hasMoreElements()) {
			BizCampo f = it1.nextElement();
			if (f.isNoIncluir() && totalizer)
				continue;
			if (f.isSoloTotalizador() && !totalizer)
				continue;
			prepareJoins(r, f);
		}

		JIterator<BizCampo> it3 = customList.getObjCampos().getStaticIterator();
		while (it3.hasMoreElements()) {
			BizCampo c = it3.nextElement();
			if (!c.isRolCampo())
				continue;

			this.attachJoin(r, c);
			if (!isWithgeo())
				continue;
			if (!c.hasGeoCampo())
				continue;
			this.attachGeoJoin(r, c);
		}
//		JIterator<BizOrder> it4 = customList.getObjOrders().getStaticIterator();
//		while (it4.hasMoreElements()) {
//			this.attachJoin(r, it4.nextElement());
//		}
		r.addFixedFilter(customList.getObjRelation().getSerializeJoin(customList.getObjRecordSource()));
  }
  public void prepareJoinsDetail(JRecords<?> r, boolean totalizer) throws Exception {
		r.clearJoins();
		JIterator<BizCampo> it1 = customList.getObjFiltros().getStaticIterator();
		while (it1.hasMoreElements()) {
			BizCampo f = it1.nextElement();
			if (f.isNoIncluir() && totalizer) 
				continue;
			if (f.isSoloTotalizador() && !totalizer) 
				continue;
			prepareJoins(r,f);

		}
		JIterator<BizCampo> it3 = customList.getObjCampos().getStaticIterator();
		while (it3.hasMoreElements()) {
			BizCampo f= it3.nextElement();
  		if (!f.isRolCampo()) continue;

			this.attachJoin(r, f);
		}
		
//		JIterator<BizOrder> it4 = customList.getObjOrders().getStaticIterator();
//		while (it4.hasMoreElements()) {
//			this.attachJoin(r, it4.nextElement());
//		}
		r.addFixedFilter(customList.getObjRelation().getSerializeJoin(customList.getObjRecordSource()));
  }

  // intenta determinar si todo el filtro se puede incluir dentro de las condiciones de un join
  public RJoins getJoinForGroup(BizCampo g,JRecords<?> r) throws Exception {
  	JIterator<BizCampo> it = g.getObjCustomList().getCamposHijos(g).getStaticIterator();
  	RJoins joinWin=null;
  	RJoins joinOp=null;
  	RJoins OldjoinOp=null;
  	while (it.hasMoreElements()) {
  		BizCampo f = it.nextElement();
  		joinOp=null;
  		if (f.getRecordOwner().equals(""))
  				joinOp=getJoinForGroup(f, r);
  		else {
  			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
  				joinOp = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
  			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
  				if (r.getStructure().getJoins()!=null) {
  					JIterator<RJoins> it2 = r.getStructure().getJoins().getIterator();
  					while (it2.hasMoreElements()) {
  						RJoins j = it2.nextElement();
  						if (j.getTypeJoin().equals(JRelations.JOIN_LEFT)) continue;
  						if (j.getTypeJoin().equals(JRelations.JOIN_RIGHT)) continue;
  						
  						joinOp=j;
  					 	//break;
  					}
  				}
  			}  		
  		}
  		if (OldjoinOp!=null) {
  			if (OldjoinOp.GetAliasJoin()!=null && !OldjoinOp.GetAliasJoin().equals(joinOp.GetAliasJoin())) {
  				return null;
  			}
  		}
  		OldjoinOp=joinOp;
  		
  		if (joinWin==null) 
  			joinWin=joinOp;
  		else {
  			if (joinOp==null ) 
  				return null;//no hay coincidencia
  			if (!joinWin.GetTablaJoin().equals(joinOp.GetTablaJoin())) {
  				return null;//no hay coincidencia
  			}
  		}
  			
  	}
  	return joinWin;
  
  }  
  
  public void prepareGroupFilters(BizCampo f,JRecords<?> r, boolean totalizer) throws Exception {
  	RJoins j=getJoinForGroup(f,r);
  	if (j!=null) 
			j.addCondicion(f.getFixedFilter(true,totalizer));
		else
			r.addFixedFilter(f.getFixedFilter(false,totalizer));
  }  
  
  public void prepareFiltersDinamico(JRecords<?> r, boolean totalizer) throws Exception {
//  	if (customList.getFiltrosDinamicos()==null) return;
//  	JIterator<RFilter> iter = customList.getFiltrosDinamicos().getIterator();
//  	while (iter.hasMoreElements()) {
//  		RFilter f = iter.nextElement();
//  		if (!f.isDynamic()) continue;
//  		if (f.isVirtual()) {
//    		BizCampo ft = this.customList.findDynamicFilter(f.getField(), f.getOperator());
//    		if (ft==null) continue;
//    		ft.setValor(f.getValue());
//    		if (!ft.hasOperador()) ft.setOperador(f.getOperator());
//    		r.addFixedFilter(ft.getFixedFilter(false,false));
//    		continue;
//  		}
//  		BizCampo ft = this.customList.findDynamicFilter(f.getField(), f.getOperator());
//  		if (ft==null) continue;
//  		if (ft.getObjCustomList()==null) continue;
//  		ft.setValor(f.getValue());
//  		if (ft.getFuncion().endsWith(BizFuncion.FUNTION_HOY) || ft.getFuncion().endsWith(BizFuncion.FUNTION_AYER) ||
//  				ft.getFuncion().endsWith(BizFuncion.FUNTION_PROXIMOS) || ft.getFuncion().endsWith(BizFuncion.FUNTION_ULTIMOS) ||
//  				ft.getFuncion().endsWith(BizFuncion.FUNTION_MANIANA) ||  ft.getFuncion().endsWith("ACTUAL"))
//  			ft.getObjCustomList().setHoy(JDateTools.StringToDate(f.getValue()));
//  		
//  		r.addFixedFilter(ft.getFixedFilter(false,false));
//  	}
  }
  public void prepareFilters(JRecords<?> r, boolean totalizer) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (f.isDependiente())
				continue;
//			if (f.isDinamico()) continue;
			if (f.isNoIncluir() && totalizer) 
				continue;
			if (f.isSoloTotalizador() && !totalizer) 
				continue;
			if (f.getRecordOwner().equals("")) {
				prepareGroupFilters(f,r,totalizer);
				continue;
			}

			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
				RJoins j = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
				if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
					r.addFixedHaving(f.getFixedFilter(false,totalizer));
				} else {
					j.addCondicion(f.getFixedFilter(true,totalizer));
				}
			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
				boolean find =false;
				if (r.getStructure().getJoins()!=null) {
					JIterator<RJoins> it = r.getStructure().getJoins().getIterator();
					while (it.hasMoreElements()) {
						RJoins j = it.nextElement();
						if (j.getTypeJoin().equals(JRelations.JOIN_LEFT)) continue;
						if (j.getTypeJoin().equals(JRelations.JOIN_RIGHT)) continue;
						find=true;
						if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
							r.addFixedHaving(f.getFixedFilter(false,totalizer));
						} else {
							j.addCondicion(f.getFixedFilter(true,totalizer));
						}
						break;
					}
				}
				if (!find) {
					if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
						r.addFixedHaving(f.getFixedFilter(false,totalizer));
					} else {
						r.addFixedFilter(f.getFixedFilter(false,totalizer));
					}
				}
			} else 
				if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
					r.addFixedHaving(f.getFixedFilter(false,totalizer));
				} else {
					r.addFixedFilter(f.getFixedFilter(false,totalizer));
				}
		}
//		JIterator<BizCampo> it3 = customList.getObjCampos().getStaticIterator();
//		while (it3.hasMoreElements()) {
//			BizCampo c=it3.nextElement();
//  		if (!isCampo(c)) continue;
//
//			this.attachFilter(r, c.getObjRelation());
//  		if (!isWithgeo()) continue;
//  		if (!c.hasGeoCampo()) continue;
//			this.attachFilter(r, c.getObjGeoRelation());
//		}
//	

  }
  public void prepareFiltersDetail(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iterC = customList.getObjCampos().getStaticIterator();
		while (iterC.hasMoreElements()) {
			BizCampo c = iterC.nextElement();
  		if (!c.isRolCampo()) continue;

			String value = customList.extract(ejes, c.getSecuencia());
			if (value==null||value.equals("")||value.equals(",")) continue;
			if (c.getObjectType().equals(JObject.JSTRING))
				r.addFixedFilter((c.getTargetAlias().equals("")?"":c.getTargetAlias())+"."+c.getCampo()+"='"+value+"'");
			else if (c.getObjectType().equals(JObject.JDATE) || c.getObjectType().equals(JObject.JDATETIME))
				r.addFixedFilter((c.getTargetAlias().equals("")?"":c.getTargetAlias())+"."+c.getCampo()+"='"+value+"'");
			else
				r.addFixedFilter((c.getTargetAlias().equals("")?"":c.getTargetAlias())+"."+c.getCampo()+"="+value);
		}
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (f.isDependiente())
				continue;
			if (f.isNoIncluir() && totalizer) 
				continue;
			if (f.isSoloTotalizador() && !totalizer) 
				continue;
//			if (f.isDinamico()) {
//				String value = customList.extract(filters, f.getOrden());
//				if (value==null||value.equals("")||value.equals(",")) continue;
//				f.setValor(value);
//			}
			if (f.getRecordOwner().equals("")) {
				r.addFixedFilter(f.getFixedFilter(false,false));
				continue;
			}
			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
				RJoins j = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
				j.addCondicion(f.getFixedFilter(true,false));
			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
				boolean find =false;
				if (r.getStructure().getJoins()!=null) {
					JIterator<RJoins> it = r.getStructure().getJoins().getIterator();
					while (it.hasMoreElements()) {
						RJoins j = it.nextElement();
						if (j.getTypeJoin().equals(JRelations.JOIN_LEFT)) continue;
						if (j.getTypeJoin().equals(JRelations.JOIN_RIGHT)) continue;
						find=true;
						j.addCondicion(f.getFixedFilter(true,false));
						break;
					}
				}
				if (!find) 
					r.addFixedFilter(f.getFixedFilter(false,false));
			} else 
				r.addFixedFilter(f.getFixedFilter(false,false));
		}
	}
  public void prepareFiltersDetail(JWin owner,JRecords<?> r) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
//			if (f.isDinamico()) {
////				if(a.getFilterMap()==null) continue;
//				String value = ((GuiDynamic)owner).getFilterDynamicMap().getFilterValue("filtro"+f.getOrden());
//				if (value==null||value.equals("")||value.equals(",")) continue;
//				f.setValor(value);
//			}
			if (f.getRecordOwner().equals("")) {
				r.addFixedFilter(f.getFixedFilter(false,false));
				continue;
			}
			if (r.hasJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias())) {
				RJoins j = r.getStructure().getJoin(f.getObjRecordOwner().getTableByFilter(f.getCampo()),f.getTargetAlias());
				j.addCondicion(f.getFixedFilter(true,false));
			} else if (r.getStructure().getTableForFrom().equals(f.getObjRecordOwner().getTableByFilter(f.getCampo()))) {
				boolean find =false;
				if (r.getStructure().getJoins()!=null) {
					JIterator<RJoins> it = r.getStructure().getJoins().getIterator();
					while (it.hasMoreElements()) {
						RJoins j = it.nextElement();
						if (j.getTypeJoin().equals(JRelations.JOIN_LEFT)) continue;
						if (j.getTypeJoin().equals(JRelations.JOIN_RIGHT)) continue;
						find=true;
						j.addCondicion(f.getFixedFilter(true,false));
						break;
					}
				}
				if (!find) 
					r.addFixedFilter(f.getFixedFilter(false,false));
			} else 
				r.addFixedFilter(f.getFixedFilter(false,false));

		}
		
  }  
  
  protected void attachKeysToFields(JBaseRecord r) throws Exception {

  }
	public boolean isAgrupado() { return true;}

		

}
