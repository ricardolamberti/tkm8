package pss.www.platform.applications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebWinFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;

public class JWebHistoryManager implements Serializable {

  private static final int HISTORY_SIZE = 20;

  private JList<JHistory> oActionHistory;
//  private JAct lastSubmit;
  private JHistory homePage;
//  private transient WeakReference<JWebApplicationSession> weakSession;
  
//  JWebApplicationSession getSession() throws Exception {
//  	if (weakSession==null) return null;
//  	JWebApplicationSession session = weakSession.get() ; 
//  	return session;
//  }

//  void setSession(JWebApplicationSession s ) {
//  	weakSession = new WeakReference<JWebApplicationSession>(s);
//  }
  JWebApplicationSession getSession() throws Exception {
  	return JWebActionFactory.getCurrentRequest().getSession();
  }
  
//  public boolean isInLastHistory(JAct submit ) throws Exception {
//  	if (!hasHistory()) return false;
//   	return getLastHistory().getMainSumbit()==submit;
//  }

	public void cleanUpToLeaveInMemory() throws Exception {
		Iterator<JHistory> it = getActionHistory().iterator();
		while (it.hasNext()) {
			JHistory m = it.next();
			m.cleanUpToLeaveInMemory();
		}
	}
  public JWebHistoryManager(JWebApplicationSession session) {
  //	setSession(session);
  }
  
	
	public JHistory getHomePageHistory() throws Exception {
		if (this.homePage!=null) return this.homePage;
		JHistory h = new JHistory();
		BizAction act=JWebActionFactory.getPssAction(getSession().getHomePageAction());
		h.addProvider(act);
		act.getObjSubmit();
//		history.setActionOwner(null);
		return (this.homePage=h);		
	}
	
	
	
	public JHistory backToQueryHistory() throws Exception {
		while (true) {
			JHistory last = this.backOneHistory();
			if (last.getMainSumbit().isQueryAction()) return last; 
		}
	}
	
	public JHistory backToConfirmedHistory() throws Exception {
		JHistory last = this.getLastHistory();
		if (last.isConfirmHistory()) return last;
		return this.backToQueryHistory();
	}

	public void bringHistory(JAct submit) throws Exception {
		int i = this.getActionHistory().size();
		while (i>0) {
			JHistory h = this.getActionHistory().getElementAt(i-1);
			if (this.isSameClass(h.getMainSumbit(), submit)) {
				this.removeHistory(h);
				h.changeActionSource(submit.getActionSource()); // pone el action source corriente, ya que puede venir por otra accion
				this.addHistory(h); // lo trae hacia adelante
				return;
			}
			i--;
		}
	}
	

	public void gotoHistory(JAct submit) throws Exception {
		int i = this.getActionHistory().size();
		while (i>0) {
			JHistory h = this.getActionHistory().getElementAt(i-1);
			if (this.isSameClass(h.getMainSumbit(), submit)) {
				this.removeHistory(h);
				return;
			}
			i--;
		}
	}
	
	private boolean isSameClass(JAct a1, JAct a2) throws Exception {
		if (!a1.getResult().getClass().isAssignableFrom(a2.getResult().getClass())) 
			return false;
		if (!a1.getClass().isAssignableFrom(a2.getClass()) )
			return false;
		return true;
	}



	public boolean isInHistory() throws Exception {
//		return this.getLastSubmit().getActionSource().isSameAction(this.getLastHistorySubmit().getActionSource());
//		BizAction last = this.getLastSubmit().getActionSource();
//		BizAction lastHist = this.getLastHistory().getFirstAction();
//		return last.isSameAction(lastHist);
		 JAct act= this.getLastHistory().getMainSumbit();
		 if (act==null) return true;
		 return act.isHistoryAction();
	}
	
	public JHistory backToTargetHistory(JAct submit) throws Exception {

		if (this.isInHistory())	
			this.backOneHistory();
		while (true) {
			JHistory last = this.getLastHistory();
			if (last.getMainSumbit().isTargetAction(submit)) {
				return last; 
			}
			last = this.backOneHistory();
		}
	}
	
