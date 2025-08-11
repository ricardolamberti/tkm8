package pss.core.win.tools.date;

/**
 * Description: at GuiMonths.java
 * @author Iván Rubín
 */

import pss.common.regions.multilanguage.JLanguage;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;

public class BizMonths extends JRecords<BizMonth> {

	private boolean bDatosLeidos=false;

	public BizMonths() {
	}

	@Override
	public Class<BizMonth> getBasedClass() {
		return BizMonth.class;
	}

	// 1~12 and not 0~11 as java.util.Calendar use to.
	public BizMonth get(int zMonth) throws Exception {
		if (zMonth<1||zMonth>12) {
			JExcepcion.SendError("Invalid month number "+zMonth);
		}
		return this.getStaticElement((zMonth-1));
	}

	@Override
	public synchronized boolean readAll() throws Exception {
		if (bDatosLeidos) return true;

		this.setStatic(true);

		this.addItem(new BizMonth(1, JLanguage.translate("Enero")));
		this.addItem(new BizMonth(2, JLanguage.translate("Febrero")));
		this.addItem(new BizMonth(3, JLanguage.translate("Marzo")));
		this.addItem(new BizMonth(4, JLanguage.translate("Abril")));
		this.addItem(new BizMonth(5, JLanguage.translate("Mayo")));
		this.addItem(new BizMonth(6, JLanguage.translate("Junio")));
		this.addItem(new BizMonth(7, JLanguage.translate("Julio")));
		this.addItem(new BizMonth(8, JLanguage.translate("Agosto")));
		this.addItem(new BizMonth(9, JLanguage.translate("Septiembre")));
		this.addItem(new BizMonth(10, JLanguage.translate("Octubre")));
		this.addItem(new BizMonth(11, JLanguage.translate("Noviembre")));
		this.addItem(new BizMonth(12, JLanguage.translate("Diciembre")));
		bDatosLeidos=true;

		return true;
	}
}
