package pss.core.winUI.controls;

import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JControlCombo implements IControl  {

	private JFormControl control;
	private JProperty property;
	private JRecord record;
	
	public JRecord getRecord() {
		return record;
	}
	public void setRecord(JRecord record) {
		this.record = record;
	}
	public JProperty getProperty() {
		return property;
	}
	public void setProperty(JProperty property) {
		this.property = property;
	}
	public void setFormControl(JFormControl c) {this.control=c;}
	public JFormControl getFormControl() { return this.control;}

  public JControlCombo() {
    super();
  }

  public JWin buildWin() throws Exception { 
  	return getRecords(true).getWinRef().getClass().newInstance();
  }
  public JWins getRecords(Object zSource, boolean one) throws Exception { 
  	return getRecords(one);
  }
  public JWins getRecords(boolean one) throws Exception { 
  	return getRecords();
  }
  public JWins getRecords() throws Exception { 
  	return null;
  }
  public boolean isTranslated() {
    return false;
  }
	@Override
	public void Blanquear() throws Exception {
		
	}
	
	public boolean autoAddOneRow() throws Exception {
		return true;
	}
	
	public String valueControl(String f) throws Exception {
		JFormControl c  = this.getFormControl().findControl(f);
		if (c==null) return null;
		return c.getValue();
	}

	public boolean hasValue(String f) throws Exception {
		JFormControl c  = this.getFormControl().findControl(f);
		if (c==null) return false;
		return c.hasValue();
	}

}


