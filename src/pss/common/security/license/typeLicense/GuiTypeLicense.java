package  pss.common.security.license.typeLicense;

import pss.common.security.license.typeLicense.detail.GuiTypeLicenseDetails;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiTypeLicense extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTypeLicense() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTypeLicense(); }
  public int GetNroIcono()   throws Exception { return 912; }
  public String GetTitle()   throws Exception { return "Tipo de licencia"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTypeLicense.class; }
  public String  getKeyField() throws Exception { return "id_typeLicense"; }
  public String  getDescripField() { return "description"; }
  public BizTypeLicense GetcDato() throws Exception { return (BizTypeLicense) this.getRecord(); }
  @Override
	public void createActionMap() throws Exception {
     addAction(10, "Detalle", null, 43, false, false, true, "Detail" );
     super.createActionMap();
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getDetalle());
  	return super.getSubmitFor(a);
  }
  
  public JWins getDetalle() throws Exception {
    GuiTypeLicenseDetails records = new GuiTypeLicenseDetails();
    records.getRecords().addFilter("company", GetcDato().getCompany());
    records.getRecords().addFilter("id_typeLicense", GetcDato().getIdtypelicense());
    return records;
  }

 }
