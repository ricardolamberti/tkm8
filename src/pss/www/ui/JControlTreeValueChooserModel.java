package pss.www.ui;

import java.io.Serializable;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIconos;

public class JControlTreeValueChooserModel  implements JWebValueChooserModel {

	
	private JOrderedMap<String, Serializable> oValuesMap;
	private JOrderedMap<String, TreeValue> oValuesTreeMap;
	private String extraClassImage;
	public void setExtraClassImage(String clase) {
		extraClassImage=clase;
	};
	public String getExtraClassImage() {
		return extraClassImage;
	}
	class TreeValue {
		public TreeValue(JWin zwin,String zid,String ztree, String zparent, boolean zselect) {
			id=zid;
			tree=ztree;
			parent=zparent;
			seleccionable=zselect;
			win=zwin;
		}
	  JWin win;
		String id;
		String tree;
		String parent;
		boolean seleccionable;
	}

	private JFormControl webControl;
	private IControl controlTree;
	private String descriptionForNullValues="";

	public JControlTreeValueChooserModel(JFormControl wc, IControl cc) {
		this.webControl=wc;
		this.controlTree=cc;
	}


	public JOrderedMap<String, Serializable> getValues(boolean one) throws Exception {
		this.load(one); 
		return this.oValuesMap;
	}
	public boolean getElegible(String zValue) throws Exception {
		this.load(false); 
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return false;
		return ts.seleccionable;
	}

	public String getParent(String zValue) throws Exception {
		this.load(false); 
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return null;
		return ts.parent;
	}
	public String getId(String zValue) throws Exception {
		this.load(false); 
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return null;
		return ts.id;
	}
	public String getIdTree(String zValue) throws Exception {
		this.load(false); 
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return null;
		return ts.tree;
	}
	public boolean hasValue(String zValue) throws Exception {
		this.load(false);
		return this.oValuesTreeMap.containsKey(zValue);
	}

	public String getDescription(String zValue) throws Exception {
		this.load(true);
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return null;
		JWin oWin=(JWin)ts.win;
		if (oWin!=null) {
			return webControl.getDescriptionValue(oWin);
		} else {
			return webControl.ifPermitirTodo()? "TODOS":getDescriptionForNullValues();
		}
	}
	
	public boolean isHtmlDescription(String zValue) throws Exception {
		this.load(true);
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValue));
		if (ts==null) return false;
		JWin oWin=(JWin)ts.win;
		if (oWin!=null) {
			return webControl.isHtmlDescriptionValue(oWin);
		} else {
			return false;
		}	}

	public JWebIcon getIconOpen(String zValue) throws Exception {
		this.load(true);
		JWin oWin=getWin(zValue);
		if (oWin!=null) {
			return  JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,oWin.GetNroIconoOpen());
		} else {
			return null;
		}
	}

	public JWebIcon getIcon(String zValue) throws Exception {
		this.load(true);
		JWin oWin=getWin(zValue);
		if (oWin!=null) {
			return JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,oWin.GetNroIcono());
		} else {
			return null;
		}
	}
	public int getNroIcon(String key) throws Exception {
		this.load(true);
		JWin win = this.getWin(key);
		if (win==null) return -1;
		return win.GetNroIcono();
	}
	public JWin getWin(String zValueId) throws Exception {
		this.load(true);
		TreeValue ts= ((TreeValue)this.oValuesTreeMap.getElement(zValueId));
		if (ts==null) return null;
		return ts.win;
	}

	private void load(boolean one) throws Exception {
		if (!this.needsReloading()) {
			return;
		}
 		JWins oWins=this.getWins(one);
		if (oWins==null) return;
		load("root_",oWins,one);

	}
	private void load(String root,JWins oWins,boolean one) throws Exception {
		JIterator<JWin> it=oWins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin oWin=it.nextElement();
			String newId = oWin.GetValorTreeItemClave();
			TreeValue tv = new TreeValue(oWin,webControl.getKeyValue(oWin), oWin.GetValorTreeItemClave() , oWin.GetValorTreeParentClave(), oWin.GetValorTreeSelectionable());
			this.oValuesTreeMap.addElement(newId, tv);
			this.oValuesMap.addElement(newId, oWin);
			JWins detail = oWin.getWinsDetail();
			if (detail==null) continue;
			load(newId+"_",detail,one);
		}

	}

	private boolean needsReloading() {
		if (this.oValuesMap==null) {
			this.oValuesMap=JCollectionFactory.createOrderedMap();
			this.oValuesTreeMap=JCollectionFactory.createOrderedMap();
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
		this.controlTree.setFormControl(this.webControl);
		return this.controlTree.getRecords(this.webControl, one);
	}

	public void blanquear() {
		if (this.oValuesMap!=null) {
			this.oValuesMap.removeAllElements();
			this.oValuesMap=null;
		}
		if (this.oValuesTreeMap!=null) {
			this.oValuesTreeMap.removeAllElements();
			this.oValuesTreeMap
			=null;
		}

	}

	public String getDescriptionForNullValues() {
		return descriptionForNullValues;
	}

	public void setDescriptionForNullValues(String descriptionForNullValues) {
		this.descriptionForNullValues=descriptionForNullValues;
	}

}
