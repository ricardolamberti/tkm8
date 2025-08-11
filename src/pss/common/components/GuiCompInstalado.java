package  pss.common.components;

import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.winUI.forms.JBaseForm;

public class GuiCompInstalado extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiCompInstalado() throws Exception {
  }
  
  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCompInstalado(); }
  @Override
	public int GetNroIcono()   throws Exception { return 78; }
  @Override
	public String GetTitle()   throws Exception { return "Clase"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCompInstalado.class; }
  @Override
	public String  getKeyField() throws Exception { return "componente"; }
  @Override
	public String  getDescripField() { return "descripcion"; }


  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
    addActionQuery( 1, "Consultar"   ) ;
    addActionDelete ( 2, "Desinstalar" ) ;
    addAction(22, "Directorios", new JAct() {@Override
		public JBaseWin GetBWin() throws Exception {return ObtenerDirectorios();}}, 90, false, false, true, "Group" );
    addAction(25, "Subcomponentes", new JAct() {@Override
		public JBaseWin GetBWin() throws Exception {return ObtenerComponentes();}}, 78, false, false, true, "Detail" );
//    AddAction(25, "Dependencias", new JAct() {public JBaseWin GetBWin() throws Exception {return ObtenerDependencias();}}, 90, false, false, true, "Group" );
  }


  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizCompInstalado GetcDato() throws Exception {
    return (BizCompInstalado) this.getRecord();
  }

  public JWins ObtenerComponentes() throws Exception {
    GuiCompInstalados oComps = new GuiCompInstalados();
    oComps.getRecords().addFilter("comp_padre", GetcDato().pComponente.getValue());
    oComps.SetVision("Completo");
    return oComps;
  }

/*
  public JWins ObtenerDependencias() throws Exception {
    GuiDependencias oDependencias = new GuiDependencias();
    oDependencias.GetDatos().SetFiltros("componente", GetcDato().pComponente.GetValor());
    return oTablas;
  }
*/
  public JWin ObtenerDirectorios() throws Exception {
    GuiDirectorio oDirectorio = new GuiDirectorio();
    oDirectorio.GetcDato().pDirectorio.setValue(GetcDato().pComponente.getValue());
    return oDirectorio;
  }




}
