package pss.bsp.consolid.model.repoCarrier.detail;

import pss.bsp.consolid.model.repoCarrier.BizRepoCarrier;
import pss.bsp.consolid.model.repoCarrier.detailMonth.BizRepoCarrierDetailMonth;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.tourism.carrier.BizCarrier;

public class BizRepoCarrierDetail extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRepoCarrierId = new JLong();
	private JString pCompany = new JString();
	private JString pCarrier = new JString();
	private JString pCodAerolinea = new JString();
	private JString pAerolinea = new JString();
	private JFloat pVentaGlobalNeto = new JFloat();
	private JFloat pVentaGlobalTotal = new JFloat();
	private JFloat pReembolsos = new JFloat();
	private JFloat pTotal = new JFloat();
	private JFloat pTtlIngresos = new JFloat();
	private JFloat pComision = new JFloat();
	
	private JMap<String,BizRepoCarrierDetailMonth> pMeses = JCollectionFactory.createMap();
	


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoCarrierDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", pRepoCarrierId);
		addItem("company", pCompany);
		addItem("carrier", pCarrier);
		addItem("cod_aerolinea", pCodAerolinea);
		addItem("aerolinea", pAerolinea);
		addItem("venta_global_neto", pVentaGlobalNeto);
		addItem("venta_global_total", pVentaGlobalTotal);
		addItem("reembolsos", pReembolsos);
		addItem("total", pTotal);
		addItem("ttl_ingresos", pTtlIngresos);
		addItem("comision", pComision);
		BizRepoCarrier repo = getObjRepoCarrier();
		if (repo==null) return;
	
		JIterator<String> itM =repo.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			
			BizRepoCarrierDetailMonth objMonth = pMeses.getElement(mes);
			if (objMonth==null) {
				objMonth=new BizRepoCarrierDetailMonth();
				pMeses.addElement(mes, objMonth);
			}
			addItem(mes+"_venta_bsp_neto", objMonth.getVentaBspNetoProp());
			addItem(mes+"_venta_bsp_total", objMonth.getVentaBspTotalProp());
			addItem(mes+"_venta_bsp_rfnd", objMonth.getVentaBspRfndProp());
			addItem(mes+"_venta_bsp_adma", objMonth.getVentaBspAdmaProp());
			addItem(mes+"_boletos_emds", objMonth.getBoletosEmdsProp());
			addItem(mes+"_boletos_tkts", objMonth.getBoletosTktsProp());
			addItem(mes+"_boletos_total", objMonth.getBoletosTotalProp());
			addItem(mes+"_comision", objMonth.getComisionProp());
			
			
		}
	}


	BizRepoCarrier objRepo;

	public BizRepoCarrier getObjRepoCarrier() throws Exception {
		if (objRepo!=null) return objRepo;
		BizRepoCarrier obj = new BizRepoCarrier();
		obj.dontThrowException(true);
		if (this.GetVision().equals("")) return null;
		if (!obj.read(Long.parseLong(this.GetVision()))) return null;
		return objRepo=obj;
	}
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "repo_dk_id", "Repo DK", true, false, 64);
		addFixedItem(FIELD, "company", "Compañía", true, false, 15);
		addFixedItem(FIELD, "carrier", "Carrier", true, false, 50);
		addFixedItem(FIELD, "cod_aerolinea", "Cod aerolinea", true, false, 50);
		addFixedItem(FIELD, "Aerolinea", "Aerolinea", true, false, 100);
		addFixedItem(FIELD, "venta_global_neto", "Venta Global Neto", true, false, 18, 2);
		addFixedItem(FIELD, "venta_global_total", "Venta Global Total", true, false, 18, 2);
		addFixedItem(FIELD, "reembolsos", "Reembolsos", true, false, 18, 2);
		addFixedItem(FIELD, "total", "Total", true, false, 18, 2);
		addFixedItem(FIELD, "ttl_ingresos", "Total Ingresos", true, false, 18, 2);
		addFixedItem(FIELD, "comision", "Comisión", true, false, 18, 2);
