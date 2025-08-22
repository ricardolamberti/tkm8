package pss.tourism.pnr;

import pss.common.customList.config.dataBiz.GuiDataBizs;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiPNROtro extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPNROtro() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNROtro(); }
  public int GetNroIcono()   throws Exception { return 10023; }
  public String GetTitle()   throws Exception { return "Voucher"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { 
  	if (GetcDato().isHotel()) return FormPNRHotel.class;
  	if (GetcDato().isCar()) return FormPNRAuto.class;
  	return FormPNROtro.class; 
  	
  }
  public String  getKeyField() throws Exception { return "secuence_id"; }
  public String  getDescripField() { return "product_desc"; }
  public BizPNROtro GetcDato() throws Exception { return (BizPNROtro) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
		this.createActionQuery();
//  	BizAction a;
//  	a=addAction(10, "Voidear", null, 48, true, true);
//  	a.setConfirmMessage(true);
//  	a.setMulti(true);
  	addAction(12, "Reprocesar", null, 17, true, true).setMulti(true);
  	addAction(30, "Archivo Interface", null, 10047, false, false);
  	addAction(120, "Todos los Datos", null, 979, false, false);
   }
  
  @Override
  public boolean OkAction(BizAction act) throws Exception {
  	if (act.getId()==12) return BizUsuario.IsAdminCompanyUser();// &&  !GetcDato().isReemited()&&  !GetcDato().isVoided();
  	return super.OkAction(act);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==12) return new JActSubmit(this, a.getId()) {
  		public void submit() throws Exception {
  			GetcDato().execReprocesar();
  		}
  	};
	  if (a.getId()==30) return new JActWins(getArchivos());
  	if (a.getId()==120) return new JActWins(this.getDatos());
  	return super.getSubmitFor(a);
  }

  private JWins getDatos() throws Exception {
  	GuiDataBizs wins = new GuiDataBizs();
  	wins.getcRecords().setObjWinClass(this);
  	return wins;
  }
  private GuiPNRFiles getArchivos() throws Exception {
  	GuiPNRFiles wins = new GuiPNRFiles();
  	wins.readAll(this);
  	return wins;
  }
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (GetVision().length()>0&&GetVision().toLowerCase().indexOf(zColName.toLowerCase())!=-1)
			return "559349";
		return super.getFieldBackground(zColName);
	}

	@Override
	public int GetSimpleClick() throws Exception {
		return 1;
	}
 }
