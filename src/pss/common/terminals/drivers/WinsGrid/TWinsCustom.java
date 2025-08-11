package pss.common.terminals.drivers.WinsGrid;


import pss.common.terminals.core.JPrinterAdapter;
import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.common.terminals.messages.answer.AwrPrintDoc;
import pss.common.terminals.messages.requires.JRequire;
//import com.lowagie.text.BaseColor;

public class TWinsCustom extends JTerminal implements JPrinterInterface {


	public TWinsCustom() throws Exception {
	}
	

	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}

	@Override
	public Answer open() throws Exception {
		return new AwrOk();
	}

	@Override
	public Answer close() throws Exception {
		return new AwrOk();
	}

	public Answer closeDay() throws Exception {
		return new AwrOk();
	}

	public Answer closeShift() throws Exception {
		return new AwrOk();
	}

	public Answer closeDoc() throws Exception {
		return new AwrOk();
	}

	public Answer cancelDoc() throws Exception {
		return new AwrOk();
	}
	
	public JPrinterAdapter createPrintAdapter() throws Exception {
  	return new TWinsCustomAdapter(this);
  }

	@Override
	public Answer printLine(String line) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer openDoc() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Answer flush() throws Exception {
		return null;
	}

	@Override
	public Answer skeepLines(int lines) throws Exception {
		return null;
	}

	@Override
	public Answer process(JRequire require) throws Exception {
		return new AwrPrintDoc();
	}

}
