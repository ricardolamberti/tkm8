package pss.bsp.chat.conversation;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiConversation  extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiConversation() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizConversation(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Chat IA"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormConversation.class; }
  public String  getKeyField() throws Exception { return "question"; }
  public String  getDescripField() { return "response"; }
  public BizConversation GetcDato() throws Exception { return (BizConversation) this.getRecord(); }

	@Override
	public Class<? extends JBaseForm> getFormFlat() throws Exception {
		return FormConversationFlat.class;
	}

  @Override
  public void createActionMap() throws Exception {
		this.createActionQuery();
// 		this.addAction(10, "Preguntar", null, 10020, false, false, true, "Group");
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
// 	if (a.getId()==10) return new JActSubmit(this) {
// 		@Override
// 		public void submit() throws Exception {
// 			GetcDato().add
// 			super.submit();
// 		}
// 	};
 	return super.getSubmitFor(a);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }

}