package pss.bsp.consola.datos.abiertos;

import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.pais.BizPais;
import pss.bsp.parseo.IParseo;
import pss.bsp.parseo.JParseoFactory;
import pss.common.security.BizUsuario;
import pss.tourism.pnr.BizPNRTicket;

public class BizAbierto extends BizPNRTicket {


  public BizAbierto() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
   * Returns the table name
   */
  public String GetTable() { return "tickets_abiertos"; }
  public String GetTableTemporal() throws Exception  { 
  	String sql="";

  	sql+=" select *";
  	sql+=" from tur_pnr_boleto b ";
  	sql+=" where b.void = 'N' and ";
  	sql+=" b.reemitted = 'N' and ";
//  	sql+=" b.creation_date <= '2015-01-01' and ";
//  	sql+=" b.departure_date >= '2015-01-01' and ";
  	sql+=" b.numeroBoleto not in ( ";
  	sql+=" 	select d.boleto ";
		BizPais pais=BizBSPUser.getUsrBSP().getObjBSPPais();
		setPais(pais.getPais());
		IParseo[] parseos=JParseoFactory.getInstance(pais.getIdparserPdf());
		if (parseos.length>0) {
			sql+=" 	from "+parseos[0].getTableDetalle()+" d ";
	  	sql+=" 	where b.company=d.company and ";
	  	sql+=" 		d.boleto=b.numeroboleto and ";
	  	sql+=" 		d.operacion = 'REFUND' )";
			
		} else {
			sql+=" 	from bsp_pdf_arg_detalle d ";
	  	sql+=" 	where b.company=d.company and ";
	  	sql+=" 		d.boleto=b.numeroboleto and ";
	  	sql+=" 		d.operacion = 'REFUND' )";
		}
  	if (BizUsuario.getUsr().isAdminUser())
  		sql+="AND b.company = '"+BizUsuario.getUsr().getCompany()+"'";
		return "("+sql+ ") as "+GetTable(); 
  }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
