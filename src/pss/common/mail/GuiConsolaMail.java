package pss.common.mail;

import pss.common.mail.mailing.BizMail;
import pss.common.mail.mailing.GuiEnviados;
import pss.common.mail.mailing.GuiMail;
import pss.common.mail.mailing.GuiMails;
import pss.common.mail.mailing.GuiRecibidos;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiConsolaMail extends JWin {

	/**
	 * Constructor de la Clase
	 */
	public GuiConsolaMail() throws Exception {
	}

	public JRecord ObtenerDato() throws Exception {
		return new BizConsolaMail();
	}

	public String GetTitle() throws Exception {
		return "Mensajes";
	}

	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormConsolaMail.class;
	}

	public int GetNroIcono() throws Exception {
		return 63;
	}

	public String getKeyField() throws Exception {
		return "usuario";
	}

	public String getDescripField() {
		return "usuario";
	}

	public BizConsolaMail GetcDato() throws Exception {
		return (BizConsolaMail) this.getRecord();
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		this.addAction(50, "Nuevo Mensaje", null, 11, true, true);
		this.addAction(10, "Recibidos", null, 63, false, false);
		this.addAction(20, "Enviados", null, 6046, false, false);
		this.addAction(30, "Papelera", null, GuiIcon.MENOS_ICON, false, false);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50)
			return new JActNew( this.getNewMail(),0);
		if (a.getId() == 10)
			return new JActWins(getRecibidos());
		if (a.getId() == 20)
			return new JActWins(getEnviados());
		if (a.getId() == 30)
			return new JActWins(getPapelera());
		return super.getSubmitFor(a);
	}


	public JWin getNewMail() throws Exception {
		GuiMail fv = new GuiMail();
		fv.getRecord().addFilter("company", BizUsuario.getUsr().getCompany());
		fv.getRecord().addFilter("usuario", BizUsuario.getUsr().GetUsuario());
		fv.getRecord().addFilter("folder", BizMail.FOLDER_SENDERS);
		return fv;
	}

	private GuiMails getRecibidos() throws Exception {
		GuiRecibidos mails = new GuiRecibidos();
		mails.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		mails.getRecords().addFilter("usuario", BizUsuario.getUsr().GetUsuario());
		mails.getRecords().addFilter("folder", BizMail.FOLDER_INBOX);
		mails.getRecords().addOrderBy("msg_date_creation", "desc");
//		mails.getRecords().addJoin("MSG_MESSAGE");
//		mails.getRecords().addFixedFilter("MSG_MESSAGE.id_message = MSG_MAIL.id_message  ");
//		mails.getRecords().addFixedOrderBy("MSG_MESSAGE.date_creation");
		return mails;
	}

	private GuiMails getEnviados() throws Exception {
		GuiEnviados mails = new GuiEnviados();
		mails.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		mails.getRecords().addFilter("usuario", BizUsuario.getUsr().GetUsuario());
		mails.getRecords().addFilter("folder", BizMail.FOLDER_SENDERS);
		mails.getRecords().addOrderBy("msg_date_creation", "desc");
//		mails.getRecords().addJoin("MSG_MESSAGE");
//		mails.getRecords().addFixedFilter("MSG_MESSAGE.id_message = MSG_MAIL.id_message  ");
//		mails.getRecords().addFixedOrderBy("MSG_MESSAGE.date_creation");
		return mails;
	}

	private GuiMails getPapelera() throws Exception {
		GuiMails mails = new GuiMails();
		mails.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		mails.getRecords().addFilter("usuario", BizUsuario.getUsr().GetUsuario());
		mails.getRecords().addFilter("folder", BizMail.FOLDER_TRASH);
		mails.getRecords().addOrderBy("msg_date_creation", "desc");
//		mails.getRecords().addJoin("MSG_MESSAGE");
//		mails.getRecords().addFixedFilter("MSG_MESSAGE.id_message = MSG_MAIL.id_message  ");
//		mails.getRecords().addFixedOrderBy("MSG_MESSAGE.date_creation");
		return mails;
	}
	
}
