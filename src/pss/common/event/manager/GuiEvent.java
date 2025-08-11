package pss.common.event.manager;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEvent extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEvent() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEvent(); }
  public int GetNroIcono()   throws Exception { return 5074; }
  public String GetTitle()   throws Exception { return "Evento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEvent.class; }
  public String  getKeyField() throws Exception { return "id_event"; }
  public String  getDescripField() { return "id_event"; }
  public BizEvent GetcDato() throws Exception { return (BizEvent) this.getRecord(); }
	public void createActionMap() throws Exception {
	}
	

 }
