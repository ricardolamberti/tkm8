package pss.bsp.contrato.evaluate.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.lists.JWinList;

public class GuiEvaluateDetails  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEvaluateDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 

	public String GetTitle() throws Exception {
		return "Tests";
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiEvaluateDetail.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
	}

	@Override
	public boolean OkAction(BizAction a) throws Exception {
		return super.OkAction(a);
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		return super.getSubmit(a);
	}

  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {

    zLista.AddColumnaLista("ok");
    zLista.AddColumnaLista("detail");
    zLista.AddColumnaLista("result");

  }
  
  @Override
  public long selectSupraCount() throws Exception {
  	return -1;
  }
  
  @Override
  public int getPagesize() {
  	return -1;
  }
 
}
