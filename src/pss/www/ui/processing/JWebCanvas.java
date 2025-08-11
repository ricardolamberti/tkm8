/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.processing;

/**
 * A canvas is the generic type for a representable object which may create and
 * return an action.<br>
 * Typical implementations of canvas are the composites of the Abstract Web UI
 * model.
 * 
 * Created on 27-jun-2003
 * @author PSS
 */

public interface JWebCanvas extends JXMLRepresentable {

  public abstract boolean isView();
  public abstract boolean isComposite();

  public void destroy();

}
