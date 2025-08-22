package pss.bsp.consolid.model.repoDK.detailContrato;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRepoDKDetailContrato extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRepoDKId = new JLong();
	private JLong pRepoDKDetailId = new JLong();
	private JString pContrato = new JString();
	private JString pCompany = new JString();
	private JFloat pComision = new JFloat();
	


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoDKDetailContrato() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", pRepoDKId);
		addItem("repo_dk_detail_id", pRepoDKDetailId);
		addItem("contrato", pContrato);
		addItem("company", pCompany);
		addItem("comision", pComision);
	}



	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "repo_dk_id", "Repo DK", true, false, 64);
		addFixedItem(FIELD, "repo_dk_detail_id", "Repo Detail DK", true, false, 64);
		addFixedItem(FIELD, "contrato", "Contrato", true, false, 20);
		addFixedItem(FIELD, "company", "Compañía", true, false, 15);
		addFixedItem(FIELD, "comision", "Comision", true, false, 18, 2);
		}



	@Override
	public String GetTable() {
		return "TUR_REPO_DK_DETAIL_CONTRATO";
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

	public void setContrato(String v) throws Exception { pContrato.setValue(v); }
	public String getContrato() throws Exception { return pContrato.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setComision(double v) throws Exception { pComision.setValue(v); }
	public double getComision() throws Exception { return pComision.getValue(); }
	public JFloat getComisionProp() throws Exception { return pComision; }

	public boolean readByContrato(String company,long repoId,long id,String contrato) throws Exception {
		addFilter("company", company);
		addFilter("repo_dk_id", repoId);
		addFilter("repo_dk_detail_id", id);
		addFilter("contrato", contrato);
		return read();
	}
}
