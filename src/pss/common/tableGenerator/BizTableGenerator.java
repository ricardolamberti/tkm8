package pss.common.tableGenerator;

import java.util.Date;

import pss.JPath;
import pss.common.dbManagement.synchro.base.JBaseSystemDBField;
import pss.common.dbManagement.synchro.base.JBaseSystemDBFields;
import pss.common.dbManagement.synchro.base.JBaseSystemDBTable;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizTableGenerator extends JRecord {

	public GuiTable oWTabla;
	private JGenTools oGenerator;
	private JBaseSystemDBFields oColumns; // = new BizColumnas();
	@SuppressWarnings("unused")
	private JBaseSystemDBTable oDato;
	private String sPkg;
	private String sTitle;
	private String sClass;
	@SuppressWarnings("unused")
	private String sClasses;
	private String sWClass;
	private String sWClasses;
	private String sfClass;
	private String sTabla;
	private String sDataType;
	private String sDesc;
	private String sIcono;
	private String sTitulo;
	private String sSingular;
	private String sPlural;
	private String autonum = "";
	@SuppressWarnings("unused")
	private boolean bNoIdx;
	private int iMaxFields = 0;

	public String tablefilter = null;
	public boolean generateUpperClass = true;

	// class properties
	public JString pPackage = new JString();
	public JString pTableName = new JString();
	public JString pSSingular = new JString();
	public JString pSPlural = new JString();
	public JString pTWin = new JString();
	public JString pTWins = new JString();
	public JString pIcono = new JString();
	public JString pItemDesc = new JString();
	public JString pItemClave = new JString();
	public JMultiple pClaves = new JMultiple();
	public JBoolean pUseQuotes = new JBoolean();

	/**
	 * Constructor
	 */
	public BizTableGenerator() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("package", this.pPackage);
		this.addItem("table_name", this.pTableName);
		this.addItem("ssingular", this.pSSingular);
		this.addItem("splural", this.pSPlural);
		this.addItem("twin", this.pTWin);
		this.addItem("twins", this.pTWins);
		this.addItem("icono", this.pIcono);
		this.addItem("itemdesc", this.pItemDesc);
		this.addItem("itemclave", this.pItemClave);
		this.addItem("claves", this.pClaves);
		this.addItem("comillas", this.pUseQuotes);
		super.createProperties();
	}
	/**
	 * Adds the fixex properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "package", "Package", true, true, 250);
		this.addFixedItem(FIELD, "table_name", "table_name", true, true, 250);
		this.addFixedItem(FIELD, "ssingular", "Sufijo Singular", true, true, 50);
		this.addFixedItem(FIELD, "splural", "Sufijo Plural", true, true, 50);
		this.addFixedItem(FIELD, "twin", "Titulo Win", true, true, 300);
		this.addFixedItem(FIELD, "twins", "Titulo Wins", true, true, 300);
		this.addFixedItem(FIELD, "icono", "Icono", true, true, 20);
		this.addFixedItem(FIELD, "itemdesc", "Item Desc", true, true, 50);
		this.addFixedItem(FIELD, "itemclave", "Item Clave", true, true, 50);
		this.addFixedItem(FIELD, "claves", "Claves", true, false, 100);
		this.addFixedItem(FIELD, "comillas", "comillas", true, false, 1);
	}

	/**
	 * Overrides the table, its not based into a table
	 */
	public String GetTable() {
		return "";
	}
	
	public GuiTable getTableGenerator() throws Exception {
		if (oWTabla==null) {
			oWTabla = new GuiTable();
			JBaseSystemDBTable tbl = JBaseSystemDBTable.VirtualCreate();
			tbl.setTableName(getTableName());
			tbl.read();
			oWTabla.setRecord(tbl);
		}
		return oWTabla;
	}
	
	public String getTableName() throws Exception {
		return pTableName.getValue();
	}
	
	public void setTableName(String tableName) throws Exception {
		pTableName.setValue(tableName);
	}

	private String determineFullPath(String zPackageName) {
		String sPath = zPackageName.replace('.', '/').replace('\\', '/');
		if (sPath.startsWith("/")) {
			sPath = sPath.substring(1);
		}
		// if (sPath.startsWith("Pss")) {
		// sPath = sPath.substring(4, sPath.length());
		// }
		if (sPath.startsWith("/")) {
			sPath = sPath.substring(1);
		}
		if (sPath.endsWith("/")) {
			sPath = sPath.substring(0, sPath.length() - 1);
		}
		// C:\javaworkspace\pss\src
		// C:\javaworkspace\pss\bin\WEB-INF\classes
		String psssrc = JPath.PssPath() + "/../../../src";

		return psssrc + "/" + sPath;
	}

	@Override
	public void processUpdate() throws Exception {
		processInsert();
	}
	/**
	 * Overrides the processInsert() method
	 */
	public void processInsert() throws Exception {
		// String sClaseTabla = "pss.core.BDatos.Metadata.JTabla" +
		// JBDatos.GetBases().GetBaseByName(
		// JBDatos.GetBases().GetDatabaseDefault() ).GetDatabaseType();
		// String sClaseColumna = "pss.core.BDatos.Metadata.JColumna" +
		// JBDatos.GetBases().GetBaseByName(
		// JBDatos.GetBases().GetDatabaseDefault() ).GetDatabaseType();

		String sClaseTabla = JBDatos.getBaseMaster().getSystemDBTableImpl(); // .getMetadataTableImpl();
		String sClaseColumna = JBDatos.getBaseMaster().getSystemDBFieldImpl(); // .getMetadataColumnImpl();

		// String sClaseTabla = "pss.core.data.metadata.JTablaSQLSERVER";
		// String sClaseColumna = "pss.core.data.metadata.JColumnaSQLSERVER";

		this.oColumns = JBaseSystemDBFields.VirtualCreate();
		this.oColumns.clearFilters();
		this.oColumns.applyDefaultFilters();
		// oTbl = (JBaseTabla)Class.forName( (sClaseTabla)).newInstance();

		String sPath = this.determineFullPath(pPackage.getValue());
		this.oGenerator = new JGenTools(sPath);
		sPkg = this.pPackage.getValue();
		sSingular = this.pSSingular.getValue();
		sPlural = this.pSPlural.getValue();
		sTitle = this.pTWins.getValue();
		sDesc = this.pItemDesc.getValue();
		sTitulo = this.pTWin.getValue();
		sIcono = this.pIcono.getValue() + "";

		oDato = (JBaseSystemDBTable) getTableGenerator().getRecord();

		//
		this.oColumns.addFilter("TABLE_NAME", oDato.getTableName());
		this.oColumns.readAll();
		bNoIdx = true;

		// Nombre de la clase Form
		sfClass = "Form" + sSingular;

		// Creo el singular BIZ de la clase
		generateBIZ();

		if (this.generateUpperClass) {

			// Creo el singular JWIN de la clase
			this.generateJWIN();
			// Creo el plural JWIN de la clase
			this.generateJWINS();
			// Creo el singular Form de la clase
			this.generateJForm();

			this.oGenerator.writeEndOfConstructorAndClass();
		}

		this.oGenerator.close();
	}

	private void generateBIZ() throws Exception {
		// Creo el singular JDB de la clase
		sTabla = oDato.getTableName();
		String sClassBase = "Biz" + sSingular + "Base";
		this.oGenerator.createAndWritePackageAndImports(sClassBase, sPkg);
		this.oGenerator.write("import java.util.Date;");
		this.oGenerator.write("import pss.core.services.records.JRecord;");
		this.oGenerator.write("import pss.core.services.fields.*;\n");
		this.oGenerator.writeClass(sClassBase, "JRecord");
		// Genero las propiedades
		this.generateProperties(this.oColumns);
		// Genero el Constructor
		this.oGenerator.writeConstructor(sClassBase);
		this.oGenerator.write("  }\n\n");
		// generates the properties methods
		this.generatePropertyMethods(sTabla, this.oColumns);

		if (this.generateUpperClass) {
			sClass = "Biz" + sSingular;
			this.oGenerator.createAndWritePackageAndImports(sClass, sPkg);
			this.oGenerator.writeClass(sClass, sClassBase);
			this.oGenerator.writeConstructor(sClass);
			this.oGenerator.writeEndOfConstructorAndClass();
		}

	}

	/**
	 * Generates the form
	 */
	private void generateJForm() throws Exception {
		this.oGenerator.createAndWritePackageAndImports(sfClass, sPkg);
		// this.oGenerator.write("import java.awt.Dimension;");
		// this.oGenerator.write("import java.awt.Rectangle;");
		this.oGenerator.write("import pss.core.winUI.forms.JBaseForm;");
		this.oGenerator.write("import pss.core.ui.components.*;");
		this.oGenerator.write("import pss.core.win.JWin;\n");
		this.oGenerator.write("import pss.core.winUI.responsiveControls.JFormEditResponsive;\n");

		this.oGenerator.writeClass(sfClass, "JBaseForm");
		this.oGenerator.write("\nprivate static final long serialVersionUID = " + new Date().getTime() + "L;\n");
		// this.oGenerator.writeComment("Propiedades de la Clase");

		// this.oColumns.firstRecord();
		// while ( this.oColumns.nextRecord() ) {
		// JBaseSystemDBField oCol = (JBaseSystemDBField)
		// this.oColumns.getRecord();
		// String sCol = oCol.getFieldName();
		// this.oGenerator.write("JPssLabel l"+ sCol + " = new JPssLabel();");
		// this.oGenerator.write("JPssEdit "+ sCol + " = new JPssEdit ();");
		// }
		this.oGenerator.writeConstructor(sfClass);
		// this.oGenerator.write( " try { jbInit(); }" );
		// this.oGenerator.write( " catch (Exception e) { e.printStackTrace(); }
		// " );
		this.oGenerator.write("  }");

		this.oGenerator.write("");
		this.oGenerator.write("  public " + sWClass + " getWin() { return (" + sWClass + ") getBaseWin(); }");
		this.oGenerator.write("");

		String sSize = new String();
		sSize = String.valueOf(iMaxFields * 22);
		this.oGenerator.writeComment("Inicializacion Grafica");
		// this.oGenerator.write( " protected void jbInit() throws Exception {"
		// );
		// this.oGenerator.write( " setLayout(null);" );
		// this.oGenerator.write( " setSize(new Dimension(357, 110+"+sSize+"));"
		// );

		// this.oColumns.firstRecord();
		// int iOffSet=0;
		// while ( this.oColumns.nextRecord() ) {
		// JBaseSystemDBField oCol = (JBaseSystemDBField)
		// this.oColumns.getRecord();
		// String sCol = oCol.getFieldName();
		// this.oGenerator.write( "\n" );
		//
		// this.oGenerator.write( " l"+sCol+".setText( \"" +
		// JTools.FirstLetterUpper( sCol ).replace('_',' ') + "\" );" );
		// this.oGenerator.write( " l"+sCol+".setBounds(new Rectangle(40,
		// 44+"+String.valueOf(iOffSet)+", 123, 22)); " );
		// this.oGenerator.write( " "+sCol+".setBounds(new Rectangle(168,
		// 44+"+String.valueOf(iOffSet)+", 143, 22)); " );
		// this.oGenerator.write( " add(l"+sCol+", null);" );
		// this.oGenerator.write( " add("+sCol+" , null);" );
		// iOffSet += 27;
		// }
		// this.oGenerator.write( " }" );

		this.oGenerator.writeComment("Linkeo los campos con la variables del form");
		this.oGenerator.write("  public void InicializarPanel( JWin zWin ) throws Exception {");
		this.oGenerator.write("      super.InicializarPanel(zWin);");
  	

//		this.oColumns.firstRecord();
//		
//		this.oGenerator.write("		JFormEditResponsive edit;");
//
//		
//		while (this.oColumns.nextRecord()) {
//			JBaseSystemDBField oCol = (JBaseSystemDBField) this.oColumns.getRecord();
//			String sCol = oCol.getFieldName();
//			if (oCol.isString()) {
//				sDataType = "CHAR";
//			} else {
//				if (oCol.isDate()) {
//					sDataType = "DATE";
//				} else {
//					if (oCol.isFloat()) {
//						sDataType = "UFLOAT";
//					} else {
//						sDataType = "UINT";
//					}
//				}
//			}
//			String hide = "";
//			String reqtype = "REQ";
//			if (BizTableGenerator.hasToHide(sCol)) {
//				hide = ".setHide(true)";
//				reqtype = "OPT";
//			} else {
//				hide = ".setSizeColumns(6)";
//			}
//			if (oCol.IsNullable())
//				reqtype = "OPT";
//			String baseAdditem = "		edit = AddItemEdit( \"" + BizTableGenerator.getLabel(sCol) + "\",  " + sDataType + ", " + reqtype;
//			if (this.pUseQuotes.getValue()) {
//				baseAdditem += ", \"\\\"" + sCol + "\\\"\" )";
//
//			} else {
//				baseAdditem += ", \"" + sCol + "\" )";
//			}
//			this.oGenerator.write(baseAdditem + ";");
//			this.oGenerator.write("		edit" + hide + ";");

//		}
	}

	/**
	 * Generates the jwins
	 */
	private void generateJWINS() throws Exception {
		sWClasses = "Gui" + sPlural;
		this.oGenerator.createAndWritePackageAndImports(sWClasses, sPkg);
		this.oGenerator.write("import pss.core.win.JWin;");
		this.oGenerator.write("import pss.core.winUI.lists.JWinList;");
		this.oGenerator.write("import pss.core.win.JWins;\n");
		this.oGenerator.writeClass(sWClasses, "JWins");
		this.oGenerator.writeConstructor(sWClasses);
		this.oGenerator.write("  }\n\n");

		this.oGenerator.write("  public int     GetNroIcono() throws Exception  { return " + sIcono + "; } ");
		this.oGenerator.write("  public String  GetTitle()    throws Exception  { return \"" + sTitle + "\"; }");
		this.oGenerator.write("  public Class<? extends JWin>  GetClassWin()                   { return " + sWClass + ".class; }");

		this.oGenerator.writeComment("Mapeo las acciones con las operaciones");
		this.oGenerator.write("  public void createActionMap() throws Exception {");
		this.oGenerator.write("    addActionNew( 1, \"Nuevo Registro\" );");
		this.oGenerator.write("  }\n\n");

		this.oGenerator.write("");
		this.oGenerator.writeComment("Configuro las columnas que quiero mostrar en la grilla");
		this.oGenerator.write("  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {");

		this.oGenerator.write("    	zLista.AddIcono(\"\");");
		this.oColumns.firstRecord();
		while (this.oColumns.nextRecord()) {
			JBaseSystemDBField oCol = (JBaseSystemDBField) this.oColumns.getRecord();
			if (BizTableGenerator.hasToHide(oCol.getFieldName()) == false)
				this.oGenerator.write("    	zLista.AddColumnaLista(\"" + oCol.getFieldName() + "\");");
		}

		this.oGenerator.write("  }");
		this.oGenerator.write("");

		this.oGenerator.write("}");
	}

	/**
	 * Generates the jwin
	 */
	private void generateJWIN() throws Exception {
		sWClass = "Gui" + sSingular;
		this.oGenerator.createAndWritePackageAndImports(sWClass, sPkg);
		this.oGenerator.write("import pss.core.services.records.JRecord;");
		this.oGenerator.write("import pss.core.win.JWin;");
		this.oGenerator.write("import pss.core.winUI.forms.JBaseForm;\n");
		this.oGenerator.writeClass(sWClass, "JWin");
		this.oGenerator.writeConstructor(sWClass);
		this.oGenerator.write("  }\n\n");

		this.oGenerator.write("  public JRecord ObtenerDato()   throws Exception { return new " + sClass + "(); }");
		this.oGenerator.write("  public int GetNroIcono()   throws Exception { return " + sIcono + "; }");
		this.oGenerator.write("  public String GetTitle()   throws Exception { return \"" + sTitulo + "\"; }");
		this.oGenerator.write("  public Class<? extends JBaseForm> getFormBase() throws Exception { return " + sfClass + ".class; }");
		if (pItemClave.getValue().trim().length() > 0) {
			this.oGenerator.write("  public String  getKeyField() throws Exception { return \"" + pItemClave.getValue() + "\"; }");
		}
		this.oGenerator.write("  public String  getDescripField() { return \"" + sDesc + "\"; }");

		this.oGenerator.write("  public " + sClass + " GetcDato() throws Exception { return (" + sClass + ") this.getRecord(); }\n\n }");
	}

	/**
	 * Generates jbd the properties
	 */
	private void generateProperties(JRecords zColumns) throws Exception {
		String sDataType;
		String sNew;
		String sGetter;
		String sSetter;
		String sFunction;

		// Creo la hash table que aloca las propiedades fijas
		zColumns.firstRecord();
		while (zColumns.nextRecord()) {
			JBaseSystemDBField oColumn = (JBaseSystemDBField) zColumns.getRecord();
			iMaxFields++;
			String sAux = oColumn.getPssDataType();
			if (BizTableGenerator.isBoolean(oColumn.getFieldName().toUpperCase()) || oColumn.getFieldLength()==1 )
				sAux = " JBoolean ";
			sDataType = "	protected " + sAux + " p";
			sNew = " = new " + sAux + "()";
			String sColumn = oColumn.getFormatedColumnName();
			this.oGenerator.write(sDataType + this.getFormatedColumnName(sColumn) + sNew + ";");

			System.out.println(oColumn.getFieldName() + "," + oColumn.getFieldLength() + "," + oColumn.getFieldPrecision() + "," + oColumn.getFieldScale());
		}
		this.oGenerator.writeSeparator("Getter & Setters methods");

		zColumns.firstRecord();
		while (zColumns.nextRecord()) {
			JBaseSystemDBField oColumn = (JBaseSystemDBField) zColumns.getRecord();

			sDataType = oColumn.getJavaDataType();
			if (BizTableGenerator.isBoolean(oColumn.getFieldName().toUpperCase())||oColumn.getFieldLength()==1)
				sDataType = " boolean ";

			String sCol = oColumn.getFormatedColumnName();
			sSetter = "	public void set";
			sFunction = " zValue) throws Exception {" + "    p" + this.getFormatedColumnName(sCol) + ".setValue(zValue);" + "  }";
			this.oGenerator.write(sSetter + this.getFormatedColumnName(sCol) + "(" + sDataType + sFunction);

			sGetter = "	public " + sDataType + " get";
			sFunction = "	throws Exception { " + "    return p" + this.getFormatedColumnName(sCol) + ".getValue();" + "  }";
			this.oGenerator.write(sGetter + this.getFormatedColumnName(sCol) + "()" + sFunction);

			sFunction = "	public boolean isNull" + this.getFormatedColumnName(sCol) + "() throws Exception { return " + " p" + this.getFormatedColumnName(sCol) + ".isNull(); } ";
			this.oGenerator.write(sFunction);

			sFunction = "	public void setNullTo" + this.getFormatedColumnName(sCol) + "() throws Exception { " + " p" + this.getFormatedColumnName(sCol) + ".setNull(); } ";
			this.oGenerator.write(sFunction);

		}
	}

	/**
	 * Generats the formatted column name
	 */
	private String getFormatedColumnName(String zCol) {
		int iIdx = zCol.indexOf("_");
		if (iIdx == -1)
			return zCol;
		String sCol = zCol.substring(0, iIdx) + zCol.substring(zCol.indexOf("_") + 1, zCol.length());
		return sCol;
	}

	/**
	 * Genero el Constructor del JBD
	 */
	private void generatePropertyMethods(String sTabla, JRecords oCols) throws Exception {
		this.oGenerator.write("	public void createProperties() throws Exception {");
		oCols.firstRecord();
		while (oCols.nextRecord()) {
			JBaseSystemDBField oCol = (JBaseSystemDBField) oCols.getRecord();
			String sCol = oCol.getFieldName();
			@SuppressWarnings("unused")
			String sLen;

			sLen = oCol.getStringLength();
			String sCol2 = oCol.getFormatedColumnName();
			if (this.pUseQuotes.getValue())
				this.oGenerator.write("		addItem(  \"\\\"" + sCol + "\\\"\", p" + this.getFormatedColumnName(sCol2) + " );");
			else
				this.oGenerator.write("		addItem( \"" + sCol + "\", p" + this.getFormatedColumnName(sCol2) + " );");
		}
		this.oGenerator.write("  }");

		this.oGenerator.writeComment("Adds the fixed object properties");
		this.oGenerator.write("	public void createFixedProperties() throws Exception {");
		oCols.firstRecord();
		while (oCols.nextRecord()) {
			JBaseSystemDBField oCol = (JBaseSystemDBField) oCols.getRecord();
			String sCol = oCol.getFieldName();
			String sTipo;
			String sLen;
			sTipo = "FIELD";
			JIterator clavesIt = pClaves.getIterator();
			while (clavesIt.hasMoreElements()) {
				String sTok = (String) clavesIt.nextElement();
				if (oCol.getFieldName().toLowerCase().trim().equals(sTok.toLowerCase().trim())) {
					sTipo = "KEY";
					break;
				}
			}
			sLen = oCol.getStringLength();

			String sees = "true, true";
			if (autonum.toLowerCase().equals(sCol.toLowerCase())) {
				sees = "false,false";
				sLen = "18";
			} else if (oCol.IsNullable()) {
				sees = "true,false";
			}

			if (this.pUseQuotes.getValue())
				this.oGenerator.write("		addFixedItem( " + sTipo + ", \"\\\"" + sCol + "\\\"\", \"" + BizTableGenerator.getLabel(sCol) + "\", " + sees + ", " + sLen + " );");
			else
				this.oGenerator.write("		addFixedItem( " + sTipo + ", \"" + sCol + "\", \"" + BizTableGenerator.getLabel(sCol) + "\", " + sees + ", " + sLen + " );");
		}
		this.oGenerator.write("  }");
		this.oGenerator.writeComment("Returns the table name");
		if (this.pUseQuotes.getValue())
			this.oGenerator.write("	public String GetTable() { return \"\\\"" + sTabla + "\\\"\" ; }");
		else
			this.oGenerator.write("	public String GetTable() { return \"" + sTabla + "\"; }");

		this.oGenerator.writeSeparator("Functionality methods");
		this.oGenerator.writeComment("Default read() method");
		String sRead = "	public boolean read( ";
		boolean bFirst = true;

		oCols.firstRecord();
		while (oCols.nextRecord()) {
			JBaseSystemDBField oCol = (JBaseSystemDBField) oCols.getRecord();
			String sCol = oCol.getFormatedColumnName();
			@SuppressWarnings("unused")
			String sTipo;
			@SuppressWarnings("unused")
			String sLen;

			JIterator clavesIt = pClaves.getIterator();
			while (clavesIt.hasMoreElements()) {
				String sTok = (String) clavesIt.nextElement();
				if (oCol.getFieldName().toLowerCase().trim().equals(sTok.toLowerCase().trim())) {
					if (bFirst == false) {
						sRead += ", ";
					} else
						bFirst = false;
					sRead += oCol.getJavaDataType() + " z" + this.getFormatedColumnName(sCol);
					break;
				}
			}
		}
		this.oGenerator.write(sRead + " ) throws Exception { ");
		// this.oGenerator.write( " clearFilters(); " );

		oCols.firstRecord();
		while (oCols.nextRecord()) {
			JBaseSystemDBField oCol = (JBaseSystemDBField) oCols.getRecord();
			String sCol = oCol.getFieldName();
			@SuppressWarnings("unused")
			String sTipo;
			@SuppressWarnings("unused")
			String sLen;

			JIterator clavesIt = pClaves.getIterator();
			while (clavesIt.hasMoreElements()) {
				String sTok = (String) clavesIt.nextElement();
				if (oCol.getFieldName().toLowerCase().trim().equals(sTok.toLowerCase().trim())) {
					if (this.pUseQuotes.getValue())
						this.oGenerator.write("		addFilter( \"\\\"" + sCol + "\\\"\", " + " z" + this.getFormatedColumnName(oCol.getFormatedColumnName()) + " ); ");
					else
						this.oGenerator.write("		addFilter( \"" + sCol + "\", " + " z" + this.getFormatedColumnName(oCol.getFormatedColumnName()) + " ); ");
					break;
				}
			}
		}
		this.oGenerator.write("		return read(); ");
		this.oGenerator.write("  } ");
		this.oGenerator.write("}");
	}

	public void process() throws Exception {
		pPackage.setValue("pss.ssv.client");
		;
		pSSingular.setValue("Client");
		pSPlural.setValue("Clients");
		pTWin.setValue("Cliente");
		pTWins.setValue("Datos del Cliente");
		pIcono.setValue("92");
		pItemDesc.setValue("company");
		pItemClave.setValue("company");
		pClaves.setValue("id");
		pUseQuotes.setValue(false);

		autonum = "ID";
		mHide.addElement("COMPANY", "COMPANY");
		mBool.addElement("LOCAL_ACCOUNTING", "LOCAL_ACCOUNTING");
		mBool.addElement("INCOME_TAX", "INCOME_TAX");
		mBool.addElement("PRE_INCOME_TAX", "PRE_INCOME_TAX");
		mBool.addElement("INTERNAL_TAX", "INTERNAL_TAX");
		mBool.addElement("GAS_TAX", "GAS_TAX");
		
		
		//mHide.addElement("COUNTRY", "COUNTRY");
		this.generateUpperClass=false;
		
		oWTabla = new GuiTable();
		JBaseSystemDBTable tbl = JBaseSystemDBTable.VirtualCreate();
		tablefilter = "ssv_client".toUpperCase();
		tbl.setTableName(tablefilter);
		tbl.read();
		oWTabla.setRecord(tbl);

		this.processInsert();
	}

	public static void main(String[] args) {

		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("generate", JAplicacion.AppTipoThread(), true);
			JBDatos.GetBases().beginTransaction();

			// PssLogger.logInfo("checking inactivity ..."); //$NON-NLS-1$
			BizTableGenerator tbl = new BizTableGenerator();

			tbl.process();

			JBDatos.GetBases().commit();
			JAplicacion.GetApp().closeApp();
			PssLogger.logInfo("close app ..."); //$NON-NLS-1$
			try {
				JAplicacion.closeSession();
			} catch (Exception eee) {
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	private static JMap<String, String> mLables = JCollectionFactory.createMap(100);
	private static JMap<String, String> mHide = JCollectionFactory.createMap(100);
	private static JMap<String, String> mBool = JCollectionFactory.createMap(100);

	public static boolean hasToHide(String field) {
		String hide = mHide.getElement(field);
		if (hide == null)
			return false;
		return true;
	}

	public static boolean isBoolean(String field) {
		String hide = mBool.getElement(field);
		if (hide == null)
			return false;
		return true;
	}

	public static String getLabel(String field) throws Exception {
		if (mLables.isEmpty()) {
			mLables.addElement("ID", "Id");
			mLables.addElement("COMPANY", "Empresa");
			mLables.addElement("STORE", "Sucursal");
			mLables.addElement("PUMP", "Surtidor");
			mLables.addElement("STATUS", "Estado");
			mLables.addElement("PAYMENT_ID", "Id Pago");
			mLables.addElement("PAYMENT_DATE", "Fecha Pago");
			mLables.addElement("PREAUT_DATE", "Fecha Preaut.");
			mLables.addElement("DESCRIPTION", "Descripcion");
			mLables.addElement("TASK", "Tarea");
			mLables.addElement("USERID", "Usuario");
			mLables.addElement("TASK_TYPE", "Tipo Tarea");
			mLables.addElement("TASK_ID", "Tarea");
			mLables.addElement("TASK_TYPE_ID", "Tipo Tarea");
			mLables.addElement("LOG_DATE", "Fecha Carga");
			mLables.addElement("TASK_TIME", "Tiempo Tarea");
			mLables.addElement("COMMENTS", "Comentarios");
			mLables.addElement("GROUP", "Grupo");
			mLables.addElement("CLIENT_ID", "Cliente");
			mLables.addElement("CLIENT_NAME", "Denominacion");
			mLables.addElement("CLIENT_LASTNAME", "Apellido");
			mLables.addElement("CLIENT_TYPE", "Tipo Cliente");
			mLables.addElement("TAX_ID", "Id Fiscal");
			mLables.addElement("ADDRESS", "Direccion");
			mLables.addElement("CITY", "Ciudad");
			mLables.addElement("STATE", "Provincia");
			mLables.addElement("COUNTRY", "Pais");
			mLables.addElement("CLOSE_MONTH", "Mes Cierre");
			mLables.addElement("CLIENT_GROUP", "Grupo Cliente");
			mLables.addElement("WORK_PERIOD", "Periodo Trabajo");
			mLables.addElement("PAYMENT_TAX_ID", "Id Fiscal Pago");
			mLables.addElement("PAYMENT_PERIOD", "Periodo Pago");

		}
		String label = mLables.getElement(field);
		if (label == null)
			return field;
		return label;
	}

}
