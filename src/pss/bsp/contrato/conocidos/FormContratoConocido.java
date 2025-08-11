package pss.bsp.contrato.conocidos;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.logica.JContratoNormal;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssHtmlTextArea;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.ui.components.JPssLabelWinLov;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.tourism.carrier.GuiCarriers;

public class FormContratoConocido extends JBaseForm {


private static final long serialVersionUID = 1477827540739L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lpais = new JPssLabel();
JComboBox pais = new JComboBox  ();
JComboBox company = new JComboBox  ();
JPssLabel laerolineas = new JPssLabel();
JPssLabelWinLov aerolineas = new JPssLabelWinLov  ();
JPssLabel lid_variableganancia = new JPssLabel();
JPssLabelWinLov id_variableganancia = new JPssLabelWinLov  ();
JPssLabel lid_variable = new JPssLabel();
JPssLabelWinLov id_variable = new JPssLabelWinLov  ();
JPssLabel ltipo_premio = new JPssLabel();
JComboBox tipo_premio = new JComboBox  ();
JPssLabel ltipo_objetivo = new JPssLabel();
JComboBox tipo_objetivo = new JComboBox  ();
JCheckBox objetivo_extra = new JCheckBox  ();
JPssEdit id = new JPssEdit  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();
JPssLabel ldetalle = new JPssLabel();
JPssHtmlTextArea detalle = new JPssHtmlTextArea  ();
JPssLabelFormLov Icono = new JPssLabelFormLov();
JComboBox pssLabelWinLov = new JComboBox();

private JPssLabel ltipo_premio1 = null;

private JComboBox periodo = null;

private JPssLabel ltipo_objetivo1 = null;
private JPssLabel pslblIdVariableAuxiliar;
private JPssLabelWinLov pssLabelWinLov_1;


