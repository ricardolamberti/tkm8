package pss.common.event.mailing;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiBSPPersona extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiBSPPersona() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizBSPPersona(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Lista de Mails"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormBSPPersona.class; }
  public String  getKeyField() throws Exception { return "codigo"; }
  public String  getDescripField() { return "descripcion_op"; }
  public BizBSPPersona GetcDato() throws Exception { return (BizBSPPersona) this.getRecord(); }

  public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.common.event.mailing.GuiBSPPersonas");

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
		rel.addFilter(" (BSP_PERSONA.company= 'COMPANY_CUSTOMLIST') ");
		
		
	}
 }
