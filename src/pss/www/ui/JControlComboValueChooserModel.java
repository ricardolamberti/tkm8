/*
 * Created on 23-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.io.Serializable;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIconos;

public class JControlComboValueChooserModel implements JWebValueChooserModel {

	private JOrderedMap<String, Serializable> valuesMap;
	private JFormControl webControl;
	private IControl controlCombo;
	private String descriptionForNullValues="";
	private boolean bPermitirTodo=true;
	private String extraClassImage;
	public void setExtraClassImage(String clase) {
		extraClassImage=clase;
	};
	public String getExtraClassImage() {
		return extraClassImage;
	}
	public JControlComboValueChooserModel(JFormControl wc, IControl cc) {
		this.webControl=wc;
		this.controlCombo=cc;
	}


	public JOrderedMap<String, Serializable> getValues(boolean one) throws Exception {
		return this.load(one); 
//		return this.oValuesMap;
	}
//	public JIterator<String> getValues() throws Exception {
//		this.load(false);
//		return this.oValuesMap.getKeyIterator();
//	}
//	
	public boolean hasValue(String key) throws Exception {
		return this.load(false).containsKey(key);
//		return this.oValuesMap.containsKey(zValue);
	}

	public String getDescription(String key) throws Exception {
		JWin win = this.getWin(key);
		if (win==null) return "";
		return webControl.getDescriptionValue(win);
//		this.load(true);
//		JWin oWin=(JWin)this.oValuesMap.getElement(zValue);
//		if (oWin!=null) {
//			return oWin.getDescripFieldValue();
//		} else {
//			return webControl.ifPermitirTodo()? "TODOS":getDescriptionForNullValues();
//		}
	}
	
	public boolean isHtmlDescription(String key) throws Exception {
		JWin win = this.getWin(key);
		if (win==null) return false;
		return webControl.isHtmlDescriptionValue(win);
	}
	public boolean getElegible(String zValue) throws Exception {
		return true;
	}
	
	public String getParent(String zValue) throws Exception {
		return null;
	}
	public String getId(String zValue) throws Exception {
		return null;

	}
	public String getIdTree(String zValue) throws Exception {
		return null;

	}
	public JWebIcon getIconOpen(String zValue) throws Exception {
		return getIcon(zValue);
	}
	
	public JWebIcon getIcon(String key) throws Exception {
		JWin win = this.getWin(key);
		if (win==null) return null;
		return JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,win.GetNroIcono());
//		this.load(true);
//		JWin oWin=(JWin)this.oValuesMap.getElement(zValue);
//		if (oWin!=null) {
//			return oWin.GetIconFile();
//		} else {
//			return "";
//		}
	}

	public int getNroIcon(String key) throws Exception {
		JWin win = this.getWin(key);
		if (win==null) return -1;
		return win.GetNroIcono();
	}
	
	public JWin getWin(String zValueId) throws Exception {
		return (JWin) this.load(true).getElement(zValueId);
	}

	private JOrderedMap<String, Serializable> load(boolean one) throws Exception {
		if (this.valuesMap!=null) return this.valuesMap;

		JOrderedMap<String, Serializable> map=JCollectionFactory.createOrderedMap();
		
		if (isPermitirVacio())
			map.addElement("", null);
		JWins wins=this.getWins(one);
		if (wins==null) return map;
		wins.ReRead();
		wins.firstRecord();
		while (wins.nextRecord()) {
			JWin win=wins.getRecord();
			map.addElement(webControl.getKeyValue(win), win);
			if (win.addItemSeparator()) {
				map.addElement("--------------------", null);
			}
		}
		return (this.valuesMap=map);
	}

//	private boolean needsReloading() {
//		if (this.oValuesMap==null) {
//			this.oValuesMap=JCollectionFactory.createOrderedMap();
//			return true;
//		} else {
//			return false;
//		}
//	}

	//
	// methods to override in subclasses
	//

	public void destroy() {
		blanquear();
		if (this.webControl!=null) {
			this.webControl=null;
		}
	}

	public JWins getWins(boolean one) throws Exception {
		this.controlCombo.setFormControl(this.webControl);
		return this.controlCombo.getRecords(this.webControl, one);
	}

	public void blanquear() {
//		if (this.oValuesMap!=null) {
//			this.oValuesMap.removeAllElements();
//			this.oValuesMap=null;
//		}
		this.valuesMap=null;
	}

	public boolean isPermitirVacio() throws Exception {
		if (webControl==null) return true;
		return webControl.isPermiteVacio();
	}
	public String getDescriptionForNullValues() {
		return descriptionForNullValues;
	}

	public void setDescriptionForNullValues(String descriptionForNullValues) {
		this.descriptionForNullValues=descriptionForNullValues;
	}

	public void setPermitirTodo(boolean v) {
		this.bPermitirTodo=v;
	}
}
