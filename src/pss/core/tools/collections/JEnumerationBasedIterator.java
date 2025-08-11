/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.core.tools.collections;

import java.util.Enumeration;

/**
 * 
 * 
 * Created on 25-jun-2003
 * 
 * @author PSS
 */

class JEnumerationBasedIterator<E> implements JIterator<E> {

	private Enumeration<E> oDelegate;

	JEnumerationBasedIterator(Enumeration<E> zDelegate) {
		this.oDelegate=zDelegate;
	}

	public boolean hasMoreElements() {
		return this.oDelegate.hasMoreElements();
	}

	public E nextElement() {
		return this.oDelegate.nextElement();
	}

	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from an Enumeration");
	}

}
