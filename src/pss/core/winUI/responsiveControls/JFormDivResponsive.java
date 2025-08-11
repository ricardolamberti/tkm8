package pss.core.winUI.responsiveControls;

public class JFormDivResponsive extends JFormPanelResponsive {

	 @Override
	  public String getResponsiveClass() {
	  	if (responsiveClass==null) return "";
	  	return super.getResponsiveClass();
	  }
}
