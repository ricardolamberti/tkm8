package pss.core.winUI.responsiveControls;

public class JFormFieldsetResponsive extends JFormPanelResponsive {

	boolean onlyFieldSet;
//only_fieldset
	public boolean isOnlyFieldSet() {
		return onlyFieldSet;
	}

	public JFormFieldsetResponsive setOnlyFieldSet(boolean onlyFieldSet) {
		this.onlyFieldSet = onlyFieldSet;
		return this;
	}
}
