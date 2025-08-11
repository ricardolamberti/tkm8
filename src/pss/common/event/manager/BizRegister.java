package pss.common.event.manager;

import pss.common.documentos.docEmail.BizDocEmail;
import pss.common.event.device.BizChannel;
import pss.common.mail.mailing.BizMail;
import pss.common.regions.entidad.BizEntidad;
import pss.common.regions.nodes.BizNodo;
import pss.common.restJason.JClientApiProcess;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizRegister extends JRecord {

	private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pPais = new JString();
  private JString pEventCode = new JString();
  private JString pListenUsuario = new JString();
  private JLong pListenEntidad = new JLong();
  private JString pDescrListenEntidad = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrListenEntidad());
  	}
  };
  private JString pSenderNodo = new JString();
  private JString pDescrSenderNodo = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrSenderNode());
  	}
  };
  private JString pSenderUser = new JString();
  private JLong pEventAction = new JLong();
  private JString pTransformer = new JString();
//  private JString pSourceFilters = new JString();
  
//  private JLong pEventActionId = new JLong();

	private JString pDescrEvento = new JString() {
		public void preset() throws Exception {
			setValue(getDescrEvento());
		}
	};

	private JString pDescrAction = new JString() {
		public void preset() throws Exception {
			setValue(getDescrAction());
		}
	};
//  private JString pDescrFiltros = new JString() {
//  	public void preset() throws Exception {
//  		setValue(getDescrFiltros());
//  	}
//  };
  private JString pDescrTransformer = new JString() {
  	public void preset() throws Exception {
  		setValue(getDescrTransformer());
  	}
  };
	
//	private JString pDescrActionId = new JString() {
//		@Override
//		public void preset() throws Exception {
//			pDescrActionId.setValue(BizEvent.getEventActionIdDescription(pEventAction.getValue(),pEventActionId.getValue()));
//		}
//	};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setEventCode(long zValue) throws Exception {    pEventCode.setValue(zValue);  }
  public String getEventCode() throws Exception {     return pEventCode.getValue();  }
  public void setEventAction(String zValue) throws Exception {    pEventAction.setValue(zValue);  }
  public long getEventAction() throws Exception {     return pEventAction.getValue();  }
  public boolean hasSenderNodo() throws Exception { return  !pSenderNodo.isEmpty(); } 
  public boolean hasListenEntidad() throws Exception { return  pListenEntidad.getValue()!=0L; } 
  public boolean hasSenderUser() throws Exception { return  !pSenderUser.isEmpty(); }
  public boolean hasListenUser() throws Exception { return  !pListenUsuario.isEmpty(); }
  public String getSenderNodo() throws Exception { return  pSenderNodo.getValue(); } 
  public String getSenderUser() throws Exception { return  pSenderUser.getValue(); }
  public String getListenUser() throws Exception { return  pListenUsuario.getValue(); }
  public long getListenEntidad() throws Exception { return  pListenEntidad.getValue(); }

  public boolean hasTransformer() throws Exception { 
  	return !this.pTransformer.isEmpty(); 
  }
  public String getTransformer() throws Exception { 
  	return this.pTransformer.getValue(); 
  }
//  
//  public boolean hasSourceFilters() throws Exception { 
//  	return !this.pSourceFilters.isEmpty(); 
//  }
//  public String getSourceFilters() throws Exception { 
//  	return this.pSourceFilters.getValue(); 
//  }

//  public void setEventActionId(long zValue) throws Exception {    pEventActionId.setValue(zValue);  }
//  public long getEventActionId() throws Exception {     return pEventActionId.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizRegister() throws Exception {
  }


  public void createProperties() throws Exception {
  	this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "pais", pPais );
    this.addItem( "listen_entidad", pListenEntidad );
    this.addItem( "listen_usuario", pListenUsuario );
    this.addItem( "sender_nodo", pSenderNodo);
    this.addItem( "sender_user", pSenderUser);
//    this.addItem( "source_filters", pSourceFilters );
    this.addItem( "transformer", pTransformer );
    this.addItem( "event_code", pEventCode );
    this.addItem( "event_action", pEventAction );
