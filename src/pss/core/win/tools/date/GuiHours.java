package pss.core.win.tools.date;

/**
 * Description: Helper for 24-hour selection combos
 *
 * @author Iván Rubín
 */

import pss.core.win.JWins;

public class GuiHours extends JWins {

  public GuiHours() throws Exception {
    SetClassWin       ( GuiHour.class );
    this.setRecords     ( new BizHours() );
  }
}
