package pss.bsp.consolid.model.repoCarrier.detail;

import pss.bsp.consolid.model.repoCarrier.detailMonth.GuiRepoCarrierDetailMonths;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiRepoCarrierDetail extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRepoCarrierDetail() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRepoCarrierDetail(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte detalle"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRepoCarrierDetail.class; }
   @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "total"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  public void createActionMap() throws Exception {
		this.createActionQuery();
		this.addAction(10, "Por mes", null, 10020, false, false, false, "Group");		
		this.addAction(30, "Por contratos", null, 10020, false, false, false, "Group");		

  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(getDetailrepoCarrier());

  	
		return super.getSubmitFor(a);
	}
  public JWins getDetailrepoCarrier() throws Exception {
  	GuiRepoCarrierDetailMonths dets = new GuiRepoCarrierDetailMonths();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("repo_dk_detail_id", GetcDato().getId());
  	return dets;
  }


//	public String getInterfaz4() throws Exception {
//		return GetcDato().getFileElectronico();
//	}
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizRepoCarrierDetail GetcDato() throws Exception {
    return (BizRepoCarrierDetail) this.getRecord();
  }


}
