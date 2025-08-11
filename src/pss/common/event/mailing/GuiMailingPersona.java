package  pss.common.event.mailing;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiMailingPersona extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMailingPersona() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMailingPersona(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Lista de Mails"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMailingPersona.class; }
  public String  getKeyField() throws Exception { return "codigo"; }
  public String  getDescripField() { return "descripcion_op"; }
  public BizMailingPersona GetcDato() throws Exception { return (BizMailingPersona) this.getRecord(); }

  public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.common.event.mailing.GuiBSPPersonas");

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
		rel.addFilter(" (BSP_PERSONA.company= 'COMPANY_CUSTOMLIST') ");
		
		
	}
 }
