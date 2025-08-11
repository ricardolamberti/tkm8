package  pss.common.security.license.typeLicense.detail;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;

public class GuiTypeLicenseDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiTypeLicenseDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 912; } 
  public String  GetTitle()    throws Exception  { return "Detalles tipos licencia"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiTypeLicenseDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
  }

	@Override
	public boolean OkAction(BizAction zAct) throws Exception {
		if (this.GetVision().equals("PROTECT")) return false;
		return super.OkAction(zAct);
	}

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    super.ConfigurarColumnasLista(zLista);
  }

}
