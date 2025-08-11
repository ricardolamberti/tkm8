/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.views;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.data.BizPssConfig;
import pss.core.ui.components.JPssImage;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebFieldsetResponsive;
import pss.www.ui.JWebForm;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLabel;
import pss.www.ui.JWebViewZoneRow;

public class JMessageViewAjax extends JMessageView {

    @Override
    protected void build() throws Exception {
//	this.setY(80);
//	this.setSize(329, 182);
//
//	this.setLayout(new JWebBorderLayout());
//
//	oPanel = new JWebPanelResponsive();
//	oPanel.setMargins(5, 5, 0, 0);
//	this.add("msg_panel", oPanel);
//	JWebBorderLayout bloyout = new JWebBorderLayout();
//	oPanel.setSkinStereotype("message_panel");
	
	JWebViewZoneRow row = new JWebViewZoneRow();
	JWebDivResponsive marco = new JWebDivResponsive();
	JWebDivResponsive panel = new JWebDivResponsive();
	JWebDivResponsive panelHeading = new JWebDivResponsive();
	JWebDivResponsive panelBody = new JWebDivResponsive();
	JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
	JWebHResponsive title = new JWebHResponsive(3,JLanguage.translate("Inicio de sesión"));
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
	JWebLabel oLabel = new JWebLabel();
	fieldset.add("msg_label", oLabel);
	
	}

}
