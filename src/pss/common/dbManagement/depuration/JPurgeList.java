package  pss.common.dbManagement.depuration;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;

public class JPurgeList {

	public static final String SET_RETAIL="Retail";
	public static final String SET_EPAY="ePay";
	public static final String SET_ADHESION="Adhesion";
	public static final String SET_PUMP="Pump";
	public static final String SET_ESSOCARD="EssoCard";
	public static final String SET_INTERFAZ="Interfaz";
	public static final String SET_CORE="Core";

	public static final String SET_RETAIL_CLASS="pss.erp.JPurgeRetail";
	public static final String SET_EPAY_CLASS="Pss.ePay.JPurgeEPay";
	public static final String SET_ADHESION_CLASS="Pss.Adhesion.JPurgeAdhesion";
	public static final String SET_PUMP_CLASS="pss.fc.JPurgePump";
	public static final String SET_ESSOCARD_CLASS="Pss.MPC.JPurgeEssoCard";
	public static final String SET_INTERFAZ_CLASS="pss.core.Interfaces.JPurgeInterfaz";
	public static final String SET_CORE_CLASS="pss.core.Depuration.JPurgeCore";

	public JPurgeList() throws Exception {
	}

	protected JRecords<BizVirtual> getPurgeList() throws Exception {
		return null;
	}

	public static JRecords<BizVirtual> getPurgeList(String purgeSet) throws Exception {
		JPurgeList oPurgeList=virtualCreate(purgeSet);
		return oPurgeList.getPurgeList();
	}

	public static JRecords<BizVirtual> getPurgeSets() throws Exception {
		JRecords<BizVirtual> oBDs=JRecords.createVirtualBDs();
		if (JTools.isInstalled(SET_RETAIL_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_RETAIL, "Platino", 1));
		if (JTools.isInstalled(SET_EPAY_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_EPAY, "ePay", 1));
		if (JTools.isInstalled(SET_ADHESION_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_ADHESION, "Adhesion", 1));
		if (JTools.isInstalled(SET_PUMP_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_PUMP, "CEM", 1));
		if (JTools.isInstalled(SET_ESSOCARD_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_ESSOCARD, "Esso Card", 1));
		if (JTools.isInstalled(SET_INTERFAZ_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_INTERFAZ, "Interfaz", 1));
		if (JTools.isInstalled(SET_CORE_CLASS)) oBDs.addItem(JRecord.virtualBD(SET_CORE, "Core", 1));

		return oBDs;
	}

	public static JPurgeList virtualCreate(String purgeSet) throws Exception {
		if (purgeSet.equals(SET_RETAIL)) return (JPurgeList) Class.forName(SET_RETAIL_CLASS).newInstance();
		if (purgeSet.equals(SET_EPAY)) return (JPurgeList) Class.forName(SET_EPAY_CLASS).newInstance();
		if (purgeSet.equals(SET_ADHESION)) return (JPurgeList) Class.forName(SET_ADHESION_CLASS).newInstance();
		if (purgeSet.equals(SET_PUMP)) return (JPurgeList) Class.forName(SET_PUMP_CLASS).newInstance();
		if (purgeSet.equals(SET_ESSOCARD)) return (JPurgeList) Class.forName(SET_ESSOCARD_CLASS).newInstance();
		if (purgeSet.equals(SET_INTERFAZ)) return (JPurgeList) Class.forName(SET_INTERFAZ_CLASS).newInstance();
		if (purgeSet.equals(SET_CORE)) return (JPurgeList) Class.forName(SET_CORE_CLASS).newInstance();
		JExcepcion.SendError("El modulo de depuración "+purgeSet+" que quiere configurar, no esta implementado");
		return null;
	}

}
