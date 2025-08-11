package  pss.common.regions.nodes;

import pss.common.security.GuiUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.JWinList;

public class GuiNodosUsuarios extends JWins {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodosUsuarios() throws Exception {
  }

  @Override
	public int     GetNroIcono() throws Exception  { return 45; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sucursales"; }
  @Override
	public Class<? extends JWin>   GetClassWin()                   { return GuiNodoUsuario.class; }

  @Override
	public void createActionMap() throws Exception {
  	this.addAction(10, "Agregar", null, GuiIcon.MULTI_MAS_ICON, true, true);
//    addActionNew     ( 1, "Nuevo Registro" );
  }
  
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWinsSelect(this.getNodos(), this.getUsuario(), true);
		return super.getSubmitFor(a);
	}
	
  @Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descr_nodo");
  }
  

	private GuiNodos getNodos() throws Exception {
		GuiNodos c = new GuiNodos();
		c.getRecords().addFilter("company", this.getRecords().getFilterValue("company"));
		return c;
	}
	
	private GuiUsuario getUsuario() throws Exception {
		GuiUsuario c = new GuiUsuario();
		c.GetcDato().Read(this.getRecords().getFilterValue("usuario"));
		return c;
	}

  

}
