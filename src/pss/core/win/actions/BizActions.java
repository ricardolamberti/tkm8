package pss.core.win.actions;

import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

/**
 * Collection of {@link BizAction} objects used by a window.
 * <p>
 * Provides helper methods for searching actions by identifier or unique name
 * and for cloning the whole set of actions when required by the UI layer.
 * </p>
 */
public class BizActions extends JRecords<BizAction> {

	public BizActions() throws Exception {
		this.setStatic(true);
	}

	@Override
	public Class<BizAction> getBasedClass() {
		return BizAction.class;
	}

	@Override
	public String GetTable() {
		return "";
	}

	public BizAction findAction(int zId) throws Exception {
		if (zId<0) return null;
		JIterator<BizAction> it=this.getStaticIterator();
		while (it.hasMoreElements()) {
			BizAction oAction=it.nextElement();
			if (oAction.pId==zId) return oAction;
			if (oAction.hasSubActions()) {
				BizAction oSubAction=oAction.GetSubAcciones().findAction(zId);
				if (oSubAction!=null) return oSubAction;
			}
		}
		return null;
	}

	public BizAction findActionByUniqueId(String zId) throws Exception {
		if (zId==null) return null;
		if ( JTools.isNumber(zId))
			return findAction((int)JTools.getLongFirstNumberEmbedded(zId));

		JIterator<BizAction> iter=this.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction oAction=iter.nextElement();
//			PssLogger.logInfo(oAction.pAccion.getValue());
			if (oAction.pAccion.getValue().equals(zId)) return oAction;
			if (oAction.hasSubActions()) {
				BizAction oSubAction=oAction.GetSubAcciones().findActionByUniqueId(zId);
				if (oSubAction!=null) return oSubAction;
			}
		}
		return null;
	}

	// public BizAction findActionBySubmit(JAct submit) throws Exception {
	// this.firstRecord();
	// while ( this.nextRecord() ) {
	// BizAction oAction = (BizAction) this.getRecord();
	// if (oAction.GetAction() == submit) return oAction;
	// // if ( oAction.ifTieneSubMenus() ) {
	// // BizAction oSubAction = oAction.GetSubAcciones().findActionBySubmit(submit);
	// // if ( oSubAction!=null ) return oSubAction;
	// // }
	// }
	// return null;
	// }

	public BizActions createClone() throws Exception {
		BizActions clone=new BizActions();
		JIterator<BizAction> iter=this.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			clone.addItem(action.createClone());
		}
		return clone;
	}
	
//  public static BizActions createFromMap(String company, JMap<String, String> map) throws Exception {
//  	BizActions records = new BizActions();
//  	records.setStatic(true);
//  	JIterator<String> iter = map.getKeyIterator();
//  	while (iter.hasMoreElements()) {
//  		String s = iter.nextElement();
//  		BizAction a = new BizAction();
//  		a.setCompany(company);
//  		a.SetIdAction(s);
//  		a.setDescrip(map.getElement(s));
//  		a.SetNroIcono(1);
//  		records.addItem(a);
//  	}
//  	return records;
//  }


}
