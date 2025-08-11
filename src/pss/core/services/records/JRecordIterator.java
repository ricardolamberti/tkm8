package pss.core.services.records;

/**
 * An iterator that contains a JBDs whose record it can explore.
 * @author Leonardo Pronzolino
 * @version 1.0
 */

import java.util.Iterator;

public class JRecordIterator implements Iterator {
  /**
   * The JBDs this iterator is based on.
   */
  private JRecords jbds = null;

  //
  // METHODS
  //
  //
  public JRecordIterator(JRecords zJBD) {
    this.jbds = zJBD;
    try {
      if (this.jbds.isStatic())
        this.jbds.firstRecord();
      else
        this.jbds.readAll();
    } catch ( Exception e ) {}
  }
  //
  // Iterator interface methods
  //
  public boolean hasNext() {
  try {
    return jbds.nextRecord();
  } catch ( Exception e ) {
    return false;
  }}

  public Object next() {
  try {
    return jbds.getRecord();
  } catch ( Exception e ) {
    return null;
  }}

  public void remove() {
    throw new java.lang.UnsupportedOperationException("Method remove() not yet implemented.");
  }
}

