package pss.bsp.chat;

import pss.bsp.chat.conversation.GuiConversations;
import pss.bsp.contrato.quevender.BizQueVender;
import pss.bsp.contrato.quevender.FormQueVender;
import pss.bsp.contrato.quevender.ms.GuiMSs;
import pss.bsp.contrato.rutas.GuiObjetivosRutas;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiChat  extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiChat() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizChat(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Chat IA"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormChat.class; }
  public String  getKeyField() throws Exception { return "question"; }
  public String  getDescripField() { return "response"; }
  public BizChat GetcDato() throws Exception { return (BizChat) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
 		this.addAction(10, "Conversation", null, 10020, false, false, true, "Group");
		this.addAction(30, "Preguntar", null, 10020, false, false, true, "Group");

  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(getConversation());
 	 	if (a.getId()==30) return new JActSubmit(this) {
 	 		@Override
 	 		public void submit() throws Exception {
 	 			GetcDato().addConversation();
 	 			super.submit();
 	 		}
 	 	};  	
 	 	return super.getSubmitFor(a);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }

  public JWins getConversation() throws Exception {
  	GuiConversations convs = new GuiConversations();
  	convs.setRecords(GetcDato().getConversation());
  	return convs;
  }
}