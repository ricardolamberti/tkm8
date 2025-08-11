package  pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiLocalidad extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLocalidad() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizLocalidad(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub-Sub División"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormLocalidad.class; }
  @Override
	public String  getKeyField() throws Exception { return "localidad_id"; }
  @Override
	public String  getDescripField()                { return "localidad"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }


  public String getTipoLocalidad()throws Exception {
    String sPais = GetcDato().getPais();
    if (!sPais.equals("")){
      BizPais oPais = new BizPais();
      oPais.Read(sPais);
      return oPais.GetLocalidad();
    }
    else return "";
  }

//  @Override
//	public JBaseForm showNewForm(boolean zShow) throws Exception {
//   JBaseForm oForm = super.showNewForm(zShow);
//   oForm.SetExitOnOk(true);
//   return oForm;
//  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizLocalidad GetcDato() throws Exception {
    return (BizLocalidad) this.getRecord();
  }


}
