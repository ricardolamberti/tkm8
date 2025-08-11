package pss.common.event.mail;

import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormMailConf extends JBaseForm {

	public FormMailConf() throws Exception {
	}

	public GuiMailConf GetWin() {
		return (GuiMailConf) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemEdit("COMPANY", CHAR, OPT, "company").hide();
		JFormPanelResponsive col1 = col.AddItemColumn(4);
		col1.AddItemEdit("Descripción", CHAR, REQ, "description");
		JFormFieldsetResponsive fset = col1.AddItemFieldset("SMTP");
		fset.AddItemEdit("Server", CHAR, OPT, "smtp_server");
		fset.AddItemEdit("Port", UINT, OPT, "smtp_port");
		fset.AddItemCombo("transport", CHAR, OPT, "transport", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getSendTransports();
			}}
		);
		fset = col1.AddItemFieldset("IMAP");
		fset.AddItemEdit("server", CHAR, OPT, "pop3_server");
		fset.AddItemEdit("port", UINT, OPT, "pop3_port");
		fset.AddItemCombo("transport", CHAR, OPT, "pop3_transport", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getReciveTransports();
			}}
		);
		JFormTabPanelResponsive tab = col.AddItemTabPanel();
		tab.setSizeColumns(8);
		tab.AddItemListOnDemand(380);
	}

	private JWins getSendTransports() throws Exception {
		JMap<String,String> m = JCollectionFactory.createOrderedMap();
		m.addElement("smtp", "smtp");
		m.addElement("smtps", "smtp SSL");
		m.addElement("smtpt", "smtp TLS");
		m.addElement("esmtp", "smtp (Exchange)");
		m.addElement("esmtps", "smtp SSL (Exchange)");
		m.addElement("esmtpt", "smtp TSL (Exchange)");
		return JWins.createVirtualWinsFromMap(m);
	}

	private JWins getReciveTransports() throws Exception {
		JMap<String,String> m = JCollectionFactory.createOrderedMap();
		m.addElement("pop3", "pop3");
		m.addElement("pop3s", "pop3 SSL");
		m.addElement("imap", "imap");
		m.addElement("imaps", "imap SSL");
		m.addElement("imapt", "imap TLS");
		m.addElement("eimap", "imap (Exchange)");
		m.addElement("eimaps", "imap SSL (Exchange)");
		m.addElement("eimapt", "imap TLS (Exchange)");
		return JWins.createVirtualWinsFromMap(m);
	}

//	private JWins getMailFormats() throws Exception {
//		JMap<String,String> m = JCollectionFactory.createOrderedMap();
//		m.addElement("text/plain", JLanguage.translate("Texto"));
//		m.addElement("text/html", "Html");
//		return JWins.createVirtualWinsFromMap(m);
//	}
} 
