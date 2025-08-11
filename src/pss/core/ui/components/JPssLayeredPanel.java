package pss.core.ui.components;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.LayoutManager;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;

/**
 * A panel which holds other panels and manage them, letting get to front one
 * at a time.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

public class JPssLayeredPanel extends JPssPanel {

  private CardLayout childrenDealer;
  private JOrderedMap<String, Component> childrenByName;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JPssLayeredPanel() {
    this.childrenByName = JCollectionFactory.createOrderedMap();
    this.setLayout(new CardLayout(0,0));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
	public void setLayout(LayoutManager zLayout) {
    if (zLayout instanceof CardLayout) {
      this.childrenDealer = (CardLayout)zLayout;
      super.setLayout(zLayout);
      this.dealChildren();
    }
  }
  private void dealChildren() {
    super.removeAll();
    JIterator<String> childrenNamesIt = this.childrenByName.getKeyIterator();
    while (childrenNamesIt.hasMoreElements()) {
      String childName = childrenNamesIt.nextElement();
      Component child = this.childrenByName.getElement(childName);
      super.add(childName, child);
    }
  }


  public Component getChild(String zChildName) {
    return this.childrenByName.getElement(zChildName);
  }
  public void showChild(String zChildName) {
    this.childrenDealer.show(this, zChildName);
  }
  public void showNextChild() {
    this.childrenDealer.next(this);
  }
  public void showPreviousChild() {
    this.childrenDealer.previous(this);
  }
  public void showFirstChild() {
    this.childrenDealer.first(this);
  }
  public void showLastChild() {
    this.childrenDealer.last(this);
  }

//  private void updateLayoutYaiNau() {
//    this.dealChildren();
//    this.doLayout();
//    this.repaint();
//  }

  //
  //  overridden to allow just to use #add(String, Component)
  //
  @Override
	public Component add(Component comp, int index) {
    throw new RuntimeException("Use #add(String,Component)");
  }
  @Override
	public void add(Component comp, Object constraints, int index) {
    throw new RuntimeException("Use #add(String,Component)");
  }
  @Override
	public void add(Component comp, Object constraints) {
    throw new RuntimeException("Use #add(String,Component)");
  }
  @Override
	public Component add(Component comp) {
    throw new RuntimeException("Use #add(String,Component)");
  }
  @Override
	public Component add(String name, Component comp) {
    comp.setVisible(false);
    super.add(comp, name);
    this.childrenByName.addElement(name, comp);
    return comp;
  }
  @Override
	public void remove(int index) {
    super.remove(index);
    this.childrenByName.removeElementAt(index);
  }
  @Override
	public void removeAll() {
    super.removeAll();
    this.childrenByName.removeAllElements();
  }

}
