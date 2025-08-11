package pss.core.win.submits;

import pss.core.win.JBaseWin;

/**
 * Action that removes the current record from the data source.
 * <p>
 * The action is marked as a submit-only operation and it will update the
 * navigation flow after the deletion is performed.
 * </p>
 */
public class JActDelete extends JAct {

/**
	 * 
	 */
	private static final long serialVersionUID = 2942000940720499429L;

	public JActDelete(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	public JActDelete(JBaseWin zResult, String zActionId) {
		super(zResult, zActionId);
	}

	@Override
	public void Do() throws Exception {
//		if (!UITools.showConfirmation("Confirmación",
//				"¿Está seguro que desea borrar el registro?"))
//			return;
		this.submit();
	}

	@Override
	public void submit() throws Exception {
		this.getWinResult().getRecord().execProcessDelete();
	}

	@Override
	public boolean isOnlySubmit() {
		return true;
	}
 
	@Override
	public boolean backAfterSubmit(JAct last) throws Exception {
		if (last instanceof JActQuery && last.getWinResult().getRecord().isSameRecord(this.getWinResult().getRecord())) {
			setExitOnOk(true);
			return true;
		}
		setExitOnOk(false);
		return false; // si no es una accion embebida tendria que ser true
	}
	

}
