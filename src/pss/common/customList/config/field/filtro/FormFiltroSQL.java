package  pss.common.customList.config.field.filtro;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import pss.common.security.BizUsuario;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormFiltroSQL extends JBaseForm {


private static final long serialVersionUID = 1226426966039L;

JPssEdit company = new JPssEdit  ();
JComboBox jClaveC;
JPssEdit listId = new JPssEdit  ();
JPssEdit secuencia = new JPssEdit  ();
  JPssEdit orden = new JPssEdit  ();
  JPssEdit negado = new JPssEdit  ();
  JPssEdit ordenPadre = new JPssEdit  ();
  JPssEdit oper = new JPssEdit  ();
  JPssEdit recordOwner = new JPssEdit  ();
JPssLabel lfiltro = new JPssLabel();
JPssLabel lvalor = new JPssLabel();
private JPssEdit jFiltro = null;

private JPssLabel lNombre = null;

private JPssEdit jNombreFiltro = null;

private JPssLabel lvalor1 = null;

private JComboBox jTipo = null;

/**
   * Constructor de la Clase
   */
  public FormFiltroSQL() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFiltroSQL getWin() { return (GuiFiltroSQL) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lvalor1 = new JPssLabel();
    lvalor1.setBounds(new Rectangle(11, 96, 81, 24));
    lvalor1.setText("valor default");
    lNombre = new JPssLabel();
    lNombre.setBounds(new Rectangle(13, 41, 71, 22));
    lNombre.setText("Nombre");
    setLayout(null);
    this.setSize(new Dimension(425, 199));

    lfiltro.setText( "Filtro" );
    lfiltro.setBounds(new Rectangle(13, 15, 71, 22)); 
    add(lfiltro, null);
    lvalor.setText("Tipo");
    lvalor.setBounds(new Rectangle(13, 67, 71, 22)); 
    add(lvalor, null);
    this.add(getJFiltro(), null);
    this.add(lNombre, null);
    this.add(getJNombreFiltro(), null);
    this.add(getJClaveC(), null);
    this.add(lvalor1, null);
    this.add(getJTipo(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" );
    AddItem( listId, UINT, REQ, "list_id" );
    AddItem( secuencia, UINT, OPT, "secuencia" );
    AddItem( orden, UINT, OPT, "orden" );

    AddItem( jFiltro, CHAR, REQ, "filtro");
    AddItem( jNombreFiltro, CHAR, OPT, "nombre_filtro" );
    AddItem( getJTipo(), CHAR, OPT, "tipo_filtro", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizFiltroSQL.getTipos());
    	}
    });
    AddItem( getJClaveC(), CHAR, OPT, "campo_key", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return JWins.createVirtualWinsFromMap(BizUsuario.getUsr().getObjBusiness().getCamposClavesParaCustomList());
    	}
    });
    }
	private JComboBox getJClaveC() {
		if (jClaveC == null) {
			jClaveC = new JComboBox();
			jClaveC.setBounds(new Rectangle(91, 96, 197, 22));
		}
		return jClaveC;
	} 

    
  public void checkControls(String campo) throws Exception {
	}
  
	/**
	 * This method initializes jFiltro	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JPssEdit getJFiltro() {
		if (jFiltro == null) {
			jFiltro = new JPssEdit();
			jFiltro.setBounds(new Rectangle(91, 14, 247, 22));

		}
		return jFiltro;
	}

	/**
	 * This method initializes jNombreFiltro	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJNombreFiltro() {
		if (jNombreFiltro == null) {
			jNombreFiltro = new JPssEdit();
			jNombreFiltro.setBounds(new Rectangle(91, 40, 194, 22));
		}
		return jNombreFiltro;
	}

	/**
	 * This method initializes jTipo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJTipo() {
		if (jTipo == null) {
			jTipo = new JComboBox();
			jTipo.setBounds(new Rectangle(90, 67, 190, 22));
		}
		return jTipo;
	} 
}  //  @jve:decl-index=0:visual-constraint="20,9" 
