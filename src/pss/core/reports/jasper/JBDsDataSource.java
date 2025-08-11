package pss.core.reports.jasper;

/**
 * <p>
 * A <code>JRDataSource</code> based on a <code>JBDs</code>.
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: PuntoSur Soluciones</p>
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class JBDsDataSource implements JRDataSource {


  private JRecords oBDs;
  private JRecord oCurrentBD;
  private boolean bReadPending;


  public JBDsDataSource(JRecords zBDs) throws Exception {
    this(zBDs, zBDs.isResultSetOpened() && !zBDs.isEOF());
  }
  public JBDsDataSource(JRecords zBDs, boolean zIsAlreadyRead) throws Exception {
    this.oBDs = zBDs;
    this.bReadPending = !zIsAlreadyRead;
  }


  public boolean next() throws JRException {
    try {
      if (this.bReadPending) {
        this.oBDs.readAll();
      }
      boolean bRecordFound = this.oBDs.nextRecord();
      if (bRecordFound) {
        this.oCurrentBD = this.oBDs.getRecord();
        this.bReadPending = false;
      } else {
        this.oCurrentBD = null;
        this.bReadPending = true;
      }
      return bRecordFound;
    } catch (Exception ex) {
      throw new JRException("Error in JBDsDataSource#next()", ex);
    }
  }
  public Object getFieldValue(JRField jRField) throws JRException {
    try {
      return this.oCurrentBD.getProp(jRField.getName()).asObject();
    } catch (Exception ex) {
      throw new JRException("Error in JBDsDataSource#getFieldValue(JRField)", ex);
    }
  }

}
