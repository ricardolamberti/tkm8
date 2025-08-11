package  pss.common.event.sql.serie;


import java.util.Date;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizVirtualSerie extends JRecord {

  JString pValor = new JString();
  JString pDescrip = new JString();
  JDateTime pFecha = new JDateTime();

  public String getValor() throws Exception { return pValor.getValue(); }
  public String getDescrip() throws Exception { return pDescrip.getValue(); }
  public Date getIcono() throws Exception { return pFecha.getValue(); }

  public void setValor( String zValor ) throws Exception { pValor.setValue( zValor ); }
  @Deprecated
  public void setDescripcion( String zDescripcion ) throws Exception { pDescrip.setValue( zDescripcion ); }
  @Deprecated
  public void setIcono( Date zValue ) throws Exception { pFecha.setValue( zValue ); }  
 
  
  public void setSerie( String zDescripcion ) throws Exception { pDescrip.setValue( zDescripcion ); }
  public void setFecha( Date zValue ) throws Exception { pFecha.setValue( zValue ); }  

  public BizVirtualSerie() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem("valor"      , pValor );
    addItem("descripcion" , pDescrip );
    addItem("fecha" , pFecha );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "valor"      , "Valor"      , true, true, 20);
    addFixedItem( FIELD, "descripcion" , "Descripción" , true, true, 50);
    addFixedItem( FIELD, "fecha" , "fecha" , true, true, 50);
  }
  
  @Override
  public String GetTable() throws Exception {
  	return "";
  }
  
  @Override
  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(" pss.common.event.sql.serie.GuiVirtualSeries");
  }

}
