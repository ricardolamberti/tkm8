package pss.bsp.consolid.model.repoDK.detailOrg;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRepoDKDetailOrg extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRepoDKId = new JLong();
	private JLong pRepoDKDetailId = new JLong();
	private JString pOrg = new JString();
	private JString pCompany = new JString();
	private JFloat pBrutaDomestico = new JFloat();
	private JFloat pBrutaInternacional = new JFloat();
	private JFloat pNoDevDomestico = new JFloat();
	private JFloat pNoDevInternacional = new JFloat();
	private JFloat pNoDevReembolsos = new JFloat();
	private JFloat pUpFrontBspDomestico = new JFloat();
	private JFloat pUpFrontBspInternacional = new JFloat();



	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoDKDetailOrg() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", pRepoDKId);
		addItem("repo_dk_detail_id", pRepoDKDetailId);
		addItem("org", pOrg);
		addItem("company", pCompany);
		addItem("bruta_dom", pBrutaDomestico);
		addItem("bruta_int", pBrutaInternacional);
		addItem("nodev_dom", pNoDevDomestico);
		addItem("nodev_int", pNoDevInternacional);
		addItem("reemb", pNoDevReembolsos);
		addItem("up_front_bsp_domestico", pUpFrontBspDomestico);
		addItem("up_front_bsp_internacional", pUpFrontBspInternacional);
	}



	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "repo_dk_id", "Repo DK", true, false, 64);
		addFixedItem(FIELD, "repo_dk_detail_id", "Repo Detail DK", true, false, 64);
		addFixedItem(FIELD, "org", "Organization", true, false, 20);
		addFixedItem(FIELD, "company", "Compañía", true, false, 15);
		addFixedItem(FIELD, "bruta_dom", "Bruta Domestica", true, false, 18, 2);
		addFixedItem(FIELD, "bruta_int", "Bruta internacional", true, false, 18, 2);
		addFixedItem(FIELD, "nodev_dom", "No dev domestico", true, false, 18, 2);
		addFixedItem(FIELD, "nodev_int", "No dev internacional", true, false, 18, 2);
		addFixedItem(FIELD, "reemb", "Reembolso", true, false, 18, 2);
		addFixedItem(FIELD, "up_front_bsp_domestico", "UpFront BSP Dom.", true, false, 18, 2);
		addFixedItem(FIELD, "up_front_bsp_internacional", "UpFront BSP Int.", true, false, 18, 2);
	}



	@Override
	public String GetTable() {
		return "TUR_REPO_DK_DETAIL_ORG";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setRepoDKId(long v) throws Exception { pRepoDKId.setValue(v); }
	public long getRepoDKId() throws Exception { return pRepoDKId.getValue(); }

	public void setRepoDKDetailId(long v) throws Exception { pRepoDKDetailId.setValue(v); }
	public long getRepoDKDetailId() throws Exception { return pRepoDKDetailId.getValue(); }

	public void setOrg(String v) throws Exception { pOrg.setValue(v); }
	public String getOrg() throws Exception { return pOrg.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setBrutaDomestico(double v) throws Exception { pBrutaDomestico.setValue(v); }
	public double getBrutaDomestico() throws Exception { return pBrutaDomestico.getValue(); }
	public JFloat getBrutaDomesticoProp() throws Exception { return pBrutaDomestico; }

	public void setBrutaInternacional(double v) throws Exception { pBrutaInternacional.setValue(v); }
	public double getBrutaInternacional() throws Exception { return pBrutaInternacional.getValue(); }
	public JFloat getBrutaInternacionalProp() throws Exception { return pBrutaInternacional; }

	public void setNoDevDomestico(double v) throws Exception { pNoDevDomestico.setValue(v); }
	public double getNoDevDomestico() throws Exception { return pNoDevDomestico.getValue(); }
	public JFloat getNoDevDomesticoProp() throws Exception { return pNoDevDomestico ; }

	public void setNoDevInternacional(double v) throws Exception { pNoDevInternacional.setValue(v); }
	public double getNoDevInternacional() throws Exception { return pNoDevInternacional.getValue(); }
	public JFloat getNoDevInternacionalProp() throws Exception { return pNoDevInternacional ; }

	public void setNoDevReembolsos(double v) throws Exception { pNoDevReembolsos.setValue(v); }
	public double getNoDevReembolsos() throws Exception { return pNoDevReembolsos.getValue(); }
	public JFloat getNoDevReembolsosProp() throws Exception { return pNoDevReembolsos ; }

	public void setUpFrontBspDomestico(double v) throws Exception { pUpFrontBspDomestico.setValue(v); }
	public double getUpFrontBspDomestico() throws Exception { return pUpFrontBspDomestico.getValue(); }
	public JFloat getUpFrontBspDomesticoProp() throws Exception { return pUpFrontBspDomestico; }

	public void setUpFrontBspInternacional(double v) throws Exception { pUpFrontBspInternacional.setValue(v); }
	public double getUpFrontBspInternacional() throws Exception { return pUpFrontBspInternacional.getValue(); }
	public JFloat getUpFrontBspInternacionalProp() throws Exception { return pUpFrontBspInternacional; }

	public boolean readByOrg(String company,long repoId,long id,String org) throws Exception {
		addFilter("company", company);
		addFilter("repo_dk_id", repoId);
		addFilter("repo_dk_detail_id", id);
		addFilter("org", org);
		return read();
	}
}
