package pss.core.win.submits;

import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JMessageInfo;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class JActNewSubmit extends JAct {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5580988789646653029L;
	
	public JActNewSubmit(JBaseWin zResult, int zActionId, boolean zKeepForm) {
		super(zResult, zActionId);
		bExitOnOk = ! zKeepForm ;
	}
	
	public JActNewSubmit(JBaseWin zResult, int zActionId) {
		super(zResult, zActionId);
	}
	
//	@Override
//	public void Do() throws Exception {
//		if (!UITools.showConfirmation("Confirmación", "¿Está seguro?"))
//			return;
//		this.submit();
//	}	
	
  @Override
	public void execSubmit() throws Exception {
  	JWin win= this.getWinResult();
  	win.getRecord().processInsert();
  	if (win.hasAttachField()) {
  		applyAttachField(win);
  	}
  	
  	setMessage(new JMessageInfo(this.getWinResult().getRecord().getMessageInsertOk()));
	}
 
  @Override
  public JAct doSubmit(boolean zWeb) throws Exception {
  	JAct act= super.doSubmit(zWeb);
		this.setClearChangeInputs(true);
  	return act;
  }
  
  @Override
	public JAct nextAction() throws Exception {
  	JWin win = this.getWinResult();
  	if (this.isForceExitOnOk())  return null;
  	JBaseForm form = win.createQueryForm(this, !canReReadForm()); // arreglar esto ya que se crea el form solo para la banndera de exitonok HGK
  	if (form==null) return null;
  	if (this.isForceExitOnOk())  return null;
  	if (!this.isExitOnOk())  form.SetExitOnOk(this.isExitOnOk());
   	form.build(); // HGK tuve que poner esto, poner un flag en el form para no tener que hacer le build
  	if (form.hasToExitOnOk() ) return null;
  	BizAction actQuery = win.findAction(JWin.ACTION_QUERY);
  	if (actQuery!=null) return actQuery.getObjSubmit();
  	return new JActQuery(win);
  }

  
//  public JAct nextAction(Exception e) throws Exception {
//  	if (!this.isWeb()) super.nextAction(e); 
//  	return new JActNew(this.getWinResult(), this.getActionId(), e);
//  }
    
  @Override
	public boolean isOnlySubmit() { 
  	return true;
  }
  
  @Override
	public boolean backAfterSubmit() { 
  	return true;
  }
  
    
  public void applyAttachField(JWin win) throws Exception {
  	JObject obj = ((JWin)win.getDropListener()).getRecord().getPropDeep(win.getAttachField());
  	if (obj instanceof JObjBD) {
  		obj.setValue(win.getRecord());
  	} else {
  		obj.setValue(win.GetValorItemClave());
  	}
  	(((JWin)win.getDropListener()).getRecord()).addExtraData(win.getAttachField()+"_saved","S");
  }
  
  public boolean desactiveReread(JAct action) throws Exception {
  	return getWinResult().hasAttachField();
  }

}
