/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActModify;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;

public class JDoPartialRefreshFormActionResolver extends JDoPssActionResolver {

	private String controlId; 
	private String sWinLovRowId; 
//	private JWin winSel; 
//	protected String getBaseActionName() {
//		return "partial-refresh-form";
//	}

	@Override
	protected boolean isAjax() {return true;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;}
  @Override
	protected boolean isNewRecordEnabled() {return false;}

  @Override
	public BizAction resolveAction(JBaseWin zActionOwner) throws Exception {
//  	return super.resolveAction(zActionOwner);
    return null;
  }

  
	public void controlsToBD(JWebActionData data, JRecord record) throws Exception {
	  String sourceId = this.getRequest().getArgument("dg_source_control_id");
	//  controlId = sourceId.substring(sourceId.lastIndexOf('-')+1);
	  sWinLovRowId=sourceId.indexOf("-")==-1?"":sourceId.substring(0,sourceId.indexOf("-"));
	  controlId=sourceId.indexOf("-")==-1?sourceId:sourceId.substring(sourceId.indexOf("-")+1);

		JWebActionData oExtraData = getRequest().getData("new_status");
		if (oExtraData!=null) { //cambia el valor de un parametro, segun demanda.
			String provider = oExtraData.get("provider");
			if (provider!=null&&!provider.equals("")&&provider.endsWith(data.getId())) {
				String param = oExtraData.get("param");
				String value = oExtraData.get("value");
				data.replace(param, value);
			}
		}
 
    super.controlsToBD(data, record);
	}

  @Override
	protected JAct getSubmit(JBaseWin actionOwner, BizAction action) throws Exception {
//  	JWin win = (JWin)actionOwner; // agregar el provider RJL
  	String provider = getRequest().getTableProvider();
  	if (provider.startsWith("form_")) provider = provider.substring(5);
    
  	JHistoryProvider hp = this.getSession().getHistoryManager().getLastHistory().findProvider(provider);
  	if (hp==null) hp = this.getSession().getHistoryManager().getLastHistory().findProviderWithinLevel(provider);
    JAct lastAction = (JAct)hp.getActionSubmit();
    JAct newAct = (JAct)lastAction.clone(); // para no pisar el lastAction
    newAct.setResult(actionOwner); // rjl, habiamos sacado esta linea, pero sin ella no anda el refresh form
    if (newAct instanceof JActFieldSwapWins)
    	actionOwner.setModeView(JWin.MODE_SWAP);
//    lastAction.getForm().findControl(controlId).
  	if (newAct instanceof JActModify)
  	  ((JActModify)newAct).markFieldChanged(null,sWinLovRowId,controlId);
  	return newAct;
  }
  public void storeOtherProviders(JBaseWin zActionOwner, BizAction zAction) throws Exception {
  }
//	protected boolean cleanPreviousHistory() throws Exception {
//		return false;
//	}
  private boolean isParentModal() throws Exception {
   	return getRequest().hasBackToModal();

//		JHistory last = this.getRequest().getSession().getHistoryManager().getLastHistory();
//		if (last==null) return false;
//		return last.getMainSumbit().getActionSource().isModal();
  	
  }
	@Override
	public boolean calculeIsModal() throws Exception {
		return isModal();
	}
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		if (calculeIsModal())
			return "resources/stylesheets/html/responsive_ajax.modalviewarea.xsl";
		return "resources/stylesheets/html/responsive_ajax.viewAreaAndTitle.xsl";
	}


}

