package org.afeka.project;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.afeka.project.server.WAFServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {

    try {
      Injector injector = Guice.createInjector(new WAFModule());
      WAFBootstrap boostrap = injector.getInstance(WAFBootstrap.class);

      WAFServer server = boostrap.getServer(Integer.parseInt(System.getenv().getOrDefault("WAF_LISTEN_PORT", "8080")));
      server.start();
      server.await();
    } catch (IOException | InterruptedException ex) {
      logger.error("Problem in startup", ex);
    }
  }
}
