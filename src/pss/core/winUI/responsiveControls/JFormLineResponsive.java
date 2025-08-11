package pss.core.winUI.responsiveControls;

public class JFormLineResponsive extends JFormControlResponsive  {
  


	public JFormLineResponsive() {
		setResponsiveClass("hr_base");
	} 

  boolean withLine = true;
  public boolean isWithLine() {
		return withLine;
	}

	public void setWithLine(boolean withLine) {
		this.withLine = withLine;
	}

}
