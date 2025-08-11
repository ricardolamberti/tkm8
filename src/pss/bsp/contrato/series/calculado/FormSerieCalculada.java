package pss.bsp.contrato.series.calculado;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormSerieCalculada extends JBaseForm {


private static final long serialVersionUID = 1446899454830L;

  /**
   * Propiedades de la Clase
   */
JPssLabel lmodelo = new JPssLabel();
JPssEdit modelo = new JPssEdit  ();
JPssLabel lvalor = new JPssLabel();
JPssEdit valor = new JPssEdit  ();
JPssLabel lacumulado = new JPssLabel();
JPssEdit acumulado = new JPssEdit  ();
JPssLabel lanio = new JPssLabel();
JPssEdit anio = new JPssEdit  ();
JPssLabel lmes = new JPssLabel();
JPssEdit mes = new JPssEdit  ();
JPssLabel lvariable = new JPssLabel();
JPssEdit variable = new JPssEdit  ();
JPssLabel lcompany = new JPssLabel();
JPssEdit company = new JPssEdit  ();
JPssLabel lid = new JPssLabel();
JPssEdit id = new JPssEdit  ();


  /**
   * Constructor de la Clase
   */
  public FormSerieCalculada() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiSerieCalculada getWin() { return (GuiSerieCalculada) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {
    setLayout(null);
    setSize(new Dimension(357, 110+176));


    lmodelo.setText( "Modelo" );
    lmodelo.setBounds(new Rectangle(40, 44+0, 123, 22)); 
    modelo.setBounds(new Rectangle(168, 44+0, 143, 22)); 
    add(lmodelo, null);
    add(modelo , null);


    lvalor.setText( "Valor" );
    lvalor.setBounds(new Rectangle(40, 44+27, 123, 22)); 
    valor.setBounds(new Rectangle(168, 44+27, 143, 22)); 
    add(lvalor, null);
    add(valor , null);


    lacumulado.setText( "Acumulado" );
    lacumulado.setBounds(new Rectangle(40, 44+54, 123, 22)); 
    acumulado.setBounds(new Rectangle(168, 44+54, 143, 22)); 
    add(lacumulado, null);
    add(acumulado , null);


    lanio.setText( "Anio" );
    lanio.setBounds(new Rectangle(40, 44+81, 123, 22)); 
    anio.setBounds(new Rectangle(168, 44+81, 143, 22)); 
    add(lanio, null);
    add(anio , null);


    lmes.setText( "Mes" );
    lmes.setBounds(new Rectangle(40, 44+108, 123, 22)); 
    mes.setBounds(new Rectangle(168, 44+108, 143, 22)); 
    add(lmes, null);
    add(mes , null);


    lvariable.setText( "Variable" );
    lvariable.setBounds(new Rectangle(40, 44+135, 123, 22)); 
    variable.setBounds(new Rectangle(168, 44+135, 143, 22)); 
    add(lvariable, null);
    add(variable , null);


    lcompany.setText( "Company" );
    lcompany.setBounds(new Rectangle(40, 44+162, 123, 22)); 
    company.setBounds(new Rectangle(168, 44+162, 143, 22)); 
    add(lcompany, null);
    add(company , null);


    lid.setText( "Id" );
    lid.setBounds(new Rectangle(40, 44+189, 123, 22)); 
    id.setBounds(new Rectangle(168, 44+189, 143, 22)); 
    add(lid, null);
    add(id , null);
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItem( modelo, UINT, REQ, "modelo" );
    AddItem( valor, UFLOAT, REQ, "valor" );
    AddItem( acumulado, UFLOAT, REQ, "acumulado" );
    AddItem( anio, UINT, REQ, "anio" );
    AddItem( mes, UINT, REQ, "mes" );
    AddItem( variable, CHAR, REQ, "variable" );
    AddItem( company, CHAR, REQ, "company" );
    AddItem( id, UINT, REQ, "id" );

  } 
} 
