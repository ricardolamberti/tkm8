package pss.core.tools.collections;

/**
 * Description:
 *
 * @author Iván Rubín
 */

public interface JFifo<E> {

  public void    insert( E zObject );
  public E  retrieve();
  public boolean isEmpty();

}
