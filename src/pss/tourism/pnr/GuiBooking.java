package pss.tourism.pnr;


import pss.common.customList.config.dataBiz.GuiDataBizs;
import pss.core.maps.GeoPositionable;
import pss.core.services.records.JRecord;
import pss.core.tools.GeoPosition;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.airports.BizAirport;

public class GuiBooking extends JWin implements GeoPositionable {



  /**
   * Constructor de la Clase
   */
  public GuiBooking() throws Exception  {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBooking(); }
  public int GetNroIcono()   throws Exception { return 5129; }
  public String GetTitle()   throws Exception { return "Booking"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBooking.class; }
  public String  getKeyField() throws Exception { return "booking"; }
  public String  getDescripField() { return "numeroboleto"; }
  public BizBooking GetcDato() throws Exception { return (BizBooking) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
  	addAction(100, "Analisis Tarifa", null, 5003, true, true);
		addAction(120, "Todos los Datos", null, 979, false, false);
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==100) return GetcDato().getObjFare()!=null;
  	return super.OkAction(a);
  }

  public JWin getRelativeWin() throws Exception {
		return this;
	}
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==100) return new JActQuery(getFare());
  	if (a.getId()==120) return new JActWins(this.getDatos());
  	return super.getSubmitFor(a);
  }

	private JWins getDatos() throws Exception {
		GuiDataBizs wins = new GuiDataBizs();
		wins.getcRecords().setObjWinClass(this);
		return wins;
	}

  public GuiPNRTicket getTicket() throws Exception {
  	GuiPNRTicket f = new GuiPNRTicket();
  	f.setRecord(GetcDato().getObjTicket());
  	return f;
  }
  public GuiPNRFare getFare() throws Exception {
  	GuiPNRFare f = new GuiPNRFare();
  	f.setRecord(GetcDato().getObjFare());
  	return f;
  }
	@Override
	public GeoPosition getGeoPosition(String field) throws Exception {
		if (field==null) return null;
		BizAirport airport = GetcDato().getObjAeropuerto(field);
		if (airport==null) return null;
		return airport.getGeoPosicion();
	}
  
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		if (GetVision().length()>0&&GetVision().toLowerCase().indexOf(zColName.toLowerCase())!=-1)
			return "559349";
		return super.getFieldBackground(zColName);
	}


 }
