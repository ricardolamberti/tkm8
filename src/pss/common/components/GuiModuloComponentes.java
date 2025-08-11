package  pss.common.components;

import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;

public class GuiModuloComponentes extends GuiModulo {

  public GuiModuloComponentes() throws Exception {
    super();
    SetModuleName( "Componentes" );
    SetNroIcono  ( 78 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction(1, "Modulo Componentes", null, 1, false, false, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
    this.addAction(2, "Componentes", null, 1, false, false, true, "Group" );
    this.addAction(8, "Generar Version (Nivel Pais)", null, 1, true, true);
    this.addAction(9, "Generar Version (Nivel Negocio)", null, 1, true, true);
    this.addAction(10, "Generar Version (Nivel Nodo)", null, 1, true, true);
    this.addAction(12, "Generar Version (Sin Datos)", null, 1, true, true);
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1 ) return new JActQuery(this);
  	if ( a.getId()==2 ) return new JActWins(this.ObtenerComps());
  	if ( a.getId()==8 ) return new JActSubmit(this, a.getId()) {@Override
		public void submit() throws Exception {processGenerarVersionCountryLevel();}};  	
  	if ( a.getId()==9 ) return new JActSubmit(this, a.getId()) {@Override
		public void submit() throws Exception {processGenerarVersionBusinessLevel();}};  	
  	if ( a.getId()==10) return new JActSubmit(this, a.getId()) {@Override
		public void submit() throws Exception {processGenerarVersionNodeLevel();}};  	
  	if ( a.getId()==12) return new JActSubmit(this, a.getId()) {@Override
		public void submit() throws Exception {processGenerarVersionSinDatos();}};  	
  	return null;
  }
    

  private JWins ObtenerComps() throws Exception {
    GuiCompInstalados oComps = new GuiCompInstalados();
    oComps.getRecords().addFilter("comp_padre", "Pss");
    return oComps;
  }

  @Override
	public boolean isModuleComponent() throws Exception { return false; }

  public void processGenerarVersionCountryLevel() throws Exception { processVersion(JSetupParameters.NIVEL_DATOS_PAIS); }
  public void processGenerarVersionBusinessLevel() throws Exception { processVersion(JSetupParameters.NIVEL_DATOS_BUSINESS);}
  public void processGenerarVersionNodeLevel() throws Exception { processVersion(JSetupParameters.NIVEL_DATOS_NODO);}
  public void processGenerarVersionSinDatos() throws Exception { processVersion(JSetupParameters.NIVEL_SIN_DATOS);}

  public void processVersion(int level) throws Exception {
    BizCompAlta oComp = new BizCompAlta();
    oComp.pComp.setValue("pss");
    oComp.pIncludeTables.setValue(true);
    oComp.pRecursivo.setValue(true);
    oComp.pDataLevel.setValue(level);
    oComp.processInsert();
  }


}
