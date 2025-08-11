package pss.core.tools.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A set of {@link Collection} related utility methods.
 * 
 * @author Hurtada vilmente por Bugui
 */
public class JCollectionUtils {

	private JCollectionUtils() {
	}

	/**
	 * Returns a {@link Collection} containing the union of the given {@link Collection}s.
	 * <p>
	 * The cardinality of each element in the returned {@link Collection} will be equal to the maximum of the cardinality of that element in the two given {@link Collection}s.
	 * 
	 * @see Collection#addAll
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Object> union(final Collection a, final Collection b) {
		ArrayList<Object> list=new ArrayList<Object>();
		Map<Object, Integer> mapa=getCardinalityMap(a);
		Map<Object, Integer> mapb=getCardinalityMap(b);
		Set<Object> elts=new HashSet<Object>(a);
		elts.addAll(b);
		Iterator<Object> it=elts.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			for(int i=0, m=Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i<m; i++) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * Returns a {@link Collection} containing the intersection of the given {@link Collection}s.
	 * <p>
	 * The cardinality of each element in the returned {@link Collection} will be equal to the minimum of the cardinality of that element in the two given {@link Collection}s.
	 * 
	 * @see Collection#retainAll
	 * @see #containsAny
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Object> intersection(final Collection a, final Collection b) {
		ArrayList<Object> list=new ArrayList<Object>();
		Map<Object, Integer> mapa=getCardinalityMap(a);
		Map<Object, Integer> mapb=getCardinalityMap(b);
		Set<Object> elts=new HashSet<Object>(a);
		elts.addAll(b);
		Iterator<Object> it=elts.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			for(int i=0, m=Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i<m; i++) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * Returns a {@link Collection} containing the exclusive disjunction (symmetric difference) of the given {@link Collection}s.
	 * <p>
	 * The cardinality of each element <i>e</i> in the returned {@link Collection} will be equal to <tt>max(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>)) - min(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>))</tt>.
	 * <p>
	 * This is equivalent to <tt>{@link #subtract subtract}({@link #union union(a,b)},{@link #intersection intersection(a,b)})</tt> or <tt>{@link #union union}({@link #subtract subtract(a,b)},{@link #subtract subtract(b,a)})</tt>.
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Object> disjunction(final Collection a, final Collection b) {
		ArrayList<Object> list=new ArrayList<Object>();
		Map<Object, Integer> mapa=getCardinalityMap(a);
		Map<Object, Integer> mapb=getCardinalityMap(b);
		Set elts=new HashSet(a);
		elts.addAll(b);
		Iterator it=elts.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			for(int i=0, m=((Math.max(getFreq(obj, mapa), getFreq(obj, mapb)))-(Math.min(getFreq(obj, mapa), getFreq(obj, mapb)))); i<m; i++) {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * Returns a {@link Collection} containing <tt><i>a</i> - <i>b</i></tt>. The cardinality of each element <i>e</i> in the returned {@link Collection} will be the cardinality of <i>e</i> in <i>a</i> minus the cardinality of <i>e</i> in <i>b</i>, or zero, whichever is greater.
	 * 
	 * @see Collection#removeAll
	 */
	public static <E> Collection<E> subtract(final Collection<E> a, final Collection<E> b) {
		ArrayList<E> list=new ArrayList<E>(a);
		Iterator<E> it=b.iterator();
		while (it.hasNext()) {
			list.remove(it.next());
		}
		return list;
	}

