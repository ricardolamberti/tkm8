package  pss.common.event.sql;

import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormSqlEvent extends JBaseForm {

  public FormSqlEvent() throws Exception {
  }

  public GuiSqlEvent getWin() { return (GuiSqlEvent) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
		JFormControlResponsive c;
  	this.setAutoRefresh(true, 10000, "SQLEVENT");
    AddItemEdit( "Company", CHAR, OPT, "company" );
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "Estado", CHAR, OPT, "estado" );
    AddItemDateTime( "fecha desde", DATE, OPT, "fdesde" ).SetReadOnly(true);
    AddItemDateTime( "fecha hasta", DATE, OPT, "fhasta" ).SetReadOnly(true);
    AddItemArea( "Observacion", CHAR, OPT, "observacion" );
    AddItemDateTime( "fecha_emergencia", DATE, OPT, "fecha_emergencia" );
    AddItemDateTime( "fecha_aviso", DATE, OPT, "fecha_aviso" );
    AddItemDateTime( "fecha_minimo", DATE, OPT, "fecha_minimo" );
    AddItemEdit( "valor_emergencia", FLOAT, OPT, "valor_emergencia" );
    AddItemEdit( "valor_aviso", FLOAT, OPT, "valor_aviso" );
    AddItemEdit( "valor_minimo", FLOAT, OPT, "valor_minimo" );
    AddItemEdit( "consulta", CHAR, OPT, "consulta" );
    AddItemEdit( "campo", CHAR, OPT, "campo" );
    AddItemEdit( "id", ULONG, OPT, "id" );
    c=AddItemEdit( "valor", FLOAT, OPT, "valor" );
//    s.setSkinStereotype("big_label");
    c.bold();
    c.SetValorDefault(getWin().GetcDato().getValor());
    c.SetReadOnly(true);
    JFormTabPanelResponsive tab = this.AddItemTabPanel();
    tab.AddItemTab(10);
    tab.AddItemTab(40);
    tab.AddItemTab(45);
    
    JFormImageResponsive i=AddItemImage( "Imagen","imagen" );
    i.setSource(JPssImage.SOURCE_SCRIPT);
    i.setKeepHeight(true);
    i.setKeepWidth(true);

  }
  
  @Override
  	public void OnPostShow() throws Exception {
  		checkControls("");
  		super.OnPostShow();
  	}
  @Override
  	public void checkControls(String sControlId) throws Exception {
//			if (getWin().GetcDato().isOK())
// 				jLabel.setText("Act."+JDateTools.DateToString(getWin().GetcDato().getFechaUpdate(), JDateTools.DEFAULT_DATE_FORMAT + " " + JDateTools.DEFAULT_HOUR_FORMAT));
// 			else
// 				jLabel.setText(getWin().GetcDato().getEstado());
  		super.checkControls(sControlId);
  	}

} 
