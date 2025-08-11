package pss.core.tools.collections;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

class JHashMap<K,E> implements JMap<K,E>, Serializable {

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
	static final long serialVersionUID = 7178408074053706499L;
  private HashMap<K,E> oMap;

//------------------------------------------------------------------------------
// Constructors
//------------------------------------------------------------------------------
  JHashMap() {
    oMap = this.createHashMap();
  }
  JHashMap(int initialCapacity) {
    oMap = this.createHashMap(initialCapacity);
  }
  JHashMap(int initialCapacity, float loadFactor) {
    oMap = this.createHashMap(initialCapacity, loadFactor);
  }
  JHashMap(Map<K,E> map) {
    oMap = this.createHashMap(map);
  }


//------------------------------------------------------------------------------
// overriddable methods
//------------------------------------------------------------------------------

  protected HashMap<K,E> createHashMap() {
    return new HashMap<K,E>();
  }

  protected HashMap<K,E> createHashMap(int initialCapacity) {
    return new HashMap<K,E>(initialCapacity);
  }

  protected HashMap<K,E> createHashMap(int initialCapacity, float loadFactor) {
    return new HashMap<K,E>(initialCapacity, loadFactor);
  }

  protected HashMap<K,E> createHashMap(Map<K,E> map) {
    return new HashMap<K,E>(map);
  }


//------------------------------------------------------------------------------
// JMap implementation methods
//------------------------------------------------------------------------------
  public boolean isEmpty() {
    return oMap.isEmpty();
  }

  public E addElement(K key, E value) {
    return oMap.put(key, value);
  }

  public void addElements(JMap<K,E> map) {
    JMapToMap(map);
  }

  public void removeAllElements() {
    oMap.clear();
  }

  public E removeElement(K key) {
    return oMap.remove(key);
  }

  public int size() {
    return oMap.size();
  }

  public E getElement(K key) {
    return oMap.get(key);
  }

  @SuppressWarnings("unchecked")
	public K[] getKeys() {
    return (K[])oMap.keySet().toArray();
  }
  @SuppressWarnings("unchecked")
	public E[] getElements() {
    return (E[])oMap.values().toArray();
  }

  public boolean containsKey(K key) {
    return oMap.containsKey(key);
  }

  public boolean containsValue(E value) {
    return oMap.containsValue(value);
  }

  /**
   * @deprecated Usen el iterador nativo (keyIterator())
   */
  @Deprecated
	public JIterator<K> getKeyIterator() {
    return new JArrayIterator<K>(oMap.keySet().iterator());
  }

  /**
   * @deprecated Usen el iterador nativo (valueIterator())
   */
  @Deprecated
	public JIterator<E> getValueIterator() {
    return new JArrayIterator<E>(oMap.values().iterator());
  }

  public final Iterator<K> keyIterator() {
    return oMap.keySet().iterator();
  }

  public final Iterator<E> valueIterator() {
    return oMap.values().iterator();
  }


  private void JMapToMap(JMap<K,E> map) {
    Iterator<K> oIter = map.keyIterator();
    K temp;
    while(oIter.hasNext()) {
      temp = oIter.next();
      oMap.put(temp, map.getElement(temp));
    }
  }
	public void setMap(HashMap m)  {
		oMap=m;
	}
	public void MapToJMap(Map<K,E> m)  {
		for(K k:m.keySet()) {
			E v = m.get(k);
			addElement(k, v);
		}
	}


	public void SortByValue (Comparator<K> vc) {
		TreeMap<K,E> sortedMap = new TreeMap<K,E>(vc);
		sortedMap.putAll(oMap);
		
		try {
			JHashMap<K, E> newMap = (JLinkedHashMap<K, E>)this.getClass().newInstance();
			Iterator<K> it =sortedMap.keySet().iterator();
			while (it.hasNext()) {
				K key=it.next();
				newMap.addElement(key, sortedMap.get(key));
			}
			oMap = newMap.oMap;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