//    this.addItem( "event_action_id", pEventActionId );
		this.addItem("descr_action", pDescrAction);
		this.addItem("descr_evento", pDescrEvento);
		this.addItem("descr_sendernodo", pDescrSenderNodo);
		this.addItem("descr_listenentidad", pDescrListenEntidad);
		this.addItem("descr_transformer", pDescrTransformer);
//		this.addItem("descr_filtros", pDescrFiltros);
//		this.addItem("descr_action_id", pDescrActionId);
		
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "listen_entidad", "Entidad Listen", true, false, 15 );
    this.addFixedItem( FIELD, "listen_usuario", "Usuario Listen", true, false, 20 );
    this.addFixedItem( FIELD, "sender_nodo", "Sucursal Emisor", true, false, 15 );
    this.addFixedItem( FIELD, "sender_user", "Usuario Emmisor", true, false, 20 );
    this.addFixedItem( FIELD, "event_code", "Event code", true, true, 15 );
    this.addFixedItem( FIELD, "event_action", "Event Action", true, false, 5 );
    this.addFixedItem( FIELD, "transformer", "Transformer", true, false, 10);
//    this.addFixedItem( FIELD, "source_filters", "Source Filters", true, false, 10);
//    this.addFixedItem( FIELD, "event_action_id", "Event Action Id", true, false, 18 );
		this.addFixedItem(VIRTUAL, "descr_action", "Acción", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_evento", "Evento", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_sendernodo", "Sender Nodo", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_listenentidad", "Listen Entidad", true, true, 50);
		this.addFixedItem(VIRTUAL, "descr_transformer", "Transformer", true, true, 50);
//		this.addFixedItem(VIRTUAL, "descr_filtros", "Filtros", true, true, 50);
//		this.addFixedItem(VIRTUAL, "descr_action_id", "Descripción Acción", true, false, 50);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "EVT_REGISTER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void processInsert() throws Exception {
  	super.processInsert();
  	this.pId.setValue(this.getIdentity("id"));
  }

//  /**
//   * Default read() method
//   */
//  public boolean read( String zCompany, long zEventCode ) throws Exception { 
//    addFilter( "company",  zCompany ); 
//    addFilter( "event_code",  zEventCode ); 
//    return read(); 
//  }
  
	public String getDescrEvento() throws Exception {
		return this.getObjEventCode().getDescripcion();
	}
	public String getDescrAction() throws Exception {
		return BizEventCode.getDescrAction(pEventAction.getValue());
	}
