package pss.common.issueTracker.issue.attachment;

import java.util.Date;

import pss.common.documentos.BizDocum;
import pss.common.documentos.BizDocumento;
import pss.common.issueTracker.issue.BizIssue;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizIssueAttachment extends JRecord {
	
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//

	private JString pCompany = new JString();
	private JLong pIssueId = new JLong();
	private JLong pNoteId = new JLong();
	private JString pDescription = new JString(){
		public void preset() throws Exception {
			pDescription.setValue( getObjDocum().getTitulo());
		}
	};
	
	private JDateTime pDateSubmitted = new JDateTime();
	
	private JString pUsuario = new JString();
	private JString pUsuarioDescr = new JString(){
		public void preset() throws Exception {
			pUsuarioDescr.setValue(getDescrUsuario());
		}
	};
	private JString pHandlerUser = new JString();
  private JLong pIdDoc = new JLong();

	public String getCompany() throws Exception { return pCompany.getValue();	}
	public long getIssueId() throws Exception{	return pIssueId.getValue();	}
	public long getNoteId() throws Exception{	return pNoteId.getValue();	}
	public String getDescription() throws Exception {	return pDescription.getValue();	}
	public Date getDateSubmitted() throws Exception{	return pDateSubmitted.getValue();	}
	public String getUsuario() throws Exception{	return pUsuario.getValue();}
	public String getHandlerUser() throws Exception{	return pHandlerUser.getValue();}
  public long getIdDoc() throws Exception {
  	return this.pIdDoc.getValue();
  }
	
	public void setCompany(String value) throws Exception { this.pCompany.setValue(value);	}
	public void setIssueId(long value) throws Exception{	this.pIssueId.setValue(value);}
	public void setNoteId(long value) throws Exception{	this.pNoteId.setValue(value);}
	public void setDescription(String value) throws Exception {pDescription.setValue(value);}
	public void setDateSubmitted(Date value) throws Exception{	this.pDateSubmitted.setValue(value);}
	public void setUsuario(String value) throws Exception{	this.pUsuario.setValue(value);}
	public void setHandlerUser(String value) throws Exception{	this.pHandlerUser.setValue(value);}	
  public void setIdDoc(long v) throws Exception {
  	this.pIdDoc.setValue(v);
  }
  
	private BizIssue oIssue= null;
	private BizUsuario usuario;
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizIssueAttachment() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("issue_id", pIssueId);
		addItem("note_id", pNoteId);
		addItem("description", pDescription);
		addItem("date_submitted", pDateSubmitted);
    addItem( "usuario", pUsuario);
    addItem( "descr_usuario", pUsuarioDescr);
    addItem( "handler_user", pHandlerUser);		
    addItem( "id_doc", pIdDoc );
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id_doc", "Id Doc", true, true, 18 );		
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "issue_id", "Nro. Issue", true, true, 18);
		addFixedItem(FIELD, "note_id", "Nro. Nota", true, false, 18);
		addFixedItem(FIELD, "date_submitted", "Fecha Alta", true, false, 30);
		addFixedItem(FIELD, "usuario", "Usuario", true, true, 15 );
		addFixedItem(FIELD, "handler_user", "Responsable", true, false, 200 );
		addFixedItem(VIRTUAL, "description", "Descripción", true, false, 500);
		addFixedItem(VIRTUAL, "descr_usuario", "Usuario", true, false, 200 );		
	}
	

	@Override
	public String GetTable() {
		return "ISSUE_TRACK_ATTACHMENT";
	}

	public boolean Read(long id_doc) throws Exception {
		addFilter("id_doc", id_doc);
		return this.read();
	}
	
	@Override
	public void processInsert()throws Exception{
		
  	if (this.hasDocumento() && !this.getObjDocumento().hasIdDoc()) { 
	  	this.getObjDocumento().processInsert();
  	}
  	BizDocumento doc = this.getObjDocumento(); 
  	this.setIdDoc(doc.getIddoc());
  	this.pUsuario.setValue(BizUsuario.getUsr().GetUsuario());
  	this.pDateSubmitted.setValue(BizUsuario.getUsr().todayGMT(true));
		super.processInsert();
  	//this.pIdCrm.setValue(this.getIdentity("id_crm"));
  	
		BizIssue is = this.getObjIssue();
		is.setLastUpdate(BizUsuario.getUsr().todayGMT(true));
		is.processUpdate();
		
	}
	

	@Override
	public void processDelete() throws Exception {
		this.getObjDocumento().processDelete();
		super.processDelete();
	}

	
	
	public BizIssue getObjIssue() throws Exception {
		if (this.oIssue!=null) return this.oIssue;
		BizIssue t = new BizIssue();
		t.Read(this.pIssueId.getValue());
		return (this.oIssue=t);
	}
	
	public String getDescrUsuario() throws Exception {
		return this.getObjUsuario().GetDescrip();
	}
	
	
	public BizUsuario getObjUsuario() throws Exception {
		if (this.usuario!=null) return this.usuario;
		BizUsuario t = new BizUsuario();
		t.Read(this.pUsuario.getValue());
		return (this.usuario=t);
	}
	
  private BizDocumento document = null;
  public void setObjDocumento(BizDocumento d) throws Exception {
  	this.document=d;
  }
  public boolean hasDocumento() throws Exception {
  	return this.document!=null;
  }
  public BizDocumento getObjDocumento() throws Exception {
  	if (this.document!=null) return this.document;
		BizDocum d = new BizDocum();
		d.read(this.pIdDoc.getValue());
		return (this.document=d.getObjDocumento());

//  	return (this.mail=this.getObjDocum().getObjDocumento());
  }

  public BizDocum getObjDocum() throws Exception {
  	return this.getObjDocumento().getObjDocum();
  }
  


}
