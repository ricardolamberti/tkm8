package pss.core.win.submits;

import pss.core.win.JWins;


public class JActGroupSubmit extends JAct {
	
	JAct actSubmit;
	
	public JActGroupSubmit(JWins zResult) {
		super(zResult);
	}
	
	public JAct getActSubmit() {
		return this.actSubmit;
	}
	
	public void setAct(JAct submit) throws Exception {
		actSubmit=submit;
	}
	@Override
	public void Do() throws Exception { 
		JAct next = this.doSubmit();
		if (next!=null) next.Do();
	};
	
	@Override
	public void submit() throws Exception {
		actSubmit.setResult(this.getWinsResult());
		actSubmit.submit();
  }

  public JAct nextAction() throws Exception { 
  	return actSubmit.nextAction();
  }
	public boolean isOnlySubmit() { return true;}

  public boolean backAfterSubmit() { 
  	return actSubmit instanceof JActDrop;
  }
  
  public JAct getFinalSubmit() throws Exception {
  	return this.actSubmit;
  }

  public boolean desactiveReread(JAct action) throws Exception {
  	return true;
  }
  
//  @Override
//  public boolean isExitOnOk() throws Exception {
//  	
//  	return false; // acciones dentro de los swap, para que cierre el modal
//  }
}
