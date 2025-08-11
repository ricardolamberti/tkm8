package pss.core.tools.collections;

/**
 * Description:
 *
 * @author Iván Rubín
 */

public class JDefaultLifo<E> implements JLifo<E> {
  JList<E> oList;

  public JDefaultLifo() {
    oList = JCollectionFactory.createList();
  }

  public boolean isEmpty() {
    return oList.isEmpty();
  }

  public void push( E zObject ) {
    oList.addElement( zObject );
  }

  public E pop() {
    E oAnswer = get();

    oList.removeElement( oAnswer );

    return oAnswer;
  }

  public E get() {
    if( oList.isEmpty() ) {
      throw new JUnderflowException();
    }

    return oList.getElementAt( oList.size() - 1 );
  }


}
