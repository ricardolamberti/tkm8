package  pss.bsp.event;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.event.sql.GuiSqlEvent;
import pss.core.services.records.JRecord;

public class GuiBSPSqlEvent extends GuiSqlEvent {

	public GuiBSPSqlEvent() throws Exception {
		super();
		}

  public JRecord ObtenerDato()   throws Exception { return new BizBSPSqlEvent(); }
  public BizBSPSqlEvent GetccDato() throws Exception { return (BizBSPSqlEvent) this.getRecord(); }

  

	public void ExecCheckForEventsNewFiles(String company) throws Exception {
	
		BizBSPCompany cpy = BizBSPCompany.getObjBSPCompany(company);//RJL
		if (cpy.getObjExtraData().getInactiva()) return;
		super.ExecCheckForEventsNewFiles(company);
	

	}
	public void ExecCheckForEvents(String company) throws Exception {
		BizBSPCompany cpy = BizBSPCompany.getObjBSPCompany(company);//RJL
		if (cpy.getObjExtraData().getInactiva()) return;
		super.ExecCheckForEvents(company);
	}	
}
