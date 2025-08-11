package pss.core.tools;

/**
 * Description: A base class for garbage collecting something that implements
 * JGarbageCollectable interface.
 *
 * It is intended to be extended by garbage collectors that you want to trigger
 * with no judgment. They're a thread each, and you'll provide the logic
 * that avoids overlapping by implementing JGarbageCollectable into the object
 * from which we'll collect.
 *
 * @author Iván Rubín
 */

public abstract class JGarbageCollector extends Thread {
  protected JGarbageCollectable oDustbin;

  public JGarbageCollector( JGarbageCollectable zDustbin ) {
    oDustbin = zDustbin;
  }

  public JGarbageCollectable getCollectableObject() { return oDustbin; }

  /**
    * Will shit collect if and only if the handler for the objects that
    * we collect (dustbin?) answers that we can start collecting,
    * through JGarbageCollectable interface
    *
    */
  @Override
	public void run() {

    if( this.oDustbin.startsGarbageCollection() ) {
      collect();
      this.oDustbin.endsGarbageCollection();
    }
  }

  protected abstract void collect();
}
