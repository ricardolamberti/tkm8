package pss.core.winUI.icons;

import pss.core.services.records.JRecords;

public class BizIconGalerys extends JRecords<BizIconGalery> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIconGalerys() throws Exception {
		this.setStatic(true);
	}

	@Override
	public Class<BizIconGalery> getBasedClass() {
		return BizIconGalery.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

}
