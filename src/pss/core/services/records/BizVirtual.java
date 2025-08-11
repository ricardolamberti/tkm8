package pss.core.services.records;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;


public class BizVirtual extends JRecord {

  JString pValor = new JString();
  JString pDescrip = new JString();
  JLong pIcono = new JLong();
  JString pIconoString = new JString();

  public String getValor() throws Exception { return pValor.getValue(); }
  public String getDescrip() throws Exception { return pDescrip.getValue(); }
  public int getIcono() throws Exception { return (int)pIcono.getValue(); }
  public boolean hasIcono() throws Exception { return pIcono.isNotNull(); }
  public boolean hasIconoString() throws Exception { return pIconoString.isNotNull(); }
  public String getIconoString() throws Exception { return pIconoString.getValue(); }

  public void setValor( String zValor ) throws Exception { pValor.setValue( zValor ); }
  public void setDescripcion( String zDescripcion ) throws Exception { pDescrip.setValue( zDescripcion ); }
  public void setIcono( int zValue ) throws Exception { pIcono.setValue( zValue ); }  
  public void setIconoString( String zValue ) throws Exception { pIconoString.setValue( zValue ); }  

  @Override
  public String getKeyField() throws Exception {
  	return "valor";
  }
  
  @Override
  public String getDescripField() throws Exception {
  	return "descripcion";
  }
  public BizVirtual() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem("valor"      , pValor );
    addItem("descripcion" , pDescrip );
    addItem("icono" , pIcono );
    addItem("icono_string" , pIconoString );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "valor"      , "Valor"      , true, true, 20);
    addFixedItem( FIELD, "descripcion" , "Descripción" , true, true, 50);
    addFixedItem( FIELD, "icono" , "Icono" , true, true, 50);
    addFixedItem( FIELD, "icono_string" , "Icono" , true, true, 500);
      }
  
  @Override
  public String GetTable() throws Exception {
  	return "";
  }
  
  @Override
  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.core.win.GuiVirtuals");
  }

}
