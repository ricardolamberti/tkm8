package pss.common.documentos.docEmail;

import pss.common.documentos.GuiDocum;
import pss.common.documentos.GuiDocumento;
import pss.common.documentos.GuiDocums;
import pss.common.documentos.docElectronico.GuiDocAdjunto;
import pss.common.documentos.docElectronico.GuiDocAdjuntos;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiDocEmail extends GuiDocumento {


  /**
   * Constructor de la Clase
   */
  public GuiDocEmail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDocEmail(); }
  public String GetTitle()   throws Exception { return "eMail"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
//  	if (this.GetcDato().isSaliente())
//  		return FormDocEmail.class;
//  	else 
  	return FormDocEmail.class;
  }
  public Class<? extends JBaseForm> getFormNew() throws Exception {
  	return FormDocEmailNew.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormUpdate() throws Exception {
  	return FormDocEmailNew.class; 
  }
  @Override
  public Class<? extends JBaseForm> getFormFlat() throws Exception {
  	return FormDocEmailFlat.class;
  }
  public String  getKeyField() throws Exception { return "id_doc"; }
  public String  getDescripField() { return "titulo"; }
  public BizDocEmail GetcDato() throws Exception { return (BizDocEmail) this.getRecord(); }

  public void createActionMap() throws Exception {
		createActionQuery().setOnlyInForm(true);
		createActionUpdate().setOnlyInForm(true);
//		createActionDelete().setOnlyInForm(true);
		this.addAction(3,   "Eliminar", null, 12, false, false).setConfirmMessage(true);
		this.addAction(100,   "Leer", null, 117, false, false);
		this.addAction(301,   "Enviar", null, 63, true, true).setOnlyInForm(true);
//		this.addAction(305,   "Ver Respuesta", null, 63, true, true).setOnlyInForm(true);
		this.addAction(302,   "Re-enviar", null, 63, true, true).setOnlyInForm(true);
//		this.addAction(320,   "Volver a Enviar", null, 63, true, true).setOnlyInForm(true);
		this.addAction(310,   "Adjuntos", null, 10079, false, false);
		this.addAction(330,   "Grupo", null, 10079, false, false);
//		this.addAction(312,   "Agregar Adjunto", null, 10080, true, true).setOnlyInForm(true);
		
		this.addAction(200, "Responder", null, 6103, true, true).setOnlyInForm(true);
		this.addAction(400, "Marcar c/leido", null, 2, false, false).setOnlyInForm(true);
		this.addAction(500, "Marcar NO leido", null, 7, true, true).setOnlyInForm(true);
//		this.addAction(310,   "Adjuntos", null, 10079, false, false).setRefreshAction(false);
  	this.addAction(600, "Refresh Last", null, 5060, true, true);

  	this.addAction(701, "Adjunto", null, 5060, false, false);
  	this.addAction(702, "Remover Adjunto", null, 5060, false, false);
  	this.addAction(704, "Agregar Adjunto", null, 5060, false, false);
//		super.createActionMap();
	}

//  private void addActionAdjuntos() throws Exception {
//  	JIterator<BizDocElectronico> iter = this.GetcDato().getObjDocsAttached().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizDocElectronico e = iter.nextElement();
//  		this.addAction("ADJ_"+e.getIddoc(),  "Ver Adjunto", null, 10080, false, false).setNuevaVentana(true);
//  	}
//  }
  
//  public JAct getActionRespuesta() throws Exception {
//  	GuiDocums mails = new GuiDocums();
//  	mails.getRecords().addFilter("id_doc_padre", this.GetcDato().getIddoc());
//  	mails.getRecords().readAll();
//  	if (mails.toStatic().getRecords().hasOnlyOneElement()) {
//  		GuiDocum c = (GuiDocum)mails.getFirstRecord(); 
//  		return new JActQuery(c.getDocumento());
//  	}
//  	return new JActWins(mails);
//  }
  

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==2) return this.GetcDato().canModificar();
  	if (a.getId()==3) return this.GetcDato().canEliminar();
  	if (a.getId()==301) return this.GetcDato().canEnviar();
  	if (a.getId()==305) return this.GetcDato().hasRespuesta();
  	if (a.getId()==310) return this.GetcDato().isTieneAdjuntos();
  	if (a.getId()==302) return this.GetcDato().canReEnviar();
//  	if (a.getId()==320) return this.GetcDato().canReEnviar();
  	if (a.getId()==330) return this.GetcDato().hasConversacion();
  	
//  	if (a.getId()==302) return this.GetcDato().isEntrante() && this.GetcDato().isEnviado() && !this.GetcDato().hasRespuesta();
  	if (a.getId()==200) return this.GetcDato().canResponder();
  	if (a.getId()==400) return this.GetcDato().canMarcarLeido();
  	if (a.getId()==500) return this.GetcDato().canMarcarNoLeido();
  	if (a.getId()==600) return BizUsuario.getUsr().isAnyAdminUser();

  	if (a.getId()==702) return this.GetcDato().canRemoveAdjunto(); 
  	if (a.getId()==704) return this.GetcDato().canAddAdjunto(); 

  	return super.OkAction(a);
  }
