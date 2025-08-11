package pss.bsp.contrato.detalle.variaciones;

import pss.bsp.contrato.detalle.GuiDetalle;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.lists.JWinList;

public class GuiVariacionParticulares extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiVariacionParticulares() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15011; } 
  public String  GetTitle()    throws Exception  { return "Variaciones"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiVariacionParticular.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Registro" );
   	this.addAction(40, "Restaurar Estimadores", null, 406, true, true, true, "Group" );

  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId() == 40)	return new JActSubmit(this) {
  		@Override
  		public void submit() throws Exception {
  			getObjDetalle().GetcDato().execProcessCopyVariaciones();
  			super.submit();
  		}
 		};
  	return super.getSubmitFor(a);
  }

  public GuiDetalle getObjDetalle() throws Exception {
  	GuiDetalle det = new GuiDetalle();
  	det.GetcDato().read(Long.parseLong(getFilterValue("linea_contrato")));
  	return det;
  }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("descripcion");
    zLista.AddColumnaLista("fecha_desde");
    zLista.AddColumnaLista("fecha_hasta");
    zLista.AddColumnaLista("variacion");
  }

}
