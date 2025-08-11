package  pss.common.security.license;

import pss.common.customMenu.BizMenu;
import pss.common.security.BizRol;
import pss.common.security.license.license.BizLicense;
import pss.common.security.license.typeLicense.detail.BizTypeLicenseDetail;


public interface ILicense {

	public long getIdLicense() throws Exception;
	public String getDescription() throws Exception;
	public void set(BizLicense lic);
	public String getFieldValue(String field) throws Exception;
	public boolean test(String field) throws Exception;
	public void reset(String field) throws Exception;
	public boolean request(String field) throws Exception;
	public void unRequest(String field) throws Exception;
	public BizMenu getCustomMenu() throws Exception;
	public BizRol getRol() throws Exception;
	
	public void addNewLicenseDetail(BizTypeLicenseDetail detail) throws Exception;
}
