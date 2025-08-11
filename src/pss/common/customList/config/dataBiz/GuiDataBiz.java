package  pss.common.customList.config.dataBiz;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.forms.JBaseForm;

public class GuiDataBiz extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDataBiz() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDataBiz(); }
  public int GetNroIcono()   throws Exception { return 10064; }
  public String GetTitle()   throws Exception { return "Datos"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDataBiz.class; }
  public String  getKeyField() throws Exception { return "campo"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDataBiz GetcDato() throws Exception { return (BizDataBiz) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
//		createActionQuery();
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	return super.getSubmitFor(a);
  }

 }
