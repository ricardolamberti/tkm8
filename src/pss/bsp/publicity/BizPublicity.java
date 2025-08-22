package  pss.bsp.publicity;

import java.util.Calendar;
import java.util.Date;

import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPublicity extends JRecord {

  private JString pCompany = new JString();
  private JString pOwner = new JString();
  private JLong pIdinterfaz = new JLong();
  private JLong pLinea = new JLong();
  private JString pCampana = new JString();
  private JString pSegmento = new JString();
  private JDate pFechaDesde =  new JDate();
  private JLong pClicks = new JLong();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setOwner(String zValue) throws Exception {    pOwner.setValue(zValue);  }
  public String getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 
  public void setIdinterfaz(long zValue) throws Exception {    pIdinterfaz.setValue(zValue);  }
  public long getIdinterfaz() throws Exception {     return pIdinterfaz.getValue();  }
  public boolean isNullIdinterfaz() throws Exception { return  pIdinterfaz.isNull(); } 
  public void setNullToIdinterfaz() throws Exception {  pIdinterfaz.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setCampana(String zValue) throws Exception {    pCampana.setValue(zValue);  }
  public String getCampana() throws Exception {     return pCampana.getValue();  }
  public void setSegmento(String zValue) throws Exception {    pSegmento.setValue(zValue);  }
  public String getSegmento() throws Exception {     return pSegmento.getValue();  }
  public void setFechaDesde(Date zValue) throws Exception {    pFechaDesde.setValue(zValue);  }
  public Date getFechaDesde() throws Exception {     return pFechaDesde.getValue();  }
  public void setClicks(long zValue) throws Exception {    pClicks.setValue(zValue);  }
  public long getClicks() throws Exception {     return pClicks.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPublicity() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "owner", pOwner );
    this.addItem( "id_interfaz", pIdinterfaz );
    this.addItem( "linea", pLinea );
    this.addItem( "campana", pCampana );
    this.addItem( "segmento", pSegmento );
    this.addItem( "fecha", pFechaDesde );
    this.addItem( "clicks", pClicks );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 50 );
    this.addFixedItem( KEY, "id_interfaz", "Idinterfaz", true, true, 18 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 18 );
    this.addFixedItem( FIELD, "owner", "Owner", true, false, 50 );
    this.addFixedItem( FIELD, "campana", "Campaña", true, false, 50 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 18 );
    this.addFixedItem( FIELD, "segmento", "Segmento", true, false, 450 );
    this.addFixedItem( FIELD, "clicks", "Clicks", true, false, 450 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PUBLICITY_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.publicity.GuiPublicities");
  	JRelation rel; 
  	rel = rels.addRelationForce(10, "restriccion usuario");
   	rel.addFilter(" (BSP_PUBLICITY_DETAIL.company is null or BSP_PUBLICITY_DETAIL.company in (COMPANY_CUSTOMLIST)) ");
// 	rel.addJoin("BSP_PUBLICITY_DETAIL.company", BizUsuario.getUsr().getCompany());

		//rels.hideField("company");
		rels.hideField("owner");
	}

  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdinterfaz, long zLinea ) throws Exception { 
    addFilter( "company",  zCompany ); 
//    addFilter( "owner",  zOwner ); 
    addFilter( "id_interfaz",  zIdinterfaz ); 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 

	@Override
	public void processInsert() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processInsert();
	}

	@Override
	public void processUpdate() throws Exception {
		if (pOwner.isNull()) pOwner.setValue(BizUsuario.getUsr().GetUsuario());
		super.processUpdate();
		fill();
	}

	public void fill() throws Exception {
		Calendar c= Calendar.getInstance();
		c.setTime(new Date());
		for (int d=1;d<90;d++) {
			BizPublicity p = new BizPublicity();
			if (((int)(Math.random()*777)%7)!=1) continue;
			p.setCampana(getCampana());
			p.setOwner(getOwner());
			p.setSegmento(getSegmento());
			c.set(Calendar.DAY_OF_YEAR,d);
			p.setFechaDesde(c.getTime());
			p.setCompany(getCompany());
			p.setIdinterfaz(getIdinterfaz());
			p.setClicks(((int)(Math.random()*777)%70));
			p.processInsert();
		}
		
	}
}
