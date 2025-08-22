package pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiInvoiceDetail extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiInvoiceDetail() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizInvoiceDetail();
	}

	public int GetNroIcono() throws Exception {
		return 10036;
	}

	public String GetTitle() throws Exception {
		return "Invoice Detalle";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormInvoiceDetail.class;
	}

	public String getKeyField() throws Exception {
		return "id";
	}

	public String getDescripField() {
		return "numero";
	}

	public BizInvoiceDetail GetcDato() throws Exception {
		return (BizInvoiceDetail) this.getRecord();
	}

	public void createActionMap() throws Exception {
		this.createActionQuery();
		
	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		return super.OkAction(a);
	}

	

}
