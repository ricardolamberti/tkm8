
package pss.core.win;

import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.winUI.controls.JFormControl;

public class JControlWin implements IControl  {
	
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
	
  public JWin buildWin() throws Exception { 
  	return getRecords(true).getWinRef().getClass().newInstance();
  }
	public boolean autoAddOneRow() throws Exception {
		return true;
	}
	/* (non-Javadoc)
	 * @see pss.core.win.IControl#setFormControl(pss.core.winUI.controls.JFormControl)
	 */
	@Override
	public void setFormControl(JFormControl c) {this.control=c;}
	/* (non-Javadoc)
	 * @see pss.core.win.IControl#getFormControl()
	 */
	@Override
	public JFormControl getFormControl() { return this.control;}
	
	public JControlWin() {
	}
	 public JWins getRecords(Object zSource, boolean one) throws Exception { 
	  	return getRecords(one);
	  }

  /* (non-Javadoc)
	 * @see pss.core.win.IControl#getRecords(boolean)
	 */
  @Override
	public JWins getRecords(boolean bOneRow) throws Exception {
  	return getRecords();
  };
  /* (non-Javadoc)
	 * @see pss.core.win.IControl#getRecords()
	 */
  @Override
	public JWins getRecords()   throws Exception {
  	return null;
  };
  /* (non-Javadoc)
	 * @see pss.core.win.IControl#Blanquear()
	 */
  @Override
	public void Blanquear() throws Exception { };
	
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


