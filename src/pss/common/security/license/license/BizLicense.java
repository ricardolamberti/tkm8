package pss.common.security.license.license;

import java.util.Date;

import pss.common.customMenu.BizMenu;
import pss.common.security.BizRol;
import pss.common.security.BizUsuario;
import pss.common.security.license.ILicense;
import pss.common.security.license.license.detail.BizLicenseDetail;
import pss.common.security.license.typeLicense.BizTypeLicense;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizLicense extends JRecord {

	private JLong pIdlicense=new JLong();
	private JString pIdtypelicense=new JString();
	private JString pCompany=new JString();
	private JString pClave=new JString();
	private JLong pCantidad=new JLong();
	private JString pDescripcion=new JString() {
		public void preset() throws Exception {
			pDescripcion.setValue(getDescripcion());
		};
	};

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setIdlicense(long zValue) throws Exception {
		pIdlicense.setValue(zValue);
	}

	public long getIdlicense() throws Exception {
		return pIdlicense.getValue();
	}
	public void setCantidad(long zValue) throws Exception {
		pCantidad.setValue(zValue);
	}
	public void setNullToCantidad() throws Exception {
		pCantidad.setNull();
	}
	public boolean isNullCantidad() throws Exception {
		return pCantidad.isNull();
	}
	public long getCantidad() throws Exception {
		return pCantidad.getValue();
	}

	public void setClave(String zValue) throws Exception {
		pClave.setValue(zValue);
	}

	public String getClave() throws Exception {
		return pClave.getValue();
	}

	public boolean isNullIdlicense() throws Exception {
		return pIdlicense.isNull();
	}

	public void setNullToIdlicense() throws Exception {
		pIdlicense.setNull();
	}

	public void setIdtypelicense(String zValue) throws Exception {
		pIdtypelicense.setValue(zValue);
	}

	public String getIdtypelicense() throws Exception {
		return pIdtypelicense.getValue();
	}

	public boolean isNullIdtypelicense() throws Exception {
		return pIdtypelicense.isNull();
	}

	public void setNullToIdtypelicense() throws Exception {
		pIdtypelicense.setNull();
	}

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	BizTypeLicense objTypeLicense;
	ILicense objLicense;

	public BizTypeLicense getObjTypeLicense() throws Exception {
		if (objTypeLicense!=null) return objTypeLicense;
		BizTypeLicense t=new BizTypeLicense();
		t.dontThrowException(true);
		if (!t.read(getCompany(), getIdtypelicense())) {
			t.clearFilters();
			t.read(BizUsuario.getUsr().getObjBusiness().getCompanyDefault(), this.getIdtypelicense());
				
		}
		return objTypeLicense=t;
	}

	public ILicense getLicense() throws Exception {
		if (objLicense!=null) return objLicense;
		objLicense=getObjTypeLicense().getNewLicense();
		objLicense.set(this);
		return objLicense;
	}
	
	public boolean isUpgradeable() throws Exception {
		return getObjTypeLicense().isUpgradeable();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizLicense() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("id_license", pIdlicense);
		this.addItem("company", pCompany);
		this.addItem("id_typeLicense", pIdtypelicense);
		this.addItem("clave", pClave);
		this.addItem("cantidad", pCantidad);
		this.addItem("descripcion", pDescripcion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id_license", "Id license", false, false, 64);
		this.addFixedItem(FIELD, "company", "Compania", true, true, 18);
		this.addFixedItem(FIELD, "id_typeLicense", "Id typelicense", true, true, 50);
		this.addFixedItem(FIELD, "clave", "clave", true, false, 200);
		this.addFixedItem(VIRTUAL, "cantidad", "cantidad", true, false, 18);
		this.addFixedItem(VIRTUAL, "descripcion", "descripcion", true, false, 400);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "SEG_LICENSE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void processInsert() throws Exception {
		if (!isNullCantidad()) {
			long cantidad = pCantidad.getValue();
			for(long i=0;i<cantidad;i++) {
				BizLicense lic = (BizLicense)this.createDefaultClone();
				lic.setNullToCantidad();
				lic.processInsert();
			}
			
		} else {
			super.processInsert();
			this.setClave(getObjTypeLicense().buildDetail(this));
			this.processUpdate();
		}
	}

	/**
	 * Default read() method
	 */
	public boolean read(long zIdlicense) throws Exception {
		addFilter("id_license", zIdlicense);
		return read();
	}

	public boolean test(String zField) throws Exception {
		long max=Long.parseLong(getFieldValue(zField));
		
		BizLicenseDetail detail=new BizLicenseDetail();
		detail.dontThrowException(true);
		if (detail.read(getIdlicense(), zField)) {
			if (max<=detail.getCount()+1) return false;
		} 

		return true;
	}


	public boolean reset(String zField) throws Exception {
		BizLicenseDetail detail=new BizLicenseDetail();
		detail.dontThrowException(true);
		if (detail.read(getIdlicense(), zField)) {
			detail.setCount(0);
			detail.processUpdate();
		}
		return true;
	}
	
	
	public boolean request(String zField) throws Exception {
		long max=Long.parseLong(getFieldValue(zField));
		BizLicenseDetail detail=new BizLicenseDetail();
		detail.dontThrowException(true);
		if (detail.read(getIdlicense(), zField)) {
			if (max<=detail.getCount()+1) return false;
			detail.setCount(detail.getCount()+1);
			detail.processUpdate();
		}
		return true;
	}

	public void unRequest(String zField) throws Exception {
		BizLicenseDetail detail=new BizLicenseDetail();
		detail.read(getIdlicense(), zField);
		detail.setCount(detail.getCount()-1);
		detail.processUpdate();
	}

	public BizMenu getCustomMenu() throws Exception {
		return getObjTypeLicense().getObjCustomMenu();
	}

	public BizRol getRol() throws Exception {
		return getObjTypeLicense().getObjRol();
	}


	public String getDescripcion() throws Exception {
		if (isNullIdtypelicense()) return "";
		return getObjTypeLicense().getDescription()+" ("+getClave().substring(0,15)+"...)";
	}

	
	public String getFieldValue(String zField) throws Exception {
		BizLicenseDetail detail = new BizLicenseDetail();
		detail.read(getIdlicense(), zField);
		return detail.getValue();
		
	
	}
	public Date getLastChange(String zField) throws Exception {
		BizLicenseDetail detail = new BizLicenseDetail();
		detail.read(getIdlicense(), zField);
		return detail.getLastChange();
		
	
	}
}
