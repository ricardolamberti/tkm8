package pss.core.winUI.controls;

import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class JControlTree implements IControl {

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

  public JControlTree() {
    super();
  }
  public JWin buildWin() throws Exception { 
  	return getRecords(true).getWinRef().getClass().newInstance();
  }

  public JWins getRecords(Object zSource, boolean one) throws Exception { 
  	return getRecords(one);
  }
  public JWins getRecords(boolean one) throws Exception { 
  	return null;
  }

  public boolean isTranslated() {
    return false;
  }
	public boolean autoAddOneRow() throws Exception {
		return true;
	}
	@Override
	public JWins getRecords() throws Exception {
		return getRecords(true);
	}
	@Override
	public void Blanquear() throws Exception {
	}

}


