package pss.bsp.consolid.model.repoDK.detail;

import pss.bsp.consolid.model.repoDK.detailContrato.GuiRepoDKDetailContratos;
import pss.bsp.consolid.model.repoDK.detailMonth.GuiRepoDKDetailMonths;
import pss.bsp.consolid.model.repoDK.detailOrg.GuiRepoDKDetailOrgs;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiRepoDKDetail extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRepoDKDetail() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRepoDKDetail(); }
  @Override
	public int GetNroIcono()       throws Exception { return 5052; }
  @Override
	public String GetTitle()       throws Exception { return "Reporte detalle"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRepoDKDetail.class; }
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
		this.addAction(20, "Por organización", null, 10020, false, false, false, "Group");		
		this.addAction(30, "Por contratos", null, 10020, false, false, false, "Group");		

  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
   	 	return super.OkAction(a);
  }
    
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWins(getDetailRepoDK());
		if (a.getId() == 20)	return new JActWins(getDetailRepoDKOrg());
		if (a.getId() == 30)	return new JActWins(getDetailRepoDKContrato());

  	
		return super.getSubmitFor(a);
	}
  public JWins getDetailRepoDK() throws Exception {
  	GuiRepoDKDetailMonths dets = new GuiRepoDKDetailMonths();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("repo_dk_detail_id", GetcDato().getId());
  	return dets;
  }
  public JWins getDetailRepoDKOrg() throws Exception {
  	GuiRepoDKDetailOrgs dets = new GuiRepoDKDetailOrgs();
  	dets.getRecords().addFilter("company", GetcDato().getCompany());
  	dets.getRecords().addFilter("repo_dk_detail_id", GetcDato().getId());
  	return dets;
  }
  public JWins getDetailRepoDKContrato() throws Exception {
  	GuiRepoDKDetailContratos dets = new GuiRepoDKDetailContratos();
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
  public BizRepoDKDetail GetcDato() throws Exception {
    return (BizRepoDKDetail) this.getRecord();
  }


}
