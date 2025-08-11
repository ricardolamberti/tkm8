package pss.core.tools.collections;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
/**
 * An ordered collection (also known as a <i>sequence</i>).  The user of this
 * interface has precise control over where in the list each element is
 * inserted.  The user can access elements by their integer index (position in
 * the list), and search for elements in the list.<p>
 *
 * Note: While it is permissible for lists to contain themselves as elements,
 * extreme caution is advised: the <tt>equals</tt> and <tt>hashCode</tt>
 * methods are no longer well defined on a such a list.
 *
 * @author  Berrio & Pronzolino
 * @see List for method documentation
 */


public interface JList<E> extends Serializable {

  int size();

  boolean isEmpty();

  boolean containsElement(E element);

  JIterator<E> getIterator();

  Iterator<E> iterator();

  Object[] toArray();

  boolean addElement(E element);

  void addElementAt(int index, E element);

  boolean addElements(JList<E> elements);

  boolean addElementsAt(int index, JList<E> elements);

  boolean removeElement(E element);

  E removeElementAt(int index);

  boolean removeElements(JList<E> elements);

  void removeAllElements();

  E setElementAt(int index, E element);

  E getElementAt(int index);

  boolean containsAll(JList<E> elements);

  int indexOf(E element);

  int lastIndexOf(E element);

  JList<E> subList(int fromIndex, int toIndex);

  void sort();

  void sort(Comparator<E> c);

}
