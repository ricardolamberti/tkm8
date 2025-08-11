package  pss.common.personalData.personaRegistro;

import java.util.Date;

import pss.common.personalData.personaRegistro.registros.GuiRegistros;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormPersonaRegistro extends JBaseForm {


private static final long serialVersionUID = 1218227728984L;


/**
   * Constructor de la Clase
   */
  public FormPersonaRegistro() throws Exception {
   }

  public GuiPersonaRegistro GetWin() { return (GuiPersonaRegistro) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    JFormControl c;
  	AddItemEdit( null, ULONG, REQ, "id_persona" ).setHide(true);
    AddItemCombo( "Archivo", UINT, REQ, "id_registro", this.getRegistros() );
//    AddItem( jAnio, CHAR, REQ, "anio").setPopupIcon(false);
    AddItemEdit( "Tomo", CHAR, REQ, "tomo").setPopupIcon(false);
    AddItemEdit( "Folio", CHAR, REQ, "folio" ).setPopupIcon(false);
//    c=AddItem( jFolioBis, CHAR, REQ, "folio_bis" );
//    c.setPopupIcon(false);
//    c.SetValorDefault(0);
    AddItemEdit( "Nro", CHAR, REQ, "nro" ).setPopupIcon(false);
//    c=AddItem( jNroBis, CHAR, REQ, "nro_bis" );
//    c.setPopupIcon(false);
//    c.SetValorDefault(0);
    AddItemDateTime( "Fecha", DATE, REQ, "fecha").SetValorDefault(new Date());
  } 
  
  public JWins getRegistros() throws Exception {
  	GuiRegistros a = new GuiRegistros();
  	a.getRecords().addFilter("id", this.GetWin().GetcDato().getIdRegistro());
  	return a;
  }
 
}  //  @jve:decl-index=0:visual-constraint="-11,35"
