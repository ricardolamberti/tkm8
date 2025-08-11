package pss.common.terminals.drivers.WinsGrid;

import pss.common.layout.BizLayout;
import pss.common.layout.BizLayout.LayoutField;
import pss.common.layout.JFieldSetTicket;
import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JWins;

public class TWinsCustomAdapter extends JPrinterAdapter {
	
	private JWinsCustom wins;
	private BizLayout layout;
	
	public JWins getWins() throws Exception {
		return this.wins;
	}

	public void setLayout(BizLayout l) throws Exception {
		this.layout=l;
	}

	public TWinsCustomAdapter(JTerminal terminal) throws Exception {
  	super(terminal);
  }
	
	@Override
	public Answer openDocument() throws Exception {
		return new AwrOk();
	}
  
  @Override
  public void generate(String section, Object source) throws Exception {
  	if (section.equals(JFieldSetTicket.REPORT_HEADER)) {
  		this.wins = new JWinsCustom();
  		this.wins.SetTitle(this.layout.getLayoutDescription());
  		this.wins.SetEstatico(true);
  		this.layout.setFieldInterface(this.getFieldInterface());
  	} else if (section.equals(JFieldSetTicket.BODY_ITEM_HEADER)) {
  		int i=1;
  		JWinCustom w = (JWinCustom)this.wins.getWinRef();
  		w.GetcDato().setTipo(section);
  		JList<LayoutField> list = layout.generateObjects(section, source, true);
  		JIterator<LayoutField> iter = list.getIterator();
  		while (iter.hasMoreElements()) {
  			LayoutField hf = iter.nextElement();
    		w.getRecord().addFixedItem(JRecord.FIELD, "c"+i, hf.getValorString(), true, false, 100, 0);
    		i++;
  		}
  	} else if (section.equals(JFieldSetTicket.BODY_ITEM)) {
  		int i=1;
  		JWinCustom w = (JWinCustom)this.wins.createJoinWin();
  		w.GetcDato().setTipo(section);
  		JIterator<LayoutField> iter = layout.generateObjects(section, source, true).getIterator();
  		while (iter.hasMoreElements()) {
  			LayoutField f = iter.nextElement();
    		w.getRecord().addItem("c"+i, f.getValor());
    		this.pushProp("c"+i, f.getValor());
    		i++;
  		}
  		this.wins.addRecord(w);
  	} else if (section.equals(JFieldSetTicket.REPORT_FOOTER)) {
  		JList<LayoutField> list = JCollectionFactory.createList();
  		JIterator<LayoutField> iter = layout.generateObjects(section, source, true).getIterator();
  		while (iter.hasMoreElements()) {
  			LayoutField f = iter.nextElement();
    		list.addElement(f);
  		}
  		this.wins.setTotals(list);
  	}

  }
  

	public void pushProp(String campo, JObject obj) throws Exception {
		JObject prop = this.wins.getWinRef().getRecord().getProp(campo, false);
		if (prop!=null) return;
		this.wins.getWinRef().getRecord().addItem(campo, obj);
	}

	public String getLinesRecordedAsXML() throws Exception {
		return "<xml></xml>";
	}

}


