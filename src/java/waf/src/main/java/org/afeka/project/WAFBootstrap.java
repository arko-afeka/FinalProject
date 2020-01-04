package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.afeka.project.server.WAFServer;
import org.afeka.project.server.WAFServerFactory;

public class WAFBootstrap extends AbstractModule {
  @Inject private WAFServerFactory factory;

  public WAFBootstrap() {}

  WAFServer getServer(int port) {
    return factory.create(port);
  }
}