//  public JAct getActEnviar() throws Exception {
//  	return new JActSubmit(this) {
//			@Override
//			public void submit() throws Exception {
//				 GetcDato().execProcessEnviar();
//			}
//		};
//  }
//  public GuiDocEmail getInfoCorreoEnviar() throws Exception {
//  	GuiDocEmail e = new GuiDocEmail();
//  	e.GetcDato().copyProperties(this.GetcDato());
//  	e.SetVision("REMITIR");
//  	e.setDropListener(this);
//  	return e;
//  }
//  public GuiDocEmail getInfoResp() throws Exception {
//  	GuiDocEmail e = new GuiDocEmail();
//  	e.GetcDato().copyKeysProperties(this.GetcDato());
//  	e.SetVision("RESP");
//  	e.setDropListener(this);
//  	return e;
//  }
//  public GuiDocEmail getInfoAcuse() throws Exception {
//  	GuiDocEmail e = new GuiDocEmail();
//  	e.GetcDato().copyKeysProperties(this.GetcDato());
//  	e.SetVision("ACUSE");
//  	e.setDropListener(this);
//  	return e;
//  }

	public JAct getSubmitFor(BizAction a) throws Exception {
//		if (a.getId()==300) return this.getActionPrint();
		if (a.getId()==100) return this.findActionQuery();
		if (a.getId()==301) return new JActSubmit(this, 301) {
			public void execSubmit() throws Exception {
				GetcDato().processEnviar();
			}
		};
		if (a.getId()==302) return new JActNew(this.getNewMailReenvio(), 0);
		if (a.getId()==200) return new JActNew(this.getNewMailRespuesta(), 0);
//		if (a.getId()==305) return this.getActionRespuesta();
		if (a.getId()==310) return new JActWins(this.getAdjuntos());
//		if (a.getId()==312) return new JActNew(this.getNewAdjunto(), 4);
//		if (a.getId()==320) return new JActNew(this.getNewMailVoverEnviar(), 0);
		if (a.getId()==330) return new JActWins(this.getGrupoMails());
		
//		if (a.getId()==300) return getPrintAction();
//		if (a.getId()==310) return new JActWins(this.getAdjuntos());
		if (a.getId()==400) return new JActSubmit(this) {
			public void execSubmit() throws Exception {
				 GetcDato().processMarcarComoLeido();
			}
		};		
		if (a.getId()==500) return new JActSubmit(this) {
			public void execSubmit() throws Exception {
				GetcDato().processMarcarComoNoLeido();
			}
		};
		if (a.getId()==600) return new JActSubmit(this) {
			public void execSubmit() throws Exception {
				GetcDato().processRefreshLast();
			};
		};
		if (a.getId()==701) return this.getAdjunto(a.getRow()).findAction(10).getObjSubmit();
		if (a.getId()==702) return new JActSubmit(this, 702) {
			public void submit() throws Exception {
				removeAdjunto(a.getRow());
			};
		};
		if (a.getId()==704) return new JActNew(this.getNewAdjunto(), 4);

//		if (a.getIdAction().startsWith("ADJ_")) return this.findDocAdj(a).findAction(10).getObjSubmit();
		return super.getSubmit(a);
	}
	
	public JWin getNewAdjunto() throws Exception {
		GuiDocAdjunto a = new GuiDocAdjunto();
		a.GetcDato().addFilter("doc_company", this.GetcDato().getCompany());
		a.setDropListener(this);
		a.GetccDato().setObjDocEmail(this.GetcDato());
		return a;
	}

	
//	private GuiDocElectronico findDocAdj(BizAction a) throws Exception {
//		GuiDocElectronico e = new GuiDocElectronico();
//		e.GetcDato().Read(Integer.parseInt(a.getIdAction().substring(a.getIdAction().indexOf("_")+1)));
//		return e;
//	}

//	@Override
// 	public boolean canConvertToURL() throws Exception {
//		return false;
//	}
 
//	public JWins getConfigCorreo() throws Exception {
//		GuiUsrMailsSender mails = new GuiUsrMailsSender();
//		mails.getRecords().addFilter("usuario", BizUsuario.getUsr().GetUsuario());
//		return mails;
//	}
	public void removeAdjunto(String row) throws Exception {
		this.GetcDato().removeAdjunto(row);
	}

	public GuiDocAdjunto getAdjunto(String row) throws Exception {
		GuiDocAdjunto a = new GuiDocAdjunto();
		a.setRecord(this.GetcDato().findAdjunto(row));
		return a;
	}

	public JWins getObjAdjuntos() throws Exception {
		GuiDocAdjuntos w = new GuiDocAdjuntos();
		w.setRecords(this.GetcDato().getObjAdjuntos());
		w.setDocEmail(this);
		w.setPreviewFlag(JWins.PREVIEW_MAX);
		return w;
	}
	
	public JWins getAdjuntos() throws Exception {
		GuiDocAdjuntos w = new GuiDocAdjuntos();
		w.setRecords(this.GetcDato().getAdjuntos());
		w.setDocEmail(this);
		w.setPreviewFlag(JWins.PREVIEW_MAX);
		return w;
//		JIterator iter = w.getStaticIterator();
//		while (iter.hasMoreElements()) {
//			GuiDocAdjunto a = (GuiDocAdjunto)iter.nextElement();
//			a.GetccDato().setObjDocEmail(this.GetcDato());
//		}
//  	w.setPreviewSplitPos(300);
//  	w.setForceSelected(false);
//		return w;	
	}
	 
