package  pss.common.calendarTools.diasNoLaborales;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormDiaNoLaborable extends JBaseForm {
 

  public GuiDiaNoLaborable getWin() { return (GuiDiaNoLaborable) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" );
    // AddItem( id_dia_no_laborable, UINT, REQ, "id_dia_no_laborable" );
    AddItemEdit( "fecha_desde", DATE, REQ, "fecha_desde" );
    AddItemEdit( "fecha_hasta", DATE, OPT, "fecha_hasta" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    // AddItem( tipo_dia_no_laborable, CHAR, REQ, "tipo_dia_no_laborable" );

    AddItemCombo( "tipo_dia_no_laborable", CHAR, REQ, "tipo_dia_no_laborable", new JControlCombo() {@Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
    		return ObtenerTipoDiaNoLaborable();
    	}
    });   
    
    AddItemEdit( "movil", CHAR, OPT, "movil").SetValorDefault(false);

  } 
  
  public JWins ObtenerTipoDiaNoLaborable() throws Exception {
    return  JWins.CreateVirtualWins(BizDiaNoLaborable.ObtenerTipoDiaNoLaborable());
  } 
  
} 
