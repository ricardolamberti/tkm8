package pss.common.customList.config.customlist;

import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.sql.BizSqlEvent;
import pss.common.regions.company.BizCompany;
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

public class JLogicaCustomListRelacional extends JLogicaCustomList {
	BizCustomList customList;
	public void setCustomList(BizCustomList zCustomList) {
		customList=zCustomList;
	}

  public  void prepareTables(JRecords<?> r) throws Exception {
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
  	}
  }
	
  public void prepareFiltersDinamico(JRecords<?> r, boolean totalizer) throws Exception {
//  	if (customList.getFiltrosDinamicos()==null) return;
//  	JIterator<RFilter> iter = customList.getFiltrosDinamicos().getIterator();
//  	while (iter.hasMoreElements()) {
//  		RFilter f = iter.nextElement();
//  		if (!f.isDynamic()) continue;
//  		if (f.isVirtual()) {
//  			BizCampo ft = this.customList.findDynamicFilter(f.getField(), f.getOperator());
//    		if (ft==null) continue;
//    		if (f.getValue().trim().equals("")) continue;
//    		ft.setValor(f.getValue());
//    		if (!ft.hasOperador()) ft.setOperador(f.getOperator());
//    		r.addFixedFilter(ft.getFixedFilter(false,false));
//    		continue;
//  		}
//  		BizCampo ft = this.customList.findDynamicFilter(f.getField(), f.getOperator());
//  		if (ft==null) continue;
//  		if (ft.getObjCustomList()==null) continue;
//  		ft.setValor(f.getValue());
//  		
//
//  		if (ft.getFuncion().endsWith(BizFuncion.FUNTION_HOY) || ft.getFuncion().endsWith(BizFuncion.FUNTION_AYER) ||
//  				ft.getFuncion().endsWith(BizFuncion.FUNTION_PROXIMOS) || ft.getFuncion().endsWith(BizFuncion.FUNTION_ULTIMOS) ||
//  				ft.getFuncion().endsWith(BizFuncion.FUNTION_MANIANA) ||  ft.getFuncion().endsWith("ACTUAL"))
//  			ft.getObjCustomList().setHoy(JDateTools.StringToDate(f.getValue()));
//   		r.addFixedFilter(ft.getFixedFilter(false,false));
//  	}
  }

  public void prepareFields(JBaseRecord r) throws Exception {
  	r.clearFields();
   	this.attachKeysToFields(r);

  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		if (!isCampo(f)) continue;
  		if(f.getCampo()==null || f.getCampo().equals("")) continue;
  		f.prepareField(r);
  	}

  	if (isWithgeo()) {

	  	iter = customList.getObjCampos().getStaticIterator();
	  	while (iter.hasMoreElements()) {
	  		BizCampo f = iter.nextElement();
	  		if (!f.hasGeoCampo()) continue;
	  		if(f.getCampo()==null || f.getCampo().equals("")) continue;
	  		f.prepareGeoField(r);
	  	}
  	}
  }
  
	public boolean isCampo(BizCampo c) throws Exception {
		if (c.isCampoCantidad()) return false;
		if (c.hasFunction() && c.getFuncion().equals(BizCampo.FUNTION_SUM)) return false;
		if (c.hasFunction() && c.getFuncion().equals(BizCampo.FUNTION_AVR)) return false;
		if (c.hasFunction() && c.getFuncion().equals(BizCampo.FUNTION_MIN)) return false;
		if (c.hasFunction() && c.getFuncion().equals(BizCampo.FUNTION_MAX)) return false;
		if (c.hasFunction() && c.getFuncion().equals(BizCampo.FUNTION_COUNT)) return false;
		return true;
	}
  
  public void extractField(BizSqlEvent event,JWin owner,int field) throws Exception{
	 	int i=0;
		JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (!isCampo(f)) continue;
			if (!f.getVisible()) continue;
			if ((i++)!=field) continue;
    	event.setIdCampo(f.getSecuencia()); 
    	event.setCampo(f.getNameField()); 
    	break;
		}
	}
  public String prepareOneFields(JWin owner,int field,JRecords<?> r,String SQLtotalize,String force) throws Exception {
  	r.clearFields();
   	this.attachKeysToFields(r);
   	int i=1;
  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
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
      prepareOneFields(f,r,SQLtotalize,force);
  	}
  	return null;

  }
  
  public String prepareOneFields(BizCampo f,JRecords<?> r,String SQLtotalize,String force) throws Exception {
		 if (force!=null) {
//      	f.setPorcentaje(false);
     	  f.setOver(true);
      	f.prepareField(r,SQLtotalize,true);
      }
      else 
       	f.prepareField(r);
     return f.getNameField();
  }
  public void prepareOrders(JRecords<?> r) throws Exception {
  	r.clearOrderBy();
  	
//  	JIterator<BizOrder> iter = customList.getObjOrders().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizOrder o = iter.nextElement();
//  		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
//  		if (p.hasDependencias()) {
//  			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
//  			while (t.hasMoreTokens()) {
//  				String c = t.nextToken();
//  				r.addOrderBy(o.getTargetTable(), c, o.getTipo());	
//  			}
//  			continue;
//  		}
//			r.addOrderBy(o.getTargetTable(), o.getCampo(), o.getTipo());	
//			long limite = o.getLimite();
//			if (limite!=0) {
//				long top = r.getTop();
//				if (top>limite) {
//					r.setTop(limite);
//				}
//			}
//  	}
  	JIterator<BizCampo> iter = customList.getObjOrders().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo o = iter.nextElement();
  		if (o.isCampoCantidad()) continue;
  		JProperty p = o.getObjRecordTarget().getFixedProp(o.getCampo());
  		if (p.hasDependencias()) {
  			JStringTokenizer t = JCollectionFactory.createStringTokenizer(p.getDependiente(), ';');
  			while (t.hasMoreTokens()) {
  				String c = t.nextToken();
  				r.addOrderBy(o.getTargetAlias(), c, "DESC");	
  			}
  			continue;
  		}
			r.addOrderBy(o.getTargetAlias(), o.getCampo(), o.getOrdenAscDesc());	
//			long limite = o.getMaxSize();
//			if (limite!=0) {
//				long top = r.getTop();
//				if (top>limite) {
//					r.setTop(limite);
//				}
//			}
  	}
  }

  public void prepareJoins(JRecords<?> r,BizCampo f) throws Exception {
  	prepareJoins(r,f,null);
  }
  public void prepareJoins(JRecords<?> r,BizCampo f,String extraCond) throws Exception {
		if (f.isGroup()) {
			JIterator<BizCampo> it2 = f.getObjCustomList().getCamposHijos(f).getStaticIterator();
			while (it2.hasMoreElements()) {
				prepareJoins(r, it2.nextElement());
			}
		} else {
			
			this.attachJoinDeep(r, f.getCampo(), f.getObjRelation(), extraCond);
		}
  }
  public void prepareJoins(JRecords<?> r, boolean totalizer) throws Exception {
		r.clearJoins();
   	JRelations relations = customList.getObjRelation().getObjRecordTarget().getRelationMap();
  	JIterator<JRelation> iter = relations.getList().getIterator();
  	while (iter.hasMoreElements()) {
  		JRelation p = iter.nextElement();
  		if (!p.isRelationFixed()) continue;
  		if (!p.hasJoin()) continue;
			attachJoinDeep(r,p.getRelKeyField(),p);
  		
  	}
  	JIterator<BizCampo> it1 = customList.getObjFiltros().getStaticIterator();
		while (it1.hasMoreElements()) {
			BizCampo f = it1.nextElement();
			if (f.isNoIncluir() && totalizer) 
				continue;
			if (f.isSoloTotalizador() && !totalizer) 
				continue;
			String extraCond = f.getFixedFilter(false,totalizer);

			prepareJoins(r,f,extraCond);
		}
  	

			JIterator<BizCampo> it3 = customList.getObjCampos().getStaticIterator();
			while (it3.hasMoreElements()) {
				BizCampo e = it3.nextElement();
				this.attachJoin(r, e);
				if (!isWithgeo()) continue;
	  		if (!e.hasGeoCampo()) continue;
				this.attachGeoJoin(r, e);
			
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
			this.attachJoin(r, it3.nextElement());
		}
		
//		JIterator<BizOrder> it4 = customList.getObjOrders().getStaticIterator();
//		while (it4.hasMoreElements()) {
//			this.attachJoin(r, it4.nextElement());
//		}
		r.addFixedFilter(customList.getObjRelation().getSerializeJoin(customList.getObjRecordSource()));
  }
  public void prepareFilters(JRecords<?> r, boolean totalizer) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjFiltros().getStaticIterator();	
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (f.getVisible() && f.getFixedProp().isVirtual()) continue;
		//	if (f.isDinamico()) continue;
			if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
				r.addFixedHaving(f.getFixedFilter(false,totalizer));
			} else {		
				r.addFixedFilter(f.getFixedFilter(false,totalizer));
			}
		}

		r.addFixedFilter(customList.getObjRelation().getSerializeJoin(customList.getObjRecordSource()));
		r.addFixedFilter(customList.getObjRelation().getSerializeFilter());

