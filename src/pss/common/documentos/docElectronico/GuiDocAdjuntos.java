package pss.common.documentos.docElectronico;

import pss.common.documentos.docEmail.GuiDocEmail;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.lists.JWinList;

public class GuiDocAdjuntos extends GuiDocElectronicos {


	private GuiDocEmail mail;
	
	public void setDocEmail(GuiDocEmail v) {
		this.mail=v;
	}

	  /**
	   * Constructor de la Clase
	   */
  public GuiDocAdjuntos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10020; } 
  public String  GetTitle()    throws Exception  { return "Ajuntos"; }
  public Class<? extends JWin>  GetClassWin()    { return GuiDocAdjunto.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//	    addActionNew( 1, "Nuevo Registro" );
  	this.addAction(312,   "Agregar Adjunto", null, 10080, true, true);//.setOnlyInForm(true);
  }

  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==312) return this.mail.GetcDato().isSaliente() && !this.mail.GetcDato().isEnviado();
  	return super.OkAction(a);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==312) return new JActNew(this.getNewAdjunto(), 4);
  	return super.getSubmitFor(a);
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	if (zLista.isForExport()) {
	    zLista.AddIcono("");
	    zLista.AddColumnaLista("File", "titulo").setActionOnClick(10);
	    if (BizUsuario.getUsr().isAnyAdminUser())
	    	zLista.AddColumnaLista("id_doc");
  	} else {
  		zLista.addColumnWinAction("Flat", 1);
  	}
  }
  
  @Override
  protected void configureList(JWinList list) throws Exception {
  	list.setHideActions(true);
  }

	public boolean hasNavigationBar() throws Exception {
		return false;
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}

	public JWin getNewAdjunto() throws Exception {
		GuiDocAdjunto a = new GuiDocAdjunto();
		a.GetcDato().addFilter("company", this.mail.GetcDato().getCompany());
		a.setDropListener(this.mail);
		a.GetccDato().setObjDocEmail(this.mail.GetcDato());
		return a;
	}

	}
