package  pss.common.layoutWysiwyg;

import pss.common.customList.config.customlist.BizCustomList;
import pss.core.services.records.JFilterMap;

public interface IPlantilla {
  public String generateListadoTemp(boolean impresion, BizCustomList docRelacionado,JFilterMap action,String company, String tipoDocLocal, boolean html ) throws Exception;
}
