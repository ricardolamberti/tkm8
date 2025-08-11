package pss.bsp.consola.datos.abiertos;

import java.util.Calendar;
import java.util.Date;

import pss.bsp.contrato.GuiContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.FormDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.common.customList.config.carpetas.IContenidoCarpeta;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.pnr.FormPNRTicket;
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
