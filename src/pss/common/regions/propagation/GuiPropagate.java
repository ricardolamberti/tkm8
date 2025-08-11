package  pss.common.regions.propagation;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiPropagate extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiPropagate() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizPropagate(); }
  @Override
	public int GetNroIcono()   throws Exception { return 10; }
  @Override
	public String GetTitle()   throws Exception { return "Propagacion de datos"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPropagate.class; }
  @Override
	public String  getKeyField() throws Exception { return "id_propagate"; }
  @Override
	public String  getDescripField() { return "description"; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar" );
    addActionUpdate(2, "Modificar" );
    addActionDelete (3, "Eliminar"  );

    addAction(5, "Propagar Detalle", new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return ObtenerDetalle(); }
    }, GuiIcon.REMOTE_REFRESH_ICON, false, false, true, "Detalle" );
  }


  /**
   * Devuelvo el dato ya casteado
   */
  public BizPropagate GetcDato() throws Exception { return (BizPropagate) this.getRecord(); }

  public JWins ObtenerDetalle() throws Exception {
    GuiPropagatesDetails oDetail = new GuiPropagatesDetails();
    oDetail.getRecords().addFilter("id_propagate", this.GetcDato().getIdpropagate() );
    return oDetail;
  }

 }
