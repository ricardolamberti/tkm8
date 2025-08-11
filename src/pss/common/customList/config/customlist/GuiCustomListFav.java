package  pss.common.customList.config.customlist;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiCustomListFav extends JWin {


	private GuiCustomListFav customListPadre;

  /**
   * Constructor de la Clase
   */
  public GuiCustomListFav() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizCustomListFav(); }
  public int GetNroIcono()   throws Exception { return 10027; }
  public String GetTitle()   throws Exception { return "Lista DiN°mica Favoritos"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCustomListFav.class; }
  public String  getKeyField() throws Exception { return "list_id"; }
  public String  getDescripField() { return "usuario"; }
  public BizCustomListFav GetcDato() throws Exception { return (BizCustomListFav) this.getRecord(); }
  

	public void createActionMap() throws Exception {
		createActionQuery();
		createActionUpdate();
		createActionDelete();
	}
	
	@Override
	public boolean OkAction(BizAction a) throws Exception {
		return super.OkAction(a);
	}
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		return super.getSubmitFor(a);
	}


}
