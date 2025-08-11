package pss.common.issueTracker.issue.attachment;

import pss.common.documentos.docElectronico.GuiDocElectronico;
import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.lists.JWinList;

public class GuiIssueAttachments extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueAttachments() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueAttachment.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 10079;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Adjuntos";
	}

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
		 this.addAction( 10, "Agregar Archivo", null, 10080, true, true );
	}
	
  @Override
  public boolean OkAction(BizAction a) throws Exception {
    	if ( a.getId()==10 ) return !this.GetVision().equals(BizIssueTrackerSetting.STATUS_CLOSED);
    	return super.OkAction(a);
  }

	
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActNew(this.getNewDocElect(), 4);
  	return super.getSubmitFor(a);
  }



	
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("description");
		zLista.AddColumnaLista("date_submitted");
		zLista.AddColumnaLista("descr_usuario");
	}

  
  public GuiDocElectronico getNewDocElect() throws Exception {
  	GuiDocElectronico c = new GuiDocElectronico();
  	if (this.getRecords().filterHasValue("company"))
  		c.GetcDato().setCompany(this.getRecords().getFilterValue("company"));
//  	c.GetcDato().setCompany(this.pos.getCompany());
  	c.setCanConvertToURL(false);
  	c.setDropListener(this.getNewCrmDoc());
  	return c;
  }
  
  public GuiIssueAttachment getNewCrmDoc() throws Exception {
  	GuiIssueAttachment c = new GuiIssueAttachment();
  	c.GetcDato().addFilter("company",this.getRecords().getFilterValue("company"));
  	c.GetcDato().addFilter("issue_id", this.getRecords().getFilterValue("issue_id"));
  	c.GetcDato().filtersToProp();
  	c.setCanConvertToURL(false);
  	return c;
  }

	
}
