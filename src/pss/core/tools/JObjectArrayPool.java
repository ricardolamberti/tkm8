package pss.core.tools;

public class JObjectArrayPool {

  // declared final so it can only be
  // modified once and by the class constructor
  private final Object[][] matrix;

//------------------------------------------------------------------------------
// Class Constructors
//------------------------------------------------------------------------------

  // capacity of the vertical vector.
  public JObjectArrayPool(int maxCapacity) {
    matrix = new Object[maxCapacity+1][];
  }


  // capacity of each horizontal vector
  public final Object[] getObjectArray(int capacity) {

    // if a vector of the specified capacity
    // already exists the method returns it for reuse.
    if( matrix[capacity] != null ) {
      return matrix[capacity];
    }

    // if it doesn't exist it is created,
    // saved for reuse, and returned.
    matrix[capacity] = new Object[capacity];
    return matrix[capacity];
  }

}
