package pss.common.documentos.docEmail;

import pss.common.documentos.docElectronico.GuiDocAdjunto;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDocEmailNew extends JBaseForm {

	  public FormDocEmailNew() throws Exception {
	  }

	  public GuiDocEmail getWin() { return (GuiDocEmail) getBaseWin(); }

	  public BizDocEmail getMail() throws Exception { return this.getWin().GetcDato(); }

	  public void InicializarPanel( JWin zWin ) throws Exception {
	  	JFormPanelResponsive col = this.AddItemColumn(10);
	  	JFormPanelResponsive panel = col.AddItemColumn();
	    panel.setLabelLeft(1);
	  	panel.AddItemEdit( "autor", CHAR, OPT, "doc_usuario" ).hide();
	  	panel.AddItemEdit( "company", CHAR, OPT, "doc_company").hide();
	    panel.AddItemEdit( "Para", CHAR, REQ, "mail_to", 12);
	  	panel.AddItemCombo("Plantilla",CHAR,OPT,"doc_plantilla", new JControlCombo() {
	  		 @Override
	  		public JWins getRecords(boolean one) throws Exception {
	  			return getPlantillas(one);
	  		}
	  	 }).setRefreshForm(true);
	  	 
	    
	    panel.AddItemEdit( "Asunto", CHAR, REQ, "doc_titulo", 12);
	    panel.AddItemHtml("Body", CHAR, OPT, "body");

	    JFormPanelResponsive panel2 = col.AddItemColumn(1);// col.AddItemFieldset("Adjuntos");
	    panel2 = col.AddItemColumn(11);// col.AddItemFieldset("Adjuntos");
//	    JFormTabPanelResponsive tab = panel2.AddItemTabPanel();
//	    tab.setSizeColumns(5);
//	    tab.AddItemTab(310);

	    JFormPanelResponsive line; JFormButtonResponsive b; int i = 0;
	    JIterator<GuiDocAdjunto> iter = this.getWin().getObjAdjuntos().getStaticIterator();
	    while (iter.hasMoreElements()) {
	    	GuiDocAdjunto a = iter.nextElement();
	    	line=panel2.AddItemColumn().AddInLinePanel();
	    	line.addButton(a.GetNroIcono(), null, false, 0).size(16);
	    	line.addButton(a.GetcDato().getTitulo(), 701, 0).setRow("SEC_"+i).size(14);
  	  	line.addButton(7, 702, true, 0).setRow("SEC_"+i);
  	  	i++;
	    }
	    line = panel2.AddInLinePanel();
			line.addButton("Agregar Adjunto", 704, 0);
			b=line.addButton(11, 704, true, 0);
			if(b!=null) b.setImageFontSize(14);
	    
	  }
	  
	  public JWins getPlantillas(boolean one) throws Exception {
	  	GuiPlantillas pls = new GuiPlantillas();
	  	pls.getRecords().addFilter("company", getWin().GetcDato().getCompany());
	  	pls.getRecords().addFilter("origen", "M");
	  	if (one) {
	    	pls.getRecords().addFilter("id_plantilla", getWin().GetcDato().getIdPlantilla());
	  		
	  	}
	  	return pls;
	  }

	  @Override
	  public void checkControls(String sControlId) throws Exception {
	  	if (sControlId.equals("doc_plantilla")) {
	  		getWin().GetcDato().setBody(getWin().GetcDato().getObjPlantilla().generateDocSimple(getWin().GetcDato()));
	  		
	  	}
	  	super.checkControls(sControlId);
	  }
}