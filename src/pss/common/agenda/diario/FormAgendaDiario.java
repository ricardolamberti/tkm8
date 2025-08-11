package pss.common.agenda.diario;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.GuiEvento;
import pss.common.security.BizUsuario;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.www.ui.JWebIcon;

public class FormAgendaDiario extends JBaseForm {

	 public GuiAgendaDiario getWin() { return (GuiAgendaDiario) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
  	if (getWin().GetVision().equals("MENSUAL"))
    	getInternalPanel().AddItemLabel(getWin().GetcDato().getTexto());
    //  	getInternalPanel().AddItemArea(null,CHAR,OPT,"texto");
  	JIterator<BizEvento> it = getWin().GetcDato().getEventos().getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizEvento ev = it.nextElement();
  		GuiEvento wev = BizUsuario.getUsr().getObjBusiness().getNewEventos();
  		wev.setRecord(ev);
  	  getInternalPanel().AddItemButton(ev.getFechaTitulo(), 50, false, ev).left().setBackground(ev.getColor()).addDropManager("EVENTO", wev);
  	}
  	
  	getInternalPanel().AddItemButton(null, JWebIcon.getResponsiveIcon("fa fa-calendar-plus"), 40, false).setSizeColumns(3);
  }
}
 