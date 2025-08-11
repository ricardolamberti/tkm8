package pss.core.ui.sounds;

import javax.sound.sampled.Clip;

import pss.core.tools.PssLogger;

/**
 * Copyright: Copyright (c) 2001 Company: PSS
 * @author PSS
 * @version 1.0
 */

public class WavPlayer implements Player {

  private WavSoundLibrary library;
  private boolean on = true;

  /**
   * Constructor
   */
  public WavPlayer() {
    this.library = new WavSoundLibrary();
  }

  public void play(String id) {
    if (!this.isTurnedOn()) return;
    try {

      Clip clip = library.getClip(id);
      clip.setFramePosition(0);
      clip.start();

    } catch (Exception e) {
    	PssLogger.logError("Error playing POS sounds " + e.getClass().getName());
    	this.turnOff();
    }
  }  

  /**
   * Inplementation of loop method
   */
  public void playLoop(String id) {}

  public void stop(String id) {}

  public void turnOff() {
    this.on = false;
  }

  public void turnOn() {
    this.on = true;
  }

  public boolean isTurnedOn() {
    return this.on;
  }

  public void load(String id, String file) throws Exception {
    if (!this.isTurnedOn()) return;
    try {
      this.library.load(id, file);
    } catch (Exception e) {
      this.turnOff();
      PssLogger.logError("SOUNDS: Ocurrió un problema con el controlador de sonidos. Sonidos deshabilitados.");
      PssLogger.logError(e);
    }
  }

  public void clearLibrary() throws Exception {
    this.library.clear();
  }

} // class close
