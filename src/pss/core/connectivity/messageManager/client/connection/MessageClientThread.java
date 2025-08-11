package pss.core.connectivity.messageManager.client.connection;


public abstract class MessageClientThread extends MessageClient {
	Thread pThread;

	@Override
	public void launchMessageClient() throws Exception {
		super.launchMessageClient();
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				runClientProcess();
			}
		};
		
		this.pThread = new Thread(run);
		pThread.start();
		pThread.setName(this.getClass().getSimpleName());
	}
	
	/**
	 * Se debe implementar este metodo para ejecutar la logica propia del cliente
	 * que implementa la clase MessageClientThread
	 */
	protected abstract void runClientProcess();

}
