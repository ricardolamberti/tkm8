package pss.common.personalData;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDomicilioFlat extends JBaseForm {

	public GuiDomicilio getWin() throws Exception {
		return (GuiDomicilio) this.getBaseWin();
	}
	public BizDomicilio getDomicilio() throws Exception {
		return this.getWin().GetcDato();
	}
	
	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn(6);
		if (this.getWin().hasDropListener()) {
			col.addButton(807, 4, 1);
		}
		col.addButton(this.getWin().GetNroIcono(), null, false, 1);
		col.addButton(this.getDomicilio().getDomicilioCompleto(), 1, false, 8);
	}
	
}
