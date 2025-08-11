package pss.core.tools.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


class JArray2<E> implements JList<E>, Comparator<E> {

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
  private final List<E> oList;

//------------------------------------------------------------------------------
// Constructors
//------------------------------------------------------------------------------
  JArray2() {
    oList = new ArrayList<E>();
  }
  JArray2(int initialCapacity) {
    oList = new ArrayList<E>(initialCapacity);
  }
  JArray2(Collection<E> c) {
    oList = new ArrayList<E>(c);
  }
  JArray2(JList<E> source) {
    oList = new ArrayList<E>();
    this.addElements(source);
  }
  private JArray2(List<E> l) {
    oList = l;
  }

//------------------------------------------------------------------------------
// Methods
//------------------------------------------------------------------------------
  public int size() {
    return oList.size();
  }

  public boolean isEmpty() {
    return oList.isEmpty();
  }



  public boolean addElement(E element) {
    return oList.add(element);
  }
	/*
  public boolean addElement(E element) {
      return oList.add(element);
  }
  */
	public void addElementAt(int index, E element) {
    oList.add(index, element);
  }

  

  public boolean addElements(JList<E> elements) {
      boolean modified = false;
      Iterator<E> e = elements.iterator();
      while( e.hasNext() ) {
        if( oList.add(e.next()) )  modified = true;
      }
      return modified;
	}
  
	public boolean addElementsAt(int index, JList<E> elements) {
    return oList.addAll(index, JListToNewList(elements));
  }



	public E setElementAt(int index, E element){
      return oList.set(index, element);
 	}
	public E getElementAt(int index) {
    return oList.get(index);
  }

  public boolean removeElement(E element) {
    return oList.remove(element);
  }

  public void removeAllElements() {
    oList.clear();
  }

  public E removeElementAt(int index) {
    return oList.remove(index);
  }

  @SuppressWarnings("deprecation")
	public boolean removeElements(JList<E> elements) {
    boolean modified = false;
    JIterator<E> oIter = this.getIterator();
    while(oIter.hasMoreElements()) {
      if( elements.containsElement(oIter.nextElement()) ) {
        oIter.remove();
        modified = true;
      }
    }
    return modified;
  }

  public int indexOf(E element) {
    return oList.indexOf(element);
  }

  public int lastIndexOf(E element) {
    return oList.lastIndexOf(element);
  }

  public boolean containsElement(E element){
    return oList.contains(element);
  }

  public boolean containsAll(JList elements) {
    JIterator e = elements.getIterator();
    while(e.hasMoreElements()) {
      if( !oList.contains(e.nextElement()) )  return false;
    }
    return true;
  }

  /**
   * @deprecated Usen el iterador nativo (iterator())
   */
  @Deprecated
	public JIterator<E> getIterator() {
    return new JArrayIterator<E>(oList.iterator());
  }

  public final Iterator<E> iterator() {
    return oList.iterator();
  }

  public JList<E> subList(int fromIndex, int toIndex) {
    return new JArray2<E>(oList.subList(fromIndex, toIndex));
  }

  public Object[] toArray() {
    return oList.toArray();
  }

  public void sort() {
    if( oList.size() < 2 )  return;
    Collections.sort(oList, this);
  }

  public void sort(Comparator<E> c) {
    if( oList.size() < 2 )  return;
    Collections.sort(oList, c);
  }

  @SuppressWarnings("unchecked")
	public int compare(E firstElement, E secondElement) {
    if( firstElement.equals(secondElement) )   return 0;
    if( firstElement  instanceof Comparable )  return  ((Comparable)firstElement).compareTo(secondElement);
    if( secondElement instanceof Comparable )  return -((Comparable)secondElement).compareTo(firstElement);
    return -1;
  }

  @SuppressWarnings("unused")
	private void JListToList(JList<E> list) {
    JIterator<E> oIter = list.getIterator();
    while(oIter.hasMoreElements()) {
      oList.add(oIter.nextElement());
    }
  }

  private List<E> JListToNewList(JList<E> list) {
    List<E> tempList = new ArrayList<E>(list.size());
    JIterator<E> oIter = list.getIterator();
    while(oIter.hasMoreElements()) {
      tempList.add(oIter.nextElement());
    }
    return tempList;
  }


}
