package  pss.common.security.reports;

import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;


public class BizReporteOperacionesRol extends JBDReportes {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizReporteOperacionesRol() throws Exception {
  }

  @Override
	public String GetTable() {return ""; }

  @Override
	protected JReport obtenerReporte() throws Exception {
    return new JReportesCommon("security/reports/templates/OperacionesRol.jod", JReport.DS_TYPE_JDBC);
  }

  @Override
	protected void verificarRestricciones() throws Exception {
  }

  @Override
	protected void configurarFormatos() throws Exception {
  }

  @Override
	protected void configurarControles() throws Exception {
  }


  @Override
	protected void configurarSQL() throws Exception {
    //String sCampo = null;
    String sQuery = "SELECT opr.operacion as operacion,";
    sQuery += " opr.clave_supervisor as clave_sup, ";
    sQuery += " rol.descripcion as descripcion_rol ";
    sQuery += " FROM ";
    sQuery += " seg_operacion_rol opr ";
    sQuery += " seg_rol rol ";
    sQuery += " WHERE ";
    sQuery += " a.rol=b.rol ";

     sQuery += " ORDER BY descripcion_rol,operacion";
  }

}


