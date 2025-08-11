package pss.core.win.submits;

import pss.core.tools.JMessageInfo;
import pss.core.win.JBaseWin;

/**
 * <b>JActBack</b>
 * <p>
 * Esta accion, retrocede un lugar en el history y ejecuta. la operatoria no la realiza su codigo, sino que la clase es tomada como marca por JdoPssActionResolver
 * 	<code>
 * private JWebActionResult processSubmit(JAct action) throws Throwable {
 * JAct nextAction=action.doSubmit(true);
 * 		if (nextAction!=null) {
 * 			if (nextAction instanceof JActBack) {
 * 				nextAction=getBackAct(nextAction);
 * 			}
 * </code> 
 * </p>
 */
/**
 * @author rlamberti
 *
 */
public class JActBack extends JAct {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1861951660654597358L;

	// private JBaseForm form;
	int backStep = 1;


	public int getBackStep() {
		return backStep;
	}

	public void setBackStep(int backStep) {
		this.backStep = backStep;
	}
	public JActBack(JBaseWin zResult, JMessageInfo zMessage) {
		super(zResult);
		setMessage(zMessage);
	}
	public JActBack(JBaseWin zResult, String zMessage) {
		super(zResult);
		setMessage(new JMessageInfo(zMessage));
	}

	public JActBack(JBaseWin zResult, int steps) {
		super(zResult);
		backStep=steps;
	}
	public JActBack(JBaseWin zResult) {
		super(zResult);
	}
	
	@Override
	public boolean isOnlySubmit() {
		return true;
	}


}
