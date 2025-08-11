package pss.common.layout;

import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiLayout extends JWin {

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public GuiLayout() throws Exception {
  }
  
  @Override
	public int GetNroIcono()       throws Exception { return 204; }
  @Override
	public String GetTitle()       throws Exception { return "Layout"; }
  @Override
	public Class<? extends JBaseForm> getFormBase()     throws Exception { return FormLayout.class; }
  @Override
	public String getKeyField()    throws Exception { return "layout"; }
  @Override
	public String getDescripField()                 { return "descripcion"; }
  @Override
	public JRecord ObtenerDato()       throws Exception { return new BizLayout(); }

  @Override
	public void createActionMap() throws Exception {
    this.addActionQuery (1, "Consultar" );
    this.addActionUpdate(2, "Modificar" );
    this.addActionDelete(3, "Eliminar"  );
    this.addAction(10, "Campos", null, 201, false, false, true, "Detail");
    this.addAction(11, "Clonar Layout", null, 857, true, true );
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==10 ) return new JActWins(this.ObtenerCampos());
  	if ( a.getId()==11 ) return new JActNew(this.getNewClonarLayout(), 0);  	
  	return null;
  }   

  public BizLayout GetcDato() throws Exception {
    return (BizLayout) this.getRecord();
  }

  public GuiLayoutCampos ObtenerCampos() throws Exception {
    GuiLayoutCampos oCampos = new GuiLayoutCampos();
    oCampos.getRecords().addFilter("company", GetcDato().pCompany.getValue());
    oCampos.getRecords().addFilter("pais", GetcDato().pPais.getValue());
    oCampos.getRecords().addFilter("layout", GetcDato().pLayout.getValue());
    oCampos.getRecords().addOrderBy("sector");
    oCampos.getRecords().addOrderBy("y");
    oCampos.getRecords().addOrderBy("x");
    return oCampos;
  }

  public static JWins ObtenerControles() throws Exception {
    JRecords<BizVirtual> oControles = JRecords.createVirtualBDs();
    oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_NEW_LINE, "Nueva Línea", 1));
    oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_NEW_PAGE, "Nueva Pagina", 1));
    oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_SECTOR_LINES, "Sector Lines", 1));
    oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_BREAK_ON, "Break On", 1));
    oControles.addItem(JRecord.virtualBD(BizLayout.CONTROL_STOPSECTOR_ON, "Stop On", 1));    
    return JWins.CreateVirtualWins(oControles);
  }

  public GuiLayoutClonar getNewClonarLayout()throws Exception{
  	GuiLayoutClonar oClonarLayout = new GuiLayoutClonar();
  	oClonarLayout.GetcDato().addFilter("company", GetcDato().pCompany.getValue());
  	oClonarLayout.GetcDato().addFilter("pais", GetcDato().pPais.getValue());
  	oClonarLayout.GetcDato().addFilter("layout", GetcDato().pLayout.getValue());
  	oClonarLayout.GetcDato().setObjLayout(GetcDato());
  	return oClonarLayout;
  }
}
