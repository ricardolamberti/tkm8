package pss.core.tools;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class represents a volatile chache where the object added,
 * may not be avaiable in some particular moment depending of
 * the growing limit or how old is the object in the cache.
 * Typically a cache for remote system access.
 * The removal policy is "The last accessed first removed"
 *
 * maxNumberOfElements indicates the maximun number of elements 
 * allowed ,cero value indicate there no limit to apply.
 *
 * defaultSecondsToGetOld indicates the default number of seconds
 * for an object in the cache to get obsolete. After that time the
 * object will not be valid on the cache. Cero indicates there is
 * no limit.
 *
 */

public class JVolatilCache {
	int maxNumberOfElements = 0;
	int defaultSecondsToGetOld = 0;
	Hashtable<Object, VolatilElement> elements;

	
	public class VolatilElement {
		long accessedTime, creationTime, secondsToLive;
		Object key, value;
		public VolatilElement() {
			super();
		}

		public VolatilElement(Object aKey, Object aValue, int timeToGetOlder) {
			super();
			key = aKey;
			value = aValue;
			secondsToLive = timeToGetOlder;
			creationTime = System.currentTimeMillis();
			accessedTime = creationTime;
		}

		public void accessed() {
			accessedTime = System.currentTimeMillis();
		}
		
		public long getAccessedTime() {
			return accessedTime;
		}
		
		public Object getKey() {
			return key;
		}
		
		public Object getValue() {
			return value;
		}
		
		public boolean isOld() {
			if (secondsToLive == 0)
				return false;
			return System.currentTimeMillis() > creationTime + 1000 * secondsToLive;
		}
	}
/**
 * VolatilCache constructor comment.
 */
public JVolatilCache() {
	super();
	elements = new Hashtable<Object, VolatilElement>();
}
/**
 * VolatilCache constructor comment.
 */
public JVolatilCache(int maximun) 
{
	super();
	maxNumberOfElements = maximun;
	elements = new Hashtable<Object, VolatilElement>(maximun);
}
/**
 * VolatilCache constructor comment.
 */
public JVolatilCache(int maximun, int defaultSeconds) 
{
	super();
	maxNumberOfElements = maximun;
	elements = new Hashtable<Object, VolatilElement>(maximun);
	defaultSecondsToGetOld = defaultSeconds;
}
/**
 * put an object in the cache
 */
private synchronized void compress()
{
	if (!this.mustCompress()) return;
	this.removeOlderElements();
	this.removeLastAccesses();
}
/**
 * Returns the Object or null if the object is not anymore in the cache
 */
public Object get(Object key)
{
	Object o = elements.get(key);
	if (o == null) return null;
	VolatilElement ve = (VolatilElement) o;
	if(ve.isOld())
	{
		elements.remove(key);
		return null;
	}

	ve.accessed();
	return ve.getValue();	
}
/**
 * put an object in the cache
 */
private boolean mustCompress()
{
	return this.usesCompression() && elements.size() > maxNumberOfElements * 2;
}
/**
 * put an object in the cache
 */
public synchronized void put(Object key, Object value)
{
	elements.put(key,new VolatilElement(key,value,defaultSecondsToGetOld));
	this.compress();
	
}
/**
 * put an object in the cache
 */
private synchronized void removeLastAccesses()
{
	if (!this.usesCompression()) return;
//not optimized!!
	while(elements.size() > maxNumberOfElements)
	{
		VolatilElement older = new VolatilElement(); //false initialization
		boolean firstTime = true;
		VolatilElement ve = older; //false initialization
		for(Enumeration<VolatilElement> e = elements.elements(); e.hasMoreElements();)
		{
			ve = e.nextElement();
			if (firstTime) {older = ve; firstTime = false;}
			else
				if (older.getAccessedTime() > ve.getAccessedTime())
					older = ve;
		}
		elements.remove(ve.getKey());
	}	
}
/**
 * put an object in the cache
 */
private synchronized void removeOlderElements()
{
	Vector<VolatilElement> toEliminate = new Vector<VolatilElement>();
	VolatilElement ve;
	for(Enumeration<VolatilElement> e = elements.elements(); e.hasMoreElements() ;)
	{
		ve = e.nextElement();
		if(ve.isOld()) toEliminate.addElement(ve);
	}
	for(Enumeration<VolatilElement> e = toEliminate.elements(); e.hasMoreElements() ;)
	{
		ve = e.nextElement();
		elements.remove(ve.getKey());
	}
}
/**
 * put an object in the cache
 */
private boolean usesCompression() 
{
	return maxNumberOfElements != 0;
}
}
