package  pss.common.personalData.personaRegistro;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JComboBox;

import pss.common.personalData.personaRegistro.registros.GuiRegistros;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormPersonaRegistro extends JBaseForm {


private static final long serialVersionUID = 1218227728984L;

  JPssEdit iddoc = new JPssEdit  ();
JPssLabel lfecha = new JPssLabel();
JPssCalendarEdit fecha = new JPssCalendarEdit();
JPssLabel lid_tipo_hito = new JPssLabel();
JComboBox idArchivo = new JComboBox  ();

private JPssLabel lTomo = null;

private JPssLabel lFolio = null;

private JPssLabel lNro = null;

private JPssEdit jTomo = null;

private JPssEdit jFolio = null;

private JPssEdit jNro = null;

/**
   * Constructor de la Clase
   */
  public FormPersonaRegistro() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiPersonaRegistro GetWin() { return (GuiPersonaRegistro) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    lNro = new JPssLabel();
    lNro.setBounds(new Rectangle(11, 108, 65, 22));
    lNro.setText("Nro");
    lFolio = new JPssLabel();
    lFolio.setBounds(new Rectangle(11, 84, 65, 22));
    lFolio.setText("Folio");
    lTomo = new JPssLabel();
    lTomo.setBounds(new Rectangle(11, 60, 65, 22));
    lTomo.setText("Tomo");
    setLayout(null);
    setSize(new Dimension(382, 176));



    lfecha.setText( "Fecha" );
    lfecha.setBounds(new Rectangle(11, 132, 65, 22)); 
    fecha.setBounds(new Rectangle(82, 132, 93, 22)); 
    add(lfecha, null);
    add(fecha , null);


    lid_tipo_hito.setText("Registro");
    lid_tipo_hito.setBounds(new Rectangle(11, 12, 65, 22)); 
    idArchivo.setBounds(new Rectangle(82, 12, 242, 22)); 
    add(lid_tipo_hito, null);
    add(idArchivo , null);
    this.add(lTomo, null);
    this.add(lFolio, null);
    this.add(lNro, null);
    this.add(getJTomo(), null);
    this.add(getJFolio(), null);
    this.add(getJNro(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    JFormControl c;
  	AddItem( iddoc, ULONG, REQ, "id_persona" );
    AddItem( idArchivo, UINT, REQ, "id_registro", this.getRegistros() );
//    AddItem( jAnio, CHAR, REQ, "anio").setPopupIcon(false);
    AddItem( jTomo, CHAR, REQ, "tomo").setPopupIcon(false);
    AddItem( jFolio, CHAR, REQ, "folio" ).setPopupIcon(false);
//    c=AddItem( jFolioBis, CHAR, REQ, "folio_bis" );
//    c.setPopupIcon(false);
//    c.SetValorDefault(0);
    AddItem( jNro, CHAR, REQ, "nro" ).setPopupIcon(false);
//    c=AddItem( jNroBis, CHAR, REQ, "nro_bis" );
//    c.setPopupIcon(false);
//    c.SetValorDefault(0);
    AddItem( fecha, DATE, REQ, "fecha").SetValorDefault(new Date());
  } 
  
  public JWins getRegistros() throws Exception {
  	GuiRegistros a = new GuiRegistros();
  	a.getRecords().addFilter("id", this.GetWin().GetcDato().getIdRegistro());
  	return a;
  }
  
//  public JWins getTiposHitos() throws Exception {
//  	return JWins.createVirtualWinsFromMap(BizDocFisicoArchivar.getTipoHitos());
//  }
  
//  @Override
//  	public void checkControls(String sControlId) throws Exception {
//  		if (sControlId.equals("id_archivo")) {
//  			BizArchivo a = new BizArchivo();
//  			a.read(this.GetWin().GetcDato().getIdArchivo());
//  			this.findControl("anio").SetValorDefault(a.getAnio());
//  			this.findControl("tomo").SetValorDefault(a.getTomo());
//  			this.findControl("folio").SetValorDefault(a.getFolio());
//  			this.findControl("nro").SetValorDefault(a.getNro()+1);
//  			this.GetControles().asignDefaultData();
//  		}
//  	}

	/**
	 * This method initializes jTomo	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJTomo() {
		if (jTomo == null) {
			jTomo = new JPssEdit();
			jTomo.setBounds(new Rectangle(82, 60, 67, 22));
		}
		return jTomo;
	}

	/**
	 * This method initializes jFolio	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJFolio() {
		if (jFolio == null) {
			jFolio = new JPssEdit();
			jFolio.setBounds(new Rectangle(82, 84, 67, 22));
		}
		return jFolio;
	}

	/**
	 * This method initializes jNro	
	 * 	
	 * @return pss.core.ui.components.JPssEdit	
	 */
	private JPssEdit getJNro() {
		if (jNro == null) {
			jNro = new JPssEdit();
			jNro.setBounds(new Rectangle(82, 108, 67, 22));
		}
		return jNro;
	}
}  //  @jve:decl-index=0:visual-constraint="-11,35"
