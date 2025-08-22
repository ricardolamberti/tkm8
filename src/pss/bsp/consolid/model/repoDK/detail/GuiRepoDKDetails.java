package pss.bsp.consolid.model.repoDK.detail;

import pss.bsp.consolid.model.repoDK.BizRepoDK;
import pss.core.services.fields.JFloat;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiRepoDKDetails extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoDKDetails() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoDKDetail.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 5052;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Reporte";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
	//	addActionNew(1, "Nuevo Registro");
	}

	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		
		JGrupoColumnaLista grupCliente = zLista.AddGrupoColumnaLista("Cliente");
		JGrupoColumnaLista grupVG = zLista.AddGrupoColumnaLista("Venta Global");
		JGrupoColumnaLista grupBoleto = zLista.AddGrupoColumnaLista("Boleto");
		zLista.AddColumnaLista("dk").setGrupo(grupCliente);
		zLista.AddColumnaLista("razon_social").setGrupo(grupCliente);
		zLista.AddColumnaLista("venta_global_neto").setGrupo(grupVG);
		zLista.AddColumnaLista("venta_global_total").setGrupo(grupVG);
		BizRepoDK obj = new BizRepoDK();
		if (this.getFilterValue("repo_dk_id")==null) return;
		obj.read(Long.parseLong(getFilterValue("repo_dk_id")));
		JIterator<String> itM =obj.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			JGrupoColumnaLista grupoMes = zLista.AddGrupoColumnaLista(mes);
			JGrupoColumnaLista grupoBoletoMes = zLista.AddGrupoColumnaLista("Boletos "+mes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_neto", "Venta BSP Neto",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_total", "Venta BSP Total",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_bajo_costo_neto", "Bajo Costo Neto",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_bajo_costo_total", "Bajo Costo total",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_emds", "EMDS",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_tkts", "TKTS",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_bajo_costo", "Bajo costo",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_total", "Total",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			
		}
		JIterator<String> it =obj.getOrganizaciones().getIterator();
		while (it.hasMoreElements()) {
			String org = it.nextElement();
			JGrupoColumnaLista grupoOrg = zLista.AddGrupoColumnaLista(org);
		//	public JProperty(String zTipo, String zCampo, String zDesc, JObject zDato, String zCampoMje, boolean zAutonumerico, boolean zRequerido, int zLongitud, int zPrecision, String zAtributo, String zDefault, String zTabla) {

			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_bruta_dom", "Bruto dom",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrg);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_bruta_int", "Bruto int",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrg);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_nodev_dom", "No dev dom",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrg);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_nodev_int", "No dev int",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrg);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_reemb", "Reembolsos",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrg);
			JGrupoColumnaLista grupoOrgUpfront = zLista.AddGrupoColumnaLista("UPFRONT "+org);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_up_front_bsp_domestico", "Upfront domestico",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrgUpfront);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,org+"_up_front_bsp_internacional", "Upfront internacionalsos",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoOrgUpfront);
		}
		JGrupoColumnaLista grupoContrato = zLista.AddGrupoColumnaLista("Contratos");

		JIterator<String> itA =obj.getContratos().getIterator();
		while (itA.hasMoreElements()) {
			String contrato = itA.nextElement();
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,contrato+"_comision", contrato,null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoContrato);
		}

		JGrupoColumnaLista grupoTot = zLista.AddGrupoColumnaLista("TOTAL");
		
		zLista.AddColumnaLista("total").setGrupo(grupoTot);
		zLista.AddColumnaLista("ttl_ingresos").setGrupo(grupoTot);
		}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("DK", "dk").setOperator("ilike");
	}

}
