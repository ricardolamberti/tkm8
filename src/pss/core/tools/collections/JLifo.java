package pss.core.tools.collections;

/**
 * Description:
 *
 * @author Iván Rubín
 */

public interface JLifo<E> {

  public void    push( E zObject );
  public E  pop();
  public boolean isEmpty();

  /* answers a reference to the object at the top of the stack */
  public E  get();

}
