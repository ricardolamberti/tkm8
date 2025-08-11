package pss.common.tableGenerator;

import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;




public class FormTableGenerator extends JBaseForm {

  /**
	 * 
	 */
	private static final long serialVersionUID = 6489783207444211311L;





	//-------------------------------------------------------------------------//




  //-------------------------------------------------------------------------//
  // Constructor de la clase
  //-------------------------------------------------------------------------//
  public FormTableGenerator()  {
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //-------------------------------------------------------------------------//
  // Dibujo el form
  //-------------------------------------------------------------------------//
  protected void jbInit() throws Exception {


  }

  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
		AddItemEdit("Paquete Java", CHAR, REQ, "package");
		AddItemEdit("Sufijo singular", CHAR, REQ, "ssingular");
		AddItemEdit("Sufijo plural", CHAR, REQ, "splural");
		AddItemEdit("Título controlador singular (JWin)", CHAR, REQ, "twin");
		AddItemEdit("Titulo controlador plural (JWins)", CHAR, REQ, "twins");
		AddItemWinLov("Icono", UINT, REQ, "icono", new JControlWin() {
			@Override
			public JWins getRecords() throws Exception {
				return new GuiIconos();
			};
		});
		AddItemWinLov("Campo descripción", CHAR, REQ, "itemdesc", new JControlWin() {
			@Override
			public JWins getRecords() throws Exception {
				return getTableColumns();
			};
		});
		AddItemCombo("Campo clave", CHAR, REQ, "itemclave", new JControlCombo() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getKeyTableColumns();
			}

		});
		
		AddItemCheck("Con comillas", CHAR, REQ, "comillas", "S", "N");
		AddItemWinLov("Campos clave", CHAR, REQ, "claves", new JControlWin() {
			@Override
			public JWins getRecords() throws Exception {
				return getTableColumns();
			};
		}, true, false, -1);

	}
  
	@Override
	public void checkControls(String sControlId) throws Exception {
	  String packageName = getControles().findControl("package").getValue().trim().replace('/', '.').replace('\\', '.');
	  String singularSuffix = getControles().findControl("ssingular").getValue().trim();
	  String pluralSuffix = getControles().findControl("splural").getValue().trim();
//	  this.updateClassLabel(this.jbClassLabel, packageName, "Biz", singularSuffix);
//	  this.updateClassLabel(this.jwClassLabel, packageName, "Gui", singularSuffix);
//	  this.updateClassLabel(this.jwsClassLabel, packageName, "Gui", pluralSuffix);
//	  this.updateClassLabel(this.jfClassLabel, packageName, "Form", singularSuffix);
		super.checkControls(sControlId);
	}


  private JWins getTableColumns() throws Exception {
    GuiTableColumns oWColumns = new GuiTableColumns();
    JRecords oColumns = oWColumns.getRecords();
    JBaseSystemDBTable oDato = (JBaseSystemDBTable) ((BizTableGenerator)this.oWin.getRecord()).getTableGenerator().getRecord();

    oColumns.clearFilters();
    oColumns.addFilter( "TABLE_NAME", oDato.getTableName() );
    oColumns.readAll();
    oColumns.toStatic();
    oColumns.setStatic(true);

    return oWColumns;
  }
  
  @SuppressWarnings("unchecked")
	private JWins getKeyTableColumns() throws Exception {
    JWins oWColumns = this.getTableColumns();
    JList toRemove = JCollectionFactory.createList();
    oWColumns.firstRecord();
    while (oWColumns.nextRecord()) {
      JRecord oColumn = oWColumns.getRecord().getRecord();
      if (!isFieldSelectedAsKey(oColumn.getProp("column_name").toString())) {
        toRemove.addElement(oColumn);
      }
    }
    JIterator oIt = toRemove.getIterator();
    while (oIt.hasMoreElements()) {
      oWColumns.getRecords().removeStaticItem((JRecord)oIt.nextElement());
    }
    return oWColumns;
  }
  private boolean isFieldSelectedAsKey(String zField) throws Exception {
    return ((JFormWinLOVResponsive)getControles().findControl("claves")).containsValueIgnoreCase(zField);
  }





  @Override
	@SuppressWarnings("deprecation")
	public void OnShow() throws Exception {
  	JBaseSystemDBTable oDato = (JBaseSystemDBTable) ((BizTableGenerator)this.oWin.getRecord()).getTableGenerator().getRecord();
    this.setTitle("Generación automática, tabla: " + oDato.getTableName());
  }

}  //  @jve:decl-index=0:visual-constraint="10,10"


