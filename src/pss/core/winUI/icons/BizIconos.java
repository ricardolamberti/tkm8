package pss.core.winUI.icons;

import pss.core.services.records.JRecords;

public class BizIconos extends JRecords<BizIcon> {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIconos() throws Exception {
	}

	@Override
	public Class<BizIcon> getBasedClass() {
		return BizIcon.class;
	}

	@Override
	public String GetTable() {
		return "graficos";
	}
}
