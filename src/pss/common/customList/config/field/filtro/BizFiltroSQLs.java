package  pss.common.customList.config.field.filtro;

import pss.common.customList.config.customlist.BizCustomList;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;

public class BizFiltroSQLs extends JRecords<BizFiltroSQL> {

//-------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizFiltroSQLs() throws Exception {
	}

	
	@Override
	public Class<BizFiltroSQL> getBasedClass() {
		return BizFiltroSQL.class;
	}

	private BizCustomList customList;
	public void setObjCustomList(BizCustomList zCustomList) throws Exception {
		customList=zCustomList;
	}
	@Override
	public BizFiltroSQL createItem(JBaseRegistro zSet) throws Exception {
		BizFiltroSQL f = super.createItem(zSet);
		f.setObjCustomList(customList);
		return f;
	}
}