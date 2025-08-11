package  pss.common.agenda.evento;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.common.agenda.turno.BizTurno;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormEvento extends JBaseForm {


private static final long serialVersionUID = 1352314773226L;

  /**
   * Propiedades de la Clase
   */


  /**
   * Constructor de la Clase
   */
  public FormEvento() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiEvento getWin() { return (GuiEvento) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  } 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		BizTurno agenda = getWin().GetcDato().getObjAgenda();
		 
		AddItemEdit( null, CHAR, REQ, "company" ).setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, UINT, OPT, "id_evento" ).setHide(true);
    
    
    
    AddItemEdit( "Razon suspensión", CHAR, OPT, "razon_suspenso" ).setVisible(false);
    
    Date inicial = getWin().GetcDato().getFechaInicio()==null?((agenda!=null)? agenda.calculeNextCita():new Date()):getWin().GetcDato().getFechaInicio();
    JFormFieldsetResponsive fs = AddItemFieldset("Fechas");
    JFormFieldsetResponsive info = AddItemFieldset("Info");
    JFormFieldsetResponsive al = AddItemFieldset("Alarma");

    if (agenda==null || agenda.isUsaHito())
    	fs.AddItemCheck( "Hito", CHAR, OPT, "hito", "S", "N" ).setSizeColumns(2).setRefreshForm(true);
    else
    	fs.AddItemCheck( "Hito", CHAR, OPT, "hito", "S", "N" ).setSizeColumns(2).SetValorDefault(false).setVisible(false);

    JFormCDatetimeResponsive dt = fs.AddItemDateTime( "fecha inicio", DATETIME, REQ, "fecha_inicio" );
  	dt.setSizeColumns(5).SetValorDefault(inicial);
    
    dt=fs.AddItemDateTime( "fecha Fin", DATETIME, OPT, "fecha_fin" );
    if (agenda==null) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(inicial);
    	cal.add(Calendar.MINUTE, 30);
    	dt.setSizeColumns(5).SetValorDefault(cal.getTime());
    } else {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(inicial);
    	cal.add(Calendar.MINUTE, (int)agenda.getTiempoTurno());
    	dt.setSizeColumns(5).SetValorDefault(cal.getTime());
    	
    }
    	
    info.AddItemEdit( "Titulo", CHAR, OPT, "titulo" );
    info.AddItemArea( "Texto", CHAR, OPT, "texto" ).setHeight(150);
    
    info.AddItemCombo( "Agenda", CHAR, OPT, "id_agenda" ).setSizeColumns(3).setReadOnly(agenda!=null);
    
    JFormComboResponsive cr;
    
    cr=info.AddItemCombo( "Estado", CHAR, REQ, "estado");
    if (agenda==null || agenda.getEstadoIncial()==null)
    	cr.setSizeColumns(3).SetValorDefault(BizEvento.ACTIVA);
    else
    	cr.setSizeColumns(3).SetValorDefault(agenda.getEstadoIncial());
 
    cr = info.AddItemCombo( "Tipo Evento", CHAR, REQ, "id_tipo_evento" );
    cr.setSizeColumns(3);
    
    if (agenda!=null)
    	cr.SetValorDefault(agenda.getTipoEventoDefault());
    info.AddItemColor("Color", CHAR, OPT, "color" ).setSizeColumns(3).setRefreshForm(true);
        
    JFormCheckResponsive ck = al.AddItemCheck( "Alarma", CHAR, OPT, "has_alarma" ,"S","N");
    if (agenda==null)
    	ck.setSizeColumns(3).SetValorDefault(false);
    else
    	ck.setSizeColumns(3).SetValorDefault(agenda.isUsaAlarma());
    
    al.AddItemDateTime( "Fecha alarma", DATETIME, OPT, "fecha_alarma" ).setSizeColumns(3).SetValorDefault(inicial);
    
    al.AddItemCombo( "Usuario alarma", CHAR, OPT, "user_alarma" , new GuiUsuarios().addFilterAdHoc("company", BizUsuario.getUsr().getCompany())).setSizeColumns(3).SetValorDefault(BizUsuario.getUsr().GetUsuario());
    
    

    
    JFormTabPanelResponsive tabZone= AddItemTabPanel();
    if (agenda==null || (agenda!=null && agenda.isUsaParticipantes()))
    	tabZone.AddItemList(10);
    if (!isAlta())
    	tabZone.AddItemListOnDemand(20);
    
    
  }
  
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls(null);
  		super.OnPostShow();
  	}


  @Override
  	public void checkControls(String sControlId) throws Exception {
 		
  		
  		if (getWin().GetcDato().isHito()) {
  			((JFormControlResponsive) this.findControl("fecha_inicio")).setLabel("Fecha");
  			this.findControl("fecha_fin").setVisible(false);
  		} else {
  			((JFormControlResponsive) this.findControl("fecha_inicio")).setLabel("Fecha inicio");
  			this.findControl("fecha_fin").setVisible(true);
  		}
  	if (this.findControl("estado").getValue()==null) return;
 		if (this.findControl("estado").getValue().equals(BizEvento.EN_SUSPENSO)) {
  			this.findControl("fecha_inicio").setVisible(false);
  			this.findControl("razon_suspenso").setVisible(true);
  		} else {
  			this.findControl("fecha_inicio").setVisible(true);
  			this.findControl("razon_suspenso").setVisible(false);
  		}
  		super.checkControls(sControlId);
  	}
}