package  pss.bsp.bo.formato;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormFormato extends JBaseForm {


private static final long serialVersionUID = 1245698898912L;

  JPssEdit company = new JPssEdit  ();
JPssEdit owner = new JPssEdit  ();
JPssEdit id_formato = new JPssEdit  ();
JPssLabel ld1 = new JPssLabel();
JComboBox d1 = new JComboBox  ();
JPssLabel ld2 = new JPssLabel();
JComboBox d2 = new JComboBox  ();
JPssLabel ld3 = new JPssLabel();
JComboBox d3 = new JComboBox  ();
JPssLabel ld4 = new JPssLabel();
JComboBox d4 = new JComboBox  ();
JPssLabel ld5 = new JPssLabel();
JComboBox d5 = new JComboBox  ();
JPssLabel ld6 = new JPssLabel();
JComboBox d6 = new JComboBox  ();
JPssLabel ld7 = new JPssLabel();
JComboBox d7 = new JComboBox  ();
JPssLabel ld8 = new JPssLabel();
JComboBox d8 = new JComboBox  ();
JPssLabel ld9 = new JPssLabel();
JComboBox d9 = new JComboBox  ();
JPssLabel ld10 = new JPssLabel();
JComboBox d10 = new JComboBox  ();
JPssLabel ld11 = new JPssLabel();
JComboBox d11 = new JComboBox  ();
JPssLabel ld12 = new JPssLabel();
JComboBox d12 = new JComboBox  ();
JPssLabel ld13 = new JPssLabel();
JComboBox d13 = new JComboBox  ();
JPssLabel ld14 = new JPssLabel();
JComboBox d14 = new JComboBox  ();
JPssLabel ld15 = new JPssLabel();
JComboBox d15 = new JComboBox  ();
JPssLabel ld16 = new JPssLabel();
JComboBox d16 = new JComboBox  ();
JPssLabel ld17 = new JPssLabel();
JComboBox d17 = new JComboBox  ();
JPssLabel ld18 = new JPssLabel();
JComboBox d18 = new JComboBox  ();
JPssLabel ld19 = new JPssLabel();
JComboBox d19 = new JComboBox  ();
JPssLabel ld20 = new JPssLabel();
JComboBox d20 = new JComboBox  ();
JPssLabel ld21 = new JPssLabel();
JComboBox d21 = new JComboBox  ();
JPssLabel ld22 = new JPssLabel();
JComboBox d22 = new JComboBox  ();
JPssLabel ld23 = new JPssLabel();
JComboBox d23 = new JComboBox  ();
JPssLabel ld24 = new JPssLabel();
JComboBox d24 = new JComboBox  ();
JPssLabel ld25 = new JPssLabel();
JComboBox d25 = new JComboBox  ();
JPssLabel ld26 = new JPssLabel();
JComboBox d26 = new JComboBox  ();
JPssLabel ld27 = new JPssLabel();
JComboBox d27 = new JComboBox  ();
JPssLabel ld28 = new JPssLabel();
JComboBox d28 = new JComboBox  ();
JPssLabel ld29 = new JPssLabel();
JComboBox d29 = new JComboBox  ();
JPssLabel ld30 = new JPssLabel();
JComboBox d30 = new JComboBox  ();
JPssLabel ld31= new JPssLabel();
JComboBox d31 = new JComboBox  ();
JPssLabel ld32 = new JPssLabel();
JComboBox d32 = new JComboBox  ();
JPssLabel ld33 = new JPssLabel();
JComboBox d33 = new JComboBox  ();
JPssLabel ld34 = new JPssLabel();
JComboBox d34 = new JComboBox  ();
JPssLabel ld35 = new JPssLabel();
JComboBox d35 = new JComboBox  ();
JPssLabel ld36 = new JPssLabel();
JComboBox d36 = new JComboBox  ();
JPssLabel ld37 = new JPssLabel();
JComboBox d37 = new JComboBox  ();
JPssLabel ld38 = new JPssLabel();
JComboBox d38 = new JComboBox  ();
JPssLabel ld39 = new JPssLabel();
JComboBox d39 = new JComboBox  ();
JPssLabel ld40 = new JPssLabel();
JComboBox d40 = new JComboBox  ();
JPssLabel ldescripcion = new JPssLabel();
JPssEdit descripcion = new JPssEdit  ();

private JLabel jLabel = null;

private JTabbedPane jTabbedPane = null;


  /**
   * Constructor de la Clase
   */
  public FormFormato() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiFormato getWin() { return (GuiFormato) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    int despl = 600;
    jLabel = new JLabel();
    jLabel.setBounds(new Rectangle(30, 313, 545, 26));
    jLabel.setText("* indica que la columna carecera de importancia en la conciliacion.");
    setLayout(null);
    setSize(new Dimension(1194, 575));


    company.setBounds(new Rectangle(5, 5, 10, 22)); 
    add(company , null);


    owner.setBounds(new Rectangle(5, 32, 12, 22)); 
    add(owner , null);


    id_formato.setBounds(new Rectangle(3, 57, 13, 22)); 
    add(id_formato , null);


    ld1.setText( "D1" );
    ld1.setBounds(new Rectangle(29, 42, 123, 22)); 
    d1.setBounds(new Rectangle(157, 42, 143, 22)); 
    add(ld1, null);
    add(d1 , null);


    ld2.setText( "D2" );
    ld2.setBounds(new Rectangle(29, 69, 123, 22)); 
    d2.setBounds(new Rectangle(157, 69, 143, 22)); 
    add(ld2, null);
    add(d2 , null);


    ld3.setText( "D3" );
    ld3.setBounds(new Rectangle(29, 96, 123, 22)); 
    d3.setBounds(new Rectangle(157, 96, 143, 22)); 
    add(ld3, null);
    add(d3 , null);


    ld4.setText( "D4" );
    ld4.setBounds(new Rectangle(29, 123, 123, 22)); 
    d4.setBounds(new Rectangle(157, 123, 143, 22)); 
    add(ld4, null);
    add(d4 , null);


    ld5.setText( "D5" );
    ld5.setBounds(new Rectangle(29, 150, 123, 22)); 
    d5.setBounds(new Rectangle(157, 150, 143, 22)); 
    add(ld5, null);
    add(d5 , null);


    ld6.setText( "D6" );
    ld6.setBounds(new Rectangle(29, 177, 123, 22)); 
    d6.setBounds(new Rectangle(157, 177, 143, 22)); 
    add(ld6, null);
    add(d6 , null);


    ld7.setText( "D7" );
    ld7.setBounds(new Rectangle(29, 204, 123, 22)); 
    d7.setBounds(new Rectangle(157, 204, 143, 22)); 
    add(ld7, null);
    add(d7 , null);


    ld8.setText( "D8" );
    ld8.setBounds(new Rectangle(29, 231, 123, 22)); 
    d8.setBounds(new Rectangle(157, 231, 143, 22)); 
    add(ld8, null);
    add(d8 , null);


    ld9.setText( "D9" );
    ld9.setBounds(new Rectangle(29, 258, 123, 22)); 
    d9.setBounds(new Rectangle(157, 258, 143, 22)); 
    add(ld9, null);
    add(d9 , null);


    ld10.setText( "D10" );
    ld10.setBounds(new Rectangle(29, 285, 123, 22)); 
    d10.setBounds(new Rectangle(157, 285, 143, 22)); 
    add(ld10, null);
    add(d10 , null);


    ld11.setText( "D11" );
    ld11.setBounds(new Rectangle(307, 42, 123, 22)); 
    d11.setBounds(new Rectangle(435, 42, 143, 22)); 
    add(ld11, null);
    add(d11 , null);


    ld12.setText( "D12" );
    ld12.setBounds(new Rectangle(307, 69, 123, 22)); 
    d12.setBounds(new Rectangle(435, 69, 143, 22)); 
    add(ld12, null);
    add(d12 , null);


    ld13.setText( "D13" );
    ld13.setBounds(new Rectangle(307, 96, 123, 22)); 
    d13.setBounds(new Rectangle(435, 96, 143, 22)); 
    add(ld13, null);
    add(d13 , null);


    ld14.setText( "D14" );
    ld14.setBounds(new Rectangle(307, 123, 123, 22)); 
    d14.setBounds(new Rectangle(435, 123, 143, 22)); 
    add(ld14, null);
    add(d14 , null);


    ld15.setText( "D15" );
    ld15.setBounds(new Rectangle(307, 150, 123, 22)); 
    d15.setBounds(new Rectangle(435, 150, 143, 22)); 
    add(ld15, null);
    add(d15 , null);


    ld16.setText( "D16" );
    ld16.setBounds(new Rectangle(307, 177, 123, 22)); 
    d16.setBounds(new Rectangle(435, 177, 143, 22)); 
    add(ld16, null);
    add(d16 , null);


    ld17.setText( "D17" );
    ld17.setBounds(new Rectangle(307, 204, 123, 22)); 
    d17.setBounds(new Rectangle(435, 204, 143, 22)); 
    add(ld17, null);
    add(d17 , null);


    ld18.setText( "D18" );
    ld18.setBounds(new Rectangle(307, 231, 123, 22)); 
    d18.setBounds(new Rectangle(435, 231, 143, 22)); 
    add(ld18, null);
    add(d18 , null);


    ld19.setText( "D19" );
    ld19.setBounds(new Rectangle(307, 258, 123, 22)); 
    d19.setBounds(new Rectangle(435, 258, 143, 22)); 
    add(ld19, null);
    add(d19 , null);


    ld20.setText( "D20" );
    ld20.setBounds(new Rectangle(307, 285, 123, 22)); 
    d20.setBounds(new Rectangle(435, 285, 143, 22)); 
    add(ld20, null);
    add(d20 , null);

    ld21.setText( "D21" );
    ld21.setBounds(new Rectangle(despl+29, 42, 123, 22)); 
    d21.setBounds(new Rectangle(despl+157, 42, 143, 22)); 
    add(ld21, null);
    add(d21 , null);


    ld22.setText( "D22" );
    ld22.setBounds(new Rectangle(despl+29, 69, 123, 22)); 
    d22.setBounds(new Rectangle(despl+157, 69, 143, 22)); 
    add(ld22, null);
    add(d22 , null);


    ld23.setText( "D23" );
    ld23.setBounds(new Rectangle(despl+29, 96, 123, 22)); 
    d23.setBounds(new Rectangle(despl+157, 96, 143, 22)); 
    add(ld23, null);
    add(d23 , null);


    ld24.setText( "D24" );
    ld24.setBounds(new Rectangle(despl+29, 123, 123, 22)); 
    d24.setBounds(new Rectangle(despl+157, 123, 143, 22)); 
    add(ld24, null);
    add(d24 , null);


    ld25.setText( "D25" );
    ld25.setBounds(new Rectangle(despl+29, 150, 123, 22)); 
    d25.setBounds(new Rectangle(despl+157, 150, 143, 22)); 
    add(ld25, null);
    add(d25 , null);


    ld26.setText( "D26" );
    ld26.setBounds(new Rectangle(despl+29, 177, 123, 22)); 
    d26.setBounds(new Rectangle(despl+157, 177, 143, 22)); 
    add(ld26, null);
    add(d26 , null);


    ld27.setText( "D27" );
    ld27.setBounds(new Rectangle(despl+29, 204, 123, 22)); 
    d27.setBounds(new Rectangle(despl+157, 204, 143, 22)); 
    add(ld27, null);
    add(d27 , null);


    ld28.setText( "D28" );
    ld28.setBounds(new Rectangle(despl+29, 231, 123, 22)); 
    d28.setBounds(new Rectangle(despl+157, 231, 143, 22)); 
    add(ld28, null);
    add(d28 , null);


    ld29.setText( "D29" );
    ld29.setBounds(new Rectangle(despl+29, 258, 123, 22)); 
    d29.setBounds(new Rectangle(despl+157, 258, 143, 22)); 
    add(ld29, null);
    add(d29 , null);


    ld30.setText( "D30" );
    ld30.setBounds(new Rectangle(despl+29, 285, 123, 22)); 
    d30.setBounds(new Rectangle(despl+157, 285, 143, 22)); 
    add(ld30, null);
    add(d30 , null);


    ld31.setText( "D31" );
    ld31.setBounds(new Rectangle(despl+307, 42, 123, 22)); 
    d31.setBounds(new Rectangle(despl+435, 42, 143, 22)); 
    add(ld31, null);
    add(d31 , null);


    ld32.setText( "D32" );
    ld32.setBounds(new Rectangle(despl+307, 69, 123, 22)); 
    d32.setBounds(new Rectangle(despl+435, 69, 143, 22)); 
    add(ld32, null);
    add(d32 , null);


    ld33.setText( "D33" );
    ld33.setBounds(new Rectangle(despl+307, 96, 123, 22)); 
    d33.setBounds(new Rectangle(despl+435, 96, 143, 22)); 
    add(ld33, null);
    add(d33 , null);


    ld34.setText( "D34" );
    ld34.setBounds(new Rectangle(despl+307, 123, 123, 22)); 
    d34.setBounds(new Rectangle(despl+435, 123, 143, 22)); 
    add(ld34, null);
    add(d34 , null);


    ld35.setText( "D35" );
    ld35.setBounds(new Rectangle(despl+307, 150, 123, 22)); 
    d35.setBounds(new Rectangle(despl+435, 150, 143, 22)); 
    add(ld35, null);
    add(d35 , null);


    ld36.setText( "D36" );
    ld36.setBounds(new Rectangle(despl+307, 177, 123, 22)); 
    d36.setBounds(new Rectangle(despl+435, 177, 143, 22)); 
    add(ld36, null);
    add(d36 , null);


    ld37.setText( "D37" );
    ld37.setBounds(new Rectangle(despl+307, 204, 123, 22)); 
    d37.setBounds(new Rectangle(despl+435, 204, 143, 22)); 
    add(ld37, null);
    add(d37 , null);


    ld38.setText( "D38" );
    ld38.setBounds(new Rectangle(despl+307, 231, 123, 22)); 
    d38.setBounds(new Rectangle(despl+435, 231, 143, 22)); 
    add(ld38, null);
    add(d38 , null);


    ld39.setText( "D39" );
    ld39.setBounds(new Rectangle(despl+307, 258, 123, 22)); 
    d39.setBounds(new Rectangle(despl+435, 258, 143, 22)); 
    add(ld39, null);
    add(d39 , null);


    ld40.setText( "D40" );
    ld40.setBounds(new Rectangle(despl+307, 285, 123, 22)); 
    d40.setBounds(new Rectangle(despl+435, 285, 143, 22)); 
    add(ld40, null);
    add(d40 , null);

    ldescripcion.setText( "Descripcion" );
    ldescripcion.setBounds(new Rectangle(29, 14, 123, 22)); 
    descripcion.setBounds(new Rectangle(157, 15, 419, 22)); 
    add(ldescripcion, null);
    add(descripcion , null);
    this.add(jLabel, null);
    this.add(getJTabbedPane(), null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( company, CHAR, REQ, "company" ).setVisible(false);
    AddItem( owner, CHAR, REQ, "owner" ).setVisible(false);
    AddItem( id_formato, CHAR, REQ, "id_formato" ).setVisible(false);
    AddItem( d1, CHAR, REQ, "d1" , getCamposPosibles());
    AddItem( d2, CHAR, REQ, "d2" , getCamposPosibles());
    AddItem( d3, CHAR, REQ, "d3" , getCamposPosibles());
    AddItem( d4, CHAR, REQ, "d4" , getCamposPosibles());
    AddItem( d5, CHAR, REQ, "d5" , getCamposPosibles());
    AddItem( d6, CHAR, REQ, "d6" , getCamposPosibles());
    AddItem( d7, CHAR, REQ, "d7" , getCamposPosibles());
    AddItem( d8, CHAR, REQ, "d8" , getCamposPosibles());
    AddItem( d9, CHAR, REQ, "d9" , getCamposPosibles());
    AddItem( d10, CHAR, REQ, "d10" , getCamposPosibles());
    AddItem( d11, CHAR, REQ, "d11" , getCamposPosibles());
    AddItem( d12, CHAR, REQ, "d12" , getCamposPosibles());
    AddItem( d13, CHAR, REQ, "d13" , getCamposPosibles());
    AddItem( d14, CHAR, REQ, "d14" , getCamposPosibles());
    AddItem( d15, CHAR, REQ, "d15" , getCamposPosibles());
    AddItem( d16, CHAR, REQ, "d16" , getCamposPosibles());
    AddItem( d17, CHAR, REQ, "d17" , getCamposPosibles());
    AddItem( d18, CHAR, REQ, "d18" , getCamposPosibles());
    AddItem( d19, CHAR, REQ, "d19" , getCamposPosibles());
    AddItem( d20, CHAR, REQ, "d20" , getCamposPosibles());
    AddItem( d21, CHAR, REQ, "d21" , getCamposPosibles());
    AddItem( d22, CHAR, REQ, "d22" , getCamposPosibles());
    AddItem( d23, CHAR, REQ, "d23" , getCamposPosibles());
    AddItem( d24, CHAR, REQ, "d24" , getCamposPosibles());
    AddItem( d25, CHAR, REQ, "d25" , getCamposPosibles());
    AddItem( d26, CHAR, REQ, "d26" , getCamposPosibles());
    AddItem( d27, CHAR, REQ, "d27" , getCamposPosibles());
    AddItem( d28, CHAR, REQ, "d28" , getCamposPosibles());
    AddItem( d29, CHAR, REQ, "d29" , getCamposPosibles());
    AddItem( d30, CHAR, REQ, "d30" , getCamposPosibles());
    AddItem( d31, CHAR, REQ, "d31" , getCamposPosibles());
    AddItem( d32, CHAR, REQ, "d32" , getCamposPosibles());
    AddItem( d33, CHAR, REQ, "d33" , getCamposPosibles());
    AddItem( d34, CHAR, REQ, "d34" , getCamposPosibles());
    AddItem( d35, CHAR, REQ, "d35" , getCamposPosibles());
    AddItem( d36, CHAR, REQ, "d36" , getCamposPosibles());
    AddItem( d37, CHAR, REQ, "d37" , getCamposPosibles());
    AddItem( d38, CHAR, REQ, "d38" , getCamposPosibles());
    AddItem( d39, CHAR, REQ, "d39" , getCamposPosibles());
    AddItem( d40, CHAR, REQ, "d40" , getCamposPosibles());   
    AddItem( descripcion, CHAR, REQ, "descripcion" );
    AddItem( getJTabbedPane(), 20);

  } 
  
  public JWins getCamposPosibles() throws Exception {
  	return JWins.createVirtualWinsFromMap(BizFormato.getCamposPosibles());
  }

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(27, 344, 1151, 222));
		}
		return jTabbedPane;
	}
  
}  //  @jve:decl-index=0:visual-constraint="10,10" 
