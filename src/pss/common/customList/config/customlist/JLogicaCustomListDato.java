package pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.BizCampo;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class JLogicaCustomListDato extends JLogicaCustomListAgrupado {

	public JLogicaCustomListDato() {
	}
	
	@Override
	public boolean isCampo(BizCampo c) throws Exception {
		if (!c.getVisible()) return false;
  	if (c.getTipoCampo().equalsIgnoreCase("JSTRING")) return false;
		if (c.getTipoCampo().equalsIgnoreCase("JBOOLEAN")) return false;
		if (c.getTipoCampo().equalsIgnoreCase("JDATE")) return false;
		return super.isCampo(c);
	}
	
  
  public void prepareFields(JBaseRecord r) throws Exception {
  	r.clearFields();
   	this.attachKeysToFields(r);

   	
   	
  	JIterator<BizCampo> iter = customList.getObjCampos().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizCampo f = iter.nextElement();
  		f.prepareField(r);
  		break; // solo el primero
  	}

  }
  
  

  public void prepareJoins(JRecords<?> r, boolean totalizer) throws Exception {
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
  			BizCampo c=it3.nextElement();
  			this.attachJoin(r, c);
    		break; //solo uno
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
  		break; //solo uno
		}
		
//		JIterator<BizOrder> it4 = customList.getObjOrders().getStaticIterator();
//		while (it4.hasMoreElements()) {
//			this.attachJoin(r, it4.nextElement());
//		}
		r.addFixedFilter(customList.getObjRelation().getSerializeJoin(customList.getObjRecordSource()));
  }

	public boolean isDato() { return true;}

}
