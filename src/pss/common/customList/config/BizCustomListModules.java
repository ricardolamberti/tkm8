package  pss.common.customList.config;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizCustomListModules extends JRecord {
	
	public static final String SET_SQL_CLASS = "pss.common.customList.advanced.BizCustomListSQL";
	public static final String SET_RETAIL_CLASS = "pss.erp.shop.business.BizCustomListRetail";
	public static final String SET_LEX_CLASS = "pss.lex.lexBusiness.BizCustomListLex";
	public static final String SET_BSP_CLASS = "pss.bsp.bspBusiness.BizCustomListBSP";
	public static final String SET_MDS_CLASS = "pss.mds.BizCustomListMDS";
	public static final String SET_JUDICIAL_CLASS = "pss.judicial.BizCustomListJudicial";
	public static final String SET_HO_CLASS = "pss.ho.company.BizCustomListHO";
	public static final String SET_TURNOS_CLASS = "pss.turnos.turnosBusiness.BizCustomListTurnos";



	public BizCustomListModules() throws Exception {
	}

	public void attachRelationMap(JRelations rels) throws Exception {
		BizUsuario.getUsr().getObjBusiness().attachRelationMap(this,rels);
	}


	public void attachModule(int nro, String clase, JRelations rels) throws Exception {
		if (!JTools.isInstalled(clase)) return;
		JRecord m = (JRecord)Class.forName(clase).newInstance();
		JIterator<JRelation> it = m.getRelationMap().getList().getIterator();
		while (it.hasMoreElements()) {
			JRelation r = it.nextElement();
			r.setOldId("m"+nro+"-"+r.getId());
			rels.addRelation(r);
		}
	}
//	private static JList<String> getCustomListSets() throws Exception {
//		JList<String> list=JCollectionFactory.createList();
//		if (JTools.isInstalled(SET_SQL_CLASS)) list.addElement(SET_SQL_CLASS);
//		if (JTools.isInstalled(SET_RETAIL_CLASS)) list.addElement(SET_RETAIL_CLASS);
//		if (JTools.isInstalled(SET_LEX_CLASS)) list.addElement(SET_LEX_CLASS);
//		return list;
//	}

}
