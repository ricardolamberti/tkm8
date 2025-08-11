package pss.common.customList.config.informe;

import pss.common.customList.config.customlist.GuiCustomList;
import pss.common.customList.config.field.campo.BizCampos;
import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiInformes extends JWins {

	private GuiCustomList customList;
	
	public void setObjCustomList(GuiCustomList value) {
		this.customList=value;
	}
	
 

  /**
   * Constructor de la Clase
   */
  public GuiInformes() throws Exception {
  }

	@Override
	public void setRecords(JRecords zDatos) throws Exception {
		if (zDatos==null)	return;
		if (((BizInformes)zDatos).getObjParentCustomList()!=null) {
			GuiCustomList w = new GuiCustomList();
			w.setRecord(((BizInformes)zDatos).getObjParentCustomList());
			setObjCustomList(w);

		}
		super.setRecords(zDatos);
	}
  public int     GetNroIcono() throws Exception  { return 10064; } 
  public String  GetTitle()    throws Exception  { return "Informes"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiInforme.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Informe" );
  }


  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddIcono("");
    zLista.AddColumnaLista("descripcion");
  }
  

}
