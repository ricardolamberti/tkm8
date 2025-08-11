/*
 * Created on 14-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.layout;

import java.awt.Dimension;

import pss.www.ui.JWebViewComponent;


public abstract class JWebLayouts {

  public static Dimension getLayoutSizeOf(JWebViewComponent zComponent) throws Exception {
    Dimension oSize = zComponent.getRealSize();
    if (oSize==null) {
      oSize = zComponent.calculateSize();
    }
    return oSize;
  }

}
