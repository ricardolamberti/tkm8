package pss.common.event.device;

import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizSubscribeLink  extends JRecord {


	protected JLong pTypeDevice= new JLong();
	protected JString pUsuario = new JString();

	protected JString pLink = new JString();

	public void setTypeDevice(Long zValue) throws Exception {
		pTypeDevice.setValue(zValue);
	}

	public long getTypeDevice() throws Exception {
		return pTypeDevice.getValue();
	}
	public void setUsuario(String zValue) throws Exception {
		pUsuario.setValue(zValue);
	}
	public void setLink(String zValue) throws Exception {
		pLink.setValue(zValue);
	}

	public String getLink() throws Exception {
		return pLink.getValue();
	}
	public boolean hasLink() throws Exception {
		return pLink.isNotNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizSubscribeLink() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_typedevice", pTypeDevice);
		this.addItem("usuario", pUsuario);
		this.addItem("link", pLink);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "id_typedevice", "Id type device", true, true, 64);
		this.addFixedItem(FIELD, "usuario", "usuario", true, true, 15);
		this.addFixedItem(FIELD, "link", "pLink", true, true, 2000);
	}
	
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_typedevice", createControlCombo(GuiTypeDevices.class,"id_typedevice", null));
  	super.createControlProperties();
  }

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}

	public void execProcessAddChannel(long zType) throws Exception {
		JExec oExec = new JExec(this, "processAddChannel") {

			@Override
			public void Do() throws Exception {
			 processAddChannel(zType);
			}
		};
		oExec.execute();
	}

	transient BizUsuario objUsuario;
	public BizUsuario getObjUsuario() throws Exception {
		if (objUsuario!=null) return objUsuario;
		BizUsuario dev= new BizUsuario();
		dev.dontThrowException(true);
		if (!dev.Read(pUsuario.getValue()))
			return null;
		return objUsuario=dev;
	}
	
	public void processAddChannel( long idType) throws Exception {
		BizChannel channel = new BizChannel();
		channel.dontThrowException(true);
		if (!channel.readByUser(pUsuario.getValue(),idType)) {
			channel.setCompany(getObjUsuario().getCompany());
			channel.setUsuario(pUsuario.getValue());
			channel.processAddChannel(null, "otros", idType);
			
		}
		setLink(channel.getLinkSubscribe());
	}
	
	public String execProcessSubscribir() throws Exception {
		 execProcessAddChannel(getTypeDevice());
		 if (getLink()!=null)
			 return getLink();
		 throw new Exception("Fallo inscripción" );
	}
}
