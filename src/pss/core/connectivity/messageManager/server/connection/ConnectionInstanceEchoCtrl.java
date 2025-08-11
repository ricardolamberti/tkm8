package pss.core.connectivity.messageManager.server.connection;

import java.util.Calendar;

import pss.core.connectivity.messageManager.server.MessageManager;

public class ConnectionInstanceEchoCtrl {
	public static final char ECHO_STATUS_TIME_WAITING = '1';
	public static final char ECHO_STATUS_ANSWER_WAITING = '2';

	private static final long ECHO_TIME_WAITING_TIMEOUT = 1000;
	private static final long ECHO_ANSWER_WAITING_TIMEOUT = 1000;
	private static final long ECHO_MAX_DEAD_WAITING_TIME = 3600000;
	private Calendar echoTimeCtrl = Calendar.getInstance();
	private char echoWaitingStatus = ' '; // TODO: Cambiar el nombre
	private long echoTimeoutValue = 0;
	private long echoTimeWaitingTimeout = ECHO_TIME_WAITING_TIMEOUT;
	private long echoAnswerWaitingTimeout = ECHO_ANSWER_WAITING_TIMEOUT;
	private long maxDeadTime = ECHO_MAX_DEAD_WAITING_TIME;

	public ConnectionInstanceEchoCtrl() {
		if (MessageManager.bDebugMode) {
			echoAnswerWaitingTimeout = 30000; // Si estoy debugueando espero 30 segundo a la respuesta
		}
	}

	public synchronized char getEchoWaitingStatus() {
		return this.echoWaitingStatus;
	}
	
	public synchronized boolean checkIfTimedOut() throws Exception {
		long lEnlapsedTime = System.currentTimeMillis() - echoTimeCtrl.getTimeInMillis();
		
		if (lEnlapsedTime > this.maxDeadTime) {
			 JEchoCtrlExcepcion.SendError("Timeout esperando respuesta de echo");
		}
		
		if (lEnlapsedTime  > this.echoTimeoutValue) {
			return true;
		}

		return false;
	}

	public synchronized void resetTimeWaiting() {
		resetTimeWaiting(true);
	}
	
	public synchronized void resetTimeWaiting(boolean bResetStatus) {
		this.echoTimeCtrl.setTimeInMillis(System.currentTimeMillis());
		if (bResetStatus) {
			if (this.getEchoWaitingStatus() == ConnectionInstanceEchoCtrl.ECHO_STATUS_ANSWER_WAITING) {
				this.setToTimeWaitingStatus();
			}
		}
	}
	
	public synchronized void setToTimeWaitingStatus() { 
		this.echoWaitingStatus = ConnectionInstanceEchoCtrl.ECHO_STATUS_TIME_WAITING;
		this.echoTimeCtrl.setTimeInMillis(System.currentTimeMillis());
		this.echoTimeoutValue = this.echoTimeWaitingTimeout;
	}
	
	public synchronized void setToAnswerWaitingStatus() {
		this.echoWaitingStatus = ConnectionInstanceEchoCtrl.ECHO_STATUS_ANSWER_WAITING;
		this.echoTimeCtrl.setTimeInMillis(System.currentTimeMillis());
		this.echoTimeoutValue = this.echoAnswerWaitingTimeout;
	}

	/**
	 * @param zEchoTimeWaitingTimeout the pEchoTimeWaitingTimeout to set
	 */
	public void setEchoTimeWaitingTimeout(long zEchoTimeWaitingTimeout) {
		echoTimeWaitingTimeout = zEchoTimeWaitingTimeout;
	}

	/**
	 * @param zEchoAnswerWaitingTimeout the pEchoAnswerWaitingTimeout to set
	 */
	public void setEchoAnswerWaitingTimeout(long zEchoAnswerWaitingTimeout) {
		echoAnswerWaitingTimeout = zEchoAnswerWaitingTimeout;
	}

	public void setMaxDeadTime(long maxDeadTime) {
  	this.maxDeadTime = maxDeadTime;
  }
	
}
