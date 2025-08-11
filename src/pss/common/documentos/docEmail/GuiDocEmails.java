package pss.common.documentos.docEmail;

import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiDocEmails extends JWins {


	public static int MAIL_CONVERSACIONES=1;
	public static int MAIL_RECIBIDOS=2;
	public static int MAIL_ENVIADOS=3;
	public static int MAIL_BORRADORES=4;
	
	private int subbar=1;

	public void setSubbar(int v) {
		this.subbar=v;
	}
	
	public int getSubbar() {
		return this.subbar;
	}

  /**
   * Constructor de la Clase
   */
  public GuiDocEmails() throws Exception {
  }

  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Mails"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiDocEmail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  	this.addAction(14, "Todos", null, 63, true, true).setForeground(this.findColor(GuiDocEmails.MAIL_CONVERSACIONES));
  	this.addAction(10, "Recibidos", null, 809, true, true).setForeground(this.findColor(GuiDocEmails.MAIL_RECIBIDOS));
  	this.addAction(12, "Enviados", null, 808, true, true).setForeground(this.findColor(GuiDocEmails.MAIL_ENVIADOS));
  	this.addAction(17, "Borradores", null, 5047, true, true).setForeground(this.findColor(GuiDocEmails.MAIL_BORRADORES));
  	this.addAction(20, "Mails", null, 63, false, false);
  	this.addAction(30, "Mis Mails", null, 63, false, false);
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==8) return this.getActionNewMail();

  	if (a.getId()==20) return new JActWins(this.getMails(false));
  	if (a.getId()==30) return new JActWins(this.getMails(true));
  	if (a.getId()==14) return new JActSubmit(this) {
  		public void submit() throws Exception {
  			mailParent.setSubbar(GuiDocEmails.MAIL_CONVERSACIONES);
  		}
  	};
  	if (a.getId()==10) return new JActSubmit(this) {
  		public void submit() throws Exception {
  			mailParent.setSubbar(GuiDocEmails.MAIL_RECIBIDOS);
  		}
  	};
  	if (a.getId()==12) return new JActSubmit(this) {
  		public void submit() throws Exception {
  			mailParent.setSubbar(GuiDocEmails.MAIL_ENVIADOS);
  		}
  	};
  	if (a.getId()==17) return new JActSubmit(this)  {
  		public void submit() throws Exception {
  			mailParent.setSubbar(GuiDocEmails.MAIL_BORRADORES);
  		}
  	};

  	return super.getSubmitFor(a);
  }
  
  public String findColor(int id) throws Exception {
  	return this.findMailParent().getSubbar()==id?"#920c82":"#337ab7";
  }

  public JAct getActionNewMail() throws Exception {
		return new JActNew(this.getNewDocEMail(), 0);
	}

  public GuiDocEmail getNewDocEMail() throws Exception {
  	GuiDocEmail c = new GuiDocEmail();
  	c.GetcDato().setEnviar(true);
  	c.GetcDato().setCompany(this.getRecords().getFilterValue("company"));
  	c.setCanConvertToURL(false);
  	return c;
  }


	public JWins getMails(boolean propios) throws Exception {
		GuiDocEmails mail=this.findMailParent();
		if (mail.getSubbar()==GuiDocEmails.MAIL_CONVERSACIONES) return this.getMailsConversaciones(propios);
		if (mail.getSubbar()==GuiDocEmails.MAIL_RECIBIDOS) return this.getMailsRecives(propios);
		if (mail.getSubbar()==GuiDocEmails.MAIL_ENVIADOS) return this.getMailsSents(propios);
		if (mail.getSubbar()==GuiDocEmails.MAIL_BORRADORES) return this.getMailsBorradores(propios);
		return null;
	}
	
	public void assingCommon(GuiDocEmails source) throws Exception {
		this.getRecords().addFilter("doc_company", source.getRecords().getFilterValue("doc_company"));
		this.setPreviewFlag(JWins.PREVIEW_MAX);
		this.setForceSelected(false);
		this.setPreviewSplitPos(600);
		this.setMailParent(source.findMailParent());
	}
	
	public GuiDocEmails createConversaciones() throws Exception {
		return new GuiMailConversaciones();
	}

	public JWins getMailsConversaciones(boolean propios) throws Exception {
		GuiDocEmails wins = this.createConversaciones();
		wins.getRecords().addFilter("last", true);
		if (propios) wins.getRecords().addFilter("doc_usuario", BizUsuario.getUsr().GetUsuario());
		wins.getRecords().addOrderBy("id_doc", "desc");
		wins.assingCommon(this);
		return wins;
	}

	public JWins getMailsSents(boolean propios) throws Exception {
		GuiMailSents wins = new GuiMailSents();
		wins.getRecords().addFilter("flujo", BizDocEmail.SALIENTE);
		if (propios) wins.getRecords().addFilter("doc_usuario", BizUsuario.getUsr().GetUsuario());
		wins.getRecords().addFilter("enviado", true);
		wins.getRecords().addOrderBy("id_doc", "desc");
		wins.assingCommon(this);
		return wins;
	}
	

	public JWins getMailsRecives(boolean propios) throws Exception {
		GuiMailRecives wins = new GuiMailRecives();
		wins.getRecords().addFilter("flujo", BizDocEmail.ENTRANTE);
		if (propios) wins.getRecords().addFilter("doc_usuario", BizUsuario.getUsr().GetUsuario());
		wins.getRecords().addOrderBy("id_doc", "desc");
		wins.assingCommon(this);
		return wins;
	}
		
	public JWins getMailsBorradores(boolean propios) throws Exception {
		GuiMailBorradores wins = new GuiMailBorradores();
		wins.getRecords().addFilter("flujo", BizDocEmail.SALIENTE);
		if (propios) wins.getRecords().addFilter("doc_usuario", BizUsuario.getUsr().GetUsuario());
		wins.getRecords().addFilter("enviado", false);
		wins.getRecords().addOrderBy("id_doc", "desc");
		wins.assingCommon(this);
		return wins;
	}


	public boolean forceCleanIdemHistory() throws Exception {
		return true;
	}

	public boolean isCleanHistory() throws Exception {
		return true;
	}

	@Override
	public long selectSupraCount() throws Exception {
		return -1L;
	}

	public boolean isForceSelectWithPreview() throws Exception {
		return false;
	}
	
	private GuiDocEmails mailParent;
	public void setMailParent(GuiDocEmails v) throws Exception {
		this.mailParent=v;
	}

	public GuiDocEmails findMailParent() throws Exception {
		if (this.mailParent==null) return this;
		return this.mailParent;
	}
	
	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	public boolean refreshOnSubmit() throws Exception {
		return true;
	}

  
