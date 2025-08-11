package pss.common.customAutoReports.config;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiReportField extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiReportField() throws Exception {
  }

  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }

  public JRecord ObtenerDato()   throws Exception { return new BizReportField(); }
  public int GetNroIcono()   throws Exception { return 90; }
  public String GetTitle()   throws Exception { return "Campo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormReportField.class; }
  public String  getKeyField() throws Exception { return "campo"; }
  public String  getDescripField() { return "descr_campo"; }
  public BizReportField GetcDato() throws Exception { return (BizReportField) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }
  
 }