	public JHistory lastToTargetHistory() throws Exception {
		JHistory last = this.backOneHistory();
		return last; 
}


	public JHistory backOneHistory() throws Exception {
		if (this.getActionHistory().size()==0) return this.getHomePageHistory(); 
		this.getActionHistory().removeElementAt(getActionHistory().size()-1);
		JHistory h = this.getLastHistory();
		h.clearAllSubmits();
		return h;
	}

//	public JBaseWin getActionOwner() throws Exception {
//		return getLastHistory().getActionOwner();
//	}
	public JHistory getLastLastHistory() throws Exception {
		if (getActionHistory().size()<=1) return null;
		JHistory last = this.getActionHistory().getElementAt(getActionHistory().size()-2);
		return last;
	}
	
	public JHistory getLastHistory() throws Exception {
		if (getActionHistory().size()==0) {
			addHistory(this.getHomePageHistory());
		}
		JHistory last = this.getActionHistory().getElementAt(getActionHistory().size()-1);
//		if (!last.getAction().isHistoryAction()) return backOneHistory();
		return last;
	}
	public JHistory getLastWinHistory() throws Exception {
		
		if (getActionHistory().size()==0) return this.getHomePageHistory();
		JHistory last = this.getActionHistory().getElementAt(getActionHistory().size()-1);
		
		while (last.getMainSumbit().isWins()&&getActionHistory().size()!=0) {
			this.getActionHistory().removeElementAt(getActionHistory().size()-1);
			last = this.getActionHistory().getElementAt(getActionHistory().size()-1);
		}
		return last;
	}
	public JHistoryProvider findHistoryProvider(String provider) throws Exception {
		if (getActionHistory().size()==0) return null;
		int j = getActionHistory().size();
		JHistory hist = this.getActionHistory().getElementAt(--j);

		while (getActionHistory().size()!=0) {
			JHistoryProvider hprov = hist.findProvider(provider);
			if (hprov!=null) return hprov;
			hist = this.getActionHistory().getElementAt(--j);
		}
		return null;
	}
	public JHistory retriveLastWinHistory() throws Exception {
		
		if (getActionHistory().size()==0) return this.getHomePageHistory();
		int j = getActionHistory().size();
		j--;
		JHistory last = this.getActionHistory().getElementAt(j);
		
		while (last.getMainSumbit().isWins()&&getActionHistory().size()!=0) {
			j--;
			last = this.getActionHistory().getElementAt(j);
		}
		return last;
	}
	public boolean hasHistory() {
		return getActionHistory().size()>0;
	}

	public JList<JHistory> getActionHistory() {
		if (oActionHistory==null)
			oActionHistory = JCollectionFactory.createList();
		return oActionHistory;
	}

	public JHistory gotoHistory(String idRef) throws Exception {
		JHistory find =null;
		JIterator<JHistory> it = this.getActionHistory().getIterator();
		while (it.hasMoreElements()) {
			JHistory hist = it.nextElement();
			if (find!=null) {
				it.remove();
			} else  if (hist.getRef().equals(idRef)) {
				find= hist;
			}
		}
		return find;
	}
	
	public void clearHistory() throws Exception {
		JIterator<JHistory> iter = this.getActionHistory().getIterator();
		while (iter.hasMoreElements()) {
			iter.nextElement().leaveHistory();
			iter.remove();
		}
	}
	public long sizeHistory() throws Exception {
		return this.getActionHistory().size();
	}
	
	public void onlyOneInHistory() throws Exception {
		int s =this.getActionHistory().size();
		for (int i=s;i>1;i--) {
			this.getActionHistory().removeElementAt(i-1);
		}
	}
	public void addHistory(BizAction a) throws Exception {
		JHistoryProvider p = new JHistoryProvider();
		p.setAction(a);
		this.addHistory(p);
	}
	public void addHistory(JHistoryProvider p) throws Exception {
		JHistory history = new JHistory();
		this.addHistory(history);
		history.addProvider(p);
	}
	public void addHistory(JHistory history) throws Exception {
		this.getActionHistory().addElement(history);
		history.markInHistory(true);
		if (getActionHistory().size()>HISTORY_SIZE)
			this.getActionHistory().removeElementAt(0);
	}
//	private boolean isHistoryRepeated(JAct lastQueryAction) throws Exception {
//		if (!this.hasHistory()) 
//			return false;
//		if (!lastQueryAction.getClass().getName().equals(getLastHistoryAction().getClass().getName())) 
//			return false;
//		String newHistorySerialized = JWebActionFactory.baseWinToURL(lastQueryAction.getResult(), false);
//		String previousHistorySerialized = JWebActionFactory.baseWinToURL(getLastHistoryAction().getResult(), false);
//		return newHistorySerialized.equals(previousHistorySerialized);
//	}
	
//	public void removeLastHistoryAction() {
//		if (hasHistory())
//			getActionHistory().removeElementAt(getActionHistory().size()-1);
//	}

