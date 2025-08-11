package pss.common.terminals.messages.answer;

import pss.common.terminals.messages.requires.display.JMenuItem;

/**
 * Description:
 *
 * @author Iván Rubín, Ricardo Lamberti
 *
 */


public class AwrMenuItemSelection extends Answer {
	
  public static final JMenuItem ANY_ITEM = null;

  private JMenuItem oMenuItem;

  public JMenuItem getItem() { return this.oMenuItem; }

  @Override
	public boolean isMenuItemSelection() { return true; }
  /**
   *  Constructs a menuitemselection event involving any item
   */
  public AwrMenuItemSelection() {
    this.oMenuItem = null;
  }

  /**
   *  Constructs a menuitemselection event involving a particular item
   */
  public AwrMenuItemSelection( JMenuItem zItem ) {
    this.oMenuItem = zItem;
  }

  @Override
	public boolean matches( Answer zEvent ) {
    /* talking about itemselections */
    if( zEvent.isMenuItemSelection() ) {

      /* Does this match any item */
      if( this.oMenuItem == AwrMenuItemSelection.ANY_ITEM ) {
        return true;
      }

      /* Will match a particular item on its hashcode... don't like it much */
      if( this.oMenuItem.hashCode() == ((AwrMenuItemSelection)zEvent).getItem().hashCode() ) {
        return true;
      }

    }

    return false;
  }
}
