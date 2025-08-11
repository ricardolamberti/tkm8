package  pss.common.agenda.mensual;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public class BizAgendaMensual extends JRecord {

	public static final String VISION_CONSOLA = "VISION_CONSOLA";
	
  private JLong pId = new JLong();
  private JLong pIdPersona = new JLong();
  private JLong pIdAgenda = new JLong();
  private JString pCompany = new JString();
  private JDate pfechaPatron = new JDate();
  private JDate pfecha = new JDate();
  private JString pEstado = new JString();
  private JString pIdTipoEvento = new JString();
  private JString pLunes = new JString();
  private JFloat pLunesPorc = new JFloat();
  private JString pMartes = new JString();
  private JFloat pMartesPorc = new JFloat();
  private JString pMiercoles = new JString();
  private JFloat pMiercolesPorc = new JFloat();
  private JString pJueves = new JString();
  private JFloat pJuevesPorc = new JFloat();
  private JString pViernes = new JString();
  private JFloat pViernesPorc = new JFloat();
  private JString pSabado = new JString();
  private JFloat pSabadoPorc = new JFloat();
  private JString pDomingo = new JString();
  private JFloat pDomingoPorc = new JFloat();
  private JObjBDs<BizEvento> pEventosLunes = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosMartes = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosMiercoles = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosJueves = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosViernes = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosSabado = new JObjBDs<BizEvento>( );
  private JObjBDs<BizEvento> pEventosDomingo = new JObjBDs<BizEvento>( );

  public long getIdPersona() throws Exception {
  	return pIdPersona.getValue();
  }
  public long getIdAgenda() throws Exception {
  	return pIdAgenda.getValue();
  }
  public String getCompany() throws Exception {
  	return pCompany.getValue();
  }
  private JString pTextoLunes = new JString() {
  	public void preset() throws Exception {
  		pTextoLunes.setValue(getTextoFormateadoLunes());
  	};
  };
  private JString pTextoMartes = new JString() {
  	public void preset() throws Exception {
  		pTextoMartes.setValue(getTextoFormateadoMartes());
  	};
  };
  private JString pTextoMiercoles = new JString() {
  	public void preset() throws Exception {
  		pTextoMiercoles.setValue(getTextoFormateadoMiercoles());
  	};
  };
  private JString pTextoJueves = new JString() {
  	public void preset() throws Exception {
  		pTextoJueves.setValue(getTextoFormateadoJueves());
  	};
  };
  private JString pTextoViernes = new JString() {
  	public void preset() throws Exception {
  		pTextoViernes.setValue(getTextoFormateadoViernes());
  	};
  };
  private JString pTextoSabado = new JString() {
  	public void preset() throws Exception {
  		pTextoSabado.setValue(getTextoFormateadoSabado());
  	};
  };
  private JString pTextoDomingo = new JString() {
  	public void preset() throws Exception {
  		pTextoDomingo.setValue(getTextoFormateadoDomingo());
  	};
  };
  private String getBackgroundColor(Double porc) throws Exception {
  	if (porc>0 && porc<=10) return "d6f9d7";
  	if (porc>10 && porc<=20) return "86f189";
  	if (porc>20 && porc<=30) return "4cd44f";
  	if (porc>30 && porc<=40) return "f1f2a0";
  	if (porc>40 && porc<=50) return "e0e163";
  	if (porc>50 && porc<=60) return "eff20b";
  	if (porc>60 && porc<=70) return "f79b9b";
  	if (porc>70 && porc<=80) return "f16161";
  	if (porc>80 && porc<=90) return "ea3030";
  	if (porc>90 && porc<=100) return "f40f0f";
  	if (porc>100) return "FF0000";
  	return null;
  }

  public double getOcupacion(int dias) throws Exception {
  	if (dias==7) return  pDomingoPorc.getValue();
  	if (dias==0) return  pLunesPorc.getValue();
  	if (dias==1) return  pMartesPorc.getValue();
  	if (dias==2) return  pMiercolesPorc.getValue();
  	if (dias==3) return  pJuevesPorc.getValue();
  	if (dias==4) return  pViernesPorc.getValue();
  	if (dias==5) return  pSabadoPorc.getValue();
  	return 0;
  }
  public String getBackgroundColor(String field) throws Exception {
  	if (field.startsWith("lunes")) return getBackgroundColor(pLunesPorc.getValue());
  	if (field.startsWith("martes")) return getBackgroundColor(pMartesPorc.getValue());
  	if (field.startsWith("miercoles")) return getBackgroundColor(pMiercolesPorc.getValue());
  	if (field.startsWith("jueves")) return getBackgroundColor(pJuevesPorc.getValue());
  	if (field.startsWith("viernes")) return getBackgroundColor(pViernesPorc.getValue());
  	if (field.startsWith("sabado")) return getBackgroundColor( pSabadoPorc.getValue());
  	if (field.startsWith("domingo")) return getBackgroundColor(pDomingoPorc.getValue());
  	return null;
  }
  
  public String getForegroundColor(String field) throws Exception {
  	String fondo = getBackgroundColor(field);
  	if (field.startsWith("lunes")) return getForegroundColor(0,fondo);
  	if (field.startsWith("martes")) return getForegroundColor(1,fondo);
  	if (field.startsWith("miercoles")) return getForegroundColor(2,fondo);
  	if (field.startsWith("jueves")) return getForegroundColor(3,fondo);
  	if (field.startsWith("viernes")) return getForegroundColor(4,fondo);
  	if (field.startsWith("sabado")) return getForegroundColor(5,fondo);
  	if (field.startsWith("domingo")) return getForegroundColor(6,fondo);
  	return null;
  }
  
  public Date getFechaColumna(String field) throws Exception {
  	int dias = 0;
  	if (field.startsWith("martes")) dias=1;
  	if (field.startsWith("miercoles")) dias=2;
  	if (field.startsWith("jueves")) dias=3;
  	if (field.startsWith("viernes")) dias=4;
  	if (field.startsWith("sabado")) dias=5;
  	if (field.startsWith("domingo")) dias=6;
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFecha());
  	d.add(Calendar.DAY_OF_MONTH, dias);
  	return JDateTools.getDateStartDay(d.getTime());
 }
  private String getForegroundColor(int dias,String colorFondo) throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFecha());
  	d.add(Calendar.DAY_OF_MONTH, dias);
  	
 	  String fore = null;
 		Date fechaPatron = getFechaPatron();
 	  if (fechaPatron!=null) {
 	  	Calendar fp=Calendar.getInstance();
 	  	fp.setTime(fechaPatron);
 	  	if (fp.get(Calendar.MONTH)!=d.get(Calendar.MONTH)) 
 	  		fore = "0000FF";
 	  }
 	  if (fore==null) 
 	  	fore = isFeriado(d)?(getOcupacion(dias)>90?"000000":"FF0000"):null;
 	  if (fore!=null && fore.equals(colorFondo)) {
 	  	fore = "000000";
 	  }
 	  return fore;
  }
  
  //pendiente: sofistiquizar el tema de los feriados
  private boolean isFeriado(Calendar d) throws Exception {
  	return d.get(Calendar.DAY_OF_WEEK)==1 || d.get(Calendar.DAY_OF_WEEK)==7;
  }
  
  
  public Date getFechaFromColumna(String field) throws Exception {
  	if (field==null) throw new Exception("Debe seleccionar un dia");
  	Date f = getFecha();
  	if (f==null) throw new Exception("Debe seleccionar un dia");
  	Calendar c = Calendar.getInstance();
  	c.setTime(f);
  	
  	if (field.equals("1")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
  	else if (field.equals("2")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
  	else if (field.equals("3")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+2);
  	else if (field.equals("4")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+3);
  	else if (field.equals("5")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+4);
  	else if (field.equals("6")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+5);
  	else if (field.equals("7")) c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+6);
  	return c.getTime();
  }
  public String getTextoFormateadoLunes() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	
  	return formateado(f, pLunesPorc.getValue(), pLunes.getValue());
  }
  public String getTextoFormateadoMartes() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 1);
  	return formateado(f, pMartesPorc.getValue(), pMartes.getValue());
  }
  public String getTextoFormateadoMiercoles() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 2);
  	return formateado(f, pMiercolesPorc.getValue(), pMiercoles.getValue());
  }
  public String getTextoFormateadoJueves() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 3);
  	return formateado(f, pJuevesPorc.getValue(), pJueves.getValue());
  }
  public String getTextoFormateadoViernes() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 4);
  	return formateado(f, pViernesPorc.getValue(), pViernes.getValue());
  }
  public String getTextoFormateadoSabado() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 5);
  	return formateado(f, pSabadoPorc.getValue(), pSabado.getValue());
  }
  public String getTextoFormateadoDomingo() throws Exception {
  	Calendar f = Calendar.getInstance();
  	f.setTime(getFecha());
  	f.add(Calendar.DAY_OF_MONTH, 6);
  	return formateado(f, pDomingoPorc.getValue(), pDomingo.getValue());
  }

  public String formateado(Calendar dia, double porc, String texto) throws Exception {
  	return "Día "+dia.get(Calendar.DAY_OF_MONTH)+" ("+JTools.forceRd(porc, 2)+"%):"+texto;
  	
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void addNota(int day,String text, float porc) throws Exception {
  	if (day==2) addNotaLunes(text,porc);
  	if (day==3) addNotaMartes(text,porc);
  	if (day==4) addNotaMiercoles(text,porc);
  	if (day==5) addNotaJueves(text,porc);
  	if (day==6) addNotaViernes(text,porc);
  	if (day==7) addNotaSabado(text,porc);
  	if (day==1) addNotaDomingo(text,porc);
  	
  }
  
  public void addNotaLunes(String text, float porc) throws Exception {
  	if (text.equals(""));pLunes.setValue((pLunes.getValue().equals("")?"":pLunes.getValue()+"\n")+text);
  	pLunesPorc.setValue(pLunesPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaMartes(String text, float porc) throws Exception {
  	if (text.equals(""));pMartes.setValue((pMartes.getValue().equals("")?"":pMartes.getValue()+"\n")+text);
  	pMartesPorc.setValue(pMartesPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaMiercoles(String text, float porc) throws Exception {
  	if (text.equals(""));pMiercoles.setValue((pMiercoles.getValue().equals("")?"":pMiercoles.getValue()+"\n")+text);
  	pMiercolesPorc.setValue(pMiercolesPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaJueves(String text, float porc) throws Exception {
  	if (text.equals(""));pJueves.setValue((pJueves.getValue().equals("")?"":pJueves.getValue()+"\n")+text);
  	pJuevesPorc.setValue(pJuevesPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaViernes(String text, float porc) throws Exception {
  	if (text.equals(""));pViernes.setValue((pViernes.getValue().equals("")?"":pViernes.getValue()+"\n")+text);
  	pViernesPorc.setValue(pViernesPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaSabado(String text, float porc) throws Exception {
  	if (text.equals(""));pSabado.setValue((pSabado.getValue().equals("")?"":pSabado.getValue()+"\n")+text);
  	pSabadoPorc.setValue(pSabadoPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }
  public void addNotaDomingo(String text, float porc) throws Exception {
  	if (text.equals(""));pDomingo.setValue((pDomingo.getValue().equals("")?"":pDomingo.getValue()+"\n")+text);
  	pDomingoPorc.setValue(pDomingoPorc.getValue()+porc);
  	pEstado.setValue("OCUPADO");
  }

  public void setId(int week) throws Exception {
  	pId.setValue(week);
  }
  public void setFecha(Date day) throws Exception {
  	pfecha.setValue(day);
  }
  public void setFechaPatron(Date day) throws Exception {
  	pfechaPatron.setValue(day);
  }
  public Date getFecha() throws Exception {
  	return pfecha.getValue();
  }
  public Date getFechaPatron() throws Exception {
  	return pfechaPatron.getValue();
  }
  /**
   * Constructor de la Clase
   */
  public BizAgendaMensual() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_persona", pIdPersona );
    this.addItem( "estado", pEstado );
    this.addItem( "id_tipo_evento", pIdTipoEvento );
    this.addItem( "id_agenda", pIdAgenda );
    this.addItem( "company", pCompany );
    this.addItem( "lunes", pLunes );
    this.addItem( "lunes_f", pTextoLunes );
    this.addItem( "lunes_p", pLunesPorc );
    this.addItem( "martes", pMartes );
    this.addItem( "martes_f", pTextoMartes );
    this.addItem( "martes_p", pMartesPorc );
    this.addItem( "miercoles", pMiercoles );
    this.addItem( "miercoles_f", pTextoMiercoles );
    this.addItem( "miercoles_p", pMiercolesPorc );
    this.addItem( "jueves", pJueves );
    this.addItem( "jueves_f", pTextoJueves);
    this.addItem( "jueves_p", pJuevesPorc);
    this.addItem( "viernes", pViernes );
    this.addItem( "viernes_f", pTextoViernes );
    this.addItem( "viernes_p", pViernesPorc );
    this.addItem( "sabado", pSabado );
    this.addItem( "sabado_f", pTextoSabado );
    this.addItem( "sabado_p", pSabadoPorc );
    this.addItem( "domingo", pDomingo );
    this.addItem( "domingo_f", pTextoDomingo);
    this.addItem( "domingo_p", pDomingoPorc );
    this.addItem( "fecha", pfecha );
    this.addItem( "fecha_p", pfechaPatron );
    this.addItem( "eventos_lunes", pEventosLunes );
    this.addItem( "eventos_martes", pEventosMartes );
    this.addItem( "eventos_miercoles", pEventosMiercoles );
    this.addItem( "eventos_jueves", pEventosJueves );
    this.addItem( "eventos_viernes", pEventosViernes );
    this.addItem( "eventos_sabado", pEventosSabado );
    this.addItem( "eventos_domingo", pEventosDomingo );
 }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, false, 64 );
    this.addFixedItem( VIRTUAL, "id_persona", "Persona", true, false, 18 );
    this.addFixedItem( FIELD, "lunes", "Lunes", true, false, 1000 );
    this.addFixedItem( FIELD, "lunes_f", "Lunes", true, false, 1000 );
    this.addFixedItem( FIELD, "lunes_p", "Lunes porc", true, false, 18 );
    this.addFixedItem( FIELD, "martes", "Martes", true, false, 1000 );
    this.addFixedItem( FIELD, "martes_f", "Martes", true, false, 1000 );
    this.addFixedItem( FIELD, "martes_p", "Martes porc", true, false, 18 );
    this.addFixedItem( FIELD, "miercoles", "Miercoles", true, false, 1000 );
    this.addFixedItem( FIELD, "miercoles_f", "Miercoles", true, false, 1000 );
    this.addFixedItem( FIELD, "miercoles_p", "Miercoles porc", true, false, 18 );
    this.addFixedItem( FIELD, "jueves", "Jueves", true, false, 1000 );
    this.addFixedItem( FIELD, "jueves_f", "Jueves", true, false, 1000 );
    this.addFixedItem( FIELD, "jueves_p", "Jueves porc", true, false, 18 );
    this.addFixedItem( FIELD, "viernes", "Viernes", true, false, 1000 );
    this.addFixedItem( FIELD, "viernes_f", "Viernes", true, false, 1000 );
    this.addFixedItem( FIELD, "viernes_p", "Viernes porc", true, false, 18 );
    this.addFixedItem( FIELD, "sabado", "Sabado", true, false, 1000 );
    this.addFixedItem( FIELD, "sabado_f", "Sabado", true, false, 1000 );
    this.addFixedItem( FIELD, "sabado_p", "Sabado porc", true, false, 18 );
    this.addFixedItem( FIELD, "domingo", "Domingo", true, false, 1000 );
    this.addFixedItem( FIELD, "domingo_f", "Domingo", true, false, 1000 );
    this.addFixedItem( FIELD, "domingo_p", "Domingo porc", true, false, 18 );
    this.addFixedItem( FIELD, "fecha", "fecha inicio semana", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_p", "fecha referencia", true, false, 18 );
    this.addFixedItem( FIELD, "estado", "estado", true, false, 50 );
    this.addFixedItem( FIELD, "id_tipo_evento", "Tipo Evento", true, false, 50 );
    this.addFixedItem( FIELD, "id_agenda", "Id agenda", true, false, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, false, 18 );
    this.addFixedItem( RECORDS, "eventos_lunes", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_martes", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_miercoles", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_jueves", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_viernes", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_sabado", "eventos", true, false, 18 ).setClase(BizEvento.class);
    this.addFixedItem( RECORDS, "eventos_domingo", "eventos", true, false, 18 ).setClase(BizEvento.class);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return ""; }

  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void setEventos(int day,JRecords<BizEvento> zVal) throws Exception{
  	if (day==2) pEventosLunes.setValue(zVal);;
  	if (day==3) pEventosMartes.setValue(zVal);
  	if (day==4) pEventosMiercoles.setValue(zVal);
  	if (day==5) pEventosJueves.setValue(zVal);
  	if (day==6) pEventosViernes.setValue(zVal);
  	if (day==7) pEventosSabado.setValue(zVal);
  	if (day==1) pEventosDomingo.setValue(zVal);
  }
  public JRecords<BizEvento> getEventos(int day ) throws Exception{
  	if (day==2) {
	  	if (pEventosLunes.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosLunes.setValue(evs);
	  	}
	  	return pEventosLunes.getValue();
  	}
  	if (day==3) {
	  	if (pEventosMartes.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosMartes.setValue(evs);
	  	}
	  	return pEventosMartes.getValue();
  	}
  	if (day==4) {
	  	if (pEventosMiercoles.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosMiercoles.setValue(evs);
	  	}
	  	return pEventosMiercoles.getValue();
  	}
  	if (day==5) {
	  	if (pEventosJueves.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosJueves.setValue(evs);
	  	}
	  	return pEventosJueves.getValue();
  	}
  	if (day==6) {
	  	if (pEventosViernes.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosViernes.setValue(evs);
	  	}
	  	return pEventosViernes.getValue();
  	}
  	if (day==7) {
	  	if (pEventosSabado.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosSabado.setValue(evs);
	  	}
	  	return pEventosSabado.getValue();
  	}
  	if (day==1) {
	  	if (pEventosDomingo.isRawNull()) {
	  		JRecords<BizEvento> evs = new JRecords<BizEvento>(BizEvento.class);
	  		evs.setStatic(true);
	  		pEventosDomingo.setValue(evs);
	  	}
	  	return pEventosDomingo.getValue();
  	}
  	return null;
  }
}
