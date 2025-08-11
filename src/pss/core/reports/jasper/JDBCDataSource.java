package pss.core.reports.jasper;

/**
 * <p>
 * A <code>JRDataSource</code> based on a JDBC <code>ResultSet</code>.
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: PuntoSur Soluciones</p>
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;


public class JDBCDataSource implements JRDataSource {


  private Connection oConnection;
  private String sSQL;

  private ResultSet resultSet;



  public JDBCDataSource(Connection zConnection, String zSQL) {
    this.oConnection = zConnection;
    this.sSQL = zSQL;
  }


  /**
   *
   */
  public boolean next() throws JRException {
    boolean hasNext = false;
    try {
      if (resultSet==null) {
        Statement oStatement = this.oConnection.createStatement();
        resultSet = oStatement.executeQuery(this.sSQL);
      }
      hasNext = resultSet.next();
    } catch (SQLException e) {
      throw new JRException("Unable to get next record.", e);
    }

    if (!hasNext) {
      resultSet = null;
    }

    return hasNext;
  }


  /**
   *
   */
  public Object getFieldValue(JRField field) throws JRException
  {
    Object objValue = null;

    if (field != null && resultSet != null)
    {
      Class clazz = field.getValueClass();

      try
      {
        if (clazz.equals(java.lang.Object.class))
        {
          objValue = resultSet.getObject(field.getName());
        }
        else if (clazz.equals(java.lang.Boolean.class))
        {
          objValue = new Boolean( resultSet.getBoolean(field.getName()) );
        }
        else if (clazz.equals(java.lang.Byte.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Byte((String)objValue);
          }
        }
        else if (clazz.equals(java.util.Date.class))
        {
          objValue = resultSet.getDate(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
        }
        else if (clazz.equals(java.sql.Timestamp.class))
        {
          objValue = resultSet.getTimestamp(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
        }
        else if (clazz.equals(java.sql.Time.class))
        {
          objValue = resultSet.getTime(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
        }
        else if (clazz.equals(java.lang.Double.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Double((String)objValue);
          }
        }
        else if (clazz.equals(java.lang.Float.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Float((String)objValue);
          }
        }
        else if (clazz.equals(java.lang.Integer.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Integer((String)objValue);
          }
        }
        else if (clazz.equals(java.io.InputStream.class))
        {
          objValue = resultSet.getBinaryStream(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
        }
        else if (clazz.equals(java.lang.Long.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Long((String)objValue);
          }
        }
        else if (clazz.equals(java.lang.Short.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new Short((String)objValue);
          }
        }
        else if (clazz.equals(java.math.BigDecimal.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
          else
          {
            objValue = new BigDecimal((String)objValue);
          }
        }
        else if (clazz.equals(java.lang.String.class))
        {
          objValue = resultSet.getString(field.getName());
          if(resultSet.wasNull())
          {
            objValue = null;
          }
        }
      }
      catch (Exception e)
      {
        throw new JRException("Unable to get value for field '" + field.getName() + "' of class '" + clazz.getName() + "'", e);
      }
    }

    return objValue;
  }


}

