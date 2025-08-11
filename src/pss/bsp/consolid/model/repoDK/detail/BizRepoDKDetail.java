package pss.bsp.consolid.model.repoDK.detail;

import pss.bsp.consolid.model.repoDK.BizRepoDK;
import pss.bsp.consolid.model.repoDK.detailContrato.BizRepoDKDetailContrato;
import pss.bsp.consolid.model.repoDK.detailMonth.BizRepoDKDetailMonth;
import pss.bsp.consolid.model.repoDK.detailOrg.BizRepoDKDetailOrg;
import pss.bsp.dk.BizClienteDK;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizRepoDKDetail extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRepoDKId = new JLong();
	private JString pCompany = new JString();
	private JString pDk = new JString();
	private JString pDkAnterior = new JString();
	private JString pTipoEmision = new JString();
	private JString pRazonSocial = new JString();
	private JString pNombreComercial = new JString();
	private JString pUbicacion = new JString();
	private JString pTipoGds = new JString();
	private JString pLineaNegocio = new JString();
	private JFloat pVentaGlobalNeto = new JFloat();
	private JFloat pVentaGlobalTotal = new JFloat();
	private JFloat pTotal = new JFloat();
	private JFloat pTtlIngresos = new JFloat();
	
	private JMap<String,BizRepoDKDetailOrg> pOrgs = JCollectionFactory.createMap();
	private JMap<String,BizRepoDKDetailContrato> pContrs = JCollectionFactory.createMap();
	private JMap<String,BizRepoDKDetailMonth> pMeses = JCollectionFactory.createMap();
	


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoDKDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", pRepoDKId);
		addItem("company", pCompany);
		addItem("dk", pDk);
		addItem("dk_anterior", pDkAnterior);
		addItem("tipo_emision", pTipoEmision);
		addItem("razon_social", pRazonSocial);
		addItem("nombre_comercial", pNombreComercial);
		addItem("ubicacion", pUbicacion);
		addItem("tipo_gds", pTipoGds);
		addItem("linea_negocio", pLineaNegocio);
		addItem("venta_global_neto", pVentaGlobalNeto);
		addItem("venta_global_total", pVentaGlobalTotal);
		addItem("total", pTotal);
		addItem("ttl_ingresos", pTtlIngresos);
		BizRepoDK repo = getObjRepoDK();
		if (repo==null) return;
		JIterator<String> it =repo.getOrganizaciones().getIterator();
		while (it.hasMoreElements()) {
			String org = it.nextElement();
			
			BizRepoDKDetailOrg objOrg = pOrgs.getElement(org);
			if (objOrg==null) {
				objOrg=new BizRepoDKDetailOrg();
				pOrgs.addElement(org, objOrg);
			}
			addItem(org+"_bruta_dom", objOrg.getBrutaDomesticoProp());
			addItem(org+"_bruta_int", objOrg.getBrutaInternacionalProp());
			addItem(org+"_nodev_dom", objOrg.getNoDevDomesticoProp());
			addItem(org+"_nodev_int", objOrg.getNoDevInternacionalProp());
			addItem(org+"_reemb", objOrg.getNoDevReembolsosProp());
			addItem(org+"_up_front_bsp_domestico", objOrg.getUpFrontBspDomesticoProp());
			addItem(org+"_up_front_bsp_internacional", objOrg.getUpFrontBspInternacionalProp());

		}
		JIterator<String> itA =repo.getContratos().getIterator();
		while (itA.hasMoreElements()) {
			String contr = itA.nextElement();
			
			BizRepoDKDetailContrato objContr = pContrs.getElement(contr);
			if (objContr==null) {
				objContr=new BizRepoDKDetailContrato();
				pContrs.addElement(contr, objContr);
			}
			addItem(contr+"_comision", objContr.getComisionProp());
			
		}
		JIterator<String> itM =repo.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			
			BizRepoDKDetailMonth objMonth = pMeses.getElement(mes);
			if (objMonth==null) {
				objMonth=new BizRepoDKDetailMonth();
				pMeses.addElement(mes, objMonth);
			}
			addItem(mes+"_venta_bsp_neto", objMonth.getVentaBspNetoProp());
			addItem(mes+"_venta_bsp_total", objMonth.getVentaBspTotalProp());
			addItem(mes+"_bajo_costo_neto", objMonth.getBajoCostoNetoProp());
			addItem(mes+"_bajo_costo_total", objMonth.getBajoCostoTotalProp());
			addItem(mes+"_boletos_emds", objMonth.getBoletosEmdsProp());
			addItem(mes+"_boletos_tkts", objMonth.getBoletosTktsProp());
			addItem(mes+"_boletos_bajo_costo", objMonth.getBoletosBajoCostoProp());
			addItem(mes+"_boletos_total", objMonth.getBoletosTotalProp());
			
		
		}
	}


	BizRepoDK objRepo;

	public BizRepoDK getObjRepoDK() throws Exception {
		if (objRepo!=null) return objRepo;
		BizRepoDK obj = new BizRepoDK();
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
		addFixedItem(FIELD, "dk", "DK", true, false, 20);
		addFixedItem(FIELD, "dk_anterior", "DK Anterior", true, false, 20);
		addFixedItem(FIELD, "tipo_emision", "Tipo Emisión", true, false, 50);
		addFixedItem(FIELD, "razon_social", "Razón Social", true, false, 100);
		addFixedItem(FIELD, "nombre_comercial", "Nombre Comercial", true, false, 100);
		addFixedItem(FIELD, "ubicacion", "Ubicación", true, false, 50);
		addFixedItem(FIELD, "tipo_gds", "Tipo GDS", true, false, 30);
		addFixedItem(FIELD, "linea_negocio", "Línea de Negocio", true, false, 50);
		addFixedItem(FIELD, "venta_global_neto", "Venta Global Neto", true, false, 18, 2);
		addFixedItem(FIELD, "venta_global_total", "Venta Global Total", true, false, 18, 2);
		addFixedItem(FIELD, "total", "Total", true, false, 18, 2);
		addFixedItem(FIELD, "ttl_ingresos", "Total Ingresos", true, false, 18, 2);
//		BizRepoDK repo = getObjRepoDK();
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
		return "TUR_REPO_DK_DETAIL";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setRepoDKId(long v) throws Exception { pRepoDKId.setValue(v); }
	public long getRepoDKId() throws Exception { return pRepoDKId.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setDk(String v) throws Exception { pDk.setValue(v); }
	public String getDk() throws Exception { return pDk.getValue(); }

	public void setDkAnterior(String v) throws Exception { pDkAnterior.setValue(v); }
	public String getDkAnterior() throws Exception { return pDkAnterior.getValue(); }

	public void setTipoEmision(String v) throws Exception { pTipoEmision.setValue(v); }
	public String getTipoEmision() throws Exception { return pTipoEmision.getValue(); }

	public void setRazonSocial(String v) throws Exception { pRazonSocial.setValue(v); }
	public String getRazonSocial() throws Exception { return pRazonSocial.getValue(); }

	public void setNombreComercial(String v) throws Exception { pNombreComercial.setValue(v); }
	public String getNombreComercial() throws Exception { return pNombreComercial.getValue(); }

	public void setUbicacion(String v) throws Exception { pUbicacion.setValue(v); }
	public String getUbicacion() throws Exception { return pUbicacion.getValue(); }

	public void setTipoGds(String v) throws Exception { pTipoGds.setValue(v); }
	public String getTipoGds() throws Exception { return pTipoGds.getValue(); }

	public void setLineaNegocio(String v) throws Exception { pLineaNegocio.setValue(v); }
	public String getLineaNegocio() throws Exception { return pLineaNegocio.getValue(); }

	public void setVentaGlobalNeto(double v) throws Exception { pVentaGlobalNeto.setValue(v); }
	public double getVentaGlobalNeto() throws Exception { return pVentaGlobalNeto.getValue(); }

	public void setVentaGlobalTotal(double v) throws Exception { pVentaGlobalTotal.setValue(v); }
	public double getVentaGlobalTotal() throws Exception { return pVentaGlobalTotal.getValue(); }

	public void setTotal(double v) throws Exception { pTotal.setValue(v); }
	public double getTotal() throws Exception { return pTotal.getValue(); }

	public void setTtlIngresos(double v) throws Exception { pTtlIngresos.setValue(v); }
	public double getTtlIngresos() throws Exception { return pTtlIngresos.getValue(); }
	BizClienteDK objDK;
	public BizClienteDK getObjClienteDK() throws Exception {
		if (objDK!=null) return objDK;
		BizClienteDK o = new BizClienteDK();
		o.dontThrowException(true);
		if (!o.ReadByDK(getCompany(),getDk())) {
			return null;
		}
		return objDK=o;
	}
	
	
	@Override
	public void Read(JBaseRegistro zSet, boolean withForeign) throws Exception {
		super.Read(zSet,withForeign);
		BizRepoDK repo = getObjRepoDK();
		if (repo==null) return;
		JIterator<String> it =repo.getOrganizaciones().getIterator();
		while (it.hasMoreElements()) {
			String org = it.nextElement();
			BizRepoDKDetailOrg objOrg = new BizRepoDKDetailOrg();
			objOrg.dontThrowException(true);
			if (!objOrg.readByOrg(getCompany(),getRepoDKId(),getId(),org)) continue;
			if (this.getProp(org+"_bruta_dom",false)==null) cleanProperties();
			this.getProp(org+"_bruta_dom").setValue(objOrg.getBrutaDomestico());
			this.getProp(org+"_bruta_int").setValue(objOrg.getBrutaInternacional());
			this.getProp(org+"_nodev_dom").setValue(objOrg.getNoDevDomestico());
			this.getProp(org+"_nodev_int").setValue(objOrg.getNoDevInternacional());
			this.getProp(org+"_reemb").setValue(objOrg.getNoDevReembolsos());
			this.getProp(org+"_up_front_bsp_domestico").setValue(objOrg.getUpFrontBspDomestico());
			this.getProp(org+"_up_front_bsp_internacional").setValue(objOrg.getUpFrontBspInternacional());
		
		}
		JIterator<String> itC =repo.getContratos().getIterator();
		while (itC.hasMoreElements()) {
			String contr = itC.nextElement();
			BizRepoDKDetailContrato objContr = new BizRepoDKDetailContrato();
			objContr.dontThrowException(true);
			if (!objContr.readByContrato(getCompany(),getRepoDKId(),getId(),contr)) continue;
			if (this.getProp(contr+"_comision",false)==null) cleanProperties();
			this.getProp(contr+"_comision").setValue(objContr.getComision());
			
		}
		JIterator<String> itM =repo.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			BizRepoDKDetailMonth objMes = new BizRepoDKDetailMonth();
			objMes.dontThrowException(true);
			if (!objMes.readByMes(getCompany(),getRepoDKId(),getId(),mes)) continue;
			if (this.getProp(mes+"_venta_bsp_neto",false)==null) cleanProperties();
			this.getProp(mes+"_venta_bsp_neto").setValue(objMes.getVentaBspNeto());
			this.getProp(mes+"_venta_bsp_total").setValue(objMes.getVentaBspTotal());
			this.getProp(mes+"_bajo_costo_neto").setValue(objMes.getBajoCostoNeto());
			this.getProp(mes+"_bajo_costo_total").setValue(objMes.getBajoCostoTotal());
			this.getProp(mes+"_boletos_emds").setValue(objMes.getBoletosEmds());
			this.getProp(mes+"_boletos_tkts").setValue(objMes.getBoletosTkts());
			this.getProp(mes+"_boletos_bajo_costo").setValue(objMes.getBoletosBajoCosto());
			this.getProp(mes+"_boletos_total").setValue(objMes.getBoletosTotal());
			
		}
		return ;
	}

}
