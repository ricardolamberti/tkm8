/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;


public abstract class JWebViewInternalComposite extends JWebViewComposite {

  public JWebViewComponent getComponent(String zChildName) throws Exception {
    return (JWebViewComponent) this.getChild(zChildName);
  }

  public void add(String zChildName, JWebViewEditComponent zComponent) throws Exception {
    this.addChild(zChildName, zComponent);
  }

  public void add(String zChildName, JWebViewFixedComponent zComponent) throws Exception {
    this.addChild(zChildName, zComponent);
  }

  public void add(String zChildName, JWebAbstractActionView zComponent) throws Exception {
    this.addChild(zChildName, zComponent);
  }

  @Override
	public void add(String zChildName, JWebViewInternalComposite zComponent) throws Exception {
    this.addChild(zChildName, zComponent);
  }

  public Dimension getMinimumSize() throws Exception {
    return null;
  }
  public Dimension getMaximumSize() throws Exception {
    return null;
  }
}
