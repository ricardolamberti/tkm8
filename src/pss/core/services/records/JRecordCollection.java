package pss.core.services.records;

/**
 * A collection that contains a JBDs over which it can create an Iterator to
 * explore its records.
 * @author Leonardo Pronzolino
 * @version 1.0
 */

import java.util.Collection;
import java.util.Iterator;

public class JRecordCollection implements Collection {
  /**
   * The JBDs this collection is based on.
   */
  private JRecords jbds = null;

  //
  //
  // METHODS
  //
  //
  public JRecordCollection(JRecords zJBD) {
    this.setJBDs(zJBD);
  }
  public JRecords getJBDs() {
    return this.jbds;
  }
  private void setJBDs(JRecords zJBD) {
    this.jbds = zJBD;
  }
  //
  // Collection interface methods
  //
  public int size() {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method size() not yet implemented.");
  }
  public boolean isEmpty() {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method isEmpty() not yet implemented.");
  }
  public boolean contains(Object o) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
  }
  public Iterator iterator() {
    return new JRecordIterator(this.jbds);
  }
  public Object[] toArray() {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
  }
  @SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
  }
  public boolean add(Object o) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method add() not yet implemented.");
  }
  public boolean remove(Object o) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method remove() not yet implemented.");
  }
  public boolean containsAll(Collection c) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method containsAll() not yet implemented.");
  }
  public boolean addAll(Collection c) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method addAll() not yet implemented.");
  }
  public boolean removeAll(Collection c) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method removeAll() not yet implemented.");
  }
  public boolean retainAll(Collection c) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method retainAll() not yet implemented.");
  }
  public void clear() {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method clear() not yet implemented.");
  }
  @Override
	public boolean equals(Object o) {
    /**@todo: Implement this java.util.Collection method*/
    throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
  }
}
