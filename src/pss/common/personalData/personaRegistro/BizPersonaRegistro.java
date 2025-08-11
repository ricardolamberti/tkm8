package  pss.common.personalData.personaRegistro;

import java.util.Date;

import pss.common.personalData.BizPersona;
import pss.common.personalData.personaRegistro.registros.BizRegistro;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;

public class BizPersonaRegistro extends JRecord {

  private JLong pIdPersona = new JLong();
  private JInteger pIdRegistro = new JInteger();
  private JString pOrigen = new JString();
  private JString pTomo = new JString();
  private JString pLetra = new JString();
  private JString pFolio = new JString();
  private JInteger pNro = new JInteger();
  private JDate pFecha = new JDate();
  private JInteger pRegIdoneo = new JInteger();

  private BizPersona persona;
  private BizRegistro registro;

  public int getIdRegistro() throws Exception {
  	return this.pIdRegistro.getValue();
  }
  public String getLetra() throws Exception {
  	return this.pLetra.getValue();
  }
  public String getOrigen() throws Exception {
  	return this.pOrigen.getValue();
  }
  public String getTomo() throws Exception {
  	return this.pTomo.getValue();
  }
  public String getFolio() throws Exception {
  	return this.pFolio.getValue();
  }
  public int getNro() throws Exception {
  	return this.pNro.getValue();
  }
  public int getRegIdoneo() throws Exception {
  	return this.pRegIdoneo.getValue();
  }
  public Date getFecha() throws Exception {
  	return this.pFecha.getValue();
  }
  
  public void setIdPersona(long zValue) throws Exception { pIdPersona.setValue(zValue); }
  public void setIdRegistro(int zValue) throws Exception { pIdRegistro.setValue(zValue); }
  public void setOrigen(String zValue) throws Exception { pOrigen.setValue(zValue);  }
  public void setLetra(String zValue) throws Exception { pLetra.setValue(zValue);  }
  public void setTomo(String zValue) throws Exception { pTomo.setValue(zValue);  }
  public void setFolio(String zValue) throws Exception {pFolio.setValue(zValue);  }
  public void setNro(int zValue) throws Exception { pNro.setValue(zValue);  }
  public void setFecha(Date zValue) throws Exception {pFecha.setValue(zValue);  }
  public void setRegIdoneo(int zValue) throws Exception { pRegIdoneo.setValue(zValue);  }
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizPersonaRegistro() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_persona", pIdPersona );
    this.addItem( "id_registro", pIdRegistro );
    this.addItem( "origen", pOrigen );
    this.addItem( "letra", pLetra );
    this.addItem( "tomo", pTomo );
    this.addItem( "folio", pFolio );
    this.addItem( "nro", pNro);
    this.addItem( "fecha", pFecha);
    this.addItem( "reg_idoneo", pRegIdoneo);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_persona", "Id persona", true, true, 18 );
    this.addFixedItem( FIELD, "id_registro", "Reistro", true, false, 5 );
    this.addFixedItem( FIELD, "origen", "Origen", true, false, 100 );
    this.addFixedItem( FIELD, "letra", "Letra", true, false, 10 );
    this.addFixedItem( FIELD, "tomo", "Tomo", true, false, 10 );
    this.addFixedItem( FIELD, "folio", "Folio", true, false, 10 );
    this.addFixedItem( FIELD, "nro", "Nro", true, false, 10 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 10 );
    this.addFixedItem( FIELD, "reg_idoneo", "Reg Idoneo", true, false, 10 );
   }
  /**
   * Returns the table name
   */
  public String GetTable() { return "PER_PERSONA_REGISTRO"; }

  public boolean read( long idPersona ) throws Exception { 
    this.addFilter( "id_persona",  idPersona); 
    return this.read(); 
  } 


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getProtocoloCorto() throws Exception {
  	if (!this.isProtocolizado()) return "";
  	return this.getObjRegistro().getDescripcion()+
//  	"- A:"+this.pAnio.getValue()+
  	" T:"+this.pTomo.getValue()+
  	" F:"+this.pFolio.getValue()+
//  	(this.pFolioBis.getValue()!=0? " b"+this.pFolioBis.getValue():"")+
  	" N:"+this.pNro.getValue()+
//  	(this.pNroBis.getValue()!=0? " b"+this.pNroBis.getValue():"")+
  	" del " +JDateTools.formatFechaLarga(this.pFecha.getValue());
  }
  
  public String getDescrRegistro() throws Exception {
  	return this.getObjRegistro().getDescripcion();
  }
  
  public String getProtocoloLargo() throws Exception {
  	if (!this.isProtocolizado()) return "";
  	return //"Registro: "+ this.getObjArchivo().getDescripcion()+
  	" Tomo:"+this.pTomo.getValue()+
  	" Folio:"+this.pFolio.getValue()+
//  	(this.pFolioBis.getValue()!=0?" b:"+this.pFolioBis.getValue():"")+
  	" Nro:"+this.pNro.getValue() + 
  //	(this.pNroBis.getValue()!=0?" b:"+this.pNroBis.getValue():"")+
  	" del " +JDateTools.formatFechaLarga(this.pFecha.getValue());
  }

//  @Override
//	public void processInsert() throws Exception {
//		this.updateCorrelatividadArchivo();
//
//		if (this.docFisico != null)
//			this.docFisico.refresh();
//	}

  
	public BizPersona getObjPersona() throws Exception {
		if (this.persona!=null) return this.persona;
		BizPersona per = new BizPersona();
		per.Read(this.pIdPersona.getValue());
		return (this.persona=per);
	}

	private BizRegistro getObjRegistro() throws Exception {
		if (this.registro!=null) return this.registro;
		BizRegistro a = new BizRegistro();
		a.read(""+this.pIdRegistro.getValue());
		return (this.registro=a);
	}
	
	public boolean isProtocolizado() throws Exception {
		return this.pIdRegistro.getValue()!=0;
	}

}