	/**
	 * Returns <code>true</code> iff some element of <i>a</i> is also an element of <i>b</i> (or, equivalently, if some element of <i>b</i> is also an element of <i>a</i>). In other words, this method returns <code>true</code> iff the {@link #intersection} of <i>a</i> and <i>b</i> is not empty.
	 * 
	 * @since 2.1
	 * @param a
	 *          a non-<code>null</code> Collection
	 * @param b
	 *          a non-<code>null</code> Collection
	 * @return <code>true</code> iff the intersection of <i>a</i> and <i>b</i> is non-empty
	 * @see #intersection
	 */
	public static boolean containsAny(final Collection a, final Collection b) {
		// TO DO: we may be able to optimize this by ensuring either a or b
		// is the larger of the two Collections, but I'm not sure which.
		for(Iterator iter=a.iterator(); iter.hasNext();) {
			if (b.contains(iter.next())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a {@link Map} mapping each unique element in the given {@link Collection} to an {@link Integer} representing the number of occurances of that element in the {@link Collection}. An entry that maps to <tt>null</tt> indicates that the element does not appear in the given {@link Collection}.
	 */
	public static Map<Object, Integer> getCardinalityMap(final Collection col) {
		HashMap<Object, Integer> count=new HashMap<Object, Integer>();
		Iterator it=col.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			Integer c=(count.get(obj));
			if (null==c) {
				count.put(obj, new Integer(1));
			} else {
				count.put(obj, new Integer(c.intValue()+1));
			}
		}
		return count;
	}

	/**
	 * Returns <tt>true</tt> iff <i>a</i> is a sub-collection of <i>b</i>, that is, iff the cardinality of <i>e</i> in <i>a</i> is less than or equal to the cardinality of <i>e</i> in <i>b</i>, for each element <i>e</i> in <i>a</i>.
	 * 
	 * @see #isProperSubCollection
	 * @see Collection#containsAll
	 */
	public static boolean isSubCollection(final Collection a, final Collection b) {
		Map<Object, Integer> mapa=getCardinalityMap(a);
		Map<Object, Integer> mapb=getCardinalityMap(b);
		Iterator it=a.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			if (getFreq(obj, mapa)>getFreq(obj, mapb)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns <tt>true</tt> iff the given {@link Collection}s contain exactly the same elements with exactly the same cardinality.
	 * <p>
	 * That is, iff the cardinality of <i>e</i> in <i>a</i> is equal to the cardinality of <i>e</i> in <i>b</i>, for each element <i>e</i> in <i>a</i> or <i>b</i>.
	 */
	public static boolean isEqualCollection(final Collection a, final Collection b) {
		if (a.size()!=b.size()) {
			return false;
		} else {
			Map<Object, Integer> mapa=getCardinalityMap(a);
			Map<Object, Integer> mapb=getCardinalityMap(b);
			if (mapa.size()!=mapb.size()) {
				return false;
			} else {
				Iterator<Object> it=mapa.keySet().iterator();
				while (it.hasNext()) {
					Object obj=it.next();
					if (getFreq(obj, mapa)!=getFreq(obj, mapb)) {
						return false;
					}
				}
				return true;
			}
		}
	}

	/**
	 * Returns the number of occurrences of <i>obj</i> in <i>col</i>.
	 */
	public static int cardinality(Object obj, final Collection col) {
		int count=0;
		Iterator it=col.iterator();
		while (it.hasNext()) {
			Object elt=it.next();
			if ((null==obj&&null==elt)||obj.equals(elt)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Adds all elements in the iteration to the given collection.
	 * 
	 * @param collection
	 *          the collection to add to
	 * @param iterator
	 *          the iterator of elements to add, may not be null
	 * @throws NullPointerException
	 *           if the collection or iterator is null
	 */
	public static void addAll(JList<Object> zTargetList, JIterator<Object> zSourceIterator) {
		while (zSourceIterator.hasMoreElements()) {
			zTargetList.addElement(zSourceIterator.nextElement());
		}
	}

	/**
	 * Adds all elements in the array to the given collection.
	 * 
	 * @param collection
	 *          the collection to add to
	 * @param elements
	 *          the array of elements to add, may be null
	 * @throws NullPointerException
	 *           if the collection or array is null
	 */
	public static void addAll(JList<Object> collection, Object[] elements) {
		for(int i=0, size=elements.length; i<size; i++) {
			collection.addElement(elements[i]);
		}
	}

	/**
	 * Given an Object, and an index, it will get the nth value in the object.
	 * <ul>
	 * <li>If obj is a Map, get the nth value from the <b>key</b> iterator.
	 * <li>If obj is a List or an array, get the nth value.
	 * <li>If obj is an iterator, enumeration or Collection, get the nth value from the iterator.
	 * <li>Return the original obj.
	 * </ul>
	 * 
	 * @param obj
	 *          the object to get an index of
	 * @param index
	 *          the index to get
	 * @throws IndexOutOfBoundsException
	 * @throws NoSuchElementException
	 */
	public static Object index(Object obj, int idx) {
		return index(obj, new Integer(idx));
	}

	/**
	 * Given an Object, and a key (index), it will get value associated with that key in the Object. The following checks are made:
	 * <ul>
	 * <li>If obj is a Map, use the index as a key to get a value. If no match continue.
	 * <li>Check key is an Integer. If not, return the object passed in.
	 * <li>If obj is a Map, get the nth value from the <b>key</b> iterator.
	 * <li>If obj is a List or an array, get the nth value.
	 * <li>If obj is an iterator, enumeration or Collection, get the nth value from the iterator.
	 * <li>Return the original obj.
	 * </ul>
	 * 
	 * @param obj
	 *          the object to get an index of
	 * @param index
	 *          the index to get
	 * @return the object at the specified index
	 * @throws IndexOutOfBoundsException
	 * @throws NoSuchElementException
	 */
	public static Object index(Object obj, Object index) {
		if (obj instanceof Map) {
			Map map=(Map) obj;
			if (map.containsKey(index)) {
				return map.get(index);
			}
		}
		int idx=-1;
		if (index instanceof Integer) {
			idx=((Integer) index).intValue();
		}
		if (idx<0) {
			return obj;
		} else if (obj instanceof Map) {
			Map map=(Map) obj;
			Iterator iterator=map.keySet().iterator();
			return index(iterator, idx);
		} else if (obj instanceof List) {
			return ((List) obj).get(idx);
		} else if (obj instanceof Object[]) {
			return ((Object[]) obj)[idx];
		} else if (obj instanceof Enumeration) {
			Enumeration enumeration=(Enumeration) obj;
			while (enumeration.hasMoreElements()) {
				idx--;
				if (idx==-1) {
					return enumeration.nextElement();
				} else {
					enumeration.nextElement();
				}
			}
		} else if (obj instanceof Iterator) {
			return index((Iterator) obj, idx);
		} else if (obj instanceof Collection) {
			Iterator iterator=((Collection) obj).iterator();
			return index(iterator, idx);
		}
		return obj;
	}

	private static Object index(Iterator iterator, int idx) {
		while (iterator.hasNext()) {
			idx--;
			if (idx==-1) {
				return iterator.next();
			} else {
				iterator.next();
			}
		}
		return iterator;
	}

	/** Reverses the order of the given array */
	public static void reverseArray(Object[] array) {
		int i=0;
		int j=array.length-1;
		Object tmp;

		while (j>i) {
			tmp=array[j];
			array[j]=array[i];
			array[i]=tmp;
			j--;
			i++;
		}
	}

	/**
	 * Reverse a items list. The object returned is diferent
	 * 
	 * @param zList
	 * @return
	 */
	public static JList<Object> reverse(JList<Object> zList) {
		JList<Object> oList=JCollectionFactory.createList(zList.size());
		Iterator<Object> oIterator=zList.iterator();
		int size=zList.size();
		for(int i=0; i<size; i++) {
			oList.addElementAt(i, zList.getElementAt(size-1-i));
		}
		return oList;
	}

	private static final int getFreq(final Object obj, final Map<Object, Integer> freqMap) {
		try {
			return (freqMap.get(obj)).intValue();
		} catch (NullPointerException e) {
			// ignored
		} catch (NoSuchElementException e) {
			// ignored
		}
		return 0;
	}

	/**
	 * Base class for collection decorators. I decided to do it this way because it seemed to result in the most reuse.
	 * 
	 * Inner class tree looks like:
	 * 
	 * <pre>
	 *       CollectionWrapper
	 *          PredicatedCollection
	 *             PredicatedSet
	 *             PredicatedList
	 *             PredicatedBag
	 *             PredicatedBuffer
	 *          UnmodifiableCollection
	 *             UnmodifiableBag
	 *             UnmodifiableBuffer
	 *          LazyCollection
	 *             LazyList
	 *             LazyBag
	 *       SynchronizedCollection
	 *          SynchronizedBuffer
	 *          SynchronizedBag
	 *          SynchronizedBuffer
	 * </pre>
	 */
	static class CollectionWrapper implements Collection {

		protected final Collection<Object> collection;

		public CollectionWrapper(Collection<Object> collection) {
			if (collection==null) {
				throw new IllegalArgumentException("Collection must not be null");
			}
			this.collection=collection;
		}

		public int size() {
			return this.collection.size();
		}

		public boolean isEmpty() {
			return this.collection.isEmpty();
		}

		public boolean contains(Object o) {
			return this.collection.contains(o);
		}

		public Iterator<Object> iterator() {
			return this.collection.iterator();
		}

		public Object[] toArray() {
			return this.collection.toArray();
		}

		@SuppressWarnings("unchecked")
		public Object[] toArray(Object[] o) {
			return this.collection.toArray(o);
		}

		public boolean add(Object o) {
			return this.collection.add(o);
		}

		public boolean remove(Object o) {
			return this.collection.remove(o);
		}

		public boolean containsAll(Collection c2) {
			return this.collection.containsAll(c2);
		}

		@SuppressWarnings("unchecked")
		public boolean addAll(Collection c2) {
			return this.collection.addAll(c2);
		}

		public boolean removeAll(Collection c2) {
			return this.collection.removeAll(c2);
		}

		public boolean retainAll(Collection c2) {
			return this.collection.retainAll(c2);
		}

		public void clear() {
			this.collection.clear();
		}

		@Override
		public boolean equals(Object o) {
			if (o==this) return true;
			return this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}

		@Override
		public String toString() {
			return this.collection.toString();
		}

	}

	static class UnmodifiableCollection extends CollectionWrapper {

		@SuppressWarnings("unchecked")
		public UnmodifiableCollection(Collection c) {
			super(c);
		}

		@Override
		public boolean add(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Iterator<Object> iterator() {
			return new UnmodifiableIterator(this.collection.iterator());
		}

	}

	static class SynchronizedCollection {

		protected final Collection<Object> collection;

		public SynchronizedCollection(Collection<Object> collection) {
			if (collection==null) {
				throw new IllegalArgumentException("Collection must not be null");
			}
			this.collection=collection;
		}

		public synchronized int size() {
			return this.collection.size();
		}

		public synchronized boolean isEmpty() {
			return this.collection.isEmpty();
		}

		public synchronized boolean contains(Object o) {
			return this.collection.contains(o);
		}

		public Iterator<Object> iterator() {
			return this.collection.iterator();
		}

		public synchronized Object[] toArray() {
			return this.collection.toArray();
		}

		public synchronized Object[] toArray(Object[] o) {
			return this.collection.toArray(o);
		}

		public synchronized boolean add(Object o) {
			return this.collection.add(o);
		}

		public synchronized boolean remove(Object o) {
			return this.collection.remove(o);
		}

		public synchronized boolean containsAll(Collection c2) {
			return this.collection.containsAll(c2);
		}

		@SuppressWarnings("unchecked")
		public synchronized boolean addAll(Collection c2) {
			return this.collection.addAll(c2);
		}

		public synchronized boolean removeAll(Collection c2) {
			return this.collection.removeAll(c2);
		}

		public synchronized boolean retainAll(Collection c2) {
			return this.collection.retainAll(c2);
		}

		public synchronized void clear() {
			this.collection.clear();
		}

		@Override
		public synchronized boolean equals(Object o) {
			return this.collection.equals(o);
		}

		@Override
		public synchronized int hashCode() {
			return this.collection.hashCode();
		}

		@Override
		public synchronized String toString() {
			return this.collection.toString();
		}

	}

	static class UnmodifiableIterator implements Iterator {

		protected final Iterator<Object> iterator;

		public UnmodifiableIterator(Iterator<Object> iterator) {
			if (iterator==null) {
				throw new IllegalArgumentException("Iterator must not be null");
			}
			this.iterator=iterator;
		}

		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		public Object next() {
			return this.iterator.next();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static boolean containsIgnoreCase(String zValue, JList zList) {
		Iterator oIterator=zList.iterator();
		while (oIterator.hasNext()) {
			String sValue=(String) oIterator.next();
			if (sValue.equalsIgnoreCase(zValue)) return true;
		}
		return false;
	}

	public static boolean contains(String zValue, JList zList) {
		Iterator oIterator=zList.iterator();
		while (oIterator.hasNext()) {
			String sValue=(String) oIterator.next();
			if (sValue.equals(zValue)) return true;
		}
		return false;
	}

}
