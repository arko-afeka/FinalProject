package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.afeka.project.server.*;
import org.afeka.project.util.http.HTTPMessageParser;
import org.afeka.project.util.http.HTTPMessageParserImpl;

public class WAFModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(WAFService.class).to(WAFServiceImpl.class);
    bind(HTTPMessageParser.class).to(HTTPMessageParserImpl.class);
    install(
        new FactoryModuleBuilder()
            .implement(WAFServer.class, WAFServerImpl.class)
            .build(WAFServerFactory.class));
  }
}
