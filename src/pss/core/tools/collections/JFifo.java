package pss.core.tools.collections;

/**
 * Description:
 *
 * @author Iv�n Rub�n
 */

public interface JFifo<E> {

  public void    insert( E zObject );
  public E  retrieve();
  public boolean isEmpty();

}
