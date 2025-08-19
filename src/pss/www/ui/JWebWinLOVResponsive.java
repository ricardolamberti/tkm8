package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinLOVResponsive extends JWebViewEditComponent implements JWebActionable {

	JFormWinLOVResponsive oFormLov;

	//	String registeredObjectOwnerId;
	private String searchString;
//	private boolean useCache;
//	private boolean useID;

	private boolean bShowLupa=false;


	public boolean isShowLupa() {
		return bShowLupa;
	}

	public JFormWinLOVResponsive getWinLov() {
		return oFormLov;
	}

	public void setShowLupa(boolean bShowLupa) {
		this.bShowLupa = bShowLupa;
	}
//	public boolean isUseID() {
//		return useID;
//	}
//
//
//	public void setUseID(boolean useID) {
//		this.useID = useID;
//	}
//	public boolean isUseCache() {
//		return useCache;
//	}
//
//	public void setUseCache(boolean useCache) {
//		this.useCache = useCache;
//	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public JWebWinLOVResponsive() {
}

	@Override
	public String getComponentType() {
		return "win_lov_responsive";
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 24);
	}


  public JWebAction getWebAction() throws Exception {
		BizAction action=oFormLov.getAction();
		if (action==null) return null;
		JWebActionOwnerProvider p = this.getObjectProvider();
		return JWebActionFactory.createViewAreaAndTitleAction(action, p , true, p.getRegisteredObjectId());
  }


	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("isSearcheable", oFormLov.isSearcheable());
		zContent.setAttribute("search_string", oFormLov.getSearchString());
		zContent.setAttribute("show_key", oFormLov.isShowKey()?"1":"0");
		zContent.setAttribute("show_lupa", oFormLov.isShowLupa());
		zContent.setAttribute("MaximumSelectionLength", oFormLov.getMaximumSelectionLength());
		zContent.setAttribute("MinimumInputLength", oFormLov.getMinimumInputLength());
		zContent.setAttribute("description", JTools.replaceForeignCharsForWeb(oFormLov.GetDisplayName()));
		zContent.setAttribute("refreshFormOnSelect", oFormLov.hasToRefreshForm());
		zContent.setAttribute("multiple", oFormLov.isMultiple());
		zContent.setAttribute("modifiedonserver", oFormLov.isModifiedOnServer());
		zContent.setAttribute("modal", oFormLov.isModal());
		if (getParent()!=null) {
			zContent.setAttributeNLS("objectOwner", this.getObjectProvider().getRegisteredObjectId());
			zContent.setAttributeNLS("obj_provider", this.getObjectProvider().getProviderName());
			if (oFormLov.getAction()!=null) zContent.setAttribute("objectAction", oFormLov.getAction().getId());
			
		}

		zContent.setAttribute("moreOptions", oFormLov.isMoreOptions());

		if (oFormLov.isMultiple() ) {
			if (oFormLov.hasWinsSelect()) {
				JIterator<JWin> it = oFormLov.GetWinsSelect().getStaticIterator();
				while (it.hasMoreElements()) {
					JWin win = it.nextElement();
					zContent.startNode("item");
					if (/*isUseCache() &&*/ !win.GetValorItemClave().equals("")) {
						String id = win.GetValorItemClave();// zContent.addObjectObj(win);
						zContent.setAttribute("id", win.GetValorItemClave());
//						zContent.setAttribute("real_id", isUseID()?win.GetValorItemClave():getKeyValue(win));
						zContent.setAttribute("real_id", getKeyValue(win));
						zContent.setAttribute("id_name", JTools.getValidFilename(id));
						
					} else {
						zContent.setAttribute("id", "");
						zContent.setAttribute("real_id", "");
					}
					zContent.setAttributeNLS("description", getDescriptionValue(win));
					zContent.endNode("item");
					
				}
			}
			
		} else {
			zContent.startNode("item");
			String showId ="";
			if (/*isUseCache() &&*/ oFormLov.GetWinSelect()!=null) {
				String id = zContent.addObjectObj( oFormLov.GetWinSelect());
				zContent.setAttribute("id", id);
//				zContent.setAttribute("real_id",  isUseID()?oFormLov.getValue():getKeyValue(oFormLov.GetWinSelect()));
				showId= getKeyValue(oFormLov.GetWinSelect());
				zContent.setAttribute("real_id",  showId);
				zContent.setAttribute("id_name", JTools.getValidFilename(id));
				
			} else {
				zContent.setAttribute("id", "");
				zContent.setAttribute("real_id", "");
			}
			zContent.setAttributeNLS("description", (oFormLov.isShowKey()?showId+" ":"") +oFormLov.getValueDescription());
			zContent.endNode("item");
		}

		if (getSearchString()!=null && !getSearchString().equals("")) {
			oFormLov.setForcedValue(getSearchString());
      oFormLov.fillSearch();
		}
		
		boolean showGroup = this.oFormLov.getItemList().size()>1;
