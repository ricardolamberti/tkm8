package pss.common.customList.config.informe;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;

public class BizInformes extends JRecords<BizInforme> {

//-------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizInformes() throws Exception {
	}

	
	@Override
	public Class<BizInforme> getBasedClass() {
		return BizInforme.class;
	}

	private BizCustomList customList;
	public void setObjParentCustomList(BizCustomList zCustomList) throws Exception {
		customList=zCustomList;
	}
	public BizCustomList getObjParentCustomList() {
		return customList;
	}


	@Override
	public BizInforme createItem(JBaseRegistro zSet) throws Exception {
		BizInforme c = super.createItem(zSet);
		c.setObjParentCustomList(customList);
		return c;
	}
}
