package pss.common.terminals.drivers.WinsGrid;

import pss.common.layout.BizLayout;
import pss.common.layout.BizLayout.LayoutField;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.totalizer.JTotalizer.Properties;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;

public class JWinsCustom extends JWins {
	
	@Override
	public Class<? extends JWin> GetClassWin() throws Exception {
		return JWinCustom.class;
	}
	
	@Override
	public int GetNroIcono() throws Exception {
		return 5;
	}

	public JWinsCustom() throws Exception {
	}
	
	private BizLayout layout;
	public void setLayout(BizLayout l) {
		this.layout=l;
	}
	
	private JList<LayoutField> totals;
	public void setTotals(JList<LayoutField> l) {
		this.totals=l;
	}

	@Override
	public void createTotalizer(JWinList list) throws Exception {
		int i=1;
		JIterator<LayoutField> iter = totals.getIterator();
		while (iter.hasMoreElements()) {
			LayoutField f = iter.nextElement();
  		Properties p = list.addTotalizer("c"+i, f.getValor(), JColumnaLista.ALIGNMENT_RIGHT);
  		i++;
		}
	}
	
	@Override
	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_MAX;
	}

}
