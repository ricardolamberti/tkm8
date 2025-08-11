package pss.core.win;

import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.winUI.controls.JFormControl;
 
public interface IControl {

	void setFormControl(JFormControl c);

	JFormControl getFormControl();
  public JWin buildWin() throws Exception;
	JWins getRecords(boolean bOneRow) throws Exception;
	JWins getRecords(Object zSource, boolean one) throws Exception;
	JWins getRecords() throws Exception;
	public void setProperty(JProperty property);
	public void setRecord(JRecord record);
	void Blanquear() throws Exception;
	public boolean autoAddOneRow() throws Exception;


}