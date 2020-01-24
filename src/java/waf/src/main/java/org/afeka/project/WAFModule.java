package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.afeka.project.log.Log4j2OverrideConfiguration;
import org.afeka.project.log.LoggerInitializer;
import org.afeka.project.server.*;

public class WAFModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new InfrastructureModule());
    install(new ValidatorModule());
    install(
        new FactoryModuleBuilder()
            .implement(WAFServer.class, WAFServerImpl.class)
            .build(WAFServerFactory.class));
    bind(WAFService.class).to(WAFServiceImpl.class);
    bind(LoggerInitializer.class).to(Log4j2OverrideConfiguration.class);
  }
}
