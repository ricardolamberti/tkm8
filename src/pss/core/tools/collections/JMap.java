package pss.core.tools.collections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * An object that maps keys to values. A JMap cannot contain duplicate keys (*); each key can map to at most one value.
 * <p>
 * 
 * All general-purpose map implementation classes should provide two "standard" constructors: a void (no arguments) constructor which creates an empty JMap, and a constructor with a single argument of type <tt>JMap</tt>, which creates a new JMap with the same key-value mappings as its argument. In effect, the latter constructor allows the user to copy any JMap, producing an equivalent JMap of the desired class.
 * 
 * @see Map interface for method documentation
 * 
 * (*) uh uh. Isn't it put then, instead of add?
 */

public interface JMap<K, E> extends Serializable {

	int size();

	boolean isEmpty();

	boolean containsKey(K key);

	boolean containsValue(E value);

	E getElement(K key);

	E addElement(K key, E value);

	E removeElement(K key);

	void addElements(JMap<K, E> jMap);

	void removeAllElements();

	JIterator<K> getKeyIterator();

	JIterator<E> getValueIterator();

	// usar iterador nativo para evitar overhead del wrapper JIterador
	Iterator<K> keyIterator();

	Iterator<E> valueIterator();

	K[] getKeys();

	E[] getElements();
	
	void setMap(HashMap m);
	
	void MapToJMap(Map<K,E> m);

	
	
}
