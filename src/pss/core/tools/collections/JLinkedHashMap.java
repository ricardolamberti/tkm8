package pss.core.tools.collections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class JLinkedHashMap<K,E> extends JHashMap<K,E>  implements JOrderedMap<K,E>,Serializable  {

	static final long serialVersionUID = 7178408074053706494L;

//------------------------------------------------------------------------------
// Constructors
//------------------------------------------------------------------------------
  JLinkedHashMap() {
    super();
  }
  JLinkedHashMap(int initialCapacity) {
    super(initialCapacity);
  }
  JLinkedHashMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }
  JLinkedHashMap(Map<K,E>  map) {
    super(map);
  }

//------------------------------------------------------------------------------
// overriddable methods
//------------------------------------------------------------------------------

  @Override
	protected HashMap<K,E>  createHashMap() {
    return new LinkedHashMap<K,E> ();
  }

  @Override
	protected HashMap<K,E>  createHashMap(int initialCapacity) {
    return new LinkedHashMap<K,E> (initialCapacity);
  }

  @Override
	protected HashMap<K,E>  createHashMap(int initialCapacity, float loadFactor) {
    return new LinkedHashMap<K,E> (initialCapacity, loadFactor);
  }

  @Override
	protected HashMap<K,E>  createHashMap(Map<K,E>  map) {
    return new LinkedHashMap<K,E> (map);
  }


//------------------------------------------------------------------------------
// JOrderedMap implementation methods
//------------------------------------------------------------------------------

  public E getElementAt(int keyIndex) {
    return this.getElement(getKeys()[keyIndex]);
  }
  public E removeElementAt(int keyIndex) {
    return this.removeElement(getKeys()[keyIndex]);
  }




}