//			JIterator<BizCampo> it3 = customList.getObjCampos().getStaticIterator();
//			while (it3.hasMoreElements()) {
//				BizCampo c=it3.nextElement();
//				this.attachFilter(r, c.getObjRelation());
//		  	if (!isWithgeo()) continue;
//	  		if (!c.hasGeoCampo()) continue;
//				this.attachFilter(r, c.getObjGeoRelation());
//  	}	
  }
  public void prepareFiltersDetail(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception {
  	JIterator<BizCampo> iterE = customList.getObjCampos().getStaticIterator();
  	while (iterE.hasMoreElements()) {
  		BizCampo f = iterE.nextElement();
  		if (!isCampo(f)) continue;
  		if (!f.getVisible()) continue;
			r.addFilter(f.hasFunction()||f.hasOperador()?null:f.getTargetAlias(), f.getCampoFromFiltro(), customList.extract(ejes,f.getSecuencia()), "=");
  	} /*
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjAllCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (f.isNoIncluir() && totalizer) 
				continue;
			if (f.isSoloTotalizador() && !totalizer) 
				continue;
//			if (f.isDinamico()) {
				String value = customList.extract(filters,f.getOrden());
				if (value==null||value.equals("")||value.equals(",")) continue;
				f.setValor(value);
//			}
			if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
				r.addFixedHaving(f.getFixedFilter(false,false));
			} else {		
				r.addFixedFilter(f.getFixedFilter(false,false));
			}
		}*/
	}

  public void prepareFiltersDetail(JWin owner,JRecords<?> r) throws Exception {
		r.clearDynamicFilters();
		JIterator<BizCampo> iter = customList.getObjAllCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCampo f = iter.nextElement();
			if (!f.isFiltroActivo()) continue;
			if (f.getFuncion().equals(BizField.FUNTION_COUNT)) {
				r.addFixedHaving(f.getFixedFilter(false,false));
			} else {		
				r.addFixedFilter(f.getFixedFilter(false,false));
			}
		}
		
  }  /*
  private void prepareSelectIN(JRecords<?> r, JMap<String, JList<BizFiltro>> map) throws Exception {
  	JIterator<JList<BizFiltro>> iter = map.getValueIterator();
  	while (iter.hasMoreElements()) {
  		JList<BizFiltro> l = iter.nextElement();
  		r.addFixedFilter(this.getFilterIN(l));
  	}
  }
  */
  
  private void attachKeysToFields(JBaseRecord r) throws Exception {
  	// se agrega las claves de la tabla para que poder acceder a las acciones dependientes
  	JIterator<JProperty> iter = customList.getObjRelation().getObjRecordTarget().getFixedProperties().getValueIterator();
  	while (iter.hasMoreElements()) {
  		JProperty p = iter.nextElement();
  		if (!p.isKey()) continue;
  		r.addField(p.GetCampo()).setTabla(customList.getObjRelation().getObjRecordTarget().GetTable());
  	}
  }

	public boolean isRelacional() { return true;}			

}
