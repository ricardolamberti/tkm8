package  pss.common.regions.nodes;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiRegion extends JWin {


  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiRegion() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizRegion(); }
  @Override
	public int GetNroIcono()       throws Exception { return 76; }
  @Override
	public String GetTitle()       throws Exception { return "Región"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormRegion.class; }
  @Override
	public String getKeyField()   throws Exception { return "region"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {

    addActionQuery( 1, "Consultar" );
    addActionUpdate( 2, "Modificar");
    addActionDelete ( 3, "Eliminar" );
    /*AddAction(6, "Sub-Regiones", new JAct() {
      public JBaseWin GetBWin() throws Exception { return ObtenerRegiones(); }
    }, 1, false, false, true, "Detail" );*/

//  AddAction(8 , "Nueva Region"   , new JAct() {public void Do() throws Exception {FormNuevaRegion();}}, 76, true, true);

    /*AddAction(10, "Vincular Region", new JAct() {
      public void Do() throws Exception {FormAltaRegion();}
    }, 91, true, true );*/

//    addAction(15, "Vincular Nodo"  , new JAct() {
//      @Override
//			public void Do() throws Exception {FormAltaNodo();}
//    }, 90, true, true );

  }

  /*public boolean ifAcceptDrop(JBaseWin zBaseWin) throws Exception {
    if ( zBaseWin instanceof GuiNodo    ) return this.ifHabilitado(10);
    if ( zBaseWin instanceof GuiRegion  ) return this.ifHabilitado(15);
    if ( zBaseWin instanceof GuiRegionNodo    ) return this.ifHabilitado(10);
    //if ( zBaseWin instanceof GuiRegionRegion  ) return this.ifHabilitado(15);
    return false;
  }

  public void Drop(JBaseWin zBaseWin) throws Exception {
    if ( zBaseWin instanceof GuiNodo  ) {
      GuiNodo oNodo = (GuiNodo) zBaseWin;
      GuiRegionNodo oRegionNodo = new GuiRegionNodo();
      oRegionNodo.GetcDato().pRegion.SetValor(GetcDato().pRegion.GetValor());
      oRegionNodo.GetcDato().pNodo.SetValor(oNodo.GetcDato().pNodo.GetValor());
      oRegionNodo.FormAlta();
      return;
    }
    if ( zBaseWin instanceof GuiRegionNodo  ) {
      GuiRegionNodo oRegionNodo1 = (GuiRegionNodo) zBaseWin;
      GuiRegionNodo oRegionNodo = new GuiRegionNodo();
      oRegionNodo.GetcDato().pRegion.SetValor(GetcDato().pRegion.GetValor());
      oRegionNodo.GetcDato().pNodo.SetValor(oRegionNodo1.GetcDato().pNodo.GetValor());
      oRegionNodo.FormAlta();
      return;
    }
    if ( zBaseWin instanceof GuiRegion  ) {
      GuiRegion oRegion = (GuiRegion) zBaseWin;
      GuiRegionRegion oRegionRegion = new GuiRegionRegion();
      oRegionRegion.GetcDato().pRegion1.SetValor(GetcDato().pRegion.GetValor());
      oRegionRegion.GetcDato().pRegion2.SetValor(oRegion.GetcDato().pRegion.GetValor());
      oRegionRegion.FormAlta();
      return;
    }
    if ( zBaseWin instanceof GuiRegionRegion  ) {
      GuiRegionRegion oRegionRegion1 = (GuiRegionRegion) zBaseWin;
      GuiRegionRegion oRegionRegion = new GuiRegionRegion();
      oRegionRegion.GetcDato().pRegion1.SetValor(GetcDato().pRegion.GetValor());
      oRegionRegion.GetcDato().pRegion2.SetValor(oRegionRegion1.GetcDato().pRegion2.GetValor());
      oRegionRegion.FormAlta();
      return;
    }
  }*/


/*  public void FormNuevaRegion() throws Exception {
     GuiRegionVincAlta oRegionVincAlta = new GuiRegionVincAlta();
     oRegionVincAlta.GetcDato().pRegionPadre.SetValor(GetcDato().pRegion2.GetValor());
     oRegionVincAlta.FormAlta();
  }
*/
  /*public JWins ObtenerRegiones() throws Exception {
    GuiRegionRegiones oRegionRegiones = new GuiRegionRegiones();
    oRegionRegiones.SetVision("Completo");
    oRegionRegiones.GetDatos().SetFiltros("region1", this.GetcDato().pRegion.GetValor());
    return oRegionRegiones;
  }*/

  /*public void FormAltaRegion() throws Exception {
     GuiRegionRegion oRegionRegion = new GuiRegionRegion();
     oRegionRegion.GetcDato().pRegion1.SetValor(GetcDato().pRegion.GetValor());
     oRegionRegion.FormAlta();
  }*/

//  public void FormAltaNodo() throws Exception {
//     GuiRegionNodo oRegionNodo = new GuiRegionNodo();
//     oRegionNodo.GetcDato().pRegion.setValue(GetcDato().pRegion.getValue());
//     oRegionNodo.showNewForm();
//  }

  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizRegion GetcDato() throws Exception {
    return (BizRegion) this.getRecord();
  }


}
