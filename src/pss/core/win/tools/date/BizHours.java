package pss.core.win.tools.date;

/**
 * Description: at GuiHours.java
 * @author Iván Rubín
 */

import pss.core.services.records.JRecords;
import pss.core.tools.JTools;

public class BizHours extends JRecords<BizHour> {

	private boolean bDatosLeidos=false;

	public BizHours() {
	}

	@Override
	public Class<BizHour> getBasedClass() {
		return BizHour.class;
	}

	@Override
	public synchronized boolean readAll() throws Exception {
		int i;

		if (bDatosLeidos) return true;

		this.setStatic(true);

		for(i=0; i<24; i++) {
			this.addItem(new BizHour(i, JTools.justifyStrings("", String.valueOf(i), 2, '0')));
		}

		bDatosLeidos=true;
		return true;
	}
}
