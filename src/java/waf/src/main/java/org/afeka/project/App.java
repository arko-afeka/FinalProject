package org.afeka.project;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.afeka.project.log.LoggerInitializer;
import org.afeka.project.server.WAFServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {
  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new WAFModule());
    LoggerInitializer loggerInitializer = injector.getInstance(LoggerInitializer.class);

    loggerInitializer.init();
    Logger logger = LoggerFactory.getLogger(App.class);

    try {
      logger.info("Initialized logger");

      WAFBootstrap bootstrap = injector.getInstance(WAFBootstrap.class);

      int port = Integer.parseInt(System.getenv().getOrDefault("WAF_LISTEN_PORT", "8080"));
      logger.info("Port used for listening {}", port);;

      WAFServer server = bootstrap.getServer(port);
      server.start();
      logger.info("Server started on {}", port);
      server.await();
      logger.info("Stopping the server");
      server.stop();
      logger.info("Server stopped");
    } catch (IOException | InterruptedException ex) {
      logger.error("Something went wrong", ex);
    }
  }
}
