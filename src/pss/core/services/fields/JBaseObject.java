package pss.core.services.fields;

import java.io.Serializable;

import org.w3c.dom.Element;


public abstract class JBaseObject implements Serializable {

	private static final long serialVersionUID = -7188720824282813372L;
	protected boolean serializeAsXML() {return false;}
	protected abstract String serialize() throws Exception;
	protected abstract void unserialize(Element e) throws Exception;
	  
}
