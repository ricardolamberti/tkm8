package pss.bsp.contrato.detalleCopaPorRutas.objetivos;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiObjetivosCopaPorRutas extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosCopaPorRutas() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizObjetivosCopaPorRutas(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo Ruta"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormObjetivosCopaPorRutas.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizObjetivosCopaPorRutas GetcDato() throws Exception { return (BizObjetivosCopaPorRutas) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(60, "Tickets Ganancia", null, 5012, true, true, true, "Group" );
   	this.addAction(70, "Tickets Total", null, 5012, true, true, true, "Group" );
   	this.addAction(150, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(160, "Tickets Ganancia", null, 5012, true, true, true, "Group" );
   	this.addAction(170, "Tickets Total", null, 5012, true, true, true, "Group" );

  }
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId() == 50)  return !GetcDato().getObjDetalle().getPax();
		if (a.getId() == 60)  return !GetcDato().getObjDetalle().getPax();
		if (a.getId() == 70)  return !GetcDato().getObjDetalle().getPax();
  	if (a.getId() == 150)  return GetcDato().getObjDetalle().getPax();
		if (a.getId() == 160)  return GetcDato().getObjDetalle().getPax();
		if (a.getId() == 170)  return GetcDato().getObjDetalle().getPax();
  	return super.OkAction(a);
  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActWins(getDetalle());
		if (a.getId() == 60)  return new JActWins(getDetalleGanancia());
		if (a.getId() == 70)  return new JActWins(getDetalleTotal());
		if (a.getId() == 150)  return new JActWins(getDetalleBoks());
		if (a.getId() == 160)  return new JActWins(getDetalleGananciaBoks());
		if (a.getId() == 170)  return new JActWins(getDetalleTotalBoks());
  	return super.getSubmitFor(a);
  }
  @Override
  public int GetDobleClick() throws Exception {
  	return 50;
  }
  
  
 
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  //	if (zColName.equals("premio_estimado")) {
    	if (GetcDato().isCumple()) return "20a429";
    	if (GetcDato().isCumplible()) return "b2b01a";
    	if (!GetcDato().isCumplible()) return "c41931";
  	//}
  	return super.getFieldForeground(zColName);
  }
  public JWins getDetalle() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalle());
		return wins;
	}
  
  public JWins getDetalleTotal() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleTotal());
		return wins;
	}
  public JWins getDetalleGanancia() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleGanancia());
		return wins;
	}
  public JWins getDetalleBoks() throws Exception {
  	GuiBookings wins = new GuiBookings();
		wins.setRecords(GetcDato().getDetalleBok());
		return wins;
	}
  
  public JWins getDetalleTotalBoks() throws Exception {
		GuiBookings wins = new GuiBookings();
		wins.setRecords(GetcDato().getDetalleTotalBok());
		return wins;
	}
  public JWins getDetalleGananciaBoks() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleGananciaBok());
		return wins;
	}
  
  @Override
  public boolean canConvertToURL() throws Exception {
  	return false;
  }
 }
