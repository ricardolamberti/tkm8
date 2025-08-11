package  pss.common.regions.company;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.records.JRecords;

public class BizCompanies extends JRecords<BizCompany> {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizCompanies() throws Exception {
  }

  @Override
	public Class<? extends BizCompany> getBasedClass() { return BizCompany.class;}

  @Override
	public String GetTable() { return "nod_company";}

  @Override
	public BizCompany createItem(JBaseRegistro zSet) throws Exception {
    return BizCompany.VirtualCreateType(zSet.CampoAsStr("business"));
  }

}



