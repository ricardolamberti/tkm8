package pss.bsp.consolid.model.repoCarrier.detail;

import pss.bsp.consolid.model.repoCarrier.BizRepoCarrier;
import pss.core.services.fields.JFloat;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiRepoCarrierDetails extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiRepoCarrierDetails() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiRepoCarrierDetail.class;
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
		zLista.AddColumnaLista("carrier").setGrupo(grupCliente);
		zLista.AddColumnaLista("cod_aerolinea").setGrupo(grupCliente);
		zLista.AddColumnaLista("aerolinea").setGrupo(grupCliente);
		zLista.AddColumnaLista("venta_global_neto").setGrupo(grupVG);
		zLista.AddColumnaLista("venta_global_total").setGrupo(grupVG);
		zLista.AddColumnaLista("reembolsos").setGrupo(grupVG);
		BizRepoCarrier obj = new BizRepoCarrier();
		if (this.getFilterValue("repo_dk_id")==null) return;
		obj.read(Long.parseLong(getFilterValue("repo_dk_id")));
		JIterator<String> itM =obj.getMeses().getIterator();
		while (itM.hasMoreElements()) {
			String mes = itM.nextElement();
			JGrupoColumnaLista grupoMes = zLista.AddGrupoColumnaLista(mes);
			JGrupoColumnaLista grupoBoletoMes = zLista.AddGrupoColumnaLista("Boletos "+mes);
			JGrupoColumnaLista grupoRFNDMes = zLista.AddGrupoColumnaLista("RFND/ADMA "+mes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_neto", "Venta BSP Neto",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_total", "Venta BSP Total",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_comision", "Comision",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_rfnd", "Venta BSP RFND",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoRFNDMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_venta_bsp_adma", "Venta BSP ADMA",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoRFNDMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_emds", "EMDS",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_tkts", "TKTS",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
			zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,mes+"_boletos_total", "Total",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoBoletoMes);
		
		}
	
		JGrupoColumnaLista grupoContrato = zLista.AddGrupoColumnaLista("Contratos");

		zLista.AddColumnaLista(JFloat.class,new JProperty(JRecord.VIRTUAL,"comision", "Comisión",null,"", true, false, 18, 2,null,null,null)).setGrupo(grupoContrato);
	
		JGrupoColumnaLista grupoTot = zLista.AddGrupoColumnaLista("TOTAL");
		
		//zLista.AddColumnaLista("total").setGrupo(grupoTot);
		zLista.AddColumnaLista("ttl_ingresos").setGrupo(grupoTot);
		}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Aerolinea", "carrier").setOperator("ilike");
	}

}
