package by.tms.onlinerclone;

import by.tms.onlinerclone.configuration.HibernateConfiguration;
import by.tms.onlinerclone.configuration.WebConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Simon Pirko on 27.06.23
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[0];
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{WebConfiguration.class, HibernateConfiguration.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
