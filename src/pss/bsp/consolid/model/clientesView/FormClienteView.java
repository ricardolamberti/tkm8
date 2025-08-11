package pss.bsp.consolid.model.clientesView;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormClienteView extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;



  /**
   * Constructor de la Clase
   */
  public FormClienteView() throws Exception {
  }

  public GuiClienteView getWin() { return (GuiClienteView) getBaseWin(); }

  
  public void InicializarPanel(JWin zBase) throws Exception {
  	JFormPanelResponsive row = AddItemRow();
  	row.AddImageCard("Generar Sitio", JWebIcon.getResponsiveIcon("fas fa-home fa-3x"), "Crear Sitio", 50).setSizeColumns(3);
  	row.AddImageCard("Actualizar Sitio", JWebIcon.getResponsiveIcon("fas fa-home fa-3x"), "Refresca Sitio", 55).setSizeColumns(3);
  	row.AddImageCard("Habilitar", JWebIcon.getResponsiveIcon("fa fa-solid fa-play fa-3x"), "Permitir usar site", 60).setSizeColumns(3);
    row.AddImageCard("Deshabilitar", JWebIcon.getResponsiveIcon("fa fa-solid fa-stop fa-3x"), "Cancelar uso site", 70).setSizeColumns(3);
    row.AddImageCard("Limpiar Clave", JWebIcon.getResponsiveIcon("fa fa-solid fa-key fa-3x"), "Elim.clave y permite recrearla", 80).setSizeColumns(3);
   
    row = AddItemRow();
    JFormEditResponsive s =row.AddItemEdit("DK", CHAR,OPT,"customer_number" );
    s.setSizeColumns(3).setReadOnly(true);
    s.setSkinStereotype("big_label");
    s.setFontSize(25);
    s.setFontWeigth(JFormEditResponsive.FONT_WEIGHT_BOLD);
		row.AddItemEdit("Denominación", CHAR,OPT,"customer_name" ).setSizeColumns(6).setReadOnly(true);
    row = AddItemRow();
		row.AddItemEdit("Régimen fiscal", CHAR,OPT,"reg_fiscal" ).setSizeColumns(5).setReadOnly(true);
		row.AddItemEdit("Ref.Bancaria", CHAR,OPT,"ref_bancaria" ).setSizeColumns(5).setReadOnly(true);
		row.AddItemEdit("Cta.Bancaria", CHAR,OPT,"cta_bancaria" ).setSizeColumns(5).setReadOnly(true);
		row.AddItemEdit("Cta.Clave", CHAR,OPT,"cta_clave" ).setSizeColumns(5).setReadOnly(true);
	    row = AddItemRow();
		row.AddItemCheck("Site", CHAR,"has_company" ).setSizeColumns(3).setReadOnly(true);
		row.AddItemCheck("Habilitado", CHAR,"active" ).setSizeColumns(3).setReadOnly(true);
    row = AddItemRow();
    row.AddItemEdit("eMail", CHAR,OPT,"email" ).setSizeColumns(6);
    row = AddItemRow();
    row.AddItemCombo("Formato Invoice", CHAR,OPT,"formato" ).setSizeColumns(6);
    row = AddItemRow();
    row.AddItemCombo("Tipo comisión", OPT,CHAR,"comision" ).setSizeColumns(3);
    row = AddItemRow();
		row.AddItemCheck("Interfaz 1 (EDM917)", CHAR,"reporte1" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 2 (ETV917)", CHAR,"reporte2" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 3 (TIA917)", CHAR,"reporte3" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 4 (CTR917)", CHAR,"reporte4" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 5 (STR097)", CHAR,"reporte5" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 6 (AER917)", CHAR,"reporte6" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 7 (COR917)", CHAR,"reporte7" ).setSizeColumns(4);
		row.AddItemCheck("Interfaz 8 (TRP917)", CHAR,"reporte8" ).setSizeColumns(4);
		row.AddItemRow().AddItemDateTime("Ult.Acceso", DATE,OPT,"ult_acceso" ).setSizeColumns(4).setReadOnly(true);
		
		JFormTabPanelResponsive tabs= AddItemTabPanel();
		tabs.AddItemList(20);
		tabs.AddItemList(30);
		tabs.AddItemList(40);
		tabs.AddItemList(45);
		}
}  //  @jve:decl-index=0:visual-constraint="14,3"
