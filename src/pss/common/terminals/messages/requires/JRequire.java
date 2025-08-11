package pss.common.terminals.messages.requires;

import pss.common.terminals.core.JTerminal;
import pss.common.terminals.core.JTerminalGroup;
import pss.common.terminals.core.JTerminalPool;
import pss.common.terminals.messages.answer.Answer;
import pss.common.terminals.messages.answer.AwrBoolean;
import pss.common.terminals.messages.answer.AwrData;
import pss.common.terminals.messages.answer.AwrError;
import pss.common.terminals.messages.answer.AwrTimeout;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjRecord;
import pss.core.services.fields.JObjRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public abstract class JRequire extends JObjRecord {

  private JObjRecords    terminalList = new JObjRecords();

  private JTerminalGroup terminalGroup;

  private Answer	 answer       = null;

  public JRequire() {
  }

  @Override
  public void loadFieldMap() throws Exception {
    this.addValue("terminal_list", terminalList);
  }

  public void setTerminalGroup(JTerminalGroup value) throws Exception {
    this.terminalGroup = value;
  }

  public void generateTerminalList() throws Exception {
    JIterator iter = this.getTerminalIterator();
    while (iter.hasMoreElements()) {
      JTerminal terminal = (JTerminal) iter.nextElement();
      this.terminalList.addValue(new JLong(terminal.getTerminalId()));
    }
  }

  public JTerminalGroup getTerminalGroup() {
    return this.terminalGroup;
  }

  public void addTerminal(JTerminal term) throws Exception {
    if (this.terminalGroup == null)
      this.terminalGroup = new JTerminalGroup();
    this.terminalGroup.addTerminal(term);
  }

  public JMap getTerminalList() throws Exception {
    return this.terminalList.getMap();
  }

  // public void setTerminalId(long term) {
  // this.pTerminalId.setValue(term);
  // }
  // public long getTerminalId() throws Exception {
  // return this.pTerminalId.getValue();
  // }

  // public void addTerminal(JTerminal term) throws Exception {
  // if (this.terminalList==null) this.terminalList = new JObjContainer();
  // this.terminalList.addValue(new JLong(term.getTerminalId()));
  // }
  //	
  // public JTerminal getTerminal(long id) throws Exception {
  // return (JTerminal) this.terminalList.getField(String.valueOf(id));
  // }

  public Answer executeRequire() throws Exception {
    if (terminalGroup == null)
      JExcepcion.SendError("Debe especificar una terminal");

    Answer awr = null;
    String macAddress = this.getTerminalMacAddress();
    if (this.isRemote(macAddress)) {
      awr = executeRemote(macAddress);
    } else {
      awr = executeLocal();
    }
    this.verifyAnswer(awr);
    return (this.answer = awr);
  }

  private Answer executeRemote(String macAddress) throws Exception {
    ISender sender = (ISender) Class.forName("pss.common.terminals.messages.requires.JSender").newInstance();
    this.generateTerminalList();
    return sender.send(macAddress, this.serializeDocument());
  }

  private boolean isRemote(String macAddress) throws Exception {
    if (macAddress == null)
      return false;
    if (macAddress.equals(""))
      return false;
    if (macAddress.equalsIgnoreCase("local"))
      return false;
    if (macAddress.equalsIgnoreCase("localhost"))
      return false;
    return true;//!macAddress.equalsIgnoreCase(JTools.getMacAddress());
  }

  public Answer getAnswer() throws Exception {
    return this.answer;
  }

  public boolean answerAsBoolean() throws Exception {
    // this.verifyAnswer();
    return (((AwrBoolean) this.getAnswer())).getValue();
  }

  public String answerAsString() throws Exception {
    // this.verifyAnswer();
    return (((AwrData) this.getAnswer())).getData();
  }

  protected void verifyAnswer(Answer awr) throws Exception {
    if (awr == null)
      JExcepcion.SendError("Requerimiento sin respuesta");

    if (awr instanceof AwrError) {
      AwrError error = (AwrError) awr;
	    if (error.hasException())
	      JExcepcion.SendError(error.getErrorMessage());
	    if (error.hasClassException())
	    	error.throwError();
    }

    if (awr instanceof AwrTimeout) {
      JExcepcion.SendError("Tiempo de espera excedido");
    }

  }

  public JIterator getTerminalIterator() throws Exception {
    return this.terminalGroup.getTerminalIterator();
  }

  private String getTerminalMacAddress() throws Exception {
    // se verifican si todas las terminales corresponden al mismo
    // terminal pool
    String macAddress = null;
    JIterator iter = this.terminalGroup.getTerminalIterator();
    while (iter.hasMoreElements()) {
      JTerminal terminal = (JTerminal) iter.nextElement();
      if (macAddress == null)
	macAddress = terminal.getMacAddress();
      if (macAddress != null && !macAddress.equalsIgnoreCase(terminal.getMacAddress()))
	JExcepcion.SendError("Difienren los pools de terminales");
    }
    return macAddress;
  }

  public Answer executeLocal() throws Exception {
    Answer awr = null;
    JIterator iter = this.getTerminalIterator();
    while (iter.hasMoreElements()) {
      JTerminal terminal = (JTerminal) iter.nextElement();
      awr = this.execute(terminal);
    }
    return awr;
  }

  public abstract Answer execute(JTerminal terminal) throws Exception;

  public void captureTerminals(JTerminalPool pool) throws Exception {
    JIterator iter = this.getTerminalList().getValueIterator();
    while (iter.hasMoreElements()) {
      long termId = ((JLong) iter.nextElement()).getValue();
      JTerminal terminal = pool.getTerminalById(termId);
      if (terminal == null)
      	JExcepcion.SendError("La terminal no esta registrada: ^" + termId);
      this.addTerminal(terminal);
    }
  }

}