//	public String getDescrFiltros() throws Exception {
//		if (this.pSourceFilters.isEmpty()) return "";		
//		return this.getObjEventCode().getSourceFilters().getElement(this.pSourceFilters.getValue());
//	}
	public String getDescrTransformer() throws Exception {
		if (this.pTransformer.isEmpty()) return "";
		return this.getObjEventCode().getTransformers().getElement(this.pTransformer.getValue());
	}
	
	private BizEventCode eventCode;
	public BizEventCode getObjEventCode() throws Exception {
		if (this.eventCode!=null) return this.eventCode;
		BizEventCode e = BizEventCode.findEventCode(this.pEventCode.getValue());
		return (this.eventCode=e);
	}

	private BizUsuario listenUsuario;
	public BizUsuario getObjListenUsuario() throws Exception {
		if (this.listenUsuario!=null) return this.listenUsuario;
		BizUsuario u = new BizUsuario();
		u.Read(this.pListenUsuario.getValue());
		return (this.listenUsuario=u);
	}

	private BizEntidad listenEntidad;
	public BizEntidad getObjListenEntidad() throws Exception {
		if (this.listenEntidad!=null) return this.listenEntidad;
		BizEntidad e = new BizEntidad();
		e.read(this.pListenEntidad.getValue());
		return (this.listenEntidad=e);
	}

	private BizUsuario sender;
	public BizUsuario getObjSenderUser() throws Exception {
		if (this.sender!=null) return this.sender;
		BizUsuario u = new BizUsuario();
		u.Read(this.pSenderUser.getValue());
		return (this.sender=u);
	}
	
	public String getDescrSenderNode() throws Exception {
		if (!this.hasSenderNodo()) return "Todas";
		return BizNodo.getStoreDesc(this.pCompany.getValue(), this.pSenderNodo.getValue());
	}

	public String getDescrListenEntidad() throws Exception {
		if (!this.hasListenEntidad()) return "Todas";
		return this.getObjListenEntidad().getDescripcion();
	}

	public boolean isActionNotify() throws Exception {     
		return pEventAction.getValue()==BizEventCode.ACT_NOTIFY;  
	}
	
	public boolean isActionMail() throws Exception {     
		return pEventAction.getValue()==BizEventCode.ACT_MAIL;  
	}
	public boolean isActionChannel() throws Exception {     
		return pEventAction.getValue()==BizEventCode.ACT_CHANNEL;  
	}

	public boolean isActionJSon() throws Exception {     
		return pEventAction.getValue()==BizEventCode.ACT_JSON;  
	}

	public void processEvent(BizEvent e) throws Exception {
		if (this.hasTransformer()) {
			this.getObjTransformer().processEvent(this, e);
			return;
		}
		e.getObjEventCode().processEvent(this, e); // para que cada modulo pueda capturar el proceso
	}
	
	public void processAction(BizEvent e) throws Exception {
		if (this.isActionNotify())
			this.notifyAction(e);
		if (this.isActionMail())
			this.sendAction(e);
		if (this.isActionChannel())
			this.processSendChannel(e);
		if (this.isActionJSon())
			this.sendJSon(e);
		// otras acciones
	}
	
	public void notifyAction(BizEvent e) throws Exception {
		BizMail n = new BizMail();
		n.setCompany(this.getCompany());
		n.setSender(e.getSenderUser());
		n.setTitle(e.getTitle());
		n.setMensaje(JTools.encodeIso(JTools.encodeString2(e.getInfo())));
		n.setUrgente(true);
		n.addDestinatario(this.pListenUsuario.getValue());
		n.processInsert();
	}
	
	public void sendAction(BizEvent e) throws Exception {
		if (!this.getObjListenUsuario().hasMail()) return;
		if (!e.getObjSenderUser().hasMail()) return;
		BizDocEmail n = new BizDocEmail();
		n.setCompany(this.getCompany());
		n.setObjMailCasilla(e.getObjSenderUser().getObjMailCasilla());
		n.setTitulo(e.getTitle());
		n.setBody(JTools.encodeIso(JTools.encodeString2(e.getInfo())));
		n.setMailTo(this.getObjListenUsuario().getObjMailCasilla().getMailFrom());
		n.processInsert();
		n.processEnviar();
	}
	
	public void processSendChannel(BizEvent e) throws Exception {
		JRecords<BizChannel> channels = new JRecords<BizChannel>(BizChannel.class);
		channels.addFilter("usuario", this.pListenUsuario.getValue());
		JIterator<BizChannel> it = channels.getStaticIterator();
		while(it.hasMoreElements()) {
			BizChannel channel = it.nextElement();
			channel.processSendEvent(e);
		}
	}

	public void sendJSon(BizEvent e) throws Exception {
		JExcepcion.SendError("No Implementado");
	}
	
//	public String transform(BizEvent e) throws Exception {
//		if (this.hasSourceTransformer()) return e.getInfo();
//		return e.getObjEventCode().transform(this, e);
//	}
//
//	public boolean isOkFilters(BizEvent e) throws Exception {
//		if (!this.hasSourceFilters()) return true;
//		return e.getObjEventCode().checkFilters(this, e);
//	}
	
	public boolean isOkSenderNode(String node) throws Exception {
		if (!this.hasSenderNodo()) return true;
		if (this.getSenderNodo().equals(node)) return true;
		return false;
	}
	
	public boolean isOkSenderUser(String user) throws Exception {
		if (!this.hasSenderUser()) return true;
		if (this.getSenderUser().equals(user)) return true;
		return false;
	}

	private JTransformer transformer;
	public JTransformer getObjTransformer() throws Exception {
		if (this.transformer!=null) return this.transformer;
		return (this.transformer=this.createTransformer());
	}
	

	public JTransformer  createTransformer() throws Exception {
		String clase="";
		if (this.getTransformer().equals(JTransformer.TRANS_E3)) clase="pss.erp.event.JE3";
		else if (this.getTransformer().equals(JTransformer.TRANS_SAP)) clase="pss.erp.event.JSAP";
		else JExcepcion.SendError("No existe Transformer");
		JTransformer t = (JTransformer)Class.forName(clase).newInstance();
		return t;
	}


}
