package pss.tourism.voidManual;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiVoidManual extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiVoidManual() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizVoidManual(); }
  @Override
	public int GetNroIcono()       throws Exception { return 1105; }
  @Override
	public String GetTitle()       throws Exception { return "Extra DK"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormVoidManual.class; }
  @Override
	public String getKeyField()   throws Exception { return "id"; }
  @Override
	public String getDescripField()                  { return "dk"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }




  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizVoidManual GetcDato() throws Exception {
    return (BizVoidManual) this.getRecord();
  }


}
