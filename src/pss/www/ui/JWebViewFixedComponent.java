/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;


/**
 * 
 * 
 * Created on 12-jun-2003
 * @author PSS
 */

public abstract class JWebViewFixedComponent extends JWebViewComponent {

  @Override
	public Dimension getDefaultSize() {
    return new Dimension(80, 20); 
  }

}
