package pss.core.ui.sounds;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import pss.core.tools.PssLogger;

/**
 * <p>Title: Pss project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author eaguilar
 * @version 1.0
 */

public class MP3Player implements Player {

  private MP3SoundLibrary oLibrary = null;
  private Thread oLoopThread = null;
  private Thread oPlayThread = null;
  private String sID = null;
  private String sFileLoop = null;
  private Clip oLoopClip = null;
  private boolean on = true;

  // EZEQUIEL CABEZA DE CORCHO METE LOS NOMBRES EN INGLES


/*******************************************************
 * Constructor
 *******************************************************/
  public MP3Player() {
    super();
    //  THREAD SINGLE SOUNDS
    this.oPlayThread = new Thread() {
      @Override
			public void run() {
        while(!oPlayThread.isInterrupted()) {
          try {
            play();
            Thread.sleep(10);
          } catch (InterruptedException ie) {
            break;

          } catch (Exception e) {
            PssLogger.logError(e);
          }
        }
      }
    };
    this.oPlayThread.setPriority(Thread.MIN_PRIORITY);
    this.oPlayThread.start();
    // THREAD LOOP SOUNDS
    this.oLoopThread = new Thread() {
      @Override
			public void run() {
        while(!oLoopThread.isInterrupted()) {
          try {
            loop();
            Thread.sleep(10);
          } catch (InterruptedException ie) {
            break;

          } catch (Exception e) {
            PssLogger.logError(e);
          }
        }
      }
    };
    this.oLoopThread.setPriority(Thread.MIN_PRIORITY);
    this.oLoopThread.start();
  }

/*******************************************************
 * Implements createLibrary
 *******************************************************/
  public void createLibrary() {
    this.oLibrary = new MP3SoundLibrary();
  }

/*******************************************************
 * Returns the library
 *******************************************************/
  public SoundLibrary getLibrary() {
    return this.oLibrary;
  }

/**
 * Inplementation of play method
 */
  public synchronized void play(String zID) {
/*    if (this.oLibrary.containsKey(zID)) {
      this.sID = zID;
    }*/
  }

/*******************************************************
 * Inplementation of loop method
 *******************************************************/
  public synchronized void playLoop(String zID) {
/*    if (this.oLibrary.containsKey(zID)) {
      this.sFileLoop = zID;
    }*/
  }

/**
 * Implementation of stoploop
 */
  public void stop(String zID) {
/*    if (this.oLoopClip == null) return;
    if (this.oLoopClip.isRunning())
      this.oLoopClip.stop();*/
  }

/*******************************************************
 * loop
 *******************************************************/
  private synchronized void loop() throws Exception  {
    try {
      if (this.sFileLoop == null) return;
      AudioInputStream oStream = AudioSystem.getAudioInputStream(this.oLibrary.getFile(this.sFileLoop));
      AudioFormat oFormat = oStream.getFormat();
      uncompressFile(oStream, oFormat);
      DataLine.Info oInfo = new DataLine.Info(Clip.class, oFormat);
      this.oLoopClip = (Clip)AudioSystem.getLine(oInfo);
      this.oLoopClip.open(oStream);
      this.oLoopClip.loop(Clip.LOOP_CONTINUOUSLY);
      this.sFileLoop = null;
    } catch (LineUnavailableException e) {
      PssLogger.logError("No hay controlador de sonido.");
      this.oLoopThread.interrupt();
    } catch (Exception e) {
      PssLogger.logError(e);
    }
  }

/**
 * play loop
 */
  private synchronized void play() throws Exception {
    try {
      if (this.sID == null) return;
      AudioInputStream oStream = AudioSystem.getAudioInputStream(this.oLibrary.getFile(this.sID));
      AudioFormat oFormat = oStream.getFormat();
      uncompressFile(oStream, oFormat);
      DataLine.Info oInfo = new DataLine.Info(Clip.class, oFormat);
      Clip oClip = (Clip)AudioSystem.getLine(oInfo);
      oClip.open(oStream);
      oClip.start();
      this.sID = null;
    } catch (LineUnavailableException e) {
      PssLogger.logError("No hay controlador de sonido.");
      this.oPlayThread.interrupt();
    } catch (Exception e) {
      PssLogger.logError(e);
    }
  }

/**
 * Stop the player
 */
  public void stop() {
    try {
      this.oLoopThread.interrupt();
      this.oPlayThread.interrupt();
    } catch (Exception e) {}
  }

  /**
   * Uncompress the mp3 file to PCM
   */
  private void uncompressFile(AudioInputStream aisTema, AudioFormat afFormato) {
    AudioFormat.Encoding eCodificacion = AudioFormat.Encoding.PCM_SIGNED;
    float fTasaSampleo = afFormato.getSampleRate();
    int iTamanioSampleoBits = 16;
    int fCanales = afFormato.getChannels();
    int fTamanioCuadros = afFormato.getChannels() * 2;
    float fTasaCuadros = afFormato.getSampleRate();
    boolean bBigEndian = false;

    AudioFormat afNuevoFormato = new AudioFormat(eCodificacion,fTasaSampleo,
        iTamanioSampleoBits,fCanales,fTamanioCuadros,fTasaCuadros,bBigEndian);
    PssLogger.logDebug( "Converting audio format to " + afNuevoFormato );

    AudioInputStream aisNuevoTema = AudioSystem.getAudioInputStream( afNuevoFormato, aisTema );

    aisTema = aisNuevoTema;
    afFormato = afNuevoFormato;
  }

  public void load(String id, String file) throws Exception {
    this.oLibrary.load(id, file);
  }

  public void turnOff() {
    this.on = false;
  }
  public void turnOn() {
    this.on = true;
  }
  public boolean isTurnedOn() {
    return this.on;
  }
  public void clearLibrary() {}

}
