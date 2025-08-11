package pss.common.documentos.docEmail;

import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiMailConversacion extends GuiDocEmail {

  public GuiMailConversacion() throws Exception {
  }

	public JAct findActionQuery() throws Exception {
		this.GetcDato().verifyLeido();
		if (this.GetcDato().hasConversacion()) {
			return new JActWins(this.getGrupoMails());
		}
		return new JActQuery(this);
	}


}