//	public JWins getAdjuntos() throws Exception {
//  	GuiDocums docs = new GuiDocums();
//  	docs.getRecords().addFilter("id_doc_padre", this.GetcDato().getIddoc());
//  	docs.getRecords().addFilter("tipo_doc", BizDocFisicoTipo.ELECT);
//  	docs.readAll();
//  	GuiDocAdjuntos recs = new GuiDocAdjuntos();
//  	recs.SetEstatico(true);
//  	recs.setPreviewSplitPos(300);
//  	JIterator<JWin> iter = docs.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		GuiDocum d = (GuiDocum)iter.nextElement();
//  		GuiDocAdjunto a = new GuiDocAdjunto();
//  		a.setRecord(d.GetcDato().getObjDocumento());
//  		a.setDocEmail(this);
//  		recs.addRecord(a);
//  	}
//  	recs.setForceSelected(false);
//  	return recs;
//  }
	
//	public JWins getAdjuntos() throws Exception {
//  	GuiDocums docs = new GuiDocums();
//  	docs.getRecords().addFilter("id_doc_padre", this.GetcDato().getIddoc());
//  	docs.getRecords().addFilter("tipo_doc", BizDocFisicoTipo.ELECT);
//  	docs.readAll();
//  	GuiDocAdjuntos recs = new GuiDocAdjuntos();
//  	recs.SetEstatico(true);
//  	recs.setPreviewSplitPos(300);
////  	recs.setToolbarForced(JWin.TOOLBAR_NONE);
//  	JIterator<JWin> iter = docs.getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		GuiDocum d = (GuiDocum)iter.nextElement();
//  		GuiDocAdjunto a = new GuiDocAdjunto();
//  		a.setRecord(d.GetcDato().getObjDocumento());
//  		a.setDocPadre(this);
//  		recs.addRecord(a);
//  	}
//  	recs.setForceSelected(false);
//  	return recs;
//  }


//	public JWin getNewAdjunto() throws Exception {
//		GuiDocAdjunto e = new GuiDocAdjunto();
//		e.GetcDato().addFilter("company", this.GetcDato().getCompany());
//		e.setDropListener(this);
//		return e;
//	}
	
	@Override
	public JAct Drop(JBaseWin baseWin) throws Exception {
		if (baseWin instanceof GuiDocAdjunto) {
			this.GetcDato().execProcessAddAttached(((GuiDocAdjunto) baseWin).GetccDato());
		} 
		return null;
	}
	
	public GuiDocEmail createNewMail() throws Exception {
		return new GuiDocEmail();
	}

	public GuiDocEmail getNewMailReenvio() throws Exception {
		GuiDocEmail email = this.createNewMail();
		email.setRecord(this.GetcDato().createMailReenvio());
		email.setCanConvertToURL(false);
		return email;
	}
	
	public GuiDocEmail getNewMailRespuesta() throws Exception {
		GuiDocEmail email = this.createNewMail();
		email.setRecord(this.GetcDato().createMailRespuesta());
		email.setCanConvertToURL(false);
		return email;
	}

//	public GuiDocEmail getNewMailVoverEnviar() throws Exception {
//		GuiDocEmail email = new GuiDocEmail();
//		email.setRecord(this.GetcDato().createMailReenvio(true));
//		email.setCanConvertToURL(false);
//		return email;
//	}


	public int GetNroIcono()   throws Exception {
		if (this.GetcDato().isSaliente()) {
//			if (this.GetcDato().isAnulado())
//				return 63;
//			if (this.GetcDato().hasRespuesta())
//				return 6048;
			if (this.GetcDato().isEnviado())
				return 63;
			return 5047; // borrador
		} else {
//	  	if (this.GetcDato().hasRespuesta())
//	  		return 6052;
	  	if (this.GetcDato().isLeido())
	  		return 59;
	  	return 69;
		}

//		if (this.GetcDato().isRespondido())
//			return 6051;
	}

//	public JAct findActionPostAlta() throws Exception {
//		return this.findActionQuery();
//	}

	public JAct findActionQuery() throws Exception {
		this.GetcDato().verifyLeido();
		return new JActQuery(this);
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	public GuiDocEmails createMailDetails() throws Exception {
		return new GuiMailDetails();
	}

	public JWins getGrupoMails() throws Exception {
		GuiDocEmails w = new GuiMailDetails();
		w.getRecords().addFilter("id_doc_grupo", this.GetcDato().getIdDocGrupo());
		w.getRecords().addOrderBy("id_doc", "desc");
		w.setPreviewFlag(JWins.PREVIEW_MAX);
		w.setShowFilters(false);
		return w;
	}


}