	public void removeLastHistory() throws Exception {
		this.removeHistory(this.getLastHistory());
	}

	public void removeHistory(JHistory h) throws Exception {
		if (!this.hasHistory()) return;
		h.leaveHistory();
		this.oActionHistory.removeElement(h);
	}

//	public void removeAction(JAct submit) throws Exception {
////		if (this.getLastHistoryAction()==submit) 
////			this.backOneHistory();
////		if (this.oLastAction == submit) 
////			this.oLastAction=this.getLastHistoryAction();
//		if (this.getLastHistory().getFirstSumbit()==submit) 
//			this.backOneHistory();
////		if (this.lastAction.getActionSubmit() == submit) 
////			this.lastAction=this.getLastHistory();
//
//	}

	
	public void addHistoryProvider(BizAction action) throws Exception {
		if (action==null) return;
		this.getLastHistory().addProvider(action);
	}

	public BizAction getLastHistoryAction() throws Exception {
		return this.getLastHistory().getFirstAction();
	}
	public JAct getLastHistorySubmit() throws Exception {
		return this.getLastHistory().getMainSumbit();
	}

	public JAct getLastSubmit() throws Exception {
		return (JAct) JWebActionFactory.getCurrentRequest().getRegisteredRequestObject( "action");
//		return this.lastSubmit;
	}

	public void setLastSubmit(JAct s) throws Exception {
		JWebActionFactory.getCurrentRequest().setRegisteredRequestObject( "action", s);
//		this.lastSubmit=s;
	}

	public JWebActionData findLastNavData(BizAction a) throws Exception {
		JHistoryProvider p = this.getLastHistory().findProvider(a);
		if (p==null ||  p.getNavigation()==null) {
			return JWebActionFactory.getCurrentRequest().getData("win_list_nav");
		}
		return p.getNavigation();
	}

	public BizAction findLastAction(BizAction a) throws Exception {
		JHistoryProvider p = this.getLastHistory().findProvider(a);
		if (p==null) return null;
		return p.getAction();
	}

	public JHistoryProvider findLastProvider(BizAction a) throws Exception {
		JHistory h = this.getLastHistory();
		if (h==null) return null;
		if (a==null) return h.getFirstProvider();
		JHistoryProvider p = h.findProvider(a);
		if (p==null) return null;
		return p;
	}

	public JWins findLastMultipleSelect(BizAction a) throws Exception {
		JHistoryProvider p = this.findLastProvider(a);
		if (p==null) return null;
		return p.getMultipleSelect();
	}

	public String findLastSelectItem(BizAction a) throws Exception {
		JHistoryProvider p = this.findLastProvider(a);
		if (p==null) return "-1";
		return p.getSelectedItem();
	}

	public String findLastScroll(BizAction a) throws Exception {
		JHistoryProvider p = this.findLastProvider(a);
		if (p==null) return "";
		return p.getScroller();
	}

	public void verifyClean(JAct submit, boolean cleanPrevious) throws Exception {
		if (submit==null) return;
		if (!submit.hasResult()) return;
		if (cleanPrevious) {
			clearHistory();
			return;
		}
		if(submit.getResult().forceCleanHistory())			
			this.clearHistory();
		if (!submit.getResult().isCleanHistory()) 
			return;
		
		if (submit.getResult().forceCleanIdemHistory()) 
			this.bringHistory(submit);

//		if (!this.getLastHistory().getMainSumbit().hasResult()) return;
//		if(this.getLastHistory().getMainSumbit().getResult().forceCleanHistory())			
//			this.clearHistory();
	}
	
