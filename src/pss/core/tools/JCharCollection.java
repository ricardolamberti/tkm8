package pss.core.tools;

import java.lang.reflect.Constructor;

public class JCharCollection {

  private final String string;
  private final char[] charArray;
  private final Object[] object = new Object[3];
  private final static Integer OFFSET = new Integer(0);
  private static Constructor STRING_CONSTRUCTOR;

  static {
    try { STRING_CONSTRUCTOR = String.class.getDeclaredConstructor(new Class[] { int.class, int.class, char[].class }); }
    catch(Exception doNothing) { }
    STRING_CONSTRUCTOR.setAccessible(true);
  }

 // size is the size of the char[].
  public JCharCollection(int size) throws Exception {
    object[0] = OFFSET;
    object[1] = new Integer(size);
    object[2] = charArray = new char[size];
    string = (String)STRING_CONSTRUCTOR.newInstance(object);
  }

 // get the String instance.
  public String getString() {
    return string;
  }

 // get the char[] instance.
  public char[] getCharArray() {
    return charArray;
  }

 // get a new String instance that points to the same char[] instance.
 // be aware that len cannot be bigger than the original String length.
  public String substring(int len) {
    return string.substring(0, len);
  }

 // get a new String instance that points to the same char[] instance.
 // be aware that endIndex cannot be bigger than the original String length.
  public String substring(int startIndex, int endIndex) {
    return string.substring(startIndex, endIndex);
  }

}
