package pss.tourism.pnr;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormBooking  extends JBaseForm {


private static final long serialVersionUID = 1446641998059L;


/**
   * Constructor de la Clase
   */
  public FormBooking() throws Exception {
 }

  public GuiBooking getWin() { return (GuiBooking) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "interface_id" ).setHide(true);
    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "codigosegmento", CHAR, REQ, "codigosegmento" ).setSizeColumns(4);
      row = AddItemRow();
    row.AddItemEdit( "estado", CHAR, REQ, "estado" ).setSizeColumns(3);
    row.AddItemEdit( "codigopnr", CHAR, REQ, "codigopnr" ).setSizeColumns(3);
    row.AddItemEdit( "segmento_ini", CHAR, REQ, "segmento_ini" ).setSizeColumns(2);
    row = AddItemRow();
    row.AddItemEdit( "clase", CHAR, REQ, "clase" ).setSizeColumns(4);
    row.AddItemEdit( "tipoequipo", CHAR, REQ, "tipoequipo" ).setSizeColumns(4);
     row.AddItemEdit( "numerovuelo", CHAR, REQ, "numerovuelo" ).setSizeColumns(4);
    row.AddItemCheck( "chckbxEmitido", OPT, "emitido" ).setSizeColumns(3);
    row.AddItemEdit( "codigocomida", CHAR, REQ, "codigocomida" ).setSizeColumns(3);
    row.AddItemEdit( "codigoentretenimiento", CHAR, REQ, "codigoentretenimiento" ).setSizeColumns(3);
    row.AddItemEdit( "tipo_segmento", CHAR, REQ, "tipo_segmento" ).setSizeColumns(3);
    row = AddItemRow();
    row.AddItemWinLov( "despegue", CHAR, REQ, "despegue" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getDespegue(bOneRow);
    	}
    });
    row = AddItemRow();
    row.AddItemEdit( "fechadespegue", CHAR, REQ, "fechadespegue" ).setSizeColumns(4);
    row.AddItemEdit( "horadespegue", CHAR, REQ, "horadespegue" ).setSizeColumns(4);
  
    row.AddItemEdit( "duracionvuelo", CHAR, REQ, "duracionvuelo" ).setSizeColumns(4);
    row = AddItemRow();
    row.AddItemWinLov( "arrivo", CHAR, REQ, "arrivo" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getArrivo(bOneRow);
    	}
    });
    row = AddItemRow();
    row.AddItemEdit( "fechaarrivo", CHAR, REQ, "fechaarrivo" ).setSizeColumns(4);
    row.AddItemEdit( "horaarrivo", CHAR, REQ, "horaarrivo" ).setSizeColumns(4);
    row = AddItemRow();
    row.AddItemWinLov( "carrier", CHAR, REQ, "carrier" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolinea(bOneRow);
    	}
    });
  
    row.AddItemTabPanel().AddItemList(120);
    } 
  
  public JWins getDespegue(boolean one) throws Exception {
  	GuiAirports wins = new GuiAirports();
  	if (one) {
  		wins.getRecords().addFilter("code", getWin().GetcDato().getDespegue());
  	}
  	return wins;
  }
  public JWins getArrivo(boolean one) throws Exception {
  	GuiAirports wins = new GuiAirports();
  	if (one) {
  		wins.getRecords().addFilter("code", getWin().GetcDato().getArrivo());
  	}
  	return wins;
  }
  public JWins getAerolinea(boolean one) throws Exception {
  	GuiCarriers wins = new GuiCarriers();
  	if (one) {
  		wins.getRecords().addFilter("carrier", getWin().GetcDato().getCarrier());
  	}
  	return wins;
  }
} 