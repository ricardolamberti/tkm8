package  pss.common.regions.entidad;

import pss.common.regions.entidad.nodos.GuiEntidadeNodos;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiEntidad extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiEntidad() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizEntidad(); }
  @Override
	public int GetNroIcono()   throws Exception { return 1; }
  @Override
	public String GetTitle()   throws Exception { return "Entidad"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEntidad.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "descripcion"; }
  public BizEntidad GetcDato() throws Exception { return (BizEntidad) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  	super.createActionMap();
  	this.addAction(10, "Scuursales", null, 1, false, false);
  }
  
  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10) return new JActWins(this.getNodos());
  	return super.getSubmitFor(a);
  }
  
  public JWins getNodos() throws Exception {
  	GuiEntidadeNodos wins = new GuiEntidadeNodos();
  	wins.setRecords(this.GetcDato().getNodos());
  	return wins;
  }
  
 }
