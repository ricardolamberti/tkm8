package pss.www.platform.actions;

import pss.core.services.records.JRecord;
import pss.www.platform.actions.requestBundle.JWebActionData;

public interface IControlToBD {
	public void controlsToBD(JWebActionData data, JRecord record) throws Exception;
}
