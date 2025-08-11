package  pss.common.agenda.evento.tipo;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTipoEvento extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTipoEvento() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTipoEvento(); }
  public int GetNroIcono()   throws Exception { return 10051; }
  public String GetTitle()   throws Exception { return "Tipo evento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTipoEvento.class; }
  public String  getKeyField() throws Exception { return "id_tipoevento"; }
  public String  getDescripField() { return "descripcion"; }
  public BizTipoEvento GetcDato() throws Exception { return (BizTipoEvento) this.getRecord(); }

 }