//		BizRepoCarrier repo = getObjRepoCarrier();
//		if (repo==null) return;
//		JIterator<String> it =repo.getOrganizaciones().getIterator();
//		while (it.hasMoreElements()) {
//			String org = it.nextElement();
//			addFixedItem(VIRTUAL, org+"_bruta_dom", "Bruto dom", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_bruta_int", "Bruto int", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_nodev_dom", "No dev dom", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_nodev_int", "No dev int", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_reemb", "Reemb", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_up_front_bsp_domestico", "Upfront dom", true, false, 18, 2);
//			addFixedItem(VIRTUAL, org+"_up_front_bsp_internacional", "Upfron int", true, false, 18, 2);
//			
//		}	
		}



	@Override
	public String GetTable() {
		return "TUR_REPO_CARRIER_DETAIL";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setRepoCarrierId(long v) throws Exception { pRepoCarrierId.setValue(v); }
	public long getRepoCarrierId() throws Exception { return pRepoCarrierId.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setCarrier(String v) throws Exception { pCarrier.setValue(v); }
	public String getCarrier() throws Exception { return pCarrier.getValue(); }

	public void setCodAerolinea(String v) throws Exception { pCodAerolinea.setValue(v); }
	public String getCodAerolinea() throws Exception { return pCodAerolinea.getValue(); }

	public void setAerolinea(String v) throws Exception { pAerolinea.setValue(v); }
	public String getAerolinea() throws Exception { return pAerolinea.getValue(); }


	public void setVentaGlobalNeto(double v) throws Exception { pVentaGlobalNeto.setValue(v); }
	public double getVentaGlobalNeto() throws Exception { return pVentaGlobalNeto.getValue(); }

	public void setVentaGlobalTotal(double v) throws Exception { pVentaGlobalTotal.setValue(v); }
	public double getVentaGlobalTotal() throws Exception { return pVentaGlobalTotal.getValue(); }
	public void setReembolsos(double v) throws Exception { pReembolsos.setValue(v); }
	public double getReembolsos() throws Exception { return pReembolsos.getValue(); }

	public void setTotal(double v) throws Exception { pTotal.setValue(v); }
	public double getTotal() throws Exception { return pTotal.getValue(); }
	public void setComision(double v) throws Exception { pComision.setValue(v); }
	public double getComision() throws Exception { return pComision.getValue(); }

	public void setTtlIngresos(double v) throws Exception { pTtlIngresos.setValue(v); }
	public double getTtlIngresos() throws Exception { return pTtlIngresos.getValue(); }
	BizCarrier objCarrier;
	public BizCarrier getObjCarrier() throws Exception {
		if (objCarrier!=null) return objCarrier;
		BizCarrier o = new BizCarrier();
		o.dontThrowException(true);
		if (!o.Read(getCarrier())) {
			return null;
		}
		return objCarrier=o;
	}
	
	
	@Override
	public void Read(JBaseRegistro zSet, boolean withForeign) throws Exception {
		super.Read(zSet,withForeign);
		BizRepoCarrier repo = getObjRepoCarrier();
		if (repo==null) return;

		JIterator<String> itM =repo.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			BizRepoCarrierDetailMonth objMes = new BizRepoCarrierDetailMonth();
			objMes.dontThrowException(true);
			if (!objMes.readByMes(getCompany(),getRepoCarrierId(),getId(),mes)) continue;
			if (this.getProp(mes+"_venta_bsp_neto",false)==null) cleanProperties();
			this.getProp(mes+"_venta_bsp_neto").setValue(objMes.getVentaBspNeto());
			this.getProp(mes+"_venta_bsp_total").setValue(objMes.getVentaBspTotal());
			this.getProp(mes+"_venta_bsp_rfnd").setValue(objMes.getVentaBspRfnd());
			this.getProp(mes+"_venta_bsp_adma").setValue(objMes.getVentaBspAdma());
			this.getProp(mes+"_boletos_emds").setValue(objMes.getBoletosEmds());
			this.getProp(mes+"_boletos_tkts").setValue(objMes.getBoletosTkts());
			this.getProp(mes+"_boletos_total").setValue(objMes.getBoletosTotal());
			this.getProp(mes+"_comision").setValue(objMes.getComision());
			
		}
		return ;
	}

}
