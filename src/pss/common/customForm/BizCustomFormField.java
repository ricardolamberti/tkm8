package pss.common.customForm;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCustomFormField extends JRecord {

	private JString pCompany=new JString();
	private JInteger pSecuencia=new JInteger();
	private JString pCampo=new JString();
	private JBoolean pRequerido=new JBoolean();
	private JBoolean pReadOnly=new JBoolean();


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public int getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}

	public String getCampo() throws Exception {
		return pCampo.getValue();
	}

	public boolean isRequerido() throws Exception {
		return pRequerido.getValue();
	}
	
	public boolean isReadOnly() throws Exception {
		return pReadOnly.getValue();
	}

	private BizCustomForm form=null;
	public BizCustomForm getObjCustomForm() throws Exception {
		if (this.form!=null) return this.form;
		BizCustomForm f = new BizCustomForm();
		f.read(this.getCompany(), this.pSecuencia.getValue());
		return (this.form=f);
	}
	
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		BizCustomForm.findCustomForm(this.getCompany(), this.getObjCustomForm().getFormulario()).clearCampos();
	}

	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
		BizCustomForm.findCustomForm(this.getCompany(), this.getObjCustomForm().getFormulario()).clearCampos();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
		BizCustomForm.findCustomForm(this.getCompany(), this.getObjCustomForm().getFormulario()).clearCampos();
	}
	/**
	 * Constructor de la Clase
	 */
	public BizCustomFormField() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("secuencia", pSecuencia);
		this.addItem("campo", pCampo);
		this.addItem("requerido", pRequerido);
		this.addItem("read_only", pReadOnly);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "secuencia", "Secuencia", true, true, 200);
		this.addFixedItem(KEY, "campo", "Campo", true, true, 100);
		this.addFixedItem(FIELD, "requerido", "Requerido", true, true, 1);
		this.addFixedItem(FIELD, "read_only", "Read Only", true, true, 1);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "LST_CUSTOM_FORM_FIELD";
	}



}
