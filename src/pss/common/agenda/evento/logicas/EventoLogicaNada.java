package  pss.common.agenda.evento.logicas;

import pss.common.agenda.evento.BizEvento;
import pss.core.win.submits.JAct;

public class EventoLogicaNada implements IEventoLogica {
  public JAct process(BizEvento ev) throws Exception {
  	return null;
  }
  public String getActionDescription() throws Exception {
		return "Realizado";
  }

  
}
