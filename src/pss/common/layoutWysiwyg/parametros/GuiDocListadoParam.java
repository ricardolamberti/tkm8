package  pss.common.layoutWysiwyg.parametros;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocListadoParam extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDocListadoParam() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocListadoParam(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Parametro"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDocListadoParam.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDocListadoParam GetcDato() throws Exception { return (BizDocListadoParam) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
		this.createActionUpdate();
  }
 }
