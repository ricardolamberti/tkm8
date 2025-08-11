package pss.www.ui.views;

import java.io.Serializable;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.icons.GuiIconos;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebValueChooserModel;
import pss.www.ui.controller.JWebFormControl;

public class JControlListValueChooserModel implements JWebValueChooserModel {

	private JOrderedMap<String, Serializable> oValuesMap;
	private JWebFormControl webControl;
	private JControlCombo controlCombo;
	private String descriptionForNullValues="";
	private String extraClassImage;
	public void setExtraClassImage(String clase) {
		extraClassImage=clase;
	};
	public String getExtraClassImage() {
		return extraClassImage;
	}
	public JControlListValueChooserModel(JWebFormControl wc, JControlCombo cc) {
		this.webControl=wc;
		this.controlCombo=cc;
	}


	public JOrderedMap<String, Serializable> getValues(boolean one) throws Exception {
		this.load(one); 
		return this.oValuesMap;
	}
//	public JIterator<String> getValues() throws Exception {
//		this.load(false);
//		return this.oValuesMap.getKeyIterator();
//	}
	
	public boolean hasValue(String zValue) throws Exception {
		this.load(false);
		return this.oValuesMap.containsKey(zValue);
	}
	public JWebIcon getIconOpen(String zValue) throws Exception {
		return getIcon(zValue);
	}

	public JWebIcon getIcon(String zValue) throws Exception {
		this.load(true);
		JWin oWin=(JWin)this.oValuesMap.getElement(zValue);
		if (oWin!=null) {
			return  JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,oWin.GetNroIcono());
		} else {
			return null;
		}
	}
	public int getNroIcon(String key) throws Exception {
		this.load(true);
		JWin oWin=(JWin)this.oValuesMap.getElement(key);
		if (oWin==null) return -1;
		return oWin.GetNroIcono();
	}
	
	public String getDescription(String zValue) throws Exception {
		this.load(true);
		JWin oWin=(JWin)this.oValuesMap.getElement(zValue);
		if (webControl==null) return oWin.getDescripFieldValue();
		if (oWin!=null) {
			return webControl.getDescriptionValue(oWin);
		} else {
			return webControl.ifPermitirTodo()? "TODOS":getDescriptionForNullValues();
		}
	}
	public boolean isHtmlDescription(String key) throws Exception {
		this.load(true);
		JWin oWin=(JWin)this.oValuesMap.getElement(key);
		if (webControl==null) return oWin.isHtmlDescriptionValue();
		if (oWin!=null) {
			return webControl.isHtmlDescriptionValue(oWin);
		} else {
			return false;
		}
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
	public JWin getWin(String zValueId) throws Exception {
		this.load(true);
		return (JWin)this.oValuesMap.getElement(zValueId);
	}

	private void load(boolean one) throws Exception {
		if (!this.needsReloading()) {
			return;
		}
		this.oValuesMap.removeAllElements();
//	    if (webControl.ifPermitirTodo())
//	    	this.oValuesMap.addElement("TODOS", null);
 //       if (!webControl.ifRequerido())
		JWins oWins=this.getWins(one);
		if (oWins==null) return;
		oWins.ReRead();
		oWins.firstRecord();
		while (oWins.nextRecord()) {
			JWin oWin=oWins.getRecord();
			this.oValuesMap.addElement(webControl==null?oWin.GetValorItemClave():webControl.getKeyValue(oWin), oWin);
		}

	}

	private boolean needsReloading() {
		if (this.oValuesMap==null) {
			this.oValuesMap=JCollectionFactory.createOrderedMap();
			return true;
		} else {
			return false;
		}
	}

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
		if (this.oValuesMap!=null) {
			this.oValuesMap.removeAllElements();
			this.oValuesMap=null;
		}
	}

	public String getDescriptionForNullValues() {
		return descriptionForNullValues;
	}

	public void setDescriptionForNullValues(String descriptionForNullValues) {
		this.descriptionForNullValues=descriptionForNullValues;
	}

}
