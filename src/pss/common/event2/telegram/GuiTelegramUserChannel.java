package pss.common.event2.telegram;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTelegramUserChannel extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTelegramUserChannel() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizTelegramUserChannel(); }
  public int GetNroIcono()   throws Exception { return 10047; }
  public String GetTitle()   throws Exception { return "Vinculación con Telegram Pendiente"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTelegramUserChannel.class; }
  public String  getKeyField() throws Exception { return "ID"; }
  public String  getDescripField() { return "channel_description"; }
  public BizTelegramUserChannel GetcDato() throws Exception { return (BizTelegramUserChannel) this.getRecord(); }

 }
