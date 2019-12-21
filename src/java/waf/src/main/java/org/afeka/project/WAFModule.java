package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.afeka.project.server.*;

public class WAFModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(WAFService.class).to(WAFServiceImpl.class);
    install(new HTTPModule());
    install(new ValidatorModule());
    install(
        new FactoryModuleBuilder()
            .implement(WAFServer.class, WAFServerImpl.class)
            .build(WAFServerFactory.class));
  }
}
