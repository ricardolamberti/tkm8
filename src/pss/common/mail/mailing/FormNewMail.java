package  pss.common.mail.mailing;

import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.common.security.type.GuiUsuarioTipos;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;


public class FormNewMail extends JBaseForm {

  public FormNewMail() throws Exception {
  }

  public GuiMail getWin() { return (GuiMail) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "usuario" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "folder" ).setHide(true);
  	AddItemEdit( null, UINT, OPT, "id_message" ).setHide(true);
		AddItemEdit( "Destinatarios", CHAR, OPT, "destinatarios",8);
    AddItemCheck( "Todas las Compañia", OPT, "allcompanies" ,2);
    AddItemCheck( "Urgente", OPT, "msg_priority",2);
    AddItemEdit( "Titulo", CHAR, OPT, "msg_title" ,12);
    AddItemCombo( "Tipo", CHAR, OPT, "types", new JControlCombo() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getTipos(zOneRow);
			}
		},6);
    AddItemDateTime( "Caducidad", DATE, OPT, "valid_time",6 );
    AddItemArea( "Mensaje", CHAR, OPT, "msg_message" ).setHeight(400);
    	 
	}
	protected JWins getDestinatarios(boolean zOneRow) throws Exception {
		GuiUsuarios wins= new GuiUsuarios();
		if (zOneRow) {
			wins.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
			wins.getRecords().addFilter("usuario", this.getWin().GetcDato().getUsuario());
		} else {
			if (!BizUsuario.getUsr().isAdminUser())
				wins.getRecords().addFilter("company", this.findControl("company").getValue());
		}
		return wins;
	}
	protected JWins getTipos(boolean zOneRow) throws Exception {
		GuiUsuarioTipos wins= new GuiUsuarioTipos();
		if (zOneRow) {
			wins.getRecords().addFilter("company", this.getWin().GetcDato().getCompany());
			wins.getRecords().addFilter("tipo_usuario", this.getWin().GetcDato().getType());
		} else {
			if (!BizUsuario.getUsr().isAdminUser())
				wins.getRecords().addFilter("company", this.findControl("company").getValue());
		}
		return wins;
	}
	
} 
