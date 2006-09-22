package net.sf.anathema.framework.view.util;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.sf.anathema.framework.presenter.view.AbstractTabView;
import net.sf.anathema.framework.presenter.view.IMultiTabView;
import net.sf.anathema.lib.gui.IView;

public class MultiTabView extends AbstractTabView<Object> implements IMultiTabView {

  public MultiTabView() {
    super(false);
  }

  private TabbedView tabbedView = new TabbedView(TabDirection.Up);

  @Override
  protected void createContent(JPanel panel, Object properties) {
    panel.setLayout(new BorderLayout());
    panel.add(tabbedView.getComponent(), BorderLayout.CENTER);
  }

  public void addTabView(IView view, TabProperties tabProperties) {
    tabbedView.addTab(view, tabProperties);
    tabbedView.getComponent().revalidate();
  }
}