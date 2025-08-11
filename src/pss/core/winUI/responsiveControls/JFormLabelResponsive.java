package pss.core.winUI.responsiveControls;

public class JFormLabelResponsive extends JFormControlResponsive  {
  


	@Override
	public void setValue(String value) throws Exception {
  	this.setForcedValue(value);
  }
  
  @Override
  	public String getColumnClass() throws Exception {
  		String out = super.getColumnClass();
   		if (getFormatFields()==null) return out;
   		if (getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT))
  			out += " gutter-col";
  		return out;
  	}
  
	public int getSizeLabels() {
		return 12;
		
	}

}
