package pss.bsp.consola;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consola.config.BizBSPConfig;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBSPConsola extends JRecord {

	JString pUsuario = new JString();

  /**
   * Constructor de la Clase
   */
  public BizBSPConsola() throws Exception {
  }
  

  public void clean() throws Exception {
  	objConfigCompany=null;
  };
  
  
//  BizBSPConfig objConfig;
//  public BizBSPConfig getObjConfig() throws Exception {
//  	if (objConfig!=null) return objConfig;
//  	BizBSPConfig c = new BizBSPConfig();
//  	c.read(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().GetUsuario());
//  	return objConfig=c;
//  }

	BizBSPConfig objConfigCompany;
	public BizBSPConfig getConfigView()  throws Exception {
		if (objConfigCompany!=null) return objConfigCompany;
		BizBSPConfig other = new  BizBSPConfig();
		other.read(BizUsuario.getUsr().getCompany(),"*");
		BizBSPCompany bsp = BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany());
		long version = bsp.getObjExtraData().getVersion();
	
		if (version==5) {
			throw new Exception("No tiene permisos para acceder a esta version");
		}
		
		return objConfigCompany=other;
	}
	@Override
	public void createProperties() throws Exception {
		addItem("usuario",pUsuario);
		super.createProperties();
	}
	
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(VIRTUAL, "usuario", "usuario",false, false, 15);
		super.createFixedProperties();
	}


}