//	@Override
//	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
//	}
	
//	@Override
//	protected void asignFilterByControl(JFormControl control) throws Exception {
//		super.asignFilterByControl(control);
//	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList list) throws Exception {
  	if (list.isForExport()) {
  		list.AddIcono("");
	  	list.AddColumnaLista("To/From", "find_email");
			list.AddColumnaLista("Detalle", "asunto_body_trunc");
			list.AddColumnaLista("Fecha", "doc_fecha");
			list.AddColumnaLista("Usuario", "doc_usuario");
//	  	list.AddColumnaLista("Grupo", "id_doc_grupo");
		} else {
			list.addColumnWinAction("Flat", 1);
			list.setWithHeader(false);
		}

  }
  
  @Override
  protected void configureList(JWinList list) throws Exception {
  	list.setHideActions(true);
  }

  @Override
  public void ConfigurarFiltros(JFormFiltro filters) throws Exception {
  	JFormControl c;
  	filters.addEditResponsive("De ", "mail_from").setFilterNeverHide(true).setOperator("like");
  	filters.addEditResponsive("Asunto", "doc_titulo").setFilterNeverHide(true).setOperator("like");
  	filters.addComboResponsive("Usuario", "doc_usuario", new JControlCombo() {
  		public JWins getRecords(boolean one) throws Exception {
  			return getUsuarios();
  		};
  	}, true).setFilterNeverHide(true);
  	c=filters.addCheckResponsive("No Respondidos", "respuesta");
  	c.setIdControl("no_respondidos");
  	c.setRefreshForm(true);
  	
  }
  
  public JWins getUsuarios() throws Exception {
		JWins oWins=new GuiUsuarios();
		oWins.getRecords().addFilter("company", this.getRecords().getFilterValue("doc_company"));
		oWins.getRecords().addFilter("activo", true);
		return oWins;
  }
  
    
  @Override
  protected void asignFilterByControl(JFormControl control) throws Exception {
  	if (control.getIdControl().equals("no_leidos")) {
  		if (control.getValue().equals("S"))
  			this.getRecords().addFilter("leido", false);
  		else
  			this.getRecords().clearFilter("leido");
  		return;
  	}
  	if (control.getIdControl().equals("no_respondidos")) {
  		if (control.getValue().equals("S")) {
  			this.getRecords().addFilter("respuesta", false);
  			this.getRecords().addFilter("anulado", false);
//  			this.getRecords().addFilter("enviado", true);
  		} else {
  			this.getRecords().clearFilter("respuesta");
  			this.getRecords().clearFilter("anulado");
//  			this.getRecords().clearFilter("enviado");
  		}
  		return;
  	}
  		
  	super.asignFilterByControl(control);
  }

}
