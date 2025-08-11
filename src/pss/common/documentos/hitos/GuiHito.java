package pss.common.documentos.hitos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiHito extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiHito() throws Exception {
  }
  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  }

  public JRecord ObtenerDato()   throws Exception { return new BizHito(); }
  public int GetNroIcono()   throws Exception { return 10017; }
  public String GetTitle()   throws Exception { return "Hito"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormHito.class; }
  public String  getKeyField() throws Exception { return "id_movim"; }
  public String  getDescripField() { return "id_tipo_hito"; }
  public BizHito GetcDato() throws Exception { return (BizHito) this.getRecord(); }

 }
