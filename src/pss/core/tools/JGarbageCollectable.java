package pss.core.tools;

/**
 * Description: Intended to connect a shit collector with a shit accumulator,
 * in order to be able to trigger collectors indiscriminately by delegating
 * (into the implementig object) the logic that avoids running more than
 * one collector at time.
 *
 * @author Iván Rubín
 */

public interface JGarbageCollectable {

  /**
   *  Should answer true if we are not being collected
   */
  public boolean startsGarbageCollection();
  /**
   *  Notification needed for synchronization
   */
  public void    endsGarbageCollection();
}
