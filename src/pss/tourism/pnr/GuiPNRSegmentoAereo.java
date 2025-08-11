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

public class GuiPNRSegmentoAereo extends JWin implements GeoPositionable {



  /**
   * Constructor de la Clase
   */
  public GuiPNRSegmentoAereo() throws Exception  {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizPNRSegmentoAereo(); }
  public int GetNroIcono()   throws Exception { return 5129; }
  public String GetTitle()   throws Exception { return "Segmento aereo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPNRSegmentoAereo.class; }
  public String  getKeyField() throws Exception { return "codigosegmento"; }
  public String  getDescripField() { return "codigosegmento"; }
  public BizPNRSegmentoAereo GetcDato() throws Exception { return (BizPNRSegmentoAereo) this.getRecord(); }

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
  
  

 }
