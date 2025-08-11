package pss.www.platform.applications;


public abstract class JAbstractApplication implements JApplication {

  protected static JAbstractApplication INSTANCE;
  private JAbstractApplicationContext oApplicationContext;
  
  

  public final void startUp(String[] args) throws Throwable {
    if (INSTANCE != null) {
      throw new RuntimeException("There is already an applicacion running: " + INSTANCE.getName());
    }
    try {
      INSTANCE = this;
      this.doStartUp(args);      
    } catch (Throwable e) {
      INSTANCE = null;
      throw e;
    }
  }
  
  
  public JAbstractApplicationContext getApplicationContext() {
    if (this.oApplicationContext==null) {
      this.oApplicationContext = this.createApplicationContext();
    }
    return this.oApplicationContext;
  }
  
  
  //
  //  METHODS TO IMPLEMENT IN SUBCLASSES
  //
  protected abstract void doStartUp(String[] args) throws Throwable;
  public abstract String getName();
  public abstract String getType();
  protected abstract JAbstractApplicationContext createApplicationContext();

}
