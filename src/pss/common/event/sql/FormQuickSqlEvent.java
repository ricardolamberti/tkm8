package  pss.common.event.sql;

import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageResponsive;

public class FormQuickSqlEvent  extends JBaseForm {

	  public FormQuickSqlEvent() throws Exception {
	  }

	  public GuiSqlEvent getWin() { return (GuiSqlEvent) getBaseWin(); }


	  public void InicializarPanel( JWin zWin ) throws Exception {
			setAutoRefresh(true, 10000, "SQLEVENT");
	    AddItemEdit( "company", CHAR, OPT, "company" );
	    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
//	    AddItem( getJTextField().getTextArea(), CHAR, OPT, "observacion" );
	   AddItemDateTime( "fecha_emergencia", DATE, OPT, "fecha_emergencia" );
	    AddItemDateTime( "fecha_aviso", DATE, OPT, "fecha_aviso" );
	    AddItemDateTime( "fecha_minimo", DATE, OPT, "fecha_minimo" );
//	    AddItem( valor_emergencia, FLOAT, OPT, "valor_emergencia" );
//	    AddItem( valor_aviso, FLOAT, OPT, "valor_aviso" );
//	    AddItem( valor_minimo, FLOAT, OPT, "valor_minimo" );
	    AddItemEdit( "consulta", CHAR, OPT, "consulta" );
	    AddItemEdit( "campo", CHAR, OPT, "campo" );
	    AddItemEdit( "id", ULONG, OPT, "id" );

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
// 			if (getWin().GetcDato().isOK())
// 				jLabel.setText("Act."+JDateTools.DateToString(getWin().GetcDato().getFechaUpdate(), JDateTools.DEFAULT_DATE_FORMAT + " " + JDateTools.DEFAULT_HOUR_FORMAT));
// 			else
// 				jLabel.setText(getWin().GetcDato().getEstado());
  		super.checkControls(sControlId);
  	}
	}  
