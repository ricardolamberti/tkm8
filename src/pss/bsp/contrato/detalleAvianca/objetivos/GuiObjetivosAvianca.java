package pss.bsp.contrato.detalleAvianca.objetivos;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiObjetivosAvianca extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosAvianca() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizObjetivosAvianca(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo COPA"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormObjetivosAvianca.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizObjetivosAvianca GetcDato() throws Exception { return (BizObjetivosAvianca) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(60, "Tickets Ganancia", null, 5012, true, true, true, "Group" );
		this.addAction(70, "Tickets Total", null, 5012, true, true, true, "Group" );

  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActWins(getDetalle());
		if (a.getId() == 60)  return new JActWins(getDetalleGanancia());
		if (a.getId() == 70)  return new JActWins(getDetalleTotal());


	  return super.getSubmitFor(a);
  }
  public JWins getDetalleTotal() throws Exception {
		GuiPNRTickets wins = new GuiPNRTickets();
		wins.setRecords(GetcDato().getDetalleTotal());
		return wins;
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
