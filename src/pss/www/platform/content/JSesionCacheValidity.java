package pss.www.platform.content;

import org.apache.excalibur.source.SourceValidity;

public class JSesionCacheValidity implements SourceValidity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6877540948993639249L;

	public int isValid() {
		return VALID;
	}

	public int isValid(SourceValidity arg0) {
		return VALID;
	}
	
	

}
