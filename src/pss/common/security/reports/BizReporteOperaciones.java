package  pss.common.security.reports;

import pss.common.regions.multilanguage.JLanguage;
import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;
import pss.core.services.fields.JString;

public class BizReporteOperaciones extends JBDReportes {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

  JString     pOrden     = new JString();
//  private String sQuery = null;
//  private String sOrder = "";
  int         iOrdenamiento;

  public void setOrden(int iOrden){iOrdenamiento=iOrden;}

  static final String[] vORDEN = new String[] {"Descripción de Operación","Operación"};

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizReporteOperaciones() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem("ordenamiento"    , pOrden  ) ;
  }

  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "ordenamiento", "ordenamiento", true, false, 100 ) ;
  }

  @Override
	public String GetTable() {return ""; }

  @Override
	protected void verificarRestricciones() throws Exception {
  }

  @Override
	protected void configurarFormatos() throws Exception {
  }

  @Override
	protected JReport obtenerReporte() throws Exception {
    return new JReportesCommon("security/reports/templates/Operaciones.jod", JReport.DS_TYPE_JDBC);
  }

  @Override
	protected void configurarControles() throws Exception {
    getReport().setLabel("ORDENAMIENTO","Ordenamiento: ^" +JLanguage.translate(vORDEN[iOrdenamiento]));
    //identificar el modo de ordenamiento
    if(pOrden.getValue().equals("Operacion")){
     getReport().setVisible("FLD_OPERACION_OP",true);
     getReport().setVisible("FLD_DESCRIPCION_OP",true);
     getReport().setVisible("FLD_OPERACION_DES",false);
     getReport().setVisible("FLD_DESCRIPCION_DES",false);

     getReport().setVisible("LBL_OPERACION_OP",true);
     getReport().setVisible("LBL_DESCRIPCION_OP",true);
     getReport().setVisible("LBL_OPERACION_DES",false);
     getReport().setVisible("LBL_DESCRIPCION_DES",false);
    }
    else{
     getReport().setVisible("FLD_OPERACION_OP",false);
     getReport().setVisible("FLD_DESCRIPCION_OP",false);
     getReport().setVisible("FLD_OPERACION_DES",true);
     getReport().setVisible("FLD_DESCRIPCION_DES",true);

     getReport().setVisible("LBL_OPERACION_OP",false);
     getReport().setVisible("LBL_DESCRIPCION_OP",false);
     getReport().setVisible("LBL_OPERACION_DES",true);
     getReport().setVisible("LBL_DESCRIPCION_DES",true);

    }

  }
/*
  public void processInsert() throws Exception {

    String sCampo = null;
    String sRol = null;
    String sOrdenamiento = "Ordenamiento";

    // crear reporte rol
    JReportesCore getReport() = new JReportesCore("Seguridad/Reportes/Operaciones.jod", JReport.DS_TYPE_JDBC);

    getReport().setLabel(sOrdenamiento.toUpperCase(),pOrden.GetValor()  );
    //identificar el modo de ordenamiento
    if(pOrden.GetValor().equals("Operacion")){
     sOrder = " ORDER BY operacion ";
     getReport().setVisible("FLD_OPERACION_OP",true);
     getReport().setVisible("FLD_DESCRIPCION_OP",true);
     getReport().setVisible("FLD_OPERACION_DES",false);
     getReport().setVisible("FLD_DESCRIPCION_DES",false);

     getReport().setVisible("LBL_OPERACION_OP",true);
     getReport().setVisible("LBL_DESCRIPCION_OP",true);
     getReport().setVisible("LBL_OPERACION_DES",false);
     getReport().setVisible("LBL_DESCRIPCION_DES",false);
    }
    else{
     sOrder = " ORDER BY descripcion ";
     getReport().setVisible("FLD_OPERACION_OP",false);
     getReport().setVisible("FLD_DESCRIPCION_OP",false);
     getReport().setVisible("FLD_OPERACION_DES",true);
     getReport().setVisible("FLD_DESCRIPCION_DES",true);

     getReport().setVisible("LBL_OPERACION_OP",false);
     getReport().setVisible("LBL_DESCRIPCION_OP",false);
     getReport().setVisible("LBL_OPERACION_DES",true);
     getReport().setVisible("LBL_DESCRIPCION_DES",true);

    }

    sQuery= getReport().getImportedSQLQuery("SEC_01");
    getReport().setSQLQueryOrder("SEC_01", sOrder);
    // ejecutar report
    getReport().DgetReport()();

  }
*/
  @Override
	protected void configurarSQL() throws Exception {
    //String sCampo = null;
    String sQuery = "SELECT seg.operacion as operacion,";
    sQuery += " seg.descripcion as descripcion ";
    sQuery += " FROM ";
    sQuery += " seg_operacion seg ";


    if(iOrdenamiento==1){
     sQuery += " ORDER BY operacion";
    getReport().setImportedSQLQuery("SEC_01",sQuery);
    }
    else if(iOrdenamiento == 0){
     sQuery += " ORDER BY descripcion";
     getReport().setImportedSQLQuery("SEC_01",sQuery);
    }


  }
}


