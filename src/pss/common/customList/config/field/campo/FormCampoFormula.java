package  pss.common.customList.config.field.campo;

import pss.common.customList.config.relation.GuiCamposGallery;
import pss.common.layout.JFieldSetWins;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.resolvers.JDoLayoutActionResolver;

public class FormCampoFormula extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


/**
   * Constructor de la Clase
   */
  public FormCampoFormula() throws Exception {
  }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "secuencia" ).setHide(true);
//    AddItem( jOrden, UINT, OPT, "orden" );
    AddItemEdit(null, CHAR, OPT, "serial_deep").setHide(true);
    AddItemEdit( null, CHAR, REQ, "record_owner" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "record_source" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "rel_id").setHide(true);

    AddItemEdit( "Campo", CHAR, OPT, "campo");
    JFormControl c = AddItemArea( "Formato", CHAR, OPT, "formato_param" , false, JDoLayoutActionResolver.getDatosPlantilla(getWin().GetcDato().getObjCustomList().getObjRelation().getClassTarget().getName(), JFieldSetWins.SECTOR_MAIN),
    		getWin().GetcDato().getObjCustomList().getObjRelation().getClassTarget().getName(),400,
    		0,0,0,0,null, 
    		false);

    AddItemEdit( "Nombre Col.", CHAR, OPT, "nombre_columna");
    AddItemCheck( "Visible", CHAR, OPT, "visible", "S","N").SetValorDefault(true);
    AddItemCheck( "Porcentaje", CHAR, OPT, "porcentaje", "S","N").SetValorDefault(false);
    c = AddItemCombo( "Funcion", CHAR, OPT, "funcion", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getFunciones(one);
    	}
    });
    c.SetValorDefault(BizCampo.FUNTION_FORMULA);
    
    if (this.getWin().GetcDato().getObjCustomList().isLista()) { 
    	c.setVisible(false);
     }
    c=AddItemCheck( "Tiene colores",  OPT, "colores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItemColor( "Color fondo", CHAR, OPT, "color_background");
    c=AddItemColor( "Color letras", CHAR, OPT, "color_foreground");
    c=AddItemCheck( "Marca mayores",  OPT, "marca_mayores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItemColor( "Color mayores fondo", CHAR, OPT, "color_topbackground");
    c.SetValorDefault("FF0000");
    c=AddItemCheck( "Marca Top",  OPT, "marca_top");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setVisible(getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=AddItemEdit( "Mayores a", CHAR, OPT, "mayores_a");
    c=AddItemCheck( "Marca menores",  OPT, "marca_menores");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c=AddItemColor( "Color menores fondo", CHAR, OPT, "color_bottombackground");
    c.SetValorDefault("00AA00");
    c=AddItemCheck(  "Marca fondo",  OPT, "marca_bottom");
    c.setRefreshForm(true);
    c.SetValorDefault(false);
    c.setVisible(getWin().GetcDato().getObjCustomList().isMatriz()?true:false);
    c=AddItemEdit( "Menores a", CHAR, OPT, "menores_a");

  }
public JWins getFunciones(boolean one) throws Exception {
	if (one) {
		return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
	}
	return JWins.createVirtualWinsFromMap(BizCampo.getFunctionAllMap());
	
}
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls(null);
  		super.OnPostShow();
  	}
  @Override
  	public void checkControls(String sControlId) throws Exception {
  		if (this.getControles().findControl("colores").getValue().equals("S")) {
  			this.getControles().findControl("color_background").edit(this.GetModo());
  			this.getControles().findControl("color_foreground").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_background").disable();
  			this.getControles().findControl("color_foreground").disable();
  		}
  		if (this.getControles().findControl("marca_mayores").getValue().equals("S")) {
  			this.getControles().findControl("color_topbackground").edit(this.GetModo());
  			this.getControles().findControl("marca_top").edit(this.GetModo());
    		if (this.getControles().findControl("marca_top").getValue().equals("S")) 
    			this.getControles().findControl("mayores_a").disable();
    		else
    			this.getControles().findControl("mayores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_topbackground").disable();
  			this.getControles().findControl("marca_top").disable();
  			this.getControles().findControl("mayores_a").disable();
  		}
  		if (this.getControles().findControl("marca_menores").getValue().equals("S")) {
  			this.getControles().findControl("color_bottombackground").edit(this.GetModo());
  			this.getControles().findControl("marca_bottom").edit(this.GetModo());
    		if (this.getControles().findControl("marca_bottom").getValue().equals("S")) 
    			this.getControles().findControl("menores_a").disable();
    		else
    			this.getControles().findControl("menores_a").edit(this.GetModo());
  		} else {
  			this.getControles().findControl("color_bottombackground").disable();
  			this.getControles().findControl("marca_bottom").disable();
  			this.getControles().findControl("menores_a").disable();
  		}
  		super.checkControls(sControlId);
  	}
  

  
  private JWins getCamposAll(boolean one) throws Exception {
  	GuiCamposGallery g = new GuiCamposGallery();
 		g.setRecords(this.getWin().GetcDato().getObjCustomList().getAllCampos((BizAction)null, true));
  	return g;
  }


}  //  @jve:decl-index=0:visual-constraint="-1,16"
