package pss.tourism.pnr;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiPNRReserva extends JWin {

	//GuiTravelFile oPos;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiPNRReserva() throws Exception {
  }
  
  @Override
  public int GetNroIcono() throws Exception {
  	return 5129;
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizPNRReserva(); }
  @Override
	public String GetTitle()       throws Exception { 
  	return "Reserva N° "+GetcDato().getCodigopnr()+" (pasajero "+GetcDato().getNombrePasajero()+") "+GetcDato().getCreationDate(); 
  }
  @Override
	public String getKeyField() { return "codigo_pnr"; }
  @Override
	public String getDescripField() { return "codigo_pnr"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRReserva.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
		this.createActionQuery();

  	addAction(10, "Ver Boleto", null, 17, true, true);
  	addAction(12, "Reprocesar", null, 17, true, true).setMulti(true);
  	addAction(30, "Archivo Interface", null, 10047, false, false);
   }
  
  @Override
  public boolean OkAction(BizAction act) throws Exception {
   	if (act.getId()==10) return GetcDato().hasTicket();
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
	  if (a.getId()==10) return new JActQuery(getBoleto());
   	
	   	
  	return super.getSubmitFor(a);
  }
   //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizPNRReserva GetcDato() throws Exception {
    return (BizPNRReserva) this.getRecord();
  }

  private GuiPNRFiles getArchivos() throws Exception {
  	GuiPNRFiles wins = new GuiPNRFiles();
  	wins.readAll(this);
  	return wins;
  }

  private GuiPNRTicket getBoleto() throws Exception {
  	GuiPNRTicket win = new GuiPNRTicket();
  	win.setRecord(GetcDato().getObjTicket());
  	return win;
  }

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		return null;
	}
  


	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (GetVision().length()>0&&zColName.length()>0&&GetVision().toLowerCase().indexOf(zColName.toLowerCase())!=-1)
			return "559349";
		return super.getFieldBackground(zColName);
	}


	@Override
	public int GetSimpleClick() throws Exception {
		return 1;
	}
}
