package pss.core.reports;


public class JReportesCommon extends JReport {

  protected static final String SUBDIR_REPORTES = "common/";

  public JReportesCommon(String zFile, int zDsType) throws Exception {
    super.createReport (SUBDIR_REPORTES+zFile, zDsType);
    this.SetConfiguracionGeneral(true);
  }

}

