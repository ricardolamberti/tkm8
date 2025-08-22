package pss.bsp.consolid.model.report;

import pss.bsp.dk.GuiClienteDKs;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormNewReportDk extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormNewReportDk() throws Exception {

	}

	public GuiReportDk GetWin() {
		return (GuiReportDk) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemCombo( "DK", UINT, OPT, "id_dk" , new JControlCombo() {
	    	@Override
	    	public JWins getRecords(boolean one) throws Exception {
	    		GuiClienteDKs dks= new GuiClienteDKs();
	    		dks.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
	    		dks.getRecords().addFilter("reporte4",true);
	    		if (one)
	    			dks.getRecords().addFilter("id",GetWin().GetcDato().getIdDK());
	    		
	    		return dks;
	    	}
	    }).setFirstOcur(true).setSizeColumns(4);
	    AddItemEdit( null, CHAR, OPT, "generated_report" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "original_report" ).setHide(true);
	  	AddItemDateTime("Fecha desde", DATE, REQ, "date_from" );
		  AddItemDateTime("Fecha hasta", DATE, REQ, "date_to" );
		  AddItemFile( "Archivo", CHAR, REQ, "original_report_file" );
		
	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
