package  pss.common.customList.advanced;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.records.JRecord;

public class BizCustomListSQL extends JRecord {

  public BizCustomListSQL() throws Exception {
  }
  
  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.addRelationChild(1, "SQL", BizAdvancedSQL.class);
  }

}

