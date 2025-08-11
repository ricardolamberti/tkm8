package pss.core.winUI.responsiveControls;

public class JFormColumnResponsive extends JFormPanelResponsive {

  public JFormColumnResponsive() {
 }
  
  @Override
  public String getResponsiveClass() {
  	if (responsiveClass==null) return "";
		return responsiveClass;
  }
  
}
