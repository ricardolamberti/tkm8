package  pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiNodoPC extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodoPC() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizNodoPC(); }
  @Override
	public String GetTitle()       throws Exception { return "PC Vinculada"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormNodoPC.class; }
  @Override
	public String getKeyField()   throws Exception { return "host"; }
  @Override
	public String getDescripField()                  { return "host"; }

  @Override
	public int GetNroIcono() throws Exception {
    if( GetcDato().getIsMaster() ) {
      return 10064;
    }
    else {
      return 10061;
    }
  }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar");
    addActionDelete ( 3, "Eliminar" );
//    addAction( 5, "Setear Master", KeyEvent.VK_INSERT, new JAct() {
//      @Override
//			public void Do() throws Exception {
//        if ( ! UITools.showConfirmation( "Confirmación", "¿Está Ud. seguro?" ) ) return;
//        GetcDato().execProcessNodoMaster(); }
//    }, 1, true, true );
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizNodoPC GetcDato() throws Exception {
    return (BizNodoPC) this.getRecord();
  }

}
