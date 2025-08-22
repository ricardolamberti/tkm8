package pss.bsp.consola.dashBoard;

import pss.common.customList.config.customlist.BizCustomList;
import pss.common.security.BizUsuario;
import pss.common.security.license.ILicense;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizDashBoard extends JRecord {

  private JString pCompany= new JString();

  private JString pLicencia= new JString() {
  	@Override
  	public void preset() throws Exception {
  		ILicense lic = getObjLicense();
  		if (lic==null) 
  			pLicencia.setValue("");
  		else
  			pLicencia.setValue(lic.getDescription());
  	}
  };


  public void setCompany(String value) {
  	this.pCompany.setValue(value);
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizDashBoard() throws Exception {
  }


  public void createProperties() throws Exception {
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
  
  
  ILicense objLicense;
  public  ILicense getObjLicense() throws Exception {
//  	if (objLicense!=null) return objLicense;
//  	if (BizUsuario.getUsr().getObjLicense()==null)
//  		throw new Exception("No tiene otorgada una licencia, comuniquese con su administrador");
//  	ILicense l=BizUsuario.getUsr().getObjLicense().getLicense();
//  	return (objLicense=l);
	  return null;
  }


  JRecords<BizCustomList> objFavoritos;
  public JRecords<BizCustomList> getObjFavoritos() throws Exception {
  //	if (this.objFavoritos!=null) return this.objFavoritos;
  	JRecords<BizCustomList> r =new JRecords<BizCustomList>(BizCustomList.class);
  	r.addFilter("company", this.pCompany.getValue().equals("")?BizUsuario.getUsr().getCompany(): this.pCompany.getValue());
		r.addJoin("LST_CUSTOM_LISTV2_FAV");
//		r.addJoin(JRelations.JOIN_ONE,"LST_GRAPH","gr"," LST_GRAPH.company=LST_CUSTOM_LISTV2_FAV.company and LST_GRAPH.list_id=LST_CUSTOM_LISTV2_FAV.list_id");
		r.addFixedFilter("where LST_CUSTOM_LISTV2_FAV.company=LST_CUSTOM_LISTV2.company"+
				" and LST_CUSTOM_LISTV2_FAV.list_id=LST_CUSTOM_LISTV2.list_id and " +
				" LST_CUSTOM_LISTV2_FAV.usuario = '"+BizUsuario.getUsr().GetUsuario()+"' ");
  	return (this.objFavoritos=r);
  }
  public BizCustomList getFavorito(int id) throws Exception {
  	int i=0;
  	JIterator<BizCustomList> inf = getObjFavoritos().getStaticIterator();
  	while (inf.hasMoreElements()) {
  		BizCustomList informe=inf.nextElement();
  		i++;
  		if (i==id) return informe;
  		
  	}
  	return null;
  }
}

