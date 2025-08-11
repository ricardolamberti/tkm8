package  pss.common.security;

import pss.common.components.JSetupParameters;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

/**
 * <p> A shortcut in the Pss Desktop toolbar, configurable for each user. </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: PuntoSur Soluciones</p>
 * @author Leonardo Pronzolino
 * @version 1.0.0
 */

public class BizToolbarShortcut extends JRecord {

  private JString pUser = new JString();
  private JString pAction = new JString();
  private JInteger pPosition = new JInteger();


  public BizToolbarShortcut() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    this.addItem("USUARIO" , pUser);
    this.addItem("ACCION"  , pAction);
    this.addItem("POSICION", pPosition);
  }

  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "USUARIO", "Usuario", true, true, 15);
    this.addFixedItem( KEY, "ACCION", "Acción" , true, true, 80 ) ;
    this.addFixedItem( FIELD, "POSICION", "Posición en la barra", true, true, 9 ) ;
  }


  @Override
	public String GetTable() {return "SEG_TOOLBAR_SHORTCUT";}


  public String getUser() throws Exception {
    return pUser.getValue();
  }
  public String getAction() throws Exception {
    return pAction.getValue();
  }
  public int getPosition() throws Exception {
    return pPosition.getValue();
  }
  public void setUser(String zUser) throws Exception {
    pUser.setValue(zUser);
  }
  public void setAction(String zActionId) throws Exception {
    pAction.setValue(zActionId);
  }
  public void setPosition(int zPosition) throws Exception {
    pPosition.setValue(zPosition);
  }

  public boolean Read(String zUser, String zActionId) throws Exception {
    this.addFilter("USUARIO" , zUser);
    this.addFilter("ACCION"  , zActionId);
    return Read();
  }

  @Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
	  zParams.setExportData(true);
  }

}
