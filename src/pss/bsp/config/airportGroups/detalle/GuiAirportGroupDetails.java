package  pss.bsp.config.airportGroups.detalle;

import pss.bsp.config.airportGroups.GuiAirportGroup;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWinsSelect;
import pss.core.winUI.lists.JWinList;
import pss.tourism.airports.GuiAirports;

public class GuiAirportGroupDetails extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiAirportGroupDetails() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10032; } 
  public String  GetTitle()    throws Exception  { return "Aeropuertos"; }
  public Class<? extends JWin>  GetClassWin() { return GuiAirportGroupDetail.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo País" );
//  	this.addAction(10, "Agregar Aeropuerto", null, 10020, true, true);
  	this.addAction(10, "Cargar Aeropuertos", null, 10020, true, true);
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
    zLista.AddColumnaLista("airport");
    zLista.AddColumnaLista("descr_airport");
  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId() == 10)	return new JActWinsSelect(new GuiAirports(), this.getGroup(),true);
		return super.getSubmitFor(a);
	}
	
	public GuiAirportGroup getGroup() throws Exception {
		GuiAirportGroup w = new GuiAirportGroup();
		w.GetcDato().read(this.getFilterValue("company"), Long.parseLong(this.getFilterValue("id_group")));
		return w;
	}
	
}
