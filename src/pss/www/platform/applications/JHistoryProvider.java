package pss.www.platform.applications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pss.core.services.records.JFilterMap;
import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.tools.orders.GuiWinsColumns;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebWinFactory;
import pss.www.platform.actions.JWinPackager;
import pss.www.platform.actions.requestBundle.JWebActionData;

public class JHistoryProvider implements Serializable {

	BizAction action = null;

	String selectedItem = "-1";
	String scroller = "";
	String multiSelectName;
	List<String> multipleSelect = null;
	List<JPair<String,String>>  columnsOrder;
	transient JWebActionData oNavigation = null;
	
	private JMap<String,String> extraData;

	public List<String> getRawMultipleSelect() throws Exception {
		return multipleSelect;
	}
	public JWins getMultipleSelect() throws Exception {
		if (multipleSelect==null) return null;
		JWins wins = (JWins) Class.forName(multiSelectName).newInstance();
		wins.SetEstatico(true);
		for(String sWin: multipleSelect) {
                               JWin win =(JWin)new JWebWinFactory(null).getRegisterObjectTemp(sWin);
			wins.addRecord(win);
		}
	//	wins.PasarADatos();
		
		return wins;
	}


	public List<JPair<String,String>>  getColumnsOrder() {
		return columnsOrder;
	}

	public void setColumnsOrder(List<JPair<String,String>>  columnsOrder) {
		this.columnsOrder = columnsOrder;
	}


	public void setMultipleSelect(JWins zMultipleSelect) throws Exception {
		if (zMultipleSelect==null) multipleSelect=null;
		else if (multipleSelect == null) {
			multipleSelect = new ArrayList<String>();
		}
		
		if (multipleSelect != null) {
			multiSelectName = zMultipleSelect.getClass().getCanonicalName();
                       JIterator<JWin> it = zMultipleSelect.getStaticIterator();
                       while(it.hasMoreElements()) {
                               JWin win = it.nextElement();
                               multipleSelect.add(new JWinPackager(null).baseWinToJSON(win));
                       }
               }
       }

	public JHistoryProvider() {
	}

	public JAct getActionSubmit() throws Exception {
		return getAction().getObjSubmit();
	}

	public void setAction(BizAction lastAction) {
//		try {
//			this.action = new JWebWinFactory(null).convertActionToURL(lastAction);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.action=lastAction;
	}

	public BizAction getAction() throws Exception {
//		return new JWebWinFactory(null).convertURLToAction(this.action);
		return this.action;
	}

	
	public JWebActionData getNavigation() {
		return oNavigation;
	}

	public boolean hasNavigation() {
		return oNavigation!=null;
	}
	
	public void setNavigation(JWebActionData navigation) {
		if (navigation==null) return;
		if (navigation.isNull()) return;
		oNavigation=navigation;
	}

	public void setFilterMap(JFilterMap f) throws Exception {
		this.getAction().setObjFilterMap(f);
		this.getAction().setFirstAccess(false);
	}

	

	
	public String getScroller() {
		return scroller;
	}

	
	public void setScroller(String scroller) {
		this.scroller=scroller;
	}

	
	public String getSelectedItem() {
		return selectedItem;
	}


	public void setSelectedItem(String selectedItem) {
		this.selectedItem=selectedItem;
	}

	@Override
	public String toString() {
		return action.toString();
	}

	public boolean refreshOnSubmit() throws Exception {
		if (!this.getAction().hasSubmit()) return false;
		
		JAct submit = this.getActionSubmit(); 
		if (!submit.hasResult()) return true;
		if (!submit.refreshOnSubmit()) return false; // estoy en un jactnew, navego con un boton, al volver si borro el submit pierdo el unico lugar donde quedo lo almacenado lo ingresado por el usuario
		
		JBaseWin result = submit.getResult();
		if (!result.refreshOnSubmit())  
			return false;
//		if (result.hasDropListener() && !result.getDropListener().refreshOnSubmit()) 
//			return false; // HGK si estoy en una lista, do de alta y vulevo a la lista tiene que refrescar por mas que tenga drop listener
		
		return true;
	}

	public void cleanUpToLeaveInMemory() throws Exception {

	}

	public JMap<String,String> getExtraData() {
		if (extraData!=null) return extraData;
		return extraData= JCollectionFactory.createOrderedMap();
	}
	public void addExtraData(String key,String value) {
		if (getExtraData().getElement(key)!=null)
			getExtraData().removeElement(key);
		getExtraData().addElement(key, value);
	}

	public String getExtraData(String key) {
		return getExtraData().getElement(key);
	}
	public boolean hasExtraData() {
		return extraData!=null && getExtraData().size()>0;
	}
	
	public void returnning() throws Exception {
		this.getAction().setFirstAccess(false); // ya no se deberia usar
		this.getAction().setSubmitedByUser(this.isNavSubmitedByUser());
	}
	
	public boolean isNavSubmitedByUser() throws Exception {
		if (!this.hasNavigation()) return false;
		return this.getNavigation().get("button_search")!=null;
	}
}
