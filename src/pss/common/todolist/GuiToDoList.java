package  pss.common.todolist;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiToDoList extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiToDoList() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizToDoList(); }
  public int GetNroIcono()   throws Exception { return 10051; }
  public String GetTitle()   throws Exception { return "Tarea"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormToDoList.class; }
  public String  getKeyField() throws Exception { return "id_todo"; }
  public String  getDescripField() { return "description"; }
  public BizToDoList GetcDato() throws Exception { return (BizToDoList) this.getRecord(); }

  public void createActionMap() throws Exception {
		addAction(100,   "Realizar", null, 2003, true, true);
		super.createActionMap();
	}  

	
  @Override
  public boolean OkAction(BizAction a) throws Exception {
		if (a.getId()==JWin.ACTION_DELETE) return BizUsuario.getUsr().IsAdminUser();
		if (a.getId()==JWin.ACTION_UPDATE) return BizUsuario.getUsr().IsAdminUser();
		if (a.getId()==JWin.ACTION_QUERY) return BizUsuario.getUsr().IsAdminUser();
		if (a.getId()==100) return BizUsuario.ifOperacionHabilitada(GetcDato().getAction());
  	return super.OkAction(a);
  }
	
  @Override
  public int GetDobleClick() throws Exception {
  	if (!BizUsuario.getUsr().isAdminUser())
  		return 100;
  	return super.GetDobleClick();
  }
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==100) return runAction();
		return super.getSubmitFor(a);
	}
	
	public JAct runAction() throws Exception {
  	BizAction action = new BizAction();
  	action.SetTitle("act");
  	action.setDescrip("");
  	action.SetNroIcono( 0);
  	action.SetId(Integer.parseInt(this.GetcDato().getAction().substring(this.GetcDato().getAction().lastIndexOf("_")+1)));
  	action.setNuevaVentana(false);
  	action.setNuevaSession(false);
  	action.setModal(false);
  	action.setInMenu(true);
  	action.setObjOwner((JBaseWin)(Class.forName(this.GetcDato().getAction().substring(0,this.GetcDato().getAction().lastIndexOf("_"))).newInstance()));
		
  	GetcDato().execRealizado();
		return action.getSubmit();
	}


 }
