package pss.core.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import pss.core.ui.components.JPssLabel;


public class FormPssProgressBar extends Window {
  static JProgressBar pProgressBar = new JProgressBar(0, 100);
  JPanel pPanel     = new JPanel();
  JPanel pPanelTitle = new JPanel();
  JPanel pPanelProgress = new JPanel();
  static JPssLabel pTitle = new JPssLabel();
  static JPssLabel pProgressTitle = new JPssLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();

  public FormPssProgressBar() {
    super(new Frame());
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void SetTitle( String zTitle ) {
    pTitle.setText(zTitle);
  }

  public static void SetProgressTitle( String zProgressTitle ) {
    pProgressTitle.setText(zProgressTitle);
    pProgressTitle.revalidate();
    pProgressTitle.repaint();
  }

  public static void SetPercentage( int zPorCiento ) {
    pProgressBar.setValue( zPorCiento );
  }

  private void jbInit() throws Exception {

    pPanelTitle.setLayout(borderLayout2 );
    pPanelProgress.setLayout(borderLayout1 );

    setLayout( new BorderLayout() );
    setBackground(Color.lightGray);
    setSize(new Dimension(295, 137));

    pPanel.setBorder( BorderFactory.createEtchedBorder() );
    pPanelTitle.setBounds(new Rectangle(4, 5, 287, 37));
    pPanelProgress.setBounds(new Rectangle(8, 47, 281, 23));
    pTitle.setFont(new java.awt.Font("Dialog", 1, 20));
    pTitle.setHorizontalAlignment(SwingConstants.CENTER);
    pTitle.setText("Pss");
    pProgressTitle.setText("Cargando la aplicación");
    pProgressTitle.setFont(new java.awt.Font("Dialog", 1, 14));
    pProgressTitle.setHorizontalAlignment(SwingConstants.CENTER);
    add(pPanel, BorderLayout.CENTER );
    pPanel.setLayout( null );

//    JRootPane oRootPanel = this.getRootPane();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((d.width - getSize().width) / 2, (d.height - ( getSize().height ) ) / 2);

    pProgressBar.setBounds(new Rectangle(28, 73, 244, 21));
    pPanel.add(pProgressBar, null);
    pPanel.add(pPanelTitle, null);
    pPanel.add(pPanelProgress, null);
    pPanelTitle.add(pTitle, BorderLayout.CENTER);
    pPanelProgress.add(pProgressTitle, BorderLayout.CENTER);

 }

}

