package pss.bsp.chat.conversation;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormConversationFlat extends JBaseForm { /**
	 * 
	 */
	private static final long serialVersionUID = -6282755766528719376L;


	
	public GuiConversation getWin() {
		return (GuiConversation) this.getBaseWin();
	}
	public BizConversation getConversation() throws Exception {
		return this.getWin().GetcDato();
	}
	
	public FormConversationFlat() {
	}
	
	@Override
	public void InicializarPanelHeader(JWin zBase) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemLabel("Conversación");
	}

	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		AddItemLabel(this.getConversation().getQuestion()).setFontSize(15);
		AddItemHtml(null,CHAR,OPT,"respuesta_html",true,80,20).setHeight(300).setAlign(JFormCheckResponsive.LABEL_RIGHT).bold();
	//	AddItemArea(null,CHAR,OPT,"respuesta",true).setHeight(300).setAlign(JFormCheckResponsive.LABEL_RIGHT).bold();

	}
}
