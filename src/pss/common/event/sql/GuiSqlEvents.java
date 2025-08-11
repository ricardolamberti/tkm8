package  pss.common.event.sql;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;

public class GuiSqlEvents extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEvents() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 15019; } 
  public String  GetTitle()    throws Exception  { return "Indicadores"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSqlEvent.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
  //  addActionNew( 1, "Nuevo Registro" );
   	this.addAction(10, "Forzar checkeo", null, 15018, true, true, true, "Group" );
   	super.createActionMap();
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==10 ) return new JActSubmit(this.getWinRef()) {
  		@Override
  		public void submit() throws Exception {
  			BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().ExecCheckForEvents(BizUsuario.getUsr().getCompany());
	//			BizBSPCompany.ExecCheckForEvents();

  		}
  	};
		return null;
	}

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (BizUsuario.getUsr().isAdminUser())
			zFiltros.addComboResponsive("Agencia", "company", new GuiCompanies());

		zFiltros.addEditResponsive("Descripcion", "descripcion").setOperator("ilike");
  	super.ConfigurarFiltros(zFiltros);
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	JGrupoColumnaLista l1=zLista.AddGrupoColumnaLista("Indicadores");
  	JGrupoColumnaLista l2=zLista.AddGrupoColumnaLista("Info de aviso");
		if (BizUsuario.getUsr().isAdminUser())
			zLista.AddColumnaLista("company").setGrupo(l1);

  	if (GetVision().equals("SYSTEM")) {
      zLista.AddColumnaLista("company").setGrupo(l1);;
  		
  	}
    zLista.AddColumnaLista("descripcion").setGrupo(l1);
    zLista.AddColumnaLista("valor").setGrupo(l1);
    zLista.AddColumnaLista("var_valor").setGrupo(l1);
    zLista.AddColumnaLista("var_porc").setGrupo(l1);
    zLista.AddColumnaLista("tendenciaview").setGrupo(l1);
    zLista.AddColumnaLista("valor_minimo").setGrupo(l2);
    zLista.AddColumnaLista("valor_emergencia").setGrupo(l2);
    zLista.AddColumnaLista("valor_aviso").setGrupo(l2);

  }

}
