package  pss.common.agenda.feriado;

import java.util.Calendar;
import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;

public class BizFeriado extends JRecord {

  private JString pCompany = new JString();
  private JLong pId = new JLong();
  private JString pUsuario = new JString();
  private JString pDescripcion = new JString();
  private JDateTime pFechaInicio = new JDateTime();
  private JDateTime pFechaFin = new JDateTime();
  private JBoolean pAnualizar = new JBoolean();
  private JString pHoraDesde = new JString() {
  	public void preset() throws Exception {
  		pHoraDesde.setValue(getHoraInicio());
  	};
  };
  private JString pHoraHasta = new JString() {
  	public void preset() throws Exception {
  		pHoraDesde.setValue(getHoraFin());
  	};
  };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public void setAnualizar(boolean zValue) throws Exception {    pAnualizar.setValue(zValue);  }
  public boolean getAnualizar() throws Exception {     return pAnualizar.getValue();  }

  public void setFechaInicio(Date zValue) throws Exception {    pFechaInicio.setValue(zValue);  }
  public Date getFechaInicio() throws Exception {     return pFechaInicio.getValue();  }
  public boolean isNullFechaInicio() throws Exception { return  pFechaInicio.isNull(); } 
  public void setNullToFechaInicio() throws Exception {  pFechaInicio.setNull(); } 
  public void setFechaFin(Date zValue) throws Exception {    pFechaFin.setValue(zValue);  }
  public Date getFechaFin() throws Exception {     return pFechaFin.getValue();  }
  public boolean isNullFechaFin() throws Exception { return  pFechaFin.isNull(); } 
  public void setNullToFechaFin() throws Exception {  pFechaFin.setNull(); } 

  /**
   * Constructor de la Clase
   */
  public BizFeriado() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "fecha_inicio", pFechaInicio );
    this.addItem( "fecha_fin", pFechaFin );
    this.addItem( "anual", pAnualizar );
    this.addItem( "hora_fin", pHoraHasta );
    this.addItem( "hora_inicio", pHoraDesde );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 250 );
    this.addFixedItem( FIELD, "fecha_inicio", "Fecha inicio", true, true, 18 );
    this.addFixedItem( FIELD, "fecha_fin", "Fecha fin", true, true, 18 );
    this.addFixedItem( FIELD, "anual", "Anualizar", true, true, 1 );
    this.addFixedItem( VIRTUAL, "hora_inicio", "Hora desde", true, false, 10 );
    this.addFixedItem( VIRTUAL, "hora_fin", "Hora fin", true, false, 10 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_feriado"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId, String zCompany ) throws Exception { 
    addFilter( "id",  zId ); 
    addFilter( "company",  zCompany ); 
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (pCompany.isNull()) pCompany.setValue(BizUsuario.getUsr().getCompany());
  	super.processInsert();
  }
  
  public String getHoraInicio() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaInicio());
  	return JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }
  public String getHoraFin() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaFin());
  	return JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }

  public static Date isFeriado (Date fecha,String company) throws Exception {
		Calendar fechaPatron = Calendar.getInstance();
		fechaPatron.setTime(fecha);

		JRecords<BizFeriado> feriados = new JRecords<BizFeriado>(BizFeriado.class);
		feriados.addFilter("company", company);
		feriados.toStatic();
		feriados.firstRecord();
		while (feriados.nextRecord()) {
			BizFeriado fr = feriados.getRecord();
			Calendar cI = Calendar.getInstance();
			cI.setTime(fr.getFechaInicio());
			Calendar cF = Calendar.getInstance();
			cF.setTime(fr.getFechaFin());
			
			if (fr.getAnualizar()) {
				cI.set(Calendar.YEAR, fechaPatron.get(Calendar.YEAR));
				cF.set(Calendar.YEAR, fechaPatron.get(Calendar.YEAR));
			}
			if ((fechaPatron.after(cI)||fechaPatron.equals(cI)) && fechaPatron.before(cF)) return cF.getTime();
		}
		return null;
  }

}
