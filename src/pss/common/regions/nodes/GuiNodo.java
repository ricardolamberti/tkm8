package  pss.common.regions.nodes;

import pss.common.personalData.BizPersona;
import pss.common.personalData.GuiPersona;
import pss.common.personalData.types.GuiPersonaJuridica;
import pss.common.regions.divitions.GuiPais;
import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.JMMWin;
import pss.core.services.JExec;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.forms.JBaseForm;

public class GuiNodo extends JMMWin {


  GuiPais oPais = null;
  //private static GuiNodo oNodo = null;

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiNodo() throws Exception {
  }

  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizNodo(); }
  @Override
	public int GetNroIcono()       throws Exception { return 10072; }
  @Override
	public String GetTitle()       throws Exception { return "Nodo"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormNodo.class; }
  @Override
	public String getKeyField()   throws Exception { return "nodo"; }
  @Override
	public String getDescripField()                  { return "descripcion"; }

  //-------------------------------------------------------------------------//
  // Mapeo las acciones con las operaciones
  //-------------------------------------------------------------------------//
  @Override
	public void createActionMap() throws Exception {
	  	super.createActionMap();

/*    this.addAction(10, "Usuarios", null, 45, true, true);
    this.addAction(20, "Sucursales Hijas", null, 77, true, true);
    this.addAction(30, "Pais", null, 1, true, true);
    this.addAction(40, "PCs Asociadas", null, 8, true, true);
    this.addAction(50, "PCs Asociadas", null, 8, true, true);

    addAction(5,  "Usuarios", new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return ObtenerNodoUsuarios() ; }
    }, 45 , false, false, true, "Group" );

    addAction(6,  "Sucursales hijas", new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return ObtenerNodosHijos() ; }
    }, 77 , false, false, true, "Group" );

    addAction(10,  "País", new JAct() {
      @Override
			public void     Do()      throws Exception { FormPais(); }
    }, 1 , true, true );

    addAction( 8, "PCs Asociadas" , new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return ObtenerPCs(); }
    }, 722 , false, false, true, "Group" );

    addAction( 12, "Bases de Datos" , new JAct() {
      @Override
			public JBaseWin GetBWin() throws Exception { return ObtenerBasesDatos(); }
    }, GuiIcon.DATABASE_ICON , false, false, true, "Group" );

    addAction(22, "Cambio de Nodo",
                  new JAct() { @Override
									public void Do() throws Exception { FormCambioNodo(); } }
                  , 1, true, true);

    this.addAction(25, "Personales", null, 745, true, true);*/

  }
  
  
  @Override
	public boolean OkAction(BizAction zAct) throws Exception {
    if ( zAct.getId()==22) return BizUsuario.IsAdminUser();
    return true;
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==25) return new JActNew(this.ObtenerPersona(), 4);
  	
  	return super.getSubmit(a);
  }
  /*
  private JWin ObtenerModuloPss() throws Exception {
    GuiModuloPss oModuloPss = (GuiModuloPss) oLoader.loadClass(GuiModuloPss.class.getName()).newInstance();
    ClassLoader oL = oModuloPss.getClass().getClassLoader();
    return oModuloPss;
  }
*/
  //-------------------------------------------------------------------------//
  // Devuelvo el dato ya casteado
  //-------------------------------------------------------------------------//
  public BizNodo GetcDato() throws Exception {
    return (BizNodo) this.getRecord();
  }

  public JWins ObtenerPCs() throws Exception {
    GuiNodoPCs oNodoPCs = new GuiNodoPCs();
    oNodoPCs.getRecords().addFilter("nodo", GetcDato().GetNodo());
    oNodoPCs.SetTitle( "PCs Vinculadas" );
    return oNodoPCs;
  }

  public JWins ObtenerNodosHijos() throws Exception {
    GuiNodos oNodosSlaves = new GuiNodos();
    oNodosSlaves.getRecords().addFilter("nodo_padre", GetcDato().GetNodo());
    oNodosSlaves.SetTitle( "Sucursales vinculadas" );
    return oNodosSlaves;
  }

  public JWins ObtenerBasesDatos() throws Exception {
    GuiNodoDatabases oNodos = new GuiNodoDatabases();
    oNodos.getRecords().addFilter("company", GetcDato().getCompany());
    oNodos.getRecords().addFilter("nodo", GetcDato().GetNodo());
    oNodos.SetTitle( "Base de datos" );
    return oNodos;
  }

  public JWins ObtenerNodoUsuarios() throws Exception {
    GuiNodosUsuarios oNodos = new GuiNodosUsuarios();
    oNodos.getRecords().addFilter("company", GetcDato().getCompany());
    oNodos.getRecords().addFilter("nodo", GetcDato().GetNodo());
    oNodos.SetTitle( "Usuarios vinculados" );
    return oNodos;
  }

/*  public static GuiNodo GetNodoLocal() throws Exception {
    if ( oNodo != null ) return oNodo;
    GuiNodo oNodo = new GuiNodo();
    oNodo.setRecord(BizUsuario.getUsr().getObjNodo());
    return oNodo;
  }
*/
  public GuiPais ObtenerPais() throws Exception {
    GuiPais oPais = new GuiPais();
    oPais.setRecord(GetcDato().ObtenerPais());
    return oPais;
  }


  public void FormPais() throws Exception {
    GuiPais oPais = new GuiPais();
    oPais.GetcDato().Read(GetcDato().GetPais());
    oPais.showQueryForm();
  }
//  public void FormCambioNodo() throws Exception {
//    GuiChangeNode oCambioNodo = new GuiChangeNode();
//    oCambioNodo.GetcDato().setSourceCompany(this.GetcDato().getCompany());
//    oCambioNodo.GetcDato().setSourceNode(this.GetcDato().GetNodo());    
//    oCambioNodo.showNewForm();
//  }
  public GuiPersona ObtenerPersona() throws Exception {
    GuiPersona oPersona = new GuiPersonaJuridica();
    oPersona.getRecord().dontThrowException(true);
    if (!oPersona.GetcDato().Read(GetcDato().getIdPersona()))
    	oPersona.setDropListener(this);
    return oPersona;
  }
  @Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
  	if (zBaseWin instanceof GuiPersona) execDropPersona(zBaseWin);
  	return null;
  }
  public void execDropPersona(final JBaseWin zBaseWin) throws Exception {
    JExec oExec = new JExec(null, null) { @Override
		public void Do() throws Exception { dropPersona(zBaseWin); } };
    oExec.execute();
  }
  public void dropPersona(JBaseWin zBaseWin) throws Exception {
  	GuiPersona oPersona = (GuiPersona)zBaseWin;
  	oPersona.GetcDato().processInsert();
  	this.GetcDato().SetPersona(oPersona.GetcDato().GetPersona());
  	this.GetcDato().update();
  }

  public static GuiNodo createNodoAll(String company, String pais) throws Exception {
		GuiNodo all =  new GuiNodo();
		all.GetcDato().setCompany(company);
		all.GetcDato().setPais(pais);
		all.GetcDato().setNodo("ALL");
		BizPersona p = new BizPersona();
		p.SetNombre("Todas");
		all.GetcDato().setObjPerson(p);
		all.getDescripFieldValue();
		return all;
  }

}
