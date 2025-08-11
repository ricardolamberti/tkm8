package pss.www.ui.views;

import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.ui.components.JPssImage;
import pss.www.platform.actions.JWebRequest;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewZoneRow;

public class JClosedView extends JWebView  {
	

	@Override
	protected void build() throws Exception {
		
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebDivResponsive marco = new JWebDivResponsive();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive imagenDiv = new JWebDivResponsive();
		JWebImageResponsive imagen = new JWebImageResponsive(JPssImage.SOURCE_PSS,"logos/"+BizPssConfig.getPssConfig().getLogo(),null);
		JWebHResponsive title;
		title = new JWebHResponsive(3,BizUsuario.getUsr().getMessage("Session cerrada", null));
	
		panel.setClassResponsive("login-panel panel panel-default");
		panelHeading.setClassResponsive("panel-heading");
		panelBody.setClassResponsive("panel-body");
		marco.setClassResponsive("col-md-4 col-md-offset-4");
		imagenDiv.setClassResponsive("col-md-offset-2 col-md-8");

		this.add("row",row);
		row.add("marco", marco);
		marco.add("panel",panel);
		panel.add("heading", panelHeading);
		panel.add("body", panelBody);
		panelHeading.add("body", title);
		panelBody.add("imagediv", imagenDiv);
		imagenDiv.add("image", imagen);


		

	}
	
	@Override
	protected void init(JWebRequest zRequest) throws Exception {
	}

	@Override
	protected void setUp(JWebRequest zRequest) throws Exception {
	}

	@Override
	protected void generateNavigationBarActions() throws Exception {
	}





}
