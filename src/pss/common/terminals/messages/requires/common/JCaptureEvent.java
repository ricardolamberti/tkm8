package pss.common.terminals.messages.requires.common;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.core.JTerminalGroup;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrClientKey;
import pss.common.terminals.messages.answer.AwrMagneticCard;
import pss.common.terminals.messages.answer.AwrManualCancel;
import pss.common.terminals.messages.answer.AwrOperKey;
import pss.common.terminals.messages.answer.AwrTag;
import pss.common.terminals.messages.answer.AwrTimeout;
import pss.common.terminals.messages.requires.JEvents;
import pss.common.terminals.messages.requires.JRequire;
import pss.common.terminals.messages.requires.display.JClientDisplay;
import pss.common.terminals.messages.requires.display.JOperDisplay;
import pss.core.services.fields.JBaseObject;
import pss.core.services.fields.JString;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;

public class JCaptureEvent extends JRequire {

	private JString pMessageOperator=new JString();
	private JString pMessageClient=new JString();
	private JEvents pEventFilters=new JEvents();

	// private JTerminalGroup terminalGroup;

	private Answer lastEvent;

	@Override
	public void loadFieldMap() throws Exception {
		super.loadFieldMap();
		this.addValue("message_oper", pMessageOperator);
		this.addValue("message_client", pMessageClient);
		this.addValue("event_filters", pEventFilters);
	}

	public void setEventFilters(JMap<String, JBaseObject> value) throws Exception {
		this.pEventFilters.setValue(value);
	}

	public void addEvent(Answer event) throws Exception {
		// if (this.eventFilters==null) this.eventFilters = new JEvents();
		this.pEventFilters.addValue(event);
	}

	public Answer getLastEvent() {
		return this.lastEvent;
	}

	public boolean hasAnyEvent() {
		return this.lastEvent!=null;
	}

	// public JTerminalGroup getTerminalGroup() {
	// return this.terminalGroup;
	// }

	public void setMessageOperator(String value) {
		this.pMessageOperator.setValue(value);
	}

	public void setMessageClient(String value) {
		this.pMessageClient.setValue(value);
	}

	// public void setEventFilter(int value) {
	// this.event_filter = value;
	// }

	// ------------------------------------------------------------------------------
	// Class Constructors
	// ------------------------------------------------------------------------------
	public JCaptureEvent() {
	}

	public JCaptureEvent(JTerminalGroup terminalGroup) throws Exception {
		this.setTerminalGroup(terminalGroup);
	}

	@Override
	public Answer executeLocal() throws Exception {
		return this.capture();
	}

	@Override
	public Answer execute(JTerminal terminal) throws Exception {
		return null;
	}

	private void displays() throws Exception {
		if (this.pMessageOperator.isNotNull()) {
			JOperDisplay require1=new JOperDisplay();
			require1.setInformation(this.pMessageOperator.getValue());
			this.getTerminalGroup().process(require1);
		}
		if (this.pMessageClient.isNotNull()) {
			JClientDisplay require2=new JClientDisplay();
			require2.setInformation(this.pMessageClient.getValue());
			this.getTerminalGroup().process(require2);
		}
	}

	public Answer capture() throws Exception {
		AwrTimeout oTimeout=(AwrTimeout) this.pEventFilters.has(AwrTimeout.class);
		long timeout=30000L;
		if (oTimeout!=null) timeout=oTimeout.getTimeout();

		this.getTerminalGroup().startPollingAll();

		this.displays();
		this.resetLastEvent();

		JList<Thread> threads=JCollectionFactory.createList();
		JIterator<JTerminal> iter=this.getTerminalGroup().getTerminalIterator();
		while (iter.hasMoreElements()) {
			JTerminal terminal=iter.nextElement();
			if (!this.hasDriverForEvents(terminal)) continue;
			threads.addElement(this.waitTerminalEvent(terminal));
		}

		try {
			synchronized (this) {
				wait(timeout);
			}
		} catch (InterruptedException e) {
			PssLogger.logDebug("Se interrumpen todos los threads");
			this.stopAllThreads(threads);
			throw e;
		}

		if (!this.hasAnyEvent()) {
			this.lastEvent=new AwrTimeout(timeout);
		}

		this.stopAllThreads(threads);

		return this.getLastEvent();
	}

	public JCaptureEvent getThis() {
		return this;
	}

	// ----------------------------------------------------------------------------------------------------------------------
	private void stopAllThreads(JList<Thread> threads) throws Exception {
		Thread.sleep(100);
		JIterator<Thread> iter=threads.getIterator();
		while (iter.hasMoreElements()) {
			Thread thread=iter.nextElement();
			thread.interrupt();
		}
		this.getTerminalGroup().stopPollingAll();
	}

	// private boolean allThreadsStopped( Thread threads[] ) {
	// for( int i=0; i<threads.length; i++ ) {
	// if( threads[i].isAlive() ) {
	// threads[i].interrupt();
	// return false;
	// }
	// }
	// return true;
	// }

	public void resetLastEvent() {
		this.lastEvent=null;
	}

	public synchronized void setLastEvent(Answer value) {
		this.lastEvent=value;
		this.notify();
	}

	private Thread waitTerminalEvent(final JTerminal terminal) throws Exception {
		Thread thread=new Thread() {

			@Override
			public void run() {
				terminal.waitTerminalEvent(getThis());
			}
		};
		thread.setName("Capture-"+terminal.getType());
		thread.start();
		return thread;
	}

	// ----------------------------------------------------------------------------------------------------------------------
	public void manualCancel() throws Exception {
		if (!this.hasToCheck(AwrManualCancel.class)) return;
		this.setLastEvent(new AwrManualCancel());
	}

	public boolean hasToCheck(Class eventClass) throws Exception {
		return this.pEventFilters.has(eventClass)!=null;
	}

	public boolean hasToCheckMCR() throws Exception {
		return this.hasToCheck(AwrMagneticCard.class);
	}

	public boolean hasToCheckTAG() throws Exception {
		return this.hasToCheck(AwrTag.class);
	}

	public boolean hasToCheckOperatorKey() throws Exception {
		return this.hasToCheck(AwrOperKey.class);
	}

	public boolean hasToCheckClientKey() throws Exception {
		return this.hasToCheck(AwrClientKey.class);
	}

	public boolean hasToCheckManualCancel() throws Exception {
		return this.hasToCheck(AwrManualCancel.class);
	}

	private boolean hasDriverForEvents(JTerminal terminal) throws Exception {
		if (this.hasToCheckClientKey()&&terminal.hasAvailableDriverFor(JTerminal.D_CLIENT_KEYB)) return true;
		if (this.hasToCheckOperatorKey()&&terminal.hasAvailableDriverFor(JTerminal.D_OPERATOR_KEYB)) return true;
		if (this.hasToCheckTAG()&&terminal.hasAvailableDriverFor(JTerminal.D_TAG)) return true;
		if (this.hasToCheckMCR()&&terminal.hasAvailableDriverFor(JTerminal.D_MAGNETIC_READ)) return true;
		return false;
	}

}
