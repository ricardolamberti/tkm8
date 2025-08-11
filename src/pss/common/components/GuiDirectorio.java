package  pss.common.components;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;

public class GuiDirectorio extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiDirectorio() throws Exception {
  }
  
  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizDirectorio(); }
  @Override
	public int GetNroIcono()   throws Exception { return 408; }
  @Override
	public String GetTitle()   throws Exception { return "Directorio"; }
  @Override
	public String  getKeyField() throws Exception { return "directorio"; }
  @Override
	public String  getDescripField() { return "directorio"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addAction(2, "Directorios", new JAct() {@Override
		public JBaseWin GetBWin() throws Exception {return ObtenerDirectorios();}}, 90, false, false, true, "Detail" );
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizDirectorio GetcDato() throws Exception {
    return (BizDirectorio) this.getRecord();
  }

  public JWins ObtenerDirectorios() throws Exception {
    GuiDirectorios oDirectorios = new GuiDirectorios();
    oDirectorios.getRecords().addFilter("directorio", GetcDato().pDirectorio.getValue());
    return oDirectorios;
  }

}
