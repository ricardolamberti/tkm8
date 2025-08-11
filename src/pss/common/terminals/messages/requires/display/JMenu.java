package pss.common.terminals.messages.requires.display;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JOperatorDisplayInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;

public class JMenu extends JPage {
  public static final int DEFAULT_ITEMS_COLLECTION_SIZE = 1000;

  private JList<JMenuItem>     oItems;
  @SuppressWarnings("unused")
  private boolean bSelectUniqueOption = false;
  @SuppressWarnings("unused")
  private boolean bFileMode = false;

  public JMenu() {
    oItems = JCollectionFactory.createList( JMenu.DEFAULT_ITEMS_COLLECTION_SIZE );
  }

  public JMenu( int zDefaultItemsCollectionSize ) {
    oItems = JCollectionFactory.createList( zDefaultItemsCollectionSize );
  }

  public void selectUniqueOption( boolean zSelect ) {  bSelectUniqueOption = zSelect;  }
  public void setFileMode( boolean zMode ) { bFileMode= zMode;}

  public void addItem( JMenuItem zItem ) {
    this.oItems.addElement( zItem );
  }

  @Override
	public void addButton(JButton oButton) throws Exception {
    if (!oButton.getDescription().equals("")) {
      if (oHeader==null)
        oHeader = new JHeader("Menu");
      oHeader.addHeader(oButton.getDescription());
    }
    super.addButton(oButton);
  }
  /**
   * JPage abstract methods
   *
   * execute
   *
   */
  @Override
	public Answer execute(JTerminal terminal) throws Exception {
  	JOperatorDisplayInterface operDisplay = (JOperatorDisplayInterface) terminal;
    return operDisplay.displayMenu(this);
  }
}
