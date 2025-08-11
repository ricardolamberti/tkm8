package  pss.common.security.reports;

import pss.core.reports.JBDReportes;
import pss.core.reports.JReport;
import pss.core.reports.JReportesCommon;


public class BizReporteRolVinculado extends JBDReportes {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizReporteRolVinculado() throws Exception {
  }

  @Override
	public String GetTable() {return ""; }

  @Override
	protected JReport obtenerReporte() throws Exception {
    return new JReportesCommon("security/reports/templates/RolVinculado.jod", JReport.DS_TYPE_JDBC);
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
    String sQuery = "SELECT rolA.descripcion as rol,";
    sQuery += " rolB.descripcion as rolVinculado ";
    sQuery += " FROM ";
    sQuery += " seg_rol_vinculado rolV, ";
    sQuery += " seg_rol rolA, ";
    sQuery += " seg_rol rolB ";
    sQuery += " WHERE ";
    sQuery += " rolV.rol = rolA.rol AND ";
    sQuery += " rolV.rol_vinculado = rolB.rol ";

     sQuery += " ORDER BY rol,rolVinculado ";
  }
}


