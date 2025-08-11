package pss.common.customList.config.field.campo;

import javax.swing.JTree;

import pss.common.customList.config.dynamic.GuiDynamics;
import pss.common.customList.config.field.BizField;
import pss.common.customList.config.field.funciones.BizFuncion;
import pss.common.customList.config.field.operadores.BizOperador;
import pss.common.customList.config.field.operadores.GuiOperadores;
import pss.common.customList.config.relation.BizCampoGallery;
import pss.common.customList.config.relation.GuiCamposGallery;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormCampoStadistic   extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;
/**
   * Constructor de la Clase
   */
  public FormCampoStadistic() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  
	public JMap<String, String> getOptions() {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement("Campo", "N");
		map.addElement("Columna", "C");
		map.addElement("Fila", "F");
		return map;
	}

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
//    AddItem( jOrden, UINT, OPT, "orden" );
    AddItemEdit( null, UINT, OPT, "orden_padre" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "rel_id").setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_gallery").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato").setHide(true);
    AddItemEdit( null, CHAR, OPT, "nombre_columna").setHide(true);
    AddItemEdit( null, CHAR, OPT, "formato_param").setHide(true);
    AddItemEdit( null, CHAR, OPT, "porcentaje").setHide(true);
    AddItemEdit( null, CHAR, OPT, "ambito_porcentaje").setHide(true);
    AddItemEdit( null, CHAR, OPT, "colores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_background").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_foreground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_mayores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_topbackground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_top").setHide(true);
    AddItemEdit( null, CHAR, OPT, "mayores_a").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_menores").setHide(true);
    AddItemEdit( null, CHAR, OPT, "color_bottombackground").setHide(true);
    AddItemEdit( null, CHAR, OPT, "marca_bottom").setHide(true);
    AddItemEdit( null, CHAR, OPT, "menores_a").setHide(true);
    AddItemEdit( null, CHAR, OPT, "calc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "porc_diferencia").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_false").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_true").setHide(true);
    AddItemEdit( null, CHAR, OPT, "dinamico").setHide(true);
    AddItemEdit( null, CHAR, OPT, "nombre_filtro").setHide(true);
    AddItemEdit( null, CHAR, OPT, "campo_key").setHide(true);
    AddItemEdit( null, CHAR, OPT, "descr_campo").setHide(true);
    AddItemEdit( null, CHAR, OPT, "visible").setHide(true);
    AddItemEdit( null, CHAR, OPT, "orientacion").setHide(true);
    AddItemEdit( null, CHAR, OPT, "has_filter").setHide(true);
    AddItemEdit( null, CHAR, OPT, "funcion").setHide(true);
    AddItemEdit( null, CHAR, OPT, "operador").SetValorDefault("in").setHide(true);
    AddItemMultipleCheck( "Valor", CHAR, OPT, "value_edit",  getWin().GetcDato().getOptionsCombo());  
  }
  @Override
	public void OnPostShow() throws Exception {
  	super.OnPostShow();
	}
 


}  //  @jve:decl-index=0:visual-constraint="-1,16"
