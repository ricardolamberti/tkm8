package pss.common.regions.propagation;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPropagatesDetail extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiPropagatesDetail() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizPropagatesDetail(); }
  @Override
	public int GetNroIcono()   throws Exception { return 10116; }
  @Override
	public String GetTitle()   throws Exception { return "Detalle de la propagación"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPropagatesDetail.class; }
  @Override
	public String  getKeyField() throws Exception { return "bdClass"; }
  @Override
	public String  getDescripField() { return "propagate"; }
  /**
   * Mapeo las acciones con las operaciones
   */
  @Override
	public void createActionMap() throws Exception {
    addActionQuery(1, "Consultar" );
    addActionUpdate(2, "Modificar" );
    addActionDelete (3, "Eliminar"  );
  }


  /**
   * Devuelvo el dato ya casteado
   */
  public BizPropagatesDetail GetcDato() throws Exception { return (BizPropagatesDetail) this.getRecord(); }

 }
