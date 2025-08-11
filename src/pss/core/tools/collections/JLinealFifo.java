package pss.core.tools.collections;

/**
 * Description: rectalfifo?
 * It could be, uh, circular?
 *
 * @author Iván Rubín, Gaston Berrio
 */

public class JLinealFifo<E> implements JFifo<E> {
  JList<E> oList;

  public JLinealFifo() {
    oList = JCollectionFactory.createList();
  }

  public boolean isEmpty() {
    return oList.isEmpty();
  }

  public void insert( E zObject ) {
    oList.addElementAt( 0, zObject );
  }

  public E retrieve() {
    if( oList.isEmpty() ) {
      throw new JUnderflowException();
    }

    E oAnswer = oList.getElementAt( oList.size()-1 );
    oList.removeElement( oAnswer );
    return oAnswer;
  }

}
