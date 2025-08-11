package  pss.bsp.consolid.model.feeDK;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormFeeDK extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormFeeDK() throws Exception {
  }

  public GuiFeeDK GetWin() { return (GuiFeeDK) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit(null, LONG, REQ, "id_dk" ).setHide(true);
    row = AddItemRow();
    row.AddItemEdit("Prioridad", LONG, REQ, "priority" ).setSizeColumns(4);
    row = AddItemRow();
    row.AddItemDateTime("Fecha Desde", DATE, REQ, "fecha_desde" ).setSizeColumns(4);
    row.AddItemDateTime("Fecha hasta", DATE, OPT, "fecha_hasta" ).setSizeColumns(4);
    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_aerolineas", "S","N","Neg","Neg").setSizeColumns(1);
    row.AddItemWinLov("Aerolineas",CHAR,OPT,"aerolineas", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getAerolineas(one);
    	}
	  }).setShowKey(true).setMultiple(true).setSizeColumns(11);

    //row.AddItemEdit( "Aerolineas", CHAR, OPT, "aerolineas" ).setSizeColumns(10);
    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_origen_pais", "S","N","Neg","Neg").setSizeColumns(1);
   // row.AddItemEdit( "Origen país", CHAR, OPT, "origen_pais" ).setSizeColumns(10);
    row.AddItemWinLov("Origen país",CHAR,OPT,"origen_pais", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getPaises(one);
    	}
	  }).setShowKey(true).setMultiple(true).setSizeColumns(11);

    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_origen_airport", "S","N","Neg","Neg").setSizeColumns(1);
   // row.AddItemEdit( "Origen aeropuerto", CHAR, OPT, "origen_airport" ).setSizeColumns(10);
    row.AddItemWinLov("Origen aeropuerto",CHAR,OPT,"origen_airport", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getAeropuertos(one);
    	}
	  }).setShowKey(true).setMultiple(true).setSizeColumns(11);
    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_destino_pais", "S","N","Neg","Neg").setSizeColumns(1);
   // row.AddItemEdit( "Destino país", CHAR, OPT, "destino_pais" ).setSizeColumns(10);
    row.AddItemWinLov("Destino país",CHAR,OPT,"destino_pais", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getPaises(one);
    	}
	  }).setShowKey(true).setMultiple(true).setSizeColumns(11);

    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_destino_airport", "S","N","Neg","Neg").setSizeColumns(1);
    //row.AddItemEdit( "Destino aeropuerto", CHAR, OPT, "destino_airport" ).setSizeColumns(10);
    row.AddItemWinLov("Destino aeropuerto",CHAR,OPT,"destino_airport", new JControlWin() {
    	@Override
    	public JWins getRecords(boolean one) throws Exception {
    		return getAeropuertos(one);
    	}
	  }).setShowKey(true).setMultiple(true).setSizeColumns(11);
    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_tipo_doc", "S","N","Neg","Neg").setSizeColumns(1);
    row.AddItemEdit( "Tipo documento", CHAR, OPT, "tipo_doc" ).setSizeColumns(11);
    row = AddItemRow();
    row.AddItemCheck( null, CHAR,OPT, "no_tipo_ticket", "S","N","Neg","Neg").setSizeColumns(1);
    row.AddItemEdit( "Tipo ticket", CHAR, OPT, "tipo_ticket" ).setSizeColumns(11);
    row = AddItemRow();
    row.AddItemThreeCheck("Dom/Internacional",CHAR, OPT, "dom_int" , "D","","I" ,"Domestico", "NO","Internacional").setSizeColumns(6);
    row = AddItemRow();
    row.AddItemCombo("Tipo Fee",CHAR, REQ, "tipo_fee" ).setSizeColumns(4);
    row = AddItemRow();
    row.AddItemEdit("Fee",FLOAT, REQ, "fee" ).setSizeColumns(4);
   
  }
  JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers events = new GuiCarriers();
  	if (one) events.getRecords().addFilter("carrier",GetWin().GetcDato().getAerolineas());
  	events.addOrderAdHoc("carrier", "ASC");
  	return events;
  }
  private JWins getPaises(boolean one) throws Exception {
  	GuiPaisesLista g = new GuiPaisesLista();
  	return g;
  }
  private JWins getAeropuertos(boolean one) throws Exception {
  	GuiAirports g = new GuiAirports();
 		return g;
  }
}  //  @jve:decl-index=0:visual-constraint="80,14"
