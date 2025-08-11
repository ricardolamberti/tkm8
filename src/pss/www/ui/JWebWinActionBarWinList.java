package pss.www.ui;

import pss.core.tools.JPair;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinActionBarWinList extends JWebWinActionBar {

	
	public JWebWinActionBarWinList(JWebActionOwnerProvider zObjectProvider, boolean zForForm, String toolbar, boolean bEmbedded) {
		super(zObjectProvider, zForForm, toolbar, bEmbedded);
		this.setFalseInternalToolbar(isFalseInternalToolbar());
	}


	public boolean isWinlist() throws Exception {
		return true;
	}
	
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}	
	
	@Override
	protected void containerToXMLActions(JXMLContent zContent) throws Exception {
		String sActionId;
		zContent.startNode("actions");
		zContent.setAttribute("name", getName());
		if (isToolbarIn()&&isFalseInternalToolbar())
			zContent.setAttribute("internal_name",  "actionbarinternal_"+getName());
		zContent.setAttribute("provider", getObjectProvider().getProviderName());
		JList<String> oAllowedWinIds;
		JList<String> oNotAllowedWinIds;
		JPair<Boolean,JList<String>> oPair;
		JPair<Boolean,JList<String>> oNoPair;
		
		// solo en lista negativa
		JIterator<String> oActionIdIt = this.getActionIdToNotAllowedWins().getKeyIterator();
		while (oActionIdIt.hasMoreElements()) {
			sActionId = oActionIdIt.nextElement();
			oPair = this.getActionIdToAllowedWins().getElement(sActionId);
			oNoPair = this.getActionIdToNotAllowedWins().getElement(sActionId);
			oAllowedWinIds = (oPair==null) ?null: oPair.secondObject;
			oNotAllowedWinIds = (oNoPair==null) ?null:oNoPair.secondObject;
			if (oAllowedWinIds!=null) continue;
			if (!oNoPair.fisrtObject) continue;
			zContent.startNode("action");
			zContent.setAttribute("id", sActionId);
			zContent.setAttribute("win", oNoPair.fisrtObject);
			zContent.setAttribute("multiple", getMultipleAllowedActions().containsElement(sActionId));
			zContent.startNode("allowed_win");
			zContent.setAttribute("id", "nothing");
			zContent.endNode("allowed_win");
			zContent.endNode("action");
		}		
		// solo en lista positiva
		oActionIdIt = this.getActionIdToAllowedWins().getKeyIterator();
		while (oActionIdIt.hasMoreElements()) {
			sActionId = oActionIdIt.nextElement();
			oPair = this.getActionIdToAllowedWins().getElement(sActionId);
			oNoPair = this.getActionIdToNotAllowedWins().getElement(sActionId);
			oAllowedWinIds = (oPair==null) ?null: oPair.secondObject;
			oNotAllowedWinIds = (oNoPair==null) ?null:oNoPair.secondObject;
			if (oNotAllowedWinIds != null) continue;
			if (!oPair.fisrtObject) continue;

			zContent.startNode("action");
			zContent.setAttribute("id", sActionId);
			zContent.setAttribute("win", oPair.fisrtObject);
			zContent.setAttribute("multiple", getMultipleAllowedActions().containsElement(sActionId));
			zContent.startNode("not_allowed_win");
			zContent.setAttribute("id", "all");
			zContent.endNode("not_allowed_win");

			zContent.endNode("action");
		}

		//ambas listas
		oActionIdIt = this.getActionIdToAllowedWins().getKeyIterator();
		while (oActionIdIt.hasMoreElements()) {
			sActionId = oActionIdIt.nextElement();
			oPair = this.getActionIdToAllowedWins().getElement(sActionId);
			oNoPair = this.getActionIdToNotAllowedWins().getElement(sActionId);
			oAllowedWinIds = (oPair==null) ?null: oPair.secondObject;
			oNotAllowedWinIds = (oNoPair==null) ?null:oNoPair.secondObject;
			if ( ( oNotAllowedWinIds == null||oAllowedWinIds == null))
				continue;

			zContent.startNode("action");
			zContent.setAttribute("id", sActionId);
			zContent.setAttribute("win", oPair.fisrtObject);
			zContent.setAttribute("multiple", getMultipleAllowedActions().containsElement(sActionId));
			
			if (oNotAllowedWinIds.size()<oAllowedWinIds.size()){
				JIterator<String> oNotAllowedWinsIt = oNotAllowedWinIds.getIterator();
				while (oNotAllowedWinsIt.hasMoreElements()) {
					zContent.startNode("not_allowed_win");
					zContent.setAttribute("id", oNotAllowedWinsIt.nextElement());
					zContent.endNode("not_allowed_win");
				}
			} else {
				JIterator<String> oAllowedWinsIt = oAllowedWinIds.getIterator();
				while (oAllowedWinsIt.hasMoreElements()) {
					zContent.startNode("allowed_win");
					zContent.setAttribute("id", oAllowedWinsIt.nextElement());
					zContent.endNode("allowed_win");
				}
			}
			
			zContent.endNode("action");
		}		
		zContent.endNode("actions");
		
	}




}
