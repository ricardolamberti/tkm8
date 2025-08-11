package pss.core.win.modules;

import pss.common.components.BizCompInstalado;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.win.JWin;
import pss.core.win.JWins;

public class GuiModulos extends JWins {

//  private JArray    aModulos = new JArray();
//  public static  GuiModulos oModulos;

  //
  //   list of loaded module class names
  //
  private GuiModulo oModuloPadre = null;

  public GuiModulos() throws Exception {
  }
  
  @Override
	public Class<? extends JWin> GetClassWin() { return GuiModulo.class;}

  public void setModuloPadre(GuiModulo zComp) {
    oModuloPadre = zComp;
  }
  //-------------------------------------------------------------------------//
  // Armo el menu de la aplicacion
  //-------------------------------------------------------------------------//
//  public JBaseMenu ArmarMenu( JBaseMenu zMenu ) throws Exception {
//    firstRecord();
//    while ( nextRecord() ) {
//      GuiModulo oMod = (GuiModulo) getRecord();
//      JWinMenuGenerator.makeMenu( oMod, zMenu );
//    }
//    return zMenu;
//  }


  //-------------------------------------------------------------------------//
  // Armo la toolbar de la aplicacion
  //-------------------------------------------------------------------------//
/*  public JPanel ArmarToolBar( ) throws Exception {
    JPanel    oPanel = new JPanel();
    JBotonBar oBotonBar;

    JArray oEnum = GetItems();
    oEnum.FirstElement();
    while ( oEnum.HasMoreElements() ) {
      GuiModulo oMod = (GuiModulo) oEnum.NextElement();
      oBotonBar = new JBotonBar();
      oBotonBar.SetAcceptEnterOff();
      oPanel.add( oMod.ArmarBotonBar( this, oBotonBar ) );
    }
    return oPanel;
  }
*/

  //-------------------------------------------------------------------------//
  // Armo el arbol
  //-------------------------------------------------------------------------//
//  public void ExpandirArbol( JBaseWin zWin, JTree zArbol, DefaultMutableTreeNode zNode ) throws Exception {
//    FirstRecord();
//    while ( NextRecord() ) {
//      GuiModulo oMod = (GuiModulo) GetRecord();
//      AddNodo( zArbol,
//               zNode,
//               oMod,
//               oMod.toString(), null, true );
//    }
//  }



//  public void ReadAllDatos() throws Exception {
//    this.ClearStaticItems();
//    this.SetEstatico(true);
//    BizCompInstalados oComps = new BizCompInstalados();
//    oComps.SetFiltros("comp_padre",  this.GetDatos().GetFiltroValue("componente_padre"));
//    oComps.SetFiltros("dynamic_module", "S");
//    oComps.ReadAll();
//    while ( oComps.NextRecord() ) {
//      BizCompInstalado oComp = (BizCompInstalado) oComps.GetRecord();
//      GuiModulo oModulo = (GuiModulo) Class.forName(oComp.GetClase()).newInstance();
//      JDebugPrint.logInfo("Módulo : {" + oModulo.GetModuleName() + "} ... OK");
//      this.AddItem(oModulo);
//    }
//  }

  @Override
	public void readAll() throws Exception {
    this.ClearStaticItems();
    this.SetEstatico(true);
    JRecords comps = oModuloPadre.getCompAsociado().getSubComponentesModulos(true);
    comps.firstRecord();
    while ( comps.nextRecord() ) {
      BizCompInstalado comp = (BizCompInstalado) comps.getRecord();
      this.addRecord(comp.getModulo());
      PssLogger.logWait("Módulo cargado: ^" + comp.getModulo().GetModuleName());
    }

/*    BizCompInstalados oComps = new BizCompInstalados();
    oComps.SetFiltros("comp_padre",  this.GetDatos().GetFiltroValue("componente_padre"));
    oComps.SetFiltros("dynamic_module", "S");
    oComps.ReadAll();
    oComps.FirstRecord();
    while ( oComps.NextRecord() ) {
      GuiModulo oModulo = this.loadModule((BizCompInstalado) oComps.GetRecord());
      this.AddItem(oModulo);
    }
    }
    */
  }
  /*
  private GuiModulo loadModule(BizCompInstalado zInstalledComponent) throws Exception {
    GuiModulo oModulo;
    try {
      oModulo = zInstalledComponent.getModulo();
      if (!oLoadedModuleClassNames.containsElement(oModulo.getClass().getName())) {
        JDebugPrint.logInfo("Módulo cargado: {" + oModulo.GetModuleName() + "}");
        oLoadedModuleClassNames.addElement(oModulo.getClass().getName());
      }
    } catch (Exception ex) {
      JDebugPrint.logError(ex, "No se pudo cargar módulo, clase: {" + zInstalledComponent.GetClase() + "}");
      throw ex;
    }
    return oModulo;
  }
*/
}

