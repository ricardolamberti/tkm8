package  pss.bsp.consola.referencia;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;

public class GuiReferencias extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiReferencias() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10055; } 
  public String  GetTitle()    throws Exception  { return "Referencias"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiReferencia.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
 //   addActionNew( 1, "Nuevo Registro" );
  }  	


  static String[][] referenciasConsol = {
  			{"943","Registros Correctos"},
  			{"940","Registros con diferencias"},
   			{"939","Registros Solo en BSP"},
  			{"941","Registros Solo en BackOffice"},
  			{"804","Texto en negro: La conciliacion fue exitosa"},
  			{"805","Texto en azul: Se muestra el dato de BSP a modo informativo"},
  			{"800","Texto en rojo: Diferencia de conciliacion"}
  };
  
  static String[][] referenciasDifer = {
  	{"943","Registros Correctos"},
 		{"939","Registros con diferencias"},
		{"804","Texto en negro: sin diferencias"},
		{"800","Texto en rojo: con diferencias"}
	}; 
  
  static GuiReferencias guiReferenciasConsol;
  static GuiReferencias guiReferenciasDifer;
  
  public static GuiReferencias getReferenciasDifer() throws Exception {
  	GuiReferencias guiref= new GuiReferencias();
  	guiref.SetEstatico(true);
  	int i=0;
  	for(String[] ref : referenciasDifer) {
    	BizReferencia r = new BizReferencia();
    	r.setId(i++);
    	r.setIcono(Long.parseLong(ref[0]));
    	r.setDescripcion(ref[1]);
    	GuiReferencia gref = new GuiReferencia();
    	gref.SetBaseDato(r);
    	guiref.addRecord(gref);
   	}
  	return guiReferenciasDifer=guiref;
 }
 
  public static GuiReferencias getReferenciasConsol() throws Exception {
  	GuiReferencias guiref= new GuiReferencias();
  	guiref.SetEstatico(true);
  	int i=0;
  	for(String[] ref : referenciasConsol) {
    	BizReferencia r = new BizReferencia();
    	r.setId(i++);
    	r.setIcono(Long.parseLong(ref[0]));
    	r.setDescripcion(ref[1]);
    	GuiReferencia gref = new GuiReferencia();
    	gref.SetBaseDato(r);
    	guiref.addRecord(gref);
   	}
  	return guiReferenciasConsol=guiref;
 }

  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	zLista.AddColumnaLista("descripcion");
  }

}
