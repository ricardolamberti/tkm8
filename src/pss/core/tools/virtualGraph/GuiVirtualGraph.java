package pss.core.tools.virtualGraph;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiVirtualGraph extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiVirtualGraph() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizVirtualGraph(); }
  public int GetNroIcono()   throws Exception { return 15011; }
  public String GetTitle()   throws Exception { return "Graph"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormVirtualGraph.class; }
  public String  getKeyField() throws Exception { return "agrupador2"; }
  public String  getDescripField() { return "cantidad1"; }
  public BizVirtualGraph GetcDato() throws Exception { return (BizVirtualGraph) this.getRecord(); }

  @Override
  public void createActionMap() throws Exception {
  }

  @Override
  public JAct getSubmitFor(BizAction a) throws Exception {
 	return super.getSubmitFor(a);
  }


 }
