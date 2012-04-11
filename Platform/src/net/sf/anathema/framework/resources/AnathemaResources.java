package net.sf.anathema.framework.resources;

import java.awt.Image;
import java.io.IOException;
import java.util.Locale;

import javax.swing.Icon;

import net.sf.anathema.lib.logging.Logger;
import net.sf.anathema.lib.resources.DefaultStringProvider;
import net.sf.anathema.lib.resources.FileStringProvider;
import net.sf.anathema.lib.resources.IAnathemaImageProvider;
import net.sf.anathema.lib.resources.IExtensibleDataSet;
import net.sf.anathema.lib.resources.IExtensibleDataSetRegistry;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.lib.resources.MultiSourceStringProvider;
import net.sf.anathema.lib.resources.StringProvider;

public class AnathemaResources implements IResources, IExtensibleDataSetRegistry {

  private static final Logger logger = Logger.getLogger(AnathemaResources.class);
  private final IAnathemaImageProvider imageProvider = new ImageProvider("icons"); //$NON-NLS-1$
  private final MultiSourceStringProvider stringHandler = new MultiSourceStringProvider();
  private final ExtensibleDataManager dataManager = new ExtensibleDataManager();

  public AnathemaResources() {
    try {
      stringHandler.add(new FileStringProvider("custom", getLocale())); //$NON-NLS-1$
      stringHandler.add(new DefaultStringProvider("Literal")); //$NON-NLS-1$
    }
    catch (IOException ioException) {
      logger.error("Error loading custom properties.", ioException); //$NON-NLS-1$
    }
  }

  public void addResourceBundle(String bundleName, ClassLoader classLoader) {
    stringHandler.add(new StringProvider(bundleName, getLocale(), classLoader)); //$NON-NLS-1$
  }

  public boolean supportsKey(String key) {
    return stringHandler.supportsKey(key);
  }

  public String getString(String key, Object... arguments) {
    return stringHandler.getString(key, arguments);
  }

  public Image getImage(Class< ? > requestor, String relativePath) {
    return imageProvider.getImage(requestor, relativePath);
  }

  public Icon getImageIcon(Class< ? > requestor, String relativePath) {
    return imageProvider.getImageIcon(requestor, relativePath);
  }

  private Locale getLocale() {
    return Locale.getDefault();
  }

  @Override
  public <T extends IExtensibleDataSet> T getDataSet(Class<T> setClass) {
	return dataManager.getDataSet(setClass);
  }
	
  @Override
  public void addDataSet(IExtensibleDataSet data) {
	dataManager.addDataSet(data);
  }
}