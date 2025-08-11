package  pss.common.regions.company;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class jbCompanyAlta extends JRecord {

	private JString pBusiness = new JString();

	public String getCompanyBusiness() throws Exception {return pBusiness.getValue();}
	public void setCompanyBusiness(String val) { pBusiness.setValue(val); }
	
  public jbCompanyAlta() throws Exception {}

  @Override
	public void createProperties() throws Exception {
    addItem("business", pBusiness);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "business", "Negocio", true, true, 15);
  }

  @Override
	public String GetTable() {
    return "";
  }

}










