package pss.bsp.contrato.detalleAvianca.grilla;

import pss.core.services.records.JRecord;
import pss.core.tools.JTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.GuiPNRTickets;

public class GuiObjetivosGrilla extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiObjetivosGrilla() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizObjetivosGrilla(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Objetivo COPA"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormObjetivosGrilla.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizObjetivosGrilla GetcDato() throws Exception { return (BizObjetivosGrilla) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
   	this.addAction(50, "Tickets Objetivo", null, 5012, true, true, true, "Group" );
   	this.addAction(60, "Tickets Ganancia", null, 5012, true, true, true, "Group" );

  }
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)  return new JActWins(getDetalle());
		if (a.getId() == 60)  return new JActWins(getDetalleGanancia());
  	return super.getSubmitFor(a);
  }
  @Override
  public int GetDobleClick() throws Exception {
  	return 50;
  }
  
  @Override
  public String getFieldBackground(String zColName) throws Exception {
  	if (zColName.toLowerCase().startsWith("labelzona")) {
     	if (GetcDato().getCumple()>=JTools.getLongFirstNumberEmbedded(zColName)) 
     		return "20a429";
  	}
  	
  	
  	return super.getFieldBackground(zColName);
  }
 
  @Override
  public String getFieldForeground(String zColName) throws Exception {
  	if (zColName.equals("total")) {
  		if (!GetcDato().isCumplible())return "FF0000";
   	}
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
