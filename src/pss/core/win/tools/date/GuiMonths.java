package pss.core.win.tools.date;

/**
 * Description: Helper for month-selection combos.
 *              Key fields range 1 to 12 throughout these objects.
 *              Descriptions are JTranslated month names
 *              Take care with java.util.Calendar instances that handle 0~11 month references.
 *
 *
 * @author Iván Rubín
 */

import pss.core.win.JWins;

public class GuiMonths extends JWins {

  public GuiMonths() throws Exception {
    SetClassWin       ( GuiMonth.class );
    this.setRecords     ( new BizMonths() );
  }
}
