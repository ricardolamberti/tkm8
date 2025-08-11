package pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail.GuiInvoiceDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActReport;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiInvoice extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiInvoice() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizInvoice();
	}

	public int GetNroIcono() throws Exception {
		return 10036;
	}

	public String GetTitle() throws Exception {
		return "Invoice";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		if (GetcDato().getHoja()==1)
			return FormInvoiceH1.class;
		if (GetcDato().getHoja()==2)
			return FormInvoiceH2.class;
		if (GetcDato().getHoja()==3)
			return FormInvoiceH3.class;
		if (GetcDato().getHoja()==4)
			return FormInvoiceH4.class;
		if (GetcDato().getHoja()==5)
			return FormInvoiceH5.class;
		if (GetcDato().getHoja()==6)
			return FormInvoiceH6.class;
		return FormInvoiceH7.class;
	}

	public String getKeyField() throws Exception {
		return "id";
	}

	public String getDescripField() {
		return "numero";
	}

	public BizInvoice GetcDato() throws Exception {
		return (BizInvoice) this.getRecord();
	}

	public void createActionMap() throws Exception {
		this.addAction(40, "Invoice", null, 10020, true, true, true, "Group").setNuevaVentana(true);
		this.addAction(45, "Detalle", null, 10020, false, false, false, "Group");
		this.addAction(50, "Detalle", null, 10020, false, false, false, "Group");
		this.addAction(60, "Detalle", null, 10020, false, false, false, "Group");
			super.createActionMap();
	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 40)
			return new JActReport(this.GetcDato().createDataSourceInvoice(), 40);
		if (a.getId() == 45)
			return new JActWins(getDetail());
		if (a.getId() == 50)
			return new JActWins(getDetailH4());
		if (a.getId() == 60)
			return new JActWins(getDetailH5());
			
		return super.getSubmitFor(a);
	}
	public JWins getDetail() throws Exception {
		GuiInvoiceDetails wins = new GuiInvoiceDetails();
		wins.setRecords(GetcDato().getDetalles());
		return wins;
		
	}
	public JWins getDetailH4() throws Exception {
		GuiInvoiceDetails wins = new GuiInvoiceDetails();
		wins.setRecords(GetcDato().getDetalles());
		wins.SetVision("H4");
		return wins;
		
	}
	public JWins getDetailH5() throws Exception {
		GuiInvoiceDetails wins = new GuiInvoiceDetails();
		wins.setRecords(GetcDato().getDetalles());
		wins.SetVision("H5");
		return wins;
		
	}
}
