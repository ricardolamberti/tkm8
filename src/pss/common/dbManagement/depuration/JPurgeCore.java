package  pss.common.dbManagement.depuration;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class JPurgeCore extends JPurgeList {

  public JPurgeCore() throws Exception {
  }

  @Override
	protected JRecords<BizVirtual> getPurgeList() throws Exception {
    JRecords<BizVirtual> oBDs = JRecords.createVirtualBDs();
    oBDs.addItem(JRecord.virtualBD("pss.core.cierres.BizTurnoGrupo", "Turnos", 1));
    return oBDs;
  }

}

