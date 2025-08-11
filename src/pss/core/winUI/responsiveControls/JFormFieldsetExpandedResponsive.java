package pss.core.winUI.responsiveControls;

public class JFormFieldsetExpandedResponsive extends JFormFieldsetResponsive {

	boolean onStartCollapse;
	// los controles internos tienen que setearse setOnlyExtended o setOnlyCollapsed para que estos aparezcan o desaparezcan segun el estado del fieldset
	public boolean isOnStartCollapse() {
		return onStartCollapse;
	}

	public JFormFieldsetExpandedResponsive setOnStartCollapse(boolean startCollapse) {
		this.onStartCollapse = startCollapse;
		return this;
	}
	
}
