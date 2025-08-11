package  pss.common.customList.config.owner;


import pss.common.customList.config.customlist.BizCustomList;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCustomListOwner extends JRecord   {



	
	private JString pCompany = new JString();
	private JLong pListId = new JLong();
	private JLong pSecuencia = new JLong();
	private JString pUsuario = new JString();
	private JString pDescripcion = new JString() {
		public void preset() throws Exception {
			pDescripcion.setValue(getDescripcion());
		};
	};

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setListId(long zValue) throws Exception {
		pListId.setValue(zValue);
	}

	public long getListId() throws Exception {
		return pListId.getValue();
	}
	
	public void setUsuario(String zValue) throws Exception {
		pUsuario.setValue(zValue);
	}
	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}

	public String getDescripcion() throws Exception {
		BizUsuario usr =getObjUsuario();
		if (usr==null) return "Desconocido";
		return usr.GetUsuario();
	}
	private BizUsuario objUsuario;
	public BizUsuario getObjUsuario() throws Exception {
		if (this.objUsuario != null) return this.objUsuario;
		BizUsuario r = new BizUsuario();
		r.dontThrowException(true);
		if (!r.Read( this.pUsuario.getValue())) return null;
		return (this.objUsuario = r);
	}
	private BizCustomList parentCustomList;
	public BizCustomList getObjParentCustomList() throws Exception {
		if (this.parentCustomList != null) return this.parentCustomList;
		BizCustomList r = new BizCustomList();
		r.read(this.pCompany.getValue(), this.pListId.getValue());
		return (this.parentCustomList = r);
	}
	private BizCustomList customList;
	public BizCustomList getObjCustomList() throws Exception {
		if (this.customList != null) return this.customList;
		BizCustomList r = new BizCustomList();
		r.read(this.pCompany.getValue(), this.pListId.getValue());
		return (this.customList = r);
	}
	/**
	 * Constructor de la Clase
	 */
	public BizCustomListOwner() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("list_id", pListId);
		this.addItem("secuencia", pSecuencia);
		this.addItem("usuario", pUsuario);
		this.addItem("descripcion", pDescripcion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "list_id", "List id", true, true, 64);
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
		this.addFixedItem(FIELD, "usuario", "Usuario", true, true, 50);
		this.addFixedItem(VIRTUAL, "Descripcion", "Descripcion", true, false, 200);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "LST_OWNERV2";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, long zListId, String zSec) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("secuencia", zSec);
		return read();
	}
	public boolean readByUsuario(String zCompany, long zListId, String zUsuario) throws Exception {
		addFilter("company", zCompany);
		addFilter("list_id", zListId);
		addFilter("usuario", zUsuario);
		return read();
	}
	public boolean checkOwner() throws Exception {
		BizCustomListOwner user = new BizCustomListOwner();
		user.dontThrowException(true);
		return user.readByUsuario(getCompany(), getListId(), BizUsuario.getUsr().GetUsuario());
	}
	public void addOwner() throws Exception {
		BizCustomListOwner user = new BizCustomListOwner();
		user.setCompany(getCompany());
		user.setListId(getListId());
		user.setUsuario(BizUsuario.getUsr().GetUsuario());
		user.insert();
	}

	public void processInsert() throws Exception {
		if (!checkOwner() && !BizUsuario.getUsr().GetUsuario().equals(getUsuario())) // nos aseguramos que el usuario quecede el derecho no lo pierda
			addOwner();
		super.processInsert();
		clean();
	}

	
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
		clean();
	}
	
}
