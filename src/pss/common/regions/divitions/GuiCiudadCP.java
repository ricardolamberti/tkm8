package pss.common.regions.divitions;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCiudadCP extends JWin {




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCiudadCP() throws Exception {
  }

  @Override
	public JRecord     ObtenerDato() throws Exception  { return new BizCiudadCP(); }
  @Override
	public int     GetNroIcono() throws Exception  { return 1; }
  @Override
	public String  GetTitle()    throws Exception  { return "Sub División Cod postal"; }
  @Override
	public Class<? extends JBaseForm>   getFormBase() throws Exception  { return FormCiudadCP.class; }
  @Override
	public String  getKeyField() throws Exception { return GetVision().equals(BizCiudadCP.ABM_CIUDAD)?"ciudad_id":"cod_postal"; }
  @Override
	public String  getDescripField()                { return "ciudad"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar" );
    addActionDelete ( 3, "Eliminar"  );
  }


  public String getTipoCiudad() throws Exception{
    String sPais = GetcDato().getPais();
    if (!sPais.equals("")){
      BizPais oPais = new BizPais();
      oPais.Read(sPais);
      return oPais.GetCiudad();
    }
    else return "";
  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizCiudadCP GetcDato() throws Exception {
    return (BizCiudadCP) this.getRecord();
  }
}
