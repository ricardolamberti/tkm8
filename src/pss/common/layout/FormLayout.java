package pss.common.layout;

import java.io.File;

import pss.common.terminals.core.JPrinterAdapter;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormLayout extends JBaseForm {

	public FormLayout() throws Exception {
	}


	public GuiLayout GetWin() {
		return (GuiLayout) getBaseWin();
	}

	// -------------------.------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();
		col.AddItemEdit("Pais", CHAR, REQ, "pais").hide();
		col.AddItemEdit("company", CHAR, REQ, "company").hide();
		col.AddItemEdit("Layout", CHAR, REQ, "Layout", 2);
		col.AddItemEdit("Descrip", CHAR, REQ, "descripcion", 4);
		col.AddItemEdit("jIdent", CHAR, OPT, "ident", 3);
		col.AddItemEdit("SubIdent", CHAR, OPT, "sub_ident", 3);
		col.AddItemEdit("Ancho Maximo", CHAR, REQ, "ancho_maximo", 2);
		col.AddItemEdit("Max Items", UINT, REQ, "max_items", 2);
		col.AddItemEdit("Copias", UINT, OPT, "copias", 1);
		col.AddItemEdit("CopiaXPag", UINT, OPT, "copias_xpag", 2).SetValorDefault(0);
		col.AddItemCombo("FieldSet", CHAR, REQ, "campos_set", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.CreateVirtualWins(JRecords.createVirtualFormMap(JFieldSet.getFieldSets()));
			}
		});
		col.AddItemCheck("Preconf", REQ, "preconfigurado", 2);
		col.AddItemCheck("printEmptyLines", REQ, "print_empty_lines", 2).SetValorDefault(true);
		col.AddItemCheck("Rellenar", REQ, "rellenar", 2);
		col.AddItemCheck("To Wins", REQ, "to_wins", 2);
		col.AddItemEdit("interl", UFLOAT, OPT, "line_spacing", 2);
		col.AddItemCombo("jFontType", CHAR, OPT, "font_type", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getFontTypes();
			}
		}, 3);
		col.AddItemEdit("FontSize", UINT, OPT, "font_size", 2);
		col.AddItemCombo("Background", CHAR, OPT, "background", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.CreateVirtualWins(getBackgrounds());
			}
		}, 3);
		col.AddItemEdit("Image Size", CHAR, OPT, "image_size", 1);
		col.AddItemCombo("Size", CHAR, OPT, "page_size", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return JWins.createVirtualWinsFromMap(JPrinterAdapter.getSizeTypes());
			}
		}, 2);
		col.AddItemEdit("CustomSize", CHAR, OPT, "custom_size", 1);
		col.AddItemEdit("Section Orden", CHAR, OPT, "section_orden", 1);
		col.AddItemTabPanel().AddItemTab(10);
	}

	public JWins getFontTypes() throws Exception {
		return JWins.createVirtualWinsFromMap(JFonts.getFonts());
	}
	
	public JRecords<BizVirtual> getBackgrounds() throws Exception {
		JRecords<BizVirtual> records=JRecords.createVirtualBDs();
		records.setStatic(true);
		String path=this.getClass().getResource("").getFile().substring(1)+"backgrounds/"+this.findControl("company").getValue();
		File file=new File(path);
		File aFile[]=file.listFiles();
		if (aFile==null) return records;
		for(int i=0; i<aFile.length; i++) {
			records.addItem(JRecord.virtualBD(aFile[i].getName(), aFile[i].getName(), 1));
		}
		return records;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
