package pss.common.event.manager;

import pss.common.regions.entidad.GuiEntidades;
import pss.common.regions.nodes.GuiNodos;
import pss.common.security.GuiUsuarios;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormRegister extends JBaseForm {


private static final long serialVersionUID = 1244686958356L;

  /**
   * Constructor de la Clase
   */
  public FormRegister() throws Exception {

  }

  public GuiRegister getWin() { return (GuiRegister) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
		JFormControlResponsive oCtrl;
		JFormPanelResponsive panel = this.AddItemColumn(6);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);
		
		panel.AddItemEdit("company", CHAR, REQ, "company").hide();
		panel.AddItemEdit("pais", CHAR, REQ, "pais").hide();
		
		panel.AddItemCombo("Listen Entidad", CHAR, OPT, "listen_entidad", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getEntidades(zOneRow);
			}
		});	
		panel.AddItemCombo("Listen User", CHAR, OPT, "listen_usuario", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getUsuarios(zOneRow);
			}
		});	
	  
		panel.AddItemCombo("Evento", CHAR, REQ, "event_code",  new JControlCombo() { 
	  		public JWins getRecords(boolean one) throws Exception {
	  			return getCodes(one);
	  		};
	  	}).setRefreshForm(true);

    panel.AddItemCombo("Sucursal Sender", CHAR, OPT, "sender_nodo",  new JControlCombo() { 
			public JWins getRecords(boolean one) throws Exception {
				return getNodos(one);
			};
		});
    panel.AddItemCombo("Usuario Sender", CHAR, OPT, "sender_user",  new JControlCombo() { 
  		public JWins getRecords(boolean one) throws Exception {
  			return getUsuarios(one);
  		};
  	});
    panel.AddItemCombo("Acción", CHAR, OPT, "event_action", new JControlCombo() { 
    	public JWins getRecords(boolean one) throws Exception {
    		return getEventActions(); 
    	};
    });
//    panel.AddItemCombo("Filtros", CHAR, OPT, "source_filters", new JControlCombo() { 
//    	public JWins getRecords(boolean one) throws Exception {
//    		return getFilters(); 
//    	};
//    });
    panel.AddItemCombo("Transformer", CHAR, OPT, "transformer", new JControlCombo() { 
    	public JWins getRecords(boolean one) throws Exception {
    		return getTransformers(); 
    	};
    });

  }
  
	private JWins getCodes(boolean onerow) throws Exception {
		GuiEventCodes wins = new GuiEventCodes();
		wins.setRecords(BizEventCode.getAllEvents());
		return wins;
	}
	
	private JWins getUsuarios(boolean onerow) throws Exception {
		GuiUsuarios wins = new GuiUsuarios();
		wins.getRecords().addFilter("company", this.valueControl("company"));
		wins.getRecords().addFilter("activo", true);
		return wins;
	}
	
	private JWins getNodos(boolean onerow) throws Exception {
		GuiNodos wins = new GuiNodos();
		wins.getRecords().addFilter("company", this.valueControl("company"));
//		wins.readAll();
//		wins.toStatic();
//		
//		GuiNodo all = GuiNodo.createNodoAll(this.valueControl("company"), this.valueControl("pais")); 
//		wins.AddItem(all, 0);
//		wins.getRecords().setStatic(true);
		return wins;
	}

	private JWins getEntidades(boolean onerow) throws Exception {
		GuiEntidades wins = new GuiEntidades();
		wins.getRecords().addFilter("company", this.valueControl("company"));
		return wins;
	}

	private JWins getEventActions() throws Exception {
		return JWins.createVirtualWinsFromMap(BizEventCode.getEventActions());
	}
	
	private JWins getTransformers() throws Exception {
		BizEventCode ec = BizEventCode.findEventCode(this.valueControl("event_code"));
		if (ec==null) return null;
		return JWins.createVirtualWinsFromMap(ec.getTransformers());
	}
//
//	private JWins getFilters() throws Exception {
//		BizEventCode ec = BizEventCode.findEventCode(this.valueControl("event_code"));
//		if (ec==null) return null;
//		return JWins.createVirtualWinsFromMap(ec.getSourceFilters());
//	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10" 
