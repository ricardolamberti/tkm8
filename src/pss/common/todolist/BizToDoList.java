package  pss.common.todolist;

import pss.common.security.GuiUsuarios;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizToDoList extends JRecord {

  private JString pCompany = new JString();
  private JString pStatus = new JString();
  private JString pGrupo = new JString();
	private JLong pIdToDo = new JLong();
  private JString pDescripcion = new JString();
  private JString pAction = new JString();
  private JLong pPriority = new JLong();
  private JString pUsuario = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setIdToDo(long zValue) throws Exception {    pIdToDo.setValue(zValue);  }
  public long getIdToDo() throws Exception {     return pIdToDo.getValue();  }
  public boolean isNullIdToDo() throws Exception { return  pIdToDo.isNull(); } 
  public void setNullToIdToDo() throws Exception {  pIdToDo.setNull(); } 
  public void setAction(String zValue) throws Exception {    pAction.setValue(zValue);  }
  public String getAction() throws Exception {     return pAction.getValue();  }
  public boolean isNullAction() throws Exception { return  pAction.isNull(); } 
  public void setNullToAction() throws Exception {  pAction.setNull(); } 
  public void setStatus(String zValue) throws Exception {    pStatus.setValue(zValue);  }
  public String getStatus() throws Exception {     return pStatus.getValue();  }
  public boolean isNullStatus() throws Exception { return  pStatus.isNull(); } 
  public void setNullToStatus() throws Exception {  pStatus.setNull(); } 
  public void setGrupo(String zValue) throws Exception {    pGrupo.setValue(zValue);  }
  public String getGrupo() throws Exception {     return pGrupo.getValue();  }
  public boolean isNullGrupo() throws Exception { return  pGrupo.isNull(); } 
  public void setNullToGrupo() throws Exception {  pGrupo.setNull(); } 
  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizToDoList() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_todo", pIdToDo);
    this.addItem( "id_usuario", pUsuario);
    this.addItem( "description", pDescripcion );
    this.addItem( "grupo", pGrupo );
    this.addItem( "action", pAction );
    this.addItem( "status", pStatus );
    this.addItem( "priority", pPriority );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_todo", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "description", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "id_usuario", "Usuario", true, false, 15 );
    this.addFixedItem( FIELD, "grupo", "Grupo", true, false, 50 );
    this.addFixedItem( FIELD, "status", "Estado", true, false, 50 );
    this.addFixedItem( FIELD, "action", "Accion", true, false, 400 );
    this.addFixedItem( FIELD, "priority", "Orden", true, false, 18 );
  
  }
  
  
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_usuario", createControlCombo(GuiUsuarios.class,"usuario", new JPair<String, String>("company","company") ));
  	this.addControlsProperty("status", createControlCombo(JWins.createVirtualWinsFromMap(BizToDoList.getEstados()),null, null) );
  	super.createControlProperties();
  }
  public static final String REALIZADA = "Realizada";
  public static final String PENDIENTE = "Pendiente";
   
  static JMap<String,String> estados;
  public static JMap<String,String> getEstados() throws Exception {
  	if (estados!=null) return estados;
  	JMap<String,String> maps = JCollectionFactory.createOrderedMap();
  	maps.addElement(PENDIENTE, "Pendiente");
   	maps.addElement(REALIZADA, "Realizada");
  	return estados=maps;
  }
  

	public void execRealizado() throws Exception {
		JExec oExec = new JExec(null, "realizado") {

			@Override
			public void Do() throws Exception {
				realizado();
			}
		};
		oExec.execute();
	}
	
	public void realizado() throws Exception {
		setStatus(REALIZADA);
		processUpdate();
	}
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "tool_todolist"; }

  /**
   * Default read() method
   */
  public boolean read(long zIdDiccionario ) throws Exception { 
    addFilter( "id_todo",  zIdDiccionario ); 
    return read(); 
  } 
  
}
