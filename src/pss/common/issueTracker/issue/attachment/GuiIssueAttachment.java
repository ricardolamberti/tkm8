package pss.common.issueTracker.issue.attachment;

import pss.common.documentos.GuiDocum;
import pss.common.documentos.GuiDocumento;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiIssueAttachment extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiIssueAttachment() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizIssueAttachment(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10079; }
  @Override
	public String GetTitle()       throws Exception { return "Adjunto"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormIssueAttachment.class; }
  @Override
	public String getKeyField()   throws Exception { return "id_doc"; }
  @Override
	public String getDescripField()                  { return "description"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  	this.createActionDelete();
    this.addAction(20, "Ver Documento", null, 117, true, true).setNuevaVentana(true);
  }
  
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==20) return this.getDocumento().findActionQuery();
  	return super.getSubmitFor(a);
  }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizIssueAttachment GetcDato() throws Exception {
    return (BizIssueAttachment) this.getRecord();
  }

  
  @Override
  public JAct Drop(JBaseWin baseWin) throws Exception {
  	if (baseWin instanceof GuiDocumento) {
	  	GuiDocumento doc = (GuiDocumento) baseWin;
	  	this.GetcDato().setObjDocumento(doc.GetcDato());
	  	this.GetcDato().execProcessInsert();
	  	doc.setDropListener(null);
	  	return null;
  	}  	
//  	
  	return super.Drop(baseWin);
  }

  
  public GuiDocumento getDocumento() throws Exception {
  	return this.getDocum().getDocumento();
  }
  
  public GuiDocum getDocum() throws Exception {
  	GuiDocum d = new GuiDocum();
  	d.setRecord(this.GetcDato().getObjDocum());
  	return d;
  }
  
  

}
