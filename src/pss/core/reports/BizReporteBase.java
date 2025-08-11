package pss.core.reports;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.win.JWins;

public class BizReporteBase extends JBDReportes {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

	JString pCompany = new JString();
	JString pNodo=new JString();	

	public void setCompany(String value) throws Exception {pCompany.setValue(value);}
	public void setNodo(String value) throws Exception {pNodo.setValue(value);}
	

	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
	public BizReporteBase() throws Exception {
	}
  public void createProperties() throws Exception {
  	addItem("company", pCompany);
    addItem("nodo", pNodo) ;

  }

	public void createFixedProperties() throws Exception {
		addFixedItem( FIELD, "company", "Empresa", true, false, 5);
		addFixedItem(VIRTUAL, "nodo", "Estación", true, false, 30);
	}


  public String GetTable() { return "";}

  @Override
  protected JReport obtenerReporte() throws Exception {
  	JReport oRep = JReport.createReport(this.getWins()) ;
  	oRep.SetConfiguracionGeneral(true) ;
  	return oRep ;
  }
  
  protected JWins obtenerWins() throws Exception {
  	return null;
  }  
  protected void verificarRestricciones() throws Exception {
  }

  protected void configurarFormatos() throws Exception {
  }

  private void configurarSecciones(boolean zValor) throws Exception {
  }
  
  protected void configurarControles() throws Exception {
  	configurarSecciones(true);
    
    if (pCompany == null ) 
    		this.setCompany(BizUsuario.getUsr().getCompany()) ;

    getReport().setLabel("TITLE",this.getWins().GetTitle());
    getReport().setAlternativeColorRow();
   
  }


  protected void configurarSQL() throws Exception {
  }
}
