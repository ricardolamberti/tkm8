package pss.core.reports.jasper;

import java.sql.Connection;
import java.sql.ResultSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import pss.core.data.interfaces.connections.JBDato;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;


public class JReportDataSourceProvider {

  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////
  private static JReportDataSourceProvider INSTANCE;


  //////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC API
  //
  //////////////////////////////////////////////////////////////////////////////
  public static JReportDataSourceProvider getInstance() {
    if (INSTANCE==null) {
      INSTANCE = new JReportDataSourceProvider();
    }
    return INSTANCE;
  }

  /**
   * Retorna la única conexión que se utiliza para las consultas del JReport.
   * Cuando el driver utilizado es JDataConnect, se crea una conexión especial.
   * En otro caso, utiliza la misma conexión que Pss
   * @return la conexión a ser usada por el JReport
   */
  public Connection getReportConnection() throws Exception {
    JBDato oBaseDefault = JBDatos.GetBases().getPrivateCurrentDatabase();
    return oBaseDefault.GetConnection();

/*    if (oBaseDefault.isDriverJDATA()) {
      JBDato oJBDCRep = null;
      String nameReport = oBaseDefault.GetName() + "_REPORT";

      try {
        oJBDCRep = JBDatos.GetBases().GetBaseByName(nameReport);
      } catch (Exception e) {
        if ( oBaseDefault.isDriverJDATA() ) {
          oJBDCRep = JBDatos.GetBases().getBaseReport();
          JBDatos.GetBases().AddItem(oJBDCRep);
          oJBDCRep.Abrir();
        }
      }
      return oJBDCRep.GetConnection();

    } else {
      return oBaseDefault.GetConnection();
    }*/
  }


  //////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE API
  //
  //////////////////////////////////////////////////////////////////////////////
  public JRDataSource createDataSource(JRecords zBDs) throws Exception {
    return new JBDsDataSource(zBDs);
  }
  public JRDataSource createDataSource(JRecords zBDs, boolean zIsAlreadyRead) throws Exception {
    return new JBDsDataSource(zBDs, zIsAlreadyRead);
  }
  public JRDataSource createDataSource(String zSQL) throws Exception {
    return new JDBCDataSource(getReportConnection(), zSQL);
  }
  public JRDataSource createDataSource(ResultSet zResultSet) throws Exception {
    return new JRResultSetDataSource(zResultSet);
  }

}