	/////////////////////////////////////////////////////
	

	public class JLocalHistoryProvider implements Serializable {

		String action;
		String selectedItem = "-1";
		String scroller = "";
		String multiSelectName;
		List<String> multipleSelect = null;
		List<JPair<String,String>>  columnsOrder;
		List<JPair<String,String>>  extraData;
	}
	
	public class JLocalHistory implements Serializable {

		List<JLocalHistoryProvider> providers; 
	  boolean isModal;
	}
	public class JLocalHistoryManager implements Serializable {
	  List<JLocalHistory> oActionHistory;
	}
	
	JLocalHistoryProvider serializeLocalHistoryProvider(JHistoryProvider local) throws Exception {
		JLocalHistoryProvider hp = new JLocalHistoryProvider();
		hp.action = new JWebWinFactory(null).convertActionToURL(local.getAction());
		hp.columnsOrder = local.getColumnsOrder();
		
		hp.multipleSelect = local.getRawMultipleSelect();
		hp.scroller = local.getScroller();
		hp.selectedItem = local.getSelectedItem();
		if (local.getExtraData()!=null) {
			hp.extraData = new ArrayList<JPair<String,String>>();
			JIterator<String> it = local.getExtraData().getKeyIterator();
			while (it.hasMoreElements()) {
				String key = it.nextElement();
				String value = local.getExtraData().getElement(key);
				hp.extraData.add(new JPair(key,value));
			}
		}
		return hp;
	}
	
	JHistoryProvider unserializeHistoryProvider(JLocalHistoryProvider local) throws Exception {
		if (local==null) return null;
		JHistoryProvider hp = new JHistoryProvider();
		hp.action = new JWebWinFactory(null).convertURLToAction(local.action);
		hp.columnsOrder= local.columnsOrder;
		hp.multipleSelect = local.multipleSelect;
		hp.multiSelectName = local.multiSelectName;
		hp.scroller = local.scroller;
		hp.selectedItem = local.selectedItem;
		if (local.extraData!=null) {
			for(JPair<String,String> p:local.extraData) {
				hp.addExtraData(p.fisrtObject, p.secondObject);
			}
		}
		return hp;
	}
	
	JLocalHistory serializeLocalHistory(JHistory local) throws Exception {
		if (local==null) return null;
		JLocalHistory h = new JLocalHistory();
		h.isModal = local.isJSModal();
		if (local.getProviders()!=null) {
			h.providers = new ArrayList();
			Iterator<String> en=local.getProviders().keySet().iterator();
			while (en.hasNext()) {
				String tableProvider = en.next();
		  	JHistoryProvider prov = local.getProviders().get(tableProvider);
		  	h.providers.add(serializeLocalHistoryProvider(prov));
		  }
		}		
		return h;
	}
	
	
	JHistory unserializeHistory(JLocalHistory local) throws Exception {
		if (local==null) return null;
		JHistory hist = new JHistory();
		hist.isModal = local.isModal;
		for(JLocalHistoryProvider hp : local.providers) {
			hist.addProvider(unserializeHistoryProvider(hp));
		}
		return hist;
	}
	
	public JLocalHistoryManager serializeHistoryManager()  throws Exception {
		JLocalHistoryManager hm = new JLocalHistoryManager();
		if (this.oActionHistory!=null) {
			hm.oActionHistory = new ArrayList();
			JIterator<JHistory> it = this.oActionHistory.getIterator();
		  while (it.hasMoreElements()) {
		  	JHistory h = it.nextElement();
		  	hm.oActionHistory.add(serializeLocalHistory(h));
		  }
			
		}
		
		return hm;
	}
	
	public JWebHistoryManager deserializeHistoryManager(JLocalHistoryManager local)  throws Exception {
		if (local.oActionHistory!=null) {
			this.oActionHistory = JCollectionFactory.createList();
			for (JLocalHistory h:local.oActionHistory) {
				this.addHistory(unserializeHistory(h));
			}
		}
		return this;
	}
	
}
