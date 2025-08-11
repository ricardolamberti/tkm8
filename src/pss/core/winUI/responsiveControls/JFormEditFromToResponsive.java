package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JProperty;

public class JFormEditFromToResponsive extends JFormTwoPropertiesResponsive  {

  
	//-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormEditFromToResponsive() {
  }
  
	public String getTipoDatoConvert() { // ver de unificar para no hacer estas negradas
		if (GetTipoDato().equals("CHAR")) return JObject.JSTRING;
		return this.GetTipoDato();
	}

	
	public JObject getProp() {
		if (oProp==null) {
			oProp = new JString();
		}
		try {
			oProp.setValue(getSpecificValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
  	return oProp;
	}

	@Override
	public JProperty getFixedProp() {
		if (oFixedProp==null) {
			oFixedProp = new JProperty(getFixedPropFrom().getType(), getName(), getLabel(), null, "", true, true, getFixedPropFrom().getSize(), getFixedPropTo().GetPrecision(), null, null, null);
		}		
		return oFixedProp;
	}


 
  @Override
  public void clear() throws Exception {
  	getPropFrom().setValue("");
  	getPropTo().setValue("");
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() {
  	 try {
			return (!getSpecificValue().equals(""));
		} catch (Exception e) {
			return false;
		}
  }


  


}
