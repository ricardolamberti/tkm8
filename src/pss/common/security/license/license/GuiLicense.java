package  pss.common.security.license.license;

import pss.common.security.license.license.detail.GuiLicenseDetails;
import pss.common.security.license.typeLicense.detail.GuiTypeLicenseDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLicense extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiLicense() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizLicense();
	}

	public int GetNroIcono() throws Exception {
		return 5051;
	}

	public String GetTitle() throws Exception {
		return "Licencia";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormLicense.class;
	}

	public Class<? extends JBaseForm> getFormNew() throws Exception {
		return FormNewLicense.class;
	}

	public String getKeyField() throws Exception {
		return "id_license";
	}

	public String getDescripField() {
		return "id_typeLicense";
	}

	public BizLicense GetcDato() throws Exception {
		return (BizLicense) this.getRecord();
	}

	@Override
	public void createActionMap() throws Exception {
		addAction(10, "Usados", null, 43, false, false, true, "Detail");
		addAction(20, "Maximos", null, 43, false, false, true, "Detail");
		createActionQuery();
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getDetalle());
		if (a.getId()==20) return new JActWins(this.getDetalleType());
		return super.getSubmitFor(a);
	}

	public JWins getDetalle() throws Exception {
		GuiLicenseDetails records=new GuiLicenseDetails();
		records.getRecords().addFilter("id_license", GetcDato().getIdlicense());
		records.SetVision("PROTECT");
		return records;
	}

	public JWins getDetalleType() throws Exception {
		GuiTypeLicenseDetails records=new GuiTypeLicenseDetails();
		records.getRecords().addFilter("company", GetcDato().getCompany());
		records.getRecords().addFilter("id_typeLicense", GetcDato().getIdtypelicense());
		records.SetVision("PROTECT");
		return records;
	}
}
