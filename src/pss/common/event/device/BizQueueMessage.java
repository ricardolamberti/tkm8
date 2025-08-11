package pss.common.event.device;

import java.util.Date;

import pss.common.event.manager.BizEvent;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JImage;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizQueueMessage extends JRecord {

	public static final String TYPE_WARNING = "warning";
	public static final String TYPE_SUCCESS = "success";
	public static final String TYPE_DANGER = "danger";
	public static final String TYPE_INFO = "info";
	
	
	protected JLong pIdDevice = new JLong();
	protected JLong pIdQueue = new JLong();
	protected JDateTime pDate = new JDateTime();
	protected JString pCompany = new JString();
	protected JString pType = new JString();
	protected JString pTitle = new JString();
	protected JString pInfo = new JString();
	protected JImage pImage = new JImage();
	protected JBoolean pPermanent = new JBoolean();
	protected JString pLink = new JString();
	protected JBoolean pSended = new JBoolean();

	public void setIdDevice(Long zValue) throws Exception {
		pIdDevice.setValue(zValue);
	}
	public void setTitle(String zValue) throws Exception {
		pTitle.setValue(zValue);
	}
	public void setInfo(String zValue) throws Exception {
		pInfo.setValue(zValue);
	}
	public void setType(String zValue) throws Exception {
		pType.setValue(zValue);
	}
	public void setDate(Date zValue) throws Exception {
		pDate.setValue(zValue);
	}
	public long getIdDevice() throws Exception {
		return pIdDevice.getValue();
	}
	
	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}
	public void setPermanent(boolean zValue) throws Exception {
		pPermanent.setValue(zValue);
	}

	public void setSended(boolean zValue) throws Exception {
		pSended.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	
	public String getTitle() throws Exception {
		return pTitle.getValue();
	}

	public Date getDate() throws Exception {
		return pDate.getValue();
	}
	public String getType() throws Exception {
		return pType.getValue();
	}
	public String getInfo() throws Exception {
		return pInfo.getValue();
	}
	public String getImage() throws Exception {
		return pImage.getValue();
	}
	public boolean isPermanent() throws Exception {
		return pPermanent.getValue();
	}
	public boolean hasLink() throws Exception {
		return pLink.isNotNull();
	}
	public String getLink() throws Exception {
		return pLink.getValue();
	}
	/**
	 * Constructor de la Clase
	 */
	public BizQueueMessage() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_queue", pIdQueue);
		this.addItem("id_device", pIdDevice);
		this.addItem("company", pCompany);
		this.addItem("date", pDate);
		this.addItem("type", pType);// no implementado en celular
		this.addItem("title", pTitle);
		this.addItem("info", pInfo);
		this.addItem("image", pImage);// no implementado
		this.addItem("link", pLink);// no implementado en celular
		this.addItem("permanent", pPermanent);// no implementado en celular
		this.addItem("sended", pSended);
		}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_queue", "Id cola", false, false, 64);
		this.addFixedItem(FIELD, "id_device", "Id dispositivo", true, true, 64);
		this.addFixedItem(FIELD, "company", "company", true, true, 15);
		this.addFixedItem(FIELD, "date", "date", true, true, 24);
		this.addFixedItem(FIELD, "type", "type", true, true, 50);
		this.addFixedItem(FIELD, "title", "title", true, true, 400);
		this.addFixedItem(FIELD, "info", "info", true, false, 1000);
		this.addFixedItem(FIELD, "image", "image", true, false, 4000);
		this.addFixedItem(FIELD, "link", "link", true, false, 450);
		this.addFixedItem(FIELD, "permanent", "permanent", true, false, 1);
		this.addFixedItem(FIELD, "sended", "sended", true, false, 1);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "EVT_DEVICE_QUEUE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void processInsert() throws Exception {
		if (pSended.isNull())
			pSended.setValue(false);
		if (pPermanent.isNull())
			pPermanent.setValue(false);
		super.processInsert();
	}
	/**
	 * Default read() method
	 */
	public boolean read(long zIdQueue) throws Exception {
		addFilter("id_queue", zIdQueue);
		return read();
	}

	static BizQueueMessage buildSimpleMessage(String title,String info,String type, boolean permanent,String link) throws Exception {
		BizQueueMessage queue = new BizQueueMessage();
		queue.pTitle.setValue(title);
		queue.pInfo.setValue(info);
		queue.pDate.setValue(new Date());
		queue.pType.setValue(type);
		queue.pLink.setValue(link);
		queue.pPermanent.setValue(permanent);
		queue.pSended.setValue(false);
		return queue;
	}
	
	static BizQueueMessage buildFromEvent(BizEvent e) throws Exception {
		BizQueueMessage queue = new BizQueueMessage();
		queue.pTitle.setValue(e.getTitle());
		queue.pInfo.setValue(e.getInfo());
		queue.pDate.setValue(new Date());
		if (e.getEventLevel()==BizEvent.EVENT_INFO)
			queue.pType.setValue(TYPE_WARNING);
		else if (e.getEventLevel()==BizEvent.EVENT_URG)
			queue.pType.setValue(TYPE_DANGER);
		else if (e.getEventLevel()==BizEvent.EVENT_INFO)
			queue.pType.setValue(TYPE_INFO);
		else if (e.getEventLevel()==BizEvent.EVENT_SUCCESS)
			queue.pType.setValue(TYPE_SUCCESS);
		else
			queue.pType.setValue(TYPE_INFO);
			
		queue.pCompany.setValue(e.getCompany());
		queue.pSended.setValue(false);
		queue.pPermanent.setValue(false);
		return queue;
	}

	public void execProcessSended() throws Exception {
		JExec oExec = new JExec(this, "processSended") {

			@Override
			public void Do() throws Exception {
				processSended();
			}
		};
		oExec.execute();
	}
	public void processSended() throws Exception {
		pSended.setValue(true);
		processUpdate();
	}
}