//		JIterator oValueIt=this.getModel().getValues().getValueIterator();
		JIterator<String> oKeyIt=this.oFormLov.getItemList().getKeyIterator();
		while (oKeyIt.hasMoreElements()) {
			String group=oKeyIt.nextElement();
			if (showGroup) zContent.startNode("groupCombo");
			if (showGroup) zContent.setAttribute("description", group);
			JIterator<JWin> oValueIt=this.oFormLov.getItemList().getElement(group).getIterator();
			while (oValueIt.hasMoreElements()) {
				JWin win=(JWin)oValueIt.nextElement();
				if (win==null) continue;
				zContent.startNode("itemCombo");
				if (/*isUseCache() && */!getKeyValue(win).equals("")) {
					String id = oFormLov.isMultiple()?win.GetValorItemClave():zContent.addObjectObjTemp(win);
					zContent.setAttribute("id", id);
//					zContent.setAttribute("real_id",  isUseID()?win.GetValorItemClave():oFormLov.getKeyValue(win));
					zContent.setAttribute("real_id",  oFormLov.getKeyValue(win));
					zContent.setAttribute("id_name", JTools.getValidFilename(id));
					
				} else {
					zContent.setAttribute("id", "");
					zContent.setAttribute("real_id", "");
				}
				zContent.setAttributeNLS("description", getDescriptionValue(win));
				if (win.addItemSeparator())
					zContent.setAttribute("addseparator", "true");
				zContent.endNode("itemCombo");
			}
			if (showGroup) zContent.endNode("groupCombo");
			
		}
		
		if (oFormLov.getWellKnows()!=null) {
			JIterator<JWin> oKeyItwk=this.oFormLov.getWellKnows().getRecords(false).getStaticIterator();
			while (oKeyItwk.hasMoreElements()) {
					JWin win=oKeyItwk.nextElement();
					if (win==null) continue;
					zContent.startNode("wellknows");
					String showId ="";
 				if (/*isUseCache() && */!getKeyValue(win).equals("")) {
						String id = oFormLov.isMultiple()?win.GetValorItemClave():zContent.addObjectObjTemp(win);
						showId= oFormLov.getKeyValue(win);
						zContent.setAttribute("id", id);
						zContent.setAttribute("real_id",  showId);
						zContent.setAttribute("id_name", JTools.getValidFilename(id));
						
					} else {
						zContent.setAttribute("id", "");
						zContent.setAttribute("real_id", "");
					}
					zContent.setAttributeNLS("description",  (oFormLov.isShowKey()?showId+" ":"") +getDescriptionValue(win));
					if (win.addItemSeparator())
						zContent.setAttribute("addseparator", "true");
					zContent.endNode("wellknows");
				}
			
		}
		
			
		
			if (oFormLov.getExtraButtons().size()>0) {
			JIterator<JFormButtonResponsive> it = oFormLov.getExtraButtons().getIterator();
			while (it.hasMoreElements()) {
				JFormButtonResponsive button = it.nextElement();
				zContent.startNode("extrabutton");
				JWebButtonResponsive.createExtraButton(button,getParent()).toXML(zContent);
				zContent.endNode("extrabutton");
			}
		}
	}
	
	public String getKeyValue(JWin win) throws Exception {
		if (win==null)
			return null;
		if (oFormLov.getSearchKey()==null)
			return win.GetValorItemClave();
		return win.getRecord().getPropAsString(oFormLov.getSearchKey());
	}
	public String getDescriptionValue(JWin win) throws Exception {
		if (win==null)
			return null;
		String field = win.getDescripField();
		if (oFormLov.getDescripToShow()!=null)
			field = oFormLov.getDescripToShow();
		else if (oFormLov.getSearchDescrip()!=null)
			field = oFormLov.getSearchDescrip();
		
		JObject obj = win.getRecord().getPropDeep(field);
		if (obj.isRecord()) {
			return ((JRecord)obj.getObjectValue()).getDescripFieldValue();
			
		}
		return obj.toString();
	}
	

	public static JWebWinLOVResponsive create(JWebViewComposite parent, JFormWinLOVResponsive winControl, String onlyThisControl) throws Exception {
		JWebWinLOVResponsive webFormLov=new JWebWinLOVResponsive();
		webFormLov.setFormLov(winControl);
		webFormLov.setSearchString(winControl.getSearchString());
		webFormLov.setEditable(!winControl.ifReadOnly());
		webFormLov.setResponsive(winControl.isResponsive());
		webFormLov.setShowLupa(winControl.isShowLupa());
		webFormLov.takeAttributesFormControlResponsive(winControl);
		if (onlyThisControl!=null && winControl.getSearchString()==null)
			return null;

		if (parent==null) return webFormLov;

		parent.addChild(winControl.getName(), webFormLov);
		return webFormLov;
	}

	@Override
	public Object getValue() throws Exception {
		return null;
	}

	@Override
	public String getValueAsDBString() throws Exception {
		return oFormLov.getValue();
	}

	@Override
	public String getValueAsUIString() throws Exception {
		return null;
	}

	@Override
	public void setValue(Object zValue) throws Exception {
	}
	
  public void setValue(JObject zVal) throws Exception {
//  	if (zVal.isBaseRecord()) {
//  		JObjBD obj = (JObjBD)zVal;
//  		JWin win = oFormLov.buildWinsSelect().getWinRef();
//  		JRecord rec= (JRecord)zVal.getObjectValue();
//  		if (rec!=null) 
//  			rec.SetVision(win.GetVision());
//    	win.setRecord(rec);
//  		
//  		oFormLov.setValue(win);
//  	} else {
//  		setValueFromDBString(zVal.asPrintealbleObject());
//  	}
  }

	@Override
	public void setValueFromDBString(String zDBString) throws Exception {
	//	oFormLov.setValue(zDBString);
	}

  public void setValue(JWin zVal) throws Exception {
  //	oFormLov.setValue(zVal);
  }
  public void setValue(JWins zVal) throws Exception {
 // 	oFormLov.setValue(zVal);
  }
	public void setFormLov(JFormWinLOVResponsive oFormLov) {
		this.oFormLov = oFormLov;
	}

	protected boolean isModoConsulta() throws Exception {
		if (this.getForm() instanceof JWebWinForm) return ((JWebWinForm) this.getForm()).getBaseForm().isConsulta();
		else return false;
	}

	@Override
	protected String getState() throws Exception {
		return (isModoConsulta()) ? null : "edit";
	}

	@Override
	public void setValueFromUIString(String zValue) throws Exception {
		searchString=zValue;
	}
	
  
  public JWin getWinElegido(String zClave) throws Exception {
  	if (oFormLov.GetWinSelect()!=null && oFormLov.GetWinSelect()!=null && oFormLov.GetWinSelect().getRecord().getPropAsStringNoExec(oFormLov.GetWinSelect().getKeyField()).equals(zClave))
  		return oFormLov.GetWinSelect();
  	if (oFormLov.hasWinsSelect()) {
  		return oFormLov.GetFirstWinsSelect();
  	}
  	return (JWin) oFormLov.BuscarWin(zClave);
  	
  }
  public JWin[] getWinElegidos(String zClave) throws Exception {
  	return null;  	
  }

}
