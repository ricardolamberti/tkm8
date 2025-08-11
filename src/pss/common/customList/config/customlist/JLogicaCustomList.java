package  pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.sql.BizSqlEvent;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.win.JWin;

public abstract class JLogicaCustomList {
	
	protected boolean withgeo;
	public boolean isWithgeo() {
		return withgeo;
	}
	public boolean isCategoria(BizCampo c) throws Exception {
		return false;
	}
	public boolean isDataset(BizCampo c) throws Exception {
		return false;
	}
	public boolean isValor(BizCampo c) throws Exception {
		return false;
	}

	public boolean isCampo(BizCampo c) throws Exception {
		if (!c.getVisible()) return false;
		return true;
	}
	public boolean isEje(BizCampo c) throws Exception {
		return false;
	}
	public boolean isColumna(BizCampo c) throws Exception {
		return false;
	}
	public boolean isFila(BizCampo c) throws Exception {
		return false;
	}
	public void setWithgeo(boolean withgeo) {
		this.withgeo = withgeo;
	}
	protected BizCustomList customList;
	public void setCustomList(BizCustomList zCustomList) {
		customList=zCustomList;
	}	
	
	public boolean isAgrupado() { return false;}
	public boolean isDato() { return false;}
	public boolean isMatriz() { return false;}
	public boolean isRelacional() { return false;}
	
