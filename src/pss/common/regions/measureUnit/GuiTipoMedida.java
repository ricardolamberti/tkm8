package  pss.common.regions.measureUnit;


import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiTipoMedida extends JWin {


  public GuiTipoMedida() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizTipoMedida(); }
  @Override
	public int GetNroIcono()       throws Exception { return 899; }
  @Override
	public String GetTitle()       throws Exception { return "Tipo de Unidad"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormTipoMedida.class; }
  @Override
	public String getKeyField()   throws Exception { return "tipo"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }

  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
    addAction( 10, "Unidades Medida", new JAct() { @Override
		public JBaseWin GetBWin() throws Exception {return ObtenerUnidades();}}, 95, true, true, true, "Detail");
  }

  public BizTipoMedida GetcDato() throws Exception {
    return (BizTipoMedida) this.getRecord();
  }

  public JWins ObtenerUnidades() throws Exception {
    GuiUnidadMedidas oUnidades = new GuiUnidadMedidas();
    oUnidades.getRecords().addFilter("tipo", GetcDato().pTipo.getValue());
    return oUnidades;
  }


}
