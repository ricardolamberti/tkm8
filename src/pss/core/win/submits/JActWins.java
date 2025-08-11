package pss.core.win.submits;

import pss.core.win.IControl;
import pss.core.win.JBaseWin;
import pss.core.win.JControlWin;
import pss.core.win.JWins;

/**
 * Action that opens a list of records inside a {@link JWins} container.
 * <p>
 * It supports multi selection as well as single line selection depending on
 * the configuration passed to the constructor. The resulting window can be used
 * to select elements or simply browse information.
 * </p>
 */
public class JActWins extends JAct {
	
	private boolean bMultiple=true;
	private boolean bLineSelect=true;
	public JActWins(JBaseWin zResult) {
		super(zResult, -1);
	}
	public JActWins(JBaseWin zResult, boolean multi,boolean zLineSelect) {
		this(zResult);
		this.bMultiple=multi;
		this.bLineSelect=zLineSelect;
	}
	public JActWins(JBaseWin zResult, boolean multi) {
		this(zResult);
		this.bMultiple=multi;
		this.bLineSelect=true;
	}
	
	public IControl createControlWin() throws Exception {
		return new JControlWin() {
			public JWins getRecords() throws Exception {
				return getSubmitByIdActionInOwner().getWinsResult();
			}
		};
	}

//	@Override
//	public void Do() throws Exception {
//    this.getWinsResult().showListForm(this.createControlWin());
//  }
	
	@Override
	public boolean isHistoryAction() throws Exception {
		return true;
	}
	
	@Override
	public boolean isQueryAction() throws Exception {
		return true;
	}
	
	public boolean isMultiple() throws Exception {
		return this.bMultiple;
	}
	public boolean isLineSelect() throws Exception {
		return this.bLineSelect;
	}
	public boolean isTargetAction() throws Exception {
		return true;
	}
	public boolean backToSource() throws Exception {
		return true;
	}

	
//	public void assingActionSource(BizAction action) throws Exception {
//		super.assingActionSource(action);
//		if (action==null) return;
//		if (!action.hasFilterMap()) return;
//		JWins wins = this.getWinsResult();
//		wins.assignFiltersFromFilterMap(action.getFilterMap());
//	}

	public JAct getObjSubmitTarget() throws Exception {
		return this.getObjSubmitBySource();
	}
	
}
