package  pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiChangeNode extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiChangeNode() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizChangeNode(); }
  @Override
	public int GetNroIcono()       throws Exception { return 76; }
  @Override
	public String GetTitle()       throws Exception { return "Cambio de Nodo"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormChangeNode.class; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizChangeNode GetcDato() throws Exception {
    return (BizChangeNode) this.getRecord();
  }

}