  /**
   * Constructor de la Clase
   */
  public FormContratoConocido() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiContratoConocido getWin() { return (GuiContratoConocido) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    ltipo_objetivo1 = new JPssLabel();
    ltipo_objetivo1.setBounds(new Rectangle(13, 239, 123, 24));
    ltipo_objetivo1.setText("Icono");
    Icono.setBounds(new Rectangle(144, 239, 280, 24));
    ltipo_premio1 = new JPssLabel();
    ltipo_premio1.setBounds(new Rectangle(364, 54, 129, 23));
    ltipo_premio1.setText("Periodo");
    setLayout(null);
    setSize(new Dimension(678, 571));


    lpais.setText( "Pais" );
    lpais.setBounds(new Rectangle(12, 55, 123, 22)); 
    pais.setBounds(new Rectangle(145, 55, 143, 22)); 
    add(lpais, null);
    add(pais , null);

    company.setBounds(new Rectangle(145, 5, 143, 22)); 
    add(company , null);

    laerolineas.setText( "Aerolineas" );
    laerolineas.setBounds(new Rectangle(12, 82, 123, 22)); 
    aerolineas.setBounds(new Rectangle(145, 82, 276, 22)); 
    add(laerolineas, null);
    add(aerolineas , null);


    lid_variableganancia.setText( "Id variableganancia" );
    lid_variableganancia.setBounds(new Rectangle(14, 166, 123, 22)); 
    id_variableganancia.setBounds(new Rectangle(147, 166, 276, 22)); 
    add(lid_variableganancia, null);
    add(id_variableganancia , null);


    lid_variable.setText( "Id variable" );
    lid_variable.setBounds(new Rectangle(12, 140, 123, 22)); 
    id_variable.setBounds(new Rectangle(145, 140, 279, 22)); 
    add(lid_variable, null);
    add(id_variable , null);


    ltipo_premio.setText( "Tipo premio" );
    ltipo_premio.setBounds(new Rectangle(287, 216, 123, 22)); 
    tipo_premio.setBounds(new Rectangle(415, 216, 143, 22)); 
    add(ltipo_premio, null);
    add(tipo_premio , null);


    ltipo_objetivo.setText( "Tipo objetivo" );
    ltipo_objetivo.setBounds(new Rectangle(12, 215, 123, 22)); 
    tipo_objetivo.setBounds(new Rectangle(145, 215, 143, 22)); 
    add(ltipo_objetivo, null);
    add(tipo_objetivo , null);


    objetivo_extra.setBounds(new Rectangle(512, 30, 143, 22)); 
    objetivo_extra.setText("Objetivo extra");
    add(objetivo_extra , null);


    

    ldescripcion.setText( "Compañia" );
    ldescripcion.setBounds(new Rectangle(12, 5, 123, 22)); 
    descripcion.setBounds(new Rectangle(145, 30, 370, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);


    ldetalle.setText( "Detalle" );
    ldetalle.setBounds(new Rectangle(12, 267, 123, 22)); 
    detalle.setBounds(new Rectangle(11, 294, 660, 271)); 
    add(ldetalle, null);
    add(detalle , null);
    this.add(ltipo_premio1, null);
    this.add(getPeriodo(), null);
    this.add(Icono, null);
    this.add(ltipo_objetivo1, null);
    
    JPssLabel pssLabel = new JPssLabel();
    pssLabel.setText("Descripcion");
    pssLabel.setBounds(new Rectangle(12, 30, 123, 22));
    pssLabel.setBounds(12, 30, 123, 22);
    add(pssLabel);
    
    JPssLabel pssLabel_1 = new JPssLabel();
    pssLabel_1.setText("Tipo contrato");
    pssLabel_1.setBounds(new Rectangle(12, 82, 123, 22));
    pssLabel_1.setBounds(12, 106, 123, 22);
    add(pssLabel_1);
    
    pssLabelWinLov.setBounds(new Rectangle(140, 129, 279, 22));
    pssLabelWinLov.setBounds(145, 107, 279, 22);
    add(pssLabelWinLov);
    
    pslblIdVariableAuxiliar = new JPssLabel();
    pslblIdVariableAuxiliar.setText("id variable auxiliar");
    pslblIdVariableAuxiliar.setBounds(new Rectangle(14, 166, 123, 22));
    pslblIdVariableAuxiliar.setBounds(12, 194, 123, 22);
    add(pslblIdVariableAuxiliar);
    
    pssLabelWinLov_1 = new JPssLabelWinLov();
    pssLabelWinLov_1.setBounds(new Rectangle(147, 166, 276, 22));
    pssLabelWinLov_1.setBounds(144, 191, 276, 22);
    add(pssLabelWinLov_1);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( id, UINT, OPT, "id" );
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( company, CHAR, REQ, "company", new GuiCompanies() ).setRefreshForm(true);;
    AddItem( pais, CHAR, REQ, "pais", new GuiPaisesLista() );
    AddItem( aerolineas, CHAR, REQ, "aerolineas" , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getAerolineas(bOneRow);
    	}
    });
    AddItem( periodo, CHAR, REQ, "periodo" , JWins.createVirtualWinsFromMap(BizContratoConocido.getTipoPeriodos()));
    AddItem( pssLabelWinLov, CHAR, REQ, "tipo_contrato" , new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getTiposContratos(bOneRow);
    	}
    }).SetValorDefault(JContratoNormal.class.getName());
    AddItem( id_variable, UINT, OPT, "id_variable"  , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getVariable(bOneRow);
    	}
    });
    AddItem( id_variableganancia, UINT, OPT, "id_variableganancia"  , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getVariableGanancia(bOneRow);
    	}
    });
    AddItem( pssLabelWinLov_1, UINT, OPT, "id_variableaux"  , new JControlWin() {
    	@Override
    	public JWins getRecords(boolean bOneRow) throws Exception {
    		return getVariableAuxiliar(bOneRow);
    	}
    });
    AddItem( Icono       , UINT, REQ, "icono",new JControlWin() {  @Override
  		public JWins getRecords()   throws Exception {return  new GuiIconos();};});

    AddItem( tipo_premio, CHAR, REQ, "tipo_premio" , JWins.createVirtualWinsFromMap(BizNivel.getTiposPremios()));
    AddItem( tipo_objetivo, CHAR, REQ, "tipo_objetivo", JWins.createVirtualWinsFromMap(BizNivel.getTiposObjetivos()) );
    AddItem( objetivo_extra, OPT, "objetivo_extra" );
    JFormControl c=AddItem( detalle, CHAR, OPT, "detalle" );
    c.setKeepHeight(true);
    c.setKeepWidth(true);

  } 
  
  
  JWins getTiposContratos(boolean one) throws Exception {
  	return JWins.createVirtualWinsFromMap(BizDetalle.getLogicasContrato());
  }
  JWins getVariable(boolean one) throws Exception {
  	GuiSqlEvents events = new GuiBSPSqlEvents();
  	if (one) events.getRecords().addFilter("id",getWin().GetcDato().getIdvariable());
  	else	events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
  	return events;
  }
  JWins getVariableGanancia(boolean one) throws Exception {
  	GuiSqlEvents events = new GuiBSPSqlEvents();
  	if (one) events.getRecords().addFilter("id",getWin().GetcDato().getIdvariableganancia());
  	else	events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
  	return events;
  }
  JWins getVariableAuxiliar(boolean one) throws Exception {
  	GuiSqlEvents events = new GuiBSPSqlEvents();
  	if (one) events.getRecords().addFilter("id",getWin().GetcDato().getIdvariableAux());
  	else	events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
  	return events;
  }
  JWins getAerolineas(boolean one) throws Exception {
  	GuiCarriers events = new GuiCarriers();
  	if (one) events.getRecords().addFilter("carrier",getWin().GetcDato().getAerolineas());
  	return events;
  }

	/**
	 * This method initializes periodo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getPeriodo() {
		if (periodo == null) {
			periodo = new JComboBox();
			periodo.setBounds(new Rectangle(496, 55, 160, 20));
		}
		return periodo;
	}
}  //  @jve:decl-index=0:visual-constraint="14,3"
