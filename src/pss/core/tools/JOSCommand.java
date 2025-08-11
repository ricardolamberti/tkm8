package pss.core.tools;

/**
 * A command to be executed by the Operative System.
 *
 * @author Leonardo Pronzolino
 * @email PSS@mcsla.com.ar
 */

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

public class JOSCommand {
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   STATIC VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static final int STATUS_READY = 0;
  public static final int STATUS_EXECUTING = 1;
  public static final String[] STATUS_DESCRIPTIONS = {"READY", "EXECUTING"};
  public static final int RESULT_NONE = 0;
  public static final int RESULT_NORMAL = 1;
  public static final int RESULT_ERROR = 2;
  public static final int RESULT_INTERRUPTED = 3;
  public static final int RESULT_TIMED_OUT = 4;
  public static final String[] RESULT_DESCRIPTIONS = {"NONE", "NORMAL", "ERROR", "INTERRUPTED", "TIMED OUT"};
  private static final int DEFAULT_TIMEOUT = Integer.MAX_VALUE;
  private static final int NEVER_TIMEOUT = -1;
  private static final int DEFAULT_RETRY = 0;
  private Process oProcess;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   INSTANCE VARIABLES
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private String commandLine = null;
  private PrintStream standardOutput = null;
  private PrintStream errorOutput = null;
  private int status = STATUS_READY;
  private int lastResult = RESULT_NONE;
  private int exitValue = 0;
  private Thread superviseThread = null;
  private Thread outputThread = null;
  private File workingDirectory;
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   CONSTRUCTORS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JOSCommand(String zCommandLine) {
    this.commandLine = zCommandLine;
  }
  public JOSCommand(String zCommandLine, PrintStream zStandardOutput, PrintStream zErrorOutput) {
    this.commandLine = zCommandLine;
    this.standardOutput = zStandardOutput;
    this.errorOutput = zErrorOutput;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //   METHODS
  //
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  //  API
  //
  public String getCommandLine() {
    return this.commandLine;
  }
  public void setCommandLine(String zCommandLine) throws Exception {
    this.checkNotExecuting();
    this.commandLine = zCommandLine;
  }
  public PrintStream getStandardOutput() {
    return this.standardOutput;
  }
  public void setStandardOutput(PrintStream zStandardOutput) throws Exception {
    this.checkNotExecuting();
    this.standardOutput = zStandardOutput;
  }
  public PrintStream getErrorOutput() {
    return this.errorOutput;
  }
  public void setErrorOutput(PrintStream zErrorOutput) throws Exception {
    this.checkNotExecuting();
    this.errorOutput = zErrorOutput;
  }
  public boolean isExecuting() {
    return this.status==STATUS_EXECUTING;
  }
  public int getStatus() {
    return this.status;
  }
  public String getStatusString() {
    return STATUS_DESCRIPTIONS[this.status];
  }
  public boolean hasTerminatedWithError() {
    return this.lastResult==RESULT_ERROR;
  }
  public boolean hasBeenInterrupted() {
    return this.lastResult==RESULT_INTERRUPTED;
  }
  public boolean hasTerminatedNormally() {
    return this.lastResult==RESULT_NORMAL;
  }
  public boolean hasTimedOut() {
    return this.lastResult==RESULT_TIMED_OUT;
  }
  public boolean isResultKnown() {
    return this.lastResult!=RESULT_NONE;
  }
  public int getLastResult() {
    return this.lastResult;
  }
  public int getProcessExitValue() {
    if (!this.isResultKnown()) {
      throw new RuntimeException("Process is still running; exit value is unknown at this state");
    }
    return this.exitValue;
  }
  public String getLastResultString() {
    return RESULT_DESCRIPTIONS[this.lastResult];
  }
  public void executeBackground() throws Exception {
    this.checkNotExecuting();
    this.primExecute(false, DEFAULT_TIMEOUT, DEFAULT_RETRY);
  }
  public void executeWaiting(int zTimeout, int zRetry) throws Exception {
    this.executeWaiting(zTimeout, zRetry, false, false);
  }
  public void executeWaiting(int zTimeout, int zRetry, boolean generateException, boolean showOKMessage) throws Exception {
    this.checkNotExecuting();
    this.primExecute(true, zTimeout, zRetry);
    if (generateException) {
      switch (this.getLastResult()) {
      case RESULT_ERROR:
        JExcepcion.SendError("Comando terminado con error:\n\n\t" + this.getCommandLine());
        break;
      case RESULT_INTERRUPTED:
        JExcepcion.SendError("Comando interrumpido:\n\n\t" + this.getCommandLine());
        break;
      case RESULT_TIMED_OUT:
        JExcepcion.SendError("Comando cancelado por timeout:\n\n\t" + this.getCommandLine());
        break;
      case RESULT_NONE:
        JExcepcion.SendError("Comando terminado con resultado desconocido:\n\n\t" + this.getCommandLine());
        break;
      }
    }
//    if (showOKMessage && this.getLastResult()==RESULT_NORMAL) {
//      UITools.MostrarMensaje("Comando ejecutado", "Comando ejecutado exitosamente:\n\n\t" + this.getCommandLine());
//    }
  }
  public void executeWaitingForEver() throws Exception {
		this.executeWaiting(NEVER_TIMEOUT, DEFAULT_RETRY);
  }
  public void executeWaiting() throws Exception {
    this.executeWaiting(DEFAULT_TIMEOUT, DEFAULT_RETRY);
  }
  public void kill() throws Exception {
    if (this.isExecuting()) {
      this.oProcess.destroy();
    }
  }
  //
  //  internal methods
  //
  private void checkNotExecuting() throws Exception {
    if (this.isExecuting()) {
      JExcepcion.SendError("OS Command is already executing");
    }
  }
  /**
   * Executes this command.<br>
   * If <code>zWaitToEnd</code> is true, waits for the process to end or time
   * out. If it times out, retries as many times as <code>zRetry</code> says.
   *
   * @author Leonardo Pronzolino
   */
  private void primExecute(boolean zWaitToEnd, int zTimeout, int zRetry) throws Exception {
    try {
      this.log("Running OS Command: " + this.commandLine);
      Process process = this.primStartProcess();
      if (zWaitToEnd) {
        this.waitFor(process, zTimeout, zRetry);
      }
    } catch (Exception ex) {
      PssLogger.logDebug(ex);
      this.lastResult = RESULT_ERROR;
      this.setReady();
      // no se debe usar JExcepcion.ProcesarError porque al ser un error java.io.IOException,
      // el método lo procesa distinto y se visualiza el error REAL, no el TEXT que enviamos nosotros.
      // EP
      JExcepcion.SendError("No se pudo ejecutar comando: " + this.commandLine);
    }
  }
  /**
   * Starts a process for this command's command line.<br>
   * Sets this command's status to STATUS_EXECUTING and last result to
   * RESULT_NONE.<br>
   * Also starts supervising the process status and output.
   *
   * @author Leonardo Pronzolino
   */
  private Process primStartProcess() throws Exception {
    this.lastResult = RESULT_NONE;
    this.status = STATUS_EXECUTING;
    Process process;
    if (workingDirectory!=null)
    	process = Runtime.getRuntime().exec(this.commandLine,null,workingDirectory);
    else
    	process = Runtime.getRuntime().exec(this.commandLine);
    this.superviseStatus(process);
    this.startOuput(process);
    return (oProcess=process);
  }
  /**
   * Waits for the given process to end end and then sets the result.<br>
   * If the process has not finished after the given timeout, destroys it and
   * set the last result to RESULT_TIMED_OUT.<br>
   * If retry is greater than 0, retries the process.
   *
   * @author Leonardo Pronzolino
   */
  private void waitFor(Process zProcess, int zTimeout, int zRetry) throws Exception {
    // try to execute until the process finishes OK or
    // the maximum attemps are reached
    Process process = zProcess;
    int currentAttempt = 0;
    int totalAttempts = zRetry + 1;
    while (currentAttempt < totalAttempts && !this.isResultKnown()) {
      currentAttempt++;
      // wait until the process finished or timed out
      long timeLimit = System.currentTimeMillis() + zTimeout;
      while (!this.isResultKnown() && 
      				(zTimeout==NEVER_TIMEOUT || System.currentTimeMillis() < timeLimit)) {
        try {Thread.sleep(50);} catch (Exception ex) {};
      };
      if (!this.isResultKnown()) {
        // if the process timed out, kill it
        this.lastResult = RESULT_TIMED_OUT;
        this.log("Process timed out after " + zTimeout + " milliseconds.");
        if (this.outputThread!=null) {
          Thread oThread = this.outputThread;
          this.outputThread=null;
          oThread.interrupt();
        }
        if (this.superviseThread != null) {
          Thread oThread = this.superviseThread;
          this.superviseThread=null;
          oThread.interrupt();
        }
        process.destroy();
        this.log("Process forced to terminate.");
        // if corresponds to retry, start the process again
        if (currentAttempt < totalAttempts) {
          this.log("Retrying process (attempt number: " + (currentAttempt+1) + ") ...");
          process = this.primStartProcess();
        }
      }
    }
    this.setReady();
  }
  /**
   * Supervises the given process status by starting a Thread which tries to
   * query its exit value.
   *
   * @author Leonardo Pronzolino
   */
  private void superviseStatus(Process zProcess) throws Exception {
    final Process process = zProcess;
    this.superviseThread = new Thread() {
      @Override
			public void run() {
        boolean executing = true;
        while (executing && !JOSCommand.this.isResultKnown() &&
                !Thread.currentThread().isInterrupted()
                && JOSCommand.this.superviseThread==Thread.currentThread()) {
          try {
            Thread.sleep(100);
            exitValue = process.exitValue();
              // here is thrown an IllegalThreadStateException
              // while the process is running
            executing = false;
          } catch (IllegalThreadStateException ex) {
            // just continue querying the exit value,
            // until it does not throw an exception
          } catch (InterruptedException ex) {
            executing = false;
          }
        }
        if (!JOSCommand.this.hasTimedOut()
            && JOSCommand.this.superviseThread==Thread.currentThread()) {
          JOSCommand.this.setReady();
        }
      }
    };
    this.superviseThread.setPriority(Thread.MIN_PRIORITY);
    this.superviseThread.start();
  }
  /**
   * Sets this command's status to STATUS_READY.<br>
   * This method is called when the command is intended to have finished.
   *
   * @author Leonardo Pronzolino
   */
  private void setReady() {
    if (this.isExecuting()) {
      if (!this.isResultKnown()) {
        this.lastResult = this.exitValue==0 ? RESULT_NORMAL : RESULT_ERROR;
      }
      this.status = STATUS_READY;
      this.log("OS Command terminated with result: " + this.getLastResultString());
    }
  }
  /**
   * Starts a Thread to query the given OS process standard and error output.
   *
   * @author Leonardo Pronzolino
   */
  private void startOuput(Process zProcess) {
    final boolean outputStandard = this.standardOutput!=null;
    final boolean outputError = this.errorOutput!=null;
    if (outputStandard || outputError) {
      final Process p = zProcess;
      this.outputThread = new Thread("command output - " + this.hashCode()) {
        @Override
				public void run() {
          try {
            outputLoop(p, outputStandard, outputError);
          } catch (Exception ex) {
            // do nothing; it doesn't matter here
          }
        }
      };
      this.outputThread.setPriority(Thread.MIN_PRIORITY);
      this.outputThread.start();
    }
  }
  /**
   * Queries the given OS process standard and error output, depending on what
   * <code>zOutputStandard</code> and <code>zOutputError</code> specify.
   *
   * @author Leonardo Pronzolino
   */
  private void outputLoop(Process zProcess, boolean zOutputStandard, boolean zOutputError) throws Exception {
    // get streams from the process
    InputStream stdOut = zOutputStandard ? zProcess.getInputStream() : null;
    InputStream errOut = zOutputError ? zProcess.getErrorStream() : null;
    // start loop
    int endChar = -1;
    int stdChar = zOutputStandard ? stdOut.read() : endChar;
    int errChar = zOutputError ? errOut.read() : endChar;
    boolean outputAvailable = stdChar!=endChar || errChar!=endChar;
    String stdLine = "";
    String errLine = "";
    while ((this.isExecuting() || outputAvailable)
            && !Thread.currentThread().isInterrupted()
            && Thread.currentThread()==JOSCommand.this.outputThread) {
      if (stdChar!=endChar) {
        if (stdChar=='\n') {
          this.osCmdLog(stdLine);
          stdLine = "";
        } else {
          stdLine += (char)stdChar;
        }
      }
      if (errChar!=endChar) {
        if (errChar=='\n') {
          this.osCmdErr(errLine);
          errLine = "";
        } else {
          errLine += (char)errChar;
        }
      }
      stdChar = zOutputStandard ? stdOut.read() : endChar;
      errChar = zOutputError ? errOut.read() : endChar;
      outputAvailable = stdChar!=endChar || errChar!=endChar;
    }
    if (stdLine.length()>0) {
      this.osCmdLog(stdLine);
    }
    if (errLine.length()>0) {
      this.osCmdErr(errLine);
    }
  }
  /**
   * Logs the given message to this command's associated standard output.
   *
   * @author Leonardo Pronzolino
   */
  private void osCmdLog(String zMsg) {
    if (this.standardOutput!=null) {
      this.standardOutput.println("[OSCMD.STDOUT] " + zMsg);
    }
  }
  /**
   * Logs the given message to this command's associated error output.
   *
   * @author Leonardo Pronzolino
   */
  private void osCmdErr(String zMsg) {
    if (this.errorOutput!=null) {
      this.errorOutput.println("[OSCMD.ERROUT] " + zMsg);
    }
  }
  /**
   * Logs the given message to the global debug printer.
   *
   * @author Leonardo Pronzolino
   */
  private void log(String zMsg) {
    PssLogger.logInfo(zMsg);
  }
	
	public File getWorkingDirectory() {
		return workingDirectory;
	}
	
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory=workingDirectory;
	}
}
