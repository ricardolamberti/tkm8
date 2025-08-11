package  pss.bsp.bo.gen.detalle;

import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiGenDetalles extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiGenDetalles() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10036; } 
  public String  GetTitle()    throws Exception  { return "Interaz BackOffice"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiGenDetalle.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
  }
  
  BizGenCabecera objHeader;
  
	public BizGenCabecera getHeader() throws Exception {
		if (objHeader!=null) return objHeader;
		BizGenCabecera c = new BizGenCabecera();
		c.read(this.getRecords().getFilterValue("company"), Long.parseLong(this.getRecords().getFilterValue("idInterfaz")));
		return (objHeader=c);
	}


  private String getTitulo(long campo)  throws Exception {
  	if (GetVision().equals("CONFIG")) 
  		if (getHeader().getTitulo(campo).equals("*")) return "D"+campo;
  	return getHeader().getTitulo(campo);
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	for(int i=1;i<=40;i++) 
  		zLista.AddColumnaLista(getTitulo(i), "d"+i);
  }

}
