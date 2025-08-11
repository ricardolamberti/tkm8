package pss.common.event.action;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormHtmlTextAreaResponsive;

public class FormSqlEventActionMessage  extends JBaseForm {

  public FormSqlEventActionMessage() throws Exception {
  }

  public GuiSqlEventAction getWin() { return (GuiSqlEventAction) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
  	AddItemEdit( null, UINT, OPT, "id_action" ).setHide(true);
  	AddItemEdit( null, UINT, OPT, "id_evento" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "class_evento" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "parametros" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "extradata" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "action" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "adjunto" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "usuario" ).setHide(true);

    
    JFormHtmlTextAreaResponsive cr=AddItemHtml("Mensaje", CHAR, OPT, "mensaje" );
    cr.SetReadOnly(true);
   }
	  

	} 