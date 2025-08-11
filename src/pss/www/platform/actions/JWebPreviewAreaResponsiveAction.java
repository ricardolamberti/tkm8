package pss.www.platform.actions;

public class JWebPreviewAreaResponsiveAction  extends JWebViewAreaAction {

	JWebPreviewAreaResponsiveAction() {
  	super("do-PreviewAreaAction", false);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "preview_panel_"+this.getProviderName();
  }

}
