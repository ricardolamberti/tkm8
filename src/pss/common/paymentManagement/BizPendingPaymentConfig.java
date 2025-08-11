package  pss.common.paymentManagement;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPendingPaymentConfig extends JRecord {

	private JString pCompany = new JString();

	private JLong daysToMessage = new JLong();
	private JLong messageLapse = new JLong();
	private JLong daysToAlert = new JLong();
	private JLong alertLapse = new JLong();
	private JLong daysToDisconnect = new JLong();
	private JString title = new JString();
	private JString pTextMessage = new JString();
	private JString pAlertMessage = new JString();
	private JString pDisconnectMessage = new JString();

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setTextMessage(String zValue) throws Exception {
		pTextMessage.setValue(zValue);
	}

	public String getTextMessage() throws Exception {
		return pTextMessage.getValue();
	}
	
	public void setTitle(String zValue) throws Exception {
		title.setValue(zValue);
	}

	public String getTitle() throws Exception {
		return title.getValue();
	}

	public long getDaysToMessage() throws Exception {
		return daysToMessage.getValue();
	}
	public long getMessageLapse() throws Exception {
		return messageLapse.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizPendingPaymentConfig() throws Exception {

	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("days_to_message", daysToMessage);
		this.addItem("message_lapse", messageLapse);
		this.addItem("days_to_alert", daysToAlert);
		this.addItem("alert_lapse", alertLapse);
		this.addItem("days_to_disconnect", daysToDisconnect);
		this.addItem("title", title);
		this.addItem("text_message", pTextMessage);
		this.addItem("alert_message", pAlertMessage);
		this.addItem("disconnect_message", pDisconnectMessage);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 50);
		this.addFixedItem(FIELD, "days_to_message", "days_to_message", true,
				false, 200);
		this.addFixedItem(FIELD, "message_lapse", "message_lapse", true, false,
				200);
		this.addFixedItem(FIELD, "days_to_alert", "days_to_alert", true, false,
				200);
		this.addFixedItem(FIELD, "alert_lapse", "alert_lapse", true, false, 200);
		this.addFixedItem(FIELD, "days_to_disconnect", "days_to_disconnect",
				true, false, 200);
		this.addFixedItem(FIELD, "title", "titulo", true, false,
				200);
		this.addFixedItem(FIELD, "text_message", "text_message", true, false,
				200);
		this.addFixedItem(FIELD, "alert_message", "alert_message", true, false,
				200);
		this.addFixedItem(FIELD, "disconnect_message", "disconnect_message",
				true, false, 200);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "PAY_PENDING_CONFIG";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean read(String company) throws Exception {
		clearFilters();
		addFilter("company", company);
		return read();
	}
}
