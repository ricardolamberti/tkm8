package  pss.common.regions.measureUnit;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiUnidadMedida extends JWin {


  public GuiUnidadMedida() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizUnidadMedida(); }
  @Override
	public int GetNroIcono()       throws Exception { return 100; }
  @Override
	public String GetTitle()       throws Exception { return "Unidad de Medida"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormUnidadMedida.class; }
  @Override
	public String getKeyField()   throws Exception { return "unidad_medida"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }

  public BizUnidadMedida GetcDato() throws Exception {
    return (BizUnidadMedida) this.getRecord();
  }


}
