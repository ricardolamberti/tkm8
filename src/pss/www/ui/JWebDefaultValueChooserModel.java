/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.io.Serializable;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;

public class JWebDefaultValueChooserModel implements JWebValueChooserModel {

	private JOrderedMap<String, Serializable> oValuesMap;
	private String extraClassImage;
	public void setExtraClassImage(String clase) {
		extraClassImage=clase;
	};
	public String getExtraClassImage() {
		return extraClassImage;
	}
	public JWebDefaultValueChooserModel() {
		this.oValuesMap=JCollectionFactory.createOrderedMap();
	}

	public String getDescriptionForNullValues() {
		return "";
	}

	public void setDescriptionForNullValues(String descriptionForNullValues) {
	}

	public void destroy() {
		blanquear();
	}
	public boolean getElegible(String zValue) throws Exception {
		return true;
	}

	public String getParent(String zValue) throws Exception {
		return "";
	}
	public String getId(String zValue) throws Exception {
		return "";

	}
	public String getIdTree(String zValue) throws Exception {
		return "";

	}

	public JOrderedMap<String, Serializable> getValues(boolean one) throws Exception {
		return this.oValuesMap;
	}

//	public JIterator<String> getValues() {
//		return this.oValuesMap.getKeyIterator();
//	}

	public boolean hasValue(String zValue) {
		return this.oValuesMap.containsKey(zValue);
	}

	public String getDescription(String zValue) {
		String ret=(String)this.oValuesMap.getElement(zValue);
		if (ret==null) {
			ret=getDescriptionForNullValues();
		}
		return ret;
	}
	public boolean isHtmlDescription(String zValue) throws Exception {
		return false;
	}

	public JWebIcon getIconOpen(String zValue) throws Exception {
		return getIcon(zValue);
	}

	public JWebIcon getIcon(String zValue) throws Exception {
		return null;
	}
	public int getNroIcon(String key) throws Exception {
		return -1;
	}
	public void addValue(String zValue, String zDescription) {
		this.oValuesMap.addElement(zValue, zDescription);
	}

	public String removeValue(String zValue) {
		return (String)this.oValuesMap.removeElement(zValue);
	}

	public void blanquear() {
		// if (this.oValuesMap != null) {
		// this.oValuesMap.removeAllElements();
		// }
		// this.oValuesMap = JCollectionFactory.createOrderedMap();
		// si se borran los datos se arruina en radio boton, en el blanqueo tienen que seguir todos los items
	}

}
