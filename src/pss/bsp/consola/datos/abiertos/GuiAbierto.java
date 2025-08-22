package pss.bsp.consola.datos.abiertos;

import pss.core.services.records.JRecord;
import pss.tourism.pnr.GuiPNRTicket;

public class GuiAbierto extends GuiPNRTicket {



  /**
   * Constructor de la Clase
   */
  public GuiAbierto() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizAbierto(); }
  public BizAbierto GetcDato() throws Exception { return (BizAbierto) this.getRecord(); }

  public void createActionMap() throws Exception {
   	this.createActionQuery();
  }





 }
