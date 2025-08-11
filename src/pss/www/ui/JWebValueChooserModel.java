/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.io.Serializable;

import pss.core.tools.collections.JOrderedMap;

public interface JWebValueChooserModel {

//	public JIterator<String> getValues() throws Exception;
	
	public JOrderedMap<String, Serializable> getValues(boolean one) throws Exception;
	
	public boolean hasValue(String zValue) throws Exception;

	public boolean isHtmlDescription(String zValue) throws Exception;

	public String getDescription(String zValue) throws Exception;

	public String getId(String zValue) throws Exception;

	public String getIdTree(String zValue) throws Exception;

	public String getParent(String zValue) throws Exception;

	public boolean getElegible(String zValue) throws Exception;
	
	public JWebIcon getIcon(String zValue) throws Exception;

	public JWebIcon getIconOpen(String zValue) throws Exception;

	public int getNroIcon(String zValue) throws Exception;
	
	public String getDescriptionForNullValues();

	public void setDescriptionForNullValues(String descriptionForNullValues);

	public void setExtraClassImage(String clase);

	public String getExtraClassImage();

	public void destroy();

	public void blanquear();

}
