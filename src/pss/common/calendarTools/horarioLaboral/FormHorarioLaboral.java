package  pss.common.calendarTools.horarioLaboral;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormHorarioLaboral extends JBaseForm {


  public FormHorarioLaboral() throws Exception {
  }

  public GuiHorarioLaboral getWin() { return (GuiHorarioLaboral) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCheck( "trabaja_lunes", REQ, "trabaja_lunes").SetValorDefault(true); 	
    AddItemCheck( "trabaja_martes", REQ, "trabaja_martes").SetValorDefault(true); 	
    AddItemCheck( "trabaja_miercoles", REQ, "trabaja_miercoles").SetValorDefault(true); 	
    AddItemCheck( "trabaja_jueves", REQ, "trabaja_jueves").SetValorDefault(true); 	
    AddItemCheck( "trabaja_viernes", REQ, "trabaja_viernes").SetValorDefault(true); 	
    AddItemCheck( "trabaja_sabado", REQ, "trabaja_sabado").SetValorDefault(false); 	
    AddItemCheck( "trabaja_domingo", REQ, "trabaja_domingo").SetValorDefault(false); 	
    AddItemEdit( "horario_entrada", CHAR, REQ, "horario_entrada" );
    AddItemEdit( "horario_salida", CHAR, REQ, "horario_salida" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );

  } 
  
}
