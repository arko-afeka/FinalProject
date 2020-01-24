package org.afeka.project;

import com.google.inject.AbstractModule;
import org.afeka.project.util.http.HTTPMessageParser;
import org.afeka.project.util.http.HTTPMessageParserImpl;

public class InfrastructureModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(HTTPMessageParser.class).to(HTTPMessageParserImpl.class);
  }
}
