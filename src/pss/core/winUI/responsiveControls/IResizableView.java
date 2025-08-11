package pss.core.winUI.responsiveControls;

public interface IResizableView {
  public void setPosX(double zValue) throws Exception;
  public void setNullPosX() throws Exception;
  public double getPosX() throws Exception;
  public boolean isNullPosX()throws Exception;

  public void setPosY(double zValue) throws Exception;
  public void setNullPosY() throws Exception;
  public double getPosY() throws Exception;
  public boolean isNullPosY() throws Exception;

  public void setWidth(double zValue) throws Exception;
  public void setNullWidth() throws Exception;
  public double getWidth() throws Exception;
  public boolean isNullWidth() throws Exception;

  public void setHeight(double zValue) throws Exception;
  public void setNullHeight() throws Exception;
  public double getHeight() throws Exception;
  public boolean isNullHeight() throws Exception;


}
