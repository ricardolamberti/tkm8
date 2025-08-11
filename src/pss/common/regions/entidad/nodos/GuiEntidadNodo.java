package  pss.common.regions.entidad.nodos;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiEntidadNodo extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiEntidadNodo() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizEntidadNodo(); }
  @Override
	public int GetNroIcono()   throws Exception { return 10072; }
  @Override
	public String GetTitle()   throws Exception { return "Sucursal"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEntidadNodo.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "descripcion"; }
  public BizEntidadNodo GetcDato() throws Exception { return (BizEntidadNodo) this.getRecord(); }

 }
