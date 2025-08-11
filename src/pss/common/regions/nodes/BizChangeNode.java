package  pss.common.regions.nodes;

import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class BizChangeNode extends JRecord {

	private JString pSourceCompany=new JString();
	private JString pSourceNode=new JString();
	private JString pTargetNode=new JString();

	// JList modules = null;
	private static JMap<String, TableToChange> tableList=null;

	public void setSourceCompany(String zVal) throws Exception {
		pSourceCompany.setValue(zVal);
	}

	public String getSourceCompany() throws Exception {
		return pSourceCompany.getValue();
	}

	public void setSourceNode(String zVal) throws Exception {
		pSourceNode.setValue(zVal);
	}

	public String getSourceNode() throws Exception {
		return pSourceNode.getValue();
	}

	public void setTargetNode(String zVal) throws Exception {
		pTargetNode.setValue(zVal);
	}

	public String getTargetNode() throws Exception {
		return pTargetNode.getValue();
	}

	public BizChangeNode() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("source_company", pSourceCompany);
		addItem("source_node", pSourceNode);
		addItem("target_node", pTargetNode);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "source_company", "Empresa Origen", true, true, 15);
		addFixedItem(FIELD, "source_node", "Nodo Origen", true, true, 15);
		addFixedItem(FIELD, "target_node", "Nodo Destino", true, true, 15);
	}

	@Override
	public String GetTable() throws Exception {
		return "";
	}

	public JList<String> getModuleList() throws Exception {
		JList<String> list=JCollectionFactory.createList();
		list.addElement("pss.core.NodosRemotos.BizChangeNode");
		return list;
	}

	@Override
	public void processInsert() throws Exception {
		BizNodo oSourceNode=new BizNodo();
		oSourceNode.dontThrowException(true);
		if (!oSourceNode.Read(pSourceCompany.getValue(), pSourceNode.getValue())) JExcepcion.SendError("No existe nodo origen");

		/*
		 * // Captura de tablas a modificar el nodo JIterator iter = this.getModuleList().getIterator(); while ( iter.hasMoreElements() ) { String module = (String) iter.nextElement(); JChangeNodeInterface interfase = this.createInterface(module); if ( interfase == null ) continue; interfase.addTablesToChangeNode(this); }
		 */
		// Proceso de cambio de nodo
		JIterator<TableToChange> cIter=BizChangeNode.getTablesToChangeNode().getValueIterator();
		while (cIter.hasMoreElements()) {
			TableToChange table=cIter.nextElement();
			this.changeNodeFromTable(table);
		}

	}

	/*
	 * private JChangeNodeInterface createInterface(String clase) throws Exception { try { return (JChangeNodeInterface) Class.forName(clase).newInstance(); } catch ( Exception e ) { return null; } }
	 */

	public static void addTable(String table, String pais, String nodo) throws Exception {
		if (tableList==null) tableList=JCollectionFactory.createMap();
		tableList.addElement(table.toUpperCase(), new TableToChange(table, pais, nodo));
	}

	private void changeNodeFromTable(TableToChange tableToChange) {
		try {
			if (tableToChange==null) return;
			String sql="update "+tableToChange.table+" set ";
			sql+=tableToChange.nodo+" = '"+pTargetNode.getValue()+"'";
			sql+=" where ";
			if (tableToChange.company!=null) {
				sql+=tableToChange.company+" = '"+pSourceCompany.getValue()+"' and ";
			}
			sql+=tableToChange.nodo+" = '"+pSourceNode.getValue()+"'";

			JBaseRegistro reg=JBaseRegistro.VirtualCreate();
			reg.Execute(sql);
			PssLogger.logDebug("Nodo cambiado en tabla: ^"+tableToChange.table);
		} catch (Exception e) {
			PssLogger.logDebug("Error cambiando clave en tabla: ^"+tableToChange.table+" - "+e.getMessage());
		}

	}

	public static boolean isOkNode(JRecord oBD, String zCompanyDestino, String zNodoDestino) throws Exception {
		TableToChange table=getTablesToChangeNode().getElement(oBD.GetTable().toUpperCase());
		if (table==null) return true;
		if (table.company!=null) {
			String company=oBD.getProp(table.company).toString();
			if (company!=null&&!company.equalsIgnoreCase(zCompanyDestino)) return false;
		}
		if (table.nodo!=null) {
			String nodo=oBD.getProp(table.nodo).toString();
			if (nodo!=null&&!nodo.equalsIgnoreCase(zNodoDestino)) return false;
		}
		return true;
	}

	public static synchronized JMap<String, TableToChange> getTablesToChangeNode() throws Exception {
		if (tableList!=null) return tableList;
		addTable("cie_config", "company", "nodo");
		addTable("cie_grupo", "company", "nodo");
		addTable("cie_grupo_detalle", "company", "nodo");
		addTable("cie_testigo", "company", "nodo");
		addTable("cie_turno", "company", "nodo");
		addTable("cie_turno_externo", "company", "nodo");
		addTable("dep_depuration", "company", "nodo");
		addTable("evt_eventos", "company", "nodo");
		addTable("evt_eventos_agrupados", "company", "nodo");
		addTable("mes_telemedidor", "company", "nodo");
		addTable("mpc_articulos_atos", "company", "nodo");
		addTable("mpc_articulos_atos_hist", "company", "nodo");
		addTable("mpc_config", "company", "nodo");
		addTable("rtl_mpc_config", "company", "nodo");
		addTable("mpc_transaccion", "company", "nodo");
		addTable("mpc_transaccion_detalle", "company", "nodo");
		addTable("nod_database", "company", "nodo");
		addTable("nod_node_flag", "country", "node");
		addTable("nod_nodo", "company", "nodo");
		addTable("nod_nodo_pc", "company", "nodo");
		addTable("nod_nodo_usuario", "company", "nodo");
		addTable("prt_cierres", "company", "nodo");
		addTable("prt_printer", "company", "nodo");
		addTable("pump_ajuste_medicion", "company", "nodo");
		addTable("pump_controler", "company", "nodo");
		addTable("pump_counters_by_close", "company", "nodo");
		addTable("pump_descarga", "company", "nodo");
		addTable("pump_despacho", "company", "nodo");
		addTable("pump_despacho_tanque", "company", "nodo");
		addTable("pump_manguera", "company", "nodo");
		addTable("pump_medicion_tanque", "company", "nodo");
		addTable("pump_preset_speedkeys", "company", "nodo");
		addTable("pump_surtidor", "company", "nodo");
		addTable("pump_tanque", "company", "nodo");
		addTable("rtl_automatic_pos", "company", "nodo");
		addTable("rtl_automatic_pos_pump", "company", "nodo");
		addTable("rtl_automatic_pos_usr_surtidor", "company", "nodo");
		addTable("rtl_caja", "company", "nodo");
		addTable("rtl_caja_cierre", "company", "nodo");
		addTable("rtl_caja_mov", "company", "nodo");
		addTable("rtl_caja_saldos", "company", "nodo");
		addTable("rtl_caja_saldos_hist", "company", "nodo");
		addTable("rtl_cashdrop", "company", "nodo");
		addTable("rtl_cliente", "company", "nodo");
		addTable("rtl_cuenta_mov", "company", "nodo");
		addTable("rtl_deposito", "company", "nodo");
		addTable("rtl_display_messages", "company", "nodo");
		addTable("rtl_emisor", "company", "nodo");
		addTable("rtl_emisor_tipo_comp", "company", "nodo");
		addTable("rtl_interfaz_cierre", "company", "nodo");
		addTable("rtl_interfaz_config", "company", "nodo");
		addTable("rtl_interfaz_config_expor", "company", "nodo");
		addTable("rtl_mapeo_producto", "company", "nodo");
		addTable("rtl_mid_range", "company", "nodo");
		addTable("rtl_numeracion", "company", "nodo");
		addTable("rtl_numeracion_talonario", "company", "nodo");
		addTable("rtl_pending_paidinout", "company", "nodo");
		addTable("rtl_pos", "company", "nodo");
		addTable("rtl_pos_impresora", "company", "nodo");
		addTable("rtl_pos_emisor", "company", "nodo");
		addTable("rtl_pump_config", "company", "nodo");
		addTable("rtl_pump_usuario", "company", "nodo");
		addTable("rtl_pumps_blocked", "company", "nodo");
		addTable("rtl_shop", "company", "nodo");
		addTable("rtl_sitemaster_func", "company", "nodo");
		addTable("rtl_sitemaster_func_header", "company", "nodo");
		addTable("rtl_stock_config", "company", "nodo");
		addTable("rtl_stock_deposito", "company", "nodo");
		addTable("rtl_stock_mov", "company", "nodo");
		addTable("rtl_stock_telemedidor", "company", "nodo");
		addTable("rtl_tarj_config", "company", "nodo");
		addTable("rtl_stock_mov", "company", "nodo");
		addTable("rtl_trans_adicionales", "company", "nodo");
		addTable("rtl_trans_det_eliminado", "company", "nodo");
		addTable("rtl_trans_detalle", "company", "nodo");
		addTable("rtl_trans_impuestos", "company", "nodo");
		addTable("rtl_trans_pagos", "company", "nodo");
		addTable("rtl_transaccion", "company", "nodo");
		addTable("rtl_trans_vinculo", "company", "nodo");
		addTable("rtl_trans_vinculo", "company", "nodo_vinc");
		addTable("rtl_turnos", "company", "nodo");
		addTable("rtl_windowpos", "company", "nodo");
		addTable("tcom_aplications", "company", "nodo");
		addTable("tcom_cfg", "company", "nodo");
		addTable("tcom_shortcuts", "company", "nodo");
		return tableList;
	}

	private static class TableToChange {

		public String table;
		public String company;
		public String nodo;

		public TableToChange(String zTable, String zCompany, String zNodo) {
			table=zTable;
			company=zCompany;
			nodo=zNodo;
		}

	}

}
