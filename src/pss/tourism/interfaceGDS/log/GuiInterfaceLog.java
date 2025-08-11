package pss.tourism.interfaceGDS.log;

import java.util.Date;

import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;

public class GuiInterfaceLog extends JWin {

//	GuiTravelFile oPos;
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiInterfaceLog() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizInterfaceLog(); }
  @Override
	public String GetTitle()       throws Exception { return "Estado Interfaz"; }
  @Override
	public String getKeyField() { return "id"; }
  @Override
	public String getDescripField()                  { return "id"; }
  @Override
	public int GetNroIcono() throws Exception {
	    
	    Date lastecho = this.GetcDato().getLastEcho();
	    Date lasttransfer = this.GetcDato().getLastTransfer();
	    Date lastfile = this.GetcDato().getLastTransfer();
	  	    
	    if ( JDateTools.addMinutes(lastecho, (long)360).before(new Date() ) ) {
	    	return 430;
	    }
	    if ( lastfile!=null && JDateTools.addHours(lastfile, (long)24).before(new Date() ) ) {
	    	return 435;
	    }    
		return 433;
	}

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }
  
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizInterfaceLog GetcDato() throws Exception {
    return (BizInterfaceLog) this.getRecord();
  }



}
