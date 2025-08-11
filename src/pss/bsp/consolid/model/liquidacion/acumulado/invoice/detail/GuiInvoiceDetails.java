package  pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail;

import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.dk.BizClienteDK;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActReport;
import pss.core.winUI.lists.JWinList;

public class GuiInvoiceDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiInvoiceDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Invoice Detalles"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiInvoiceDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  }
 
  
  


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    if (GetVision().equals("H5")) {
      zLista.AddColumnaLista("INVOICE","nombre");
      zLista.AddColumnaLista("AIRLINE","ruta");
      zLista.AddColumnaLista("No.TICKET","cupon");
      zLista.AddColumnaLista("DATE","fecha");
      zLista.AddColumnaLista("Gross Fare","tarifa");
      zLista.AddColumnaLista("IVA","iva");
      zLista.AddColumnaLista("TUA","tua");
      zLista.AddColumnaLista("TOTAL","total");
      zLista.AddColumnaLista("Form of payment","serv");
        return;
    }
    if (GetVision().equals("H4")) {
      zLista.AddColumnaLista("Description","ruta");
      zLista.AddColumnaLista("Quantity","tarifa");
      zLista.AddColumnaLista("Unit Price","otros");
      zLista.AddColumnaLista("Currency","serv");
      zLista.AddColumnaLista("Amount","total");
    	return;
    }
    zLista.AddColumnaLista("nombre");
    zLista.AddColumnaLista("ruta");
    zLista.AddColumnaLista("serv");
    zLista.AddColumnaLista("cupon");
    zLista.AddColumnaLista("clave");
    zLista.AddColumnaLista("tarifa");
    zLista.AddColumnaLista("iva");
    zLista.AddColumnaLista("tua");
    zLista.AddColumnaLista("otros");
    zLista.AddColumnaLista("total");
 	 
  }

}
