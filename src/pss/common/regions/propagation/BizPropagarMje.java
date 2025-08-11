package  pss.common.regions.propagation;

import pss.common.regions.nodes.BizChangeNode;
import pss.common.regions.nodes.BizNodo;
import pss.common.regions.nodes.BizNodoDatabase;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizPropagarMje extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pNodo=new JString();
	JString pEnviarSlaves=new JString();
	JString pEnviarMaster=new JString();
	JString pMetodo=new JString();
	JString pOrigen=new JString();
	JString pRestringirNodo=new JString();

	JRecord ObjBD=null;

	public void SetPropagateRule(JSetupPropagate zValue) {
		oPropagateRule=zValue;
	}

	public void SetOrigen(String zValue) {
		pOrigen.setValue(zValue);
	}

	public void SetMetodo(String zValue) {
		pMetodo.setValue(zValue);
	}

	public void SetRestringirNodo(String zValue) {
		pRestringirNodo.setValue(zValue);
	}

	public void SetObjBD(JRecord zValue) {
		ObjBD=zValue;
	}

	private JSetupPropagate oPropagateRule=null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizPropagarMje() throws Exception {
		pEnviarSlaves.setValue("S");
		pEnviarMaster.setValue("S");
		pOrigen.setValue("");
		pRestringirNodo.setValue("");
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public void processInsert() throws Exception {
		try {
			BizUsuario.getUsr().getObjNodo();
		} catch (Exception e) {
			return;
		} // no hay nodo local
		if (!BizUsuario.getUsr().getObjNodo().ifPropagar()) return;
		return;
	}

	/*
	 * public void PropagarNodosSlaves() throws Exception { if ( oNodoLocal.ifSlave() ) return; JBDs oNodosEsclavos = new JBDs(BizNodoSlave.class); oNodosEsclavos.SetFiltros("nodo", oNodoLocal.pNodo.GetValor()); oNodosEsclavos.ReadAll(); JArray oEnum = oNodosEsclavos.GetItems(); while ( oEnum.HasMoreElements() ) { BizNodoSlave oNodoSlave = (BizNodoSlave) oEnum.NextElement(); if ( oNodoSlave.pNodo.GetValor().trim().equals(pOrigen.GetValor().trim()) && oNodoSlave.pSlave.GetValor().trim().equals(pSubOrigen.GetValor().trim())) continue; ObjBD.EnviarObjeto(oNodoSlave.ObtenerMjeCliente(), pMetodo.GetValor(), "S"); } }
	 */

	public void PropagarNodosHijos() throws Exception {
		if (!ObjBD.getSetupPropagate(oPropagateRule).isPropagateToChildren()) return;
		if (!BizUsuario.getUsr().getObjNodo().isConnectedToDBMaster()) return;

		JRecords<BizNodo> oNodosHijos=new JRecords<BizNodo>(BizNodo.class);
		oNodosHijos.addFilter("nodo_padre", BizUsuario.getUsr().getNodo());

		oNodosHijos.readAll();
		while (oNodosHijos.nextRecord()) {
			BizNodo oNodoHijo=oNodosHijos.getRecord();

			// No se reenvia al origen del mensaje
			if (oNodoHijo.GetNodo().trim().equals(pOrigen.getValue().trim())) continue;
			if (!ObjBD.getSetupPropagate(oPropagateRule).isPropagateToNode(oNodoHijo.GetNodo())) continue;
			// Utiliza el array cargado en BizChangeNode para determinar si el registro cambiado
			// corresponde ser enviado al este nodo
			if (!BizChangeNode.isOkNode(ObjBD, oNodoHijo.getCompany(), oNodoHijo.GetNodo())) continue;

			BizNodoDatabase oNodoDBMaster=oNodoHijo.getNodoDatabaseMaster();
		}
	}

}
