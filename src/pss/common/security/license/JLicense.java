package pss.common.security.license;

import java.util.Date;

import pss.common.customMenu.BizMenu;
import pss.common.security.BizRol;
import pss.common.security.license.license.BizLicense;
import pss.common.security.license.license.detail.BizLicenseDetail;
import pss.common.security.license.typeLicense.detail.BizTypeLicenseDetail;


public class JLicense implements ILicense {
	BizLicense license;
	public void set(BizLicense lic) {
		license=lic;
	}
	protected BizLicense getLicense() {
		return license;
	}
	
	public String getFieldValue(String field) throws Exception{
		return license.getFieldValue(field);
	}
	public void reset(String field) throws Exception {
		license.reset(field);
	}

	public boolean request(String field) throws Exception{
		return license.request(field);
	}
	public void unRequest(String field) throws Exception{ 
		license.unRequest(field);
	}
	
	public BizMenu getCustomMenu() throws Exception{
		return license.getCustomMenu();
	}
	
	public BizRol getRol() throws Exception{
		return license.getRol();
	}


	public String getDescription() throws Exception {
		return license.getObjTypeLicense().getDescription();
	}


	public long getIdLicense() throws Exception {
		return license.getIdlicense();
	}


	public boolean test(String field) throws Exception {
		return license.test(field);
	}
	
	public Date getLastChange(String field) throws Exception {
		return license.getLastChange(field);
	}


	@Override
	public void addNewLicenseDetail(BizTypeLicenseDetail zDetail) throws Exception {
		BizLicenseDetail detail = new BizLicenseDetail();
		detail.setIdlicense(license.getIdlicense());
		detail.setField(zDetail.getField());
		detail.setValue(zDetail.getValue());
		detail.processInsert();
	}
	
	

}
