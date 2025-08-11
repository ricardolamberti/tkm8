package pss.common.mail.mailing;

import pss.common.security.GuiUsuarios;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiMail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMail(); }
  public String GetTitle()   throws Exception { return "Correo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMail.class; }
  public Class<? extends JBaseForm> getFormNew() throws Exception { return FormNewMail.class; }
  public String  getKeyField() throws Exception { return "id_message"; }
  public String  getDescripField() { return "id_message"; }
  public BizMail GetcDato() throws Exception { return (BizMail) this.getRecord(); }
	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
    this.createActionQuery();
    this.addAction(12, "Emilinar", null, 12, true, true);
    this.addAction(14, "Emilinar", null, 12, true, true).setConfirmMessage(true);
    this.addAction(50, "Lista", null, GuiIcon.MENOS_ICON, false, false);
    this.addAction(60, "Recuperar", null, 6055, true, true);
    this.addAction(64, "Marcar No Leido", null, 10012, true, true).setMulti(true);
	}
	
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==12) return !this.GetcDato().isFolderTrash() && this.GetcDato().isReaded();
		if (a.getId()==14) return this.GetcDato().isFolderTrash();
		if (a.getId()==60) return this.GetcDato().isFolderTrash();
		if (a.getId()==64) return this.GetcDato().isFolderInbox() && this.GetcDato().isReaded();
		return super.OkAction(a);
	}
	
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return viewMail();
		if (a.getId()==12) return new JActSubmit(this, 12) {
			public void execSubmit() throws Exception {
				GetcDato().processMoveToTrash();
			};
		};
		if (a.getId()==14) return new JActSubmit(this, 14) {
			public void execSubmit() throws Exception {
				GetcDato().processDelete();
			};
		};
		if (a.getId()==60) return new JActSubmit(this, 60) {
			public void execSubmit() throws Exception {
				GetcDato().processRecuperar();
			};
		};
		if (a.getId()==64) return new JActSubmit(this, 64) {
			public void execSubmit() throws Exception {
				GetcDato().processMarcarNoLeido();
			};
		};
		return super.getSubmit(a);
	}
	
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 50) return new JActWinsSelect(this.getDestinatarios(),this, true);
		return null;
	}
	
	public JAct viewMail() throws Exception {
		JAct act = new JActQuery(this.getRelativeWin());
		this.GetcDato().execProcessMarkReaded();
		return act;
	}
	
	protected JWins getDestinatarios() throws Exception {
		GuiUsuarios wins= new GuiUsuarios();
//		if (!BizUsuario.getUsr().isAdminUser())
		wins.getRecords().addFilter("company", GetcDato().getCompany());
		wins.getRecords().addOrderBy("descripcion");
		return wins;
	}
	
  static JWins objFolders;
  public static  JWins getFolders() throws Exception {
  	if (objFolders!=null) return objFolders;
    JMap<String,String> folders = JCollectionFactory.createMap();
    folders.addElement(BizMail.FOLDER_INBOX, "Recibidos");
    folders.addElement(BizMail.FOLDER_SENDERS, "Enviados");
    folders.addElement(BizMail.FOLDER_TRASH, "Papelera");
  	return objFolders=JWins.createVirtualWinsFromMap(folders);
  }
  
  public int GetNroIcono()   throws Exception {
  	if (this.GetcDato().isFolderInbox()) {
  		return this.GetcDato().isReaded()? 6054 : 6053;
  	}
    if (this.GetcDato().isFolderSenders())
    	return 6046;
    
    if (this.GetcDato().isFolderTrash())
    	return 1107;
    
  	return 10058; 
  }

 }
