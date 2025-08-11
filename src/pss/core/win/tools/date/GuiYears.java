package pss.core.win.tools.date;

/**
 * Description: Helper for year-selection combos.
 *              Calculates min(zField) max(zField) and creats a wins with the
 * range of values in between (inclusive.)
 *
 * zTable       DB Table to query
 * zFields      Table.Field to query
 * zObjectType  Datatype of the field. It supports JInteger.class and JString.class
 *
 * @author Iván Rubín
 */

import pss.core.win.JWins;

public class GuiYears extends JWins {

  public GuiYears( String zTabla, String zCampo, Class zObjectType ) throws Exception {
    SetClassWin       ( GuiYear.class );
    this.setRecords     ( new BizYears( zTabla, zCampo, zObjectType ) );
  }
}
