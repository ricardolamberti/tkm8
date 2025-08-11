package  pss.bsp.parseo;

/// el que implenta esta interfaz sabe que hacer con los datos
/// hay especializaciones por pais, lo que obliga a un cast en la implementacion del IParseo
public interface IFinder {
	public void start() throws Exception;
	public void stop() throws Exception;
	public void error(Exception e) throws Exception;
}
