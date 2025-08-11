package pss.core.tools.collections;

/**
 * Iterates over a collection.
 * Serves as a wrapper to the Iterator interface.
 *
 * @author  Berrio & Pronzolino
 * @see Iterator
 */

public interface JIterator<E> {

  boolean hasMoreElements();

  E nextElement();

  void remove();

}
