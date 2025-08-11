package pss.core.tools.collections;

import java.util.Iterator;

class JArrayIterator<E> implements JIterator<E> {

  private Iterator<E> oIter;

  JArrayIterator(Iterator<E> i) {
    oIter = i;
  }

  public boolean hasMoreElements() {
    return oIter.hasNext();
  }

  public E nextElement() {
    return oIter.next();
  }

  public void remove() {
    oIter.remove();
  }


}
