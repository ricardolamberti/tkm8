package  pss.bsp.config.carrierGroups.detalle;

import pss.bsp.config.carrierGroups.GuiCarrierGroup;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.lists.JWinList;
import pss.tourism.carrier.GuiCarriers;

public class GuiCarrierGroupDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiCarrierGroupDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Aerolineas"; }
  public Class<? extends JWin>  GetClassWin() { return GuiCarrierGroupDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo País" );
  	this.addAction(10, "Agregar Línea Aerea", null, 10020, true, true);
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("descr_carrier");
  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWinsSelect(new GuiCarriers(), this.getGroup(),true);
		return super.getSubmitFor(a);
	}
	
	public GuiCarrierGroup getGroup() throws Exception {
		GuiCarrierGroup w = new GuiCarrierGroup();
		w.GetcDato().read(this.getFilterValue("company"), Long.parseLong(this.getFilterValue("id_group")));
		return w;
	}
	
}
