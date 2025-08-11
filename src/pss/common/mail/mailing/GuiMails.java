package pss.common.mail.mailing;

import java.util.Date;

import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiMails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiMails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10058; } 
  public String  GetTitle()    throws Exception  { return "Correos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiMail.class; }
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
    zLista.AddColumnaLista("Fecha", "msg_date_creation");
    zLista.AddColumnaLista("De", "msg_sender");
    zLista.AddColumnaLista("To", "destinatarios");
//    zLista.AddColumnaLista("Urgente", "urgente");
    zLista.AddColumnaLista("Asunto", "msg_title");
    zLista.AddColumnaLista("Mensaje", "message_trunc");
//    zLista.AddColumnaLista("readed");
  }
  
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateTimeResponsive("Fecha", "msg_date_creation");
		d.SetValorDefault(JDateTools.DateTimeToDate(new Date(), "00:00:00"),JDateTools.DateTimeToDate(new Date(), "23:59:59"));

//		Date today = BizUsuario.getUsr().todayGMT();
//		JFormControl d1 = zFiltros.NuevoCDate("Desde", "msg_date_creation");
//	    d1.SetValorDefault(JDateTools.addDays(today, -7));
//	    d1.setOperator(">=");
//	    d1.setIdControl("fecha_desde");
//	    
//	    d1 = zFiltros.NuevoCDate("Hasta", "msg_date_creation");
//	    d1.SetValorDefault(today);
//	    d1.setOperator("<");
//	    d1.setIdControl("fecha_hasta");
	}
	
	@Override
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("fecha_hasta")) {
			this.getRecords().addFilter("msg_date_creation", JDateTools.addDays(new Date(),  1), "<").setDynamic(true);;
			return;
		}
		super.asignFilterByControl(control);
	}

	
	
}
