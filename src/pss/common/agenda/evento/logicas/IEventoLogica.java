package  pss.common.agenda.evento.logicas;

import pss.common.agenda.evento.BizEvento;
import pss.core.win.JWin;
import pss.core.win.submits.JAct;

public interface IEventoLogica {
   public JAct process(BizEvento ev) throws Exception ;
   public String getActionDescription() throws Exception;
}
