package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.afeka.project.server.WAFServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/** Hello world! */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new WAFModule());
    WAFBootstrap boostrap = injector.getInstance(WAFBootstrap.class);

    try {
      WAFServer server = boostrap.getServer(8080);
      server.start();
    } catch (IOException ex) {
      logger.error("Problem in startup", ex);
    }
  }
}