	public abstract void prepareLiteralFilters(JRecords<?> r) throws Exception;
  public abstract void prepareFilters(JRecords<?> r, boolean totalizer) throws Exception ;
  public abstract void prepareFiltersDinamico(JRecords<?> r, boolean totalizer) throws Exception;
  public abstract void prepareFiltersDetail(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception ;
  public abstract void prepareFiltersDetail(JWin owner,JRecords<?> r) throws Exception ;
  public abstract String prepareOneFields(JWin owner,int field,JRecords<?> r,String SQLtotalize, String force) throws Exception;
  public abstract String prepareOneFields(BizCampo f,JRecords<?> r,String SQLtotalize,String force) throws Exception;
  public abstract void prepareFields(JBaseRecord r) throws Exception;
  public abstract void prepareJoinsDetail(JRecords<?> r, boolean totalizer) throws Exception;
  public abstract void prepareJoins(JRecords<?> r, boolean totalizer) throws Exception;
  public abstract void prepareOrders(JRecords<?> r) throws Exception;
  public abstract void prepareTables(JRecords<?> r) throws Exception;
  public abstract void extractField(BizSqlEvent event,JWin owner,int field) throws Exception;
  public  void prepareFiltersDetailFila(JRecords<?> r,String ejes,String filters, boolean totalizer) throws Exception {
  	prepareFiltersDetail(r,ejes,filters, totalizer);
  }
  
  public  void prepareFiltersDetailFila(JWin owner,JRecords<?> r) throws Exception {
  	prepareFiltersDetail(owner,r);
  }

//  public void attachJoin(JRecords<?> r, BizEje f) throws Exception {
//		if (!f.getObjRelation().hasJoin()) return;
//		if (r.hasJoin(f.getTargetTableForFrom())) return;
//		r.addJoin(f.getTargetTableForFrom());
//		r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//  }
// 
  
  public void attachJoin(JRecords<?> r, BizCampo c) throws Exception {
		this.attachJoinDeep(r, c, c.getObjRelation());
  }
  public void attachGeoJoin(JRecords<?> r, BizCampo f) throws Exception {
  	if (!isWithgeo()) return;
		this.attachGeoJoinDeep(r, f, f.getObjGeoRelation());
  }  

  public void attachGeoJoinDeep(JRecords<?> r, BizCampo f, JRelation rel) throws Exception {
  	if (rel==null) return;
		if (rel.hasRelParent()) {
			this.attachGeoJoinDeep(r, f, rel.getObjRelParent());
		}
		if (rel.hasJoin()) {
			if (r.hasJoinOrTable(rel.getTargetGeoTableForFrom(f), rel.getAlias())) return;
			String condicion =rel.getSerializeJoin();
			if ( rel.getObjRelParent()!=null && rel.getObjRelParent().getAlias()!=null && !rel.getObjRelParent().getAlias().equals(""))
				condicion = JTools.replace(condicion, rel.getObjRelParent().getObjRecordTarget().GetTable(), rel.getObjRelParent().getAlias());
			if ( rel.getSimpleAlias()!=null && rel.getAlias()!=null)
				condicion = JTools.replace(condicion, rel.getSimpleAlias(), rel.getAlias());
			r.addJoin(rel.getTypeJoin(), rel.getTargetGeoTableForFrom(f), rel.getAlias(), condicion);
		}
  }
//  public void attachJoinDeep(JRecords<?> r, BizCampo c, JRelation rel) throws Exception {
//  	if (rel==null) return;
//		if (rel.hasRelParent()) {
//			this.attachJoinDeep(r, c, rel.getObjRelParent());
//		}
//		if (r.hasJoinOrTable(rel.getTargetTableForFrom(c), rel.getAlias(),false)) return;
//		r.addJoin(rel.getTypeJoin(), rel.getTargetTableForFrom(c), rel.getAlias(), rel.getSerializeJoin());
//  }

  public void attachJoinDeep(JRecords<?> r, BizCampo f, JRelation rel) throws Exception {
//  	if (rel==null) return;
//		if (rel.hasRelParent()) {
//			this.attachJoinDeep(r, f, rel.getObjRelParent());
//		}
//		if (rel.hasJoin()) {
//			String table = rel.getTargetTableForFrom(f.getCampo());
//			table = (table==null)?rel.getTargetTableForFrom("EXTERN"):table;
//			if (r.hasJoin(table, rel.getAlias())) return;
//			String condicion = rel.getSerializeJoin();
//			if ( rel.getObjRelParent()!=null && rel.getObjRelParent().getAlias()!=null && !rel.getObjRelParent().getAlias().equals(""))
//				condicion = JTools.replace(condicion, rel.getObjRelParent().getObjRecordTarget().GetTable(), rel.getObjRelParent().getAlias());
//			if ( rel.getSimpleAlias()!=null && rel.getAlias()!=null)
//				condicion = JTools.replace(condicion, rel.getSimpleAlias(), rel.getAlias());
//			r.addJoin(rel.getTypeJoin(), table, rel.getAlias(), condicion);
//		}
  	attachJoinDeep(r, f.getCampo(), rel);
	
  } 
  public void attachJoinDeep(JRecords<?> r, String campo, JRelation rel) throws Exception {
  	attachJoinDeep(r,campo,rel,null);
  }
  public void attachJoinDeep(JRecords<?> r, String campo, JRelation rel, String extraCond) throws Exception {
  	if (rel==null) return;
		if (rel.hasRelParent()) {
			this.attachJoinDeep(r, campo, rel.getObjRelParent(),extraCond);
		}
		if (rel.hasJoin()) {
			String table = rel.getTargetTableForFrom(campo);
			table = (table==null)?rel.getTargetTableForFrom("EXTERN"):table;
			if (r.hasJoin(table, rel.getAlias())) return;
			String condicion = rel.getSerializeJoin() ;
			String s =rel.getSerializeFilter();
			condicion+= (rel.getSerializeFilter()==null)?"": " and "+rel.getSerializeFilter();
			condicion+=  (extraCond==null?"":" and ("+extraCond+") ");
			if ( rel.getObjRelParent()!=null && rel.getObjRelParent().getAlias()!=null && !rel.getObjRelParent().getAlias().equals(""))
				condicion = JTools.replace(condicion, rel.getObjRelParent().getObjRecordTarget().GetTable(), rel.getObjRelParent().getAlias());
			if ( rel.getSimpleAlias()!=null && rel.getAlias()!=null)
				condicion = JTools.replace(condicion, rel.getSimpleAlias(), rel.getAlias());
			r.addJoin(rel.getTypeJoin(), table, rel.getAlias(), condicion);
		}
	
  }
 
  public void attachFilter(JRecords<?> r, JRelation rel) throws Exception {
  	if (rel==null) return;
		if (rel.hasRelParent()) {
			this.attachFilter(r, rel.getObjRelParent());
		}
		if (!rel.hasFilters()) return;
		r.addFixedFilter(rel.getSerializeFilter());
  }

//  public void attachJoin(JRecords<?> r, BizOrder f) throws Exception {
//		this.attachJoinDeep(r, f, f.getObjRelation());
//  }
//  public void attachJoinDeep(JRecords<?> r, BizOrder f, JRelation rel) throws Exception {
//  	if (rel==null) return;
//		if (rel.hasRelParent()) {
//			this.attachJoinDeep(r, f, rel.getObjRelParent());
//		}
//		if (!rel.hasJoin()) return;
//		if (r.hasJoinOrTable(rel.getTargetTableForFrom(f.getCampo()), rel.getAlias())) return;
//		r.addJoin(rel.getTypeJoin(), rel.getTargetTableForFrom(f.getCampo()), rel.getAlias(), rel.getSerializeJoin());
//	}
  
  
  private boolean needRenameParent(JRelation rel) throws Exception {
  	JRelation parent = rel.getObjRelParent();
  	if (parent==null) return false;
  	if (parent.getTypeJoin().equals(JRelations.JOIN_ONE) || parent.getTypeJoin().equals(JRelations.JOIN_TEN)) return false;
  	return parent.getAlias()!=null && !parent.getAlias().equals("");
  }
  private boolean needRenameChild(JRelation rel) throws Exception {
  	return rel.getSimpleAlias()!=null && rel.getAlias()!=null;
  }  
  
//  public void attachJoin(JRecords<?> r, BizFiltro f) throws Exception {
//		if (f.getObjRelation()==null || !f.getObjRelation().hasJoin()) return;
//		if (f.isOperIN()) return;
//		if (r.hasJoin(f.getTargetTable())) return;
//		if (r.GetTable().toLowerCase().equals(f.getTargetTable().toLowerCase())) return;
//		r.addJoin(f.getTargetTableForFrom());
//		r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//	}
  
  
//  public void attachJoinDetail(JRecords<?> r, BizFiltro f) throws Exception {
//		if (r.hasJoin(f.getTargetTableForFrom())) return;
//		if (r.GetTable().toLowerCase().equals(f.getTargetTable().toLowerCase())) {
//			r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//			return;
//		}
//		r.addJoin(f.getTargetTableForFrom());
//		r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//	}
//  public void attachJoinDetail(JRecords<?> r, BizCampo f) throws Exception {
//		if (r.hasJoin(f.getTargetTableForFrom())) return;
//		if (r.GetTable().toLowerCase().equals(f.getTargetAlias().toLowerCase())) {
//			r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//			return; 
//		}
//		r.addJoin(f.getTargetTableForFrom());
//		r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//  }
//  public void attachJoinDetail(JRecords<?> r, BizOrder f) throws Exception {
//		if (r.hasJoin(f.getTargetTableForFrom())) return;
//		if (r.GetTable().toLowerCase().equals(f.getTargetTable().toLowerCase())) {
//			r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//			return;
//		}
//		r.addJoin(f.getTargetTableForFrom());
//		r.addFixedFilter(f.getObjRelation().getSerializeJoin());
//  }

}
