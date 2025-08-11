package pss.bsp.contrato.detalleCopaWS.objetivos;

import java.util.Calendar;
import java.util.Date;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiObjetivosCopaWS extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosCopaWS() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizObjetivosCopaWS(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo COPA WS"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormObjetivosCopaWS.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizObjetivosCopaWS GetcDato() throws Exception { return (BizObjetivosCopaWS) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(60, "Tickets Ganancia", null, 5012, true, true, true, "Group" );
   	this.addAction(70, "Tickets Total", null, 5012, true, true, true, "Group" );
   	this.addAction(80, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(90, "Tickets Total", null, 5012, true, true, true, "Group" );

  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActWins(getDetalle());
		if (a.getId() == 60)  return new JActWins(getDetalleGanancia());
		if (a.getId() == 70)  return new JActWins(getDetalleTotal());
		if (a.getId() == 80)  return new JActWins(getDetalleTickets());
		if (a.getId() == 90)  return new JActWins(getDetalleTotalTickets());
  	return super.getSubmitFor(a);
  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId() == 50)  return !GetcDato().getObjDetalle().getPax();
  	if (a.getId() == 70)  return !GetcDato().getObjDetalle().getPax();
  	if (a.getId() == 80)  return GetcDato().getObjDetalle().getPax();
  	if (a.getId() == 90)  return GetcDato().getObjDetalle().getPax();
		
  	return super.OkAction(a);
  }
  @Override
  public int GetDobleClick() throws Exception {
  	return 50;
  }
  
  
 
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	return super.getFieldForeground(zColName);
  }
  public JWins getDetalle() throws Exception {
  	GuiBookings wins = new GuiBookings();
		wins.setRecords(GetcDato().getDetalle());
		return wins;
	}
  
  public JWins getDetalleTotal() throws Exception {
		GuiBookings wins = new GuiBookings();
		wins.setRecords(GetcDato().getDetalleTotal());
		return wins;
	}
  public JWins getDetalleTickets() throws Exception {
  	GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleTickets());
		wins.setShowFilters(false);
		return wins;
	}
  
  public JWins getDetalleTotalTickets() throws Exception {
  	GuiPNRTickets wins = new GuiPNRTickets();
		wins.setShowFilters(false);
		wins.setRecords(GetcDato().getDetalleTotalTickets());
		return wins;
	}
  public JWins getDetalleGanancia() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleGanancia());
		return wins;
	}
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
