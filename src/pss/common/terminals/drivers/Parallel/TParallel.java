package pss.common.terminals.drivers.Parallel;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.interfaces.JPrinterInterface;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrOk;
import pss.core.connectivity.client.parallel.JParallelClient;
import pss.core.tools.JTools;

public class TParallel extends JTerminal implements JPrinterInterface {
	private JParallelClient parallel = null;

	public TParallel() {

	}

	@Override
	public void loadDrivers() throws Exception {
		this.addDriver(JTerminal.D_PRINTER);
	}

	@Override
	public Answer open() throws Exception {
		this.close();
		JParallelClient parallelConn = this.createParallelConnection();
		parallelConn.connect();
		this.parallel = parallelConn;
		return new AwrOk();
	}

	@Override
	public Answer close() throws Exception {
		if (this.parallel != null) {
			this.parallel.disconnect();
			this.parallel = null;
		}
		return new AwrOk();
	}

	public Answer printLine(String line) throws Exception {
		if (parallel == null) return new AwrOk();
		if (line.equals("^n")) {
			this.newLine();
			return new AwrOk();
		}
		if (line.equals("^p")) {
			return new AwrOk();
		}
		// Thread.sleep( 50 );
		parallel.write(JTools.getBytesAscii(line));
		Thread.sleep(80);
		return new AwrOk();
	}

	public Answer openDoc() throws Exception {
		return this.open();
	}

	public Answer cancelDoc() throws Exception {
		this.newLine(3);
		Thread.sleep(300);
		this.printLine("** COMPROBANTE ANULADO POR EL USUARIO **");
		this.newLine(5);
		// parallel.write(CUT_TICKET);
		return new AwrOk();

	}

	public Answer closeDoc() throws Exception {
		parallel.disconnect();
		return new AwrOk();
	}

	public Answer flush() throws Exception {
		return new AwrOk();
	}

	public Answer skeepLines(int lines) throws Exception {
		for (int i = 0; i < lines; i++) {
			this.newLine();
		}
		return new AwrOk();
	}

	public void newLine() throws Exception {
		this.printLine("\n");
	}

	private void newLine(int howManyLines) throws Exception {
		for (int i = 0; i < howManyLines; i++) {
			this.printLine("");
		}
	}

	public Answer closeShift() throws Exception {
		return new AwrOk();
	}

	public Answer closeDay() throws Exception {
		return new AwrOk();
	}

}