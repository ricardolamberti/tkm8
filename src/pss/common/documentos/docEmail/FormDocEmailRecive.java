package pss.common.documentos.docEmail;

import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormDocEmailRecive extends JBaseForm {


@Override
public boolean isFullSize() throws Exception {
	return true;
}

	  /**
	   * Constructor de la Clase
	   */
  public FormDocEmailRecive() throws Exception {
  }

	  public GuiDocEmail GetWin() { return (GuiDocEmail) getBaseWin(); }


	  /**
	   * Linkeo los campos con la variables del form
	   */
	  public void InicializarPanel( JWin zWin ) throws Exception {

//	    JFormControl c=AddItem(getJLegajo(), CHAR, OPT, "id_doc_rel", new JControlCombo() {
//	    	public JWins getRecords(boolean one) throws Exception {
//	    		return getLegajos(one);
//	    	}
//	    });
//	    c.setRefreshForm(true);
//	    if (GetWin().GetcDato().getObjTramiteOrigen()!=null && GetWin().GetcDato().getObjTramiteOrigen().getFirstLegajo()!=null)
//	    	c.SetValorDefault(GetWin().GetcDato().getObjTramiteOrigen().getFirstLegajo().getIddoc());
//	    else if (BizConsola.getConsola().getObjTramite()!=null && BizConsola.getConsola().getObjTramite().getFirstLegajo()!=null)
//	    	c.SetValorDefault(BizConsola.getConsola().getObjTramite().getFirstLegajo().getIddoc());
	    AddItemEdit( "Asunto", CHAR, REQ, "titulo");
	    AddItemDateTime( "Fecha", DATETIME, OPT, "fecha").SetReadOnly(true);
	    
	    JFormControl a = AddItemHtml( "Email", CHAR, OPT, "body", false,GetWin().GetcDato().getAnchoEditor(),GetWin().GetcDato().getMargenIzqEditor());
	    a.setKeepHeight(true);
	    a.setKeepWidth(true);
   
	    a=AddItemEdit( "Autor", CHAR, OPT, "mail_from" );
	    a.SetReadOnly(true);

	    if (this.GetWin().GetcDato().isTieneAdjuntos())
	    	this.AddItemTabPanel().AddItemTab( 310);

	  }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
