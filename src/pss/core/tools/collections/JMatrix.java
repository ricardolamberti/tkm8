package pss.core.tools.collections;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class JMatrix<Y,X,E>  implements Serializable {

//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
	static final long serialVersionUID = 7178408071053706499L;
  private TreeMap<Y,TreeMap<X,E>> oMap;

//------------------------------------------------------------------------------
// Constructors
//------------------------------------------------------------------------------
  public JMatrix() {
    oMap = new TreeMap<Y,TreeMap<X,E>>();
  }


//------------------------------------------------------------------------------
// JMap implementation methods
//------------------------------------------------------------------------------
  public boolean isEmpty() {
    return oMap.isEmpty();
  }

  private TreeMap<X,E> getXMap(Y keyY) {
  	TreeMap<X,E> m= oMap.get(keyY);
  	if (m!=null) return m;
  	m= new TreeMap<X, E>();
  	oMap.put(keyY, m);
  	return m;
  }
  
  public E addElement(Y keyY, X keyX, E value) {
  	
    return getXMap(keyY).put(keyX, value);
  }

  public void removeAllElements() {
    oMap.clear();
  }

  public E removeElement(Y keyY,X keyX) {
    return getXMap(keyY).remove(keyX);
  }

  public int sizeY() {
    return oMap.size();
  }

  public int sizeX(Y keyY) {
    return getXMap(keyY).size();
  }

  public E getElement(Y keyY,X keyX) {
    return getXMap(keyY).get(keyX);
  }

  @SuppressWarnings("unchecked")
	public Y[] getKeysY() {
    return (Y[])oMap.keySet().toArray();
  }
  @SuppressWarnings("unchecked")
	public X[] getKeysX(Y keyY) {
    return (X[])getXMap(keyY).keySet().toArray();
  }
  @SuppressWarnings("unchecked")
	public E[] getElements(Y keyY) {
    return (E[])getXMap(keyY).values().toArray();
  }

  public boolean containsKey(Y keyY,X keyX) {
    return getXMap(keyY).containsKey(keyX);
  }

  public boolean containsValue(E value) {
  	boolean out=false;
  	for (Y keyY:getKeysY()) {
  		out|=getXMap(keyY).containsKey(value);
  		if (out) break;
  	}
    return out;
  }

	public Iterator<Y> keyYIterator() {
    return oMap.keySet().iterator();
  }

	public Iterator<X> keyXIterator(Y keyY) {
    return getXMap(keyY).keySet().iterator();
  }
	public Iterator<E> valueXIterator(Y keyY) {
    return getXMap(keyY).values().iterator();
  }

	

}
