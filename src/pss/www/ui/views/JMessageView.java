/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.views;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.tools.JMessageInfo;
import pss.core.ui.components.JPssImage;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.ui.JWebButtonResponsive;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebViewsConstants;

public class JMessageView extends JWebView {

	private int iType;
//	JWebPanelResponsive oPanel;
	JWebHResponsive title;
	JWebHResponsive oLabel;

	public static String getMessageTypeString(int zMessageType) {
		String sType;
		switch (zMessageType) {
		case JWebViewsConstants.MESSAGE_TYPE_ERROR:
			sType = JMessageInfo.TYPE_ERROR;
			break;
		case JWebViewsConstants.MESSAGE_TYPE_INFO:
			sType = JMessageInfo.TYPE_INFO;
			break;
		default:
			sType = JMessageInfo.TYPE_ERROR;
		}
		return sType;
	}

	public static int getMessageTypeFromString(String zMessageTypeString) {
		int iMessageType;
		if (JMessageInfo.TYPE_ERROR.equalsIgnoreCase(zMessageTypeString)) {
			iMessageType = JWebViewsConstants.MESSAGE_TYPE_ERROR;
		} else if (JMessageInfo.TYPE_INFO.equalsIgnoreCase(zMessageTypeString)) {
			iMessageType = JWebViewsConstants.MESSAGE_TYPE_INFO;
		} else {
			iMessageType = JWebViewsConstants.MESSAGE_TYPE_ERROR;
		}
		return iMessageType;
	}

	@Override
	protected void build() throws Exception {
//		this.setY(80);
//		this.setSize(329, 182);
//
//		this.setLayout(new JWebBorderLayout());
//
//		oPanel = new JWebPanelResponsive();
//		oPanel.setMargins(5, 5, 0, 0);
//		this.add("msg_panel", oPanel);
//		JWebBorderLayout bloyout = new JWebBorderLayout();
////		oPanel.setSkinStereotype("message_panel");
//
//		// create the form
//		JWebLabelResponsive oLabel = new JWebLabelResponsive();
//		oLabel.setSize(250, 130);
//		oPanel.add("msg_label", oLabel);
//		bloyout.setCenterComponent(oLabel);
//
//		JWebButtonResponsive oConfirm = new JWebButtonResponsive();
//		oConfirm.setSize(80, 25);
//		oConfirm.setLabel("<< Atrás");
//		oConfirm.setWebAction(JWebActionFactory.createGoBackSubmitting());
//		bloyout.setBottomComponent(oConfirm);
//		oPanel.add("confirm", oConfirm);
		
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
		title = new JWebHResponsive(3,JLanguage.translate("Mensaje"));
		JWebFieldsetResponsive fieldset = new JWebFieldsetResponsive();
		JWebForm oForm = new JWebForm();

		panel.setClassResponsive("login-panel panel panel-default");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-xs-offset-0 col-xs-12 col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8 col-lg-4 col-lg-offset-4");
		fieldset.setClassResponsive("fieldset-soft");
		imagen.setClassResponsive("img-responsive center-block");
		
		this.add("row",row);
		row.add("marco", marco);
		marco.add("panel",panel);
		panel.add("heading", panelHeading);
		panel.add("body", panelBody);
		panelHeading.add("body", title);
		if (!BizPssConfig.getPssConfig().getLogo().equalsIgnoreCase("nada.gif"))
			panelBody.add("image", imagen);
		panelBody.add("login_form", oForm);
		oForm.add("fieldset", fieldset);

		// create the form
		oLabel = new JWebHResponsive(3,"");
		fieldset.add("msg_label", oLabel);
		
		JWebButtonResponsive oConfirm = new JWebButtonResponsive();
		oConfirm.setSize(80, 25);
		oConfirm.setLabel(this.getMessage("<< Atrás"));
		oConfirm.setWebAction(JWebActionFactory.createGoBackSubmitting());
		fieldset.add("confirm", oConfirm);
			
	}

	@Override
	protected void setUp(JWebRequest zRequest) throws Exception {
		JWebActionData oData = zRequest.getData("msg_attrs");
		if (oData != null && !oData.isNull()) {
			String sMessage = oData.get("msg");
			String sType = oData.get("type");
			String sTitle = oData.get("title");
			title.setLabel(sTitle);
			title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,GuiIcon.ERROR_MESSAGE_ICON));
			this.setMessageType(getMessageTypeFromString(sType));
			oLabel.setLabel(this.getMessage(sMessage));
		} else if (zRequest.getModelObject("msg")!=null) {
			String sMessage = (String)zRequest.getModelObject("msg");
			String sType = (String)zRequest.getModelObject("type");
			String sTitle =(String)zRequest.getModelObject("title");
			title.setLabel(sTitle);
			this.setTitle(sTitle);
			title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,GuiIcon.ERROR_MESSAGE_ICON));
			this.setMessageType(getMessageTypeFromString(sType));
			oLabel.setLabel(this.getMessage(sMessage));
			
		}
		if (this.getTitle() == null) {
			String sTitle;
			switch (this.iType) {
			case MESSAGE_TYPE_INFO:
				sTitle = "Información";
				break;
			case MESSAGE_TYPE_ERROR:
			default:
				sTitle = "Error";
			}
		
			title.setLabel(getMessage(sTitle));
		}
		if (this.getIcon() == null) {
			int iIcon;
			switch (this.iType) {
			case MESSAGE_TYPE_INFO:
				iIcon = GuiIcon.INFO_MESSAGE_ICON;
				break;
			case MESSAGE_TYPE_ERROR:
			default:
				iIcon = GuiIcon.ERROR_MESSAGE_ICON;
			}
			title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,iIcon));
		}
	}

	public void setMessageType(int zType) {
		this.iType = zType;
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}

}
