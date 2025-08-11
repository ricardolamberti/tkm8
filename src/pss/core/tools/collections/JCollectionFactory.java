package pss.core.tools.collections;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;


public class JCollectionFactory {

	public static <K, E> JMap<K, E> createMap() {
		return new JHashMap<K, E>();
	}
 
	public static <K, E> JMap<K, E> createMap(int initialCapacity) {
		return new JHashMap<K, E>(initialCapacity);
	}

	public static <K, E> JMap<K, E> createMap(int initialCapacity, float loadFactor) {
		return new JHashMap<K, E>(initialCapacity, loadFactor);
	}

	public static <K, E> JMap<K, E> createMap(Map<K, E> map) {
		return new JHashMap<K, E>(map);
	}

	public static <K, E> JOrderedMap<K, E> createOrderedMap() {
		return new JLinkedHashMap<K, E>();
	}

	public static <K, E> JOrderedMap<K, E> createOrderedMap(int initialCapacity) {
		return new JLinkedHashMap<K, E>(initialCapacity);
	}

	public static <K, E> JOrderedMap<K, E> createOrderedMap(int initialCapacity, float loadFactor) {
		return new JLinkedHashMap<K, E>(initialCapacity, loadFactor);
	}

	public static <K, E> JOrderedMap<K, E> createOrderedMap(Map<K, E> map) {
		return new JLinkedHashMap<K, E>(map);
	}

	public static <E> JList<E> createList() {
		return new JArray2<E>();
	}

	public static <E> JList<E> createList(int initialCapacity) {
		return new JArray2<E>(initialCapacity);
	}

	public static <E> JList<E> createList(Collection<E> c) {
		return new JArray2<E>(c);
	}

	public static <E> JList<E> createList(JList<E> source) {
		return new JArray2<E>(source);
	}

	public static JStringTokenizer createStringTokenizer(String string, char delimiter) {
		return new JStringTokenizerImpl(string, delimiter);
	}

	public static JStringTokenizer createStringTokenizer(String string) {
		return new JStringTokenizerImpl(string);
	}

	public static <E> JIterator<E> createIterator(Enumeration<E> zEnum) {
		return new JEnumerationBasedIterator<E>(zEnum);
	}

}
