package pss.common.customForm;

import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizCustomForm extends JRecord {

	private JString pCompany=new JString();
	private JInteger pSecuencia=new JInteger();
	private JString pClaseWin=new JString();
	private JString pFormulario=new JString();

	private static JMap<String, BizCustomForm> hForms=null; 
	private JRecords<BizCustomFormField> campos=null; 

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

	public String getFormulario() throws Exception {
		return pFormulario.getValue();
	}

	public String getClaseWin() throws Exception {
		return pClaseWin.getValue();
	}
	
	/**
	 * Constructor de la Clase
	 */
	public BizCustomForm() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("secuencia", pSecuencia);
		this.addItem("clase_win", pClaseWin);
		this.addItem("formulario", pFormulario);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "secuencia", "Secuencia", true, true, 1);
		this.addFixedItem(FIELD, "clase_win", "Win", true, true, 200);
		this.addFixedItem(FIELD, "formulario", "Form", true, true, 200);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "LST_CUSTOM_FORM";
	}

	public static BizCustomForm findCustomFormMap(BizCustomForm form) throws Exception {
		return BizCustomForm.findCustomForm(form.getCompany(), form.getFormulario());
	}

	public static BizCustomForm findCustomForm(String company, String form) throws Exception {
		if (BizCustomForm.hForms==null) BizCustomForm.hForms=JCollectionFactory.createMap(); 
		String key=company+"-"+form;
		if (!hForms.containsKey(key)) {
			BizCustomForm cf = new BizCustomForm();
			cf.dontThrowException(true);
			if (!cf.readByForm(company, form)) {
				hForms.addElement(key, null);
			} else {
				hForms.addElement(key, cf);
			}
		}
		return hForms.getElement(key);
//		}
//		BizCustomForm cf = hForms.getElement(company+"-"+form);
//		if (cf!=null) return cf;
//		cf = new BizCustomForm();
//		cf.dontThrowException(true);
//		if (!cf.readByForm(company, form)) {
//			hForms.addElement(company+"-"+form, null);
//			return null;
//		}
//		hForms.addElement(company+"-"+form, cf);
	}

	public static synchronized void loadCustomFormMap() throws Exception {
		JMap<String, BizCustomForm> map = JCollectionFactory.createMap();
		JRecords<BizCustomForm> recs = new JRecords<BizCustomForm>(BizCustomForm.class);
		recs.readAll();
		JIterator<BizCustomForm> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCustomForm c = iter.nextElement();
			map.addElement(c.getCompany()+"-"+c.getFormulario(), c);
		}
		BizCustomForm.hForms=map;
	}
	
	public static void addCustomFormMap(BizCustomForm c) throws Exception {
		if (BizCustomForm.hForms==null) BizCustomForm.loadCustomFormMap(); 
		hForms.addElement(c.getCompany()+"-"+c.getFormulario(), c);
	}

	public static void removeCustomFormMap(BizCustomForm c) throws Exception {
		if (BizCustomForm.hForms==null) BizCustomForm.loadCustomFormMap(); 
		hForms.removeElement(c.getCompany()+"-"+c.getFormulario());
	}

	public JRecords<BizCustomFormField> getCampos() throws Exception {
		JRecords<BizCustomFormField> recs = new JRecords<BizCustomFormField>(BizCustomFormField.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("secuencia", this.getSecuencia());
		recs.readAll();
		return recs;
	}
	public JRecords<BizCustomFormField> getObjCampos() throws Exception {
		if (this.campos!=null) return this.campos;
		JRecords<BizCustomFormField> recs = this.getCampos();
		recs.convertToHash("campo");
		return (this.campos=recs);
	}
	
	@Override
	public void processInsert() throws Exception {
		if (this.pSecuencia.isNull()) {
			BizCustomForm max = new BizCustomForm();
			max.addFilter("company", this.pCompany.getValue());
			this.pSecuencia.setValue(max.SelectMaxInt("secuencia")+1);
		}
		super.processInsert();
		BizCustomForm.addCustomFormMap(this);
	}

	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
		BizCustomForm.addCustomFormMap(this);
	}

	@Override
	public void processDelete() throws Exception {
		this.getCampos().processDeleteAll();
		super.processDelete();
		BizCustomForm.removeCustomFormMap(this);
	}

	public void clearCampos() throws Exception {
		this.campos=null;
	}
//	public BizCustomFormField findCampo(String campo) throws Exception {
//		return (BizCustomFormField)this.getObjCampos().findInHash(campo);
//	}
//
//	public boolean isRequired(String campo) throws Exception {
//		return this.findCampo(campo).isRequerido();
//	}

	public boolean read(String company, int sec) throws Exception {
		this.addFilter("company", company);
		this.addFilter("secuencia", sec);
		return super.read();
	}
	
	public boolean readByForm(String company, String form) throws Exception {
		this.addFilter("company", company);
		this.addFilter("formulario", form);
		return super.read();
	}

}
