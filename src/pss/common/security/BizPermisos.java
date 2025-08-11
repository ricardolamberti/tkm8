package pss.common.security;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.records.JRecords;

public class BizPermisos extends JRecords<BizPermiso> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizPermisos() throws Exception {
	}

	@Override
	public Class<BizPermiso> getBasedClass() {
		return BizPermiso.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public boolean readAll() throws Exception {
		BizPermiso oEstado=new BizPermiso();
		oEstado.pCod_permiso.setValue("T");
		oEstado.pTipo_permiso.setValue(JLanguage.translate("Logear Todo"));
		this.addItem(oEstado);
		oEstado=new BizPermiso();
		oEstado.pCod_permiso.setValue("N");
		oEstado.pTipo_permiso.setValue(JLanguage.translate("Logear Nada"));
		this.addItem(oEstado);
		setStatic(true);
		return true;
	}

}
