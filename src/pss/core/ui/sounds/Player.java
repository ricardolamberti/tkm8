package pss.core.ui.sounds;

/**
 * Copyright:    Copyright (c) 2001
 * Company:      PSS
 * @author PSS
 * @version 1.0
 */

public interface Player {

  public final static int PLAY_ONCE = 0;
  public final static int PLAY_LOOP = 1;


  /**
   * Must override this behavior since each file has diferents encoders
   */
  public void play(String id);
  public void playLoop(String id);
  public void stop(String id);
  public void turnOn();
  public void turnOff();
  public boolean isTurnedOn();
  public void load(String id, String file) throws Exception;
  public void clearLibrary() throws Exception;
}
