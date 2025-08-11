package pss.common.layout;

import pss.common.security.BizUsuario;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.printGen.JPrintGen;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JActFileGenerate;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;

public class JWinLayout {

	private JBaseWin baseWin;
	private JBaseLayout baseLayout;
//	private JRecord lastRecord;
	
	public JWinLayout(JBaseWin value) throws Exception {
		this.baseWin=value;
		this.baseLayout= new JBaseLayout(this.baseWin.GetBaseDato());
	}
	
	public JActFileGenerate getPrintAction() throws Exception {
		return new JActFileGenerate(this.baseWin) {
			public String generate() throws Exception {
				return generateLayout();
			}
		};
	}
	
	public String generateLayout() throws Exception {
		this.baseLayout.setObjPrintGen(this.createPrintGen());
		this.baseLayout.generateLayout();
		return this.getUniqueFileName();
	}

	public String getUniqueFileName() throws Exception {
		return this.baseLayout.getUniqueFileName();
	}

	private JPrintGen createPrintGen() throws Exception {
		BizLayout l = new BizLayout();
		l.dontThrowException(true);
		if(l.ReadByIdent(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getCountry(), JFieldSet.WINS_SET, this.baseWin.GetBaseDato().getLayoutIdent(), this.baseWin.GetBaseDato().getLayoutSubIdent())) 
				return l.createPrintGen(this.getUniqueFileName(), null);

		l.clearFilters();
		if(!l.ReadByType(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getCountry(), JFieldSet.WINS_SET)) 
			l= this.createDefaultLayout();
		l.getObjCampos();
		this.createDefaultCampos(l);
		return l.createPrintGen(this.getUniqueFileName(), null);
	}
	
	private void createDefaultCampos(BizLayout l) throws Exception {
		if (this.baseWin instanceof JWins) 
			this.createCamposWins(l);
		if (this.baseWin instanceof JWin) 
			this.createCamposWin(l);
	}
	
	private void createCamposWin(BizLayout l) throws Exception {
		JWin win = (JWin)this.baseWin;
		BizLayoutCampo c = new BizLayoutCampo();
		c.pSector.setValue(JFieldSetWins.REPORT_HEADER);
		c.pTipoCampo.setValue(BizLayoutCampo.CAMPO_CONSTANTE);
		c.pAlineacion.setValue("Left");
//			c.pCampo.setValue(cl.GetCampo());
		c.pConstante.setValue("Sin Configuración");
		c.pX.setValue(1);
		c.pY.setValue(1);
		c.pLongMax.setValue(19);
		l.addCampo(c);
	}
	
	private void createCamposWins(BizLayout l) throws Exception {
		JWins wins = (JWins)this.baseWin;
		JWinList winList = new JWinList(wins); // WinList fantasma para poder obtener las columnas de details
		int proxX = 2	;
		wins.ConfigurarColumnasLista(winList);
		JIterator<JColumnaLista> iter = winList.GetColumnasLista().getIterator();
		while(iter.hasMoreElements()) {
			JColumnaLista cl = iter.nextElement();
			if (cl.IfIcono()) continue;
			BizLayoutCampo c = new BizLayoutCampo();
			c.pSector.setValue(JFieldSetWins.REPORT_HEADER);
			c.pTipoCampo.setValue(BizLayoutCampo.CAMPO_CONSTANTE);
			c.pAlineacion.setValue("Left");
//			c.pCampo.setValue(cl.GetCampo());
			c.pConstante.setValue(cl.GetColumnaTitulo());
			c.pX.setValue(proxX);
			c.pY.setValue(1);
			c.pLongMax.setValue(19);
			l.addCampo(c);

			c = new BizLayoutCampo();
			c.pSector.setValue(JFieldSetWins.REPORT_HEADER);
			c.pTipoCampo.setValue(BizLayoutCampo.CAMPO_CONSTANTE);
			c.pAlineacion.setValue("Left");
//			c.pCampo.setValue(cl.GetCampo());
			c.pConstante.setValue("--------------------");
			c.pX.setValue(proxX);
			c.pY.setValue(2);
			c.pLongMax.setValue(19);
			l.addCampo(c);

			c = new BizLayoutCampo();
			c.pSector.setValue(JFieldSetWins.SECTOR_MAIN);
			c.pCampo.setValue(cl.GetCampo());
			c.pAlineacion.setValue("Left");
			c.pX.setValue(proxX);
			c.pY.setValue(1);
			c.pLongMax.setValue(19);
			l.addCampo(c);
			proxX=c.pX.getValue()+20;
		}
		l.getObjCampos().Ordenar("y;x");
	}
	
	public BizLayout createDefaultLayout() throws Exception {
		BizLayout lay=new BizLayout();
		lay.pAnchoMaximo.setValue(100);
		lay.pMaxItems.setValue(80);
		lay.pPrintEmptyLines.setValue(true);
		lay.pFontType.setValue(JFonts.COURIER);
		lay.pFontSize.setValue(12);
		lay.setSize(JPrinterAdapter.LETTER);
		return lay;
	}
	
//	private BizTerminal createPDFTerminal(String name) throws Exception {
//		JTerminal terminalPointer=new TPDFPrinter();
//		terminalPointer.setConnectionString(name);
//		terminalPointer.setCompany(BizUsuario.getUsr().getCompany());
//		BizTerminal terminal=new BizTerminal();
//		terminal.setObjTerminalPointer(terminalPointer);
//		return terminal;
//	}

//
//	@Override
//	public Object getField(JFieldReq req) throws Exception {
//		JRecord r = (JRecord)req.getSource1();
//		if (r instanceof JRecord) {
//			JObject field = r.getPropDeep(req.id);
//			if (field==null) return "";
//			return field.asObject();
//		}
//		return null;
//	}
	
}
