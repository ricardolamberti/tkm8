package pss.common.documentos.tipos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocFisicoTipo extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiDocFisicoTipo() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocFisicoTipo(); }
  public int GetNroIcono()   throws Exception { return 10036; }
  public String GetTitle()   throws Exception { return "Tipo documento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormDocFisicoTipo.class; }
  public String  getKeyField() throws Exception { return "id_tipo_doc"; }
  public String  getDescripField() { return "descripcion"; }
  public BizDocFisicoTipo GetcDato() throws Exception { return (BizDocFisicoTipo) this.getRecord(); }

 }
