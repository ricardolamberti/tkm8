package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;

public class JFormTwoPropertiesResponsive  extends JFormControlResponsive {

	protected JObject oPropTo=null;
	protected String oFieldPropTo=null;
	protected JProperty oFixedPropTo=null;
	protected JObject oPropFrom=null;
	protected String oFieldPropFrom=null;
	protected JProperty oFixedPropFrom=null;

	public JObject getPropTo() {
		return oPropTo;
	}
	public void setFixedPropTo(JProperty zProp) {
		oFixedPropTo=zProp;
		if (zProp==null) return;
		if (zProp.GetCampo()==null) return;
		if (zProp.isTableBased()) bCampoQuery=true;
	}
	public void setPropTo(JObject zProp) {
		oPropTo=zProp;
	}
	public JProperty getFixedPropTo() {
		return oFixedPropTo;
	}
	public String getFieldPropTo() {
		return oFieldPropTo;
	}
	public void setFieldPropTo(String oFieldProp) {
		this.oFieldPropTo = oFieldProp;
	}
	

	public JObject getPropFrom() {
		return oPropFrom;
	}
	public void setFixedPropFrom(JProperty zProp) {
		oFixedPropFrom=zProp;
		if (zProp==null) return;
		if (zProp.GetCampo()==null) return;
		if (zProp.isTableBased()) bCampoQuery=true;
	}
	public void setPropFrom(JObject zProp) {
		oPropFrom=zProp;
	}
	public JProperty getFixedPropFrom() {
		return oFixedPropFrom;
	}
	public String getFieldPropFrom() {
		return oFieldPropFrom;
	}
	public void setFieldPropFrom(String oFieldProp) {
		this.oFieldPropFrom = oFieldProp;
	}
	
	public boolean useTwoFields() throws Exception {
		return oFixedPropTo!=null;
	}
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		if (!useTwoFields()) {
			super.BaseToControl(zModo, userRequest);
			return;
		}

	
	}
	
  @Override
	public void setValue( String zVal ) throws Exception {
  		if(zVal==null) return;
    	int pos = zVal.indexOf(",");
    	if (pos==-1) return;
    	getPropFrom().setValue(zVal.substring(0,pos));
   	 	getPropTo().setValue(zVal.substring(pos+1));
   	
  }
  @Override
  public String getSpecificValue() throws Exception {
  		if (getPropTo().toString().equals("")&&getPropFrom().toString().equals("")) return "";
  		return getPropFrom().toString()+","+getPropTo();
   }
}
